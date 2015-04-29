package com.foya.dao.mybatis.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbAuthRoleMenuFun;
import com.foya.dao.mybatis.model.TbAuthRoleMenuFunExample;

public interface TbAuthRoleMenuFunMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    int countByExample(TbAuthRoleMenuFunExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    int deleteByExample(TbAuthRoleMenuFunExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    int deleteByPrimaryKey(BigDecimal ROLE_MENU_FUN_ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    int insert(TbAuthRoleMenuFun record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    int insertSelective(TbAuthRoleMenuFun record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    List<TbAuthRoleMenuFun> selectByExample(TbAuthRoleMenuFunExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    TbAuthRoleMenuFun selectByPrimaryKey(BigDecimal ROLE_MENU_FUN_ID);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    int updateByExampleSelective(@Param("record") TbAuthRoleMenuFun record, @Param("example") TbAuthRoleMenuFunExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    int updateByExample(@Param("record") TbAuthRoleMenuFun record, @Param("example") TbAuthRoleMenuFunExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    int updateByPrimaryKeySelective(TbAuthRoleMenuFun record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table TB_AUTH_ROLE_MENU_FUN
     *
     * @mbggenerated Wed Aug 13 16:52:10 CST 2014
     */
    int updateByPrimaryKey(TbAuthRoleMenuFun record);
}