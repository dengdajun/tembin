package com.assess.controller;

import com.base.database.trading.model.TradingFeedBackDetail;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingFeedBackDetail;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateParseException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Administrtor on 2014/12/10.
 */
@Controller
public class ClientAssessController extends BaseAction{

    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ClientAssessController.class);
    @Autowired
    private ITradingFeedBackDetail iTradingFeedBackDetail;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private UserInfoService userInfoService;
    @Value("${EBAY.API.URL}")
    public String apiUrl;//api的调用地址
    /**
     * 客户评价管理界面
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/clientassess/clientAssessManager.do")
    public ModelAndView clientAssessManager(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws DateParseException {
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        Map map=new HashMap();
        List<UsercontrollerEbayAccountExtend> ebays = systemUserManagerService.queryCurrAllEbay(map);
        List<UsercontrollerUserExtend> orgUsers=systemUserManagerService.queryAllUsersByOrgID("yes");
        for(UsercontrollerUserExtend orgUser:orgUsers){
            if(orgUser.getUserId()==sessionVO.getId()&&orgUser.getUserParentId()==null){
                ebays=systemUserManagerService.queryACurrAllEbay(map);
            }
        }
        modelMap.put("ebays",ebays);
        return forword("clientassess/clientassessmanager",modelMap);
    }

    /**
     * 加载界面数据
     * @param request
     * @param modelMap
     * @param commonParmVO
     * @throws DateParseException
     */
    @RequestMapping("/ajax/loadClientAssessTable.do")
    @ResponseBody
    public void loadClientAssessTable(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,CommonParmVO commonParmVO) throws DateParseException {
        Map m = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        m.put("userid",c.getId());
        String commentType = request.getParameter("commentType");
        String commentAmount = request.getParameter("commentAmount");
        if(commentType!=null&&!"".equals(commentType)){
            m.put("commentType",commentType);
        }
        if(commentAmount!=null&&!"".equals(commentAmount)){
            m.put("commentAmount",commentAmount);
        }
        String selecttype = request.getParameter("selecttype");
        String selectvalue = request.getParameter("selectvalue");
        if(StringUtils.isNotEmpty(selecttype)){
            m.put("selecttype",selecttype);
            if(StringUtils.isNotEmpty(selectvalue)){
                m.put("selectvalue",selectvalue);
            }
        }

        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<TradingFeedBackDetail> paypalli = this.iTradingFeedBackDetail.selectClientAssessFeedBackList(m,page);
        jsonBean.setList(paypalli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    /**
     * 加载界面数据
     * @param request
     * @param modelMap
     * @param commonParmVO
     * @throws DateParseException
     */
    @RequestMapping("/ajax/replyFeedBack.do")
    @ResponseBody
    public void replyFeedBack(HttpServletRequest request,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,CommonParmVO commonParmVO) throws DateParseException {
        Map m = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        m.put("userid",c.getId());
        String id = request.getParameter("id");
        String reply = request.getParameter("reply");
        TradingFeedBackDetail tfbd = this.iTradingFeedBackDetail.selectByFeedBackId(Long.parseLong(id));
        if("1".equals(tfbd.getReplyflag())){
            AjaxSupport.sendFailText("", "已回复，不能重复回！");
        }
        List<UsercontrollerEbayAccount> liue = this.userInfoService.selectByEbayName(tfbd.getEbayAccount());
        String token = "";
        if(liue!=null&&liue.size()>0){
            UsercontrollerEbayAccount ue = this.userInfoService.getEbayAccountByEbayID(liue.get(0).getId());
            token = ue.getEbayToken();
        }
        if(token==null||"".equals(token)){
            AjaxSupport.sendFailText("", "按用户名未找到对应的token，不能回复，请联系系统管理员！");
        }
        String xml = this.getXml(tfbd,reply,token);
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.RespondToFeedback);
        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if ("fail".equalsIgnoreCase(r1)) {
            logger.error("回复评价信息报错，返回报文为："+res);
            AjaxSupport.sendFailText("", "回复评价信息报错，返回报文为："+res);
        }
        String ack = null;
        try {
            ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        } catch (Exception e) {
            logger.error("提取报文报错，返回报文为："+res);
            AjaxSupport.sendFailText("", "提取报文报错，返回报文为："+res);
            e.printStackTrace();
        }
        if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
            tfbd.setReplyflag("1");
            tfbd.setReplytext(reply);
            this.iTradingFeedBackDetail.saveTradingFeedBackDetailMy(tfbd);
            AjaxSupport.sendSuccessText("","操作成功！");
        }else{
            logger.error("回复评价信息报错，返回报文为："+res);
            AjaxSupport.sendFailText("", "回复评价信息报错，返回报文为："+res);
        }
    }

    public String getXml(TradingFeedBackDetail tfbd,String reply,String token){
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<RespondToFeedbackRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "    <RequesterCredentials>\n" +
                "        <eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "    </RequesterCredentials>\n" +
                "    <FeedbackID>"+tfbd.getFeedbackid()+"</FeedbackID>\n" +
                "    <ItemID>"+tfbd.getItemid()+"</ItemID>\n" +
                "    <ResponseText>"+reply+"</ResponseText>\n" +
                "    <ResponseType>Reply</ResponseType>\n" +
                "    <TargetUserID>"+tfbd.getCommentinguser()+"</TargetUserID>\n" +
                "    <TransactionID>"+tfbd.getTransactionid()+"</TransactionID>\n" +
                "</RespondToFeedbackRequest>";

        return xml;
    }

}
