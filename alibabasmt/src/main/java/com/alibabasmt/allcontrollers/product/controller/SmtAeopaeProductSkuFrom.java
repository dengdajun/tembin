package com.alibabasmt.allcontrollers.product.controller;

import com.alibabasmt.database.smtproduct.model.SmtAeopSkuProperty;
import com.alibabasmt.database.smtproduct.model.SmtAeopaeProductSku;

import java.util.List;

/**
 * Created by Administrtor on 2015/3/24.
 */
public class SmtAeopaeProductSkuFrom extends SmtAeopaeProductSku {

    private List<SmtAeopSkuProperty> skuPropertyList;

    private String parentValue;

    public String getParentValue() {
        return parentValue;
    }

    public void setParentValue(String parentValue) {
        this.parentValue = parentValue;
    }

    public List<SmtAeopSkuProperty> getSkuPropertyList() {
        return skuPropertyList;
    }

    public void setSkuPropertyList(List<SmtAeopSkuProperty> skuPropertyList) {
        this.skuPropertyList = skuPropertyList;
    }
}
