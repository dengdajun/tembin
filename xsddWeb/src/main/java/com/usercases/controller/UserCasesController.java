package com.usercases.controller;

import com.alibaba.fastjson.JSONObject;
import com.base.database.task.model.TaskGetUserCases;
import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.UserCasesQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.BindAccountAPI;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.thirdpart.OrderQueryTrack;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.task.service.ITaskGetUserCases;
import com.trading.service.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrtor on 2014/8/28.
 */
@Controller
@RequestMapping("userCases")
public class UserCasesController extends BaseAction{
    static Logger logger = Logger.getLogger(UserCasesController.class);
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ITradingGetUserCases iTradingGetUserCases;
    @Autowired
    private ITradingGetEBPCaseDetail iTradingGetEBPCaseDetail;
    @Autowired
    private ITradingCasePaymentDetail iTradingCasePaymentDetail;
    @Autowired
    private ITradingCaseResponseHistory iTradingCaseResponseHistory;
    @Autowired
    private ITradingGetDispute iTradingGetDispute;
    @Autowired
    private ITradingGetDisputeMessage iTradingGetDisputeMessage;
    @Autowired
    private ITradingOrderGetOrders iTradingOrderGetOrders;
    @Autowired
    private ITradingOrderGetSellerTransactions iTradingOrderGetSellerTransactions;
    @Autowired
    private ITradingOrderGetItem iTradingOrderGetItem;
    @Autowired
    private ITradingOrderPictureDetails iTradingOrderPictureDetails ;
    @Autowired
    private ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner;
    @Autowired
    private ITradingMessageTemplate iTradingMessageTemplate;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private ITaskGetUserCases iTaskGetUserCases;
    @Value("${SERVICE_ITEM_URL}")
    private String SERVICE_ITEM_URL;
    @Value("${ITEM_LIST_ICON_URL}")
    private String ITEM_LIST_ICON_URL;

    @Autowired
    private ITradingListingData iTradingListingData;
    @Autowired
    private ITradingDataDictionary iTradingDataDictionary;


