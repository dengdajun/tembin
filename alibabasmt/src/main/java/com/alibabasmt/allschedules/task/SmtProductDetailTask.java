package com.alibabasmt.allschedules.task;

import com.alibabasmt.allschedules.SMTTaskMainParm;
import com.alibabasmt.allservices.product.service.IAlieProduct;
import com.alibabasmt.allservices.productlisting.service.ISmtProductListing;
import com.alibabasmt.database.smtproduct.model.SmtProductListing;
import com.base.mybatis.page.Page;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.MyStringUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskPool;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrtor on 2015/3/31.
 */
public class SmtProductDetailTask extends BaseScheduledClass implements Scheduledable {

    static Logger logger = Logger.getLogger(SmtProductDetailTask.class);
    @Override
    public String getScheduledType() {
        return SMTTaskMainParm.SMT_PRODUCT_LISTING_DETAIL;
    }

    @Override
    public Integer crTimeMinu() {
        Integer ii=SMTTaskMainParm.SOME_CRTIMEMINU.get(SMTTaskMainParm.SMT_PRODUCT_LISTING_DETAIL);
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
        IAlieProduct iAlieProduct = (IAlieProduct) ApplicationContextUtil.getBean(IAlieProduct.class);
        Map map = new HashMap();
        Page page = new Page();
        page.setPageSize(20);
        page.setCurrentPage(1);
        List<SmtProductListing> list = iSmtProductListing.selectByProductQueryList(map,page);
        if(list!=null&&list.size()>0){
            for(SmtProductListing spl:list){
                try {
                    iAlieProduct.saveSynchronizeProduct(spl.getProductid(), spl.getCreateUser(),spl);
                }catch(Exception e){
                    logger.error("保存数据失败：",e);
                    continue;
                }
            }
        }
        TempStoreDataSupport.removeData("task_" + getScheduledType());
        TaskPool.threadRunTime.remove("thread_" + getScheduledType());
        Thread.currentThread().setName("thread_" + getScheduledType()+ MyStringUtil.getRandomStringAndNum(5));
    }
}
