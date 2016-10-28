package com.base.database.trading.model;

import java.util.Date;

public class TradingInventoryComplement {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_inventory_complement.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_inventory_complement.item_sku
     *
     * @mbggenerated
     */
    private String itemSku;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_inventory_complement.ebay_id
     *
     * @mbggenerated
     */
    private Long ebayId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_inventory_complement.ebay_account
     *
     * @mbggenerated
     */
    private String ebayAccount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_inventory_complement.create_user
     *
     * @mbggenerated
     */
    private Long createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_inventory_complement.create_date
     *
     * @mbggenerated
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_inventory_complement.create_user_name
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_inventory_complement.id
     *
     * @return the value of trading_inventory_complement.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_inventory_complement.id
     *
     * @param id the value for trading_inventory_complement.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_inventory_complement.item_sku
     *
     * @return the value of trading_inventory_complement.item_sku
     *
     * @mbggenerated
     */
    public String getItemSku() {
        return itemSku;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_inventory_complement.item_sku
     *
     * @param itemSku the value for trading_inventory_complement.item_sku
     *
     * @mbggenerated
     */
    public void setItemSku(String itemSku) {
        this.itemSku = itemSku == null ? null : itemSku.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_inventory_complement.ebay_id
     *
     * @return the value of trading_inventory_complement.ebay_id
     *
     * @mbggenerated
     */
    public Long getEbayId() {
        return ebayId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_inventory_complement.ebay_id
     *
     * @param ebayId the value for trading_inventory_complement.ebay_id
     *
     * @mbggenerated
     */
    public void setEbayId(Long ebayId) {
        this.ebayId = ebayId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_inventory_complement.ebay_account
     *
     * @return the value of trading_inventory_complement.ebay_account
     *
     * @mbggenerated
     */
    public String getEbayAccount() {
        return ebayAccount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_inventory_complement.ebay_account
     *
     * @param ebayAccount the value for trading_inventory_complement.ebay_account
     *
     * @mbggenerated
     */
    public void setEbayAccount(String ebayAccount) {
        this.ebayAccount = ebayAccount == null ? null : ebayAccount.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_inventory_complement.create_user
     *
     * @return the value of trading_inventory_complement.create_user
     *
     * @mbggenerated
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_inventory_complement.create_user
     *
     * @param createUser the value for trading_inventory_complement.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_inventory_complement.create_date
     *
     * @return the value of trading_inventory_complement.create_date
     *
     * @mbggenerated
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_inventory_complement.create_date
     *
     * @param createDate the value for trading_inventory_complement.create_date
     *
     * @mbggenerated
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_inventory_complement.create_user_name
     *
     * @return the value of trading_inventory_complement.create_user_name
     *
     * @mbggenerated
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_inventory_complement.create_user_name
     *
     * @param createUserName the value for trading_inventory_complement.create_user_name
     *
     * @mbggenerated
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }
}