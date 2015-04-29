package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbInvTr;
import com.foya.dao.mybatis.model.TbInvTrExample;

public interface TbInvTrMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TR
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	int countByExample(TbInvTrExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TR
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	int deleteByExample(TbInvTrExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TR
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	int deleteByPrimaryKey(String tr_no);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TR
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	int insert(TbInvTr record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TR
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	int insertSelective(TbInvTr record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TR
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	List<TbInvTr> selectByExample(TbInvTrExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TR
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	TbInvTr selectByPrimaryKey(String tr_no);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TR
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	int updateByExampleSelective(@Param("record") TbInvTr record,
			@Param("example") TbInvTrExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TR
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	int updateByExample(@Param("record") TbInvTr record,
			@Param("example") TbInvTrExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TR
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	int updateByPrimaryKeySelective(TbInvTr record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TR
	 * @mbggenerated  Thu Jan 08 11:08:08 CST 2015
	 */
	int updateByPrimaryKey(TbInvTr record);
}