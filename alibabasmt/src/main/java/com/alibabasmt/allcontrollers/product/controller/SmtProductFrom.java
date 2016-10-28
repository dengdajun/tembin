package com.alibabasmt.allcontrollers.product.controller;

import com.alibabasmt.database.smtproduct.model.SmtAeopaeProductProperty;
import com.alibabasmt.database.smtproduct.model.SmtAeopaeProductSku;
import com.alibabasmt.database.smtproduct.model.SmtProduct;

import java.util.List;

/**
 * Created by Administrtor on 2015/3/24.
 */
public class SmtProductFrom extends SmtProduct {

    public List<SmtAeopaeProductProperty> smtAeopaeProductProperties;

    private List<SmtAeopaeProductSkuFrom> smtAeopaeProductSkuFroms;

    public List<SmtAeopaeProductProperty> getSmtAeopaeProductProperties() {
        return smtAeopaeProductProperties;
    }

    public void setSmtAeopaeProductProperties(List<SmtAeopaeProductProperty> smtAeopaeProductProperties) {
        this.smtAeopaeProductProperties = smtAeopaeProductProperties;
    }

    public List<SmtAeopaeProductSkuFrom> getSmtAeopaeProductSkuFroms() {
        return smtAeopaeProductSkuFroms;
    }

    public void setSmtAeopaeProductSkuFroms(List<SmtAeopaeProductSkuFrom> smtAeopaeProductSkuFroms) {
        this.smtAeopaeProductSkuFroms = smtAeopaeProductSkuFroms;
    }
}
