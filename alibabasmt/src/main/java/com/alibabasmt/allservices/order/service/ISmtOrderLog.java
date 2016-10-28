package com.alibabasmt.allservices.order.service;

import com.alibabasmt.database.smtorder.model.SmtOrderLog;

import java.util.Map;

/**
 * Created by Administrtor on 2015/3/27.
 */
public interface ISmtOrderLog {
    public void saveSmtOrderLog(SmtOrderLog smtOrderLog) throws Exception;
    public void parseSmtOrderLogAndSave(Map jsons) throws Exception;
    SmtOrderLog selectSmtOrderLogByLogId(Long logId);
}
