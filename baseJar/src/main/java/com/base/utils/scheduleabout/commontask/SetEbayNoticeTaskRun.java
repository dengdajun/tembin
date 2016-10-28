package com.base.utils.scheduleabout.commontask;

import com.base.database.trading.mapper.UsercontrollerEbayAccountMapper;
import com.base.database.trading.model.UsercontrollerEbayAccount;
import com.base.database.trading.model.UsercontrollerEbayAccountExample;
import com.base.domains.userinfo.UsercontrollerDevAccountExtend;
import com.base.sampleapixml.APINameStatic;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.CommAutowiredClass;
import com.base.utils.common.MyStringUtil;
import com.base.utils.common.ObjectUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.MainTaskStaticParam;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.AddApiTask;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/2/9.
 * 设置ebay帐号的notice通知功能
 */
public class SetEbayNoticeTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SetEbayNoticeTaskRun.class);
    @Override
    public String getScheduledType() {
        return MainTask.SET_EBAY_NOTICE_TASK;
    }

    @Override
    public Integer crTimeMinu() {
        return MainTaskStaticParam.SOME_CRTIMEMINU.get(MainTask.SET_EBAY_NOTICE_TASK);
    }

    @Override
    public void setMark(String x) {

    }

    @Override
    public String getMark() {
        return null;
    }

    @Override
    public void run() {
        CommAutowiredClass commPars = (CommAutowiredClass) ApplicationContextUtil.getBean(CommAutowiredClass.class);//获取注入的参数
        UsercontrollerEbayAccountMapper ueam = (UsercontrollerEbayAccountMapper) ApplicationContextUtil.getBean(UsercontrollerEbayAccountMapper.class);
        UsercontrollerEbayAccountExample ueame = new UsercontrollerEbayAccountExample();
        ueame.createCriteria().andConfEbayNoticeIsNull().andEbayStatusEqualTo("1");
        List<UsercontrollerEbayAccount> liue = ueam.selectByExampleWithBLOBs(ueame);
        if (ObjectUtils.isLogicalNull(liue)){
            return;
        }

        for (UsercontrollerEbayAccount uea:liue){
            String xml=getXML(uea.getEbayToken());
            AddApiTask addApiTask = new AddApiTask();
            UsercontrollerDevAccountExtend d = new UsercontrollerDevAccountExtend();
            d.setApiCallName(APINameStatic.SetNotificationPreferences);
            d.setApiSiteid("0");
            String res="";
            try {
                Map<String, String> resMap = addApiTask.exec2(d, xml, commPars.apiUrl);
                res=resMap.get("message");
                String r1 = resMap.get("stat");
                if(StringUtils.isBlank(r1)||"fail".equalsIgnoreCase(r1)){
                    continue;
                }
                String ack= MyStringUtil.getStringBetween2char(res,"<Ack>","</Ack>");
                if("Success".equalsIgnoreCase(ack)) {
                    UsercontrollerEbayAccount ut=new UsercontrollerEbayAccount();
                    ut.setId(uea.getId());
                    ut.setConfEbayNotice("1");
                    ueam.updateByPrimaryKeySelective(ut);
                }

            } catch (Exception e) {
                logger.error("设置消息通知失败!",e);
                continue;
            }
        }
    }





    /**获取设置的xml*/
    private String getXML(String token){
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<SetNotificationPreferencesRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n" +
                "<RequesterCredentials>\n" +
                "<eBayAuthToken>"+token+"</eBayAuthToken>\n" +
                "</RequesterCredentials>\n" +
                "<ApplicationDeliveryPreferences>\n" +
                "<ApplicationURL>http://task.tembin.com:8080/xsddWeb/receiveNoti.do</ApplicationURL>\n" +
                "<ApplicationEnable>Enable</ApplicationEnable>\n" +
                "</ApplicationDeliveryPreferences>\n" +
                "<UserDeliveryPreferenceArray>\n" +
                "<NotificationEnable>\n" +
                "<EventType>MyMessageseBayMessage</EventType>\n" +
                "<EventEnable>Enable</EventEnable>\n" +
                "</NotificationEnable>\n" +
                "<NotificationEnable>\n" +
                "<EventType>MyMessagesM2MMessage</EventType>\n" +
                "<EventEnable>Enable</EventEnable>\n" +
                "</NotificationEnable>\n" +
                "<NotificationEnable>\n" +
                "<EventType>EBNOrderCanceled</EventType>\n" +
                "<EventEnable>Enable</EventEnable>\n" +
                "</NotificationEnable>\n" +
                "<NotificationEnable>\n" +
                "<EventType>FixedPriceTransaction</EventType>\n" +
                "<EventEnable>Enable</EventEnable>\n" +
                "</NotificationEnable>\n" +
                "<NotificationEnable>\n" +
                "<EventType>ItemSold</EventType>\n" +
                "<EventEnable>Enable</EventEnable>\n" +
                "</NotificationEnable>\n" +
                "<NotificationEnable>\n" +
                "<EventType>ItemSold</EventType>\n" +
                "<EventEnable>Enable</EventEnable>\n" +
                "</NotificationEnable>\n" +
                "</UserDeliveryPreferenceArray>\n" +
                "</SetNotificationPreferencesRequest>" ;

        return xml;
    }
}
