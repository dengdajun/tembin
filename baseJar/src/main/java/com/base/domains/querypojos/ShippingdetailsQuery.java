package com.base.domains.querypojos;

import com.base.database.trading.model.TradingInternationalshippingserviceoption;
import com.base.database.trading.model.TradingShippingdetails;
import com.base.database.trading.model.TradingShippingserviceoptions;

import java.util.List;

/**
 * Created by Administrtor on 2014/8/1.
 */
public class ShippingdetailsQuery  extends TradingShippingdetails {

    private String siteName;

    private String ebayName;

    private List<TradingShippingserviceoptions> lits;

    private List<TradingInternationalshippingserviceoption> liti;

    private String siteImg;

    private String currencyId;

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

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
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

    public String getEbayName() {
        return ebayName;
    }

    public void setEbayName(String ebayName) {
        this.ebayName = ebayName;
    }

    public List<TradingShippingserviceoptions> getLits() {
        return lits;
    }

    public void setLits(List<TradingShippingserviceoptions> lits) {
        this.lits = lits;
    }

    public List<TradingInternationalshippingserviceoption> getLiti() {
        return liti;
    }

    public void setLiti(List<TradingInternationalshippingserviceoption> liti) {
        this.liti = liti;
    }
}
