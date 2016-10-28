package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingDiscountpriceinfo;
import com.base.database.trading.model.TradingDiscountpriceinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingDiscountpriceinfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_discountpriceinfo
     *
     * @mbggenerated
     */
    int countByExample(TradingDiscountpriceinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_discountpriceinfo
     *
     * @mbggenerated
     */
    int deleteByExample(TradingDiscountpriceinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_discountpriceinfo
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_discountpriceinfo
     *
     * @mbggenerated
     */
    int insert(TradingDiscountpriceinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_discountpriceinfo
     *
     * @mbggenerated
     */
    int insertSelective(TradingDiscountpriceinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_discountpriceinfo
     *
     * @mbggenerated
     */
    List<TradingDiscountpriceinfo> selectByExample(TradingDiscountpriceinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_discountpriceinfo
     *
     * @mbggenerated
     */
    TradingDiscountpriceinfo selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_discountpriceinfo
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingDiscountpriceinfo record, @Param("example") TradingDiscountpriceinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_discountpriceinfo
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingDiscountpriceinfo record, @Param("example") TradingDiscountpriceinfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_discountpriceinfo
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingDiscountpriceinfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_discountpriceinfo
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingDiscountpriceinfo record);
}