package com.base.xmlpojo.trading.addproduct;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 店面
 * Created by Administrtor on 2014/7/17.
 */
@XStreamAlias("Storefront")
public class Storefront {
    @XStreamAlias("StoreCategory2ID")
    private Long storeCategory2ID;
    @XStreamAlias("StoreCategory2Name")
    private String storeCategory2Name;
    @XStreamAlias("StoreCategoryID")
    private Long storeCategoryID;
    @XStreamAlias("StoreCategoryName")
    private String storeCategoryName;

    public Long getStoreCategory2ID() {
        return storeCategory2ID;
    }

    public void setStoreCategory2ID(Long storeCategory2ID) {
        this.storeCategory2ID = storeCategory2ID;
    }

    public String getStoreCategory2Name() {
        return storeCategory2Name;
    }

    public void setStoreCategory2Name(String storeCategory2Name) {
        this.storeCategory2Name = storeCategory2Name;
    }

    public Long getStoreCategoryID() {
        return storeCategoryID;
    }

    public void setStoreCategoryID(Long storeCategoryID) {
        this.storeCategoryID = storeCategoryID;
    }

    public String getStoreCategoryName() {
        return storeCategoryName;
    }

    public void setStoreCategoryName(String storeCategoryName) {
        this.storeCategoryName = storeCategoryName;
    }
}
