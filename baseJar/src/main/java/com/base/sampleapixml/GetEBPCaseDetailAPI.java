package com.base.sampleapixml;

import com.base.database.trading.model.TradingCasePaymentDetail;
import com.base.database.trading.model.TradingCaseResponseHistory;
import com.base.database.trading.model.TradingGetEBPCaseDetail;
import com.base.utils.common.DateUtils;
import com.base.utils.xmlutils.SamplePaseXml;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 */
public class GetEBPCaseDetailAPI {
    public static Map<String,Object> parseXMLAndSave(String res) throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();
        TradingGetEBPCaseDetail ebpCaseDetail=new TradingGetEBPCaseDetail();
        List<TradingCaseResponseHistory> historyList=new ArrayList<TradingCaseResponseHistory>();
        List<TradingCasePaymentDetail> paymentDetailList=new ArrayList<TradingCasePaymentDetail>();
        Document document= SamplePaseXml.formatStr2Doc(res);
        Element root=document.getRootElement();
        Element caseDetail=root.element("caseDetail");
        ebpCaseDetail.setOpenreason(SamplePaseXml.getSpecifyElementText(caseDetail,"openReason"));
        String decisionDate=SamplePaseXml.getSpecifyElementText(caseDetail,"decisionDate");
        ebpCaseDetail.setDecisiondate(DateUtils.returnDate(decisionDate));
        ebpCaseDetail.setDecision(SamplePaseXml.getSpecifyElementText(caseDetail,"decision"));
        ebpCaseDetail.setGlobalid(SamplePaseXml.getSpecifyElementText(caseDetail,"globalId"));
        ebpCaseDetail.setAppealdecision(SamplePaseXml.getSpecifyElementText(caseDetail,"decisionReasonDetail","content"));
        String appealDecisionDate=SamplePaseXml.getSpecifyElementText(caseDetail,"appeal","decisionDate");
        ebpCaseDetail.setAppealdecisiondate(DateUtils.returnDate(appealDecisionDate));
        String creationDate=SamplePaseXml.getSpecifyElementText(caseDetail,"appeal","creationDate");
        ebpCaseDetail.setAppealcreationdate(DateUtils.returnDate(creationDate));
        ebpCaseDetail.setSellershipmentaddress(SamplePaseXml.getSpecifyElementText(caseDetail,"sellerShipment","shippingAddress"));
        ebpCaseDetail.setBuyerreturnshipmentaddress(SamplePaseXml.getSpecifyElementText(caseDetail,"buyerReturnShipment","shippingAddress"));
        ebpCaseDetail.setAgreedrefundamount(SamplePaseXml.getSpecifyElementText(caseDetail,"agreedRefundAmount"));
        ebpCaseDetail.setDetailstatusinfodescription(SamplePaseXml.getSpecifyElementText(caseDetail,"detailStatusInfo","description"));
        ebpCaseDetail.setInitialbuyerexpectationdetail(SamplePaseXml.getSpecifyElementText(caseDetail,"initialBuyerExpectationDetail","description"));
        ebpCaseDetail.setTransactionid(SamplePaseXml.getSpecifyElementText(root,"caseSummary","item","transactionId"));
        Iterator history=caseDetail.elementIterator("responseHistory");
        while(history.hasNext()){
            Element response= (Element) history.next();
            TradingCaseResponseHistory responseHistory=new TradingCaseResponseHistory();
            responseHistory.setNote(SamplePaseXml.getSpecifyElementText(response,"note"));
            responseHistory.setAuthorrole(SamplePaseXml.getSpecifyElementText(response,"author","role"));
            responseHistory.setActivity(SamplePaseXml.getSpecifyElementText(response,"activityDetail","description"));
            String creationDate1=SamplePaseXml.getSpecifyElementText(response,"creationDate");
            responseHistory.setCreationdate(DateUtils.returnDate(creationDate1));
            historyList.add(responseHistory);
        }
        Element paymentDetail1=caseDetail.element("paymentDetail");
        Iterator pay=null;
        if(paymentDetail1!=null){
            pay =paymentDetail1.elementIterator("moneyMovement");
        }
        if(pay!=null) {
            while (pay.hasNext()) {
                Element payment = (Element) pay.next();
                TradingCasePaymentDetail paymentDetail = new TradingCasePaymentDetail();
                paymentDetail.setType(SamplePaseXml.getSpecifyElementText(payment, "type"));
                paymentDetail.setFrompartyrole(SamplePaseXml.getSpecifyElementText(payment, "fromParty", "role"));
                paymentDetail.setTopartyrole(SamplePaseXml.getSpecifyElementText(payment, "toParty", "role"));
                paymentDetail.setAmount(SamplePaseXml.getSpecifyElementText(payment, "amount"));
                paymentDetail.setPaymentmethod(SamplePaseXml.getSpecifyElementText(payment, "paymentMethod"));
                paymentDetail.setStatus(SamplePaseXml.getSpecifyElementText(payment, "status"));
                String transactionDate = SamplePaseXml.getSpecifyElementText(payment, "transactionDate");
                paymentDetail.setTransactiondate(DateUtils.returnDate(transactionDate));
                paymentDetail.setBalancedue(SamplePaseXml.getSpecifyElementText(paymentDetail1, "balanceDue"));
                paymentDetailList.add(paymentDetail);
            }
        }
        Element buyerReturnShipment=caseDetail.element("buyerReturnShipment");
        if(buyerReturnShipment!=null){
            Element shippingAddress=buyerReturnShipment.element("shippingAddress");
            if(shippingAddress!=null){
                ebpCaseDetail.setName(SamplePaseXml.getSpecifyElementText(shippingAddress,"name"));
                ebpCaseDetail.setStreet1(SamplePaseXml.getSpecifyElementText(shippingAddress,"street1"));
                ebpCaseDetail.setStreet2(SamplePaseXml.getSpecifyElementText(shippingAddress,"street2"));
                ebpCaseDetail.setCity(SamplePaseXml.getSpecifyElementText(shippingAddress,"city"));
                ebpCaseDetail.setStateorprovince(SamplePaseXml.getSpecifyElementText(shippingAddress,"stateOrProvince"));
                ebpCaseDetail.setCountry(SamplePaseXml.getSpecifyElementText(shippingAddress,"country"));
                ebpCaseDetail.setPostalcode(SamplePaseXml.getSpecifyElementText(shippingAddress,"postalCode"));
            }
        }
        map.put("ebpCaseDetail",ebpCaseDetail);
        map.put("historyList",historyList);
        map.put("paymentDetailList",paymentDetailList);
        return map;
    }
}
