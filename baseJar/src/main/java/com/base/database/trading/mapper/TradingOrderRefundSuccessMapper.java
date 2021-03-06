package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingOrderRefundSuccess;
import com.base.database.trading.model.TradingOrderRefundSuccessExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingOrderRefundSuccessMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_refund_success
     *
     * @mbggenerated
     */
    int countByExample(TradingOrderRefundSuccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_refund_success
     *
     * @mbggenerated
     */
    int deleteByExample(TradingOrderRefundSuccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_refund_success
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_refund_success
     *
     * @mbggenerated
     */
    int insert(TradingOrderRefundSuccess record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_refund_success
     *
     * @mbggenerated
     */
    int insertSelective(TradingOrderRefundSuccess record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_refund_success
     *
     * @mbggenerated
     */
    List<TradingOrderRefundSuccess> selectByExample(TradingOrderRefundSuccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_refund_success
     *
     * @mbggenerated
     */
    TradingOrderRefundSuccess selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_refund_success
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingOrderRefundSuccess record, @Param("example") TradingOrderRefundSuccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_refund_success
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingOrderRefundSuccess record, @Param("example") TradingOrderRefundSuccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_refund_success
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingOrderRefundSuccess record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_refund_success
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingOrderRefundSuccess record);
}