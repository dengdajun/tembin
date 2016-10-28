package com.alibabasmt.allservices.product.service;

import com.alibabasmt.database.smtproduct.model.SmtAeopaeProductSku;

import java.util.List;

/**
 * Created by Administrtor on 2015/4/18.
 */
public interface ISmtAeopaeProductSku {
    List<SmtAeopaeProductSku> selectByParentId(long parentId);
}
