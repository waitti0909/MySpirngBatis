package com.foya.dao.mybatis.mapper;

import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.dao.mybatis.model.UTbInvSrlExample;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UTbInvSrlMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table TB_INV_SRL
	 * 
	 * @mbggenerated Mon Nov 17 10:07:31 CST 2014
	 */
	int countByExample(UTbInvSrlExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table TB_INV_SRL
	 * 
	 * @mbggenerated Mon Nov 17 10:07:31 CST 2014
	 */
	int deleteByExample(UTbInvSrlExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table TB_INV_SRL
	 * 
	 * @mbggenerated Mon Nov 17 10:07:31 CST 2014
	 */
	int deleteByPrimaryKey(Long srl_no);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table TB_INV_SRL
	 * 
	 * @mbggenerated Mon Nov 17 10:07:31 CST 2014
	 */
	int insert(TbInvSrlDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table TB_INV_SRL
	 * 
	 * @mbggenerated Mon Nov 17 10:07:31 CST 2014
	 */
	int insertSelective(TbInvSrlDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table TB_INV_SRL
	 * 
	 * @mbggenerated Mon Nov 17 10:07:31 CST 2014
	 */
	List<TbInvSrlDTO> selectByExample(UTbInvSrlExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table TB_INV_SRL
	 * 
	 * @mbggenerated Mon Nov 17 10:07:31 CST 2014
	 */
	TbInvSrlDTO selectByPrimaryKey(Long srl_no);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table TB_INV_SRL
	 * 
	 * @mbggenerated Mon Nov 17 10:07:31 CST 2014
	 */
	int updateByExampleSelective(@Param("record") TbInvSrlDTO record,
			@Param("example") UTbInvSrlExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table TB_INV_SRL
	 * 
	 * @mbggenerated Mon Nov 17 10:07:31 CST 2014
	 */
	int updateByExample(@Param("record") TbInvSrlDTO record,
			@Param("example") UTbInvSrlExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table TB_INV_SRL
	 * 
	 * @mbggenerated Mon Nov 17 10:07:31 CST 2014
	 */
	int updateByPrimaryKeySelective(TbInvSrlDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table TB_INV_SRL
	 * 
	 * @mbggenerated Mon Nov 17 10:07:31 CST 2014
	 */
	int updateByPrimaryKey(TbInvSrlDTO record);

	// 序號歷程查詢結果
	List<TbInvSrl> searchInvSrlJoinMaterial(HashMap<String, Object> dataObj);

	/**
	 * 取得標籤號碼 & 廠商序號
	 * 
	 * @param trNo
	 *            調撥單號
	 * @param matNo
	 *            料號
	 * @param dtlNo
	 *            調撥清單SeqId
	 * 
	 * @return TbInvSrl
	 */
	TbInvSrl getInvTrSrlTagNo(@Param("trNo") String trNo,
			@Param("matNo") String matNo, @Param("dtlNo") long dtlNo);
	
	/**
	 * 
	 * @return
	 */
	int updateTbInvSrl(TbInvSrl tbInvSrl);
}