package com.base.domains.querypojos;

import com.base.database.trading.model.TradingDiscountpriceinfo;

/**
 * Created by cz on 2014/7/29.
 */

public class DiscountpriceinfoQuery extends TradingDiscountpriceinfo {
    private String ebayName;

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

    public String getEbayName() {
        return ebayName;
    }

    public void setEbayName(String ebayName) {
        this.ebayName = ebayName;
    }
}
