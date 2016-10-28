package com.trading.service.impl;

import com.base.database.customtrading.mapper.TradingVeriftListingTaskQueryMapper;
import com.base.database.trading.mapper.TradingListingSetMapper;
import com.base.database.trading.mapper.TradingVeriftListingTaskMapper;
import com.base.database.trading.model.TradingListingSet;
import com.base.database.trading.model.TradingListingSetExample;
import com.base.database.trading.model.TradingVeriftListingTask;
import com.base.database.trading.model.TradingVeriftListingTaskExample;
import com.base.mybatis.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 物品所在地
 * Created by cz on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingVeriftListingTaskImpl implements com.trading.service.ITradingVeriftListingTask {

    @Autowired
    private TradingVeriftListingTaskMapper tradingVeriftListingTaskMapper;

    @Autowired
    private TradingVeriftListingTaskQueryMapper tradingVeriftListingTaskQueryMapper;

    @Override
    public void saveVeriftListingTask(TradingVeriftListingTask tradingVeriftListingTask){
        if(tradingVeriftListingTask.getId()==null){
            this.tradingVeriftListingTaskMapper.insertSelective(tradingVeriftListingTask);
        }else{
            this.tradingVeriftListingTaskMapper.updateByPrimaryKeySelective(tradingVeriftListingTask);
        }
    }

    @Override
    public List<TradingVeriftListingTask> selectByTaskFlag(String taskFalg){
        TradingVeriftListingTaskExample tve = new TradingVeriftListingTaskExample();
        tve.createCriteria().andTimerFlagEqualTo(taskFalg);
        return this.tradingVeriftListingTaskMapper.selectByExample(tve);
    }

    @Override
    public List<TradingVeriftListingTask> selectByItemId(String itemId){
        TradingVeriftListingTaskExample tve = new TradingVeriftListingTaskExample();
        tve.createCriteria().andItemIdEqualTo(itemId);
        return this.tradingVeriftListingTaskMapper.selectByExample(tve);
    }

    @Override
    public List<TradingVeriftListingTask> selectToPage(Map m,Page page){
        return this.tradingVeriftListingTaskQueryMapper.selectByVeriftListingTask(m,page);
    }

}
