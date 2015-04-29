package com.foya.noms.web.controller.common;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComCompanyExample;
import com.foya.dao.mybatis.model.TbComItemCat;
import com.foya.dao.mybatis.model.TbComItemMain;
import com.foya.dao.mybatis.model.TbComPoItem;
import com.foya.dao.mybatis.model.TbComPoMain;
import com.foya.exception.CreateFailException;
import com.foya.exception.DataExistsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dto.basic.ItemMainDTO;
import com.foya.noms.dto.basic.PoItemDTO;
import com.foya.noms.dto.common.PoMainDTO;
import com.foya.noms.enums.FeeType;
import com.foya.noms.enums.PurchaseOrderType;
import com.foya.noms.service.basic.CM001Service;
import com.foya.noms.service.basic.CM003Service;
import com.foya.noms.service.common.CM004Service;
import com.foya.noms.service.common.CompanyQueryService;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.st.OutsourcingService;
import com.foya.noms.service.system.LookupService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/cm/po")
public class CM004Controller extends BaseController {

	@Autowired
	private CM004Service cM004Service;
	@Autowired
	private CM001Service cM001Service;
	@Autowired
	private LookupService lookupService;
	@Autowired
	private DomainService domainService;
	@Autowired
	private CM003Service cM003Service;
	@Autowired
	private CompanyQueryService companyService;
	@Autowired
	private OutsourcingService outsourcingService;

	/**
	 * 共用PO單初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/common/init")
	public String initCommonPage(HttpServletRequest request, Map<String, Object> model,
			PoMainDTO comPo) {
		TbComCompanyExample example = new TbComCompanyExample();
		example.createCriteria().andTYPE_ENGEqualTo("Y").andTYPE_EQUEqualTo("N")
				.andTYPE_WHEqualTo("N");
		String targetFrameId = request.getParameter("targetFrameId");
		if (targetFrameId != null) {
			model.put("targetFrameId", targetFrameId);
		}
		model.put("companyList", cM001Service.searchCompanyByExample(example));
		model.put("comPo", comPo);
		return "ajaxPage/common/POQuery";
	}

	/**
	 * 查詢
	 * 
	 * @param poNo
	 * @param year
	 * @param poName
	 * @param co_vat_No
	 * @param isTemp
	 * @return
	 */
	@RequestMapping(value = "/common/search")
	@ResponseBody
	public JqGridData<PoMainDTO> search(@RequestParam("poNo") String poNo,
			@RequestParam("year") String year, @RequestParam("poName") String poName,
			@RequestParam("co_vat_No") String co_vat_No, @RequestParam("isTemp") String isTemp) {

		List<PoMainDTO> poMaingList = cM004Service.searchPoByCond(poNo, year, poName, co_vat_No,
				isTemp);
		PageList<PoMainDTO> page = (PageList<PoMainDTO>) poMaingList;
		return new JqGridData<>(page.getPaginator().getTotalCount(), poMaingList);
	}

	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String initPage(HttpServletRequest request, Map<String, Object> model) {
		TbComCompanyExample example = new TbComCompanyExample();
		example.createCriteria().andTYPE_ENGEqualTo("Y").andTYPE_EQUEqualTo("N")
				.andTYPE_WHEqualTo("N");
		model.put("companyList", cM001Service.searchCompanyByExample(example));

		return "ajaxPage/basic/CM004";
	}

