package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbLsReward;
import com.foya.dao.mybatis.model.TbLsRewardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbLsRewardMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	int countByExample(TbLsRewardExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	int deleteByExample(TbLsRewardExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	int deleteByPrimaryKey(String LS_NO);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	int insert(TbLsReward record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	int insertSelective(TbLsReward record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	List<TbLsReward> selectByExample(TbLsRewardExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	TbLsReward selectByPrimaryKey(String LS_NO);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	int updateByExampleSelective(@Param("record") TbLsReward record,
			@Param("example") TbLsRewardExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	int updateByExample(@Param("record") TbLsReward record,
			@Param("example") TbLsRewardExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	int updateByPrimaryKeySelective(TbLsReward record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_REWARD
	 * @mbggenerated  Wed Jan 28 16:10:34 CST 2015
	 */
	int updateByPrimaryKey(TbLsReward record);
}