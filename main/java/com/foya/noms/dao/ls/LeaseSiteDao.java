package com.foya.noms.dao.ls;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteMainMapper;
import com.foya.dao.mybatis.mapper.UTbLsAlocDtlMapper;
import com.foya.dao.mybatis.mapper.UTbSiteMainMapper;
import com.foya.dao.mybatis.model.TbLsAlocDtl;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.ls.SiteAlocDetailDTO;
import com.foya.noms.dto.st.SiteDTO;

@Repository
public class LeaseSiteDao extends BaseDao {

	
	@Autowired
	private UTbSiteMainMapper uTbSiteMainMapper;
	
	@Autowired
	private UTbLsAlocDtlMapper uTbLsAlocDtlMapper;
	

	
	/**TB_Site_MAIN Example
	 * Location下的站台查詢使用站點
	 * @param locationId
	 * @return
	 */
	public List<SiteDTO> getSiteMainByLocationId(String locationId,String[] siteStatus) {
		return uTbSiteMainMapper.selectSiteMainByLocIdSiteStatus(locationId,siteStatus);
//		return uTbSiteMainMapper.selectByExample(example);
	}
	/**TB_Site_MAIN Example
	 * Location下的站台查詢使用站點
	 * @param locationId
	 * @return
	 */
	public List<SiteDTO> getSiteMainBySiteIdList(String[] siteIds) {
		
		return uTbSiteMainMapper.selectSiteMainBySiteIdList(siteIds);
	}
	
	/**TB_LS_Site Example
	 * 租約下的站台查詢使用站點
	 * @param locationId
	 * @param lsNo
	 * @param ver
	 * @return
	 */
	public List<SiteAlocDetailDTO> getLeaseSiteAlocByLocationId(String locType,String[] feqType,Integer siteCount,String[] alocItem,String carriers,String[] feqName){
		Map<String, Object> cond = new HashMap<>();
		cond.put("CARRIERS_ID", carriers);
		cond.put("FEQ_TYPE", feqType);
		cond.put("CHECKDATE", new Date());
		cond.put("LOC_TYPE", locType);
		cond.put("SITE_NBR", siteCount);
		cond.put("ALOC_ITEM", alocItem);
		cond.put("FEQ_NAME", feqName);
		
		return uTbLsAlocDtlMapper.selectSiteAloc4New(cond);
	}
	
	/**
	 * 取得已存在資源互換中站台分攤比明細
	 * @param locationId
	 * @param lsNo
	 * @param ver
	 * @return
	 */
	public List<SiteAlocDetailDTO> findExchSiteAlocExitByCond(String lsNo, String lsVer, String mainLocId, String exchLocId){
		Map<String, String> cond = new HashMap<String, String>();
		cond.put("lsNo", lsNo);
		cond.put("lsVer", lsVer);
		cond.put("exchLocId", exchLocId);
		cond.put("mainLocId", mainLocId);
		
		return uTbLsAlocDtlMapper.findExchSiteAlocExitByCond(cond);
	}
	/**
	 * 取得已存在資源互換中站台分攤比明細
	 * @param locationId
	 * @param lsNo
	 * @param ver
	 * @return
	 */
	public List<SiteAlocDetailDTO> findExchSiteAlocAddedExitByCond(String appSeq, String mainLocId, String exchLocId){
		Map<String, String> cond = new HashMap<String, String>();
		cond.put("appSeq", appSeq);
		cond.put("exchLocId", exchLocId);
		cond.put("mainLocId", mainLocId);
		
		return uTbLsAlocDtlMapper.findExchSiteAlocAddedExitByCond(cond);
	}
	
}