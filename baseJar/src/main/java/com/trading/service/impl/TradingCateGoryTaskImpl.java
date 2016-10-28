package com.trading.service.impl;

import com.base.database.customtrading.mapper.TradingVeriftListingTaskQueryMapper;
import com.base.database.trading.mapper.TradingCategoryTaskMapper;
import com.base.database.trading.mapper.TradingVeriftListingTaskMapper;
import com.base.database.trading.model.TradingCategoryTask;
import com.base.database.trading.model.TradingCategoryTaskExample;
import com.base.database.trading.model.TradingVeriftListingTask;
import com.base.database.trading.model.TradingVeriftListingTaskExample;
import com.base.mybatis.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 查询分类信息定时表
 * Created by cz on 2014/7/23.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingCateGoryTaskImpl implements com.trading.service.ITradingCateGoryTask {
    @Autowired
    public TradingCategoryTaskMapper tradingCategoryTaskMapper;

    @Override
    public void saveTradingCategoryTask(TradingCategoryTask tradingCategoryTask){
        if(tradingCategoryTask.getId()!=null){
            this.tradingCategoryTaskMapper.updateByPrimaryKeySelective(tradingCategoryTask);
        }else{
            this.tradingCategoryTaskMapper.insertSelective(tradingCategoryTask);
        }
    }

    @Override
    public void deleteTradingCategoryTask(Long id){
        this.tradingCategoryTaskMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<TradingCategoryTask> selectByStatus(){
        TradingCategoryTaskExample tct = new TradingCategoryTaskExample();
        tct.createCriteria().andStatusEqualTo("0");
        return this.tradingCategoryTaskMapper.selectByExample(tct);
    }

    @Override
    public TradingCategoryTask selectByCateIdSiteId(String cateId,String siteId){
        TradingCategoryTaskExample tct = new TradingCategoryTaskExample();
        tct.createCriteria().andCateIdEqualTo(cateId).andSiteSoureIdEqualTo(Long.parseLong(siteId));
        List<TradingCategoryTask> li =  this.tradingCategoryTaskMapper.selectByExample(tct);
        if(li!=null&&li.size()>0){
            return li.get(0);
        }else{
            return null;
        }
    }
}
