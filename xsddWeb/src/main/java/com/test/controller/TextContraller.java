package com.test.controller;

import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.trading.model.TradingDataDictionary;
import com.base.database.userinfo.model.SystemLog;
import com.base.database.userinfo.model.UsercontrollerOrg;
import com.base.database.userinfo.model.UsercontrollerUser;
import com.base.domains.DictQuery.DataDicShipingQueryParamVO;
import com.base.domains.LoginVO;
import com.base.domains.SessionVO;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.ObjectUtils;
import com.base.utils.common.SystemLogUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.exception.RedirectAssertsException;
import com.base.utils.ftpabout.service.FTPservice;
import com.base.utils.mailUtil.MailUtils;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.google.code.kaptcha.Constants;
import com.test.service.Test1Service;
import com.test.service.TestService;
import com.trading.service.ITradingDataDictionary;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by chace.cai on 2014/6/20.
 * 测试
 */
@Controller
public class TextContraller extends BaseAction {
    static Logger logger = Logger.getLogger(TextContraller.class);
    @Autowired
    private TestService testService;
    @Autowired
    private Test1Service test1Service;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private FTPservice ftPservice;

    @Autowired
    private ITradingDataDictionary tradingDataDictionary;


    @RequestMapping("/testlogin.do")
    public ModelAndView test(HttpServletRequest request,HttpServletResponse response,
                             @ModelAttribute( "initSomeParmMap" )ModelMap modelMap)throws Exception{
        DataDicShipingQueryParamVO m=new DataDicShipingQueryParamVO();
        m.setOrgID(17L);
        m.setSiteID(311L);
        List<String> sss=new ArrayList<>();
        sss.add("USPSPriority");
        sss.add("UPSGround");
        sss.add("UPSNextDay");
        sss.add("USPSParcel");
        m.setShipingString(sss);
        List<String> x = DataDictionarySupport.checkShipingIsExit(m);

        return forword("test",modelMap);
    }

    /**登录后的主框架页面*/
    @RequestMapping("/mainFrame.do")
    public ModelAndView mainFrame(HttpServletRequest request,HttpServletResponse response,
                             @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        // Map map = new HashMap();
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();
        modelMap.put("ccc",sessionVO.getUserName());
        return forword("mainFrame",modelMap);
    }

   /* *//**注册用户*/
    @RequestMapping("doReglogin.do")
    @ResponseBody
    public void doReg(UsercontrollerUser user ,UsercontrollerOrg org,String yqm,
                              @ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
       // Asserts.assertTrue(false,"尚未开放注册");
        //Asserts.assertTrue(ObjectUtils.isNumOrChar(user.getUserLoginId()),"登录名只能由数字和字母组成!");
        Asserts.assertTrue(StringUtils.isNotEmpty(user.getUserEmail()),"邮箱不能为空!");
        Asserts.assertTrue(StringUtils.isNotEmpty(user.getUserPassword()), "密码不能为空!");
        if ("电话号码".equalsIgnoreCase(user.getTelPhone())){
            user.setTelPhone("");
        }
        //Asserts.assertTrue(StringUtils.isNotBlank(yqm),"请输入邀请码");
        user.setUserLoginId(user.getUserEmail());
        Map map =new HashMap();
        map.put("UsercontrollerUser",user);
        map.put("UsercontrollerOrg",org);
        yqm=yqm.trim();
        map.put("code",yqm);
        userInfoService.regInsertUserInfo(map);
        AjaxSupport.sendSuccessText("success","申请已提交，请等待审核！");
    }


