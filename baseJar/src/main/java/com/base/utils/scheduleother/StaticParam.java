package com.base.utils.scheduleother;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2014/11/13.
 * 其他定时任务的任务变量名称
 */
public class StaticParam {
    public final static String ebayFeeOne="60";
    public final static String subUserFeeOne="60";
    public final static Integer costDay=32;//计费周期
    public final static List<String> xietiao=new ArrayList<>(1);//用于协调检查费用和扣费两个定时任务



    public final static String IMG_CHECK_SC = "IMG_CHECK_SC";//图片检查任务
    public final static String IMG_CHECK_SC_TAKE = "IMG_CHECK_SC_TAKE";//图片检查任务，取出处理任务

    //public final static String INVENTORY_CHECK_SC_TAKE = "INVENTORY_CHECK_SC_TAKE";//获取库存数据


    public final static String SYSTEM_VIP_USER_CHECK = "SYSTEM_VIP_USER_CHECK";//需要付费的用户检查

    public final static String SYSTEM_VIP_USER_COST_FEE = "SYSTEM_VIP_USER_COST_FEE";//扣月费

    public final static String SYSTEM_SMS = "SYSTEM_SMS";//短信任务
    public final static String SYSTEM_SMS_STATUS = "SYSTEM_SMS_STATUS";//短信任务获取状态
}