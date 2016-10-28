package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingShippingdetails;
import com.base.database.trading.model.TradingShippingdetailsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingShippingdetailsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingdetails
     *
     * @mbggenerated
     */
    int countByExample(TradingShippingdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingdetails
     *
     * @mbggenerated
     */
    int deleteByExample(TradingShippingdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingdetails
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingdetails
     *
     * @mbggenerated
     */
    int insert(TradingShippingdetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingdetails
     *
     * @mbggenerated
     */
    int insertSelective(TradingShippingdetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingdetails
     *
     * @mbggenerated
     */
    List<TradingShippingdetails> selectByExample(TradingShippingdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingdetails
     *
     * @mbggenerated
     */
    TradingShippingdetails selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingdetails
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingShippingdetails record, @Param("example") TradingShippingdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingdetails
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingShippingdetails record, @Param("example") TradingShippingdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingdetails
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingShippingdetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_shippingdetails
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingShippingdetails record);
}