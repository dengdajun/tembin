package com.base.database.trading.model;

import java.util.Date;

public class TradingVariations {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_variations.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_variations.parent_id
     *
     * @mbggenerated
     */
    private Long parentId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_variations.parent_uuid
     *
     * @mbggenerated
     */
    private String parentUuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_variations.uuid
     *
     * @mbggenerated
     */
    private String uuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_variations.create_user
     *
     * @mbggenerated
     */
    private Long createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_variations.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_variations.id
     *
     * @return the value of trading_variations.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_variations.id
     *
     * @param id the value for trading_variations.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_variations.parent_id
     *
     * @return the value of trading_variations.parent_id
     *
     * @mbggenerated
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_variations.parent_id
     *
     * @param parentId the value for trading_variations.parent_id
     *
     * @mbggenerated
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_variations.parent_uuid
     *
     * @return the value of trading_variations.parent_uuid
     *
     * @mbggenerated
     */
    public String getParentUuid() {
        return parentUuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_variations.parent_uuid
     *
     * @param parentUuid the value for trading_variations.parent_uuid
     *
     * @mbggenerated
     */
    public void setParentUuid(String parentUuid) {
        this.parentUuid = parentUuid == null ? null : parentUuid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_variations.uuid
     *
     * @return the value of trading_variations.uuid
     *
     * @mbggenerated
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_variations.uuid
     *
     * @param uuid the value for trading_variations.uuid
     *
     * @mbggenerated
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_variations.create_user
     *
     * @return the value of trading_variations.create_user
     *
     * @mbggenerated
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_variations.create_user
     *
     * @param createUser the value for trading_variations.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_variations.create_time
     *
     * @return the value of trading_variations.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_variations.create_time
     *
     * @param createTime the value for trading_variations.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}