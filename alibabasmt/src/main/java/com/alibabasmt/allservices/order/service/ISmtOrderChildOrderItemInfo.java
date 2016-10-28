package com.alibabasmt.allservices.order.service;

import com.alibabasmt.database.smtorder.model.SmtOrderChildOrderItemInfo;

import java.util.Map;

/**
 * Created by Administrtor on 2015/3/26.
 */
public interface ISmtOrderChildOrderItemInfo {
    public void saveSmtOrderChildOrderItemInfo(SmtOrderChildOrderItemInfo smtOrderChildOrderItemInfo) throws Exception;
    SmtOrderChildOrderItemInfo selectSmtOrderChildOrderItemInfoByOrderId(Long productId);
    public void parseSmtOrderChildOrderItemInfoAndSave(Map jsons,String orderId) throws Exception;
}
