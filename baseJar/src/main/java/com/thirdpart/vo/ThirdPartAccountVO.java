package com.thirdpart.vo;

import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.utils.common.EncryptionUtil;
import com.thirdpart.utils.ThirtPartUtil;

import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/27.
 * 第三方帐户信息
 */
public class ThirdPartAccountVO {
    private Long id;
    private String userAccount;
    private String userName;
    private String phone;
    private String email;
    private String parentID;
    private String thirdPartMark;//第三方开发者的标记

    private String orgName;
    private String orgID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getThirdPartMark() {
        return thirdPartMark;
    }

    public void setThirdPartMark(String thirdPartMark) {
        this.thirdPartMark = thirdPartMark;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }



    public UsercontrollerUser toUser(){
        UsercontrollerUser user = new UsercontrollerUser();//用户
        user.setStatus("1");
        user.setDefaultDevAccount(1L);
        user.setCreateTime(new Date());
        /**密码规则为帐号+同步年月日*/
        Map<String,String> m= ThirtPartUtil.formatDate2YMDH(new Date());
        String dt=m.get("year")+m.get("month")+m.get("day")+m.get("hour");
        //user.setUserPassword(EncryptionUtil.md5Encrypt2(this.getUserAccount() + "tembin" + dt));
        user.setUserPassword(EncryptionUtil.pwdEncrypt((this.getUserAccount() + "tembin" + dt),this.getUserAccount()));
        user.setUserName(this.getUserName());
        user.setUserLoginId(this.getUserAccount());
        user.setUserEmail(this.getEmail());
        user.setTelPhone(this.getPhone());
        user.setUserType("third");
        user.setThirdPartMark(this.getThirdPartMark());
        return user;
    }
}
