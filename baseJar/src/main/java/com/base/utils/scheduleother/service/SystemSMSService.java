package com.base.utils.scheduleother.service;

import com.base.database.userinfo.model.SystemSmsLog;

/**
 * Created by Administrator on 2015/6/15.
 */
public interface SystemSMSService {
    void checkAndSend();

    /**获取发送结果*/
    void getSendResult();

    void insertSMS(SystemSmsLog sms);
}
