package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingInventoryComplement;
import com.base.database.trading.model.TradingInventoryComplementExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingInventoryComplementMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_inventory_complement
     *
     * @mbggenerated
     */
    int countByExample(TradingInventoryComplementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_inventory_complement
     *
     * @mbggenerated
     */
    int deleteByExample(TradingInventoryComplementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_inventory_complement
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_inventory_complement
     *
     * @mbggenerated
     */
    int insert(TradingInventoryComplement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_inventory_complement
     *
     * @mbggenerated
     */
    int insertSelective(TradingInventoryComplement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_inventory_complement
     *
     * @mbggenerated
     */
    List<TradingInventoryComplement> selectByExample(TradingInventoryComplementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_inventory_complement
     *
     * @mbggenerated
     */
    TradingInventoryComplement selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_inventory_complement
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingInventoryComplement record, @Param("example") TradingInventoryComplementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_inventory_complement
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingInventoryComplement record, @Param("example") TradingInventoryComplementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_inventory_complement
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingInventoryComplement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_inventory_complement
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingInventoryComplement record);
}