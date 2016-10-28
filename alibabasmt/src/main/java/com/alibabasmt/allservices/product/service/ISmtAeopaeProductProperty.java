package com.alibabasmt.allservices.product.service;

import com.alibabasmt.database.smtproduct.model.SmtAeopaeProductProperty;

import java.util.List;

/**
 * Created by Administrtor on 2015/4/13.
 */
public interface ISmtAeopaeProductProperty {
    List<SmtAeopaeProductProperty> selectByParentId(long parentId);
}