	/**
	 * 新增頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insertPage")
	public String insertPage(HttpServletRequest request, Map<String, Object> model) {
		model.put("feeTypeList", FeeType.getLabelValueList());
		model.put("currencyList", lookupService.getLabelValueList("CURRENCYTYPE"));
		model.put("domainList", domainService.getStandardAndTopDomain());
		model.put("poTypeList", PurchaseOrderType.getLabelValueList());
		model.put("poEvent", "insert");
		model.put("poQuotaDTOList", cM004Service.getpoQuotaDTOList(null));
		model.put("result", new TbComPoMain());
		model.put("poYearL",cM004Service.getMinYear());
		
		return "ajaxPage/basic/CM004M";
	}

	/**
	 * 新增
	 * 
	 * @param poMain
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public Map<String, Object> saveNewPo(TbComPoMain poMain,String[] domainQuotaArray,
			@RequestParam("poEvent")String poEvent){
		
		if("on".equals(poMain.getENABLED())){
			poMain.setENABLED("Y");
		}else{
			poMain.setENABLED("N");
		}
				
		Map<String, Object> map = new HashMap<String, Object>();
		if("insert".equals(poEvent)){
			try {
				cM004Service.saveNewPo(poMain ,domainQuotaArray,this.getLoginUser().getUsername());
				map.put("result", true);
			} catch (CreateFailException e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				map.put("result", this.getMessageDetail(e.getErrCode()));
			} catch (DataExistsException e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				map.put("result", this.getMessageDetail(e.getErrCode()));
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
	@RequestMapping(value = "/editPage")
	public String editPage(HttpServletRequest request, Map<String, Object> model) {
		String poId = request.getParameter("po_ID");
		TbComPoMain resultList = cM004Service.searchPoMainByPoId(poId);
		TbComCompany companyName = cM001Service.getCompanyDetailByID(resultList.getCO_VAT_NO());

		model.put("poQuotaDTOList", cM004Service.getpoQuotaDTOList(resultList.getPO_ID().toString()));
		model.put("result", resultList);
		model.put("companyName", companyName == null ? "" : companyName.getCO_NAME());
		model.put("feeTypeList", FeeType.getLabelValueList());
		model.put("currencyList",lookupService.getLabelValueList("CURRENCYTYPE"));
		model.put("domainList", domainService.getStandardAndTopDomain());
		model.put("poTypeList", PurchaseOrderType.getLabelValueList());
		model.put("poEvent", request.getParameter("btnType"));
		model.put("poLineIdList", cM004Service.selectLineIdByPoNo(resultList.getPO_NO()));
		model.put("poLineIdMapList", cM004Service.selectPoLineIdMapByPoNo(resultList.getPO_NO()));
		model.put("poYearL",cM004Service.getMinYear());
		model.put("unUsedAmount", StringUtils.isNotEmpty(poId) ? outsourcingService.getUnusePoMainPayment(Long.valueOf(poId)) : 0);
		
		if (StringUtils.isNotEmpty(poId)) {			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("poId", poId);
			model.put("bookingAmount", outsourcingService.sumPayAmountNoAreabyOutcourcing(map, 0L));
		}
		return "ajaxPage/basic/CM004M";
	}
	
	
	/**
	 * Update Save
	 * @param poMain
	 * @param domainQuotaArray
	 * @return
	 */
	@RequestMapping(value = "/updateSave")
	public @ResponseBody Map<String, Object> saveUpdatePo(TbComPoMain poMain,String[] domainQuotaArray, String jLineIdMap){
		Map<String, Object> map = new HashMap<String, Object>();
		if("on".equals(poMain.getENABLED())){
			poMain.setENABLED("Y");
		}else{
			poMain.setENABLED("N");
		}
		try {
			cM004Service.saveUpdatePo(poMain,domainQuotaArray);
			cM004Service.saveUpdatePoLineMapping(jLineIdMap, poMain.getPO_NO());
			map.put("result", true);
		} catch (UpdateFailException ex) {
			map.put("result", this.getMessageDetail(ex.getErrCode()));
		} catch (Exception ex) {
			map.put("result", ex.getMessage());
		}
		
		return map;
	}
	
	
	/**
	 * 判斷正式PO單是否已被合併
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/countPONo")
	public @ResponseBody String countPONo(HttpServletRequest request) {
		List<TbComPoMain> result = cM004Service.searchByPoNO(request.getParameter("mergeTargetPoNo"));
		Integer count = result.size();
		return count.toString();	
	}
	
	
	/**
	 * 合併PO單
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toMerger")
	public @ResponseBody BaseJasonObject<Object> mergePo(HttpServletRequest request) {
		
		try {
			cM004Service.mergePo(request.getParameter("tempID"),request.getParameter("targetID"));
			return new BaseJasonObject<>(true, request.getParameter("targetID"));
		} catch (DataExistsException ex) {
			return new BaseJasonObject<>(false, ex.getMessage());
		}
	}
	
	/**
	 * 取得所有中項之清單
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/initPoItem")
	public String initPoItem(HttpServletRequest request, Map<String, Object> model, String poId) {
		
		log.info("poId: " + poId);
		TbComPoMain poMain = cM004Service.searchPoMainByPoId(poId);
		TbComCompany company = companyService.queryCompanyNameByCoVatNo(poMain.getCO_VAT_NO());
		
		model.put("subCatItemList", cM003Service.getAllSubItemCat());
		model.put("poMain", poMain);
		model.put("company", company);
		
		return "ajaxPage/basic/CM004I";
	}
	
	/**
	 * 查詢主項
	 * @param request
	 * @param parentCatId
	 * @return
	 */
	@RequestMapping(value = "/getAllItemMain")
	@ResponseBody
	public HashMap<String, Object> getAllItemMain(HttpServletRequest request, 
			                                      String catNo,
			                                      String poId){
		
		log.info("catNo" + catNo + ", poId: " + poId);
		TbComItemCat cat = cM003Service.selectItemCatByCatNo(catNo);
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		HashMap<String,String> dataObj = new HashMap<String,String>();
		dataObj.put("mainItemCatId", String.valueOf(cat.getPARENT_CAT()));
		dataObj.put("subItemCatId", String.valueOf(cat.getCAT_ID()));
		
		List<ItemMainDTO> list = cM003Service.searchByCatForExcel(dataObj);
		List<ItemMainDTO> result = new ArrayList<ItemMainDTO>();
		for (ItemMainDTO item : list) {
			if ("Y".equalsIgnoreCase(item.getENABLED())) {
				result.add(item);
			}
		}
		
		dataObj.put("poId", poId);
		String poFaCategory = "";
		//PO_ITEM的FA_Category 如為null 則帶出 該中項TB_COM_ITEM_CAT所對應之FA_Category
		List<String> poFaCategoryList = cM004Service.selectFaCategoryByCat(dataObj);
		if (poFaCategoryList != null && poFaCategoryList.size() > 0) {
			poFaCategory = poFaCategoryList.get(0);
		}
		
		map.put("subMainfaCategory", StringUtils.isBlank(poFaCategory) ?  cat.getFA_CATEGORY() : poFaCategory);
		map.put("itemList", result);
		
		return map;
		
	}

