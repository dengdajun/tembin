package com.base.database.trading.model;

public class TradingOrderRefundSuccess {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_order_refund_success.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_order_refund_success.orderID
     *
     * @mbggenerated
     */
    private String orderid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_order_refund_success.refundDesc
     *
     * @mbggenerated
     */
    private String refunddesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column trading_order_refund_success.create_user
     *
     * @mbggenerated
     */
    private Long createUser;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_order_refund_success.id
     *
     * @return the value of trading_order_refund_success.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_order_refund_success.id
     *
     * @param id the value for trading_order_refund_success.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_order_refund_success.orderID
     *
     * @return the value of trading_order_refund_success.orderID
     *
     * @mbggenerated
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_order_refund_success.orderID
     *
     * @param orderid the value for trading_order_refund_success.orderID
     *
     * @mbggenerated
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_order_refund_success.refundDesc
     *
     * @return the value of trading_order_refund_success.refundDesc
     *
     * @mbggenerated
     */
    public String getRefunddesc() {
        return refunddesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_order_refund_success.refundDesc
     *
     * @param refunddesc the value for trading_order_refund_success.refundDesc
     *
     * @mbggenerated
     */
    public void setRefunddesc(String refunddesc) {
        this.refunddesc = refunddesc == null ? null : refunddesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column trading_order_refund_success.create_user
     *
     * @return the value of trading_order_refund_success.create_user
     *
     * @mbggenerated
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column trading_order_refund_success.create_user
     *
     * @param createUser the value for trading_order_refund_success.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
}