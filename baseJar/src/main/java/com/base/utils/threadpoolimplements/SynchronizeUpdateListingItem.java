package com.base.utils.threadpoolimplements;

import com.base.aboutpaypal.service.PayPalService;
import com.base.database.customtrading.mapper.ItemMapper;
import com.base.database.publicd.mapper.PublicItemPictureaddrAndAttrMapper;
import com.base.database.publicd.mapper.PublicUserConfigMapper;
import com.base.database.trading.mapper.*;
import com.base.database.trading.model.*;
import com.base.database.userinfo.mapper.UsercontrollerUserMapper;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.*;
import com.base.utils.exception.Asserts;
import com.base.utils.itemdescription.IItemDescription;
import com.base.utils.threadpool.TaskMessageVO;
import com.base.utils.xmlutils.SamplePaseXml;
import com.base.xmlpojo.trading.addproduct.*;
import com.publicd.service.*;
import com.sitemessage.service.SiteMessageStatic;
import com.trading.service.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.*;

/**
 * Created by Administrtor on 2014/9/13.
 * 更新在线范本，成功后执行的
 */
@Service
public class SynchronizeUpdateListingItem implements ThreadPoolBaseInterFace {
    static Logger logger = Logger.getLogger(SynchronizeUpdateListingItem.class);
    @Autowired
    private TradingItemMapper tradingItemMapper;
    @Autowired
    private ITradingPictureDetails iTradingPictureDetails;
    @Autowired
    private ITradingAttrMores iTradingAttrMores;
    @Autowired
    private ITradingPublicLevelAttr iTradingPublicLevelAttr;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ITradingVariations iTradingVariations;
    @Autowired
    private ITradingVariation iTradingVariation;
    @Autowired
    private ITradingPictures iTradingPictures;
    @Autowired
    private ITradingAddItem iTradingAddItem;
    @Autowired
    private ITradingPayPal iTradingPayPal;
    @Autowired
    private ITradingReturnpolicy iTradingReturnpolicy;
    @Autowired
    private ITradingBuyerRequirementDetails iTradingBuyerRequirementDetails;
    @Autowired
    private ITradingItemAddress iTradingItemAddress;
    @Autowired
    private ITradingShippingDetails iTradingShippingDetails;
    @Autowired
    private TradingReturnpolicyMapper tradingReturnpolicyMapper;
    @Autowired
    private TradingItemAddressMapper tradingItemAddressMapper;
    @Autowired
    private ITradingShippingServiceOptions iTradingShippingServiceOptions;
    @Autowired
    private ITradingInternationalShippingServiceOption iTradingInternationalShippingServiceOption;
    @Autowired
    private ITradingDiscountPriceInfo iTradingDiscountPriceInfo;
    @Autowired
    private UsercontrollerPaypalAccountMapper usercontrollerPaypalAccountMapper;
    @Autowired
    private TradingPaypalMapper tradingPaypalMapper;
    @Autowired
    private TradingBuyerRequirementDetailsMapper tradingBuyerRequirementDetailsMapper;
    @Autowired
    private TradingDiscountpriceinfoMapper tradingDiscountpriceinfoMapper;
    @Autowired
    private TradingShippingdetailsMapper tradingShippingdetailsMapper;
    @Autowired
    public PublicUserConfigMapper publicUserConfigMapper;
    @Autowired
    public IPublicItemInventory iPublicItemInventory;
    @Autowired
    public IPublicItemInformation iPublicItemInformation;
    @Autowired
    public IPublicItemCustom iPublicItemCustom;
    @Autowired
    public IPublicItemSupplier iPublicItemSupplier;
    @Autowired
    public UsercontrollerUserMapper usercontrollerUserMapper;
    @Autowired
    public IPublicItemPictureaddrAndAttr iPublicItemPictureaddrAndAttr;
    @Autowired
    private ITradingDescriptionDetails iTradingDescriptionDetails;
    @Autowired
    private ITradingTemplateInitTable iTradingTemplateInitTable;
    @Autowired
    private PayPalService payPalService;
    @Autowired
    private ITradingListingPicUrl iTradingListingPicUrl;
    @Autowired
    private ITradingListingReport iTradingListingReport;
    @Autowired
    private ITradingListingSuccess iTradingListingSuccess;
    @Value("${IMAGE_URL_PREFIX}")
    private String image_url_prefix;

    @Value("${ITEM_LIST_ICON_URL}")
    private String item_list_icon_url;
    @Autowired
    public PublicItemPictureaddrAndAttrMapper publicItemPictureaddrAndAttrMapper;
    @Autowired
    public ITradingAssessViewSet iTradingAssessViewSet;
    @Autowired
    private ITradingDataDictionary iTradingDataDictionary;
    @Autowired
    private TradingTempTypeKeyMapper tradingTempTypeKeyMapper;
    @Autowired
    private ITradingShippingServiceOptionsDoc iTradingShippingServiceOptionsDoc;
    @Autowired
    private ITradingInternationalShippingServiceOptionDoc iTradingInternationalShippingServiceOptionDoc;
    @Autowired
    private IItemDescription iItemDescription;
    @Autowired
    private ITradingItem iTradingItem;

