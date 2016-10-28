package com.item.controller;

import com.alibaba.fastjson.JSON;
import com.base.aboutpaypal.service.PayPalService;
import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.task.model.TradingTaskXml;
import com.base.database.trading.model.*;
import com.base.database.userinfo.model.SystemLog;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.ItemQuery;
import com.base.domains.querypojos.VariationQuery;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.mybatis.page.Page;
import com.base.mybatis.page.PageJsonBean;
import com.base.sampleapixml.APINameStatic;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.annotations.AvoidDuplicateSubmission;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.*;
import com.base.utils.exception.Asserts;
import com.base.utils.ftpabout.FtpUploadFile;
import com.base.utils.imageManage.service.ImageService;
import com.base.utils.imageManage.service.impl.ImageServiceImpl;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.PojoXmlUtil;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.*;
import com.base.xmlpojo.trading.addproduct.attrclass.MadeForOutletComparisonPrice;
import com.base.xmlpojo.trading.addproduct.attrclass.MinimumAdvertisedPrice;
import com.base.xmlpojo.trading.addproduct.attrclass.OriginalRetailPrice;
import com.base.xmlpojo.trading.addproduct.attrclass.StartPrice;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import com.task.service.ITradingTaskXml;
import com.trading.service.*;
import com.trading.service.ITradingTembinListingLog;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
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
 * Created by Administrtor on 2014/8/2.
 */
@Controller
@Scope(value = "prototype")
public class ItemController extends BaseAction{
    static Logger logger = Logger.getLogger(ItemController.class);

    @Autowired
    private ITradingItem iTradingItem;
    @Autowired
    private ITradingPayPal iTradingPayPal;
    @Autowired
    private ITradingShippingDetails iTradingShippingDetails;
    @Autowired
    private ITradingDiscountPriceInfo iTradingDiscountPriceInfo;
    @Autowired
    private ITradingItemAddress iTradingItemAddress;
    @Autowired
    private ITradingReturnpolicy iTradingReturnpolicy;
    @Autowired
    private ITradingBuyerRequirementDetails iTradingBuyerRequirementDetails;
    @Autowired
    private ITradingAttrMores iTradingAttrMores;
    @Autowired
    private ITradingPictureDetails iTradingPictureDetails;
    @Value("${EBAY.API.URL}")
    private String apiUrl;

    @Value("${EBAY.SANDBOX.API.URL}")
    private String sandboxApiUrl;
    @Autowired
    private ITradingPublicLevelAttr iTradingPublicLevelAttr;
    @Autowired
    private ITradingVariation iTradingVariation;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ITradingVariations iTradingVariations;
    @Autowired
    private ITradingAddItem iTradingAddItem;
    @Autowired
    private ITradingPictures iTradingPictures;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Autowired
    private ITradingTemplateInitTable iTradingTemplateInitTable;
    @Autowired
    private ITradingDescriptionDetails iTradingDescriptionDetails;
    @Autowired
    private ITradingTimerListing iTradingTimerListing;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PayPalService payPalService;
    @Autowired
    private SystemUserManagerService systemUserManagerService;

    @Autowired
    private ITradingListingPicUrl iTradingListingPicUrl;

    @Autowired
    private ITradingDataDictionary iTradingDataDictionary;

    @Autowired
    private ITradingListingData iTradingListingData;
    @Autowired
    private ITradingAssessViewSet iTradingAssessViewSet;
    @Value("${SERVICE_ITEM_URL}")
    private String service_item_url;
    @Autowired
    private CommAutowiredClass autowiredClass;
    @Autowired
    private ITradingTaskXml iTradingTaskXml;
    @Autowired
    private ITradingTembinListingLog iTradingTembinListingLog;
    @Autowired
    private ITradingStoreCategory iTradingStoreCategory;
    @Autowired
    private ITradingInternationalShippingServiceOption iTradingInternationalShippingServiceOption;
    private int selectNumber=0;
    /**
     * 范本管理
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/itemManager.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView itemManager(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        SessionVO c= SessionCacheSupport.getSessionVO();
        //List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        //List<UsercontrollerEbayAccount> ebayList = this.iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(c.getId());
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        return forword("item/itemManager",modelMap);
    }

    /**
     * 商品展示列表
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/itemList.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView itemList(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        String flag=request.getParameter("flag");
        if(flag!=null&&!"".equals(flag)){
            modelMap.put("flag",flag);
        }

        String county = request.getParameter("county");
        if(county!=null&&!"".equals(county)){
            modelMap.put("county",county);
        }
        String listingtype = request.getParameter("listingtype");
        if(listingtype!=null&&!"".equals(listingtype)){
            modelMap.put("listingtype",listingtype);
        }
        String ebayaccount = request.getParameter("ebayaccount");
        if(ebayaccount!=null&&!"".equals(ebayaccount)){
            modelMap.put("ebayaccount",ebayaccount);
        }
        String selectType = request.getParameter("selectType");
        if(selectType!=null&&!"".equals(selectType)){
            modelMap.put("selectType",selectType);
        }
        String selectValue = request.getParameter("selectValue");
        if(selectValue!=null&&!"".equals(selectValue)){
            modelMap.put("selectValue",selectValue);
        }
        String folderid = request.getParameter("folderid");
        if(folderid!=null&&!"".equals(folderid)){
            modelMap.put("folderid",folderid);
        }
        return forword("item/itemList",modelMap);
    }



    @RequestMapping("/ajax/loadItemList.do")
    @ResponseBody
    public void loadItemList(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO){
        Map m = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        m.put("userid",c.getId());
        String flag=request.getParameter("flag");
        if(flag!=null&&!"".equals(flag)){
            m.put("flag",flag);
        }
        String county = request.getParameter("county");
        if(county!=null&&!"".equals(county)){
            m.put("county",county);
        }
        String listingtype = request.getParameter("listingtype");
        if(listingtype!=null&&!"".equals(listingtype)){
            m.put("listingtype",listingtype);
        }
        String ebayaccount = request.getParameter("ebayaccount");
        if(ebayaccount!=null&&!"".equals(ebayaccount)){
            m.put("ebayaccount",ebayaccount);
        }
        String selectType = request.getParameter("selectType");
        if(selectType!=null&&!"".equals(selectType)){
            m.put("selectType",selectType);
        }
        String selectValue = request.getParameter("selectValue");
        if(selectValue!=null&&!"".equals(selectValue)){
            m.put("selectValue",selectValue);
        }
        String folderid = request.getParameter("folderid");
        if(folderid!=null&&!"".equals(folderid)){
            m.put("folderid",folderid);
        }
        String descStr = request.getParameter("descStr");
        if(descStr!=null&&!"".equals(descStr)){
            m.put("descStr",descStr);
        }else{
            m.put("descStr","id");
        }
        String ascStr = request.getParameter("desStr");
        if(ascStr!=null&&!"".equals(ascStr)){
            m.put("desStr",ascStr);
        }else{
            m.put("desStr","desc");
        }
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<ItemQuery> itemli = this.iTradingItem.selectByItemList(m,page);
        for(ItemQuery iq:itemli){
            SystemLog sl = SystemLogUtils.selectSystemLogByTableId("ListingItemTimer",iq.getId());
            if(sl!=null){
                iq.setSl(sl);
            }
        }
        jsonBean.setList(itemli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    /**
     * 移到到文件夹
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/shiftModelToFolder.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    @ResponseBody
    public void shiftModelToFolder(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String idStr = request.getParameter("idStr");
        String [] ids =idStr.split(",");
        String folderid = request.getParameter("folderid");

        List<TradingItemWithBLOBs> litld = new ArrayList<TradingItemWithBLOBs>();
        for(String id: ids){
            TradingItemWithBLOBs tld = this.iTradingItem.selectByIdBL(Long.parseLong(id));
            tld.setFolderId(folderid);
            litld.add(tld);
        }
        if(litld.size()>0) {
            try {
                this.iTradingItem.saveTradingItemList(litld);
                AjaxSupport.sendSuccessText("","操作成功!");
            } catch (Exception e) {
                logger.error("",e);
                AjaxSupport.sendSuccessText("","操作失败!");
            }
        }else{
            AjaxSupport.sendSuccessText("","操作失败，你未选择商品，或你选择的商品不存在!");
        }

    }

    /**刊登主页面*/
    @RequestMapping("/addItem.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws DateParseException {
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);

