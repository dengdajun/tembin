package com.promotionalsale.service.impl;

import com.base.database.trading.mapper.TradingItemPromotionalSaleSetMapper;
import com.base.database.trading.model.TradingItemPromotionalSaleSet;
import com.base.database.trading.model.TradingItemPromotionalSaleSetExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2015/6/18.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingItemPromotionalSaleSetImpl implements com.promotionalsale.service.ITradingItemPromotionalSaleSet {
    @Autowired
    private TradingItemPromotionalSaleSetMapper tradingItemPromotionalSaleSetMapper;

    @Override
    public void saveSet(TradingItemPromotionalSaleSet tradingItemPromotionalSaleSet){
        if(tradingItemPromotionalSaleSet.getId()!=null){
            this.tradingItemPromotionalSaleSetMapper.updateByPrimaryKeySelective(tradingItemPromotionalSaleSet);
        }else{
            this.tradingItemPromotionalSaleSetMapper.insertSelective(tradingItemPromotionalSaleSet);
        }
    }

    @Override
    public void saveListSet(List<TradingItemPromotionalSaleSet> list){
        if(list!=null){
            for(TradingItemPromotionalSaleSet ts:list){
                this.saveSet(ts);
            }
        }
    }

    @Override
    public TradingItemPromotionalSaleSet selectById(long id){
        return this.tradingItemPromotionalSaleSetMapper.selectByPrimaryKey(id);
    }

    @Override
    public TradingItemPromotionalSaleSet selectByItemId(String itemId){
        TradingItemPromotionalSaleSetExample ti = new TradingItemPromotionalSaleSetExample();
        ti.createCriteria().andItemIdEqualTo(itemId).andCheckFlagEqualTo("0");
        List<TradingItemPromotionalSaleSet> list = this.tradingItemPromotionalSaleSetMapper.selectByExample(ti);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }

    }
    @Override
    public List<TradingItemPromotionalSaleSet> selectByRuleIdToSet(long ruleId,String flag){
        TradingItemPromotionalSaleSetExample ti = new TradingItemPromotionalSaleSetExample();
        if("0".equals(flag)){//需要新增的商品
            ti.createCriteria().andRuleIdEqualTo(ruleId).andCheckFlagEqualTo("0").andEbayStatusEqualTo("0");
        }else if("1".equals(flag)){//需要删除的商品
            ti.createCriteria().andRuleIdEqualTo(ruleId).andCheckFlagEqualTo("1").andEbayStatusEqualTo("1");
        }
        List<TradingItemPromotionalSaleSet> list = this.tradingItemPromotionalSaleSetMapper.selectByExample(ti);
        return list;
    }

    @Override
    public void delByRuleId(long ruleId,String flag){
        TradingItemPromotionalSaleSetExample ti = new TradingItemPromotionalSaleSetExample();
        if("1".equals(flag)){//删除成功，删除的数据
            ti.createCriteria().andRuleIdEqualTo(ruleId).andEbayStatusEqualTo("1").andCheckFlagEqualTo("1");
        }else if("2".equals(flag)){//上传失败删除的数据
            ti.createCriteria().andRuleIdEqualTo(ruleId).andEbayStatusEqualTo("0").andCheckFlagEqualTo("0");
        }
        this.tradingItemPromotionalSaleSetMapper.deleteByExample(ti);
    }

    @Override
    public void delByRuleIdAll(long ruleId){
        TradingItemPromotionalSaleSetExample ti = new TradingItemPromotionalSaleSetExample();
        ti.createCriteria().andRuleIdEqualTo(ruleId);
        this.tradingItemPromotionalSaleSetMapper.deleteByExample(ti);
    }

    @Override
    public void delById(long id){
        this.tradingItemPromotionalSaleSetMapper.deleteByPrimaryKey(id);
    }
}
