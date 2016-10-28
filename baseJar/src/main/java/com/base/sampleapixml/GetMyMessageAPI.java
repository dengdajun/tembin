package com.base.sampleapixml;

import com.base.database.trading.model.TradingMessageAddmembermessage;
import com.base.database.trading.model.TradingMessageGetmymessage;
import com.base.domains.SessionVO;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.userinfo.service.UserInfoService;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.DateUtils;
import com.base.utils.threadpool.AddApiTask;
import com.base.utils.threadpool.EbayErrorCodeStatic;
import com.base.utils.xmlutils.SamplePaseXml;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/14.
 */
public class GetMyMessageAPI {
    public static List<Element> getMessages(String xml) throws Exception {
        Document document= SamplePaseXml.formatStr2Doc(xml);
        Element root= document.getRootElement();
        Element messages= root.element("Messages");
        Iterator iterator=messages.elementIterator("Message");
        List<Element> list=new ArrayList<Element>();
        Map<String,Object> map=new HashMap<String, Object>();
        while(iterator.hasNext()){
            list.add((Element) iterator.next());
        }
        return list;
    }
    public static List<Element> getMessagesByGetNotifi(String xml) throws Exception {
        Document document= SamplePaseXml.formatStr2Doc(xml);
        Element root= document.getRootElement();
        Element soapenvBody= root.element("Body");
        if(soapenvBody==null){
            return null;
        }
        Element myMessages=soapenvBody.element("GetMyMessagesResponse");
        if(myMessages==null){
            return null;
        }
        Element messages=myMessages.element("Messages");
        if(messages==null){
            return  null;
        }
        Iterator iterator=messages.elementIterator("Message");
        List<Element> list=new ArrayList<Element>();
        Map<String,Object> map=new HashMap<String, Object>();
        while(iterator.hasNext()){
            list.add((Element) iterator.next());
        }
        return list;
    }
    public static TradingMessageGetmymessage addDatabaseByGetNotifi(Element message) throws Exception {
       /* for(Element message:messages){*/
        TradingMessageGetmymessage ms=new TradingMessageGetmymessage();
        ms.setSender(SamplePaseXml.getSpecifyElementText(message,"Sender"));
        ms.setRecipientuserid(SamplePaseXml.getSpecifyElementText(message,"RecipientUserID"));
        ms.setSendtoname(SamplePaseXml.getSpecifyElementText(message,"SendToName"));
        ms.setSubject(SamplePaseXml.getSpecifyElementText(message,"Subject"));
        ms.setMessageid(SamplePaseXml.getSpecifyElementText(message,"MessageID"));
        ms.setExternalmessageid(SamplePaseXml.getSpecifyElementText(message,"ExternalMessageID"));
        ms.setFlagged(SamplePaseXml.getSpecifyElementText(message,"Flagged"));
        ms.setRead(SamplePaseXml.getSpecifyElementText(message,"Read"));
        ms.setItemid(SamplePaseXml.getSpecifyElementText(message,"ItemID"));
        ms.setReplied(SamplePaseXml.getSpecifyElementText(message,"Replied"));
        String text=SamplePaseXml.getSpecifyElementText(message,"Text");
        if(StringUtils.isNotBlank(text)){
            text=text.replace("&lt;![CDATA["," ");
            text=text.replace("]]&gt;"," ");
            text=text.replace("<![CDATA[ "," ");
            text=text.replace("]]>"," ");
            ms.setTextHtml(StringEscapeUtils.escapeXml(text));
        }
        String ReceiveDate=SamplePaseXml.getSpecifyElementText(message,"ReceiveDate");
        Date date=DateUtils.returnDate(ReceiveDate);
        if(date!=null){
            ms.setReceivedate(date);
        }
        String ExpirationDate=SamplePaseXml.getSpecifyElementText(message,"ExpirationDate");
        Date date1=DateUtils.returnDate(ExpirationDate);
        if(date1!=null){
            ms.setExpirationdate(date1);
        }
        ms.setResponseenabled(SamplePaseXml.getSpecifyElementText(message,"ResponseDetails","ResponseEnabled"));
        String responseURL=SamplePaseXml.getSpecifyElementText(message, "ResponseDetails", "ResponseURL");
        if(StringUtils.isNotBlank(responseURL)&&responseURL.length()>1000){
            responseURL=responseURL.substring(0,1000);
        }
        if(StringUtils.isNotBlank(responseURL)){
            responseURL="";
        }
        ms.setResponseurl(responseURL);
        ms.setFolderid(SamplePaseXml.getSpecifyElementText(message,"Folder","FolderID"));
        return ms;
    }

