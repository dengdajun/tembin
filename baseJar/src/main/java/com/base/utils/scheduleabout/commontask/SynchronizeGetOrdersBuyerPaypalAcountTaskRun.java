package com.base.utils.scheduleabout.commontask;

import com.base.aboutpaypal.domain.PaypalVO;
import com.base.aboutpaypal.service.PayPalService;
import com.base.database.trading.model.TradingOrderGetOrders;
import com.base.database.trading.model.TradingOrderGetSellerTransactions;
import com.base.database.trading.model.UsercontrollerPaypalAccount;
import com.base.domains.querypojos.OrderGetOrdersQuery;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.cache.TempStoreDataSupport;
import com.base.utils.common.MyStringUtil;
import com.base.utils.scheduleabout.BaseScheduledClass;
import com.base.utils.scheduleabout.MainTask;
import com.base.utils.scheduleabout.MainTaskStaticParam;
import com.base.utils.scheduleabout.Scheduledable;
import com.base.utils.threadpool.EbayErrorCodeStatic;
import com.base.utils.threadpool.TaskPool;
import com.trading.service.ITradingOrderGetOrders;
import com.trading.service.ITradingOrderGetOrdersNoTransaction;
import com.trading.service.ITradingOrderGetSellerTransactions;
import com.trading.service.IUsercontrollerEbayAccount;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by Administrtor on 2014/8/29.
 * 定时同步buyer paypal账号 //两分钟
 */
