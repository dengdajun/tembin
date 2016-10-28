package com.base.utils.scheduleabout.commontask;

import com.base.database.trading.model.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.DateUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.promotionalsale.service.ITradingItemPromotionalSaleRule;
import com.promotionalsale.service.ITradingItemPromotionalSaleSet;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by Administrtor on 2015/6/24.
 */
public class GetPromotionalSaleDetailsRun extends BaseScheduledClass implements Scheduledable {

    static Logger logger = Logger.getLogger(GetPromotionalSaleDetailsRun.class);

    @Override
    public String getScheduledType() {
        return MainTask.GET_PROMOTIONAL_SALE_DETAILS;
    }

    @Override
    public Integer crTimeMinu() {
        return 10;
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
        ITradingItemPromotionalSaleRule iTradingItemPromotionalSaleRule = (ITradingItemPromotionalSaleRule) ApplicationContextUtil.getBean(ITradingItemPromotionalSaleRule.class);
        ITradingItemPromotionalSaleSet iTradingItemPromotionalSaleSet = (ITradingItemPromotionalSaleSet) ApplicationContextUtil.getBean(ITradingItemPromotionalSaleSet.class);
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        List<TradingItemPromotionalSaleRule> list  = iTradingItemPromotionalSaleRule.getList();
        if(list!=null&&list.size()>0){
            for(TradingItemPromotionalSaleRule tis:list){
                UsercontrollerEbayAccount ue = userInfoService.getEbayAccountByEbayID(tis.getEbayaccountid());
                String xml = this.getXml(ue.getEbayToken(),tis.getPromotionalsaleid());
                String res = "";
                try {
                    UsercontrollerDevAccountExtend d = userInfoService.getDevByOrder(new HashMap());
                    d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(tis.getSiteid()).getName1());
                    d.setApiCallName(APINameStatic.GetPromotionalSaleDetails);
                    AddApiTask addApiTask = new AddApiTask();
                    CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
                    Map<String, String> resMap = addApiTask.exec2(d, xml, commPars.apiUrl);
                    res = resMap.get("message");
                    if("apiFail".equals(res)){
                        //logger.error("传入xml::::::::::::"+xml);
                        TempStoreDataSupport.removeData("ApiFail_"+d.getApiCallName());
                        continue;
                    }
                    String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                    if ("Success".equals(ack)||"Warning".equals(ack)) {//ＡＰＩ成功请求，保存数据
                        SamplePaseXml.getPro(res,tis);
                        continue;
                    }else{
                        if("219351".equals(SamplePaseXml.getSpecifyElementTextAllInOne(res, "Errors", "ErrorCode"))) {//未找到用户的促销规则
                            //报这个错误码，是未找到用户的促销规则，之前是不报这个错的，在2015-10-09突然报这个错，可能ebay后台那边改了一下规则!
                            //这个可能ebay后台都把规则删除了，但我们这边不在，所以我们就把本地的规则删掉
                            this.deleteTradingItemPromotionalSaleRule(tis.getId());
                        }else {
                            logger.error("获取促销规则报错，返回参数为：：：：：" + res);
                        }
                        continue;
                    }
                }catch(Exception e){
                    logger.error("获取促销规则报错：：：：：：："+e.getMessage());
                    logger.error("获取促销规则报错，请求参数为：：：：："+xml);
                    logger.error("获取促销规则报错，返回参数为：：：：："+res);
                    continue;
                }
            }
        }
        TempStoreDataSupport.removeData("task_" + getScheduledType());
    }

    public String getXml(String token,String proId){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetPromotionalSaleDetailsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "  <RequesterCredentials>\n" +
                "    <eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "  </RequesterCredentials>\n" +
                "   <ErrorLanguage>en_US</ErrorLanguage>\n" +
                "  <PromotionalSaleID>"+proId+"</PromotionalSaleID>\n" +
                "</GetPromotionalSaleDetailsRequest>";
        return xml;
    }

    public void deleteTradingItemPromotionalSaleRule(long id){
        ITradingItemPromotionalSaleRule iTradingItemPromotionalSaleRule = (ITradingItemPromotionalSaleRule) ApplicationContextUtil.getBean(ITradingItemPromotionalSaleRule.class);
        iTradingItemPromotionalSaleRule.delById(id);
    }
}
