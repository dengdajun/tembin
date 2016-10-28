package com.promotionalsale.service.impl;

import com.base.database.customtrading.mapper.PromotionalSaleQueryMapper;
import com.base.database.trading.mapper.TradingItemPromotionalSaleRuleMapper;
import com.base.database.trading.model.TradingItemPromotionalSaleRule;
import com.base.database.trading.model.TradingItemPromotionalSaleRuleExample;
import com.base.domains.querypojos.PromotionalSaleQuery;
import com.base.mybatis.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/6/18.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingItemPromotionalSaleRuleImpl implements com.promotionalsale.service.ITradingItemPromotionalSaleRule {
    @Autowired
    private TradingItemPromotionalSaleRuleMapper tradingItemPromotionalSaleRuleMapper;
    @Autowired
    private PromotionalSaleQueryMapper promotionalSaleQueryMapper;
    @Override
    public void saveRule(TradingItemPromotionalSaleRule tradingItemPromotionalSaleRule){
        if(tradingItemPromotionalSaleRule.getId()!=null){
            this.tradingItemPromotionalSaleRuleMapper.updateByPrimaryKeySelective(tradingItemPromotionalSaleRule);
        }else{
            this.tradingItemPromotionalSaleRuleMapper.insertSelective(tradingItemPromotionalSaleRule);
        }
    }

    @Override
    public TradingItemPromotionalSaleRule selectById(Long id){
        return this.tradingItemPromotionalSaleRuleMapper.selectByPrimaryKey(id);
    }

    @Override
    public TradingItemPromotionalSaleRule selectByPromotionalSaleId(String id){
        TradingItemPromotionalSaleRuleExample ti = new TradingItemPromotionalSaleRuleExample();
        ti.createCriteria().andPromotionalsaleidEqualTo(id);
        List<TradingItemPromotionalSaleRule> li =  this.tradingItemPromotionalSaleRuleMapper.selectByExample(ti);
        if(li!=null&&li.size()>0){
            return li.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<PromotionalSaleQuery> selectByList(Map map,Page page){
        return this.promotionalSaleQueryMapper.selectByList(map,page);
    }

    @Override
    public List<TradingItemPromotionalSaleRule> getList(){
        TradingItemPromotionalSaleRuleExample ti = new TradingItemPromotionalSaleRuleExample();
        ti.createCriteria().andCheckFlagEqualTo("0");
        return this.tradingItemPromotionalSaleRuleMapper.selectByExample(ti);
    }

    @Override
    public void delById(long id){
        this.tradingItemPromotionalSaleRuleMapper.deleteByPrimaryKey(id);
    }
}
