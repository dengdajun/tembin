package com.trading.service.impl;

import com.base.database.trading.mapper.TradingListingSetMapper;
import com.base.database.trading.mapper.TradingListingpicUrlMapper;
import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.trading.service.IUsercontrollerEbayAccount;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class TradingListingSetImpl implements com.trading.service.ITradingListingSet {

    @Autowired
    private TradingListingSetMapper tradingListingSetMapper;

    @Override
    public void saveTradingListingSet(TradingListingSet tradingListingSet){
        if(tradingListingSet.getId()!=null){
            this.tradingListingSetMapper.updateByPrimaryKeySelective(tradingListingSet);
        }else{
            this.tradingListingSetMapper.insertSelective(tradingListingSet);
        }
    }

    @Override
    public TradingListingSet selectById(Long id){
        return this.tradingListingSetMapper.selectByPrimaryKey(id);
    }

    @Override
    public TradingListingSet selectByOrgId(Long orgId){
        TradingListingSetExample tls = new TradingListingSetExample();
        tls.createCriteria().andOrgIdEqualTo(orgId);
        List<TradingListingSet> list = this.tradingListingSetMapper.selectByExample(tls);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }


}
