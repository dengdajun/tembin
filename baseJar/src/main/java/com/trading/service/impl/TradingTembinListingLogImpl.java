package com.trading.service.impl;

import com.base.database.trading.mapper.TradingTembinListingLogMapper;
import com.base.database.trading.model.TradingTembinListingLog;
import com.base.utils.common.ObjectUtils;
import com.trading.service.ITradingTembinListingLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 付款模块
 * Created by cz on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingTembinListingLogImpl implements ITradingTembinListingLog {
    @Autowired
    private TradingTembinListingLogMapper tradingTembinListingLogMapper;


    @Override
    public void saveTradingTembinListingLog(TradingTembinListingLog tradingTembinListingLog) throws Exception {
        if(tradingTembinListingLog.getId()==null){
            ObjectUtils.toInitPojoForInsert(tradingTembinListingLog);
            this.tradingTembinListingLogMapper.insertSelective(tradingTembinListingLog);
        }else{

            this.tradingTembinListingLogMapper.updateByPrimaryKeySelective(tradingTembinListingLog);
        }

    }

}
