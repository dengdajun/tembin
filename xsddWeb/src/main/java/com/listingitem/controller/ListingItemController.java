package com.listingitem.controller;

import com.base.aboutpaypal.service.PayPalService;
import com.base.database.publicd.model.PublicUserConfig;
import com.base.database.task.model.ListingDataTask;
import com.base.database.trading.model.*;
import com.base.domains.CommonParmVO;
import com.base.domains.SessionVO;
import com.base.domains.querypojos.*;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.domains.userinfo.UsercontrollerEbayAccountExtend;
import com.base.domains.userinfo.UsercontrollerUserExtend;
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
import com.base.utils.common.DateUtils;
import com.base.utils.common.EncryptionUtil;
import com.base.utils.common.MyStringUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.imageManage.service.ImageService;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.EbayErrorCodeStatic;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.PojoXmlUtil;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.*;
import com.base.xmlpojo.trading.addproduct.attrclass.StartPrice;
import com.common.base.utils.ajax.AjaxResponse;
import com.common.base.utils.ajax.AjaxSupport;
import com.common.base.web.BaseAction;
import com.keymove.service.IKeyMoveProgress;
import com.listingitem.controller.ResponseVO.TradingListingDataResponseVO;
import com.sitemessage.service.SiteMessageService;
import com.sitemessage.service.SiteMessageStatic;
import com.task.service.IListingDataTask;
import com.trading.service.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrtor on 2014/8/18.
 */
@Controller
public class ListingItemController extends BaseAction {
    static Logger logger = Logger.getLogger(ListingItemController.class);

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ITradingDataDictionary iTradingDataDictionary;
    @Value("${EBAY.API.URL}")
    private String apiUrl;
    @Autowired
    private ITradingItem iTradingItem;
    @Autowired
    private ITradingListingData iTradingListingData;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Autowired
    public ITradingListingAmend iTradingListingAmend;
    @Autowired
    public ITradingTablePrice iTradingTablePrice;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private IListingDataTask iListingDataTask;

    @Autowired
    private ITradingListingPicUrl iTradingListingPicUrl;

    @Autowired
    private ITradingListingSuccess iTradingListingSuccess;
    @Autowired
    private PayPalService payPalService;
    @Autowired
    private ITradingTemplateInitTable iTradingTemplateInitTable;
    @Autowired
    private ITradingDescriptionDetails iTradingDescriptionDetails;
    @Autowired
    private ITradingAssessViewSet iTradingAssessViewSet;
    @Autowired
    private ITradingAttrMores iTradingAttrMores;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ITradingItemAddress iTradingItemAddress;
    @Autowired
    private IKeyMoveProgress iKeyMoveProgress;


