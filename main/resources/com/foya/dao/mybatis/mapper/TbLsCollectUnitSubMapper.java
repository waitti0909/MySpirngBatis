package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbLsCollectUnitSub;
import com.foya.dao.mybatis.model.TbLsCollectUnitSubExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.foya.dao.mybatis.model.TbLsCollectUnitSubKey;

public interface TbLsCollectUnitSubMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_COLLECT_UNIT_SUB
	 * @mbggenerated  Mon Mar 23 21:05:34 CST 2015
	 */
	int countByExample(TbLsCollectUnitSubExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_COLLECT_UNIT_SUB
	 * @mbggenerated  Mon Mar 23 21:05:34 CST 2015
	 */
	int deleteByExample(TbLsCollectUnitSubExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_COLLECT_UNIT_SUB
	 * @mbggenerated  Mon Mar 23 21:05:34 CST 2015
	 */
	int deleteByPrimaryKey(TbLsCollectUnitSubKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_COLLECT_UNIT_SUB
	 * @mbggenerated  Mon Mar 23 21:05:34 CST 2015
	 */
	int insert(TbLsCollectUnitSub record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_COLLECT_UNIT_SUB
	 * @mbggenerated  Mon Mar 23 21:05:34 CST 2015
	 */
	int insertSelective(TbLsCollectUnitSub record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_COLLECT_UNIT_SUB
	 * @mbggenerated  Mon Mar 23 21:05:34 CST 2015
	 */
	List<TbLsCollectUnitSub> selectByExample(TbLsCollectUnitSubExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_COLLECT_UNIT_SUB
	 * @mbggenerated  Mon Mar 23 21:05:34 CST 2015
	 */
	TbLsCollectUnitSub selectByPrimaryKey(TbLsCollectUnitSubKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_COLLECT_UNIT_SUB
	 * @mbggenerated  Mon Mar 23 21:05:34 CST 2015
	 */
	int updateByExampleSelective(@Param("record") TbLsCollectUnitSub record,
			@Param("example") TbLsCollectUnitSubExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_COLLECT_UNIT_SUB
	 * @mbggenerated  Mon Mar 23 21:05:34 CST 2015
	 */
	int updateByExample(@Param("record") TbLsCollectUnitSub record,
			@Param("example") TbLsCollectUnitSubExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_COLLECT_UNIT_SUB
	 * @mbggenerated  Mon Mar 23 21:05:34 CST 2015
	 */
	int updateByPrimaryKeySelective(TbLsCollectUnitSub record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_COLLECT_UNIT_SUB
	 * @mbggenerated  Mon Mar 23 21:05:34 CST 2015
	 */
	int updateByPrimaryKey(TbLsCollectUnitSub record);
}