package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingProductlistingdetails;
import com.base.database.trading.model.TradingProductlistingdetailsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingProductlistingdetailsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_productlistingdetails
     *
     * @mbggenerated
     */
    int countByExample(TradingProductlistingdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_productlistingdetails
     *
     * @mbggenerated
     */
    int deleteByExample(TradingProductlistingdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_productlistingdetails
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_productlistingdetails
     *
     * @mbggenerated
     */
    int insert(TradingProductlistingdetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_productlistingdetails
     *
     * @mbggenerated
     */
    int insertSelective(TradingProductlistingdetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_productlistingdetails
     *
     * @mbggenerated
     */
    List<TradingProductlistingdetails> selectByExample(TradingProductlistingdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_productlistingdetails
     *
     * @mbggenerated
     */
    TradingProductlistingdetails selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_productlistingdetails
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingProductlistingdetails record, @Param("example") TradingProductlistingdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_productlistingdetails
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingProductlistingdetails record, @Param("example") TradingProductlistingdetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_productlistingdetails
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingProductlistingdetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_productlistingdetails
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingProductlistingdetails record);
}