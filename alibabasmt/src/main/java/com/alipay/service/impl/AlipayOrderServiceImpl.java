package com.alipay.service.impl;

import com.alipay.service.AlipayOrderService;
import com.base.database.userinfo.mapper.AlipayOrdersMapper;
import com.base.database.userinfo.mapper.UsercontrollerUserBillMapper;
import com.base.database.userinfo.model.AlipayOrders;
import com.base.database.userinfo.model.AlipayOrdersExample;
import com.base.database.userinfo.model.UsercontrollerUserBill;
import com.base.domains.SessionVO;
import com.base.utils.cache.SessionCacheSupport;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.base.utils.scheduleother.service.SystemVipUserCostFeeService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/5/31.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AlipayOrderServiceImpl implements AlipayOrderService {
    static Logger logger = Logger.getLogger(AlipayOrderServiceImpl.class);

    @Autowired
    private AlipayOrdersMapper alipayOrdersMapper;
    /*@Autowired
    private UsercontrollerUserBillMapper userBillMapper;*/
    @Autowired
    private SystemVipUserCostFeeService costFeeService;

    @Override
    /**写入一条订单*/
    public void addOrUpdate(AlipayOrders alipayOrders){
        Asserts.assertTrue((alipayOrders != null && StringUtils.isNotBlank(alipayOrders.getMyTradeNo())), "订单号不能为空！");
        AlipayOrdersExample example=new AlipayOrdersExample();
        example.createCriteria()
                .andMyTradeNoEqualTo(alipayOrders.getMyTradeNo());
                //.andTembinBuyerIdEqualTo(alipayOrders.getTembinBuyerId());
        List<AlipayOrders> orders=alipayOrdersMapper.selectByExample(example);

        UsercontrollerUserBill bill=new UsercontrollerUserBill();
        bill.setBillSouce("alipay");
        bill.setBillType("recharge");
        SessionVO sessionVO= SessionCacheSupport.getSessionVO();
        try {
            if (sessionVO.getParentId()==0){
                bill.setUserId(alipayOrders.getTembinBuyerId());
            }else {
                bill.setUserId(((Long)sessionVO.getParentId()).intValue());
            }
        } catch (Exception e) {
            bill.setUserId(-10);
            logger.error(e);
        }

        bill.setStatus("0");

        if (ObjectUtils.isLogicalNull(orders)){//如果还没有记录
            alipayOrdersMapper.insertSelective(alipayOrders);
            if (StringUtils.isNotBlank(alipayOrders.getAlipayTradeNo())){//如果有ali订单号
                bill.setBillSourceId(alipayOrders.getId());
                bill.setAmount(new BigDecimal(alipayOrders.getTotalFee()));
                costFeeService.addBill(bill);
                //userBillMapper.insertSelective(bill);
            }

        }else {
            AlipayOrders o=orders.get(0);
            if (StringUtils.isBlank(o.getAlipayTradeNo())){
                o.setTradeStatus(alipayOrders.getTradeStatus());
                o.setUpdateTime(new Date());
                o.setTotalFee(alipayOrders.getTotalFee());
                o.setAlipayTradeNo(alipayOrders.getAlipayTradeNo());
                o.setBuyerEmail(alipayOrders.getBuyerEmail());
                alipayOrdersMapper.updateByPrimaryKeySelective(o);
                bill.setBillSourceId(o.getId());
                bill.setUserId(o.getTembinBuyerId());
                bill.setAmount(new BigDecimal(alipayOrders.getTotalFee()));
                costFeeService.addBill(bill);
                //userBillMapper.insertSelective(bill);
            }else {
                logger.error("订单已经存在==" + alipayOrders.getMyTradeNo());
            }

        }
    }

    @Override
    /**结束一个订单*/
    //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知更改状态
    public void endOrder(AlipayOrders alipayOrders){
        Asserts.assertTrue((alipayOrders != null && StringUtils.isNotBlank(alipayOrders.getMyTradeNo())), "订单号不能为空！");
        AlipayOrdersExample example=new AlipayOrdersExample();
        example.createCriteria()
                .andMyTradeNoEqualTo(alipayOrders.getMyTradeNo());
        List<AlipayOrders> orders=alipayOrdersMapper.selectByExample(example);
        if (!ObjectUtils.isLogicalNull(orders)){
            AlipayOrders o=orders.get(0);
            o.setUpdateTime(new Date());
            o.setTradeStatus(alipayOrders.getTradeStatus());
            alipayOrdersMapper.updateByPrimaryKeySelective(o);
        }else {
            logger.error("没有找到订单！"+alipayOrders.getAlipayTradeNo());
        }

    }




}
