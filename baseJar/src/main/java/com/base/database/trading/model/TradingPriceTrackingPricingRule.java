package com.base.database.trading.model;

import java.util.Date;

public class TradingPriceTrackingPricingRule {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_pricing_rule.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_pricing_rule.ruleType
     *
     * @mbggenerated
     */
    private String ruletype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_pricing_rule.competitorItemid
     *
     * @mbggenerated
     */
    private String competitoritemid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_pricing_rule.increaseOrDecrease
     *
     * @mbggenerated
     */
    private String increaseordecrease;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_pricing_rule.inputValue
     *
     * @mbggenerated
     */
    private String inputvalue;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_pricing_rule.sign
     *
     * @mbggenerated
     */
    private String sign;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_pricing_rule.autoPricing_id
     *
     * @mbggenerated
     */
    private Long autopricingId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_pricing_rule.priceTracking_id
     *
     * @mbggenerated
     */
    private Long pricetrackingId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_pricing_rule.create_user
     *
     * @mbggenerated
     */
    private Long createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_pricing_rule.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_price_tracking_pricing_rule.uuid
     *
     * @mbggenerated
     */
    private String uuid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_pricing_rule.id
     *
     * @return the value of trading_price_tracking_pricing_rule.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_pricing_rule.id
     *
     * @param id the value for trading_price_tracking_pricing_rule.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_pricing_rule.ruleType
     *
     * @return the value of trading_price_tracking_pricing_rule.ruleType
     *
     * @mbggenerated
     */
    public String getRuletype() {
        return ruletype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_pricing_rule.ruleType
     *
     * @param ruletype the value for trading_price_tracking_pricing_rule.ruleType
     *
     * @mbggenerated
     */
    public void setRuletype(String ruletype) {
        this.ruletype = ruletype == null ? null : ruletype.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_pricing_rule.competitorItemid
     *
     * @return the value of trading_price_tracking_pricing_rule.competitorItemid
     *
     * @mbggenerated
     */
    public String getCompetitoritemid() {
        return competitoritemid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_pricing_rule.competitorItemid
     *
     * @param competitoritemid the value for trading_price_tracking_pricing_rule.competitorItemid
     *
     * @mbggenerated
     */
    public void setCompetitoritemid(String competitoritemid) {
        this.competitoritemid = competitoritemid == null ? null : competitoritemid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_pricing_rule.increaseOrDecrease
     *
     * @return the value of trading_price_tracking_pricing_rule.increaseOrDecrease
     *
     * @mbggenerated
     */
    public String getIncreaseordecrease() {
        return increaseordecrease;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_pricing_rule.increaseOrDecrease
     *
     * @param increaseordecrease the value for trading_price_tracking_pricing_rule.increaseOrDecrease
     *
     * @mbggenerated
     */
    public void setIncreaseordecrease(String increaseordecrease) {
        this.increaseordecrease = increaseordecrease == null ? null : increaseordecrease.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_pricing_rule.inputValue
     *
     * @return the value of trading_price_tracking_pricing_rule.inputValue
     *
     * @mbggenerated
     */
    public String getInputvalue() {
        return inputvalue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_pricing_rule.inputValue
     *
     * @param inputvalue the value for trading_price_tracking_pricing_rule.inputValue
     *
     * @mbggenerated
     */
    public void setInputvalue(String inputvalue) {
        this.inputvalue = inputvalue == null ? null : inputvalue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_pricing_rule.sign
     *
     * @return the value of trading_price_tracking_pricing_rule.sign
     *
     * @mbggenerated
     */
    public String getSign() {
        return sign;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_pricing_rule.sign
     *
     * @param sign the value for trading_price_tracking_pricing_rule.sign
     *
     * @mbggenerated
     */
    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_pricing_rule.autoPricing_id
     *
     * @return the value of trading_price_tracking_pricing_rule.autoPricing_id
     *
     * @mbggenerated
     */
    public Long getAutopricingId() {
        return autopricingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_pricing_rule.autoPricing_id
     *
     * @param autopricingId the value for trading_price_tracking_pricing_rule.autoPricing_id
     *
     * @mbggenerated
     */
    public void setAutopricingId(Long autopricingId) {
        this.autopricingId = autopricingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_pricing_rule.priceTracking_id
     *
     * @return the value of trading_price_tracking_pricing_rule.priceTracking_id
     *
     * @mbggenerated
     */
    public Long getPricetrackingId() {
        return pricetrackingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_pricing_rule.priceTracking_id
     *
     * @param pricetrackingId the value for trading_price_tracking_pricing_rule.priceTracking_id
     *
     * @mbggenerated
     */
    public void setPricetrackingId(Long pricetrackingId) {
        this.pricetrackingId = pricetrackingId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_pricing_rule.create_user
     *
     * @return the value of trading_price_tracking_pricing_rule.create_user
     *
     * @mbggenerated
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_pricing_rule.create_user
     *
     * @param createUser the value for trading_price_tracking_pricing_rule.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_pricing_rule.create_time
     *
     * @return the value of trading_price_tracking_pricing_rule.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_pricing_rule.create_time
     *
     * @param createTime the value for trading_price_tracking_pricing_rule.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_price_tracking_pricing_rule.uuid
     *
     * @return the value of trading_price_tracking_pricing_rule.uuid
     *
     * @mbggenerated
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_price_tracking_pricing_rule.uuid
     *
     * @param uuid the value for trading_price_tracking_pricing_rule.uuid
     *
     * @mbggenerated
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }
}