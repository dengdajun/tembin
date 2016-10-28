package com.base.database.task.mapper;

import com.base.database.task.model.TaskGetUserCases;
import com.base.database.task.model.TaskGetUserCasesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskGetUserCasesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_user_cases
     *
     * @mbggenerated
     */
    int countByExample(TaskGetUserCasesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_user_cases
     *
     * @mbggenerated
     */
    int deleteByExample(TaskGetUserCasesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_user_cases
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_user_cases
     *
     * @mbggenerated
     */
    int insert(TaskGetUserCases record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_user_cases
     *
     * @mbggenerated
     */
    int insertSelective(TaskGetUserCases record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_user_cases
     *
     * @mbggenerated
     */
    List<TaskGetUserCases> selectByExampleWithBLOBs(TaskGetUserCasesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_user_cases
     *
     * @mbggenerated
     */
    List<TaskGetUserCases> selectByExample(TaskGetUserCasesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_user_cases
     *
     * @mbggenerated
     */
    TaskGetUserCases selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_user_cases
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TaskGetUserCases record, @Param("example") TaskGetUserCasesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_user_cases
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") TaskGetUserCases record, @Param("example") TaskGetUserCasesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_user_cases
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TaskGetUserCases record, @Param("example") TaskGetUserCasesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_user_cases
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TaskGetUserCases record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_user_cases
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(TaskGetUserCases record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_user_cases
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TaskGetUserCases record);
}