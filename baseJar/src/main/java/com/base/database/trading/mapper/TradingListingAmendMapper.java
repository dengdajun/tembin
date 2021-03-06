package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingListingAmend;
import com.base.database.trading.model.TradingListingAmendExample;
import com.base.database.trading.model.TradingListingAmendWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingListingAmendMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_amend
     *
     * @mbggenerated
     */
    int countByExample(TradingListingAmendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_amend
     *
     * @mbggenerated
     */
    int deleteByExample(TradingListingAmendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_amend
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_amend
     *
     * @mbggenerated
     */
    int insert(TradingListingAmendWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_amend
     *
     * @mbggenerated
     */
    int insertSelective(TradingListingAmendWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_amend
     *
     * @mbggenerated
     */
    List<TradingListingAmendWithBLOBs> selectByExampleWithBLOBs(TradingListingAmendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_amend
     *
     * @mbggenerated
     */
    List<TradingListingAmend> selectByExample(TradingListingAmendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_amend
     *
     * @mbggenerated
     */
    TradingListingAmendWithBLOBs selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_amend
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingListingAmendWithBLOBs record, @Param("example") TradingListingAmendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_amend
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") TradingListingAmendWithBLOBs record, @Param("example") TradingListingAmendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_amend
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingListingAmend record, @Param("example") TradingListingAmendExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_amend
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingListingAmendWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_amend
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(TradingListingAmendWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_listing_amend
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingListingAmend record);
}