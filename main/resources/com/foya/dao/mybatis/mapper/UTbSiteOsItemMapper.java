package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.foya.dao.mybatis.model.TbSiteOsItemKey;
import com.foya.dao.mybatis.model.UTbSiteOsItemExample;
import com.foya.noms.dto.st.TbSiteOsItemDTO;

public interface UTbSiteOsItemMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OS_ITEM
	 * @mbggenerated  Mon Dec 29 15:28:19 CST 2014
	 */
	int countByExample(UTbSiteOsItemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OS_ITEM
	 * @mbggenerated  Mon Dec 29 15:28:19 CST 2014
	 */
	int deleteByExample(UTbSiteOsItemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OS_ITEM
	 * @mbggenerated  Mon Dec 29 15:28:19 CST 2014
	 */
	int deleteByPrimaryKey(TbSiteOsItemKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OS_ITEM
	 * @mbggenerated  Mon Dec 29 15:28:19 CST 2014
	 */
	int insert(TbSiteOsItemDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OS_ITEM
	 * @mbggenerated  Mon Dec 29 15:28:19 CST 2014
	 */
	int insertSelective(TbSiteOsItemDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OS_ITEM
	 * @mbggenerated  Mon Dec 29 15:28:19 CST 2014
	 */
	List<TbSiteOsItemDTO> selectByExample(UTbSiteOsItemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OS_ITEM
	 * @mbggenerated  Mon Dec 29 15:28:19 CST 2014
	 */
	TbSiteOsItemDTO selectByPrimaryKey(TbSiteOsItemKey key);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OS_ITEM
	 * @mbggenerated  Mon Dec 29 15:28:19 CST 2014
	 */
	int updateByExampleSelective(@Param("record") TbSiteOsItemDTO record, @Param("example") UTbSiteOsItemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OS_ITEM
	 * @mbggenerated  Mon Dec 29 15:28:19 CST 2014
	 */
	int updateByExample(@Param("record") TbSiteOsItemDTO record, @Param("example") UTbSiteOsItemExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OS_ITEM
	 * @mbggenerated  Mon Dec 29 15:28:19 CST 2014
	 */
	int updateByPrimaryKeySelective(TbSiteOsItemDTO record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table TB_SITE_OS_ITEM
	 * @mbggenerated  Mon Dec 29 15:28:19 CST 2014
	 */
	int updateByPrimaryKey(TbSiteOsItemDTO record);

	/**
	 * 委外申請-儲存 
	 */
	int updateSiteOsItem(Map<String, String> map);
	
	/**
	 * 委外申請-儲存 
	 */
	int saveSiteOsItem(Map<String, String> map);
	
	int deleteSiteOsItem(Map<String, String> map);
}