package com.foya.noms.web.controller.inv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foya.dao.mybatis.model.TbInvBooking;
import com.foya.dao.mybatis.model.TbInvInv;
import com.foya.dao.mybatis.model.TbInvTr;
import com.foya.dao.mybatis.model.TbInvTrDtl;
import com.foya.dao.mybatis.model.TbInvTrDtlExample;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.exception.NomsException;
import com.foya.noms.dto.ExcelImportDTO;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvTrDtlCompleteDTO;
import com.foya.noms.dto.inv.TbInvTrDtlDTO;
import com.foya.noms.dto.inv.TbInvTrReadExcelDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.inv.INV007Service;
import com.foya.noms.service.inv.INV008Service;
import com.foya.noms.service.inv.INV016Service;
import com.foya.noms.service.inv.InvPublicUtilService;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.DateUtils;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/inv")
public class INV007Controller extends BaseController {

	@Inject
	private InvPublicUtilController invPublicUtilController;
	@Inject 
	private INV007Service inv007Service;
	@Autowired
	private WorkflowActionService workflowActionService;
	@Inject
	private INV008Service inv008Service;
	@Inject
	private InvPublicUtilService invPublicUtilService;
	@Inject
	private INV016Service inv016Service;
	@Inject
	private DomainService domainService;
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv007/init")
	public String init(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		
		//model.put("wareHouseSelect", invPublicUtilController.initWareHouseDeptSelect(request, getLoginUser().getUsername()));
		model.put("personnelSelect", invPublicUtilController.initPersonnelDeptSelectlike(request,"4"));
		//model.put("deptSelect", invPublicUtilController.initDeptSelect(request));
		model.put("statusSelect", invPublicUtilController.getLookupByType("INV_TR_STATUS"));
		model.put("deptId", getLoginUser().getDeptId());
		model.put("domainSelect",
				domainService.getAccessDomainByUser(getLoginUser().getDomain(), true));
		return "ajaxPage/inv/INV007";
	}
	
	
	/**
	 * 查詢
	 */
	@RequestMapping(value = "/inv007/Search")
	@ResponseBody
	public JqGridData<TbInvTr> search(
			@RequestParam("trNo") String trNo,
			@RequestParam("statusSelect") String statusSelect,
			@RequestParam("wareHouseOutSelect") String wareHouseOutSelect,
			@RequestParam("wareHouseInSelect") String wareHouseInSelect,
			@RequestParam("needStartDate") String needStartDate,
			@RequestParam("needEndDate") String needEndDate,
			@RequestParam("deptNeedSelect") String deptNeedSelect,
			@RequestParam("applyStartDate") String applyStartDate,
			@RequestParam("applyEndDate") Date applyEndDate,
			@RequestParam("deptApplySelect") String deptApplySelect,
			@RequestParam("troutStartDate") String troutStartDate,
			@RequestParam("troutEndDate") String troutEndDate,
			@RequestParam("personnelSelect") String personnelSelect,
			@RequestParam("trinStartDate") String trinStartDate,
			@RequestParam("trinEndDate") String trinEndDate,
			@RequestParam("domain") String domain
//			@RequestParam("checkOutWareHouse") String checkOutWareHouse
			) throws Exception{		
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		log.debug("applyEndDate:"+applyEndDate);
		dataObj.put("trNo", trNo);
		dataObj.put("statusSelect", statusSelect);
		dataObj.put("wareHouseOut", wareHouseOutSelect);
		dataObj.put("wareHouseIn", wareHouseInSelect);
//		dataObj.put("needStartDate",formatter.parse(needStartDate));
//		dataObj.put("needEndDate", formatter.parse(needEndDate));
		dataObj.put("deptNeed", deptNeedSelect);
		dataObj.put("appDept", deptApplySelect);
		if (needStartDate != null && ! "".equals(needStartDate)) 
			dataObj.put("needStartDate", formatter.parse(needStartDate));
		if (needEndDate != null && ! "".equals(needEndDate)) {
			dataObj.put("needEndDate", formatter.parse(needEndDate));
		}
		if (applyStartDate != null && ! "".equals(applyStartDate)) 
			dataObj.put("applyStartDate", formatter.parse(applyStartDate));
		if (applyEndDate != null && ! "".equals(applyEndDate)) 
			dataObj.put("applyEndDate", applyEndDate);
		dataObj.put("deptApplySelect", deptApplySelect);
		if (troutStartDate != null && ! "".equals(troutStartDate)) 
			dataObj.put("troutStartDate", formatter.parse(troutStartDate));
		if (troutEndDate != null && ! "".equals(troutEndDate)) 
			dataObj.put("troutEndDate", formatter.parse(troutEndDate));
		dataObj.put("appUser", personnelSelect);
		if (trinStartDate != null && ! "".equals(trinStartDate)) 
			dataObj.put("trinStartDate", formatter.parse(trinStartDate));
		if (trinEndDate != null && ! "".equals(trinEndDate)) 
			dataObj.put("trinEndDate", formatter.parse(trinEndDate));
//		dataObj.put("checkOutWareHouse", checkOutWareHouse);
		dataObj.put("domain", domain);
		List<TbInvTr> InvTrList = inv007Service.search(dataObj);
		
		PageList<TbInvTr> page = (PageList<TbInvTr>) InvTrList;
		return new JqGridData<TbInvTr>(page.getPaginator().getTotalCount(),InvTrList);
	}
	/**
	 * 明細頁面-查詢主檔資料
	 */
	@RequestMapping(value = "/inv007/searchMasterDscr")
	public String searchMasterDscr(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("trNo") String trNo) throws Exception{
		HashMap<String,String> dataObj=new HashMap<String,String>();
		dataObj.put("trNo", trNo);
		model.put("invTrDscr", inv007Service.searchTrMasterDscr(dataObj));
		model.put("trNo", trNo);
		return "ajaxPage/inv/INV007L";
	}
//	/**
//	 * 明細頁面-查詢明細資料
//	 */
//	@RequestMapping(value = "/inv007/searchDtlLpage")
//	@ResponseBody
//	public JqGridData<TbInvTrDtlCompleteDTO> searchDtlLpage(HttpServletRequest request,
//			@RequestParam("trNo") String trNo,
//			Map<String, Object> model
//		)throws Exception{		
//		List<TbInvTrDtlCompleteDTO> InvTrDtlList = inv008Service.getInvTrDtlDTo(trNo, null, null);		
//		PageList<TbInvTrDtlCompleteDTO> page = (PageList<TbInvTrDtlCompleteDTO>) InvTrDtlList;
//		model.put("dtlData", page);
//		return new JqGridData<TbInvTrDtlCompleteDTO>(page.getPaginator().getTotalCount(),InvTrDtlList);
//	}
	