    @Override
    public <T> void doWork(String ebpRes, T... t) {

        if(StringUtils.isEmpty(ebpRes)){return;}
        TaskMessageVO taskMessageVO = (TaskMessageVO)t[0];
        Object[] xs= (Object[]) taskMessageVO.getObjClass();
        Item item = (Item)xs[0];
        TradingItemWithBLOBs tradingItem = (TradingItemWithBLOBs)xs[1];
        Map map = (Map)xs[2];
        String cosXml = (String)xs[3];
        item.setSite(tradingItem.getSite());
        String ebpAck = null;
        //logger.error("更新范本：：：反回信息：：：："+ebpRes);
        try {
            ebpAck = SamplePaseXml.getVFromXmlString(ebpRes, "Ack");
            if ("Success".equalsIgnoreCase(ebpAck) || "Warning".equalsIgnoreCase(ebpAck)) {
                this.saveItem(item,tradingItem,map);
            }else {
                SendEMailUtil.sendEmail2Admin("请求xml：：：：：：：：返回xml：：：：：" + ebpRes, "更新在线范本报错：");
                logger.error("通过范本更新在线数据报错:::::::::::::请求xml：：：：："+cosXml);
                logger.error("通过范本更新在线数据报错：：：：：：：返回xml:::::::::"+ebpRes);
            }
        } catch (Exception e) {
            logger.error("解析xml出错,请稍后到ebay网站确认结果188,一键搬家！",e);
            return;
        }
    }

