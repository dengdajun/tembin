package com.base.utils.scheduleother.updateorder;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.trading.service.impl.TradingOrderGetOrdersNoTransactionDoImpl;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2015/1/1.
 * 用于TradingOrderGetOrders表的操作
 */
public class UpdateTradingGeDispute extends Thread {
    static Logger logger = Logger.getLogger(UpdateTradingGeDispute.class);

    @Override
    public void run() {
        Thread.currentThread().setName("thread_UpdateTradingGeDispute");

        TradingOrderGetOrdersNoTransactionDoImpl xx= ApplicationContextUtil.getBean(TradingOrderGetOrdersNoTransactionDoImpl.class);
        try {
            xx.saveGetDispute(null);
        } catch (Exception e) {
            logger.error("执行Dispute表报错!",e);
        }

    }
}
