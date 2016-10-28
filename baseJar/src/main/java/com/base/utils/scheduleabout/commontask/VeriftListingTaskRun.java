package com.base.utils.scheduleabout.commontask;

import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.mybatis.page.Page;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.MyStringUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.MainTaskStaticParam;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.trading.service.ITradingItem;
import com.trading.service.ITradingListingData;
import com.trading.service.ITradingTembinListingLog;
import com.trading.service.ITradingVeriftListingTask;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/2/9.
 * 设置ebay帐号的notice通知功能
 */
public class VeriftListingTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(VeriftListingTaskRun.class);
    @Override
    public String getScheduledType() {
        return MainTask.RE_LIST_ITEM;
    }

    @Override
    public Integer crTimeMinu() {
        return 2;
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
        String isRunging = TempStoreDataSupport.pullData("task_" + getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        ITradingVeriftListingTask iTradingVeriftListingTask = (ITradingVeriftListingTask) ApplicationContextUtil.getBean(ITradingVeriftListingTask.class);//获取注入的参数
        ITradingItem iTradingItem = (ITradingItem) ApplicationContextUtil.getBean(ITradingItem.class);
        ITradingListingData iTradingListingData = (ITradingListingData) ApplicationContextUtil.getBean(ITradingListingData.class);
        ITradingTembinListingLog iTradingTembinListingLog = (ITradingTembinListingLog) ApplicationContextUtil.getBean(ITradingTembinListingLog.class);
        Page page = new Page();
        page.setPageSize(20);
        page.setCurrentPage(1);
        page.setTotalCount(1000);
        List<TradingVeriftListingTask> list = iTradingVeriftListingTask.selectToPage(new HashMap(),page);
        if(list!=null&&list.size()>0){
            for(TradingVeriftListingTask tt : list){
                try {
                    UsercontrollerEbayAccount ue = userInfoService.getEbayAccountByEbayID(tt.getEbayAccountId());
                    String xml = this.getXml(ue.getEbayToken(), tt.getItemId());
                    UsercontrollerDevAccountExtend d = userInfoService.getDevByOrder(new HashMap());
                    d.setApiSiteid(tt.getSiteId());
                    d.setApiCallName(APINameStatic.RelistItem);
                    AddApiTask addApiTask = new AddApiTask();
                    CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
                    Map<String, String> resMap = addApiTask.exec2(d, xml, commPars.apiUrl);
                    String res = resMap.get("message");
                    if("apiFail".equals(res)){
                        //logger.error("传入xml::::::::::::"+xml);
                        TempStoreDataSupport.removeData("ApiFail_" + d.getApiCallName());
                        continue;
                    }
                    String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                    if ("Success".equals(ack)||"Warning".equals(ack)) {//ＡＰＩ成功请求，保存数据
                        String itemId = SamplePaseXml.getVFromXmlString(res, "ItemID");
                        tt.setTimerFlag("1");
                        tt.setNewItemId(itemId);
                        iTradingVeriftListingTask.saveVeriftListingTask(tt);
                        //刊登成功后,记录一些信息
                        TradingItemWithBLOBs tradingItem = iTradingItem.selectByIdBL(tt.getDocId());

                        tradingItem.setItemId(itemId);
                        tradingItem.setIsFlag("Success");
                        iTradingItem.saveTradingItem(tradingItem);
                        iTradingItem.saveListingSuccess(res, itemId);
                        //新增在线商品表数据
                        iTradingListingData.saveTradingListingDataByTradingItem(tradingItem,res);
                        TradingTembinListingLog ttll = new TradingTembinListingLog();
                        ttll.setDocId(tradingItem.getId());
                        ttll.setCreateDate(new Date());
                        ttll.setIsTimer("0");
                        ttll.setItemId(itemId);
                        ttll.setListingType(tradingItem.getListingtype());
                        iTradingTembinListingLog.saveTradingTembinListingLog(ttll);
                        continue;
                    }else{
                        tt.setTimerFlag("2");
                        iTradingVeriftListingTask.saveVeriftListingTask(tt);
                        logger.error("重新刊登拍买失败，返回参数为：：：：："+res);
                        continue;
                    }

                }catch(Exception e){
                    tt.setTimerFlag("2");
                    iTradingVeriftListingTask.saveVeriftListingTask(tt);
                    logger.error("重新刊登拍买失败：：：：：：：："+e);
                    continue;
                }
            }
        }
        TempStoreDataSupport.removeData("task_" + getScheduledType());
    }
    public String getXml(String token,String itemId){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<RelistItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "  <RequesterCredentials>\n" +
                "    <eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "  </RequesterCredentials>\n" +
                "  <ErrorLanguage>en_US</ErrorLanguage>\n" +
                "  <Item>\n" +
                "    <ItemID>"+itemId+"</ItemID>\n" +
                "    <Quantity>1</Quantity>\n" +
                "  </Item>\n" +
                "</RelistItemRequest>";
        return xml;
    }
}
