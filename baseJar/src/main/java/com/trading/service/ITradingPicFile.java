package com.trading.service;

import com.base.database.trading.model.TradingPicFile;

/**
 * Created by Administrtor on 2015/6/2.
 */
public interface ITradingPicFile {
    void saveTradingPicFile(TradingPicFile tradingPicFile);

    TradingPicFile selectByMd5id(String mdk5id);
}
