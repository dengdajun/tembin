package com.base.utils.scheduleabout.commontask;

import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.trading.model.TradingListingData;
import com.base.database.trading.model.TradingPriceTracking;
import com.base.database.trading.model.TradingPriceTrackingAutoPricing;
import com.base.database.trading.model.TradingPriceTrackingPricingRule;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.SamplePaseXml;
import com.sitemessage.service.SiteMessageService;
import com.trading.service.ITradingListingData;
import com.trading.service.ITradingPriceTracking;
import com.trading.service.ITradingPriceTrackingAutoPricing;
import com.trading.service.ITradingPriceTrackingPricingRule;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 价格跟踪定时去某个卖家商品，定时任务
 */
public class PriceTrackingByItemIdTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(PriceTrackingByItemIdTaskRun.class);
    private void syschronizePriceTracking(List<TradingPriceTracking> priceTrackings){
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        SiteMessageService siteMessageService=(SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
        ITradingPriceTracking iTradingPriceTracking=(ITradingPriceTracking)ApplicationContextUtil.getBean(ITradingPriceTracking.class);
        ITradingPriceTrackingAutoPricing iTradingPriceTrackingAutoPricing=(ITradingPriceTrackingAutoPricing)ApplicationContextUtil.getBean(ITradingPriceTrackingAutoPricing.class);
        ITradingPriceTrackingPricingRule iTradingPriceTrackingPricingRule=(ITradingPriceTrackingPricingRule)ApplicationContextUtil.getBean(ITradingPriceTrackingPricingRule.class);
        ITradingListingData iTradingListingData=(ITradingListingData)ApplicationContextUtil.getBean(ITradingListingData.class);
        UserInfoService userInfoService=(UserInfoService)ApplicationContextUtil.getBean(UserInfoService.class);
        if(priceTrackings!=null&&priceTrackings.size()>0){
            String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                    "<GetMultipleItemsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">";
            for(TradingPriceTracking priceTracking:priceTrackings){
                xml+="<ItemID>"+priceTracking.getItemid()+"</ItemID>";
            }
            xml+="</GetMultipleItemsRequest>​";
            UsercontrollerDevAccountExtend dev= null;
            try {
                dev = userInfoService.getDevByOrder(new HashMap());
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<BasicHeader> headers = new ArrayList<BasicHeader>();//sandpoin-1f58-4e64-a45b-56507b02bbeb
            headers.add(new BasicHeader("X-EBAY-API-APP-ID",dev.getApiAppName()));
            //headers.add(new BasicHeader("X-EBAY-API-APP-ID", "chengdul-6dfe-4b1b-905c-41b1bd716d72"));
            headers.add(new BasicHeader("X-EBAY-API-VERSION", "897"));
            headers.add(new BasicHeader("X-EBAY-API-SITE-ID", "0"));
            headers.add(new BasicHeader("X-EBAY-API-CALL-NAME", "GetMultipleItems"));
            headers.add(new BasicHeader("X-EBAY-API-REQUEST-ENCODING", "XML"));
            HttpClient httpClient = HttpClientUtil.getHttpsClient();
            String ack="";
            String res="";
            try {
                res = HttpClientUtil.post(httpClient, "http://open.api.ebay.com/shopping", xml, "UTF-8", headers);
                int i=1;
                ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            } catch (Exception e) {
                logger.error("PriceTrackingByItemIdTaskRun第50:"+e);
            }
            if("Success".equals(ack)||"Warning".equals(ack)){
                if("Warning".equals(ack)){
                    String errors = "";
                    try{
                        errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                    }catch (Exception e){
                        logger.error("PriceTrackingByItemIdTaskRun第62"+res,e);
                        errors="";
                    }
                    if(!StringUtils.isNotBlank(errors)){
                        try{
                            errors =  SamplePaseXml.getWarningInformation(res);
                        }catch (Exception e){
                            logger.error("PriceTrackingByItemIdTaskRunl第69"+res,e);
                        }
                    }
                    List<PublicSitemessage> list1=siteMessageService.selectPublicSitemessageByMessage("price_tracking_by_itemid_FAIL","定时价格跟踪"+priceTrackings.get(0).getItemid());
                    if(list1==null||list1.size()==0){
                        TaskMessageVO taskMessageVO = new TaskMessageVO();
                        taskMessageVO.setMessageType("price_tracking_by_itemid_FAIL");
                        taskMessageVO.setMessageTitle("定时价格跟踪有警告!");
                        taskMessageVO.setMessageContext("定时价格跟踪调用API有警告:" + errors);
                        taskMessageVO.setMessageTo(priceTrackings.get(0).getCreateUser());
                        taskMessageVO.setMessageFrom("system");
                        taskMessageVO.setOrderAndSeller("定时价格跟踪:"+priceTrackings.get(0).getItemid());
                        siteMessageService.addSiteMessage(taskMessageVO);
                    }
                    logger.error("定时价格跟踪有警告!" + errors);
                }
                List<TradingPriceTracking> priceTrackings1=new ArrayList<TradingPriceTracking>();
                try {
                    priceTrackings1=SamplePaseXml.getPriceTrackingItemByItemId(res);
                } catch (Exception e) {
                    logger.error("解析定时价格跟踪res错误:" + e);
                }
                for(TradingPriceTracking priceTracking:priceTrackings1){
                    List<TradingPriceTracking> trackings=iTradingPriceTracking.selectPriceTrackingByItemId(priceTracking.getItemid());
                    if(trackings!=null&&trackings.size()>0){
                        priceTracking.setId(trackings.get(0).getId());
                    }
                    List<TradingPriceTrackingPricingRule> rules= iTradingPriceTrackingPricingRule.selectTradingPriceTrackingPricingRuleByPriceTrackingId(priceTracking.getId());
                    for(TradingPriceTrackingPricingRule rule:rules){
                        TradingPriceTrackingAutoPricing autoPricing=iTradingPriceTrackingAutoPricing.selectPriceTrackingAutoPricingById(rule.getAutopricingId());
                        if(autoPricing!=null){
                            TradingListingData listingData=iTradingListingData.selectById(autoPricing.getListingdateId());
                            if(listingData!=null){
                                Double listPrice=listingData.getPrice();
                                Double trackPrice= Double.valueOf(priceTracking.getCurrentprice());
                                if("priceLowerType".equals(rule.getRuletype())){
                                    if(listPrice<trackPrice&&!priceTracking.getCurrentprice().equals(trackings.get(0).getCurrentprice())){
                                        autoPricing.setPricecount(0);
                                    }
                                }else if("priceHigherType".equals(rule.getRuletype())){
                                    if(listPrice>trackPrice&&!priceTracking.getCurrentprice().equals(trackings.get(0).getCurrentprice())){
                                        autoPricing.setPricecount(0);
                                    }
                                }
                            }
                            try {
                                iTradingPriceTrackingAutoPricing.savePriceTrackingAutoPricing(autoPricing);
                            } catch (Exception e) {
                                e.printStackTrace();
                                logger.error("TradingPriceTrackingAutoPricing保存异常:",e);
                            }
                        }

                    }
                    try {
                        Long updatecount=trackings.get(0).getUpdatecount();
                        priceTracking.setUpdatecount(updatecount+1);
                        iTradingPriceTracking.savePriceTracking(priceTracking);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("PriceTrackingByItemIdTaskRunl保存异常:",e);
                    }
                }
            }else{
                for(TradingPriceTracking priceTracking:priceTrackings){
                    Long updatecount=priceTracking.getUpdatecount();
                    priceTracking.setUpdatecount(updatecount + 1);
                    try {
                        iTradingPriceTracking.savePriceTracking(priceTracking);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("PriceTrackingByItemIdTaskRunl保存异常:",e);
                    }
                }
                logger.error("调用API失败:"+res);
            }
        }
    }
    @Override
    public void run(){
        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }
        String isRunging = TempStoreDataSupport.pullData("task_" + getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        ITradingPriceTracking iTradingPriceTracking=(ITradingPriceTracking)ApplicationContextUtil.getBean(ITradingPriceTracking.class);
        List<TradingPriceTracking> priceTrackings=iTradingPriceTracking.selectPriceTracking();
        if(priceTrackings!=null&&priceTrackings.size()>0){
            List<TradingPriceTracking> priceTrackings1=new ArrayList<>();
            priceTrackings1.add(priceTrackings.get(0));
            syschronizePriceTracking(priceTrackings1);
        }
        TempStoreDataSupport.removeData("task_" + getScheduledType());
    }

    /**只从集合记录取多少条*/
    private List<TradingPriceTracking> filterLimitList(List<TradingPriceTracking> tlist){
        List<TradingPriceTracking> list=new ArrayList<TradingPriceTracking>();
        for(int i=0;i<20;i++){
            list.add(tlist.get(i));
        }
        return list;
    }

    public PriceTrackingByItemIdTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.PRICE_TRACKING_BY_ITEMID;
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
}