public class SynchronizeGetOrdersBuyerPaypalAcountTaskRun extends BaseScheduledClass implements Scheduledable {
    static Logger logger = Logger.getLogger(SynchronizeGetOrdersBuyerPaypalAcountTaskRun.class);
    String mark;
    public void synchronizeBuyerPaypalAcount(List<TradingOrderGetOrders> orders){

        ITradingOrderGetSellerTransactions iTradingOrderGetSellerTransactions=(ITradingOrderGetSellerTransactions) ApplicationContextUtil.getBean(ITradingOrderGetSellerTransactions.class);
        IUsercontrollerEbayAccount iUsercontrollerEbayAccount=(IUsercontrollerEbayAccount)ApplicationContextUtil.getBean(IUsercontrollerEbayAccount.class);
        PayPalService payPalService=(PayPalService)ApplicationContextUtil.getBean(PayPalService.class);
        ITradingOrderGetOrdersNoTransaction iTradingOrderGetOrdersNoTransaction=(ITradingOrderGetOrdersNoTransaction)ApplicationContextUtil.getBean(ITradingOrderGetOrdersNoTransaction.class);
        for(TradingOrderGetOrders order:orders){
            /**如果paypal未验证的，不做查询*/
            String keyi="check_paypal_black_bill"+order.getSelleremail();
            String keyi1="check_paypal_black_bill1"+order.getSelleremail();

            List<TradingOrderGetSellerTransactions> transactionses=iTradingOrderGetSellerTransactions. selectTradingOrderGetSellerTransactionsByTransactionId(order.getItemid(),order.getTransactionid());
            if(transactionses!=null&&transactionses.size()>0){
                    Map map3 =new HashMap();
                    if(StringUtils.isNotBlank(transactionses.get(0).getExternaltransactionid())){
                        map3.put("transactionID", transactionses.get(0).getExternaltransactionid());
                        map3.put("paypalEmail",order.getSelleremail());
                        PaypalVO acc=null;
                        try {
                            Map map = payPalService.getTransactionDetails(map3);
                            if (map.isEmpty() || map.get("paypal")==null){
                                TempStoreDataSupport.pushDataByTime(keyi, "black", 3600);
                                logger.error("paypal账号未验证SynchronizeGetOrdersBuyerPaypalAcountTaskRun:订单号:"+order.getOrderid());
                                continue;
                            }
                            acc= (PaypalVO) map.get("paypal");
                            String res=(String) map.get("res");
                            String xml=(String) map.get("xml");
                            if(acc==null){
                                logger.error("paypal账号未验证SynchronizeGetOrdersBuyerPaypalAcountTaskRun:订单号:"+order.getOrderid());
                            }else if("Failure".equals(acc.getAck())){
                                //==============如果失败了没有权限=================
                                UsercontrollerPaypalAccount account= (UsercontrollerPaypalAccount) map.get("account");
                                if (EbayErrorCodeStatic.PermissiondeniedErr.equalsIgnoreCase(acc.getErrCode())
                                ||EbayErrorCodeStatic.transactioncouldnotbeloaded.equalsIgnoreCase(acc.getErrCode())){
                                    //logger.error("GetOrderPaypal放入暂停名单>>>>>>>>"+acc.getErrCode()+">>"+keyi1);
                                    Integer time=EbayErrorCodeStatic.small_err.get("paypal_" + acc.getErrCode());
                                    TempStoreDataSupport.pushDataByTime(keyi1,"black",(time==null?50:time));
                                }
                                logger.error("SynchronizeGetOrdersBuyerPaypalAcountTaskRun获取paypal费用失败paypalAmount.do,账号:"+account.getPaypalAccount()+"订单号:"+order.getOrderid()+"XML:"+xml);
                            }else if(!StringUtils.isNotBlank(res)){
                                logger.error("调用paypalAPI返回的res为空");
                                transactionses.get(0).setPaypalprice("");
                            }else{
                                order.setBuyerpayalacount(acc.getPayer());
                                order.setPaypalname(acc.getPaypalname());
                                order.setPaypalcountryname(acc.getPaypalcountryname());
                                order.setPaypalstreet1(acc.getPaypalstreet1());
                                order.setPaypalstreet2(acc.getPaypalstreet2());
                                order.setPaypalpostalcode(acc.getPaypalpostalcode());
                                order.setPaypalcityname(acc.getPaypalcityname());
                                TaskPool.togos.put(order);
                            }
                        }catch(Exception e){
                            logger.error("SynchronizeGetOrdersBuyerPaypalAcountTaskRun paypalAmount.do获取paypal费用失败,订单号:"+order.getOrderid(),e);
                        }
                    }
            }
        }
        if("0".equals(TaskPool.togosBS[0])){
            try {
                iTradingOrderGetOrdersNoTransaction.saveOrderGetOrders(null);
            } catch (Exception e) {
                logger.error("SynchronizeGetOrdersBuyerPaypalAcountTaskRun iTradingOrderGetOrdersNoTransaction保存失败", e);
            }
        }
    }
    @Override
    public void run(){
        int i= TaskPool.scheduledThreadPoolTaskExecutor.getActiveCount();
        if(i>30){
            return;
        }
        int tnumber = TaskPool.countThreadNumByName("thread_" + getScheduledType());
        if(tnumber>10){logger.error("当前执行获取交易费信息的线程过多...");return;}
        if(MainTaskStaticParam.CATCH_OEDER_BUYER_PAYPAL_ACCOUNT_QUEUE.isEmpty()){
            if (!"0".equalsIgnoreCase(this.mark)){
                return;
            }
            ITradingOrderGetOrders iTradingOrderGetOrders=(ITradingOrderGetOrders) ApplicationContextUtil.getBean(ITradingOrderGetOrders.class);
            //todo 记得限制每次获取数据的数量
            List<OrderGetOrdersQuery> orders1=iTradingOrderGetOrders.selectOrderGetOrdersByBuyerPaypalAcountIsNull();
            List<TradingOrderGetOrders> orders=new ArrayList<TradingOrderGetOrders>();
            if(orders1!=null&&orders1.size()>0){
                orders.addAll(orders1);
            }
            if (orders==null || orders.isEmpty()){return;}
            if(MainTaskStaticParam.CATCH_OEDER_BUYER_PAYPAL_ACCOUNT_QUEUE.isEmpty()){
                for (TradingOrderGetOrders order:orders){
                    if(MainTaskStaticParam.CATCH_OEDER_BUYER_PAYPAL_ACCOUNT_QUEUE.size()>=2000){break;}
                    try {
                        if (MainTaskStaticParam.CATCH_OEDER_BUYER_PAYPAL_ACCOUNT_QUEUE.contains(order)){logger.error("队列中已有"); continue;}
                        Boolean b= TaskPool.threadIsAliveByName("thread_" + getScheduledType() + "_" + order.getId());
                        if (b){logger.error(getScheduledType()+order.getId()+"===之前的买家paypal帐号任务还未结束不放入===");continue;}
                        MainTaskStaticParam.CATCH_OEDER_BUYER_PAYPAL_ACCOUNT_QUEUE.put(order);
                    } catch (Exception e) {logger.error("放入买家paypal帐号队列出错",e);continue;}
                }
            }
        }else {
            //logger.error("线程编号不为0,执行====");
        }

        if( MainTaskStaticParam.CATCH_OEDER_BUYER_PAYPAL_ACCOUNT_QUEUE.isEmpty()){return;}
        // List<TradingOrderGetOrders> ord=new ArrayList<TradingOrderGetOrders>();
        Iterator<TradingOrderGetOrders> iterator=MainTaskStaticParam.CATCH_OEDER_BUYER_PAYPAL_ACCOUNT_QUEUE.iterator();
        int ix=0;
        while (iterator.hasNext()){
            if(MainTaskStaticParam.CATCH_OEDER_BUYER_PAYPAL_ACCOUNT_QUEUE.isEmpty()||ix>=50){break;}
            TradingOrderGetOrders oo=null;
            try {
                oo=MainTaskStaticParam.CATCH_OEDER_BUYER_PAYPAL_ACCOUNT_QUEUE.take();
                //====================
                String keyi="check_paypal_black_bill"+oo.getSelleremail();
                String keyi1="check_paypal_black_bill1"+oo.getSelleremail();
                String icheck=TempStoreDataSupport.pullData(keyi);
                if (StringUtils.isNotBlank(icheck) && "black".equalsIgnoreCase(icheck)){
                    continue;
                }

                String checkerr=TempStoreDataSupport.pullData(keyi1);

                if (StringUtils.isNotBlank(checkerr)){
                    //logger.error("GetOrderPaypal存在于暂停名单>>>"+keyi1);
                    continue;
                }
                //======================
                if (oo==null){continue;}
            } catch (Exception e) {continue;}
            String threadName="thread_" + getScheduledType() + "_" + oo.getId();

            Boolean b= TaskPool.threadIsAliveByName(threadName);
            if (b){logger.error(getScheduledType()+oo.getId()+"===之前的买家paypal帐号任务还未结束取下一个===");continue;}
            Thread.currentThread().setName(threadName);
            List<TradingOrderGetOrders> ord=new ArrayList<TradingOrderGetOrders>();
            ord.add(oo);
            synchronizeBuyerPaypalAcount(ord);
            TaskPool.threadRunTime.remove(threadName);
            Thread.currentThread().setName("_"+threadName + MyStringUtil.getRandomStringAndNum(5));
            ix++;
        }
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

    public SynchronizeGetOrdersBuyerPaypalAcountTaskRun(){

    }

    @Override
    public String getScheduledType() {
        return MainTask.SYNCHRONIZE_GET_ORDERS_BUYER_PAYPAL_ACOUNT;
    }

    @Override
    public Integer crTimeMinu() {
        return MainTaskStaticParam.SOME_CRTIMEMINU.get(getScheduledType());
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
