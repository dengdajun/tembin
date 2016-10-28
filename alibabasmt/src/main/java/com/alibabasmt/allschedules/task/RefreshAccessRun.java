package com.alibabasmt.allschedules.task;

import com.alibabasmt.allschedules.SMTTaskMainParm;
import com.base.database.publicd.model.PublicDataDict;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.httpclient.HttpClientUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.xmlutils.SamplePaseXml;
import com.test.service.Test1Service;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2014/12/23.
 */
public class RefreshAccessRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(RefreshAccessRun.class);

    String mark="";

    @Override
    public void setMark(String x) {
        this.mark=x;
    }

    @Override
    public String getMark() {
        return this.mark;
    }

    @Override
    public String getScheduledType() {
        return SMTTaskMainParm.SMT_REFRESH_ACCESSTOKEN;
    }

    @Override
    public Integer crTimeMinu() {
        Integer ii=SMTTaskMainParm.SOME_CRTIMEMINU.get(SMTTaskMainParm.SMT_REFRESH_ACCESSTOKEN);
        return (ii==null||ii==0)?10:ii;
    }

    @Override
    public void run() {

        System.out.println("acc=============");
    }




}
