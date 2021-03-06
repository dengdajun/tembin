package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingVariation;
import com.base.database.trading.model.TradingVariationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingVariationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variation
     *
     * @mbggenerated
     */
    int countByExample(TradingVariationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variation
     *
     * @mbggenerated
     */
    int deleteByExample(TradingVariationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variation
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variation
     *
     * @mbggenerated
     */
    int insert(TradingVariation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variation
     *
     * @mbggenerated
     */
    int insertSelective(TradingVariation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variation
     *
     * @mbggenerated
     */
    List<TradingVariation> selectByExample(TradingVariationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variation
     *
     * @mbggenerated
     */
    TradingVariation selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variation
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingVariation record, @Param("example") TradingVariationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variation
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingVariation record, @Param("example") TradingVariationExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variation
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingVariation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_variation
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingVariation record);
}