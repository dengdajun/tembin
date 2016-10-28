package com.base.utils.mailUtil;

import com.base.database.userinfo.model.SystemSmsLog;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.scheduleother.service.SystemSMSService;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2015/6/15.
 * 短信发送工具
 */
public class SMSUtils {
    static Logger logger = Logger.getLogger(SMSUtils.class);

    /**发送注册提醒短信*/
    public static void sendREGSMS(String phone,String content){
        Integer i=TempStoreDataSupport.pullData(phone+"reg");
        if (i==null||i==0){i=1;}else {
            i=i+1;
        }
        TempStoreDataSupport.pushDataByTime(phone+"reg",i,3600);
        if (i>3){
            logger.error(phone+"发送短信异常！===");
            return;
        }

        SystemSmsLog sms=new SystemSmsLog();
        sms.setPhone(phone);
        sms.setContent(content);
        sms.setPostStatus("S");
        sms.setSmsType("reg");
        SystemSMSService smsService= ApplicationContextUtil.getBean(SystemSMSService.class);
        smsService.insertSMS(sms);
    }

    public static void sendSMS(String phone,String content,String type){
        Integer i=TempStoreDataSupport.pullData(phone+type);
        if (i==null||i==0){i=1;}else {
            i=i+1;
        }
        TempStoreDataSupport.pushDataByTime(phone+type,i,3600);
        if (i>3){
            logger.error(phone+"发送短信异常！===");
            return;
        }

        SystemSmsLog sms=new SystemSmsLog();
        sms.setPhone(phone);
        sms.setContent(content);
        sms.setPostStatus("S");
        sms.setSmsType(type);
        SystemSMSService smsService= ApplicationContextUtil.getBean(SystemSMSService.class);
        smsService.insertSMS(sms);
    }
}
