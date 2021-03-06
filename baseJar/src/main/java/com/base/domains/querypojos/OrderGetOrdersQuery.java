package com.base.domains.querypojos;

import com.base.database.trading.model.TradingOrderGetOrders;

import java.util.Date;
import java.util.List;

/**
 * Created by cz on 2014/7/28.
 */
public class OrderGetOrdersQuery extends TradingOrderGetOrders{

    private Long countNum;

    private String pictrue;

    private String itemUrl;

    private String message;

    private String imgUrl;

    private String feedbackMessage;

    private String feedbackMessageType;

    private String role;

    private String feedbackMessage1;

    private String totalAmount;

    private String currency;


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFeedbackMessageType() {
        return feedbackMessageType;
    }

    public void setFeedbackMessageType(String feedbackMessageType) {
        this.feedbackMessageType = feedbackMessageType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRole1() {
        return role1;
    }

    public void setRole1(String role1) {
        this.role1 = role1;
    }

    public String getFeedbackMessage1() {
        return feedbackMessage1;
    }

    public void setFeedbackMessage1(String feedbackMessage1) {
        this.feedbackMessage1 = feedbackMessage1;
    }

    private String role1;

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private List<String> variationspecificsMap;

    public List<String> getVariationspecificsMap() {
        return variationspecificsMap;
    }

    public void setVariationspecificsMap(List<String> variationspecificsMap) {
        this.variationspecificsMap = variationspecificsMap;
    }

    private Date paypalPaidTime;

    private Date paypalPaymentTime;

    private String externalTransactionID;

    private boolean flagNotAllComplete;

    private String itemSite;

    private String siteName;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getItemSite() {
        return itemSite;
    }

    public void setItemSite(String itemSite) {
        this.itemSite = itemSite;
    }

    public boolean isFlagNotAllComplete() {
        return flagNotAllComplete;
    }

    public void setFlagNotAllComplete(boolean flagNotAllComplete) {
        this.flagNotAllComplete = flagNotAllComplete;
    }

    public String getExternalTransactionID() {
        return externalTransactionID;
    }

    public void setExternalTransactionID(String externalTransactionID) {
        this.externalTransactionID = externalTransactionID;
    }

    public Date getPaypalPaidTime() {
        return paypalPaidTime;
    }

    public void setPaypalPaidTime(Date paypalPaidTime) {
        this.paypalPaidTime = paypalPaidTime;
    }

    public Date getPaypalPaymentTime() {
        return paypalPaymentTime;
    }

    public void setPaypalPaymentTime(Date paypalPaymentTime) {
        this.paypalPaymentTime = paypalPaymentTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPictrue() {
        return pictrue;
    }

    public void setPictrue(String pictrue) {
        this.pictrue = pictrue;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public Long getCountNum() {
        return countNum;
    }

    public void setCountNum(Long countNum) {
        this.countNum = countNum;
    }

}
