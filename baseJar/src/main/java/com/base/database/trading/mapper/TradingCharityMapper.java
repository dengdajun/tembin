package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingCharity;
import com.base.database.trading.model.TradingCharityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingCharityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_charity
     *
     * @mbggenerated
     */
    int countByExample(TradingCharityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_charity
     *
     * @mbggenerated
     */
    int deleteByExample(TradingCharityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_charity
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_charity
     *
     * @mbggenerated
     */
    int insert(TradingCharity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_charity
     *
     * @mbggenerated
     */
    int insertSelective(TradingCharity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_charity
     *
     * @mbggenerated
     */
    List<TradingCharity> selectByExample(TradingCharityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_charity
     *
     * @mbggenerated
     */
    TradingCharity selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_charity
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingCharity record, @Param("example") TradingCharityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_charity
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingCharity record, @Param("example") TradingCharityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_charity
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingCharity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_charity
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingCharity record);
}