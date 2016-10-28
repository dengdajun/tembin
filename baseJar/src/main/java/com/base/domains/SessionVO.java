package com.base.domains;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2014/7/21.
 * 用户信息VO
 */
public class SessionVO {
    private String loginId;//用户登录账号
    private long Id;//用户自增id
    private Date loginTime;
    private String userName;//用户中文名
    private String orgName;//所在公司名
    private long orgId;//公司id
    private long parentId;//父级id
    private String telPhone;
    private List<RoleVO> roleVOList;
    private List<PermissionVO> permissions;//权限url列表
    private String status;//账户状态
    private String sessionID;//
    private String defaultDevID;//
    private String thirtPartMark;//第三方用户标志
    private String thirdPartLogin;//是否是第三方登陆


    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getThirdPartLogin() {
        return thirdPartLogin;
    }

    public void setThirdPartLogin(String thirdPartLogin) {
        this.thirdPartLogin = thirdPartLogin;
    }

    public String getThirtPartMark() {
        return thirtPartMark;
    }

    public void setThirtPartMark(String thirtPartMark) {
        this.thirtPartMark = thirtPartMark;
    }

    public String getDefaultDevID() {
        return defaultDevID;
    }

    public void setDefaultDevID(String defaultDevID) {
        this.defaultDevID = defaultDevID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public List<RoleVO> getRoleVOList() {
        return roleVOList;
    }

    public void setRoleVOList(List<RoleVO> roleVOList) {
        this.roleVOList = roleVOList;
    }

    public List<PermissionVO> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionVO> permissions) {
        this.permissions = permissions;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }
}
