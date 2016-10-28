package com.base.database.publicd.mapper;

import com.base.database.publicd.model.PublicItemSupplier;
import com.base.database.publicd.model.PublicItemSupplierExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PublicItemSupplierMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_supplier
     *
     * @mbggenerated
     */
    int countByExample(PublicItemSupplierExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_supplier
     *
     * @mbggenerated
     */
    int deleteByExample(PublicItemSupplierExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_supplier
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_supplier
     *
     * @mbggenerated
     */
    int insert(PublicItemSupplier record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_supplier
     *
     * @mbggenerated
     */
    int insertSelective(PublicItemSupplier record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_supplier
     *
     * @mbggenerated
     */
    List<PublicItemSupplier> selectByExample(PublicItemSupplierExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_supplier
     *
     * @mbggenerated
     */
    PublicItemSupplier selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_supplier
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") PublicItemSupplier record, @Param("example") PublicItemSupplierExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_supplier
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") PublicItemSupplier record, @Param("example") PublicItemSupplierExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_supplier
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PublicItemSupplier record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_supplier
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PublicItemSupplier record);
}