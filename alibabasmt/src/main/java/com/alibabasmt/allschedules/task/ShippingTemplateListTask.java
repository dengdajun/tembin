package com.alibabasmt.allschedules.task;

import com.alibabasmt.allschedules.SMTTaskMainParm;
import com.alibabasmt.allservices.shipping.service.ISmtShippingTemplate;
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
public class ShippingTemplateListTask  extends BaseScheduledClass implements Scheduledable {
    @Override
    public String getScheduledType() {
        return SMTTaskMainParm.SMT_SHIPPING_TEMPLATE_LIST;
    }

    @Override
    public Integer crTimeMinu() {
        Integer ii=SMTTaskMainParm.SOME_CRTIMEMINU.get(SMTTaskMainParm.SMT_SHIPPING_TEMPLATE_LIST);
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

        ISmtShippingTemplate iSmtShippingTemplate = (ISmtShippingTemplate) ApplicationContextUtil.getBean(ISmtShippingTemplate.class);
        iSmtShippingTemplate.saveShippingTemplateTask(1L);

        TaskPool.threadRunTime.remove("thread_" + getScheduledType());
        Thread.currentThread().setName("thread_" + getScheduledType()+ MyStringUtil.getRandomStringAndNum(5));
    }
}
