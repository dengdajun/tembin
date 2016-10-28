package com.base.database.trading.model;

import java.util.Date;

public class TradingTablePrice {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_table_price.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_table_price.ebay_account
     *
     * @mbggenerated
     */
    private String ebayAccount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_table_price.sku
     *
     * @mbggenerated
     */
    private String sku;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_table_price.price
     *
     * @mbggenerated
     */
    private Double price;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_table_price.check_flag
     *
     * @mbggenerated
     */
    private String checkFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_table_price.create_user
     *
     * @mbggenerated
     */
    private Long createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_table_price.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_table_price.id
     *
     * @return the value of trading_table_price.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_table_price.id
     *
     * @param id the value for trading_table_price.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_table_price.ebay_account
     *
     * @return the value of trading_table_price.ebay_account
     *
     * @mbggenerated
     */
    public String getEbayAccount() {
        return ebayAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_table_price.ebay_account
     *
     * @param ebayAccount the value for trading_table_price.ebay_account
     *
     * @mbggenerated
     */
    public void setEbayAccount(String ebayAccount) {
        this.ebayAccount = ebayAccount == null ? null : ebayAccount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_table_price.sku
     *
     * @return the value of trading_table_price.sku
     *
     * @mbggenerated
     */
    public String getSku() {
        return sku;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_table_price.sku
     *
     * @param sku the value for trading_table_price.sku
     *
     * @mbggenerated
     */
    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_table_price.price
     *
     * @return the value of trading_table_price.price
     *
     * @mbggenerated
     */
    public Double getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_table_price.price
     *
     * @param price the value for trading_table_price.price
     *
     * @mbggenerated
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_table_price.check_flag
     *
     * @return the value of trading_table_price.check_flag
     *
     * @mbggenerated
     */
    public String getCheckFlag() {
        return checkFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_table_price.check_flag
     *
     * @param checkFlag the value for trading_table_price.check_flag
     *
     * @mbggenerated
     */
    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag == null ? null : checkFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_table_price.create_user
     *
     * @return the value of trading_table_price.create_user
     *
     * @mbggenerated
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_table_price.create_user
     *
     * @param createUser the value for trading_table_price.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_table_price.create_time
     *
     * @return the value of trading_table_price.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_table_price.create_time
     *
     * @param createTime the value for trading_table_price.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}