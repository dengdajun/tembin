package com.base.domains.userinfo;

import com.base.database.trading.model.UsercontrollerEbayAccount;

import java.util.Date;

/**
 * Created by Administrtor on 2014/8/8.
 */
public class UsercontrollerEbayAccountExtend extends UsercontrollerEbayAccount {
    private String paypalName;
    private String feedBackNum;
    private String perFeedBack;


    public String getPaypalName() {
        return paypalName;
    }

    public void setPaypalName(String paypalName) {
        this.paypalName = paypalName;
    }

    public String getFeedBackNum() {
        return feedBackNum;
    }

    public void setFeedBackNum(String feedBackNum) {
        this.feedBackNum = feedBackNum;
    }

    public String getPerFeedBack() {
        return perFeedBack;
    }

    public void setPerFeedBack(String perFeedBack) {
        this.perFeedBack = perFeedBack;
    }
}
