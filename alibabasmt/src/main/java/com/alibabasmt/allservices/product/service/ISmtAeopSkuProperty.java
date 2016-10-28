package com.alibabasmt.allservices.product.service;

import com.alibabasmt.database.smtproduct.model.SmtAeopSkuProperty;

import java.util.List;

/**
 * Created by Administrtor on 2015/4/21.
 */
public interface ISmtAeopSkuProperty {
    List<SmtAeopSkuProperty> selectByParentId(long parentid);
}
