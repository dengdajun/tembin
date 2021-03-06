package com.alibabasmt.database.smtaccount.model;

import java.util.Date;

public class SmtUserAccount {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_user_account.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_user_account.smt_account_name
     *
     * @mbggenerated
     */
    private String smtAccountName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_user_account.smt_account
     *
     * @mbggenerated
     */
    private String smtAccount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_user_account.smt_freshtoken
     *
     * @mbggenerated
     */
    private String smtFreshtoken;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_user_account.org_id
     *
     * @mbggenerated
     */
    private Integer orgId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_user_account.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_user_account.create_user
     *
     * @mbggenerated
     */
    private Integer createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_user_account.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_user_account.smt_account_shortname
     *
     * @mbggenerated
     */
    private String smtAccountShortname;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_user_account.id
     *
     * @return the value of smt_user_account.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_user_account.id
     *
     * @param id the value for smt_user_account.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_user_account.smt_account_name
     *
     * @return the value of smt_user_account.smt_account_name
     *
     * @mbggenerated
     */
    public String getSmtAccountName() {
        return smtAccountName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_user_account.smt_account_name
     *
     * @param smtAccountName the value for smt_user_account.smt_account_name
     *
     * @mbggenerated
     */
    public void setSmtAccountName(String smtAccountName) {
        this.smtAccountName = smtAccountName == null ? null : smtAccountName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_user_account.smt_account
     *
     * @return the value of smt_user_account.smt_account
     *
     * @mbggenerated
     */
    public String getSmtAccount() {
        return smtAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_user_account.smt_account
     *
     * @param smtAccount the value for smt_user_account.smt_account
     *
     * @mbggenerated
     */
    public void setSmtAccount(String smtAccount) {
        this.smtAccount = smtAccount == null ? null : smtAccount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_user_account.smt_freshtoken
     *
     * @return the value of smt_user_account.smt_freshtoken
     *
     * @mbggenerated
     */
    public String getSmtFreshtoken() {
        return smtFreshtoken;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_user_account.smt_freshtoken
     *
     * @param smtFreshtoken the value for smt_user_account.smt_freshtoken
     *
     * @mbggenerated
     */
    public void setSmtFreshtoken(String smtFreshtoken) {
        this.smtFreshtoken = smtFreshtoken == null ? null : smtFreshtoken.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_user_account.org_id
     *
     * @return the value of smt_user_account.org_id
     *
     * @mbggenerated
     */
    public Integer getOrgId() {
        return orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_user_account.org_id
     *
     * @param orgId the value for smt_user_account.org_id
     *
     * @mbggenerated
     */
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_user_account.create_time
     *
     * @return the value of smt_user_account.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_user_account.create_time
     *
     * @param createTime the value for smt_user_account.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_user_account.create_user
     *
     * @return the value of smt_user_account.create_user
     *
     * @mbggenerated
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_user_account.create_user
     *
     * @param createUser the value for smt_user_account.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_user_account.update_time
     *
     * @return the value of smt_user_account.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_user_account.update_time
     *
     * @param updateTime the value for smt_user_account.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_user_account.smt_account_shortname
     *
     * @return the value of smt_user_account.smt_account_shortname
     *
     * @mbggenerated
     */
    public String getSmtAccountShortname() {
        return smtAccountShortname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_user_account.smt_account_shortname
     *
     * @param smtAccountShortname the value for smt_user_account.smt_account_shortname
     *
     * @mbggenerated
     */
    public void setSmtAccountShortname(String smtAccountShortname) {
        this.smtAccountShortname = smtAccountShortname == null ? null : smtAccountShortname.trim();
    }
}