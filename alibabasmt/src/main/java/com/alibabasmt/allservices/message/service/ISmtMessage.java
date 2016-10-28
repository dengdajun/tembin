package com.alibabasmt.allservices.message.service;

import com.alibabasmt.database.smtmessage.model.SmtMessage;

import java.util.Map;

/**
 * Created by Administrtor on 2015/4/13.
 */
public interface ISmtMessage {
    public void saveSmtMessage(SmtMessage smtMessage) throws Exception;
    public void parseSmtMessageAndSave(Map jsons,Long smtAcountId) throws Exception;
    SmtMessage selectSmtMessageByMessageId(Long messageId);
}
