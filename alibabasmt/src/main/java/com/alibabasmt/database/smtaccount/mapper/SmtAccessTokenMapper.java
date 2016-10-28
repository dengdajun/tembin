package com.alibabasmt.database.smtaccount.mapper;

import com.alibabasmt.database.smtaccount.model.SmtAccessToken;
import com.alibabasmt.database.smtaccount.model.SmtAccessTokenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SmtAccessTokenMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_access_token
     *
     * @mbggenerated
     */
    int countByExample(SmtAccessTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_access_token
     *
     * @mbggenerated
     */
    int deleteByExample(SmtAccessTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_access_token
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_access_token
     *
     * @mbggenerated
     */
    int insert(SmtAccessToken record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_access_token
     *
     * @mbggenerated
     */
    int insertSelective(SmtAccessToken record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_access_token
     *
     * @mbggenerated
     */
    List<SmtAccessToken> selectByExample(SmtAccessTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_access_token
     *
     * @mbggenerated
     */
    SmtAccessToken selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_access_token
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") SmtAccessToken record, @Param("example") SmtAccessTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_access_token
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") SmtAccessToken record, @Param("example") SmtAccessTokenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_access_token
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(SmtAccessToken record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table smt_access_token
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SmtAccessToken record);
}