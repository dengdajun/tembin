package com.base.utils.scheduleother.service;

import com.base.database.userinfo.model.UsercontrollerUserBill;
import com.base.database.userinfo.model.UsercontrollerVipUser;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2015/6/3.
 */
public interface SystemVipUserCostFeeService {
    /**根据条件判断是不是该扣费*/
    void sfCost(UsercontrollerVipUser vipUser);

    /**查询当前用户的余额*/
    BigDecimal queryBanlace(String userId);

    /**扣费 月费    *
     * @param bill
     *bill type:  deduct  recharge
     * costtype: ebayAccount  subUser
     * costtarget:ebayid userid
     * amount  金额
     * user_id  扣费的主账号
     * status  0已扣  1预扣 2作废
     * costs_date 扣费的月份
     * @return
     */
    String costVIPMonth(UsercontrollerUserBill bill);

    /**直接写入一笔费用比如充值等情况*/
    void addBill(UsercontrollerUserBill bill);

    /**作废一笔账单*/
    void cancelBill(Integer billId);

    void middleCost(UsercontrollerUserBill userBill);
}
