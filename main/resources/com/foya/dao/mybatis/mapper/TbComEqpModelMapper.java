package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbComEqpModelMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_EQP_MODEL
	 * @mbggenerated  Wed Oct 29 14:20:51 CST 2014
	 */
	int countByExample(TbComEqpModelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_EQP_MODEL
	 * @mbggenerated  Wed Oct 29 14:20:51 CST 2014
	 */
	int deleteByExample(TbComEqpModelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_EQP_MODEL
	 * @mbggenerated  Wed Oct 29 14:20:51 CST 2014
	 */
	int deleteByPrimaryKey(String EQP_MODEL_ID);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_EQP_MODEL
	 * @mbggenerated  Wed Oct 29 14:20:51 CST 2014
	 */
	int insert(TbComEqpModel record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_EQP_MODEL
	 * @mbggenerated  Wed Oct 29 14:20:51 CST 2014
	 */
	int insertSelective(TbComEqpModel record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_EQP_MODEL
	 * @mbggenerated  Wed Oct 29 14:20:51 CST 2014
	 */
	List<TbComEqpModel> selectByExample(TbComEqpModelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_EQP_MODEL
	 * @mbggenerated  Wed Oct 29 14:20:51 CST 2014
	 */
	TbComEqpModel selectByPrimaryKey(String EQP_MODEL_ID);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_EQP_MODEL
	 * @mbggenerated  Wed Oct 29 14:20:51 CST 2014
	 */
	int updateByExampleSelective(@Param("record") TbComEqpModel record, @Param("example") TbComEqpModelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_EQP_MODEL
	 * @mbggenerated  Wed Oct 29 14:20:51 CST 2014
	 */
	int updateByExample(@Param("record") TbComEqpModel record, @Param("example") TbComEqpModelExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_EQP_MODEL
	 * @mbggenerated  Wed Oct 29 14:20:51 CST 2014
	 */
	int updateByPrimaryKeySelective(TbComEqpModel record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_COM_EQP_MODEL
	 * @mbggenerated  Wed Oct 29 14:20:51 CST 2014
	 */
	int updateByPrimaryKey(TbComEqpModel record);
}