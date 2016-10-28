package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingMessageAddmembermessage;
import com.base.database.trading.model.TradingMessageAddmembermessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingMessageAddmembermessageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_message_addmembermessage
     *
     * @mbggenerated
     */
    int countByExample(TradingMessageAddmembermessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_message_addmembermessage
     *
     * @mbggenerated
     */
    int deleteByExample(TradingMessageAddmembermessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_message_addmembermessage
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_message_addmembermessage
     *
     * @mbggenerated
     */
    int insert(TradingMessageAddmembermessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_message_addmembermessage
     *
     * @mbggenerated
     */
    int insertSelective(TradingMessageAddmembermessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_message_addmembermessage
     *
     * @mbggenerated
     */
    List<TradingMessageAddmembermessage> selectByExample(TradingMessageAddmembermessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_message_addmembermessage
     *
     * @mbggenerated
     */
    TradingMessageAddmembermessage selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_message_addmembermessage
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingMessageAddmembermessage record, @Param("example") TradingMessageAddmembermessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_message_addmembermessage
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingMessageAddmembermessage record, @Param("example") TradingMessageAddmembermessageExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_message_addmembermessage
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingMessageAddmembermessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_message_addmembermessage
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingMessageAddmembermessage record);
}