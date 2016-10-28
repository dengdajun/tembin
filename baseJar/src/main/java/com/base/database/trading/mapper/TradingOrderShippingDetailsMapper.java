package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingOrderShippingDetails;
import com.base.database.trading.model.TradingOrderShippingDetailsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingOrderShippingDetailsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingdetails
     *
     * @mbggenerated
     */
    int countByExample(TradingOrderShippingDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingdetails
     *
     * @mbggenerated
     */
    int deleteByExample(TradingOrderShippingDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingdetails
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingdetails
     *
     * @mbggenerated
     */
    int insert(TradingOrderShippingDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingdetails
     *
     * @mbggenerated
     */
    int insertSelective(TradingOrderShippingDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingdetails
     *
     * @mbggenerated
     */
    List<TradingOrderShippingDetails> selectByExample(TradingOrderShippingDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingdetails
     *
     * @mbggenerated
     */
    TradingOrderShippingDetails selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingdetails
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingOrderShippingDetails record, @Param("example") TradingOrderShippingDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingdetails
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingOrderShippingDetails record, @Param("example") TradingOrderShippingDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingdetails
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingOrderShippingDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_shippingdetails
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingOrderShippingDetails record);
}