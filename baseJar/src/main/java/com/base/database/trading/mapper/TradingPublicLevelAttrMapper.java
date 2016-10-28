package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingPublicLevelAttr;
import com.base.database.trading.model.TradingPublicLevelAttrExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingPublicLevelAttrMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_public_level_attr
     *
     * @mbggenerated
     */
    int countByExample(TradingPublicLevelAttrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_public_level_attr
     *
     * @mbggenerated
     */
    int deleteByExample(TradingPublicLevelAttrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_public_level_attr
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_public_level_attr
     *
     * @mbggenerated
     */
    int insert(TradingPublicLevelAttr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_public_level_attr
     *
     * @mbggenerated
     */
    int insertSelective(TradingPublicLevelAttr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_public_level_attr
     *
     * @mbggenerated
     */
    List<TradingPublicLevelAttr> selectByExample(TradingPublicLevelAttrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_public_level_attr
     *
     * @mbggenerated
     */
    TradingPublicLevelAttr selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_public_level_attr
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingPublicLevelAttr record, @Param("example") TradingPublicLevelAttrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_public_level_attr
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingPublicLevelAttr record, @Param("example") TradingPublicLevelAttrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_public_level_attr
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingPublicLevelAttr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_public_level_attr
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingPublicLevelAttr record);
}