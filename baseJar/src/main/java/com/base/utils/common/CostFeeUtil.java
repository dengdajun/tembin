package com.base.utils.common;

import com.base.database.userinfo.model.UsercontrollerUserBill;
import com.base.utils.applicationcontext.ApplicationContextUtil;
import com.base.utils.scheduleother.service.SystemVipUserCostFeeService;

/**
 * Created by Administrator on 2015/6/3.
 * 用于扣费
 */
public class CostFeeUtil {
    /**写入一笔记账*/
    private static void addBill(UsercontrollerUserBill bill){
        SystemVipUserCostFeeService service= ApplicationContextUtil.getBean(SystemVipUserCostFeeService.class);
        service.addBill(bill);
    }
}
