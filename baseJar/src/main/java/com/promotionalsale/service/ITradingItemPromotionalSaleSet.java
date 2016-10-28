package com.promotionalsale.service;

import com.base.database.trading.model.TradingItemPromotionalSaleSet;

import java.util.List;

/**
 * Created by Administrtor on 2015/6/18.
 */
public interface ITradingItemPromotionalSaleSet {
    void saveSet(TradingItemPromotionalSaleSet tradingItemPromotionalSaleSet);

    void saveListSet(List<TradingItemPromotionalSaleSet> list);

    TradingItemPromotionalSaleSet selectById(long id);

    TradingItemPromotionalSaleSet selectByItemId(String itemId);

    List<TradingItemPromotionalSaleSet> selectByRuleIdToSet(long ruleId,String flag);

    void delByRuleId(long ruleId,String flag);

    void delByRuleIdAll(long ruleId);

    void delById(long id);
}
