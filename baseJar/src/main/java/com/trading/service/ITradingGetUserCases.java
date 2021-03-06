package com.trading.service;

import com.base.database.trading.model.TradingGetUserCases;
import com.base.domains.querypojos.UserCasesQuery;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/7/22.
 */
public interface ITradingGetUserCases {

    void saveGetUserCases(TradingGetUserCases GetUserCases) throws Exception;

    List<TradingGetUserCases> selectGetUserCasesByTransactionId(String transactionid,Long ebayId);

    List<UserCasesQuery> selectGetUserCases(Map map, Page page);

    TradingGetUserCases selectUserCasesById(Long id);

    List<TradingGetUserCases> selectGetUserCasesByHandled(List<UsercontrollerUserExtend> orgUsers);
}
