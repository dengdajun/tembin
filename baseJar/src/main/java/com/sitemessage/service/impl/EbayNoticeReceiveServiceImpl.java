package com.sitemessage.service.impl;

import com.base.database.trading.model.*;
import com.base.sampleapixml.GetMyMessageAPI;
import com.base.sampleapixml.GetOrderItemAPI;
import com.base.sampleapixml.GetOrdersAPI;
import com.base.userinfo.service.SystemUserManagerService;
import com.base.utils.common.DateUtils;
import com.trading.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/2/5.
 * 处理ebay各种类型的通知
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class EbayNoticeReceiveServiceImpl implements com.sitemessage.service.EbayNoticeReceiveService {
    static Logger logger = Logger.getLogger(EbayNoticeReceiveServiceImpl.class);
    @Autowired
    private ITradingMessageGetmymessage iTradingMessageGetmymessage;
    @Autowired
    private SystemUserManagerService systemUserManagerService;
    @Autowired
    private IUsercontrollerEbayAccount iUsercontrollerEbayAccount;
    @Autowired
    private ITradingOrderGetOrders iTradingOrderGetOrders;
    @Autowired
    private ITradingOrderGetItem iTradingOrderGetItem;
    @Autowired
    private ITradingOrderSeller iTradingOrderSeller;
    @Autowired
    private ITradingOrderShippingDetails iTradingOrderShippingDetails;
    @Autowired
    private ITradingOrderCalculatedShippingRate iTradingOrderCalculatedShippingRate;
    @Autowired
    private ITradingOrderSellerInformation iTradingOrderSellerInformation;
    @Autowired
    private ITradingOrderSellingStatus iTradingOrderSellingStatus;
    @Autowired
    private ITradingOrderReturnpolicy iTradingOrderReturnpolicy;
    @Autowired
    private ITradingOrderShippingServiceOptions iTradingOrderShippingServiceOptions;
    @Autowired
    private ITradingOrderItemSpecifics iTradingOrderItemSpecifics;
    @Autowired
    private ITradingOrderVariation iTradingOrderVariation;
    @Autowired
    private ITradingOrderVariationSpecifics iTradingOrderVariationSpecifics;
    @Autowired
    private ITradingOrderPictures iTradingOrderPictures;
    @Autowired
    private ITradingOrderListingDetails iTradingOrderListingDetails;
    @Autowired
    private ITradingOrderPictureDetails iTradingOrderPictureDetails;
    @Override
    /**处理MyMessagesM2MMessage消息接收*/
    public void processMyMessagesM2MMessage(String xml) throws Exception {
            List<Element> messages = GetMyMessageAPI.getMessagesByGetNotifi(xml);
            if(messages!=null&&messages.size()>0){
                for (Element message : messages) {
                    TradingMessageGetmymessage ms = GetMyMessageAPI.addDatabaseByGetNotifi(message);//保存到数据库
                    String sendtoname=ms.getSendtoname();
                    String sender=ms.getSender();
                    String recipientuserid=ms.getRecipientuserid();
                    String folder=ms.getFolderid();
                    UsercontrollerEbayAccount ebayAccount=new UsercontrollerEbayAccount();
                    if("0".equals(folder)){
                        if(StringUtils.isNotBlank(sendtoname)){
                            ebayAccount=iUsercontrollerEbayAccount.selectByEbayAccount(sendtoname);
                            if (ebayAccount==null){
                                logger.error("ebayAccount不能为空！sendtoname:"+sendtoname);
                            }
                        }else{
                            ebayAccount=iUsercontrollerEbayAccount.selectByEbayAccount(recipientuserid);
                            if (ebayAccount==null){
                                logger.error("ebayAccount不能为空!recipientuserid:"+recipientuserid);
                            }
                        }
                    }else if("1".equals(folder)){
                        ebayAccount=iUsercontrollerEbayAccount.selectByEbayAccount(sender);
                        if (ebayAccount==null){
                            logger.error("folder=1;ebayAccount不能为空!recipientuserid:"+recipientuserid);
                        }
                    }
                    if (ebayAccount==null){return;}

                    List<TradingMessageGetmymessage> getmymessages = iTradingMessageGetmymessage.selectMessageGetmymessageByMessageId(ms.getMessageid(), ebayAccount.getId());

                    if(ebayAccount!=null){
                        ms.setCreateUser(ebayAccount.getUserId());
                        ms.setLoginAccountId(ebayAccount.getUserId());
                        ms.setEbayAccountId(ebayAccount.getId());
                    }
                    if (getmymessages.size() > 0) {
                        ms.setId(getmymessages.get(0).getId());
                        if ("true".equals(ms.getReplied())) {
                            ms.setRead("true");
                        } else {
                            ms.setRead(getmymessages.get(0).getRead());
                        }
                    }
                    ms.setUpdatetime(new Date());
                    iTradingMessageGetmymessage.saveMessageGetmymessage(ms);
                }
            }
        //logger.error("====接收到一条消息====");
    }
    public void processFixedPriceTransaction(String xml) throws Exception{
        Map<String,Object> mapOrder = GetOrdersAPI.parseXMLAndSave1(xml);
        List<TradingOrderGetOrders> orders= (List<TradingOrderGetOrders>) mapOrder.get("OrderList");
        for(TradingOrderGetOrders order:orders){
            UsercontrollerEbayAccount ebayAccount=iUsercontrollerEbayAccount.selectByEbayAccount(order.getSelleruserid());
            if(ebayAccount!=null){
                List<TradingOrderGetOrders> lists = iTradingOrderGetOrders.selectOrderGetOrdersByTransactionId(order.getTransactionid(),ebayAccount.getId(),order.getItemid());
                if(lists==null||lists.size()==0){
                    order.setEbayId(ebayAccount.getId());
                    order.setCreateUser(ebayAccount.getUserId());
                    iTradingOrderGetOrders.saveOrderGetOrders(order);
                }
            }
        }
    }
    public void processItemSold(String xml) throws Exception{
        Map<String, Object> items = GetOrderItemAPI.parseXMLAndSave1(xml);
        TradingOrderGetItem item = (TradingOrderGetItem) items.get(GetOrderItemAPI.ORDER_ITEM);
        List<TradingOrderGetItem> itemList = iTradingOrderGetItem.selectOrderGetItemByItemId(item.getItemid());
        if(itemList==null||itemList.size()==0){
            logger.error("processItemSold:itemid:"+item.getItemid()+",时间:"+ DateUtils.DateToString2(new Date()));
            TradingOrderListingDetails listingDetails = (TradingOrderListingDetails) items.get(GetOrderItemAPI.LISTING_DETAILS);
            TradingOrderSeller orderSeller = (TradingOrderSeller) items.get(GetOrderItemAPI.ORDER_SELLER);
            TradingOrderSellingStatus sellingStatus = (TradingOrderSellingStatus) items.get(GetOrderItemAPI.SELLING_STATUS);
            TradingOrderShippingDetails shippingDetails = (TradingOrderShippingDetails) items.get(GetOrderItemAPI.SHIPPING_DETAILS);
            TradingOrderPictureDetails pictureDetails = (TradingOrderPictureDetails) items.get(GetOrderItemAPI.PICTURE_DETAILS);
            TradingOrderReturnpolicy returnpolicy = (TradingOrderReturnpolicy) items.get(GetOrderItemAPI.RETURN_POLICY);
            TradingOrderSellerInformation sellerInformation = (TradingOrderSellerInformation) items.get(GetOrderItemAPI.SELLER_INFORMATION);
            TradingOrderCalculatedShippingRate shippingRate = (TradingOrderCalculatedShippingRate) items.get(GetOrderItemAPI.SHIPPING_RATE);
            List<TradingOrderShippingServiceOptions> serviceOptionses = (List<TradingOrderShippingServiceOptions>) items.get(GetOrderItemAPI.SERVICE_OPTIONS);
            UsercontrollerEbayAccount ebayAccount=iUsercontrollerEbayAccount.selectByEbayAccount(String.valueOf(orderSeller.getUserid()));
            if(ebayAccount!=null){
                shippingRate.setCreateUser(ebayAccount.getUserId());
                shippingDetails.setCreateUser(ebayAccount.getUserId());
                sellerInformation.setCreateUser(ebayAccount.getUserId());
                listingDetails.setCreateUser(ebayAccount.getUserId());
                orderSeller.setCreateUser(ebayAccount.getUserId());
                sellingStatus.setCreateUser(ebayAccount.getUserId());
                pictureDetails.setCreateUser(ebayAccount.getUserId());
                returnpolicy.setCreateUser(ebayAccount.getUserId());
                if (itemList != null && itemList.size() > 0) {
                    item.setId(itemList.get(0).getId());
                    listingDetails.setId(itemList.get(0).getListingdetailsId());
                    orderSeller.setId(itemList.get(0).getSellerId());
                    sellingStatus.setId(itemList.get(0).getSellingstatusId());
                    shippingDetails.setId(itemList.get(0).getShippingdetailsId());
                    pictureDetails.setId(itemList.get(0).getPicturedetailsId());
                    returnpolicy.setId(itemList.get(0).getOrderreturnpolicyId());
                    List<TradingOrderSeller> sellerList = iTradingOrderSeller.selectOrderGetItemById(orderSeller.getId());
                    if (sellerList != null && sellerList.size() > 0) {
                        sellerInformation.setId(sellerList.get(0).getSellerinfoId());
                    }
                    List<TradingOrderShippingDetails> shippingDetailsList = iTradingOrderShippingDetails.selectOrderGetItemById(shippingDetails.getId());
                    if (shippingDetailsList != null && shippingDetailsList.size() > 0) {
                        shippingRate.setId(shippingDetailsList.get(0).getCalculatedshippingrateId());
                    }
                    iTradingOrderCalculatedShippingRate.saveOrderCalculatedShippingRate(shippingRate);
                    shippingDetails.setCalculatedshippingrateId(shippingRate.getId());
                    iTradingOrderShippingDetails.saveOrderShippingDetails(shippingDetails);
                    iTradingOrderSellerInformation.saveOrderSellerInformation(sellerInformation);
                    iTradingOrderListingDetails.saveOrderListingDetails(listingDetails);
                    orderSeller.setSellerinfoId(sellerInformation.getId());
                    iTradingOrderSeller.saveOrderSeller(orderSeller);
                    iTradingOrderSellingStatus.saveOrderSellingStatus(sellingStatus);
                    iTradingOrderPictureDetails.saveOrderPictureDetails(pictureDetails);
                    iTradingOrderReturnpolicy.saveOrderReturnpolicy(returnpolicy);

                } else {
                    iTradingOrderCalculatedShippingRate.saveOrderCalculatedShippingRate(shippingRate);
                    iTradingOrderSellerInformation.saveOrderSellerInformation(sellerInformation);
                    iTradingOrderListingDetails.saveOrderListingDetails(listingDetails);
                    orderSeller.setSellerinfoId(sellerInformation.getId());
                    iTradingOrderSeller.saveOrderSeller(orderSeller);
                    iTradingOrderSellingStatus.saveOrderSellingStatus(sellingStatus);
                    shippingDetails.setCalculatedshippingrateId(shippingRate.getId());
                    iTradingOrderShippingDetails.saveOrderShippingDetails(shippingDetails);
                    iTradingOrderPictureDetails.saveOrderPictureDetails(pictureDetails);
                    iTradingOrderReturnpolicy.saveOrderReturnpolicy(returnpolicy);
                    item.setListingdetailsId(listingDetails.getId());
                    item.setSellerId(orderSeller.getId());
                    item.setSellingstatusId(sellingStatus.getId());
                    item.setShippingdetailsId(shippingDetails.getId());
                    item.setPicturedetailsId(pictureDetails.getId());
                    item.setOrderreturnpolicyId(returnpolicy.getId());
                }
                List<TradingOrderShippingServiceOptions> shippingServiceOptionses = iTradingOrderShippingServiceOptions.selectOrderGetItemByShippingDetailsId(shippingDetails.getId());
                for (TradingOrderShippingServiceOptions shippingServiceOptionse : shippingServiceOptionses) {
                    iTradingOrderShippingServiceOptions.deleteOrderShippingServiceOptions(shippingServiceOptionse);
                }
                for (TradingOrderShippingServiceOptions serviceOptionse : serviceOptionses) {
                    serviceOptionse.setShippingdetailsId(shippingDetails.getId());
                    serviceOptionse.setCreateUser(ebayAccount.getUserId());
                    iTradingOrderShippingServiceOptions.saveOrderShippingServiceOptions(serviceOptionse);
                }
                item.setCreateUser(ebayAccount.getUserId());
                item.setUpdatetime(new Date());
                iTradingOrderGetItem.saveOrderGetItem(item);
                List<TradingOrderItemSpecifics> specificItemList = (List<TradingOrderItemSpecifics>) items.get(GetOrderItemAPI.ITEM_SPECIFICS);
                List<TradingOrderItemSpecifics> Itemspecifics = iTradingOrderItemSpecifics.selectOrderItemSpecificsByItemId(item.getId());
                if (Itemspecifics != null && Itemspecifics.size() > 0) {
                    for (TradingOrderItemSpecifics specifics : Itemspecifics) {
                        iTradingOrderItemSpecifics.deleteOrderItemSpecifics(specifics);
                    }
                }
                for (TradingOrderItemSpecifics specifics : specificItemList) {
                    specifics.setOrderitemId(item.getId());
                    specifics.setCreateUser(ebayAccount.getUserId());
                    iTradingOrderItemSpecifics.saveOrderItemSpecifics(specifics);
                }
                List<TradingOrderVariation> variationList = (List<TradingOrderVariation>) items.get(GetOrderItemAPI.VARIATION);
                List<TradingOrderVariation> variationLists = iTradingOrderVariation.selectOrderVariationByItemId(item.getId());
                if (variationLists != null && variationLists.size() > 0) {
                    for (TradingOrderVariation specifics : variationLists) {
                        List<TradingOrderVariationSpecifics> specificsVariations = iTradingOrderVariationSpecifics.selectOrderVariationSpecificsByVariationId(specifics.getId());
                        if (specificsVariations != null && specificsVariations.size() > 0) {
                            for (TradingOrderVariationSpecifics specificsVariation : specificsVariations) {
                                iTradingOrderVariationSpecifics.deleteOrderVariationSpecifics(specificsVariation);
                            }
                        }
                        iTradingOrderVariation.deleteOrderVariation(specifics);
                    }
                }
                for (TradingOrderVariation specifics : variationList) {
                    specifics.setOrderitemId(item.getId());
                    specifics.setCreateUser(ebayAccount.getUserId());
                    iTradingOrderVariation.saveOrderVariation(specifics);
                    List<TradingOrderVariationSpecifics> specificsList = (List<TradingOrderVariationSpecifics>) items.get(GetOrderItemAPI.VARIATION_SPECIFICS);
                    for (TradingOrderVariationSpecifics specificsVariation : specificsList) {
                        specificsVariation.setOrdervariationId(specifics.getId());
                        specificsVariation.setCreateUser(ebayAccount.getUserId());
                        iTradingOrderVariationSpecifics.saveOrderVariationSpecifics(specificsVariation);
                    }
                }
                List<TradingOrderPictures> pictrueList = (List<TradingOrderPictures>) items.get(GetOrderItemAPI.PICTURES);
                List<TradingOrderPictures> pictrueLists = iTradingOrderPictures.selectOrderPicturesByItemId(item.getId());
                if (pictrueLists != null && pictrueLists.size() > 0) {
                    for (TradingOrderPictures specifics : pictrueLists) {
                        iTradingOrderPictures.deleteOrderPictures(specifics);
                    }
                }
                for (TradingOrderPictures specifics : pictrueList) {
                    specifics.setOrderitemId(item.getId());
                    specifics.setCreateUser(ebayAccount.getUserId());
                    iTradingOrderPictures.saveOrderPictures(specifics);
                }
            }
        }

    }
}
