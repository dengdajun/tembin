package com.base.database.keymove.mapper;

import com.base.database.keymove.model.KeyMoveList;
import com.base.database.keymove.model.KeyMoveListExample;
import com.base.database.keymove.model.KeyMoveListWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface KeyMoveListMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table key_move_list
     *
     * @mbggenerated
     */
    int countByExample(KeyMoveListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table key_move_list
     *
     * @mbggenerated
     */
    int deleteByExample(KeyMoveListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table key_move_list
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table key_move_list
     *
     * @mbggenerated
     */
    int insert(KeyMoveListWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table key_move_list
     *
     * @mbggenerated
     */
    int insertSelective(KeyMoveListWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table key_move_list
     *
     * @mbggenerated
     */
    List<KeyMoveListWithBLOBs> selectByExampleWithBLOBs(KeyMoveListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table key_move_list
     *
     * @mbggenerated
     */
    List<KeyMoveList> selectByExample(KeyMoveListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table key_move_list
     *
     * @mbggenerated
     */
    KeyMoveListWithBLOBs selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table key_move_list
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") KeyMoveListWithBLOBs record, @Param("example") KeyMoveListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table key_move_list
     *
     * @mbggenerated
     */
    int updateByExampleWithBLOBs(@Param("record") KeyMoveListWithBLOBs record, @Param("example") KeyMoveListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table key_move_list
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") KeyMoveList record, @Param("example") KeyMoveListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table key_move_list
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(KeyMoveListWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table key_move_list
     *
     * @mbggenerated
     */
    int updateByPrimaryKeyWithBLOBs(KeyMoveListWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table key_move_list
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(KeyMoveList record);
}