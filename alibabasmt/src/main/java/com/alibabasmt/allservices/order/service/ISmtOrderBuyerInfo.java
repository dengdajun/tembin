package com.alibabasmt.allservices.order.service;

import com.alibabasmt.database.smtorder.model.SmtOrderBuyerInfo;

import java.util.Map;

/**
 * Created by Administrtor on 2015/3/25.
 */
public interface ISmtOrderBuyerInfo {
    public void saveSmtOrderBuyerInfo(SmtOrderBuyerInfo smtOrderBuyerInfo) throws Exception;
    SmtOrderBuyerInfo selectSmtOrderBuyerInfoByLoginId(String loginId);
    SmtOrderBuyerInfo paseSmtOrderBuyerInfoAndSave(Map jsons) throws Exception;
}
