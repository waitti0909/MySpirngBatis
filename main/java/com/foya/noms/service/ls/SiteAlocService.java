package com.foya.noms.service.ls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.exception.DataNotFoundException;
import com.foya.noms.dao.ls.LS001Dao;
import com.foya.noms.dao.ls.LeaseSiteDao;
import com.foya.noms.dto.ls.LeaseMainDTO;
import com.foya.noms.dto.ls.SiteAlocDetailDTO;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.enums.LsEnumCommon;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.service.BaseService;

@Service
public class SiteAlocService extends BaseService {

	public static final String LOCTYPE_NORMAL="R";
	public static final String LOCTYPE_EXCHANGE="E";
	public static final String ALOCITEM_RENT="R";
	public static final String ALOCITEM_ELEC="E";
	public static final String ALOCITEM_RENT_ELEC="R,E";
	public static final String FEQTYPE_NORMAL="R";
	public static final String FEQTYPE_IN="IN";
	public static final String FEQTYPE_OUT="OUT";
	
	
	@Autowired
	private  LeaseSiteDao leaseSiteDao;
	@Autowired
	private  LS001Dao lS001Dao;
	
	
	/**TB_Site_MAIN Example
	 * Location下的站台查詢使用站點
	 * @param locationId
	 * @return
	 */
	public List<SiteDTO> getSiteMainByLocationId(String locationId,String... siteStatus) {
		
		return leaseSiteDao.getSiteMainByLocationId(locationId, siteStatus);
	}
	
	/**TB_Site_MAIN Example
	 * Location下的站台查詢使用站點
	 * @param locationId
	 * @return
	 */
	public List<SiteDTO> getSiteMainBySiteId(String[] siteId) {
		
		return leaseSiteDao.getSiteMainBySiteIdList(siteId);
	}
	
	
	/**
	 * 取得一般站點的基站分攤比
	 * @param locationId
	 * @return
	 */
	public List<SiteAlocDetailDTO> getNormalSiteAlocByLocationId(String locationId,String alocItem) throws DataNotFoundException{
		List<SiteDTO> siteMainList = getSiteMainByLocationId(locationId, SiteStatus.getAvailableSiteStatus());
		int siteCount= siteMainList.size();
		String[] feqName = new String[siteCount];
		
		for (int i = 0; i < feqName.length; i++) {
			feqName[i]=siteMainList.get(i).getFeqName();
		}
		
		List<SiteAlocDetailDTO> result =leaseSiteDao.getLeaseSiteAlocByLocationId(LOCTYPE_NORMAL, new String[]{FEQTYPE_NORMAL}, siteCount, new String[]{alocItem}, null, feqName);
		if(result==null || result.size()==0){
			throw new DataNotFoundException("該站台分攤比尚未設定或找不到");
		}
		
		for (SiteAlocDetailDTO item: result) {
			for (SiteDTO s:siteMainList) {
				if(s.getFeqName().equalsIgnoreCase(item.getFEQ_NAME())){
					item.setSiteName(s.getSITE_NAME());
					item.setSiteId(s.getSITE_ID());
					item.setBtsSiteId(s.getBTS_SITE_ID());
					
					break;
				}
			}
		}
		
		return result;
	}
	
	
	/**
	 * 取得一般站點的基站分攤比
	 * @param locationId
	 * @return
	 */
	public List<SiteAlocDetailDTO> getNormalSiteAlocBySiteIds(String[] siteIds,String alocItem) throws DataNotFoundException{
		List<SiteDTO> siteMainList =getSiteMainBySiteId(siteIds);
		
		int siteCount= siteIds.length;
		String[] feqName = new String[siteCount];
		
		for (int i = 0; i < feqName.length; i++) {
			feqName[i]=siteMainList.get(i).getFeqName();
		}
		
		List<SiteAlocDetailDTO> result =leaseSiteDao.getLeaseSiteAlocByLocationId(LOCTYPE_NORMAL, new String[]{FEQTYPE_NORMAL}, siteCount, new String[]{alocItem}, null, feqName);
		if(result==null || result.size()==0){
			throw new DataNotFoundException("該站台分攤比尚未設定或找不到");
		}
		
		for (SiteAlocDetailDTO item: result) {
			for (SiteDTO s:siteMainList) {
				if(s.getFeqName().equalsIgnoreCase(item.getFEQ_NAME())){
					item.setSiteName(s.getSITE_NAME());
					item.setSiteId(s.getSITE_ID());
					item.setBtsSiteId(s.getBTS_SITE_ID());
					
					break;
				}
			}
		}
		
		return result;
	}
	
	
	
