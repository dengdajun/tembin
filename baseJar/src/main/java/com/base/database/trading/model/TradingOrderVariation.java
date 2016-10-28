package com.base.database.trading.model;

import java.util.Date;

public class TradingOrderVariation {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_order_variation.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_order_variation.sKU
     *
     * @mbggenerated
     */
    private String sku;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_order_variation.startPrice
     *
     * @mbggenerated
     */
    private Double startprice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_order_variation.quantity
     *
     * @mbggenerated
     */
    private Integer quantity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_order_variation.quantitySold
     *
     * @mbggenerated
     */
    private Integer quantitysold;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_order_variation.orderItem_id
     *
     * @mbggenerated
     */
    private Long orderitemId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_order_variation.create_user
     *
     * @mbggenerated
     */
    private Long createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_order_variation.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_order_variation.uuid
     *
     * @mbggenerated
     */
    private String uuid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_order_variation.id
     *
     * @return the value of trading_order_variation.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_order_variation.id
     *
     * @param id the value for trading_order_variation.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_order_variation.sKU
     *
     * @return the value of trading_order_variation.sKU
     *
     * @mbggenerated
     */
    public String getSku() {
        return sku;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_order_variation.sKU
     *
     * @param sku the value for trading_order_variation.sKU
     *
     * @mbggenerated
     */
    public void setSku(String sku) {
        this.sku = sku == null ? null : sku.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_order_variation.startPrice
     *
     * @return the value of trading_order_variation.startPrice
     *
     * @mbggenerated
     */
    public Double getStartprice() {
        return startprice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_order_variation.startPrice
     *
     * @param startprice the value for trading_order_variation.startPrice
     *
     * @mbggenerated
     */
    public void setStartprice(Double startprice) {
        this.startprice = startprice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_order_variation.quantity
     *
     * @return the value of trading_order_variation.quantity
     *
     * @mbggenerated
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_order_variation.quantity
     *
     * @param quantity the value for trading_order_variation.quantity
     *
     * @mbggenerated
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_order_variation.quantitySold
     *
     * @return the value of trading_order_variation.quantitySold
     *
     * @mbggenerated
     */
    public Integer getQuantitysold() {
        return quantitysold;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_order_variation.quantitySold
     *
     * @param quantitysold the value for trading_order_variation.quantitySold
     *
     * @mbggenerated
     */
    public void setQuantitysold(Integer quantitysold) {
        this.quantitysold = quantitysold;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_order_variation.orderItem_id
     *
     * @return the value of trading_order_variation.orderItem_id
     *
     * @mbggenerated
     */
    public Long getOrderitemId() {
        return orderitemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_order_variation.orderItem_id
     *
     * @param orderitemId the value for trading_order_variation.orderItem_id
     *
     * @mbggenerated
     */
    public void setOrderitemId(Long orderitemId) {
        this.orderitemId = orderitemId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_order_variation.create_user
     *
     * @return the value of trading_order_variation.create_user
     *
     * @mbggenerated
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_order_variation.create_user
     *
     * @param createUser the value for trading_order_variation.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_order_variation.create_time
     *
     * @return the value of trading_order_variation.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_order_variation.create_time
     *
     * @param createTime the value for trading_order_variation.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_order_variation.uuid
     *
     * @return the value of trading_order_variation.uuid
     *
     * @mbggenerated
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_order_variation.uuid
     *
     * @param uuid the value for trading_order_variation.uuid
     *
     * @mbggenerated
     */
    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }
}