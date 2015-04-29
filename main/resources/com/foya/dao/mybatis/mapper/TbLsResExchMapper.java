package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbLsResExch;
import com.foya.dao.mybatis.model.TbLsResExchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbLsResExchMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	int countByExample(TbLsResExchExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	int deleteByExample(TbLsResExchExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	int insert(TbLsResExch record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	int insertSelective(TbLsResExch record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	List<TbLsResExch> selectByExample(TbLsResExchExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	int updateByExampleSelective(@Param("record") TbLsResExch record,
			@Param("example") TbLsResExchExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_LS_RES_EXCH
	 * @mbggenerated  Tue Apr 07 10:25:19 CST 2015
	 */
	int updateByExample(@Param("record") TbLsResExch record,
			@Param("example") TbLsResExchExample example);
}