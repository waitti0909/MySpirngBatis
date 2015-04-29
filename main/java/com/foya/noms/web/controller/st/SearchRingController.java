package com.foya.noms.web.controller.st;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteLocationExample;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteSearch;
import com.foya.dao.mybatis.model.TbSiteSearchExample;
import com.foya.dao.mybatis.model.TbSiteSearchRing;
import com.foya.dao.mybatis.model.TbSiteShareSearchExample;
import com.foya.dao.mybatis.model.TbSiteShareSearchKey;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkExample;
import com.foya.exception.CreateFailException;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dto.st.SearchRingDTO;
import com.foya.noms.dto.st.SiteSearchDTO;
import com.foya.noms.enums.InBuildingType;
import com.foya.noms.enums.RoomType;
import com.foya.noms.service.st.ST002SP1Service;
import com.foya.noms.service.st.SearchRingService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/st/sr")
public class SearchRingController extends BaseController {
	@Autowired
	private SearchRingService searchRingService;
	@Autowired
	private ST002SP1Service sT002SP1Service;

	/**
	 * ST002SP2初始頁面
	 * 
	 * @param request
	 * @param model
	 * @param site
	 * @return
	 */
	@RequestMapping(value = "/ST002SP2/init")
	public String initST002Page(HttpServletRequest request, Map<String, Object> model,
			SearchRingDTO searchRing) {
		String targetFrameId = "";
		if (request.getParameter("targetFrameId") != null
				&& request.getParameter("targetFrameId").length() > 0) {
			targetFrameId = request.getParameter("targetFrameId");
		}
		model.put("allSiteFeq", sT002SP1Service.getSiteFeqs());
		model.put("searchRing", searchRing);
		model.put("targetFrameId", targetFrameId);
		return "/ajaxPage/st/ST002SP2";
	}

	/**
	 * ST002SP2查詢
	 * 
	 * @param srId
	 * @param lon
	 * @param lat
	 * @param coverRange
	 * @return
	 */
	@RequestMapping(value = "/ST002SP2/search")
	@ResponseBody
	public JqGridData<SearchRingDTO> search(HttpServletRequest request,@RequestParam("srId") String srId,
			@RequestParam("lon") String lon, @RequestParam("lat") String lat,
			@RequestParam("coverRange") String coverRange, @RequestParam("city") String city,
			@RequestParam("area") String area, @RequestParam("feqId") String feqId) {
		Map<String, String> model = new HashMap<String, String>();
		if (StringUtils.trimToNull(lon) != null) {
			double lonValue = Double.valueOf(lon);
			model.put("lonBeing", String.valueOf(lonValue - 0.001));
			model.put("lonEnd", String.valueOf(lonValue + 0.001));
		}
		if (StringUtils.trimToNull(lat) != null) {
			double latValue = Double.valueOf(lat);
			model.put("latBeing", String.valueOf(latValue - 0.001));
			model.put("latEnd", String.valueOf(latValue + 0.001));
		}
		model.put("srId", srId);
		model.put("coverRange", coverRange);
		model.put("city", city);
		model.put("area", area);
		model.put("feqId", feqId);
		if("Y".equals(request.getParameter("isST002SP2Page")) && StringUtils.isNotEmpty(feqId)){
			model.put("addFeqCondition", "all");
		}
		List<SearchRingDTO> searchRingList = searchRingService.getSearchRingList(model);
		for (SearchRingDTO searchRing : searchRingList) {
			TbSiteMainExample example = new TbSiteMainExample();
			example.createCriteria().andSR_IDEqualTo(searchRing.getSR_ID())
					.andBTS_SITE_IDIsNotNull();
			List<TbSiteMain> siteMainList = sT002SP1Service.getWorkSiteListByExample(example);
			String btsSiteId = "";
			int index = 1;
			int listSize = siteMainList.size();
			for (TbSiteMain siteMain : siteMainList) {
				if (index < listSize) {
					btsSiteId += siteMain.getBTS_SITE_ID() + ",";
					index = index + 1;
				} else {
					btsSiteId += siteMain.getBTS_SITE_ID();
				}
				listSize -= 1;
			}
			searchRing.setBtsSiteId(btsSiteId);
			log.debug("SearchRingDTO getSR_LON :"+searchRing.getSR_LON());
		}
		
		PageList<SearchRingDTO> page = (PageList<SearchRingDTO>) searchRingList;
		return new JqGridData<>(page.getPaginator().getTotalCount(), searchRingList);
	}

	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchRing")
	public String initPage(HttpServletRequest request, Map<String, Object> model) {
		model.put("allSiteFeq", sT002SP1Service.getSiteFeqs());
		return "/ajaxPage/st/sr/SearchRing";
	}

