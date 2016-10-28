package com.base.userinfo.service.impl;

import com.base.database.trading.mapper.UsercontrollerDevAccountMapper;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.mapper.UsercontrollerEbayDevMapper;
import com.base.database.trading.mapper.UsercontrollerPaypalAccountMapper;
import com.base.database.trading.model.*;
import com.base.database.userinfo.mapper.*;
import com.base.database.userinfo.model.*;
import com.base.domains.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.userinfo.mapper.UserInfoServiceMapper;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.EncryptionUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.common.UUIDUtil;
import com.base.utils.exception.Asserts;
import com.base.utils.mailUtil.MailUtils;
import com.base.utils.scheduleother.service.SystemVIPUserCheckService;
import com.base.utils.scheduleother.service.SystemVipUserCostFeeService;
import com.trading.service.ITradingListingReport;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrtor on 2014/7/23.
 * 获取用户信息
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoServiceImpl implements com.base.userinfo.service.UserInfoService {
    static Logger logger = Logger.getLogger(UserInfoServiceImpl.class);
    @Autowired
    private UserInfoServiceMapper userInfoServiceMapper;//查询用户信息
    @Autowired
    private UsercontrollerDevAccountMapper usercontrollerDevAccountMapper;//查询开发帐号信息
    @Autowired
    private UsercontrollerEbayAccountMapper usercontrollerEbayAccountMapper;
    @Autowired
    private UsercontrollerEbayDevMapper UsercontrollerEbayDevMapper;
    @Autowired
    private UsercontrollerUserMapper userMapper;
    @Autowired
    private UsercontrollerUserEbayMapper userEbayMapper;
    @Autowired
    private UsercontrollerPaypalAccountMapper paypalAccountMapper;
    @Autowired
    private UsercontrollerOrgMapper usercontrollerOrgMapper;
    @Autowired
    private UsercontrollerUserRoleMapper userRoleMapper;
    @Autowired
    private ITradingListingReport iTradingListingReport;
    @Autowired
    private SystemInvitationCodeMapper systemInvitationCodeMapper;

    @Override
    public SessionVO getUserInfo(LoginVO loginVO){
        String enPwd= EncryptionUtil.pwdEncrypt(loginVO.getPassword(),loginVO.getLoginId());
        loginVO.setEnpassword(enPwd);
        SessionVO sessionVO=userInfoServiceMapper.querySessionVOInfo(loginVO);//用email做登录账户
        if(ObjectUtils.isLogicalNull(sessionVO)){return null;}
        Asserts.assertTrueRedirect(StringUtils.isNotEmpty(sessionVO.getStatus()) &&
                "1".equalsIgnoreCase(sessionVO.getStatus()) ,"账户已停用或者等待审核");
        Map map = new HashMap();
        map.put("loginId",loginVO.getLoginId());
        List<RoleVO> roleVOs = userInfoServiceMapper.queryUserRole(map);//获取角色信息
        List<PermissionVO> permissions;
        if(!ObjectUtils.isLogicalNull(roleVOs)){
            sessionVO.setRoleVOList(roleVOs);
            Map map1=new HashMap();
            map1.put("idArray",roleVOs);
            permissions = userInfoServiceMapper.queryPermissionByRoleID(map1);//获取权限列表
            if (sessionVO.getParentId()!=0L){
                Iterator<PermissionVO> it= permissions.iterator();
                while (it.hasNext()){
                    PermissionVO pv=it.next();
                    if((pv.getParentID()!=null && pv.getParentID()==6L) || pv.getPermissionID()==6L){
                        it.remove();
                    }
                }
            }
            sessionVO.setPermissions(permissions);
        }
       return sessionVO;
    }

    @Override
    /**获取开发者帐号的信息*/
    public UsercontrollerDevAccountExtend getDevInfo( Long id ) throws Exception {
        if(id==null || id==0L){//如果为空，那么就去取账户设置的默认dev
            /*SessionVO sessionVO = SessionCacheSupport.getSessionVO();
            Asserts.assertTrue(sessionVO.getDefaultDevID()!=null,"未设置默认开发帐号");
            id= Long.valueOf(sessionVO.getDefaultDevID());*/
            return new UsercontrollerDevAccountExtend();
        }
        UsercontrollerDevAccount x= usercontrollerDevAccountMapper.selectByPrimaryKey(id);
        return x.toExtend();
    }

    @Override
    /**保存绑定帐号过后的token*/
    public void saveToken(UsercontrollerEbayAccount ebayAccount, CommonParmVO commonParmVO) throws Exception {

        UsercontrollerEbayAccountExample ebayAccountExample=new UsercontrollerEbayAccountExample();
        ebayAccountExample.createCriteria().andEbayNameEqualTo(ebayAccount.getEbayName().trim())
                .andCreateUserEqualTo(ebayAccount.getCreateUser());
        List<UsercontrollerEbayAccount> ebayAccountList = usercontrollerEbayAccountMapper.selectByExample(ebayAccountExample);

        if (ebayAccountList==null || ebayAccountList.size()==0){
            usercontrollerEbayAccountMapper.insertSelective(ebayAccount);
            Asserts.assertTrue(ebayAccount.getId() != null, "ebay帐号id不能为空");
            UsercontrollerEbayDev ebayDev = new UsercontrollerEbayDev();
            ObjectUtils.toInitPojoForInsert(ebayDev);
            ebayDev.setDevAccountId(commonParmVO.getId());
            ebayDev.setEbayAccountId(ebayAccount.getId());
            UsercontrollerEbayDevMapper.insertSelective(ebayDev);

            UsercontrollerUserEbay userEbay = new UsercontrollerUserEbay();
            userEbay.setUserId(ebayAccount.getUserId());
            userEbay.setEbayId(ebayAccount.getId());
            userEbayMapper.insertSelective(userEbay);

            /**中途增加账单*/
            SessionVO sessionVO=SessionCacheSupport.getSessionVO();
            SystemVIPUserCheckService vipUserCheckService=ApplicationContextUtil.getBean(SystemVIPUserCheckService.class);
            UsercontrollerVipUser vu = vipUserCheckService.selectByUserId(((Long)sessionVO.getId()).toString());
            if (vu!=null){
                UsercontrollerUserBill bill=new UsercontrollerUserBill();
                bill.setBillSouce("system");
                bill.setBillType("deduct");
                bill.setCostsType("ebayAccount");
                bill.setCostsTarget(String.valueOf(ebayAccount.getId()));
                bill.setUserId((((Long) sessionVO.getId()).intValue()));
                bill.setStatus("0");
                SystemVipUserCostFeeService vipUserCostFeeService= ApplicationContextUtil.getBean(SystemVipUserCostFeeService.class);
                vipUserCostFeeService.middleCost(bill);
            }


        }else if (ebayAccountList.size()==1){
            UsercontrollerEbayAccount account=ebayAccountList.get(0);
            account.setEbayToken(ebayAccount.getEbayToken());
            account.setUseTimeStart(ebayAccount.getUseTimeStart());
            account.setUseTimeEnd(ebayAccount.getUseTimeEnd());
            account.setEbayStatus("1");
            ebayAccount.setId(account.getId());
            usercontrollerEbayAccountMapper.updateByPrimaryKey(account);
            Asserts.assertTrue(false,"帐户更新成功");
        }else {
            Asserts.assertTrue(false,"帐户数据有异常！");
        }


    }
    @Override
    /**更新将获取到的ebay帐号更新到表*/
    public void updateEbayAccount(UsercontrollerEbayAccount ebayAccount){
        usercontrollerEbayAccountMapper.updateByPrimaryKeySelective(ebayAccount);
    }

    @Override
    /**查询当前系统账户绑定了哪些ebay账户，并不是管理了哪些ebay帐号，请用queryACurrAllEbay*/
    public List<UsercontrollerEbayAccountExtend> getEbayAccountForCurrUser(Map map1,Page page){
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        Map map =new HashMap();
        map.put("userID",sessionVO.getId());
        map.put("resultNum","all");
        List<UsercontrollerEbayAccountExtend> ebayAccounts=userInfoServiceMapper.queryAllEbayAccountForUser(map,page);
        for (UsercontrollerEbayAccountExtend ebayAccountExtend : ebayAccounts){
            ebayAccountExtend.setEbayToken("");
        }
        return ebayAccounts;
    }

    @Override
    /**根据ebay帐号id 查询token*/
    public String getTokenByEbayID(Long ebayID){
        //boolean b=ebayIsBindDev(ebayID);
        //if(!b){return "1";}//返回1，代表当前ebay帐号没绑定当前设定的默认开发帐号

        UsercontrollerEbayAccount ebayAccount = userInfoServiceMapper.getTokenByEbayID(ebayID);
        return ebayAccount.getEbayToken();
    }
    @Override
    /**根据ebay帐号id 查询UsercontrollerEbayAccount tiken*/
    public UsercontrollerEbayAccount getEbayAccountByEbayID(Long ebayID){
        //boolean b=ebayIsBindDev(ebayID);
        //if(!b){return null;}//返回1，代表当前ebay帐号没绑定当前设定的默认开发帐号

        UsercontrollerEbayAccount ebayAccount = userInfoServiceMapper.getTokenByEbayID(ebayID);
        return ebayAccount;
    }

    @Override
    /**判断ebay账户是否绑定了默认的开发帐号*/
    public boolean ebayIsBindDev(Long ebayID){
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        if(sessionVO==null){return true;}
        UsercontrollerUser user=userMapper.selectByPrimaryKey(((Long)sessionVO.getId()).intValue());
        Long defaultDevID=user.getDefaultDevAccount();//当前用户的默认绑定dev帐号
        Map map = new HashMap();
        map.put("ebayID",ebayID);
        map.put("devID",defaultDevID);
        int r=userInfoServiceMapper.ebayIsBindDev(map);
        return r>0?true:false;
    }


    @Override
    /**查询所有的开发帐号*/
    public List<UsercontrollerDevAccount> queryAllDevAccount(){
        return userInfoServiceMapper.queryAllDevAccount();
    }

    @Override
    /**获取到用量最小的开发帐号*/
    public UsercontrollerDevAccountExtend getDevByOrder(Map map) throws Exception {
        UsercontrollerDevAccount u =TempStoreDataSupport.pullData("develop_account_info");
        if(u==null || u.getRunname()==null){
            u =userInfoServiceMapper.getDevByOrder(map);
            TempStoreDataSupport.pushData("develop_account_info",u);
        }
        return u.toExtend();
    }

    @Override
    /**累计开发帐号的使用次数*/
    public void addUseNum(Map map){
        userInfoServiceMapper.addUseNum(map);
    }

    @Override
    /**初清零开发帐号的使用次数*/
    public void initUseNum(Map map){
        userInfoServiceMapper.initUseNum(map);
    }

    @Override
    /**启用或者停用指定ebay账户*/
    public void startOrStopEbayAccount(Map map){
        Long ebayId= (Long) map.get("ebayId");
        String act = (String) map.get("act");
        if("stop".equalsIgnoreCase(act)){
            act="0";
        }else if ("start".equalsIgnoreCase(act)){
            act="1";
        }
        UsercontrollerEbayAccount e = usercontrollerEbayAccountMapper.selectByPrimaryKey(ebayId);
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        Asserts.assertTrue(sessionVO.getId()==e.getCreateUser().longValue(),"没有权限修改此记录！");
        e.setEbayStatus(act);
        usercontrollerEbayAccountMapper.updateByPrimaryKey(e);
    }
    @Override
    /**修改ebay账户*/
    public void editEbayAccount(Map map){
        Long ebayId= (Long) map.get("id");
        String name= (String) map.get("name");
        String code= (String) map.get("code");
        //String payPalId= (String) map.get("payPalId");
        UsercontrollerEbayAccount e = usercontrollerEbayAccountMapper.selectByPrimaryKey(ebayId);
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        Asserts.assertTrue(sessionVO.getId()==e.getCreateUser().longValue(),"没有权限修改此记录！");
        e.setEbayName(name);
        e.setEbayNameCode(code);
//        e.setPaypalAccountId(Long.valueOf(payPalId));
        usercontrollerEbayAccountMapper.updateByPrimaryKey(e);
    }
    @Override
    /**绑定ebay账户*/
    public void bindEbayPaypalAccount(Map map){
        Long ebayId= (Long) map.get("id");
        String payPalId= (String) map.get("payPalId");
        String isDefault=(String)map.get("isDefault");
        UsercontrollerEbayAccount e = usercontrollerEbayAccountMapper.selectByPrimaryKey(ebayId);
        Asserts.assertTrue(e!=null,"ebay帐号出错!");
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        Asserts.assertTrue(sessionVO.getId()==e.getCreateUser().longValue(),"没有权限修改此记录！");
        e.setPaypalAccountId(Long.valueOf(payPalId));
        e.setUseDefaultPaypalOnly(isDefault);
        usercontrollerEbayAccountMapper.updateByPrimaryKey(e);
    }

    @Override
    /**根据id查询ebay信息*/
    public UsercontrollerEbayAccountExtend queryEbayInfoById(Map map){
        UsercontrollerEbayAccount ea=usercontrollerEbayAccountMapper.selectByPrimaryKey((Long) map.get("ebayId"));
        UsercontrollerEbayAccountExtend eae=new UsercontrollerEbayAccountExtend();
        eae.setEbayName(ea.getEbayName());
        eae.setEbayNameCode(ea.getEbayNameCode());
        eae.setPaypalAccountId(ea.getPaypalAccountId());

        if(ea.getPaypalAccountId()!=null){
            UsercontrollerPaypalAccount s= paypalAccountMapper.selectByPrimaryKey(ea.getPaypalAccountId());
            eae.setPaypalName(s.getPaypalAccount());
        }
        return eae;
    }

    @Override
    /**新增注册用户*/
    public void regInsertUserInfo(Map map){
        UsercontrollerOrg org= (UsercontrollerOrg) map.get("UsercontrollerOrg");
        UsercontrollerUser user = (UsercontrollerUser) map.get("UsercontrollerUser");
        //String code = (String) map.get("code");
        //String codeStat = queryInvitationCode(code);
        //Asserts.assertTrue(StringUtils.isNotBlank(codeStat),"邀请码不正确");
        //updateInvitationCodeStat(code);
        UsercontrollerUserExample example=new UsercontrollerUserExample();
        example.createCriteria().andUserLoginIdEqualTo(user.getUserLoginId());

        List<UsercontrollerUser> uss = userMapper.selectByExample(example);
        Asserts.assertTrue(ObjectUtils.isLogicalNull(uss), "帐户已经注册过！");
        org.setStatus("1");
        usercontrollerOrgMapper.insert(org);
        user.setUserOrgId(org.getOrgId().intValue());
        user.setStatus("2");//申请以后需要开通才能使用
        user.setDefaultDevAccount(1L);
        user.setUserPassword(EncryptionUtil.pwdEncrypt(user.getUserPassword(), user.getUserLoginId()));
        user.setUserParentId(null);
        user.setCreateTime(new Date());
        userMapper.insert(user);
        //初始化首页统计信息
        this.iTradingListingReport.initListingReport(user.getUserId());
        UsercontrollerUserRole userRole = new UsercontrollerUserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(1);//通过网页注册的帐号，默认都是管理员帐号
        userRoleMapper.insert(userRole);
        try {
            MailUtils mailUtils=ApplicationContextUtil.getBean("mailUtils",MailUtils.class);
            Email email=new HtmlEmail();
            email.addTo("510871881@qq.com");
            email.addCc("892129701@qq.com");
            email.setSubject("TemBin审核通知");
            email.setMsg("有人申请tembin账号了！</br>账号是:"+user.getUserLoginId()+";</br>手机是:"+user.getTelPhone()+";</br>公司:"+org.getOrgName());
            mailUtils.sendMail(email);
        } catch (Exception e) {
            logger.error(e);
        }
    }


    @Override
    /**查询邀请码*/
    public String queryInvitationCode(String code){
        SystemInvitationCodeExample sce=new SystemInvitationCodeExample();
        sce.createCriteria().andCodeEqualTo(code).andStautEqualTo("0");
        List<SystemInvitationCode> lcode= systemInvitationCodeMapper.selectByExample(sce);
        if (ObjectUtils.isLogicalNull(lcode)){
            return null;
        }
        if (code.equalsIgnoreCase(lcode.get(0).getCode())){
            return "yes";
        }
        return null;
    }

    @Override
    /**将邀请码标记为已使用*/
    public void updateInvitationCodeStat(String code){
        SystemInvitationCodeExample sce=new SystemInvitationCodeExample();
        sce.createCriteria().andCodeEqualTo(code).andStautEqualTo("0");

        SystemInvitationCode s=new SystemInvitationCode();
        s.setStaut("1");

        systemInvitationCodeMapper.updateByExampleSelective(s,sce);
    }

    @Override
    /**生成邀请码*/
    public void createCode(){
        for (int i=0;i<50;i++){
            SystemInvitationCode s=new SystemInvitationCode();
            s.setStaut("0");
            s.setcDate(new Date());
            s.setCode(UUIDUtil.getUUID());
            //s.setId(i);
            systemInvitationCodeMapper.insert(s);
        }

    }

    /**
     * 通过ebayname查询是否已绑定
     * @param ebayName
     * @return
     */
    @Override
    public List<UsercontrollerEbayAccount> selectByEbayName(String ebayName){
        UsercontrollerEbayAccountExample ueae= new UsercontrollerEbayAccountExample();
        ueae.createCriteria().andEbayAccountEqualTo(ebayName);
        //usercontrollerEbayAccountMapper.countByExample(ueae);
        return this.usercontrollerEbayAccountMapper.selectByExample(ueae);
    }
    @Override
    public void delEbayid(Long ebayID){
        UsercontrollerEbayAccountExample ueae= new UsercontrollerEbayAccountExample();
        ueae.createCriteria().andIdEqualTo(ebayID);
        logger.error("有重复绑定！"+ebayID);
        usercontrollerEbayAccountMapper.deleteByPrimaryKey(ebayID);
    }

}
