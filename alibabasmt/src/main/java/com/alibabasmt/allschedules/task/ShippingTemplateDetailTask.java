package com.alibabasmt.allschedules.task;

import com.alibabasmt.allschedules.SMTTaskMainParm;
import com.alibabasmt.allservices.shipping.service.ISmtShippingTemplate;
import com.alibabasmt.allservices.shipping.service.ISmtShippingTemplateDetail;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.MyStringUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskPool;
import org.apache.commons.lang.StringUtils;


/**
 * Created by Administrtor on 2015/3/31.
 */
public class ShippingTemplateDetailTask extends BaseScheduledClass implements Scheduledable {
    @Override
    public String getScheduledType() {
        return SMTTaskMainParm.SMT_SHIPPING_TEMPLATE_DETAIL;
    }

    @Override
    public Integer crTimeMinu() {
        Integer ii=SMTTaskMainParm.SOME_CRTIMEMINU.get(SMTTaskMainParm.SMT_SHIPPING_TEMPLATE_DETAIL);
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

        ISmtShippingTemplateDetail iSmtShippingTemplateDetail = (ISmtShippingTemplateDetail) ApplicationContextUtil.getBean(ISmtShippingTemplateDetail.class);
        iSmtShippingTemplateDetail.taskShippingTemplate();

        TaskPool.threadRunTime.remove("thread_" + getScheduledType());
        Thread.currentThread().setName("thread_" + getScheduledType()+ MyStringUtil.getRandomStringAndNum(5));
    }
}