	/**
	 * 明細頁面-查詢明細資料
	 */
	@RequestMapping(value = "/inv007/searchDtl")
	@ResponseBody
	public JqGridData<TbInvTrDtlDTO> searchDtl(HttpServletRequest request,
			@RequestParam("trNo") String trNo,
			Map<String, Object> model
		)throws Exception{		
		
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("trNo", trNo);
		List<TbInvTrDtlDTO> InvTrDtlList = inv007Service.searchDtlPage(dataObj);
		PageList<TbInvTrDtlDTO> page = (PageList<TbInvTrDtlDTO>) InvTrDtlList;
		model.put("dtlData", page);
		return new JqGridData<TbInvTrDtlDTO>(page.getPaginator().getTotalCount(),InvTrDtlList);
	}
	/**
	 * 主畫面取代新增畫面
	 */
	@RequestMapping(value = "/inv007/add")
	public String searchMasterDscr(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		//model.put("wareHouseSelect", invPublicUtilController.initWareHouseDeptSelect(request, getLoginUser().getUsername()));
		model.put("personnelSelect", invPublicUtilController.initPersonnelDeptSelectlike(request,"4"));
		//model.put("deptSelect", invPublicUtilController.initDeptSelect(request));
		model.put("catg1Select", invPublicUtilController.initCategorySelect(request));
		model.put("matNoSelect", invPublicUtilController.initMaterialSelect(request));
		model.put("matStatus", invPublicUtilController.getLookupByType("MAT_STATUS"));
		model.put("saveMode", "add");//007M.jsp 儲存模式 add:新增儲存 editAdd:修改儲存
		model.put("domainSelect",
				domainService.getAccessDomainByUser(getLoginUser().getDomain(), true));
		return "ajaxPage/inv/INV007M";
	}
	
