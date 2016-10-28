package com.base.utils.xmlutils;

import com.base.database.publicd.mapper.PublicDataDictMapper;
import com.base.database.publicd.model.PublicDataDict;
import com.base.database.publicd.model.PublicDataDictExample;
import com.base.database.trading.mapper.TradingDataDictionaryMapper;
import com.base.database.trading.model.*;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.DataDictionarySupport;
import com.base.utils.common.DateUtils;
import com.base.utils.common.DictCollectionsUtil;
import com.base.utils.htmlutil.HtmlUtil;
import com.base.utils.common.MyStringUtil;
import com.base.xmlpojo.trading.addproduct.*;
import com.base.xmlpojo.trading.addproduct.attrclass.*;
import com.promotionalsale.service.ITradingItemPromotionalSaleRule;
import com.promotionalsale.service.ITradingItemPromotionalSaleSet;
import com.trading.service.ITradingCateGoryTask;
import com.trading.service.ITradingStoreCategory;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.util.HtmlUtils;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/7.
 * 解析结构比较简单的xml
 */
public class SamplePaseXml {
    static Logger logger = Logger.getLogger(SamplePaseXml.class);

    public static Document formatStr2Doc(String xml) throws Exception{
        if("apiFail".equalsIgnoreCase(xml)){return null;}
        if(StringUtils.isBlank(xml) || !StringUtils.containsIgnoreCase(xml, "<?xml")){
            logger.error("传入的xml内容不对!"+xml);
            return null;
        }
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
        } catch (DocumentException e) {
            logger.error("格式化xml文档失败！DocumentException");
            return null;
        } catch (UnsupportedEncodingException e) {
            logger.error("格式化xml：UnsupportedEncodingException");
            return null;
        }catch (Exception x){
            logger.error("格式化xml：报错");
            return null;
        }
        return document;
    }


    public static String getVFromXmlString(String xml,String nodeName) throws Exception {
        if("apiFail".equalsIgnoreCase(xml)){return null;}
        if(StringUtils.isBlank(xml) || !StringUtils.containsIgnoreCase(xml,"<?xml")){
            logger.error("传入的xml内容不对!"+xml);
            return null;
        }
        //ByteArrayInputStream is = new ByteArrayInputStream(res.getBytes());//文件
        Document document=formatStr2Doc(xml);
        if (document==null){return null;}
        //Document document = DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        //Iterator iter = rootElt.elementIterator("SessionID");
        Element e =  rootElt.element(nodeName);
        if(e==null){return null;}
        return e.getTextTrim();
    }

    public static String getResponseCaseString(String xml,String nodeName) throws Exception {
        if("apiFail".equalsIgnoreCase(xml)){return null;}
        if(StringUtils.isBlank(xml) || !StringUtils.containsIgnoreCase(xml,"<?xml")){
            logger.error("传入的xml内容不对!"+xml);
            return null;
        }
        //ByteArrayInputStream is = new ByteArrayInputStream(res.getBytes());//文件
        Document document=formatStr2Doc(xml);
        if (document==null){return null;}
        //Document document = DocumentHelper.parseText(xml);
        Element rootElt = document.getRootElement();
        //Iterator iter = rootElt.elementIterator("SessionID");
        Element errorMessage=rootElt.element("errorMessage");
        if(errorMessage!=null){
            Element e =errorMessage.element("error");
            if(e==null){return null;}
            Element e1= e.element(nodeName);
            if(e1==null){return null;}
            return e1.getTextTrim();
        }else{
            Element e =  rootElt.element("error");
            if(e==null){return null;}
            Element e1= e.element(nodeName);
            if(e1==null){return null;}
            return e1.getTextTrim();
        }

    }

    /**
     * 查询商品明细
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static Item getItem(String xml) throws Exception {
        Item item = new Item();
        Document document= formatStr2Doc(xml);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        Element element = rootElt.element("Item");
        item.setTitle(HtmlUtils.htmlEscape(element.elementText("Title")));
        item.setCurrency(element.elementText("Currency"));
        item.setCountry(element.elementText("Country"));
        item.setSite(element.elementText("Site"));
        item.setPostalCode(element.elementText("PostalCode"));
        item.setLocation(element.elementText("Location"));
        item.setItemID(element.elementText("ItemID"));
        item.setHitCounter(element.elementText("HitCounter"));
        item.setAutoPay(element.elementText("AutoPay").equals("true")?true:false);
        item.setGiftIcon(element.elementText("GiftIcon"));
        item.setListingDuration(element.elementText("ListingDuration"));
        item.setQuantity(Integer.parseInt(element.elementText("Quantity")));
        item.setPayPalEmailAddress(element.elementText("PayPalEmailAddress"));
        item.setGetItFast("false".equals(element.elementText("GetItFast"))?false:true);
        item.setPrivateListing("true".equals(element.elementText("PrivateListing"))?true:false);
        item.setDispatchTimeMax(Integer.parseInt(element.elementText("DispatchTimeMax")));
        item.setListingDuration(element.elementText("ListingDuration"));
        item.setDescription(element.elementText("Description"));
        item.setSKU(element.elementText("SKU"));
        item.setConditionID(Integer.parseInt(element.elementText("CategoryID")==null?"1000":element.elementText("CategoryID")));
        PrimaryCategory pc = new PrimaryCategory();
        pc.setCategoryID(element.element("PrimaryCategory").elementText("CategoryID"));

        item.setPrimaryCategory(pc);
        String listType = "";
        if("FixedPriceItem".equals(element.elementText("ListingType"))){
            if(element.element("Variations")!=null){
                listType = "2";
            }else{
                listType = element.elementText("ListingType");
            }
        }else{
            listType = element.elementText("ListingType");
        }
        item.setListingType(listType);
        //自定义属性
        Element elspe = element.element("ItemSpecifics");
        if(elspe!=null){
            ItemSpecifics itemSpecifics = new ItemSpecifics();
            Iterator<Element> itnvl = elspe.elementIterator("NameValueList");
            List<NameValueList> linvl = new ArrayList<NameValueList>();
            while (itnvl.hasNext()){
                Element nvl = itnvl.next();
                NameValueList nvli = new NameValueList();
                nvli.setName(nvl.elementText("Name"));
                List<String> listr = new ArrayList();
                Iterator<Element> itval = nvl.elementIterator("Value");
                while (itval.hasNext()){
                    listr.add(itval.next().getText());
                }
                nvli.setValue(listr);
                linvl.add(nvli);
            }
            itemSpecifics.setNameValueList(linvl);
            item.setItemSpecifics(itemSpecifics);
        }
        //图片信息
        Element pice = element.element("PictureDetails");
        if(pice!=null){
            PictureDetails pd = new PictureDetails();
            if(pice.elementText("GalleryType")!=null){
                pd.setGalleryType(pice.elementText("GalleryType"));
            }
            if(pice.elementText("PhotoDisplay")!=null){
                pd.setPhotoDisplay(pice.elementText("PhotoDisplay"));
            }
            if(pice.elementText("GalleryURL")!=null){
                String url = pice.elementText("GalleryURL");
                if(url.indexOf("?")>0){
                    url.substring(0,url.indexOf("?"));
                }
                pd.setGalleryURL(url);
            }
            Iterator<Element> itpicurl = pice.elementIterator("PictureURL");
            List<String> urlli = new ArrayList();
            while (itpicurl.hasNext()){
                Element url = itpicurl.next();
                String urlstr = url.getStringValue();
                if(urlstr.indexOf("?")>0){
                    urlstr = urlstr.substring(0,urlstr.indexOf("?"));
                }
                urlli.add(urlstr);
            }
            pd.setPictureURL(urlli);
            item.setPictureDetails(pd);
        }
        //取得退货政策并封装
        Element returne = element.element("ReturnPolicy");
        ReturnPolicy rp = new ReturnPolicy();
        rp.setRefundOption(returne.elementText("RefundOption"));
        rp.setReturnsWithinOption(returne.elementText("ReturnsWithinOption"));
        rp.setReturnsAcceptedOption(returne.elementText("ReturnsAcceptedOption"));
        rp.setDescription(returne.elementText("Description"));
        rp.setShippingCostPaidByOption(returne.elementText("ShippingCostPaidByOption"));
        item.setReturnPolicy(rp);
        //买家要求
        BuyerRequirementDetails brd = new BuyerRequirementDetails();
        MaximumItemRequirements mirs = new MaximumItemRequirements();
        Element buyere = element.element("BuyerRequirementDetails");
        if(buyere!=null) {
            Element maxiteme = buyere.element("MaximumItemRequirements");
            if(maxiteme!=null) {
                if (maxiteme.elementText("MaximumItemCount") != null) {
                    mirs.setMaximumItemCount(Integer.parseInt(maxiteme.elementText("MaximumItemCount")));
                }
                if (maxiteme.elementText("MinimumFeedbackScore") != null) {
                    mirs.setMinimumFeedbackScore(Integer.parseInt(maxiteme.elementText("MinimumFeedbackScore")));
                }
                brd.setMaximumItemRequirements(mirs);
            }

            Element maxUnpaid = buyere.element("MaximumUnpaidItemStrikesInfo");
            if(maxUnpaid!=null) {
                MaximumUnpaidItemStrikesInfo muis = new MaximumUnpaidItemStrikesInfo();
                String count = maxUnpaid.elementText("Count");
                muis.setCount(Integer.parseInt(count));
                muis.setPeriod(maxUnpaid.elementText("Period"));
                brd.setMaximumUnpaidItemStrikesInfo(muis);
            }

            Element maxPolicy = buyere.element("MaximumBuyerPolicyViolations");
            if(maxPolicy!=null) {
                MaximumBuyerPolicyViolations mbpv = new MaximumBuyerPolicyViolations();
                mbpv.setCount(Integer.parseInt(maxPolicy.elementText("Count")));
                mbpv.setPeriod(maxPolicy.elementText("Period"));
                brd.setMaximumBuyerPolicyViolations(mbpv);
            }
            if(buyere.elementText("LinkedPayPalAccount")!=null) {
                brd.setLinkedPayPalAccount(buyere.elementText("LinkedPayPalAccount").equals("true") ? true : false);
            }
            if(buyere.elementText("ShipToRegistrationCountry")!=null) {
                brd.setShipToRegistrationCountry(buyere.elementText("ShipToRegistrationCountry").equals("true") ? true : false);
            }
            item.setBuyerRequirementDetails(brd);
        }
        //运输选项
        ShippingDetails sd = new ShippingDetails();
        Element elsd = element.element("ShippingDetails");
        Iterator<Element> itershipping = elsd.elementIterator("ShippingServiceOptions");
        List<ShippingServiceOptions> lisso = new ArrayList();
        //国内运输
        while (itershipping.hasNext()){
            Element shipping = itershipping.next();
            ShippingServiceOptions sso = new ShippingServiceOptions();
            sso.setShippingService(shipping.elementText("ShippingService"));
            if(shipping.elementText("ShippingServiceCost")!=null){
                sso.setShippingServiceCost(new ShippingServiceCost(shipping.attributeValue("currencyID"),Double.parseDouble(shipping.elementText("ShippingServiceCost"))));
            }
            sso.setShippingServicePriority(Integer.parseInt(shipping.elementText("ShippingServicePriority")));
            if(shipping.elementText("FreeShipping")!=null){
                sso.setFreeShipping(shipping.elementText("FreeShipping").equals("true")?true:false);
            }
            ShippingServiceAdditionalCost ssac= new ShippingServiceAdditionalCost();
            ssac.setValue(Double.parseDouble(shipping.elementText("ShippingServiceAdditionalCost")!=null?shipping.elementText("ShippingServiceAdditionalCost"):"0"));
            sso.setShippingServiceAdditionalCost(ssac);
            ShippingSurcharge ss =new ShippingSurcharge();
            ss.setValue(Double.parseDouble(shipping.elementText("ShippingSurcharge")!=null?shipping.elementText("ShippingSurcharge"):"0"));
            sso.setShippingSurcharge(ss);
            lisso.add(sso);
        }
        if(lisso.size()>0){
            sd.setShippingServiceOptions(lisso);
        }
        //不运送到的国家
        Iterator<Element> excEl = elsd.elementIterator("ExcludeShipToLocation");
        List<String> liex = new ArrayList<String>();
        while(excEl.hasNext()){
            Element els = excEl.next();
            liex.add(els.getText());
        }
        if(liex.size()>0){
            sd.setExcludeShipToLocation(liex);
        }
        //国际运输
        Iterator<Element> iteInt = elsd.elementIterator("InternationalShippingServiceOption");
        List<InternationalShippingServiceOption> liint = new ArrayList<InternationalShippingServiceOption>();
        while (iteInt.hasNext()){
            Element intel = iteInt.next();
            InternationalShippingServiceOption isso = new InternationalShippingServiceOption();
            isso.setShippingService(intel.elementText("ShippingService"));
            isso.setShippingServiceCost(new ShippingServiceCost(intel.attributeValue("currencyID"),Double.parseDouble(intel.elementText("ShippingServiceCost"))));
            Iterator<Element> iteto = intel.elementIterator("ShipToLocation");
            List<String> listr = new ArrayList();
            while (iteto.hasNext()){
                listr.add(iteto.next().getText());
            }
            isso.setShipToLocation(listr);
            isso.setShippingServicePriority(Integer.parseInt(intel.elementText("ShippingServicePriority")));

            ShippingServiceAdditionalCost ssac= new ShippingServiceAdditionalCost();
            ssac.setValue(Double.parseDouble(intel.elementText("ShippingServiceAdditionalCost")!=null?intel.elementText("ShippingServiceAdditionalCost"):"0"));
            isso.setShippingServiceAdditionalCost(ssac);
            liint.add(isso);
        }
        if(liint.size()>0){
            sd.setInternationalShippingServiceOption(liint);
        }
        //计算所需的长宽高
        Element re = elsd.element("CalculatedShippingRate");
        if(re!=null){
            CalculatedShippingRate csr = new CalculatedShippingRate();
            if(re.elementText("InternationalPackagingHandlingCosts")!=null){
                csr.setPackagingHandlingCosts(new PackagingHandlingCosts(re.attributeValue("currencyID"),Double.parseDouble(re.elementText("InternationalPackagingHandlingCosts"))));
            }
            if(re.elementText("OriginatingPostalCode")!=null){
                csr.setOriginatingPostalCode(re.elementText("OriginatingPostalCode"));
            }
            if(re.elementText("PackageDepth")!=null){
                csr.setPackageDepth(Double.parseDouble(re.elementText("PackageDepth")));
            }
            if(re.elementText("PackageLength")!=null){
                csr.setPackageLength(Double.parseDouble(re.elementText("PackageLength")));
            }
            if(re.elementText("PackageWidth")!=null){
                csr.setPackageWidth(Double.parseDouble(re.elementText("PackageWidth")));
            }
            if(re.elementText("ShippingIrregular")!=null){
                csr.setShippingIrregular(Boolean.parseBoolean(re.elementText("ShippingIrregular")));
            }
            if(re.elementText("ShippingPackage")!=null){
                csr.setShippingPackage(re.elementText("ShippingPackage"));
            }
            if(re.elementText("WeightMajor")!=null){
                csr.setWeightMajor(Double.parseDouble(re.elementText("WeightMajor")));
            }
            if(re.elementText("WeightMinor")!=null){
                csr.setWeightMinor(Double.parseDouble(re.elementText("WeightMinor")));
            }
            sd.setCalculatedShippingRate(csr);
        }
        sd.setShippingType(elsd.elementText("ShippingType"));
        item.setShippingDetails(sd);
        //卖家信息
        Seller seller = new Seller();
        Element elsel = element.element("Seller");
        seller.setUserID(elsel.elementText("UserID"));
        seller.setEmail(elsel.elementText("Email"));
        item.setSeller(seller);
        //多属性
        Element vartions = element.element("Variations");
        if(vartions!=null){
            Iterator<Element> elvar = vartions.elementIterator("Variation");
            List<Variation> livar = new ArrayList();
            while (elvar.hasNext()){
                Element ele = elvar.next();
                Variation var = new Variation();
                var.setSKU(ele.elementText("SKU"));
                var.setQuantity(Integer.parseInt(ele.elementText("Quantity"))-Integer.parseInt(ele.element("SellingStatus").elementText("QuantitySold")));
                var.setStartPrice(new StartPrice(ele.attributeValue("currencyID"), Double.parseDouble(ele.elementText("StartPrice"))));
                Element elvs = ele.element("VariationSpecifics");
                Iterator<Element> elnvl = elvs.elementIterator("NameValueList");
                List<NameValueList> linvl = new ArrayList();
                while (elnvl.hasNext()){
                    Element elment = elnvl.next();
                    NameValueList nvl = new NameValueList();
                    nvl.setName(elment.elementText("Name"));
                    List<String> li = new ArrayList<String>();
                    li.add(elment.elementText("Value"));
                    nvl.setValue(li);
                    linvl.add(nvl);
                }
                List<VariationSpecifics> livs = new ArrayList();
                VariationSpecifics vs = new VariationSpecifics();
                vs.setNameValueList(linvl);
                livs.add(vs);
                var.setVariationSpecifics(livs);
                livar.add(var);
            }
            Variations vtions = new Variations();
            vtions.setVariation(livar);
            //多属性值
            Element elvss = vartions.element("VariationSpecificsSet");
            Iterator<Element> itele = elvss.elementIterator("NameValueList");
            List<NameValueList> linvl = new ArrayList();
            while (itele.hasNext()){
                Element nvlel = itele.next();
                NameValueList nvl=new NameValueList();
                nvl.setName(nvlel.elementText("Name"));
                Iterator<Element> itvalue = nvlel.elementIterator("Value");
                List<String> livalue = new ArrayList();
                while (itvalue.hasNext()){
                    Element value = itvalue.next();
                    livalue.add(value.getText());
                }
                nvl.setValue(livalue);
                linvl.add(nvl);
            }
            VariationSpecificsSet vss = new VariationSpecificsSet();
            vss.setNameValueList(linvl);
            vtions.setVariationSpecificsSet(vss);
            //多属性图片信息
            Pictures pic = new Pictures();
            Element elpic = vartions.element("Pictures");
            if(elpic!=null){
                pic.setVariationSpecificName(elpic.elementText("VariationSpecificName"));
                Iterator<Element> iturl = elpic.elementIterator("VariationSpecificPictureSet");
                List<VariationSpecificPictureSet> livsps = new ArrayList();
                while (iturl.hasNext()){
                    Element urle = iturl.next();
                    VariationSpecificPictureSet vsps = new VariationSpecificPictureSet();
                    vsps.setVariationSpecificValue(urle.elementText("VariationSpecificValue"));
                    Iterator<Element> url = urle.elementIterator("PictureURL");
                    List li = new ArrayList();
                    while (url.hasNext()){
                        Element e = url.next();
                        String urlstr = e.getText();
                        if(urlstr.indexOf("?")>0){
                            urlstr = urlstr.substring(0,urlstr.indexOf("?"));
                        }
                        li.add(urlstr);
                    }
                    vsps.setPictureURL(li);
                    livsps.add(vsps);
                }
                pic.setVariationSpecificPictureSet(livsps);
                vtions.setPictures(pic);
            }
            item.setVariations(vtions);
        }else{
            Element el = element.element("StartPrice");
            item.setStartPrice(new StartPrice(el.attributeValue("currencyID"),Double.parseDouble(el.getText())));
            if(element.element("BuyItNowPrice")!=null) {
                item.setBuyItNowPrice(Double.parseDouble(element.elementText("BuyItNowPrice")));
            }
            if(element.element("ReservePrice")!=null){
                item.setReservePrice(Double.parseDouble(element.elementText("ReservePrice")));
            }
        }
        return item;
    }

    /**
     * 定时从在线商品中同步数据下来，行成在线数据
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static List<TradingListingData> getItemListElememt(String xml,String ebayAccount) throws Exception {
        List li = new ArrayList();
        Document document= formatStr2Doc(xml);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        Element recommend = rootElt.element("ItemArray");
        Iterator<Element> iter = recommend.elementIterator("Item");
        String listType = "";
        while(iter.hasNext()){
            TradingListingData item = new TradingListingData();
            Element element = iter.next();
            item.setTitle(HtmlUtils.htmlEscape(element.elementText("Title")));
            item.setItemId(element.elementText("ItemID"));
            item.setSite(element.elementText("Site"));
            item.setSku(element.elementText("SKU"));
            if(element.elementText("WatchCount")!=null){
                item.setWatchcount(element.elementText("WatchCount"));
            }
            if(element.elementText("ApplicationData")!=null){
                item.setApplicationdata(element.elementText("ApplicationData"));
            }
            item.setCounty(element.elementText("Country"));
            item.setAddress(element.elementText("Location"));
            item.setEbayAccount(ebayAccount);
            if("FixedPriceItem".equals(element.elementText("ListingType"))){
                if(element.element("Variations")!=null){
                    listType = "2";
                }else{
                    listType = element.elementText("ListingType");
                }
            }else{
                listType = element.elementText("ListingType");
            }
            item.setListingType(listType);
            item.setPrice(Double.parseDouble(element.element("SellingStatus").elementText("CurrentPrice")));
            Element shippingdes = element.element("ShippingDetails");
            if(shippingdes!=null){
                List<Element> shippingit = shippingdes.elements("ShippingServiceOptions");
                if(shippingit!=null&&shippingit.size()>0){
                    Element shippingOption = shippingit.get(0);
                    if(shippingOption!=null){
                        Element option  = shippingOption.element("ShippingServiceCost");
                        if(option!=null){
                            item.setShippingPrice(Double.parseDouble(option.getText()));
                        }else{
                            item.setShippingPrice(0.00);
                        }
                    }else{
                        item.setShippingPrice(0.00);
                    }
                }else{
                    item.setShippingPrice(0.00);
                }
            }else{
                item.setShippingPrice(0.00);
            }
            item.setQuantity(Long.parseLong(element.elementText("Quantity"))-Long.parseLong(element.element("SellingStatus").elementText("QuantitySold")));
            item.setQuantitysold(Long.parseLong(element.element("SellingStatus").elementText("QuantitySold")));
            Element elflag = element.element("SellingStatus").element("ListingStatus");
            if(elflag!=null){
                if(elflag.getText().equals("Active")){
                    item.setIsFlag("0");
                }else{
                    item.setIsFlag("1");
                }
            }else{
                item.setIsFlag("1");
            }
            item.setSubtitle("");
            item.setBuyitnowprice(Double.parseDouble(element.element("ListingDetails").elementText("ConvertedBuyItNowPrice")));
            item.setReserveprice(Double.parseDouble(element.element("ListingDetails").elementText("ConvertedReservePrice")));
            item.setListingduration(element.elementText("ListingDuration"));
            item.setStarttime(DateUtils.returnDate(element.element("ListingDetails").elementText("StartTime")));
            item.setEndtime(DateUtils.returnDate(element.element("ListingDetails").elementText("EndTime")));
            String url = element.element("PictureDetails").elementText("GalleryURL");

            //item.setPicUrl(url.substring(0,url.lastIndexOf("_")+1)+"14"+url.substring(url.lastIndexOf(".")));
            item.setPicUrl("http://thumbs.ebaystatic.com/pict/"+item.getItemId()+".jpg");
            li.add(item);
        }
        return li;
    }

    /**
     * 封装在线商品列表
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static List<Item> getItemElememt(String xml) throws Exception {
        List li = new ArrayList();
        Document document= formatStr2Doc(xml);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        Element recommend = rootElt.element("ItemArray");
        Iterator<Element> iter = recommend.elementIterator("Item");
        while(iter.hasNext()){
            Item item = new Item();
            Element element = iter.next();
            Element elflag = element.element("SellingStatus").element("ListingStatus");
            if(elflag!=null){//如查商品不在线，就不取在线商品
                if(elflag.getText().equals("Active")){

                }else{
                    continue;
                }
            }
            item.setTitle(element.elementText("Title"));
            item.setCurrency(element.elementText("Currency"));
            item.setCountry(element.elementText("Country"));
            item.setSite(element.elementText("Site"));
            item.setPostalCode(element.elementText("PostalCode"));
            item.setLocation(element.elementText("Location"));
            item.setItemID(element.elementText("ItemID"));
            item.setHitCounter(element.elementText("HitCounter"));
            item.setAutoPay(element.elementText("AutoPay").equals("true") ? true : false);
            item.setGiftIcon(element.elementText("GiftIcon"));
            item.setListingDuration(element.elementText("ListingDuration"));
            item.setQuantity(Integer.parseInt(element.elementText("Quantity")));
            item.setSKU(element.elementText("SKU"));
            StartPrice sp = new StartPrice();
            sp.setValue(Double.parseDouble(element.element("SellingStatus").elementText("CurrentPrice")));
            sp.setCurrencyID(element.element("SellingStatus").element("CurrentPrice").attributeValue("currencyID"));
            item.setStartPrice(sp);
            item.setConditionID(Integer.parseInt(element.elementText("ConditionID")==null?"1000":element.elementText("ConditionID")));
            List lishipto = new ArrayList();
            Iterator<Element> shipe = element.elementIterator("ShipToLocations");
            while (shipe.hasNext()){
                Element elstr = shipe.next();
                lishipto.add(elstr.getText());
            }
            item.setShipToLocations(lishipto);
            //取得退货政策并封装
            Element returne = element.element("ReturnPolicy");
            ReturnPolicy rp = new ReturnPolicy();
            rp.setRefundOption(returne.elementText("RefundOption"));
            rp.setReturnsWithinOption(returne.elementText("ReturnsWithinOption"));
            rp.setReturnsAcceptedOption(returne.elementText("ReturnsAcceptedOption"));
            rp.setDescription(returne.elementText("Description"));
            rp.setShippingCostPaidByOption(returne.elementText("ShippingCostPaidByOption"));
            item.setReturnPolicy(rp);
            //买家要求
            BuyerRequirementDetails brd = new BuyerRequirementDetails();
            MaximumItemRequirements mirs = new MaximumItemRequirements();
            Element buyere = element.element("BuyerRequirementDetails");
            if(buyere!=null) {
                Element maxiteme = buyere.element("MaximumItemRequirements");
                if(maxiteme!=null) {
                    if(StringUtils.isNotEmpty(maxiteme.elementText("MaximumItemCount"))) {
                        mirs.setMaximumItemCount(Integer.parseInt(maxiteme.elementText("MaximumItemCount")));
                    }
                    if(StringUtils.isNotEmpty(maxiteme.elementText("MinimumFeedbackScore"))) {
                        mirs.setMinimumFeedbackScore(Integer.parseInt(maxiteme.elementText("MinimumFeedbackScore")));
                    }
                    brd.setMaximumItemRequirements(mirs);
                }

                Element maxUnpaid = buyere.element("MaximumUnpaidItemStrikesInfo");
                if(maxUnpaid!=null){
                    MaximumUnpaidItemStrikesInfo muis = new MaximumUnpaidItemStrikesInfo();
                    if(StringUtils.isNotEmpty(maxUnpaid.elementText("Count"))) {
                        muis.setCount(Integer.getInteger(maxUnpaid.elementText("Count")));
                    }
                    muis.setPeriod(maxUnpaid.elementText("Period"));
                    brd.setMaximumUnpaidItemStrikesInfo(muis);
                }

                Element maxPolicy = buyere.element("MaximumBuyerPolicyViolations");
                if(maxPolicy!=null){
                    MaximumBuyerPolicyViolations mbpv= new MaximumBuyerPolicyViolations();
                    if(StringUtils.isNotEmpty(maxPolicy.elementText("Count"))) {
                        mbpv.setCount(Integer.parseInt(maxPolicy.elementText("Count")));
                    }
                    mbpv.setPeriod(maxPolicy.elementText("Period"));
                    brd.setMaximumBuyerPolicyViolations(mbpv);
                }
                item.setBuyerRequirementDetails(brd);
            }

            li.add(item);
        }
        return li;
    }
    /**
     * 得到反馈信息列表
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static List<TradingFeedBackDetail> getFeedBackListElement(String xml) throws Exception {
        List<TradingFeedBackDetail> lifb = new ArrayList();
        Document document= formatStr2Doc(xml);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        Element recommend = rootElt.element("FeedbackDetailArray");
        if (recommend==null){logger.error("FeedbackDetailArray为空!"+xml);return null;}
        Iterator<Element> iter = recommend.elementIterator("FeedbackDetail");
        while (iter.hasNext()){
            Element element = iter.next();
            TradingFeedBackDetail tfbd = new TradingFeedBackDetail();
            tfbd.setCommentinguser(element.elementText("CommentingUser"));
            tfbd.setCommentinguserscore(Long.parseLong(element.elementText("CommentingUserScore")));
            tfbd.setCommenttext(StringEscapeUtils.escapeXml(element.element("CommentText").getStringValue()));
            tfbd.setCommenttime(DateUtils.returnDate(element.elementText("CommentTime")));
            tfbd.setCommenttype(element.elementText("CommentType"));
            tfbd.setItemid(element.elementText("ItemID"));
            tfbd.setRole(element.elementText("Role"));
            tfbd.setFeedbackid(element.elementText("FeedbackID"));
            tfbd.setTransactionid(element.elementText("TransactionID"));
            tfbd.setOrderlineitemid(element.elementText("OrderLineItemID"));
            tfbd.setItemtitle(element.elementText("ItemTitle"));
            tfbd.setCreateTime(new Date());
            if(element.elementText("ItemPrice")!=null) {
                tfbd.setItemprice(Double.parseDouble(element.elementText("ItemPrice")));
            }
            lifb.add(tfbd);
        }
        return lifb;
    }

    /**将商品目录属性api返回的xml解析为List<PublicDataDict>*/
    public static List<PublicDataDict> getListForPublicDataDict(String xml) throws Exception {
        List<PublicDataDict> publicDataDictList = new ArrayList<PublicDataDict>();
        Document document= formatStr2Doc(xml);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        Element recommend = rootElt.element("Recommendations");

        Element cad= recommend.element("CategoryID");
        String categoryID=cad.getTextTrim();

        Iterator<Element> iter = recommend.elementIterator("NameRecommendation");
        while (iter.hasNext()){

            Element element = iter.next();
            String itemId=element.element("Name").getText();
            Iterator<Element> valueIter = element.elementIterator("ValueRecommendation");
            while (valueIter.hasNext()){
                Element element1 = valueIter.next();
                PublicDataDict publicDataDict = new PublicDataDict();
                publicDataDict.setItemId(itemId);
                publicDataDict.setItemParentId(categoryID);
                String val=element1.element("Value").getText();
                publicDataDict.setItemEnName(val);
                publicDataDict.setItemType(DictCollectionsUtil.categorySpecifics);
                publicDataDictList.add(publicDataDict);
            }
        }
        if(publicDataDictList.isEmpty()){
            PublicDataDict publicDataDict = new PublicDataDict();
            publicDataDict.setItemId(DictCollectionsUtil.categorySpecifics+categoryID);
            publicDataDict.setItemParentId(categoryID);
            publicDataDict.setItemEnName("noval");
            publicDataDict.setItemType(DictCollectionsUtil.categorySpecifics);
            publicDataDictList.add(publicDataDict);
        }
       return publicDataDictList;
    }

    /**
     * 获取根需要解析的元素
     */
    public static Element getApiElement(String xml,String nodeName) throws  Exception{
        Document document= formatStr2Doc(xml);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        Element root=  rootElt.element(nodeName);
        return root;
    }
    /**
     * 获取指定元素下面的某层指定元素内容
     */
    public static String getSpecifyElementText(Element root,String... firstElement) throws  Exception{
        String text=null;
        String[] nodes=firstElement;
        int length=nodes.length;
        for(int i=0;i<length;i++){
            String node=nodes[i];
            if(i==(length-1)){
                if(root==null){
                    return null;
                }
                Element last=root.element(node);
                if(last!=null){
                    text=last.getTextTrim();
                }
            }else{
                if(root==null){
                    return null;
                }
                root=root.element(node);

            }
        }
        return text;
    }

    /**
     * 取得运输选项基础数据
     * @param res
     * @return
     * @throws Exception
     */
    public static List<TradingDataDictionary> getShippingSelect(String res) throws Exception{
        Document document= formatStr2Doc(res);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        List<TradingDataDictionary> litdd = new ArrayList<>();
        if(rootElt!=null){
            Iterator<Element> itshipping = rootElt.elementIterator("ShippingServiceDetails");
            if(itshipping!=null){
                while (itshipping.hasNext()){
                    Element el = itshipping.next();
                    TradingDataDictionary tdd = new TradingDataDictionary();
                    if(el.elementText("InternationalService")==null){//国内运输
                        tdd.setType("domestic transportation");
                    }else{//国际运输
                        tdd.setType("International transport");
                    }
                    if(el.elementText("ShippingTimeMax")!=null){
                        tdd.setTimermax(el.elementText("ShippingTimeMax"));
                    }
                    if(el.elementText("ShippingTimeMin")!=null){
                        tdd.setTimermin(el.elementText("ShippingTimeMin"));
                    }
                    tdd.setValue(el.elementText("ShippingService"));
                    tdd.setName(el.elementText("Description"));
                    litdd.add(tdd);
                }
            }
        }
        return litdd;
    }

    /**直接根据nodes来获取消息*/
    public static String getSpecifyElementTextAllInOne(String res,String... nodes) throws Exception {
        Document document= formatStr2Doc(res);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        return getSpecifyElementText(rootElt,nodes);
    }

    /**
     * 通过Title 查询是相似分类信息
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static List<TradingReseCategory> selectCategoryByKey(String xml,TradingDataDictionary tdd) throws Exception {
        PublicDataDictMapper publicDataDictMapper = (PublicDataDictMapper) ApplicationContextUtil.getBean(PublicDataDictMapper.class);
        ITradingCateGoryTask iTradingCateGoryTask = (ITradingCateGoryTask) ApplicationContextUtil.getBean(ITradingCateGoryTask.class);
        List<TradingReseCategory> litrc = new ArrayList();
        //商品分类目录查询
        Document document= formatStr2Doc(xml);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        if(rootElt==null){
            return litrc;
        }
        Element recommend = rootElt.element("SuggestedCategoryArray");
        if(recommend==null){
            return litrc;
        }
        Iterator<Element> ite = recommend.elementIterator("SuggestedCategory");
        while (ite.hasNext()){
            Element ele = ite.next();
            Element cate = ele.element("Category");
            Element PercentItemFound = ele.element("PercentItemFound");
            TradingReseCategory trc = new TradingReseCategory();
            trc.setId(Long.parseLong(cate.elementText("CategoryID")));
            trc.setCategoryId(cate.elementText("CategoryID"));
            PublicDataDictExample pdde = new PublicDataDictExample();
            pdde.createCriteria().andItemIdEqualTo(trc.getCategoryId()).andItemTypeEqualTo("category").andSiteIdEqualTo(tdd.getId()+"");
            List<PublicDataDict> lipdd = publicDataDictMapper.selectByExample(pdde);
            List<Element> lie = cate.elements("CategoryParentID");
            String mainId = "";
            if(lie!=null&&lie.size()>0){
                mainId = lie.get(0).getText();
            }
            if(lipdd==null|| lipdd.size()==0){
                TradingCategoryTask tcll = iTradingCateGoryTask.selectByCateIdSiteId(trc.getCategoryId(),tdd.getId()+"");
                if(tcll==null) {
                    TradingCategoryTask tck = new TradingCategoryTask();
                    tck.setCateId(trc.getCategoryId());
                    tck.setSiteId(tdd.getName1());
                    tck.setSiteSoureId(tdd.getId());
                    tck.setMainId(mainId);
                    iTradingCateGoryTask.saveTradingCategoryTask(tck);
                }
            }
            DataDictionarySupport.getPublicDataDictionaryByItemIDs(trc.getCategoryId(),"");
            Iterator<Element> ites = cate.elementIterator("CategoryParentName");

            String cateName = "";
            while (ites.hasNext()){
                Element ent = ites.next();
                cateName=cateName+ent.getText()+":";
            }
            trc.setCategoryKey(PercentItemFound.getText()+"%");
            trc.setCategoryName(cateName+cate.elementText("CategoryName"));
            litrc.add(trc);
        }
        return litrc;
    }
    /**
     * 通过Title 商品所属分类查询
     * @param xml
     * @param key
     * @return
     * @throws DocumentException
     */
    public static List<TradingReseCategory> selectCategoryBytitle(String xml,String key) throws Exception {
        List<TradingReseCategory> litrc = new ArrayList();
        //之前是做商品所属分类查询
        Document document= formatStr2Doc(xml);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        Element recommend = rootElt.element("searchResult");
        Iterator<Element> iter = recommend.elementIterator("item");
        while (iter.hasNext()){
            Element ele=iter.next();
            Element elecate = ele.element("primaryCategory");
            TradingReseCategory trc = new TradingReseCategory();
            trc.setId(Long.parseLong(elecate.elementText("categoryId")));
            trc.setCategoryId(elecate.elementText("categoryId"));
            trc.setCategoryName(StringEscapeUtils.escapeHtml(elecate.elementText("categoryName")));
            trc.setCategoryKey(key);
            litrc.add(trc);
        }

        return litrc;
    }

    /**
     * 组装运输方式
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static List<TradingDataDictionary> selectShippingService(String xml) throws Exception {
        List<TradingDataDictionary> lidata = new ArrayList();
        Document document= formatStr2Doc(xml);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        Iterator<Element> ies = rootElt.elementIterator("ShippingServiceDetails");
        while (ies.hasNext()){
            Element element = ies.next();
            TradingDataDictionary tdd = new TradingDataDictionary();
            tdd.setType("domestic transportation");
            tdd.setValue(element.elementText("ShippingService"));
            tdd.setName(element.elementText("Description"));
            tdd.setName1(element.elementText("ShippingCategory"));
            lidata.add(tdd);
        }
        return lidata;
    }

    /**
     * 得到分类
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static List<PublicDataDict> selectPublicDataDict(String xml) throws Exception {
        List<PublicDataDict> lidata = new ArrayList();
        Document document= formatStr2Doc(xml);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        Element el = rootElt.element("CategoryArray");
        Iterator<Element> ies = el.elementIterator("Category");
        while (ies.hasNext()){
            Element ment = ies.next();
            PublicDataDict pdd = new PublicDataDict();
            pdd.setItemEnName(StringEscapeUtils.escapeXml(ment.elementText("CategoryName")) );
            pdd.setItemId(ment.elementText("CategoryID"));
            pdd.setItemLevel(ment.elementText("CategoryLevel"));
            pdd.setItemParentId(ment.elementText("CategoryParentID"));
            pdd.setItemType("category");
            lidata.add(pdd);
        }
        return lidata;
    }

    /**上传图片的api*/
    public static String uploadEbayImage(UsercontrollerEbayAccount ua,String url,String picName){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<UploadSiteHostedPicturesRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "  <RequesterCredentials>\n" +
                "    <eBayAuthToken>"+ua.getEbayToken()+"</eBayAuthToken>\n" +
                "  </RequesterCredentials>\n" +
                "  <WarningLevel>High</WarningLevel>\n" +
                "  <ExternalPictureURL>"+url+"</ExternalPictureURL>\n" +
                "  <PictureName>"+picName+"</PictureName><ErrorLanguage>zh_HK</ErrorLanguage>\n" +
                "</UploadSiteHostedPicturesRequest>";
        return xml;
    }
    //获取错误代码
    public static String getErrorCode(String res) throws Exception {
        Document document= formatStr2Doc(res);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        String errors = SamplePaseXml.getSpecifyElementText(rootElt, "Errors", "ErrorCode");
        return errors;
    }
    public static String getWarningInformation(String res) throws Exception {
        Document document= formatStr2Doc(res);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        String errors = SamplePaseXml.getSpecifyElementText(rootElt,"Errors","LongMessage");
        return errors;
    }
    public static String getCaseWarningInformation(String res) throws Exception {
        Document document= formatStr2Doc(res);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        String errors = SamplePaseXml.getSpecifyElementText(rootElt,"errorMessage","error","message");
        return errors;
    }
    public static String getCaseerrorId(String res) throws Exception {
        Document document= formatStr2Doc(res);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        String errors = SamplePaseXml.getSpecifyElementText(rootElt,"errorMessage","error","errorId");
        return errors;
    }

    public static List<TradingPriceTracking> getPriceTrackingItem(String res,String title) throws Exception {
        List<TradingPriceTracking> list=new ArrayList<TradingPriceTracking>();
        Document document= formatStr2Doc(res);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        Element searchResult=rootElt.element("searchResult");
        Iterator items=searchResult.elementIterator("item");
        while(items.hasNext()){
            TradingPriceTracking priceTracking=new TradingPriceTracking();
            Element item= (Element) items.next();
            priceTracking.setItemid(SamplePaseXml.getSpecifyElementText(item, "itemId"));
            priceTracking.setCategoryid(SamplePaseXml.getSpecifyElementText(item, "primaryCategory", "categoryId"));
            priceTracking.setCategoryname(SamplePaseXml.getSpecifyElementText(item, "primaryCategory", "categoryName"));
            priceTracking.setCurrentprice(SamplePaseXml.getSpecifyElementText(item, "sellingStatus", "currentPrice"));
            priceTracking.setSellerusername(SamplePaseXml.getSpecifyElementText(item, "sellerInfo", "sellerUserName"));
            priceTracking.setTitle(SamplePaseXml.getSpecifyElementText(item, "title"));
            priceTracking.setBidcount(SamplePaseXml.getSpecifyElementText(item, "sellingStatus", "bidCount"));
            priceTracking.setPictureurl(SamplePaseXml.getSpecifyElementText(item,"galleryURL"));
            String starttime=SamplePaseXml.getSpecifyElementText(item,"listingInfo","startTime");
            String endtime=SamplePaseXml.getSpecifyElementText(item,"listingInfo","endTime");
            if(StringUtils.isNotBlank(starttime)){
                priceTracking.setStarttime(DateUtils.returnDate(starttime));
            }
            if(StringUtils.isNotBlank(endtime)){
                priceTracking.setEndtime(DateUtils.returnDate(endtime));
            }
            Element sellingStatus=item.element("sellingStatus");
            String currencyId1="";
            if(sellingStatus!=null){
                Element currentPrice=sellingStatus.element("currentPrice");
                if(currentPrice!=null){
                    Attribute currencyId=currentPrice.attribute("currencyId");
                    if(currencyId!=null){
                        currencyId1=currencyId.getValue();
                    }
                }
            }
            priceTracking.setCurrencyid(currencyId1);
            Element shippingInfo=item.element("shippingInfo");
            if(shippingInfo!=null){
                Element shippingServiceCost=shippingInfo.element("shippingServiceCost");
                if(shippingServiceCost!=null){
                    Attribute shippingcurrencyId=shippingServiceCost.attribute("currencyId");
                    if(shippingcurrencyId!=null){
                        priceTracking.setShippingcurrencyid(shippingcurrencyId.getValue());
                    }
                    priceTracking.setShippingservicecost(shippingServiceCost.getTextTrim());
                }
            }
            priceTracking.setQuerytitle(title);
            list.add(priceTracking);
        }
        return list;
    }
    //解析价格跟踪
    public static List<TradingPriceTracking> getPriceTrackingItemByItemId(String res) throws Exception {
        List<TradingPriceTracking> priceTrackings=new ArrayList<TradingPriceTracking>();
        Document document= formatStr2Doc(res);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        Iterator items=rootElt.elementIterator("Item");
        while(items.hasNext()){
            TradingPriceTracking priceTracking=new TradingPriceTracking();
            Element item= (Element) items.next();
            priceTracking.setItemid(SamplePaseXml.getSpecifyElementText(item,"ItemID"));
            priceTracking.setTitle(SamplePaseXml.getSpecifyElementText(item, "Title"));
            priceTracking.setCurrentprice(SamplePaseXml.getSpecifyElementText(item, "ConvertedCurrentPrice"));
            priceTracking.setBidcount(SamplePaseXml.getSpecifyElementText(item, "BidCount"));
            Element ConvertedCurrentPrice=item.element("ConvertedCurrentPrice");
            String endtime=SamplePaseXml.getSpecifyElementText(item,"EndTime");
            if(StringUtils.isNotBlank(endtime)){
                priceTracking.setEndtime(DateUtils.returnDate(endtime));
            }
            String currencyId1="";
            if(ConvertedCurrentPrice!=null){
                Attribute currencyId=ConvertedCurrentPrice.attribute("currencyID");
                if(currencyId!=null){
                    currencyId1=currencyId.getValue();
                }
            }
            priceTracking.setCurrencyid(currencyId1);
            priceTrackings.add(priceTracking);
        }
        return priceTrackings;
    }

    /**当errors有很多个时，提取Error错误信息*/
    public static String getErrorsWhenMor(String xml){
        StringBuffer sb=new StringBuffer();
        List<String> es= MyStringUtil.subStringsBetween(xml, "<Errors>", "</Errors>");
        if (es==null){return "";}
        for (String e:es){
            if (StringUtils.contains(e,"<SeverityCode>Error</SeverityCode>")){
                sb.append(e).append("<br/>");
            }
        }
        return StringEscapeUtils.unescapeHtml(sb.toString());
    }
    /**当errors有很多个时，提取Error错误信息*/
    public static List<String> getErrorsAndWarningWhenMor(String xml){
        StringBuffer sbErr=new StringBuffer();
        StringBuffer sbWar=new StringBuffer();
        List<String> s=new ArrayList<>();//0为err,1为war
        List<String> es= MyStringUtil.subStringsBetween(xml, "<Errors>", "</Errors>");
        if (es==null){return null;}
        for (String e:es){
            if (StringUtils.contains(e,"<SeverityCode>Error</SeverityCode>")){
                sbErr.append(e).append("<br/>");
            }
            if (StringUtils.contains(e,"<SeverityCode>Warning</SeverityCode>")){
                sbWar.append(e).append("<br/>");
            }
        }

        String s0=StringEscapeUtils.unescapeHtml(sbErr.toString());
        String s1=StringEscapeUtils.unescapeHtml(sbWar.toString());

        s.add(0,StringUtils.isBlank(s0)?" ":s0);
        s.add(1,StringUtils.isBlank(s1)?" ":s1);
        return s;
    }

    /**
     * 组装运输到的地方
     * @param xml
     * @param parentid
     * @return
     * @throws Exception
     */
    public static List<TradingDataDictionary> saveShipptingtoCounty(String xml,long parentid) throws Exception {
        List<TradingDataDictionary> lidata = new ArrayList();
        Document document= formatStr2Doc(xml);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        Iterator<Element> ies = rootElt.elementIterator("ShippingLocationDetails");
        while (ies.hasNext()){
            Element element = ies.next();
            TradingDataDictionary tdd = new TradingDataDictionary();
            tdd.setType("ShippingToAddress");
            tdd.setValue(element.elementText("ShippingLocation"));
            tdd.setName(element.elementText("Description"));
            tdd.setParentId(parentid);
            lidata.add(tdd);
        }
        return lidata;
    }

    /**
     * 保存退货政策
     * @param xml
     * @param parentid
     * @return
     * @throws Exception
     */
    public static List<TradingDataDictionary> saveReturnPolicy(String xml,long parentid) throws Exception {
        List<TradingDataDictionary> lidata = new ArrayList();
        Document document= formatStr2Doc(xml);
        if (document==null){return null;}
        Element rootElt = document.getRootElement();
        Element returnp = rootElt.element("ReturnPolicyDetails");
        Iterator<Element> eRefund = returnp.elementIterator("Refund");
        if(eRefund!=null){
            while (eRefund.hasNext()){
                Element element = eRefund.next();
                TradingDataDictionary tdd = new TradingDataDictionary();
                tdd.setType("RefundOption");
                tdd.setValue(element.elementText("RefundOption"));
                tdd.setName(StringEscapeUtils.unescapeXml(element.elementText("Description")));
                tdd.setParentId(parentid);
                lidata.add(tdd);
            }
        }
        Iterator<Element> eReturnsWithin = returnp.elementIterator("ReturnsWithin");
        if(eReturnsWithin!=null){
            while (eReturnsWithin.hasNext()){
                Element element = eReturnsWithin.next();
                TradingDataDictionary tdd = new TradingDataDictionary();
                tdd.setType("ReturnsWithinOption");
                tdd.setValue(element.elementText("ReturnsWithinOption"));
                tdd.setName(StringEscapeUtils.unescapeXml(element.elementText("Description")));
                tdd.setParentId(parentid);
                lidata.add(tdd);
            }
        }

        Iterator<Element> eReturnsAccepted = returnp.elementIterator("ReturnsAccepted");
        if(eReturnsAccepted!=null){
            while (eReturnsAccepted.hasNext()){
                Element element = eReturnsAccepted.next();
                TradingDataDictionary tdd = new TradingDataDictionary();
                tdd.setType("ReturnsAcceptedOption");
                tdd.setValue(element.elementText("ReturnsAcceptedOption"));
                tdd.setName(StringEscapeUtils.unescapeXml(element.elementText("Description")));
                tdd.setParentId(parentid);
                lidata.add(tdd);
            }
        }

        Iterator<Element> eShippingCostPaidBy = returnp.elementIterator("ShippingCostPaidBy");
        if(eShippingCostPaidBy!=null){
            while (eShippingCostPaidBy.hasNext()){
                Element element = eShippingCostPaidBy.next();
                TradingDataDictionary tdd = new TradingDataDictionary();
                tdd.setType("ShippingCostPaidByOption");
                tdd.setValue(element.elementText("ShippingCostPaidByOption"));
                tdd.setName(StringEscapeUtils.unescapeXml(element.elementText("Description")));
                tdd.setParentId(parentid);
                lidata.add(tdd);
            }
        }

        Iterator<Element> eRestockingFeeValue = returnp.elementIterator("RestockingFeeValue");
        if(eRestockingFeeValue!=null){
            while (eRestockingFeeValue.hasNext()){
                Element element = eRestockingFeeValue.next();
                TradingDataDictionary tdd = new TradingDataDictionary();
                tdd.setType("RestockingFeeValueOption");
                tdd.setValue(element.elementText("RestockingFeeValueOption"));
                tdd.setName(StringEscapeUtils.unescapeXml(element.elementText("Description")));
                tdd.setParentId(parentid);
                lidata.add(tdd);
            }
        }

        return lidata;
    }

    public static void getPro(String xml,TradingItemPromotionalSaleRule tis) throws Exception{
        ITradingItemPromotionalSaleRule iTradingItemPromotionalSaleRule = (ITradingItemPromotionalSaleRule) ApplicationContextUtil.getBean(ITradingItemPromotionalSaleRule.class);
        ITradingItemPromotionalSaleSet iTradingItemPromotionalSaleSet = (ITradingItemPromotionalSaleSet) ApplicationContextUtil.getBean(ITradingItemPromotionalSaleSet.class);
        Document document= SamplePaseXml.formatStr2Doc(xml);
        if (document==null){return;}
        Element rootElts = document.getRootElement();
        Element elements = rootElts.element("PromotionalSaleDetails");
        Iterator<Element> pro = elements.elementIterator("PromotionalSale");
        while (pro.hasNext()){
            Element elpro = pro.next();
            tis.setStatus(elpro.elementText("Status"));
            tis.setPromotionalsaleName(elpro.elementText("PromotionalSaleName"));
            tis.setDiscounttype(elpro.elementText("DiscountType"));
            tis.setDiscountvalue(Double.parseDouble(elpro.elementText("DiscountValue")));
            tis.setPromotionalsalestarttime(DateUtils.returnDate(elpro.elementText("PromotionalSaleStartTime")));
            tis.setPromotionalsaleendtime(DateUtils.returnDate(elpro.elementText("PromotionalSaleEndTime")));
            tis.setUpdateTime(new Date());
            iTradingItemPromotionalSaleRule.saveRule(tis);
            iTradingItemPromotionalSaleSet.delByRuleIdAll(tis.getId());
            if(elpro.element("PromotionalSaleItemIDArray")!=null){
                if(elpro.element("PromotionalSaleItemIDArray").elementIterator("ItemID")!=null){
                    Iterator<Element> liItem = elpro.element("PromotionalSaleItemIDArray").elementIterator("ItemID");
                    while (liItem.hasNext()){
                        Element item = liItem.next();
                        TradingItemPromotionalSaleSet tiss= iTradingItemPromotionalSaleSet.selectByItemId(item.getText());
                        if(tiss==null){
                            tiss = new TradingItemPromotionalSaleSet();
                            tiss.setCheckFlag("0");
                            tiss.setEbayStatus("1");
                            tiss.setRuleId(tis.getId());
                            tiss.setItemId(item.getText());
                            tiss.setCreateUser(tis.getCreateUser());
                            tiss.setCreateTime(new Date());
                            iTradingItemPromotionalSaleSet.saveSet(tiss);
                        }
                    }
                }
            }
        }
    }

    public static void setStoreCategory(String xml,long ebayAccountId) throws Exception {
        Document document = SamplePaseXml.formatStr2Doc(xml);
        if (document == null) {
            return;
        }
        Element rootElts = document.getRootElement();
        Element elements = rootElts.element("Store");
        if(elements!=null){
            Element cateroot = elements.element("CustomCategories");
            if(cateroot!=null){
                Iterator<Element> itecateArray = cateroot.elementIterator("CustomCategory");
                if(itecateArray!=null){
                    while (itecateArray.hasNext()){
                        Element cate1 = itecateArray.next();
                        ITradingStoreCategory iTradingStoreCategory = (ITradingStoreCategory) ApplicationContextUtil.getBean(ITradingStoreCategory.class);
                        TradingStoreCategory tsc1 = iTradingStoreCategory.selectByCategoryId(ebayAccountId,cate1.elementText("CategoryID"));
                        if(tsc1==null){
                            tsc1= new TradingStoreCategory();
                            tsc1.setCreateDate(new Date());
                            tsc1.setSiteId("0");
                        }
                        tsc1.setStoreCategoryName(cate1.elementText("Name"));
                        tsc1.setStoreOrder(Integer.parseInt(cate1.elementText("Order")));
                        tsc1.setParentId(-1L);
                        tsc1.setStoreCategoryParentId("-1");
                        tsc1.setStoreCategoryId(cate1.elementText("CategoryID"));
                        tsc1.setEbayAccountId(ebayAccountId);
                        tsc1.setUpdateDate(new Date());
                        iTradingStoreCategory.saveTradingStoreCategory(tsc1);
                        Iterator<Element> iterator2 = cate1.elementIterator("ChildCategory");
                        if(iterator2!=null){
                            while (iterator2.hasNext()){
                                Element cate2 = iterator2.next();
                                TradingStoreCategory tsc2 = iTradingStoreCategory.selectByCategoryId(ebayAccountId,cate2.elementText("CategoryID"));
                                if(tsc2==null){
                                    tsc2= new TradingStoreCategory();
                                    tsc2.setCreateDate(new Date());
                                    tsc2.setSiteId("0");
                                }
                                tsc2.setStoreCategoryName(cate2.elementText("Name"));
                                tsc2.setStoreOrder(Integer.parseInt(cate2.elementText("Order")));
                                tsc2.setStoreCategoryId(cate2.elementText("CategoryID"));
                                tsc2.setParentId(tsc1.getId());
                                tsc2.setStoreCategoryParentId(tsc1.getStoreCategoryId());
                                tsc2.setEbayAccountId(ebayAccountId);
                                tsc2.setUpdateDate(new Date());
                                iTradingStoreCategory.saveTradingStoreCategory(tsc2);
                                Iterator<Element> iterator3 = cate2.elementIterator("ChildCategory");
                                if(iterator3!=null){
                                    while (iterator3.hasNext()){
                                        Element cate3 = iterator3.next();
                                        TradingStoreCategory tsc3 = iTradingStoreCategory.selectByCategoryId(ebayAccountId,cate3.elementText("CategoryID"));
                                        if(tsc3==null){
                                            tsc3= new TradingStoreCategory();
                                            tsc3.setCreateDate(new Date());
                                            tsc3.setSiteId("0");
                                        }
                                        tsc3.setStoreCategoryName(cate3.elementText("Name"));
                                        tsc3.setStoreOrder(Integer.parseInt(cate3.elementText("Order")));
                                        tsc3.setStoreCategoryId(cate3.elementText("CategoryID"));
                                        tsc3.setParentId(tsc2.getId());
                                        tsc3.setEbayAccountId(ebayAccountId);
                                        tsc3.setStoreCategoryParentId(tsc2.getStoreCategoryId());
                                        tsc3.setUpdateDate(new Date());
                                        iTradingStoreCategory.saveTradingStoreCategory(tsc3);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
