package com.alibabasmt.allservices.order.service;

import com.alibabasmt.database.smtorder.model.SmtOrderReceiptAddress;

import java.util.Map;

/**
 * Created by Administrtor on 2015/3/25.
 */
public interface ISmtOrderReceiptAddress {
    public void saveSmtOrderReceiptAddress(SmtOrderReceiptAddress smtOrderReceiptAddress) throws Exception;
    SmtOrderReceiptAddress selectSmtOrderReceiptAddressByOrderId(String orderId);
    SmtOrderReceiptAddress paseSmtOrderBuyerInfoAndSave(Map jsons,String orderId) throws Exception;
}
