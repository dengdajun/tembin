package com.alibabasmt.database.smtproduct.mapper;

import com.alibabasmt.database.smtproduct.model.SmtProduct;
import com.alibabasmt.database.smtproduct.model.SmtProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmtProductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_product
     *
     * @mbggenerated
     */
    int countByExample(SmtProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_product
     *
     * @mbggenerated
     */
    int deleteByExample(SmtProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_product
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_product
     *
     * @mbggenerated
     */
    int insert(SmtProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_product
     *
     * @mbggenerated
     */
    int insertSelective(SmtProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_product
     *
     * @mbggenerated
     */
    List<SmtProduct> selectByExampleWithBLOBs(SmtProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_product
     *
     * @mbggenerated
     */
    List<SmtProduct> selectByExample(SmtProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_product
     *
     * @mbggenerated
     */
    SmtProduct selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_product
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SmtProduct record, @Param("example") SmtProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_product
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") SmtProduct record, @Param("example") SmtProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_product
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SmtProduct record, @Param("example") SmtProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_product
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SmtProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_product
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(SmtProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_product
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SmtProduct record);
}