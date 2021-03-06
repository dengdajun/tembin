package com.message.controller;

import com.base.aboutpaypal.service.PayPalService;
import com.base.database.task.model.TaskGetMessages;
import com.base.database.trading.model.*;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.CommonParmVO;
import com.base.domains.querypojos.MessageGetmymessageQuery;
import com.base.domains.querypojos.TradingOrderAddMemberMessageAAQToPartnerQuery;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.common.DateUtils;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.htmlutil.HtmlUtil;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.task.service.ITaskGetMessages;
import com.trading.service.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrtor on 2014/8/6.
 */
@Controller
@RequestMapping("message")
public class GetmymessageController extends BaseAction{

    static Logger logger = Logger.getLogger(GetmymessageController.class);
    @Autowired
    private ITradingMessageGetmymessage iTradingMessageGetmymessage;
    @Autowired
    private ITradingMessageAddmembermessage iTradingMessageAddmembermessage;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private ITradingOrderAddMemberMessageAAQToPartner iTradingOrderAddMemberMessageAAQToPartner;
    @Autowired
    private ITradingOrderGetOrders iTradingOrderGetOrders;
    @Autowired
    private ITradingOrderGetSellerTransactions iTradingOrderGetSellerTransactions;
    @Autowired
    private PayPalService payPalService;
    @Autowired
    private  ITradingOrderGetAccount iTradingOrderGetAccount;
    @Autowired
    private ITradingOrderGetItem iTradingOrderGetItem;
    @Autowired
    private ITradingOrderPictureDetails iTradingOrderPictureDetails;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Autowired
    private ITradingOrderSenderAddress iTradingOrderSenderAddress;
    @Autowired
    private ITaskGetMessages iTaskGetMessages;

