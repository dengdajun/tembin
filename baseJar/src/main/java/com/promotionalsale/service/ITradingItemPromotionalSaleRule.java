package com.promotionalsale.service;

import com.base.database.trading.model.TradingItemPromotionalSaleRule;
import com.base.domains.querypojos.PromotionalSaleQuery;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/6/18.
 */
public interface ITradingItemPromotionalSaleRule {
    void saveRule(TradingItemPromotionalSaleRule tradingItemPromotionalSaleRule);

    TradingItemPromotionalSaleRule selectById(Long id);

    TradingItemPromotionalSaleRule selectByPromotionalSaleId(String id);

    List<PromotionalSaleQuery> selectByList(Map map, Page page);

    List<TradingItemPromotionalSaleRule> getList();

    void delById(long id);
}
