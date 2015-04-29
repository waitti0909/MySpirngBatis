package com.foya.dao.mybatis.mapper;



import com.foya.dao.mybatis.model.TbInvSiteTxn;
import com.foya.dao.mybatis.model.UTbInvSiteTxnExample;
import com.foya.noms.dto.inv.TbInvSiteTxnDTO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UTbInvSiteTxnMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_SITE_TXN
	 * @mbggenerated  Mon Nov 17 10:06:36 CST 2014
	 */
	int countByExample(UTbInvSiteTxnExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_SITE_TXN
	 * @mbggenerated  Mon Nov 17 10:06:36 CST 2014
	 */
	int deleteByExample(UTbInvSiteTxnExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_SITE_TXN
	 * @mbggenerated  Mon Nov 17 10:06:36 CST 2014
	 */
	int deleteByPrimaryKey(Long site_txn_no);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_SITE_TXN
	 * @mbggenerated  Mon Nov 17 10:06:36 CST 2014
	 */
	int insert(TbInvSiteTxnDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_SITE_TXN
	 * @mbggenerated  Mon Nov 17 10:06:36 CST 2014
	 */
	int insertSelective(TbInvSiteTxnDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_SITE_TXN
	 * @mbggenerated  Mon Nov 17 10:06:36 CST 2014
	 */
	List<TbInvSiteTxnDTO> selectByExample(UTbInvSiteTxnExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_SITE_TXN
	 * @mbggenerated  Mon Nov 17 10:06:36 CST 2014
	 */
	TbInvSiteTxnDTO selectByPrimaryKey(Long site_txn_no);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_SITE_TXN
	 * @mbggenerated  Mon Nov 17 10:06:36 CST 2014
	 */
	int updateByExampleSelective(@Param("record") TbInvSiteTxnDTO record,
			@Param("example") UTbInvSiteTxnExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_SITE_TXN
	 * @mbggenerated  Mon Nov 17 10:06:36 CST 2014
	 */
	int updateByExample(@Param("record") TbInvSiteTxnDTO record,
			@Param("example") UTbInvSiteTxnExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_SITE_TXN
	 * @mbggenerated  Mon Nov 17 10:06:36 CST 2014
	 */
	int updateByPrimaryKeySelective(TbInvSiteTxnDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_INV_SITE_TXN
	 * @mbggenerated  Mon Nov 17 10:06:36 CST 2014
	 */
	int updateByPrimaryKey(TbInvSiteTxnDTO record);
	
	//InvDao-20141125 start
	/**
     * select TB_INV_SITE_TXN where By site_id,order_no
     * 
     */
    List<TbInvSiteTxn> selectInvSiteTxnBySiteIdAndOrderNo(TbInvSiteTxn record);
    
    /**
     * UPDATE TB_INV_SITE_TXN.to_erp_date By site_id,order_no,txt_type    
     * 
     */
    int updateBySiteIdAndOrderNoSelective(TbInvSiteTxn record);
    //InvDao-20141125 end
}