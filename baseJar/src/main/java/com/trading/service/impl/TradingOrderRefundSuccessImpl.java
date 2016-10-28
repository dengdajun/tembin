package com.trading.service.impl;

import com.base.database.trading.mapper.TradingOrderRefundSuccessMapper;
import com.base.database.trading.model.TradingOrderRefundSuccess;
import com.base.database.trading.model.TradingOrderRefundSuccessExample;
import com.base.database.trading.model.TradingOrderSellerInformation;
import com.base.utils.common.ObjectUtils;
import com.base.utils.exception.Asserts;
import com.trading.service.ITradingOrderRefundSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrtor on 2015/5/5.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TradingOrderRefundSuccessImpl implements ITradingOrderRefundSuccess {
    @Autowired
    private TradingOrderRefundSuccessMapper tradingOrderRefundSuccessMapper;
    @Override
    public void saveTradingOrderRefundSuccess(TradingOrderRefundSuccess refundSuccess) throws Exception {
        if(refundSuccess.getId()==null){
            ObjectUtils.toInitPojoForInsert(refundSuccess);
            tradingOrderRefundSuccessMapper.insert(refundSuccess);
        }else{
            TradingOrderRefundSuccess t=tradingOrderRefundSuccessMapper.selectByPrimaryKey(refundSuccess.getId());
            Asserts.assertTrue(t != null && t.getCreateUser() != null, "没有找到记录或者记录创建者为空");
            ObjectUtils.valiUpdate(t.getCreateUser(), TradingOrderRefundSuccessMapper.class, refundSuccess.getId(), "Synchronize");
            tradingOrderRefundSuccessMapper.updateByPrimaryKeySelective(refundSuccess);
        }
    }
    @Override
    public List<TradingOrderRefundSuccess> selectTradingOrderRefundSuccessByOrderId(String orderId) {
        TradingOrderRefundSuccessExample example=new TradingOrderRefundSuccessExample();
        TradingOrderRefundSuccessExample.Criteria cr=example.createCriteria();
        cr.andOrderidEqualTo(orderId);
        return tradingOrderRefundSuccessMapper.selectByExample(example);
    }
}