    public static TradingMessageGetmymessage addDatabase(Element message,Long accountId,Long ebay) throws Exception {
       /* for(Element message:messages){*/
        TradingMessageGetmymessage ms=new TradingMessageGetmymessage();
        ms.setSender(SamplePaseXml.getSpecifyElementText(message,"Sender"));
        ms.setRecipientuserid(SamplePaseXml.getSpecifyElementText(message,"RecipientUserID"));
        ms.setSendtoname(SamplePaseXml.getSpecifyElementText(message,"SendToName"));
        String subject=SamplePaseXml.getSpecifyElementText(message,"Subject");
        if(StringUtils.isNotBlank(subject)) {
            if (subject.length() > 255) {
                subject = subject.substring(0, 255);
            }
            ms.setSubject(subject);
        }
        ms.setMessageid(SamplePaseXml.getSpecifyElementText(message,"MessageID"));
        ms.setExternalmessageid(SamplePaseXml.getSpecifyElementText(message,"ExternalMessageID"));
        ms.setFlagged(SamplePaseXml.getSpecifyElementText(message,"Flagged"));
        ms.setRead(SamplePaseXml.getSpecifyElementText(message,"Read"));
        ms.setItemid(SamplePaseXml.getSpecifyElementText(message,"ItemID"));
        ms.setReplied(SamplePaseXml.getSpecifyElementText(message,"Replied"));
        String ReceiveDate=SamplePaseXml.getSpecifyElementText(message,"ReceiveDate");
        Date date=DateUtils.returnDate(ReceiveDate);
        if(date!=null){
            ms.setReceivedate(date);
        }
        String ExpirationDate=SamplePaseXml.getSpecifyElementText(message,"ExpirationDate");
        Date date1=DateUtils.returnDate(ExpirationDate);
        if(date1!=null){
            ms.setExpirationdate(date1);
        }
        ms.setResponseenabled(SamplePaseXml.getSpecifyElementText(message,"ResponseDetails","ResponseEnabled"));
        String responseURL=SamplePaseXml.getSpecifyElementText(message, "ResponseDetails", "ResponseURL");
        if(StringUtils.isNotBlank(responseURL)&&responseURL.length()>1000){
            responseURL=responseURL.substring(0,1000);
        }
        if(StringUtils.isNotBlank(responseURL)){
            responseURL="";
        }
        ms.setResponseurl(responseURL);
        ms.setFolderid(SamplePaseXml.getSpecifyElementText(message,"Folder","FolderID"));
        ms.setLoginAccountId(accountId);
        ms.setEbayAccountId(ebay);
        return ms;
    }
    public static Map<String,String> apiAddmembermessage(Map<String,Object> m) throws Exception {
        UsercontrollerDevAccountExtend dev= (UsercontrollerDevAccountExtend) m.get("devAccount");
        TradingMessageAddmembermessage addmessage= (TradingMessageAddmembermessage) m.get("addMessage");
        UserInfoService userInfoService= (UserInfoService) m.get("userInfoService");
        Long ebayId= (Long) m.get("ebayId");
        String apiUrl= (String) m.get("url");
        dev.setApiCallName(APINameStatic.AddMemberMessageRTQ);
        String xml= BindAccountAPI.getAddMemberMessageRTQ(addmessage,userInfoService.getTokenByEbayID(ebayId));
        AddApiTask addApiTask = new AddApiTask();
       /* Map<String, String> resMap= addApiTask.exec(dev, xml, "https://api.ebay.com/ws/api.dll");*/
        Map<String, String> resMap= addApiTask.exec(dev, xml, apiUrl);
        String r1=resMap.get("stat");
        String res=resMap.get("message");
        Map map=new HashMap();
        if("fail".equalsIgnoreCase(r1)){
            map.put("msg","false");
            map.put("par","发送失败");
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        if("Success".equalsIgnoreCase(ack)){
            map.put("msg","true");
            map.put("par","发送成功");
        }else {
            map.put("msg","false");
            map.put("par","获取必要的参数失败！请稍后重试");
        }
        return map;
    }
    public static String getContent(Map m) throws Exception {
        UsercontrollerDevAccountExtend dev= (UsercontrollerDevAccountExtend) m.get("devAccount");
        String messageID= (String) m.get("messageId");
        UserInfoService userInfoService= (UserInfoService) m.get("userInfoService");
        Long ebayId= (Long) m.get("ebayId");
        String apiUrl= (String) m.get("url");
        dev.setApiCallName(APINameStatic.GetMyMessages);
        //测试环境
        String xml= BindAccountAPI.getGetMyMessagesByReturnHeader(messageID,userInfoService.getTokenByEbayID(ebayId));//获取接受消息
        //真实环境
        //String xml= BindAccountAPI.getGetMyMessagesByReturnHeader(messageID,"AgAAAA**AQAAAA**aAAAAA**5+zOVA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlIWnC5iEpAidj6x9nY+seQ**blACAA**AAMAAA**4YY9ZyYrV6rBr5Y51YDou86lPUDHdTfLXryrxIjp4NQwEAutmYswQ2TaLTKOw5SKphrKK0V5nsFlhlFwaYr121t24tsZMqkADpz3XwFSu3/ygi7aXwjvwdjK6yi0Npsweyh+sDRZk+r7l8MBjT4Wfqd6xrrDTNFamFPJbvRUcLj/WWL8elKbB2ibfMbN3cf5dt4RvL0+V+I8E+0fK7XtzOE51K91r21NwPcW2+2qjnmxmiDGt6QHrJRAUoE/L1hTAMlecnw6qjRecKBn1AbsgA9FFi63C28A46ZEAsIjv+dg0E1iDcjuoRvz+bsyRxUGIPD2j+8sLjUygqNopSuhyljqQA2JahIV18abDyE0vtUdGpn82a225kQ153+qeK5VrPbDiiHVfLedksnrtnO44ySOjsfa8PM7CAgiav8WQ/KtYtWtaGubW+z2r7zFAimku2k6temuD9FOlPOXM1Mx1UgX+bVYxs5KnMXBcm7ybaxmFB0xi2wyeNoz6Dbta1fmiPmhsAAbdDEtY3chPEEjsQRzdeGLTsbtiJjROTyEY4enD5UfMOXCXUIb6iBm1NNe/PRRSdxum4Pk9eosky37Bdzx59bXWDgvnkKjx6pwRGWgpLcRVAYxjkvkmTHcwoN0WBgRqZ7JPnuaWQDLkzjzS1/8zdQV0dyj7I4sI/8+kjjdPNa004iTVsjt2wdrzgLf1gJMc+kpCD97c1dUzbAe9Kp8/hL1W1VT5RcWk9JsuPK5IHMGlGKGBd2UffBP6V7e");//获取接受消息
        AddApiTask addApiTask = new AddApiTask();
        //Map<String, String> resMap= addApiTask.exec(dev, xml, "https://api.ebay.com/ws/api.dll");
        Map<String, String> resMap= addApiTask.exec(dev, xml, apiUrl);
        String r1=resMap.get("stat");
        String res=resMap.get("message");
        if("fail".equalsIgnoreCase(r1)){
            return null;
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        if("Success".equalsIgnoreCase(ack)||"Warning".equals(ack)){
            SessionVO sessionVO= SessionCacheSupport.getSessionVO();
            String text=getText(res);
            if(StringUtils.isNotBlank(text)){
                text=text.replace("&lt;![CDATA["," ");
                text=text.replace("]]&gt;"," ");
                text=text.replace("<![CDATA[ "," ");
                text=text.replace("]]>"," ");
            }
            return text;
        }
        /*if(!"Success".equalsIgnoreCase(ack)&& !"Warning".equalsIgnoreCase(ack)){
            String errorCode11=SamplePaseXml.getErrorCode(res);
            for(String messageA:EbayErrorCodeStatic.messageArray){
                if(messageA.equals(errorCode11)){
                    TempStoreDataSupport.removeData("ApiFail_"+APINameStatic.GetMyMessages);
                }
            }
        }*/
        return null;
    }
    public static String getContent1(Map m) throws Exception {
        UsercontrollerDevAccountExtend dev= (UsercontrollerDevAccountExtend) m.get("devAccount");
        String messageID= (String) m.get("messageId");
        UserInfoService userInfoService= (UserInfoService) m.get("userInfoService");
        Long ebayId= (Long) m.get("ebayId");
        String apiUrl= (String) m.get("url");
        dev.setApiCallName(APINameStatic.GetMyMessages);
        //测试环境
        String xml= BindAccountAPI.getGetMyMessagesByReturnHeader1(messageID,userInfoService.getTokenByEbayID(ebayId));//获取接受消息
        //真实环境
        //String xml= BindAccountAPI.getGetMyMessagesByReturnHeader(messageID,"AgAAAA**AQAAAA**aAAAAA**5+zOVA**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AFlIWnC5iEpAidj6x9nY+seQ**blACAA**AAMAAA**4YY9ZyYrV6rBr5Y51YDou86lPUDHdTfLXryrxIjp4NQwEAutmYswQ2TaLTKOw5SKphrKK0V5nsFlhlFwaYr121t24tsZMqkADpz3XwFSu3/ygi7aXwjvwdjK6yi0Npsweyh+sDRZk+r7l8MBjT4Wfqd6xrrDTNFamFPJbvRUcLj/WWL8elKbB2ibfMbN3cf5dt4RvL0+V+I8E+0fK7XtzOE51K91r21NwPcW2+2qjnmxmiDGt6QHrJRAUoE/L1hTAMlecnw6qjRecKBn1AbsgA9FFi63C28A46ZEAsIjv+dg0E1iDcjuoRvz+bsyRxUGIPD2j+8sLjUygqNopSuhyljqQA2JahIV18abDyE0vtUdGpn82a225kQ153+qeK5VrPbDiiHVfLedksnrtnO44ySOjsfa8PM7CAgiav8WQ/KtYtWtaGubW+z2r7zFAimku2k6temuD9FOlPOXM1Mx1UgX+bVYxs5KnMXBcm7ybaxmFB0xi2wyeNoz6Dbta1fmiPmhsAAbdDEtY3chPEEjsQRzdeGLTsbtiJjROTyEY4enD5UfMOXCXUIb6iBm1NNe/PRRSdxum4Pk9eosky37Bdzx59bXWDgvnkKjx6pwRGWgpLcRVAYxjkvkmTHcwoN0WBgRqZ7JPnuaWQDLkzjzS1/8zdQV0dyj7I4sI/8+kjjdPNa004iTVsjt2wdrzgLf1gJMc+kpCD97c1dUzbAe9Kp8/hL1W1VT5RcWk9JsuPK5IHMGlGKGBd2UffBP6V7e");//获取接受消息

        AddApiTask addApiTask = new AddApiTask();
        //Map<String, String> resMap= addApiTask.exec(dev, xml, "https://api.ebay.com/ws/api.dll");
        Map<String, String> resMap= addApiTask.exec(dev, xml, apiUrl);
        String r1=resMap.get("stat");
        String res=resMap.get("message");
        if("fail".equalsIgnoreCase(r1)){
            return null;
        }
        String ack = SamplePaseXml.getVFromXmlString(res, "Ack");
        if("Success".equalsIgnoreCase(ack)||"Warning".equals(ack)){
            SessionVO sessionVO= SessionCacheSupport.getSessionVO();
            String text=getText(res);
            if(StringUtils.isNotBlank(text)){
                text=text.replace("&lt;![CDATA["," ");
                text=text.replace("]]&gt;"," ");
                text=text.replace("<![CDATA[ "," ");
                text=text.replace("]]>"," ");
            }
            return text;
        }
        /*if(!"Success".equalsIgnoreCase(ack)&& !"Warning".equalsIgnoreCase(ack)){
            String errorCode11=SamplePaseXml.getErrorCode(res);
            for(String messageA:EbayErrorCodeStatic.messageArray){
                if(StringUtils.isNotBlank(messageA)&&messageA.equals(errorCode11)){
                    TempStoreDataSupport.removeData("ApiFail_"+APINameStatic.GetMyMessages);
                }
            }
        }*/
        return null;
    }
    public static String getText(String xml) throws Exception {
        Document document= SamplePaseXml.formatStr2Doc(xml);
        Element root= document.getRootElement();
        Element messages= root.element("Messages");
        Iterator iterator=messages.elementIterator("Message");
        String text="";
        while(iterator.hasNext()){
            Element message= (Element) iterator.next();
            Element Text=message.element("Text");
            if(Text!=null){
                text=Text.getTextTrim();
            }
        }
        return text;
    }
}
