package com.alibabasmt.utils.other;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2015/3/24.
 * 一些需要注入的值
 */
@Component
public class AutoValueBean {
    static Logger logger = Logger.getLogger(AutoValueBean.class);
    @Value("${SMT_APPKEY}")
    public String appKey;
    @Value("${SMT_APPSECRET}")
    public String appSecret;
    @Value("${SMT_AFTER_SIN_REDIRECT_URI}")
    public String sinAfterUrl;
    @Value("${SMT_WHEN_POST_HOST_URL}")
    public String sinWhenPostHostUrl;
    @Value("${SMT_TASK_LIST}")
    public String smtTaskList;
}