    @RequestMapping("/getListingItemList.do")
    public ModelAndView getListingItemList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        /*String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
            "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
            "<RequesterCredentials>\n" +
            "<eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**vVcRVA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4GhCpGGoA+dj6x9nY+seQ**cx0CAA**AAMAAA**Z2MB0OtmO4JsPFBZPcjclippjnZG4bwpcpXYRXDdc6wEppv5m/WiCvsjyTKWoVXCMxQl2se3U6Vn93oBL6zg8EcR3GCXCC3ZbTpEQ3lBX8avBrME9VHo0RcfcE7oLVtnBAuSffy3Dk5ICUNyU7g57/rHw8d5DnO3JeitpQcTLKAInt+sEZslri3wa4Mx0xgyFW5OF3w8mNK8ib8+57PTHcApnp8xRTAlIVuwW3F/fGbSFVReS07/MulzlFXBoW/ZPLq+L2aLFpn5s+IB5/gB0HoDo5uGzRnALmXxUz8BuwJMrUE29830W7xVSEaYSYsOcJyue6PjJKyZt0rXf8TNHusXCHX240dWllrjMVxS7pEHgKb/FKfd/79PH3rXTFmuexesXS6H1lRmHBBE1iknFwtzzS+UeN22Rd6W+hjSjuOHB33o2gMS5cOdVXHuHyOQ6VJU3bJL/eNDgyB+wz3HhZmz6sF+lmLIRKP82H1QXdlwdGdpVhAhyqnE4FH4qTgPBMxv6c4jRL5BRuyUZDLeJI1WXmaZ4pNMss+MiME7Qu+7bP7S2TZhmValbfW/FvqSrxR9LlHji7iQSsz2m56x5TLjOtkFWjRxmB6C1wzBVtzdILzbvmA/1+9RlMevalW6bg22irusiv7iuD/AnC9pZ0Sju2XK/7WpjVW4/lZyBmRbqHQJPbU/5MU3xrM8pTV8rZmPfQrRh2araaWGIBE5IW3gsTrETpRUQybXd/a107ee61GwXEUqVat1EfznFpIs</eBayAuthToken>\n" +
            "</RequesterCredentials>\n" +
            "<Pagination ComplexType=\"PaginationType\">\n" +
            "\t<EntriesPerPage>10</EntriesPerPage>\n" +
            "\t<PageNumber>1</PageNumber>\n" +
            "</Pagination>\n" +
            "<StartTimeFrom>2014-07-18T16:15:12.000Z</StartTimeFrom>\n" +
            "<StartTimeTo>2014-08-18T16:15:12.000Z</StartTimeTo>\n" +
            "<UserID>testuser_sandpoint</UserID>\n" +
            "<GranularityLevel>Coarse</GranularityLevel>\n" +
            "<DetailLevel>ItemReturnDescription</DetailLevel>\n" +
            "</GetSellerListRequest>​";

        List<Item> li = SamplePaseXml.getItemElememt(this.cosPostXml(colStr,APINameStatic.ListingItemList));
        modelMap.put("li",li);*/
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
        return forword("listingitem/listingitemList",modelMap);
    }

    @RequestMapping("/getTablePriceList.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView getTablePriceList(HttpServletRequest request, HttpServletResponse response, @ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {

        return forword("listingitem/getTablePriceList",modelMap);
    }

    /**
     * 导入模板数据保存
     * @param response
     * @throws Exception
     */
    @RequestMapping("/importTemplateSave.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public void importTemplateSave(@RequestParam("templatename")MultipartFile[] multipartFiles,HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        if(ObjectUtils.isLogicalNull(multipartFiles)){
            AjaxResponse.sendText(response,"nofile");
            return;
        }
        MultipartFile multipartFile = multipartFiles[0];
        InputStream input = multipartFile.getInputStream();
        HSSFWorkbook workbook = new HSSFWorkbook(input);
        HSSFSheet sheet = workbook.getSheetAt(0);

        SessionVO c= SessionCacheSupport.getSessionVO();
        List<TradingTablePrice> littp = new ArrayList<TradingTablePrice>();
        for(int i=1;i<=sheet.getLastRowNum();i++){
            HSSFRow row = sheet.getRow(i);
            double price=row.getCell(2).getNumericCellValue();
            Asserts.assertTrue(NumberUtils.isNumber(price + ""), "在" + (i + 1) + "行第3列，用户输入的价格不是数字，请查看修改！");
            TradingTablePrice ttp = new TradingTablePrice();
            ttp.setSku(row.getCell(0).getStringCellValue());
            ttp.setEbayAccount(row.getCell(1).getStringCellValue());
            ttp.setPrice(price);
            ttp.setCheckFlag("0");
            ttp.setCreateUser(c.getId());
            ttp.setCreateTime(new Date());
            littp.add(ttp);
        }

        this.iTradingTablePrice.saveTablePriceList(littp);

        AjaxResponse.sendText(response, "success");
    }
    /**
     * 导入模板页面跳转
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/importTemplate.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView importTemplate(HttpServletRequest request, HttpServletResponse response, @ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        return forword("listingitem/importTemplate",modelMap);
    }
    /*
   *下载模板downloadTemplate
   */
    @RequestMapping("/downloadTemplate.do")
    public void  downloadTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("UTF-8");
        String outputFile1= request.getSession().getServletContext().getRealPath("/");
        String outputFile=outputFile1+"template/template.xls";
        response.setHeader("Content-Disposition","attachment;filename=template.xls");// 组装附件名称和格式
        InputStream fis = new BufferedInputStream(new FileInputStream(outputFile));
        byte[] buffer = new byte[fis.available()];
        fis.read(buffer);
        fis.close();
        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        toClient.write(buffer);
        toClient.flush();
        toClient.close();
    }

    @RequestMapping("/addTablePrice.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView addTablePrice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        return forword("listingitem/addTablePrice",modelMap);
    }

    @RequestMapping("/editTablePrice.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editTablePrice(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        TradingTablePrice ttp  = this.iTradingTablePrice.selectById(Long.parseLong(request.getParameter("id")));
        modelMap.put("ttp",ttp);
        String type = request.getParameter("type");
        if(type!=null&&!"".equals(type)){
            modelMap.put("type",type);
        }
        return forword("listingitem/addTablePrice",modelMap);
    }

    /**
     * 失败日志继续处理
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/continueWork.do")
    @ResponseBody
    public void continueWork(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<TradingDataDictionary> lisite = DataDictionarySupport.getTradingDataDictionaryByType("site");
        String id = request.getParameter("id");
        String endid = request.getParameter("endid");
        TradingListingData tldata = this.iTradingListingData.selectById(Long.parseLong(id));
        TradingListingAmendWithBLOBs tlend = this.iTradingListingAmend.selectById(Long.parseLong(endid));

        AddApiTask addApiTask = new AddApiTask();
        UsercontrollerDevAccountExtend dt = userInfoService.getDevByOrder(new HashMap());
        dt.setApiCallName(APINameStatic.ReviseItem);
        String siteid="";
        for(TradingDataDictionary tdd : lisite){
            if(tldata.getSite().equals(tdd.getValue())){
                siteid=tdd.getName1();
                break;
            }
        }
        dt.setApiSiteid(siteid);
        Map<String, String> resMap = addApiTask.exec(dt, tlend.getCosxml(), apiUrl);
        String res = resMap.get("message");
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");

        tlend.setCreateTime(new Date());
        tlend.setCreateUser(c.getId());
        System.out.println(":::::::::::"+res);
        if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
            tlend.setId(null);
            tlend.setIsFlag("1");
            this.iTradingListingAmend.saveListingAmend(tlend);
            AjaxSupport.sendSuccessText("message", "操作成功！");
        }else{
            String resStr = "";
            if(res!=null){
                resStr = SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage");
            }else{
                resStr = "请求失败！";
            }
            this.saveSystemLog(resStr,"继续修改报错",SiteMessageStatic.LISTING_DATA_UPDATE);
            tlend.setId(null);
            tlend.setIsFlag("0");
            this.iTradingListingAmend.saveListingAmend(tlend);
            AjaxSupport.sendFailText("fail","操作失败！");
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
     * 保存数据
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/delTablePrice.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void delTablePrice(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        TradingTablePrice ttp  = this.iTradingTablePrice.selectById(Long.parseLong(request.getParameter("id")));
        ttp.setCheckFlag("1");
        this.iTradingTablePrice.saveTablePrice(ttp);
        AjaxSupport.sendSuccessText("","操作成功!");
    }

    /**
     * 执行表格调价
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/ajax/runPrice.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void runPrice(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        //List<UsercontrollerEbayAccount> ebay = this.iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(c.getId());
        String ebayAccount = request.getParameter("ebayAccount");
        String token = "";
        String currencyID = "";
        List<TradingDataDictionary> lisite = DataDictionarySupport.getTradingDataDictionaryByType("site");
        String siteid = "0";

        String price = request.getParameter("price");
        String sku = request.getParameter("sku");
        String ebayaccount = request.getParameter("ebayaccount");

        String [] prices = price.split(",");
        String [] skus = sku.split(",");
        String [] ebayaccounts = ebayaccount.split(",");
        String xml = "";
        ReviseItemRequest rir = new ReviseItemRequest();
        rir.setXmlns("urn:ebay:apis:eBLBaseComponents");
        rir.setErrorLanguage("en_US");
        rir.setWarningLevel("High");
        AddApiTask addApiTask = new AddApiTask();
        UsercontrollerDevAccountExtend dt = userInfoService.getDevByOrder(new HashMap());
        dt.setApiCallName(APINameStatic.ReviseItem);
        for(int i=0;i<skus.length;i++){
            List<TradingListingData> litld = this.iTradingListingData.selectByList(skus[i],ebayaccounts[i]);
            for(TradingListingData tld:litld){
                //取得当前数据的token
                Map m = new HashMap();
                m.put("needToken",null);
                List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(m);
                if(ebayList!=null&&ebayList.size()>0){
                    for(UsercontrollerEbayAccountExtend ue:ebayList){
                        if(tld.getEbayAccount().equals(ue.getEbayName())){
                            token = ue.getEbayToken();
                            break;
                        }
                    }
                }else{
                    Asserts.assertTrue(false,"未绑定ebay账号！");
                }
                //取得站点，及货币ＩＤ
                for(TradingDataDictionary tdd : lisite){
                    if(tld.getSite().equals(tdd.getValue())){
                        siteid=tdd.getName1();
                        currencyID = tdd.getValue1();
                        break;
                    }
                }

                xml="";
                dt.setApiSiteid(siteid);
                Item ite = new Item();
                ite.setItemID(tld.getItemId());
                StartPrice sp = new StartPrice();
                sp.setValue(Double.parseDouble(prices[i]));
                ite.setStartPrice(sp);

                RequesterCredentials rc = new RequesterCredentials();
                rc.seteBayAuthToken(token);
                rir.setRequesterCredentials(rc);
                rir.setItem(ite);
                xml = PojoXmlUtil.pojoToXml(rir);
                xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
                System.out.println(xml);

                Map<String, String> resMap = addApiTask.exec(dt, xml, apiUrl);
                String res = resMap.get("message");
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
                tla.setItem(Long.parseLong(tld.getItemId()));
                tla.setParentId(tld.getId());
                tla.setAmendType("EndItem");
                tla.setContent("商品调价：从"+tld.getPrice()+"调整为"+prices[i] );
                tla.setCreateUser(c.getId());
                tla.setCreateTime(new Date());

                if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
                    tla.setIsFlag("1");
                    this.iTradingListingAmend.saveListingAmend(tla);
                    AjaxSupport.sendSuccessText("message", "操作成功！");
                }else{
                    String resStr = "";
                    if(res!=null){
                        resStr = SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage");
                    }else{
                        resStr = "请求失败！";
                    }
                    this.saveSystemLog(resStr,"表格调价报错",SiteMessageStatic.LISTING_DATA_UPDATE);
                    tla.setIsFlag("0");
                    this.iTradingListingAmend.saveListingAmend(tla);
                    AjaxSupport.sendFailText("fail","操作失败！");
                }
            }
        }
        AjaxSupport.sendSuccessText("","操作成功!");
    }

    /**
     * 获取用户自定义的文件夹
     * @param request
     * @param modelMap
     * @param commonParmVO
     * @throws Exception
     */
    @RequestMapping("/ajax/selfFolder.do")
    @ResponseBody
    public void selfFolder(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        DataDictionarySupport.removePublicUserConfig(c.getId());
        String folderType = request.getParameter("folderType");
        List<PublicUserConfig> lipuc = DataDictionarySupport.getPublicUserConfigByType(folderType,c.getId());
        List<PublicUserConfig> li = new ArrayList<PublicUserConfig>();
        if(lipuc!=null&&lipuc.size()>0) {
            for (PublicUserConfig puc : lipuc) {
                if ("true".equals(puc.getConfigValue())) {
                    li.add(puc);
                }
            }
            AjaxSupport.sendSuccessText("", li);
        }else{
            AjaxSupport.sendSuccessText("", null);
        }
    }


    /**
     * 用户点击同步时，查询
     * @param request
     * @param modelMap
     * @param commonParmVO
     * @throws Exception
     */
    @RequestMapping("/ajax/myEbayAccount.do")
    @ResponseBody
    public void myEbayAccount(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        //List<UsercontrollerEbayAccount> liuserebay = this.iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(c.getId());
        List<Map> lim = new ArrayList<Map>();
        for(UsercontrollerEbayAccount uea:ebayList){
            Map mpar = new HashMap();
            mpar.put("ebayAccount",uea.getEbayName());

            Map m = new HashMap();
            m.put("ebayAccount",uea.getEbayName());
            m.put("ebayName",uea.getEbayName());
            ListingDataTask ldt = this.iTradingListingSuccess.selectByMaxCreateDate(mpar);
            if(ldt==null){
                m.put("maxDate","暂时未同步！");
            }else{
                m.put("maxDate",ldt.getCreateDate());
            }

            lim.add(m);
        }
        AjaxSupport.sendSuccessText("", lim);
    }
    /**
     * 刊登商品
     * @param request
     * @throws Exception
     */
    @RequestMapping("/ajax/saveTablePrice.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveTablePrice(HttpServletRequest request,Item item,TradingItem tradingItem,Date timerListing) throws Exception {
        String sku = request.getParameter("sku");
        String price = request.getParameter("price");
        String [] ebayAccounts = request.getParameterValues("ebayAccounts");
        String id = request.getParameter("id");
        SessionVO c= SessionCacheSupport.getSessionVO();

        if(ebayAccounts!=null&&ebayAccounts.length>0){
            for(String ebayaccount:ebayAccounts){
                TradingTablePrice ttp  = new TradingTablePrice();
                if(id!=null&&!"".equals(id)){
                    ttp.setId(Long.parseLong(id));
                }
                ttp.setEbayAccount(ebayaccount);
                ttp.setPrice(Double.parseDouble(price));
                ttp.setSku(sku);
                ttp.setCreateTime(new Date());
                ttp.setCreateUser(c.getId());
                ttp.setCheckFlag("0");
                this.iTradingTablePrice.saveTablePrice(ttp);
            }
        }
        AjaxSupport.sendSuccessText("","操作成功!");
    }


    @RequestMapping("/ajax/ajaxTablePriceList.do")
    @ResponseBody
    public void getAjaxTablePriceList(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        Map map = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        map.put("userid",c.getId());
        map.put("checkflag","0");
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<TablePriceQuery> paypalli = this.iTradingTablePrice.selectByList(map, page);
        jsonBean.setList(paypalli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    @RequestMapping("/getListItemDataAmend.do")
    public ModelAndView getListItemDataAmend(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
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
        String amendType = request.getParameter("amendType");
        if(amendType!=null&&!"".equals(amendType)){
            modelMap.put("amendType",amendType);
        }
        String amendFlag = request.getParameter("amendFlag");
        if(amendFlag!=null&&!"".equals(amendFlag)){
            modelMap.put("amendFlag",amendFlag);
        }
        String parentId = request.getParameter("parentId");
        modelMap.put("parentId",parentId);
        return forword("listingitem/listingdataAmend",modelMap);
    }

    /**
     * 移到到文件夹
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/shiftListingToFolder.do")
    @ResponseBody
    public void shiftListingToFolder(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String idStr = request.getParameter("idStr");
        String [] ids =idStr.split(",");
        String folderid = request.getParameter("folderid");

        List<TradingListingData> litld = new ArrayList<TradingListingData>();
        for(String id: ids){
            TradingListingData tld = this.iTradingListingData.selectById(Long.parseLong(id));
            tld.setFolderId(folderid);
            litld.add(tld);
        }
        if(litld.size()>0) {
            try {
                this.iTradingListingData.saveTradingListingDataList(litld);
                AjaxSupport.sendSuccessText("","操作成功!");
            } catch (Exception e) {
                logger.error("shiftListingToFolder.do报错",e);
                AjaxSupport.sendSuccessText("","操作失败!");
            }
        }else{
            AjaxSupport.sendSuccessText("", "操作失败，你未选择商品，或你选择的商品不存在!");
        }

    }
    /**
     * 添加备注
     * @param request
     * @param response
     * @param modelMap
     * @throws Exception
     */
    @RequestMapping("/ajax/addRemark.do")
    @ResponseBody
    public void addRemark(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        String [] ids = id.split(",");
        String remark = request.getParameter("remark");
        if(remark==null||"".equals(remark)){
            remark="";
        }else {
            remark = new String(remark.getBytes("ISO-8859-1"), "UTF-8");
        }
        List<TradingListingData> litld = new ArrayList<TradingListingData>();
        for(int i=0;i<ids.length;i++) {
            TradingListingData tld = this.iTradingListingData.selectById(Long.parseLong(ids[i]));
            if(tld!=null){
                tld.setRemark(remark);
                this.iTradingListingData.updateTradingListingData(tld);
                litld.add(tld);
            }
        }
        AjaxSupport.sendSuccessText("",litld);
    }

    @RequestMapping("/ajax/getListItemDataAmend.do")
    @ResponseBody
    public void getListItemDataAmend(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        Map map = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        map.put("userid",c.getId());
        String flag = request.getParameter("flag");
        if(flag!=null&&!"".equals(flag)){
            map.put("flag",flag);
        }
        String county = request.getParameter("county");
        if(county!=null&&!"".equals(county)){
            map.put("county",county);
        }
        String listingtype = request.getParameter("listingtype");
        if(listingtype!=null&&!"".equals(listingtype)){
            map.put("listingtype",listingtype);
        }
        String ebayaccount = request.getParameter("ebayaccount");
        if(ebayaccount!=null&&!"".equals(ebayaccount)){
            map.put("ebayaccount",ebayaccount);
        }
        String selectType = request.getParameter("selectType");
        if(selectType!=null&&!"".equals(selectType)){
            map.put("selectType",selectType);
        }
        String selectValue = request.getParameter("selectValue");
        if(selectValue!=null&&!"".equals(selectValue)){
            map.put("selectValue",selectValue);
        }
        String amendType = request.getParameter("amendType");
        if(amendType!=null&&!"".equals(amendType)){
            map.put("amendType",amendType);
        }
        String amendFlag = request.getParameter("amendFlag");
        if(amendFlag!=null&&!"".equals(amendFlag)){
            map.put("amendFlag",amendFlag);
        }
        String parentId = request.getParameter("parentId");
        if(parentId!=null&&!"".equals(parentId)){
            map.put("parentId",parentId);
        }
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<ListingDataAmendQuery> paypalli = this.iTradingListingData.selectAmendData(map, page);
        jsonBean.setList(paypalli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);
    }

    @RequestMapping("/ajax/endItem.do")
    @ResponseBody
    public void endItem(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        //List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        String ebayAccount = request.getParameter("ebayAccount");
        String token = "";
        List<TradingDataDictionary> lisite = DataDictionarySupport.getTradingDataDictionaryByType("site");
        String siteid = "0";
        String itemidstr = request.getParameter("itemidStr");
        Asserts.assertTrue(itemidstr!=null,"您还未选择商品,请选择需结束的商品！");
        String reason = request.getParameter("reason");
        String [] itemids = itemidstr.split(",");
        Map map=new HashMap();
        for(String id:itemids){
            TradingListingData tld = this.iTradingListingData.selectByItemid(id);
            if(map.get(tld.getEbayAccount())==null){
                Map mss = new HashMap();
                mss.put(id,id);
                map.put(tld.getEbayAccount(),mss);
            }else{
                Map mss = (Map) map.get(tld.getEbayAccount());
                mss.put(id,id);
                map.put(tld.getEbayAccount(),mss);
            }
        }
        UsercontrollerDevAccountExtend dt = userInfoService.getDevByOrder(new HashMap());
        dt.setApiCallName(APINameStatic.EndItems);
        dt.setApiSiteid(siteid);
        AddApiTask addApiTask = new AddApiTask();
        List<TradingListingData> litld = new ArrayList<TradingListingData>();
        Set<String> keys = map.keySet();
        for(String key:keys){
            String ebayAccountName = key;
            Map ms = (Map) map.get(key);
            String [] items = new String[ms.size()];
            Set<String> itemss = ms.keySet();
            int i=0;
            for(String itemid:itemss){
                items[i]=itemid;
                i++;
            }
            Map m = new HashMap();
            m.put("needToken",null);
            List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(m);
            if(ebayList!=null&&ebayList.size()>0){
                for(UsercontrollerEbayAccountExtend ue:ebayList){
                    if(ebayAccountName.equals(ue.getEbayName())){
                        token = ue.getEbayToken();
                        break;
                    }
                }
            }else{
                Asserts.assertTrue(false,"未绑定ebay账号！");
            }
            String xml = this.costXml(items,reason,token);
            /*TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
            tla.setItem(Long.parseLong(tld.getItemId()));
            tla.setParentId(tld.getId());
            tla.setAmendType("EndItem");
            tla.setContent("商品下架，下架原因：" + reason);
            tla.setCreateUser(c.getId());
            tla.setCreateTime(new Date());
            tla.setCosxml(xml);*/
            Map<String, String> resMap = addApiTask.exec(dt, xml, apiUrl);
            String res = resMap.get("message");
            if("apiFail".equals(res)){
                AjaxSupport.sendFailText("fail","调用结束API时出错！");
            }
            if("21926".equals(SamplePaseXml.getSpecifyElementTextAllInOne(res, "Errors", "ErrorCode"))){//商品下架
                TempStoreDataSupport.removeData("ApiFail_"+APINameStatic.EndItems);
            }

            String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                for(int is=0;is<items.length;is++){
                    String itemid = items[is];
                    TradingListingData tld = this.iTradingListingData.selectByItemid(itemid);
                    TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
                    tla.setItem(Long.parseLong(tld.getItemId()));
                    tla.setParentId(tld.getId());
                    tla.setAmendType("EndItem");
                    tla.setContent("商品下架，下架原因：" + reason);
                    tla.setCreateUser(c.getId());
                    tla.setCreateTime(new Date());
                    tla.setCosxml(xml);
                    tla.setIsFlag("1");
                    tld.setIsFlag("1");
                    this.iTradingListingData.updateTradingListingData(tld);
                    this.saveEndListingItem(res,tld.getItemId());
                    litld.add(tld);
                    TradingItemWithBLOBs tradingItemWithBLOBs = this.iTradingItem.selectByItemId(tld.getItemId());
                    if(tradingItemWithBLOBs!=null){
                        tradingItemWithBLOBs.setIsFlag("");
                        tradingItemWithBLOBs.setItemId("");
                        this.iTradingItem.saveTradingItem(tradingItemWithBLOBs);
                    }
                    this.iTradingListingData.insertTradingListingAmend(tla);
                }
            }else{
                if("PartialFailure".equals(ack)){
                    Document document= SamplePaseXml.formatStr2Doc(res);
                    Element rootElt = document.getRootElement();
                    Iterator<Element> ie = rootElt.elementIterator("EndItemResponseContainer");
                    int is = 0;
                    while (ie.hasNext()){
                        Element nvl = ie.next();
                        String itemid = items[is];
                        TradingListingData tld = this.iTradingListingData.selectByItemid(itemid);
                        if(nvl.element("Errors")!=null){
                            TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
                            tla.setItem(Long.parseLong(tld.getItemId()));
                            tla.setParentId(tld.getId());
                            tla.setAmendType("EndItem");
                            tla.setContent("商品下架，下架原因：" + reason);
                            tla.setCreateUser(c.getId());
                            tla.setCreateTime(new Date());
                            tla.setCosxml(xml);
                            tla.setIsFlag("0");
                            this.iTradingListingData.insertTradingListingAmend(tla);
                        }else{
                            TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
                            tla.setItem(Long.parseLong(tld.getItemId()));
                            tla.setParentId(tld.getId());
                            tla.setAmendType("EndItem");
                            tla.setContent("商品下架，下架原因：" + reason);
                            tla.setCreateUser(c.getId());
                            tla.setCreateTime(new Date());
                            tla.setCosxml(xml);
                            tla.setIsFlag("1");
                            tld.setIsFlag("1");
                            this.iTradingListingData.updateTradingListingData(tld);
                            this.saveEndListingItem(res,tld.getItemId());
                            litld.add(tld);
                            TradingItemWithBLOBs tradingItemWithBLOBs = this.iTradingItem.selectByItemId(tld.getItemId());
                            if(tradingItemWithBLOBs!=null){
                                tradingItemWithBLOBs.setIsFlag("");
                                tradingItemWithBLOBs.setItemId("");
                                this.iTradingItem.saveTradingItem(tradingItemWithBLOBs);
                            }
                            this.iTradingListingData.insertTradingListingAmend(tla);
                        }
                        is++;
                    }
                }else{
                    String resStr = "";
                    if(res!=null){
                        resStr = SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage");
                    }else{
                        resStr = "请求失败！";
                    }
                    this.saveSystemLog(resStr,"提前结束商品报错",SiteMessageStatic.LISTING_DATA_UPDATE);
                }

                /*TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
                tla.setItem(Long.parseLong(tld.getItemId()));
                tla.setParentId(tld.getId());
                tla.setAmendType("EndItem");
                tla.setContent("商品下架，下架原因：" + reason);
                tla.setCreateUser(c.getId());
                tla.setCreateTime(new Date());
                tla.setCosxml(xml);
                tla.setIsFlag("0");*/
            }

        }



        /*for(String itemid:itemids){
            TradingListingAmend tlaold = this.iTradingListingAmend.selectByItemID(itemid,"EndItem");
            if(tlaold!=null){
                continue;
            }
            TradingListingData tld = this.iTradingListingData.selectByItemid(itemid);
            Map m = new HashMap();
            m.put("needToken",null);
            List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(m);
            if(ebayList!=null&&ebayList.size()>0){
                for(UsercontrollerEbayAccountExtend ue:ebayList){
                    if(tld.getEbayAccount().equals(ue.getEbayName())){
                        token = ue.getEbayToken();
                        break;
                    }
                }
            }else{
                Asserts.assertTrue(false,"未绑定ebay账号！");
            }

            for(TradingDataDictionary tdd : lisite){
                if(tld.getSite().equals(tdd.getValue())){
                    siteid=tdd.getName1();
                }
            }
            dt.setApiSiteid(siteid);
            String xml = this.costXml(itemid,reason,token);
            TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
            tla.setItem(Long.parseLong(tld.getItemId()));
            tla.setParentId(tld.getId());
            tla.setAmendType("EndItem");
            tla.setContent("商品下架，下架原因：" + reason);
            tla.setCreateUser(c.getId());
            tla.setCreateTime(new Date());
            tla.setCosxml(xml);
            Map<String, String> resMap = addApiTask.exec(dt, xml, apiUrl);
            String res = resMap.get("message");
            if("21926".equals(SamplePaseXml.getSpecifyElementTextAllInOne(res, "Errors", "ErrorCode"))||"400".equals(SamplePaseXml.getSpecifyElementTextAllInOne(res, "Errors", "ErrorCode"))){//商品下架
                TempStoreDataSupport.removeData("ApiFail_"+APINameStatic.EndItem);
                logger.error(itemid+"商品下架失败!");
            }

            String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                tla.setIsFlag("1");
                tld.setIsFlag("1");
                this.iTradingListingData.updateTradingListingData(tld);
                this.saveEndListingItem(res,tld.getItemId());
                litld.add(tld);
                TradingItemWithBLOBs tradingItemWithBLOBs = this.iTradingItem.selectByItemId(tld.getItemId());
                if(tradingItemWithBLOBs!=null){
                    tradingItemWithBLOBs.setIsFlag("");
                    tradingItemWithBLOBs.setItemId("");
                    this.iTradingItem.saveTradingItem(tradingItemWithBLOBs);
                }

            }else{
                String resStr = "";
                if(res!=null){
                    resStr = SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage");
                }else{
                    resStr = "请求失败！";
                }
                this.saveSystemLog(resStr,"提前结束商品报错",SiteMessageStatic.LISTING_DATA_UPDATE);
                tla.setIsFlag("0");
            }
            this.iTradingListingData.insertTradingListingAmend(tla);
        }*/
        AjaxSupport.sendSuccessText("",litld);
    }

    public void saveEndListingItem(String xml,String itemId) throws Exception{
        List<TradingListingSuccess> litls = this.iTradingListingSuccess.selectByItemid(itemId);
            String EndTime = SamplePaseXml.getVFromXmlString(xml, "EndTime");
            if(litls!=null&&litls.size()>0){
                TradingListingSuccess tls = litls.get(0);
                tls.setEndDate(DateUtils.returnDate(EndTime));
                this.iTradingListingSuccess.save(tls);
            }

    }

    public String costXml(String [] itemids,String reason,String token){
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
                "<EndItemsRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "  <RequesterCredentials>\n" +
                "    <eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "  </RequesterCredentials>\n";
        for(int i=0;i<itemids.length;i++){
            if(itemids[i]==null||"".equals(itemids[i])){
                continue;
            }
            xml+="<EndItemRequestContainer>\n" +
                    "    <MessageID>"+(i+1)+"</MessageID>\n" +
                    "    <EndingReason>"+reason+"</EndingReason>\n" +
                    "    <ItemID>"+itemids[i]+"</ItemID>\n" +
                    "  </EndItemRequestContainer>\n";
        }
        xml+="</EndItemsRequest>\n";
        return xml;
    }

    @RequestMapping("/listingdataManager.do")
    public ModelAndView listingdataManager(HttpServletRequest request, HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        //List<PublicUserConfig> ebayList = DataDictionarySupport.getPublicUserConfigByType(DataDictionarySupport.PUBLIC_DATA_DICT_PAYPAL, c.getId());
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        modelMap.put("ebayList",ebayList);
        return forword("listingitem/listingdataManager",modelMap);
    }

    /**
     * 快速编辑
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping("/quickEdit.do")
    public ModelAndView quickEdit(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        String id = request.getParameter("id");
        String listingType = request.getParameter("listingType");
        String [] ids = id.split(",");
        List<TradingListingData> litld = new ArrayList<TradingListingData>();
        for(int i=0;i<ids.length;i++){
            TradingListingData tld = this.iTradingListingData.selectById(Long.parseLong(ids[i]));
            //临时存放站点货币
            Map m = new HashMap();
            m.put("type","site");
            m.put("value",tld.getSite());

            List<TradingDataDictionary> litdd = DataDictionarySupport.getTradingDataDictionaryByMap(m);
            if(litdd!=null&&litdd.size()>0){
                TradingDataDictionary tdd = litdd.get(0);
                tld.setListingduration(tdd.getValue1());
            }else{
                tld.setListingduration("");
            }
            litld.add(tld);
        }
        modelMap.put("litld",litld);
        modelMap.put("listingType",listingType);
        return forword("listingitem/quickEdit",modelMap);
    }
    @RequestMapping("/ajax/savequickData.do")
    @ResponseBody
    public void savequickData(HttpServletRequest request,HttpServletResponse response,@ModelAttribute( "initSomeParmMap" )ModelMap modelMap) throws Exception {
        SessionVO c = SessionCacheSupport.getSessionVO();
        String [] ids =request.getParameterValues("ids");
        String listingType = request.getParameter("listingType");
        String message="";
        for(int i=0;i<ids.length;i++){
            String price = request.getParameter("price_"+i);
            String quantity = request.getParameter("quantity_"+i);
            String buyitnowprice = request.getParameter("buyitnowprice_"+i);
            String reserveprice = request.getParameter("reserveprice_"+i);
            String title = request.getParameter("title_"+i);
          //  title= StringEscapeUtils.escapeHtml(title);
            String subtitle = request.getParameter("subtitle_"+i);
            String sku = request.getParameter("sku_"+i);
            String id = request.getParameter("id_"+i);
            TradingListingData tld = this.iTradingListingData.selectById(Long.parseLong(id));
            Item item = new Item();
            item.setTitle(title);
            item.setSubTitle(subtitle);
            tld.setTitle(title);
            tld.setSubtitle(subtitle);
            TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
            if("2".equals(listingType)){//多属性
                tla.setAmendType("Title");
                tla.setContent("快速编辑：多属性修改title");
            }else if("Chinese".equals(listingType)){//拍卖
                tla.setAmendType("Title");
                tla.setContent("快速编辑：拍卖修改");
                item.setSKU(sku);
                StartPrice sp = new StartPrice();
                sp.setValue(Double.parseDouble(price));
                item.setStartPrice(sp);
                item.setBuyItNowPrice(Double.parseDouble(buyitnowprice));
                item.setReservePrice(Double.parseDouble(reserveprice));
                item.setQuantity(Integer.parseInt(quantity));
                tld.setPrice(Double.parseDouble(price));
                tld.setBuyitnowprice(Double.parseDouble(buyitnowprice));
                tld.setReserveprice(Double.parseDouble(reserveprice));
                tld.setSku(sku);
                tld.setQuantity(Long.parseLong(quantity));
            }else if("FixedPriceItem".equals(listingType)){//固价
                tla.setAmendType("Title");
                tla.setContent("快速编辑：固价修改");
                item.setSKU(sku);
                StartPrice sp = new StartPrice();
                sp.setValue(Double.parseDouble(price));
                item.setStartPrice(sp);
                item.setQuantity(Integer.parseInt(quantity));
                tld.setPrice(Double.parseDouble(price));
                tld.setSku(sku);
                tld.setQuantity(Long.parseLong(quantity));
            }
            item.setItemID(tld.getItemId());
            tla.setParentId(tld.getId());
            tla.setItem(Long.parseLong(tld.getItemId()));
            tla.setCreateUser(c.getId());
            tla.setCreateTime(new Date());
            String xml="";
            ReviseItemRequest rir = new ReviseItemRequest();
            rir.setXmlns("urn:ebay:apis:eBLBaseComponents");
            rir.setErrorLanguage("en_US");
            rir.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            String ebayAccount = tld.getEbayAccount();

            //List<UsercontrollerEbayAccount> liuea = this.iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(c.getId());
            String token = "";
            Map m = new HashMap();
            m.put("needToken",null);
            List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(m);
            if(ebayList!=null&&ebayList.size()>0){
                for(UsercontrollerEbayAccountExtend ue:ebayList){
                    if(tld.getEbayAccount().equals(ue.getEbayName())){
                        token = ue.getEbayToken();
                        break;
                    }
                }
            }else{
                Asserts.assertTrue(false,"未绑定ebay账号！");
            }
            rc.seteBayAuthToken(token);
            rir.setRequesterCredentials(rc);
            rir.setItem(item);
            xml = PojoXmlUtil.pojoToXml(rir);
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
            tla.setCosxml(xml);
            String returnString = this.cosPostXml(xml,APINameStatic.ReviseItem);
            System.out.println(returnString);
            String ack = SamplePaseXml.getVFromXmlString(returnString,"Ack");
            if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
                tla.setIsFlag("1");
                this.iTradingListingAmend.saveListingAmend(tla);
                this.iTradingListingData.updateTradingListingData(tld);
                message+="SKU："+tld.getSku()+"；物品号："+tld.getItemId()+"修改成功！\n";
            }else{
                String resStr = "";
                if(returnString!=null){
                    resStr = SamplePaseXml.getSpecifyElementTextAllInOne(returnString,"Errors","LongMessage");
                }else{
                    resStr = "请求失败！";
                }
                this.saveSystemLog(resStr,"快速修改报错",SiteMessageStatic.LISTING_DATA_UPDATE);
                tla.setIsFlag("0");
                this.iTradingListingAmend.saveListingAmend(tla);
                Document document= SamplePaseXml.formatStr2Doc(returnString);
                Element rootElt = document.getRootElement();
                Element tl = rootElt.element("Errors");
                String longMessage = tl.elementText("LongMessage");
                if(longMessage==null){
                    longMessage = tl.elementText("ShortMessage");
                }
                message+="SKU："+tld.getSku()+"；物品号："+tld.getItemId()+"修改失败，原因如下："+longMessage+";";
            }
        }
        AjaxSupport.sendSuccessText("message", message);
    }


        @RequestMapping("/editListingItem.do")
    @AvoidDuplicateSubmission(needSaveToken = true)
    public ModelAndView editListingItem(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        String itemid = request.getParameter("itemid");
        String [] itemidStr = itemid.split(",");
        TradingListingData tldata = this.iTradingListingData.selectByItemid(itemidStr[0]);
        TradingItemWithBLOBs tradingItemWithBLOBs = this.iTradingItem.selectByItemId(itemidStr[0]);
        modelMap.put("imageUrlPrefix",imageService.getImageUrlPrefix());
        if(tradingItemWithBLOBs!=null&&tradingItemWithBLOBs.getTemplateId()!=null){
            TradingTemplateInitTable ttit = this.iTradingTemplateInitTable.selectById(tradingItemWithBLOBs.getTemplateId());
            modelMap.put("ttit",ttit);
            List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetidUuid(tradingItemWithBLOBs.getId(),"TemplatePicUrl",tradingItemWithBLOBs.getUuid());
            modelMap.put("templi",litam);
        }
        UsercontrollerEbayAccount uea = this.iUsercontrollerEbayAccount.selectByEbayAccount(tldata.getEbayAccount());
        if(tradingItemWithBLOBs!=null){
            modelMap.put("ebayid",tradingItemWithBLOBs.getEbayAccount());
        }
            modelMap.put("sku",tldata.getSku());
            modelMap.put("ebayAccount",tldata.getItemId());
            /*modelMap.put("lipic",item.getPictureDetails().getPictureURL());*/
            Map m = new HashMap();
            m.put("type","site");
            m.put("value",tldata.getSite());
            List<TradingDataDictionary> litdd = DataDictionarySupport.getTradingDataDictionaryByMap(m);
            if(litdd==null||litdd.size()==0){
                Asserts.assertTrue(false,"站点信息不正确，请查看在线商品ID："+itemidStr[0]);
            }
            TradingDataDictionary tddsite = litdd.get(0);
            if(litdd!=null&&litdd.size()>0){
                modelMap.put("tdd",litdd.get(0));
            }
            modelMap.put("tldata",tldata);
            modelMap.put("itemidstr",itemid);
            SessionVO c= SessionCacheSupport.getSessionVO();

            Map map =new HashMap();
            map.put("userId",c.getId());
            List<UsercontrollerUserExtend> liuue = systemUserManagerService.queryAllUsersByOrgID("yes");
            List<String> liue = new ArrayList<String>();
            for (UsercontrollerUserExtend uue : liuue) {
                liue.add(uue.getUserId() + "");
            }
            liue.add(c.getId() + "");
            map.put("liue", liue);
            PageJsonBean jsonBean=new PageJsonBean();
            jsonBean.setPageCount(1000);
            jsonBean.setPageNum(1);
            List<UsercontrollerPaypalAccount> paypalAccounts = payPalService.queryPayPalListTo(map, jsonBean.toPage());
            modelMap.put("paypalList",paypalAccounts);

            List<TradingDataDictionary> lidata = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SITE);
            modelMap.put("siteList",lidata);

            List<TradingDataDictionary> acceptList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.RETURNS_ACCEPTED_OPTION,tddsite.getId());
            modelMap.put("acceptList",acceptList);

            List<TradingDataDictionary> withinList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.RETURNS_WITHIN_OPTION,tddsite.getId());
            modelMap.put("withinList",withinList);

            List<TradingDataDictionary> refundList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.REFUND_OPTION,tddsite.getId());
            modelMap.put("refundList",refundList);

            List<TradingDataDictionary> costPaidList = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.SHIPPING_COST_PAID,tddsite.getId());
            modelMap.put("costPaidList",costPaidList);

            List<TradingDataDictionary> lidatas = this.iTradingDataDictionary.selectDictionaryByType("country");
            modelMap.put("countryList",lidatas);
            Long siteid = 0L;
            for(TradingDataDictionary tdd : lidata){
                if(tdd.getValue().equals(tldata.getSite())){
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
                if(tdd.getName1().equals("Economy services")){
                    li1.add(tdd);
                }else if(tdd.getName1().equals("Expedited services")){
                    li2.add(tdd);
                }else if(tdd.getName1().equals("One-day services")){
                    li3.add(tdd);
                }else if(tdd.getName1().equals("Other services")){
                    li4.add(tdd);
                }else if(tdd.getName1().equals("Standard services")){
                    li5.add(tdd);
                }
            }
            modelMap.put("li1",li1);
            modelMap.put("li2",li2);
            modelMap.put("li3",li3);
            modelMap.put("li4",li4);
            modelMap.put("li5",li5);*/

            /*List<TradingDataDictionary> liinter = DataDictionarySupport.getTradingDataDictionaryByType(DataDictionarySupport.DATA_DICT_SHIPPINGINTER_TYPE);
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
        return forword("listingitem/editListingItem",modelMap);
    }

    @RequestMapping("/ajax/getListingItem.do")
    public void getListingItem(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
        String itemid = request.getParameter("itemid");
        String [] itemidStr = itemid.split(",");
        TradingListingData tldata = this.iTradingListingData.selectByItemid(itemidStr[0]);
        TradingItemWithBLOBs tradingItemWithBLOBs = this.iTradingItem.selectByItemId(itemidStr[0]);
        if(tradingItemWithBLOBs!=null){
            Item item = null;
            try{
                item = this.iTradingItem.toItemByTradingItem(tradingItemWithBLOBs);
            }catch(Exception e){
                logger.error("通过范本信息转换成Item出错：",e);
            }
            if(item==null){
                logger.error("通过范本信息转换成Item为null");
                logger.error("在线编辑，调用API，查询比较慢！商品ID："+itemidStr[0]);
                UsercontrollerEbayAccount uea = this.iUsercontrollerEbayAccount.selectByEbayAccount(tldata.getEbayAccount());
                String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<GetItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                        "<RequesterCredentials>\n" +
                        "<eBayAuthToken>" + uea.getEbayToken() + "</eBayAuthToken>\n" +
                        "</RequesterCredentials>\n" +
                        "<ItemID>" + itemidStr[0] + "</ItemID>\n" +
                        "<DetailLevel>ReturnAll</DetailLevel>\n" +
                        "</GetItemRequest>";
                String res = this.cosPostXml(xml, APINameStatic.GetItem);
                String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
                if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                    if (res != null) {
                        item = SamplePaseXml.getItem(res);
                    }
                } else {
                    logger.error("查询在线商品报错，返回报文：：：：："+res);
                    Asserts.assertTrue(false, "能过在线查询是数据报错！");
                }
            }
            if(tldata!=null){
                if(item.getStartPrice()!=null){
                    item.getStartPrice().setValue(tldata.getPrice());
                }else{
                    StartPrice sp = new StartPrice();
                    sp.setValue(tldata.getPrice());
                    item.setStartPrice(sp);
                }
                if(tldata.getQuantity()!=null){
                    item.setQuantity(tldata.getQuantity().intValue());
                }else{
                    item.setQuantity(0);
                }
            }
            AjaxSupport.sendSuccessText("", item);
        }else {
            logger.error("在线编辑，调用API，查询比较慢！商品ID："+itemidStr[0]);
            UsercontrollerEbayAccount uea = this.iUsercontrollerEbayAccount.selectByEbayAccount(tldata.getEbayAccount());
            String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<GetItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                    "<RequesterCredentials>\n" +
                    "<eBayAuthToken>" + uea.getEbayToken() + "</eBayAuthToken>\n" +
                    "</RequesterCredentials>\n" +
                    "<ItemID>" + itemidStr[0] + "</ItemID>\n" +
                    "<DetailLevel>ReturnAll</DetailLevel>\n" +
                    "</GetItemRequest>";
            String res = this.cosPostXml(xml, APINameStatic.GetItem);
            String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
            if ("Success".equalsIgnoreCase(ack) || "Warning".equalsIgnoreCase(ack)) {
                Item item = null;
                if (res != null) {
                    item = SamplePaseXml.getItem(res);
                }
                AjaxSupport.sendSuccessText("", item);
            } else {
                Asserts.assertTrue(false, "查询数据报错！");
            }
        }
    }

    @RequestMapping("/ajax/updateListingData.do")
    @ResponseBody
    public void updateListingData(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        SessionVO c= SessionCacheSupport.getSessionVO();
        String type = request.getParameter("type");
        String price = request.getParameter("price");
        String itemId = request.getParameter("itemId");
        String ids = request.getParameter("ids");
        TradingListingData tld = this.iTradingListingData.selectById(Long.parseLong(ids));
        TradingListingDataResponseVO oldtld = new TradingListingDataResponseVO(); //this.iTradingListingData.selectById(Long.parseLong(ids));
        oldtld.setPrice(tld.getPrice());
        oldtld.setQuantity(tld.getQuantity());
        if(itemId==null){
            AjaxSupport.sendFailText("fail",oldtld);
            return;
        }
        Item item = new Item();
        item.setItemID(itemId);
        TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
        String token = "";
        Map m = new HashMap();
        m.put("needToken",null);
        List<UsercontrollerEbayAccountExtend>  liue = this.systemUserManagerService.queryCurrAllEbay(m);
        if(liue!=null&&liue.size()>0){
            for(UsercontrollerEbayAccountExtend ue:liue){
                if(tld.getEbayAccount().equals(ue.getEbayName())){
                    token = ue.getEbayToken();
                    break;
                }
            }
        }else{
            Asserts.assertTrue(false,"未绑定Ebay账号！");
        }
        String xml="";
        if(type.equals("price")){
            StartPrice sp = new StartPrice();
            sp.setValue(Double.parseDouble(price));
            item.setStartPrice(sp);
            tla.setAmendType("StartPrice");
            tla.setContent("将价格从" + tld.getPrice() + "修改为" + item.getStartPrice().getValue());
            tld.setPrice(item.getStartPrice().getValue());
            ReviseItemRequest rir = new ReviseItemRequest();
            rir.setXmlns("urn:ebay:apis:eBLBaseComponents");
            rir.setErrorLanguage("en_US");
            rir.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            String ebayAccount = tld.getEbayAccount();
            rc.seteBayAuthToken(token);
            rir.setRequesterCredentials(rc);
            rir.setItem(item);
            xml = PojoXmlUtil.pojoToXml(rir);
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
        }else if(type.equals("quantity")){
            tla.setAmendType("Quantity");
            item.setQuantity(Integer.parseInt(price));
            tla.setContent("将数量从" + tld.getQuantity() + "修改为" + item.getQuantity());
            tld.setQuantity(item.getQuantity().longValue());
            xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<ReviseInventoryStatusRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                    "  <RequesterCredentials>\n" +
                    "    <eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                    "  </RequesterCredentials>\n" +
                    "  <ErrorLanguage>en_US</ErrorLanguage>\n" +
                    "  <WarningLevel>High</WarningLevel>\n" +
                    "  <InventoryStatus>\n" +
                    "    <ItemID>"+itemId+"</ItemID>\n" +
                    "    <Quantity>"+item.getQuantity()+"</Quantity>\n" +
                    "  </InventoryStatus>"+
                    "</ReviseInventoryStatusRequest>";
        }
        tla.setParentId(tld.getId());
        tla.setItem(Long.parseLong(tld.getItemId()));
        tla.setCreateUser(c.getId());
        tla.setCreateTime(new Date());
        System.out.println(xml);
        tla.setCosxml(xml);
        String returnString = "";
        if(type.equals("price")){
            returnString = this.cosPostXml(xml,APINameStatic.ReviseItem);
        }else if(type.equals("quantity")){
            returnString = this.cosPostXml(xml,APINameStatic.ReviseInventoryStatus);
        }
        System.out.println(returnString);
        String ack = SamplePaseXml.getVFromXmlString(returnString,"Ack");
        if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
            tla.setIsFlag("1");
            this.iTradingListingAmend.saveListingAmend(tla);
            this.iTradingListingData.updateTradingListingData(tld);
            AjaxSupport.sendSuccessText("message", tld);
        }else{
            tla.setIsFlag("0");
            this.iTradingListingAmend.saveListingAmend(tla);
            Document document= SamplePaseXml.formatStr2Doc(returnString);
            if(document==null){
                logger.error("修改价格或数量，返回报文的null;");
                AjaxSupport.sendFailText("fail",oldtld);
            }else {
                Element rootElt = document.getRootElement();
                Element tl = rootElt.element("Errors");
                String longMessage = tl.elementText("LongMessage");
                if (longMessage == null) {
                    longMessage = tl.elementText("ShortMessage");
                }

                this.saveSystemLog(longMessage, "修改价格数量报错", SiteMessageStatic.LISTING_DATA_UPDATE);
                oldtld.setErrMessage(longMessage);
                AjaxSupport.sendFailText("fail", oldtld);
            }
        }
    }
    @RequestMapping("/ajax/ListingItemList.do")
    @ResponseBody
    public void ListingItemList(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        Map map = new HashMap();
        SessionVO c= SessionCacheSupport.getSessionVO();
        map.put("userid",c.getId());
        String flag = request.getParameter("flag");
        if(flag!=null&&!"".equals(flag)){
            map.put("flag",flag);
        }
        String county = request.getParameter("county");
        if(county!=null&&!"".equals(county)){
            map.put("county",county);
        }
        String listingtype = request.getParameter("listingtype");
        if(listingtype!=null&&!"".equals(listingtype)){
            map.put("listingtype",listingtype);
        }
        String ebayaccount = request.getParameter("ebayaccount");
        if(ebayaccount!=null&&!"".equals(ebayaccount)){
            map.put("ebayaccount",ebayaccount);
        }
        String selectType = request.getParameter("selectType");
        if(selectType!=null&&!"".equals(selectType)){
            map.put("selectType",selectType);
        }
        String selectValue = request.getParameter("queryValue");
        if(selectValue!=null&&!"".equals(selectValue)){
            map.put("selectValue",selectValue);
        }
        String folderid = request.getParameter("folderid");
        if(folderid!=null&&!"".equals(folderid)){
            map.put("folderid",folderid);
        }
        String descStr = request.getParameter("descStr");
        if(descStr!=null&&!"".equals(descStr)){
            map.put("descStr",descStr);
        }else{
            map.put("descStr","tion.id");
        }
        String ascStr = request.getParameter("desStr");
        if(ascStr!=null&&!"".equals(ascStr)){
            map.put("desStr",ascStr);
        }else{
            map.put("desStr","desc");
        }
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=jsonBean.toPage();
        List<ListingDataQuery> paypalli = this.iTradingListingData.selectData(map,page);
        for(ListingDataQuery ldq:paypalli){
            ldq.setDocTitle(HtmlUtils.htmlUnescape(ldq.getDocTitle()));
        }
        jsonBean.setList(paypalli);
        jsonBean.setTotal((int)page.getTotalCount());
        AjaxSupport.sendSuccessText("",jsonBean);

        /**注释从1-23之前的svn去找*/
    }

    @RequestMapping("/ajax/queryItemAddress.do")
    @ResponseBody
    public void queryItemAddress(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        Map m = new HashMap();
        String content = request.getParameter("content");
        SessionVO c= SessionCacheSupport.getSessionVO();
        m.put("content",content);
        List<UsercontrollerUserExtend> liuue = systemUserManagerService.queryAllUsersByOrgID("yes");
        if(systemUserManagerService.isMainUserAcount()){
            List<String> liue = new ArrayList<String>();
            for(UsercontrollerUserExtend uue:liuue){
                liue.add(uue.getUserId()+"");
            }
            liue.add(c.getId()+"");
            m.put("liue",liue);
        }else{
            for(UsercontrollerUserExtend ue:liuue){
                if(ue.getUserParentId()==null){
                    m.put("userid1",ue.getUserId());
                }
            }
            m.put("userid",c.getId());
        }
        /**分页组装*/
        PageJsonBean jsonBean=commonParmVO.getJsonBean();
        Page page=new Page();
        page.setPageSize(10);
        page.setCurrentPage(1);
        List<ItemAddressQuery> li = this.iTradingItemAddress.selectByItemAddressQuery(m,page);
        List<ItemInformationQuery> list = new ArrayList<>();
        Map ms = new HashMap();
        for(ItemAddressQuery l:li){
            ms.put(l.getAddress(),l.getAddress());
        }
        Set<Map.Entry<String,String>> entryset = ms.entrySet();
        Iterator iter = entryset.iterator();
        int i=1;
        while(iter.hasNext()){
            Map.Entry<String,String> entry = (Map.Entry<String,String>)iter.next();
            ItemInformationQuery in = new ItemInformationQuery();
            in.setId((long) i++);
            in.setSku(entry.getValue());
            list.add(in);
        }
        AjaxSupport.sendSuccessText("", list);
    }

    public String cosPostXml(String colStr,String month) throws Exception {
        UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(1L);
        d.setApiSiteid("0");
        d.setApiCallName(month);
        AddApiTask addApiTask = new AddApiTask();
        Map<String, String> resMap= addApiTask.exec(d, colStr, apiUrl);
        String res=resMap.get("message");
        String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
        if("Success".equals(ack)||"Warning".equalsIgnoreCase(ack)){
            return res;
        }else{
            return res;
        }

    }

    /**
     * 在线修改数据
     * @param request
     * @throws Exception
     */
    @RequestMapping("/saveListingItem.do")
    @AvoidDuplicateSubmission(needRemoveToken = true)
    @ResponseBody
    public void saveListingItem(HttpServletRequest request,Item item,TradingItem tradingItem) throws Exception {
        String [] selectType = request.getParameterValues("selectType");
        String isUpdateFlag = request.getParameter("isUpdateFlag");
        String listingType = request.getParameter("listingType");
        String itemidStr = request.getParameter("ItemID");
        String [] itemid = itemidStr.split(",");
        for(int i=0;i<itemid.length;i++){
            item.setItemID(itemid[i]);
            if("1".equals(isUpdateFlag)){//需要更新范本
                TradingItemWithBLOBs tradingItem1=this.iTradingItem.selectByItemId(item.getItemID());
                Asserts.assertTrue(tradingItem1!=null,"范本不存在，不允许更新范本操作！");
                if(tradingItem1!=null){//更新数据库中的范本
                    this.iTradingItem.updateTradingItem(item,tradingItem1,tradingItem1.getId()+"");
                }
            }
            TradingListingData tld = this.iTradingListingData.selectByItemid(item.getItemID());

            Item ite = new Item();
            List litla = new ArrayList();
            ReviseItemRequest rir = new ReviseItemRequest();
            rir.setXmlns("urn:ebay:apis:eBLBaseComponents");
            rir.setErrorLanguage("en_US");
            rir.setWarningLevel("High");
            RequesterCredentials rc = new RequesterCredentials();
            String ebayAccount = tld.getEbayAccount();
            SessionVO c= SessionCacheSupport.getSessionVO();
            //List<UsercontrollerEbayAccount> liuea = this.iUsercontrollerEbayAccount.selectUsercontrollerEbayAccountByUserId(c.getId());
            String token = "";
            Map m = new HashMap();
            m.put("needToken",null);
            List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(m);
            if(ebayList!=null&&ebayList.size()>0){
                for(UsercontrollerEbayAccountExtend ue:ebayList){
                    if(tld.getEbayAccount().equals(ue.getEbayName())){
                        token = ue.getEbayToken();
                        break;
                    }
                }
            }else{
                Asserts.assertTrue(false,"未绑定ebay账号！");
            }
            rc.seteBayAuthToken(token);
            rir.setRequesterCredentials(rc);
            ite.setItemID(item.getItemID());
            for(String str : selectType){
                TradingListingAmendWithBLOBs tla = new TradingListingAmendWithBLOBs();
                tla.setItem(Long.parseLong(tld.getItemId()));
                tla.setParentId(tld.getId());
                if(str.equals("StartPrice")){//改价格
                    tla.setAmendType("StartPrice");
                    if("FixedPriceItem".equals(listingType)) {
                        ite.setStartPrice(item.getStartPrice());
                        tla.setContent("将价格从" + tld.getPrice() + "修改为" + item.getStartPrice().getValue());
                        tld.setPrice(item.getStartPrice().getValue());
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
                        tla.setContent("将价格从" + tld.getPrice() + "修改为" + item.getStartPrice().getValue());
                        tld.setPrice(item.getStartPrice().getValue());
                        item.setBuyItNowPrice(item.getStartPrice().getValue());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setBuyItNowPrice(item.getBuyItNowPrice());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                    }

                }else if(str.equals("Quantity")){//改数量
                    if(!item.getListingType().equals("2")) {
                        tla.setAmendType("Quantity");
                        tla.setContent("将数量从" + tld.getQuantity() + "修改为" + item.getQuantity());
                        ite.setQuantity(item.getQuantity());
                        tld.setQuantity(item.getQuantity().longValue());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setQuantity(item.getQuantity());
                        rir.setItem(ites);
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + PojoXmlUtil.pojoToXml(rir));
                    }else{
                        ite.setVariations(item.getVariations());
                        Item ites = new Item();
                        ites.setItemID(item.getItemID());
                        ites.setVariations(item.getVariations());
                        rir.setItem(ites);
                        tla.setAmendType("Quantity");
                        tla.setContent("多属性数量调整");
                        tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + PojoXmlUtil.pojoToXml(rir));
                    }
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
                    TradingItemWithBLOBs tradingItem1=this.iTradingItem.selectByItemId(item.getItemID());
                    if(tradingItem1!=null){//更新数据库中的范本
                        this.iTradingItem.updateTradingItem(item,tradingItem1,tradingItem1.getId()+"");
                    }
                    tla.setAmendType("Title");
                    tla.setContent("标题修改为："+item.getTitle());
                    ite.setTitle(item.getTitle());
                    tld.setTitle(item.getTitle());
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
                    tld.setSku(item.getSKU());
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
                    ite.setCountry(item.getCountry());
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setLocation(item.getLocation());
                    ites.setCountry(item.getCountry());
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
                    this.replateDescription(request,item.getItemID(),ite);
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setDescription(item.getDescription());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("ShippingDetails")){//改运输详情
                    tla.setAmendType("ShippingDetails");
                    tla.setContent("修改运输详情");
                    ShippingDetails sdf = item.getShippingDetails();
                    List<ShippingServiceOptions> liss = sdf.getShippingServiceOptions();
                    for(ShippingServiceOptions sso:liss){
                        if(sso.getShippingService()!=null){
                            sso.setShippingService(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(sso.getShippingService())).getValue());
                        }
                        if(sso.getShippingSurcharge()!=null&&sso.getShippingSurcharge().getValue()==0){
                            sso.setShippingSurcharge(null);
                        }
                    }
                    List<InternationalShippingServiceOption> liinter= sdf.getInternationalShippingServiceOption();
                    if(liinter!=null&&liinter.size()>0){
                        for(InternationalShippingServiceOption is:liinter){
                            is.setShippingService(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(is.getShippingService())).getValue());
                        }
                    }
                    String nottoLocation = request.getParameter("notLocationValue");
                    if(!ObjectUtils.isLogicalNull(nottoLocation)){
                        String noLocation[] =nottoLocation.split(",");
                        List listr = new ArrayList();
                        for(String nostr : noLocation){
                            listr.add(nostr);
                        }
                        sdf.setExcludeShipToLocation(listr);
                    }else{
                        sdf.setExcludeShipToLocation(new ArrayList());
                    }
                    if(sdf.getInternationalShippingServiceOption()!=null&&sdf.getInternationalShippingServiceOption().size()>0){
                        List<String> listr = sdf.getInternationalShippingServiceOption().get(0).getShipToLocation();
                        if(listr!=null&&listr.size()>0){
                            List<String> lis = new ArrayList();
                            for(String stsr:listr){
                                lis.add(stsr);
                            }
                            ite.setShipToLocations(lis);
                        }
                    }

                    ite.setShippingDetails(sdf);
                    Item ites = new Item();
                    ites.setItemID(item.getItemID());
                    ites.setShippingDetails(item.getShippingDetails());
                    ites.setDispatchTimeMax(item.getDispatchTimeMax());
                    rir.setItem(ites);
                    tla.setCosxml("<?xml version=\"1.0\" encoding=\"utf-8\"?>"+PojoXmlUtil.pojoToXml(rir));
                }else if(str.equals("selectTemplate")){
                    Item ites = new Item();
                    ite.setItemID(item.getItemID());
                    this.replateTemplate(request,item.getItemID(),ite);
                }
                litla.add(tla);
            }

            String xml = "";
            rir.setItem(ite);
            xml = PojoXmlUtil.pojoToXml(rir);
            xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+xml;
            System.out.println(xml);
            String returnString = this.cosPostXml(xml,APINameStatic.ReviseItem);
            String ack = SamplePaseXml.getVFromXmlString(returnString,"Ack");
            if(ack==null){
                AjaxSupport.sendFailText("message","更新在线，返回文为空！");
                logger.error("更新在线,返回文为空!请稍后再试！");
                return;
            }
            if("Success".equalsIgnoreCase(ack)||"Warning".equalsIgnoreCase(ack)){
                this.saveAmend(litla,"1");
                this.iTradingListingData.updateTradingListingData(tld);
            }else{
                this.saveAmend(litla,"0");
                Document document= SamplePaseXml.formatStr2Doc(returnString);
                if(document==null){
                    AjaxSupport.sendFailText("message","更新在线，返回文为空！");
                    logger.error("更新在线,返回文为空:::::::"+returnString);
                    return;
                }
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
        AjaxSupport.sendSuccessText("message", "操作成功！");

    }


    public void replateDescription(HttpServletRequest request,String itemId,Item item) throws Exception {
        TradingItemWithBLOBs tradingItem = this.iTradingItem.selectByItemId(itemId);
        if(tradingItem==null){
            Asserts.assertTrue(false,"通过在线商品未能找到范本，不能修改描述信息！");
        }
        tradingItem.setDescription(item.getDescription());
        String templateId = tradingItem.getTemplateId()+"";
        if(templateId==null||"".equals(templateId)){
            Asserts.assertTrue(false,"未选择模板！");
        }
        if(templateId!=null){
            tradingItem.setTemplateId(Long.parseLong(templateId));
        }
        String template = "";
        List<TradingAttrMores> litam = this.iTradingAttrMores.selectByParnetidUuid(tradingItem.getId(),"TemplatePicUrl",tradingItem.getUuid());

        String [] tempicUrls = new String[litam.size()];//界面中添加的模板图片
        for(int i=0;i<litam.size();i++){
            TradingAttrMores tam = litam.get(i);
            tempicUrls[i]=tam.getValue();
        }
        TradingTemplateInitTable tttt = this.iTradingTemplateInitTable.selectById(tradingItem.getTemplateId());
        if(tempicUrls!=null&&tempicUrls.length>0){//如果界面中模板图片不为空，那么替换模板中ＵＲＬ地址
            this.iTradingAttrMores.deleteByParentId("TemplatePicUrl",tradingItem.getId());
            for(String url:tempicUrls){
                TradingAttrMores tam = null;
                try {
                    tam = this.iTradingAttrMores.toDAOPojo("TemplatePicUrl",url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tam.setParentId(tradingItem.getId());
                tam.setParentUuid(tradingItem.getUuid());
                this.iTradingAttrMores.saveAttrMores(tam);
            }
        }
        if(tradingItem.getTemplateId()!=null){//如果选择了模板
            template = tttt.getTemplateHtml();
            if(StringUtils.containsIgnoreCase(template, "{templateTitle}")){
                Map map = new HashMap();
                map.put("templateTitle",tradingItem.getTitle());
                template = MyStringUtil.replaceArgs(template, map);
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
            }
            org.jsoup.nodes.Document docs = org.jsoup.Jsoup.parse(template);
            org.jsoup.nodes.Element elss = docs.body();
            elss.prepend("<div id='EBdescription_body' style='text-align: center;'></div>");
            template = docs.html();
            template = template.replace("{ProductDetail}",item.getDescription());
            Map<String,String> map = new HashMap<>();
            if(tttt!=null&&tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());
                map.put("PaymentMethodTitle",tdd.getPayTitle()==null?"PayTitle":tdd.getPayTitle());
                map.put("PaymentMethod",tdd.getPayInfo());
                map.put("ShippingDetailTitle",tdd.getShippingTitle()==null?"ShippingTitle":tdd.getShippingTitle());
                map.put("ShippingDetail",tdd.getShippingInfo());
                map.put("SalesPolicyTitle",tdd.getGuaranteeTitle()==null?"GuaranteeTitle":tdd.getGuaranteeTitle());
                map.put("SalesPolicy",tdd.getGuaranteeInfo());
                map.put("AboutUsTitle",tdd.getFeedbackTitle()==null?"FeedbackTitle":tdd.getFeedbackTitle());
                map.put("AboutUs",tdd.getFeedbackInfo());
                map.put("ContactUsTitle",tdd.getContactTitle()==null?"ContactTitle":tdd.getContactTitle());
                map.put("ContactUs",tdd.getContactInfo());
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

        this.iTradingItem.saveTradingItem(tradingItem);

    }

    /**
     * 处理模板信息
     * @param request
     * @param itemId
     * @param item
     * @throws DateParseException
     */
    public void replateTemplate(HttpServletRequest request,String itemId,Item item) throws Exception {
        TradingItemWithBLOBs tradingItem = this.iTradingItem.selectByItemId(itemId);
        if(tradingItem==null){
            Asserts.assertTrue(false,"通过在线商品未能找到范本，不能进行模板更换！");
        }
        String templateId = request.getParameter("templateId");
        if(templateId==null||"".equals(templateId)){
            Asserts.assertTrue(false,"未选择模板！");
        }
        if(templateId!=null){
            tradingItem.setTemplateId(Long.parseLong(templateId));
        }
        String template = "";
        String [] tempicUrls = request.getParameterValues("blankimg");//界面中添加的模板图片
        TradingTemplateInitTable tttt = this.iTradingTemplateInitTable.selectById(tradingItem.getTemplateId());
        if(tempicUrls!=null&&tempicUrls.length>0){//如果界面中模板图片不为空，那么替换模板中ＵＲＬ地址
            this.iTradingAttrMores.deleteByParentId("TemplatePicUrl",tradingItem.getId());
            for(String url:tempicUrls){
                TradingAttrMores tam = null;
                try {
                    tam = this.iTradingAttrMores.toDAOPojo("TemplatePicUrl",url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                tam.setParentId(tradingItem.getId());
                tam.setParentUuid(tradingItem.getUuid());
                this.iTradingAttrMores.saveAttrMores(tam);
            }
        }
        if(tradingItem.getTemplateId()!=null){//如果选择了模板
            template = tttt.getTemplateHtml();
            if(StringUtils.containsIgnoreCase(template, "{templateTitle}")){
                Map map = new HashMap();
                map.put("templateTitle",tradingItem.getTitle());
                template = MyStringUtil.replaceArgs(template, map);

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
            }
            org.jsoup.nodes.Document docs = org.jsoup.Jsoup.parse(template);
            org.jsoup.nodes.Element elss = docs.body();
            elss.prepend("<div id='EBdescription_body' style='text-align: center;'></div>");
            template = docs.html();
            template = template.replace("{ProductDetail}",tradingItem.getDescription());
            Map<String,String> map = new HashMap<>();
            if(tttt!=null&&tradingItem.getSellerItemInfoId()!=null){//如果选择了卖家描述
                TradingDescriptionDetailsWithBLOBs tdd = this.iTradingDescriptionDetails.selectById(tradingItem.getSellerItemInfoId());
                map.put("PaymentMethodTitle",tdd.getPayTitle()==null?"PayTitle":tdd.getPayTitle());
                map.put("PaymentMethod",tdd.getPayInfo());
                map.put("ShippingDetailTitle",tdd.getShippingTitle()==null?"ShippingTitle":tdd.getShippingTitle());
                map.put("ShippingDetail",tdd.getShippingInfo());
                map.put("SalesPolicyTitle",tdd.getGuaranteeTitle()==null?"GuaranteeTitle":tdd.getGuaranteeTitle());
                map.put("SalesPolicy",tdd.getGuaranteeInfo());
                map.put("AboutUsTitle",tdd.getFeedbackTitle()==null?"FeedbackTitle":tdd.getFeedbackTitle());
                map.put("AboutUs",tdd.getFeedbackInfo());
                map.put("ContactUsTitle",tdd.getContactTitle()==null?"ContactTitle":tdd.getContactTitle());
                map.put("ContactUs",tdd.getContactInfo());
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
            template+=tradingItem.getDescription()+"</br>";
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

        this.iTradingItem.saveTradingItem(tradingItem);

    }

    public void saveAmend(List<TradingListingAmendWithBLOBs> litlam,String isflag) throws Exception {

        for(TradingListingAmendWithBLOBs tla : litlam){
            ObjectUtils.toInitPojoForInsert(tla);
            tla.setIsFlag(isflag);
            this.iTradingListingData.insertTradingListingAmend(tla);
        }
    }

    /**
     * 同步在线商品
     * @param ebayName
     * @param startTime
     * @param endTime
     * @param pageNumber
     * @param token
     * @return
     */
    public String getCosXml(String ebayName,String startTime,String endTime,int pageNumber,String token){
        String colStr="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<GetSellerListRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<Pagination ComplexType=\"PaginationType\">\n" +
                "\t<EntriesPerPage>100</EntriesPerPage>\n" +
                "\t<PageNumber>"+pageNumber+"</PageNumber>\n" +
                "</Pagination>\n" +
                "<EndTimeFrom>"+startTime+"</EndTimeFrom>\n" +
                "<EndTimeTo>"+endTime+"</EndTimeTo>\n" +
                "<UserID>"+ebayName+"</UserID>\n" +
                "<GranularityLevel>Fine</GranularityLevel><IncludeVariations>true</IncludeVariations><IncludeWatchCount>true</IncludeWatchCount>\n" +
                "<DetailLevel>ReturnAll</DetailLevel>\n" +
                "</GetSellerListRequest>​";
        return colStr;
    }

    /**
     * 同步在线商品
     * @param request
     * @param modelMap
     * @param commonParmVO
     * @throws Exception
     */
    @RequestMapping("/ajax/synListingData.do")
    @ResponseBody
    public void synListingData(HttpServletRequest request,ModelMap modelMap,CommonParmVO commonParmVO) throws Exception {
        List<UsercontrollerEbayAccountExtend> ebayList=systemUserManagerService.queryCurrAllEbay(new HashMap());
        List<String> listr = new ArrayList<String>();
        for(UsercontrollerEbayAccountExtend uee:ebayList){
            listr.add(uee.getId()+"");
        }
        //设置一个默认值
        listr.add("0");
        List<KeyMoveProgressQuery> limp = this.iKeyMoveProgress.selectByUserId(listr);
        boolean aa=true;
        if(limp!=null&&limp.size()>0){
            for(KeyMoveProgressQuery kkmp:limp){
                if(Integer.parseInt(kkmp.getWaitcount())>0){
                    aa=false;
                    break;
                }
            }
        }
        Asserts.assertTrue(aa,"系统正在搬家，无法同步数据，搬家完成后，系统自动分配同步数据空间！");

        String [] ebayAccounts = request.getParameter("ebayAccount").split(",");
        SessionVO c= SessionCacheSupport.getSessionVO();
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + 119);
        String startTo="";
        String startFrom="";
        
            Date endDate = dft.parse(dft.format(date.getTime()));
            startTo = DateUtils.DateToString(DateUtils.nowDateAddDay(100));
            startFrom = DateUtils.DateToString(DateUtils.nowDateMinusDay(18));

        //站点列表
        AddApiTask addApiTask = new AddApiTask();
        UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
        d.setApiCallName(APINameStatic.ListingItemList);
        List<TradingDataDictionary> litdd = DataDictionarySupport.getTradingDataDictionaryByType("site");
        TradingDataDictionary tdd = DataDictionarySupport.getTradingDataDictionaryByID(311L);
        List<UsercontrollerEbayAccount> liusereae = new ArrayList<UsercontrollerEbayAccount>();
        for(String ebayAccount : ebayAccounts){
            liusereae.add(this.iUsercontrollerEbayAccount.selectByEbayAccount(ebayAccount));
        }
        if(liusereae!=null&&liusereae.size()>0) {
            for (UsercontrollerEbayAccount ue : liusereae) {
                if(ue==null){
                    continue;
                }
                UsercontrollerEbayAccount ues = this.iUsercontrollerEbayAccount.selectById(ue.getId());
                //for(TradingDataDictionary tdd:litdd){
                List<ListingDataTask> lidk = iListingDataTask.selectByflag(tdd.getName1(), ue.getEbayAccount());
                if (lidk != null && lidk.size() > 0) {
                    for (ListingDataTask ldk : lidk) {
                        ldk.setTaskFlag("1");
                        ldk.setCreateDate(new Date());
                        iListingDataTask.saveListDataTask(ldk);
                    }
                }
                ListingDataTask ldt = new ListingDataTask();
                ldt.setCreateDate(new Date());
                ldt.setEbayaccount(ue.getEbayAccount());
                ldt.setSite(tdd.getName1());
                ldt.setToken(ue.getEbayToken());
                ldt.setUserid(ue.getUserId());
                ldt.setTaskFlag("2");
                iListingDataTask.saveListDataTask(ldt);

                d.setApiSiteid(tdd.getName1());
                String colStr = this.getCosXml(ues.getEbayAccount(), startFrom, startTo, 1, ues.getEbayToken());
                TaskMessageVO taskMessageVO = new TaskMessageVO();
                taskMessageVO.setMessageContext(ues.getEbayName() + "在" + tdd.getName() + "站点同步在线商品");
                taskMessageVO.setMessageTitle("同步在线商品");
                taskMessageVO.setMessageType(SiteMessageStatic.SYN_MESSAGE_LISTING_DATA_TYPE);
                taskMessageVO.setBeanNameType(SiteMessageStatic.SYN_MESSAGE_LISTING_DATA_BEAN);
                taskMessageVO.setMessageFrom("system");
                SessionVO sessionVO = SessionCacheSupport.getSessionVO();
                taskMessageVO.setMessageTo(sessionVO.getId());
                taskMessageVO.setObjClass(new String[]{ues.getEbayAccount(), c.getId() + "", ues.getEbayToken(), tdd.getName1()});
                //不发送消息
                taskMessageVO.setSendOrNotSend(false);
                addApiTask.execDelayReturn(d, colStr, apiUrl, taskMessageVO);
                //}
            }
        }
        AjaxSupport.sendSuccessText("message", "操作成功！后台正在同步数据,请稍后查看！");
    }

    /**
     * 保存同步在线商品数据
     * @param cosXml
     * @param ebayAccount
     * @param userid
     * @param pages
     * @param token
     *//*
    public void saveSynListingData(String cosXml,String ebayAccount,Long userid,int pages,String token,String siteid){
        SessionVO c= SessionCacheSupport.getSessionVO();
        UserInfoService userInfoService = (UserInfoService) ApplicationContextUtil.getBean(UserInfoService.class);
        TradingListingDataMapper tldm = (TradingListingDataMapper) ApplicationContextUtil.getBean(TradingListingDataMapper.class);
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 119);
        String startTo="";
        String startFrom="";
        try {
            Date endDate = dft.parse(dft.format(date.getTime()));
            startTo = DateUtils.DateToString(new Date());
            startFrom = DateUtils.DateToString(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            UsercontrollerDevAccountExtend d = userInfoService.getDevInfo(userid);
            d.setApiSiteid(siteid);
            d.setApiCallName(APINameStatic.ListingItemList);
            AddApiTask addApiTask = new AddApiTask();
            Map<String, String> resMap= addApiTask.exec(d, cosXml, commPars.apiUrl);
            String res=resMap.get("message");
            System.out.println(res);
            String ack = SamplePaseXml.getVFromXmlString(res,"Ack");
            if(ack.equals("Success")){
                Document document= DocumentHelper.parseText(res);
                Element rootElt = document.getRootElement();
                Element totalElt = rootElt.element("PaginationResult");
                String totalCount = totalElt.elementText("TotalNumberOfEntries");
                String page =  totalElt.elementText("TotalNumberOfPages");
                for(int i=1;i<=Integer.parseInt(page);i++){
                    String colStr = this.getCosXml(ebayAccount,startFrom,startTo,i,token);
                    TaskMessageVO taskMessageVO=new TaskMessageVO();
                    taskMessageVO.setMessageContext("用户点击同步在线商品");
                    taskMessageVO.setMessageTitle("同步在线商品");
                    taskMessageVO.setMessageType(SiteMessageStatic.SYN_MESSAGE_LISTING_DATA_TYPE);
                    taskMessageVO.setBeanNameType(SiteMessageStatic.SYN_MESSAGE_LISTING_DATA_BEAN);
                    taskMessageVO.setMessageFrom("system");
                    SessionVO sessionVO=SessionCacheSupport.getSessionVO();
                    taskMessageVO.setMessageTo(sessionVO.getId());
                    taskMessageVO.setObjClass(new String[]{ebayAccount,c.getId()+"",});
                    addApiTask.execDelayReturn(d, colStr, apiUrl, taskMessageVO);
                }
            }else{
                String resStr = "";
                if(res!=null){
                    resStr = SamplePaseXml.getSpecifyElementTextAllInOne(res,"Errors","LongMessage");
                }else{
                    resStr = "请求失败！";
                }
                this.saveSystemLog(resStr,"同步在线商品报错",SiteMessageStatic.SYN_MESSAGE_LISTING_DATA_TYPE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }*/
}
