package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingAutoMessage;
import com.base.database.trading.model.TradingAutoMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingAutoMessageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message
     *
     * @mbggenerated
     */
    int countByExample(TradingAutoMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message
     *
     * @mbggenerated
     */
    int deleteByExample(TradingAutoMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message
     *
     * @mbggenerated
     */
    int insert(TradingAutoMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message
     *
     * @mbggenerated
     */
    int insertSelective(TradingAutoMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message
     *
     * @mbggenerated
     */
    List<TradingAutoMessage> selectByExample(TradingAutoMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message
     *
     * @mbggenerated
     */
    TradingAutoMessage selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingAutoMessage record, @Param("example") TradingAutoMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingAutoMessage record, @Param("example") TradingAutoMessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingAutoMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_auto_message
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingAutoMessage record);
}