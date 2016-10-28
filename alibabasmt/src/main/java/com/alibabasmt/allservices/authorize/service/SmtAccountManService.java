package com.alibabasmt.allservices.authorize.service;

import com.alibabasmt.domains.querypojos.smtaccount.SmtUserAccountExt;

import java.util.List;

/**
 * Created by Administrator on 2015/3/26.
 */
public interface SmtAccountManService {
    List<SmtUserAccountExt> querySMTAccByOrg();

    List<SmtUserAccountExt> querySMTAccByCurrId();
}
