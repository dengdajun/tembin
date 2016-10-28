package com.trading.service.impl;

import com.base.database.trading.mapper.TradingStoreCategoryMapper;
import com.base.database.trading.model.TradingStoreCategory;
import com.base.database.trading.model.TradingStoreCategoryExample;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/7/10.
 * 查询店铺分类信息
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingStoreCategoryImpl implements com.trading.service.ITradingStoreCategory {
    static Logger logger = Logger.getLogger(TradingStoreCategoryImpl.class);
    @Autowired
    public TradingStoreCategoryMapper tradingStoreCategoryMapper;

    @Override
    public void saveTradingStoreCategory(TradingStoreCategory tradingStoreCategory){
        if(tradingStoreCategory.getId()!=null){
            this.tradingStoreCategoryMapper.updateByPrimaryKeySelective(tradingStoreCategory);
        }else{
            this.tradingStoreCategoryMapper.insertSelective(tradingStoreCategory);
        }
    }

    @Override
    public TradingStoreCategory selectByEbayId(long ebayAccountId){
        TradingStoreCategoryExample tsc = new TradingStoreCategoryExample();
        tsc.createCriteria().andEbayAccountIdEqualTo(ebayAccountId);
        List<TradingStoreCategory> list = this.tradingStoreCategoryMapper.selectByExample(tsc);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public TradingStoreCategory selectByCategoryId(long ebayAccountId,String categoryId){
        TradingStoreCategoryExample tsc = new TradingStoreCategoryExample();
        tsc.createCriteria().andEbayAccountIdEqualTo(ebayAccountId).andStoreCategoryIdEqualTo(categoryId);
        List<TradingStoreCategory> list = this.tradingStoreCategoryMapper.selectByExample(tsc);
        if(list!=null&&list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<TradingStoreCategory> selectByEbayAccountIdList(long ebayAccountId){
        TradingStoreCategoryExample tsc = new TradingStoreCategoryExample();
        tsc.createCriteria().andEbayAccountIdEqualTo(ebayAccountId);
        return this.tradingStoreCategoryMapper.selectByExample(tsc);
    }

    @Override
    public TradingStoreCategory selectById(long id){
        return this.tradingStoreCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void getApiStoreCategory(UsercontrollerEbayAccount ue){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetStoreRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "  <RequesterCredentials>\n" +
                "    <eBayAuthToken>"+ue.getEbayToken()+"</eBayAuthToken>\n" +
                "  </RequesterCredentials>\n" +
                "  <CategoryStructureOnly>true</CategoryStructureOnly>\n" +
                "</GetStoreRequest>";
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        String res="";
        try{
            UsercontrollerDevAccountExtend d = userInfoService.getDevByOrder(new HashMap());
            d.setApiSiteid("0");
            d.setApiCallName("GetStore");
            AddApiTask addApiTask = new AddApiTask();
            Map<String, String> resMap = addApiTask.exec2(d, xml, commPars.apiUrl);
            res = resMap.get("message");
            if("apiFail".equals(res)){
                return;
            }
            String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equals(ack)||"Warning".equals(ack)) {//ＡＰＩ成功请求，保存数据
                SamplePaseXml.setStoreCategory(res,ue.getId());
            }else{
                logger.error("绑定ebay账号后获取店铺分类出错，返回xml为11111：：：：：：：：：：：："+res);
            }
        }catch(Exception e){
            logger.error("绑定ebay账号后获取店铺分类出错，返回xml为222222：：：：：：：：：：：："+res);
        }
    }

}