	/**
	 * 新增明細JqGrid row畫面前置確認作業
	 */
	@RequestMapping(value = "/inv007/addRowCheck")
	@ResponseBody
	public BaseJasonObject<TbInvInvDTO> addRowCheck(HttpServletRequest request,
			@RequestParam("matNo") String matNo,
			@RequestParam("matStatus") String matStatus,
			@RequestParam("wareHouseOut") String wareHouseOut,
			Map<String, Object> model) throws Exception{
		HashMap<String,String> dataObj=new HashMap<String,String>();
		dataObj.put("matNo", matNo);
		dataObj.put("matStatus", matStatus);
		dataObj.put("wareHouseOut", wareHouseOut);
		return new BaseJasonObject<>(inv007Service.addDtlRowCheck(dataObj), AJAX_SUCCESS, AJAX_EMPTY);
	}
	/**
	 * 修改頁面-查詢主檔資料
	 */
	@RequestMapping(value = "/inv007/searchDtlMasterDscr")
	public String searchDtlMasterDscr(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("trNo") String trNo) throws Exception{
		HashMap<String,String> dataObj=new HashMap<String,String>();
		dataObj.put("trNo", trNo);
		String deptCode=trNo.replaceAll("[0-9]","");
		deptCode=deptCode.substring(2, deptCode.length());
		model.put("deptCode",deptCode);
		System.out.println("deptCode = " + deptCode);
		model.put("saveMode", "editAdd");//007M.jsp 儲存模式 add:新增儲存 editAdd:修改儲存
		model.put("invTrDscr", inv007Service.searchTrMasterDscr(dataObj));
		//model.put("wareHouseSelect", invPublicUtilController.selectWareHouseByDomainDept(request, getLoginUser().getUsername(),getLoginUser().getDomain().getID()));//登入者domain找倉別
		model.put("wareHouseSelect", invPublicUtilController.selectWareHouseByDomainDept(request, getLoginUser().getUsername(),"HQ".equals(deptCode)?"":deptCode));//改抓單號的domain找倉別 全區就給空白！
		model.put("personnelSelect", invPublicUtilController.initPersonnelDeptSelectlike(request,"4"));
		//model.put("deptSelect", invPublicUtilController.initDeptSelectByDomain(request,getLoginUser().getDomain().getID()));//登入者domain找部門
		if (deptCode != null && deptCode.equals("HQ")) {
			model.put("deptSelect", invPublicUtilService.getDeptByDomain(new TbOrgDeptExample()));
		} else {
			model.put("deptSelect", invPublicUtilController.initDeptSelectByDomain(request,deptCode));//改抓單號的domain找部門
		}
		model.put("catg1Select", invPublicUtilController.initCategorySelect(request));
		model.put("matNoSelect", invPublicUtilController.initMaterialSelect(request));
		model.put("matStatus", invPublicUtilController.getLookupByType("MAT_STATUS"));
		model.put("domainSelect",
				domainService.getAccessDomainByUser(getLoginUser().getDomain(), true));
		
		return "ajaxPage/inv/INV007M";
	}
	/**
	 * 主畫面取代修改畫面
	 */
	@RequestMapping(value = "/inv007/editQry")
	@ResponseBody
	public JqGridData<TbInvTrDtlDTO> edit(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("trNo") String trNo,
			@RequestParam("wareHouseOut") String wareHouseOut) throws Exception{
		HashMap<String,Object> dataObj=new HashMap<String,Object>();
		dataObj.put("trNo", trNo);
		List<TbInvTrDtlDTO> InvTrDtlList = inv007Service.searchDtlPage(dataObj);
		//將明細資料傳至service處理 判斷數量欄位/Booking狀態 
		List<TbInvTrDtlDTO> returnList=inv007Service.searchInvDtl(InvTrDtlList, wareHouseOut);
		PageList<TbInvTrDtlDTO> page = (PageList<TbInvTrDtlDTO>) returnList;
			try{
				return new JqGridData<TbInvTrDtlDTO>(page.getPaginator().getTotalCount(),returnList);
			}catch(NullPointerException e){
				return new JqGridData<TbInvTrDtlDTO>(0, returnList);
			}
			
	}
	

	
	/**
	 * 新增/更新 主檔資料
	 */
	@RequestMapping(value = "/inv007/saveMasterData")
	@ResponseBody
	public BaseJasonObject<Object> saveMasterData(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("saveMode") String saveMode,
			@RequestParam("status") String status,
			@RequestParam("outWareHouse") String outWareHouse,
			@RequestParam("inWareHouse") String inWareHouse,
			@RequestParam("deptNeed") String deptNeed,
			@RequestParam("needDate") String needDate,
			@RequestParam("deptApply") String deptApply,
			@RequestParam("personnel") String personnel,
			@RequestParam(value = "memo", required = false)  String memo,
			@RequestParam(value = "trNo", required = false)  String trNo,
			@RequestParam(value = "crTime", required = false)  Date crTime) throws Exception{
		TbInvTr inv = new TbInvTr();
		String returnMsg="success",invMsg="";
		BaseJasonObject<Object> json=null;		
		//寫入主檔
		inv=returnInv(trNo,status,inWareHouse,outWareHouse,deptNeed,needDate,
				deptApply,personnel,memo,crTime);		
		if("add".equals(saveMode)){
			invMsg=inv007Service.insertTbInvTrSelective(inv);
		}else if("editAdd".equals(saveMode)){
			invMsg=inv007Service.updateTbInvTrSelective(inv);
		}
		if("fail".equals(invMsg))
			returnMsg=" 調撥申請主檔寫入錯誤 ";
		if("success".equals(returnMsg))
			 json=new BaseJasonObject<Object>(true,trNo);
		 else
			 json=new BaseJasonObject<Object>(false,returnMsg);
		return json;
	}
	
