package com.alibabasmt.domains.querypojos.smtproduct;

import com.alibabasmt.database.smtproduct.model.SmtProduct;

/**
 * Created by Administrtor on 2015/4/3.
 */
public class SmtProductQuery extends SmtProduct{

    private String imgSrc;

    private String unitName;

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
