package com.promotionalsale.controller;

import com.base.database.trading.model.TradingDataDictionary;
import com.base.database.trading.model.TradingItemPromotionalSaleRule;
import com.base.database.trading.model.TradingItemPromotionalSaleSet;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.BuyerRequirementDetailsQuery;
import com.base.domains.querypojos.ListingDataQuery;
import com.base.domains.querypojos.PromotionalSaleQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.xmlutils.SamplePaseXml;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.promotionalsale.service.ITradingItemPromotionalSaleRule;
import com.promotionalsale.service.ITradingItemPromotionalSaleSet;
import com.trading.service.ITradingListingData;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrtor on 2015/6/18.
 */
@Controller
@RequestMapping("promotionalsale")
public class PromotionalSaleController extends BaseAction {
    static Logger logger = Logger.getLogger(PromotionalSaleController.class);

    @Autowired
    private ITradingItemPromotionalSaleRule iTradingItemPromotionalSaleRule;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private UserInfoService userInfoService;
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Autowired
    private ITradingListingData iTradingListingData;
    @Autowired
    private ITradingItemPromotionalSaleSet iTradingItemPromotionalSaleSet;

    @RequestMapping("/promotionalsaleManager.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView promotionalsaleManager(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        return forword("promotionalsale/promotionalsaleManager",modelMap);
    }