	/**
	 * 取得資源互換站點的基站分攤比
	 * @param lookupType
	 * @return
	 */
	public List<SiteAlocDetailDTO> getExchangeSiteAlocByLocationId(String exchLocId, String mainLocId, String lsNo, String lsVer,String appSeq,String lsType) {
		//借出站台
		List<SiteDTO> outSiteList = getSiteMainByLocationId(mainLocId, SiteStatus.getAvailableSiteStatus());
		int outSiteCount = outSiteList.size();
		String[] outFeqNames = new String[outSiteCount];
		for (int i = 0; i < outFeqNames.length; i++) {
			outFeqNames[i] = outSiteList.get(i).getFeqName();
		}
		//借出站台
		List<SiteDTO> inSiteList = getSiteMainByLocationId(exchLocId, SiteStatus.getAvailableSiteStatus());
		int inSiteCount = inSiteList.size();
		String[] inFeqNames = new String[inSiteCount];
		for (int i = 0; i < inFeqNames.length; i++) {
			inFeqNames[i] = inSiteList.get(i).getFeqName();
		}
		
		int totalSiteNum = outSiteCount + inSiteCount;
		LeaseMainDTO lease =null;
		if(!LsEnumCommon.LsTypeEnum.ContinueLease.getLsType().equals(lsType)){
			 lease = lS001Dao.getLsMainByNo(lsNo, lsVer);
		}else if(StringUtils.isNotEmpty(appSeq)){
			 lease = lS001Dao.getLsMainAddedByAppSeq(appSeq);
		}
	
		String[] alocItem = null;
		if (lease.getEXCH_ITEM().indexOf("EX4") > -1) {
			String[] exchItemArry = StringUtils.split(lease.getEXCH_ITEM(), ",");
			alocItem = exchItemArry.length > 1 ? new String[]{SiteAlocService.ALOCITEM_RENT, SiteAlocService.ALOCITEM_ELEC}
											   : new String[]{SiteAlocService.ALOCITEM_ELEC};
		} else {
			alocItem = new String[]{SiteAlocService.ALOCITEM_RENT};
		}
		
		List<SiteAlocDetailDTO> result = leaseSiteDao.getLeaseSiteAlocByLocationId(LOCTYPE_EXCHANGE, null, totalSiteNum, alocItem, lease.getEXCH_CARRIERS(), null);
		if(result == null || result.size()==0){
			throw new DataNotFoundException("該站台資源互換分攤比尚未設定或找不到");
		}
		//將result依照seqId分類
		Map<String, List<SiteAlocDetailDTO>> groupedSiteAlocDetails = new HashMap<String, List<SiteAlocDetailDTO>>();
		for (SiteAlocDetailDTO dto: result) {
		    String key = dto.getSEQ_ID().toString();
		    if (groupedSiteAlocDetails.get(key) == null) {
		    	groupedSiteAlocDetails.put(key, new ArrayList<SiteAlocDetailDTO>());
		    }
		    groupedSiteAlocDetails.get(key).add(dto);
		}
		
		Set<String> groupedSiteAlocDetailKeySet = groupedSiteAlocDetails.keySet();
		List<String> notValidSeqIdList = new ArrayList<String>();
		for (String seqId: groupedSiteAlocDetailKeySet) {
			//分組檢查IN / OUT 是否相符
		   List<SiteAlocDetailDTO> groupedSiteAlocDetailList = groupedSiteAlocDetails.get(seqId);
		   //檢查借入
		   int validInCnt = inSiteCount;	
			for (int i = 0; i < inFeqNames.length; i++) {
				for (SiteAlocDetailDTO dto : groupedSiteAlocDetailList) {
					// 只選擇一種來比對就好
					if (!alocItem[0].equals(dto.getAlocItem())) {
						continue;
					}
					//FEQ_NAME相同且FEQ_TYPE = IN 
					if (inFeqNames[i].equals(dto.getFEQ_NAME())
							&& dto.getFEQ_TYPE().equals(FEQTYPE_IN)) {
						validInCnt -= 1;
					}
					if (validInCnt == 0) {
						break;
					}
				}
			}
		   if (validInCnt != 0) {
			   notValidSeqIdList.add(seqId);
			   continue;
		   }
		   
		   //檢查借出
		   validInCnt = outSiteCount;
			for (int i = 0; i < outFeqNames.length; i++) {
				for (SiteAlocDetailDTO dto : groupedSiteAlocDetailList) {
					if (!alocItem[0].equals(dto.getAlocItem())) {
						continue;
					}
					//FEQ_NAME相同且FEQ_TYPE = OUT
					if (outFeqNames[i].equals(dto.getFEQ_NAME())
							&& dto.getFEQ_TYPE().equals(FEQTYPE_OUT)) {
						validInCnt -= 1;
					}
					if (validInCnt == 0) {
						break;
					}
				}
			}
		   
		   if (validInCnt != 0) {
			   notValidSeqIdList.add(seqId);
			   continue;
		   }
		}
		
		for (String notValidSeqId : notValidSeqIdList){
			groupedSiteAlocDetails.remove(notValidSeqId);
		}
		
		List<SiteAlocDetailDTO> validResult = new ArrayList<SiteAlocDetailDTO>();
		groupedSiteAlocDetailKeySet = groupedSiteAlocDetails.keySet();
		for (String seqId: groupedSiteAlocDetailKeySet) {
		   List<SiteAlocDetailDTO> groupedSiteAlocDetailList = groupedSiteAlocDetails.get(seqId);
		   for (SiteAlocDetailDTO dto : groupedSiteAlocDetailList) {
		        if (dto.getFEQ_TYPE().equals(FEQTYPE_OUT)) {
		        	for (SiteDTO s : outSiteList) {
						if(s.getFeqName().equalsIgnoreCase(dto.getFEQ_NAME())){
							dto.setLocationId(s.getLOCATION_ID());
							dto.setSiteName(s.getSITE_NAME());
							dto.setSiteId(s.getSITE_ID());
							dto.setBtsSiteId(s.getBTS_SITE_ID());
							break;
						}
					}
		        } else if (dto.getFEQ_TYPE().equals(FEQTYPE_IN)) {
		        	 for (SiteDTO s : inSiteList) {
							if(s.getFeqName().equalsIgnoreCase(dto.getFEQ_NAME())){
								dto.setLocationId(s.getLOCATION_ID());
								dto.setSiteName(s.getSITE_NAME());
								dto.setSiteId(s.getSITE_ID());
								dto.setBtsSiteId(s.getBTS_SITE_ID());
								break;
							}
						}
		        }
		       
		        validResult.add(dto);
		   }
		}
		return validResult;
	}
	
