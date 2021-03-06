package com.base.database.publicd.mapper;

import com.base.database.publicd.model.PublicItemPictureaddrAndAttr;
import com.base.database.publicd.model.PublicItemPictureaddrAndAttrExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PublicItemPictureaddrAndAttrMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_pictureaddr_and_attr
     *
     * @mbggenerated
     */
    int countByExample(PublicItemPictureaddrAndAttrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_pictureaddr_and_attr
     *
     * @mbggenerated
     */
    int deleteByExample(PublicItemPictureaddrAndAttrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_pictureaddr_and_attr
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_pictureaddr_and_attr
     *
     * @mbggenerated
     */
    int insert(PublicItemPictureaddrAndAttr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_pictureaddr_and_attr
     *
     * @mbggenerated
     */
    int insertSelective(PublicItemPictureaddrAndAttr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_pictureaddr_and_attr
     *
     * @mbggenerated
     */
    List<PublicItemPictureaddrAndAttr> selectByExample(PublicItemPictureaddrAndAttrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_pictureaddr_and_attr
     *
     * @mbggenerated
     */
    PublicItemPictureaddrAndAttr selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_pictureaddr_and_attr
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") PublicItemPictureaddrAndAttr record, @Param("example") PublicItemPictureaddrAndAttrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_pictureaddr_and_attr
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") PublicItemPictureaddrAndAttr record, @Param("example") PublicItemPictureaddrAndAttrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_pictureaddr_and_attr
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(PublicItemPictureaddrAndAttr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public_item_pictureaddr_and_attr
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(PublicItemPictureaddrAndAttr record);
}