    @Value("${EBAY.API.URL}")
    private String apiUrl;
    /**
     * 消息列表
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/MessageGetmymessageList.do")
    public ModelAndView MessageGetmymessageList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<MessageGetmymessageQuery> list=iTradingMessageGetmymessage.selectByMessageGetmymessageNoReadCount();
        if(list!=null&&list.size()>0){
            modelMap.put("noReadNumber",list.size());
        }

        return forword("MessageGetmymessage/MessageGetmymessageList",modelMap);
    }
    @RequestMapping("/addComment.do")
    public ModelAndView addComment(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
       String id=request.getParameter("id");
        if(StringUtils.isNotBlank(id)){
            TradingMessageGetmymessage message=iTradingMessageGetmymessage.selectMessageGetmymessageById(Long.valueOf(id));
            modelMap.put("message",message);
        }

        return forword("MessageGetmymessage/addComment",modelMap);
    }
    //获取未读消息ajax方法
    @RequestMapping("/noReadMessageGetmymessageList.do")
    @ResponseBody
    public void noReadMessageGetmymessageList(HttpServletRequest request,CommonParmVO commonParmVO) throws Exception {
        String readed=request.getParameter("readed");
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays=systemUserManagerService.queryCurrAllEbay(map);
        Map m = new HashMap();
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<MessageGetmymessageQuery> lists=new ArrayList<MessageGetmymessageQuery>();
        if(ebays.size()>0){
            m.put("read",readed);
            m.put("ebays",ebays);
            m.put("folderID","0");
            lists= this.iTradingMessageGetmymessage.selectMessageGetmymessageByGroupList(m,page);
        }

        jsonBean.setList(lists);
        AjaxSupport.sendSuccessText("",jsonBean);

    }

    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/saveComment.do")
    @ResponseBody
    public void saveComment(HttpServletRequest request,CommonParmVO commonParmVO) throws Exception {
        String id=request.getParameter("id");
        String comment=request.getParameter("comment");
        if(StringUtils.isNotBlank(id)){
            TradingMessageGetmymessage message=iTradingMessageGetmymessage.selectMessageGetmymessageById(Long.valueOf(id));
            message.setComment(comment);
            iTradingMessageGetmymessage.saveMessageGetmymessage(message);
            AjaxSupport.sendSuccessText("","备注成功");
        }else{
            AjaxSupport.sendFailText("fail","备注失败");
        }

    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadMessageAddmymessageList.do")
    @ResponseBody
    public void loadMessageAddmymessageList(HttpServletRequest request,CommonParmVO commonParmVO) throws Exception {
        String replied=request.getParameter("replied");
        String amount=request.getParameter("amount");
        String status=request.getParameter("status");
        String day=request.getParameter("day");
        String type=request.getParameter("type");
        String content=request.getParameter("content");
        String starttime1=request.getParameter("starttime1");
        String endtime1=request.getParameter("endtime1");
        String messageType=request.getParameter("messageType");
        Date starttime=null;
        Date endtime=null;
        if(!StringUtils.isNotBlank(content)){
            content=null;
        }
        if(!StringUtils.isNotBlank(type)){
            type=null;
        }
        if(!StringUtils.isNotBlank(replied)){
            replied=null;
        }
        if(!StringUtils.isNotBlank(amount)){
            amount=null;
        }
        if(!StringUtils.isNotBlank(status)){
            status=null;
        }else{
            replied=status;
        }
        if(StringUtils.isBlank(messageType)){
            messageType=null;
        }
        if(!StringUtils.isNotBlank(day)){
            day=null;
        }else{
            if("2".equals(day)){
                starttime=DateUtils.subDays(new Date(),1);
                Date endTime= DateUtils.addDays(starttime, 0);
                endtime= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
            }else{
                int days=Integer.parseInt(day);
                starttime=DateUtils.subDays(new Date(),days-1);
                Date endTime= DateUtils.addDays(starttime,days-1);
                endtime= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
            }
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
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays=systemUserManagerService.queryCurrAllEbay(map);
        List<TradingOrderAddMemberMessageAAQToPartnerQuery> lists=new ArrayList<TradingOrderAddMemberMessageAAQToPartnerQuery>();
        if(ebays.size()>0){
            m.put("ebays",ebays);
            m.put("amount",amount);
            m.put("starttime",starttime);
            m.put("endtime",endtime);
            m.put("replied",replied);
            m.put("messageType",4);
            m.put("type",type);
            m.put("content",content);
            m.put("messageType",messageType);
            lists=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartner(m, page);
        }
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }
    /**标记为已读*/
    @RequestMapping("/ajax/markReaded.do")
    @ResponseBody
    public void markReaded(HttpServletRequest request) throws Exception {
        String ids1=request.getParameter("value");
        String value=request.getParameter("value1");
        String[] ids=ids1.split(",");
        for(int i=0;i<ids.length;i++){
            Long id= Long.valueOf(ids[i]);
            TradingMessageGetmymessage message=iTradingMessageGetmymessage.selectMessageGetmymessageById(id);
            if(message!=null){
                message.setRead(value);
                iTradingMessageGetmymessage.saveMessageGetmymessage(message);
            }
        }
        List<MessageGetmymessageQuery> list=iTradingMessageGetmymessage.selectByMessageGetmymessageNoReadCount();
        Integer countNum=0;
        if(list!=null&&list.size()>0){
            countNum=list.size();
        }
        Map<String,String> map=new HashMap<String, String>();
        map.put("message","已标记为已读");
        map.put("count",countNum+"");
        if("true".equals(value)){
            AjaxSupport.sendSuccessText("", map);
        }else{
            AjaxSupport.sendSuccessText("", map);
        }

    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/updateReadStatus.do")
    @ResponseBody
    public void updateReadStatus(HttpServletRequest request,CommonParmVO commonParmVO) throws Exception {
        String messageid=request.getParameter("messageid");
        String ebayId=request.getParameter("ebayId");
        List<TradingMessageGetmymessage> messages=iTradingMessageGetmymessage.selectMessageGetmymessageByMessageId(messageid,Long.valueOf(ebayId));
        for(TradingMessageGetmymessage message:messages){
            message.setRead("true");
            iTradingMessageGetmymessage.saveMessageGetmymessage(message);
        }
        List<MessageGetmymessageQuery> list=iTradingMessageGetmymessage.selectByMessageGetmymessageNoReadCount();
        Integer countNum=0;
        if(list!=null&&list.size()>0){
            countNum=list.size();
        }
        AjaxSupport.sendSuccessText("",countNum);
    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadMessageGetmymessageList.do")
    @ResponseBody
    public void loadMessageGetmymessageList(HttpServletRequest request,CommonParmVO commonParmVO) throws Exception {
         /*amount,status,day,type,content*/
        String amount=request.getParameter("amount");
        String status=request.getParameter("status");
        String day=request.getParameter("day");
        String type=request.getParameter("type");
        String content=request.getParameter("content");
        String starttime1=request.getParameter("starttime1");
        String endtime1=request.getParameter("endtime1");
        String messageFrom=request.getParameter("messageFrom");
        String folderID=request.getParameter("folderID");
        Date starttime=null;
        Date endtime=null;
        if(!StringUtils.isNotBlank(messageFrom)){
            messageFrom=null;
        }
        if(!StringUtils.isNotBlank(amount)){
            amount=null;
        }
       /* if(!StringUtils.isNotBlank(content)){
            content=null;
        }*/
        if(!StringUtils.isNotBlank(type)){
            type=null;
        }
        if(!StringUtils.isNotBlank(folderID)){
            folderID="0";
        }
        if(!StringUtils.isNotBlank(status)){
            status=null;
        }
        if(!StringUtils.isNotBlank(day)){
            day=null;
        }else{
            if("2".equals(day)){
                starttime=DateUtils.subDays(new Date(),1);
                Date endTime= DateUtils.addDays(starttime, 0);
                endtime= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
            }else{
                int days=Integer.parseInt(day);
                starttime=DateUtils.subDays(new Date(),days-1);
                Date endTime= DateUtils.addDays(starttime,days-1);
                endtime= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
            }
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
        Map m = new HashMap();
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays=systemUserManagerService.queryCurrAllEbay(map);
        List<MessageGetmymessageQuery> lists=new ArrayList<MessageGetmymessageQuery>();
            if(ebays.size()>0){
                m.put("ebays",ebays);
                m.put("amount",amount);
            m.put("status",status);
            m.put("starttime",starttime);
            m.put("endtime",endtime);
            m.put("messageFrom",messageFrom);
            m.put("type",type);
            m.put("content",content);
            m.put("folderID",folderID);
            lists= this.iTradingMessageGetmymessage.selectMessageGetmymessageByGroupList(m,page);
        }
        jsonBean.setList(lists);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }
    @RequestMapping("/viewMessageAddmymessage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewMessageAddmymessage(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String itemid=request.getParameter("itemid");
        String recipientid=request.getParameter("recipientid");
        String sender=request.getParameter("sender");
        String transactionid=request.getParameter("transactionid");
        String messageid=request.getParameter("messageid");
        String ebayId=request.getParameter("ebayId");
        String messageType=request.getParameter("messageType");
        if(!StringUtils.isNotBlank(transactionid)){
            transactionid=null;
        }
        if(!StringUtils.isNotBlank(messageid)){
            messageid=null;
        }
        if(!StringUtils.isNotBlank(itemid)){
            itemid=null;
        }
        List<List<TradingOrderAddMemberMessageAAQToPartner>> addMessages=new ArrayList<List<TradingOrderAddMemberMessageAAQToPartner>>();
        List<TradingMessageGetmymessage> messages1=new ArrayList<TradingMessageGetmymessage>();
        List<TradingMessageGetmymessage> messageList=new ArrayList<TradingMessageGetmymessage>();
        List<TradingOrderAddMemberMessageAAQToPartner> addmessageList=new ArrayList<TradingOrderAddMemberMessageAAQToPartner>();
        if(messageid!=null){
            messageList=iTradingMessageGetmymessage.selectMessageGetmymessageByMessageId(messageid,Long.valueOf(ebayId));
            addmessageList=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByMessageID(messageid, Integer.valueOf(messageType),"false");
            messages1.addAll(messageList);
            if(addmessageList!=null&&addmessageList.size()>0){
                addMessages.add(addmessageList);
            }
            if(messageList!=null&&messageList.size()>0) {
                if ("eBay".equals(messageList.get(0).getSender())) {
                    messages1.add(messageList.get(0));
                } else {
                    List<MessageGetmymessageQuery> messagequ = iTradingMessageGetmymessage.selectMessageGetmymessageByItemIdAndSenderFolderIDIsZoneOrOne(messageList.get(0).getItemid(), messageList.get(0).getSender(), messageList.get(0).getSendtoname());
                    if (messagequ != null) {
                        messages1.addAll(messagequ);
                    }
                }
            }
        }else if(itemid!=null){
            messageList=iTradingMessageGetmymessage.selectMessageGetmymessageByItemIdAndSender(itemid,recipientid,sender);

            addmessageList=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByItemIdAndSender(itemid,4,sender,recipientid,"false");
            messages1.addAll(messageList);
            if(addmessageList!=null&&addmessageList.size()>0){
                addMessages.add(addmessageList);
            }
            if(messageList!=null&&messageList.size()>0){
                if("eBay".equals(messageList.get(0).getSender())){
                    messages1.add(messageList.get(0));
                }else{
                    List<MessageGetmymessageQuery> messagequ = iTradingMessageGetmymessage.selectMessageGetmymessageByItemIdAndSenderFolderIDIsZoneOrOne(messageList.get(0).getItemid(), messageList.get(0).getSender(), messageList.get(0).getSendtoname());
                    if (messagequ != null) {
                        messages1.addAll(messagequ);
                    }
                }
            }
        }
        int ii=0;
        for(TradingMessageGetmymessage message:messages1){
            String text=message.getTextHtml();
            if(StringUtils.isNotBlank(text)) {
                text = StringEscapeUtils.unescapeHtml(text);
                if(StringUtils.isNotBlank(text)){
                   /* text=text.replace("&lt;![CDATA["," ");
                    text=text.replace("]]&gt;"," ");
                    text=text.replace("<![CDATA[ "," ");
                    text=text.replace("]]>"," ");*/
                    text = StringUtils.removeStart(text, "&lt;![CDATA[");
                    text = StringUtils.removeStart(text, "<![CDATA[");
                    text = StringUtils.removeEnd(text, "]]&gt;");
                    text = StringUtils.removeEnd(text, "]]>");
                }
                List<TradingOrderAddMemberMessageAAQToPartner> addMessages3 = new ArrayList<TradingOrderAddMemberMessageAAQToPartner>();
                if (!"eBay".equals(message.getSender())) {

                    List<String> bodys = HtmlUtil.getByerMessageFromXML(text);
                    for (String body : bodys) {
                        TradingOrderAddMemberMessageAAQToPartner partner=new TradingOrderAddMemberMessageAAQToPartner();
                        if(ii%2==0){
                            partner.setSender(message.getSender());
                            partner.setSubject(message.getSubject());
                            partner.setRecipientid(message.getSendtoname());
                            partner.setCreateTime(message.getReceivedate());
                            partner.setItemid(message.getItemid());
                        }else{
                            partner.setSender(message.getSendtoname());
                            partner.setSubject(message.getSubject());
                            partner.setRecipientid(message.getSender());
                            partner.setCreateTime(message.getReceivedate());
                            partner.setItemid(message.getItemid());
                        }
                        partner.setBody(body);
                        partner.setFailnumber(1000);
                        addMessages3.add(partner);
                    }
                } else {
                    org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(text);
                    Element div = doc.getElementById("SingleItemCTA");
                    Boolean ebayFlag = false;
                    if (div != null) {
                        Elements font = div.getElementsByTag("font");
                        if (font != null && font.size() > 0) {
                            Elements divs = font.get(0).getElementsByTag("div");
                            if (divs != null && divs.size() > 0) {
                                String body = divs.get(0).html();
                                TradingOrderAddMemberMessageAAQToPartner partner = new TradingOrderAddMemberMessageAAQToPartner();
                                partner.setSender(message.getSender());
                                partner.setSubject(message.getSubject());
                                partner.setRecipientid(message.getRecipientuserid());
                                partner.setCreateTime(message.getReceivedate());
                                partner.setItemid(message.getItemid());
                                partner.setBody(body);
                                partner.setFailnumber(1000);
                                addMessages3.add(partner);
                                ebayFlag = true;
                            }
                        }
                    }
                    if (!ebayFlag) {
                        TradingOrderAddMemberMessageAAQToPartner partner = new TradingOrderAddMemberMessageAAQToPartner();
                        partner.setSender(message.getSender());
                        partner.setSubject(message.getSubject());
                        partner.setRecipientid(message.getRecipientuserid());
                        partner.setCreateTime(message.getReceivedate());
                        partner.setItemid(message.getItemid());
                        partner.setBody(message.getSubject());
                        partner.setFailnumber(1000);
                        addMessages3.add(partner);
                    }
                }
                Object[] addMessages3s=addMessages3.toArray();
                List<TradingOrderAddMemberMessageAAQToPartner> list123=new ArrayList<>();
                for(int i=addMessages3s.length-1;i>=0;i--){
                    TradingOrderAddMemberMessageAAQToPartner messageAAQToPartner= (TradingOrderAddMemberMessageAAQToPartner) addMessages3s[i];
                    list123.add(messageAAQToPartner);
                }
                addMessages.add(list123);
            }
            /*TradingOrderAddMemberMessageAAQToPartner partner=new TradingOrderAddMemberMessageAAQToPartner();
            partner.setSender(message.getSender());
            partner.setSubject(message.getSubject());
            partner.setRecipientid(message.getRecipientuserid());
            partner.setCreateTime(message.getReceivedate());
            partner.setItemid(message.getItemid());
            String text=message.getTextHtml();
            if(!"eBay".equals(message.getSender())){
                if(StringUtils.isNotBlank(text)){
                    String text4="";
                    String[] text1=text.split("<table border=\"0\" cellpadding=\"2\" cellspacing=\"3\" width=\"100%\"><tr><td>");
                    for(int i=1;i<text1.length;i++){
                        String[] text2=text1[i].split("<div style=\"font-weight:bold; font-size:10pt; font-family:arial, sans-serif; color:#000\">- "+message.getSender());
                        if(text2[0].contains(message.getRecipientuserid())){
                            String text3=text2[0];
                            if(!text3.contains("- "+message.getRecipientuserid())){
                                text4+=text3+"<br/>";
                                partner.setBody(text4);

                            }
                        }
                    }
                    if(partner.getBody()!=null||!"".equals(partner.getBody())){
                        addMessages.add(partner);
                    }
                }
            }*/
            ii++;
        }
        Object[] addMessages1=addMessages.toArray();
        for(int i=0;i<addMessages1.length;i++){
            for(int j=i+1;j<addMessages1.length;j++){
                List<TradingOrderAddMemberMessageAAQToPartner> l1= (List<TradingOrderAddMemberMessageAAQToPartner>) addMessages1[i];
                List<TradingOrderAddMemberMessageAAQToPartner> l2= (List<TradingOrderAddMemberMessageAAQToPartner>) addMessages1[j];
                if(l1!=null&&l1.size()>0&&l2!=null&&l2.size()>0&&l1.get(0).getCreateTime().after(l2.get(0).getCreateTime())){
                    addMessages1[i]=l2;
                    addMessages1[j]=l1;
                }else{
                    addMessages1[i]=l1;
                    addMessages1[j]=l2;
                }
            }
        }
/*        Object[] addMessages1=addMessages.toArray();
        for(int i=0;i<addMessages1.length;i++){
            for(int j=i+1;j<addMessages1.length;j++){
                TradingOrderAddMemberMessageAAQToPartner l1= (TradingOrderAddMemberMessageAAQToPartner) addMessages1[i];
                TradingOrderAddMemberMessageAAQToPartner l2= (TradingOrderAddMemberMessageAAQToPartner) addMessages1[j];
                if(l1.getCreateTime().after(l2.getCreateTime())){
                    addMessages1[i]=l2;
                    addMessages1[j]=l1;
                }else{
                    addMessages1[i]=l1;
                    addMessages1[j]=l2;
                }
            }
        }*/
        List<TradingOrderGetOrders> lists=new ArrayList<TradingOrderGetOrders>();
        List<TradingOrderGetOrders> lists1=new ArrayList<TradingOrderGetOrders>();
        if(StringUtils.isNotBlank(transactionid)&&StringUtils.isNotBlank(ebayId)){
            lists=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid,Long.valueOf(ebayId),itemid);
        }

        if(lists!=null&&lists.size()>0){
            lists1=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(lists.get(0).getTransactionid(),lists.get(0).getEbayId(),itemid);
        }
        if(messageList!=null&&messageList.size()>0){
            modelMap.put("message",messageList.get(0));
            modelMap.put("sender",messageList.get(0).getSender());
            modelMap.put("recipient",messageList.get(0).getRecipientuserid());
            modelMap.put("messageID",messageList.get(0).getMessageid());
            modelMap.put("parentMessageID",messageList.get(0).getExternalmessageid());
        }else{
            if(lists1!=null&&lists1.size()>0){
                modelMap.put("sender",lists1.get(0).getSelleruserid());
                modelMap.put("recipient",lists1.get(0).getBuyeruserid());
            }
        }
        List<String> palpays=new ArrayList<String>();
        List<String> grossdetailamounts=new ArrayList<String>();
        List<String> pictures=new ArrayList<String>();
        List<String> accs=new ArrayList<String>();
        for(TradingOrderGetOrders order:lists){
            List<TradingOrderGetSellerTransactions> sellerTransactions=iTradingOrderGetSellerTransactions.selectTradingOrderGetSellerTransactionsByTransactionId(order.getItemid(),order.getTransactionid());
            if(sellerTransactions!=null&&sellerTransactions.size()>0){
                if(StringUtils.isNotBlank(sellerTransactions.get(0).getExternaltransactionid())){
                    palpays.add(sellerTransactions.get(0).getExternaltransactionid());
                    accs.add(sellerTransactions.get(0).getPaypalprice());
                }
            }else{
                palpays.add("");
            }
            List<TradingOrderGetAccount> accountlist=iTradingOrderGetAccount.selectTradingOrderGetAccountByTransactionId(order.getTransactionid());
            if(accountlist!=null&&accountlist.size()>0){
                for(TradingOrderGetAccount account:accountlist){
                    if("成交費".equals(account.getDescription())){
                        grossdetailamounts.add(account.getGrossdetailamount());
                    }else{
                        grossdetailamounts.add("");
                    }
                }
            }

            String ItemId=order.getItemid();
            List<TradingOrderGetItem> itemList= iTradingOrderGetItem.selectOrderGetItemByItemId(ItemId);
            if(itemList!=null&&itemList.size()>0){
                Long pictureid=itemList.get(0).getPicturedetailsId();
                List<TradingOrderPictureDetails> pictureDetailses=iTradingOrderPictureDetails.selectOrderGetItemById(pictureid);
                if(pictureDetailses!=null&&pictureDetailses.size()>0){
                    pictures.add(pictureDetailses.get(0).getPictureurl());
                }else{
                    pictures.add("");
                }
            }

        }
        List<TradingOrderSenderAddress> senderAddresses=new ArrayList<TradingOrderSenderAddress>();
        if(lists1!=null&&lists1.size()>0){
            senderAddresses=iTradingOrderSenderAddress.selectOrderSenderAddressByOrderId(lists1.get(0).getOrderid());
            modelMap.put("orderId",lists1.get(0).getOrderid());
            modelMap.put("order",lists1.get(0));
            modelMap.put("sender",lists1.get(0).getBuyeruserid());
            modelMap.put("recipient",lists1.get(0).getSelleruserid());
            SystemLog log= SystemLogUtils.selectSystemLogByTableId(SystemLogUtils.ORDER_OPERATE_RECORD_REFUND, lists1.get(0).getId());
            if(log!=null){
                modelMap.put("refundAmountDesc",log.getEventdesc());
                modelMap.put("refundDate",log.getCreatedate());
            }
        }
        TradingOrderSenderAddress type1=new TradingOrderSenderAddress();
        TradingOrderSenderAddress type2=new TradingOrderSenderAddress();
        for(TradingOrderSenderAddress senderAddresse:senderAddresses){
            if("1".equals(senderAddresse.getType())){
                type1=senderAddresse;
            }
            if("2".equals(senderAddresse.getType())){
                type2=senderAddresse;
            }
        }
        if(lists!=null&&lists.size()>0){
            modelMap.put("flag","true");
        }else{
            modelMap.put("flag","false");
        }
       /* modelMap.put("messages",messages);
        modelMap.put("messageID",messages.get(0).getMessageid());*/
        modelMap.put("addMessage1",addMessages1);
        modelMap.put("ebayId",ebayId);
        modelMap.put("orders",lists);
        String rootpath=request.getContextPath();
        modelMap.put("rootpath",rootpath);
        modelMap.put("addresstype1",type1);
        modelMap.put("addresstype2",type2);
        modelMap.put("paypals",palpays);
        modelMap.put("grossdetailamounts",grossdetailamounts);
        modelMap.put("pictures",pictures);
        modelMap.put("accs",accs);
        modelMap.put("messageFlag","true");
        modelMap.put("orderFlag","false");
        modelMap.put("ebayFlag","false");
        return forword("orders/order/viewOrderGetOrders",modelMap);
    }
    /**
     * 查看原始邮件
     */
    @RequestMapping("/viewPrimevalMessage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewPrimevalMessage(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String itemid=request.getParameter("itemId");
        String sender=request.getParameter("sender");
        String sendtoname=request.getParameter("sendtoname");
        String messageId=request.getParameter("messageId");
        String ebayId=request.getParameter("ebayId");
        List<MessageGetmymessageQuery> messagaAll=new ArrayList<>();
        List<MessageGetmymessageQuery> messages=iTradingMessageGetmymessage.selectMessageGetmymessageByItemIdAndSenderFolderIDIsZoneOrOne(itemid,sender,sendtoname);
        List<MessageGetmymessageQuery> messages1=iTradingMessageGetmymessage.selectMessageGetmymessageByItemIdAndSenderFolderIDIsZoneOrOne(itemid,sendtoname,sender);
        if(messages!=null&&messages.size()>0){
            messagaAll.addAll(messages);
        }
        if(messages1!=null&&messages1.size()>0){
            messagaAll.addAll(messages1);
        }
        List<String> texts=new ArrayList<>();
        Object[] query=messagaAll.toArray();
        if(messagaAll!=null&&messagaAll.size()>0){
            for(int i=0;i<query.length;i++){
                for(int j=i;j<query.length;j++){
                    MessageGetmymessageQuery query1= (MessageGetmymessageQuery) query[i];
                    MessageGetmymessageQuery query2=(MessageGetmymessageQuery) query[j];
                    if(query1!=null&&query2!=null&&query1.getReceivedate()!=null&&query2.getReceivedate()!=null&&query1.getReceivedate().before(query2.getReceivedate())){
                        query[i]=query2;
                        query[j]=query1;
                    }
                }
            }
            for(int i=0;i<query.length;i++){
                MessageGetmymessageQuery message= (MessageGetmymessageQuery) query[i];
                String text=message.getTextHtml();
                if(StringUtils.isNotBlank(text)) {
                    text = StringEscapeUtils.unescapeHtml(text);
                    texts.add(text);
                }
            }
        }else{
            if(StringUtils.isNotBlank(messageId)&&StringUtils.isNotBlank(ebayId)){
                List<TradingMessageGetmymessage> myMessages=iTradingMessageGetmymessage.selectMessageGetmymessageByMessageId(messageId,Long.valueOf(ebayId));
                for(TradingMessageGetmymessage getmymessage:myMessages){
                    String text=getmymessage.getTextHtml();
                    if(StringUtils.isNotBlank(text)) {
                        text = StringEscapeUtils.unescapeHtml(text);
                        texts.add(text);
                    }
                }
            }
        }
        modelMap.put("texts",texts);
        return forword("MessageGetmymessage/viewPrimevalMessage",modelMap);
    }
   /**
     * 查看消息
     * @param request
     * @param response
     * @param modelMap
     * @return
     */

    @RequestMapping("/viewMessageGetmymessage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewTemplateInitTable(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String messageID=request.getParameter("messageID");
        String sender=request.getParameter("sender");
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays = systemUserManagerService.queryCurrAllEbay(map);
        if(!StringUtils.isNotBlank(messageID)){
            messageID="";
        }
        Map m=new HashMap();
        m.put("messageID",messageID);
        m.put("ebays",ebays);
        m.put("sender",sender);
        List<MessageGetmymessageQuery> messages=iTradingMessageGetmymessage.selectMessageGetmymessageBySender(m);
            List<List<TradingOrderAddMemberMessageAAQToPartner>> addMessages=new ArrayList<List<TradingOrderAddMemberMessageAAQToPartner>>();
            List<TradingMessageGetmymessage> messages1=new ArrayList<TradingMessageGetmymessage>();
            List<TradingOrderAddMemberMessageAAQToPartner> addmessageList=new ArrayList<TradingOrderAddMemberMessageAAQToPartner>();
            if(messages.size()>0&&!"eBay".equals(messages.get(0).getSender())){
                //addmessageList = iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByMessageID(messages.get(0).getMessageid(), 4,"true");
                if(StringUtils.isNotBlank(messages.get(0).getItemid())){
                    addmessageList=iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByItemIdAndSender(messages.get(0).getItemid(),4,messages.get(0).getRecipientuserid(),messages.get(0).getSender(),"true");
                }else{
                    addmessageList = iTradingOrderAddMemberMessageAAQToPartner.selectTradingOrderAddMemberMessageAAQToPartnerByMessageID(messages.get(0).getMessageid(), 4,"true");
                }
            }
            if(addmessageList!=null&&addmessageList.size()>0){
                for(TradingOrderAddMemberMessageAAQToPartner partner:addmessageList){
                    List<TradingOrderAddMemberMessageAAQToPartner> partners=new ArrayList<>();
                    partners.add(partner);
                    addMessages.add(partners);
                }
            }
            if(messages!=null&&messages.size()>0){
                modelMap.put("ebayId",messages.get(0).getEbayAccountId());
                if("eBay".equals(messages.get(0).getSender())){
                    messages1.add(messages.get(0));
                }else{
                   List<MessageGetmymessageQuery> messagequ=iTradingMessageGetmymessage.selectMessageGetmymessageByItemIdAndSenderFolderIDIsZoneOrOne(messages.get(0).getItemid(),messages.get(0).getSender(),messages.get(0).getSendtoname());
                    if(messagequ!=null&&messagequ.size()>0){
                        messages1.addAll(messagequ);
                    }
                }
            }
            Integer ii=0;
            for(TradingMessageGetmymessage message:messages1){
                String text=message.getTextHtml();
                if(StringUtils.isNotBlank(text)){
                    text=StringEscapeUtils.unescapeHtml(text);
                    if(StringUtils.isNotBlank(text)){
                        text=text.replace("&lt;![CDATA["," ");
                        text=text.replace("]]&gt;"," ");
                        text=text.replace("<![CDATA[ "," ");
                        text=text.replace("]]>"," ");
                    }
                    List<TradingOrderAddMemberMessageAAQToPartner> addMessages3=new ArrayList<TradingOrderAddMemberMessageAAQToPartner>();
                    if(!"eBay".equals(message.getSender())){
                        modelMap.put("ebayFlag","false");
                        List<String> bodys=HtmlUtil.getByerMessageFromXML(text);
                        for(String body:bodys){
                            TradingOrderAddMemberMessageAAQToPartner partner=new TradingOrderAddMemberMessageAAQToPartner();
                            if(ii%2==0){
                                partner.setSender(message.getSender());
                                partner.setSubject(message.getSubject());
                                partner.setRecipientid(message.getSendtoname());
                                partner.setCreateTime(message.getReceivedate());
                                partner.setItemid(message.getItemid());
                            }else{
                                partner.setSender(message.getSendtoname());
                                partner.setSubject(message.getSubject());
                                partner.setRecipientid(message.getSender());
                                partner.setCreateTime(message.getReceivedate());
                                partner.setItemid(message.getItemid());
                            }
                            partner.setFailnumber(1000);
                            partner.setBody(body);
                            addMessages3.add(partner);
                        }
                    }else{
                        /*org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(text);
                        Element div=doc.getElementById("SingleItemCTA");
                        Boolean ebayFlag=false;
                        if(div!=null){
                            Elements font=div.getElementsByTag("font");
                            if(font!=null&&font.size()>0){
                                Elements divs=font.get(0).getElementsByTag("div");
                                if(divs!=null&&divs.size()>0){
                                    String body=divs.get(0).html();
                                    TradingOrderAddMemberMessageAAQToPartner partner=new TradingOrderAddMemberMessageAAQToPartner();
                                    partner.setSender(message.getSender());
                                    partner.setSubject(message.getSubject());
                                    partner.setRecipientid(message.getRecipientuserid());
                                    partner.setCreateTime(message.getReceivedate());
                                    partner.setItemid(message.getItemid());
                                    partner.setBody(body);
                                    addMessages3.add(partner);
                                    ebayFlag=true;
                                }
                            }
                        }
                        if(!ebayFlag){*/
                        modelMap.put("ebayFlag","true");
                            TradingOrderAddMemberMessageAAQToPartner partner=new TradingOrderAddMemberMessageAAQToPartner();
                            partner.setSender(message.getSender());
                            partner.setSubject(message.getSubject());
                            partner.setRecipientid(message.getRecipientuserid());
                            partner.setCreateTime(message.getReceivedate());
                            partner.setItemid(message.getItemid());
                            text=text+"<div style='display:none' id='hideme'></div>";
                            text=text.replaceAll("<p>","<div>");
                            text=text.replaceAll("</p>","</div>");
                            partner.setBody(text);
                            partner.setFailnumber(1000);
                            addMessages3.add(partner);
                     /*   }*/
                    }
                    Object[] addMessages3s=addMessages3.toArray();
                    List<TradingOrderAddMemberMessageAAQToPartner> list123=new ArrayList<>();
                    for(int i=addMessages3s.length-1;i>=0;i--){
                        TradingOrderAddMemberMessageAAQToPartner messageAAQToPartner= (TradingOrderAddMemberMessageAAQToPartner) addMessages3s[i];
                        list123.add(messageAAQToPartner);
                    }
                    addMessages.add(list123);
                }
                ii++;
            }
            Object[] addMessages1=addMessages.toArray();
            for(int i=0;i<addMessages1.length;i++){
                for(int j=i+1;j<addMessages1.length;j++){
                    List<TradingOrderAddMemberMessageAAQToPartner> l1= (List<TradingOrderAddMemberMessageAAQToPartner>) addMessages1[i];
                    List<TradingOrderAddMemberMessageAAQToPartner> l2= (List<TradingOrderAddMemberMessageAAQToPartner>) addMessages1[j];
                    if(l1!=null&&l1.size()>0&&l2!=null&&l2.size()>0&&l1.get(0).getCreateTime().after(l2.get(0).getCreateTime())){
                        addMessages1[i]=l2;
                        addMessages1[j]=l1;
                    }
                }
            }

            List<TradingOrderGetOrders> lists=new ArrayList<TradingOrderGetOrders>();
            List<TradingOrderGetOrders> lists1=new ArrayList<TradingOrderGetOrders>();
            if(messages!=null&&messages.size()>0){
                modelMap.put("message",messages.get(0));
                modelMap.put("sender",messages.get(0).getSender());
                modelMap.put("recipient",messages.get(0).getSendtoname());
                if(!"eBay".equals(messages.get(0).getSender())){
                    if(messages.get(0).getItemid()!=null){
                        lists=iTradingOrderGetOrders.selectOrderGetOrdersByBuyerAndItemid(messages.get(0).getItemid(),messages.get(0).getSender());
                    }
                }
            }
            if(lists!=null&&lists.size()>0){
                lists1=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(lists.get(0).getTransactionid(),lists.get(0).getEbayId(),messages.get(0).getItemid());
            }
            List<String> palpays=new ArrayList<String>();
            List<String> grossdetailamounts=new ArrayList<String>();
            List<String> pictures=new ArrayList<String>();
            List<String> accs=new ArrayList<String>();
            for(TradingOrderGetOrders order:lists){
                List<TradingOrderGetSellerTransactions> sellerTransactions=iTradingOrderGetSellerTransactions.selectTradingOrderGetSellerTransactionsByTransactionId(order.getItemid(),order.getTransactionid());
                if(sellerTransactions!=null&&sellerTransactions.size()>0){
                    if(StringUtils.isNotBlank(sellerTransactions.get(0).getExternaltransactionid())){
                        palpays.add(sellerTransactions.get(0).getExternaltransactionid());
                        accs.add(sellerTransactions.get(0).getPaypalprice());
                    }
                }else{
                    palpays.add("");
                }
                List<TradingOrderGetAccount> accountlist=iTradingOrderGetAccount.selectTradingOrderGetAccountByTransactionId(order.getTransactionid());
                if(accountlist!=null&&accountlist.size()>0){
                    for(TradingOrderGetAccount account:accountlist){
                        if("成交費".equals(account.getDescription())){
                            grossdetailamounts.add(account.getGrossdetailamount());
                        }else{
                            grossdetailamounts.add("");
                        }
                    }
                }
                String ItemId=order.getItemid();
                List<TradingOrderGetItem> itemList= iTradingOrderGetItem.selectOrderGetItemByItemId(ItemId);
                if(itemList!=null&&itemList.size()>0){
                    Long pictureid=itemList.get(0).getPicturedetailsId();
                    List<TradingOrderPictureDetails> pictureDetailses=iTradingOrderPictureDetails.selectOrderGetItemById(pictureid);
                    if(pictureDetailses!=null&&pictureDetailses.size()>0){
                        pictures.add(pictureDetailses.get(0).getPictureurl());
                    }else{
                        pictures.add("");
                    }
                }

            }
            List<TradingOrderSenderAddress> senderAddresses=new ArrayList<TradingOrderSenderAddress>();
            if(lists1!=null&&lists1.size()>0){
                senderAddresses=iTradingOrderSenderAddress.selectOrderSenderAddressByOrderId(lists1.get(0).getOrderid());
                modelMap.put("orderId",lists1.get(0).getOrderid());
                modelMap.put("order",lists1.get(0));
                modelMap.put("sender",lists1.get(0).getBuyeruserid());
                modelMap.put("recipient",lists1.get(0).getSelleruserid());
                SystemLog log=SystemLogUtils.selectSystemLogByTableId(SystemLogUtils.ORDER_OPERATE_RECORD_REFUND,lists1.get(0).getId());
                if(log!=null){
                    modelMap.put("refundAmountDesc",log.getEventdesc());
                    modelMap.put("refundDate",log.getCreatedate());
                }
            }
            TradingOrderSenderAddress type1=new TradingOrderSenderAddress();
            TradingOrderSenderAddress type2=new TradingOrderSenderAddress();
            for(TradingOrderSenderAddress senderAddresse:senderAddresses){
                if("1".equals(senderAddresse.getType())){
                    type1=senderAddresse;
                }
                if("2".equals(senderAddresse.getType())){
                    type2=senderAddresse;
                }
            }
            if(lists!=null&&lists.size()>0){
                modelMap.put("flag","true");
            }else{
                modelMap.put("flag","false");
            }
        modelMap.put("addMessage1",addMessages1);
        modelMap.put("orders",lists);
        String rootpath=request.getContextPath();
        modelMap.put("rootpath",rootpath);
        modelMap.put("addresstype1",type1);
        modelMap.put("addresstype2",type2);
        modelMap.put("paypals",palpays);
        modelMap.put("grossdetailamounts",grossdetailamounts);
        modelMap.put("pictures",pictures);
        modelMap.put("messageID",messageID);
        if(messages!=null&&messages.size()>0){
            modelMap.put("ebayFlag",messages.get(0).getResponseenabled());
            modelMap.put("parentMessageID",messages.get(0).getExternalmessageid());
        }
        modelMap.put("messageFlag","true");
        modelMap.put("orderFlag","false");
        modelMap.put("accs",accs);
        return forword("orders/order/viewOrderGetOrders",modelMap);
    }

    @RequestMapping("/sendMessageGetmymessage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    /**初始化回复消息页面*/
    public ModelAndView sendMessageGetmymessage(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        String messageID=request.getParameter("messageid");
        Map m=new HashMap();
        m.put("messageID",messageID);
        List<MessageGetmymessageQuery> messages=iTradingMessageGetmymessage.selectMessageGetmymessageBySender(m);
        modelMap.put("message",messages.get(0));
        return forword("MessageGetmymessage/sendMessageGetmymessageList",modelMap);

    }

   /* @RequestMapping("/ajax/saveMessageGetmymessage.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    *//**回复消息*//*
    public void saveMessageGetmymessage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String messageid=request.getParameter("messageid");
        String sendHeader=request.getParameter("sendHeader");
        String content= request.getParameter("content");
        String emailCopyToSender=request.getParameter("emailCopyToSender");
        String displayToPublic=request.getParameter("displayToPublic");
        UsercontrollerDevAccountExtend dev= (UsercontrollerDevAccountExtend) request.getSession().getAttribute("dveId");
        Map<String,Object> parms=new HashMap<String, Object>();
        Map m=new HashMap();
        m.put("messageID",messageid);
        List<MessageGetmymessageQuery> messages=iTradingMessageGetmymessage.selectMessageGetmymessageBySender(m);
        MessageGetmymessageQuery message=messages.get(0);
        TradingMessageAddmembermessage tm=new TradingMessageAddmembermessage();
        tm.setItemid(message.getItemid());
        tm.setMessageid(messageid);
        tm.setBody(content);
        tm.setRecipientid(message.getSender());
        tm.setParentmessageid(message.getExternalmessageid());
        tm.setEmailcopytosender(emailCopyToSender);
        tm.setDisplaytopublic(displayToPublic);
        tm.setSender(message.getRecipientuserid());
        tm.setSubject(message.getSubject());
        parms.put("addMessage", tm);
        parms.put("ebayId",message.getEbayAccountId());
        parms.put("devAccount",dev);
        parms.put("url",apiUrl);
        parms.put("userInfoService",userInfoService);
        Map<String,String> map= GetMyMessageAPI.apiAddmembermessage(parms);
        String flag=map.get("msg");
        if(!"true".equals(flag)){
            tm.setReplied("false");
            iTradingMessageAddmembermessage.saveMessageAddmembermessage(tm);
            AjaxSupport.sendSuccessText("fail", map.get("par"));
        }else{
            tm.setReplied("true");
            iTradingMessageAddmembermessage.saveMessageAddmembermessage(tm);
            AjaxSupport.sendSuccessText("message",  map.get("par"));
        }
    }
*/
    @RequestMapping("/GetmymessageEbay.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    @ResponseBody
    public ModelAndView GetmymessageEbay(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
      /*  Asserts.assertTrue(commonParmVO.getId() != null, "参数获取失败！");*/
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays = systemUserManagerService.queryCurrAllEbay(map);
        List<Date> ebayDates=new ArrayList<Date>();
        for(UsercontrollerEbayAccountExtend ebay:ebays){
            List<TaskGetMessages> taskGetMessageses= iTaskGetMessages.selectTaskGetMessagesByFlagIsFalseOrderByLastSycTimeAndEbayName(ebay.getEbayName());
            if(taskGetMessageses!=null&&taskGetMessageses.size()>0){
                ebayDates.add(taskGetMessageses.get(taskGetMessageses.size()-1).getLastsyctime());
            }else{
                ebayDates.add(null);
            }
        }
        modelMap.put("ebayDates",ebayDates);
        modelMap.put("ebays",ebays);
        return forword("MessageGetmymessage/GetmymessageEbay",modelMap);
    }
    //选择消息模板初始化
    @RequestMapping("/selectSendMessage.do")
    public ModelAndView selectSendMessage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String transactionid=request.getParameter("transactionid");
        String ebayId=request.getParameter("ebayId");
        String paypal=request.getParameter("paypal");
        String itemId = request.getParameter("itemId");
        if(StringUtils.isNotBlank(transactionid)&& StringUtils.isNotBlank(ebayId)){
            List<TradingOrderGetOrders> orders=iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(transactionid,Long.valueOf(ebayId),itemId);
            if(orders.size()>0){
                List<TradingOrderGetItem> items=iTradingOrderGetItem.selectOrderGetItemByItemId(orders.get(0).getItemid());
                modelMap.put("order",orders.get(0));
                if(items.size()>0){
                    modelMap.put("item",items.get(0));
                }
            }
        }
        modelMap.put("date",new Date());
        modelMap.put("paypal",paypal);
        return forword("MessageGetmymessage/selectSendMessage",modelMap);
    }

    @RequestMapping("/apiGetMyMessagesRequest")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    /**获取接受信息总数*/
    public void apiGetMyMessagesRequest(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        //修改后--------------------
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
        List<TaskGetMessages> taskGetMessageses= iTaskGetMessages.selectTaskGetMessagesByFlagIsFalseOrderBysaveTime();
        for(Long ebay:ebays){
            if(taskGetMessageses!=null&&taskGetMessageses.size()>0){
                Long ebayNameID=taskGetMessageses.get(0).getEbayid();
                UsercontrollerEbayAccount ebay1=userInfoService.getEbayAccountByEbayID(ebay);
                if(ebay1.getId()!=ebayNameID){
                    Integer flag=taskGetMessageses.get(0).getTokenflag();
                    for(TaskGetMessages taskGetMessages:taskGetMessageses){
                     /*   if(ebay==taskGetMessages.getEbayid()){*/
                        if(ebay1.getId()==taskGetMessages.getEbayid()){
                            TaskGetMessages ta=new TaskGetMessages();
                            ta=taskGetMessages;
                            flag=flag-1;
                            ta.setTokenflag(flag<0?0:flag);
                            ta.setLastsyctime(new Date());
                            iTaskGetMessages.saveListTaskGetMessages(ta);
                        }
                    }
                }
            }else{
                Map map=new HashMap();
                List<UsercontrollerEbayAccountExtend> ebayAcounts=systemUserManagerService.queryCurrAllEbay(map);
                Date date=new Date();
                for(UsercontrollerEbayAccount ebayAcount:ebayAcounts){
                    Date date2=DateUtils.addDays(date,1);
                    Date date1=DateUtils.subDays(date2, 7);
                    Date end1= com.base.utils.common.DateUtils.turnToDateEnd(date2);
                    Date start1=com.base.utils.common.DateUtils.turnToDateStart(date1);
                    String start= DateUtils.DateToString(start1);
                    String end=DateUtils.DateToString(end1);
                    TaskGetMessages TaskGetMessages=new TaskGetMessages();
                    TaskGetMessages.setToken(ebayAcount.getEbayToken());
                    TaskGetMessages.setTokenflag(0);
                    TaskGetMessages.setFromtime(start);
                    TaskGetMessages.setEndtime(end);
                    TaskGetMessages.setSavetime(date);
                    TaskGetMessages.setUserid(ebayAcount.getUserId());
                    TaskGetMessages.setEbayid(ebayAcount.getId());
                    TaskGetMessages.setEbayname(ebayAcount.getEbayName());
                    iTaskGetMessages.saveListTaskGetMessages(TaskGetMessages);
                }
            }
        }

        //修改前-----------------
       /* //String ebayId=request.getParameter("ebayId");
        //Long ebay=Long.valueOf(ebayId);
        //测试环境
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiSiteid("0");
        //真实环境
    //    UsercontrollerDevAccountExtend d=new UsercontrollerDevAccountExtend();
        //d.setApiDevName("5d70d647-b1e2-4c7c-a034-b343d58ca425");
        //d.setApiAppName("sandpoin-23af-4f47-a304-242ffed6ff5b");
        //d.setApiCertName("165cae7e-4264-4244-adff-e11c3aea204e");
       // d.setApiCompatibilityLevel("881");
       // d.setApiSiteid("0");

        Map map=new HashMap();
        d.setApiCallName(APINameStatic.GetMyMessages);
        request.getSession().setAttribute("dveId", d);
        Date startTime2= com.base.utils.common.DateUtils.subDays(new Date(),6);
        Date endTime= DateUtils.addDays(startTime2, 6);
        //Date startTime2= com.base.utils.common.DateUtils.subDays(new Date(),120);
        //Date endTime= DateUtils.addDays(startTime2, 120);
        //MutualWithdrawalAgreementLate


        Date end1= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
        String start=DateUtils.DateToString(startTime2);
        String end= DateUtils.DateToString(end1);
        //测试环境
        String token=userInfoService.getTokenByEbayID(ebay);
        //真实环境
        //String token="AgAAAA**AQAAAA**aAAAAA**kRx8VA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AGloWiAZCCogSdj6x9nY+seQ**blACAA**AAMAAA**d0Px77QqgOj2GHC7XDNXkRKusIUT1y5uPdXz87hiC9ghsh75Q6hQb3BRbKwkJsFz3BlORq7L8lEiHsqBnFzd65yK1MJ/CQMsY165Q+4Rw664b0dP3vnPzjeN3cfKOkDwwoLqFGrMclvrrpntfSDBcO/r1QaC+CUB0GD6UiuhdyhBIPd1gb+z0KmYCTwpFENyHDzRtiTcT5qCt5eYfYzsve2e6O1c+NsTyBgJzUD1v78aIluxKhoC+huF9Uxscm2DU4mOr0JYONHJCs3dN18fKLp0Dc3hSvmPSIaxPmjcvlVfWuVPtw6KwXvxw8U8PGUdfACzb9ZIBiUEEhFHU6xv73egj2hkN/ZTJr7yu3l+qvDJFHLlgBMoprseFc0tmDi/hbRUILxuOy8TOpGri71DoQBzwuQxxrG5GMJ77NFLOLYxsH6/gpA/7+vFT1X5CUsIv+BYZyY7g3RLZWYem3Gqv9T+sVNC/DEhxmdO1Yx49rAwHcUw3aeXTrKpa1xCNkgHg4Feheu5V6Pu9lb5DQUC9YidqELrLEvos6yoiH31myqAmI72Gt4i7SBjwS8k5O+7xjxhDrKpg0IFwCdQk4PEByoBnud/dDNyCZkZdCqTkb36aqmgdnTANz9M7DtcQTH/Lf6h+Suj3RVSeFfDZcJJDax7Ie5qwte+oHJ6yTuBZ2dt4hMmKZIZwn26Ei+DUfCPhx6nEqcAOf6Sbxf8RxkWJ2pLcIvbifrditHIuyGjOf4yMoIHOcSp6FsVbmkMleBG";

        map.put("token", token);
        map.put("detail", "ReturnHeaders");
        map.put("startTime", start);
        map.put("endTime", end);
        String xml = BindAccountAPI.getGetMyMessages(map);//获取接受消息
        AddApiTask addApiTask = new AddApiTask();
        //Map<String, String> resMap = addApiTask.exec(d, xml, "https://api.ebay.com/ws/api.dll");
        //Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        TaskMessageVO taskMessageVO=new TaskMessageVO();
        taskMessageVO.setMessageContext("接受的消息");
        taskMessageVO.setMessageTitle("获取接受消息的");
        taskMessageVO.setMessageType(SiteMessageStatic.SYNCHRONIZE_GET_MESSAGE_TYPE);
        taskMessageVO.setBeanNameType(SiteMessageStatic.SYNCHRONIZE_GET_MESSAGE_BEAN);
        taskMessageVO.setMessageFrom("system");
        Map m=new HashMap();
        m.put("accountId",commonParmVO.getId());
        m.put("ebay",ebay);
        m.put("dev",d);
        taskMessageVO.setObjClass(m);
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        taskMessageVO.setMessageTo(sessionVO.getId());
        //测试环境
        addApiTask.execDelayReturn(d, xml, apiUrl, taskMessageVO);
        //真实环境
        //addApiTask.execDelayReturn(d, xml, "https://api.ebay.com/ws/api.dll", taskMessageVO);*/
        AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
    }
}