    @RequestMapping("/ajax/loadPromotionalSaleQueryList.do")
    @ResponseBody
    public void loadPromotionalSaleQueryList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,CommonParmVO commonParmVO){
        Map m = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerUserExtend> liuue = systemUserManagerService.queryAllUsersByOrgID("yes");

        Set<String> users = new HashSet<>();
        users.add(((Long)c.getId()).toString());
        for (UsercontrollerUserExtend userExtend:liuue){
            users.add(userExtend.getUserId().toString());
        }
        m.put("users",users);
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<PromotionalSaleQuery> li = this.iTradingItemPromotionalSaleRule.selectByList(m,page);
        jsonBean.setList(li);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    @RequestMapping("/addRule.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addRule(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        return forword("promotionalsale/salerule",modelMap);
    }

    @RequestMapping("/ajax/saveRuel.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public void saveRuel(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,CommonParmVO commonParmVO,TradingItemPromotionalSaleRule tradingItemPromotionalSaleRule){
        Map m = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        String [] ebayAccounts = request.getParameterValues("ebayaccountid");
        String promotionalsalestarttime = request.getParameter("promotionalsalestarttime");
        String promotionalsaleendtime = request.getParameter("promotionalsaleendtime");
        if(tradingItemPromotionalSaleRule.getId()==null){//新增规则
            if(ebayAccounts !=null){
                for(String ebayid:ebayAccounts){
                    try {
                        tradingItemPromotionalSaleRule.setEbayaccountid(Long.parseLong(ebayid));
                        tradingItemPromotionalSaleRule.setPromotionalsalestarttime(DateUtils.parseDateTime(promotionalsalestarttime));
                        tradingItemPromotionalSaleRule.setPromotionalsaleendtime(DateUtils.parseDateTime(promotionalsaleendtime));
                        UsercontrollerEbayAccount ue = this.userInfoService.getEbayAccountByEbayID(Long.parseLong(ebayid));
                        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
                        d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(tradingItemPromotionalSaleRule.getSiteid()).getName1());
                        d.setApiCallName(APINameStatic.SetPromotionalSale);
                        AddApiTask addApiTask = new AddApiTask();
                        String xml = this.cosXml(ue.getEbayToken(),tradingItemPromotionalSaleRule.getDiscounttype(),tradingItemPromotionalSaleRule.getDiscountvalue(),DateUtils.DateToString(tradingItemPromotionalSaleRule.getPromotionalsalestarttime()),DateUtils.DateToString(tradingItemPromotionalSaleRule.getPromotionalsaleendtime()),tradingItemPromotionalSaleRule.getPromotionalsaleName());
                        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
                        String r1 = resMap.get("stat");
                        String res = resMap.get("message");
                        if ("fail".equalsIgnoreCase(r1)) {
                            logger.error("设置促销规则失败，请查看设置的参数！");
                            AjaxSupport.sendFailText("","操作失败！，请重新再试!");
                            return;
                        }
                        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                        if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                            tradingItemPromotionalSaleRule.setId(null);
                            tradingItemPromotionalSaleRule.setCheckFlag("0");
                            tradingItemPromotionalSaleRule.setCreateTime(new Date());
                            tradingItemPromotionalSaleRule.setUpdateTime(new Date());
                            tradingItemPromotionalSaleRule.setCreateUser(c.getId());
                            tradingItemPromotionalSaleRule.setPromotionalsaleid(SamplePaseXml.getVFromXmlString(res, "PromotionalSaleID"));
                            this.iTradingItemPromotionalSaleRule.saveRule(tradingItemPromotionalSaleRule);
                            AjaxSupport.sendSuccessText("","操作成功！");
                            return;
                        }else{
                            logger.error("请求xml：：：：：：：：：："+xml);
                            logger.error("设置促销规则报错：：：返回报文为：：：：："+res);
                            Document document= SamplePaseXml.formatStr2Doc(res);
                            String errors="";
                            if(document==null){
                                errors="设置促销规则报错："+res;
                            }else {
                                Element rootElt = document.getRootElement();
                                Iterator<Element> e = rootElt.elementIterator("Errors");
                                if (e != null) {
                                    while (e.hasNext()) {
                                        Element es = e.next();
                                        if (es.element("SeverityCode") != null && "Error".equals(es.elementText("SeverityCode"))) {
                                            errors += es.elementText("LongMessage") + ";";
                                        }
                                    }
                                }
                            }
                            AjaxSupport.sendFailText("",errors);
                            return;
                        }
                    }catch (Exception e){
                        logger.error("设置促销规则时报错：：：：：："+e);
                        AjaxSupport.sendFailText("",e);
                        System.out.println(e);
                    }
                }
            }
        }else{//更新规则

        }

    }

    public String cosXml(String token,String ruleType,Double ruleValue,String startDate,String endDate,String ruleName){
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<SetPromotionalSaleRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "  <RequesterCredentials>\n" +
                "    <eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "  </RequesterCredentials>\n" +
                "  <WarningLevel>High</WarningLevel>\n" +
                "  <Action>Add</Action>\n" +
                "  <PromotionalSaleDetails>\n" +
                "    <PromotionalSaleName>"+ruleName+"</PromotionalSaleName>\n" +
                "    <DiscountType>"+ruleType+"</DiscountType>\n" +
                "    <DiscountValue>"+ruleValue+"</DiscountValue>\n" +
                "    <PromotionalSaleType>PriceDiscountOnly</PromotionalSaleType>\n" +
                "    <PromotionalSaleStartTime>"+startDate+"</PromotionalSaleStartTime>\n" +
                "    <PromotionalSaleEndTime>"+endDate+"</PromotionalSaleEndTime>\n" +
                "  </PromotionalSaleDetails>\n" +
                " <ErrorLanguage>en_US</ErrorLanguage>\n"+
                "</SetPromotionalSaleRequest>";
        return xml;
    }


    @RequestMapping("/addItemToRule.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addItemToRule(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        modelMap.put("site",request.getParameter("site"));
        modelMap.put("ebayAccount",request.getParameter("ebayAccount"));
        modelMap.put("ruleId",request.getParameter("ruleId"));
        return forword("promotionalsale/selectItemToRule",modelMap);
    }


    @RequestMapping("/ajax/loadListingDataItemQueryList.do")
    @ResponseBody
    public void loadListingDataItemQueryList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,CommonParmVO commonParmVO){
        Map m = new HashMap();
        String site = request.getParameter("site");
        String ebayAccount = request.getParameter("ebayAccount");
        String ruleId = request.getParameter("ruleId");
        String selectType = request.getParameter("selectType");
        String selectValue = request.getParameter("selectValue");

        m.put("site",DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(site)).getValue());
        m.put("ebayAccount",this.userInfoService.getEbayAccountByEbayID(Long.parseLong(ebayAccount)).getEbayName());
        m.put("ruleId",ruleId);
        m.put("selectType",selectType);
        m.put("selectValue",selectValue);

        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<ListingDataQuery> li = this.iTradingListingData.selectByEbayAccountSite(m, page);
        jsonBean.setList(li);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    @RequestMapping("/ajax/loadListingDataOnSelectItemQueryList.do")
    @ResponseBody
    public void loadListingDataOnSelectItemQueryList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,CommonParmVO commonParmVO){
        Map m = new HashMap();
        String ruleId = request.getParameter("ruleId");
        m.put("ruleId",ruleId);

        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<ListingDataQuery> li = this.iTradingListingData.selectByonSite(m, page);
        jsonBean.setList(li);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    @RequestMapping("/ajax/saveItemToRuel.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public void saveItemToRuel(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,CommonParmVO commonParmVO,TradingItemPromotionalSaleRule tradingItemPromotionalSaleRule) {
        String action = request.getParameter("action");
        String ruleId = request.getParameter("ruleId");
        String itemId = request.getParameter("itemId");
        SessionVO c= SessionCacheSupport.getSessionVO();
        TradingItemPromotionalSaleSet tiss = this.iTradingItemPromotionalSaleSet.selectByItemId(itemId);
        if("add".equals(action)){
            if(tiss!=null){
                Asserts.assertTrue(false,"该商品处于另一个促销规则中，应该先移除再添加！");
            }else{
                TradingItemPromotionalSaleSet ti = new TradingItemPromotionalSaleSet();
                ti.setCreateUser(c.getId());
                ti.setCreateTime(new Date());
                ti.setCheckFlag("0");
                ti.setItemId(itemId);
                ti.setRuleId(Long.parseLong(ruleId));
                ti.setEbayStatus("0");
                this.iTradingItemPromotionalSaleSet.saveSet(ti);
                AjaxSupport.sendSuccessText("", "");
            }
        }else if("del".equals(action)){
            if(tiss!=null){
                tiss.setCheckFlag("1");
                this.iTradingItemPromotionalSaleSet.saveSet(tiss);
                AjaxSupport.sendSuccessText("", "");
            }
        }
    }

    @RequestMapping("/ajax/saveApiData.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public void saveApiData(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,CommonParmVO commonParmVO) {
        String ruleId = request.getParameter("ruleId");
        boolean abflag = false;
        //需要添加商品到规则中
        List<TradingItemPromotionalSaleSet> list = this.iTradingItemPromotionalSaleSet.selectByRuleIdToSet(Long.parseLong(ruleId),"0");
        //需要添加商品到规则中
        List<TradingItemPromotionalSaleSet> listdel = this.iTradingItemPromotionalSaleSet.selectByRuleIdToSet(Long.parseLong(ruleId),"1");
        TradingItemPromotionalSaleRule tpsr = this.iTradingItemPromotionalSaleRule.selectById(Long.parseLong(ruleId));
        UsercontrollerEbayAccount ue = this.userInfoService.getEbayAccountByEbayID(tpsr.getEbayaccountid());
        String errors = "";
        String success = "";
        if(list!=null&&list.size()>0){
            try {
                UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
                d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(tpsr.getSiteid()).getName1());
                d.setApiCallName(APINameStatic.SetPromotionalSaleListings);
                AddApiTask addApiTask = new AddApiTask();
                String xml = this.toSetXml(ue.getEbayToken(), tpsr.getPromotionalsaleid(), list,"Add");
                Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
                String r1 = resMap.get("stat");
                String res = resMap.get("message");
                if ("fail".equalsIgnoreCase(r1)) {
                    logger.error("设置商品到促销规则失败，请查看设置的参数！");
                    return;
                }
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                    for(TradingItemPromotionalSaleSet tips:list){
                        tips.setEbayStatus("1");
                        this.iTradingItemPromotionalSaleSet.saveSet(tips);
                    }
                    success="操作成功";
                } else {
                    this.iTradingItemPromotionalSaleSet.delByRuleId(Long.parseLong(ruleId),"2");
                    logger.error("请求xml：：：：：：：：：："+xml);
                    logger.error("设置商品到促销规则失败：：：返回报文为：：：：：" + res);
                    Document document = SamplePaseXml.formatStr2Doc(res);
                    if (document == null) {
                        errors = "设置促销规则报错：" + res;
                    } else {
                        Element rootElt = document.getRootElement();
                        Iterator<Element> e = rootElt.elementIterator("Errors");
                        if (e != null) {
                            while (e.hasNext()) {
                                Element es = e.next();
                                if (es.element("SeverityCode") != null && "Error".equals(es.elementText("SeverityCode"))) {
                                    errors += es.elementText("LongMessage") + ";";
                                }
                            }
                        }
                    }
                }
            }catch (Exception e){
                logger.error(e.getMessage());
                AjaxSupport.sendFailText("","调用API报错！");
            }
        }else{
            AjaxSupport.sendFailText("","未处理数据，因为示选择需要处理的数据！");
            abflag=true;
        }
        if(listdel!=null&&listdel.size()>0) {
            try {
                UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
                d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(tpsr.getSiteid()).getName1());
                d.setApiCallName(APINameStatic.SetPromotionalSaleListings);
                AddApiTask addApiTask = new AddApiTask();
                String xml = this.toSetXml(ue.getEbayToken(), tpsr.getPromotionalsaleid(), listdel,"Delete");
                Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
                String r1 = resMap.get("stat");
                String res = resMap.get("message");
                if ("fail".equalsIgnoreCase(r1)) {
                    logger.error("设置商品到促销规则失败，请查看设置的参数2！");
                    return;
                }
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                    this.iTradingItemPromotionalSaleSet.delByRuleId(Long.parseLong(ruleId),"1");
                    if(success==""){
                        success="操作成功";
                    }
                } else {
                    for(TradingItemPromotionalSaleSet tips:listdel){
                        tips.setCheckFlag("0");
                        this.iTradingItemPromotionalSaleSet.saveSet(tips);
                    }
                    logger.error("请求xml：：：：：：：：：："+xml);
                    logger.error("设置商品到促销规则失败：：：返回报文为2：：：：：" + res);
                    Document document = SamplePaseXml.formatStr2Doc(res);
                    if (document == null) {
                        errors = "设置促销规则报错2：" + res;
                    } else {
                        Element rootElt = document.getRootElement();
                        Iterator<Element> e = rootElt.elementIterator("Errors");
                        if (e != null) {
                            while (e.hasNext()) {
                                Element es = e.next();
                                if (es.element("SeverityCode") != null && "Error".equals(es.elementText("SeverityCode"))) {
                                    errors += es.elementText("LongMessage") + ";";
                                }
                            }
                        }
                    }
                }
            }catch (Exception e){
                logger.error(e.getMessage());
                AjaxSupport.sendFailText("","调用API报错2！");
            }
        }else{
            if(abflag){
                AjaxSupport.sendFailText("","未处理数据，因为示选择需要处理的数据！");
            }
        }
        if(errors!=""){
            AjaxSupport.sendFailText("",errors);
        }else{
            AjaxSupport.sendSuccessText("",success);
        }
    }

    public String toSetXml(String token,String saleId,List<TradingItemPromotionalSaleSet> list,String action){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<SetPromotionalSaleListingsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "  <RequesterCredentials>\n" +
                "    <eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "  </RequesterCredentials>\n" +
                "  <Action>"+action+"</Action>\n" +
                "  <PromotionalSaleID>"+saleId+"</PromotionalSaleID>\n" +
                "  <PromotionalSaleItemIDArray>\n";
            for(TradingItemPromotionalSaleSet tss:list){
                xml+="    <ItemID>"+tss.getItemId()+"</ItemID>\n";
            }
               xml+= "  </PromotionalSaleItemIDArray>\n" +
                "  <AllFixedPriceItems>false</AllFixedPriceItems>\n" +
                "  <AllStoreInventoryItems>false</AllStoreInventoryItems>\n" +
                " <ErrorLanguage>en_US</ErrorLanguage>\n"+
                "</SetPromotionalSaleListingsRequest> ";
        return xml;
    }
}
