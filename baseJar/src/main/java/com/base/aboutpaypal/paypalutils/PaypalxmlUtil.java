package com.base.aboutpaypal.paypalutils;

import com.base.aboutpaypal.domain.PaypalVO;
import com.base.database.trading.model.UsercontrollerPaypalAccount;
import com.base.utils.exception.Asserts;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.xmlutils.SamplePaseXml;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * Created by Administrator on 2014/9/12.
 */
public class PaypalxmlUtil {
    private static final Log logger = LogFactory.getLog(PaypalxmlUtil.class);
    /**获取paypal余额的请求报文*/
    public static String getBalanceXML(UsercontrollerPaypalAccount uspa){
String xml="<soapenv:Envelope  xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:ns=\"urn:ebay:api:PayPalAPI\" xmlns:ebl=\"urn:ebay:apis:eBLBaseComponents\" xmlns:cc=\"urn:ebay:apis:CoreComponentTypes\" xmlns:ed=\"urn:ebay:apis:EnhancedDataTypes\" >\n" +
        "  <soapenv:Header>\n" +
        "    <ns:RequesterCredentials>\n" +
        "      <ebl:Credentials>\n" +
        "        <ebl:Username>"+uspa.getApiUserName()+"</ebl:Username>\n" +
        "        <ebl:Password>"+uspa.getApiPassword()+"</ebl:Password>\n" +
        "        <ebl:Signature>"+uspa.getApiSignature()+"</ebl:Signature>\n" +
        "        <ebl:Subject>"+uspa.getEmail()+"</ebl:Subject>\n" +
        "      </ebl:Credentials>\n" +

/*        "      <ebl:Credentials>\n" +
        "        <ebl:Username>payment_api1.tembin.com</ebl:Username>\n" +
        "        <ebl:Password>PM8MXQ27DKPUCZH3</ebl:Password>\n" +
        "        <ebl:Signature></ebl:Signature>\n" +
        "      </ebl:Credentials>\n" +*/
        "    </ns:RequesterCredentials>\n" +
        "  </soapenv:Header>\n" +
        "  <soapenv:Body>\n" +
        "    <ns:GetBalanceReq>\n" +
        "      <ns:GetBalanceRequest>\n" +
        "        <ebl:DetailLevel>ReturnAll</ebl:DetailLevel>\n" +
        "        <ebl:ErrorLanguage>en_US</ebl:ErrorLanguage>\n" +
        "        <ebl:Version>94.0</ebl:Version>\n" +
        "      </ns:GetBalanceRequest>\n" +
        "    </ns:GetBalanceReq>\n" +
        "  </soapenv:Body>\n" +
        "</soapenv:Envelope>";
        return xml;
    }

    /**解析paypal余额返回的xml*/
    public static PaypalVO getBalance(String xml) throws Exception {
        Element element=getSpecElement(xml,"Body","GetBalanceResponse","Balance");
        Element ack=getSpecElement(xml,"Body","GetBalanceResponse","Ack");
        PaypalVO paypalVO=new PaypalVO();
        if(ack!=null){
            paypalVO.setAck(ack.getTextTrim());
        }
        if ( "Success".equalsIgnoreCase(paypalVO.getAck()) ){
            paypalVO.setBalance(element.getTextTrim());
            paypalVO.setUnitName(element.attributeValue("currencyID"));
        }else {
            logger.error("获取paypal余额失败"+xml);
        }

        return paypalVO;
    }

