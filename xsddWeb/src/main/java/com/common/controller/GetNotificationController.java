package com.common.controller;

/**
 * Created by Administrator on 2015/2/4.
 */

import com.base.database.task.mapper.TradingTaskXmlMapper;
import com.base.database.task.model.TradingTaskXml;
import com.base.utils.common.MyStringUtil;
import com.common.base.utils.ajax.AjaxResponse;
import com.sitemessage.service.EbayNoticeReceiveService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by Administrator on 2015/2/4.
 * 用于接收通知
 */
@Controller
public class GetNotificationController {
    static Logger logger = Logger.getLogger(GetNotificationController.class);


    @Autowired
    private EbayNoticeReceiveService noticeReceiveService;
    @Autowired
    private TradingTaskXmlMapper taskXmlMapper;//此处不需要事务，只记录不能识别的xml类型

    @RequestMapping("receiveNoti.do")
    @ResponseBody
    public void receiveNoti(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /**先遍历出http头*/
        // request.getHeaders 和 request.getHeaderNames
        // 获得所有头信息 key值集合
/*        Enumeration<String> enumeration1 = request.getHeaderNames();
        while (enumeration1.hasMoreElements()) {
            String key = enumeration1.nextElement();
            //System.out.println("头信息的key:" + key);
            Enumeration<String> enumeration2 = request.getHeaders(key);
            while (enumeration2.hasMoreElements()) {
                String value = enumeration2.nextElement();
               // logger.error("头信息" + key + ":" + value);
            }
        }*/

            InputStream input = request.getInputStream();
            StringBuffer recieveData = new StringBuffer();

            BufferedReader in = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                recieveData.append(inputLine);
            }

        if(recieveData==null || "".equalsIgnoreCase(recieveData.toString())){
            AjaxResponse.sendText(response, "200");
            return;
        }

        String eventName = MyStringUtil.getStringBetween2char(recieveData.toString(), "<NotificationEventName>", "</NotificationEventName>");
        TradingTaskXml taskXml=null;
        if (StringUtils.isBlank(eventName)){
            taskXml=new TradingTaskXml();
            taskXml.setCreateDate(new Date());
            taskXml.setXmlContent(recieveData.toString());
            taskXml.setTaskType("noticetest");

        }else {
            switch (eventName){
                case "MyMessagesM2MMessage":{
                    noticeReceiveService.processMyMessagesM2MMessage(recieveData.toString());
                    taskXml=new TradingTaskXml();
                    taskXml.setCreateDate(new Date());
                    taskXml.setXmlContent(recieveData.toString());
                    taskXml.setTaskType(eventName);
                    break;
                }
                case "MyMessageseBayMessage":{
                    noticeReceiveService.processMyMessagesM2MMessage(recieveData.toString());
                    taskXml=new TradingTaskXml();
                    taskXml.setCreateDate(new Date());
                    taskXml.setXmlContent(recieveData.toString());
                    taskXml.setTaskType(eventName);
                    break;
                }
                case "FixedPriceTransaction":{
                    noticeReceiveService.processFixedPriceTransaction(recieveData.toString());
                    taskXml=new TradingTaskXml();
                    taskXml.setCreateDate(new Date());
                    taskXml.setXmlContent(recieveData.toString());
                    taskXml.setTaskType(eventName);
                    break;
                }
                case "ItemSold":{
                    noticeReceiveService.processItemSold(recieveData.toString());
                    taskXml=new TradingTaskXml();
                    taskXml.setCreateDate(new Date());
                    taskXml.setXmlContent(recieveData.toString());
                    taskXml.setTaskType(eventName);
                    break;
                }
                default:{
                    taskXml=new TradingTaskXml();
                    taskXml.setCreateDate(new Date());
                    taskXml.setXmlContent(recieveData.toString());
                    taskXml.setTaskType(eventName);
                  //  taskXmlMapper.insert(taskXml);
                }
            }
        }

        //if (taskXml!=null){
           // taskXmlMapper.insert(taskXml);
        //}
           // logger.error(recieveData.toString());


        AjaxResponse.sendText(response, "200");

    }



}
