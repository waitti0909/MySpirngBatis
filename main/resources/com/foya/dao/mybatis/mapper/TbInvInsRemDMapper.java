package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbInvInsRemD;
import com.foya.dao.mybatis.model.TbInvInsRemDExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbInvInsRemDMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	int countByExample(TbInvInsRemDExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	int deleteByExample(TbInvInsRemDExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	int deleteByPrimaryKey(Long DTL_SEQ);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	int insert(TbInvInsRemD record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	int insertSelective(TbInvInsRemD record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	List<TbInvInsRemD> selectByExample(TbInvInsRemDExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	TbInvInsRemD selectByPrimaryKey(Long DTL_SEQ);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	int updateByExampleSelective(@Param("record") TbInvInsRemD record,
			@Param("example") TbInvInsRemDExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	int updateByExample(@Param("record") TbInvInsRemD record,
			@Param("example") TbInvInsRemDExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	int updateByPrimaryKeySelective(TbInvInsRemD record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_INS_REM_D
	 * @mbggenerated  Mon Feb 02 17:12:51 CST 2015
	 */
	int updateByPrimaryKey(TbInvInsRemD record);
}