package com.alibabasmt.database.smtorder.mapper;

import com.alibabasmt.database.smtorder.model.SmtOrderItemSku;
import com.alibabasmt.database.smtorder.model.SmtOrderItemSkuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmtOrderItemSkuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_item_sku
     *
     * @mbggenerated
     */
    int countByExample(SmtOrderItemSkuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_item_sku
     *
     * @mbggenerated
     */
    int deleteByExample(SmtOrderItemSkuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_item_sku
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_item_sku
     *
     * @mbggenerated
     */
    int insert(SmtOrderItemSku record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_item_sku
     *
     * @mbggenerated
     */
    int insertSelective(SmtOrderItemSku record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_item_sku
     *
     * @mbggenerated
     */
    List<SmtOrderItemSku> selectByExample(SmtOrderItemSkuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_item_sku
     *
     * @mbggenerated
     */
    SmtOrderItemSku selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_item_sku
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SmtOrderItemSku record, @Param("example") SmtOrderItemSkuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_item_sku
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SmtOrderItemSku record, @Param("example") SmtOrderItemSkuExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_item_sku
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SmtOrderItemSku record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_item_sku
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SmtOrderItemSku record);
}