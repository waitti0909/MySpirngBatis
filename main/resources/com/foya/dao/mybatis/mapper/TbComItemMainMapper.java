package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbComItemMain;
import com.foya.dao.mybatis.model.TbComItemMainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbComItemMainMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ITEM_MAIN
	 * @mbggenerated  Wed Jan 14 18:01:45 GMT+08:00 2015
	 */
	int countByExample(TbComItemMainExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ITEM_MAIN
	 * @mbggenerated  Wed Jan 14 18:01:45 GMT+08:00 2015
	 */
	int deleteByExample(TbComItemMainExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ITEM_MAIN
	 * @mbggenerated  Wed Jan 14 18:01:45 GMT+08:00 2015
	 */
	int deleteByPrimaryKey(String ITEM_ID);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ITEM_MAIN
	 * @mbggenerated  Wed Jan 14 18:01:45 GMT+08:00 2015
	 */
	int insert(TbComItemMain record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ITEM_MAIN
	 * @mbggenerated  Wed Jan 14 18:01:45 GMT+08:00 2015
	 */
	int insertSelective(TbComItemMain record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ITEM_MAIN
	 * @mbggenerated  Wed Jan 14 18:01:45 GMT+08:00 2015
	 */
	List<TbComItemMain> selectByExample(TbComItemMainExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ITEM_MAIN
	 * @mbggenerated  Wed Jan 14 18:01:45 GMT+08:00 2015
	 */
	TbComItemMain selectByPrimaryKey(String ITEM_ID);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ITEM_MAIN
	 * @mbggenerated  Wed Jan 14 18:01:45 GMT+08:00 2015
	 */
	int updateByExampleSelective(@Param("record") TbComItemMain record,
			@Param("example") TbComItemMainExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ITEM_MAIN
	 * @mbggenerated  Wed Jan 14 18:01:45 GMT+08:00 2015
	 */
	int updateByExample(@Param("record") TbComItemMain record,
			@Param("example") TbComItemMainExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ITEM_MAIN
	 * @mbggenerated  Wed Jan 14 18:01:45 GMT+08:00 2015
	 */
	int updateByPrimaryKeySelective(TbComItemMain record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_ITEM_MAIN
	 * @mbggenerated  Wed Jan 14 18:01:45 GMT+08:00 2015
	 */
	int updateByPrimaryKey(TbComItemMain record);
}