package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingAssessViewSet;
import com.base.database.trading.model.TradingAssessViewSetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingAssessViewSetMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_assess_view_set
     *
     * @mbggenerated
     */
    int countByExample(TradingAssessViewSetExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_assess_view_set
     *
     * @mbggenerated
     */
    int deleteByExample(TradingAssessViewSetExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_assess_view_set
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_assess_view_set
     *
     * @mbggenerated
     */
    int insert(TradingAssessViewSet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_assess_view_set
     *
     * @mbggenerated
     */
    int insertSelective(TradingAssessViewSet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_assess_view_set
     *
     * @mbggenerated
     */
    List<TradingAssessViewSet> selectByExample(TradingAssessViewSetExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_assess_view_set
     *
     * @mbggenerated
     */
    TradingAssessViewSet selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_assess_view_set
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingAssessViewSet record, @Param("example") TradingAssessViewSetExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_assess_view_set
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingAssessViewSet record, @Param("example") TradingAssessViewSetExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_assess_view_set
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingAssessViewSet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_assess_view_set
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingAssessViewSet record);
}