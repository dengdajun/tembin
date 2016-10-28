package com.trading.service.impl;

import com.base.database.trading.mapper.TradingGetDisputeMapper;
import com.base.database.trading.mapper.TradingOrderGetOrdersMapper;
import com.base.database.trading.model.TradingGetDispute;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.threadpool.TaskPool;
import com.trading.service.ITradingOrderGetOrdersNoTransaction;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 写入order表的任务
 * Created by lq on 2014/7/29.
 */

//@Transactional(propagation= Propagation.NOT_SUPPORTED)
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingOrderGetOrdersNoTransactionDoImpl {
    static Logger logger = Logger.getLogger(TradingOrderGetOrdersNoTransactionDoImpl.class);
    @Autowired
    private TradingOrderGetOrdersMapper tradingOrderGetOrdersMapper;
    @Autowired
    private TradingGetDisputeMapper tradingGetDisputeMapper;
    private int bbs=0;//是否在运行的标记,0为未运行  1为正在运行
    private int bbs1=0;//是否在运行的标记,0为未运行  1为正在运行

    public void saveOrderGetOrders(TradingOrderGetOrders OrderGetOrders1) throws Exception {
        if(bbs==1){logger.error("saveOrderGetOrders正在执行，等待....");return;}
        bbs=1;
        //logger.error("TradingOrderGetOrdersNoTransactionImpl被调用！"+TaskPool.togosBS[0]+":数量"+TaskPool.togos.size());
        while (!TaskPool.togos.isEmpty()) {
        try {
            TradingOrderGetOrders oo = TaskPool.togos.take();//获取记录
            String title=oo.getTitle();
            if(StringUtils.isNotBlank(title)){
                title= StringEscapeUtils.escapeXml(title);
            }
            oo.setUpdatetime(new Date());
            if (oo.getId() == null) {
                ObjectUtils.toInitPojoForInsert(oo);
                tradingOrderGetOrdersMapper.insert(oo);
            } else {
                TradingOrderGetOrders t = tradingOrderGetOrdersMapper.selectByPrimaryKey(oo.getId());
                Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
                ObjectUtils.valiUpdate(t.getCreateUser(), TradingOrderGetOrdersMapper.class, oo.getId(), "Synchronize");
                tradingOrderGetOrdersMapper.updateByPrimaryKey(oo);
            }
        } catch (Exception e) {
            logger.error("写入TradingOrderGetOrders报错:",e);
            continue;
        }
        }
        //logger.error("TradingOrderGetOrdersNoTransactionImpl调用结束！"+TaskPool.togosBS[0]+":数量"+TaskPool.togos.size());
        bbs=0;
    }
    public void saveGetDispute(TradingGetDispute getDispute1) throws Exception {
        if(bbs1==1){logger.error("saveGetDispute正在执行，等待....");return;}
        bbs1=1;
        //logger.error("TradingOrderGetOrdersNoTransactionImpl被调用！"+TaskPool.togosBS[0]+":数量"+TaskPool.togos.size());
        while (!TaskPool.togos1.isEmpty()) {
            try {
                TradingGetDispute oo = TaskPool.togos1.take();//获取记录
                String title=oo.getTitle();
                if(StringUtils.isNotBlank(title)){
                    title= StringEscapeUtils.escapeXml(title);
                }
                if (oo.getId() == null) {
                    ObjectUtils.toInitPojoForInsert(oo);
                    tradingGetDisputeMapper.insert(oo);
                } else {
                    TradingGetDispute t = tradingGetDisputeMapper.selectByPrimaryKey(oo.getId());
                    Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
                    ObjectUtils.valiUpdate(t.getCreateUser(), TradingGetDisputeMapper.class, oo.getId(), "Synchronize");
                    tradingGetDisputeMapper.updateByPrimaryKey(oo);
                }
            } catch (Exception e) {
                logger.error("写入TradingGetDispute报错:",e);
                continue;
            }
        }
        //logger.error("TradingOrderGetOrdersNoTransactionImpl调用结束！"+TaskPool.togosBS[0]+":数量"+TaskPool.togos.size());
        bbs1=0;
    }
}
