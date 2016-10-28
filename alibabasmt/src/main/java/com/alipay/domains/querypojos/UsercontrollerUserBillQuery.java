package com.alipay.domains.querypojos;

import com.base.database.userinfo.model.UsercontrollerUserBill;

/**
 * Created by Administrtor on 2015/5/31.
 */
public class UsercontrollerUserBillQuery extends UsercontrollerUserBill {

    private String accName;

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }
}