	/**
	 * 新增/更新 明細檔 資料
	 */
	@RequestMapping(value = "/inv007/saveDetailData")
	@ResponseBody
	public BaseJasonObject<Object> saveDetailData(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("saveMode") String saveMode,
			@RequestParam("personnel") String personnel,
			@RequestParam("matNo") String matNo,
			@RequestParam("maeName") String maeName,
			@RequestParam("matStatus") String matStatus,
			@RequestParam("appQty") int appQty,
			@RequestParam(value = "trNo", required = false)  String trNo,
			@RequestParam("ctrlType") String ctrlType,
			@RequestParam(value = "trDtlNo", required = false)  Long trDtlNo,
			@RequestParam("crTime") Date crTime) throws Exception{
		TbInvTrDtl invDtl = new TbInvTrDtl();
		String returnMsg="success",invDtlMsg="";
		BaseJasonObject<Object> json=null;	
		TbInvTrDtlExample dtlExample = new TbInvTrDtlExample();
		dtlExample.createCriteria().andTr_noEqualTo(trNo).andMat_noEqualTo(matNo).andMat_statusEqualTo(new Byte(matStatus));
		if("editAdd".equals(saveMode)){
			inv007Service.deleteTbInvTrDtlByPrimarykey(dtlExample);
		}
		//如果為序號件 拆成一筆一筆寫入dtl
		if("S".equals(ctrlType)){
			for(int i=0;i<Integer.valueOf(appQty);i++){
				//寫入明細檔
					invDtl=returnInvDtl(trNo,matNo,matStatus,1,personnel,crTime);	
				//更新invTr與Dtl檔
				if("add".equals(saveMode)){
					invDtlMsg=inv007Service.insertTbInvTrDtlSelective(invDtl);
				}else if("editAdd".equals(saveMode)){
					invDtlMsg=inv007Service.insertTbInvTrDtlSelective(invDtl);
				}
			}
		}else{		
			//寫入明細檔
			invDtl=returnInvDtl(trNo,matNo,matStatus,appQty,personnel,crTime);			
			if("add".equals(saveMode)){
				invDtlMsg=inv007Service.insertTbInvTrDtlSelective(invDtl);	
			}else if("editAdd".equals(saveMode)){
				invDtlMsg=inv007Service.insertTbInvTrDtlSelective(invDtl);
			}
		}
		
		if("fail".equals(invDtlMsg))
			returnMsg=returnMsg+" 調撥申請明細檔寫入錯誤 ";
		if("success".equals(returnMsg))
			 json=new BaseJasonObject<Object>(true,trNo);
		 else
			 json=new BaseJasonObject<Object>(false,returnMsg);
		return json;
	}
	
