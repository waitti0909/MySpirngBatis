package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbLsLocElec;
import com.foya.dao.mybatis.model.TbLsLocElecExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.foya.dao.mybatis.model.TbLsLocElecKey;

public interface TbLsLocElecMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LOC_ELEC
	 * @mbggenerated  Thu Mar 19 20:33:34 CST 2015
	 */
	int countByExample(TbLsLocElecExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LOC_ELEC
	 * @mbggenerated  Thu Mar 19 20:33:34 CST 2015
	 */
	int deleteByExample(TbLsLocElecExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LOC_ELEC
	 * @mbggenerated  Thu Mar 19 20:33:34 CST 2015
	 */
	int deleteByPrimaryKey(TbLsLocElecKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LOC_ELEC
	 * @mbggenerated  Thu Mar 19 20:33:34 CST 2015
	 */
	int insert(TbLsLocElec record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LOC_ELEC
	 * @mbggenerated  Thu Mar 19 20:33:34 CST 2015
	 */
	int insertSelective(TbLsLocElec record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LOC_ELEC
	 * @mbggenerated  Thu Mar 19 20:33:34 CST 2015
	 */
	List<TbLsLocElec> selectByExample(TbLsLocElecExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LOC_ELEC
	 * @mbggenerated  Thu Mar 19 20:33:34 CST 2015
	 */
	TbLsLocElec selectByPrimaryKey(TbLsLocElecKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LOC_ELEC
	 * @mbggenerated  Thu Mar 19 20:33:34 CST 2015
	 */
	int updateByExampleSelective(@Param("record") TbLsLocElec record,
			@Param("example") TbLsLocElecExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LOC_ELEC
	 * @mbggenerated  Thu Mar 19 20:33:34 CST 2015
	 */
	int updateByExample(@Param("record") TbLsLocElec record,
			@Param("example") TbLsLocElecExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LOC_ELEC
	 * @mbggenerated  Thu Mar 19 20:33:34 CST 2015
	 */
	int updateByPrimaryKeySelective(TbLsLocElec record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_LOC_ELEC
	 * @mbggenerated  Thu Mar 19 20:33:34 CST 2015
	 */
	int updateByPrimaryKey(TbLsLocElec record);
	
	
}