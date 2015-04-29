package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbComTownDomainTeam;
import com.foya.dao.mybatis.model.TbComTownDomainTeamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbComTownDomainTeamMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_TOWN_DOMAIN_TEAM
	 * @mbggenerated  Tue Nov 04 17:22:09 CST 2014
	 */
	int countByExample(TbComTownDomainTeamExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_TOWN_DOMAIN_TEAM
	 * @mbggenerated  Tue Nov 04 17:22:09 CST 2014
	 */
	int deleteByExample(TbComTownDomainTeamExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_TOWN_DOMAIN_TEAM
	 * @mbggenerated  Tue Nov 04 17:22:09 CST 2014
	 */
	int deleteByPrimaryKey(String REGION_ID);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_TOWN_DOMAIN_TEAM
	 * @mbggenerated  Tue Nov 04 17:22:09 CST 2014
	 */
	int insert(TbComTownDomainTeam record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_TOWN_DOMAIN_TEAM
	 * @mbggenerated  Tue Nov 04 17:22:09 CST 2014
	 */
	int insertSelective(TbComTownDomainTeam record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_TOWN_DOMAIN_TEAM
	 * @mbggenerated  Tue Nov 04 17:22:09 CST 2014
	 */
	List<TbComTownDomainTeam> selectByExample(TbComTownDomainTeamExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_TOWN_DOMAIN_TEAM
	 * @mbggenerated  Tue Nov 04 17:22:09 CST 2014
	 */
	TbComTownDomainTeam selectByPrimaryKey(String REGION_ID);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_TOWN_DOMAIN_TEAM
	 * @mbggenerated  Tue Nov 04 17:22:09 CST 2014
	 */
	int updateByExampleSelective(@Param("record") TbComTownDomainTeam record, @Param("example") TbComTownDomainTeamExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_TOWN_DOMAIN_TEAM
	 * @mbggenerated  Tue Nov 04 17:22:09 CST 2014
	 */
	int updateByExample(@Param("record") TbComTownDomainTeam record, @Param("example") TbComTownDomainTeamExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_TOWN_DOMAIN_TEAM
	 * @mbggenerated  Tue Nov 04 17:22:09 CST 2014
	 */
	int updateByPrimaryKeySelective(TbComTownDomainTeam record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_TOWN_DOMAIN_TEAM
	 * @mbggenerated  Tue Nov 04 17:22:09 CST 2014
	 */
	int updateByPrimaryKey(TbComTownDomainTeam record);
}