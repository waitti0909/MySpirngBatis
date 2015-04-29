package com.foya.noms.service.st;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkExample;
import com.foya.noms.dao.common.FileDtlDao;
import com.foya.noms.dao.st.WorkDao;
import com.foya.noms.dto.common.FileDtlDTO;
import com.foya.noms.enums.WorkType;
import com.foya.noms.service.BaseService;

@Service
public class SiteFileService extends BaseService {

	@Autowired
	private WorkDao workDao;
	
	@Autowired
	private FileDtlDao fileDtlDao;
	
	/**
	 * 取得該站台下所有作業MAP<作業單號, 作業類型>
	 * @param siteId
	 * @return
	 * @author Charlie Woo
	 */
	public Map<String, String> getWorksForSite(String siteId) {
		TbSiteWorkExample example = new TbSiteWorkExample();
		example.createCriteria().andSITE_IDEqualTo(siteId);
		example.setOrderByClause("CR_TIME asc");
		List<TbSiteWork> works = workDao.findByConditions(example);
		
		log.debug("取得該站台下作業筆數：" + works.size());
		Map<String, String> workMap = new LinkedHashMap<>();
		if (!works.isEmpty()) {			
			for (TbSiteWork work : works) {				
				workMap.put(work.getWORK_ID(), WorkType.detectLabel(work.getWORK_TYPE()) + "("+work.getWORK_ID()+")");
			}
		} 	
		return workMap;
	}
	
	/**
	 * 取得站台已上傳檔案明細
	 * @param typePath
	 * @param fileDoc
	 * @param fileType
	 * @param sort
	 * @param order
	 * @return
	 * @author Charlie Woo
	 */
	public List<FileDtlDTO> getSiteFileList(List<String> fileDoc, String sort, String order) {
		HashMap<String, String> condition = new HashMap<String, String>();
		condition.put("sort", sort);
		condition.put("order", order);
		return fileDtlDao.findByDocNo(condition, fileDoc);
	}
}
