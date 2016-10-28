package com.alibabasmt.allservices.order.service;

import com.alibabasmt.database.smtorder.model.SmtOrderItemSku;

/**
 * Created by Administrtor on 2015/3/26.
 */
public interface ISmtOrderItemSku {
    public void saveSmtOrderItemSku(SmtOrderItemSku smtOrderItemSku) throws Exception;
    SmtOrderItemSku selectSmtOrderItemSkuByProductId(Long productId);
}
