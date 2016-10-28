package com.trading.service;

import com.base.database.trading.model.TradingListingSet;

/**
 * Created by Administrtor on 2015/7/1.
 */
public interface ITradingListingSet {
    void saveTradingListingSet(TradingListingSet tradingListingSet);

    TradingListingSet selectById(Long id);

    TradingListingSet selectByOrgId(Long orgId);
}