    public void saveItem(Item item,TradingItemWithBLOBs tradingItem1,Map map) throws Exception {
        try{
//保存商品信息到数据库中
            if(item.getVariations()!=null){
                Pictures pt = new Pictures();
                pt.setVariationSpecificName(item.getVariations().getVariationSpecificsSet().getNameValueList().get(0).getName());
                if(item.getVariations().getPictures()!=null) {
                    pt.setVariationSpecificPictureSet(item.getVariations().getPictures().getVariationSpecificPictureSet());
                }
                item.getVariations().setPictures(pt);
            }
            tradingItem1.setConditionid(item.getConditionID().longValue());
            tradingItem1.setCategoryid(item.getPrimaryCategory().getCategoryID());
            tradingItem1.setCurrency(DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(item.getSite())).getValue1());
            //tradingItem1.setListingtype(item.getListingType());
            if(item.getStartPrice()!=null&&!"".equals(item.getStartPrice())){
                tradingItem1.setStartprice(item.getStartPrice().getValue());
            }
            if(item.getOutOfStockControl()!=null) {
                tradingItem1.setOutofstockcontrol(item.getOutOfStockControl() ? "1" : "0");
            }
            String [] paypals = (String[]) map.get("ebayAccounts");
            Map itemMap = new HashMap();
            String mouth = (String) map.get("dataMouth");//刊登方式
            //String [] dicUrl =
            Asserts.assertTrue(paypals != null, "请选择一个paypal帐号!");
            for(int is =0;is<paypals.length;is++) {
                TradingItemWithBLOBs tradingItem = new TradingItemWithBLOBs();
                tradingItem = tradingItem1;

                tradingItem.setEbayAccount(paypals[is]);
                if(is>=1){
                    tradingItem.setId(null);
                }
                tradingItem.setTitle(HtmlUtils.htmlEscape((String) map.get("Title_" + paypals[is])));
                tradingItem.setSubtitle((String) map.get("SubTitle_" + paypals[is]));
                if(map.get("Quantity_" + paypals[is])!=null&&!"".equals(map.get("Quantity_" + paypals[is]))){
                    tradingItem.setQuantity(Long.parseLong((String) map.get("Quantity_" + paypals[is])));
                }
                if(map.get("StartPrice.value_" + paypals[is])!=null&&!"".equals(map.get("StartPrice.value_" + paypals[is]))){
                    tradingItem.setStartprice(Double.parseDouble((String) map.get("StartPrice.value_" + paypals[is])));
                }
                if("timeSave".equals(mouth)){
                    tradingItem.setListingWay("1");//表示为定时刊登
                }else{
                    tradingItem.setListingWay("0");//表示为正常刊登
                }
                tradingItem.setSku(item.getSKU());
                tradingItem.setListingduration(item.getListingDuration() == null ? "GTC" : item.getListingDuration());
                /*TradingAssessViewSet ta = this.iTradingAssessViewSet.selectByUserid((Long) map.get("userId"));
                if(ta!=null) {
                    tradingItem.setAssessRange(map.get("setView")==null?"": (String) map.get("setView"));
                    tradingItem.setAssessSetview(ta.getSetview());
                }*/
                tradingItem.setItemName(StringEscapeUtils.escapeHtml(tradingItem.getItemName()));
                if(item.getSecondaryCategory()!=null&&!ObjectUtils.isLogicalNull(item.getSecondaryCategory().getCategoryID())) {
                    tradingItem.setSecondaryCategoryid(item.getSecondaryCategory().getCategoryID());
                }else{
                    tradingItem.setSecondaryCategoryid("");
                }
                tradingItem.setUpdateTimer(new Date());
                this.iTradingItem.saveTradingItem(tradingItem);

                //处理运输选项，
                //国内运输
                String [] ShippingServiceCost = (String[]) map.get(tradingItem.getShippingDeailsId() + ".ShippingServiceCost.value");
                String [] ShippingServiceAdditionalCost = (String[]) map.get(tradingItem.getShippingDeailsId()+".ShippingServiceAdditionalCost.value");
                String [] ShippingSurcharge = (String[]) map.get(tradingItem.getShippingDeailsId()+".ShippingSurcharge.value");
                String [] shippingservice = (String[]) map.get(tradingItem.getShippingDeailsId()+".shippingservice");
                String [] sourceId = (String[]) map.get(tradingItem.getShippingDeailsId()+".sourceId");
                List<TradingShippingserviceoptions> litsd = this.iTradingShippingServiceOptions.selectByParentId(tradingItem.getShippingDeailsId());
                String shippingName = "";
                List<TradingShippingserviceoptionsDoc> litsds = new ArrayList<TradingShippingserviceoptionsDoc>();
                for(int n=0;n<litsd.size();n++){
                    TradingShippingserviceoptions tsd = litsd.get(n);
                    TradingShippingserviceoptionsDoc tsds = new TradingShippingserviceoptionsDoc();
                    ConvertPOJOUtil.convert(tsds, tsd);
                    if(tsds.getShippingservice()==null){
                        continue;
                    }
                    shippingName = DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tsds.getShippingservice())).getName();
                    if(shippingservice[n].equals(tsds.getShippingservice())){
                        tsds.setShippingservicecost(Double.parseDouble(ShippingServiceCost[n]));
                        tsds.setShippingserviceadditionalcost(Double.parseDouble(ShippingServiceAdditionalCost[n]));
                        tsds.setShippingsurcharge(Double.parseDouble(ShippingSurcharge[n]));
                        tsds.setSourceId(tsds.getId());
                    }else{
                    	tsds.setShippingservicecost(Double.parseDouble(ShippingServiceCost[n]));
                        tsds.setShippingserviceadditionalcost(Double.parseDouble(ShippingServiceAdditionalCost[n]));
                        tsds.setShippingsurcharge(Double.parseDouble(ShippingSurcharge[n]));
                        tsds.setSourceId(tsds.getId());
                    }
                    tsds.setParentId(tradingItem.getShippingDeailsId());
                    tsds.setParentUuid(tradingItem.getUuid());
                    tsds.setDocId(tradingItem.getId());
                    tsds.setCreateUser((Long) map.get("userId"));
                    tsds.setCreateTime(new Date());
                    litsds.add(tsds);
                }
                //选删除记录信息，再保存
                this.iTradingShippingServiceOptionsDoc.delTradingShippingserviceoptionsDoc(tradingItem.getId());
                if(litsds!=null&&litsds.size()>0) {
                    this.iTradingShippingServiceOptionsDoc.saveTradingShippingserviceoptionsDoc(litsds);
                }
                //国际运输
                String [] intershippingservice = (String[]) map.get(tradingItem.getShippingDeailsId() + ".intershippingservice");
                String [] interShippingServiceCost = (String[]) map.get(tradingItem.getShippingDeailsId()+".interShippingServiceCost.value");
                String [] interShippingServiceAdditionalCost = (String[]) map.get(tradingItem.getShippingDeailsId()+".interShippingServiceAdditionalCost.value");
                String [] intersourceId = (String[]) map.get(tradingItem.getShippingDeailsId()+".intersourceId");
                List<TradingInternationalshippingserviceoptionDoc> liidoc = new ArrayList<TradingInternationalshippingserviceoptionDoc>();
                List<TradingInternationalshippingserviceoption> litio = this.iTradingInternationalShippingServiceOption.selectByParentid(tradingItem.getShippingDeailsId());
                for(int n=0;n<litio.size();n++){
                    TradingInternationalshippingserviceoption tio = litio.get(n);
                    TradingInternationalshippingserviceoptionDoc tidoc = new TradingInternationalshippingserviceoptionDoc();
                    ConvertPOJOUtil.convert(tidoc,tio);
                    if(tidoc.getShippingservice()==null){
                        continue;
                    }
                    shippingName = DataDictionarySupport.getTradingDataDictionaryByID(Long.parseLong(tidoc.getShippingservice())).getName();
                    if(intershippingservice[n].equals(tidoc.getShippingservice())){
                        tidoc.setShippingservicecost(Double.parseDouble(interShippingServiceCost[n]));
                        tidoc.setShippingserviceadditionalcost(Double.parseDouble(interShippingServiceAdditionalCost[n]));
                        tidoc.setSourceId(tidoc.getId());
                    }else{
                    	tidoc.setShippingservicecost(Double.parseDouble(interShippingServiceCost[n]));
                        tidoc.setShippingserviceadditionalcost(Double.parseDouble(interShippingServiceAdditionalCost[n]));
                        tidoc.setSourceId(tidoc.getId());
                    }
                    tidoc.setParentId(tradingItem.getShippingDeailsId());
                    tidoc.setParentUuid(tradingItem.getUuid());
                    tidoc.setDocId(tradingItem.getId());
                    tidoc.setCreateUser((Long) map.get("userId"));
                    tidoc.setCreateTime(new Date());
                    liidoc.add(tidoc);
                }
                this.iTradingInternationalShippingServiceOptionDoc.delTradingInternationalshippingserviceoptionDoc(tradingItem.getId());
                if(liidoc!=null&&liidoc.size()>0){
                    this.iTradingInternationalShippingServiceOptionDoc.saveTradingInternationalshippingserviceoptionDoc(liidoc);
                }
                //处理模板中上传的图片
           /* if(tradingItem.getTemplateId()!=null){*///用户选择了模板，才会处理模板图片信息
                String [] tempicUrls = (String[]) map.get("blankimg");//界面中添加的模板图片
                TradingTemplateInitTable ttit = this.iTradingTemplateInitTable.selectById(tradingItem.getTemplateId());
                if(tempicUrls!=null&&tempicUrls.length>0){//如果界面中模板图片不为空，那么替换模板中ＵＲＬ地址
                    this.iTradingAttrMores.deleteByParentId("TemplatePicUrl",tradingItem.getId());
                    for(String url:tempicUrls){
                        TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("TemplatePicUrl",url);
                        tam.setParentId(tradingItem.getId());
                        tam.setParentUuid(tradingItem.getUuid());
                        this.iTradingAttrMores.saveAttrMores(tam);
                    }
                }
            /*}*/
                if(!tradingItem1.getListingtype().equals("2")){
                    TradingVariations tvs = this.iTradingVariations.selectByParentId(tradingItem.getId());
                    if(tvs!=null) {
                        List<TradingVariation> dlitv = this.iTradingVariation.selectByParentId(tvs.getId());
                        for (TradingVariation tv : dlitv) {
                            List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecifics", tv.getId());
                            for (TradingPublicLevelAttr tpa : litpla) {
                                this.iTradingPublicLevelAttr.deleteByParentID(null, tpa.getId());
                            }
                            this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecifics", tv.getId());
                        }
                        this.iTradingVariation.deleteParentId(tvs.getId());

                        List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificsSet", tvs.getId());
                        for (TradingPublicLevelAttr tpa : litpla) {
                            List<TradingPublicLevelAttr> li = this.iTradingPublicLevelAttr.selectByParentId("NameValueList", tpa.getId());
                            for (TradingPublicLevelAttr l : li) {
                                this.iTradingAttrMores.deleteByParentId("Name", l.getId());
                                this.iTradingAttrMores.deleteByParentId("Value", l.getId());
                            }
                            this.iTradingPublicLevelAttr.deleteByParentID("NameValueList", tpa.getId());
                        }
                        this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificsSet", tvs.getId());
                    }
                }

