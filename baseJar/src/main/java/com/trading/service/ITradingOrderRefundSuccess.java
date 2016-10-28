package com.trading.service;

import com.base.database.trading.model.TradingOrderRefundSuccess;

import java.util.List;

/**
 * Created by Administrtor on 2015/5/5.
 */
public interface ITradingOrderRefundSuccess {
    void saveTradingOrderRefundSuccess(TradingOrderRefundSuccess refundSuccess) throws Exception;

    List<TradingOrderRefundSuccess> selectTradingOrderRefundSuccessByOrderId(String orderId);
}
