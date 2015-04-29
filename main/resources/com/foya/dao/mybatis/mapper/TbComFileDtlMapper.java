package com.foya.dao.mybatis.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbComFileDtl;
import com.foya.dao.mybatis.model.TbComFileDtlExample;

public interface TbComFileDtlMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	int countByExample(TbComFileDtlExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	int deleteByExample(TbComFileDtlExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	int deleteByPrimaryKey(BigDecimal FILE_DTL_SEQ_ID);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	int insert(TbComFileDtl record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	int insertSelective(TbComFileDtl record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	List<TbComFileDtl> selectByExample(TbComFileDtlExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	TbComFileDtl selectByPrimaryKey(BigDecimal FILE_DTL_SEQ_ID);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	int updateByExampleSelective(@Param("record") TbComFileDtl record, @Param("example") TbComFileDtlExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	int updateByExample(@Param("record") TbComFileDtl record, @Param("example") TbComFileDtlExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	int updateByPrimaryKeySelective(TbComFileDtl record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_FILE_DTL
	 * @mbggenerated  Thu Sep 04 17:43:10 CST 2014
	 */
	int updateByPrimaryKey(TbComFileDtl record);
}