	/**
	 * 刪除 明細檔 資料
	 */
	@RequestMapping(value = "/inv007/delDtlData")
	@ResponseBody
	public BaseJasonObject<Object> delDtlData(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "trNo", required = false)  String trNo,
			@RequestParam(value = "matNo", required = false)  String matNo,
			@RequestParam(value = "matStatus", required = false)  String matStatus)throws Exception{		
		BaseJasonObject<Object> json=null;
		TbInvTrDtlExample example = new TbInvTrDtlExample();
		example.createCriteria().andTr_noEqualTo(trNo).andMat_noEqualTo(matNo).andMat_statusEqualTo(new Byte(matStatus));
		 if("success".equals(inv007Service.deleteTbInvTrDtlByPrimarykey(example)))
			 json=new BaseJasonObject<Object>(true,"");
		 else
			 json=new BaseJasonObject<Object>(false,"");
		return json;
	}
	
	/**
	 * 刪除 主檔 資料
	 */
	@RequestMapping(value = "/inv007/delMasData")
	@ResponseBody
	public BaseJasonObject<Object> delDtlData(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("trNo") String trNo)throws Exception{
		BaseJasonObject<Object> json=null;
		 if("success".equals(inv007Service.deleteByPrimaryKey(trNo)))
			 json=new BaseJasonObject<Object>(true,"");
		 else
			 json=new BaseJasonObject<Object>(false,"");
		 return json;
	}
	/**
	 * 產生調撥單號
	 */
	@RequestMapping(value = "/inv007/genTrNo")
	@ResponseBody
	public BaseJasonObject<Object> genTrNo(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("personnel") String personnel,
			@RequestParam(value = "trNo", required = false)  String trNo) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		try{
			if("".equals(trNo) || trNo.equals(null)){
				trNo = sdf.format(new Date());
				trNo=inv007Service.selectTrNoToday(trNo,personnel);
			}else{
				trNo=inv007Service.selectTrNoToday(trNo,personnel);
			}
		}catch(NullPointerException e){
			trNo = sdf.format(invPublicUtilService.getToday());
			trNo=inv007Service.selectTrNoToday(trNo,personnel);
		}
		
		return new BaseJasonObject<Object>(true,trNo);
	}
	/**
	 * 按鈕動作(申請) 資料
	 */
	@RequestMapping(value = "/inv007/applyData")
	@ResponseBody
	public BaseJasonObject<Object> applyData(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("clickType") String clickType,
			@RequestParam("saveMode") String saveMode,
			@RequestParam("trNo") String trNo,
			@RequestParam("masData") String masData,
			@RequestParam("dtlArray") String dtlArray
			) throws Exception{
		BaseJasonObject<Object> json=new BaseJasonObject<Object>(AJAX_SUCCESS,trNo);
		try{
			List<TbInvTrDtlDTO> tempList = new ArrayList<TbInvTrDtlDTO>();
			
			TbInvTr trData = new TbInvTr();
			JSONArray jsonArray = new JSONArray(dtlArray);//組合明細清單
			for (int i = 0; i < jsonArray.length(); i++) {
				TbInvTrDtlDTO temp = new TbInvTrDtlDTO();
				JSONObject obj = jsonArray.getJSONObject(i);
				temp.setTr_no((String)obj.get("tr_no"));
				temp.setMat_no((String)obj.get("mat_no"));
				temp.setApp_qty(Integer.valueOf((String)obj.get("app_qty")));
				temp.setMat_status(new Byte((String)obj.get("mat_status")));
				temp.setCtrlType(obj.getString("ctrlType"));
				tempList.add(temp);
			}
			JSONArray jsonMasArray = new JSONArray(masData);//組合明細清單
			for (int i = 0; i < jsonMasArray.length(); i++) {
				JSONObject obj = jsonMasArray.getJSONObject(i);
				trData.setTr_no(obj.getString("tr_no"));
				trData.setTr_in_wh_id(obj.getString("tr_in_wh_id"));
				trData.setTr_out_wh_id(obj.getString("tr_out_wh_id"));
				trData.setNeed_dept_id(obj.getString("need_dept_id"));
				trData.setApp_dept_id(obj.getString("app_dept_id"));
				trData.setApp_user(obj.getString("app_user"));
				trData.setNeed_date(DateUtils.parse(obj.getString("need_date"), "yyyy/MM/dd"));
				trData.setStatus(new Byte(obj.getString("status")));
				trData.setMemo(obj.getString("memo"));
			}
			inv007Service.applyData(saveMode, trData, tempList,getLoginUser().getUsername(),clickType);
		}catch(Exception throwable){
			log.error(throwable.getMessage());
			throwable.printStackTrace();
			json=new BaseJasonObject<Object>(false,"調撥申請錯誤 原因:"+throwable.getMessage());
		}
		return json;
	}
