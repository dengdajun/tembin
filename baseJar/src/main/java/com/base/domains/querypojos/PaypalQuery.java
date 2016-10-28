package com.base.domains.querypojos;

import com.base.database.trading.model.TradingPaypal;

/**
 * Created by cz on 2014/7/28.
 */
public class PaypalQuery extends TradingPaypal{

    private String siteName;

    private String payPalName;

    private String siteImg;

    private Boolean isMainAcount;//当前账号是否是主账号

    private Long curAcountUserId;//当前账号userId;

    public Boolean getIsMainAcount() {
        return isMainAcount;
    }

    public void setIsMainAcount(Boolean isMainAcount) {
        this.isMainAcount = isMainAcount;
    }

    public Long getCurAcountUserId() {
        return curAcountUserId;
    }

    public void setCurAcountUserId(Long curAcountUserId) {
        this.curAcountUserId = curAcountUserId;
    }

    public String getSiteImg() {
        return siteImg;
    }

    public void setSiteImg(String siteImg) {
        this.siteImg = siteImg;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getPayPalName() {
        return payPalName;
    }

    public void setPayPalName(String payPalName) {
        this.payPalName = payPalName;
    }
}