    /**根据tranctionid获取交易的费用明细*/
    public static String getTransactionDetailsXML(UsercontrollerPaypalAccount uspa,PaypalVO paypalVO){
        String xml="<soapenv:Envelope  xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:ns=\"urn:ebay:api:PayPalAPI\" xmlns:ebl=\"urn:ebay:apis:eBLBaseComponents\" xmlns:cc=\"urn:ebay:apis:CoreComponentTypes\" xmlns:ed=\"urn:ebay:apis:EnhancedDataTypes\" >\n" +
                "  <soapenv:Header>\n" +
                "    <ns:RequesterCredentials>\n" +
                "      <ebl:Credentials>\n" +
                "        <ebl:Username>"+uspa.getApiUserName()+"</ebl:Username>\n" +
                "        <ebl:Password>"+uspa.getApiPassword()+"</ebl:Password>\n" +
                "        <ebl:Signature>"+uspa.getApiSignature()+"</ebl:Signature>\n" +
                "        <ebl:Subject>"+uspa.getEmail()+"</ebl:Subject>\n" +
                "      </ebl:Credentials>\n" +
               /* "      <ebl:Credentials>\n" +
                "        <ebl:Username>payment_api1.tembin.com</ebl:Username>\n" +
                "        <ebl:Password>PM8MXQ27DKPUCZH3</ebl:Password>\n" +
                "        <ebl:Signature>AFcWxV21C7fd0v3bYYYRCpSSRl31AK2RZxSioHeU5VTvyufB3.eLT7r6</ebl:Signature>\n" +
                "        <ebl:Subject>yezhengfan98@gmail.com</ebl:Subject>\n" +
                "      </ebl:Credentials>\n" +*/
                "    </ns:RequesterCredentials>\n" +
                "  </soapenv:Header>\n" +
                "  <soapenv:Body>\n" +
                "    <ns:GetTransactionDetailsReq>\n" +
                "      <ns:GetTransactionDetailsRequest>\n" +
                "        <ns:TransactionID>"+paypalVO.getTransactionID()+"</ns:TransactionID>\n" +
            /*    "        <ns:TransactionID>00P05800PB685682C</ns:TransactionID>\n" +*/
                "        <ebl:Version>94.0</ebl:Version>\n" +
                "      </ns:GetTransactionDetailsRequest>\n" +
                "    </ns:GetTransactionDetailsReq>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        return xml;
    }

/**获取paypal交易的各项费用*/
    public static PaypalVO getTranDetail(String resXML,UsercontrollerPaypalAccount uspa) throws Exception {
        PaypalVO paypalVO=new PaypalVO();
        Element ackelement=getSpecElement(resXML,"Body","GetTransactionDetailsResponse","Ack");
        paypalVO.setAck(ackelement.getTextTrim());
        String ack=ackelement.getTextTrim();
        if(!"Failure".equals(ackelement.getTextTrim())){
            Element payelement=getSpecElement(resXML,"Body","GetTransactionDetailsResponse",
                    "PaymentTransactionDetails","PaymentInfo");
            if(payelement!=null){
                Element transactionID=payelement.element("TransactionID");
                if(transactionID!=null){
                    paypalVO.setTransactionID(transactionID.getTextTrim());
                }
                Element paymentType=payelement.element("PaymentType");
                if(paymentType!=null){
                    paypalVO.setPaymentType(paymentType.getTextTrim());
                }
                Element grossAmount=payelement.element("GrossAmount");
                if(grossAmount!=null){
                    paypalVO.setGrossAmount(grossAmount.getTextTrim());
                    paypalVO.setGrossAmountUnit(grossAmount.attributeValue("currencyID"));
                }
                Element feeAmount=payelement.element("FeeAmount");
                if(feeAmount!=null){
                    paypalVO.setFeeAmount(feeAmount.getTextTrim());
                    paypalVO.setFeeAmountUnit(feeAmount.attributeValue("currencyID"));
                }
                Element taxAmount=payelement.element("TaxAmount");
                if(taxAmount!=null){
                    paypalVO.setTaxAmount(taxAmount.getTextTrim());
                    paypalVO.setTaxAmountUnit(taxAmount.attributeValue("currencyID"));
                }
            }
            Element payerInfo=getSpecElement(resXML,"Body","GetTransactionDetailsResponse",
                    "PaymentTransactionDetails","PayerInfo");
            if(payerInfo!=null){
                Element payer=payerInfo.element("Payer");
                if(payer!=null){
                    paypalVO.setPayer(payer.getTextTrim());
                }
            }

            Element address=getSpecElement(resXML,"Body","GetTransactionDetailsResponse",
                    "PaymentTransactionDetails","PayerInfo","Address");
            if(address!=null){
                Element name=address.element("Name");
                if(name!=null){
                    paypalVO.setPaypalname(name.getTextTrim());
                }
                Element street1=address.element("Street1");
                if(street1!=null){
                    paypalVO.setPaypalstreet1(street1.getTextTrim());
                }
                Element street2=address.element("Street2");
                if(street2!=null){
                    paypalVO.setPaypalstreet2(street2.getTextTrim());
                }
                Element countryName=address.element("CountryName");
                if(countryName!=null){
                    paypalVO.setPaypalcountryname(countryName.getTextTrim());
                }
                Element postalCode=address.element("PostalCode");
                if(postalCode!=null){
                    paypalVO.setPaypalpostalcode(postalCode.getTextTrim());
                }
                Element cityName=address.element("CityName");
                if(cityName!=null){
                    paypalVO.setPaypalcityname(cityName.getTextTrim());
                }
            }
        }else{
            Element message1=getSpecElement(resXML,"Body","GetTransactionDetailsResponse","Errors","LongMessage");
            Element errcode=getSpecElement(resXML,"Body","GetTransactionDetailsResponse","Errors","ErrorCode");
            if (errcode!=null){
                paypalVO.setErrCode(errcode.getTextTrim());
            }
            String message=message1.getTextTrim();
            logger.error("paypal费用失败:"+message+"paypal账号:"+uspa.getEmail()+"res:"+resXML);
        }
        return paypalVO;
    }


