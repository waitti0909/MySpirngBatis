package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbLsLessorAdded;
import com.foya.dao.mybatis.model.TbLsLessorAddedExample;
import com.foya.dao.mybatis.model.TbLsLessorAddedKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbLsLessorAddedMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR_ADDED
	 * @mbggenerated  Wed Apr 15 15:38:30 CST 2015
	 */
	int countByExample(TbLsLessorAddedExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR_ADDED
	 * @mbggenerated  Wed Apr 15 15:38:30 CST 2015
	 */
	int deleteByExample(TbLsLessorAddedExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR_ADDED
	 * @mbggenerated  Wed Apr 15 15:38:30 CST 2015
	 */
	int deleteByPrimaryKey(TbLsLessorAddedKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR_ADDED
	 * @mbggenerated  Wed Apr 15 15:38:30 CST 2015
	 */
	int insert(TbLsLessorAdded record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR_ADDED
	 * @mbggenerated  Wed Apr 15 15:38:30 CST 2015
	 */
	int insertSelective(TbLsLessorAdded record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR_ADDED
	 * @mbggenerated  Wed Apr 15 15:38:30 CST 2015
	 */
	List<TbLsLessorAdded> selectByExample(TbLsLessorAddedExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR_ADDED
	 * @mbggenerated  Wed Apr 15 15:38:30 CST 2015
	 */
	TbLsLessorAdded selectByPrimaryKey(TbLsLessorAddedKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR_ADDED
	 * @mbggenerated  Wed Apr 15 15:38:30 CST 2015
	 */
	int updateByExampleSelective(@Param("record") TbLsLessorAdded record,
			@Param("example") TbLsLessorAddedExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR_ADDED
	 * @mbggenerated  Wed Apr 15 15:38:30 CST 2015
	 */
	int updateByExample(@Param("record") TbLsLessorAdded record,
			@Param("example") TbLsLessorAddedExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR_ADDED
	 * @mbggenerated  Wed Apr 15 15:38:30 CST 2015
	 */
	int updateByPrimaryKeySelective(TbLsLessorAdded record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LESSOR_ADDED
	 * @mbggenerated  Wed Apr 15 15:38:30 CST 2015
	 */
	int updateByPrimaryKey(TbLsLessorAdded record);
}