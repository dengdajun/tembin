package com.alibabasmt.allschedules.task;

import com.alibabasmt.allschedules.SMTTaskMainParm;
import com.alibabasmt.allservices.productlisting.service.ISmtProductListing;
import com.alibabasmt.allservices.shipping.service.ISmtShippingTemplateDetail;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.MyStringUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskPool;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrtor on 2015/3/31.
 */
public class SmtProductListingTask extends BaseScheduledClass implements Scheduledable {
    @Override
    public String getScheduledType() {
        return SMTTaskMainParm.SMT_PRODUCT_LISTING;
    }

    @Override
    public Integer crTimeMinu() {
        Integer ii=SMTTaskMainParm.SOME_CRTIMEMINU.get(SMTTaskMainParm.SMT_PRODUCT_LISTING);
        return ii;
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
        String isRunging = TempStoreDataSupport.pullData("task_" + getScheduledType());
        if(StringUtils.isNotEmpty(isRunging)){return;}
        TempStoreDataSupport.pushData("task_" + getScheduledType(), "x");

        ISmtProductListing iSmtProductListing = (ISmtProductListing) ApplicationContextUtil.getBean(ISmtProductListing.class);
        Map m = new HashMap();
        m.put("0","onSelling");
        m.put("1","offline");
        m.put("2","auditing");
        m.put("3","editingRequired");
        for(int i=0;i<4;i++){
            iSmtProductListing.taskProductList(1L,m.get(i+"")+"");
        }
        TaskPool.threadRunTime.remove("thread_" + getScheduledType());
        Thread.currentThread().setName("thread_" + getScheduledType()+ MyStringUtil.getRandomStringAndNum(5));
    }
}
