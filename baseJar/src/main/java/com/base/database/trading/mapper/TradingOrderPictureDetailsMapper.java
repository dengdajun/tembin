package com.base.database.trading.mapper;

import com.base.database.trading.model.TradingOrderPictureDetails;
import com.base.database.trading.model.TradingOrderPictureDetailsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingOrderPictureDetailsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    int countByExample(TradingOrderPictureDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    int deleteByExample(TradingOrderPictureDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    int insert(TradingOrderPictureDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    int insertSelective(TradingOrderPictureDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    List<TradingOrderPictureDetails> selectByExample(TradingOrderPictureDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    TradingOrderPictureDetails selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingOrderPictureDetails record, @Param("example") TradingOrderPictureDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingOrderPictureDetails record, @Param("example") TradingOrderPictureDetailsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingOrderPictureDetails record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_order_picturedetails
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingOrderPictureDetails record);
}