//	/**
//	 * 申請/更新 主檔資料
//	 */
//	@RequestMapping(value = "/inv007/applyMasterData")
//	@ResponseBody
//	public BaseJasonObject<Object> applyMasterData(HttpServletRequest request,
//			Map<String, Object> model,
//			@RequestParam("saveMode") String saveMode,
//			@RequestParam("status") String status,
//			@RequestParam("outWareHouse") String outWareHouse,
//			@RequestParam("inWareHouse") String inWareHouse,
//			@RequestParam("deptNeed") String deptNeed,
//			@RequestParam("needDate") String needDate,
//			@RequestParam("deptApply") String deptApply,
//			@RequestParam("personnel") String personnel,
//			@RequestParam(value = "memo", required = false)  String memo,
//			@RequestParam(value = "trNo", required = false)  String trNo,
//			@RequestParam(value = "crTime", required = false)  Date crTime) throws Exception{
//		TbInvTr inv = new TbInvTr();
//		String returnMsg="success",invMsg="";
//		BaseJasonObject<Object> json=null;
//		//寫入主檔
//		inv=returnInv(trNo,status,inWareHouse,outWareHouse,deptNeed,needDate,
//				deptApply,String.valueOf(getLoginUser().getUserId()),memo,crTime);
//		//更新invTr與Dtl檔
//		try{
//			if("add".equals(saveMode)){
//				invMsg=inv007Service.insertTbInvTrSelective(inv,getLoginUser().getHrDeptId());			
//			}else if("editAdd".equals(saveMode)){
//				invMsg=inv007Service.updateTbInvTrSelective(inv,getLoginUser().getHrDeptId());
//			}
//		}catch(NomsException e){}
//		if("fail".equals(invMsg))
//			returnMsg=" 調撥申請主檔寫入錯誤 ";
//		if("success".equals(returnMsg))
//			 json=new BaseJasonObject<Object>(true,trNo);
//		 else
//			 json=new BaseJasonObject<Object>(false,returnMsg);
//		return json;
//	}
	
	/**
	 * 申請/更新 明細檔 資料
	 */
	@RequestMapping(value = "/inv007/applyDetailData")
	@ResponseBody
	public BaseJasonObject<Object> applyDetailData(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("saveMode") String saveMode,
			@RequestParam("outWareHouse") String outWareHouse,
			@RequestParam("personnel") String personnel,
			@RequestParam("matNo") String matNo,
			@RequestParam("maeName") String maeName,
			@RequestParam("matStatus") String matStatus,
			@RequestParam("appQty") int appQty,
			@RequestParam(value = "trNo", required = false)  String trNo,
			@RequestParam("ctrlType") String ctrlType,
			@RequestParam("crTime") Date crTime) throws Exception{
		TbInvTrDtl invDtl = new TbInvTrDtl();
		TbInvBooking invBooking = new TbInvBooking();
		TbInvInv invInv = new TbInvInv();
		String returnMsg="success",invDtlMsg="",bookingMsg="",invInvMsg="";
		BaseJasonObject<Object> json=null;
		TbInvTrDtlExample dtlExample = new TbInvTrDtlExample();
		dtlExample.createCriteria().andTr_noEqualTo(trNo).andMat_noEqualTo(matNo).andMat_statusEqualTo(new Byte(matStatus));
		if("1".equals(matStatus)){
			//寫入booking檔
			invBooking.setWh_id(outWareHouse);
			TbInvWarehouseDTO wareHouse;
			UTbInvWarehouseExample example = new UTbInvWarehouseExample();
			example.createCriteria().andIs_effectiveEqualTo(true).andWh_idEqualTo(outWareHouse);
			List<TbInvWarehouseDTO> list = inv016Service.selectInvWarehouseByExample(example);
			try{//是可用品需要增加寫入 domain dept_id from 調出倉
				if(list.size()>0){
					wareHouse = (TbInvWarehouseDTO) list.get(0);
				invBooking.setDept_id(wareHouse.getDept_id());
				invBooking.setDomain(wareHouse.getDomain());
				}
			}catch(Exception e){}
			invBooking.setMat_no(matNo);
			invBooking.setAct_no(trNo);
			invBooking.setAct_type(Byte.valueOf("2"));
			invBooking.setBooking_qty(appQty);
			invBooking.setCr_user(String.valueOf(getLoginUser().getUserId()));
			invBooking.setCr_time(crTime);
			invBooking.setMd_user(String.valueOf(getLoginUser().getUserId()));
			invBooking.setMd_time(crTime);
			bookingMsg=inv007Service.inserttbInvBookingSelective(invBooking);
			//寫入invinv檔
			invInv.setBooking_qty(appQty);
			invInv.setMd_user(String.valueOf(getLoginUser().getUserId()));
			invInv.setMd_time(crTime);
			invInvMsg=inv007Service.updateTbInvInvSelective(invInv, outWareHouse, matNo);
		}
		if("editAdd".equals(saveMode)){
			inv007Service.deleteTbInvTrDtlByPrimarykey(dtlExample);
		}
		//如果為序號件 拆成一筆一筆寫入dtl
		if("S".equals(ctrlType)){
			for(int i=0;i<Integer.valueOf(appQty);i++){
				//寫入明細檔
					invDtl=returnInvDtl(trNo,matNo,matStatus,1,String.valueOf(getLoginUser().getUserId()),crTime);	
				//更新invTr與Dtl檔
				if("add".equals(saveMode)){
					invDtlMsg=inv007Service.insertTbInvTrDtlSelective(invDtl);
				}else if("editAdd".equals(saveMode)){
					invDtlMsg=inv007Service.insertTbInvTrDtlSelective(invDtl);
				}
			}
		}else{			
			//寫入明細檔
			invDtl=returnInvDtl(trNo,matNo,matStatus,appQty,String.valueOf(getLoginUser().getUserId()),crTime);
			invDtlMsg=inv007Service.insertTbInvTrDtlSelective(invDtl);
		}
		if("fail".equals(invDtlMsg))
			returnMsg=returnMsg+" 調撥申請明細檔寫入錯誤 ";
		if("fail".equals(bookingMsg))
			returnMsg=returnMsg+" 預約歷程檔寫入錯誤 ";
		if("fail".equals(invInvMsg))
			returnMsg=returnMsg+" 庫存主檔寫入錯誤 ";
		if("success".equals(returnMsg))
			 json=new BaseJasonObject<Object>(true,trNo);
		 else
			 json=new BaseJasonObject<Object>(false,returnMsg);
		return json;
	}
	/**
	 * 主畫面取代新增畫面
	 */
	@RequestMapping(value = "/inv007/batchAdd")
	public String batchAdd(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		//model.put("wareHouseSelect", invPublicUtilController.initWareHouseDeptSelect(request, getLoginUser().getUsername()));
		model.put("personnelSelect", invPublicUtilController.initPersonnelDeptSelectlike(request,"4"));
		//model.put("deptSelect", invPublicUtilController.initDeptSelect(request));
		model.put("saveMode", "add");//007M.jsp 儲存模式 add:新增儲存 editAdd:修改儲存
		model.put("deptId", getLoginUser().getDeptId());
		model.put("domainSelect",
				domainService.getAccessDomainByUser(getLoginUser().getDomain(), true));
		return "ajaxPage/inv/INV007A";
	}	

	/**
	 * 匯出excel
	 * @return
	 */
	@RequestMapping(value = "/inv007/genBatchApplyExcel", method = RequestMethod.GET) 
	public void getExcel(HttpServletRequest request,HttpServletResponse response,
			Map<String, Object> model,
			@RequestParam("wareHouseOut") String wareHouseOut) throws Exception { 

		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("wareHouseOut", wareHouseOut);
		List<TbInvInvDTO> invInvList = inv007Service.selectForBatchExcel(dataObj);
		
		//file name
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = wareHouseOut + sdf.format(new Date())+".xls";
		
		inv007Service.exportExcel(response, "sheet", fileName, invInvList);
    } 
	
	/**
	 * 匯入excel
	 * @return
	 */
	@RequestMapping(value = "/inv007/readBatchApply", method = RequestMethod.POST)
	@ResponseBody
	public BaseJasonObject<TbInvTrDtlDTO> handleUploadProcess(
			MultipartHttpServletRequest mutipartRequest,
			Map<String, Object> model,
			@RequestParam("wareHouseOut") String wareHouseOut) throws Exception {
		List<ExcelImportDTO<TbInvTrReadExcelDTO>> models = null;
		List<ExcelImportDTO<TbInvTrReadExcelDTO>> checkTitleModels = null;
		JqGridData<TbInvTrDtlDTO> grid=null;
		// 讀取報表抬頭
		for (Iterator<String> itr = mutipartRequest.getFileNames(); itr
				.hasNext();) {
			MultipartFile file = mutipartRequest.getFile(itr.next());
			checkTitleModels = inv007Service.readExcel(file.getInputStream(), 0, 0);
		}
		
		ExcelImportDTO<TbInvTrReadExcelDTO> excelData = (ExcelImportDTO<TbInvTrReadExcelDTO>)checkTitleModels.get(0);
		if(!"料號".equals(excelData.getRecord().getMatNo()) || !"品名".equals(excelData.getRecord().getMatName())
				|| !"物料狀態".equals(excelData.getRecord().getMatStatus()) || !"申請調撥數".equals(excelData.getRecord().getAppQty()))
			return new BaseJasonObject<>(false,"所匯入檔案標題列有誤，請符合調撥申請批次新增所產生之檔案格式");
		// 讀取報表
		for (Iterator<String> itr = mutipartRequest.getFileNames(); itr
				.hasNext();) {
			MultipartFile file = mutipartRequest.getFile(itr.next());
			models = inv007Service.readExcel(file.getInputStream(), 0, 1);
		}
		
		// 將明細資料傳至service處理 判斷數量欄位/Booking狀態
		List<TbInvTrDtlDTO> returnList = inv007Service.searchBatchApply(models,
				wareHouseOut);
		PageList<TbInvTrDtlDTO> page = (PageList<TbInvTrDtlDTO>) returnList;
		
		try {
			grid=new JqGridData<TbInvTrDtlDTO>(page.getPaginator()
					.getTotalCount(), returnList);
		} catch (NullPointerException e) {
			grid=new JqGridData<TbInvTrDtlDTO>(0, returnList);
		}
		TbInvTrDtlDTO pageInfo = new TbInvTrDtlDTO();
		
		pageInfo.setRecord(grid.getRecords());
		pageInfo.setPage(grid.getRecords());
		pageInfo.setTotal(grid.getTotal());
		returnList.add(pageInfo);
		
		return new BaseJasonObject<>(returnList, "匯入檔案成功", AJAX_EMPTY);
	}
	
	 /**
     * 供WORKFLOW審核呼叫
     * @param orderId 庫存單號或調撥單號
     * @param action  SUCCESS/REJECT
     * @return String
     */
    @RequestMapping(value = "/inv007/applyByWF")
    @ResponseBody
    public String applyByWF( @RequestParam("orderId")String orderId, @RequestParam("action") String action )throws NomsException{
    	log.debug("orderId : "+orderId+",action : "+action);
              String result = "";
              int status=0;
              if(action.equals(AppConstants.WORKFLOW_REST_APPROVAL)){ //審核通過,狀態改核可                        
            	  	status=3;
              }else if(action.equals(AppConstants.WORKFLOW_REST_REJECT)){
            	  	status=10;
            	  	inv007Service.updateTbInvInvAndBooking(orderId);//如果狀態為駁回 減回庫存及預約數
              }                    
              TbInvTr data = new TbInvTr();
              data.setStatus(new Byte(String.valueOf(status)));
              data.setMd_user("system");
              data.setMd_time(invPublicUtilService.getToday());
              data.setTr_no(orderId);
              result = inv007Service.updateTbInvTrSelective(data);             
              return result;
    }
    
    //取得庫存數
    @RequestMapping(value = "/inv007/getInvQty")
    @ResponseBody
    public BaseJasonObject<Object> getInvQty( @RequestParam(value = "whId", required = false) String whId, @RequestParam(value = "matNo", required = false)  String matNo 
    		,@RequestParam(value = "matStatus", required = false) String matStatus)throws Exception{
    		log.debug("whId: "+whId+" matNo: "+matNo+" matStatus:"+matStatus);
    		if(!"".equals(whId) && !"".equals(matNo) && !"".equals(matStatus)){
    			try{
    				if(whId.equals(null));
    			}catch(NullPointerException e){whId="";}
    			try{
    				if(matNo.equals(null));
    			}catch(NullPointerException e){matNo="";}
    			try{
    				if(matStatus.equals(null));
    			}catch(NullPointerException e){matStatus="";}
    			return new BaseJasonObject<Object>(true,String.valueOf(inv007Service.getInvQty(whId, matNo, matStatus)));
    		}else{
    			return new BaseJasonObject<Object>(false,"");
    		}
    }
	//設定TbInvTr object
	public TbInvTr returnInv(String trNo,String status,String inWareHouse,String outWareHouse,
			String deptNeed,String needDate,String deptApply,String personnel,String memo,Date crTime) throws Exception{
		TbInvTr inv = new TbInvTr();
		String user=getLoginUser().getUsername();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		List<TbSysLookup> lookupList=invPublicUtilService.getLookupByTypeAndCode("ORG_SPECIFIC_DEPT_ID", "LOGISTICS_DEPT_ID");
		TbSysLookup data=(TbSysLookup)lookupList.get(0);
				inv.setTr_no(trNo);
				inv.setStatus(Byte.valueOf(status));
				inv.setTr_in_wh_id(inWareHouse);
				inv.setTr_out_wh_id(outWareHouse);
				inv.setNeed_dept_id(deptNeed);
				if (needDate != null && ! "".equals(needDate)) 
					inv.setNeed_date(formatter.parse(needDate));
				inv.setApp_dept_id(deptApply);
				inv.setApp_user(personnel);
				inv.setApp_date(crTime);
				inv.setMemo(memo);
				inv.setCr_user(user);
				inv.setCr_time(crTime);
				inv.setMd_user(user);
				inv.setMd_time(crTime);
				inv.setIf_manual_close(false);//預設不結案
				inv.setProcessType(StringUtils.equals(data.getVALUE1(), getLoginUser().getHrDeptId())?"TransferApplyForLogistics":"TransferApply");
		return inv;
	}
	//設定TbInvTrDtl object
	public TbInvTrDtl returnInvDtl(String trNo,String matNo,String matStatus,int appQty,String personnel,Date crTime){
		TbInvTrDtl invDtl = new TbInvTrDtl();
		invDtl.setTr_no(trNo);
		invDtl.setMat_no(matNo);
		invDtl.setMat_status(Byte.valueOf(matStatus));
		invDtl.setApp_qty(appQty);
		invDtl.setCr_user(personnel);
		invDtl.setCr_time(crTime);
		invDtl.setMd_user(personnel);
		invDtl.setMd_time(crTime);
		return invDtl;
	}
}
