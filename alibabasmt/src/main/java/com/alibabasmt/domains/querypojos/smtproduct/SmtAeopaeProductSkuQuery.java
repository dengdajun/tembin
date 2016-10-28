package com.alibabasmt.domains.querypojos.smtproduct;

import com.alibabasmt.database.smtproduct.model.SmtAeopSkuProperty;
import com.alibabasmt.database.smtproduct.model.SmtAeopaeProductSku;

import java.util.List;

/**
 * Created by Administrtor on 2015/4/21.
 */
public class SmtAeopaeProductSkuQuery extends SmtAeopaeProductSku {

    private List<SmtAeopSkuProperty> listSmtskuPro;

    public List<SmtAeopSkuProperty> getListSmtskuPro() {
        return listSmtskuPro;
    }

    public void setListSmtskuPro(List<SmtAeopSkuProperty> listSmtskuPro) {
        this.listSmtskuPro = listSmtskuPro;
    }
}
