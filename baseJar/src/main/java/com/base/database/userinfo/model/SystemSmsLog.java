package com.base.database.userinfo.model;

import java.util.Date;

public class SystemSmsLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_sms_log.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_sms_log.phone
     *
     * @mbggenerated
     */
    private String phone;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_sms_log.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_sms_log.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_sms_log.task_id
     *
     * @mbggenerated
     */
    private String taskId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_sms_log.s_send_time
     *
     * @mbggenerated
     */
    private Date sSendTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_sms_log.post_status
     *
     * @mbggenerated
     */
    private String postStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_sms_log.send_status
     *
     * @mbggenerated
     */
    private String sendStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_sms_log.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column system_sms_log.sms_type
     *
     * @mbggenerated
     */
    private String smsType;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_sms_log.id
     *
     * @return the value of system_sms_log.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_sms_log.id
     *
     * @param id the value for system_sms_log.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_sms_log.phone
     *
     * @return the value of system_sms_log.phone
     *
     * @mbggenerated
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_sms_log.phone
     *
     * @param phone the value for system_sms_log.phone
     *
     * @mbggenerated
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_sms_log.content
     *
     * @return the value of system_sms_log.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_sms_log.content
     *
     * @param content the value for system_sms_log.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_sms_log.create_time
     *
     * @return the value of system_sms_log.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_sms_log.create_time
     *
     * @param createTime the value for system_sms_log.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_sms_log.task_id
     *
     * @return the value of system_sms_log.task_id
     *
     * @mbggenerated
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_sms_log.task_id
     *
     * @param taskId the value for system_sms_log.task_id
     *
     * @mbggenerated
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_sms_log.s_send_time
     *
     * @return the value of system_sms_log.s_send_time
     *
     * @mbggenerated
     */
    public Date getsSendTime() {
        return sSendTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_sms_log.s_send_time
     *
     * @param sSendTime the value for system_sms_log.s_send_time
     *
     * @mbggenerated
     */
    public void setsSendTime(Date sSendTime) {
        this.sSendTime = sSendTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_sms_log.post_status
     *
     * @return the value of system_sms_log.post_status
     *
     * @mbggenerated
     */
    public String getPostStatus() {
        return postStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_sms_log.post_status
     *
     * @param postStatus the value for system_sms_log.post_status
     *
     * @mbggenerated
     */
    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus == null ? null : postStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_sms_log.send_status
     *
     * @return the value of system_sms_log.send_status
     *
     * @mbggenerated
     */
    public String getSendStatus() {
        return sendStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_sms_log.send_status
     *
     * @param sendStatus the value for system_sms_log.send_status
     *
     * @mbggenerated
     */
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_sms_log.update_time
     *
     * @return the value of system_sms_log.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_sms_log.update_time
     *
     * @param updateTime the value for system_sms_log.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column system_sms_log.sms_type
     *
     * @return the value of system_sms_log.sms_type
     *
     * @mbggenerated
     */
    public String getSmsType() {
        return smsType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column system_sms_log.sms_type
     *
     * @param smsType the value for system_sms_log.sms_type
     *
     * @mbggenerated
     */
    public void setSmsType(String smsType) {
        this.smsType = smsType == null ? null : smsType.trim();
    }
}