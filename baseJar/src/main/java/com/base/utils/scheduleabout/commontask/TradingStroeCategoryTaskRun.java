package com.base.utils.scheduleabout.commontask;

import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.TradingItemPromotionalSaleRule;
import com.base.database.trading.model.TradingStoreCategory;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.trading.model.UsercontrollerEbayAccountExample;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.ObjectUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.promotionalsale.service.ITradingItemPromotionalSaleRule;
import com.promotionalsale.service.ITradingItemPromotionalSaleSet;
import com.trading.service.ITradingStoreCategory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/6/24.
 */
public class TradingStroeCategoryTaskRun extends BaseScheduledClass implements Scheduledable {

    static Logger logger = Logger.getLogger(TradingStroeCategoryTaskRun.class);

    @Override
    public String getScheduledType() {
        return MainTask.GetStoreCateGory;
    }

    @Override
    public Integer crTimeMinu() {
        return 800;
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
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        //ITradingStoreCategory iTradingStoreCategory = (ITradingStoreCategory) ApplicationContextUtil.getBean(ITradingStoreCategory.class);
        UsercontrollerEbayAccountMapper ueam = (UsercontrollerEbayAccountMapper) ApplicationContextUtil.getBean(UsercontrollerEbayAccountMapper.class);
        UsercontrollerEbayAccountExample ueame = new UsercontrollerEbayAccountExample();
        ueame.createCriteria().andEbayStatusEqualTo("1");
        List<UsercontrollerEbayAccount> liue = ueam.selectByExampleWithBLOBs(ueame);
        String res="";
        for(int i =0 ;i<liue.size();i++) {
            UsercontrollerEbayAccount ue = liue.get(i);
            try{
                /*TradingStoreCategory tscold = iTradingStoreCategory.selectByEbayId(ue.getId());
                if(tscold==null){//该账号还未开通店铺功能
                    continue;
                }*/
                String storeFlag = TempStoreDataSupport.pullData(getScheduledType()+"_storeFlag_"+ue.getEbayAccount());
                if("1".equals(storeFlag)){
                    continue;
                }
                String xml = getXml(ue.getEbayToken());
                UsercontrollerDevAccountExtend d = userInfoService.getDevByOrder(new HashMap());
                d.setApiSiteid("0");
                d.setApiCallName("GetStore");
                AddApiTask addApiTask = new AddApiTask();
                Map<String, String> resMap = addApiTask.exec2(d, xml, commPars.apiUrl);
                res = resMap.get("message");
                if("apiFail".equals(res)){
                    TempStoreDataSupport.removeData("ApiFail_" + d.getApiCallName());
                    continue;
                }
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                if ("Success".equals(ack)||"Warning".equals(ack)) {//ＡＰＩ成功请求，保存数据
                    SamplePaseXml.setStoreCategory(res,ue.getId());
                }else{
                    if("13003".equals(SamplePaseXml.getSpecifyElementTextAllInOne(res, "Errors", "ErrorCode"))){
                        TempStoreDataSupport.pushDataByTime(getScheduledType()+"_storeFlag_"+ue.getEbayAccount(),"1",1200);
                    }else{
                        logger.error("同步:"+ue.getEbayAccount()+"账号的店铺出错1111:::::::"+res);
                    }
                }
            }catch(Exception e){
                logger.error("同步:"+ue.getEbayAccount()+"账号的店铺出错:::::::"+res);
                continue;
            }
        }

        TempStoreDataSupport.removeData("task_" + getScheduledType());
    }

    public String getXml(String token){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetStoreRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "  <RequesterCredentials>\n" +
                "    <eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "  </RequesterCredentials>\n" +
                "  <CategoryStructureOnly>true</CategoryStructureOnly>\n" +
                "</GetStoreRequest>";
        return xml;
    }
}
