package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.ListingDataTask;
import com.base.database.task.model.TaskComplement;
import com.base.database.task.model.TaskGetOrders;
import com.base.database.task.model.TradingTaskXml;
import com.base.database.trading.mapper.TradingListingDataMapper;
import com.base.database.trading.model.*;
import com.base.database.userinfo.mapper.UsercontrollerUserBillMapper;
import com.base.database.userinfo.mapper.UsercontrollerUserMapper;
import com.base.database.userinfo.model.SystemLog;
import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.*;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.MainTaskStaticParam;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskPool;
import com.base.utils.xmlutils.SamplePaseXml;
import com.complement.service.ITradingAutoComplement;
import com.task.service.IListingDataTask;
import com.task.service.ITaskComplement;
import com.task.service.ITradingTaskXml;
import com.trading.service.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 在线商品每2分钟执行，定时任务
 */
public class ListingItemDataTimerTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(ListingItemDataTimerTaskRun.class);
    String mark="";


    public String getCosXml(String ebayName,String startTime,String endTime,int pageNumber,String token){
        String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<Pagination ComplexType=\"PaginationType\">\n" +
                "<EntriesPerPage>50</EntriesPerPage>\n" +
                "<PageNumber>"+pageNumber+"</PageNumber>\n" +
                "</Pagination>\n" +
                "<EndTimeFrom>"+startTime+"</EndTimeFrom>\n" +
                "<EndTimeTo>"+endTime+"</EndTimeTo>\n" +
                "<UserID>"+ebayName+"</UserID>\n" +
                "<GranularityLevel>Fine</GranularityLevel><IncludeVariations>true</IncludeVariations><IncludeWatchCount>true</IncludeWatchCount>\n" +
                "<DetailLevel>ReturnAll</DetailLevel>\n" +
                "</GetSellerListRequest>";
        return colStr;
    }

    public String  saveListingData(String ebayAccount,Long userid,String token,String siteid,long ebayAccountId){
        TradingListingDataMapper tldm = (TradingListingDataMapper) ApplicationContextUtil.getBean(TradingListingDataMapper.class);
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        ITradingListingSuccess iTradingListingSuccess = (ITradingListingSuccess) ApplicationContextUtil.getBean(ITradingListingSuccess.class);
        ITradingAutoComplement iTradingAutoComplement = (ITradingAutoComplement) ApplicationContextUtil.getBean(ITradingAutoComplement.class);
        UsercontrollerUserMapper usercontrollerUserMapper=(UsercontrollerUserMapper)ApplicationContextUtil.getBean(UsercontrollerUserMapper.class);
        ITradingListingSet iTradingListingSet=(ITradingListingSet)ApplicationContextUtil.getBean(ITradingListingSet.class);
        ITradingVeriftListingTask iTradingVeriftListingTask=(ITradingVeriftListingTask)ApplicationContextUtil.getBean(ITradingVeriftListingTask.class);
        UsercontrollerUser user = usercontrollerUserMapper.selectByPrimaryKey(Integer.parseInt(userid.toString()));
        ITradingListingData iTradingListingData=(ITradingListingData)ApplicationContextUtil.getBean(ITradingListingData.class);
        if(user.getUserOrgId()==null){
            return "1";
        }
        TradingListingSet tradingListingSet = iTradingListingSet.selectByOrgId(Long.parseLong(user.getUserOrgId().toString()));
        ITradingItem iTradingItem = (ITradingItem) ApplicationContextUtil.getBean(ITradingItem.class);
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + 119);
        String endTo="";
        String endFrom="";
        try {
            endTo = DateUtils.DateToString(DateUtils.nowDateAddDay(100));
            endFrom = DateUtils.DateToString(DateUtils.nowDateMinusDay(18));
        } catch (Exception e) {
            logger.error("ListItemDataTimerTask:",e);
        }
        String saveXml = "";
        AddApiTask addApiTask = new AddApiTask();
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiCallName(APINameStatic.ListingItemList);
        d.setApiSiteid(siteid);
        String colStr = this.getCosXml(ebayAccount, endFrom, endTo, 1, token);
        String res="";
        List<String> listItemId = new ArrayList<>();
        //boolean isupdatedata = false;
        String page="";
        String totalCount = "0";
        try {
            Map<String, String> resMap= addApiTask.exec2(d, colStr, commPars.apiUrl);
            res=resMap.get("message");
            if("apiFail".equals(res)){
                return "2";
            }
            String r1 = resMap.get("stat");
            if(res==null||"".equals(res)||"fail".equalsIgnoreCase(r1)){
                return "2";
            }
            //saveXml = res;
            String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if("Success".equals(ack)) {
                Document document  = SamplePaseXml.formatStr2Doc(res);
                Element rootElt = document.getRootElement();
                Element totalElt = rootElt.element("PaginationResult");
                totalCount = totalElt.elementText("TotalNumberOfEntries");
                page = totalElt.elementText("TotalNumberOfPages");
                for(int i=1;i<=Integer.parseInt(page);i++) {
                    Thread.currentThread().sleep(1000);
                    String colXml = this.getCosXml(ebayAccount, endFrom, endTo, i, token);
                    Map<String, String> resMapxml = addApiTask.exec2(d, colXml, commPars.apiUrl);
                    String returnstr = resMapxml.get("message");
                    if("apiFail".equals(returnstr)){
                        TempStoreDataSupport.removeData("ApiFail_"+d.getApiCallName());
                        continue;
                    }
                    String r11 = resMapxml.get("stat");
                    if(returnstr==null||"".equals(returnstr)||"fail".equalsIgnoreCase(r11)){
                        TempStoreDataSupport.removeData("ApiFail_"+d.getApiCallName());
                        continue;
                    }
                    saveXml=returnstr;
                    String retrunack = SamplePaseXml.getVFromXmlString(returnstr, "Ack");

                    if ("Success".equals(retrunack)||"Warning".equals(retrunack)) {
                        List<TradingListingData> litld = SamplePaseXml.getItemListElememt(returnstr, ebayAccount);
                        for(TradingListingData td : litld){
                            listItemId.add(td.getItemId());
                            td.setEbayAccount(ebayAccount);
                            td.setCreateUser(userid);
                            TradingListingDataExample tlde  = new TradingListingDataExample();
                            tlde.createCriteria().andItemIdEqualTo(td.getItemId());
                            List<TradingListingData> litl = tldm.selectByExample(tlde);
                            boolean isFlag = true;
                            //验证数据是否需要自动补数。
                            iTradingAutoComplement.checkAutoComplementType(td,token,siteid);
                            //验证商品是不是拍买
                            if(td.getListingType().equals("Chinese")&&"1".equals(td.getIsFlag())){
                                TradingItemWithBLOBs docitem = iTradingItem.selectByItemId(td.getItemId());
                                if(docitem!=null&&tradingListingSet!=null&&"0".equals(tradingListingSet.getAutoListing())&&DateUtils.comparTwoDate(new Date(),tradingListingSet.getEndDate())==-1&&DateUtils.comparTwoDate(new Date(),tradingListingSet.getStartDate())==1){//用户设置了自动刊登,且当前时间在设置时间范围内
                                    List<TradingVeriftListingTask> livertask = iTradingVeriftListingTask.selectByItemId(td.getItemId());
                                    if(livertask!=null&&livertask.size()>0){
                                        //说明已加入到任务表中，无需加入数据了！
                                    }else {
                                        TradingVeriftListingTask tvlk = new TradingVeriftListingTask();
                                        tvlk.setCreateTime(new Date());
                                        tvlk.setDocId(docitem.getId());
                                        tvlk.setEbayAccountId(ebayAccountId);
                                        tvlk.setItemId(td.getItemId());
                                        tvlk.setTimerFlag("0");
                                        tvlk.setSiteId(siteid);
                                        iTradingVeriftListingTask.saveVeriftListingTask(tvlk);
                                    }
                                }
                            }

                            if(litl!=null&&litl.size()>0){
                                TradingListingData oldtd = litl.get(0);
                                td.setId(oldtd.getId());
                                td.setUpdateDate(new Date());
                                tldm.updateByPrimaryKeySelective(td);
                                String site="";
                                List<TradingDataDictionary> litdd = DataDictionarySupport.getTradingDataDictionaryByType("site");
                                for(TradingDataDictionary tdd:litdd){
                                    if(tdd.getValue().equals(td.getSite())){
                                        site=tdd.getId()+"";
                                        break;
                                    }
                                }
                                TradingItemWithBLOBs tradingItem =null;
                                if(!ObjectUtils.isLogicalNull(td.getApplicationdata())){
                                    tradingItem = iTradingItem.selectApplitionData(td.getApplicationdata());
                                    if(tradingItem==null){
                                        tradingItem = iTradingItem.selectByTitleSkuEbayAccount(td.getSku(), oldtd.getTitle(),ebayAccountId+"",oldtd.getListingType(),site);
                                    }
                                }else{
                                    tradingItem = iTradingItem.selectByTitleSkuEbayAccount(td.getSku(), oldtd.getTitle(),ebayAccountId+"",oldtd.getListingType(),site);
                                }
                                if(tradingItem!=null){//物品重新上架
                                    SystemLog slg = SystemLogUtils.selectSystemLogByTableId("ListingItemTimer", tradingItem.getId());
                                    if(slg!=null){
                                        slg.setEventname(slg.getEventname()+"_del");
                                        SystemLogUtils.updateSystemLog(slg);
                                    }
                                    /*if("0".equals(td.getIsFlag())&&(tradingItem.getItemId()==null||"".equals(tradingItem.getItemId()))) {//物品重新上架
                                        tradingItem.setIsFlag("Success");
                                        tradingItem.setItemId(td.getItemId());
                                        iTradingItem.saveTradingItem(tradingItem);
                                    }*/
                                    tradingItem.setIsFlag("Success");
                                    tradingItem.setItemId(td.getItemId());
                                    if(!ObjectUtils.isLogicalNull(td.getApplicationdata())){
                                        tradingItem.setApplicationdata(td.getApplicationdata());
                                    }
                                    iTradingItem.saveTradingItem(tradingItem);
                                }
                            }else{
                                td.setUpdateDate(new Date());
                                td.setCreateDate(new Date());
                                tldm.insertSelective(td);
                            }
                            //如查物品下架，改变范本表中的状态
                            if("1".equals(td.getIsFlag())) {
                                TradingItemWithBLOBs tradingItemWithBLOBs = iTradingItem.selectByItemId(td.getItemId());
                                if (tradingItemWithBLOBs != null) {
                                    if(!ObjectUtils.isLogicalNull(td.getApplicationdata())){
                                        tradingItemWithBLOBs.setUuid(td.getApplicationdata());
                                    }
                                    tradingItemWithBLOBs.setIsFlag("");
                                    tradingItemWithBLOBs.setItemId("");
                                    iTradingItem.saveTradingItem(tradingItemWithBLOBs);
                                }
                            }

                            List<TradingListingSuccess> litls = iTradingListingSuccess.selectByItemid(td.getItemId());
                            if(litls==null||litls.size()==0){
                                TradingListingSuccess tls = new TradingListingSuccess();
                                tls.setItemId(td.getItemId());
                                tls.setStartDate(td.getStarttime());
                                tls.setEndDate(td.getEndtime());
                                iTradingListingSuccess.save(tls);
                            }else{
                                TradingListingSuccess tls = litls.get(0);
                                tls.setStartDate(td.getStarttime());
                                tls.setEndDate(td.getEndtime());
                                iTradingListingSuccess.save(tls);
                            }
                        }
                    }else{
                        //itemStr = new ArrayList<>();
                        TempStoreDataSupport.removeData("ApiFail_"+d.getApiCallName());
                        continue;
                    }
                }

            }else{
                TempStoreDataSupport.removeData("ApiFail_"+d.getApiCallName());
                return "2";
            }
            TempStoreDataSupport.pushDataByTime(getScheduledType()+"_"+ebayAccount+"_Number",listItemId,1200);
            return "1";
        } catch (Exception e) {
            /*TradingTaskXml ttx = new TradingTaskXml();
            ttx.setCreateDate(new Date());
            ttx.setTaskType(getScheduledType());
            ttx.setXmlContent(saveXml);
            iTradingTaskXml.saveTradingTaskXml(ttx);*/
            logger.error("listItemDT:",e);
            return "2";
        }finally {
            List list=TempStoreDataSupport.pullData(getScheduledType()+"_"+ebayAccount+"_Number");
            if(!ObjectUtils.isLogicalNull(list)&&!"0".equals(totalCount)&&list.size()>=Integer.parseInt(totalCount)) {//放入集合中的数量，大于等于xml中取出数量时，表示商品已取完
                TradingListingDataExample tld = new TradingListingDataExample();
                tld.createCriteria().andEbayAccountEqualTo(ebayAccount).andIsFlagEqualTo("0");
                List<TradingListingData> lidata = tldm.selectByExample(tld);
                List<String> dataItem = new ArrayList<>();
                for(TradingListingData data:lidata){
                    dataItem.add(data.getItemId());
                }

                List<String> outList = MyCollectionsUtil.listRetain(dataItem,listItemId);
                if(!ObjectUtils.isLogicalNull(outList)) {
                    for (String itemId : outList) {
                        if(ObjectUtils.isLogicalNull(itemId)){
                            logger.error("ebayName:"+ebayAccount+"中出现了空的itemID,在线是："+listItemId.size()+";本地在线："+dataItem.size());
                            continue;
                        }
                        logger.error("该物品被Ebay删除::::::" + itemId);
                        //更新在线数据表
                        TradingListingData tldold = iTradingListingData.selectByItemid(itemId);
                        if (tldold != null) {
                            tldold.setIsFlag("1");
                            tldm.updateByPrimaryKeySelective(tldold);
                        }
                        TradingItemWithBLOBs tradingItemWithBLOBs = iTradingItem.selectByItemId(itemId);
                        if (tradingItemWithBLOBs != null) {
                            tradingItemWithBLOBs.setIsFlag("");
                            tradingItemWithBLOBs.setItemId("");
                            try {
                                iTradingItem.saveTradingItem(tradingItemWithBLOBs);
                            } catch (Exception e) {
                                logger.error("同步在线，更新范本报错！。。。。。。。"+e.getMessage());
                            }
                        }
                    }
                }
                List<String> listr = TempStoreDataSupport.getKeyWithStart(getScheduledType()+"_"+ebayAccount+"_Number");
                for(String key:listr){
                    TempStoreDataSupport.removeData(key);
                    TempStoreDataSupport.pullData(key);
                }
            }
        }
    }
    @Override
    public void run() {
        IListingDataTask iListingDataTask = (IListingDataTask) ApplicationContextUtil.getBean(IListingDataTask.class);
        List<ListingDataTask> lildt= null;
        if (MainTaskStaticParam.CATCH_LISTINGDATA_QUEUE.isEmpty()){
            lildt = iListingDataTask.selectByTimerTaskflag();
            if (lildt==null || lildt.isEmpty()){return;}
        }

        if(MainTaskStaticParam.CATCH_LISTINGDATA_QUEUE.isEmpty()){
            for (ListingDataTask t : lildt){
                try {
                    Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType() + "_" + t.getId());
                    if (b){
                        //logger.error(getScheduledType()+t.getId()+"===之前的帐号任务还未结束不放入===");
                        continue;
                    }
                    MainTaskStaticParam.CATCH_LISTINGDATA_QUEUE.put(t);
                } catch (Exception e) {logger.error("放入ListingData队列出错",e);continue;}
            }
        }

        String taskFlag="";
        ListingDataTask o = null;
        try {
            Iterator<ListingDataTask> iterator=MainTaskStaticParam.CATCH_LISTINGDATA_QUEUE.iterator();
            while (iterator.hasNext()){
                ListingDataTask oo=MainTaskStaticParam.CATCH_LISTINGDATA_QUEUE.take();
                if (oo==null){continue;}
                Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType() + "_" + oo.getId());
                if (b){
                    //logger.error(getScheduledType()+oo.getId()+"===之前的帐号任务还未结束取下一个===");
                    continue;
                }
                o=oo;
                break;
            }
        } catch (Exception e) {

        }
        if(o==null){
            return;
        }
        //logger.error(getScheduledType() +o.getId() + "===任务开始===");
        Thread.currentThread().setName("thread_" + getScheduledType()+"_"+o.getId());
        taskFlag = this.saveListingData(o.getEbayaccount(),o.getUserid(),o.getToken(),o.getSite(),o.getEbayaccountid());
        o.setTaskFlag(taskFlag);
        o.setCreateDate(new Date());
        iListingDataTask.saveListDataTask(o);
        TaskPool.threadRunTime.remove("thread_" + getScheduledType()+"_"+o.getId());
        Thread.currentThread().setName("thread_" + getScheduledType()+"_"+o.getId()+ MyStringUtil.getRandomStringAndNum(5));
        //logger.error(getScheduledType()+o.getId() + "===任务结束===");
        /*for(ListingDataTask ldt:lildt){
            taskFlag = this.saveListingData(ldt.getEbayaccount(),ldt.getUserid(),ldt.getToken(),ldt.getSite());
            ldt.setTaskFlag(taskFlag);
            ldt.setCreateDate(new Date());
            iListingDataTask.saveListDataTask(ldt);
        }*/
    }

    /**只从集合记录取多少条*/
    private List<ListingDataTask> filterLimitList(List<ListingDataTask> tlist){

        return filterLimitListFinal(tlist,2);

        /*List<ListingDataTask> x=new ArrayList<ListingDataTask>();
        for (int i = 0;i<2;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public ListingItemDataTimerTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.LISTING_TIMER_TASK_DATA;
    }

    @Override
    public Integer crTimeMinu() {
        return 20;
    }

    @Override
    public void setMark(String x) {
        this.mark=x;
    }

    @Override
    public String getMark() {
        return this.mark;
    }
}
