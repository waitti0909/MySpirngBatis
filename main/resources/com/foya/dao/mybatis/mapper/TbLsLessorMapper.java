package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbLsLessor;
import com.foya.dao.mybatis.model.TbLsLessorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.foya.dao.mybatis.model.TbLsLessorKey;

public interface TbLsLessorMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR
	 * @mbggenerated  Sun Mar 22 11:26:01 CST 2015
	 */
	int countByExample(TbLsLessorExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR
	 * @mbggenerated  Sun Mar 22 11:26:01 CST 2015
	 */
	int deleteByExample(TbLsLessorExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR
	 * @mbggenerated  Sun Mar 22 11:26:01 CST 2015
	 */
	int deleteByPrimaryKey(TbLsLessorKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR
	 * @mbggenerated  Sun Mar 22 11:26:01 CST 2015
	 */
	int insert(TbLsLessor record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR
	 * @mbggenerated  Sun Mar 22 11:26:01 CST 2015
	 */
	int insertSelective(TbLsLessor record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR
	 * @mbggenerated  Sun Mar 22 11:26:01 CST 2015
	 */
	List<TbLsLessor> selectByExample(TbLsLessorExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR
	 * @mbggenerated  Sun Mar 22 11:26:01 CST 2015
	 */
	TbLsLessor selectByPrimaryKey(TbLsLessorKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR
	 * @mbggenerated  Sun Mar 22 11:26:01 CST 2015
	 */
	int updateByExampleSelective(@Param("record") TbLsLessor record,
			@Param("example") TbLsLessorExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR
	 * @mbggenerated  Sun Mar 22 11:26:01 CST 2015
	 */
	int updateByExample(@Param("record") TbLsLessor record,
			@Param("example") TbLsLessorExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR
	 * @mbggenerated  Sun Mar 22 11:26:01 CST 2015
	 */
	int updateByPrimaryKeySelective(TbLsLessor record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR
	 * @mbggenerated  Sun Mar 22 11:26:01 CST 2015
	 */
	int updateByPrimaryKey(TbLsLessor record);
}