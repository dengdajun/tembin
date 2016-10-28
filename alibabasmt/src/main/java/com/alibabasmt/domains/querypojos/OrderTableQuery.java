package com.alibabasmt.domains.querypojos;

import com.alibabasmt.database.smtorder.model.SmtOrderTable;

/**
 * Created by Administrtor on 2015/3/24.
 */
public class OrderTableQuery extends SmtOrderTable {
    private String pictureUrl;
    private String skuCode;
    private String price;
    private String cur;
    private String status;
    private String productName;
    private String productId;
    private String firstName;
    private String lastName;
    private String buyerEmail;
    private String logisticsNo;
    private String logisticsServiceName;
    private String sellerOperatorLoginId;
    private String productSnapUrl;

    public String getProductSnapUrl() {
        return productSnapUrl;
    }

    public void setProductSnapUrl(String productSnapUrl) {
        this.productSnapUrl = productSnapUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSellerOperatorLoginId() {
        return sellerOperatorLoginId;
    }

    public void setSellerOperatorLoginId(String sellerOperatorLoginId) {
        this.sellerOperatorLoginId = sellerOperatorLoginId;
    }

    public String getLogisticsServiceName() {
        return logisticsServiceName;
    }

    public void setLogisticsServiceName(String logisticsServiceName) {
        this.logisticsServiceName = logisticsServiceName;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCur() {
        return cur;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
