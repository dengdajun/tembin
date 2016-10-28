package com.userinfo.controller;

import com.base.database.trading.model.UsercontrollerDevAccount;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.userinfo.model.UsercontrollerVipUser;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.CommonParmVO2;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.sampleapixml.BindAccountAPI;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.common.MyNumberUtils;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.scheduleother.StaticParam;
import com.base.utils.scheduleother.service.SystemVIPUserCheckService;
import com.base.utils.scheduleother.service.SystemVipUserCostFeeService;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.trading.service.ITradingFeedBackDetail;
import com.trading.service.ITradingStoreCategory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2014/8/6.
 * ebay用户管理
 */
@Controller
@RequestMapping("user")
@Scope("prototype")
//@SessionAttributes({"ebaySessionID","runName"})
public class UserManageController extends BaseAction {
    static Logger logger = Logger.getLogger(UserManageController.class);
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Value("${USER.TOKEN.PRO.URL}")
    private String tokenPageUrl;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SystemUserManagerService userManagerService;
    @Autowired
    private ITradingFeedBackDetail feedBackDetail;


    /**绑定新增ebay帐号的弹出主页面*/
    @RequestMapping("bindEbayAccount.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView bindEbayAccount(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,
                                        CommonParmVO commonParmVO) throws Exception {
        modelMap.put("tokenPageUrl",tokenPageUrl);
        return   forword("/userinfo/pop/bindEbayAccount",modelMap);
    }
    /**编辑ebay帐号页面初始化*/
    @RequestMapping("editEbayAccount.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editEbayAccount(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,
                                        CommonParmVO commonParmVO) throws Exception {


        modelMap.put("tokenPageUrl",tokenPageUrl);
        modelMap.put("ebayId",commonParmVO.getId());
        UsercontrollerEbayAccountExtend e = userInfoService.queryEbayInfoById(modelMap);
        modelMap.put("ebay",e);
        return   forword("/userinfo/pop/bindEbayAccount",modelMap);
    }

    /**ebay帐号管理页面*/
    @RequestMapping("ebayAccountManager.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView ebayAccountManager(@ModelAttribute( "initSomeParmMap" )ModelMap modelMap,
                                        CommonParmVO commonParmVO) throws Exception {

        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        long p= sessionVO.getParentId();
        if (p!=0){
            modelMap.put("errMessage","主账号才有此访问权限");
            return   forword("/errorPage",modelMap);
        }
        boolean ism = userManagerService.isAdminRole();
        if (!ism){
            modelMap.put("errMessage","必须要有管理员权限");
            return   forword("/errorPage",modelMap);
        }

        return   forword("/userinfo/ebayAccountManager",modelMap);
    }

    /**启用或者停用ebay帐号*/
    @RequestMapping("startOrStopEbayAccount")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void startOrStopEbayAccount(String act,Long ebayId){
        Map map =new HashMap();
        map.put("act",act);
        map.put("ebayId",ebayId);
        userInfoService.startOrStopEbayAccount(map);
        AjaxSupport.sendSuccessText("","操作成功");
    }
    /**绑定ebay paypal帐号*/
    @RequestMapping("doBindEbayPaypalAccount.do")
    @ResponseBody
    public void doBindEbayPaypalAccount(CommonParmVO commonParmVO){
        Map map =new HashMap();
        map.put("id",commonParmVO.getId());
        map.put("payPalId",commonParmVO.getPayPalId());
        map.put("isDefault",commonParmVO.getStrV3());
        userInfoService.bindEbayPaypalAccount(map);
        AjaxSupport.sendSuccessText("","操作成功");
    }
    /**编辑ebay帐号*/
    @RequestMapping("doEditEbayAccount.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void doEditEbayAccount(CommonParmVO commonParmVO){
        Map map =new HashMap();
        map.put("id",commonParmVO.getId());
        map.put("name",commonParmVO.getStrV2());
        map.put("code",commonParmVO.getStrV3());
 //       map.put("payPalId",commonParmVO.getPayPalId());
        userInfoService.editEbayAccount(map);
        AjaxSupport.sendSuccessText("","操作成功");
    }

