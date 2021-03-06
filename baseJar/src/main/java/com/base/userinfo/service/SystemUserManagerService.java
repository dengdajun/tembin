package com.base.userinfo.service;

import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.domains.RoleVO;
import com.base.domains.userinfo.AddSubUserVO;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/9/19.
 */
public interface SystemUserManagerService {
    /**查询系统用户以及列表*/
    List<UsercontrollerUserExtend> queryAccountListByUserID(Map map,Page page);

    /**对用户账户进行停用启用等操作*/
    void operationAccount(Map map);

    /**查询当前用户定义了那些角色*/
    List<RoleVO> queryAllCustomRole(Map map);

    /**获取当前用户有哪些角色*/
    List<RoleVO> queryCurrUserRole(Map map);

    /**查询账户绑定了哪些ebay账户*/
    List<UsercontrollerEbayAccountExtend> queryCurrAllEbay(Map map);


    /**查询账户绑定了哪些ebay账户*/
    List<UsercontrollerEbayAccountExtend> queryACurrAllEbay(Map map);

    /**编辑页面综合查询用户信息*/
    AddSubUserVO queryAllUserAccountInfo(Long userID);

    /**添加子账户*/
    void addSubAccount(AddSubUserVO addSubUserVO);

    /**编辑子账户信息*/
    void editSubAccount(AddSubUserVO addSubUserVO);

    /**判断某个账户是否有某个角色roleID参见role表*/
    boolean pdRole(Long roleID, List<RoleVO> roleVOList);

    /**判断当前用户是否有管理员权限*/
    boolean isAdminRole();

    /**判断当前用户是否是主账号*/
    boolean isMainUserAcount();

    /**根据当前用户的orgid查询所有系统用户*/
    List<UsercontrollerUserExtend> queryAllUsersByOrgID(String isShowStop);

    /**根据userid获取用户的orgid*/
    String gerOrgIdByuserId(Integer userId);

    /**修改密码*/
    void changePWD(String oldPWD, String newPWD);

    /**发送找回密码的验证码*/
    void sendSafeCode(Map map);

    /**修改被遗忘的密码*/
    String doChangeForgetPassWord(Map map);
    /*查询指定邮件的User账号*/
    UsercontrollerUser selectUsercontrollerUserByEail(String email);

    UsercontrollerUser selectUsercontrollerUserByEail1(String email);


    /**停用整个公司的帐号和ebay帐号*/
    void stopAllUserByOrg(String orgID);
}