    /**登录操作*/
    @RequestMapping("/login.do")
    public ModelAndView login(LoginVO loginVO,HttpServletRequest request,HttpServletResponse response,
                              @ModelAttribute( "initSomeParmMap" )ModelMap modelMap,
                              RedirectAttributes redirectAttributes
                              ){
        if(ObjectUtils.isLogicalNull(loginVO.getLoginId()) || "请输入你的帐号".equalsIgnoreCase(loginVO.getLoginId())){
            request.getSession().setAttribute("errMessage_","登信息为空");
            return redirect("/login.jsp");
        }
        //首先检查是否需要检查验证码，以及验证验证码是否正确
        Object vcode = request.getSession().getAttribute("valCapCode");
        if(vcode!=null && "yes".equalsIgnoreCase((String)vcode)){
            String code = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
            if (StringUtils.isBlank(code)){
                request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, "ghgh+_-Tembin=");
                request.getSession().setAttribute("showCapImage","yes");
                request.getSession().setAttribute("valCapCode","yes");
                request.getSession().setAttribute("errMessage_","请输入验证码");
                return redirect("/login.jsp");
            }
            if(!code.equalsIgnoreCase(loginVO.getCapcode())){
                request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, "ghgh+_-Tembin=");
                request.getSession().setAttribute("showCapImage","yes");
                request.getSession().setAttribute("valCapCode","yes");
                request.getSession().setAttribute("errMessage_","验证码错误或失效!请重新获取并输入验证码");
                return redirect("/login.jsp");
            }
        }
       //检查账户密码师傅正确
        SessionVO sessionVO = null;
        try {
            sessionVO = userInfoService.getUserInfo(loginVO);
        } catch (RedirectAssertsException e) {
            request.getSession().setAttribute("errMessage_",e.getMessage());
            return redirect("/login.jsp");

        }
        if(ObjectUtils.isLogicalNull(sessionVO)){
            request.getSession().setAttribute("errMessage_","帐号或者密码不正确");
            Object num=request.getSession().getAttribute("loginFailNum_");
            if(num==null){
                request.getSession().setAttribute("loginFailNum_",1);
            }else {
                Integer i=(Integer)num+1;
                request.getSession().setAttribute("loginFailNum_",i);
            }
            Integer ii = (Integer) request.getSession().getAttribute("loginFailNum_");
            if(ii>3){//如果输入次数超过了3次，那么就需要输入验证码
                request.getSession().setAttribute("showCapImage","yes");
                request.getSession().setAttribute("valCapCode","yes");
            }

            return redirect("/login.jsp");
        }
        request.getSession().removeAttribute("valCapCode");
        request.getSession().setAttribute(SessionCacheSupport.USERLOGINID, sessionVO.getLoginId());
        sessionVO.setLoginId(sessionVO.getLoginId());
        SessionCacheSupport.remove(sessionVO.getLoginId());
        sessionVO.setSessionID(request.getSession().getId());
        sessionVO.setLoginTime(new Date());
        SessionCacheSupport.put(sessionVO);

        SystemLog systemLog=new SystemLog();
        systemLog.setCreatedate(new Date());
        systemLog.setEventname(SystemLogUtils.USER_LOGIN_LOG);
        systemLog.setOperuser(sessionVO.getLoginId());
        systemLog.setEventdesc("用户登陆！");
        try {
            SystemLogUtils.saveLog(systemLog);
        } catch (Exception e) {
            logger.error("记录登陆日志报错!",e);
        }
        return redirect("mainFrame.do");
        //modelMap.put("ccc",sessionVO.getUserName());
        //return forword("test",modelMap);
    }
    /**登出操作*/
    @RequestMapping("/logout.do")
    public ModelAndView logout(HttpServletRequest request) throws IOException {
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        SessionCacheSupport.remove(sessionVO.getLoginId());
        HttpSession session = request.getSession();
        //session.removeAttribute(SessionCacheSupport.USERLOGINID);
        session.invalidate();
        return redirect("/login.jsp");
    }

    @RequestMapping("/test1.do")
    @ResponseBody
    public void test1(HttpServletRequest request){
        SessionVO c= SessionCacheSupport.getSessionVO();
        //List<TradingDataDictionary> x= tradingDataDictionary.selectDictionaryByType("site");
        List<TradingDataDictionary> x = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        List<PublicDataDict> x1=DataDictionarySupport.getPublicDataDictionaryByType("category");
        List<PublicUserConfig> x2=DataDictionarySupport.getPublicUserConfigByType("ebayaccount",c.getId());


        ApplicationContext xc= ApplicationContextUtil.getContext();
        CacheManager cacheManager= (CacheManager) xc.getBean(CacheManager.class);
        String[] ss = cacheManager.getCacheNames();//查询有哪些缓存库
        Cache cache =cacheManager.getCache("dataDictionaryCache");
        List ll = cache.getKeys();

        request.getSession().setAttribute("vvv","eee");
        //testService.serviceTest();
        TestVO testVO=new TestVO();
        TestVO testVO1=new TestVO();
        testVO1.setBb("的发");
        TestVO testVO2=new TestVO();
        List<TestVO> testVOs=new ArrayList<TestVO>();
        testVOs.add(testVO);
        testVOs.add(testVO1);
        testVOs.add(testVO2);
//Integer.parseInt("f");
        AjaxSupport.sendSuccessText("啊", c);
    }

    @RequestMapping("/test2.do")
    @ResponseBody
    public void test2(HttpServletRequest request) throws Exception {

        CommAutowiredClass x=ApplicationContextUtil.getBean(CommAutowiredClass.class);
        System.out.println(x.getSiteURL("3"));

        //request.getSession().setAttribute("vvv","eee");
        //testService.serviceTest();
       // testService.testReturnPolicy();
//userInfoService.ebayIsBindDev(6L);
        AjaxSupport.sendSuccessText("啊", x.getSiteURL("3"));
    }
    @RequestMapping("/xxlogin.do")
    @ResponseBody
    public void xxlogin(String u,String p) throws Exception {
        Map map=new HashMap();
        map.put("passw",p);
        ftPservice.changeFtpPassWord(map);
        AjaxSupport.sendSuccessText("","a");

    }

    /**发送修改密码的验证码*/
    @RequestMapping("sometest.do")
    public ModelAndView sometest(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        return forword("sometest",modelMap);
        /*Email email = new SimpleEmail();
        email.addTo("caixu23@qq.com");
        email.setSubject("这个是标题");
        email.setMsg("这个是测试邮件");

        MailUtils mailUtils= (MailUtils) ApplicationContextUtil.getBean("mailUtils");
        mailUtils.sendMail(email);
        AjaxSupport.sendSuccessText("","a");*/
    }
}
