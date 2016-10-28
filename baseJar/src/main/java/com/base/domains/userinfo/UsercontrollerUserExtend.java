package com.base.domains.userinfo;

import com.base.database.userinfo.model.UsercontrollerUser;

import java.util.Date;

/**
 * Created by Administrator on 2014/9/19.
 */
public class UsercontrollerUserExtend extends UsercontrollerUser {
    private String orgName;
    private String roleName;
    private Date createTime;
    private Date newLoinTime;


    public Date getNewLoinTime() {
        return newLoinTime;
    }

    public void setNewLoinTime(Date newLoinTime) {
        this.newLoinTime = newLoinTime;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
