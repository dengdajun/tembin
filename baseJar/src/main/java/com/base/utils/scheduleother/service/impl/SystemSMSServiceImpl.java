package com.base.utils.scheduleother.service.impl;

import com.base.database.userinfo.mapper.SystemSmsLogMapper;
import com.base.database.userinfo.model.SystemSmsLog;
import com.base.database.userinfo.model.SystemSmsLogExample;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.scheduleother.service.SystemSMSService;
import com.base.utils.xmlutils.SamplePaseXml;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2015/6/15.
 * 短信服务
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemSMSServiceImpl implements SystemSMSService {
    static Logger logger = Logger.getLogger(SystemSMSServiceImpl.class);

    private static final String serverUrl="http://115.29.49.158/";
    private static final List<BasicNameValuePair> smsUserParam = new ArrayList<BasicNameValuePair>();
    static {
        smsUserParam.add(new BasicNameValuePair("userid","655"));
        smsUserParam.add(new BasicNameValuePair("account","HY_AtJob"));
        smsUserParam.add(new BasicNameValuePair("password","123456"));
    }


    @Autowired
    private SystemSmsLogMapper smsLogMapper;

    @Override
    public void checkAndSend(){
        SystemSmsLogExample example=new SystemSmsLogExample();
        example.setOrderByClause("id asc");
        example.createCriteria().andPostStatusEqualTo("S").andTaskIdIsNull();
        List<SystemSmsLog> smsLogs=smsLogMapper.selectByExample(example);

        if (ObjectUtils.isLogicalNull(smsLogs)){
            return;
        }

        /**发送短信的参数*/

        Set<String> regpNumber=new HashSet<>();//注册成功短信
        //List<String> regpNumber=new ArrayList<>();//注册成功短信
        List<Long> regpId=new ArrayList<>();//注册成功短信短信id
        String content="";
        for (SystemSmsLog sms:smsLogs){
            try {
            if (StringUtils.isBlank(sms.getPhone())&&
                    (sms.getPhone().length()!=11||sms.getPhone().length()!=13)){
                continue;
            }
            if ("reg".equalsIgnoreCase(sms.getSmsType())){
                regpNumber.add(sms.getPhone());
                regpId.add(sms.getId());
                if (StringUtils.isBlank(content)){
                    content=sms.getContent();
                }
            }  else {
                List<BasicNameValuePair> smsUserParam1 = new ArrayList<BasicNameValuePair>();
                smsUserParam1.add(new BasicNameValuePair("action","send"));
                smsUserParam1.add(new BasicNameValuePair("sendTime",""));
                smsUserParam1.add(new BasicNameValuePair("extno", ""));
                smsUserParam1.addAll(smsUserParam);
                smsUserParam1.add(new BasicNameValuePair("mobile", sms.getPhone()));
                smsUserParam1.add(new BasicNameValuePair("content",sms.getContent()));
                String xml = post("sms.aspx", smsUserParam1);
                if (StringUtils.isBlank(xml)){continue;}
                    afterPostStep1(xml, sms.getId());
            }

        } catch (Exception e) {
                logger.error(e);
            continue;
        }
        }
        if (!ObjectUtils.isLogicalNull(regpNumber)){
            List<BasicNameValuePair> smsUserParam1 = new ArrayList<BasicNameValuePair>();
            smsUserParam1.add(new BasicNameValuePair("action","send"));
            smsUserParam1.add(new BasicNameValuePair("sendTime",""));
            smsUserParam1.add(new BasicNameValuePair("extno", ""));
            smsUserParam1.addAll(smsUserParam);
            String ps=regpNumber.toString().replace("[","").replace("]","").replace(" ","");
            smsUserParam1.add(new BasicNameValuePair("mobile", ps));
            smsUserParam1.add(new BasicNameValuePair("content",content));
            String xml = post("sms.aspx",smsUserParam1);

            try {
                afterPostStep1(xml, regpId);
            } catch (Exception e) {
                logger.error(e);
            }
        }



    }





    //post
    private String post(String api,List<BasicNameValuePair> valuePairs) {
        HttpClient httpClient= HttpClientUtil.getHttpClient();
        String res="";
        try {
            res = HttpClientUtil.postWithParam(httpClient,
                    serverUrl+api,
                    valuePairs,
                    null, "UTF-8", null);
        } catch (Exception e) {
            logger.error(e);
        }finally {
            return res;
        }
    }


    private void afterPostStep1(String xml,Long phones) throws Exception{
        List<Long> ids=new ArrayList<>();
        ids.add(phones);
        afterPostStep1(xml,ids);
    }
    //解析提交成功后的xml
    private void afterPostStep1(String xml,List<Long> phones) throws Exception {
        if (StringUtils.isBlank(xml)) {return;
        }

        String st = SamplePaseXml.getSpecifyElementTextAllInOne(xml, "returnstatus");
        String message = SamplePaseXml.getSpecifyElementTextAllInOne(xml, "message");
        String remainpoint = SamplePaseXml.getSpecifyElementTextAllInOne(xml, "remainpoint");
        String taskID = SamplePaseXml.getSpecifyElementTextAllInOne(xml, "taskID");
        if ("Faild".equalsIgnoreCase(st)) {
            logger.error("发送短信失败！" + message);
        }
        SystemSmsLog sms = new SystemSmsLog();
        sms.setUpdateTime(new Date());
        sms.setPostStatus(StringUtils.isBlank(st)?"D":st);
        sms.setTaskId(taskID);
        SystemSmsLogExample example = new SystemSmsLogExample();
        example.createCriteria().andIdIn(phones);
        smsLogMapper.updateByExampleSelective(sms, example);
    }


    @Override
    /**获取发送结果*/
    public void getSendResult(){
        List<BasicNameValuePair> smsUserParam1 = new ArrayList<BasicNameValuePair>();
        smsUserParam1.add(new BasicNameValuePair("action","query"));
        smsUserParam1.addAll(smsUserParam);
        String xml=post("statusApi.aspx", smsUserParam1);
        if (StringUtils.isBlank(xml)){return;}
        Document document= null;
        try {
            document = SamplePaseXml.formatStr2Doc(xml);
        } catch (Exception e) {
            logger.error(e);
            return;
        }
        if (document==null){return ;}
        Element rootElt = document.getRootElement();
        List<Element> elementList = rootElt.elements("statusbox");
        if (ObjectUtils.isLogicalNull(elementList)){return;}
        for (Element e:elementList){
            try {
                String phone = e.element("mobile").getTextTrim();
                String taskid = e.element("taskid").getTextTrim();
                String status = e.element("status").getTextTrim();//状态报告----10：发送成功，20：发送失败
                if ("20".equalsIgnoreCase(status)){
                    String errorcode = e.element("errorcode").getTextTrim();
                    logger.error("错误代码>>"+phone+">>"+errorcode);
                }
                SystemSmsLog sms = new SystemSmsLog();
                sms.setUpdateTime(new Date());
                sms.setSendStatus(status);
                //sms.setTaskId(taskid);
                SystemSmsLogExample example = new SystemSmsLogExample();
                example.createCriteria().andTaskIdEqualTo(taskid).andPhoneEqualTo(phone);
                smsLogMapper.updateByExampleSelective(sms, example);

            }catch (Exception e1){
                logger.error("获取状态报告报错！"+xml);
                continue;
            }

        }
    }

    @Override
    public void insertSMS(SystemSmsLog sms){
        Date csd=TempStoreDataSupport.pullData("sendSMSTime_"+sms.getPhone());
        if (csd!=null){
            logger.error(sms.getPhone()+"短信发送出现异常频率太高"+csd);
            return;
        }

        sms.setContent("【TemBin】"+sms.getContent());
        sms.setCreateTime(new Date());
        smsLogMapper.insertSelective(sms);
        TempStoreDataSupport.pushDataByIdelTime("sendSMSTime_" + sms.getPhone(), new Date(), 2);
    }


    public static void main(String[] args) {
        List<String> x=new ArrayList<>();
        x.add("123");
        x.add("456");
        x.add("789");

        System.out.println( x.toString().replace(" ",""));
    }
}
