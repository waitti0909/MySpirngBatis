package com.foya.dao.mybatis.mapper;

import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.dao.mybatis.model.TbInvTxnExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbInvTxnMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TXN
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	int countByExample(TbInvTxnExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TXN
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	int deleteByExample(TbInvTxnExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TXN
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	int deleteByPrimaryKey(Long inv_txn_no);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TXN
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	int insert(TbInvTxn record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TXN
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	int insertSelective(TbInvTxn record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TXN
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	List<TbInvTxn> selectByExample(TbInvTxnExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TXN
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	TbInvTxn selectByPrimaryKey(Long inv_txn_no);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TXN
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	int updateByExampleSelective(@Param("record") TbInvTxn record,
			@Param("example") TbInvTxnExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TXN
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	int updateByExample(@Param("record") TbInvTxn record,
			@Param("example") TbInvTxnExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TXN
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	int updateByPrimaryKeySelective(TbInvTxn record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_TXN
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	int updateByPrimaryKey(TbInvTxn record);
}