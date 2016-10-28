package com.trading.service;

import com.base.database.trading.model.TradingStoreCategory;
import com.base.database.trading.model.UsercontrollerEbayAccount;

import java.util.List;

/**
 * Created by Administrtor on 2015/7/10.
 */
public interface ITradingStoreCategory {
    void saveTradingStoreCategory(TradingStoreCategory tradingStoreCategory);

    TradingStoreCategory selectByEbayId(long ebayAccountId);

    TradingStoreCategory selectByCategoryId(long ebayAccountId, String categoryId);

    List<TradingStoreCategory> selectByEbayAccountIdList(long ebayAccountId);

    TradingStoreCategory selectById(long id);

    void getApiStoreCategory(UsercontrollerEbayAccount ue);
}
