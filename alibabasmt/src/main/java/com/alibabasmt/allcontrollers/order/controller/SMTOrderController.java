package com.alibabasmt.allcontrollers.order.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibabasmt.allservices.authorize.service.SmtAccountManService;
import com.alibabasmt.allservices.datadic.service.ISmtDataDictionary;
import com.alibabasmt.allservices.order.service.*;
import com.alibabasmt.database.datadic.model.SmtDataDictionary;
import com.alibabasmt.database.smtorder.model.*;
import com.alibabasmt.domains.querypojos.smtorder.OrderTableQuery;
import com.alibabasmt.domains.querypojos.smtaccount.SmtUserAccountExt;
import com.alibabasmt.utils.signature.api.APIStaticParm;
import com.alibabasmt.utils.signature.api.ApiCallService;
import com.alibabasmt.utils.signature.vo.APICallVO;
import com.alibabasmt.utils.signature.vo.SignatureVO;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.publicd.service.IPublicUserConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by Administrtor on 2015/3/20.
 */
@Controller
@RequestMapping("SMTorder")
public class SMTOrderController extends BaseAction {
    static Logger logger = Logger.getLogger(SMTOrderController.class);
    @Autowired
    private ISmtOrderTable iSmtOrderTable;
    @Autowired
    private ISmtOrderChildrenOrder iSmtOrderChildrenOrder;
    @Autowired
    private ISmtOrderDetails iSmtOrderDetails;
    @Autowired
    private SmtAccountManService smtAccountManService;
    @Autowired
    private ISmtDataDictionary iSmtDataDictionary;
    @Autowired
    private IPublicUserConfig iPublicUserConfig;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private ISmtOrderBuyerInfo iSmtOrderBuyerInfo;
    @Autowired
    private ISmtOrderReceiptAddress iSmtOrderReceiptAddress;
    /**打开授权页面主页*/
    @RequestMapping("orderPage.do")
    public ModelAndView bindSMTAccountPage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        /*MyStringUtil.generateRandomFilename();*/
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        List<PublicUserConfig> configs=new ArrayList<PublicUserConfig>();
        List<PublicUserConfig> list=iPublicUserConfig.selectUserConfigByItemType("smtOrderFolder",sessionVO.getId());
        for(PublicUserConfig config:list){
            String value=config.getConfigValue();
            if(StringUtils.isNotBlank(value)){
                configs.add(config);
            }
        }
        modelMap.put("folderNum",list.size());
        modelMap.put("folders",list);
        return forword("/alibabasmt/order/orderPage",modelMap);
    }
    /**打开同步订单页面主页*/
    @RequestMapping("ebayListPage.do")
    public ModelAndView ebayListPage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        List<SmtUserAccountExt> acounts= smtAccountManService.querySMTAccByOrg();
        modelMap.put("acounts",acounts);
        return forword("/alibabasmt/order/ebayListPage",modelMap);
    }
    /*同步速卖通订单*/
    @RequestMapping("/ajax/sycOrder.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void sycOrder(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String ids1=request.getParameter("id");
        if(StringUtils.isNotBlank(ids1)){
            String[] ids=ids1.split(",");
            for(String id1:ids){
                Long id= Long.valueOf(id1);
                APICallVO apiCallVO=new APICallVO();
                SignatureVO signatureVO=new SignatureVO();
                signatureVO.initVO();
                apiCallVO.setSmtAccountID(id);
                apiCallVO.setUrlPath(APIStaticParm.findOrderListSimpleQuery);
                Map<String,String> param=new HashMap<String, String>();
                //---------------------------------------------------------------------------
                Boolean flag=true;
                int i=1;
                while(flag){
                    param.put("page",i+"");
                    param.put("pageSize","50");
                    param.put("access_token", "");//access_token就设置为空，可以添加其他参数
                    apiCallVO.setParam(param);
                    apiCallVO.setSignatureVO(signatureVO);
                    String result =ApiCallService.callApi(apiCallVO);
                    Map jsons = JSON.parseObject(result, HashMap.class);
                    JSONArray orderList= (JSONArray) jsons.get("orderList");
                    if(orderList!=null&&orderList.size()>0){
                        List<SmtOrderTable> tables= iSmtOrderTable.parseSMTOrderAndSaveOrder(result,id);
                        for(SmtOrderTable table:tables){
                            iSmtOrderDetails.paseSmtOrderDetailsAndSave(table.getOrderid(),1L);
                        }
                        i=i+1;
                    }else{
                        flag=false;
                    }
                }
            }
            AjaxSupport.sendSuccessText("","同步成功");
        }else{
            AjaxSupport.sendFailText("fail","该账号不存在请核实");
        }


    }
    /**获取国家*/
    @RequestMapping("/selectCountry.do")
    @ResponseBody
    public void selectCountry(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String countryId=request.getParameter("countryId");
        List<SmtDataDictionary> lidata = iSmtDataDictionary.queryByTypeAndCountryId("country",Long.parseLong(countryId));
        AjaxSupport.sendSuccessText("", lidata);
    }
    //选择地区
    @RequestMapping("/selectCountrys.do")
    public ModelAndView selectCountrys(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String countryNames=request.getParameter("countryNames");
        String num=request.getParameter("num");
        List<SmtDataDictionary> lidata = iSmtDataDictionary.queryByType("delta");
        List<SmtDataDictionary> li1 = new ArrayList();
        List<String> names=new ArrayList<String>();
        List<String> countryIds=new ArrayList<String>();
        for(SmtDataDictionary tdd : lidata){
            if(tdd.getName1().equals("International")){
                li1.add(tdd);
                names.add(tdd.getName());
                countryIds.add(String.valueOf(tdd.getId()));
            }
        }
        String root=request.getContextPath();
        modelMap.put("countrys",li1);
        modelMap.put("count",li1.size());
        modelMap.put("names",names);
        modelMap.put("countryIds",countryIds);
        modelMap.put("root",root);
        modelMap.put("num",num);
        modelMap.put("countryNames",countryNames);
        return forword("/alibabasmt/order/selectCountrys",modelMap);
    }
    /**
     * 查看订单详情
     */
    @RequestMapping("/viewOrderGetOrders.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView viewTemplateInitTable(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String orderid=request.getParameter("orderid");
        SmtOrderDetails details=iSmtOrderDetails.selectSmtOrderDetailsByOrderId(orderid);
        List<SmtOrderChildrenOrder> childrenOrders= iSmtOrderChildrenOrder.selectSmtOrderChildrenOrderByParentId(orderid);
        SmtOrderTable orderTable= iSmtOrderTable.selectSmtOrderTableByOrderId(orderid);
        SmtOrderBuyerInfo buyerInfo= iSmtOrderBuyerInfo.selectSmtOrderBuyerInfoByLoginId(details.getBuyerloginid());
        SmtOrderReceiptAddress address= iSmtOrderReceiptAddress.selectSmtOrderReceiptAddressByOrderId(orderid);
        modelMap.put("details",details);
        modelMap.put("childrenOrders",childrenOrders);
        modelMap.put("orderTable",orderTable);
        modelMap.put("buyerInfo",buyerInfo);
        modelMap.put("address",address);
        modelMap.put("orderid",orderid);
        modelMap.put("smtAcountId",orderTable.getSmtAcountId());
        return forword("alibabasmt/order/viewOrderGetOrders",modelMap);
    }
    //添加备注
    @RequestMapping("/addComment.do")
    public ModelAndView addComment(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String ids=request.getParameter("ids");
        String[] ids1=ids.split(",");
        if(ids1.length==1){
            SmtOrderTable orderTable=iSmtOrderTable.selectSmtOrderTableById(Long.valueOf(ids1[0]));
            if(orderTable!=null){
                modelMap.put("order",orderTable);
            }
        }
        modelMap.put("ids",ids);
        return forword("/alibabasmt/order/addComment",modelMap);
    }
    //保存备注
    @RequestMapping("/saveComment.do")
    public void saveComment(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String ids=request.getParameter("ids");
        String comment=request.getParameter("comment");
        if(StringUtils.isNotBlank(ids)){
            String[] ids1=ids.split(",");
            for(String id:ids1){
                SmtOrderTable orderTable=iSmtOrderTable.selectSmtOrderTableById(Long.valueOf(id));
                orderTable.setComment(comment);
                iSmtOrderTable.saveSmtOrderTable(orderTable);
            }
            AjaxSupport.sendSuccessText("","添加备注成功");
        }else{
            AjaxSupport.sendFailText("fail","该订单不存在");
        }
    }
    //移动订单到指定文件夹初始化
    @RequestMapping("/moveFolder.do")
    public ModelAndView moveFolder(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String table=request.getParameter("table");
        String table1=request.getParameter("table1");
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        List<String> orderids=new ArrayList<String>();
        int i=0;
        while(i>=0){
            String order=request.getParameter("orderid["+i+"]");
            if(order!=null){
                orderids.add(order);
                i++;
            }else{
                i=-1;
            }
        }
        List<PublicUserConfig> list=iPublicUserConfig.selectUserConfigByItemType("smtOrderFolder",sessionVO.getId());
        List<PublicUserConfig> configs=new ArrayList<PublicUserConfig>();
        for(PublicUserConfig config:list){
            if(StringUtils.isNotBlank(config.getConfigValue())){
                configs.add(config);
            }
        }
        modelMap.put("folders",configs);
        modelMap.put("orderids",orderids);
        return forword("/alibabasmt/order/moveFolder",modelMap);
    }
    //保存订单到指定文件夹
    @RequestMapping("/saveFolder.do")
    public void saveFolder(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String folderId=request.getParameter("folderId");
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        int i=0;
        while(i>=0){
            String orderid=request.getParameter("orderid["+i+"]");
            if(orderid!=null&&StringUtils.isNotBlank(folderId)){
                SmtOrderTable order=iSmtOrderTable.selectSmtOrderTableByOrderId(orderid);
                order.setFolder(folderId);
                iSmtOrderTable.saveSmtOrderTable(order);
                i++;
            }else if(!StringUtils.isNotBlank(folderId)){
                i=-2;
            }else{
                i=-1;
            }
        }
        if(i==-2){
            AjaxSupport.sendFailText("fail","文件夹不存在");
            return;
        }
        AjaxSupport.sendSuccessText("","已经移动到指定的文件夹中");

    }
    /*
  *下载订单downloadOrders
  */
    @RequestMapping("/downloadOrders.do")
    public void  downloadOrders(HttpServletRequest request,HttpServletResponse response) throws Exception {

        String outputFile1= request.getSession().getServletContext().getRealPath("/");
        String outputFile=outputFile1+"download\\smtOrders.xls";
        String status=request.getParameter("status");
        String folderId=request.getParameter("folderId");
        String ids=request.getParameter("ids");
        List<SmtOrderTable> list=new ArrayList<>();
        List<UsercontrollerUserExtend> orgebays=systemUserManagerService.queryAllUsersByOrgID("yes");
        List<Long> ebays=new ArrayList<>();
        if(orgebays!=null&&orgebays.size()>0){
            for(UsercontrollerUserExtend ebay:orgebays){
                ebays.add(Long.valueOf(ebay.getUserId()));
            }
        }
        if(StringUtils.isNotBlank(ids)){
            String[] ids1=ids.split(",");
            for(String id:ids1){
                SmtOrderTable table=iSmtOrderTable.selectSmtOrderTableById(Long.valueOf(id));
                list.add(table);
            }
        }else{
            List<SmtOrderTable> tables=iSmtOrderTable.selectSmtOrderTableByStatusOrFolder(status,folderId,ebays);
            if(tables!=null&&tables.size()>0){
                list.addAll(tables);
            }
        }
        response.setHeader("Content-Disposition","attachment;filename=orders.xls");// 组装附件名称和格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        iSmtOrderTable.downloadOrders(list, outputFile,response);
    }
    /*
  *批量上传跟踪号
  */
    @RequestMapping("/modifyOrderNums.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView modifyOrderNums(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        List<String> ids=new ArrayList<String>();
        int i=0;
        while(i>=0){
            String id=request.getParameter("id["+i+"]");
            if(id!=null){
                ids.add(id);
                i++;
            }else{
                i=-1;
            }
        }
        List<SmtOrderDetails> orders=new ArrayList<SmtOrderDetails>();
        for(String id1:ids){
            Long id=Long.valueOf(id1);
            SmtOrderTable order1=iSmtOrderTable.selectSmtOrderTableById(id);
            SmtOrderDetails order=iSmtOrderDetails.selectSmtOrderDetailsByOrderId(order1.getOrderid());
            if(order!=null){
                orders.add(order);
            }
        }
        modelMap.put("orders",orders);
        return forword("alibabasmt/order/modifyOrderNums",modelMap);
    }
    /*
     *修改订单状态
     */
    @RequestMapping("/apiModifyOrdersMessage.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void apiModifyOrdersMessage(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        int i=0;
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        Map<String,String> map=new HashMap<>();
        String type=request.getParameter("refund");
        while (i>=0) {
            String orderid = request.getParameter("orderid"+i);
            i++;
            String ShipmentTrackingNumber = request.getParameter("ShipmentTrackingNumber"+i);
            String ShippingCarrierUsed = request.getParameter("ShippingCarrierUsed"+i);
            if(StringUtils.isNotBlank(orderid)) {
                SmtOrderTable orderTable=iSmtOrderTable.selectSmtOrderTableByOrderId(orderid);
                APICallVO apiCallVO=new APICallVO();
                SignatureVO signatureVO=new SignatureVO();
                signatureVO.initVO();
                apiCallVO.setSmtAccountID(orderTable.getSmtAcountId());//速卖通账户id
                apiCallVO.setUrlPath(APIStaticParm.sellerShipment);
                Map<String,String> param=new HashMap<String, String>();
                param.put("serviceName",ShippingCarrierUsed);
                param.put("logisticsNo",ShipmentTrackingNumber);
                param.put("sendType",type);
                param.put("outRef",orderid);
           /*     param.put("serviceName","");
                param.put("logisticsNo","");
                param.put("sendType","");
                param.put("outRef","");*/
                param.put("access_token", "");//access_token就设置为空，可以添加其他参数
                apiCallVO.setParam(param);
                apiCallVO.setSignatureVO(signatureVO);
                String result =ApiCallService.callApi(apiCallVO);
                Map jsons = JSON.parseObject(result, HashMap.class);
                Boolean flag= (Boolean) jsons.get("success");
                if(flag!=null&&flag){
                    continue;
                }else{
                    Object obj=jsons.get("message");
                    String message="";
                    if(obj!=null){
                        message= String.valueOf(obj);
                    }else{
                        obj=jsons.get("error_message");
                        if(obj!=null){
                            message= String.valueOf(obj);
                        }
                    }
                    map.put("message",message);
                    i=-1;
                }
            }else if(!StringUtils.isNotBlank(orderid)&&i==0){
                i=-3;
            }else{
                i=-2;
            }

        }
        if(i==-1){
            AjaxSupport.sendFailText("fail", map.get("message"));
        }
        if(i==-2){
            AjaxSupport.sendSuccessText("success", "订单状态修改成功");
        }
        if(i==-3){
            AjaxSupport.sendFailText("fail", "没找到数据,请核实!");
        }
    }
    /*
    *修改订单状态初始化
    */
    @RequestMapping("/modifyOrderStatus.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView modifyOrderStatus(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String orderid=request.getParameter("orderid");
        SmtOrderDetails details=iSmtOrderDetails.selectSmtOrderDetailsByOrderId(orderid);
        modelMap.put("order",details);
        return forword("alibabasmt/order/modifyOrderStatus",modelMap);
    }
    /*
         *发送消息初始化
         */
    @RequestMapping("/initOrdersSendMessage.do")
    public ModelAndView initOrdersSendMessage(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        String orderid=request.getParameter("orderid");
        SmtOrderTable orderTable=iSmtOrderTable.selectSmtOrderTableByOrderId(orderid);
        if(orderTable!=null){
            modelMap.put("smtAcountId",orderTable.getSmtAcountId());
        }
        modelMap.put("orderid",orderid);
        return forword("/alibabasmt/order/orderSendMessage",modelMap);
    }
    /*
    *订单中卖家发消息
    */
    @RequestMapping("/apiGetOrdersSendMessage.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void apiGetOrdersSendMessage(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        String orderid=request.getParameter("orderid");
        String body=request.getParameter("body");
        String buyerid=request.getParameter("buyerid");
        String smtAcountId=request.getParameter("smtAcountId");
        if(StringUtils.isNotBlank(body)){
            APICallVO apiCallVO=new APICallVO();
            SignatureVO signatureVO=new SignatureVO();
            signatureVO.initVO();
            apiCallVO.setSmtAccountID(Long.valueOf(smtAcountId));//速卖通账户id
            Map<String,String> param=new HashMap<String, String>();
            if(StringUtils.isNotBlank(orderid)){
                param.put("orderId",orderid);
                apiCallVO.setUrlPath(APIStaticParm.addOrderMessage);
            }else if(StringUtils.isNotBlank(buyerid)){
                param.put("buyerId",buyerid);
                apiCallVO.setUrlPath(APIStaticParm.addMessage);
            }
            param.put("content",body);
            param.put("access_token", "");//access_token就设置为空，可以添加其他参数
            apiCallVO.setParam(param);
            apiCallVO.setSignatureVO(signatureVO);
            String result =ApiCallService.callApi(apiCallVO);
            if("0".equals(result)){
                AjaxSupport.sendSuccessText("","发送成功");
            }else{
                SmtDataDictionary dictionary=iSmtDataDictionary.queryByTypeAndName("OrderMessageErrorCode",result);
                if(dictionary!=null){
                    AjaxSupport.sendSuccessText("","发送失败:"+dictionary.getValue());
                }else{
                    AjaxSupport.sendSuccessText("","发送失败,错误码为:"+result);
                }
            }
        }else{
            AjaxSupport.sendFailText("fail","内容不能为空");
            return;
        }

    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadSMTorderList.do")
    @ResponseBody
    public void loadSMTorderList(CommonParmVO commonParmVO,HttpServletRequest request,Date endtime,Date starttime) throws Exception {
        String status=request.getParameter("status");
        String countryQ=request.getParameter("countryQ");
        String daysQ=request.getParameter("daysQ");
        String starttime1=request.getParameter("starttime1");
        String endtime1=request.getParameter("endtime1");
        String itemType=request.getParameter("itemType");
        String content=request.getParameter("content");
        String statusQ=request.getParameter("statusQ");
        String folderId=request.getParameter("folderId");
        if(StringUtils.isBlank(statusQ)){
            statusQ=null;
        }
        if(StringUtils.isBlank(folderId)){
            folderId=null;
        }
        if(StringUtils.isBlank(daysQ)){
            daysQ=null;
        }else{
            if("2".equals(daysQ)){
                starttime=DateUtils.subDays(new Date(),1);
                Date endTime= DateUtils.addDays(starttime, 0);
                endtime= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
            }else{
                int day=Integer.parseInt(daysQ);
                starttime=DateUtils.subDays(new Date(),day-1);
                Date endTime= DateUtils.addDays(starttime,day-1);
                endtime= com.base.utils.common.DateUtils.turnToDateEnd(endTime);
            }
        }
        if(StringUtils.isBlank(status)){
            status=null;
        }
        if(StringUtils.isBlank(countryQ)){
            countryQ=null;
        }
        if(StringUtils.isBlank(itemType)){
            itemType=null;
        }
        if(StringUtils.isBlank(content)){
            content=null;
        }
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map m=new HashMap();
        if(daysQ!=null){
            m.put("dayQ",daysQ);
        }else{
            if(StringUtils.isNotBlank(starttime1)){
                starttime= DateUtils.turnToDateStart(DateUtils.StringToDate1(starttime1));
            }
            if(StringUtils.isNotBlank(endtime1)){
                endtime=DateUtils.turnToDateEnd(DateUtils.StringToDate1(endtime1));
            }
        }
        m.put("endtime",endtime);
        m.put("starttime",starttime);
        m.put("status",status);
        m.put("countryQ",countryQ);
        m.put("itemType",itemType);
        m.put("content",content);
        m.put("statusQ",statusQ);
        m.put("folderId",folderId);
        List<OrderTableQuery> list =iSmtOrderTable.selectSmtOrderTableList(m, page);
        jsonBean.setList(list);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }


}
