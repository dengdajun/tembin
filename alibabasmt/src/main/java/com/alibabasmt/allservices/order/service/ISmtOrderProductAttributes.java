package com.alibabasmt.allservices.order.service;

import com.alibabasmt.database.smtorder.model.SmtOrderProductAttributes;

/**
 * Created by Administrtor on 2015/3/30.
 */
public interface ISmtOrderProductAttributes {
    public void saveSmtOrderProductAttributes(SmtOrderProductAttributes smtOrderProductAttributes) throws Exception;
    SmtOrderProductAttributes selectSmtOrderProductAttributesByProductId(Long productId);
}
