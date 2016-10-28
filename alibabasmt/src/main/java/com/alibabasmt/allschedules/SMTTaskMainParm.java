package com.alibabasmt.allschedules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/3/24.
 * 关于速卖通定时任务的一些参数
 */
public class SMTTaskMainParm {
    /**定时刷新accesstoken*/
    public static final String SMT_REFRESH_ACCESSTOKEN="smt_refresh_accesstoken";
    /**定时获取运费模板列表信息*/
    public static final String SMT_SHIPPING_TEMPLATE_LIST="smt_shipping_template_list";
    /**定时获取运费模板明细信息*/
    public static final String SMT_SHIPPING_TEMPLATE_DETAIL="smt_shipping_template_detail";
    /**在线产品列表信息信息*/
    public static final String SMT_PRODUCT_LISTING="smt_product_listing";
    /**在线产品列表信息信息*/
    public static final String SMT_PRODUCT_LISTING_DETAIL="smt_product_listing_detail";
    /**速卖通订单task的生成*/
    public static final String SMT_ORDERS_TASK ="smt_orders_task ";
    /**定义一些每次执行时需要开多个线程的任务，以及次数*/
    public static final Map<String,Integer> SOME_MULIT_TASK=new HashMap<String, Integer>();
    static {
        SOME_MULIT_TASK.put(SMT_REFRESH_ACCESSTOKEN,1);
        SOME_MULIT_TASK.put(SMT_SHIPPING_TEMPLATE_LIST,1);
        SOME_MULIT_TASK.put(SMT_SHIPPING_TEMPLATE_DETAIL,1);
        SOME_MULIT_TASK.put(SMT_PRODUCT_LISTING,1);
        SOME_MULIT_TASK.put(SMT_PRODUCT_LISTING_DETAIL,1);
    }

    /**定义两分钟一次的定时，需要跑哪些任务*/
    public final static List<String> doList=new ArrayList<String>();
    static {
        doList.add(SMT_REFRESH_ACCESSTOKEN); //排除掉归零任务
        doList.add(SMT_SHIPPING_TEMPLATE_LIST);
        doList.add(SMT_SHIPPING_TEMPLATE_DETAIL);
        doList.add(SMT_PRODUCT_LISTING);
        doList.add(SMT_PRODUCT_LISTING_DETAIL);
    }
    /**定义一天一次的任务*/
    public final static List<String> doListEveryDay=new ArrayList<String>();
    static {
        doListEveryDay.add(SMT_ORDERS_TASK);
        // doList.add();
    }


    /**定义各个任务的间隔时间*/
    public static final Map<String,Integer> SOME_CRTIMEMINU=new HashMap<String, Integer>();
    static {
        SOME_CRTIMEMINU.put(SMT_REFRESH_ACCESSTOKEN,2);
        SOME_CRTIMEMINU.put(SMT_SHIPPING_TEMPLATE_LIST,2);
        SOME_CRTIMEMINU.put(SMT_SHIPPING_TEMPLATE_DETAIL,2);
        SOME_CRTIMEMINU.put(SMT_PRODUCT_LISTING,2);
        SOME_CRTIMEMINU.put(SMT_PRODUCT_LISTING_DETAIL,2);
    }
}
