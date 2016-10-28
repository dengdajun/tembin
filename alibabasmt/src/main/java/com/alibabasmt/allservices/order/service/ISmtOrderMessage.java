package com.alibabasmt.allservices.order.service;

import com.alibabasmt.database.smtorder.model.SmtOrderMessage;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/3/26.
 */
public interface ISmtOrderMessage {
    public void saveSmtOrderMessage(SmtOrderMessage smtOrderMessage) throws Exception;
    List<SmtOrderMessage> selectSmtOrderMessageByOrderId(String orderId);
    SmtOrderMessage selectSmtOrderMessageById(Long id);
    SmtOrderMessage selectSmtOrderMessageByOrderIdAndGmtCreate(String orderId,Date gmtCreate);
    public void parseSmtOrderMessageAndSave(Map jsons) throws Exception;
}
