package com.foya.dao.mybatis.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbAuthMenuFun;
import com.foya.dao.mybatis.model.TbAuthMenuFunExample;

public interface TbAuthMenuFunMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_MENU_FUN
	 * @mbggenerated  Thu Aug 14 16:49:09 CST 2014
	 */
	int countByExample(TbAuthMenuFunExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_MENU_FUN
	 * @mbggenerated  Thu Aug 14 16:49:09 CST 2014
	 */
	int deleteByExample(TbAuthMenuFunExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_MENU_FUN
	 * @mbggenerated  Thu Aug 14 16:49:09 CST 2014
	 */
	int deleteByPrimaryKey(BigDecimal menuFunId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_MENU_FUN
	 * @mbggenerated  Thu Aug 14 16:49:09 CST 2014
	 */
	int insert(TbAuthMenuFun record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_MENU_FUN
	 * @mbggenerated  Thu Aug 14 16:49:09 CST 2014
	 */
	int insertSelective(TbAuthMenuFun record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_MENU_FUN
	 * @mbggenerated  Thu Aug 14 16:49:09 CST 2014
	 */
	List<TbAuthMenuFun> selectByExample(TbAuthMenuFunExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_MENU_FUN
	 * @mbggenerated  Thu Aug 14 16:49:09 CST 2014
	 */
	TbAuthMenuFun selectByPrimaryKey(BigDecimal menuFunId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_MENU_FUN
	 * @mbggenerated  Thu Aug 14 16:49:09 CST 2014
	 */
	int updateByExampleSelective(@Param("record") TbAuthMenuFun record,
			@Param("example") TbAuthMenuFunExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_MENU_FUN
	 * @mbggenerated  Thu Aug 14 16:49:09 CST 2014
	 */
	int updateByExample(@Param("record") TbAuthMenuFun record,
			@Param("example") TbAuthMenuFunExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_MENU_FUN
	 * @mbggenerated  Thu Aug 14 16:49:09 CST 2014
	 */
	int updateByPrimaryKeySelective(TbAuthMenuFun record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_MENU_FUN
	 * @mbggenerated  Thu Aug 14 16:49:09 CST 2014
	 */
	int updateByPrimaryKey(TbAuthMenuFun record);
}