                itemMap.put(paypals[is],tradingItem.getId());
                if(tradingItem1.getListingtype().equals("Chinese")){//拍买商品保存数据
                    TradingAddItem tai = this.iTradingAddItem.selectParentId(tradingItem.getId());
                    if(tai==null){
                        tai = new TradingAddItem();
                    }
                    tai.setParentId(tradingItem.getId());
                    tai.setParentUuid(tradingItem.getUuid());
                    if(tradingItem.getDiscountpriceinfoId()!=null){
                        tai.setDisId(tradingItem.getDiscountpriceinfoId());
                    }
                    tai.setListingflag((String) map.get("ListingFlag"));
                    tai.setListingduration((String) map.get("ListingDuration"));
                    tai.setListingscale(Long.parseLong(map.get("ListingScale")==null?"0":(String) map.get("ListingScale")));
                    tai.setBuyitnowprice(Double.parseDouble(map.get("BuyItNowPrice")==null?"0":(String) map.get("BuyItNowPrice")));
                    tai.setPrivatelisting((String) map.get("PrivateListing"));
                    tai.setReserveprice(Double.parseDouble( map.get("ReservePrice")==null?"0":(String) map.get("ReservePrice")));
                    tai.setSecondflag((String) map.get("SecondFlag"));
                    this.iTradingAddItem.saveAddItem(tai);

                }else if(tradingItem1.getListingtype().equals("2")){//多属性刊登保存数据
                    //保存多属性信息
                    if(item.getVariations()!=null){
                        //删除下面的所有字属性
                        TradingVariations tvs = this.iTradingVariations.selectByParentId(tradingItem.getId());
                        if(tvs!=null) {
                            List<TradingVariation> dlitv = this.iTradingVariation.selectByParentId(tvs.getId());
                            for (TradingVariation tv : dlitv) {
                                List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecifics", tv.getId());
                                for (TradingPublicLevelAttr tpa : litpla) {
                                    this.iTradingPublicLevelAttr.deleteByParentID(null, tpa.getId());
                                }
                                this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecifics", tv.getId());
                            }
                            this.iTradingVariation.deleteParentId(tvs.getId());

                            List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificsSet", tvs.getId());
                            for (TradingPublicLevelAttr tpa : litpla) {
                                List<TradingPublicLevelAttr> li = this.iTradingPublicLevelAttr.selectByParentId("NameValueList", tpa.getId());
                                for (TradingPublicLevelAttr l : li) {
                                    this.iTradingAttrMores.deleteByParentId("Name", l.getId());
                                    this.iTradingAttrMores.deleteByParentId("Value", l.getId());
                                }
                                this.iTradingPublicLevelAttr.deleteByParentID("NameValueList", tpa.getId());
                            }
                            this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificsSet", tvs.getId());
                        }
                        this.iTradingVariations.deleteByparentId(tradingItem.getId());
                        TradingVariations tv = new TradingVariations();
                        tv.setParentId(tradingItem.getId());
                        tv.setParentUuid(tradingItem.getUuid());
                        tv.setCreateUser(Long.parseLong(map.get("userId")+""));
                        this.iTradingVariations.saveVariations(tv);
                        List<Variation> livt = item.getVariations().getVariation();
                        for(int i = 0 ; i<livt.size();i++){
                            Variation vtion = livt.get(i);
                            TradingVariation tvtion = this.iTradingVariation.toDAOPojo(vtion);
                            tvtion.setParentId(tv.getId());
                            tvtion.setParentUuid(tv.getUuid());
                            tvtion.setStartprice(vtion.getStartPrice().getValue());
                            tvtion.setCreateUser((Long)map.get("userId"));
                            this.iTradingVariation.saveVariation(tvtion);

                            this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecifics",tvtion.getId());

                            TradingPublicLevelAttr tpla = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecifics",null);
                            tpla.setParentId(tvtion.getId());
                            tpla.setParentUuid(tvtion.getUuid());
                            this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);

                            List<NameValueList> linvls = item.getVariations().getVariationSpecificsSet().getNameValueList();
                            this.iTradingPublicLevelAttr.deleteByParentID(null,tpla.getId());
                            List<VariationSpecifics> livar = vtion.getVariationSpecifics();
                            if(livar!=null){
                                for(VariationSpecifics vs:livar){
                                    List<NameValueList> linvlss = vs.getNameValueList();
                                    if(linvlss!=null){
                                        for(NameValueList vss : linvlss){
                                            TradingPublicLevelAttr tpl = this.iTradingPublicLevelAttr.toDAOPojo(vss.getName(),vss.getValue().get(0));
                                            tpl.setParentUuid(tpla.getUuid());
                                            tpl.setParentId(tpla.getId());
                                            this.iTradingPublicLevelAttr.savePublicLevelAttr(tpl);
                                        }
                                    }
                                }
                            }
                        }

                        if(item.getVariations().getVariationSpecificsSet()!=null){
                            this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificsSet",tv.getId());
                            TradingPublicLevelAttr tpla = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecificsSet",null);
                            tpla.setParentId(tv.getId());
                            tpla.setParentUuid(tv.getUuid());
                            this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);

                            this.iTradingPublicLevelAttr.deleteByParentID("NameValueList",tpla.getId());
                            List<NameValueList> liset = item.getVariations().getVariationSpecificsSet().getNameValueList();
                            for(int i = 0; i<liset.size();i++){
                                NameValueList nvl = liset.get(i);
                                TradingPublicLevelAttr tplas = this.iTradingPublicLevelAttr.toDAOPojo("NameValueList",null);
                                tplas.setParentId(tpla.getId());
                                tplas.setParentUuid(tpla.getUuid());
                                this.iTradingPublicLevelAttr.savePublicLevelAttr(tplas);

                                this.iTradingAttrMores.deleteByParentId("Name",tplas.getId());
                                TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("Name",nvl.getName());
                                tam.setParentId(tplas.getId());
                                tam.setParentUuid(tplas.getUuid());
                                this.iTradingAttrMores.saveAttrMores(tam);

                                this.iTradingAttrMores.deleteByParentId("Value",tplas.getId());
                                List<String> listr = nvl.getValue();
                                listr = MyCollectionsUtil.listUnique(listr);
                                for(String str: listr){
                                    if(str!=null&&!"".equals(str)){
                                        TradingAttrMores tams = this.iTradingAttrMores.toDAOPojo("Value",str);
                                        tams.setParentId(tplas.getId());
                                        tams.setParentUuid(tplas.getUuid());
                                        this.iTradingAttrMores.saveAttrMores(tams);
                                    }
                                }
                            }
                        }
                        String [] morePicUrl = (String[]) map.get("pic_mackid_more");
                        //保存多属必图片信息
                        Pictures pictrue = item.getVariations().getPictures();
                        if(pictrue!=null) {
                            TradingPictures tp = this.iTradingPictures.toDAOPojo(pictrue);
                            tp.setParentId(tv.getId());
                            tp.setParentUuid(tv.getUuid());
                            tp.setCreateUser((Long) map.get("userId"));
                            this.iTradingPictures.savePictures(tp);

                            List<VariationSpecificPictureSet> vspsli = pictrue.getVariationSpecificPictureSet();
                            this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificPictureSet", tp.getId());
                            if (vspsli != null) {
                                for (VariationSpecificPictureSet vsps : vspsli) {
                                    TradingPublicLevelAttr tplas = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecificPictureSet", null);
                                    tplas.setParentId(tp.getId());
                                    tplas.setParentUuid(tp.getUuid());
                                    this.iTradingPublicLevelAttr.savePublicLevelAttr(tplas);

                                    this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificValue", tplas.getId());

                                    TradingPublicLevelAttr tpname = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecificValue", vsps.getVariationSpecificValue());
                                    tpname.setParentId(tplas.getId());
                                    tpname.setParentUuid(tplas.getUuid());
                                    this.iTradingPublicLevelAttr.savePublicLevelAttr(tpname);

                                    this.iTradingAttrMores.deleteByParentId("MuAttrPictureURL", tplas.getId());
                                    if (vsps.getPictureURL() != null) {
                                        List<String> listr = vsps.getPictureURL();
                                        listr = MyCollectionsUtil.listUnique(listr);
                                        for (int i = 0; i < listr.size(); i++) {
                                            String str = listr.get(i);
                                            if (str != null && !"".equals(str)) {
                                                TradingAttrMores tams = this.iTradingAttrMores.toDAOPojo("MuAttrPictureURL", str);
                                                tams.setParentId(tplas.getId());
                                                tams.setParentUuid(tplas.getUuid());
                                                String mackId = EncryptionUtil.md5Encrypt(str);
                                                tams.setAttr1(mackId);
                                                this.iTradingAttrMores.saveAttrMores(tams);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    /*//保存多属性信息
                    if(item.getVariations()!=null){
                        //删除下面的所有字属性
                        TradingVariations tvs = this.iTradingVariations.selectByParentId(tradingItem.getId());
                        if(tvs!=null) {
                            List<TradingVariation> dlitv = this.iTradingVariation.selectByParentId(tvs.getId());
                            for (TradingVariation tv : dlitv) {
                                List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecifics", tv.getId());
                                for (TradingPublicLevelAttr tpa : litpla) {
                                    this.iTradingPublicLevelAttr.deleteByParentID(null, tpa.getId());
                                }
                                this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecifics", tv.getId());
                            }
                            this.iTradingVariation.deleteParentId(tvs.getId());

                            List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("VariationSpecificsSet", tvs.getId());
                            for (TradingPublicLevelAttr tpa : litpla) {
                                List<TradingPublicLevelAttr> li = this.iTradingPublicLevelAttr.selectByParentId("NameValueList", tpa.getId());
                                for (TradingPublicLevelAttr l : li) {
                                    this.iTradingAttrMores.deleteByParentId("Name", l.getId());
                                    this.iTradingAttrMores.deleteByParentId("Value", l.getId());
                                }
                                this.iTradingPublicLevelAttr.deleteByParentID("NameValueList", tpa.getId());
                            }
                            this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificsSet", tvs.getId());
                        }

                        TradingVariations tv = new TradingVariations();
                        tv.setParentId(tradingItem.getId());
                        tv.setParentUuid(tradingItem.getUuid());
                        tv.setCreateUser((Long) map.get("userId"));

                        this.iTradingVariations.saveVariations(tv);
                        List<Variation> livt = item.getVariations().getVariation();
                        for(int i = 0 ; i<livt.size();i++){
                            Variation vtion = livt.get(i);
                            TradingVariation tvtion = this.iTradingVariation.toDAOPojo(vtion);
                            tvtion.setParentId(tv.getId());
                            tvtion.setParentUuid(tv.getUuid());
                            tvtion.setStartprice(vtion.getStartPrice().getValue());
                            tvtion.setCreateUser((Long) map.get("userId"));

                            this.iTradingVariation.saveVariation(tvtion);

                            this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecifics",tvtion.getId());

                            TradingPublicLevelAttr tpla = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecifics",null);
                            tpla.setParentId(tvtion.getId());
                            tpla.setParentUuid(tvtion.getUuid());

                            this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);

                            List<NameValueList> linvls = item.getVariations().getVariationSpecificsSet().getNameValueList();
                            this.iTradingPublicLevelAttr.deleteByParentID(null,tpla.getId());
                            if(linvls!=null&&linvls.size()>0){
                                for(NameValueList vs : linvls){
                                    if(vs.getValue()==null||vs.getValue().size()==0){
                                        continue;
                                    }
                                    TradingPublicLevelAttr tpl = this.iTradingPublicLevelAttr.toDAOPojo(vs.getName(),vs.getValue().get(i));
                                    tpl.setParentUuid(tpla.getUuid());
                                    tpl.setParentId(tpla.getId());
                                    this.iTradingPublicLevelAttr.savePublicLevelAttr(tpl);
                                }
                            }
                        }

                        if(item.getVariations().getVariationSpecificsSet()!=null){
                            this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificsSet",tv.getId());
                            TradingPublicLevelAttr tpla = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecificsSet",null);
                            tpla.setParentId(tv.getId());
                            tpla.setParentUuid(tv.getUuid());
                            this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);

                            this.iTradingPublicLevelAttr.deleteByParentID("NameValueList",tpla.getId());
                            List<NameValueList> liset = item.getVariations().getVariationSpecificsSet().getNameValueList();
                            for(int i = 0; i<liset.size();i++){
                                NameValueList nvl = liset.get(i);
                                TradingPublicLevelAttr tplas = this.iTradingPublicLevelAttr.toDAOPojo("NameValueList",null);
                                tplas.setParentId(tpla.getId());
                                tplas.setParentUuid(tpla.getUuid());
                                this.iTradingPublicLevelAttr.savePublicLevelAttr(tplas);

                                this.iTradingAttrMores.deleteByParentId("Name",tplas.getId());
                                TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("Name",nvl.getName());
                                tam.setParentId(tplas.getId());
                                tam.setParentUuid(tplas.getUuid());
                                this.iTradingAttrMores.saveAttrMores(tam);

                                this.iTradingAttrMores.deleteByParentId("Value",tplas.getId());
                                List<String> listr = nvl.getValue();
                                listr = MyCollectionsUtil.listUnique(listr);
                                for(String str: listr){
                                    if(str!=null&&!"".equals(str)){
                                        TradingAttrMores tams = this.iTradingAttrMores.toDAOPojo("Value",str);
                                        tams.setParentId(tplas.getId());
                                        tams.setParentUuid(tplas.getUuid());
                                        this.iTradingAttrMores.saveAttrMores(tams);
                                    }
                                }
                            }
                        }
                        String [] morePicUrl = (String[]) map.get("pic_mackid_more");
                        //保存多属必图片信息
                        Pictures pictrue = item.getVariations().getPictures();
                        if(pictrue!=null) {
                            TradingPictures tp = this.iTradingPictures.toDAOPojo(pictrue);
                            tp.setParentId(tv.getId());
                            tp.setCreateUser((Long) map.get("userId"));
                            tp.setParentUuid(tv.getUuid());
                            this.iTradingPictures.savePictures(tp);

                            List<VariationSpecificPictureSet> vspsli = pictrue.getVariationSpecificPictureSet();
                            this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificPictureSet", tp.getId());
                            if (vspsli != null) {
                                for (VariationSpecificPictureSet vsps : vspsli) {
                                    TradingPublicLevelAttr tplas = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecificPictureSet", null);
                                    tplas.setParentId(tp.getId());
                                    tplas.setParentUuid(tp.getUuid());
                                    this.iTradingPublicLevelAttr.savePublicLevelAttr(tplas);

                                    this.iTradingPublicLevelAttr.deleteByParentID("VariationSpecificValue", tplas.getId());

                                    TradingPublicLevelAttr tpname = this.iTradingPublicLevelAttr.toDAOPojo("VariationSpecificValue", vsps.getVariationSpecificValue());
                                    tpname.setParentId(tplas.getId());
                                    tpname.setParentUuid(tplas.getUuid());
                                    this.iTradingPublicLevelAttr.savePublicLevelAttr(tpname);

                                    this.iTradingAttrMores.deleteByParentId("MuAttrPictureURL", tplas.getId());
                                    if (vsps.getPictureURL() != null) {
                                        List<String> listr = vsps.getPictureURL();
                                        listr = MyCollectionsUtil.listUnique(listr);
                                        for (int i = 0; i < listr.size(); i++) {
                                            String str = listr.get(i);
                                            if (str != null && !"".equals(str)) {
                                                TradingAttrMores tams = this.iTradingAttrMores.toDAOPojo("MuAttrPictureURL", str);
                                                tams.setParentId(tplas.getId());
                                                tams.setParentUuid(tplas.getUuid());
                                                String mackId = EncryptionUtil.md5Encrypt(str);
                                                tams.setAttr1(mackId);
                                                this.iTradingAttrMores.saveAttrMores(tams);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }*/
                }
                String [] picUrl =  (String[]) map.get("pic_mackid");
                //保存图片信息
                PictureDetails picd = item.getPictureDetails();
                if(picd!=null){
                    String [] picurl =  (String[]) map.get("PictureDetails_"+paypals[is]+".PictureURL");
                    TradingPicturedetails tpicd = this.iTradingPictureDetails.toDAOPojo(picd);
                    tpicd.setParentId(tradingItem.getId());
                    tpicd.setParentUuid(tradingItem.getUuid());
                    tpicd.setGallerytype("Gallery");
                    tpicd.setPhotodisplay("PicturePack");
                    if(picurl!=null&&picurl.length>0) {
                        tpicd.setGalleryurl(picurl[0]);
                    }
                    tpicd.setCreateUser((Long) map.get("userId"));
                    this.iTradingPictureDetails.savePictureDetails(tpicd);
                    this.iTradingAttrMores.deleteByParentId("PictureURL",tpicd.getId());
                    if(picurl!=null&&picurl.length>0) {
                        for (int i = 0; i < picurl.length; i++) {
                            TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("PictureURL", picurl[i]);
                            tam.setParentId(tpicd.getId());
                            tam.setParentUuid(tpicd.getUuid());
                            tam.setAttr1(picUrl[i]);
                            this.iTradingAttrMores.saveAttrMores(tam);
                        }
                    }
                }else{
                    String [] picurl =  (String[]) map.get("PictureDetails_"+paypals[is]+".PictureURL");
                    TradingPicturedetails tpicd = this.iTradingPictureDetails.toDAOPojo(picd);
                    tpicd.setParentId(tradingItem.getId());
                    tpicd.setParentUuid(tradingItem.getUuid());
                    tpicd.setGallerytype("Gallery");
                    tpicd.setPhotodisplay("PicturePack");
                    tpicd.setCreateUser((Long) map.get("userId"));
                    if(picurl!=null&&picurl.length>0) {
                        tpicd.setGalleryurl(picurl[0]);
                    }
                    this.iTradingPictureDetails.savePictureDetails(tpicd);
                    this.iTradingAttrMores.deleteByParentId("PictureURL",tpicd.getId());
                    Asserts.assertTrue(picurl!=null,"请为商品选择展示图片!");
                    for(int i = 0;i<picurl.length;i++){
                        TradingAttrMores tam = this.iTradingAttrMores.toDAOPojo("PictureURL",picurl[i]);
                        tam.setParentId(tpicd.getId());
                        tam.setParentUuid(tpicd.getUuid());
                        tam.setAttr1(picUrl[i]);
                        this.iTradingAttrMores.saveAttrMores(tam);
                    }
                }
                //保存属性值
                if(item.getItemSpecifics()!=null){
                    ItemSpecifics iscs = item.getItemSpecifics();
                    List<NameValueList> linv = iscs.getNameValueList();
                    //删除下面的字属性
                    List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("ItemSpecifics",tradingItem.getId());
                    for(TradingPublicLevelAttr tpa : litpla){
                        this.iTradingPublicLevelAttr.deleteByParentID(null,tpa.getId());
                    }
                    this.iTradingPublicLevelAttr.deleteByParentID("ItemSpecifics",tradingItem.getId());
                    TradingPublicLevelAttr tpla = this.iTradingPublicLevelAttr.toDAOPojo("ItemSpecifics",null);
                    tpla.setParentId(tradingItem.getId());
                    tpla.setParentUuid(tradingItem.getUuid());
                    this.iTradingPublicLevelAttr.savePublicLevelAttr(tpla);

                    this.iTradingPublicLevelAttr.deleteByParentID(null,tpla.getId());
                    for(NameValueList nvl : linv){
                        if(nvl.getValue()!=null&&nvl.getValue().size()>0) {
                            TradingPublicLevelAttr tpl = this.iTradingPublicLevelAttr.toDAOPojo(nvl.getName(), nvl.getValue().get(0).toString());
                            tpl.setParentUuid(tpla.getUuid());
                            tpl.setParentId(tpla.getId());
                            this.iTradingPublicLevelAttr.savePublicLevelAttr(tpl);
                        }
                    }
                }else{
                    List<TradingPublicLevelAttr> litpla = this.iTradingPublicLevelAttr.selectByParentId("ItemSpecifics",tradingItem.getId());
                    for(TradingPublicLevelAttr tpa : litpla){
                        this.iTradingPublicLevelAttr.deleteByParentID(null,tpa.getId());
                    }
                    this.iTradingPublicLevelAttr.deleteByParentID("ItemSpecifics",tradingItem.getId());
                }
            }
        }catch(Exception e){
            logger.error("更新在线范本报错：",e);
            throw new Exception(e);
        }

    }
    @Override
    public String getType() {
        return SiteMessageStatic.UPDATE_LISTING_ITEM_BEAN;
    }
}
