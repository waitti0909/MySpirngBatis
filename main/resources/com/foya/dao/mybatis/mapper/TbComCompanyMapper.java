package com.foya.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComCompanyExample;

public interface TbComCompanyMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_COMPANY
	 * @mbggenerated  Fri Jan 09 17:37:20 CST 2015
	 */
	int countByExample(TbComCompanyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_COMPANY
	 * @mbggenerated  Fri Jan 09 17:37:20 CST 2015
	 */
	int deleteByExample(TbComCompanyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_COMPANY
	 * @mbggenerated  Fri Jan 09 17:37:20 CST 2015
	 */
	int deleteByPrimaryKey(String CO_VAT_NO);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_COMPANY
	 * @mbggenerated  Fri Jan 09 17:37:20 CST 2015
	 */
	int insert(TbComCompany record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_COMPANY
	 * @mbggenerated  Fri Jan 09 17:37:20 CST 2015
	 */
	int insertSelective(TbComCompany record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_COMPANY
	 * @mbggenerated  Fri Jan 09 17:37:20 CST 2015
	 */
	List<TbComCompany> selectByExample(TbComCompanyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_COMPANY
	 * @mbggenerated  Fri Jan 09 17:37:20 CST 2015
	 */
	TbComCompany selectByPrimaryKey(String CO_VAT_NO);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_COMPANY
	 * @mbggenerated  Fri Jan 09 17:37:20 CST 2015
	 */
	int updateByExampleSelective(@Param("record") TbComCompany record, @Param("example") TbComCompanyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_COMPANY
	 * @mbggenerated  Fri Jan 09 17:37:20 CST 2015
	 */
	int updateByExample(@Param("record") TbComCompany record, @Param("example") TbComCompanyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_COMPANY
	 * @mbggenerated  Fri Jan 09 17:37:20 CST 2015
	 */
	int updateByPrimaryKeySelective(TbComCompany record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_COMPANY
	 * @mbggenerated  Fri Jan 09 17:37:20 CST 2015
	 */
	int updateByPrimaryKey(TbComCompany record);
}