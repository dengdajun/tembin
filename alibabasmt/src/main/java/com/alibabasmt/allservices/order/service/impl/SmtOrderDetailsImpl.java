package com.alibabasmt.allservices.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibabasmt.allservices.order.service.*;
import com.alibabasmt.database.smtorder.mapper.SmtOrderDetailsMapper;
import com.alibabasmt.database.smtorder.model.*;
import com.alibabasmt.utils.signature.api.APIStaticParm;
import com.alibabasmt.utils.signature.api.ApiCallService;
import com.alibabasmt.utils.signature.vo.APICallVO;
import com.alibabasmt.utils.signature.vo.SignatureVO;
import com.base.utils.common.DateUtils;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrtor on 2015/3/24.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SmtOrderDetailsImpl implements ISmtOrderDetails {
    @Autowired
    private SmtOrderDetailsMapper smtOrderDetailsMapper;
    @Autowired
    private ISmtOrderBuyerInfo iSmtOrderBuyerInfo;
    @Autowired
    private ISmtOrderReceiptAddress iSmtOrderReceiptAddress;
    @Autowired
    private ISmtOrderMessage iSmtOrderMessage;
    @Autowired
    private ISmtOrderLog iSmtOrderLog;
    @Autowired
    private ISmtOrderChildOrderItemInfo iSmtOrderChildOrderItemInfo;
    @Autowired
    private ISmtOrderChildOrderDetails iSmtOrderChildOrderDetails;
    @Override
    public void saveSmtOrderDetails(SmtOrderDetails smtOrderDetails) throws Exception {
        if(smtOrderDetails.getId()==null){
            ObjectUtils.toInitPojoForInsert(smtOrderDetails);
            smtOrderDetailsMapper.insert(smtOrderDetails);
        }else{
            SmtOrderDetails t=smtOrderDetailsMapper.selectByPrimaryKey(smtOrderDetails.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(),SmtOrderDetailsMapper.class,smtOrderDetails.getId(),"Synchronize");
            smtOrderDetailsMapper.updateByPrimaryKeySelective(smtOrderDetails);
        }
    }

    @Override
    public void paseSmtOrderDetailsAndSave(String orderId,Long smtAccountId) throws Exception{
        APICallVO apiCallVO=new APICallVO();
        SignatureVO signatureVO=new SignatureVO();
        signatureVO.initVO();
        apiCallVO.setSmtAccountID(smtAccountId);
        apiCallVO.setUrlPath(APIStaticParm.findOrderById);
        Map<String,String> param=new HashMap<String, String>();
        //---------------------------------------------
        param.put("orderId",orderId);
        param.put("access_token", "");//access_token就设置为空，可以添加其他参数
        apiCallVO.setParam(param);
        apiCallVO.setSignatureVO(signatureVO);
        String result = ApiCallService.callApi(apiCallVO);
        //String result="{\"receiptAddress\":{\"zip\":\"310012\",\"address2\":\"\",\"detailAddress\":\"dfddfdf\",\"country\":\"US\",\"city\":\"agagag\",\"phoneNumber\":\"2221\",\"province\":\"Arkansas\",\"phoneArea\":\"223\",\"phoneCountry\":\"011\",\"contactPerson\":\"Henry Xue\",\"mobileNo\":\"\"},\"gmtModified\":\"20131114014210000-0800\",\"buyerInfo\":{\"lastName\":\"King\",\"loginId\":\"aliqatest07\",\"email\":\"rhh07@sina.cn\",\"firstName\":\"Amanda\",\"country\":\"IN\"},\"buyerloginid\":\"aliqatest07\",\"logisticsAmount\":{\"amount\":0.00,\"cent\":0,\"currencyCode\":\"USD\",\"centFactor\":100,\"currency\":{\"defaultFractionDigits\":2,\"currencyCode\":\"USD\",\"symbol\":\"$\"}},\"childOrderExtInfoList\":[{\"unitPrice\":{\"amount\":50.00,\"cent\":5000,\"currencyCode\":\"USD\",\"centFactor\":100,\"currency\":{\"defaultFractionDigits\":2,\"currencyCode\":\"USD\",\"symbol\":\"$\"}},\"quantity\":0,\"sku\":\"{\\\"sku\\\":[{\\\"order\\\":1,\\\"pId\\\":14,\\\"pName\\\":\\\"Color\\\",\\\"pValue\\\":\\\"Red\\\",\\\"pValueId\\\":10,\\\"selfDefineValue\\\":\\\"\\\",\\\"showType\\\":\\\"colour_atla\\\",\\\"skuImg\\\":\\\"\\\"}]}\",\"productName\":\"5 COLORS 4GB 4TH GEN MP3 MP4 PLAYER With 1.8'' Inch TFT Screen & FM Radio & Games &Voice Recorder &Earphone & USB\",\"productId\":1080627398}],\"orderMsgList\":[],\"issueInfo\":{\"issueStatus\":\"NO_ISSUE\"},\"logisticInfoList\":[],\"id\":30004396705804,\"issueStatus\":\"NO_ISSUE\",\"frozenStatus\":\"NO_FROZEN\",\"logisticsStatus\":\"NO_LOGISTICS\",\"orderAmount\":{\"amount\":50.00,\"cent\":5000,\"currencyCode\":\"USD\",\"centFactor\":100,\"currency\":{\"defaultFractionDigits\":2,\"currencyCode\":\"USD\",\"symbol\":\"$\"}},\"sellerSignerFullname\":\"vikki Lee\",\"initOderAmount\":{\"amount\":50.00,\"cent\":5000,\"currencyCode\":\"USD\",\"centFactor\":100,\"currency\":{\"defaultFractionDigits\":2,\"currencyCode\":\"USD\",\"symbol\":\"$\"}},\"childOrderList\":[{\"lotNum\":1,\"productAttributes\":\"{\\\"sku\\\":[{\\\"order\\\":1,\\\"pId\\\":14,\\\"pName\\\":\\\"Color\\\",\\\"pValue\\\":\\\"Red\\\",\\\"pValueId\\\":10,\\\"selfDefineValue\\\":\\\"\\\",\\\"showType\\\":\\\"colour_atla\\\",\\\"skuImg\\\":\\\"\\\"}]}\",\"orderStatus\":\"PLACE_ORDER_SUCCESS\",\"productUnit\":\"piece\",\"skuCode\":\"\",\"productId\":1080627398,\"id\":30004396705804,\"issueStatus\":\"NO_ISSUE\",\"frozenStatus\":\"NO_FROZEN\",\"productCount\":1,\"fundStatus\":\"NOT_PAY\",\"initOrderAmt\":{\"amount\":50.00,\"cent\":5000,\"currencyCode\":\"USD\",\"centFactor\":100,\"currency\":{\"defaultFractionDigits\":2,\"currencyCode\":\"USD\",\"symbol\":\"$\"}},\"productPrice\":{\"amount\":50.00,\"cent\":5000,\"currencyCode\":\"USD\",\"centFactor\":100,\"currency\":{\"defaultFractionDigits\":2,\"currencyCode\":\"USD\",\"symbol\":\"$\"}},\"productName\":\"5 COLORS 4GB 4TH GEN MP3 MP4 PLAYER With 1.8'' Inch TFT Screen & FM Radio & Games &Voice Recorder &Earphone & USB\"}],\"gmtCreate\":\"20131114014210000-0800\",\"sellerOperatorLoginId\":\"cn1501470674\",\"loanInfo\":{},\"orderStatus\":\"PLACE_ORDER_SUCCESS\",\"buyerSignerFullname\":\"Amanda King\",\"loanStatus\":\"not_pay\",\"fundStatus\":\"NOT_PAY\"}";
        Map jsons = JSON.parseObject(result, HashMap.class);
        SmtOrderDetails smtOrderDetails=new SmtOrderDetails();
        SmtOrderDetails details=selectSmtOrderDetailsByOrderId(orderId);
        if(details!=null){
            smtOrderDetails.setId(details.getId());
        }
        //获取买家信息
        JSONObject infoObject= (JSONObject) jsons.get("buyerInfo");
        SmtOrderBuyerInfo smtOrderBuyerInfo=iSmtOrderBuyerInfo.paseSmtOrderBuyerInfoAndSave(jsons);
        smtOrderDetails.setBuyerinfoId(smtOrderBuyerInfo.getId());
        //获取订单接收的地址
        JSONObject addressObject= (JSONObject) jsons.get("receiptAddress");
        SmtOrderReceiptAddress smtOrderReceiptAddress=iSmtOrderReceiptAddress.paseSmtOrderBuyerInfoAndSave(jsons,orderId);
        smtOrderDetails.setReceiptaddressId(smtOrderReceiptAddress.getId());
        //获取订单留言
        //iSmtOrderMessage.parseSmtOrderMessageAndSave(jsons);
        //子订单产品信息
        iSmtOrderChildOrderItemInfo.parseSmtOrderChildOrderItemInfoAndSave(jsons,orderId);
        //物流信息
        JSONArray logisticInfoList= (JSONArray) jsons.get("logisticInfoList");
        if(logisticInfoList!=null&&logisticInfoList.size()>0){
            JSONObject logistiv=logisticInfoList.getJSONObject(0);
            if(logistiv!=null){
                smtOrderDetails.setReceivestatus(logistiv.getString("receiveStatus"));
                smtOrderDetails.setLogisticsno(logistiv.getString("logisticsNo"));
                smtOrderDetails.setLogisticsservicename(logistiv.getString("logisticsServiceName"));
                smtOrderDetails.setLogisticstypecode(logistiv.getString("logisticsTypeCode"));
                String gmtSend=logistiv.getString("gmtSend");
                smtOrderDetails.setGmtsend(DateUtils.NumStringToDate(gmtSend));
            }
        }
        //保存订单日志
        iSmtOrderLog.parseSmtOrderLogAndSave(jsons);
        //获取子订单详情
        iSmtOrderChildOrderDetails.parseSmtOrderChildOrderDetailsAndSave(jsons);
        //----------------------------------------------
        String gmtModified= (String) jsons.get("gmtModified");
        smtOrderDetails.setGmtmodified(DateUtils.NumStringToDate(gmtModified));
        smtOrderDetails.setBuyerloginid((String) jsons.get("buyerloginid"));
        smtOrderDetails.setFrozenstatus((String) jsons.get("frozenStatus"));
        smtOrderDetails.setIssuestatus((String) jsons.get("issueStatus"));
        smtOrderDetails.setLogisticsstatus((String) jsons.get("logisticsStatus"));
        smtOrderDetails.setSellersignerfullname((String) jsons.get("sellerSignerFullname"));
        String gmtCreate= (String) jsons.get("gmtCreate");
        smtOrderDetails.setGmtcreate(DateUtils.NumStringToDate(gmtCreate));
        smtOrderDetails.setSelleroperatorloginid((String) jsons.get("sellerOperatorLoginId"));
        smtOrderDetails.setPaymenttype((String) jsons.get("paymentType"));
        smtOrderDetails.setOrderstatus((String) jsons.get("orderStatus"));
        smtOrderDetails.setBuyersignerfullname((String) jsons.get("buyerSignerFullname"));
        smtOrderDetails.setLoanstatus((String) jsons.get("loanStatus"));
        String gmtPaySuccess= (String) jsons.get("gmtPaySuccess");
        smtOrderDetails.setGmtpaysuccess(DateUtils.NumStringToDate(gmtPaySuccess));
        smtOrderDetails.setFundstatus((String) jsons.get("fundStatus"));
        smtOrderDetails.setUpdateTime(new Date());
        JSONObject logisticsAmount= (JSONObject) jsons.get("logisticsAmount");
        if(logisticsAmount!=null){
            smtOrderDetails.setLogisticsamount(String.valueOf(logisticsAmount.getBigDecimal("amount")));
            smtOrderDetails.setLogisticsamountcurrency(logisticsAmount.getString("currencyCode"));
        }
        JSONObject orderAmount= (JSONObject) jsons.get("orderAmount");
        if(orderAmount!=null){
            smtOrderDetails.setOrderamount(String.valueOf(orderAmount.getBigDecimal("amount")));
            smtOrderDetails.setOrderamountcurrency(orderAmount.getString("currencyCode"));
        }
        JSONObject initOderAmount= (JSONObject) jsons.get("initOderAmount");
        if(initOderAmount!=null){
            smtOrderDetails.setInitoderamount(String.valueOf(initOderAmount.getBigDecimal("amount")));
            smtOrderDetails.setInitoderamountcurrency(initOderAmount.getString("currencyCode"));
        }
        smtOrderDetails.setOrderid(orderId);
        saveSmtOrderDetails(smtOrderDetails);
    }

    @Override
    public SmtOrderDetails selectSmtOrderDetailsByOrderId(String orderId) {
        SmtOrderDetailsExample example=new SmtOrderDetailsExample();
        SmtOrderDetailsExample.Criteria cr=example.createCriteria();
        cr.andOrderidEqualTo(orderId);
        List<SmtOrderDetails> list=smtOrderDetailsMapper.selectByExample(example);
        return list!=null&&list.size()>0?list.get(0):null;
    }
}
