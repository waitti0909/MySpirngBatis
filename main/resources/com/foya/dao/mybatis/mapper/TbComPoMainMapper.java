package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbComPoMain;
import com.foya.dao.mybatis.model.TbComPoMainExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbComPoMainMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_PO_MAIN
	 * @mbggenerated  Wed Apr 15 16:01:18 CST 2015
	 */
	int countByExample(TbComPoMainExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_PO_MAIN
	 * @mbggenerated  Wed Apr 15 16:01:18 CST 2015
	 */
	int deleteByExample(TbComPoMainExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_PO_MAIN
	 * @mbggenerated  Wed Apr 15 16:01:18 CST 2015
	 */
	int deleteByPrimaryKey(Long PO_ID);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_PO_MAIN
	 * @mbggenerated  Wed Apr 15 16:01:18 CST 2015
	 */
	int insert(TbComPoMain record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_PO_MAIN
	 * @mbggenerated  Wed Apr 15 16:01:18 CST 2015
	 */
	int insertSelective(TbComPoMain record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_PO_MAIN
	 * @mbggenerated  Wed Apr 15 16:01:18 CST 2015
	 */
	List<TbComPoMain> selectByExample(TbComPoMainExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_PO_MAIN
	 * @mbggenerated  Wed Apr 15 16:01:18 CST 2015
	 */
	TbComPoMain selectByPrimaryKey(Long PO_ID);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_PO_MAIN
	 * @mbggenerated  Wed Apr 15 16:01:18 CST 2015
	 */
	int updateByExampleSelective(@Param("record") TbComPoMain record, @Param("example") TbComPoMainExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_PO_MAIN
	 * @mbggenerated  Wed Apr 15 16:01:18 CST 2015
	 */
	int updateByExample(@Param("record") TbComPoMain record, @Param("example") TbComPoMainExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_PO_MAIN
	 * @mbggenerated  Wed Apr 15 16:01:18 CST 2015
	 */
	int updateByPrimaryKeySelective(TbComPoMain record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_PO_MAIN
	 * @mbggenerated  Wed Apr 15 16:01:18 CST 2015
	 */
	int updateByPrimaryKey(TbComPoMain record);
}