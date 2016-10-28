package com.base.domains.querypojos;


import com.base.database.trading.model.TradingPriceTracking;

/**
 * Created by cz on 2014/7/28.
 */
public class PriceTrackingQuery extends TradingPriceTracking{
    private String itemid1;
    private String itemPrice1;
    private String sku;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getItemid1() {
        return itemid1;
    }

    public void setItemid1(String itemid1) {
        this.itemid1 = itemid1;
    }

    public String getItemPrice1() {
        return itemPrice1;
    }

    public void setItemPrice1(String itemPrice1) {
        this.itemPrice1 = itemPrice1;
    }
}
