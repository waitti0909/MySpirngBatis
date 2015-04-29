package com.foya.noms.dao.st.base;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.UTbSiteWorkOrderMapper;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.st.SiteWorkOrderDTO;

@Repository
public class BaseOrderProcessDao extends BaseDao {
	
	@Autowired
	private UTbSiteWorkOrderMapper uTbSiteWorkOrderMapper;	

	/**
	 * 用workId和isActive查詢SiteWorkOrder
	 * @param workId
	 * @param isActive
	 * @return
	 */
	public List<SiteWorkOrderDTO> findSiteWorkOrderByWorkIdAndIsActive(String workId,String isActive){
		return uTbSiteWorkOrderMapper.selectSiteWorkOrderByWorkIdAndIsActive(workId, isActive);
	}
}