    @RequestMapping("apiGetSessionID")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    /**绑定账号的时候获取sessionid*/
    public void apiGetSessionID(CommonParmVO commonParmVO,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception{
       // SystemVipUserCostFeeService.class
        SessionVO sessionVO = SessionCacheSupport.getSessionVO();

        SystemVIPUserCheckService vipUserCheckService=ApplicationContextUtil.getBean(SystemVIPUserCheckService.class);
        UsercontrollerVipUser vu = vipUserCheckService.selectByUserId(((Long) sessionVO.getId()).toString());
        if(vu!=null){
            SystemVipUserCostFeeService costFeeService = ApplicationContextUtil.getBean(SystemVipUserCostFeeService.class);
            BigDecimal bb= costFeeService.queryBanlace(((Long) sessionVO.getId()).toString());

            BigDecimal b= MyNumberUtils.add(new BigDecimal("-"+StaticParam.ebayFeeOne), bb);

            if (b.compareTo(BigDecimal.ZERO)==-1){
                Asserts.assertTrue(false,"code:-111,余额不够!!请充值!");
            }
        }


//        Asserts.assertTrue(commonParmVO.getId()!=null,"参数获取失败！");
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(1L);
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.GetSessionID);
        String xml= BindAccountAPI.getSessionID("runName");

        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap= addApiTask.exec(d, xml, apiUrl);
        String r1=resMap.get("stat");
        String res=resMap.get("message");
        if("fail".equalsIgnoreCase(r1)){
          AjaxSupport.sendFailText("fail",res);
            return;
        }

        String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
        if("Success".equalsIgnoreCase(ack)){
            String sessionid=SamplePaseXml.getVFromXmlString(res,"SessionID");
            //modelMap.addAttribute("ebaySessionID",sessionid);
            //modelMap.addAttribute("runName",d.getRunname());
            AjaxSupport.sendSuccessText("success","{\"runName\":\""+d.getRunname()+"\",\"sessionid\":\""+sessionid+"\"}");
        }else {
            String errors=SamplePaseXml.getSpecifyElementTextAllInOne(res, "Errors", "LongMessage");
            logger.error("获取apisessionid失败!"+errors);
            AjaxSupport.sendFailText("fail","获取必要的参数失败！请稍后重试");
        }
    }

    @RequestMapping("apiFetchToken")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    /**授权完成后获取token*/
    public void apiFetchToken(CommonParmVO commonParmVO) throws Exception {
        Asserts.assertTrue(commonParmVO.getId()!=null && StringUtils.isNotEmpty(commonParmVO.getStrV1()),"参数获取失败！");
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(1L);//开发者帐号id
        d.setApiSiteid("0");
        d.setApiCallName(APINameStatic.FetchToken);
        String xml= BindAccountAPI.getFetchToken(commonParmVO.getStrV1());//上一步获取到的sessionID

        List<UsercontrollerEbayAccount> liuea = this.userInfoService.selectByEbayName(commonParmVO.getStrV2());
        if(liuea!=null&&liuea.size()>0){
            Asserts.assertTrue(false,"该账号已绑定，请重新选择账号！");
            return;
        }
        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap= addApiTask.exec(d, xml, apiUrl);
        String r1=resMap.get("stat");
        String res=resMap.get("message");
        if("fail".equalsIgnoreCase(r1)){
            AjaxSupport.sendFailText("fail",res);
            return;
        }

        String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        if("Success".equalsIgnoreCase(ack)){

            String token=SamplePaseXml.getVFromXmlString(res,"eBayAuthToken");

            UsercontrollerEbayAccount ebayAccount = new UsercontrollerEbayAccount();
            ObjectUtils.toInitPojoForInsert(ebayAccount);
            ebayAccount.setEbayToken(token);
            ebayAccount.setEbayName(commonParmVO.getStrV2());//ebay账户别名
            ebayAccount.setUserId(sessionVO.getId());
            ebayAccount.setEbayNameCode(commonParmVO.getStrV3());//ebay账户简写代码
            String htime=SamplePaseXml.getVFromXmlString(res,"HardExpirationTime");//有效期
            ebayAccount.setUseTimeStart(new Date());
            ebayAccount.setUseTimeEnd(DateUtils.returnDate(htime));
            ebayAccount.setEbayStatus("1");
            userInfoService.saveToken(ebayAccount, commonParmVO);


            try {
                ITradingStoreCategory category=ApplicationContextUtil.getBean(ITradingStoreCategory.class);
                category.getApiStoreCategory(ebayAccount);
            } catch (Exception e) {
                logger.error("===========================",e);
            }


            /**获取ebay账户*/
            String einfoxml = BindAccountAPI.getEbayUserInfo(token);
            d.setApiCallName(APINameStatic.GetUser);
            Map<String, String> resMap1= addApiTask.exec(d, einfoxml, apiUrl);
            String r11=resMap1.get("stat");
            String res1=resMap1.get("message");
            String ack1 = SamplePaseXml.getVFromXmlString(res1,"Ack");
            if("Success".equalsIgnoreCase(ack1)){
               String userID= SamplePaseXml.getSpecifyElementTextAllInOne(res1,"User","UserID");
                UsercontrollerEbayAccount u=new UsercontrollerEbayAccount();
                u.setId(ebayAccount.getId());
                u.setEbayAccount(userID);
                List<UsercontrollerEbayAccount> liueax = this.userInfoService.selectByEbayName(userID);
                if (ObjectUtils.isLogicalNull(liueax)){
                    userInfoService.updateEbayAccount(u);
                }else {
                    userInfoService.delEbayid(ebayAccount.getId());
                    Asserts.assertTrue(false,"该账号已绑定，请不要重复绑定！");
                }

            }
            AjaxSupport.sendSuccessText("success","绑定成功！");
        }else {
            String errors=SamplePaseXml.getVFromXmlString(res,"Errors");
            logger.error("绑定失败!"+errors+">>帐号:"+commonParmVO.getStrV2()+">>user:"+sessionVO.getLoginId());
            AjaxSupport.sendFailText("fail","获取必要的参数失败！请稍后重试");
        }

    }


