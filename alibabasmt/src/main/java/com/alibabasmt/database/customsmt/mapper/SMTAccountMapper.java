package com.alibabasmt.database.customsmt.mapper;

import com.alibabasmt.domains.querypojos.smtaccount.SmtUserAccountExt;

import java.util.List;
import java.util.Map;

public interface SMTAccountMapper {

    /**
     *
     * @param map
     * @return
     */
    List<SmtUserAccountExt> selectSmtAccountList(Map map);

}