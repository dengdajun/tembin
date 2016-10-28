package com.base.database.task.model;

import java.util.Date;

public class TaskFeedBack {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_feed_back.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_feed_back.userid
     *
     * @mbggenerated
     */
    private Long userid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_feed_back.ebayName
     *
     * @mbggenerated
     */
    private String ebayname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_feed_back.ebay_id
     *
     * @mbggenerated
     */
    private Long ebayId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_feed_back.commentType
     *
     * @mbggenerated
     */
    private String commenttype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_feed_back.tokenFlag
     *
     * @mbggenerated
     */
    private Integer tokenflag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_feed_back.savetime
     *
     * @mbggenerated
     */
    private Date savetime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column task_feed_back.token
     *
     * @mbggenerated
     */
    private String token;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_feed_back.id
     *
     * @return the value of task_feed_back.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_feed_back.id
     *
     * @param id the value for task_feed_back.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_feed_back.userid
     *
     * @return the value of task_feed_back.userid
     *
     * @mbggenerated
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_feed_back.userid
     *
     * @param userid the value for task_feed_back.userid
     *
     * @mbggenerated
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_feed_back.ebayName
     *
     * @return the value of task_feed_back.ebayName
     *
     * @mbggenerated
     */
    public String getEbayname() {
        return ebayname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_feed_back.ebayName
     *
     * @param ebayname the value for task_feed_back.ebayName
     *
     * @mbggenerated
     */
    public void setEbayname(String ebayname) {
        this.ebayname = ebayname == null ? null : ebayname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_feed_back.ebay_id
     *
     * @return the value of task_feed_back.ebay_id
     *
     * @mbggenerated
     */
    public Long getEbayId() {
        return ebayId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_feed_back.ebay_id
     *
     * @param ebayId the value for task_feed_back.ebay_id
     *
     * @mbggenerated
     */
    public void setEbayId(Long ebayId) {
        this.ebayId = ebayId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_feed_back.commentType
     *
     * @return the value of task_feed_back.commentType
     *
     * @mbggenerated
     */
    public String getCommenttype() {
        return commenttype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_feed_back.commentType
     *
     * @param commenttype the value for task_feed_back.commentType
     *
     * @mbggenerated
     */
    public void setCommenttype(String commenttype) {
        this.commenttype = commenttype == null ? null : commenttype.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_feed_back.tokenFlag
     *
     * @return the value of task_feed_back.tokenFlag
     *
     * @mbggenerated
     */
    public Integer getTokenflag() {
        return tokenflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_feed_back.tokenFlag
     *
     * @param tokenflag the value for task_feed_back.tokenFlag
     *
     * @mbggenerated
     */
    public void setTokenflag(Integer tokenflag) {
        this.tokenflag = tokenflag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_feed_back.savetime
     *
     * @return the value of task_feed_back.savetime
     *
     * @mbggenerated
     */
    public Date getSavetime() {
        return savetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_feed_back.savetime
     *
     * @param savetime the value for task_feed_back.savetime
     *
     * @mbggenerated
     */
    public void setSavetime(Date savetime) {
        this.savetime = savetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column task_feed_back.token
     *
     * @return the value of task_feed_back.token
     *
     * @mbggenerated
     */
    public String getToken() {
        return token;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column task_feed_back.token
     *
     * @param token the value for task_feed_back.token
     *
     * @mbggenerated
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }
}