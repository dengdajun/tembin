package com.base.userinfo.service.impl;

import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.trading.model.UsercontrollerEbayAccountExample;
import com.base.database.userinfo.mapper.UsercontrollerUserEbayMapper;
import com.base.database.userinfo.mapper.UsercontrollerUserMapper;
import com.base.database.userinfo.mapper.UsercontrollerUserRoleMapper;
import com.base.database.userinfo.mapper.UsercontrollerVipUserMapper;
import com.base.database.userinfo.model.*;
import com.base.domains.RoleVO;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.AddSubUserVO;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.userinfo.mapper.SystemUserManagerServiceMapper;
import com.base.userinfo.mapper.UserInfoServiceMapper;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.*;
import com.base.utils.exception.Asserts;
import com.base.utils.mailUtil.MailUtils;
import com.base.utils.scheduleother.service.SystemVIPUserCheckService;
import com.base.utils.scheduleother.service.SystemVipUserCostFeeService;
import com.google.common.collect.Collections2;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.*;

/**
 * Created by Administrator on 2014/9/19.
 * 系统账户管理
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemUserManagerServiceImpl implements SystemUserManagerService {
    static Logger logger = Logger.getLogger(SystemUserManagerServiceImpl.class);

    @Autowired
    private SystemUserManagerServiceMapper systemUserManagerServiceMapper;
    @Autowired
    private UsercontrollerUserMapper userMapper;
    @Autowired
    private UserInfoServiceMapper userInfoServiceMapper;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UsercontrollerUserRoleMapper userRoleMapper;
    @Autowired
    private UsercontrollerUserEbayMapper userEbayMapper;
    @Autowired
    private UsercontrollerEbayAccountMapper ebayAccountMapper;

    @Autowired
    private SystemVipUserCostFeeService vipUserCostFeeService;
    @Autowired
    private SystemVIPUserCheckService vipUserCheckService;
    @Autowired
    private UsercontrollerVipUserMapper vipUserMapper;

    @Override
    /**根据创建者查询系统用户以及列表*/
    public List<UsercontrollerUserExtend> queryAccountListByUserID(Map map, Page page){
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        map.put("userID",sessionVO.getId());
        return systemUserManagerServiceMapper.queryAccountListByUserID(map,page);
    }

    @Override
    /**主账号对用户账户进行停用启用等操作*/
    public void operationAccount(Map map){
        Integer uid=Integer.valueOf((String) map.get("userid"));
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        Integer curruser = ((Long)sessionVO.getId()).intValue();

        UsercontrollerUser u=userMapper.selectByPrimaryKey(uid);

        Asserts.assertTrue(u.getUserId() != curruser && !u.getUserId().equals(curruser), "管理员帐号不能被停用或者不能停用当前登录的账户");
        Asserts.assertTrue((u.getUserParentId() == null || u.getUserParentId() == curruser || u.getUserParentId().equals(curruser)), "当前帐号不是管理员帐号不能进行修改操作");

        /*UsercontrollerUserExample userExample=new UsercontrollerUserExample();
        userExample.createCriteria().andUserIdEqualTo(curruser);*/

        UsercontrollerUser user=new UsercontrollerUser();
        user.setUserId(uid);
        user.setStatus("stop".equalsIgnoreCase((String) map.get("doaction")) ? "0" : "1");

        if ("1".equalsIgnoreCase(user.getStatus())){
            UsercontrollerVipUser vu = vipUserCheckService.selectByUserId(curruser.toString());
            if (vu!=null){
                UsercontrollerUserBill bill=new UsercontrollerUserBill();
                bill.setBillSouce("system");
                bill.setBillType("deduct");
                bill.setCostsType("subUser");
                bill.setCostsTarget(user.getUserId().toString());
                bill.setUserId((((Long) sessionVO.getId()).intValue()));
                bill.setStatus("0");
                vipUserCostFeeService.middleCost(bill);
            }
        }


        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    /**查询当前用户定义了那些角色*/
    public List<RoleVO> queryAllCustomRole(Map map){
        if(!map.containsKey("userId")){
            SessionVO sessionVO=SessionCacheSupport.getSessionVO();
            map.put("userId",sessionVO.getId());
        }
        List<RoleVO> roleVOs = systemUserManagerServiceMapper.queryAllRoleByUserID(map);
        return roleVOs;
    }

    @Override
    /**获取用户有哪些角色*/
    public List<RoleVO> queryCurrUserRole(Map map){
        if(!map.containsKey("userId")){
            SessionVO sessionVO=SessionCacheSupport.getSessionVO();
            return sessionVO.getRoleVOList();
        }else {
            return systemUserManagerServiceMapper.queryUserRoleByUserID(map);
        }
    }

    @Override
    /**查询账户被分配了哪些ebay账户,需要token的话map中放入needToken*/
    public List<UsercontrollerEbayAccountExtend> queryCurrAllEbay(Map map){
        if(!map.containsKey("userID") && !map.containsKey("AllEbay")){
            SessionVO sessionVO=SessionCacheSupport.getSessionVO();
            if (sessionVO==null){return null;}
            map.put("userID",sessionVO.getId());
        }
        List<UsercontrollerEbayAccountExtend> ebays = userInfoServiceMapper.queryEbayAccountForUser(map);
        if(map!=null && map.containsKey("needToken")){

        }else {
            for (UsercontrollerEbayAccountExtend ebayAccountExtend : ebays){
                ebayAccountExtend.setEbayToken("");
            }
        }

        return ebays;
    }
    @Override
    /**查询账户绑定了哪些ebay账户--绑定开发帐号的操作*/
    public List<UsercontrollerEbayAccountExtend> queryACurrAllEbay(Map map){
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        map.put("userID",sessionVO.getId());
        List<UsercontrollerEbayAccountExtend> ebays = userInfoServiceMapper.queryAllEbayAccountForUser(map, Page.newAOnePage());
        for (UsercontrollerEbayAccountExtend ebayAccountExtend : ebays){
            ebayAccountExtend.setEbayToken("");
        }
        return ebays;
    }

    @Override
    /**编辑页面综合查询用户信息*/
    public AddSubUserVO queryAllUserAccountInfo(Long userID){
        AddSubUserVO addSubUserVO=new AddSubUserVO();
        Map map=new HashMap();
        map.put("userId",userID);
        map.put("userID",userID);
        List<UsercontrollerEbayAccountExtend> es=queryCurrAllEbay(map);
        addSubUserVO.setEbays(es);

        addSubUserVO.setRoles(systemUserManagerServiceMapper.queryUserRoleByUserID(map));

        UsercontrollerUser user = userMapper.selectByPrimaryKey(userID.intValue());
        addSubUserVO.setAddress(user.getAddress());
        addSubUserVO.setEmail(user.getUserEmail());
        addSubUserVO.setName(user.getUserName());
        addSubUserVO.setLoginID(user.getUserLoginId());
        addSubUserVO.setPhone(user.getTelPhone());

        return addSubUserVO;
    }

    @Override
    /**添加子账户*/
    public void addSubAccount(AddSubUserVO addSubUserVO){
        /**判断相同用户名是否已经存在*/
        UsercontrollerUserExample userExample=new UsercontrollerUserExample();
        userExample.createCriteria().andUserEmailEqualTo(addSubUserVO.getEmail());
        List<UsercontrollerUser> u1=userMapper.selectByExample(userExample);

        Asserts.assertTrue(ObjectUtils.isLogicalNull(u1),"相同邮箱已经存在!");

        UsercontrollerUser user=new UsercontrollerUser();
        user.setUserLoginId(addSubUserVO.getLoginID());
        user.setUserName(addSubUserVO.getName());
        user.setUserEmail(addSubUserVO.getEmail());
        user.setAddress(addSubUserVO.getAddress());
        user.setTelPhone(addSubUserVO.getPhone());

        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        user.setUserParentId(((Long)sessionVO.getId()).intValue());
        user.setUserPassword(EncryptionUtil.pwdEncrypt("123456", user.getUserEmail()));//初始密码是123456
        user.setStatus("1");//新增的ebay帐户都是启用状态
        user.setDefaultDevAccount(1L);
        user.setUserOrgId(((Long) sessionVO.getOrgId()).intValue());
        user.setUserLoginId(user.getUserEmail());
        userMapper.insertSelective(user);//插入user表

        /**判断费用*/
        UsercontrollerVipUser vu = vipUserCheckService.selectByUserId(((Long)sessionVO.getId()).toString());
        if (vu!=null){
            UsercontrollerUserBill bill=new UsercontrollerUserBill();
            bill.setBillSouce("system");
            bill.setBillType("deduct");
            bill.setCostsType("subUser");
            bill.setCostsTarget(user.getUserId().toString());
            bill.setUserId((((Long) sessionVO.getId()).intValue()));
            bill.setStatus("0");
            vipUserCostFeeService.middleCost(bill);
        }

        insertRoleAndEbay(addSubUserVO, user);
    }
    @Override
    /**编辑子账户信息*/
    public void editSubAccount(AddSubUserVO addSubUserVO){
        Asserts.assertTrue(addSubUserVO.getUserID() != null, "主键不能为空!");

        UsercontrollerUser user1= userMapper.selectByPrimaryKey(addSubUserVO.getUserID().intValue());
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();

        Asserts.assertTrue(user1.getUserOrgId().longValue()==sessionVO.getOrgId(),"没有权限修改");
        Asserts.assertTrue(pdRole(1L, sessionVO.getRoleVOList()), "不是管理员角色！");
        Asserts.assertTrue(sessionVO.getParentId() == 0L, "只能由最顶层帐号来做编辑操作!");

        UsercontrollerUser user=new UsercontrollerUser();
        user.setUserName(addSubUserVO.getName());
        user.setUserEmail(addSubUserVO.getEmail());
        user.setAddress(addSubUserVO.getAddress());
        user.setTelPhone(addSubUserVO.getPhone());
        user.setUserId(addSubUserVO.getUserID().intValue());
        int i = userMapper.updateByPrimaryKeySelective(user);
        insertRoleAndEbay(addSubUserVO,user);
    }

    @Override
    /**判断某个账户是否有某个角色roleID参见role表,比如判断当前用户是否有管理员权限*/
    public boolean pdRole(Long roleID,List<RoleVO> roleVOList){
        for (RoleVO roleVO : roleVOList){
            if(roleID==roleVO.getRoleID()){
                return true;
            }
        }
        return false;
    }

    @Override
    /**判断当前用户是否有管理员权限*/
    public boolean isAdminRole(){
       SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        List<RoleVO> roleVOs = sessionVO.getRoleVOList();
        for (RoleVO roleVO : roleVOs){
            if(roleVO.getRoleID()==1L){
                return true;
            }
        }
        return false;
    }

    @Override
    /**判断当前用户是否是主账号*/
    public boolean isMainUserAcount(){
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        List<RoleVO> roleVOs = sessionVO.getRoleVOList();
        for (RoleVO roleVO : roleVOs){
            if(roleVO.getRoleID()==1L&&sessionVO.getParentId()==0){
                return true;
            }
        }
        return false;
    }

    @Override
    /**根据当前用户的orgid查询所有系统用户*/
    public List<UsercontrollerUserExtend> queryAllUsersByOrgID(String isShowStop){
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        if(sessionVO==null){throw new RuntimeException("sessionVO不能为空!");}
        Map map=new HashMap();
        map.put("orgID",sessionVO.getOrgId());
        if(StringUtils.isEmpty(isShowStop)){isShowStop="no";}
        map.put("isShowStopOnly",isShowStop);
        return systemUserManagerServiceMapper.queryAllUsersByOrgID(map);
    }

    @Override
    /**根据userid获取用户的orgid*/
    public String gerOrgIdByuserId(Integer userId){
        UsercontrollerUser user=userMapper.selectByPrimaryKey(userId);
        return user.getUserOrgId().toString();
    }


    /**清除并添加用户的权限和ebay帐号信息*/
    private void insertRoleAndEbay(AddSubUserVO addSubUserVO,UsercontrollerUser user){
        if(user.getUserId()!=null){
            UsercontrollerUserRoleExample userRoleExample=new UsercontrollerUserRoleExample();
            userRoleExample.createCriteria().andUserIdEqualTo(user.getUserId());
            userRoleMapper.deleteByExample(userRoleExample);
            UsercontrollerUserEbayExample userEbayExample=new UsercontrollerUserEbayExample();
            userEbayExample.createCriteria().andUserIdEqualTo(user.getUserId().longValue());
            userEbayMapper.deleteByExample(userEbayExample);
        }
        /**插入角色表*/
        for (RoleVO roleVO : addSubUserVO.getRoles()){
            if(roleVO.getRoleID()==null){continue;}
            UsercontrollerUserRole userRole=new UsercontrollerUserRole();
            userRole.setRoleId(roleVO.getRoleID().intValue());
            userRole.setUserId(addSubUserVO.getUserID()==null?user.getUserId():addSubUserVO.getUserID().intValue());
            userRoleMapper.insertSelective(userRole);
        }

        /**插入user-ebay帐号表*/
        for (UsercontrollerEbayAccountExtend uea:addSubUserVO.getEbays()){
            if(uea.getId()==null){continue;}
            UsercontrollerUserEbay userEbay=new UsercontrollerUserEbay();
            userEbay.setEbayId(uea.getId());
            userEbay.setUserId(addSubUserVO.getUserID()==null?user.getUserId().longValue():addSubUserVO.getUserID());
            userEbayMapper.insertSelective(userEbay);
        }
    }

    @Override
    /**修改密码*/
    public void changePWD(String oldPWD,String newPWD){
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        UsercontrollerUser user = userMapper.selectByPrimaryKey((int) sessionVO.getId());
        String EOld = EncryptionUtil.pwdEncrypt(oldPWD, user.getUserLoginId());
        Asserts.assertTrue(user.getUserPassword().equalsIgnoreCase(EOld),"原密码错误！");
        user.setUserPassword(EncryptionUtil.pwdEncrypt(newPWD, user.getUserLoginId()));
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    /**发送找回密码的验证码*/
    public void sendSafeCode(Map map){
        HttpServletRequest request = (HttpServletRequest) map.get("HttpServletRequest");
        String loginUserID = (String) map.get("loginUserID");

        Object sdate=request.getSession().getAttribute("opDateTime_");
        if(sdate!=null){
            long date1= ((Date) sdate).getTime();
            Date date2=new Date();
            long t=((date2.getTime()-date1)/1000L);
            Asserts.assertTrue(t>60L,"请一分钟后再操作!");
        }
        String scode= UUIDUtil.getUUID();
        request.getSession().setAttribute("passWordSafeCode_",scode);
        request.getSession().setAttribute("opDateTime_",new Date());
        request.getSession().setAttribute("loginUserIDchangePWD_",loginUserID);
        Map map1=new HashMap();
        map1.put("emailLoginID",loginUserID);
        map1.put("scode",scode);
        TempStoreDataSupport.pushData(request.getSession().getId()+"_session",map1);

        UsercontrollerUserExample userExample = new UsercontrollerUserExample();
        userExample.createCriteria().andUserEmailEqualTo(loginUserID);
        List<UsercontrollerUser> user = userMapper.selectByExample(userExample);
        Asserts.assertTrue(!ObjectUtils.isLogicalNull(user),"此邮箱尚未注册！");
        UsercontrollerUser usert=user.get(0);

        Asserts.assertTrue(StringUtils.isNotEmpty(usert.getUserEmail()),"该帐号没有指定email，请联系管理员");

        Email email = new HtmlEmail();
        try {

            SystemLog log=new SystemLog();
            log.setEventname(SystemLogUtils.FIND_PASSWORD);
            log.setOperuser(usert.getUserLoginId());
            log.setEventdesc("通过邮件找回密码!邮箱为"+usert.getUserEmail());
            SystemLogUtils.saveLog(log);

            email.addTo(usert.getUserEmail());
            email.setSubject("tembin密码修改");
            String nowUel=request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+nowUel+"/";
            String urll=basePath+"/findPWD.jsp?email="+loginUserID+"&scode="+scode+""+"&sid="+request.getSession().getId();
            email.setMsg("您好，<br/>您申请了本次修改密码的请求，<a href="+urll+">点击修改密码</a> <br/>或者请复制以下链接至浏览器执行!"+urll+"" +
                    " <br>本链接五分钟内有效！<br>本邮件由系统生成，请勿回复");
            //email.setTextMsg("或者复制此连接到浏览器执行"+urll);

            MailUtils mailUtils= (MailUtils) ApplicationContextUtil.getBean("mailUtils");
            mailUtils.sendMail(email);

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

    }

    @Override
    /**修改被遗忘的密码*/
    public String doChangeForgetPassWord(Map map){
        HttpServletRequest request = (HttpServletRequest) map.get("HttpServletRequest");
        String loginUserID = (String) map.get("loginUserId");
        String newPWD = (String) map.get("newPWD");
        String safeCode = (String) map.get("safeCode");
        String sid = (String) map.get("sid");

        Map<String,String> map1= TempStoreDataSupport.pullData(sid+"_session");
        Asserts.assertTrue(!ObjectUtils.isLogicalNull(map1), "验证码已经过期!请重新获取!");
        String safeFinal=map1.get("scode");
        String loginUserIDEmail=map1.get("emailLoginID");
        Asserts.assertTrue(safeCode.equals(safeFinal) && loginUserID.equals(loginUserIDEmail), "验证码和需要修改的账户密码无效");

        /*Object remotsafeCodeobj= request.getSession().getAttribute("passWordSafeCode_");
        Asserts.assertTrue(!ObjectUtils.isLogicalNull(remotsafeCodeobj),"请先获取邮箱验证码，谢谢！");*/

        /*String remotsafeCode = (String) remotsafeCodeobj;
        Asserts.assertTrue(remotsafeCode.equals(safeCode),"邮箱验证码不正确!");
        String loginUserId1= (String) request.getSession().getAttribute("loginUserIDchangePWD_");
        Asserts.assertTrue(loginUserID.equals(loginUserId1),"需要修改的账户名不对！");*/


        UsercontrollerUserExample userExample = new UsercontrollerUserExample();
        userExample.createCriteria().andUserEmailEqualTo(loginUserID);
        List<UsercontrollerUser> users = userMapper.selectByExample(userExample);
        Asserts.assertTrue(!ObjectUtils.isLogicalNull(users),"没有此帐号");
        UsercontrollerUser user=users.get(0);

        String enewp = EncryptionUtil.pwdEncrypt(newPWD, loginUserID);
        user.setUserPassword(enewp);
        userMapper.updateByPrimaryKeySelective(user);
        return "success";
    }

    @Override
    public UsercontrollerUser selectUsercontrollerUserByEail(String email) {
        UsercontrollerUserExample example=new UsercontrollerUserExample();
        UsercontrollerUserExample.Criteria cr=example.createCriteria();
        cr.andUserIdEqualTo(Integer.valueOf(email));
        List<UsercontrollerUser> list=userMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }
    @Override
    public UsercontrollerUser selectUsercontrollerUserByEail1(String email) {
        UsercontrollerUserExample example=new UsercontrollerUserExample();
        UsercontrollerUserExample.Criteria cr=example.createCriteria();
        cr.andUserEmailEqualTo(email);
        List<UsercontrollerUser> list=userMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }



    @Override
    /**停用整个公司的帐号和ebay帐号*/
    public void stopAllUserByOrg(String orgID){
        Map map=new HashMap();
        map.put("orgID",orgID);
        map.put("isShowStopOnly", "no");
        List<UsercontrollerUserExtend> us = systemUserManagerServiceMapper.queryAllUsersByOrgID(map);

        List<Integer> ids=new ArrayList<>();
        for (UsercontrollerUserExtend u:us){
            ids.add(u.getUserId());
            if (u.getUserParentId()==null || u.getUserParentId()==0){
                /*List<Long>  ebayids=new ArrayList<>();
                Page page= new Page();
                page.setPageSize(1000);
                page.setCurrentPage(1);
                Map map1 =new HashMap();
                map1.put("userID", u.getUserId());
                map1.put("resultNum", "all");
                List<UsercontrollerEbayAccountExtend> ebayAccounts=userInfoServiceMapper.queryAllEbayAccountForUser(map1,page);
                *///List<UsercontrollerEbayAccountExtend> accountExtends = userInfoService.getEbayAccountForCurrUser(new HashMap(),page);
                /*if (!ObjectUtils.isLogicalNull(ebayAccounts)){
                    for (UsercontrollerEbayAccountExtend e:ebayAccounts){
                        ebayids.add(e.getId());
                    }*/
                    UsercontrollerEbayAccount ebayAccount=new UsercontrollerEbayAccount();
                    ebayAccount.setEbayStatus("0");
                    UsercontrollerEbayAccountExample ebayAccountExample=new UsercontrollerEbayAccountExample();
                    ebayAccountExample.createCriteria().andCreateUserEqualTo(u.getUserId().longValue());
                    ebayAccountMapper.updateByExampleSelective(ebayAccount, ebayAccountExample);
                logger.error(">>>>ebayCreateUser帐号被停用>>>>" + u.getUserLoginId() + ">>" + u.getUserId());
               // }
                UsercontrollerVipUser vipUser=new UsercontrollerVipUser();
                vipUser.setStatus("1");
                vipUser.setUpdateTime(new Date());
                //vipUser.setUserId(u.getUserId().toString());
                UsercontrollerVipUserExample vipuserExample=new UsercontrollerVipUserExample();
                vipuserExample.createCriteria().andUserIdEqualTo(u.getUserId().toString());
                vipUserMapper.updateByExampleSelective(vipUser, vipuserExample);
            }
        }

        UsercontrollerUserExample userExample=new UsercontrollerUserExample();
        userExample.createCriteria().andUserIdIn(ids);
        UsercontrollerUser ur=new UsercontrollerUser();
        ur.setStatus("0");
        userMapper.updateByExampleSelective(ur, userExample);
        logger.error(">>>>User帐号被停用>>>>" + ids.toString());



    }

}
