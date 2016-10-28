package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingOrderSeller;
import com.base.database.trading.model.TradingOrderSellerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingOrderSellerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_seller
     *
     * @mbggenerated
     */
    int countByExample(TradingOrderSellerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_seller
     *
     * @mbggenerated
     */
    int deleteByExample(TradingOrderSellerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_seller
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_seller
     *
     * @mbggenerated
     */
    int insert(TradingOrderSeller record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_seller
     *
     * @mbggenerated
     */
    int insertSelective(TradingOrderSeller record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_seller
     *
     * @mbggenerated
     */
    List<TradingOrderSeller> selectByExample(TradingOrderSellerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_seller
     *
     * @mbggenerated
     */
    TradingOrderSeller selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_seller
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingOrderSeller record, @Param("example") TradingOrderSellerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_seller
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingOrderSeller record, @Param("example") TradingOrderSellerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_seller
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingOrderSeller record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_seller
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingOrderSeller record);
}