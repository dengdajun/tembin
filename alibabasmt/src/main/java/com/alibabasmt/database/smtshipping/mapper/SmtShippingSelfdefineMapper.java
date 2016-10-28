package com.alibabasmt.database.smtshipping.mapper;

import com.alibabasmt.database.smtshipping.model.SmtShippingSelfdefine;
import com.alibabasmt.database.smtshipping.model.SmtShippingSelfdefineExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmtShippingSelfdefineMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_selfdefine
     *
     * @mbggenerated
     */
    int countByExample(SmtShippingSelfdefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_selfdefine
     *
     * @mbggenerated
     */
    int deleteByExample(SmtShippingSelfdefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_selfdefine
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_selfdefine
     *
     * @mbggenerated
     */
    int insert(SmtShippingSelfdefine record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_selfdefine
     *
     * @mbggenerated
     */
    int insertSelective(SmtShippingSelfdefine record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_selfdefine
     *
     * @mbggenerated
     */
    List<SmtShippingSelfdefine> selectByExampleWithBLOBs(SmtShippingSelfdefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_selfdefine
     *
     * @mbggenerated
     */
    List<SmtShippingSelfdefine> selectByExample(SmtShippingSelfdefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_selfdefine
     *
     * @mbggenerated
     */
    SmtShippingSelfdefine selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_selfdefine
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SmtShippingSelfdefine record, @Param("example") SmtShippingSelfdefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_selfdefine
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") SmtShippingSelfdefine record, @Param("example") SmtShippingSelfdefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_selfdefine
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SmtShippingSelfdefine record, @Param("example") SmtShippingSelfdefineExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_selfdefine
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SmtShippingSelfdefine record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_selfdefine
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(SmtShippingSelfdefine record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_shipping_selfdefine
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SmtShippingSelfdefine record);
}