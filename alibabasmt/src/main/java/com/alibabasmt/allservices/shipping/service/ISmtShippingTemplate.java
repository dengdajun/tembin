package com.alibabasmt.allservices.shipping.service;

import com.alibabasmt.database.smtshipping.model.SmtShippingTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/3/30.
 */
public interface ISmtShippingTemplate {
    List<Map> queryShippingTemplateList(long smtAccountId);

    Map queryShippingTemplateDetailMain(long smtAccountId, long templateId);

    void saveSmtShippingTemplateList(List<SmtShippingTemplate> li);

    void saveSmtShippingTemplate(SmtShippingTemplate sst);

    SmtShippingTemplate selectById(long id);

    void saveShippingTemplateTask(long smtAccount);
}