	/**
	 * 新增頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchRing/insertPage")
	public String insertPage(HttpServletRequest request, Map<String, Object> model) {
		model.put("allSiteFeq", sT002SP1Service.getSiteFeqs());
		model.put("searchRingEvent", "insert");
		return "/ajaxPage/st/sr/SearchRingM";
	}

	/**
	 * 儲存
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchRing/save")
	@ResponseBody
	public Map<String, Object> save(TbSiteSearchRing searchRing,
			@RequestParam("searchRingEvent") String searchRingEvent) {
		Map<String, Object> map = new HashMap<String, Object>();
		if ("insert".equals(searchRingEvent)) {
			try {
				map.put("searchRing",
						searchRingService.insert(searchRing, getLoginUser().getUsername()));
				map.put("result", true);
			} catch (CreateFailException | NomsException ex) {
				map.put("result", ex.getMessage());
				log.error(ExceptionUtils.getFullStackTrace(ex));
			}
		} else {
			try {
				searchRingService.update(searchRing, getLoginUser().getUsername());
				map.put("result", true);
			} catch (UpdateFailException | NomsException ex) {
				map.put("result", ex.getMessage());
				log.error(ExceptionUtils.getFullStackTrace(ex));
			}
		}

		return map;
	}

	/**
	 * 修改頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchRing/updatePage")
	public String updarePage(HttpServletRequest request, Map<String, Object> model,
			@RequestParam("srId") String srId, @RequestParam("status") String status) {
		TbSiteSearchRing searchRing = searchRingService.getSearchRingByPrimaryKey(srId);
		TbSiteWorkExample workExample = new TbSiteWorkExample();
		workExample.createCriteria().andSR_IDEqualTo(srId);
		List<TbSiteWork> workList = searchRingService.getSiteWorkByConditions(workExample);
		TbSiteLocationExample locExample = new TbSiteLocationExample();
		locExample.createCriteria().andSR_IDEqualTo(srId);
		List<TbSiteLocation> locationList = searchRingService.getLocationByConditions(locExample);
		log.debug("workList : " + workList.size());
		model.put("searchRing", searchRing);
		model.put("allSiteFeq", sT002SP1Service.getSiteFeqs());
		model.put("searchRingEvent", "update");
		model.put("status", status);
		model.put("existSiteWork", workList.size());
		model.put("existLocation", locationList.size());
		return "/ajaxPage/st/sr/SearchRingM";
	}

	/**
	 * 查詢siteSearchTable
	 * 
	 * @param srId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchRing/search/siteSearchTable")
	@ResponseBody
	public BaseJasonObject<SiteSearchDTO> getSearchRecordTable(
			@RequestParam("srId") String srId, Map<String, Object> model) {
		List<SiteSearchDTO> siteSearchDTOList = new ArrayList<SiteSearchDTO>();
		if(srId != null){
			TbSiteSearchExample example = new TbSiteSearchExample();
			example.createCriteria().andSR_IDEqualTo(srId);
			List<TbSiteSearch> siteSearchList = searchRingService.getSiteSearchByExample(example);
			
			TbSiteMainExample siteMainExample = new TbSiteMainExample();
			siteMainExample.createCriteria().andSR_IDEqualTo(srId);
			List<TbSiteMain> siteMainList = searchRingService.getSiteMainByConditions(siteMainExample);
			
			for(TbSiteSearch siteSearch : siteSearchList){
				SiteSearchDTO searchDTO = new SiteSearchDTO();
				BeanUtils.copyProperties(siteSearch, searchDTO);
				for(TbSiteMain siteMain : siteMainList){
					if(siteMain.getSITE_ID().equals(siteSearch.getSITE_ID())){
						searchDTO.setBtsSiteId(siteMain.getBTS_SITE_ID());
						siteSearchDTOList.add(searchDTO);
						break;
					}
				}
			}
		}
		return new BaseJasonObject<>(siteSearchDTOList, AJAX_SUCCESS,
				AJAX_EMPTY);
	}

	/**
	 * 查詢siteSearch
	 * 
	 * @param srId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/searchRing/search/siteSearch")
	@ResponseBody
	public SiteSearchDTO getSearchRecord(@RequestParam("srId") String srId,
			@RequestParam("srSeq") String srSeq) {
		SiteSearchDTO siteSearch = searchRingService.getSiteSearchByPk(srId, srSeq);
		siteSearch.setBASE_TYPE(RoomType.detectLabel(siteSearch.getBASE_TYPE()));
		siteSearch.setINDOOR_OPTION(InBuildingType.detectLabel(siteSearch.getINDOOR_OPTION()));
		// 共構共站
		String siteShareSearchStr = "";

		TbSiteShareSearchExample siteShareSearchExample = new TbSiteShareSearchExample();
		siteShareSearchExample.createCriteria().andSR_IDEqualTo(srId).andSR_SEQEqualTo(srSeq);
		List<TbSiteShareSearchKey> siteShareSearchList = searchRingService
				.getSiteShareSearchByConditions(siteShareSearchExample);
		for (TbSiteShareSearchKey siteShareSearch : siteShareSearchList) {
			siteShareSearchStr += siteShareSearch.getSHARE_COM() + ",";
		}
		siteSearch.setShareCom(siteShareSearchStr);
		return siteSearch;
	}
	
	@RequestMapping(value = "/searchRing/doValidate")
	@ResponseBody
	public BaseJasonObject<String> doValidateSearchRing(TbSiteSearchRing searchRing){
		String message = searchRingService.doValidate(searchRing);
		return new BaseJasonObject<String>(message, AJAX_SUCCESS);
	}
}
