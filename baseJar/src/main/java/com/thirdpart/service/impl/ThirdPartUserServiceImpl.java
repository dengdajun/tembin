package com.thirdpart.service.impl;

import com.base.database.userinfo.mapper.*;
import com.base.database.userinfo.model.*;
import com.base.domains.PermissionVO;
import com.base.domains.RoleVO;
import com.base.domains.SessionVO;
import com.base.userinfo.mapper.UserInfoServiceMapper;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.RequestResponseContext;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.EncryptionUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.thirdpart.mapper.ThirdPartMapper;
import com.thirdpart.service.ThirdPartUserService;
import com.thirdpart.utils.ThirtPartUtil;
import com.thirdpart.vo.AccessKeyVO;
import com.thirdpart.vo.ThirdPartAccountVO;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * Created by Administrator on 2015/4/24.
 * 第三方用户
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ThirdPartUserServiceImpl implements ThirdPartUserService {
    static Logger logger = Logger.getLogger(ThirdPartUserServiceImpl.class);

    @Autowired
    private UsercontrollerUserMapper userMapper;//查询用户信息
    @Autowired
    private UserInfoServiceMapper userInfoServiceMapper;//查询用户信息
    @Autowired
    private UsercontrollerUserRoleMapper userRoleMapper;
    @Autowired
    private ThirdPartMapper thirdPartMapper;
    @Autowired
    private UsercontrollerOrgMapper usercontrollerOrgMapper;
    @Autowired
    private UsercontrollerUserThirdMarkMapper thirdMarkMapper;
    @Autowired
    private UsercontrollerThirdPartMapper thirdPartMarkMapper;//查询第三方mark

    private final static String preCacheStr="THIRDPART_";//缓存中的第三方数据标志
    private final static String thirtPartUserMarkInSession="third";

    @Override
    /**同步帐户 一个主账号所有子账号为一个list*/
    public List<AccessKeyVO> sync3PartAccount(List<ThirdPartAccountVO> thirdPartAccountVOs){

        String thirdMark=thirdPartAccountVOs.get(0).getThirdPartMark();
        if (StringUtils.isBlank(thirdMark)){
            logger.error("thirdPartMark不能为空");
            return null;
        }
        UsercontrollerThirdPartExample example=new UsercontrollerThirdPartExample();
        example.createCriteria().andThirdPartMarkEqualTo(thirdMark);
        List<UsercontrollerThirdPart> partList = thirdPartMarkMapper.selectByExample(example);
        if (ObjectUtils.isLogicalNull(partList)){
            logger.error("不存在的thirdpart...");
            return null;
        }

        UsercontrollerOrg org = new UsercontrollerOrg();//公司
        org.setOrgName(thirdPartAccountVOs.get(0).getOrgName());
        org.setStatus("1");
        usercontrollerOrgMapper.insertSelective(org);
        /**先写入主账号*/
        ThirdPartAccountVO ma= ThirtPartUtil.findMainAccount(thirdPartAccountVOs);
        UsercontrollerUser user=ma.toUser();
        user.setUserOrgId(org.getOrgId().intValue());
        user.setUserParentId(null);
        userMapper.insert(user);
        UsercontrollerUserRole userRole = new UsercontrollerUserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(1);
        userRoleMapper.insert(userRole);
        UsercontrollerUserThirdMark userThirdMark=new UsercontrollerUserThirdMark();
        userThirdMark.setCreateTime(new Date());
        Map<String,String> m= ThirtPartUtil.formatDate2YMDH(new Date());
        String dt=m.get("year")+m.get("month")+m.get("day");
        userThirdMark.setMark(EncryptionUtil.md5Encrypt2(user.getUserLoginId() + "tembinMark" + dt));
        userThirdMark.setUserId(Long.valueOf(user.getUserId()));
        thirdMarkMapper.insertSelective(userThirdMark);
        List<AccessKeyVO> voList=new ArrayList<>();
        AccessKeyVO v=new AccessKeyVO();
        v.setUser(user.getUserLoginId());
        v.setUserKey(userThirdMark.getMark());
        voList.add(v);
        sunc3PartSubAccount(ma, user, thirdPartAccountVOs, voList);
        return voList;
    }
    /**写入子账号*/
    public void sunc3PartSubAccount(ThirdPartAccountVO mainAccountVO,UsercontrollerUser mUser,List<ThirdPartAccountVO> thirdPartAccountVOList,List<AccessKeyVO> accessKeyVOList){
        for (ThirdPartAccountVO v:thirdPartAccountVOList){
            if (mainAccountVO.equals(v) || mainAccountVO.getUserAccount().equalsIgnoreCase(v.getUserAccount())||
                    StringUtils.isBlank(v.getParentID()) || !mainAccountVO.getId().toString().equalsIgnoreCase(v.getParentID())||
                    !mainAccountVO.getThirdPartMark().equalsIgnoreCase(v.getThirdPartMark())){
                continue;
            }
            UsercontrollerUser user=v.toUser();
            user.setUserOrgId(mUser.getUserOrgId());
            user.setUserParentId(mUser.getUserId());
            userMapper.insert(user);
            UsercontrollerUserRole userRole = new UsercontrollerUserRole();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(1);
            userRoleMapper.insert(userRole);
            UsercontrollerUserThirdMark userThirdMark=new UsercontrollerUserThirdMark();
            userThirdMark.setCreateTime(new Date());
            Map<String,String> m= ThirtPartUtil.formatDate2YMDH(new Date());
            String dt=m.get("year")+m.get("month")+m.get("day");
            userThirdMark.setMark(EncryptionUtil.md5Encrypt2(user.getUserLoginId() + "tembinMark" + dt));
            userThirdMark.setUserId(Long.valueOf(user.getUserId()));
            thirdMarkMapper.insertSelective(userThirdMark);
            AccessKeyVO vv=new AccessKeyVO();
            vv.setUser(user.getUserLoginId());
            vv.setUserKey(userThirdMark.getMark());
            accessKeyVOList.add(vv);
        }

    }


    /**放入AccessKey*/
    public void putAccessKey(AccessKeyVO accessKeyVO){
        SessionCacheSupport.putOther(preCacheStr+accessKeyVO.getAccessKey(),accessKeyVO);
    }
    @Override
    /**根据用户的mark和第三方的mark，生成accessKey*/
    public String generateAccessKey(String userMark, String thirdPartMark){
        Map map=new HashMap();
        map.put("userMark",userMark);
        map.put("thirdMark",thirdPartMark);
        AccessKeyVO v= thirdPartMapper.findUserByKey(map);
        if (v==null){return null;}
        v.setThirdPartMark(thirdPartMark);
        Map<String,String> m= ThirtPartUtil.formatDate2YMDH(new Date());
        String dt=m.get("year")+m.get("month")+m.get("day")+m.get("hour")+m.get("minute");
        v.setAccessKey(EncryptionUtil.md5Encrypt2(v.getUser() + "tembinAccessKey" + dt));
        putAccessKey(v);
        return v.getAccessKey();
    }

    @Override
    /**模拟登陆，放入登陆信息*/
    public void thirdPartLogin(String accessKey){
        //AccessKeyVO accessKeyVO1=new AccessKeyVO();
        //accessKeyVO1.setAccessKey("123456789");
        //accessKeyVO1.setThirdPartMark("selltool");
       // accessKeyVO1.setUser("caixu23@126.com");
      //  putAccessKey(accessKeyVO1);
        AccessKeyVO accessKeyVO=SessionCacheSupport.getOther(preCacheStr + accessKey);
        if (accessKeyVO==null){
            logger.error("还没有放入accessKey");
            return;
        }
        SessionVO sessionVO=findSessionVOBYAccessKey(accessKeyVO.getAccessKey(),accessKeyVO.getThirdPartMark());
        HttpServletRequest request = RequestResponseContext.getRequest();
        request.getSession().setAttribute(SessionCacheSupport.USERLOGINID, sessionVO.getLoginId());
        sessionVO.setSessionID(request.getSession().getId());
        sessionVO.setThirdPartLogin("yes");
        SessionCacheSupport.put(sessionVO);
    }

    /**通过accesskey找对应说sessionVO*/
    public SessionVO findSessionVOBYAccessKey(String key,String thirdPartMark){
        AccessKeyVO v = SessionCacheSupport.getOther(preCacheStr + key);
        String loginId=v.getUser();

        /*UsercontrollerUserExample example=new UsercontrollerUserExample();
        example.createCriteria().andUserLoginIdEqualTo(loginId);
        List<UsercontrollerUser> users = userMapper.selectByExample(example);
        if (ObjectUtils.isLogicalNull(users)){
            logger.error("第三方用户查询报错，无此用户！");
            return null;
        }
        if (users.size()>1){
            logger.error("第三方用户查询异常！数据超过一条！");
            return null;
        }
        UsercontrollerUser user=users.get(0);*/
        Map map=new HashMap<>();
        map.put("loginId",loginId);
        map.put("thirdPartMark",thirdPartMark);
        SessionVO sessionVO=thirdPartMapper.findSessionVO4ThirdPart(map);
        if (sessionVO==null){
            logger.error("第三方用户查询报错，无此用户！");
            return null;
        }
        Asserts.assertTrue(StringUtils.isNotEmpty(sessionVO.getStatus()) && "1".equalsIgnoreCase(sessionVO.getStatus()), "账户已停用");
        sessionVO.setThirtPartMark(thirtPartUserMarkInSession);
        Map mapm = new HashMap();
        mapm.put("loginId",loginId);
        List<RoleVO> roleVOs = userInfoServiceMapper.queryUserRole(mapm);//获取角色信息
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
                        continue;
                    }
                }
            }
            sessionVO.setPermissions(permissions);
        }
        return sessionVO;
    }
}
