package com.foya.noms.web.controller.st;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbSiteAntCfg;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteShareKey;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.dto.st.SiteLocationDTO;
import com.foya.noms.enums.AntInstallWay;
import com.foya.noms.enums.AntSignal;
import com.foya.noms.enums.FeederCableType;
import com.foya.noms.enums.Feederless;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.service.st.ST002SP1Service;
import com.foya.noms.service.st.ST004Service;
import com.foya.noms.service.st.ST011Service;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping("/st/st011")
public class ST011Controller extends BaseController {

	@Autowired
	private ST011Service st011Service;
	@Autowired
	private ST002SP1Service st002SP1Service;
	@Autowired
	private ST004Service st004Service;
	
	@RequestMapping("/init")
	public String initST011Page(Map<String, Object> model) {
		model.put("allSiteFeq", st002SP1Service.getSiteFeqs());
		model.put("allEqpTypes", st004Service.getEqpTypes());
		model.put("allFeederless", Feederless.getLabelValueList());
		model.put("allSiteStatus", SiteStatus.getLabelValueList());
		return "ajaxPage/st/ST011L";
	}
	
	
	/**
	 * 查詢基站
	 * @param btsSiteId
	 * @param siteName
	 * @param feqId
	 * @param eqpTypeId
	 * @param city
	 * @param area
	 * @param siteStatus
	 * @param feederless
	 * @return
	 */
	@RequestMapping("/getSiteMainList")
	@ResponseBody
	public JqGridData<SiteDTO> getSiteMainList(String btsSiteId,
			String siteName, String feqId, String eqpTypeId, String city,
			String area, String siteStatus, String feederless) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("btsSiteId", btsSiteId); // 基站編號
		map.put("siteName", siteName); //基站名稱 
		map.put("feqName", feqId); //基站頻段 
		map.put("eqpName", eqpTypeId); //設備型態
		map.put("siteCity", city); //行政區 - 縣市 
		map.put("siteTown", area); //行政區 -鄉鎮市區
		map.put("siteStatus", siteStatus); //基站狀態
		map.put("feederless", feederless); //Feederless
		map.put("eqpTypeNotIn", "eqpTypeNotIn");
		PageList<SiteDTO> siteList = st011Service.findSiteMainList(map);
		return new JqGridData<SiteDTO>(siteList.getPaginator().getTotalCount(), siteList);
	}
	
	/**
	 * 檢視
	 * @param siteId
	 * @param model
	 * @return
	 */
	@RequestMapping("/viewDetails")
	public String viewDetails(String siteId, Map<String,Object> model){
		
		SiteDTO SiteDTO =  st011Service.selectSiteBySiteId(siteId);
		SiteLocationDTO siteLocation =	st011Service.findSiteByPrimaryKey(SiteDTO.getLOCATION_ID());
		List<TbSiteAntCfg> tbSiteAntCfgList =	st011Service.findByCondition(siteId);
		
		model.put("siteDTO", SiteDTO); //站台資料
		model.put("SiteLocationDTO", siteLocation); //站點資訊
		model.put("tbSiteAntCfgList", tbSiteAntCfgList); //天線組態
		model.put("allAntSignal", AntSignal.getLabelValueList()); //訊號源
		model.put("allAntInstallWay", AntInstallWay.getLabelValueList()); //安裝方式
		model.put("allFeederCableType", FeederCableType.getLabelValueList()); //Feeder cable type
		model.put("antennaList", st011Service.findAntenna()); //天線型號
		
		if(SiteDTO.getLOCATION_ID() == null){
			return "ajaxPage/st/ST011D";
		}
		
		//共同業者
		for(TbSiteShareKey shareCom :st011Service.findSharComByLocationId(SiteDTO.getLOCATION_ID())){
			model.put(shareCom.getSHARE_COM().toUpperCase(),shareCom.getSHARE_COM());
		}
		
		List<TbSiteMain> tbSiteMainList = st011Service.selectSiteBylocationIdAndSiteStatusNotS09(SiteDTO.getLOCATION_ID());
		
		if(tbSiteMainList!=null && tbSiteMainList.size() >= 2){
			model.put("multiSite", "checked");
		}
		
		return "ajaxPage/st/ST011D";
	}
	
	
	
}