    @RequestMapping("queryEbaysForCurrUser.do")
    @ResponseBody
    /**取得当前系统帐号对应的ebay帐号信息*/
    public void queryEbaysForCurrUser(CommonParmVO2 commonParmVO,
                                      String hsource){
        Map map=new HashMap();
        PageJsonBean jsonBean = commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        //jsonBean.setPageCount(1000);
        //jsonBean.setPageNum(1);

        List<UsercontrollerEbayAccountExtend> ebayAccountExtendList=userManagerService.queryCurrAllEbay(new HashMap());
        //List<UsercontrollerEbayAccountExtend> ebayAccountExtendList= userInfoService.getEbayAccountForCurrUser(new HashMap(),page);
        for (UsercontrollerEbayAccountExtend u : ebayAccountExtendList){
            if(u==null){continue;}
            u.setEbayToken("");
            u.setPerFeedBack(u.getPerFeedBack()+"%");
            /*if ("indexPage".equalsIgnoreCase(hsource)){
                Map map1=new HashMap();
                map1.put("ebayAccount",u.getEbayName());
                FeedBackReportQuery f=feedBackDetail.selectFeedBackEbayAccountReportList(map1);
                if (f==null){continue;}
                if("0".equalsIgnoreCase(f.getTjNumber())){continue;}
                u.setFeedBackNum(f.getTjNumber());
                map1.put("commentType","Positive");
                FeedBackReportQuery f1=feedBackDetail.selectFeedBackEbayAccountReportList(map1);
                if (f1==null){continue;}
                String posiNum=f1.getTjNumber();
                if("0".equalsIgnoreCase(posiNum)){continue;}
                String per = MyNumberUtils.divide(new BigDecimal(posiNum), new BigDecimal(u.getFeedBackNum())).multiply(new BigDecimal("100")).setScale(0, RoundingMode.HALF_UP).toString();
                u.setPerFeedBack(per+"%");
            }*/
        }
        jsonBean.setList(ebayAccountExtendList);
        jsonBean.setTotal((int) page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
        return;
    }

    @RequestMapping("queryAllDev")
    @ResponseBody
    /**获取所有的开发者帐号列表*/
    public void queryAllDev(){
        List<UsercontrollerDevAccount> devAccounts=userInfoService.queryAllDevAccount();
        AjaxSupport.sendSuccessText("",devAccounts);
        return;
    }



}
