package com.foya.noms.web.controller.st;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteWorkAntCfgTemp;
import com.foya.dao.mybatis.model.TbSiteWorkAntCfgTempExample;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTemp;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTempExample;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.service.st.SiteLinesService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.web.controller.BaseController;

@Controller
@RequestMapping(value ="/st/siteLine")
public class SiteLinesController extends BaseController {
	@Autowired
	private SiteLinesService siteLinesService;
	
	/**
	 * 儲存機房資訊到siteTemp
	 * @param siteTemp
	 */
	@RequestMapping(value = "/tabs/saveEngineRoom")
	@ResponseBody
	public Map<String ,Object> saveSite(TbSiteWorkSiteTemp siteTemp) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		try{
			siteLinesService.saveSite(siteTemp);
			map.put("result", true);
		}catch(UpdateFailException e){
			map.put("result", this.getMessageDetail(e.getErrCode()));
			ExceptionUtils.getFullStackTrace(e);
		}
		return map;
	}
	
	/**
	 * 查詢基站查詢
	 * 
	 * @param workId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/siteTemp")
	@ResponseBody
	public BaseJasonObject<TbSiteWorkSiteTemp> getOutSourceTable(@RequestParam("workId") String workId, Map<String, Object> model) {
		TbSiteWorkSiteTempExample example = new TbSiteWorkSiteTempExample();	
		example.createCriteria().andWORK_IDEqualTo(workId);
		List<TbSiteWorkSiteTemp> siteTempList = siteLinesService.getSiteTempByExample(example);
		
		return new BaseJasonObject<>(siteTempList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 查詢基站查詢
	 * 
	 * @param workId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/siteMain")
	@ResponseBody
	public BaseJasonObject<TbSiteMain> getMainTable(@RequestParam("btsSiteId") String btsSiteId, Map<String, Object> model) {
		TbSiteMainExample example = new TbSiteMainExample();	
		example.createCriteria().andBTS_SITE_IDEqualTo(btsSiteId);
		List<TbSiteMain> siteMainList = siteLinesService.getSiteMainByExample(example);
		
		return new BaseJasonObject<>(siteMainList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 *  查詢 出 天線態資料
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/siteWorkAntCfgTemp")
	@ResponseBody
	public BaseJasonObject<TbSiteWorkAntCfgTemp> getSiteSrAntCfgTemp(@RequestParam("workId") String workId, @RequestParam("siteId") String siteId) {
		TbSiteWorkAntCfgTempExample example = new TbSiteWorkAntCfgTempExample();
		example.createCriteria().andWORK_IDEqualTo(workId).andSITE_IDEqualTo(siteId);
		List<TbSiteWorkAntCfgTemp> workAntCfgList = siteLinesService.getSiteWorkAntCfgTempByExample(example);
		return new BaseJasonObject<>(workAntCfgList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	
	/**
	 * 基站資訊儲存
	 */
	@RequestMapping(value = "saveWorkSiteTemp")
	@ResponseBody
	public boolean saveWorkSiteTemp(
			Map<String, Object> map,TbSiteWorkSiteTemp siteTemp,
			// @RequestBody SiteSearchDTO siteSearch, HttpServletRequest
			// request,
//			@RequestParam("workId") String workId, 
//			@RequestParam("siteId") String siteId,
//			@RequestParam("siteTempName") String siteTempName, 
//			@RequestParam("siteHuntEqpTemp") String siteHuntEqpTemp,
//			@RequestParam("siteHuntEqpModelTemp") String siteHuntEqpModelTemp, 
//			@RequestParam("feederlessTeam") String feederlessTeam,
//			@RequestParam("masterSiteTemp") String masterSiteTemp, 
//			@RequestParam("configuration") String configuration,
//			@RequestParam("speedUpload") String speedUpload, 
//			@RequestParam("speedDownload") String speedDownload,
//			@RequestParam("donorBts") String donorBts, 
//			@RequestParam("includeRange") String includeRange,
//			@RequestParam("coverageInFloor") String coverageInFloor, 
//			@RequestParam("power") String power,
//			@RequestParam("cluster") String cluster,
//			@RequestParam("rncBts") String rncBts, 
//			
//			@RequestParam("coverageIndoor") String coverageIndoor,
//			@RequestParam("hidden") String hidden,
//			@RequestParam("noisOnAir") String noisOnAir,
//			@RequestParam("l2Switch") String l2Switch,
//			@RequestParam("selectOmcuObject") String selectOmcuObject,
//			@RequestParam("siteLon") BigDecimal siteLon,
//			@RequestParam("siteLat") BigDecimal siteLat,
//			@RequestParam("repeater") String repeater,
			@RequestParam("exportList") String exportJson
			) {
		try {
			log.debug("=====SearchRecordSave====");
			log.debug("siteTemp : "+siteTemp.toString());
			// ========================================TB_SITE_SR_SITE_TEMP
//			TbSiteWorkSiteTemp record = new TbSiteWorkSiteTemp();
//			record.setWORK_ID(workId);
//			record.setSITE_ID(siteId);
//			record.setSITE_NAME(siteTempName);
//			record.setEQP_TYPE_ID(siteHuntEqpTemp);
//			record.setEQP_MODEL_ID(siteHuntEqpModelTemp);
//			record.setFEEDERLESS(feederlessTeam);
//			record.setMASTER_SITE(masterSiteTemp);
//			record.setBTS_CONFIG(configuration);
//			record.setSPEED_UPLOAD(speedUpload);
//			record.setSPEED_DOWNLOAD(speedDownload);
//			record.setDONOR_SITE(donorBts);
//			record.setCOVERAGE_TYPE(includeRange);
//			record.setPOWER(power);
//			record.setCLUSTER(cluster);
//			record.setRNC_BTS(rncBts);
//			record.setCOVERAGE_IN_FLOOR(coverageInFloor);
//			record.setCOVERAGE_INDOOR(coverageIndoor);
//			record.setHIDDEN(hidden);
//			record.setNOIS_ON_AIR(noisOnAir);
//			record.setL2_SWITCH(l2Switch);	
//			record.setOMCU_OBJ(selectOmcuObject);
//			record.setLOA(siteLon);
//			record.setLAT(siteLat);
//			record.setNIOS_RPT_STATUS(repeater);
			// 利用 srId & srSeq 更新該筆資料
			TbSiteWorkSiteTempExample example = new TbSiteWorkSiteTempExample();
			example.createCriteria().andWORK_IDEqualTo(siteTemp.getWORK_ID()).andSITE_IDEqualTo(siteTemp.getSITE_ID());
//			// ========================================TB_SITE_SR_SITE_TEMP End
//
//			// ========================================TB_SITE_SR_ANT_CFG_TEMP
			List<TbSiteWorkAntCfgTemp> tempList = new ArrayList<TbSiteWorkAntCfgTemp>();

			JSONArray jsonArray = new JSONArray(exportJson);

			for (int i = 0; i < jsonArray.length(); i++) {
				TbSiteWorkAntCfgTemp temp = new TbSiteWorkAntCfgTemp();
				JSONObject obj = jsonArray.getJSONObject(i);
				temp.setWORK_ID(siteTemp.getWORK_ID());
				temp.setSITE_ID(siteTemp.getSITE_ID());
				temp.setANT_NO(String.valueOf(obj.get("siteLine2")));//天線編號
				temp.setCELL_ID(String.valueOf(obj.get("siteLine2")));//cell
				temp.setSEG_SOURCE(String.valueOf(obj.get("siteLine3")));//訊號源
				temp.setP_CODE(String.valueOf(obj.get("siteLine4")));//p-code
				temp.setANT_MODEL(String.valueOf(obj.get("siteLine5")));//天線型號
				if(!obj.get("siteLine6").equals(null) && !obj.get("siteLine6").equals("")) {
					temp.setANT_HIGH(BigDecimal.valueOf(Double.parseDouble(String.valueOf(obj.get("siteLine6")))));//安裝高度
				}
				if(!obj.get("siteLine7").equals(null) && !obj.get("siteLine7").equals("")) {
					temp.setANT_ORIENT(BigDecimal.valueOf(Double.parseDouble(String.valueOf(obj.get("siteLine7")))));//方向角
				}
				if(!obj.get("siteLine8").equals(null) && !obj.get("siteLine8").equals("")) {
					temp.setE_TILT(BigDecimal.valueOf(Double.parseDouble(String.valueOf(obj.get("siteLine8")))));//E_TILT
				}
				if(!obj.get("siteLine9").equals(null) && !obj.get("siteLine9").equals("")) {
					temp.setM_TILT(BigDecimal.valueOf(Double.parseDouble(String.valueOf(obj.get("siteLine9")))));//MTILT
				}
				temp.setSETUP_STYLE(String.valueOf(obj.get("siteLine10")));//安裝方式
				if(!obj.get("siteLine11").equals(null) && !obj.get("siteLine11").equals("")) {
					temp.setFLOOR_LEVEL(new BigDecimal(String.valueOf(obj.get("siteLine11"))));//樓高
				}
				if(!obj.get("siteLine12").equals(null) && !obj.get("siteLine12").equals("")) {
					temp.setTOWER_HEIGHT(new BigDecimal(String.valueOf(obj.get("siteLine12"))));//鐵塔	
				}
				if(!obj.get("siteLine13").equals(null) && !obj.get("siteLine13").equals("")) {
					temp.setPENTHOUSE_HEIGHT(new BigDecimal(String.valueOf(obj.get("siteLine13"))));//屋突
				}
				temp.setADDR(String.valueOf(obj.get("siteLine14"))); //地址
				if(!obj.get("siteLine15").equals(null) && !obj.get("siteLine15").equals("")) {
					temp.setLON(new BigDecimal(String.valueOf(obj.get("siteLine15"))));//經度	
				}
				if(!obj.get("siteLine16").equals(null) && !obj.get("siteLine16").equals("")) {
					temp.setLAT(new BigDecimal(String.valueOf(obj.get("siteLine16"))));//緯度
				}
				temp.setF_CABLE_TYPE(String.valueOf(obj.get("siteLine17")));//Feeder cable type	
				if(!obj.get("siteLine18").equals(null) && !obj.get("siteLine18").equals("")) {
					temp.setF_CABLE_LEN(new BigDecimal(String.valueOf(obj.get("siteLine18"))));//Feeder cable 長度	
				}
				if(!obj.get("siteLine19").equals(null) && !obj.get("siteLine19").equals("")) {
					temp.setJ_CABLE_LEN(new BigDecimal(String.valueOf(obj.get("siteLine19"))));//Jumper cable 長度
				}
				temp.setCOVER_REG(String.valueOf(obj.get("siteLine20")));//覆蓋區域
				temp.setZIP(String.valueOf(obj.get("siteLine21")));//郵遞區號
				temp.setCITY(String.valueOf(obj.get("siteLine22")));//地址-縣市
				temp.setAREA(String.valueOf(obj.get("siteLine23")));//地址-鄉鎮區
				temp.setVILLAGE(String.valueOf(obj.get("siteLine24")));//地址-村里
				temp.setROAD(String.valueOf(obj.get("siteLine25")));//地址-路(街)
				temp.setADJACENT(String.valueOf(obj.get("siteLine26")));//地址-鄰
				temp.setLANE(String.valueOf(obj.get("siteLine27")));//地址-巷
				temp.setALLEY(String.valueOf(obj.get("siteLine28")));//地址-弄
				temp.setSUB_ALLEY(String.valueOf(obj.get("siteLine29")));//地址-衖
				temp.setDOOR_NO(String.valueOf(obj.get("siteLine30")));//地址-門牌
				temp.setDOOR_NO_EX(String.valueOf(obj.get("siteLine31")));//地址-門牌(之)
				temp.setFLOOR(String.valueOf(obj.get("siteLine32")));//地址-樓層
				temp.setFLOOR_EX(String.valueOf(obj.get("siteLine33")));//地址-樓層(之)
				temp.setROOM(String.valueOf(obj.get("siteLine34")));//地址(室)
				temp.setADD_OTHER(String.valueOf(obj.get("siteLine35")));//地址-其他			
				tempList.add(temp);
			}
			
			// ========================================TB_SITE_SR_ANT_CFG_TEMP
			// End
			siteLinesService.saveWorkTemp(siteTemp, example, tempList);
		} catch (NomsException nomsException) {
			log.error(nomsException.getMessage());
			nomsException.printStackTrace();
			return false;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return true;
	}
}