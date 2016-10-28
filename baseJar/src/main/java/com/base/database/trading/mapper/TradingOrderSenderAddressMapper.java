package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingOrderSenderAddress;
import com.base.database.trading.model.TradingOrderSenderAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingOrderSenderAddressMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_sender_address
     *
     * @mbggenerated
     */
    int countByExample(TradingOrderSenderAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_sender_address
     *
     * @mbggenerated
     */
    int deleteByExample(TradingOrderSenderAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_sender_address
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_sender_address
     *
     * @mbggenerated
     */
    int insert(TradingOrderSenderAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_sender_address
     *
     * @mbggenerated
     */
    int insertSelective(TradingOrderSenderAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_sender_address
     *
     * @mbggenerated
     */
    List<TradingOrderSenderAddress> selectByExample(TradingOrderSenderAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_sender_address
     *
     * @mbggenerated
     */
    TradingOrderSenderAddress selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_sender_address
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingOrderSenderAddress record, @Param("example") TradingOrderSenderAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_sender_address
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingOrderSenderAddress record, @Param("example") TradingOrderSenderAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_sender_address
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingOrderSenderAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_sender_address
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingOrderSenderAddress record);
}