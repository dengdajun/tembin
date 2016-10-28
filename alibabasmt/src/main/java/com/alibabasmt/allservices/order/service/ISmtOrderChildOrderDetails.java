package com.alibabasmt.allservices.order.service;

import com.alibabasmt.database.smtorder.model.SmtOrderChildOrderDetails;

import java.util.Map;

/**
 * Created by Administrtor on 2015/3/30.
 */
public interface ISmtOrderChildOrderDetails {
    public void saveSmtOrderChildOrderDetails(SmtOrderChildOrderDetails smtOrderChildOrderDetails) throws Exception;
    SmtOrderChildOrderDetails selectSmtOrderChildOrderDetailsByChildOrderId(String childId);
    public void parseSmtOrderChildOrderDetailsAndSave(Map jsons) throws Exception;

}
