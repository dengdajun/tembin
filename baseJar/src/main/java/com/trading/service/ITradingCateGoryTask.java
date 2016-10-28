package com.trading.service;

import com.base.database.trading.model.TradingCategoryTask;

import java.util.List;

/**
 * Created by Administrtor on 2015/7/9.
 */
public interface ITradingCateGoryTask {
    void saveTradingCategoryTask(TradingCategoryTask tradingCategoryTask);

    void deleteTradingCategoryTask(Long id);

    List<TradingCategoryTask> selectByStatus();

    TradingCategoryTask selectByCateIdSiteId(String cateId, String siteId);
}
