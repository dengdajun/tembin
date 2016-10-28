package com.alibabasmt.allcontrollers.message.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibabasmt.allservices.authorize.service.SmtAccountManService;
import com.alibabasmt.allservices.message.service.ISmtMessage;
import com.alibabasmt.domains.querypojos.smtaccount.SmtUserAccountExt;
import com.alibabasmt.utils.signature.api.APIStaticParm;
import com.alibabasmt.utils.signature.api.ApiCallService;
import com.alibabasmt.utils.signature.vo.APICallVO;
import com.alibabasmt.utils.signature.vo.SignatureVO;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.SessionCacheSupport;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * Created by Administrtor on 2015/4/13.
 */
@Controller
@RequestMapping("SMTmessage")
public class SMTMessageController extends BaseAction {
    @Autowired
    private SmtAccountManService smtAccountManService;
    @Autowired
    private ISmtMessage iSmtMessage;
    /**打开授权页面主页*/
    @RequestMapping("messagePage.do")
    public ModelAndView bindSMTAccountPage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        /*MyStringUtil.generateRandomFilename();*/
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        return forword("/alibabasmt/message/messagePage",modelMap);
    }
    /**打开同步订单页面主页*/
    @RequestMapping("ebayListPage.do")
    public ModelAndView ebayListPage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        List<SmtUserAccountExt> acounts= smtAccountManService.querySMTAccByOrg();
        modelMap.put("acounts",acounts);
        return forword("/alibabasmt/message/ebayListPage",modelMap);
    }
    /*同步速卖通订单*/
    @RequestMapping("/ajax/sycMessage.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void sycOrder(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String ids1=request.getParameter("id");
        if(StringUtils.isNotBlank(ids1)){
            AjaxSupport.sendSuccessText("", "同步成功,请稍后刷新!");
            String[] ids=ids1.split(",");
            for(String id1:ids){
                Long id= Long.valueOf(id1);
                APICallVO apiCallVO=new APICallVO();
                SignatureVO signatureVO=new SignatureVO();
                signatureVO.initVO();
                apiCallVO.setSmtAccountID(id);
                apiCallVO.setUrlPath(APIStaticParm.queryMessageList);
                Map<String,String> param=new HashMap<String, String>();
                //---------------------------------------------------------------------------
                Boolean flag=true;
                int i=1;
                while(flag){
                    param.put("currentPage",i+"");
                    param.put("pageSize","50");
                    param.put("access_token", "");//access_token就设置为空，可以添加其他参数
                    apiCallVO.setParam(param);
                    apiCallVO.setSignatureVO(signatureVO);
                    String result = ApiCallService.callApi(apiCallVO);
                    Map jsons = JSON.parseObject(result, HashMap.class);
                    JSONArray msgList= (JSONArray) jsons.get("msgList");
                    if(msgList!=null&&msgList.size()>0){
                        iSmtMessage.parseSmtMessageAndSave(jsons,id);
                        i++;
                    }else{
                        flag=false;
                    }
                }
            }
        }else{
            AjaxSupport.sendFailText("fail","该账号不存在请核实");
        }
    }
    /**获取list数据的ajax方法*/
    @RequestMapping("/ajax/loadSMTmessageList.do")
    @ResponseBody
    public void loadSMTmessageList(CommonParmVO commonParmVO,HttpServletRequest request) throws Exception {
        /*String status=request.getParameter("status");
        String countryQ=request.getParameter("countryQ");
        String daysQ=request.getParameter("daysQ");
        String starttime1=request.getParameter("starttime1");
        String endtime1=request.getParameter("endtime1");
        String itemType=request.getParameter("itemType");
        String content=request.getParameter("content");
        String statusQ=request.getParameter("statusQ");
        String folderId=request.getParameter("folderId");*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        Map m=new HashMap();
        /*m.put("status",status);
        m.put("countryQ",countryQ);
        m.put("itemType",itemType);
        m.put("content",content);
        m.put("statusQ",statusQ);
        m.put("folderId",folderId);*/
        //List<OrderTableQuery> list =iSmtOrderTable.selectSmtOrderTableList(m, page);
        /*jsonBean.setList(list);
        jsonBean.setTotal((int)page.getTotalCount());*/
        AjaxSupport.sendSuccessText("", jsonBean);
    }
}
