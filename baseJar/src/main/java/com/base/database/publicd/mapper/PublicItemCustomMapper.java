package com.base.database.publicd.mapper;

import com.base.database.publicd.model.PublicItemCustom;
import com.base.database.publicd.model.PublicItemCustomExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PublicItemCustomMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_custom
     *
     * @mbggenerated
     */
    int countByExample(PublicItemCustomExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_custom
     *
     * @mbggenerated
     */
    int deleteByExample(PublicItemCustomExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_custom
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_custom
     *
     * @mbggenerated
     */
    int insert(PublicItemCustom record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_custom
     *
     * @mbggenerated
     */
    int insertSelective(PublicItemCustom record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_custom
     *
     * @mbggenerated
     */
    List<PublicItemCustom> selectByExample(PublicItemCustomExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_custom
     *
     * @mbggenerated
     */
    PublicItemCustom selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_custom
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") PublicItemCustom record, @Param("example") PublicItemCustomExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_custom
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") PublicItemCustom record, @Param("example") PublicItemCustomExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_custom
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PublicItemCustom record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_custom
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PublicItemCustom record);
}