	/**
	 * 取得已存在資源互換中站台分攤比明細
	 * @param lookupType
	 * @return
	 */
	public List<SiteAlocDetailDTO> findExchSiteAlocExitByCond(String lsNo, String lsVer, String mainLocId, String exchLocId,String lsType,String appSeq) {
		List<SiteAlocDetailDTO> list = null;
		
		if(!StringUtils.equals(lsType, LsEnumCommon.LsTypeEnum.ContinueLease.getLsType())){
			list = leaseSiteDao.findExchSiteAlocExitByCond(lsNo, lsVer, mainLocId, exchLocId);
		}else{
			list = leaseSiteDao.findExchSiteAlocAddedExitByCond(appSeq, mainLocId, exchLocId);
		}
		
		
		if (list == null || list.size() == 0) {
			return null;
		}
		List<SiteDTO> outSiteList = getSiteMainByLocationId(mainLocId, SiteStatus.getAvailableSiteStatus());
		//借出站台
		List<SiteDTO> inSiteList = getSiteMainByLocationId(exchLocId, SiteStatus.getAvailableSiteStatus());
		for (SiteAlocDetailDTO dto : list) {
	        if (dto.getFEQ_TYPE().equals(FEQTYPE_OUT)) {
	        	for (SiteDTO s : outSiteList) {
					if(s.getFeqName().equalsIgnoreCase(dto.getFEQ_NAME())){
						dto.setLocationId(s.getLOCATION_ID());
						dto.setSiteName(s.getSITE_NAME());
						dto.setSiteId(s.getSITE_ID());
						dto.setBtsSiteId(s.getBTS_SITE_ID());
						break;
					}
				}
	        } else if (dto.getFEQ_TYPE().equals(FEQTYPE_IN)) {
	        	 for (SiteDTO s : inSiteList) {
						if(s.getFeqName().equalsIgnoreCase(dto.getFEQ_NAME())){
							dto.setLocationId(s.getLOCATION_ID());
							dto.setSiteName(s.getSITE_NAME());
							dto.setSiteId(s.getSITE_ID());
							dto.setBtsSiteId(s.getBTS_SITE_ID());
							break;
						}
					}
	        }
	       
	   }
		
		return list;
	}
}
