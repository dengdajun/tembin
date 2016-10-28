package com.alibabasmt.database.smtorder.model;

import java.util.Date;

public class SmtOrderChildOrderDetails {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.childId
     *
     * @mbggenerated
     */
    private String childid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.productId
     *
     * @mbggenerated
     */
    private Long productid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.fundStatus
     *
     * @mbggenerated
     */
    private String fundstatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.orderStatus
     *
     * @mbggenerated
     */
    private String orderstatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.initOrderAmt
     *
     * @mbggenerated
     */
    private String initorderamt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.initOrderAmtCurrency
     *
     * @mbggenerated
     */
    private String initorderamtcurrency;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.productUnit
     *
     * @mbggenerated
     */
    private String productunit;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.productCount
     *
     * @mbggenerated
     */
    private Integer productcount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.lotNum
     *
     * @mbggenerated
     */
    private Integer lotnum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.frozenStatus
     *
     * @mbggenerated
     */
    private String frozenstatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.skuCode
     *
     * @mbggenerated
     */
    private String skucode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.issueStatus
     *
     * @mbggenerated
     */
    private String issuestatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.productName
     *
     * @mbggenerated
     */
    private String productname;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.productPrice
     *
     * @mbggenerated
     */
    private String productprice;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.productPriceCurrency
     *
     * @mbggenerated
     */
    private String productpricecurrency;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.create_user
     *
     * @mbggenerated
     */
    private Long createUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column smt_order_child_order_details.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.id
     *
     * @return the value of smt_order_child_order_details.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.id
     *
     * @param id the value for smt_order_child_order_details.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.childId
     *
     * @return the value of smt_order_child_order_details.childId
     *
     * @mbggenerated
     */
    public String getChildid() {
        return childid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.childId
     *
     * @param childid the value for smt_order_child_order_details.childId
     *
     * @mbggenerated
     */
    public void setChildid(String childid) {
        this.childid = childid == null ? null : childid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.productId
     *
     * @return the value of smt_order_child_order_details.productId
     *
     * @mbggenerated
     */
    public Long getProductid() {
        return productid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.productId
     *
     * @param productid the value for smt_order_child_order_details.productId
     *
     * @mbggenerated
     */
    public void setProductid(Long productid) {
        this.productid = productid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.fundStatus
     *
     * @return the value of smt_order_child_order_details.fundStatus
     *
     * @mbggenerated
     */
    public String getFundstatus() {
        return fundstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.fundStatus
     *
     * @param fundstatus the value for smt_order_child_order_details.fundStatus
     *
     * @mbggenerated
     */
    public void setFundstatus(String fundstatus) {
        this.fundstatus = fundstatus == null ? null : fundstatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.orderStatus
     *
     * @return the value of smt_order_child_order_details.orderStatus
     *
     * @mbggenerated
     */
    public String getOrderstatus() {
        return orderstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.orderStatus
     *
     * @param orderstatus the value for smt_order_child_order_details.orderStatus
     *
     * @mbggenerated
     */
    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus == null ? null : orderstatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.initOrderAmt
     *
     * @return the value of smt_order_child_order_details.initOrderAmt
     *
     * @mbggenerated
     */
    public String getInitorderamt() {
        return initorderamt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.initOrderAmt
     *
     * @param initorderamt the value for smt_order_child_order_details.initOrderAmt
     *
     * @mbggenerated
     */
    public void setInitorderamt(String initorderamt) {
        this.initorderamt = initorderamt == null ? null : initorderamt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.initOrderAmtCurrency
     *
     * @return the value of smt_order_child_order_details.initOrderAmtCurrency
     *
     * @mbggenerated
     */
    public String getInitorderamtcurrency() {
        return initorderamtcurrency;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.initOrderAmtCurrency
     *
     * @param initorderamtcurrency the value for smt_order_child_order_details.initOrderAmtCurrency
     *
     * @mbggenerated
     */
    public void setInitorderamtcurrency(String initorderamtcurrency) {
        this.initorderamtcurrency = initorderamtcurrency == null ? null : initorderamtcurrency.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.productUnit
     *
     * @return the value of smt_order_child_order_details.productUnit
     *
     * @mbggenerated
     */
    public String getProductunit() {
        return productunit;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.productUnit
     *
     * @param productunit the value for smt_order_child_order_details.productUnit
     *
     * @mbggenerated
     */
    public void setProductunit(String productunit) {
        this.productunit = productunit == null ? null : productunit.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.productCount
     *
     * @return the value of smt_order_child_order_details.productCount
     *
     * @mbggenerated
     */
    public Integer getProductcount() {
        return productcount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.productCount
     *
     * @param productcount the value for smt_order_child_order_details.productCount
     *
     * @mbggenerated
     */
    public void setProductcount(Integer productcount) {
        this.productcount = productcount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.lotNum
     *
     * @return the value of smt_order_child_order_details.lotNum
     *
     * @mbggenerated
     */
    public Integer getLotnum() {
        return lotnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.lotNum
     *
     * @param lotnum the value for smt_order_child_order_details.lotNum
     *
     * @mbggenerated
     */
    public void setLotnum(Integer lotnum) {
        this.lotnum = lotnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.frozenStatus
     *
     * @return the value of smt_order_child_order_details.frozenStatus
     *
     * @mbggenerated
     */
    public String getFrozenstatus() {
        return frozenstatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.frozenStatus
     *
     * @param frozenstatus the value for smt_order_child_order_details.frozenStatus
     *
     * @mbggenerated
     */
    public void setFrozenstatus(String frozenstatus) {
        this.frozenstatus = frozenstatus == null ? null : frozenstatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.skuCode
     *
     * @return the value of smt_order_child_order_details.skuCode
     *
     * @mbggenerated
     */
    public String getSkucode() {
        return skucode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.skuCode
     *
     * @param skucode the value for smt_order_child_order_details.skuCode
     *
     * @mbggenerated
     */
    public void setSkucode(String skucode) {
        this.skucode = skucode == null ? null : skucode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.issueStatus
     *
     * @return the value of smt_order_child_order_details.issueStatus
     *
     * @mbggenerated
     */
    public String getIssuestatus() {
        return issuestatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.issueStatus
     *
     * @param issuestatus the value for smt_order_child_order_details.issueStatus
     *
     * @mbggenerated
     */
    public void setIssuestatus(String issuestatus) {
        this.issuestatus = issuestatus == null ? null : issuestatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.productName
     *
     * @return the value of smt_order_child_order_details.productName
     *
     * @mbggenerated
     */
    public String getProductname() {
        return productname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.productName
     *
     * @param productname the value for smt_order_child_order_details.productName
     *
     * @mbggenerated
     */
    public void setProductname(String productname) {
        this.productname = productname == null ? null : productname.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.productPrice
     *
     * @return the value of smt_order_child_order_details.productPrice
     *
     * @mbggenerated
     */
    public String getProductprice() {
        return productprice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.productPrice
     *
     * @param productprice the value for smt_order_child_order_details.productPrice
     *
     * @mbggenerated
     */
    public void setProductprice(String productprice) {
        this.productprice = productprice == null ? null : productprice.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.productPriceCurrency
     *
     * @return the value of smt_order_child_order_details.productPriceCurrency
     *
     * @mbggenerated
     */
    public String getProductpricecurrency() {
        return productpricecurrency;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.productPriceCurrency
     *
     * @param productpricecurrency the value for smt_order_child_order_details.productPriceCurrency
     *
     * @mbggenerated
     */
    public void setProductpricecurrency(String productpricecurrency) {
        this.productpricecurrency = productpricecurrency == null ? null : productpricecurrency.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.create_user
     *
     * @return the value of smt_order_child_order_details.create_user
     *
     * @mbggenerated
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.create_user
     *
     * @param createUser the value for smt_order_child_order_details.create_user
     *
     * @mbggenerated
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.create_time
     *
     * @return the value of smt_order_child_order_details.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.create_time
     *
     * @param createTime the value for smt_order_child_order_details.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column smt_order_child_order_details.update_time
     *
     * @return the value of smt_order_child_order_details.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column smt_order_child_order_details.update_time
     *
     * @param updateTime the value for smt_order_child_order_details.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}