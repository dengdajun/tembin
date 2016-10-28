package com.trading.service;

import com.base.database.trading.model.TradingVeriftListingTask;
import com.base.mybatis.page.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/7/3.
 */
public interface ITradingVeriftListingTask {
    void saveVeriftListingTask(TradingVeriftListingTask tradingVeriftListingTask);

    List<TradingVeriftListingTask> selectByTaskFlag(String taskFalg);

    List<TradingVeriftListingTask> selectByItemId(String itemId);

    List<TradingVeriftListingTask> selectToPage(Map m, Page page);
}