    /**获取指定的节点(指定获取单个的节点)*/
    public static Element getSpecElement(String xml,String... nodes) throws Exception {
        Document document= SamplePaseXml.formatStr2Doc(xml);
        Asserts.assertTrue(document!=null,"获取返回内容为空，请重新操作！");
        Element rootElt = document.getRootElement();
        Element element=null;

        if(nodes!=null && nodes.length>0){
            for (int i =0;i<nodes.length;i++){
                if(element==null){element=rootElt;}
                element = element.element(nodes[i]);
            }
        }

        return element;
    }
    /**paypal退全款:根据paypal交易号*/
    public static String getRefundTransactionFull(UsercontrollerPaypalAccount uspa,String transactionId){
        String xml="<soapenv:Envelope  xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:ns=\"urn:ebay:api:PayPalAPI\" xmlns:ebl=\"urn:ebay:apis:eBLBaseComponents\" xmlns:cc=\"urn:ebay:apis:CoreComponentTypes\" xmlns:ed=\"urn:ebay:apis:EnhancedDataTypes\" >\n" +
                "  <soapenv:Header>\n" +
                "    <ns:RequesterCredentials>\n" +
                "      <ebl:Credentials>\n" +
                "        <ebl:Username>"+uspa.getApiUserName()+"</ebl:Username>\n" +
                "        <ebl:Password>"+uspa.getApiPassword()+"</ebl:Password>\n" +
                "        <ebl:Signature>"+uspa.getApiSignature()+"</ebl:Signature>\n" +
                "        <ebl:Subject>"+uspa.getEmail()+"</ebl:Subject>\n" +
                "      </ebl:Credentials>\n" +
                "    </ns:RequesterCredentials>\n" +
                "  </soapenv:Header>\n" +
                "  <soapenv:Body>\n" +
                "    <ns:RefundTransactionReq>\n" +
                "      <ns:RefundTransactionRequest>\n" +
                "        <ns:TransactionID>"+transactionId+"</ns:TransactionID>\n" +
                "        <ns:RefundType>Full</ns:RefundType>\n" +
                "        <ebl:DetailLevel>ReturnAll</ebl:DetailLevel>\n" +
                "        <ebl:Version>94.0</ebl:Version>\n" +
                "      </ns:RefundTransactionRequest>\n" +
                "    </ns:RefundTransactionReq>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        return xml;
    }
    /**paypal退半款:根据paypal交易号*/
    public static String getRefundTransactionPartial(UsercontrollerPaypalAccount uspa,String transactonId,String money,String currency){
        Asserts.assertTrue(StringUtils.isNotBlank(currency),"货币符号不能为空!");
        String xml="<soapenv:Envelope  xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"  xmlns:ns=\"urn:ebay:api:PayPalAPI\" xmlns:ebl=\"urn:ebay:apis:eBLBaseComponents\" xmlns:cc=\"urn:ebay:apis:CoreComponentTypes\" xmlns:ed=\"urn:ebay:apis:EnhancedDataTypes\" >\n" +
                "  <soapenv:Header>\n" +
                "    <ns:RequesterCredentials>\n" +
                "      <ebl:Credentials>\n" +
                "        <ebl:Username>"+uspa.getApiUserName()+"</ebl:Username>\n" +
                "        <ebl:Password>"+uspa.getApiPassword()+"</ebl:Password>\n" +
                "        <ebl:Signature>"+uspa.getApiSignature()+"</ebl:Signature>\n" +
                "        <ebl:Subject>"+uspa.getEmail()+"</ebl:Subject>\n" +
                "      </ebl:Credentials>\n" +
                "    </ns:RequesterCredentials>\n" +
                "  </soapenv:Header>\n" +
                "  <soapenv:Body>\n" +
                "    <ns:RefundTransactionReq>\n" +
                "      <ns:RefundTransactionRequest>\n" +
                "        <ns:TransactionID>"+transactonId+"</ns:TransactionID>\n" +
                "        <ns:RefundType>Partial</ns:RefundType>\n" +
                "        <ns:Amount currencyID=\""+currency+"\">"+money+"</ns:Amount>\n" +
                "        <ebl:DetailLevel>ReturnAll</ebl:DetailLevel>\n" +
                "        <ebl:Version>94.0</ebl:Version>\n" +
                "      </ns:RefundTransactionRequest>\n" +
                "    </ns:RefundTransactionReq>\n" +
                "  </soapenv:Body>\n" +
                "</soapenv:Envelope>";
        return xml;
    }


    public static void main(String[] args) throws Exception{

        UsercontrollerPaypalAccount uspa=new UsercontrollerPaypalAccount();
uspa.setApiUserName("payment_api1.tembin.com");
        uspa.setApiPassword("PM8MXQ27DKPUCZH3");
        uspa.setApiSignature("AFcWxV21C7fd0v3bYYYRCpSSRl31AK2RZxSioHeU5VTvyufB3.eLT7r6");
        uspa.setEmail("renqing899@gmail.com");

        PaypalVO paypalVO=new PaypalVO();
        paypalVO.setTransactionID("9HX06662WW836174S");

        HttpClient h= HttpClientUtil.getHttpsClient();
        String res = HttpClientUtil.post(h, "https://api-3t.paypal.com/2.0",
                PaypalxmlUtil.getTransactionDetailsXML(uspa, paypalVO));


        System.out.println(res);
    }


}

