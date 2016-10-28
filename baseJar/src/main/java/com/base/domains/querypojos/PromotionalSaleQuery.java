package com.base.domains.querypojos;

import com.base.database.trading.model.TradingItemPromotionalSaleRule;

/**
 * Created by Administrtor on 2015/6/18.
 */
public class PromotionalSaleQuery extends TradingItemPromotionalSaleRule {
    public String ebayAccountName;

    public String siteurl;

    public String getEbayAccountName() {
        return ebayAccountName;
    }

    public void setEbayAccountName(String ebayAccountName) {
        this.ebayAccountName = ebayAccountName;
    }

    public String getSiteurl() {
        return siteurl;
    }

    public void setSiteurl(String siteurl) {
        this.siteurl = siteurl;
    }
}