        SessionVO c= SessionCacheSupport.getSessionVO();
        //List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        modelMap.put("imageUrlPrefix",imageService.getImageUrlPrefix());
        TradingAssessViewSet ta = this.iTradingAssessViewSet.selectByUserid(c.getId());
        modelMap.put("ta",ta);
        return forword("item/addItem",modelMap);
    }

    /**定时选择时间页面*/
    @RequestMapping("/selectTimer.do")
    public ModelAndView selectTimer(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return forword("item/selectTimer",modelMap);
    }

    @RequestMapping("/editItem.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        Asserts.assertTrue(StringUtils.isNotEmpty(id),"后台得到参数为空！");
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);
        TradingItem ti = null;
        TradingItemWithBLOBs tis = null;
        if(id!=null&&!"".equals(id)){
            ti = this.iTradingItem.selectById(Long.parseLong(id));
            tis = this.iTradingItem.selectByIdBL(Long.parseLong(id));
        }
        Asserts.assertTrue(ti!=null,"获取参数为空！");
        //tis.setDescription(content.get(0).toString());
        tis.setTitle(HtmlUtils.htmlUnescape(tis.getTitle()));
        modelMap.put("item", tis);

        modelMap.put("itemTitle", HtmlUtils.htmlEscape(tis.getTitle()));
        modelMap.put("titleLength",tis.getTitle().length());
        SessionVO c= SessionCacheSupport.getSessionVO();
        if(tis!=null){
            Long storeCategoryId = tis.getStoreCategoryId();
            if(!ObjectUtils.isLogicalNull(storeCategoryId)) {
                TradingStoreCategory tsc = this.iTradingStoreCategory.selectById(storeCategoryId);
                if (tsc != null) {
                    
                    modelMap.put("tsc", tsc);
                }
            }
        }
        //System.out.println(tis.getTitle().replace("'", "\'"));
        //List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        UsercontrollerEbayAccount ebay = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(ti.getEbayAccount().toString()));
        List<UsercontrollerEbayAccount> ebayList = new ArrayList();
        ebayList.add(ebay);
        modelMap.put("ebayList",ebayList);
        TradingAssessViewSet ta = this.iTradingAssessViewSet.selectByUserid(c.getId());
        modelMap.put("ta",ta);
        List<TradingPicturedetails> litp = this.iTradingPictureDetails.selectByParentId(Long.parseLong(id));
        for(TradingPicturedetails tp : litp){
            List<TradingAttrMores> lipic = this.iTradingAttrMores.selectByParnetid(tp.getId(),"PictureURL");
            if(lipic!=null&&lipic.size()>0){
                modelMap.put("lipic",lipic);
            }
        }

        List<TradingPublicLevelAttr> lilevle = this.iTradingPublicLevelAttr.selectByParentId("ItemSpecifics",Long.parseLong(id));
        for(TradingPublicLevelAttr tpla :lilevle){
            List<TradingPublicLevelAttr> lipa = this.iTradingPublicLevelAttr.selectByParentId(null,tpla.getId());
            if(lipa!=null){
                for(TradingPublicLevelAttr tpli:lipa){
                    tpli.setValue(HtmlUtils.htmlEscape(tpli.getValue()));
                }
                modelMap.put("lipa",lipa);
            }

        }

        if(ti.getListingtype().equals("2")) {
            TradingVariations tvs = this.iTradingVariations.selectByParentId(ti.getId());
            if (tvs != null) {
                Map m = new HashMap();
                //m.put("userid", c.getId());
                m.put("parentid", tvs.getId());
                List<VariationQuery> liv = this.iTradingVariation.selectByParentId(m);
                if (liv != null && liv.size() > 0) {
                    for (VariationQuery iv : liv) {
                        List<TradingPublicLevelAttr> litpa = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecifics", iv.getId());
                        for (TradingPublicLevelAttr tap : litpa) {
                            List<TradingPublicLevelAttr> litpas = this.iTradingPublicLevelAttr.selectByParentId(null, tap.getId());
                            for(TradingPublicLevelAttr tpas:litpas){
                                tpas.setValue(HtmlUtils.htmlEscape(tpas.getValue()));
                            }
                            iv.setTradingPublicLevelAttr(litpas);
                        }
                    }
                    modelMap.put("liv", liv);
                }

                List<TradingPublicLevelAttr> tplas = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificsSet", tvs.getId());
                if(!ObjectUtils.isLogicalNull(tplas)){
                    TradingPublicLevelAttr tpla=tplas.get(0);
                    List<TradingPublicLevelAttr> litpa = this.iTradingPublicLevelAttr.selectByParentId("NameValueList", tpla.getId());
                    List li = new ArrayList();
                    for (TradingPublicLevelAttr tp : litpa) {
                        TradingAttrMores aa =  this.iTradingAttrMores.selectByParnetid(tp.getId(), "Name").get(0);
                        aa.setValue(aa.getValue());
                        li.add(aa);
                    }
                    modelMap.put("clso", li);
                }

                TradingPictures tpes = this.iTradingPictures.selectParnetId(tvs.getId());
                if (tpes != null) {
                    List<TradingPublicLevelAttr> livsps = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificPictureSet", tpes.getId());
                    List lipics = new ArrayList();
                    for (int i = 0; i < livsps.size(); i++) {
                        Map ms = new HashMap();
                        TradingPublicLevelAttr tpa = livsps.get(i);
                        List<TradingPublicLevelAttr> livspsss = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificValue", tpa.getId());
                        List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetid(tpa.getId(), "MuAttrPictureURL");
                        ms.put("litam", litam);
                        if(livspsss!=null&&livspsss.size()>0) {
                            if(livspsss.get(0).getValue()!=null) {
                                ms.put("tamname", HtmlUtils.htmlEscape(livspsss.get(0).getValue()));
                            }
                        }
                        lipics.add(ms);
                    }
                    if (lipics.size() > 0) {
                        modelMap.put("lipics", lipics);
                    }
                }
            }
        }


        List<TradingPicturedetails> lipd = this.iTradingPictureDetails.selectByParentId(Long.parseLong(id));
        for(TradingPicturedetails pd : lipd){
            List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetid(pd.getId(),"PictureURL");
            modelMap.put("litam",litam);
        }

        TradingAddItem tai = this.iTradingAddItem.selectParentId(Long.parseLong(id));
        if(tai!=null){
            modelMap.put("tai",tai);
        }

        TradingTemplateInitTable ttit = this.iTradingTemplateInitTable.selectById(ti.getTemplateId());
        /*if(ttit!=null){*/
            List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetidUuid(ti.getId(),"TemplatePicUrl",ti.getUuid());
            modelMap.put("templi",litam);
        /*}*/
        modelMap.put("ttit",ttit);
        modelMap.put("imageUrlPrefix",imageService.getImageUrlPrefix());
        return forword("item/addItem",modelMap);
    }

    /**
     * 处理刊登图片
     * @param item
     */
    public void getEbayPicUrl(Item item,TradingItem tradingItem,String paypal){
        PictureDetails picd = item.getPictureDetails();
        if(picd!=null){
            List<String> lipicurl = picd.getPictureURL();
            List<String> liebaypic = new ArrayList();
            for(int i=0;i<lipicurl.size();i++){
                String picurl = lipicurl.get(i);
                String picName = picurl.substring(picurl.lastIndexOf("/",picurl.lastIndexOf(".")));
                String [] returnstr = this.uploadPictures(item,tradingItem,picurl,paypal,picName);
                if(returnstr!=null){
                    liebaypic.add(returnstr[2]);
                    List<TradingPicturedetails> litp = this.iTradingPictureDetails.selectByParentId(tradingItem.getId());
                    TradingPicturedetails tp = null;
                    if(litp!=null&&litp.size()>0){
                        tp = litp.get(0);
                        if(i==0){
                            tp.setBaseurl(returnstr[0]);
                            tp.setPicformat(returnstr[1]);
                            try {
                                this.iTradingPictureDetails.savePictureDetails(tp);
                            } catch (Exception e) {
                                logger.error("处理刊登图片",e);
                            }
                        }
                    }
                }
            }
            picd.setPictureURL(liebaypic);
            item.setPictureDetails(picd);
            //多属性图片
            Variations vars = item.getVariations();
            if(vars!=null){
                List<VariationSpecificPictureSet> vspsli = new ArrayList<VariationSpecificPictureSet>();
                List<VariationSpecificPictureSet> livsps = vars.getPictures().getVariationSpecificPictureSet();
                if(livsps!=null&&livsps.size()>0){
                    for(VariationSpecificPictureSet vsps:livsps){
                        List<String> vspic = vsps.getPictureURL();
                        List<String> picli = new ArrayList<String>();
                        for(String picurl:vspic){
                            String picName = picurl.substring(picurl.lastIndexOf("/",picurl.lastIndexOf(".")));
                            String [] returnstr = this.uploadPictures(item,tradingItem,picurl,paypal,picName);
                            if(returnstr!=null){
                                picli.add(returnstr[2]);
                            }
                        }
                        if(picli!=null&&picli.size()>0){
                            vsps.setPictureURL(picli);
                        }
                        vspsli.add(vsps);
                    }
                }
                vars.getPictures().setVariationSpecificPictureSet(vspsli);
            }
            item.setVariations(vars);
        }
    }

    /**
     * 把图片地址转换成ebay地址
     * @param item
     * @param tradingItem
     * @param request
     */
    public void getEbayPicUrl(Item item,TradingItem tradingItem,HttpServletRequest request,String ebayAccountId) throws InterruptedException {
        //转换图片地址
        PictureDetails picd = item.getPictureDetails();
        String [] picUrl = request.getParameterValues("pic_mackid");
        String [] picMoreUrl = request.getParameterValues("pic_mackid_more");
        if(picd!=null) {
            List<String> lipicurl = picd.getPictureURL();
            List<String> liebaypic = new ArrayList();
            for (int i = 0; i < picUrl.length; i++) {
                if(picUrl[i]!=null) {
                    List<TradingListingpicUrl> litam = this.iTradingListingPicUrl.selectByMackId(picUrl[i]);
                    if (litam != null && litam.size() > 0) {
                        TradingListingpicUrl tam = litam.get(0);
                        String url = tam.getUrl();
                        Asserts.assertTrue(StringUtils.isNotEmpty(url),"上传的商品图片中，第"+(i+1)+"张图片未上传成功，请删除重新上传！");
                        String picName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
                        if (tam.getEbayurl() == null) {
                            Thread.sleep(5000L);
                            Asserts.assertTrue(picUrl[i] != null && !"".equals(picUrl[i]), "图片上传失败，请从新选择图片上传");
                            List<TradingListingpicUrl> litamss = this.iTradingListingPicUrl.selectByMackId(picUrl[i]);
                            tam = litamss.get(0);
                            if (tam.getCheckFlag().equals("1")) {
                                liebaypic.add(tam.getEbayurl());
                            } else {
                                try {
                                    tam = this.iTradingListingPicUrl.uploadPic(tradingItem, tam.getUrl(), picName, tam);
                                    if (tam.getEbayurl() == null && tam.getCheckFlag().equals("2")) {
                                        Asserts.assertTrue(false, "图片上传失败，请从新选择图片上传");
                                    } else {
                                        liebaypic.add(tam.getEbayurl());
                                    }
                                } catch (Exception e) {
                                    logger.error(url + "图片上传失败！", e);
                                    Asserts.assertTrue(false, "图片上传失败，请从新选择图片上传");
                                }
                            }
                        } else {
                            liebaypic.add(tam.getEbayurl());
                        }
                    }
                }
            }
            picd.setPictureURL(liebaypic);
            item.setPictureDetails(picd);
        }else{
            List<String> liebaypic = new ArrayList();
            String [] picurl = request.getParameterValues("PictureDetails_"+ebayAccountId+".PictureURL");
            picd = new PictureDetails();
            if(picurl!=null&&picurl.length>0){
                for(int i=0;i<picurl.length;i++){
                    if(picUrl[i]!=null) {
                        List<TradingListingpicUrl> litam = this.iTradingListingPicUrl.selectByMackId(picUrl[i]);
                        if (litam != null && litam.size() > 0) {
                            TradingListingpicUrl tam = litam.get(0);
                            String url = tam.getUrl();
                            String picName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
                            if (tam.getEbayurl() == null) {
                                Thread.sleep(5000L);
                                Asserts.assertTrue(picUrl[i] != null && !"".equals(picUrl[i]), "图片上传失败，请从新选择图片上传");
                                List<TradingListingpicUrl> litamss = this.iTradingListingPicUrl.selectByMackId(picUrl[i]);
                                tam = litamss.get(0);
                                if (tam.getCheckFlag().equals("1")) {
                                    liebaypic.add(tam.getEbayurl());
                                } else {
                                    try {
                                        tam = this.iTradingListingPicUrl.uploadPic(tradingItem, tam.getUrl(), picName, tam);
                                        if (tam.getEbayurl() == null && tam.getCheckFlag().equals("2")) {
                                            Asserts.assertTrue(false, "图片上传失败，请从新选择图片上传");
                                        } else {
                                            liebaypic.add(tam.getEbayurl());
                                        }
                                    } catch (Exception e) {
                                        logger.error(url + "图片上传失败！", e);
                                        Asserts.assertTrue(false, "图片上传失败，请从新选择图片上传");
                                    }
                                }
                            } else {
                                liebaypic.add(tam.getEbayurl());
                            }
                        }
                    }
                }
            }

            picd.setPictureURL(liebaypic);
            item.setPictureDetails(picd);
        }
        //转换多属性图片地址
        //多属性图片
        if("2".equals(tradingItem.getListingtype())){
            TradingVariations tradvars = this.iTradingVariations.selectByParentId(tradingItem.getId());
            Variations vars = new Variations();
            if(tradvars!=null){
                TradingPictures tptures = this.iTradingPictures.selectParnetId(tradvars.getId());
                if(tptures!=null){
                    Pictures pic = new Pictures();
                    List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificPictureSet",tptures.getId());
                    List<VariationSpecificPictureSet> livss = new ArrayList<VariationSpecificPictureSet>();
                    for(TradingPublicLevelAttr tpla:litpla){
                        VariationSpecificPictureSet vss = new VariationSpecificPictureSet();
                        List<TradingPublicLevelAttr> livalue = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificValue",tpla.getId());
                        vss.setVariationSpecificValue(livalue.get(0).getValue());
                        List<String> listr = new ArrayList<String>();
                        List<TradingAttrMores> liname = this.iTradingAttrMores.selectByParnetid(tpla.getId(),"MuAttrPictureURL");
                        for(TradingAttrMores tam : liname){
                            List<TradingListingpicUrl> liplu = this.iTradingListingPicUrl.selectByMackId(tam.getAttr1());
                            if(liplu!=null&&liplu.size()>0){
                                TradingListingpicUrl plu = liplu.get(0);
                                if(plu.getEbayurl()==null){
                                    listr.add(tam.getValue());
                                }else {
                                    listr.add(plu.getEbayurl());
                                }
                            }else {
                                listr.add(tam.getValue());
                            }
                        }
                        vss.setPictureURL(listr);
                        livss.add(vss);
                    }
                    pic.setVariationSpecificPictureSet(livss);
                    if(tptures.getVariationspecificname()!=null){
                        pic.setVariationSpecificName(tptures.getVariationspecificname());
                    }
                    vars.setPictures(pic);
                }

                List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificsSet",tradvars.getId());
                if(litpla!=null&&litpla.size()>0){
                    VariationSpecificsSet vss = new VariationSpecificsSet();
                    for(TradingPublicLevelAttr tpla : litpla){
                        List<NameValueList> lnvl = new ArrayList<NameValueList>();
                        List<TradingPublicLevelAttr> linvlist = this.iTradingPublicLevelAttr.selectByParentId("NameValueList",tpla.getId());
                        for(TradingPublicLevelAttr nvlist : linvlist){
                            NameValueList nvl = new NameValueList();
                            List<TradingAttrMores> liname = this.iTradingAttrMores.selectByParnetid(nvlist.getId(),"Name");
                            nvl.setName(liname.get(0).getValue());
                            List<TradingAttrMores> lival = this.iTradingAttrMores.selectByParnetid(nvlist.getId(),"Value");
                            List<String> listr = new ArrayList<String>();
                            for(TradingAttrMores str : lival){
                                listr.add(str.getValue());
                            }
                            nvl.setValue(listr);
                            lnvl.add(nvl);
                        }
                        vss.setNameValueList(lnvl);
                    }
                    vars.setVariationSpecificsSet(vss);
                }
                List<Variation> liva = new ArrayList<Variation>();
                List<TradingVariation> litv = this.iTradingVariation.selectByParentId(tradvars.getId());
                if(litv!=null&&litv.size()>0){
                    for(TradingVariation tv:litv){
                        Variation var = new Variation();
                        if(tv.getQuantity()!=null){
                            var.setQuantity(tv.getQuantity().intValue());
                        }
                        if(tv.getStartprice()!=null){
                            StartPrice sp  = new StartPrice();
                            sp.setValue(tv.getStartprice());
                            var.setStartPrice(sp);
                        }
                        if(tv.getSku()!=null){
                            var.setSKU(tv.getSku());
                        }
                        List<TradingPublicLevelAttr> livs = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecifics",tv.getId());
                        if(livs!=null&&livs.size()>0){
                            List<VariationSpecifics> livcs = new ArrayList<VariationSpecifics>();
                            for(TradingPublicLevelAttr tpla : livs){
                                VariationSpecifics vsss = new VariationSpecifics();
                                List<NameValueList> lnvl = new ArrayList<NameValueList>();
                                List<TradingPublicLevelAttr> linvlist = this.iTradingPublicLevelAttr.selectByParentId(null,tpla.getId());
                                for(TradingPublicLevelAttr nvlist : linvlist){
                                    NameValueList nvl = new NameValueList();
                                    nvl.setName(nvlist.getName());
                                    List<String> listr = new ArrayList<String>();
                                    listr.add(nvlist.getValue());
                                    nvl.setValue(listr);
                                    lnvl.add(nvl);
                                }
                                vsss.setNameValueList(lnvl);
                                livcs.add(vsss);
                            }
                            var.setVariationSpecifics(livcs);
                        }
                        if(tv.getMadeforoutletcomparisonprice()!=null||tv.getMinimumadvertisedprice()!=null||tv.getMinimumadvertisedpriceexposure()!=null||tv.getSoldoffebay()!=null||tv.getSoldonebay()!=null||tv.getOriginalretailprice()!=null){
                            DiscountPriceInfo dpi = new DiscountPriceInfo();
                            if(tv.getMadeforoutletcomparisonprice()!=null){
                                MadeForOutletComparisonPrice mfocp = new MadeForOutletComparisonPrice();
                                mfocp.setValue(tv.getMadeforoutletcomparisonprice());
                                dpi.setMadeForOutletComparisonPrice(mfocp);
                            }
                            if(tv.getMinimumadvertisedprice()!=null){
                                MinimumAdvertisedPrice map  = new MinimumAdvertisedPrice();
                                map.setValue(tv.getMinimumadvertisedprice());
                                dpi.setMinimumAdvertisedPrice(map);
                            }
                            if(tv.getMinimumadvertisedpriceexposure()!=null){
                                dpi.setMinimumAdvertisedPriceExposure(tv.getMinimumadvertisedpriceexposure());
                            }
                            if(tv.getOriginalretailprice()!=null){
                                OriginalRetailPrice orp = new OriginalRetailPrice();
                                orp.setValue(tv.getOriginalretailprice());
                                dpi.setOriginalRetailPrice(orp);
                            }
                            if(tv.getSoldonebay()!=null){
                                dpi.setSoldOneBay(tv.getSoldonebay().equals("1")?true:false);
                            }
                            if(tv.getSoldoffebay()!=null){
                                dpi.setSoldOffeBay(tv.getSoldoffebay().equals("1")?true:false);
                            }
                            var.setDiscountPriceInfo(dpi);
                        }
                        liva.add(var);
                    }
                    vars.setVariation(liva);
                }
            }
            item.setVariations(vars);
        }

    }


    public String [] uploadPictures(Item item,TradingItem tradingItem,String picurl,String paypal,String picName){
        UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(paypal));
        AddApiTask addApiTask = new AddApiTask();
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<UploadSiteHostedPicturesRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "  <RequesterCredentials>\n" +
                "    <eBayAuthToken>"+ua.getEbayToken()+"</eBayAuthToken>\n" +
                "  </RequesterCredentials>\n" +
                "  <WarningLevel>High</WarningLevel>\n" +
                "  <ExternalPictureURL>"+picurl+"</ExternalPictureURL>\n" +
                "  <PictureName>"+picName+"</PictureName>\n" +
                "</UploadSiteHostedPicturesRequest>​";
        d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
        d.setApiCallName(APINameStatic.UploadSiteHostedPictures);
        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        String [] returnstr = new String[3];
        try {
            String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                Document document= SamplePaseXml.formatStr2Doc(res);
                Element rootElt = document.getRootElement();
                Element picelt = rootElt.element("SiteHostedPictureDetails");
                String baseUrl = picelt.elementText("BaseURL");
                String picformat = picelt.elementText("PictureFormat");
                String fullUrl = picelt.elementText("FullURL");
                return new String [] {baseUrl,picformat,fullUrl};
            }else{
                return null;
            }
        }catch(Exception e){
            logger.error(res+":::",e);
            return null;
        }

    }

    public void replateTemplate(HttpServletRequest request,Item item,TradingItemWithBLOBs tradingItem) throws DateParseException {
        String template = "";
        if(tradingItem.getTemplateId()!=null){//如果选择了模板
            TradingTemplateInitTable tttt = this.iTradingTemplateInitTable.selectById(tradingItem.getTemplateId());
            template = tttt.getTemplateHtml();
            String [] tempicUrls = request.getParameterValues("blankimg");//界面中添加的模板图片
            if(StringUtils.containsIgnoreCase(template,"{templateTitle}")){
                Map map = new HashMap();
                map.put("templateTitle",tradingItem.getTitle());
                template = MyStringUtil.replaceArgs(template,map);

            }
            org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(template);
            if(tempicUrls!=null&&tempicUrls.length>0){//如果界面中模板图片不为空，那么替换模板中ＵＲＬ地址
                if(StringUtils.containsIgnoreCase(template,"{templateImage")){
                    Map map = new HashMap();
                    for(int i=0;i<tempicUrls.length;i++){
                        map.put("templateImage"+(i+1),tempicUrls[i]);
                    }
                    template = MyStringUtil.replaceArgs(template,map);
                    //visibility: hidden;
                    org.jsoup.nodes.Document docself = org.jsoup.Jsoup.parse(template);
                    org.jsoup.select.Elements content = docself.getElementsByAttributeValueStarting("src", "{templateImage");
                    for(int i=0;i<content.size();i++){
                        org.jsoup.nodes.Element el = content.get(i);
                        el.attr("style","visibility: hidden");
                    }
                    template=docself.html();
                }else{
                    org.jsoup.select.Elements content = doc.getElementsByAttributeValue("name", "blankimg");
                    String url = "";
                    int j=0;
                    for(int i=0;i<content.size();i++){
                        org.jsoup.nodes.Element el = content.get(i);
                        url = el.attr("src");
                        if(url.indexOf("blank.jpg")>0&&j<tempicUrls.length){
                            el.attr("src",tempicUrls[j]);
                            j++;
                        }else{
                            el.remove();
                        }
                    }
                    template=doc.html();
                }
            }else{
                if(doc!=null) {
                    org.jsoup.select.Elements content = doc.getElementsByAttributeValue("name", "blankimg");
                    String url = "";
                    int j = 0;
                    for (int i = 0; i < content.size(); i++) {
                        org.jsoup.nodes.Element el = content.get(i);
                        el.remove();
                    }
                    template = doc.html();
                }else{
                    Asserts.assertTrue(false,"未选择模板！");
                }
                org.jsoup.nodes.Document docself = org.jsoup.Jsoup.parse(template);
                org.jsoup.select.Elements content = docself.getElementsByAttributeValueStarting("src", "{templateImage");
                for(int i=0;i<content.size();i++){
                    org.jsoup.nodes.Element el = content.get(i);
                    el.attr("style","visibility: hidden");
                }
                template=docself.html();
            }
            org.jsoup.nodes.Document docs = org.jsoup.Jsoup.parse(template);
            org.jsoup.nodes.Element elss = docs.body();
            elss.prepend("<div id='EBdescription_body' style='text-align: center;'></div>");
            template = docs.html();
            if(template==null){
                template=tradingItem.getDescription();
            }
            template = template.replace("{ProductDetail}",tradingItem.getDescription());
            Map<String,String> map = new HashMap<>();
            if(tttt!=null&&tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());
                if(ObjectUtils.isLogicalNull(tdd.getPayInfo())){
                    map.put("PaymentMethod", "");
                    map.put("PaymentMethodTitle","");
                }else {
                    map.put("PaymentMethod", tdd.getPayInfo());
                    map.put("PaymentMethodTitle",tdd.getPayTitle()==null?"PayTitle":tdd.getPayTitle());
                }
                if(ObjectUtils.isLogicalNull(tdd.getShippingInfo())){
                    map.put("ShippingDetail", "");
                    map.put("ShippingDetailTitle","");
                }else {
                    map.put("ShippingDetail", tdd.getShippingInfo());
                    map.put("ShippingDetailTitle",tdd.getShippingTitle()==null?"ShippingTitle":tdd.getShippingTitle());
                }
                if(ObjectUtils.isLogicalNull(tdd.getGuaranteeInfo())){
                    map.put("SalesPolicyTitle","");
                    map.put("SalesPolicy", "");
                }else {
                    map.put("SalesPolicyTitle",tdd.getGuaranteeTitle()==null?"GuaranteeTitle":tdd.getGuaranteeTitle());
                    map.put("SalesPolicy", tdd.getGuaranteeInfo());
                }
                if(ObjectUtils.isLogicalNull(tdd.getFeedbackInfo())){
                    map.put("AboutUsTitle","");
                    map.put("AboutUs", tdd.getFeedbackInfo());
                }else {
                    map.put("AboutUsTitle",tdd.getFeedbackTitle()==null?"FeedbackTitle":tdd.getFeedbackTitle());
                    map.put("AboutUs", tdd.getFeedbackInfo());
                }
                if(ObjectUtils.isLogicalNull(tdd.getContactInfo())){
                    map.put("ContactUsTitle","");
                    map.put("ContactUs", "");
                }else {
                    map.put("ContactUsTitle",tdd.getContactTitle()==null?"ContactTitle":tdd.getContactTitle());
                    map.put("ContactUs", tdd.getContactInfo());
                }
            }else{
                map.put("PaymentMethodTitle","");
                map.put("PaymentMethod","");
                map.put("ShippingDetailTitle","");
                map.put("ShippingDetail","");
                map.put("SalesPolicyTitle","");
                map.put("SalesPolicy","");
                map.put("AboutUsTitle","");
                map.put("AboutUs","");
                map.put("ContactUsTitle","");
                map.put("ContactUs","");
            }
            template = MyStringUtil.replaceArgs(template,map);
        }else{//未选择模板，
            template+=item.getDescription()+"</br>";
            if(tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());
                template+=tdd.getPayTitle()+"</br>"+tdd.getPayInfo()==null?"":tdd.getPayInfo()+"</br>";
                template+=tdd.getShippingTitle()+"</br>"+tdd.getShippingInfo()==null?"":tdd.getShippingInfo()+"</br>";
                template+=tdd.getGuaranteeTitle()+"</br>"+tdd.getGuaranteeInfo()==null?"":tdd.getGuaranteeInfo()+"</br>";
                template+=tdd.getFeedbackTitle()+"</br>"+tdd.getFeedbackInfo()==null?"":tdd.getFeedbackInfo()+"</br>";
                template+=tdd.getContactTitle()+"</br>"+tdd.getContactInfo()==null?"":tdd.getContactInfo()+"</br>";
            }
        }
        SessionVO c= SessionCacheSupport.getSessionVO();
        TradingAssessViewSet ta = this.iTradingAssessViewSet.selectByUserid(c.getId());
        String script="";
        if(ta!=null){
            if(ta.getApprange().equals("1")){//应用到所有刊登
                if(ta.getSetview().equals("1")){
                    script="<script type=\"text/javascript\">\n" +
                            "    var az = \"SC\";var bz = \"RI\";var cz = \"PT\";var dz = \"SR\";var ez = \"C=\";var fz = \"htt\";var gz = \"p://\";\n" +
                            "    var hz = \".com\";\n" +
                            "    var fz0 = \"task.tembin.com:8080\"+\"/\"+\"xsddWeb/js/item/showFeedBackNum.js\";\n" +
                            "    document.write (\"<\"+az+bz+cz+\" type='text/javascript'\"+dz+ez+fz+gz+fz0+\">\");\n" +
                            "    document.write(\"</\"+az+bz+cz+\">\");\n" +
                            "</script>";
                }else{
                    script="<script type=\"text/javascript\">\n" +
                            "    var az = \"SC\";var bz = \"RI\";var cz = \"PT\";var dz = \"SR\";var ez = \"C=\";var fz = \"htt\";var gz = \"p://\";\n" +
                            "    var hz = \".com\";\n" +
                            "    var fz0 = \"task.tembin.com:8080\"+\"/\"+\"xsddWeb/js/item/showFeedBackNum.js\";\n" +
                            "    document.write (\"<\"+az+bz+cz+\" type='text/javascript'\"+dz+ez+fz+gz+fz0+\">\");\n" +
                            "    document.write(\"</\"+az+bz+cz+\">\");\n" +
                            "</script>";
                }
            }else{//用户自定义
                if(request.getParameter("setView")!=null&&request.getParameter("setView").equals("1")){
                    if(ta.getSetview().equals("1")){
                        script="<script type=\"text/javascript\">\n" +
                                "    var az = \"SC\";var bz = \"RI\";var cz = \"PT\";var dz = \"SR\";var ez = \"C=\";var fz = \"htt\";var gz = \"p://\";\n" +
                                "    var hz = \".com\";\n" +
                                "    var fz0 = \"task.tembin.com:8080\"+\"/\"+\"xsddWeb/js/item/showFeedBackNum.js\";\n" +
                                "    document.write (\"<\"+az+bz+cz+\" type='text/javascript'\"+dz+ez+fz+gz+fz0+\">\");\n" +
                                "    document.write(\"</\"+az+bz+cz+\">\");\n" +
                                "</script>";
                    }else{

                    }
                }
            }
        }
        item.setDescription(template+"<div style='text-align: center;'><a href='http://www.tembin.com' target='_blank' ><img style='width: 150px;height: 28px;' src='http://www.tembin.com/images/logo.png'/></a></div>"+script);

    }

    /**
     * 刊登商品
     * @param request
     * @throws Exception
     */
    @RequestMapping("/saveItem.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveItem(HttpServletRequest request,Item item,TradingItemWithBLOBs tradingItem,Date timerListing) throws Exception {
        tradingItem.setUpdateTimer(new Date());
        if("2".equals(item.getListingType())){//多属性与自定义属性，查看是否重复
            List<String> liver = new ArrayList<>();
            List<String> lisi = new ArrayList<>();
            if(item.getVariations()!=null&&item.getVariations().getVariationSpecificsSet()!=null){
                List<NameValueList> lina1= item.getVariations().getVariationSpecificsSet().getNameValueList();
                if(lina1!=null&&lina1.size()>0){
                    for(NameValueList nvl1:lina1){
                        liver.add(nvl1.getName());
                    }
                }
            }
            if(item.getItemSpecifics()!=null){
                List<NameValueList> linvl = item.getItemSpecifics().getNameValueList();
                if(linvl!=null&&linvl.size()>0){
                    for(NameValueList nvl:linvl){
                        lisi.add(nvl.getName());
                    }
                }
            }
            if(liver.size()>0&&lisi.size()>0){
                List<String>[] lis = new List[2];
                lis[0]=liver;
                lis[1]=lisi;
                List<String> lists = MyCollectionsUtil.listRetain(lis);
                Asserts.assertTrue(ObjectUtils.isLogicalNull(lists),"多属性主属性名称与自定义属性名称重复("+lists.toString()+")，请检查！");
            }
        }
        if(!ObjectUtils.isLogicalNull(tradingItem.getShippingDeailsId())){
            TradingShippingdetails tradingShippingdetails = this.iTradingShippingDetails.selectById(tradingItem.getShippingDeailsId());
            List<TradingInternationalshippingserviceoption> list = this.iTradingInternationalShippingServiceOption.selectByParentid(tradingShippingdetails.getId());
            if(list!=null&&list.size()>0){
                for(TradingInternationalshippingserviceoption ti :list){
                    List<TradingAttrMores> liattr = this.iTradingAttrMores.selectByParnetid(ti.getId(),"ShipToLocation");
                    if(liattr==null||liattr.size()==0){
                        Asserts.assertTrue(false,"所选运输选项中，国际运输选项未选择运输到的地方！");
                    }
                }
            }
        }
        if("Chinese".equals(item.getListingType())){
            item.setQuantity(1);
            tradingItem.setQuantity(1L);
        }
        Asserts.assertTrue(!ObjectUtils.isLogicalNull(tradingItem.getDescription()),"商品描述不能为空！");
        //logger.error(item.getSKU()+"请求开始时间："+DateUtils.formatDateTime(new Date()));
        String mouth = request.getParameter("dataMouth");
        String [] paypals = request.getParameterValues("ebayAccounts");
        tradingItem.setTitle(HtmlUtils.htmlUnescape(request.getParameter("Title_" + paypals[0])));
        if("save".equals(mouth)||"othersave".equals(mouth)){//保存范本、另存为新范本
            //保存商品信息到数据库中
            if("othersave".equals(mouth)){
                tradingItem.setItemId(null);
                tradingItem.setIsFlag(null);
            }
            try {
                this.iTradingItem.saveItem(item, tradingItem);
           }catch(Exception e){
                logger.error("保存数据失败！原因：",e);
                AjaxSupport.sendFailText("fail","保存数据失败！原因："+e.getMessage());
            }
            AjaxSupport.sendSuccessText("message", "操作成功！");
        }else if("all".equals(mouth)||"Backgrounder".equals(mouth)||"timeSave".equals(mouth)){//立即刊登、后台刊登、定时刊登
            //保存商品信息到数据库中
            Map itemMap = null;
            try{
                itemMap = this.iTradingItem.saveItem(item,tradingItem);
                if(itemMap.size()==1){
                    tradingItem = this.iTradingItem.selectByIdBL(Long.parseLong(itemMap.get(paypals[0])+""));
                }
            }catch(Exception e){
                logger.error("保存数据失败！原因：",e);
                AjaxSupport.sendFailText("fail","保存数据失败！原因："+e.getMessage());
            }
            //处理刊登公共数据
            this.unitItemData(tradingItem,item,request);
            String xml="";
            //当选择多账号时刊登
            item.setApplicationData(tradingItem.getApplicationdata());

            if("timeSave".equals(mouth)){//定时刊登
                for (String paypal : paypals) {
                    if(item.getStorefront()!=null){
                        if(!ObjectUtils.isLogicalNull(item.getStorefront().getStoreCategoryID())&&item.getStorefront().getStoreCategoryID()!=0){
                            Storefront sf = new Storefront();
                            sf.setStoreCategoryID(item.getStorefront().getStoreCategoryID());
                            sf.setStoreCategoryName(item.getStorefront().getStoreCategoryName());
                            item.setStorefront(sf);
                        }else{
                            item.setStorefront(null);
                        }
                    }else{
                        item.setStorefront(null);
                    }
                    item.setTitle(HtmlUtils.htmlUnescape(request.getParameter("Title_"+paypal)));
                    item.setSubTitle(HtmlUtils.htmlUnescape(request.getParameter("SubTitle_"+paypal)));
                    if(request.getParameter("Quantity_"+paypal)!=null&&!"".equals(request.getParameter("Quantity_"+paypal))){
                        item.setQuantity(Integer.parseInt(request.getParameter("Quantity_"+paypal)));
                    }
                    if(request.getParameter("StartPrice.value_"+paypal)!=null&&!"".equals(request.getParameter("StartPrice.value_"+paypal))){
                        StartPrice sp = new StartPrice();
                        sp.setValue(Double.parseDouble(request.getParameter("StartPrice.value_"+paypal)));
                        sp.setCurrencyID(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getValue1());
                        item.setStartPrice(sp);
                    }
                    UsercontrollerEbayAccount ueas = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(paypal));
                    Long paypalAccountId = ueas.getPaypalAccountId();
                    if(paypalAccountId==null||paypalAccountId==0){
                        Asserts.assertTrue(false,ueas.getEbayName()+"未绑定paypal账号，不允许刊登！");
                    }
                    if(paypals.length>1) {
                        item.setPayPalEmailAddress(this.payPalService.selectById(paypalAccountId).getPaypalAccount());
                    }
                    String [] picurl = request.getParameterValues("PictureDetails_"+paypal+".PictureURL");
                    if(picurl!=null&&picurl.length>0){
                        PictureDetails pd = item.getPictureDetails();
                        if(pd==null){
                            pd = new PictureDetails();
                        }
                        List lipic = new ArrayList();
                        for(int i = 0;i<picurl.length;i++){
                            lipic.add(picurl[i]);
                        }
                        if(lipic.size()>0){
                            pd.setPictureURL(lipic);
                        }
                        item.setPictureDetails(pd);
                    }
                    //logger.error(tradingItem.getId()+":::检查图片是否上传开始:::"+DateUtils.formatDateTime(new Date()));
                    getEbayPicUrl(item,tradingItem,request,paypal);
                    //logger.error(tradingItem.getId()+":::检查图片上传结束:::"+DateUtils.formatDateTime(new Date()));
                    //getEbayPicUrl(item,tradingItem,paypal);
                    UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(paypal));
                    //PublicUserConfig pUserConfig = DataDictionarySupport.getPublicUserConfigByID(ua.getPaypalAccountId());
                    //如果配置ＥＢＡＹ账号时，选择强制使用paypal账号则用该账号
                    //item.setPayPalEmailAddress(pUserConfig.getConfigValue());
                    //定时刊登时，需要获取保存到数据库中的ＩＤ
                    if(tradingItem.getListingtype().equals("2")){
                        item.setStartPrice(null);
                        item.setQuantity(null);
                    }
                    if (item.getListingType().equals("Chinese")) {
                        StartPrice sp = new StartPrice();
                        sp.setValue(tradingItem.getStartprice());
                        sp.setCurrencyID(tradingItem.getCurrency());
                        item.setStartPrice(sp);
                        AddItemRequest addItem = new AddItemRequest();
                        addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                        addItem.setErrorLanguage("en_US");
                        addItem.setWarningLevel("High");
                        RequesterCredentials rc = new RequesterCredentials();
                        rc.seteBayAuthToken(ua.getEbayToken());

                        addItem.setRequesterCredentials(rc);
                        addItem.setItem(item);

                        xml = PojoXmlUtil.pojoToXml(addItem);
                        xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
                    } else {
                        if("2".equals(tradingItem.getListingtype())){
                            item.setStartPrice(null);
                            item.setQuantity(null);
                        }
                        AddFixedPriceItemRequest addItem = new AddFixedPriceItemRequest();
                        addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                        addItem.setErrorLanguage("en_US");
                        addItem.setWarningLevel("High");
                        RequesterCredentials rc = new RequesterCredentials();
                        rc.seteBayAuthToken(ua.getEbayToken());
                        addItem.setRequesterCredentials(rc);
                        addItem.setItem(item);
                        xml = PojoXmlUtil.pojoToXml(addItem);
                        xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
                    }
                    //得到刊登成功后的ＩＤ
                    TradingTimerListingWithBLOBs ttl = new TradingTimerListingWithBLOBs();
                    ttl.setItem(Long.parseLong(itemMap.get(paypal).toString()));
                    ttl.setTimer(timerListing);
                    ttl.setTimerMessage(StringEscapeUtils.escapeXml(xml));
                    if (item.getListingType().equals("Chinese")) {
                        ttl.setApiMethod(APINameStatic.AddItem);
                    } else {
                        ttl.setApiMethod(APINameStatic.AddFixedPriceItem);
                    }
                    ttl.setEbayId(paypal);
                    ttl.setStateId(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
                    ttl.setTimerFlag("0");
                    this.iTradingTimerListing.saveTradingTimer(ttl);
                    //System.out.println(xml);
                }
                AjaxSupport.sendSuccessText("message", "操作成功！");
            }else {//立即刊登
                String messages="";
                for (String paypal : paypals) {
                    TradingItemWithBLOBs tradingItem_1 = this.iTradingItem.selectByTitleSkuEbayAccount(tradingItem.getSku(),tradingItem.getTitle(),paypal,tradingItem.getListingtype(),tradingItem.getSite());
                    if(tradingItem_1!=null){
                        tradingItem_1.setSite(tradingItem.getSite());
                        tradingItem=tradingItem_1;
                    }
                    if(item.getStorefront()!=null){
                        if(!ObjectUtils.isLogicalNull(item.getStorefront().getStoreCategoryID())&&item.getStorefront().getStoreCategoryID()!=0){
                            Storefront sf = new Storefront();
                            sf.setStoreCategoryID(item.getStorefront().getStoreCategoryID());
                            sf.setStoreCategoryName(item.getStorefront().getStoreCategoryName());
                            item.setStorefront(sf);
                        }else{
                            item.setStorefront(null);
                        }
                    }else{
                        item.setStorefront(null);
                    }
                    item.setTitle(HtmlUtils.htmlUnescape(request.getParameter("Title_"+paypal)));
                    item.setSubTitle(HtmlUtils.htmlUnescape(request.getParameter("SubTitle_"+paypal)));
                    if(request.getParameter("Quantity_"+paypal)!=null&&!"".equals(request.getParameter("Quantity_"+paypal))){
                        item.setQuantity(Integer.parseInt(request.getParameter("Quantity_"+paypal)));
                    }
                    if(request.getParameter("StartPrice.value_"+paypal)!=null&&!"".equals(request.getParameter("StartPrice.value_"+paypal))){
                        StartPrice sp = new StartPrice();
                        sp.setValue(Double.parseDouble(request.getParameter("StartPrice.value_"+paypal)));
                        sp.setCurrencyID(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getValue1());
                        item.setStartPrice(sp);
                    }
                    UsercontrollerEbayAccount ueas = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(paypal));
                    Long paypalAccountId = ueas.getPaypalAccountId();
                    if(paypalAccountId==null||paypalAccountId==0){
                        Asserts.assertTrue(false,ueas.getEbayName()+"未绑定paypal账号，不允许刊登！");
                    }
                    if(paypals.length>1) {
                        item.setPayPalEmailAddress(this.payPalService.selectById(paypalAccountId).getPaypalAccount());
                    }
                    String [] picurl = request.getParameterValues("PictureDetails_"+paypal+".PictureURL");
                    if(picurl!=null&&picurl.length>0){
                        PictureDetails pd = item.getPictureDetails();
                        if(pd==null){
                            pd = new PictureDetails();
                        }
                        List lipic = new ArrayList();
                        for(int i = 0;i<picurl.length;i++){
                            lipic.add(picurl[i]);
                        }
                        if(lipic.size()>0){
                            pd.setPictureURL(lipic);
                        }
                        item.setPictureDetails(pd);
                    }

                    //logger.error(tradingItem.getId()+":::检查图片是否上传开始:::"+DateUtils.formatDateTime(new Date()));
                    getEbayPicUrl(item,tradingItem,request,paypal);
                    //logger.error(tradingItem.getId()+":::检查图片上传结束:::"+DateUtils.formatDateTime(new Date()));
                    //getEbayPicUrl(item,tradingItem,paypal);
                    UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(paypal));
                    //PublicUserConfig pUserConfig = DataDictionarySupport.getPublicUserConfigByID(ua.getPaypalAccountId());
                    //如果配置ＥＢＡＹ账号时，选择强制使用paypal账号则用该账号
                    //item.setPayPalEmailAddress(pUserConfig.getConfigValue());
                    //定时刊登时，需要获取保存到数据库中的ＩＤ
                    if(tradingItem.getListingtype().equals("2")){
                        item.setStartPrice(null);
                        item.setQuantity(null);
                    }
                    if (item.getListingType().equals("Chinese")) {
                        StartPrice sp = new StartPrice();
                        sp.setValue(tradingItem.getStartprice());
                        sp.setCurrencyID(tradingItem.getCurrency());
                        item.setStartPrice(sp);
                        AddItemRequest addItem = new AddItemRequest();
                        addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                        addItem.setErrorLanguage("en_US");
                        addItem.setWarningLevel("High");
                        RequesterCredentials rc = new RequesterCredentials();
                        rc.seteBayAuthToken(ua.getEbayToken());
                        addItem.setRequesterCredentials(rc);
                        addItem.setItem(item);
                        xml = PojoXmlUtil.pojoToXml(addItem);
                        xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
                    } else {
                        if("2".equals(tradingItem.getListingtype())){
                            item.setStartPrice(null);
                        }
                        AddFixedPriceItemRequest addItem = new AddFixedPriceItemRequest();
                        addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                        addItem.setErrorLanguage("en_US");
                        addItem.setWarningLevel("High");
                        RequesterCredentials rc = new RequesterCredentials();
                        rc.seteBayAuthToken(ua.getEbayToken());
                        addItem.setRequesterCredentials(rc);
                        addItem.setItem(item);
                        xml = PojoXmlUtil.pojoToXml(addItem);
                        xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
                    }
                    //System.out.println(xml);
                    //Asserts.assertTrue(false, "错误");
                    UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
                    d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
                    if (item.getListingType().equals("Chinese")) {
                        d.setApiCallName(APINameStatic.AddItem);
                    } else {
                        d.setApiCallName(APINameStatic.AddFixedPriceItem);
                    }
                    //String xml= BindAccountAPI.getSessionID(d.getRunname());

                    AddApiTask addApiTask = new AddApiTask();

                    if("1".equals(request.getParameter("ListingMessage"))){//如果是延迟通知//后台刊登
                        TaskMessageVO taskMessageVO=new TaskMessageVO();
                        taskMessageVO.setMessageContext("刊登");
                        taskMessageVO.setMessageTitle("刊登操作");
                        taskMessageVO.setMessageType(SiteMessageStatic.LISTING_MESSAGE_TYPE);
                        taskMessageVO.setBeanNameType(SiteMessageStatic.LISTING_ITEM_BEAN);
                        taskMessageVO.setMessageFrom("system");
                        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
                        taskMessageVO.setMessageTo(sessionVO.getId());
                        taskMessageVO.setObjClass(tradingItem);
                        addApiTask.execDelayReturn(d, xml, apiUrl, taskMessageVO);
                        AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
                        return;
                    }else {
                        //logger.error(tradingItem.getId()+":::调用ＡＰＩ开始:::"+DateUtils.formatDateTime(new Date()));
                        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
                        //logger.error(tradingItem.getId()+":::调用ＡＰＩ结束:::"+DateUtils.formatDateTime(new Date()));
                        String r1 = resMap.get("stat");
                        String res = resMap.get("message");
                        if ("fail".equalsIgnoreCase(r1)) {
                            logger.error("数据已保存，但刊登失败！由于返回报文不全，无法得到更详细的信息！");
                            //AjaxSupport.sendFailText("fail", "数据已保存，但刊登失败！");
                            AjaxSupport.sendFailText("fail", "{\"isFlag\":\"1\",\"message\":\"数据已保存，但刊登失败！\",\"tradingItemId\":\""+tradingItem.getId()+"\"}");
                            //logger.error(item.getSKU()+"解悉ＸＭＬ报错，时间："+DateUtils.formatDateTime(new Date()));
                            return;
                        }
                        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");

                        if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                            String itemId = SamplePaseXml.getVFromXmlString(res, "ItemID");
                            tradingItem.setItemId(itemId);
                            tradingItem.setIsFlag("Success");
                            this.iTradingItem.saveTradingItem(tradingItem);
                            this.iTradingItem.saveListingSuccess(res, itemId);
                            //新增在线商品表数据
                            this.iTradingListingData.saveTradingListingDataByTradingItem(tradingItem,res);
                            TradingDataDictionary tdds = DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite()));
                            messages+="ebay账号："+ueas.getEbayName()+"，商品SKU为："+tradingItem.getSku()+"，名称为：<a target=_blank style='color:blue' href='"+autowiredClass.getSiteURL(tdds.getName1())+itemId+"'>"+tradingItem.getItemName()+"<a>，刊登成功！";
                            //logger.error(item.getSKU()+"立即刊登成功请求结束时间："+DateUtils.formatDateTime(new Date()));
                            SystemLog sl = SystemLogUtils.selectSystemLogByTableId("ListingItemTimer", tradingItem.getId());
                            if(sl!=null){
                                sl.setEventname(sl.getEventname()+"_del");
                                SystemLogUtils.updateSystemLog(sl);
                            }
                            TradingTembinListingLog ttll = new TradingTembinListingLog();
                            ttll.setDocId(tradingItem.getId());
                            ttll.setCreateDate(new Date());
                            ttll.setIsTimer("0");
                            ttll.setItemId(itemId);
                            ttll.setListingType(tradingItem.getListingtype());
                            this.iTradingTembinListingLog.saveTradingTembinListingLog(ttll);
                        } else {
                            //String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
                            String errors = "";
                            /*TradingTaskXml ttx = new TradingTaskXml();
                            ttx.setCreateDate(new Date());
                            ttx.setTaskType("addItem");
                            ttx.setXmlContent(xml);
                            iTradingTaskXml.saveTradingTaskXml(ttx);*/
                            String errorCode="";
                            logger.error("刊登失败，请求xml为〉〉〉〉〉〉〉〉》"+xml);
                            logger.error("刊登失败，返回xml为：：：：：："+res);
                            Document document= SamplePaseXml.formatStr2Doc(res);
                            if(document==null){
                                errors="刊登失败，返回报文不全,内容为："+res;
                            }else {
                                Element rootElt = document.getRootElement();
                                Iterator<Element> e = rootElt.elementIterator("Errors");
                                if (e != null) {
                                    while (e.hasNext()) {
                                        Element es = e.next();
                                        if (es.element("SeverityCode") != null && "Error".equals(es.elementText("SeverityCode"))) {
                                            errors += es.elementText("LongMessage") + ";</br>";
                                            errorCode+=es.elementText("SeverityCode")+";";
                                        }
                                    }
                                }
                            }
                            SystemLog sl = SystemLogUtils.selectSystemLogByTableId("ListingItemdo",tradingItem.getId());
                            if(sl==null){
                                sl = new SystemLog();
                            }
                            sl.setCreatedate(new Date());
                            sl.setOperuser(tradingItem.getCreateUser() + "");
                            sl.setTableId(tradingItem.getId());
                            sl.setEventname("ListingItemdo");
                            sl.setOther(errorCode);
                            sl.setEventdesc("刊登失败！原因：" + errors);
                            SystemLogUtils.saveLog(sl);
                            //发送系统消息
                            List<String> errList = SamplePaseXml.getErrorsAndWarningWhenMor(res);
                            StringBuffer stringBuffer=new StringBuffer();
                            stringBuffer.append("错误信息:").append(errList.get(0)).append("</br></br>");
                            if(!ObjectUtils.isLogicalNull(errList.get(1))){
                                stringBuffer.append("警告信息:").append(errList.get(1)).append("</br></br>");
                            }
                            TaskMessageVO taskMessageVO=new TaskMessageVO();
                            taskMessageVO.setMessageContext(stringBuffer.toString());
                            taskMessageVO.setMessageTitle("立即刊登出错");
                            taskMessageVO.setMessageType(SiteMessageStatic.LISTING_ERROR_MESSAGE);
                            SessionVO c= SessionCacheSupport.getSessionVO();
                            taskMessageVO.setMessageTo(c.getId());
                            taskMessageVO.setMessageFrom("system");
                            SiteMessageService siteMessageService= (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
                            siteMessageService.addSiteMessage(taskMessageVO);
                            AjaxSupport.sendFailText("","失败原因：：：："+errors+"可以在系统消息中查看详情。");
                            SendEMailUtil.sendEmail2Admin("请求xml：：：：：：：：;返回xml：：：：：" + res, "立即刊登报错：");
                        }
                    }
                }
                AjaxSupport.sendSuccessText("message", messages);
            }
        }else if("updateListing".equals(mouth)){//更新在线刊登
            this.updateListingData(item,tradingItem,request);
            AjaxSupport.sendSuccessText("message","更新成功！");
            //logger.error(item.getSKU()+"更新在线范本请求结束时间："+DateUtils.formatDateTime(new Date()));
            return;
        }

    }

    /**
     * 处理刊登时的数据，从方法中提出来公用一个。
     * @param tradingItem
     * @param item
     * @throws Exception
     */
    public void unitItemData(TradingItemWithBLOBs tradingItem,Item item,HttpServletRequest request) throws Exception {
        TradingShippingdetails tshipping = this.iTradingShippingDetails.selectById(tradingItem.getShippingDeailsId());
        TradingBuyerRequirementDetails tbuyer = this.iTradingBuyerRequirementDetails.selectById(tradingItem.getBuyerId());
        TradingReturnpolicy treturn = this.iTradingReturnpolicy.selectById(tradingItem.getReturnpolicyId());
        //组装买家要求
        if(tbuyer!=null) {
            BuyerRequirementDetails brd = this.iTradingBuyerRequirementDetails.toXmlPojo(tradingItem.getBuyerId());
            item.setBuyerRequirementDetails(brd);
        }
        //组装退货政策
        if(treturn!=null) {
            ReturnPolicy rp = this.iTradingReturnpolicy.toXmlPojo(tradingItem.getReturnpolicyId());
            item.setReturnPolicy(rp);
            item.getReturnPolicy().setReturnsAcceptedOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getReturnsAcceptedOption())).getValue());
            item.getReturnPolicy().setReturnsWithinOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getReturnsWithinOption())).getValue());
            if(!ObjectUtils.isLogicalNull(item.getReturnPolicy().getRefundOption())) {
                item.getReturnPolicy().setRefundOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getRefundOption())).getValue());
            }
            item.getReturnPolicy().setShippingCostPaidByOption(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getReturnPolicy().getShippingCostPaidByOption())).getValue());
        }
        //运输详情
        if(tshipping!=null) {
            ShippingDetails sd = this.iTradingShippingDetails.toXmlPojo(tradingItem.getShippingDeailsId(),tradingItem.getId());
            item.setShippingDetails(sd);
            if(tshipping.getDispatchtimemax()!=null&&tshipping.getDispatchtimemax()!=0) {
                item.setDispatchTimeMax(tshipping.getDispatchtimemax().intValue());
            }
        }
        //处理折扣信息
        if(tradingItem.getDiscountpriceinfoId()!=null&&tradingItem.getDiscountpriceinfoId()!=0){
            TradingDiscountpriceinfo tpdi = this.iTradingDiscountPriceInfo.selectById(tradingItem.getDiscountpriceinfoId());
            if(tpdi!=null){
                DiscountPriceInfo dpi = new DiscountPriceInfo();
                if(tpdi.getSoldonebay()!=null&&"1".equals(tpdi.getSoldonebay())){
                    dpi.setSoldOneBay(true);
                }
                if(tpdi.getSoldoffebay()!=null&&"1".equals(tpdi.getSoldoffebay())){
                    dpi.setSoldOffeBay(true);
                }
                if(tpdi.getMinimumadvertisedprice()!=null&&tpdi.getMinimumadvertisedprice()>0){
                    MinimumAdvertisedPrice map = new MinimumAdvertisedPrice();
                    map.setValue(tpdi.getMinimumadvertisedprice());
                    map.setCurrencyID(item.getCurrency());
                    dpi.setMinimumAdvertisedPrice(map);
                }
                if(tpdi.getMinimumadvertisedpriceexposure()!=null){
                    dpi.setMinimumAdvertisedPriceExposure(tpdi.getMinimumadvertisedpriceexposure());
                }
                if(tpdi.getMadeforoutletcomparisonprice()!=null&&tpdi.getMadeforoutletcomparisonprice()>0){
                    MadeForOutletComparisonPrice mf = new MadeForOutletComparisonPrice();
                    mf.setValue(tpdi.getMadeforoutletcomparisonprice());
                    mf.setCurrencyID(tradingItem.getCurrency());
                    dpi.setMadeForOutletComparisonPrice(mf);
                }
                if(tpdi.getOriginalretailprice()!=null){
                    OriginalRetailPrice orp = new OriginalRetailPrice();
                    orp.setValue(Double.parseDouble(tpdi.getOriginalretailprice()));
                    orp.setCurrencyID(tradingItem.getCurrency());
                    dpi.setOriginalRetailPrice(orp);
                }
                dpi.setPricingTreatment("STP");
                item.setDiscountPriceInfo(dpi);
            }
        }
        //物品所在址
        TradingItemAddress tia = this.iTradingItemAddress.selectById(tradingItem.getItemLocationId());
        Asserts.assertTrue(tia!=null,"物品所在地不能为空！");
        item.setCountry(DataDictionarySupport.getTradingDataDictionaryByID(tia.getCountryId()).getValue());
        item.setCurrency(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue1());
        item.setPostalCode(tia.getPostalcode());
        item.setLocation(tia.getAddress());
        //刊登站点
        item.setSite(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue());
        //付款方式
        TradingPaypal tp = this.iTradingPayPal.selectById(tradingItem.getPayId());
        if(tp!=null) {
            item.setPayPalEmailAddress(payPalService.selectById(Long.parseLong(tp.getPaypal())).getEmail());
        }
        List<String> limo = new ArrayList();
        limo.add("PayPal");
        item.setPaymentMethods(limo);

        //处理模板信息,商品描述
        this.replateTemplate(request,item,tradingItem);
        //商品第二分类
        if(ObjectUtils.isLogicalNull(request.getParameter("SecondaryCategory.CategoryID"))){
            item.setSecondaryCategory(null);
        }
        //刊登天数
        item.setListingDuration(item.getListingDuration() == null ? "GTC" : item.getListingDuration());
        if(item.getDispatchTimeMax()==null) {
            item.setDispatchTimeMax(0);
        }
        //多属性
        if(item.getVariations()!=null) {
            List<Variation> livt = item.getVariations().getVariation();
            if(livt!=null){
                for(int i = 0 ; i<livt.size();i++){
                    Variation vtion = livt.get(i);
                    List<VariationSpecifics> livar = new ArrayList();
                    VariationSpecifics vsf = new VariationSpecifics();
                    List<NameValueList> linvls = item.getVariations().getVariationSpecificsSet().getNameValueList();
                    if(linvls!=null&&linvls.size()>0){
                        List<NameValueList> linameList = new ArrayList();
                        for(NameValueList vs : linvls){
                            NameValueList nvl = new NameValueList();
                            nvl.setName(vs.getName());
                            List li = new ArrayList();
                            li.add(vs.getValue().get(i));
                            nvl.setValue(li);
                            linameList.add(nvl);
                        }
                        vsf.setNameValueList(linameList);
                    }
                    livar.add(vsf);
                    vtion.setVariationSpecifics(livar);
                }
            }
            item.getVariations().setVariation(livt);
            if(item.getVariations().getPictures()==null){
                Pictures pictu = new Pictures();
                pictu.setVariationSpecificName(item.getVariations().getVariationSpecificsSet().getNameValueList().get(0).getName());
                item.getVariations().setPictures(pictu);
            }else {
                item.getVariations().getPictures().setVariationSpecificName(item.getVariations().getVariationSpecificsSet().getNameValueList().get(0).getName());
            }
            if(item.getStartPrice()!=null){
                tradingItem.setStartprice(item.getStartPrice().getValue());
            }
            List<NameValueList>  linvl= item.getVariations().getVariationSpecificsSet().getNameValueList();
            for(NameValueList nvlst : linvl){
                try{
                    List<String> listr = nvlst.getValue();
                    listr = MyCollectionsUtil.listUnique(listr);
                    nvlst.setValue(listr);
                }catch(Exception e){
                    Asserts.assertTrue(false,"多属性中，属性值为空！");
                }
            }
            item.getVariations().getVariationSpecificsSet().setNameValueList(linvl);
        }
        tradingItem.setListingtype(item.getListingType());
        //刊登类型
        item.setListingType(item.getListingType().equals("2")?"FixedPriceItem":item.getListingType());
        item.setApplicationData(item.getApplicationData());
    }

    public void updateListingData(Item item,TradingItemWithBLOBs tradingItem,HttpServletRequest request) throws Exception{
        //处理刊登模块公共数据
        this.unitItemData(tradingItem,item,request);
        TradingListingData listingItem = this.iTradingListingData.selectByItemid(tradingItem.getItemId());
        SessionVO c= SessionCacheSupport.getSessionVO();
        /*//处理模板信息
        this.replateTemplate(request,item,tradingItem);
        if(request.getParameter("SecondaryCategory.CategoryID")==null||"".equals(request.getParameter("SecondaryCategory.CategoryID"))){
            item.setSecondaryCategory(null);
        }
        TradingItemAddress tia = this.iTradingItemAddress.selectById(tradingItem.getItemLocationId());
        Asserts.assertTrue(tia!=null,"物品所在地不能为空！");
        item.setCountry(DataDictionarySupport.getTradingDataDictionaryByID(tia.getCountryId()).getValue());
        item.setCurrency(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue1());
        item.setSite(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue());
        item.setPostalCode(tia.getPostalcode());
        item.setLocation(tia.getAddress());
        TradingPaypal tp = this.iTradingPayPal.selectById(tradingItem.getPayId());
        if(tp!=null) {
            item.setPayPalEmailAddress(payPalService.selectById(Long.parseLong(tp.getPaypal())).getEmail());
        }
        List<String> limo = new ArrayList();
        limo.add("PayPal");
        item.setPaymentMethods(limo);
        item.setListingDuration(item.getListingDuration() == null ? "GTC" : item.getListingDuration());
        if(item.getDispatchTimeMax()==null) {
            item.setDispatchTimeMax(0);
        }
        if(item.getVariations()!=null) {
            List<Variation> livt = item.getVariations().getVariation();
            for(int i = 0 ; i<livt.size();i++){
                Variation vtion = livt.get(i);
                List<VariationSpecifics> livar = new ArrayList();
                VariationSpecifics vsf = new VariationSpecifics();
                List<NameValueList> linvls = item.getVariations().getVariationSpecificsSet().getNameValueList();
                if(linvls!=null&&linvls.size()>0){
                    List<NameValueList> linameList = new ArrayList();
                    for(NameValueList vs : linvls){
                        if(vs==null){
                            continue;
                        }
                        NameValueList nvl = new NameValueList();
                        nvl.setName(vs.getName());
                        List li = new ArrayList();
                        li.add(vs.getValue().get(i));
                        nvl.setValue(li);
                        linameList.add(nvl);
                    }
                    vsf.setNameValueList(linameList);
                }
                livar.add(vsf);
                vtion.setVariationSpecifics(livar);
            }
            item.getVariations().setVariation(livt);
            if(item.getVariations().getPictures()==null){
                Pictures pictu = new Pictures();
                pictu.setVariationSpecificName(item.getVariations().getVariationSpecificsSet().getNameValueList().get(0).getName());
                item.getVariations().setPictures(pictu);
            }else {
                item.getVariations().getPictures().setVariationSpecificName(item.getVariations().getVariationSpecificsSet().getNameValueList().get(0).getName());
            }
            if(item.getStartPrice()!=null){
                tradingItem.setStartprice(item.getStartPrice().getValue());
            }

            List<NameValueList>  linvl= item.getVariations().getVariationSpecificsSet().getNameValueList();
            for(NameValueList nvlst : linvl){
                List<String> listr = nvlst.getValue();
                listr = MyCollectionsUtil.listUnique(listr);
                nvlst.setValue(listr);
            }
            item.getVariations().getVariationSpecificsSet().setNameValueList(linvl);
        }
        tradingItem.setListingtype(item.getListingType());*/
        item.setListingType(item.getListingType().equals("2")?"FixedPriceItem":item.getListingType());
        String [] paypals = request.getParameterValues("ebayAccounts");
        for (int is=0;is<paypals.length;is++) {
            String paypal=paypals[is];
            if(item.getStorefront()!=null){
                if(!ObjectUtils.isLogicalNull(item.getStorefront().getStoreCategoryID())&&item.getStorefront().getStoreCategoryID()!=0){
                    Storefront sf = new Storefront();
                    sf.setStoreCategoryID(item.getStorefront().getStoreCategoryID());
                    sf.setStoreCategoryName(item.getStorefront().getStoreCategoryName());
                    item.setStorefront(sf);
                }else{
                    item.setStorefront(null);
                }
            }else{
                item.setStorefront(null);
            }
            item.setTitle(HtmlUtils.htmlUnescape(request.getParameter("Title_"+paypal)));
            item.setSubTitle(HtmlUtils.htmlUnescape(request.getParameter("SubTitle_"+paypal)));
            if(request.getParameter("Quantity_"+paypal)!=null&&!"".equals(request.getParameter("Quantity_"+paypal))){
                item.setQuantity(Integer.parseInt(request.getParameter("Quantity_"+paypal)));
            }
            if(request.getParameter("StartPrice.value_"+paypal)!=null&&!"".equals(request.getParameter("StartPrice.value_"+paypal))){
                StartPrice sp = new StartPrice();
                sp.setValue(Double.parseDouble(request.getParameter("StartPrice.value_"+paypal)));
                sp.setCurrencyID(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getValue1());
                item.setStartPrice(sp);
            }
            String [] picurl = request.getParameterValues("PictureDetails_"+paypal+".PictureURL");
            if(picurl!=null&&picurl.length>0){
                PictureDetails pd = item.getPictureDetails();
                if(pd==null){
                    pd = new PictureDetails();
                }
                List lipic = new ArrayList();
                for(int i = 0;i<picurl.length;i++){
                    if(ObjectUtils.isLogicalNull(picurl[i])){
                        continue;
                    }
                    lipic.add(picurl[i]);
                }
                if(lipic.size()>0){
                    pd.setPictureURL(lipic);
                }
                item.setPictureDetails(pd);
            }
            TradingItem tradingItem1 = this.iTradingItem.selectById(tradingItem.getId());
            if(tradingItem1.getItemId()==null||"".equals(tradingItem1.getItemId())){
                Asserts.assertTrue(false,"该商品未成功刊登，不能进行修改！");
            }
            item.setItemID(tradingItem1.getItemId());
            String domainname = "";
            //处理图片信息
            if(item.getPictureDetails()!=null){
                PictureDetails pds = item.getPictureDetails();
                /*List<String> liStringsss = pds.getPictureURL();
                List<String> liurls = new ArrayList();
                if(liStringsss!=null&&liStringsss.size()>0){
                    for(String url : liStringsss){
                        List<TradingListingpicUrl> liurl = this.iTradingListingPicUrl.selectByUrl(url);
                        if(liurl!=null&&liurl.size()>0){
                            liurls.add(liurl.get(0).getEbayurl());
                        }else{
                            liurls.add(url);
                        }
                    }
                }
                pds.setPictureURL(liurls);*/
                List<String> liString = pds.getPictureURL();
                if(liString!=null&&liString.size()>0){
                    domainname=MyStringUtil.subStringBetween(liString.get(0),"//","/");
                    if(ObjectUtils.isLogicalNull(domainname)){
                        domainname="ebayimg.com";
                    }
                    List<String> lipic = new ArrayList<>();
                    for(String str:liString){
                        String mackid = EncryptionUtil.md5Encrypt(str);
                        List<TradingListingpicUrl> liurl= this.iTradingListingPicUrl.selectByMackId(mackid);
                        if(liurl==null||liurl.size()==0){
                            Asserts.assertTrue(false,"主图片未上传成功:"+str+"！请删掉重新再传！");
                            return;
                        }else{
                            lipic.add(liurl.get(0).getEbayurl());
                        }
                    }
                    pds.setPictureURL(lipic);
                }
            }
            //处理多属性图片信息
            if(item.getVariations()!=null&&item.getVariations().getPictures()!=null){
                Pictures pictures = item.getVariations().getPictures();
                List<VariationSpecificPictureSet> livss = pictures.getVariationSpecificPictureSet();
                if(livss!=null&&livss.size()>0){
                    for(VariationSpecificPictureSet vsp:livss){
                        List<String> listr = vsp.getPictureURL();
                        if(listr!=null&&listr.size()>0){
                            List<String> listring = new ArrayList<>();
                            for(String str:listr){
                                String mackid = EncryptionUtil.md5Encrypt(str);
                                List<TradingListingpicUrl> liurl = this.iTradingListingPicUrl.selectByMackId(mackid);
                                if(liurl!=null&&liurl.size()>0){
                                    listring.add(liurl.get(0).getEbayurl());
                                }else{
                                    Asserts.assertTrue(false,"多属性图片未上传成功："+str+"！请删掉重新再传！");
                                    return ;
                                }
                            }
                            vsp.setPictureURL(listring);
                        }
                    }
                }
            }
            if(listingItem!=null&&listingItem.getApplicationdata()!=null&&!"".equals(listingItem.getApplicationdata())){
                item.setApplicationData(listingItem.getApplicationdata());
            }
            //getEbayPicUrl(item,tradingItem,request,paypal);
            UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(paypal));
            //PublicUserConfig pUserConfig = DataDictionarySupport.getPublicUserConfigByID(ua.getPaypalAccountId());
            //如果配置ＥＢＡＹ账号时，选择强制使用paypal账号则用该账号
            //item.setPayPalEmailAddress(pUserConfig.getConfigValue());
            //定时刊登时，需要获取保存到数据库中的ＩＤ
            ReviseItemRequest rir = new ReviseItemRequest();
            rir.setXmlns("urn:ebay:apis:eBLBaseComponents");
            rir.setErrorLanguage("en_US");
            rir.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            //SessionVO c= SessionCacheSupport.getSessionVO();
            rc.seteBayAuthToken(ua.getEbayToken());
            rir.setRequesterCredentials(rc);
            String xml = "";
            if(tradingItem1.getListingtype().equals("2")){
                item.setStartPrice(null);
                item.setQuantity(null);
            }
            item.setApplicationData(tradingItem.getApplicationdata());
            rir.setItem(item);
            xml = PojoXmlUtil.pojoToXml(rir);
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
            //logger.error("更新在线范本传入xml::::::::::::"+xml);
            //System.out.println(xml);
            //Asserts.assertTrue(false, "错误");
            UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
            d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
            d.setApiCallName(APINameStatic.ReviseItem);
            //String xml= BindAccountAPI.getSessionID(d.getRunname());

            AddApiTask addApiTask = new AddApiTask();

            TaskMessageVO taskMessageVO=new TaskMessageVO();
            taskMessageVO.setMessageContext("通过范本更新在线商品");
            taskMessageVO.setMessageTitle("通过范本更新在线商品");
            taskMessageVO.setMessageType(SiteMessageStatic.UPDATE_LISTING_DATA_MESSAGE_TYPE);
            taskMessageVO.setBeanNameType(SiteMessageStatic.UPDATE_LISTING_ITEM_BEAN);
            taskMessageVO.setMessageFrom("system");
            SessionVO sessionVO=SessionCacheSupport.getSessionVO();
            taskMessageVO.setMessageTo(sessionVO.getId());
            Map map=new HashMap();
            map.put("ebayAccounts",request.getParameterValues("ebayAccounts"));
            map.put("dataMouth",request.getParameter("dataMouth"));
            map.put("Title_"+paypal,request.getParameter("Title_"+paypal));
            map.put("SubTitle_"+paypal,request.getParameter("SubTitle_"+paypal));
            map.put("Quantity_"+paypal,request.getParameter("Quantity_"+paypal));
            map.put("StartPrice.value_"+paypal,request.getParameter("StartPrice.value_"+paypal));
            map.put("setView",request.getParameter("setView"));
            map.put(tradingItem.getShippingDeailsId()+".ShippingServiceCost.value",request.getParameterValues(tradingItem.getShippingDeailsId()+".ShippingServiceCost.value"));
            map.put(tradingItem.getShippingDeailsId()+".ShippingServiceAdditionalCost.value",request.getParameterValues(tradingItem.getShippingDeailsId()+".ShippingServiceAdditionalCost.value"));
            map.put(tradingItem.getShippingDeailsId()+".ShippingSurcharge.value",request.getParameterValues(tradingItem.getShippingDeailsId()+".ShippingSurcharge.value"));
            map.put(tradingItem.getShippingDeailsId()+".shippingservice",request.getParameterValues(tradingItem.getShippingDeailsId()+".shippingservice"));
            map.put(tradingItem.getShippingDeailsId()+".sourceId",request.getParameterValues(tradingItem.getShippingDeailsId()+".sourceId"));
            map.put(tradingItem.getShippingDeailsId()+".intershippingservice",request.getParameterValues(tradingItem.getShippingDeailsId()+".intershippingservice"));
            map.put(tradingItem.getShippingDeailsId()+".interShippingServiceCost.value",request.getParameterValues(tradingItem.getShippingDeailsId()+".interShippingServiceCost.value"));
            map.put(tradingItem.getShippingDeailsId()+".interShippingServiceAdditionalCost.value",request.getParameterValues(tradingItem.getShippingDeailsId()+".interShippingServiceAdditionalCost.value"));
            map.put(tradingItem.getShippingDeailsId()+".intersourceId",request.getParameterValues(tradingItem.getShippingDeailsId()+".intersourceId"));
            map.put("blankimg",request.getParameterValues("blankimg"));
            if(tradingItem.getListingtype().equals("Chinese")){
                map.put("ListingFlag",request.getParameter("ListingFlag"));
                map.put("ListingDuration",request.getParameter("ListingDuration"));
                map.put("ListingScale",request.getParameter("ListingScale"));
                map.put("BuyItNowPrice",request.getParameter("BuyItNowPrice"));
                map.put("PrivateListing",request.getParameter("PrivateListing"));
                map.put("ReservePrice",request.getParameter("ReservePrice"));
                map.put("SecondFlag",request.getParameter("SecondFlag"));
                map.put("pic_mackid",request.getParameterValues("pic_mackid"));
                map.put("PictureDetails_"+paypal+".PictureURL",request.getParameterValues("PictureDetails_"+paypal+".PictureURL"));
            }else if(tradingItem.getListingtype().equals("2")||"FixedPriceItem".equals(tradingItem.getListingtype())){
                map.put("pic_mackid_more",request.getParameterValues("pic_mackid_more"));
                map.put("pic_mackid",request.getParameterValues("pic_mackid"));
                map.put("PictureDetails_"+paypal+".PictureURL",request.getParameterValues("PictureDetails_"+paypal+".PictureURL"));
            }
            map.put("userId",c.getId());
            Object[] obj = new Object[4];
            obj[0]=item;
            obj[1]=tradingItem;
            obj[2]=map;
            obj[3]=xml;
            taskMessageVO.setObjClass(obj);
            taskMessageVO.setSendOrNotSend(true);
            taskMessageVO.setWeithSendSuccessMessage(false);
            //logger.error("更新在线范本xml::::::::::::::"+xml);
            addApiTask.execDelayReturn(d, xml, apiUrl, taskMessageVO);
            AjaxSupport.sendSuccessText("message", "操作成功！结果请稍后查看消息！");
        }
    }

    /**
     * 选择产品列表列表
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/selectItemInformation.do")
    public ModelAndView selectItemInformation(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        return forword("/itemInformation/selectItemInformation",modelMap);
    }
    /**
     * 预览功能
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/previewItem.do")
    @ResponseBody
    public void previewItem(TradingItemWithBLOBs tradingItem,Item item,HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception{
        String [] paypals = request.getParameterValues("ebayAccounts");
        //保存商品信息到数据库中
        Map itemMap = this.iTradingItem.saveItem(item,tradingItem);

        if(itemMap.size()==1){
            tradingItem = this.iTradingItem.selectByIdBL(Long.parseLong(itemMap.get(paypals[0])+""));
        }
        /**
         * 处理刊登模块中的数据
         */
        this.unitItemData(tradingItem,item,request);
        /*//处理模板信息
        this.replateTemplate(request, item, tradingItem);

        if(request.getParameter("SecondaryCategory.CategoryID")==null||"".equals(request.getParameter("SecondaryCategory.CategoryID"))){
            item.setSecondaryCategory(null);
        }
        TradingItemAddress tia = this.iTradingItemAddress.selectById(tradingItem.getItemLocationId());
        Asserts.assertTrue(tia!=null,"物品所在地不能为空！");
        item.setCountry(DataDictionarySupport.getTradingDataDictionaryByID(tia.getCountryId()).getValue());
        item.setCurrency(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue1());
        item.setSite(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue());
        item.setPostalCode(tia.getPostalcode());
        item.setLocation(tia.getAddress());

        TradingPaypal tp = this.iTradingPayPal.selectById(tradingItem.getPayId());
        if(tp!=null) {
            item.setPayPalEmailAddress(payPalService.selectById(Long.parseLong(tp.getPaypal())).getEmail());
        }
        List<String> limo = new ArrayList();
        limo.add("PayPal");
        item.setPaymentMethods(limo);
        item.setListingDuration(item.getListingDuration() == null ? "GTC" : item.getListingDuration());
        if(item.getDispatchTimeMax()==null) {
            item.setDispatchTimeMax(0);
        }
        if(item.getVariations()!=null) {
            List<Variation> livt = item.getVariations().getVariation();
            for(int i = 0 ; i<livt.size();i++){
                Variation vtion = livt.get(i);
                List<VariationSpecifics> livar = new ArrayList();
                VariationSpecifics vsf = new VariationSpecifics();
                List<NameValueList> linvls = item.getVariations().getVariationSpecificsSet().getNameValueList();
                if(linvls!=null&&linvls.size()>0){
                    List<NameValueList> linameList = new ArrayList();
                    for(NameValueList vs : linvls){
                        NameValueList nvl = new NameValueList();
                        nvl.setName(vs.getName());
                        List li = new ArrayList();
                        li.add(vs.getValue().get(i));
                        nvl.setValue(li);
                        linameList.add(nvl);
                    }
                    vsf.setNameValueList(linameList);
                }
                livar.add(vsf);
                vtion.setVariationSpecifics(livar);
            }
            item.getVariations().setVariation(livt);
            item.getVariations().getPictures().setVariationSpecificName(item.getVariations().getVariationSpecificsSet().getNameValueList().get(0).getName());
            if(item.getStartPrice()!=null){
                tradingItem.setStartprice(item.getStartPrice().getValue());
            }

            List<NameValueList>  linvl= item.getVariations().getVariationSpecificsSet().getNameValueList();
            for(NameValueList nvlst : linvl){
                List<String> listr = nvlst.getValue();
                listr = MyCollectionsUtil.listUnique(listr);
                nvlst.setValue(listr);
            }
            item.getVariations().getVariationSpecificsSet().setNameValueList(linvl);
        }

        item.setListingType(item.getListingType().equals("2")?"FixedPriceItem":item.getListingType());*/

        String xml="";
        //当选择多账号时刊登
        if(item.getStorefront()!=null){
            if(!ObjectUtils.isLogicalNull(item.getStorefront().getStoreCategoryID())&&item.getStorefront().getStoreCategoryID()!=0){
                Storefront sf = new Storefront();
                sf.setStoreCategoryID(item.getStorefront().getStoreCategoryID());
                sf.setStoreCategoryName(item.getStorefront().getStoreCategoryName());
                item.setStorefront(sf);
            }else{
                item.setStorefront(null);
            }
        }else{
            item.setStorefront(null);
        }
        String paypal = paypals[0];
        item.setTitle(request.getParameter("Title_"+paypal));
        item.setSubTitle(request.getParameter("SubTitle_"+paypal));
        if(request.getParameter("Quantity_"+paypal)!=null&&!"".equals(request.getParameter("Quantity_"+paypal))){
            item.setQuantity(Integer.parseInt(request.getParameter("Quantity_"+paypal)));
        }
        if(request.getParameter("StartPrice.value_"+paypal)!=null&&!"".equals(request.getParameter("StartPrice.value_"+paypal))){
            StartPrice sp = new StartPrice();
            sp.setValue(Double.parseDouble(request.getParameter("StartPrice.value_"+paypal)));
            sp.setCurrencyID(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getValue1());
            item.setStartPrice(sp);
        }
        String [] picurl = request.getParameterValues("PictureDetails_"+paypal+".PictureURL");
        if(picurl!=null&&picurl.length>0){
            PictureDetails pd = item.getPictureDetails();
            if(pd==null){
                pd = new PictureDetails();
            }
            List lipic = new ArrayList();
            for(int i = 0;i<picurl.length;i++){
                lipic.add(picurl[i]);
            }
            if(lipic.size()>0){
                pd.setPictureURL(lipic);
            }
            item.setPictureDetails(pd);
        }
        getEbayPicUrl(item,tradingItem,request,paypal);
        //getEbayPicUrl(item,tradingItem,paypal);
        UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.valueOf(autowiredClass.sandboxEbayID));
        //PublicUserConfig pUserConfig = DataDictionarySupport.getPublicUserConfigByID(ua.getPaypalAccountId());
        //如果配置ＥＢＡＹ账号时，选择强制使用paypal账号则用该账号
        //item.setPayPalEmailAddress(pUserConfig.getConfigValue());
        //定时刊登时，需要获取保存到数据库中的ＩＤ
        item.setPayPalEmailAddress("caixu23@gmail.com");
        if(item.getTitle().length()>70){
            item.setTitle(item.getTitle().substring(0,70)+MyStringUtil.getRandomStringAndNum(5));
        }else{
            item.setTitle(item.getTitle()+MyStringUtil.getRandomStringAndNum(5));
        }

        if (item.getListingType().equals("Chinese")) {
            AddItemRequest addItem = new AddItemRequest();
            addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
            addItem.setErrorLanguage("en_US");
            addItem.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            rc.seteBayAuthToken(ua.getEbayToken());
            addItem.setRequesterCredentials(rc);
            addItem.setItem(item);
            xml = PojoXmlUtil.pojoToXml(addItem);
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
        } else {
            if("2".equals(tradingItem.getListingtype())){
                item.setStartPrice(null);
            }
            AddFixedPriceItemRequest addItem = new AddFixedPriceItemRequest();
            addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
            addItem.setErrorLanguage("en_US");
            addItem.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            rc.seteBayAuthToken(ua.getEbayToken());
            addItem.setRequesterCredentials(rc);
            addItem.setItem(item);
            xml = PojoXmlUtil.pojoToXml(addItem);
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
        }
        //System.out.println(xml);
        //Asserts.assertTrue(false, "错误");
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
        if (item.getListingType().equals("Chinese")) {
            d.setApiCallName(APINameStatic.AddItem);
        } else {
            d.setApiCallName(APINameStatic.AddFixedPriceItem);
        }
        //String xml= BindAccountAPI.getSessionID(d.getRunname());

        AddApiTask addApiTask = new AddApiTask();

        Map<String, String> resMap = addApiTask.exec(d, xml, sandboxApiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if ("fail".equalsIgnoreCase(r1)) {
            logger.error("预览数据失败，返回xml为>>>>>>>>>>>>>>>>>>"+res);
            SendEMailUtil.sendEmail2Admin("请求xml：：：：：：：：;返回xml：：：：：" + res, "预览数据报错：");
            AjaxSupport.sendFailText("fail", "预览数据失败！");
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");

        if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
            String itemId = SamplePaseXml.getVFromXmlString(res, "ItemID");
            tradingItem.setItemId(itemId);
            tradingItem.setIsFlag("Success");
            //this.iTradingItem.saveTradingItem(tradingItem);
            modelMap.put("itemid",itemId);
            AjaxSupport.sendSuccessText("message", modelMap);
        } else {
            TradingTaskXml ttx = new TradingTaskXml();
            ttx.setCreateDate(new Date());
            ttx.setTaskType("preview");
            ttx.setXmlContent(res);
            iTradingTaskXml.saveTradingTaskXml(ttx);
            logger.error("预览数据失败，返回xml为>>>>>>>>>>>>>>>>>>"+res);
            Document document= SamplePaseXml.formatStr2Doc(res);
            if(document==null){
                AjaxSupport.sendFailText("fail", "预览数据失败,返回报文为空！");
                return;
            }
            Element rootElt = document.getRootElement();
            Iterator<Element> e =  rootElt.elementIterator("Errors");
            String errors = "";
            if(e!=null){
                while (e.hasNext()){
                    Element es = e.next();
                    if(es.element("SeverityCode")!=null&&"Error".equals(es.elementText("SeverityCode"))) {
                        errors += es.elementText("LongMessage") + "/n";
                    }
                }
            }
            SendEMailUtil.sendEmail2Admin("请求xml：：：：：：：：;返回xml：：：：：" + res, "预览数据报错：");
            AjaxSupport.sendFailText("fail", "预览数据失败！"+errors);
        }
    }

    /**
     * 检查ebay费
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/ajax/checkEbayFee.do")
    public void checkEbayFee(TradingItemWithBLOBs tradingItem,Item item,HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception{
        String [] paypals = request.getParameterValues("ebayAccounts");
        //保存商品信息到数据库中
        Map itemMap = this.iTradingItem.saveItem(item,tradingItem);
        if(itemMap.size()==1){
            tradingItem = this.iTradingItem.selectByIdBL(Long.parseLong(itemMap.get(paypals[0])+""));
        }
        //处理刊登模块中的数据
        this.unitItemData(tradingItem,item,request);
        /*//处理模板信息
        this.replateTemplate(request, item, tradingItem);

        if(request.getParameter("SecondaryCategory.CategoryID")==null||"".equals(request.getParameter("SecondaryCategory.CategoryID"))){
            item.setSecondaryCategory(null);
        }
        //刊登天数
        item.setListingDuration(item.getListingDuration() == null ? "GTC" : item.getListingDuration());
        if(item.getDispatchTimeMax()==null) {
            item.setDispatchTimeMax(0);
        }
        //多属性
        if(item.getVariations()!=null) {
            List<Variation> livt = item.getVariations().getVariation();
            for(int i = 0 ; i<livt.size();i++){
                Variation vtion = livt.get(i);
                List<VariationSpecifics> livar = new ArrayList();
                VariationSpecifics vsf = new VariationSpecifics();
                List<NameValueList> linvls = item.getVariations().getVariationSpecificsSet().getNameValueList();
                if(linvls!=null&&linvls.size()>0){
                    List<NameValueList> linameList = new ArrayList();
                    for(NameValueList vs : linvls){
                        NameValueList nvl = new NameValueList();
                        nvl.setName(vs.getName());
                        List li = new ArrayList();
                        li.add(vs.getValue().get(i));
                        nvl.setValue(li);
                        linameList.add(nvl);
                    }
                    vsf.setNameValueList(linameList);
                }
                livar.add(vsf);
                vtion.setVariationSpecifics(livar);
            }
            item.getVariations().setVariation(livt);
            item.getVariations().getPictures().setVariationSpecificName(item.getVariations().getVariationSpecificsSet().getNameValueList().get(0).getName());
            if(item.getStartPrice()!=null){
                tradingItem.setStartprice(item.getStartPrice().getValue());
            }

            List<NameValueList>  linvl= item.getVariations().getVariationSpecificsSet().getNameValueList();
            for(NameValueList nvlst : linvl){
                List<String> listr = nvlst.getValue();
                listr = MyCollectionsUtil.listUnique(listr);
                nvlst.setValue(listr);
            }
            item.getVariations().getVariationSpecificsSet().setNameValueList(linvl);
        }

        item.setListingType(item.getListingType().equals("2")?"FixedPriceItem":item.getListingType());*/

        String xml="";
        //当选择多账号时刊登
        if(item.getStorefront()!=null){
            if(!ObjectUtils.isLogicalNull(item.getStorefront().getStoreCategoryID())&&item.getStorefront().getStoreCategoryID()!=0){
                Storefront sf = new Storefront();
                sf.setStoreCategoryID(item.getStorefront().getStoreCategoryID());
                sf.setStoreCategoryName(item.getStorefront().getStoreCategoryName());
                item.setStorefront(sf);
            }else{
                item.setStorefront(null);
            }
        }else{
            item.setStorefront(null);
        }
        String paypal = paypals[0];
        item.setTitle(request.getParameter("Title_"+paypal));
        item.setSubTitle(request.getParameter("SubTitle_"+paypal));
        if(request.getParameter("Quantity_"+paypal)!=null&&!"".equals(request.getParameter("Quantity_"+paypal))){
            item.setQuantity(Integer.parseInt(request.getParameter("Quantity_"+paypal)));
        }
        if(request.getParameter("StartPrice.value_"+paypal)!=null&&!"".equals(request.getParameter("StartPrice.value_"+paypal))){
            StartPrice sp = new StartPrice();
            sp.setValue(Double.parseDouble(request.getParameter("StartPrice.value_"+paypal)));
            sp.setCurrencyID(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getValue1());
            item.setStartPrice(sp);
        }
        String [] picurl = request.getParameterValues("PictureDetails_"+paypal+".PictureURL");
        if(picurl!=null&&picurl.length>0){
            PictureDetails pd = item.getPictureDetails();
            if(pd==null){
                pd = new PictureDetails();
            }
            List lipic = new ArrayList();
            for(int i = 0;i<picurl.length;i++){
                lipic.add(picurl[i]);
            }
            if(lipic.size()>0){
                pd.setPictureURL(lipic);
            }
            item.setPictureDetails(pd);
        }
        getEbayPicUrl(item,tradingItem,request,paypal);
        //getEbayPicUrl(item,tradingItem,paypal);
        UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(tradingItem.getEbayAccount()));
        UsercontrollerPaypalAccount uepay = payPalService.selectById(ua.getPaypalAccountId());
        //PublicUserConfig pUserConfig = DataDictionarySupport.getPublicUserConfigByID(ua.getPaypalAccountId());
        //如果配置ＥＢＡＹ账号时，选择强制使用paypal账号则用该账号
        //item.setPayPalEmailAddress(pUserConfig.getConfigValue());
        //定时刊登时，需要获取保存到数据库中的ＩＤ
        item.setPayPalEmailAddress(uepay.getEmail());
        if(item.getTitle().length()>70){
            item.setTitle(item.getTitle().substring(0,70)+MyStringUtil.getRandomStringAndNum(5));
        }else{
            item.setTitle(item.getTitle()+MyStringUtil.getRandomStringAndNum(5));
        }
        if (item.getListingType().equals("Chinese")) {
            AddItemRequest addItem = new AddItemRequest();
            addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
            addItem.setErrorLanguage("en_US");
            addItem.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            rc.seteBayAuthToken(ua.getEbayToken());
            addItem.setRequesterCredentials(rc);
            addItem.setItem(item);
            xml = PojoXmlUtil.pojoToXml(addItem);
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
            xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<VerifyAddItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                    "  <ErrorLanguage>en_US</ErrorLanguage>\n" +
                    "  <WarningLevel>High</WarningLevel>"+
                    PojoXmlUtil.pojoToXml(item)+
                    "<RequesterCredentials>\n" +
                    "    <eBayAuthToken>"+ua.getEbayToken()+"</eBayAuthToken>\n" +
                    "  </RequesterCredentials>\n" +
                    "</VerifyAddItemRequest>";
        } else {
            if("2".equals(tradingItem.getListingtype())){
                item.setStartPrice(null);
            }
            AddFixedPriceItemRequest addItem = new AddFixedPriceItemRequest();
            addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
            addItem.setErrorLanguage("en_US");
            addItem.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            rc.seteBayAuthToken(ua.getEbayToken());
            addItem.setRequesterCredentials(rc);
            addItem.setItem(item);
            xml = PojoXmlUtil.pojoToXml(addItem);
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
            xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<VerifyAddFixedPriceItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                    "  <ErrorLanguage>en_US</ErrorLanguage>\n" +
                    "  <WarningLevel>High</WarningLevel>"+
                    PojoXmlUtil.pojoToXml(item)+
                    "<RequesterCredentials>\n" +
                    "    <eBayAuthToken>"+ua.getEbayToken()+"</eBayAuthToken>\n" +
                    "  </RequesterCredentials>\n" +
                    "</VerifyAddFixedPriceItemRequest>";
        }
        //System.out.println(xml);
        //Asserts.assertTrue(false, "错误");
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
        if (item.getListingType().equals("Chinese")) {
            //d.setApiCallName(APINameStatic.AddItem);
            d.setApiCallName("VerifyAddItem");
        } else {
            //d.setApiCallName(APINameStatic.AddFixedPriceItem);
            d.setApiCallName("VerifyAddFixedPriceItem");
        }
        //String xml= BindAccountAPI.getSessionID(d.getRunname());

        AddApiTask addApiTask = new AddApiTask();
        //logger.error("请求参数：：：：：：：："+xml);
        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
        String r1 = resMap.get("stat");
        String res = resMap.get("message");
        if ("fail".equalsIgnoreCase(r1)) {
            logger.error("检查EBAY费失败，返回xml为>>>>>>>>>>>>>>>>>>"+res);
            SendEMailUtil.sendEmail2Admin("请求xml：：：：：：：：;返回xml：：：：：" + res, "检查ebay费报错：");
            AjaxSupport.sendFailText("fail", "提交数据不全，请稍后重试");
            return;
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");

        //logger.error("返加参数：：：：：：："+res);
        if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
            /*String itemId = SamplePaseXml.getVFromXmlString(res, "ItemID");
            tradingItem.setItemId(itemId);
            tradingItem.setIsFlag("Success");
            //this.iTradingItem.saveTradingItem(tradingItem);
            modelMap.put("tradingItem",tradingItem);*/
            Document document= SamplePaseXml.formatStr2Doc(res);
            if(document==null){
                logger.error("对象对空，返回报文为：；；：：：：："+res);
                return;
            }
            Element rootElt = document.getRootElement();
            Element recommend = rootElt.element("Fees");
            Iterator<Element> iter = recommend.elementIterator("Fee");
            List<Map> lim = new ArrayList<Map>();
            while (iter.hasNext()){
                Element ment = iter.next();
                if(ment==null){
                    continue;
                }
                if(ment.elementText("Fee")!=null&&Double.parseDouble(ment.elementText("Fee"))>0){
                    Map m = new HashMap();
                    m.put("name",ment.elementText("Name"));
                    m.put("value",ment.elementText("Fee"));
                    lim.add(m);
                }
                if((ment.elementText("PromotionalDiscount")!=null&&Double.parseDouble(ment.elementText("PromotionalDiscount"))>0)){
                    Map m = new HashMap();
                    m.put("name",ment.elementText("Name"));
                    m.put("value",ment.elementText("PromotionalDiscount")+""+ment.element("PromotionalDiscount").attributeValue("currencyID"));
                    lim.add(m);
                }
            }
            AjaxSupport.sendSuccessText("message", lim);
        } else {
            //String errors = SamplePaseXml.getVFromXmlString(res, "Errors");
            TradingTaskXml ttx = new TradingTaskXml();
            ttx.setCreateDate(new Date());
            ttx.setTaskType("checkEbayFee");
            ttx.setXmlContent(xml);
            logger.error("检查EBAY费失败，返回xml为>>>>>>>>>>>>>>>>>>"+res);
            iTradingTaskXml.saveTradingTaskXml(ttx);
            Document document= SamplePaseXml.formatStr2Doc(res);
            if(document==null){
                AjaxSupport.sendFailText("fail", "检查ebay费失败:返回报文为空！");
                return;
            }else {
                Element rootElt = document.getRootElement();
                Iterator<Element> e = rootElt.elementIterator("Errors");
                String errors = "";
                if (e != null) {
                    while (e.hasNext()) {
                        Element es = e.next();
                        if (es.element("SeverityCode") != null && "Error".equals(es.elementText("SeverityCode"))) {
                            errors += es.elementText("LongMessage") + "/n";
                        }
                    }
                }
                SendEMailUtil.sendEmail2Admin("请求xml：：：：：：：：;返回xml：：：：：" + res, "检查ebay费报错：");
                AjaxSupport.sendFailText("fail", "检查ebay费失败:" + errors);
                return;
            }
        }
    }

    /**
     * 列表界面,立即刊登
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/listingItem.do")
    @ResponseBody
    public void listingItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String idstr = request.getParameter("id");
        String [] ids = idstr.split(",");
        AddApiTask addApiTask = new AddApiTask();
        String xml = "";
        List<String> successmessage=new ArrayList<String>();
        List<String> errormessage=new ArrayList<String>();
        for(int i=0;i<ids.length;i++){
            TradingItemWithBLOBs tradingItem = this.iTradingItem.selectByIdBL(Long.parseLong(ids[i]));
            Item item = this.iTradingItem.toItem(tradingItem);
            item.setApplicationData(tradingItem.getApplicationdata());
            UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(tradingItem.getEbayAccount()));
            if(tradingItem.getListingtype().equals("Chinese")){
                StartPrice sp = new StartPrice();
                sp.setValue(tradingItem.getStartprice());
                sp.setCurrencyID(tradingItem.getCurrency());
                item.setStartPrice(sp);
                AddItemRequest addItem = new AddItemRequest();
                addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                addItem.setErrorLanguage("en_US");
                addItem.setWarningLevel("High");
                RequesterCredentials rc = new RequesterCredentials();
                rc.seteBayAuthToken(ua.getEbayToken());
                addItem.setRequesterCredentials(rc);
                addItem.setItem(item);
                xml = PojoXmlUtil.pojoToXml(addItem);
                xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
            }else{
                AddFixedPriceItemRequest addItem = new AddFixedPriceItemRequest();
                addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                addItem.setErrorLanguage("en_US");
                addItem.setWarningLevel("High");
                RequesterCredentials rc = new RequesterCredentials();
                rc.seteBayAuthToken(ua.getEbayToken());
                addItem.setRequesterCredentials(rc);
                addItem.setItem(item);
                xml = PojoXmlUtil.pojoToXml(addItem);
                xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
            }
            TradingTaskXml ttx = new TradingTaskXml();
            ttx.setCreateDate(new Date());
            ttx.setTaskType("addItemList");
            ttx.setXmlContent(xml);
            iTradingTaskXml.saveTradingTaskXml(ttx);
            //System.out.println(xml);

            UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
            d.setApiSiteid(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
            if (item.getListingType().equals("Chinese")) {
                d.setApiCallName(APINameStatic.AddItem);
            } else {
                d.setApiCallName(APINameStatic.AddFixedPriceItem);
            }
            //后台刊登
            /*TaskMessageVO taskMessageVO=new TaskMessageVO();
            taskMessageVO.setMessageContext("刊登");
            taskMessageVO.setMessageTitle("刊登操作");
            taskMessageVO.setMessageType(SiteMessageStatic.LISTING_MESSAGE_TYPE);
            taskMessageVO.setBeanNameType(SiteMessageStatic.LISTING_ITEM_BEAN);
            taskMessageVO.setMessageFrom("system");
            SessionVO sessionVO=SessionCacheSupport.getSessionVO();
            taskMessageVO.setMessageTo(sessionVO.getId());
            taskMessageVO.setObjClass(tradingItem);
            addApiTask.execDelayReturn(d, xml, apiUrl, taskMessageVO);*/
            //前台刊登
            Map<String, String> resMap= addApiTask.exec(d, xml, apiUrl);
            String res=resMap.get("message");
            String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
            if(ack.equals("Success")||"Warning".equalsIgnoreCase(ack)){
                tradingItem.setIsFlag("Success");
                String itemId = SamplePaseXml.getVFromXmlString(res, "ItemID");
                tradingItem.setItemId(itemId);
                this.iTradingItem.saveTradingItem(tradingItem);
                this.iTradingItem.saveListingSuccess(res,itemId);
                TradingTembinListingLog ttll = new TradingTembinListingLog();
                ttll.setDocId(tradingItem.getId());
                ttll.setCreateDate(new Date());
                ttll.setIsTimer("0");
                ttll.setItemId(itemId);
                ttll.setListingType(tradingItem.getListingtype());
                iTradingTembinListingLog.saveTradingTembinListingLog(ttll);
                //新增在线商品数据
                this.iTradingListingData.saveTradingListingDataByTradingItem(tradingItem,res);
                SystemLog sl = SystemLogUtils.selectSystemLogByTableId("ListingItemdo",tradingItem.getId());
                if(sl!=null){
                    sl.setEventname(sl.getEventname()+"_del");
                    SystemLogUtils.updateSystemLog(sl);
                }
                successmessage.add("商品SKU为："+tradingItem.getSku()+"，名称为：<a target=_blank style='color:blue' href='"+service_item_url+itemId+"'>"+tradingItem.getItemName()+"<a>，刊登成功！");
            }else{
                Document document= SamplePaseXml.formatStr2Doc(res);
                Element rootElt = document.getRootElement();
                Element tl = rootElt.element("Errors");
                String longMessage = tl.elementText("LongMessage");

                Iterator<Element> e =  rootElt.elementIterator("Errors");
                String errorCode = "";
                String errors = "";
                if(e!=null){
                    while (e.hasNext()){
                        Element es = e.next();
                        if(es.element("SeverityCode")!=null&&"Error".equals(es.elementText("SeverityCode"))) {
                            errors += es.elementText("LongMessage") + ";";
                            errorCode+=es.elementText("SeverityCode")+";";
                        }
                    }
                }
                SystemLog sl = SystemLogUtils.selectSystemLogByTableId("ListingItemdo",tradingItem.getId());
                if(sl==null) {
                    sl = new SystemLog();
                }
                sl.setCreatedate(new Date());
                sl.setOperuser(tradingItem.getCreateUser() + "");
                sl.setTableId(tradingItem.getId());
                sl.setEventname("ListingItemdo");
                sl.setOther(errorCode);
                sl.setEventdesc("刊登失败！原因：" + errors);
                SystemLogUtils.saveLog(sl);
                SendEMailUtil.sendEmail2Admin("请求xml：：：：：：：：;返回xml：：：：：" + res, "界面列表刊登报错：");
                logger.error("商品SKU为："+tradingItem.getSku()+"，名称为："+tradingItem.getItemName()+"，刊登失败！失败原因："+errors);
                errormessage.add("商品SKU为："+tradingItem.getSku()+"，名称为："+tradingItem.getItemName()+"，刊登失败！失败原因："+errors);
            }
        }
        Map m = new HashMap();
        if(successmessage.size()>0){
            m.put("su",successmessage);
        }
        if(errormessage.size()>0){
            m.put("er",errormessage);
        }
        AjaxSupport.sendSuccessText("message", m);
    }

    /**
     * 删除范本
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/delItem.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void delItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("ids");
        String [] ids = id.split(",");
        if(ids!=null&&ids.length>0){
            try{
                this.iTradingItem.delItem(ids);
                for(String str:ids){
                    this.iTradingTimerListing.delTradingTimer(str);
                }
                AjaxSupport.sendSuccessText("",ids);
            }catch(Exception e){
                logger.error("删除失败!",e);
                AjaxSupport.sendSuccessText("","删除失败!");
            }

        }else {
            AjaxSupport.sendSuccessText("","请选择需要删除的范本!");
        }

    }

    /**
     * 重命名
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/rename.do")
    @ResponseBody
    public void rename(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("ids");
        String [] ids = id.split(",");
        String fileName = request.getParameter("fileName");
        if(ids!=null&&ids.length>0){
            try{
                this.iTradingItem.rename(ids,fileName);
                AjaxSupport.sendSuccessText("","操作成功!");
            }catch(Exception e){
                logger.error(id+"::",e);
                AjaxSupport.sendSuccessText("","删除失败!");
            }

        }else {
            AjaxSupport.sendSuccessText("","请选择需要删除的范本!");
        }
    }

    /**
     * 获取登录用户的Ｅbay账号
     * @param request
     * @param modelMap
     * @param commonParmVO
     * @throws Exception
     */
    @RequestMapping("/ajax/selfEbayAccount.do")
    @ResponseBody
    public void selfEbayAccount(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        AjaxSupport.sendSuccessText("",ebayList);
    }

    /**
     * 复制
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/copyItem.do")
    @ResponseBody
    public void copyItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("ids");
        String [] ids = id.split(",");
        String ebayaccount = request.getParameter("ebayaccount");
        if(ids!=null&&ids.length>0){
            try{
                this.iTradingItem.copyItem(ids,ebayaccount);
                AjaxSupport.sendSuccessText("","操作成功!");
            }catch(Exception e){
                logger.error("复制报错",e);
                AjaxSupport.sendSuccessText("","复制失败!");
            }
        }else {
            AjaxSupport.sendSuccessText("","请选择需要删除的范本!");
        }
    }

    /**
     * 得到分类名称
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/getCategoryName.do")
    @ResponseBody
    public void getCategoryName(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String categoryId = request.getParameter("categoryId");
        String siteId = request.getParameter("siteId");
        selectNumber=0;
        String categoryName = this.selectBycriId(categoryId,siteId);
        AjaxSupport.sendSuccessText("",categoryName);
    }


    public String selectBycriId(String categoryId,String siteId){
        List<PublicDataDict> lipdd = this.iTradingDataDictionary.selectByDicExample(categoryId, siteId);
        if(lipdd==null||lipdd.size()==0){
            return null;
        }

        PublicDataDict pdd = lipdd.get(0);
        String categoryName = "";
        if(selectNumber>=5){
            return pdd.getItemEnName();
        }
        selectNumber++;
        if(!"0".equals(pdd.getItemParentId())&&!(pdd.getItemId().equals(pdd.getItemParentId()))){
            categoryName = this.selectBycriId(pdd.getItemParentId(),siteId)+" -> "+pdd.getItemEnName();
        }else{
            categoryName = pdd.getItemEnName();
        }
        return categoryName;
    }


    /**
     * 选择站点
     * @param request
     * @param response
     * @param modelMap
     * @return
     */
    @RequestMapping("/selectSiteList.do")
    public ModelAndView selectSiteList(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap){
        String siteidStr = request.getParameter("siteidStr");
        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);
        modelMap.put("siteidStr",siteidStr);
        return forword("item/selectSiteList",modelMap);
    }

    @RequestMapping("/editMoreItem.do")
    public ModelAndView editMoreItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception{
        String idsStr = request.getParameter("ids");
        String [] ids = idsStr.split(",");
        modelMap.put("idsStr",idsStr);
        TradingItem tradingItem = this.iTradingItem.selectById(Long.parseLong(ids[0]));
        Item item = this.iTradingItem.toEditItem(tradingItem);
        modelMap.put("sku",tradingItem.getSku());
        modelMap.put("ebayAccount",tradingItem.getEbayAccount());
        List<TradingPicturedetails> litp = this.iTradingPictureDetails.selectByParentId(tradingItem.getId());
        for(TradingPicturedetails tp : litp){
            List<TradingAttrMores> lipic = this.iTradingAttrMores.selectByParnetid(tp.getId(),"PictureURL");
            if(lipic!=null&&lipic.size()>0){
                modelMap.put("lipic",lipic);
            }
        }

        modelMap.put("item",item);
        modelMap.put("itemidstr",item.getItemID());
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<PublicUserConfig> paypalList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        modelMap.put("paypalList",paypalList);

        List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
        modelMap.put("siteList",lidata);

        List<TradingDataDictionary> acceptList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.RETURNS_ACCEPTED_OPTION);
        modelMap.put("acceptList",acceptList);

        List<TradingDataDictionary> withinList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.RETURNS_WITHIN_OPTION);
        modelMap.put("withinList",withinList);

        List<TradingDataDictionary> refundList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.REFUND_OPTION);
        modelMap.put("refundList",refundList);

        List<TradingDataDictionary> costPaidList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.SHIPPING_COST_PAID);
        modelMap.put("costPaidList",costPaidList);

        List<TradingDataDictionary> lidatas = this.iTradingDataDictionary.selectDictionaryByType("country");
        modelMap.put("countryList",lidatas);
        Long siteid = 0L;
        for(TradingDataDictionary tdd : lidata){
            if(tdd.getValue().equals(item.getSite())){
                siteid=tdd.getId();
                break;
            }
        }
        modelMap.put("siteid",siteid);
        List<TradingDataDictionary> litype = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPING_TYPE,siteid);
        /*List<TradingDataDictionary> li1 = new ArrayList<TradingDataDictionary>();
        List<TradingDataDictionary> li2 = new ArrayList<TradingDataDictionary>();
        List<TradingDataDictionary> li3 = new ArrayList<TradingDataDictionary>();
        List<TradingDataDictionary> li4 = new ArrayList<TradingDataDictionary>();
        List<TradingDataDictionary> li5 = new ArrayList<TradingDataDictionary>();
        for(TradingDataDictionary tdd:litype){
            if("Economy services".equals(tdd.getName1())){
                li1.add(tdd);
            }else if("Expedited services".equals(tdd.getName1())){
                li2.add(tdd);
            }else if("One-day services".equals(tdd.getName1())){
                li3.add(tdd);
            }else if("Other services".equals(tdd.getName1())){
                li4.add(tdd);
            }else if("Standard services".equals(tdd.getName1())){
                li5.add(tdd);
            }
        }
        modelMap.put("li1",li1);
        modelMap.put("li2",li2);
        modelMap.put("li3",li3);
        modelMap.put("li4",li4);
        modelMap.put("li5",li5);

        List<TradingDataDictionary> liinter = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPINGINTER_TYPE);
        List<TradingDataDictionary> inter1 = new ArrayList();
        List<TradingDataDictionary> inter2 = new ArrayList();
        for(TradingDataDictionary tdd:liinter){
            if(tdd.getName1().equals("Expedited services")){
                inter1.add(tdd);
            }else if(tdd.getName1().equals("Other services")){
                inter2.add(tdd);
            }
        }
        modelMap.put("inter1",inter1);
        modelMap.put("inter2",inter2);*/

        List<TradingDataDictionary> lipackage = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPINGPACKAGE);

        modelMap.put("lipackage",lipackage);

        List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_EBAYACCOUNT, c.getId());
        modelMap.put("ebayList",ebayList);
        return forword("item/editMoreItem",modelMap);
    }


    /**
     * 批量编辑修改
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/saveMoreItem.do")
    @ResponseBody
    public void saveMoreItem(Item item,TradingItem tradingItem,HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String [] selectType = request.getParameterValues("selectType");
        String isUpdateFlag = request.getParameter("isUpdateFlag");
        String listingType = request.getParameter("listingType");
        String itemidStr = request.getParameter("ItemID");
        String idsStr = request.getParameter("idsStr");
        String [] itemid = itemidStr.split(",");
        String [] ids = idsStr.split(",");

        for(int i=0;i<ids.length;i++){
            TradingItemWithBLOBs tradingItem1=this.iTradingItem.selectByIdBL(Long.parseLong(ids[i]));

            if("1".equals(isUpdateFlag)&&tradingItem1.getItemId()!=null){//需要更新在线商
                item.setItemID(tradingItem1.getItemId());
                //TradingListingData tld = this.iTradingListingData.selectByItemid(item.getItemID());
                UsercontrollerEbayAccount uea = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(tradingItem1.getEbayAccount()));
                Item ite = new Item();
                List litla = new ArrayList();
                ReviseItemRequest rir = new ReviseItemRequest();
                rir.setXmlns("urn:ebay:apis:eBLBaseComponents");
                rir.setErrorLanguage("en_US");
                rir.setWarningLevel("High");
                RequesterCredentials rc = new RequesterCredentials();
                String ebayAccount = uea.getEbayAccount();
                SessionVO c= SessionCacheSupport.getSessionVO();
                String token =uea.getEbayToken();
                rc.seteBayAuthToken(token);
                rir.setRequesterCredentials(rc);
                ite.setItemID(item.getItemID());
                for(String str : selectType){
                    TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
                    tla.setItem(Long.parseLong(tradingItem1.getItemId()));
                    tla.setParentId(tradingItem1.getId());
                    if(str.equals("StartPrice")){//改价格
                        tla.setAmendType("StartPrice");
                        if("FixedPriceItem".equals(listingType)) {
                            ite.setStartPrice(item.getStartPrice());
                            tla.setContent("将价格从" + tradingItem1.getStartprice() + "修改为" + item.getStartPrice().getValue());
                            tradingItem1.setStartprice(item.getStartPrice().getValue());
                            Item ites = new Item();
                            ites.setItemID(item.getItemID());
                            ites.setStartPrice(item.getStartPrice());
                            rir.setItem(ites);
                            tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                        }else if("2".equals(listingType)){
                            tla.setContent("多属性价格调整！");
                            ite.setVariations(item.getVariations());
                            Item ites = new Item();
                            ites.setItemID(item.getItemID());
                            ites.setVariations(item.getVariations());
                            rir.setItem(ites);
                            tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                        }else if("Chinese".equals(listingType)){
                            tla.setContent("将价格从" + tradingItem1.getStartprice() + "修改为" + item.getStartPrice().getValue());
                            tradingItem1.setStartprice(item.getStartPrice().getValue());
                            item.setBuyItNowPrice(item.getStartPrice().getValue());
                            Item ites = new Item();
                            ites.setItemID(item.getItemID());
                            ites.setBuyItNowPrice(item.getBuyItNowPrice());
                            rir.setItem(ites);
                            tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                        }

                    }else if(str.equals("Quantity")){//改数量
                        tla.setAmendType("Quantity");
                        tla.setContent("将数量从" + tradingItem1.getQuantity() + "修改为" + item.getQuantity());
                        ite.setQuantity(item.getQuantity());
                        tradingItem1.setQuantity(item.getQuantity().longValue());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setQuantity(item.getQuantity());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("PictureDetails")){//改图片
                        tla.setAmendType("PictureDetails");
                        tla.setContent("图片修改");

                        Item ites = new Item();
                        String [] pics = request.getParameterValues("PictureDetails_"+item.getItemID()+".PictureURL");
                        PictureDetails pd = new PictureDetails();
                        pd.setGalleryType("Gallery");
                        pd.setGalleryURL(pics[0]);
                        pd.setPhotoDisplay("PicturePack");
                        List<String> listr = new ArrayList<String>();
                        for(String pic:pics){
                            String mackid = EncryptionUtil.md5Encrypt(pic);
                            List<TradingListingpicUrl> litamss = this.iTradingListingPicUrl.selectByMackId(mackid);
                            if(litamss==null||litamss.size()==0){
                                listr.add(pic);
                            }else {
                                TradingListingpicUrl tam = litamss.get(0);
                                listr.add(tam.getEbayurl());
                            }
                        }
                        pd.setPictureURL(listr);
                        ite.setPictureDetails(pd);
                        ites.setPictureDetails(pd);
                        ites.setItemID(item.getItemID());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("PayPal")){//改支付方式
                        tla.setAmendType("PayPal");
                        tla.setContent("支付方式修改");
                        ite.setPayPalEmailAddress(item.getPayPalEmailAddress());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setPayPalEmailAddress(item.getPayPalEmailAddress());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("ReturnPolicy")){//改退货政策
                        tla.setAmendType("ReturnPolicy");
                        tla.setContent("退货政策修改");
                        ite.setReturnPolicy(item.getReturnPolicy());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setReturnPolicy(item.getReturnPolicy());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("Title")){//改标题　
                        tla.setAmendType("Title");
                        tla.setContent("标题修改为："+item.getTitle());
                        ite.setTitle(item.getTitle());
                        tradingItem1.setTitle(item.getTitle());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setTitle(item.getTitle());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("Buyer")){//改买家要求
                        tla.setAmendType("Buyer");
                        tla.setContent("修改买家要求");
                        ite.setBuyerRequirementDetails(item.getBuyerRequirementDetails());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setBuyerRequirementDetails(item.getBuyerRequirementDetails());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("SKU")){//改ＳＫＵ
                        tla.setAmendType("SKU");
                        tla.setContent("SKU修改为："+item.getSKU());
                        ite.setSKU(item.getSKU());
                        tradingItem1.setSku(item.getSKU());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setSKU(item.getSKU());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("PrimaryCategory")){//改分类
                        tla.setAmendType("PrimaryCategory");
                        tla.setContent("商品分类修改为:"+item.getPrimaryCategory().getCategoryID());
                        ite.setPrimaryCategory(item.getPrimaryCategory());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setPrimaryCategory(item.getPrimaryCategory());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("ConditionID")){//改商品状态
                        tla.setAmendType("ConditionID");
                        tla.setContent("修改商品状态");
                        ite.setConditionID(item.getConditionID());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setConditionID(item.getConditionID());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("Location")){//改运输到的地址
                        tla.setAmendType("Location");
                        ite.setLocation(item.getLocation());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setLocation(item.getLocation());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("DispatchTimeMax")){//最快处理时间
                        tla.setAmendType("DispatchTimeMax");
                        tla.setContent("修改处理时间");
                        ite.setDispatchTimeMax(item.getDispatchTimeMax());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setDispatchTimeMax(item.getDispatchTimeMax());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("PrivateListing")){//改是否允许私人买
                        tla.setAmendType("PrivateListing");
                        ite.setPrivateListing(item.getPrivateListing());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setPrivateListing(item.getPrivateListing());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("ListingDuration")){//改刊登天数
                        tla.setAmendType("ListingDuration");
                        tla.setContent("修改刊登天数为："+item.getListingDuration());
                        ite.setListingDuration(item.getListingDuration());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setListingDuration(item.getListingDuration());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("Description")){//改商品描述
                        tla.setContent("修改商品描述");
                        tla.setAmendType("Description");
                        ite.setDescription(item.getDescription());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setDescription(item.getDescription());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }else if(str.equals("ShippingDetails")){//改运输详情
                        tla.setAmendType("ShippingDetails");
                        tla.setContent("修改运输详情");
                        ShippingDetails sdf = item.getShippingDetails();
                        String nottoLocation = request.getParameter("notLocationValue");
                        if(!ObjectUtils.isLogicalNull(nottoLocation)){
                            String noLocation[] =nottoLocation.split(",");
                            List listr = new ArrayList();
                            for(String nostr : noLocation){
                                listr.add(nostr);
                            }
                            sdf.setExcludeShipToLocation(listr);
                        }
                        ite.setShippingDetails(sdf);
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setShippingDetails(item.getShippingDetails());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }
                    litla.add(tla);
                }

                String xml = "";
                rir.setItem(ite);
                xml = PojoXmlUtil.pojoToXml(rir);
                xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
                //System.out.println(xml);

                String returnString = this.cosPostXml(xml,APINameStatic.ReviseItem);
                String ack = SamplePaseXml.getVFromXmlString(returnString,"Ack");
                if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
                    this.saveAmend(litla, "1");
                }else{
                    this.saveAmend(litla,"0");
                    Document document= SamplePaseXml.formatStr2Doc(returnString);
                    Element rootElt = document.getRootElement();
                    Element tl = rootElt.element("Errors");
                    String longMessage = tl.elementText("LongMessage");
                    if(longMessage==null){
                        longMessage = tl.elementText("ShortMessage");
                    }
                    this.saveSystemLog(longMessage,"在线修改商品报错",SiteMessageStatic.LISTING_DATA_UPDATE);
                    AjaxSupport.sendFailText("fail",longMessage);
                }
            }
            if(tradingItem1!=null){//更新数据库中的范本
                this.iTradingItem.updateTradingItem(item,tradingItem1,ids[i]);
            }
        }
        AjaxSupport.sendSuccessText("message", "操作成功！");
    }


    public String cosPostXml(String colStr,String month) throws Exception {
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(1L);
        d.setApiSiteid("0");
        d.setApiCallName(month);
        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap= addApiTask.exec(d, colStr, apiUrl);
        String res=resMap.get("message");
        String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
        if(ack.equals("Success")||"Warning".equalsIgnoreCase(ack)){
            return res;
        }else{
            return res;
        }

    }

    public void saveAmend(List<TradingListingAmendWithBLOBs> litlam,String isflag) throws Exception {
        for(TradingListingAmendWithBLOBs tla : litlam){
            ObjectUtils.toInitPojoForInsert(tla);
            tla.setIsFlag(isflag);
            this.iTradingListingData.insertTradingListingAmend(tla);
        }
    }

    public void saveSystemLog(String context,String title,String type){
        SiteMessageService siteMessageService= (SiteMessageService) ApplicationContextUtil.getBean(SiteMessageService.class);
        TaskMessageVO taskMessageVO=new TaskMessageVO();
        taskMessageVO.setMessageContext(context);
        taskMessageVO.setMessageTitle(title);
        taskMessageVO.setMessageType(type);
        taskMessageVO.setMessageFrom("system");
        SessionVO sessionVO=SessionCacheSupport.getSessionVO();
        taskMessageVO.setMessageTo(sessionVO.getId());
        taskMessageVO.setObjClass(null);
        siteMessageService.addSiteMessage(taskMessageVO);
    }

    /**
     * 取消定时任务
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/delTradingTimer.do")
    @ResponseBody
    public void delTradingTimer(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String itemids = request.getParameter("itemids");
        String [] itemid = itemids.split(",");
        for(String id:itemid){
            this.iTradingTimerListing.delTradingTimer(id);
            TradingItemWithBLOBs tradingItemWithBLOBs = this.iTradingItem.selectByIdBL(Long.parseLong(id));
            tradingItemWithBLOBs.setListingWay("0");
            this.iTradingItem.saveTradingItem(tradingItemWithBLOBs);
        }
        AjaxSupport.sendSuccessText("message", "操作成功！");
    }

    /**
     * 添加备注
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/addItemRemark.do")
    @ResponseBody
    public void addItemRemark(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        String [] ids = id.split(",");
        String remark = request.getParameter("remark");
        if(remark==null||"".equals(remark)){
            remark="";
        }else {
            remark = new String(remark.getBytes("ISO-8859-1"), "UTF-8");
        }
        List<TradingItem> litld = new ArrayList<TradingItem>();
        for(int i=0;i<ids.length;i++) {
            TradingItemWithBLOBs tradingItem = this.iTradingItem.selectByIdBL(Long.parseLong(ids[i]));
            if(tradingItem!=null){
                tradingItem.setRemark(remark);
                this.iTradingItem.saveTradingItem(tradingItem);
                litld.add(tradingItem);
            }
        }
        AjaxSupport.sendSuccessText("",litld);
    }

    @RequestMapping("/ajax/timerListingItem.do")
    @ResponseBody
    public void timerListingItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String idstr = request.getParameter("id");
        String timerStr = request.getParameter("timerStr");
        String [] ids = idstr.split(",");
        String xml = "";
        List<Map> lim = new ArrayList();
        for(int i=0;i<ids.length;i++){
            TradingItemWithBLOBs tradingItem = this.iTradingItem.selectByIdBL(Long.parseLong(ids[i]));
            Item item = this.iTradingItem.toItem(tradingItem);
            UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(tradingItem.getEbayAccount()));
            if(tradingItem.getListingtype().equals("Chinese")){
                item.setQuantity(1);
                StartPrice sp = new StartPrice();
                sp.setValue(tradingItem.getStartprice());
                sp.setCurrencyID(tradingItem.getCurrency());
                item.setStartPrice(sp);
                AddItemRequest addItem = new AddItemRequest();
                addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                addItem.setErrorLanguage("en_US");
                addItem.setWarningLevel("High");
                RequesterCredentials rc = new RequesterCredentials();
                rc.seteBayAuthToken(ua.getEbayToken());
                addItem.setRequesterCredentials(rc);
                addItem.setItem(item);
                xml = PojoXmlUtil.pojoToXml(addItem);
                xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
            }else{
                AddFixedPriceItemRequest addItem = new AddFixedPriceItemRequest();
                addItem.setXmlns("urn:ebay:apis:eBLBaseComponents");
                addItem.setErrorLanguage("en_US");
                addItem.setWarningLevel("High");
                RequesterCredentials rc = new RequesterCredentials();
                rc.seteBayAuthToken(ua.getEbayToken());
                addItem.setRequesterCredentials(rc);
                addItem.setItem(item);
                xml = PojoXmlUtil.pojoToXml(addItem);
                xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + xml;
            }
            tradingItem.setListingWay("1");
            this.iTradingItem.saveTradingItem(tradingItem);

            //System.out.println(xml);
            TradingTimerListingWithBLOBs ttl = new TradingTimerListingWithBLOBs();
            ttl.setItem(tradingItem.getId());
            ttl.setTimer(DateUtils.parseDateTime(timerStr));
            ttl.setTimerMessage(StringEscapeUtils.escapeXml(xml));
            if (item.getListingType().equals("Chinese")) {
                ttl.setApiMethod(APINameStatic.AddItem);
            } else {
                ttl.setApiMethod(APINameStatic.AddFixedPriceItem);
            }
            ttl.setEbayId(tradingItem.getEbayAccount());
            ttl.setStateId(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tradingItem.getSite())).getName1());
            this.iTradingTimerListing.saveTradingTimer(ttl);
            Map m = new HashMap();
            m.put("id",tradingItem.getId());
            m.put("sku",tradingItem.getSku());
            lim.add(m);
        }
        AjaxSupport.sendSuccessText("message", lim);
    }

    @RequestMapping("/ajax/getSiteUrl.do")
    @ResponseBody
    public void getSiteUrl(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        CommAutowiredClass commAutowiredClass= (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);
        List<TradingDataDictionary> litdd = DataDictionarySupport.getTradingDataDictionaryByType("site");
        modelMap.put("siteListStr", JSON.toJSONString(litdd));
        modelMap.put("siteUrlStr",commAutowiredClass.serviceItemUrls);
        AjaxSupport.sendSuccessText("message", modelMap);
    }
    @RequestMapping("/ajax/getPaypalIdStr.do")
    @ResponseBody
    public void getPaypalIdStr(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception {
        String [] ebayId = request.getParameterValues("ebayId");
        List<String> listr = new ArrayList<String>();
        if(ebayId!=null){
            for(String str:ebayId){
                UsercontrollerEbayAccount uea = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(str));
                listr.add(uea.getPaypalAccountId()+"");
            }
        }
        AjaxSupport.sendSuccessText("message", listr);
    }

    @RequestMapping("/ajax/checkPicUrlEbay.do")
    @ResponseBody
    public void checkPicUrlEbay(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        //主图片mackid
        String picmackid = request.getParameter("pic_mackid");
        String morepicid = request.getParameter("pic_mackid_more");
        String [] picmackids = null;
        //多属性图片mackid
        String [] morepicmackid = null;
        if(!ObjectUtils.isLogicalNull(picmackid)){
            picmackids = picmackid.split(",");
        }
        if(!ObjectUtils.isLogicalNull(morepicid)){
            morepicmackid = morepicid.split(",");
        }
        List<TradingListingpicUrl> li = new ArrayList<>();
        if(picmackids!=null&&picmackids.length>0){
            for(String mackid : picmackids){
                List<TradingListingpicUrl> litlu = this.iTradingListingPicUrl.selectByMackId(mackid);
                if(litlu!=null&&litlu.size()>0){
                    TradingListingpicUrl tlu = litlu.get(0);
                    if(tlu.getEbayurl()!=null&&!"".equals(tlu.getEbayurl())){
                        //如果该ebay地址有值，那么说明该图片已上传到ebay图片网址，不需要再上传
                    }else{//图片未上传成功
                        li.add(tlu);
                    }
                }
            }
        }

        if(morepicmackid!=null&&morepicmackid.length>0){
            for(String mackid : morepicmackid){
                List<TradingListingpicUrl> litlu = this.iTradingListingPicUrl.selectByMackId(mackid);
                if(litlu!=null&&litlu.size()>0){
                    TradingListingpicUrl tlu = litlu.get(0);
                    if(tlu.getEbayurl()!=null&&!"".equals(tlu.getEbayurl())){
                        //如果该ebay地址有值，那么说明该图片已上传到ebay图片网址，不需要再上传
                    }else{//图片未上传成功
                        li.add(tlu);
                    }
                }
            }
        }
        PageJsonBean jsonBean= new PageJsonBean();

        Page page=new Page();
        page.setCurrentPage(1);
        page.setPageSize(20);
        jsonBean.setPageCount(1);
        jsonBean.setPageNum(1);
        jsonBean.setTotal(20);
        jsonBean.setList(li);
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    @RequestMapping("/ajax/uploadPicToEbay.do")
    @ResponseBody
    public void uploadPicToEbay(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        ImageService imageService1= (ImageService) ApplicationContextUtil.getBean(ImageService.class);
        String ids = request.getParameter("ids");
        String ebayAccount = request.getParameter("ebayAccount");
        SessionVO c = SessionCacheSupport.getSessionVO();
        if(ids!=null){
            String [] id = ids.split(",");
            if(id!=null&&id.length>0){
                for(String str:id){
                    TradingListingpicUrl tlu = this.iTradingListingPicUrl.queryByid(Long.parseLong(str));
                    if(tlu.getEbayurl()!=null&&!"".equals(tlu.getEbayurl())){
                        AjaxSupport.sendSuccessText("","1");
                        return;
                    }else{
                        Map<String,String> map=imageService1.getFTPINfo();
                        String urls = tlu.getUrl().replace("img.tembin.com", map.get("ftpIP"));
                        //String urls = tlu.getUrl().replace("192.168.0.241",map.get("ftpIP"));
                        String picName = StringUtils.substringAfterLast(tlu.getUrl(),"/");
                        tlu.setUrl(this.upPic(urls));
                        /*if(TempStoreDataSupport.pullData(c.getId()+""+picName)!=null&&Integer.parseInt(TempStoreDataSupport.pullData(c.getId()+""+picName).toString())>0) {
                            tlu.setUrl(this.upPic(urls));
                        }*/
                        UsercontrollerEbayAccount ua = this.iUsercontrollerEbayAccount.selectById(Long.parseLong(ebayAccount));
                        AddApiTask addApiTask = new AddApiTask();
                        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
                        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                                "<UploadSiteHostedPicturesRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                                "  <RequesterCredentials>\n" +
                                "    <eBayAuthToken>"+ua.getEbayToken()+"</eBayAuthToken>\n" +
                                "  </RequesterCredentials>\n" +
                                "  <WarningLevel>High</WarningLevel>\n" +
                                "  <ExternalPictureURL>"+tlu.getUrl()+"</ExternalPictureURL>\n" +
                                "  <PictureName>"+picName+"</PictureName>\n" +
                                "</UploadSiteHostedPicturesRequest>";
                        d.setApiSiteid("0");
                        d.setApiCallName(APINameStatic.UploadSiteHostedPictures);
                        logger.error(">>调用上传图片API开始>>:"+DateUtils.formatDateTime(new Date()));
                        Map<String, String> resMap = addApiTask.exec(d, xml, apiUrl);
                        logger.error(">>调用上传图片API结束>>:"+DateUtils.formatDateTime(new Date()));
                        String r1 = resMap.get("stat");
                        String res = resMap.get("message");
                        String [] returnstr = new String[3];
                        int cacheError = 1;
                        try {
                            String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                            if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                                Document document= SamplePaseXml.formatStr2Doc(res);
                                Element rootElt = document.getRootElement();
                                Element picelt = rootElt.element("SiteHostedPictureDetails");
                                String baseUrl = picelt.elementText("BaseURL");
                                String picformat = picelt.elementText("PictureFormat");
                                String fullUrl = picelt.elementText("FullURL");
                                tlu.setEbayurl(fullUrl);
                                tlu.setCheckFlag("1");
                                this.iTradingListingPicUrl.saveListingPicUrl(tlu);
                                AjaxSupport.sendSuccessText("","1");
                                return;
                            }else{
                                /*if (TempStoreDataSupport.pullData(c.getId() + "" + picName) != null) {
                                    TempStoreDataSupport.pushDataByTime(c.getId() + "" + picName, Integer.parseInt(TempStoreDataSupport.pullData(c.getId() + "" + picName).toString()) + 1, 5);
                                } else {
                                    TempStoreDataSupport.pushDataByTime(c.getId() + "" + picName, cacheError, 5);
                                }*/
                                /*if(!"21916543".equals(SamplePaseXml.getSpecifyElementTextAllInOne(res, "Errors", "ErrorCode"))){
                                    logger.error("请求xml::::::::::::::"+xml);
                                    logger.error("返回xml：：：：：：：："+res);
                                }*/
                                logger.error("请求xml::::::::::::::"+xml);
                                logger.error("返回xml：：：：：：：：" + res);
                                AjaxSupport.sendSuccessText("","0");
                                return;
                            }
                        }catch(Exception e){
                            try {
                                logger.error("请求xml::::::::::::::"+xml);
                                logger.error("返回xml：：：：：：：："+res);
                               /* if(!"21916543".equals(SamplePaseXml.getSpecifyElementTextAllInOne(res, "Errors", "ErrorCode"))){
                                    logger.error("请求xml::::::::::::::"+xml);
                                    logger.error("返回xml：：：：：：：："+res);
                                }*/
                            } catch (Exception e1) {
                                //e1.printStackTrace();
                            }
                            /*if (TempStoreDataSupport.pullData(c.getId() + "" + picName) != null) {
                                TempStoreDataSupport.pushDataByTime(c.getId() + "" + picName, Integer.parseInt(TempStoreDataSupport.pullData(c.getId() + "" + picName).toString()) + 1, 5);
                            } else {
                                TempStoreDataSupport.pushDataByTime(c.getId() + "" + picName, cacheError, 5);
                            }*/
                            logger.error(res+":::",e);
                            AjaxSupport.sendSuccessText("","0");

                        }
                    }
                }
            }
        }

    }

    public String  upPic(String url){
        String su = FtpUploadFile.uploadpicByurl(url);
        if("ok".equals(su)){
            //logger.error("向美国服务器传输===============================================");
            return "http://"+ ImageServiceImpl.usFtpServerIP+"/"+StringUtils.substringAfterLast(url,"/");
        }else{
            return url;
        }
    }


    @RequestMapping("/ajax/toPicList.do")
    public ModelAndView toPicList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws  Exception{
        return forword("item/waituploadpic",modelMap);
    }

    @RequestMapping("/ajax/deltePic.do")
    @ResponseBody
    public void deltePic(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
        String ids = request.getParameter("ids");
        try {
            this.iTradingListingPicUrl.deltelById(Long.parseLong(ids));
            AjaxSupport.sendSuccessText("","1");
        }catch(Exception e){
            AjaxSupport.sendSuccessText("","0");
        }
    }

    @RequestMapping("/ajax/selectEbayAccountMore.do")
    @ResponseBody
    public void selectEbayAccountMore(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
        List<UsercontrollerEbayAccount> liebay = this.iUsercontrollerEbayAccount.selectAllEbayAccount();
        AjaxSupport.sendSuccessText("",liebay);
    }


    @RequestMapping("/ajax/copyTradingItemtoEbayAccount.do")
    @ResponseBody
    public void copyTradingItemtoEbayAccount(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
        String startAccount = request.getParameter("startAccount");
        String endAccount  = request.getParameter("endAccount");
        List<TradingItemWithBLOBs> liti = this.iTradingItem.selectByEbayAccount(startAccount);
        if(liti!=null&&liti.size()>0){
            for(TradingItemWithBLOBs ti:liti){
                try {
                    this.iTradingItem.copyItem(new String[]{ti.getId()+""},endAccount);
                } catch (Exception e) {
                    logger.error("复制数据报错",e);
                    continue;
                }
            }
        }
        AjaxSupport.sendSuccessText("","复制成功！");
    }

    @RequestMapping("/store/selectStoreCategory.do")
    public ModelAndView selectStoreCategory(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws  Exception{
        String ebayAccountId = request.getParameter("ebayAccountId");
        modelMap.put("ebayAccountId",ebayAccountId);
        return forword("item/selectStoreCategory",modelMap);
    }


    @RequestMapping("/ajax/loadStoreCategoryList.do")
    @ResponseBody
    public void loadStoreCategoryList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,CommonParmVO commonParmVO) {
        String ebayAccountId = request.getParameter("ebayAccountId");
        Asserts.assertTrue(!ObjectUtils.isLogicalNull(ebayAccountId),"ebayid不能为空");
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        //Page page=Page.newAOnePage();
        String cacheName="MenuForEbayStoresCategory_"+ebayAccountId;
        List<TradingStoreCategory> li = TempStoreDataSupport.pullData(cacheName);
        if (ObjectUtils.isLogicalNull(li)){
            List<TradingStoreCategory> lii = this.iTradingStoreCategory.selectByEbayAccountIdList(Long.parseLong(ebayAccountId));
            li=DictCollectionsUtil.menuForEbayStoresCategory(lii);
            TempStoreDataSupport.pushDataByTime(cacheName,li,180);
        }
        jsonBean.setList(li);
        jsonBean.setTotal(200);
        AjaxSupport.sendSuccessText("", jsonBean);
    }

    @RequestMapping("/ajax/isStore.do")
    @ResponseBody
    public void isStore(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
        String ebayAccountId = request.getParameter("ebayAccountId");
        if(!ObjectUtils.isLogicalNull(ebayAccountId)) {
            List<TradingStoreCategory> li = this.iTradingStoreCategory.selectByEbayAccountIdList(Long.parseLong(ebayAccountId));
            if (ObjectUtils.isLogicalNull(li)) {
                modelMap.put("isStore", "0");
            } else {
                modelMap.put("isStore", "1");
            }
        }else{
            modelMap.put("isStore", "0");
        }
        AjaxSupport.sendSuccessText("", modelMap);
    }
}
