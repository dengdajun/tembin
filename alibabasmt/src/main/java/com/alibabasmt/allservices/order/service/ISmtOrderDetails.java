package com.alibabasmt.allservices.order.service;

import com.alibabasmt.database.smtorder.model.SmtOrderDetails;

/**
 * Created by Administrtor on 2015/3/24.
 */
public interface ISmtOrderDetails {
    public void saveSmtOrderDetails(SmtOrderDetails smtOrderDetails) throws Exception;
    void paseSmtOrderDetailsAndSave(String orderId,Long smtAccountId)throws Exception;
    SmtOrderDetails selectSmtOrderDetailsByOrderId(String orderId) ;
}
