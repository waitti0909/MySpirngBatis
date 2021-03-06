package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.dao.mybatis.model.TbSiteOutsourcingExample;
import com.foya.dao.mybatis.model.UTbSiteOutsourcingExample;
import com.foya.noms.dto.st.TbSiteOutsourcingDTO;

public interface UTbSiteOutsourcingMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OUTSOURCING
	 * @mbggenerated  Mon Feb 02 09:53:03 CST 2015
	 */
	int countByExample(UTbSiteOutsourcingExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OUTSOURCING
	 * @mbggenerated  Mon Feb 02 09:53:03 CST 2015
	 */
	int deleteByExample(UTbSiteOutsourcingExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OUTSOURCING
	 * @mbggenerated  Mon Feb 02 09:53:03 CST 2015
	 */
	int deleteByPrimaryKey(String OS_ID);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OUTSOURCING
	 * @mbggenerated  Mon Feb 02 09:53:03 CST 2015
	 */
	int insert(TbSiteOutsourcing record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OUTSOURCING
	 * @mbggenerated  Mon Feb 02 09:53:03 CST 2015
	 */
	int insertSelective(TbSiteOutsourcing record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OUTSOURCING
	 * @mbggenerated  Mon Feb 02 09:53:03 CST 2015
	 */
	List<TbSiteOutsourcingDTO> selectByExample(UTbSiteOutsourcingExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OUTSOURCING
	 * @mbggenerated  Mon Feb 02 09:53:03 CST 2015
	 */
	TbSiteOutsourcingDTO selectByPrimaryKey(String OS_ID);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OUTSOURCING
	 * @mbggenerated  Mon Feb 02 09:53:03 CST 2015
	 */
	int updateByExampleSelective(@Param("record") TbSiteOutsourcingDTO record,
			@Param("example") UTbSiteOutsourcingExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OUTSOURCING
	 * @mbggenerated  Mon Feb 02 09:53:03 CST 2015
	 */
	int updateByExample(@Param("record") TbSiteOutsourcingDTO record,
			@Param("example") UTbSiteOutsourcingExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OUTSOURCING
	 * @mbggenerated  Mon Feb 02 09:53:03 CST 2015
	 */
	int updateByPrimaryKeySelective(TbSiteOutsourcingDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OUTSOURCING
	 * @mbggenerated  Mon Feb 02 09:53:03 CST 2015
	 */
	int updateByPrimaryKey(TbSiteOutsourcingDTO record);

	/**
	 * 委外申請-儲存
	 */
	int updateOutSourcing(Map<String, String> map);

	/**
	 * 委外申請-儲存
	 */
	int saveOutSourcing(Map<String, String> map);

	/**
	 * 計算分區配額
	 * @param map
	 * @return
	 * @author Charlie Woo
	 */
	Long sumPayAmountbyOutcourcing(Map<String, String> map);

	/**
	 * 計算PO單總額
	 * @param map
	 * @return
	 * @author Charlie Woo
	 */
	Long sumPayAmountNoAreabyOutcourcing(Map<String, String> map);

	/**
	 * 查詢委外相關金額加總
	 * @param example
	 * @return
	 */
	TbSiteOutsourcingDTO selectSumAmountByExample(UTbSiteOutsourcingExample example);

	List<TbSiteOutsourcingDTO> selectByExample(TbSiteOutsourcingExample example);
}