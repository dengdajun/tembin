package com.base.database.task.mapper;

import com.base.database.task.model.TradingTaskXml;
import com.base.database.task.model.TradingTaskXmlExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TradingTaskXmlMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_task_xml
     *
     * @mbggenerated
     */
    int countByExample(TradingTaskXmlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_task_xml
     *
     * @mbggenerated
     */
    int deleteByExample(TradingTaskXmlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_task_xml
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_task_xml
     *
     * @mbggenerated
     */
    int insert(TradingTaskXml record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_task_xml
     *
     * @mbggenerated
     */
    int insertSelective(TradingTaskXml record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_task_xml
     *
     * @mbggenerated
     */
    List<TradingTaskXml> selectByExampleWithBLOBs(TradingTaskXmlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_task_xml
     *
     * @mbggenerated
     */
    List<TradingTaskXml> selectByExample(TradingTaskXmlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_task_xml
     *
     * @mbggenerated
     */
    TradingTaskXml selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_task_xml
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TradingTaskXml record, @Param("example") TradingTaskXmlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_task_xml
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") TradingTaskXml record, @Param("example") TradingTaskXmlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_task_xml
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TradingTaskXml record, @Param("example") TradingTaskXmlExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_task_xml
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TradingTaskXml record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_task_xml
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(TradingTaskXml record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trading_task_xml
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TradingTaskXml record);
}