    /*
    *纠纷列表
    */
    @RequestMapping("/userCasesList.do")
    public ModelAndView userCasesList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays=systemUserManagerService.queryCurrAllEbay(map);
        List<UsercontrollerUserExtend> orgUsers=systemUserManagerService.queryAllUsersByOrgID("yes");
        for(UsercontrollerUserExtend orgUser:orgUsers){
            if(orgUser.getUserId()==sessionVO.getId()&&orgUser.getUserParentId()==null){
                ebays=systemUserManagerService.queryACurrAllEbay(map);
            }
        }
        modelMap.put("serviceItemUrl",SERVICE_ITEM_URL);
        modelMap.put("itemListIconUrl",ITEM_LIST_ICON_URL);
        modelMap.put("ebays",ebays);
        return forword("/usercases/userCasesList",modelMap);
    }
    /*
    *同步纠纷初始化
    */
    @RequestMapping("/getUserCases.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    @ResponseBody
    public ModelAndView getUserCases(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays = systemUserManagerService.queryCurrAllEbay(map);
        List<Date> ebayDates=new ArrayList<Date>();
        for(UsercontrollerEbayAccountExtend ebay:ebays){
            List<TaskGetUserCases> taskGetUserCaseses= iTaskGetUserCases.selectTaskGetMessagesByFlagIsFalseOrderByLastSycTimeAndEbayName(ebay.getEbayName());
            if(taskGetUserCaseses!=null&&taskGetUserCaseses.size()>0){
                ebayDates.add(taskGetUserCaseses.get(taskGetUserCaseses.size()-1).getLastsyctime());
            }else{
                ebayDates.add(null);
            }
        }
        modelMap.put("ebayDates",ebayDates);
        modelMap.put("ebays",ebays);
        return forword("usercases/synUserCases",modelMap);
    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadUserCasesList.do")
    @ResponseBody
    public void loadUserCasesList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        /**分页组装*/
        String account=request.getParameter("account");
        String type=request.getParameter("type");
        String status=request.getParameter("status");
        String days=request.getParameter("days");
        String name=request.getParameter("name");
        String content=request.getParameter("content");
        String starttime1=request.getParameter("starttime1");
        String endtime1=request.getParameter("endtime1");
        if(!StringUtils.isNotBlank(account)){
            account=null;
        }
        if(!StringUtils.isNotBlank(type)){
            type=null;
        }
        if(!StringUtils.isNotBlank(status)){
            status=null;
        }
        Date starttime=null;
        Date endtime=null;
        if(!StringUtils.isNotBlank(days)){
            days=null;
        }else{
            if("2".equals(days)){
                starttime=DateUtils.subDays(new Date(),1);
                Date endTime= DateUtils.addDays(starttime, 0);
                endtime= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
            }else{
                int day=Integer.parseInt(days);
                starttime=DateUtils.subDays(new Date(),day-1);
                Date endTime= DateUtils.addDays(starttime,day-1);
                endtime= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
            }
        }
        if(!StringUtils.isNotBlank(name)){
            name=null;
        }
        if(!StringUtils.isNotBlank(content)){
            content=null;
        }
        if(StringUtils.isNotBlank(starttime1)){
            int year=Integer.valueOf(starttime1.substring(0,4));
            int month=Integer.valueOf(starttime1.substring(5,7))-1;
            int day1=Integer.valueOf(starttime1.substring(8));
            starttime=DateUtils.turnToDateStart(DateUtils.buildDate(year,month,day1));
        }
        if(StringUtils.isNotBlank(endtime1)){
            
            int year=Integer.valueOf(endtime1.substring(0,4));
            int month=Integer.valueOf(endtime1.substring(5,7))-1;
            int day1=Integer.valueOf(endtime1.substring(8));
            endtime=DateUtils.turnToDateEnd(DateUtils.buildDate(year,month,day1));
        }
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays=systemUserManagerService.queryCurrAllEbay(map);
        Map m = new HashMap();
        m.put("account",account);
        m.put("type",type);
        m.put("status",status);
        m.put("starttime",starttime);
        m.put("endtime",endtime);
        m.put("name",name);
        m.put("content",content);
        List<UserCasesQuery> list=new ArrayList<>();
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        if(ebays!=null&&ebays.size()>0){
            m.put("ebays",ebays);
            list =iTradingGetUserCases.selectGetUserCases(m, page);
        }else{
            list=new ArrayList<>();
        }
        jsonBean.setList(list);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    /**获取list数据的ajax方法*/
   /* @RequestMapping("/sendMessage.do")
    @ResponseBody
    public void sendMessage(CommonParmVO2 commonParmVO,HttpServletRequest request) throws Exception {
        String number=request.getParameter("number");
        String carrier=request.getParameter("carrier");
        String sendmessage=request.getParameter("sendmessage");
        String transactionid=request.getParameter("transactionid");
        String status=request.getParameter("status");
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid);
        if((number==null&&carrier==null&&sendmessage==null)||(sendmessage!=null&&number!=null)||(sendmessage!=null&&carrier!=null)){
            AjaxSupport.sendFailText("fail","请选择其中一个进行提交");
            return;
        }
        if(StringUtils.isNotBlank(status)){
            Map<String,String> map=sendMessage1(orders.get(0),"Tracking status","Tracking number:"+number+",carrier:"+carrier+",status:"+status);
            if("false".equals(map.get("flag"))){
                AjaxSupport.sendFailText("fail",map.get("message"));
                return;
            }else{
                AjaxSupport.sendSuccessText("", map.get("message"));
                return;
            }
        }else if(sendmessage==null&&!StringUtils.isNotBlank(status)){
            AjaxSupport.sendFailText("fail","status is empty");
        }
        if(sendmessage!=null){
            Map<String,String> map=sendMessage1(orders.get(0),"纠纷消息",sendmessage);
            if("false".equals(map.get("flag"))){
                AjaxSupport.sendFailText("fail",map.get("message"));
            }else{
                AjaxSupport.sendSuccessText("", map.get("message"));
            }
        }
    }*/
    /**获取list数据的ajax方法*/
   /* @RequestMapping("/sendMessage2.do")
    @ResponseBody
    public void sendMessage2(CommonParmVO2 commonParmVO,HttpServletRequest request) throws Exception {
        String transactionid=request.getParameter("transactionid");
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid);
        Map<String,String> map=sendMessage1(orders.get(0),"Tracking status","I shipped the item without tracking information");
        if("false".equals(map.get("flag"))){
            AjaxSupport.sendFailText("fail",map.get("message"));
        }else{
            AjaxSupport.sendSuccessText("", map.get("message"));
        }
    }*/
    /*
     * 同步纠纷详情初始化
     */
    @RequestMapping("/synDetails.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView synDetails(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id1=request.getParameter("id");
        Long id= Long.valueOf(id1);
        TradingGetUserCases userCases=iTradingGetUserCases.selectUserCasesById(id);
        modelMap.put("userCases",userCases);
        return forword("usercases/synEBPCases",modelMap);
    }
    /*
    * 查看纠纷详情
    */
    @RequestMapping("/viewDetails.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewDetails(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String transactionid=request.getParameter("transactionid");
        String ebayId=request.getParameter("ebayId");
        List<TradingGetDispute> disputeList=iTradingGetDispute.selectGetDisputeByTransactionId(Long.valueOf(ebayId),transactionid);
        TradingGetDispute dispute=new TradingGetDispute();
        TradingOrderGetOrders order=new TradingOrderGetOrders();
        TradingOrderGetSellerTransactions sellerTransaction=new TradingOrderGetSellerTransactions();
        if(disputeList!=null&&disputeList.size()>0){
            dispute=disputeList.get(0);
        }
        String url="http://www.ebay.com/itm/"+dispute.getItemid();
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid,Long.valueOf(ebayId),dispute.getItemid());
        if(orders!=null&&orders.size()>0){
            order=orders.get(0);
        }
        List<TradingOrderGetSellerTransactions> sellerTransactionses=new ArrayList<>();
        if(order!=null&&order.getItemid()!=null){
            sellerTransactionses = iTradingOrderGetSellerTransactions.selectTradingOrderGetSellerTransactionsByTransactionId(order.getItemid(),transactionid);
        }
        if(sellerTransactionses!=null&&sellerTransactionses.size()>0){
            sellerTransaction=sellerTransactionses.get(0);
        }
        String pic="";
        if(order!=null){
            List<TradingOrderGetItem> items=iTradingOrderGetItem.selectOrderGetItemByItemId(order.getItemid());
            List<TradingOrderPictureDetails> detailses=iTradingOrderPictureDetails.selectOrderGetItemById(items.get(0).getPicturedetailsId());
            pic=detailses.get(0).getPictureurl();
        }
        modelMap.put("url",url);
        modelMap.put("order",order);
        modelMap.put("dispute",dispute);
        modelMap.put("transaction",sellerTransaction);
        modelMap.put("pic",pic);
        return forword("usercases/viewCases",modelMap);
    }
    /*
   * 处理纠纷
   */
  /*  @RequestMapping("/handleDispute.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView handleDispute(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String transactionid=request.getParameter("transactionid");
        List<TradingGetEBPCaseDetail> EBPlist=iTradingGetEBPCaseDetail.selectGetEBPCaseDetailByTransactionId(transactionid);
        List<TradingGetUserCases> casesList=iTradingGetUserCases.selectGetUserCasesByTransactionId(transactionid);
        TradingGetEBPCaseDetail ebpCaseDetail=new TradingGetEBPCaseDetail();
        TradingGetUserCases cases=new TradingGetUserCases();
        List<TradingCaseResponseHistory> responses=new ArrayList<TradingCaseResponseHistory>();

        if(EBPlist!=null&&EBPlist.size()>0){
            ebpCaseDetail=EBPlist.get(0);
            responses=iTradingCaseResponseHistory.selectCaseResponseHistoryByEBPId(ebpCaseDetail.getId());
        }
        if(casesList!=null&&casesList.size()>0){
            cases=casesList.get(0);
        }
        TradingOrderGetOrders order=new TradingOrderGetOrders();
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid);
        if(orders!=null&&orders.size()>0){
            order=orders.get(0);
        }
        modelMap.put("ebpCaseDetail",ebpCaseDetail);
        modelMap.put("cases",cases);
        modelMap.put("responses",responses);
        modelMap.put("order",order);
        return forword("usercases/handleDispute",modelMap);
    }*/
    //escalateToCustomer
    @RequestMapping("/escalateToCustomer.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView escalateToCustomer(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String ebayId=request.getParameter("ebayId");
        String transactionId=request.getParameter("transactionId");
        String comments=request.getParameter("textarea");
        String type=request.getParameter("type");
        if(StringUtils.isNotBlank(comments)){
            comments=HtmlUtils.htmlEscape(comments);
        }
        List<TradingGetUserCases> casesList=iTradingGetUserCases.selectGetUserCasesByTransactionId(transactionId,Long.valueOf(ebayId));
        if(casesList!=null&&casesList.size()>0){
            modelMap.put("cases",casesList.get(0));
            List<TradingGetEBPCaseDetail> details=iTradingGetEBPCaseDetail.selectGetEBPCaseDetailByTransactionId(casesList.get(0).getEbayId(),casesList.get(0).getTransactionid());
            if(details!=null&&details.size()>0){
                modelMap.put("EBPcase",details.get(0));
            }
            UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
            d.setSoaOperationName("escalateToCustomerSupport");
            Map map1=new HashMap();
            map1.put("needToken","1");
            List<UsercontrollerEbayAccountExtend> dList= systemUserManagerService.queryCurrAllEbay(map1);
            String token=null;
            for(UsercontrollerEbayAccountExtend list:dList){
                if(StringUtils.isNotBlank(ebayId)){
                    Long ebayid=Long.valueOf(ebayId);
                    if(ebayid==list.getId()){
                        token=list.getEbayToken();
                    }
                }
            }
            d.setSoaSecurityToken(token);
            d.setHeaderType("DisputeApiHeader");
            String caseId=casesList.get(0).getCaseid();
            String caseType=casesList.get(0).getCasetype();
            String xml = BindAccountAPI.escalateToCustomerSupport(token,caseId,caseType,type,comments);
            AddApiTask addApiTask = new AddApiTask();
            Map<String, String> resEbpMap = addApiTask.exec(d, xml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1");
            String r1 = resEbpMap.get("stat");
            String res = resEbpMap.get("message");
            if ("fail".equalsIgnoreCase(r1)) {
                modelMap.put("flag","false");
                modelMap.put("message","调用API无返回参数"+res);
                logger.error("escalateToCustomerSupport API出错第366"+res);
            }else{
                String ack = SamplePaseXml.getVFromXmlString(res, "ack");
                if ("Success".equalsIgnoreCase(ack)) {
                    List<TradingGetUserCases> userCaseses=iTradingGetUserCases.selectGetUserCasesByTransactionId(transactionId,Long.valueOf(ebayId));
                    for(TradingGetUserCases userCases:userCaseses){
                        userCases.setHandled(1);
                        iTradingGetUserCases.saveGetUserCases(userCases);
                    }
                    modelMap.put("flag","true");
                }else{
                    String errors =SamplePaseXml.getResponseCaseString(res,"message");
                    modelMap.put("flag","false");
                    modelMap.put("message",errors);
                    logger.error("escalateToCustomerSupport API出错第375"+errors);
                }
            }
        }else{
            modelMap.put("flag","false");
            modelMap.put("message","没找到对应的纠纷,请核实!");
        }
        modelMap.put("type",type);
        return forword("usercases/escalateToCustomer",modelMap);
    }

    /*
     * 查看纠纷详情
     */
    @RequestMapping("/viewCaseDetails.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewCaseDetails(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String transactionid=request.getParameter("transactionid");
        String ebayId=request.getParameter("ebayId");
        List<TradingGetUserCases> cases=iTradingGetUserCases.selectGetUserCasesByTransactionId(transactionid,Long.valueOf(ebayId));
        if(cases!=null&&cases.size()>0){
            List<TradingGetEBPCaseDetail> ebpCaseDetails=iTradingGetEBPCaseDetail.selectGetEBPCaseDetailByTransactionId(cases.get(0).getEbayId(),transactionid);
            List<TradingCaseResponseHistory> histories=new ArrayList<>();
            if(ebpCaseDetails!=null&&ebpCaseDetails.size()>0){
                List<TradingCaseResponseHistory> histories1=iTradingCaseResponseHistory.selectCaseResponseHistoryByEBPId(ebpCaseDetails.get(0).getId());
                if(histories1!=null&&histories1.size()>0){
                    Object[] histories2=histories1.toArray();
                    for(int i=0;i<histories2.length;i++){
                        for(int j=i+1;j<histories2.length;j++){
                            TradingCaseResponseHistory hi= (TradingCaseResponseHistory) histories2[i];
                            TradingCaseResponseHistory hj= (TradingCaseResponseHistory) histories2[j];
                            if(hj.getCreationdate().after(hi.getCreationdate())){
                                histories2[i]=hj;
                                histories2[j]=hi;
                            }
                        }
                    }
                    for(int i=0;i<histories2.length;i++){
                        TradingCaseResponseHistory hi=(TradingCaseResponseHistory) histories2[i];
                        if("SELLER".equals(hi.getAuthorrole())&&hi.getActivity().contains("refunded")&&hi.getActivity().contains("callback")){
                            modelMap.put("refundFlag","true");
                        }
                        histories.add(hi);

                    }
                }
            }
            modelMap.put("histories",histories);
            modelMap.put("cases",cases.get(0));
            if(ebpCaseDetails!=null&&ebpCaseDetails.size()>0){
                modelMap.put("ebpdetail",ebpCaseDetails.get(0));
            }
        }
        return forword("usercases/viewCaseDetails",modelMap);
    }
    /*
  * 响应纠纷
  */
    @RequestMapping("/responseDispute.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView responseDispute(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String transactionid=request.getParameter("transactionid");
        String ebayId=request.getParameter("ebayId");
        String itemId = request.getParameter("itemId");
        TradingOrderGetOrders order=new TradingOrderGetOrders();
        TradingGetUserCases cases=new TradingGetUserCases();
        List<TradingOrderGetItem> items=new ArrayList<TradingOrderGetItem>();
        List<String> pics=new ArrayList<String>();
       /* List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid,sellerid);*/
        //测试------
        List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid,Long.valueOf(ebayId),itemId);
        //-----
        List<TradingGetUserCases> casesList=iTradingGetUserCases.selectGetUserCasesByTransactionId(transactionid,Long.valueOf(ebayId));
        String reason="";
        String content="";
        String information="";
        if(casesList!=null&&casesList.size()>0){
            cases=casesList.get(0);
            String caseType=cases.getCasetype();
            if(caseType.contains("EBP")){
                List<TradingGetEBPCaseDetail> details=iTradingGetEBPCaseDetail.selectGetEBPCaseDetailByTransactionId(cases.getEbayId(),cases.getTransactionid());
                if(details!=null&&details.size()>0){
                    reason=details.get(0).getOpenreason();
                    content=details.get(0).getAppealdecision();
                    information=details.get(0).getInitialbuyerexpectationdetail();
                    List<TradingCaseResponseHistory> histories= iTradingCaseResponseHistory.selectCaseResponseHistoryByEBPId(details.get(0).getId());
                    if(histories!=null&&histories.size()>0){
                        /*for(TradingCaseResponseHistory history:histories){
                            Date casesDate=DateUtils.turnToDateStart(cases.getCreationdate());
                            Date historyDate=DateUtils.turnToDateStart(history.getCreationdate());
                            if("BUYER".equals(history.getAuthorrole())&&casesDate.equals(historyDate)){
                                modelMap.put("createReason",history.getNote());
                            }
                        }*/
                        modelMap.put("createReason", histories.get(0).getNote());
                    }
                }
            }else{
                List<TradingGetDispute> disputes=iTradingGetDispute.selectGetDisputeByTransactionId(cases.getEbayId(),cases.getTransactionid());
                if(disputes!=null&&disputes.size()>0){
                    reason=disputes.get(0).getDisputereason();
                    content=disputes.get(0).getDisputereason();
                }
            }
        }
        if(orders!=null&&orders.size()>0){
            order=orders.get(0);
            List<TradingOrderGetOrders> lists=iTradingOrderGetOrders.selectOrderGetOrdersByOrderId(order.getOrderid());
            for(TradingOrderGetOrders list:lists){
                List<TradingOrderGetItem> item= iTradingOrderGetItem.selectOrderGetItemByItemId(list.getItemid());
                if(item!=null&&item.size()>0){
                    List<TradingOrderPictureDetails> pictureDetailses=iTradingOrderPictureDetails.selectOrderGetItemById(item.get(0).getPicturedetailsId());
                    items.add(item.get(0));
                    String pic="";
                    if(pictureDetailses!=null&&pictureDetailses.size()>0){
                        pic=pictureDetailses.get(0).getPictureurl();
                    }
                    pics.add(pic);
                }
            }
            if(order.getTrackstatus()!=null/*&&"4".equals(order.getTrackstatus())*/){
                modelMap.put("receiveFlag","true");
            }else{
                modelMap.put("receiveFlag","");
            }
        }
        //查询货币信息
        if(!ObjectUtils.isLogicalNull(itemId)){
            List<TradingOrderGetItem> liorItem = this.iTradingOrderGetItem.selectOrderGetItemByItemId(itemId);
            if(liorItem!=null&&liorItem.size()>0){
                modelMap.put("currency",liorItem.get(0).getCurrency());
            }else{
                TradingListingData listingData= this.iTradingListingData.selectByItemid(itemId);
                if(listingData!=null) {
                    String site = listingData.getSite();
                    Map m = new HashMap();
                    m.put("type", "site");
                    m.put("value", site);
                    List<TradingDataDictionary> litdds = this.iTradingDataDictionary.selectDictionaryByMap(m);
                    if (litdds != null && litdds.size() > 0) {
                        modelMap.put("currency", litdds.get(0).getValue1());
                    } else {
                        modelMap.put("currency", "USD");
                    }
                }else{
                    modelMap.put("currency", "USD");
                }
            }
        }

        List<TradingMessageTemplate> ts=new ArrayList<TradingMessageTemplate>();
        List<TradingMessageTemplate> templates=iTradingMessageTemplate.selectMessageTemplatebType("caseType");
        for(TradingMessageTemplate template:templates){
            if(template.getStatus()==1){
                ts.add(template);
            }
        }
        String reasonFlag="";
        String contentFlag="";
        if(cases.getCasetype().contains("INR")){
            reasonFlag="true";
        }
        if(cases.getCasetype().contains("SNAD")){
            contentFlag="true";
        }
        if(cases.getCreationdate()!=null){
            String creationtime1=cases.getCreationdate().toString();
            String creationtime=creationtime1.substring(4,10);
            modelMap.put("creationtime",creationtime);
        }
        if(cases.getSolvecatetime()!=null){
            String solvecatetime1=cases.getSolvecatetime().toString();
            String solvecatetime=solvecatetime1.substring(4,10);
            modelMap.put("solvecatetime",solvecatetime);
        }
        if(cases.getRespondbydate()!=null){
            Date respondbydate=cases.getRespondbydate();
            Date respondbydate1=org.apache.commons.lang.time.DateUtils.addHours(respondbydate,-8);
            Date respondbydate2=org.apache.commons.lang.time.DateUtils.addSeconds(respondbydate1,-1);
            cases.setRespondbydate(respondbydate2);
        }
        modelMap.put("reasonFlag",reasonFlag);
        modelMap.put("contentFlag",contentFlag);
        modelMap.put("order",order);
        modelMap.put("cases",cases);
        String caseTitle=cases.getItemtitle();
        if(StringUtils.isNotBlank(caseTitle)){
            caseTitle=StringEscapeUtils.escapeXml(caseTitle);
            modelMap.put("caseTitle",caseTitle);
        }

        modelMap.put("transactionid",transactionid);
        modelMap.put("items",items);
        modelMap.put("pics",pics);
        modelMap.put("reason",reason);
        modelMap.put("content",content);
        modelMap.put("information",information);
        modelMap.put("templates",ts);
        modelMap.put("date",new Date());
        return forword("usercases/responseDispute",modelMap);
    }
    /*
    *同步纠纷详情
    */
   /* @RequestMapping("/apiEBPCasessRequest.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void apiEBPCasessRequest(CommonParmVO2 commonParmVO,HttpServletRequest request) throws Exception {
        String sellerId=request.getParameter("sellerId");
        String caseId=request.getParameter("caseId");
        String caseType=request.getParameter("caseType");
        List<UsercontrollerEbayAccountExtend> ebayList=userInfoService.getEbayAccountForCurrUser();
        String token="";
        for(UsercontrollerEbayAccountExtend ebay:ebayList){
            if(sellerId.equals(ebay.getEbayName())){
                token=userInfoService.getTokenByEbayID(ebay.getId());
            }
        }
        token="AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C";
        UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        AddApiTask addApiTask = new AddApiTask();
        if(caseType.contains("EBP")){
           *//* d.setSoaGlobalId("EBAY-US");
            d.setSoaServiceVersion("1.0.0");*//*
           *//* d.setSoaServiceName("ResolutionCaseManagementService");*//*
            d.setSoaOperationName("getEBPCaseDetail");
            d.setSoaSecurityToken(token);
           *//* d.setSoaRequestDataFormat("XML");*//*
            d.setHeaderType("DisputeApiHeader");
*//*            d.setSoaResponseDataformat("XML");*//*
            Map ebpMap=new HashMap();
            ebpMap.put("token",token);
            ebpMap.put("caseId", caseId);
            ebpMap.put("caseType",caseType);
            String ebpXml = BindAccountAPI.getEBPCase(ebpMap);
            //---修改前---
           *//* Map<String, String> resEbpMap = addApiTask.exec(d, ebpXml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1");
            String ebpR1 = resEbpMap.get("stat");
            String ebpRes = resEbpMap.get("message");

            if ("fail".equalsIgnoreCase(ebpR1)) {
                AjaxSupport.sendFailText("fail", ebpRes);
                return;
            }
            String ebpAck = SamplePaseXml.getVFromXmlString(ebpRes, "ack");
            if ("Success".equalsIgnoreCase(ebpAck)) {
                Map<String,Object> ebpmap= GetEBPCaseDetailAPI.parseXMLAndSave(ebpRes);
                TradingGetEBPCaseDetail caseDetail= (TradingGetEBPCaseDetail) ebpmap.get("ebpCaseDetail");
                List<TradingCaseResponseHistory> historyList= (List<TradingCaseResponseHistory>) ebpmap.get("historyList");
                List<TradingCasePaymentDetail> paymentDetailList= (List<TradingCasePaymentDetail>) ebpmap.get("paymentDetailList");
                AjaxSupport.sendSuccessText("success", "同步成功！");
            }else{
                String errors = SamplePaseXml.getVFromXmlString(ebpRes, "Errors");
                logger.error("获取EBP纠纷失败!" + errors);
                AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
            }*//*
            TaskMessageVO taskMessageVO=new TaskMessageVO();
            taskMessageVO.setMessageContext("EBP纠纷");
            taskMessageVO.setMessageTitle("同步EBP纠纷");
            taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_USER_CASE_EBP_TYPE);
            taskMessageVO.setBeanNameType(SiteMessageStatic.SYNCHRONIZE_USER_CASE_EBP_BEAN);
            taskMessageVO.setMessageFrom("system");
            SessionVO sessionVO= SessionCacheSupport.getSessionVO();
            taskMessageVO.setMessageTo(sessionVO.getId());
            addApiTask.execDelayReturn(d, ebpXml,"https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1", taskMessageVO);
            AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
            //---修改后---
        }else{
           *//* UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
            d.setApiSiteid("0");
            d.setApiCallName(APINameStatic.GetOrders);
            request.getSession().setAttribute("dveId", d);*//*
            d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
            d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
            d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
            d.setApiCompatibilityLevel("883");
            d.setApiSiteid("0");
            d.setHeaderType("");
            d.setApiCallName(APINameStatic.GetDispute);
            String xml = BindAccountAPI.getGetDispute(token, caseId);
            //---修改前---
            *//*Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");
            String r1 = resMap.get("stat");
            String res = resMap.get("message");
            if ("fail".equalsIgnoreCase(r1)) {
                AjaxSupport.sendFailText("fail", res);
                return;
            }
            String Ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equalsIgnoreCase(Ack)) {
                Map<String,Object> disputeMap= GetDisputeAPI.parseXMLAndSave(res);
                TradingGetDispute dispute= (TradingGetDispute) disputeMap.get("dispute");
                List<TradingGetDispute> disputeList=iTradingGetDispute.selectGetDisputeByTransactionId(dispute.getTransactionid());
                if(disputeList!=null&&disputeList.size()>0){
                    dispute.setId(disputeList.get(0).getId());
                }
                iTradingGetDispute.saveGetDispute(dispute);
                List<TradingGetDisputeMessage> messageList= (List<TradingGetDisputeMessage>) disputeMap.get("disputeMessage");
                List<TradingGetDisputeMessage> list=iTradingGetDisputeMessage.selectGetDisputeMessageByDisputeId(dispute.getId());
                if(list!=null&&list.size()>0){
                    for(TradingGetDisputeMessage message:list){
                        iTradingGetDisputeMessage.deleteGetDisputeMessage(message);
                    }
                }
                for(TradingGetDisputeMessage message:messageList){
                    message.setDisputeId(dispute.getId());
                    iTradingGetDisputeMessage.saveGetDisputeMessage(message);
                }
                AjaxSupport.sendSuccessText("success", "同步成功！");
            }else{
                String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                logger.error("获取一般纠纷失败!" + errors);
                AjaxSupport.sendFailText("fail", "获取必要的参数失败！请稍后重试");
            }*//*
            //---修改后---
            TaskMessageVO taskMessageVO=new TaskMessageVO();
            taskMessageVO.setMessageContext("一般纠纷");
            taskMessageVO.setMessageTitle("同步一般纠纷");
            taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_USER_CASE_DISPUTE_TYPE);
            taskMessageVO.setBeanNameType(SiteMessageStatic.SYNCHRONIZE_USER_CASE_DISPUTE_BEAN);
            taskMessageVO.setMessageFrom("system");
            SessionVO sessionVO= SessionCacheSupport.getSessionVO();
            taskMessageVO.setMessageTo(sessionVO.getId());
            addApiTask.execDelayReturn(d, xml,"https://api.ebay.com/ws/api.dll", taskMessageVO);
            AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
        }
    }*/
    /*
     *同步纠纷目录
     */
    @RequestMapping("/apiGetuserCasessRequest.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void apiGetuserCasessRequest(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String ebayIds=request.getParameter("ebayIds");
        List<Long> ebays=new ArrayList<Long>();
        if(StringUtils.isNotBlank(ebayIds)){
            String[] ebayIds1=ebayIds.split(",");
            for(String ebayId:ebayIds1){
                ebays.add(Long.valueOf(ebayId));
            }
        }else{
            AjaxSupport.sendFailText("fail","ebay账号不存在请核实!");
            return;
        }
        List<TaskGetUserCases> taskGetUserCaseses= iTaskGetUserCases.selectTaskGetUserCasesByFlagIsFalseOrderBysaveTime();
        for(Long ebayID:ebays){
            if(!ObjectUtils.isLogicalNull(taskGetUserCaseses)){
                Long ebayNameID0=taskGetUserCaseses.get(0).getEbayId();//检查第一个是不是需要跑的
                Integer flag=taskGetUserCaseses.get(0).getTokenflag();
                if (ebayNameID0!=ebayID){
                    for (TaskGetUserCases t:taskGetUserCaseses){
                        if (ebayID==t.getEbayId()){
                            t.setLastsyctime(new Date());
                            flag=flag-1;
                            t.setTokenflag(flag<0?0:flag);
                            iTaskGetUserCases.saveListTaskGetUserCases(t);
                        }
                    }
                }
            }else {
                //todo 没有列表的时候应该生成列表！
                Map map=new HashMap();
                List<UsercontrollerEbayAccountExtend> ebayAcounts=systemUserManagerService.queryCurrAllEbay(map);
                Date date=new Date();
                for(UsercontrollerEbayAccount ebayAcount:ebayAcounts){
                    Date date2=DateUtils.addDays(date,1);
                    Date date1=DateUtils.subDays(date2, 80);
                    Date end1= DateUtils.turnToDateEnd(date2);
                    Date start1= DateUtils.turnToDateStart(date1);
                    String start= DateUtils.DateToString(start1);
                    String end=DateUtils.DateToString(end1);
                    TaskGetUserCases TaskGetUserCases=new TaskGetUserCases();
                    TaskGetUserCases.setToken(ebayAcount.getEbayToken());
                    TaskGetUserCases.setTokenflag(0);
                    TaskGetUserCases.setFromtime(start);
                    TaskGetUserCases.setEndtime(end);
                    TaskGetUserCases.setSavetime(date);
                    TaskGetUserCases.setUserid(ebayAcount.getUserId());
                    TaskGetUserCases.setEbayname(ebayAcount.getEbayName());
                    TaskGetUserCases.setEbayId(ebayAcount.getId());
                    iTaskGetUserCases.saveListTaskGetUserCases(TaskGetUserCases);
                }
            }
        }
       /* String ebayId=request.getParameter("ebayId");
        Long ebay=Long.valueOf(ebayId);
        //UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
        UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
      //  d.setSoaGlobalId("EBAY-US");
        //d.setSoaServiceVersion("1.0.0");
        //d.setSoaServiceName("ResolutionCaseManagementService");*//*
        //d.setSoaSecurityToken("AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C");*//*
       //d.setSoaRequestDataFormat("XML");*//*
     //   d.setSoaResponseDataformat("XML");*//*
        d.setSoaOperationName("getUserCases");
        //没写死的
        String token=userInfoService.getTokenByEbayID(ebay);
        //写死了的
       // String token="AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C";*//*
       //   String token="AgAAAA**AQAAAA**aAAAAA**kRx8VA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AGloWiAZCCogSdj6x9nY+seQ**blACAA**AAMAAA**d0Px77QqgOj2GHC7XDNXkRKusIUT1y5uPdXz87hiC9ghsh75Q6hQb3BRbKwkJsFz3BlORq7L8lEiHsqBnFzd65yK1MJ/CQMsY165Q+4Rw664b0dP3vnPzjeN3cfKOkDwwoLqFGrMclvrrpntfSDBcO/r1QaC+CUB0GD6UiuhdyhBIPd1gb+z0KmYCTwpFENyHDzRtiTcT5qCt5eYfYzsve2e6O1c+NsTyBgJzUD1v78aIluxKhoC+huF9Uxscm2DU4mOr0JYONHJCs3dN18fKLp0Dc3hSvmPSIaxPmjcvlVfWuVPtw6KwXvxw8U8PGUdfACzb9ZIBiUEEhFHU6xv73egj2hkN/ZTJr7yu3l+qvDJFHLlgBMoprseFc0tmDi/hbRUILxuOy8TOpGri71DoQBzwuQxxrG5GMJ77NFLOLYxsH6/gpA/7+vFT1X5CUsIv+BYZyY7g3RLZWYem3Gqv9T+sVNC/DEhxmdO1Yx49rAwHcUw3aeXTrKpa1xCNkgHg4Feheu5V6Pu9lb5DQUC9YidqELrLEvos6yoiH31myqAmI72Gt4i7SBjwS8k5O+7xjxhDrKpg0IFwCdQk4PEByoBnud/dDNyCZkZdCqTkb36aqmgdnTANz9M7DtcQTH/Lf6h+Suj3RVSeFfDZcJJDax7Ie5qwte+oHJ6yTuBZ2dt4hMmKZIZwn26Ei+DUfCPhx6nEqcAOf6Sbxf8RxkWJ2pLcIvbifrditHIuyGjOf4yMoIHOcSp6FsVbmkMleBG";

        d.setSoaSecurityToken(token);
        d.setHeaderType("DisputeApiHeader");
        Map map=new HashMap();
    //   Date startTime2= DateUtils.subDays(new Date(), 30);
      //  Date endTime= DateUtils.addDays(startTime2, 30);*//*
        Date startTime2= DateUtils.subDays(new Date(), 6);
        Date endTime= DateUtils.addDays(startTime2,6);
        Date end1= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
        String start= DateUtils.DateToString(startTime2);
        String end=DateUtils.DateToString(end1);
        map.put("fromTime", start);
        map.put("toTime", end);
        map.put("page","1");
        String xml = BindAccountAPI.getUserCases(map);
        AddApiTask addApiTask = new AddApiTask();
        TaskMessageVO taskMessageVO=new TaskMessageVO();
        taskMessageVO.setMessageContext("纠纷");
        taskMessageVO.setMessageTitle("同步纠纷");
        taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_USER_CASE_TYPE);
        taskMessageVO.setBeanNameType(SiteMessageStatic.SYNCHRONIZE_USER_CASE_BEAN);
        taskMessageVO.setMessageFrom("system");
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        Map map2=new HashMap();
        map2.put("token",token);
        map2.put("d",d);
        taskMessageVO.setObjClass(map2);
        taskMessageVO.setMessageTo(sessionVO.getId());
        addApiTask.execDelayReturn(d, xml,"https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1?REST-PAYLOAD", taskMessageVO);
        */
        AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
    }
    /*private Map<String,String> sendMessage1(TradingOrderGetOrders order,String subject,String body) throws Exception {
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(null);//开发者帐号id
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.AddMemberMessageAAQToPartner);
        Map map=new HashMap();
        Map<String,String> returnMap=new HashMap<String, String>();
        String ebayName=order.getSelleruserid();
        List<UsercontrollerEbayAccountExtend> dList= userInfoService.getEbayAccountForCurrUser();
        String token=null;
        for(UsercontrollerEbayAccountExtend list:dList){
            if(StringUtils.isNotBlank(ebayName)&&ebayName.equals(list.getEbayName())){
                token=list.getEbayToken();
            }
        }
        map.put("token", token);
        map.put("subject",subject);
        map.put("body",body);
        map.put("itemid",order.getItemid());
        map.put("buyeruserid",order.getBuyeruserid());
        String xml = BindAccountAPI.getAddMemberMessageAAQToPartner(map);//获取接受消息
        AddApiTask addApiTask = new AddApiTask();
          *//*  Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");*//*
        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if ("fail".equalsIgnoreCase(r1)) {
            AjaxSupport.sendFailText("fail", res);
            returnMap.put("flag","false");
            returnMap.put("message",res);
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        if ("Success".equalsIgnoreCase(ack)) {
            TradingOrderAddMemberMessageAAQToPartner message1=new TradingOrderAddMemberMessageAAQToPartner();
            message1.setBody(body);
            message1.setItemid(order.getItemid());
            message1.setRecipientid(order.getBuyeruserid());
            message1.setSubject(subject);
            message1.setSender(order.getSelleruserid());
            message1.setMessagetype(1);
            message1.setTransactionid(order.getTransactionid());
            iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(message1);
            returnMap.put("flag","true");
            returnMap.put("message", "发送成功");
        }else{
            returnMap.put("flag","false");
            returnMap.put("message","获取必要的参数失败！请稍后重试");
        }
        return returnMap;
    }*/
    //提供运输信息provideShippingInfo
    @RequestMapping("/sendTrackNum.do")
    @ResponseBody
    public void sendTrackNum(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String trackingNum=request.getParameter("trackingNum");
        String carrier=request.getParameter("carrier");
        String message=request.getParameter("textarea");
        String transactionId=request.getParameter("transactionId");
        String ebayIds=request.getParameter("ebayId");
        Asserts.assertTrue(StringUtils.isNotBlank(ebayIds),"ebayId不能为空！");
        Long ebayId=Long.valueOf(ebayIds);

        if(StringUtils.isNotBlank(message)){
            message=HtmlUtils.htmlEscape(message);
        }
        UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        d.setSoaOperationName("provideShippingInfo");
        /*String token="AgAAAA**AQAAAA**aAAAAA**jek4VA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlIWnC5iEpAidj6x9nY+seQ**tSsCAA**AAMAAA**y8BaJPw6GUdbbbco8zXEwRR4Ttr9sLd78jL0FyYa0yonvk5hz1RY6DtKkaDtn9NuzluKeFZoqsNbujZP48S4QZhHVa5Dp0bDGqBdKaosolzsrPDm8qozoxbsTiWY8X/M5xev/YU2zJ42/JRGDlEdnQhwCASG1BcSo+DqXuG3asbj0INJr4/HsArf8cCYsPQCtUDkq5QJY6Rvil+Kla/dGhViTQ3gt7a4t3KjxKH+/jlhDU/6sUEKlvb2nY1gCmX8S9pU48c+4Vy6G6NpfcGUcIG/TXFWBTqU0R+v+/6DOIfDW8s90rrLSVMGFqnRxA2sexdEmVhyF5csBmv9+TVfjdyEZK5UgvDqWJHesuDMFTr0KIc8EtdnTQaE3YeZch15DdoEbqcyyBQBZHidBPdDHz/DkpTg7iq1953yKodm2y0mW6aaYAfc5beW+PoqMW8C3WwGJmWZqh3dBi+QEKznEJ9SRg43Bc3q2344JFY7YpIEfJDaQ36BHRcIZxLew8v7RIGL5YYO1BBdTolVV9/eMCQDsUB0mUeMYjxnH5w0K/6CDmJ9WNMQTblNol0x3vhJbil1L/CMP9KGEHj5Yqx0003MLL9Yod7nL89Zpy+a8I/E5byxFt21KZTGE90Ot0LyLpRXsotDwIm5+ZdvATsU6mGADX4tk970CpCeM487v9fn1opouaCBvknCINqXoSeGXLQ7uZFpeqkWts1lIWh9vEuuiuZa4vNoL7aCr+93LTFnsO6AsZp7dmboQcI96I/o";
        */
        Map map1=new HashMap();
        map1.put("needToken","1");
        List<UsercontrollerEbayAccountExtend> dList= systemUserManagerService.queryCurrAllEbay(map1);
        String token=null;
        for(UsercontrollerEbayAccountExtend list:dList){
                if(ebayId==list.getId()){
                    token=list.getEbayToken();
            }
        }
        d.setSoaSecurityToken(token);
        d.setHeaderType("DisputeApiHeader");
        List<TradingGetUserCases> caseses= iTradingGetUserCases.selectGetUserCasesByTransactionId(transactionId,ebayId);
        List<TradingOrderGetOrders> orderses=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionId,Long.valueOf(ebayId),caseses.get(0).getItemid());
        Date shippedTime=null;
        if(orderses!=null&&orderses.size()>0){
            shippedTime=orderses.get(0).getShippedtime();
        }else{
            AjaxSupport.sendFailText("fail","该商品没发货");
            return;
        }
        if(caseses!=null&&caseses.size()>0){
            String caseId=caseses.get(0).getCaseid();
            String caseType=caseses.get(0).getCasetype();
            String shippedTime1=DateUtils.DateToString(shippedTime);
            String xml = BindAccountAPI.ProvideShippingInfo(token,caseId,caseType,carrier,shippedTime1,message);
            AddApiTask addApiTask = new AddApiTask();
            Map<String, String> resEbpMap = addApiTask.exec(d, xml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1");
            String r1 = resEbpMap.get("stat");
            String res = resEbpMap.get("message");
            if ("fail".equalsIgnoreCase(r1)) {
                AjaxSupport.sendFailText("fail", res);
                return;
            }
            String ack = SamplePaseXml.getVFromXmlString(res, "ack");
            if ("Success".equalsIgnoreCase(ack)) {
                TradingGetUserCases cases=caseses.get(0);
                cases.setHandled(1);
                iTradingGetUserCases.saveGetUserCases(cases);
                AjaxSupport.sendSuccessText("message", "提供运输信息成功!");
            }else{
                String errors =SamplePaseXml.getResponseCaseString(res,"message");
                logger.error("提供运输信息失败!" + errors);
                AjaxSupport.sendFailText("fail", "提供运输信息失败:"+errors);
            }
        }else{
            AjaxSupport.sendFailText("fail","纠纷不存在");
        }
    }
    //提供跟踪信息provideTrackingInfo
    @RequestMapping("/sendWithoutNum.do")
    @ResponseBody
    public void sendWithoutNum(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String trackingNum=request.getParameter("trackingNum");
        String carrier=request.getParameter("carrier");
        String message=request.getParameter("textarea");
        //String message2=request.getParameter("textarea2");
        String transactionId=request.getParameter("transactionId");
        String ebayId=request.getParameter("ebayId");
        if(StringUtils.isNotBlank(message)){
            message=HtmlUtils.htmlEscape(message);
        }
        UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        d.setSoaOperationName("provideTrackingInfo");
       /* String token="AgAAAA**AQAAAA**aAAAAA**CLSRUQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlYCjDJGCqA+dj6x9nY+seQ**FdYBAA**AAMAAA**w2sMbwlQ7TBHWxj9EsVedHQRI3+lonY9MDfiyayQbnFkjEanjL/yMCpS/D2B9xHRzRx+ppxWZkRPgeAKJvNotPLLrVTuEzOl5M7pi6Tw8+pzcmIEsOh7HQO78JlyFlvLc/ruE6/hG0E/HO1UX76YBwxp00N9f1NNUpo5u36D/TYsx5O2jXFTKkCOHwz6RW9vtN6TU39aLm+JQme2+NfFFXnbX8MHzoUiX7Sty0R88ZpX5wLp8ZdgXCEc5zZDQziYB1MSXF9hsmby5wKbxFF+OvW/zKADThk1gprgAgnEOucyoao+cUMHopLlYgMbjnLzdCXP5F9z+fkYTnKF6AEl5eHBpcKQGbPzswnKebRoBVw+bI2I1C/iq+PvBUyndFAexjrvlDQbEKr6qb6AWRVTTfkW2ce6a0ixRuCTq35zEpWpfAqkSKo+X23d/Q4V8R30rDXotOWDZL6o408cMO+UQ17uVA2arA1JNkYfc/AZ0T0z7ze5o/yp93jJPlDgi05Ut4fpCAMZw3X85GxrTlbEtawWgoyUbmMuv4f6QHZLZAerOaJA8DRJkzkzjJJ025bp1HvAECOc4ggdv0cofu4q96shssgNYYZJUPM+q4+0fnGK0pxQTNY9SV6vSaVCVoTZJo6vefW7OiHX2/eLoPKFuUfsKXXEv9OY71gD1xzYg/rpCMAqCTq1dKqqyT1R5fxANnoRX7vwkq+7jkCj2fAfKTnHi9mSuBFsilKLmnsqqWy3IGShMgdxiQwBEk6IWi9C";
        */
        Map map1=new HashMap();
        map1.put("needToken","1");
        List<UsercontrollerEbayAccountExtend> dList= systemUserManagerService.queryCurrAllEbay(map1);
        String token=null;
        for(UsercontrollerEbayAccountExtend list:dList){
            if(StringUtils.isNotBlank(ebayId)){
                Long ebayid=Long.valueOf(ebayId);
                if(ebayid==list.getId()){
                    token=list.getEbayToken();
                }
            }
        }
        d.setSoaSecurityToken(token);
        d.setHeaderType("DisputeApiHeader");
        List<TradingGetUserCases> caseses= iTradingGetUserCases.selectGetUserCasesByTransactionId(transactionId,Long.valueOf(ebayId));
        List<TradingOrderGetOrders> orderses=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionId,Long.valueOf(ebayId),caseses.get(0).getItemid());
        if(!StringUtils.isNotBlank(trackingNum)){
            AjaxSupport.sendFailText("fail", "无跟踪号,请填写!");
            return;
        }
        if(!StringUtils.isNotBlank(carrier)){
            AjaxSupport.sendFailText("fail", "无货运商,请填写!");
            return;
        }
        if(orderses==null||orderses.size()==0) {
            AjaxSupport.sendFailText("fail", "该商品没发货");
            return;
        }
        if(caseses!=null&&caseses.size()>0){
            String caseId=caseses.get(0).getCaseid();
            String caseType=caseses.get(0).getCasetype();
            String xml=BindAccountAPI.provideTrackingInfo(token,caseId,caseType,trackingNum,carrier,message);
            AddApiTask addApiTask = new AddApiTask();
            Map<String, String> resEbpMap = addApiTask.exec(d, xml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1");
            String r1 = resEbpMap.get("stat");
            String res = resEbpMap.get("message");
            if ("fail".equalsIgnoreCase(r1)) {
                AjaxSupport.sendFailText("fail", res);
                return;
            }
            String ack = SamplePaseXml.getVFromXmlString(res, "ack");
            if ("Success".equalsIgnoreCase(ack)) {
                TradingGetUserCases cases=caseses.get(0);
                cases.setHandled(1);
                iTradingGetUserCases.saveGetUserCases(cases);
                AjaxSupport.sendSuccessText("message", "提供跟踪信息成功!");
            }else{
                String errors = SamplePaseXml.getResponseCaseString(res,"message");
                logger.error("提供跟踪信息失败!" + errors);
                AjaxSupport.sendFailText("fail", "提供跟踪信息失败:"+errors);
            }
        }else{
            AjaxSupport.sendFailText("fail","纠纷不存在");
        }
    }
    @RequestMapping("/sendRefund.do")
    @ResponseBody
    public void sendRefund(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String fullRefund=request.getParameter("fullRefund");
        String partialRefund=request.getParameter("partialRefund");
        String amout=request.getParameter("amout");
        String transactionId=request.getParameter("transactionId");
        String ebayId=request.getParameter("ebayId");
        String textarea=request.getParameter("textarea");
        if(StringUtils.isNotBlank(textarea)){
            textarea=textarea.trim();
            textarea= HtmlUtils.htmlEscape(textarea);
        }
        if(StringUtils.isNotBlank(fullRefund)&&StringUtils.isNotBlank(partialRefund)){
            AjaxSupport.sendFailText("fail","请选择一个");
            return;
        }
        UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        Map map1=new HashMap();
        map1.put("needToken","1");
        List<UsercontrollerEbayAccountExtend> dList= systemUserManagerService.queryCurrAllEbay(map1);
        String token=null;
        for(UsercontrollerEbayAccountExtend list:dList){
            if(StringUtils.isNotBlank(ebayId)){
                Long ebayid=Long.valueOf(ebayId);
                if(ebayid==list.getId()){
                    token=list.getEbayToken();
                }
            }
        }
       /* token="AgAAAA**AQAAAA**aAAAAA**kRx8VA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AGloWiAZCCogSdj6x9nY+seQ**blACAA**AAMAAA**d0Px77QqgOj2GHC7XDNXkRKusIUT1y5uPdXz87hiC9ghsh75Q6hQb3BRbKwkJsFz3BlORq7L8lEiHsqBnFzd65yK1MJ/CQMsY165Q+4Rw664b0dP3vnPzjeN3cfKOkDwwoLqFGrMclvrrpntfSDBcO/r1QaC+CUB0GD6UiuhdyhBIPd1gb+z0KmYCTwpFENyHDzRtiTcT5qCt5eYfYzsve2e6O1c+NsTyBgJzUD1v78aIluxKhoC+huF9Uxscm2DU4mOr0JYONHJCs3dN18fKLp0Dc3hSvmPSIaxPmjcvlVfWuVPtw6KwXvxw8U8PGUdfACzb9ZIBiUEEhFHU6xv73egj2hkN/ZTJr7yu3l+qvDJFHLlgBMoprseFc0tmDi/hbRUILxuOy8TOpGri71DoQBzwuQxxrG5GMJ77NFLOLYxsH6/gpA/7+vFT1X5CUsIv+BYZyY7g3RLZWYem3Gqv9T+sVNC/DEhxmdO1Yx49rAwHcUw3aeXTrKpa1xCNkgHg4Feheu5V6Pu9lb5DQUC9YidqELrLEvos6yoiH31myqAmI72Gt4i7SBjwS8k5O+7xjxhDrKpg0IFwCdQk4PEByoBnud/dDNyCZkZdCqTkb36aqmgdnTANz9M7DtcQTH/Lf6h+Suj3RVSeFfDZcJJDax7Ie5qwte+oHJ6yTuBZ2dt4hMmKZIZwn26Ei+DUfCPhx6nEqcAOf6Sbxf8RxkWJ2pLcIvbifrditHIuyGjOf4yMoIHOcSp6FsVbmkMleBG";
        */d.setSoaSecurityToken(token);
        d.setHeaderType("DisputeApiHeader");
        List<TradingGetUserCases> caseses= iTradingGetUserCases.selectGetUserCasesByTransactionId(transactionId,Long.valueOf(ebayId));
        if(caseses!=null&&caseses.size()>0){
            String caseId=caseses.get(0).getCaseid();
            String caseType=caseses.get(0).getCasetype();
            String xml="";
            //issueFullRefund退全款
            if(fullRefund!=null){
                d.setSoaOperationName("issueFullRefund");
                xml=BindAccountAPI.issueFullRefund(token,caseId,caseType,textarea);
            }
            //issuePartialRefund退半款
            if(partialRefund!=null){
                d.setSoaOperationName("issuePartialRefund");
                xml=BindAccountAPI.issuePartialRefund(token,caseId,caseType,textarea,amout);
            }
            AddApiTask addApiTask = new AddApiTask();
            Map<String, String> resEbpMap = addApiTask.exec(d, xml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1");
            String r1 = resEbpMap.get("stat");
            String res = resEbpMap.get("message");
            if ("fail".equalsIgnoreCase(r1)) {
                AjaxSupport.sendFailText("fail", res);
                return;
            }
            String ack = SamplePaseXml.getVFromXmlString(res, "ack");
            if ("Success".equalsIgnoreCase(ack)) {
                TradingGetUserCases cases=caseses.get(0);
                cases.setCasecontent("solve a refund");
                cases.setComments(textarea);
                cases.setSolvecatetime(new Date());
                cases.setHandled(1);
                iTradingGetUserCases.saveGetUserCases(cases);
                AjaxSupport.sendSuccessText("message", "退款成功!");
            }else{
                String errors = SamplePaseXml.getResponseCaseString(res,"message");
                logger.error("退款失败!" + errors);
                AjaxSupport.sendFailText("fail", "退款失败:"+errors);
            }
        }else{
            AjaxSupport.sendFailText("fail","纠纷不存在");
        }
        AjaxSupport.sendSuccessText("message", "sendRefund");
    }
    //offerOtherSolution 发送消息
    @RequestMapping("/sendMessageForm.do")
    @ResponseBody
    public void sendMessageForm(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String transactionId=request.getParameter("transactionId");
        String textarea=request.getParameter("textarea");
        String ebayId=request.getParameter("ebayId");
        if(StringUtils.isNotBlank(textarea)){
            textarea= StringEscapeUtils.escapeXml(textarea);
            //textarea=HtmlUtils.htmlEscape(textarea);
        }
        UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        Map map1=new HashMap();
        map1.put("needToken","1");
        List<UsercontrollerEbayAccountExtend> dList= systemUserManagerService.queryCurrAllEbay(map1);
        String token=null;
        for(UsercontrollerEbayAccountExtend list:dList){
            if(StringUtils.isNotBlank(ebayId)){
                Long ebayid=Long.valueOf(ebayId);
                if(ebayid==list.getId()){
                    token=list.getEbayToken();
                }
            }
        }
        /*token="AgAAAA**AQAAAA**aAAAAA**5+zOVA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlIWnC5iEpAidj6x9nY+seQ**blACAA**AAMAAA**4YY9ZyYrV6rBr5Y51YDou86lPUDHdTfLXryrxIjp4NQwEAutmYswQ2TaLTKOw5SKphrKK0V5nsFlhlFwaYr121t24tsZMqkADpz3XwFSu3/ygi7aXwjvwdjK6yi0Npsweyh+sDRZk+r7l8MBjT4Wfqd6xrrDTNFamFPJbvRUcLj/WWL8elKbB2ibfMbN3cf5dt4RvL0+V+I8E+0fK7XtzOE51K91r21NwPcW2+2qjnmxmiDGt6QHrJRAUoE/L1hTAMlecnw6qjRecKBn1AbsgA9FFi63C28A46ZEAsIjv+dg0E1iDcjuoRvz+bsyRxUGIPD2j+8sLjUygqNopSuhyljqQA2JahIV18abDyE0vtUdGpn82a225kQ153+qeK5VrPbDiiHVfLedksnrtnO44ySOjsfa8PM7CAgiav8WQ/KtYtWtaGubW+z2r7zFAimku2k6temuD9FOlPOXM1Mx1UgX+bVYxs5KnMXBcm7ybaxmFB0xi2wyeNoz6Dbta1fmiPmhsAAbdDEtY3chPEEjsQRzdeGLTsbtiJjROTyEY4enD5UfMOXCXUIb6iBm1NNe/PRRSdxum4Pk9eosky37Bdzx59bXWDgvnkKjx6pwRGWgpLcRVAYxjkvkmTHcwoN0WBgRqZ7JPnuaWQDLkzjzS1/8zdQV0dyj7I4sI/8+kjjdPNa004iTVsjt2wdrzgLf1gJMc+kpCD97c1dUzbAe9Kp8/hL1W1VT5RcWk9JsuPK5IHMGlGKGBd2UffBP6V7e";
        */
        d.setSoaSecurityToken(token);
        d.setSoaOperationName("offerOtherSolution");
        d.setHeaderType("DisputeApiHeader");
        List<TradingGetUserCases> caseses= iTradingGetUserCases.selectGetUserCasesByTransactionId(transactionId,Long.valueOf(ebayId));
        if(caseses!=null&&caseses.size()>0) {
            String caseId = caseses.get(0).getCaseid();
            String caseType = caseses.get(0).getCasetype();
            String xml=BindAccountAPI.offerOtherSolution(token,caseId,caseType,textarea);
            AddApiTask addApiTask = new AddApiTask();
            Map<String, String> resEbpMap = addApiTask.exec(d, xml, "https://svcs.ebay.com/services/resolution/ResolutionCaseManagementService/v1?REST-PAYLOAD");
            String r1 = resEbpMap.get("stat");
            String res = resEbpMap.get("message");
            if ("fail".equalsIgnoreCase(r1)) {
                AjaxSupport.sendFailText("fail", res);
                return;
            }
            String ack = SamplePaseXml.getVFromXmlString(res, "ack");
            if ("Success".equalsIgnoreCase(ack)) {
                TradingGetUserCases cases=caseses.get(0);
                cases.setCasecontent("send a message");
                cases.setComments(textarea);
                cases.setSolvecatetime(new Date());
                cases.setHandled(1);
                iTradingGetUserCases.saveGetUserCases(cases);
                TradingOrderAddMemberMessageAAQToPartner partner=new TradingOrderAddMemberMessageAAQToPartner();
                partner.setSender(cases.getSellerid());
                partner.setBody(textarea);
                partner.setTransactionid(cases.getTransactionid());
                partner.setItemid(cases.getItemid());
                partner.setRecipientid(cases.getBuyerid());
                partner.setMessagetype(1);
                partner.setReplied("true");
                partner.setEbayId(Long.valueOf(ebayId));
                iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(partner);
                AjaxSupport.sendSuccessText("message", "发送消息成功!");
            }else{
                TradingGetUserCases cases=caseses.get(0);
                TradingOrderAddMemberMessageAAQToPartner partner=new TradingOrderAddMemberMessageAAQToPartner();
                partner.setSender(cases.getSellerid());
                partner.setBody(textarea);
                partner.setTransactionid(cases.getTransactionid());
                partner.setItemid(cases.getItemid());
                partner.setRecipientid(cases.getBuyerid());
                partner.setMessagetype(1);
                partner.setReplied("false");
                partner.setFailereason(SamplePaseXml.getWarningInformation(res));
                partner.setEbayId(Long.valueOf(ebayId));
                iTradingOrderAddMemberMessageAAQToPartner.saveOrderAddMemberMessageAAQToPartner(partner);
                String errors = SamplePaseXml.getResponseCaseString(res,"message");
                logger.error("发送消息失败!" + res);
                AjaxSupport.sendFailText("fail", "发送消息失败:"+errors);
            }
        }else{
            AjaxSupport.sendFailText("fail","纠纷不存在");
        }
        AjaxSupport.sendSuccessText("message", "sendMessageForm");
    }

    //获取track number
    @RequestMapping("/queryTrack.do")
    @ResponseBody
    public void queryTrack(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        /*OrderQueryTrack.queryTrack(orderses);*/
        String transactionId=request.getParameter("transactionId");
        String ebayId=request.getParameter("ebayId");
        String itemId = request.getParameter("itemId");
        List<TradingOrderGetOrders> orderses=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionId,Long.valueOf(ebayId),itemId);
        if(orderses!=null&&orderses.size()>0){
            List<JSONObject> list=OrderQueryTrack.queryTrack1(orderses);
            if(list!=null&&list.size()>0){
                JSONObject json = list.get(0);
                json.getString("Status");
                AjaxSupport.sendSuccessText("message", json);
            }else{
                AjaxSupport.sendFailText("fail","调用API出错,请稍后再试!");
            }

        }else{
            AjaxSupport.sendFailText("fail","没找到对应的订单,请核查");
        }

        /*request.setCharacterEncoding("UTF-8");
        BufferedReader in = null;
        String content = null;

        String token=(URLEncoder.encode("RXYaxblwfBeNY+2zFVDbCYTz91r+VNWmyMTgXE4v16gCffJam2FcsPUpiau6F8Yk"));
        String url="http://api.91track.com/track?culture=zh-CN&numbers="+trackNum+"&token="+token;
        *//*String url="http://api.91track.com/track?culture=en&numbers="+"RD275816257CN"+"&token="+token;*//*
        HttpClient client=new DefaultHttpClient();
        HttpGet get=new HttpGet();
        get.setURI(new URI(url));
        HttpResponse response = client.execute(get);

        in = new BufferedReader(new InputStreamReader(response.getEntity()
                .getContent()));
        StringBuffer sb = new StringBuffer("");
        String line ="";
        String NL = System.getProperty("line.separator");
        while ((line = in.readLine()) != null) {
            sb.append(line + NL);
        }
        in.close();
        content = sb.toString();
        String[] arr=content.split(",");
        String content1="{"+arr[1]+"}";*/
       /* String content1="{\"age\":66,\"name\":\"老张头\"}";
        JSONObject json = JSON.parseObject(content1);
        json.getString("Status");
        AjaxSupport.sendSuccessText("message", json);*/
    }
}
