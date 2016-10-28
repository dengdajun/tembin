package com.trading.service.impl;

import com.base.database.trading.model.TradingGetDispute;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.utils.scheduleother.updateorder.UpdateTradingGeDispute;
import com.base.utils.scheduleother.updateorder.UpdateTradingOrderGetOrders;
import com.base.utils.threadpool.TaskPool;
import com.trading.service.ITradingOrderGetOrdersNoTransaction;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 写入order表的任务
 * Created by lq on 2014/7/29.
 */

//@Transactional(propagation= Propagation.NOT_SUPPORTED)
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingOrderGetOrdersNoTransactionImpl implements ITradingOrderGetOrdersNoTransaction {
    static Logger logger = Logger.getLogger(TradingOrderGetOrdersNoTransactionImpl.class);

    @Override
    public void saveOrderGetOrders(TradingOrderGetOrders OrderGetOrders1) throws Exception {
        TaskPool.togosBS[0]="0";
        Boolean b= TaskPool.threadIsAliveByName("thread_UpdateTradingOrderGetOrders");
        if(b){
            //logger.error("UpdateTradingOrderGetOrders===之前的任务还未完成继续等待下一个循环===");
            return;
        }
        UpdateTradingOrderGetOrders x=new UpdateTradingOrderGetOrders();
        x.start();
    }
    @Override
    public void saveGetDispute(TradingGetDispute getDispute) throws Exception {
        TaskPool.togosBS1[0]="0";
        Boolean b= TaskPool.threadIsAliveByName("thread_UpdateTradingGeDispute");
        if(b){
            logger.error("UpdateTradingGeDispute===之前的任务还未完成继续等待下一个循环===");
            return;
        }
        UpdateTradingGeDispute x=new UpdateTradingGeDispute();
        x.start();
    }
}