	/**
	 * 查詢包含於ＰＯ單的工項
	 * @param request
	 * @param poId
	 * @return
	 */
	@RequestMapping(value = "/getPoItemList")
	@ResponseBody
	public BaseJasonObject<PoItemDTO> getPoItemList(HttpServletRequest request, String poId){
		log.info("poId: " + poId);
		List<PoItemDTO> poItemList = cM004Service.getPoitemByPoIdAndUsedBySite(poId);
	
		return new BaseJasonObject<>(poItemList, AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 儲存ＰＯ單之明細檔
	 * @param request
	 * @param model
	 * @return
	 * @throws JSONException 
	 */
	@RequestMapping(value = "/savePoItem")
	@ResponseBody
	public BaseJasonObject<TbComItemMain> savePoItem(HttpServletRequest request, 
			                          @RequestParam("jArray")String jArray,
			                          @RequestParam("poId")String poId,
			                          @RequestParam("catNo")String catNo,
			                          @RequestParam("faCategory")String faCategory) throws JSONException {
		log.info("jArray: " + jArray + ", poId: " + poId + ", catNo: " + catNo + ", faCategory: " + faCategory);
		//存檔時將 輸入 FA_Category 存入 PO_Item
		try {
			JSONArray jsonArray = new JSONArray(jArray);
			List<TbComPoItem> poItemList = new ArrayList<TbComPoItem>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				TbComPoItem poItem = new TbComPoItem();
				poItem.setPO_ID(Long.parseLong(poId));
				poItem.setITEM_ID(obj.getString("itemId"));
				if (!StringUtils.isBlank(obj.getString("price"))) {
					poItem.setPRICE(new BigDecimal(obj.getString("price")));
				}
				poItem.setFA_CATEGORY(faCategory);
				poItem.setCR_USER(getLoginUser().getUsername());
				poItem.setCR_TIME(new Date());
				poItemList.add(poItem);
			}
			
			cM004Service.savePoItem(poItemList, poId, catNo, faCategory);
			
			return new BaseJasonObject<TbComItemMain>(true, getMessageDetail("msg.update.success"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<TbComItemMain>(false, e.getMessage());
		}
		
	}
	
	/**
	 * 匯出工項
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/exportExcel", method = RequestMethod.GET) 
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("poId") String poId) { 

		log.info("poId: " + poId);
		Map<String, String> map = new HashMap<String, String>();
		map.put("poId", poId);
		List<ItemMainDTO> itemList = cM004Service.selectItemMainItem(map);
		
		String fileName = "";
		try {
			fileName = new String("工項".getBytes(), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
		
		cM003Service.exportExcel(response, "sheet", fileName, itemList);
    } 
	
	/**
	 * 匯入工項
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST) 
	@ResponseBody
    public BaseJasonObject<TbComPoItem> importExcel(MultipartHttpServletRequest mutipartRequest,
    		@RequestParam("poId") String poId) { 

		log.info("entry importExcel, poId: " + poId);
		try {
			cM004Service.importPoItem(poId, mutipartRequest);
			return new BaseJasonObject<TbComPoItem>(true, "匯入成功");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new BaseJasonObject<TbComPoItem>(false, e.getMessage());
		}
		
    } 
}
