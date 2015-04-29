package com.foya.noms.web.controller.st;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.service.common.AddressService;
import com.foya.noms.service.st.ST002SP1Service;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
public class ST002SP1Controller extends BaseController {
	@Autowired
	private ST002SP1Service sT002SP1Service;
	@Autowired
	private AddressService addressService;
	/**
	 * 初始頁面
	 * @param request
	 * @param model
	 * @param site
	 * @return
	 */
	@RequestMapping(value = "/common/st002SP1/init")
	public String initST002Page(HttpServletRequest request,
			Map<String, Object> model,SiteDTO site) {
		model.put("allEqpTypes", sT002SP1Service.getEqpTypes());
		model.put("allSiteFeqs", sT002SP1Service.getSiteFeqs());
		model.put("site", site);
		//add if pass param domain to filter
		if(request.getParameter("domainId")!=null){
			model.put("filterDomainId", request.getParameter("domainId"));
		}
		String mode = "single";
		if(request.getParameter("selectMode")!=null && request.getParameter("selectMode").length()>0){
			mode = request.getParameter("selectMode");
		}
		
		String targetFrameId = "";
		if(request.getParameter("targetFrameId")!=null && request.getParameter("targetFrameId").length()>0){
			targetFrameId = request.getParameter("targetFrameId");
		}
		
		
		String locationId = "";
		if(request.getParameter("locationId")!=null && request.getParameter("locationId").length()>0){
			locationId = request.getParameter("locationId");
		}
		
		
		model.put("siteStatusList", SiteStatus.getLabelValueList());
		model.put("selectMode", mode);
		model.put("targetFrameId", targetFrameId);
		model.put("site", site);
		model.put("isClosed", request.getParameter("isClosed"));
		log.debug("siteType"+request.getParameter("siteType"));
		model.put("siteType", request.getParameter("siteType"));
		model.put("locationId", locationId);
		return "/ajaxPage/st/ST002SP1";
	}
	
	/**
	 * 查詢
	 * @param btsSiteId
	 * @param siteName
	 * @param feqName
	 * @param eqpName
	 * @param siteCity
	 * @param siteTown
	 * @return
	 */
	@RequestMapping(value = "/common/st002SP1/search")
	@ResponseBody
	public JqGridData<SiteDTO> search(HttpServletRequest request ,@RequestParam("btsSiteId") String btsSiteId,
			@RequestParam("siteName") String siteName,
			@RequestParam("feqName") String feqName,
			@RequestParam("eqpName") String eqpName,
			@RequestParam("siteCity") String siteCity,
			@RequestParam("siteTown") String siteTown,
			@RequestParam("siteStatus") String siteStatus,
			@RequestParam("siteType") String siteType) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("btsSiteId", btsSiteId);
		model.put("siteName", siteName);
		model.put("feqName", feqName);
		model.put("eqpName", eqpName);
		model.put("siteCity", siteCity);
		model.put("siteTown", siteTown);
		model.put("siteStatus", siteStatus);
		model.put("siteType", siteType);
		String locationId = request.getParameter("locationId");
		if(StringUtils.isNotEmpty(locationId)){
			model.put("locationId", locationId);
			
		}
		
		
//		model.put("sort", request.getParameter("sort"));
//		model.put("order", request.getParameter("order"));
		List<SiteDTO> siteList = sT002SP1Service.getWorkSiteList(model);
		PageList<SiteDTO> page = (PageList<SiteDTO>) siteList;
		return new JqGridData<>(page.getPaginator().getTotalCount(), siteList);
	}
}
