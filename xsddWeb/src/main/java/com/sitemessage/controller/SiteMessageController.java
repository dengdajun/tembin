package com.sitemessage.controller;

import com.base.database.sitemessage.model.CustomPublicSitemessage;
import com.base.database.sitemessage.model.PublicSitemessage;
import com.base.database.trading.model.TradingMessageGetmymessage;
import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.mailUtil.MailUtils;
import com.base.utils.threadpool.TaskMessageVO;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.ITradingMessageGetmymessage;
import com.base.utils.mailUtil.SMSUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrtor on 2014/9/4.
 */
@Controller
@RequestMapping("sitemessage")
public class SiteMessageController extends BaseAction {
    static Logger logger = Logger.getLogger(SiteMessageController.class);

    @Autowired
    private SiteMessageService siteMessageService;
    @Autowired
    private ITradingMessageGetmymessage iTradingMessageGetmymessage;
    @Autowired
    private SystemUserManagerService systemUserManagerService;

    /**消息列表页面*/
    @RequestMapping("siteMessagePage.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView siteMessagePage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        modelMap.put("mtype", SiteMessageStatic.messageMap);
        return forword("/sitemessage/siteMessagePage",modelMap);
    }

    /**查看消息的页面*/
    @RequestMapping("readMessagePage.do")
    public ModelAndView readMessagePage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,Long messageId){
        if(messageId!=null){
            modelMap.put("messageId",messageId);
        }
        return forword("/sitemessage/readMessagePage",modelMap);
    }

    @RequestMapping("selectSiteMessage.do")
    @ResponseBody
    /**分页查询站内信息列表*/
    public void selectSiteMessage(CommonParmVO commonParmVO,PublicSitemessage publicSitemessage){
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<CustomPublicSitemessage> customPublicSitemessages=new ArrayList<>();
        String type=publicSitemessage.getMessageType();
        if(StringUtils.isNotBlank(type)&&"feed_back_message".equals(type)){
            customPublicSitemessages = siteMessageService.querySiteMessage1(publicSitemessage, page);
        }else{
            customPublicSitemessages = siteMessageService.querySiteMessage(publicSitemessage, page);
        }
        List<TradingMessageGetmymessage> messages=iTradingMessageGetmymessage.selectMessageGetmymessageByNoRead("false");
        if("num".equalsIgnoreCase(commonParmVO.getStrV1())){
            String r="{\"systemMessageNum\":\""+page.getTotalCount()+"\",\"ebayMessageNum\":\""+messages.size()+"\"}";
            AjaxSupport.sendSuccessText("",r);
            return;
        }
        if(customPublicSitemessages!=null){
            for (CustomPublicSitemessage c : customPublicSitemessages){
                if(StringUtils.isNotEmpty(c.getMessageType())){
                   String ty=StringUtils.replaceEach(c.getMessageType(), new String[]{"_SUCCESS", "_FAIL"}, new String[]{"", ""});
                    String nty=SiteMessageStatic.messageMap.get(ty)==null?"":(String)SiteMessageStatic.messageMap.get(ty);
                    c.setMessageType(nty);
                }
            }
        }

        jsonBean.setList(customPublicSitemessages);
        jsonBean.setTotal((int) page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    /**查看并标记*/
    @RequestMapping("readSiteMessage.do")
    @ResponseBody
    public void readSiteMessage(PublicSitemessage publicSitemessage){
        Asserts.assertTrue(publicSitemessage.getId() != null, "id不能为空");
        CustomPublicSitemessage customPublicSitemessage = siteMessageService.fetchSiteMessage(publicSitemessage);
        customPublicSitemessage.setMessage(StringEscapeUtils.unescapeHtml(customPublicSitemessage.getMessage()));

        AjaxSupport.sendSuccessText("",customPublicSitemessage);
    }

    @RequestMapping("markReaded.do")
    @ResponseBody
    @AvoidDuplicateSubmission(needRemoveToken = true)
    /**标记*/
    public void markReaded(String[] ids){
        Map map=new HashMap();
        if (!ObjectUtils.isLogicalNull(ids)){
            map.put("idArray",ids);
        }
        siteMessageService.batchSetReaded(map);
        AjaxSupport.sendSuccessText("", "标记成功！");
    }
    /**用户反馈的意见*/
    @RequestMapping("/ajax/feedBackMessage.do")
    @ResponseBody
    public void feedBackMessage(String content,String email) throws Exception {
        UsercontrollerUser user=systemUserManagerService.selectUsercontrollerUserByEail1("caixu23@126.com");
        //-------------------------------------
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String messageId=sdf.format(date);
        //--------------------------------------
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        TaskMessageVO taskMessageVO = new TaskMessageVO();
        taskMessageVO.setMessageType(SiteMessageStatic.FEED_BACK_MESSAGE);
        taskMessageVO.setMessageContext(content);
        taskMessageVO.setMessageTitle(email);
        taskMessageVO.setMessageTo(Long.valueOf(user.getUserId()));
        taskMessageVO.setMessageFrom(sessionVO.getId() + "");
        taskMessageVO.setMessageId(messageId + sessionVO.getLoginId());
        try {
            SMSUtils.sendSMS("13550329026","有反馈信息！-----------","notice");
            String phone=sessionVO.getTelPhone();
            if (StringUtils.isNotBlank(phone)){
                phone=phone.trim();
                if (phone.length()==11){
                    SMSUtils.sendSMS(phone,"您的反馈信息已经收到，为了更及时的解决您的问题加QQ群 244373066 谢谢支持！","notice");
                }
            }
            Email emaill = new HtmlEmail();
            emaill.addTo(sessionVO.getLoginId());
            emaill.setSubject("tembin已经收到您的反馈消息！");
            emaill.setMsg("您好！您的反馈信息我们已经收到！请稍后查看菜单：系统消息页面，查询意见反馈信息分类，查看回复！谢谢！<br/>" +
                    "如需更多帮助请加群 244373066  <br/>");
            MailUtils mailUtils= ApplicationContextUtil.getBean(MailUtils.class);
            mailUtils.sendMail(emaill);
        } catch (Exception e) {
            logger.error(e);
        }
        siteMessageService.addSiteMessage(taskMessageVO);
        AjaxSupport.sendSuccessText("", "反馈成功！");
    }

    /**查看消息的页面*/
    @RequestMapping("initSendMessage.do")
    public ModelAndView initSendMessage(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,HttpServletRequest request){
        String id=request.getParameter("id");
        PublicSitemessage sitemessage=siteMessageService.selectPublicSitemessageById(Long.valueOf(id));
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        Map map=new HashMap();
        Page page=new Page();
        map.put("messageFrom",sitemessage.getMessageFrom());
        map.put("messageTo",sitemessage.getMessageTo());
        map.put("messageId",sitemessage.getMessageId());
        page.setCurrentPage(1);
        page.setPageSize(Integer.MAX_VALUE);
        List<CustomPublicSitemessage> customPublicSitemessages= siteMessageService.selectPublicSitemessageByMessageId(map,page);
        modelMap.put("sitemessage", sitemessage);
        modelMap.put("messages",customPublicSitemessages);
        modelMap.put("loginId",sessionVO.getId());
        if(StringUtils.isNotBlank(sitemessage.getMessageFrom())&&sessionVO.getId()!=Integer.valueOf(sitemessage.getMessageFrom())){
            modelMap.put("messageFrom",sitemessage.getMessageFrom());
            modelMap.put("messageTo",sitemessage.getMessageTo());
        }else{
            modelMap.put("messageFrom",sitemessage.getMessageTo());
            modelMap.put("messageTo",sitemessage.getMessageFrom());
        }

        return forword("/sitemessage/initSendMessage",modelMap);
    }

    /**回复用户反馈的意见*/
    @RequestMapping("/sendMessage.do")
    @ResponseBody
    public void sendMessage(String messageFrom,
                            String messageTo,
                            String body,
                            String subject,
                            String messageId) throws Exception {

        if(StringUtils.isBlank(body)){
            AjaxSupport.sendFailText("fail", "内容不能为空!");
            return;
        }
        if(StringUtils.isBlank(messageFrom)){
            AjaxSupport.sendFailText("fail", "发件箱为空!");
            return;
        }
        TaskMessageVO taskMessageVO = new TaskMessageVO();
        taskMessageVO.setMessageType(SiteMessageStatic.FEED_BACK_MESSAGE);
        taskMessageVO.setMessageContext(body);
        taskMessageVO.setMessageTitle(subject);
        taskMessageVO.setMessageTo(Long.valueOf(messageFrom));
        taskMessageVO.setMessageFrom(messageTo);
        taskMessageVO.setMessageId(messageId);
        siteMessageService.addSiteMessage(taskMessageVO);
        AjaxSupport.sendSuccessText("", "发送成功！");
    }
}
