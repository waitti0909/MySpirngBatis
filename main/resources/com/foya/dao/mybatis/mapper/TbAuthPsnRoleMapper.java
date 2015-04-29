package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbAuthPsnRole;
import com.foya.dao.mybatis.model.TbAuthPsnRoleExample;
import com.foya.dao.mybatis.model.TbAuthPsnRoleKey;

public interface TbAuthPsnRoleMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_PSN_ROLE
	 * @mbggenerated  Mon Jul 28 17:14:45 CST 2014
	 */
	int countByExample(TbAuthPsnRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_PSN_ROLE
	 * @mbggenerated  Mon Jul 28 17:14:45 CST 2014
	 */
	int deleteByExample(TbAuthPsnRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_PSN_ROLE
	 * @mbggenerated  Mon Jul 28 17:14:45 CST 2014
	 */
	int deleteByPrimaryKey(TbAuthPsnRoleKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_PSN_ROLE
	 * @mbggenerated  Mon Jul 28 17:14:45 CST 2014
	 */
	int insert(TbAuthPsnRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_PSN_ROLE
	 * @mbggenerated  Mon Jul 28 17:14:45 CST 2014
	 */
	int insertSelective(TbAuthPsnRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_PSN_ROLE
	 * @mbggenerated  Mon Jul 28 17:14:45 CST 2014
	 */
	List<TbAuthPsnRole> selectByExample(TbAuthPsnRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_PSN_ROLE
	 * @mbggenerated  Mon Jul 28 17:14:45 CST 2014
	 */
	TbAuthPsnRole selectByPrimaryKey(TbAuthPsnRoleKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_PSN_ROLE
	 * @mbggenerated  Mon Jul 28 17:14:45 CST 2014
	 */
	int updateByExampleSelective(@Param("record") TbAuthPsnRole record,
			@Param("example") TbAuthPsnRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_PSN_ROLE
	 * @mbggenerated  Mon Jul 28 17:14:45 CST 2014
	 */
	int updateByExample(@Param("record") TbAuthPsnRole record,
			@Param("example") TbAuthPsnRoleExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_PSN_ROLE
	 * @mbggenerated  Mon Jul 28 17:14:45 CST 2014
	 */
	int updateByPrimaryKeySelective(TbAuthPsnRole record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_AUTH_PSN_ROLE
	 * @mbggenerated  Mon Jul 28 17:14:45 CST 2014
	 */
	int updateByPrimaryKey(TbAuthPsnRole record);
}