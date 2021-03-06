package com.base.database.userinfo.mapper;

import com.base.database.userinfo.model.UsercontrollerOrg;
import com.base.database.userinfo.model.UsercontrollerOrgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsercontrollerOrgMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_org
     *
     * @mbggenerated
     */
    int countByExample(UsercontrollerOrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_org
     *
     * @mbggenerated
     */
    int deleteByExample(UsercontrollerOrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_org
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long orgId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_org
     *
     * @mbggenerated
     */
    int insert(UsercontrollerOrg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_org
     *
     * @mbggenerated
     */
    int insertSelective(UsercontrollerOrg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_org
     *
     * @mbggenerated
     */
    List<UsercontrollerOrg> selectByExample(UsercontrollerOrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_org
     *
     * @mbggenerated
     */
    UsercontrollerOrg selectByPrimaryKey(Long orgId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_org
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") UsercontrollerOrg record, @Param("example") UsercontrollerOrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_org
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") UsercontrollerOrg record, @Param("example") UsercontrollerOrgExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_org
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UsercontrollerOrg record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table usercontroller_org
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UsercontrollerOrg record);
}