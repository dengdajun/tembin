package com.alibabasmt.allservices.shipping.service;

import com.alibabasmt.database.smtshipping.model.SmtShippingSelfdefine;
import com.alibabasmt.database.smtshipping.model.SmtShippingSelfstandard;
import com.alibabasmt.database.smtshipping.model.SmtShippingTemplateDetailWithBLOBs;

/**
 * Created by Administrtor on 2015/3/30.
 */
public interface ISmtShippingTemplateDetail {
    void saveShippingTemplateDetail(SmtShippingTemplateDetailWithBLOBs smtShippingTemplateDetailWithBLOBs);

    void taskShippingTemplate();

    void saveSmtShippingSelfdefine(SmtShippingSelfdefine smtShippingSelfdefine);

    void saveSmtShippingSelfstandard(SmtShippingSelfstandard smtShippingSelfstandard);
}
