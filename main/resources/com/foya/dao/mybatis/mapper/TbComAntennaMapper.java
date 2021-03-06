package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbComAntenna;
import com.foya.dao.mybatis.model.TbComAntennaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbComAntennaMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ANTENNA
	 * @mbggenerated  Wed Oct 29 14:20:36 CST 2014
	 */
	int countByExample(TbComAntennaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ANTENNA
	 * @mbggenerated  Wed Oct 29 14:20:36 CST 2014
	 */
	int deleteByExample(TbComAntennaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ANTENNA
	 * @mbggenerated  Wed Oct 29 14:20:36 CST 2014
	 */
	int deleteByPrimaryKey(String ANTENNA_ID);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ANTENNA
	 * @mbggenerated  Wed Oct 29 14:20:36 CST 2014
	 */
	int insert(TbComAntenna record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ANTENNA
	 * @mbggenerated  Wed Oct 29 14:20:36 CST 2014
	 */
	int insertSelective(TbComAntenna record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ANTENNA
	 * @mbggenerated  Wed Oct 29 14:20:36 CST 2014
	 */
	List<TbComAntenna> selectByExample(TbComAntennaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ANTENNA
	 * @mbggenerated  Wed Oct 29 14:20:36 CST 2014
	 */
	TbComAntenna selectByPrimaryKey(String ANTENNA_ID);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ANTENNA
	 * @mbggenerated  Wed Oct 29 14:20:36 CST 2014
	 */
	int updateByExampleSelective(@Param("record") TbComAntenna record, @Param("example") TbComAntennaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ANTENNA
	 * @mbggenerated  Wed Oct 29 14:20:36 CST 2014
	 */
	int updateByExample(@Param("record") TbComAntenna record, @Param("example") TbComAntennaExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ANTENNA
	 * @mbggenerated  Wed Oct 29 14:20:36 CST 2014
	 */
	int updateByPrimaryKeySelective(TbComAntenna record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ANTENNA
	 * @mbggenerated  Wed Oct 29 14:20:36 CST 2014
	 */
	int updateByPrimaryKey(TbComAntenna record);
}