package com.base.utils.scheduleabout.commontask;

import com.base.database.trading.model.TradingDataDictionary;
import com.base.database.trading.model.TradingItemWithBLOBs;
import com.base.database.trading.model.TradingPublicLevelAttr;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.domains.querypojos.ItemQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.Item;
import com.base.xmlpojo.trading.addproduct.ItemSpecifics;
import com.base.xmlpojo.trading.addproduct.NameValueList;
import com.trading.service.ITradingItem;
import com.trading.service.ITradingPublicLevelAttr;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/6/24.
 */
public class ItemSpecificsRun extends BaseScheduledClass implements Scheduledable {

    static Logger logger = Logger.getLogger(ItemSpecificsRun.class);

    @Override
    public String getScheduledType() {
        return MainTask.ItemSpecificsRun;
    }

    @Override
    public Integer crTimeMinu() {
        return 5;
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }

    @Override
    public void run() {
        logger.error("同步开始……。……………。");
        if(TempStoreDataSupport.pullData("testzz")!=null&&"testzz".equals(TempStoreDataSupport.pullData("testzz"))){
            return;
        }
        TempStoreDataSupport.pushDataByTime("testzz","testzz",3600);
        ITradingItem iTradingItem = (ITradingItem) ApplicationContextUtil.getBean(ITradingItem.class);
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        ITradingPublicLevelAttr iTradingPublicLevelAttr = (ITradingPublicLevelAttr) ApplicationContextUtil.getBean(ITradingPublicLevelAttr.class);
        List<ItemQuery> list = iTradingItem.selectByItemIdList();
        for(int i = 0;i<list.size();i++){
            ItemQuery items = list.get(i);
            logger.error("：：：：：：：：：：：：："+i);
            try {
                TradingItemWithBLOBs tradingItemWithBLOBs = iTradingItem.selectByIdBL(items.getId());
                UsercontrollerEbayAccount ue = userInfoService.getEbayAccountByEbayID(Long.parseLong(tradingItemWithBLOBs.getEbayAccount()));
                String xml = this.getXml(ue.getEbayToken(), tradingItemWithBLOBs.getItemId());
                UsercontrollerDevAccountExtend d = userInfoService.getDevByOrder(new HashMap());
                d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItemWithBLOBs.getSite())).getName1());
                d.setApiCallName(APINameStatic.GetItem);
                AddApiTask addApiTask = new AddApiTask();
                CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
                Map<String, String> resMap = addApiTask.exec2(d, xml, commPars.apiUrl);
                String res = resMap.get("message");
                if("apiFail".equals(res)){
                    //logger.error("传入xml::::::::::::"+xml);
                    TempStoreDataSupport.removeData("ApiFail_"+d.getApiCallName());
                    continue;
                }
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                if ("Success".equals(ack)) {//ＡＰＩ成功请求，保存数据
                    Item item = SamplePaseXml.getItem(res);
                    if (item.getItemSpecifics() != null) {
                        ItemSpecifics iscs = item.getItemSpecifics();
                        List<NameValueList> linv = iscs.getNameValueList();
                        //删除下面的字属性
                        List<TradingPublicLevelAttr> litpla = iTradingPublicLevelAttr.selectByParentId("ItemSpecifics", tradingItemWithBLOBs.getId());
                        for (TradingPublicLevelAttr tpa : litpla) {
                            iTradingPublicLevelAttr.deleteByParentID(null, tpa.getId());
                        }
                        iTradingPublicLevelAttr.deleteByParentID("ItemSpecifics", tradingItemWithBLOBs.getId());
                        TradingPublicLevelAttr tpla = iTradingPublicLevelAttr.toDAOPojo("ItemSpecifics", null);
                        tpla.setParentId(tradingItemWithBLOBs.getId());
                        tpla.setParentUuid(tradingItemWithBLOBs.getUuid());
                        iTradingPublicLevelAttr.savePublicLevelAttr(tpla);
                        iTradingPublicLevelAttr.deleteByParentID(null, tpla.getId());
                        for (NameValueList nvl : linv) {
                            TradingPublicLevelAttr tpl = iTradingPublicLevelAttr.toDAOPojo(nvl.getName(), nvl.getValue().get(0).toString());
                            tpl.setParentUuid(tpla.getUuid());
                            tpl.setParentId(tpla.getId());
                            iTradingPublicLevelAttr.savePublicLevelAttr(tpl);
                        }
                    }
                }else{
                    //logger.error("传入xml::::::::::::"+xml);
                    TempStoreDataSupport.removeData("ApiFail_"+d.getApiCallName());
                    continue;
                }
            }catch(Exception e){
                logger.error(e);
                continue;
            }
            if(i==list.size()-1){
                logger.error("同步自定义属性完成！！！！！！！！！！！！！！！！！！！！");
                logger.error("同步自定义属性完成！！！！！！！！！！！！！！！！！！！！");
                logger.error("同步自定义属性完成！！！！！！！！！！！！！！！！！！！！");
                logger.error("同步自定义属性完成！！！！！！！！！！！！！！！！！！！！");
                logger.error("同步自定义属性完成！！！！！！！！！！！！！！！！！！！！");
                logger.error("同步自定义属性完成！！！！！！！！！！！！！！！！！！！！");
                logger.error("同步自定义属性完成！！！！！！！！！！！！！！！！！！！！");
                logger.error("同步自定义属性完成！！！！！！！！！！！！！！！！！！！！");
                logger.error("同步自定义属性完成！！！！！！！！！！！！！！！！！！！！");
            }
        }
        logger.error("同步结束。…。…。…。…。…。…。…。…。…。………");
    }

    public String getXml(String token,String itemId){
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<ItemID>" + itemId + "</ItemID>\n" +
                "<DetailLevel>ReturnAll</DetailLevel>\n" +
                "<IncludeItemSpecifics>true</IncludeItemSpecifics>\n"+
                "</GetItemRequest>";
        return xml;
    }
}
