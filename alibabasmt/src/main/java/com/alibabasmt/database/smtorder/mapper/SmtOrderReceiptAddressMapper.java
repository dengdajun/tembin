package com.alibabasmt.database.smtorder.mapper;

import com.alibabasmt.database.smtorder.model.SmtOrderReceiptAddress;
import com.alibabasmt.database.smtorder.model.SmtOrderReceiptAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmtOrderReceiptAddressMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_receipt_address
     *
     * @mbggenerated
     */
    int countByExample(SmtOrderReceiptAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_receipt_address
     *
     * @mbggenerated
     */
    int deleteByExample(SmtOrderReceiptAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_receipt_address
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_receipt_address
     *
     * @mbggenerated
     */
    int insert(SmtOrderReceiptAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_receipt_address
     *
     * @mbggenerated
     */
    int insertSelective(SmtOrderReceiptAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_receipt_address
     *
     * @mbggenerated
     */
    List<SmtOrderReceiptAddress> selectByExample(SmtOrderReceiptAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_receipt_address
     *
     * @mbggenerated
     */
    SmtOrderReceiptAddress selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_receipt_address
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SmtOrderReceiptAddress record, @Param("example") SmtOrderReceiptAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_receipt_address
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SmtOrderReceiptAddress record, @Param("example") SmtOrderReceiptAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_receipt_address
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SmtOrderReceiptAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_order_receipt_address
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SmtOrderReceiptAddress record);
}