package com.base.utils.scheduleabout.commontask;

import com.base.database.task.model.TaskGetOrdersSellerTransaction;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.common.MyStringUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.MainTaskStaticParam;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.TaskPool;
import com.task.service.IScheduleGetTimerOrders;
import com.task.service.ITaskGetOrdersSellerTransaction;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrtor on 2014/8/29.
 * 获取外部交易 定时同步
 */
public class SynchronizeGetOrdersSellerTransactionTimerTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetOrdersSellerTransactionTimerTaskRun.class);
    String mark="";
    public void synchronizeOrderSellerTrasaction(TaskGetOrdersSellerTransaction sellerTransaction){
        IScheduleGetTimerOrders iScheduleGetTimerOrders=(IScheduleGetTimerOrders) ApplicationContextUtil.getBean(IScheduleGetTimerOrders.class);
        try{
            iScheduleGetTimerOrders.synchronizeOrderSellerTrasaction(sellerTransaction);
        }catch (Exception e){
            logger.error("定时同步订单外部交易失败task:",e);
        }
    }
    @Override
    public void run(){
        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }


        List<TaskGetOrdersSellerTransaction> list=null;
        if (MainTaskStaticParam.CATCH_SELLER_TRANSACTION_QUEUE.isEmpty()){
            if (!"0".equalsIgnoreCase(this.mark)){
                return;
            }
            ITaskGetOrdersSellerTransaction iTaskGetOrdersSellerTransaction = (ITaskGetOrdersSellerTransaction) ApplicationContextUtil.getBean(ITaskGetOrdersSellerTransaction.class);
            list=iTaskGetOrdersSellerTransaction.selectTaskGetOrdersSellerTransactionByFlagIsFalseOrderBysaveTime();
            if (list==null || list.isEmpty()){return;}

            if(MainTaskStaticParam.CATCH_SELLER_TRANSACTION_QUEUE.isEmpty()){
                for (TaskGetOrdersSellerTransaction t : list){
                    try {
                        if(MainTaskStaticParam.CATCH_SELLER_TRANSACTION_QUEUE.contains(t)){continue;}
                        MainTaskStaticParam.CATCH_SELLER_TRANSACTION_QUEUE.put(t);
                    } catch (Exception e) {logger.error("放入case队列出错",e);continue;}
                }
            }

        }

        TaskGetOrdersSellerTransaction o=null;
        try {
            Iterator<TaskGetOrdersSellerTransaction> iterator=MainTaskStaticParam.CATCH_SELLER_TRANSACTION_QUEUE.iterator();
            while (iterator.hasNext()){
                if (MainTaskStaticParam.CATCH_SELLER_TRANSACTION_QUEUE.isEmpty()){break;}
                TaskGetOrdersSellerTransaction oo=MainTaskStaticParam.CATCH_SELLER_TRANSACTION_QUEUE.take();
                if (oo==null){continue;}

                Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType()+"_"+oo.getId());
                if (b){
                    //logger.error(getScheduledType()+oo.getId()+"===之前的帐号任务还未结束取下一个===");
                    continue;}
                o=oo;
                break;
            }
        } catch (Exception e) {}
        if(o==null){
            return;
        }
        Thread.currentThread().setName("thread_" + getScheduledType() + "_" + o.getId());
        synchronizeOrderSellerTrasaction(o);
        TaskPool.threadRunTime.remove("thread_" + getScheduledType()+"_"+o.getId());
        Thread.currentThread().setName("thread_" + getScheduledType()+"_"+o.getId()+ MyStringUtil.getRandomStringAndNum(5));


        /*int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }
        Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType());
        if(b){
            //logger.error(getScheduledType()+"===之前的任务还未完成继续等待下一个循环===");
            return;
        }
        Thread.currentThread().setName("thread_" + getScheduledType());

        ITaskGetOrdersSellerTransaction iTaskGetOrdersSellerTransaction = (ITaskGetOrdersSellerTransaction) ApplicationContextUtil.getBean(ITaskGetOrdersSellerTransaction.class);
        List<TaskGetOrdersSellerTransaction> sellerTransactionses=iTaskGetOrdersSellerTransaction.selectTaskGetOrdersSellerTransactionByFlagIsFalseOrderBysaveTime();
        if(sellerTransactionses!=null&&sellerTransactionses.size()>0){
            TaskGetOrdersSellerTransaction sellerTransaction=sellerTransactionses.get(0);
            synchronizeOrderSellerTrasaction(sellerTransaction);
        }
        TaskPool.threadRunTime.remove("thread_" + getScheduledType());
        Thread.currentThread().setName("thread_" + getScheduledType()+ MyStringUtil.getRandomStringAndNum(5));*/

    }

    /**只从集合记录取多少条*/
    private List<TradingOrderGetOrders> filterLimitList(List<TradingOrderGetOrders> tlist){

        return filterLimitListFinal(tlist,20);

        /*List<TaskGetOrders> x=new ArrayList<TaskGetOrders>();
        for (int i = 0;i<2;i++){
            x.add(tlist.get(i));
        }
        return x;*/
    }

    public SynchronizeGetOrdersSellerTransactionTimerTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONIZE_GET_ORDERS_SELLER_TRANSACTION_TIMER;
    }

    @Override
    public Integer crTimeMinu() {
     /*   ITaskGetOrders iTaskGetOrders = (ITaskGetOrders) ApplicationContextUtil.getBean(ITaskGetOrders.class);
        List<TaskGetOrders> list=iTaskGetOrders.selectTaskGetOrdersByFlagIsFalseOrderBysaveTime();
        if(list.size()>0&&list.size()<=50){
            return 60;
        }else{
            return 2;
        }*/
        return 5;
    }

    @Override
    public void setMark(String x) {
        this.mark=x;
    }

    @Override
    public String getMark() {
        return this.mark;
    }
}
