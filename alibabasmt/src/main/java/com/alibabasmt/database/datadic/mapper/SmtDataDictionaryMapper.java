package com.alibabasmt.database.datadic.mapper;

import com.alibabasmt.database.datadic.model.SmtDataDictionary;
import com.alibabasmt.database.datadic.model.SmtDataDictionaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmtDataDictionaryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_data_dictionary
     *
     * @mbggenerated
     */
    int countByExample(SmtDataDictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_data_dictionary
     *
     * @mbggenerated
     */
    int deleteByExample(SmtDataDictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_data_dictionary
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_data_dictionary
     *
     * @mbggenerated
     */
    int insert(SmtDataDictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_data_dictionary
     *
     * @mbggenerated
     */
    int insertSelective(SmtDataDictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_data_dictionary
     *
     * @mbggenerated
     */
    List<SmtDataDictionary> selectByExample(SmtDataDictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_data_dictionary
     *
     * @mbggenerated
     */
    SmtDataDictionary selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_data_dictionary
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SmtDataDictionary record, @Param("example") SmtDataDictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_data_dictionary
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SmtDataDictionary record, @Param("example") SmtDataDictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_data_dictionary
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SmtDataDictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_data_dictionary
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SmtDataDictionary record);
}