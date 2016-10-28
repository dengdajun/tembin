package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingSetComplement;
import com.base.database.trading.model.TradingSetComplementExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingSetComplementMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    int countByExample(TradingSetComplementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    int deleteByExample(TradingSetComplementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    int insert(TradingSetComplement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    int insertSelective(TradingSetComplement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    List<TradingSetComplement> selectByExample(TradingSetComplementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    TradingSetComplement selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingSetComplement record, @Param("example") TradingSetComplementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingSetComplement record, @Param("example") TradingSetComplementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingSetComplement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_set_complement
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingSetComplement record);
}