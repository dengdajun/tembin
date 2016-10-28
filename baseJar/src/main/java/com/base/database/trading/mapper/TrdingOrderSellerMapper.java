package com.base.database.trading.mapper;

import com.base.database.trading.model.TrdingOrderSeller;
import com.base.database.trading.model.TrdingOrderSellerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TrdingOrderSellerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_seller
     *
     * @mbggenerated
     */
    int countByExample(TrdingOrderSellerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_seller
     *
     * @mbggenerated
     */
    int deleteByExample(TrdingOrderSellerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_seller
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_seller
     *
     * @mbggenerated
     */
    int insert(TrdingOrderSeller record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_seller
     *
     * @mbggenerated
     */
    int insertSelective(TrdingOrderSeller record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_seller
     *
     * @mbggenerated
     */
    List<TrdingOrderSeller> selectByExample(TrdingOrderSellerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_seller
     *
     * @mbggenerated
     */
    TrdingOrderSeller selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_seller
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TrdingOrderSeller record, @Param("example") TrdingOrderSellerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_seller
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TrdingOrderSeller record, @Param("example") TrdingOrderSellerExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_seller
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TrdingOrderSeller record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trding_order_seller
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TrdingOrderSeller record);
}