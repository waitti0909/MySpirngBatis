package com.foya.dao.mybatis.mapper;

import java.util.List;
import java.util.Map;

import com.foya.dao.mybatis.model.TbSiteLineApply;
import com.foya.noms.dto.st.line.SiteLineApplyDTO;

public interface UTbSiteLineApplyMapper {

	/**
	 * 用DeptId查詢專線
	 * @param deptIdList
	 * @return
	 */
	List<SiteLineApplyDTO> selectlineAppleListByDeptId(List<String> deptIdList);
	
	
	/**
	 * 用appId查詢專線資料
	 * @return
	 */
	SiteLineApplyDTO selectLineApplyByAppId(String appId);
	
	/**
	 * 查詢全部siteWork
	 * @param map
	 * @return
	 */
	List<SiteLineApplyDTO> selectByConditions(Map<String ,String> map);
	
	/**
	 * 用appId查詢專線資料
	 * @return
	 */
	SiteLineApplyDTO selectLineApplyByExcel(String appId);
	
	List<SiteLineApplyDTO> selectLineApply(Map<String,String> map);
	
	SiteLineApplyDTO selectByExcelExport(String appId);
	
	SiteLineApplyDTO selectLineNumber(TbSiteLineApply tbSiteLineApply);
}
