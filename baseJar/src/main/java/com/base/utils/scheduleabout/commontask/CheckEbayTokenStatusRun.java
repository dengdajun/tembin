package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.ListingDataTask;
import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.trading.model.UsercontrollerEbayAccountExample;
import com.base.domains.querypojos.ItemQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
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
import com.task.service.IListingDataTask;
import com.trading.service.ITradingItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/29.
 * 检查ebaytoken状态，定时任务
 */
public class CheckEbayTokenStatusRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(CheckEbayTokenStatusRun.class);
    @Override
    public void run() {
        String isRunging = TempStoreDataSupport.pullData("task_"+getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        UsercontrollerEbayAccountMapper ueam = (UsercontrollerEbayAccountMapper) ApplicationContextUtil.getBean(UsercontrollerEbayAccountMapper.class);
        UsercontrollerEbayAccountExample ueame = new UsercontrollerEbayAccountExample();
        List<String> s=new ArrayList<>();
        s.add("1");
        s.add("2");
        ueame.createCriteria().andEbayStatusIn(s);
        List<UsercontrollerEbayAccount> liue = ueam.selectByExampleWithBLOBs(ueame);
        if (ObjectUtils.isLogicalNull(liue)){
            return;
        }
        for(UsercontrollerEbayAccount ue:liue){
            String xml = this.getColXml(ue.getEbayToken());
            AddApiTask addApiTask = new AddApiTask();
            UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
            d.setApiCallName(APINameStatic.CheckEbayTokenStatus);
            d.setApiSiteid("0");
            String res="";
            try {
                Map<String, String> resMap = addApiTask.exec2(d, xml, commPars.apiUrl);
                res=resMap.get("message");
                String r1 = resMap.get("stat");
                if(res==null||"".equals(res)||"fail".equalsIgnoreCase(r1)){
                    continue;
                }
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                UsercontrollerEbayAccount ut=new UsercontrollerEbayAccount();
                if("Success".equalsIgnoreCase(ack)) {
                    Document document = SamplePaseXml.formatStr2Doc(res);
                    Element rootElt = document.getRootElement();
                    Element totalElt = rootElt.element("TokenStatus");
                    String status = totalElt.elementText("Status");
                    if("Active".equals(status)){
                       // ue.setEbayStatusDesc(status);
                        ut.setId(ue.getId());
                        ut.setEbayStatus("1");
                        ut.setEbayStatusDesc(status);
                    }else{
                        ut.setId(ue.getId());
                        ut.setEbayStatus("2");
                        ut.setEbayStatusDesc(status);
                    }

                }else if ("Failure".equalsIgnoreCase(ack)){
                    if ("841".equalsIgnoreCase(MyStringUtil.subStringBetween(res, "<ErrorCode>", "</ErrorCode>")))
                    ut.setId(ue.getId());
                    ut.setEbayStatus("2");
                    ut.setEbayStatusDesc("suspended");
                }
                ueam.updateByPrimaryKeySelective(ut);
            }catch(Exception e){
                logger.error("检查ebay账号状态出错：", e);
                continue;
            }
        }


        TempStoreDataSupport.removeData("task_"+getScheduledType());
    }

    public String getColXml(String token){
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetTokenStatusRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "</GetTokenStatusRequest>​";
        return xml;
    }


    /**只从集合记录取多少条*/
    private List<UsercontrollerEbayAccount> filterLimitList(List<UsercontrollerEbayAccount> tlist){
        return filterLimitListFinal(tlist,2);
        /*List<UsercontrollerEbayAccount> x=new ArrayList<UsercontrollerEbayAccount>();
        for (int i = 0;i<2;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public CheckEbayTokenStatusRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.CHECK_EBAY_STATUS_DESC;
    }

    @Override
    public Integer crTimeMinu() {
        return MainTaskStaticParam.SOME_CRTIMEMINU.get(MainTask.CHECK_EBAY_STATUS_DESC);
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }
}
