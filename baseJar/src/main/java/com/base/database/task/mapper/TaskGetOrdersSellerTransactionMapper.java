package com.base.database.task.mapper;

import com.base.database.task.model.TaskGetOrdersSellerTransaction;
import com.base.database.task.model.TaskGetOrdersSellerTransactionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskGetOrdersSellerTransactionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_orders_seller_transaction
     *
     * @mbggenerated
     */
    int countByExample(TaskGetOrdersSellerTransactionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_orders_seller_transaction
     *
     * @mbggenerated
     */
    int deleteByExample(TaskGetOrdersSellerTransactionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_orders_seller_transaction
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_orders_seller_transaction
     *
     * @mbggenerated
     */
    int insert(TaskGetOrdersSellerTransaction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_orders_seller_transaction
     *
     * @mbggenerated
     */
    int insertSelective(TaskGetOrdersSellerTransaction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_orders_seller_transaction
     *
     * @mbggenerated
     */
    List<TaskGetOrdersSellerTransaction> selectByExampleWithBLOBs(TaskGetOrdersSellerTransactionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_orders_seller_transaction
     *
     * @mbggenerated
     */
    List<TaskGetOrdersSellerTransaction> selectByExample(TaskGetOrdersSellerTransactionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_orders_seller_transaction
     *
     * @mbggenerated
     */
    TaskGetOrdersSellerTransaction selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_orders_seller_transaction
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TaskGetOrdersSellerTransaction record, @Param("example") TaskGetOrdersSellerTransactionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_orders_seller_transaction
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") TaskGetOrdersSellerTransaction record, @Param("example") TaskGetOrdersSellerTransactionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_orders_seller_transaction
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TaskGetOrdersSellerTransaction record, @Param("example") TaskGetOrdersSellerTransactionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_orders_seller_transaction
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TaskGetOrdersSellerTransaction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_orders_seller_transaction
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(TaskGetOrdersSellerTransaction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table task_get_orders_seller_transaction
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TaskGetOrdersSellerTransaction record);
}