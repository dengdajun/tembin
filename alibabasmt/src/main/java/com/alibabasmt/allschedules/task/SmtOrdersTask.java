package com.alibabasmt.allschedules.task;

import com.alibabasmt.allschedules.SMTTaskMainParm;
import com.alibabasmt.allservices.task.service.ITaskSmtOrder;
import com.alibabasmt.database.smtaccount.mapper.SmtUserAccountMapper;
import com.alibabasmt.database.smtaccount.model.SmtUserAccount;
import com.alibabasmt.database.smtaccount.model.SmtUserAccountExample;
import com.alibabasmt.database.smttask.model.TaskSmtOrder;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.DateUtils;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.Scheduledable;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrtor on 2015/4/10.
 */
public class SmtOrdersTask extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SmtOrdersTask.class);
    @Override
    public String getScheduledType() {
        return SMTTaskMainParm.SMT_ORDERS_TASK;
    }

    @Override
    public Integer crTimeMinu() {
        return null;
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
        SmtUserAccountMapper ueam = (SmtUserAccountMapper) ApplicationContextUtil.getBean(SmtUserAccountMapper.class);
        SmtUserAccountExample ueame = new SmtUserAccountExample();
        List<SmtUserAccount> liue = ueam.selectByExample(ueame);
        Date date=new Date();
        saveTaskSmtOrders(liue, date);
    }
    public void saveTaskSmtOrders(List<SmtUserAccount> smtUserAccounts,Date date){
        ITaskSmtOrder iTaskSmtOrder = (ITaskSmtOrder) ApplicationContextUtil.getBean(ITaskSmtOrder.class);
        try{
            for(SmtUserAccount smtUserAccount:smtUserAccounts){
                Date date2= DateUtils.addDays(date, 1);
                Date date1=DateUtils.subDays(date2, 7);
                Date end1= com.base.utils.common.DateUtils.turnToDateEnd(date2);
                Date start1=com.base.utils.common.DateUtils.turnToDateStart(date1);
                String start= DateUtils.DateToString1(start1);
                String end=DateUtils.DateToString1(end1);
                TaskSmtOrder taskSmtOrder=new TaskSmtOrder();
                taskSmtOrder.setTokenflag(0);
                taskSmtOrder.setFromtime(start);
                taskSmtOrder.setTotime(end);
                taskSmtOrder.setSavetime(date);
                taskSmtOrder.setUserid(Long.valueOf(smtUserAccount.getOrgId()));
                taskSmtOrder.setSmtAmountId(smtUserAccount.getId());
                taskSmtOrder.setNewuserflag(null);
                iTaskSmtOrder.saveTaskSmtOrder(taskSmtOrder);
            }
        }catch (Exception e){
            logger.error("定时每天插入账号去获取订单出错:"+e.getMessage(),e);
        }
    }
}
