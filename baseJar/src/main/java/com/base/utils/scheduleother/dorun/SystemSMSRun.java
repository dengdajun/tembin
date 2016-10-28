package com.base.utils.scheduleother.dorun;

import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.MyStringUtil;
import com.base.utils.scheduleother.StaticParam;
import com.base.utils.scheduleother.service.SystemSMSService;
import com.base.utils.threadpool.TaskPool;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2015/6/15.
 * 短信任务
 */
public class SystemSMSRun implements ScheduleOtherBase{
    static Logger logger = Logger.getLogger(SystemSMSRun.class);


    @Override
    public String stype() {
        return StaticParam.SYSTEM_SMS;
    }

    @Override
    public Integer cyclesTime() {
        return 2;
    }

    @Override
    public void run() {
        String tname="thread_" + stype() + "_S";
        Boolean b= TaskPool.threadIsAliveByName(tname);
        if (b){
            return;}

        Thread.currentThread().setName(tname);

        SystemSMSService systemSMSService= ApplicationContextUtil.getBean(SystemSMSService.class);
        systemSMSService.checkAndSend();
        //systemSMSService.getSendResult();

        TaskPool.threadRunTime.remove(tname);
        Thread.currentThread().setName("_thread_" + stype()+"_S_"+ MyStringUtil.getRandomStringAndNum(5));
    }
}
