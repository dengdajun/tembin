package com.alipay.service;

import com.base.database.userinfo.model.AlipayOrders;

/**
 * Created by Administrator on 2015/5/31.
 */
public interface AlipayOrderService {
    /**写入一条订单*/
    void addOrUpdate(AlipayOrders alipayOrders);

    /**结束一个订单*/
    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知更改状态
    void endOrder(AlipayOrders alipayOrders);
}
