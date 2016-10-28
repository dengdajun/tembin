package com.base.utils.scheduleabout.commontask;

import com.base.database.trading.mapper.TradingItemMapper;
import com.base.database.trading.mapper.TradingTimerListingMapper;
import com.base.database.trading.model.*;
import com.base.database.userinfo.mapper.UsercontrollerUserMapper;
import com.base.database.userinfo.model.SystemLog;
import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.ObjectUtils;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.trading.service.ITradingAssessViewSet;
import com.trading.service.ITradingItem;
import com.trading.service.ITradingListingData;
import com.trading.service.ITradingTembinListingLog;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 定时刊登任务
 */
public class TimeListingItemTaskRunAble extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(TimeListingItemTaskRunAble.class);
    @Override
    public void run() {





        String isRunging = TempStoreDataSupport.pullData("task_"+getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_"+getScheduledType(),"x");
        /*TestService testService=ApplicationContextUtil.getBean(TestService.class);

        Test1Service test1Service=ApplicationContextUtil.getBean(Test1Service.class);
        testService.serviceTest();
        if(true){throw new RuntimeException("d");}
        test1Service.serviceTest();

        if(true){return;}*/

        TradingTimerListingMapper timeMapper = (TradingTimerListingMapper) ApplicationContextUtil.getBean(TradingTimerListingMapper.class);
        ITradingTembinListingLog iTradingTembinListingLog = (ITradingTembinListingLog) ApplicationContextUtil.getBean(ITradingTembinListingLog.class);
        TradingTimerListingExample texample=new TradingTimerListingExample();
        texample.createCriteria().andTimerLessThanOrEqualTo(new Date()).andTimerFlagEqualTo("0").andCheckFlagEqualTo("0");
        List<TradingTimerListingWithBLOBs> listingWithBLOBses= timeMapper.selectByExampleWithBLOBs(texample);/**查询出有哪些刊登任务是需要执行的*/
        if(ObjectUtils.isLogicalNull(listingWithBLOBses)){
            TempStoreDataSupport.removeData("task_"+getScheduledType());
            return;
        }

        UserInfoService userInfoService= (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        UsercontrollerUserMapper user = (UsercontrollerUserMapper) ApplicationContextUtil.getBean(UsercontrollerUserMapper.class);
        TradingItemMapper tradingItemMappers = (TradingItemMapper) ApplicationContextUtil.getBean(TradingItemMapper.class);
        CommAutowiredClass commV = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);

        ITradingItem iTradingItem = (ITradingItem) ApplicationContextUtil.getBean(ITradingItem.class);
        ITradingListingData iTradingListingData = (ITradingListingData) ApplicationContextUtil.getBean(ITradingListingData.class);
        if(listingWithBLOBses.size()>20){
            listingWithBLOBses =filterLimitList(listingWithBLOBses);
        }

        for (TradingTimerListingWithBLOBs withBLOBs : listingWithBLOBses){
            if(TempStoreDataSupport.pullData("doc_"+withBLOBs.getItem())==null){
                TempStoreDataSupport.pushDataByTime("doc_"+withBLOBs.getItem(),1,1440);
                logger.error("doc_"+withBLOBs.getItem()+":::::::::::执行次数：1");
            }else{
                TempStoreDataSupport.pushData("doc_"+withBLOBs.getItem(),Integer.parseInt(TempStoreDataSupport.pullData("doc_"+withBLOBs.getItem()).toString())+1);
                logger.error("doc_"+withBLOBs.getItem()+":::::::::::执行次数："+TempStoreDataSupport.pullData("doc_"+withBLOBs.getItem()));
            }



            TradingTimerListingWithBLOBs t=new TradingTimerListingWithBLOBs();
            TradingTimerListingWithBLOBs t1=new TradingTimerListingWithBLOBs();
            TradingItemWithBLOBs tradingItems = tradingItemMappers.selectByPrimaryKey(withBLOBs.getItem());
            if(!ObjectUtils.isLogicalNull(tradingItems.getItemId())){
                logger.error("通过范本id查询到数据：：：：：：：：："+tradingItems.getItemId());
                withBLOBs.setTimerFlag("1");
                timeMapper.updateByPrimaryKeySelective(withBLOBs);
                tradingItems.setListingWay("0");
                tradingItemMappers.updateByPrimaryKeySelective(tradingItems);
                continue;
            }
            t1.setRunStartTime(new Date());
            t1.setTimerFlag("0");
            t1.setId(withBLOBs.getId());

            timeMapper.updateByPrimaryKeySelective(t1);//设置一个开始时间
            //首先根据ebay帐号id获取到token
            if(StringUtils.isEmpty(withBLOBs.getEbayId())){continue;}
            UsercontrollerEbayAccount ebay=userInfoService.getEbayAccountByEbayID(Long.parseLong(withBLOBs.getEbayId()));
            Long userId = ebay.getUserId();//获取到系统用户id
            if(ObjectUtils.isLogicalNull(userId)){continue;}
            //根据系统id去获取绑定的默认开发者id
            UsercontrollerUser userVo= user.selectByPrimaryKey(userId.intValue());
            Long devId = userVo.getDefaultDevAccount();//获取到开发帐号
            if(ObjectUtils.isLogicalNull(devId)){continue;}
            try {
                //UsercontrollerDevAccountExtend devInfo= userInfoService.getDevInfo(devId);
                UsercontrollerDevAccountExtend devInfo = userInfoService.getDevByOrder(new HashMap());
                devInfo.setRepetitionMark(withBLOBs.getItem()+"");
                devInfo.setApiSiteid(withBLOBs.getStateId());
                devInfo.setApiCallName(withBLOBs.getApiMethod());
                AddApiTask addApiTask = new AddApiTask();
                Map<String, String> resMap = addApiTask.exec2(devInfo, StringEscapeUtils.unescapeXml(withBLOBs.getTimerMessage()), commV.apiUrl);
                String r1 = resMap.get("stat");
                String res = resMap.get("message");
                if("apiFail".equals(res)){
                    logger.error("定时报错，返回xml：：：：：："+res);
                    TempStoreDataSupport.removeData("ApiFail_"+devInfo.getApiCallName());
                    continue;
                }
                if ("fail".equalsIgnoreCase(r1)||ObjectUtils.isLogicalNull(res)) {
                    logger.error("定时刊登失败!"+withBLOBs.getId());
                    t.setReturnMessage(res);
                    t.setTimerFlag("2");//刊登失败，但是也已经刊登过了
                    tradingItems.setListingWay("0");
                    tradingItemMappers.updateByPrimaryKeySelective(tradingItems);
                    SystemLog sl = new SystemLog();
                    sl.setCreatedate(new Date());
                    sl.setOperuser(tradingItems.getId() + "");
                    sl.setEventname("ListingItemTimer");
                    sl.setEventdesc("定时刊登失败！原因：获取返回参数异常，请稍后检查是否在线！");
                    SystemLogUtils.saveLog(sl);
                }else {
                    t.setReturnMessage(res);
                    t.setTimerFlag("1");
                }
                //将刊登成功的itemid写入item表
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                    String itemId = SamplePaseXml.getVFromXmlString(res, "ItemID");
                    TradingItemWithBLOBs tradingItem=new TradingItemWithBLOBs();
                    tradingItem.setId(withBLOBs.getItem());
                    tradingItem.setItemId(itemId);
                    tradingItem.setIsFlag("Success");
                    iTradingItem.saveTradingItem(tradingItem);
                    iTradingItem.saveListingSuccess(res,itemId);

                    TradingTembinListingLog ttll = new TradingTembinListingLog();
                    ttll.setDocId(tradingItem.getId());
                    ttll.setCreateDate(new Date());
                    ttll.setIsTimer("1");
                    ttll.setItemId(itemId);
                    ttll.setListingType(tradingItem.getListingtype());
                    iTradingTembinListingLog.saveTradingTembinListingLog(ttll);

                    iTradingItem.saveListingSuccess(res, itemId);
                    tradingItem = iTradingItem.selectByIdBL(tradingItem.getId());
                    iTradingListingData.saveTradingListingDataByTradingItem(tradingItem,res);
                    //检查定时刊登是否有错误日志，如果有，删掉之前定时刊登的错误日志
                    SystemLog sl = SystemLogUtils.selectSystemLogByUserId("ListingItemTimer",tradingItems.getId()+"");
                    if(sl!=null){
                        sl.setEventname(sl.getEventname()+"_del");
                        SystemLogUtils.updateSystemLog(sl);
                    }
                }else{
                    Document document= SamplePaseXml.formatStr2Doc(res);
                    if(document==null){
                        logger.error("定时刊登任务报错:id"+withBLOBs.getId()+"返回报文为空！");
                        return ;
                    }
                    Element rootElt = document.getRootElement();
                    Iterator<Element> e =  rootElt.elementIterator("Errors");
                    String errors = "";
                    String errorCode="";
                    if(e!=null){
                        while (e.hasNext()){
                            Element es = e.next();
                            if(es.element("SeverityCode")!=null&&"Error".equals(es.elementText("SeverityCode"))) {
                                errors += es.elementText("LongMessage") + ";";
                                errorCode+=es.elementText("SeverityCode")+";";
                            }
                        }
                    }
                    tradingItems.setListingWay("0");
                    tradingItemMappers.updateByPrimaryKeySelective(tradingItems);

                    SystemLog sl = SystemLogUtils.selectSystemLogByTableId("ListingItemTimer",tradingItems.getId());
                    if(sl==null) {
                        sl = new SystemLog();
                        sl.setCreatedate(new Date());
                        sl.setOperuser(withBLOBs.getCreateUser() + "");
                        sl.setTableId(tradingItems.getId());
                        sl.setEventname("ListingItemTimer");
                        sl.setOther(errorCode);
                        sl.setEventdesc("定时刊登失败！原因：" + errors);
                        SystemLogUtils.saveLog(sl);
                    }else{
                        sl.setOther(errorCode);
                        sl.setCreatedate(new Date());
                        sl.setEventdesc("定时刊登失败！原因：" + errors);
                        SystemLogUtils.updateSystemLog(sl);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage()+"定时刊登任务报错 id"+withBLOBs.getId(),e);
            }finally {
                t.setId(withBLOBs.getId());
                t.setRunEndTime(new Date());
                timeMapper.updateByPrimaryKeySelective(t);
                TempStoreDataSupport.removeData("task_"+getScheduledType());
            }
        }
        TempStoreDataSupport.removeData("task_"+getScheduledType());
    }

    /**只从集合记录取多少条*/
    private List<TradingTimerListingWithBLOBs> filterLimitList(List<TradingTimerListingWithBLOBs> tlist){

       return filterLimitListFinal(tlist,20);

        /*List<TradingTimerListingWithBLOBs> x=new ArrayList<TradingTimerListingWithBLOBs>();
        for (int i = 0;i<20;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public TimeListingItemTaskRunAble(){
    }

    @Override
    public String getScheduledType() {
        return MainTask.LISTING_SCHEDULE;
    }

    @Override
    public Integer crTimeMinu() {
        return null;
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }
}
