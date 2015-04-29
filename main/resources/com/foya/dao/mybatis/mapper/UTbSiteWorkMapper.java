package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.noms.dto.st.SiteWorkDTO;


public interface UTbSiteWorkMapper {
	
	/**
	 * 查詢全部siteWork
	 * @param map
	 * @return
	 */
	List<SiteWorkDTO> selectWorkList(Map<String ,String> map);
	
	/**
	 * 用DeptId查詢siteWork
	 * @param deptIdList
	 * @return
	 */
	List<SiteWorkDTO> selectWorkListByDeptId(List<String> deptIdList);
	
	/**
	 * 用workId查詢siteWork
	 * @return
	 */
	SiteWorkDTO selectSiteWorkByWorkId(String workId);
	
	/**
	 * 用orderId查詢siteWork
	 * @param orderId
	 * @return
	 */
	TbSiteWork selectSiteWorkByOrderId(String orderId);
	
	/**
	 * 用workId查詢osType
	 * @return
	 */
//	SiteWorkDTO selectTbSiteWorkByOrderId(Map<String, String> map);
	
	/**
	 * 用workId查詢siteWork
	 * @return
	 */
	SiteWorkDTO selectSiteWorkDTOByWorkId(String workId);
	
	/**
	 * 查詢建站siteWork
	 * @param map
	 * @return
	 */
	List<SiteWorkDTO> selectWorkListBySiteBuild(Map<String ,Object> map);
	
	/**
	 * 查詢單張工單siteWork
	 * @param map
	 * @return
	 */
	//List<SiteWorkDTO> selectWorkSgnList(Map<String ,Object> map);
	
	/**
	 * 單張工單作業申請
	 * 申請單位
	 * @return
	 */
	List<SiteWorkDTO>selectWorkAppDept();
	
}