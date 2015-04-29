package com.foya.noms.web.controller.inv;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbInvInv;
import com.foya.dao.mybatis.model.TbInvMaterial;
import com.foya.dao.mybatis.model.TbInvMaterialOpt;
import com.foya.dao.mybatis.model.TbInvMrAct;
import com.foya.dao.mybatis.model.TbInvMrD;
import com.foya.dao.mybatis.model.TbInvOnhand;
import com.foya.dao.mybatis.model.TbInvSrl;
import com.foya.dao.mybatis.model.TbInvTxn;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.exception.NomsException;
import com.foya.noms.dto.auth.RoleMenuFunDTO;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.dto.inv.TbInvMrDDTO;
import com.foya.noms.dto.inv.TbInvTxnDTO;
import com.foya.noms.dto.profile.TodoOrdersDTO;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.auth.RoleMenuFunService;
import com.foya.noms.service.inv.INV008Service;
import com.foya.noms.service.inv.INV012Service;
import com.foya.noms.service.inv.InvPublicUtilService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.IReportUtil;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/inv")
public class INV012Controller extends BaseController {

	@Inject
	private InvPublicUtilController invPublicUtilController;
	
	@Inject
	private INV012Service inv012Service;
	
	@Autowired
	private InvPublicUtilService invPublicUtilService;
	
	@Inject
	private PersonnelService personnelService;
	@Inject
	private INV008Service inv008Service;
	@Autowired
	private RoleMenuFunService roleMenuFunService;
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv012/init")
	public String init(HttpServletRequest request, Map<String, Object> model)
			throws Exception {
//		model.put("wareHouseSelect", invPublicUtilController
//				.initWareHouseDeptSelect(request, getLoginUser().getUsername()));
		
		if(!"HQ".equals(getLoginUser().getDomain().getID())){
			model.put("wareHouseSelect", invPublicUtilService.getWareHouseLikeDomain(getLoginUser().getDomain().getID().substring(0, 1)));
		}else{
			model.put("wareHouseSelect", invPublicUtilController.initWareHouseDeptSelect(request, getLoginUser().getUsername()));
		}
		model.put("personnelSelect", invPublicUtilController
				.initPersonnelDeptSelectlike(request, "4"));
		//model.put("deptSelect", invPublicUtilController.initDeptSelectByDomain(request, getLoginUser().getDomain().getID()));
		if (getLoginUser().getDomain().getID() != null && getLoginUser().getDomain().getID().equals("HQ")) {
			model.put("deptSelect", invPublicUtilService.getAllFourthDept());
		} else {
			model.put("deptSelect", invPublicUtilService.getAllDeptByDomain(getLoginUser().getDomain().getID()));
		}
		model.put("statusSelect",
				invPublicUtilController.getLookupByType("INV_MR_STATUS"));
		return "ajaxPage/inv/INV012";
	}

	/**
	 * 查詢
	 */
	@RequestMapping(value = "/inv012/search")
	@ResponseBody
	public JqGridData<TbInvMaterialOptDTO> search(
			@RequestParam("wareHouseSelect") String wareHouseSelect,
			@RequestParam("statusSelect") String statusSelect,
			@RequestParam("optId") String optId,
			@RequestParam("siteId") String siteId,
			@RequestParam("deptApplySelect") String deptApplySelect,
			@RequestParam("personnelSelect") String personnelSelect)
			throws Exception {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();

		dataObj.put("wareHouse", wareHouseSelect);
		dataObj.put("status", statusSelect);
		dataObj.put("optId", optId);
		if(!"".equals(siteId)){
			try{
				dataObj.put("siteId", invPublicUtilService.getSiteId(siteId).getSITE_ID());
			}catch(Exception e){dataObj.put("siteId","");}
		}		
		dataObj.put("deptApply", deptApplySelect);
		dataObj.put("personnel", personnelSelect);

		List<TbInvMaterialOptDTO> TbInvMaterialOptDTOList = inv012Service
				.search(dataObj);

		PageList<TbInvMaterialOptDTO> page = (PageList<TbInvMaterialOptDTO>) TbInvMaterialOptDTOList;
		return new JqGridData<TbInvMaterialOptDTO>(page.getPaginator()
				.getTotalCount(), TbInvMaterialOptDTOList);
	}

	/**
	 * 明細畫面 主檔資料
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv012/searchMasterDscr")
	public String searchMasterDscr(
			HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "optId", required = false) String optId,
			@RequestParam(value = "whIdDscr", required = false) String whIdDscr,
			@RequestParam(value = "deptIdDscr", required = false) String deptIdDscr,
			@RequestParam(value = "appUser", required = false) String appUser,
			@RequestParam(value = "appDate", required = false) String appDate,
			@RequestParam(value = "siteIdDscr", required = false) String siteIdDscr,
			@RequestParam(value = "apwhIdpDate", required = false) String whId,
			@RequestParam(value = "siteId", required = false) String siteId,
			@RequestParam(value = "statusDscr", required = false) String statusDscr)
			throws Exception {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();

		dataObj.put("optId", optId);
		dataObj.put("whIdDscr", whIdDscr);
		dataObj.put("deptIdDscr", deptIdDscr);
		dataObj.put("appUser", appUser);
		dataObj.put("appDate", appDate);
		dataObj.put("siteIdDscr", siteIdDscr);
		dataObj.put("whId", whId);
		dataObj.put("siteId", siteId);
		dataObj.put("statusDscr", statusDscr);
		model.put("masterDscr", dataObj);
		return "ajaxPage/inv/INV012D";
	}

	/**
	 * 明細畫面 明細資料
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv012/searchDtl")
	@ResponseBody
	public JqGridData<TbInvMrDDTO> searchDtl(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "optId", required = false) String optId)
			throws Exception {
		List<TbInvMrDDTO> TbInvMrDDTOList = inv012Service
				.searchUTbInvMrD(optId);

		PageList<TbInvMrDDTO> page = (PageList<TbInvMrDDTO>) TbInvMrDDTOList;
		return new JqGridData<TbInvMrDDTO>(page.getPaginator().getTotalCount(),
				TbInvMrDDTOList);
	}

	/**
	 * 明細畫面 歷程資料
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv012/searchDtlHistory")
	@ResponseBody
	public JqGridData<TbInvTxnDTO> searchDtlHistory(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "optId", required = false) String optId,
			@RequestParam(value = "matNo", required = false) String matNo,
			@RequestParam(value = "dtlSeq", required = false) Long dtlSeq)
			throws Exception {
		List<TbInvTxnDTO> TbInvTxnDTOList = null;
		TbInvTxnDTOList = inv012Service.searchUTbInvTxn(optId, matNo, dtlSeq);

		PageList<TbInvTxnDTO> page = (PageList<TbInvTxnDTO>) TbInvTxnDTOList;
		return new JqGridData<TbInvTxnDTO>(page.getPaginator().getTotalCount(),
				TbInvTxnDTOList);
	}

	/**
	 * 主畫面 結案 更新material_opt資料
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv012/close")
	@ResponseBody
	public BaseJasonObject<Object> close(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "optId") String optId,
			@RequestParam(value = "whId") String whId) throws Exception {
		BaseJasonObject<Object> json = null;
		try {
			// 更新Material Opt inv_inv inv_booking
			inv012Service.updateTbInvMaterialOpt(optId, "MR05", getLoginUser()
					.getUsername(), invPublicUtilService.getToday(),whId);
			json = new BaseJasonObject<Object>(true, "更新成功");
		} catch (Exception e) {
			json = new BaseJasonObject<Object>(false, "更新發料主檔錯誤"+e.fillInStackTrace());
		}
		return json;
	}

	/**
	 * 主畫面 結案更新預約主檔
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv012/updateBooking")
	@ResponseBody
	public BaseJasonObject<Object> updateBooking(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "optId") String optId,
			@RequestParam(value = "whId") String whId) throws Exception {
		BaseJasonObject<Object> json = null;
		// 取得matNo
		List<TbInvMrD> matNoList = inv012Service.searchTbInvMrDMatNo(optId);
		// 更新booking
		try {
			inv012Service.updateTbInvBooking(optId, matNoList, whId);
			json = new BaseJasonObject<Object>(true, "");
		} catch (Exception e) {
			json = new BaseJasonObject<Object>(false, "更新預約主檔錯誤");
		}
		return json;
	}

	/**
	 * 主畫面 結案更新庫存主檔
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv012/updateInvInv")
	@ResponseBody
	public BaseJasonObject<Object> updateInvInv(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "optId") String optId,
			@RequestParam(value = "whId") String whId) throws Exception {
		BaseJasonObject<Object> json = null;
		// 取得matNo
		List<TbInvMrD> matNoList = inv012Service.searchTbInvMrDMatNo(optId);
		// 更新invinv
		try {
			inv012Service.updateTbInvInv(matNoList, whId);
			json = new BaseJasonObject<Object>(true, "更新成功");
		} catch (Exception e) {
			json = new BaseJasonObject<Object>(false, "更新庫存主檔錯誤");
		}
		return json;
	}

	/**
	 * 發料單查詢畫面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv012/txnPage")
	public String txnPage(HttpServletRequest request, Map<String, Object> model)
			throws Exception {
//		model.put("wareHouseSelect", invPublicUtilController
//				.initWareHouseDeptSelect(request, getLoginUser().getUsername()));
		
		if(!"HQ".equals(getLoginUser().getDomain().getID())){
			model.put("wareHouseSelect", invPublicUtilService.getWareHouseLikeDomain(getLoginUser().getDomain().getID().substring(0, 1)));
		}else{
			model.put("wareHouseSelect", invPublicUtilController.initWareHouseDeptSelect(request, getLoginUser().getUsername()));
		}
		model.put("personnelSelect", invPublicUtilController
				.initPersonnelDeptSelectlike(request, "4"));
		model.put("deptSelect", invPublicUtilController.initDeptSelect(request));
		return "ajaxPage/inv/INV012L";
	}

	/**
	 * 發料單查詢畫面 主檔資料
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv012/searchTxnMaster")
	@ResponseBody
	public JqGridData<TbInvTxnDTO> searchTxnMaster(
			HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "txnNo", required = false) String txnNo,
			@RequestParam(value = "wareHouse", required = false) String wareHouse,
			@RequestParam(value = "dept", required = false) String dept,
			@RequestParam(value = "crStartDate", required = false) String crStartDateString,
			@RequestParam(value = "crEndDate", required = false) String crEndDateString,
			@RequestParam(value = "personnel", required = false) String personnel,
			@RequestParam(value = "txnType", required = false) String txnType,
			@RequestParam(value = "siteId", required = false) String siteId)
			throws Exception {

		Date crStartDate = null, crEndDate = null;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		if (crStartDateString != null && !"".equals(crStartDateString)){
			crStartDate = formatter.parse(crStartDateString + " 00:00");
		}
		if (crEndDateString != null && !"".equals(crEndDateString)){
			crEndDate = formatter.parse(crEndDateString+ " 23:59");
		}
		if(!"".equals(siteId)){
			try{
				siteId=invPublicUtilService.getSiteId(siteId).getSITE_ID();
			}catch(Exception e){siteId="";}	
		}
		List<TbInvTxnDTO> TbInvTxnDTOList = inv012Service.searchTbInvTxn(txnNo,
				wareHouse, personnel, crStartDate, crEndDate, txnType, siteId);

		PageList<TbInvTxnDTO> page = (PageList<TbInvTxnDTO>) TbInvTxnDTOList;
		return new JqGridData<TbInvTxnDTO>(page.getPaginator().getTotalCount(),
				TbInvTxnDTOList);
	}

	/**
	 * 發料單查詢畫面 明細資料
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv012/searchWithInvSrlByTxnNo")
	@ResponseBody
	public JqGridData<TbInvTxnDTO> searchWithInvSrlByTxnNo(
			HttpServletRequest request, Map<String, Object> model,
			@RequestParam(value = "txnNo", required = false) String txnNo,
			@RequestParam(value = "matNo", required = false) String matNo,
			@RequestParam(value = "dtlSeq", required = false) String dtlSeq)
			throws Exception {

		List<TbInvTxnDTO> TbInvTxnDTOList = inv012Service
				.searchWithInvSrlByTxnNo(txnNo, matNo, dtlSeq);
		PageList<TbInvTxnDTO> page = (PageList<TbInvTxnDTO>) TbInvTxnDTOList;
		return new JqGridData<TbInvTxnDTO>(page.getPaginator().getTotalCount(),
				TbInvTxnDTOList);
	}

	/**
	 * SP1查詢畫面 明細資料
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv012/searchWithTxnNoForSp1")
	@ResponseBody
	public JqGridData<TbInvTxnDTO> searchWithTxnNoForSp1(
			HttpServletRequest request, Map<String, Object> model,
			@RequestParam(value = "txnNo", required = false) String txnNo)
			throws Exception {
		List<TbInvTxnDTO> TbInvTxnDTOList = inv012Service
				.searchWithTxnNoForSp1(txnNo);
		PageList<TbInvTxnDTO> page = (PageList<TbInvTxnDTO>) TbInvTxnDTOList;
		return new JqGridData<TbInvTxnDTO>(page.getPaginator().getTotalCount(),
				TbInvTxnDTOList);
	}

	/**
	 * 領料簽收單
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv012/printSignPage")
	public void printSignPage(
			HttpServletResponse response,
			HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "txnNo", required = true) String txnNo,
			@RequestParam(value = "applicant", required = true) String applicant,
			@RequestParam(value = "sendDate", required = true) String sendDate,
			@RequestParam(value = "whName", required = false) String whName)
			throws Exception {

		List<TbInvTxnDTO> invTxnDtoList = inv012Service
				.searchInvTxnPrintPageDetail(txnNo);

		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		TbInvTxnDTO txnDto = null;
		for (int i = 0; i < invTxnDtoList.size(); i++) {
			TbInvTxnDTO dto = invTxnDtoList.get(i);
			if(i==0){
				txnDto = dto;
			}

			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("index", (i + 1));
			map.put("matNo", dto.getMat_no());
			map.put("materialsNumber", dto.getRef_act_no());
			map.put("platformNo", dto.getBtsSiteId());
			map.put("receiptNumber", dto.getOrder_no());
			map.put("matName", dto.getMatName());
			map.put("companyNumber", dto.getVenSn());
			map.put("labelNumber", dto.getTagNo());
			map.put("callInNumber", dto.getQty());

			listMap.add(map);
		}

		// 新增 Parameters
		HashMap<String, Object> parametersMap = new HashMap<String, Object>();
		parametersMap.put("exportNumberP", txnNo);
		parametersMap.put("exportDepot",
				new String(whName.getBytes("ISO-8859-1"), "UTF-8"));
		parametersMap.put("printUser", getLoginUser().getChName());
		parametersMap.put("printTime",
				new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date()));

		parametersMap.put("applicant",
					new String(applicant.getBytes("ISO-8859-1"), "UTF-8"));
		parametersMap.put("applyDept", txnDto.getDept_name());
		parametersMap.put("sendUser", txnDto.getAppUserName());
		if (sendDate == null || sendDate == "") {
			parametersMap.put("sendDate", new SimpleDateFormat(
					"yyyy/MM/dd HH:mm").format(new Date()));
		} else {
			parametersMap.put("sendDate", sendDate);
		}

		String fileName = "receipt_"
				+ new SimpleDateFormat("HH:mm:ss").format(new Date())+".pdf";
		
		IReportUtil reportUtil = new IReportUtil("INV012SP2",request);

		JasperPrint jasperPrint = JasperFillManager.fillReport(
				reportUtil.getJasperReport(), parametersMap,
				reportUtil.getJRDataSource(listMap));

		response.setHeader(reportUtil.getHeaderName(),
				reportUtil.getHeaderValue(fileName));

		response.setContentType("application/pdf");

		response.setCharacterEncoding("UTF-8");

		reportUtil.mountJRPdfExporter(response, jasperPrint);

		response.getOutputStream().close();
	}

	/**
	 * 發料初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv012/msPage")
	public String sp1(
			HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "wareHouse", required = false) String wareHouse,
			@RequestParam(value = "applicant", required = false) String applicant,
			@RequestParam(value = "wareHouseVal", required = false) String wareHouseVal,
			@RequestParam(value = "optId", required = false) String optId,
			@RequestParam(value = "workType", required = false) String workType)
			throws Exception {
//		model.put("wareHouseSelect", invPublicUtilController
//				.initWareHouseDeptSelect(request, getLoginUser().getUsername()));
		if(!"HQ".equals(getLoginUser().getDomain().getID())){
			model.put("wareHouseSelect", invPublicUtilService.getWareHouseLikeDomain(getLoginUser().getDomain().getID().substring(0, 1)));
		}else{
			model.put("wareHouseSelect", invPublicUtilController.initWareHouseDeptSelect(request, getLoginUser().getUsername()));
		}
		model.put("personnelSelect", invPublicUtilController
				.initPersonnelDeptSelectlike(request, "4"));
		if (getLoginUser().getDomain().getID() != null && getLoginUser().getDomain().getID().equals("HQ")) {
			model.put("deptSelectAll", invPublicUtilService.getAllFourthDept());
		} else {
			model.put("deptSelectAll", invPublicUtilService.getAllDeptByDomain(getLoginUser().getDomain().getID()));
		}
		model.put("deptSelect", invPublicUtilService.getAllDeptByDomain(getLoginUser().getDomain().getID()));
		if("S".equals(workType)){
			try{
				wareHouseVal=inv012Service.selectInvOpt(optId).get(0).getWH_ID();
				model.put("wareHouse" ,invPublicUtilService.getWareHouseByPkey(wareHouseVal).getWh_name());
				model.put("wareHouseVal" ,wareHouseVal);
			}catch(Exception e){}
		}else{
			model.put("wareHouse", wareHouse);
			model.put("wareHouseVal", wareHouseVal);
		}
		model.put("workType", workType);
		model.put("applicant", applicant);
		model.put("optId", optId);
		model.put("deptId", getLoginUser().getDeptId());
		model.put("loginUserId", getLoginUser().getUsername());
		model.put("loginUserName", getLoginUser().getChName());
		return "ajaxPage/inv/INV012M";
	}

	/**
	 * 發料功能 領料資料
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv012/searchTxnMrD")
	@ResponseBody
	public JqGridData<TbInvMrDDTO> searchTxnMrD(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "optId", required = false) String optId,
			@RequestParam(value = "whId", required = false) String whId,
			@RequestParam(value = "workType", required = false) String workType)
			throws Exception {
		List<String> optIdList = new ArrayList<String>();
		if("S".equals(workType)){
			optIdList.add(optId);
		}else{
			String[] optIdStr = optId.split("&");
			for (int g=0; g<optIdStr.length; g++) {
				String[] optStr = optIdStr[g].split("="); 
				optIdList.add(optStr[1]);
			}
		}
		List<TbInvMrDDTO> TbInvMrDList = inv012Service.searchMrD(optIdList,
				whId);
		PageList<TbInvMrDDTO> page = (PageList<TbInvMrDDTO>) TbInvMrDList;
		return new JqGridData<TbInvMrDDTO>(page.getPaginator().getTotalCount(),
				TbInvMrDList);
	}

	/**
	 * 退料站台畫面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inv012/mrLoad")
	public String mrLoad(
			HttpServletRequest request,
			Map<String, Object> model,
			TbInvTxnDTO txn,
			@RequestParam(value = "matNoList", required = false) String matNoList)
			throws Exception {
		String[] matNostr = matNoList.split("&");
		List<Map<String, Object>> matList = new ArrayList<Map<String, Object>>();
		Map<String, Object> mat = new HashMap<String, Object>();
		for (int g = 0; g < matNostr.length; g++) {
			mat = new HashMap<String, Object>();
			String[] matStr = matNostr[g].split("=");
			mat.put("matNo", matStr[1]);
			matList.add(mat);
		}
		model.put("matList", matList);
		model.put("master", txn);
		return "ajaxPage/inv/INV012SP1";
	}
	/**
	 * 產生取品名
	 */
	@RequestMapping(value = "/inv012/getMatName")
	@ResponseBody
	public BaseJasonObject<Object> getMatName(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "matNo", required = false) String matNo) throws Exception {
		
		String matName = "";
		try {
			TbInvMaterial mat = inv012Service.getMatName(matNo);
			matName=mat.getMat_name();
		} catch (NullPointerException e) {}
		return new BaseJasonObject<Object>(true, matName);
	}
	/**
	 * 產生發料單號
	 */
	@RequestMapping(value = "/inv012/genTxnNo")
	@ResponseBody
	public BaseJasonObject<Object> genTxnNo(HttpServletRequest request,
			Map<String, Object> model) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String initTxnNo = sdf.format(new Date()), txnNo = "";
		try {
			txnNo = inv012Service.selectTxnNoToday(initTxnNo, getLoginUser().getUsername());
		} catch (NullPointerException e) {
			txnNo = "產生單號為空值";
		}
		return new BaseJasonObject<Object>(true, txnNo);
	}
	/**
	 * 發料動作合併
	 */
	@RequestMapping(value = "/inv012/saveToTable")
	@ResponseBody
	public BaseJasonObject<Object> saveToTable(@RequestParam("msArray") String msArray) {
		Date today=invPublicUtilService.getToday();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String initTxnNo = sdf.format(new Date()), txnNo = "";
		try {
			txnNo = inv012Service.selectTxnNoToday(initTxnNo, getLoginUser().getUsername());
		} catch (NullPointerException e) {
			txnNo = "產生單號為空值";
		}
			try{
				inv012Service.insertToTable(msArray, today, txnNo);
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
				return new BaseJasonObject<Object>(false,throwable.getMessage());
			}
			catch(Exception e){
				log.error(e.getMessage());
				e.printStackTrace();
				return new BaseJasonObject<Object>(false,e.getMessage());
			}
			return new BaseJasonObject<Object>(true,txnNo);
	}
	/**
	 * 更新OPT
	 */
	@RequestMapping(value = "/inv012/updateOpt")
	@ResponseBody
	public BaseJasonObject<Object> updateOpt(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "optId", required = false) String optId,
			@RequestParam(value = "whId", required = false) String whId)
			throws Exception {
		String status = "發料完成", statusForMrIng = "發料中";
		TbInvMaterialOpt record = new TbInvMaterialOpt();
		TbInvMaterialOpt recordMring = new TbInvMaterialOpt();
		try {
			List<TbSysLookup> list = invPublicUtilController
					.getLookupByTypeName("INV_MR_STATUS", status);
			TbSysLookup lookupData = (TbSysLookup) list.get(0);
			record.setSTATUS(lookupData.getCODE());
		} catch (IndexOutOfBoundsException e) {
		}// 避免領料數大於的情形
		catch (NullPointerException e) {
		}
		try {
			List<TbSysLookup> list = invPublicUtilController
					.getLookupByTypeName("INV_MR_STATUS", statusForMrIng);
			TbSysLookup lookupData = (TbSysLookup) list.get(0);
			recordMring.setSTATUS(lookupData.getCODE());
		} catch (IndexOutOfBoundsException e) {
		}// 避免領料數大於的情形
		catch (NullPointerException e) {
		}
		String[] optIdStr = optId.split("&");
		Set<String> optIdList = new HashSet<String>();
		for (int g = 0; g < optIdStr.length; g++) {
			String[] optStr = optIdStr[g].split("=");
			optIdList.add(optStr[1]);
		}
		Iterator<String> it = optIdList.iterator();
		while (it.hasNext()) {
			record.setOPT_ID(it.next());
			record.setOPT_TYPE("MRQ");
			record.setMD_TIME(invPublicUtilService.getToday());
			record.setMD_USER(getLoginUser().getUsername());
			inv012Service.updateOpt(record); // 先更新所有的optId狀態成發料完成
		}
		// 取得此optId清單所有的資料
		List<TbInvMrDDTO> mrDList = inv012Service.searchMrDOptId(optIdList);
		for (int i = 0; i < mrDList.size(); i++) {
			HashMap<String, Object> dataObj = new HashMap<String, Object>();
			TbInvMrDDTO mrD = (TbInvMrDDTO) mrDList.get(i);
			dataObj.put("txnType", "1");
			dataObj.put("optId", mrD.getOPT_ID());
			dataObj.put("matNo", mrD.getMAT_NO());
			dataObj.put("qty", mrD.getQTY());
			List<TbInvTxnDTO> list = inv012Service.searchLessQty(dataObj);
			try {
				for(int iTxn=0;iTxn<list.size();iTxn++){
					TbInvTxnDTO dto=list.get(iTxn);
					if(mrD.getOPT_ID().equals(dto.getRef_act_no())){
						if(mrD.getQTY()>dto.getQty()){
							recordMring.setOPT_ID(mrD.getOPT_ID());
							recordMring.setOPT_TYPE("MRQ");
							recordMring.setMD_TIME(invPublicUtilService.getToday());
							recordMring.setMD_USER(getLoginUser().getUsername());
							inv012Service.updateOpt(recordMring);
						}
					}
				}	
			} catch (IndexOutOfBoundsException e) {
			}// 如果無資料
			catch (NullPointerException e) {
			}// 如果無資料
		}
		return new BaseJasonObject<Object>(true, "");
	}

	/**
	 * 更新Booking
	 */
	@RequestMapping(value = "/inv012/updateBookingMinusQty")
	@ResponseBody
	public BaseJasonObject<Object> updateBookingMinusQty(
			HttpServletRequest request, Map<String, Object> model,
			@RequestParam(value = "optId", required = false) String optId,
			@RequestParam(value = "matNo", required = false) String matNo,
			@RequestParam(value = "qty", required = false) String qty,
			@RequestParam(value = "whId", required = false) String whId)
			throws Exception {

		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("optId", optId);
		dataObj.put("matNo", matNo);
		try {
			dataObj.put("qty", Integer.valueOf(qty));
		} catch (NumberFormatException e) {
			dataObj.put("qty", 0);
		} catch (NullPointerException e) {
			dataObj.put("qty", 0);
		}
		dataObj.put("whId", whId);
		String.valueOf(inv012Service.updateBooking(dataObj));
		return new BaseJasonObject<Object>(true, "");
	}

	/**
	 * 更新InvInv
	 */
	@RequestMapping(value = "/inv012/updateInvInvMinusQty")
	@ResponseBody
	public BaseJasonObject<Object> updateInvInvMinusQty(
			HttpServletRequest request, Map<String, Object> model,
			@RequestParam(value = "optId", required = false) String optId,
			@RequestParam(value = "matNo", required = false) String matNo,
			@RequestParam(value = "qty", required = false) String qty,
			@RequestParam(value = "whId", required = false) String whId)
			throws Exception {

		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("matNo", matNo);
		try {
			dataObj.put("qty", Integer.valueOf(qty));
		} catch (NumberFormatException e) {
			dataObj.put("qty", 0);
		} catch (NullPointerException e) {
			dataObj.put("qty", 0);
		}
		dataObj.put("whId", whId);
		String.valueOf(inv012Service.updateInvInv(dataObj));
		return new BaseJasonObject<Object>(true, "");
	}

	/**
	 * 寫入InvTxn
	 */
	@RequestMapping(value = "/inv012/insertTxn")
	@ResponseBody
	public BaseJasonObject<Object> insertTxn(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "txnNo", required = false) String txnNo,
			@RequestParam(value = "optId", required = false) String optId,
			@RequestParam(value = "matNo", required = false) String matNo,
			@RequestParam(value = "qty", required = false) String qty,
			@RequestParam(value = "whId", required = false) String whId,
			@RequestParam(value = "tagNo", required = false) String tagNo,
			@RequestParam(value = "faNo", required = false) String faNo,
			@RequestParam(value = "srlNo", required = false) String srlNo,
			@RequestParam(value = "crTime", required = false) Date crTime,
			@RequestParam(value = "dtlSeq", required = false) String dtlSeq,
			@RequestParam(value = "rtUser", required = false) String rtUser,
			@RequestParam(value = "rcvUser", required = false) String rcvUser)
			throws Exception {
		TbInvTxn record = new TbInvTxn();
		record.setTxn_no(txnNo);
		record.setTxn_type(new Byte("1"));
		record.setRef_act_no(optId);
		record.setWh_id(whId);
		record.setMat_no(matNo);
		List<TbSysLookup> list = invPublicUtilController.getLookupByTypeName(
				"MAT_STATUS", "可用品");// 可用品
		TbSysLookup lookupData = (TbSysLookup) list.get(0);
		record.setMat_status(new Byte(lookupData.getCODE()));
		try {
			record.setQty(0 - Integer.valueOf(qty));
		} catch (NumberFormatException e) {
			record.setQty(0);
		} catch (NullPointerException e) {
			record.setQty(0);
		}
		try {
			record.setFa_no(faNo);
		} catch (NullPointerException e) {
		}
		try {
			record.setSrl_no(Long.valueOf(srlNo));
		} catch (NumberFormatException e) {
		} catch (NullPointerException e) {
		}
		List<TbInvMaterialOpt> optObjectList = inv012Service
				.selectInvOpt(optId);
		TbInvMaterialOpt optObject = (TbInvMaterialOpt) optObjectList.get(0);
		record.setSite_id(optObject.getSITE_ID());
		record.setOrder_no(optObject.getORDER_ID());
		record.setCr_user(rtUser);// 提高自由度
									// 改由畫面選擇String.valueOf(getLoginUser().getUsername())
		record.setCr_time(crTime);
		record.setDtl_seq(Long.valueOf(dtlSeq));
		record.setMro_rt_user(rtUser);
		record.setSend_rcv_user(rcvUser);
		String.valueOf(inv012Service.insertInvTxn(record));
		return new BaseJasonObject<Object>(true, "");
	}

	/**
	 * 寫入InvOnhand
	 */
	@RequestMapping(value = "/inv012/insertOnHand")
	@ResponseBody
	public BaseJasonObject<Object> insertOnHand(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "txnNo", required = false) String txnNo,
			@RequestParam(value = "optId", required = false) String optId,
			@RequestParam(value = "matNo", required = false) String matNo,
			@RequestParam(value = "qty", required = false) String qty,
			@RequestParam(value = "whId", required = false) String whId,
			@RequestParam(value = "tagNo", required = false) String tagNo,
			@RequestParam(value = "faNo", required = false) String faNo,
			@RequestParam(value = "srlNo", required = false) String srlNo,
			@RequestParam(value = "crTime", required = false) Date crTime)
			throws Exception {
		TbInvOnhand record = new TbInvOnhand();
		record.setTxn_no(txnNo);
		record.setTxn_type(new Byte("1"));
		record.setWh_id(whId);
		record.setMat_no(matNo);

		List<TbSysLookup> list = invPublicUtilController.getLookupByTypeName(
				"MAT_STATUS", "可用品");// 可用品
		TbSysLookup lookupData = (TbSysLookup) list.get(0);
		record.setMat_status(new Byte(lookupData.getCODE()));
		try {
			record.setQty(Math.abs(Integer.valueOf(qty)));
			record.setOnhand_qty(Math.abs(Integer.valueOf(qty)));
		} catch (NumberFormatException e) {
			record.setQty(0);
			record.setOnhand_qty(0);
		} catch (NullPointerException e) {
			record.setQty(0);
			record.setOnhand_qty(0);
		}
		try {
			record.setFa_no(faNo);
		} catch (NullPointerException e) {
		}
		try {
			record.setSrl_no(Long.valueOf(srlNo));
		} catch (NumberFormatException e) {
		} catch (NullPointerException e) {
		}
		List<TbInvMaterialOpt> optObjectList = inv012Service
				.selectInvOpt(optId);
		TbInvMaterialOpt optObject = (TbInvMaterialOpt) optObjectList.get(0);
		record.setSite_id(optObject.getSITE_ID());
		record.setOrder_no(optObject.getORDER_ID());
		record.setCr_user(getLoginUser().getUsername());
		record.setCr_time(crTime);
		record.setMd_user(getLoginUser().getUsername());
		record.setMd_time(crTime);
		String.valueOf(inv012Service.insertInvOnhand(record));
		return new BaseJasonObject<Object>(true, "");
	}

	/**
	 * 寫入InvMrAct
	 */
	@RequestMapping(value = "/inv012/insertMrAct")
	@ResponseBody
	public BaseJasonObject<Object> insertMrAct(
			HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "txnNo", required = false) String txnNo,
			@RequestParam(value = "optId", required = false) String optId,
			@RequestParam(value = "matNo", required = false) String matNo,
			@RequestParam(value = "qty", required = false) String qty,
			@RequestParam(value = "tagNo", required = false) String tagNo,
			@RequestParam(value = "srlNo", required = false) String srlNo,
			@RequestParam(value = "crTime", required = false) Date crTime,
			@RequestParam(value = "prPersonnel", required = false) String prPersonnel,
			@RequestParam(value = "msPersonnel", required = false) String msPersonnel)
			throws Exception {

		BaseJasonObject<Object> json = null;
		TbInvMrAct record = new TbInvMrAct();
		List<TbInvMrD> tbInvMrDObjectList = inv012Service
				.searchTbInvMrDMatNo(optId);
		List<TbInvTxn> TbInvTxnObjectList = inv012Service.selectInvTxn(txnNo);
		TbInvTxn tbInvTxn = (TbInvTxn) TbInvTxnObjectList.get(0);
		for (int i = 0; i < tbInvMrDObjectList.size(); i++) {
			TbInvMrD tbInvMrD = tbInvMrDObjectList.get(i);
			record.setDTL_SEQ(tbInvMrD.getDTL_SEQ());
			record.setTXN_NO(tbInvTxn.getTxn_no());
			record.setTXN_NO(String.valueOf(tbInvTxn.getInv_txn_no()));
			record.setOPT_ID(optId);
			record.setMAT_NO(matNo);
			try {
				record.setSRL_NO(Long.valueOf(srlNo));
			} catch (NumberFormatException e) {
			} catch (NullPointerException e) {
			}
			try {
				record.setQTY(Integer.valueOf(qty));
			} catch (NumberFormatException e) {
				record.setQTY(0);
			} catch (NullPointerException e) {
				record.setQTY(0);
			}
			record.setCR_USER(getLoginUser().getUsername());
			record.setCR_TIME(crTime);
			record.setRCV_USER(prPersonnel);
			record.setRLS_USER(msPersonnel);
			try {
				if(!"".equals(srlNo)){
					inv012Service.updateSrl(Long.valueOf(srlNo));
				}
				json = new BaseJasonObject<Object>(true, "");
			} catch (Exception e) {
				json = new BaseJasonObject<Object>(false, "更新TB_INV_SRL錯誤");
				break;
			}
			try {
				inv012Service.insertInvMrAct(record);
				json = new BaseJasonObject<Object>(true, "");
			} catch (Exception e) {
				json = new BaseJasonObject<Object>(false, "寫入TB_INV_ACT錯誤");
				break;
			}
		}
		return json;
	}
	/**
	 * 取物料狀態
	 */
	@RequestMapping(value = "/inv012/getTxnMatNoStatus")
	@ResponseBody
	public BaseJasonObject<TbInvMaterial> getTxnMatNoStatus(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "matNo", required = false) String matNo) throws Exception {
		return new BaseJasonObject<TbInvMaterial>(inv012Service.getMaterial(matNo),
				AJAX_SUCCESS);
	}
	/**
	 * 取貼標號碼
	 */
	@RequestMapping(value = "/inv012/getTxnMatNo")
	@ResponseBody
	public BaseJasonObject<TbInvInv> getTagNo(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "whId", required = false) String whId) throws Exception {
		return new BaseJasonObject<>(invPublicUtilService.getInvMaterial(whId),
				AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 取實發料號
	 */
	@RequestMapping(value = "/inv012/getTagNo")
	@ResponseBody
	public BaseJasonObject<TbInvSrl> getTagNo(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "matNo", required = false) String matNo,
			@RequestParam(value = "whId", required = false) String whId)
			throws Exception {
		return new BaseJasonObject<>(inv012Service.selectTagNo(matNo, whId),
				AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 取資產編號
	 */
	@RequestMapping(value = "/inv012/getFaNo")
	@ResponseBody
	public BaseJasonObject<TbInvSrl> getFaNo(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "matNo", required = false) String matNo,
			@RequestParam(value = "whId", required = false) String whId,
			@RequestParam(value = "tagNo", required = false) String tagNo,
			@RequestParam(value = "venSn", required = false) String venSn)
			throws Exception {
		return new BaseJasonObject<>(inv012Service.selectFaNo(matNo, whId,
				tagNo, venSn), AJAX_SUCCESS, AJAX_EMPTY);
	}

	/*****************************************************************************/
	/**
	 * 發料單查詢畫面 明細資料
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param model
	 *            Map<String, Object>
	 * @return JqGridData<TbInvTxnDTO>
	 */
	@RequestMapping(value = "/inv012L/searchTxnDetail")
	@ResponseBody
	public JqGridData<TbInvTxnDTO> searchTxnDetail(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "txnNo", required = false) String txnNo) {

		List<TbInvTxnDTO> TbInvTxnDTOList = inv012Service
				.searchTxnDetailByNo(txnNo);

		PageList<TbInvTxnDTO> page = (PageList<TbInvTxnDTO>) TbInvTxnDTOList;
		return new JqGridData<TbInvTxnDTO>(page.getPaginator().getTotalCount(),
				TbInvTxnDTOList);
	}
	

	/**
	 * 供待辦事項呼叫
	 */
	@RequestMapping(value = "/inv/inv012/getTodoOrders")
	@ResponseBody
	public List<TodoOrdersDTO> getTodoOrders() {
		List<TodoOrdersDTO> todoDtoList = new ArrayList<TodoOrdersDTO>();
		List<TbAuthMenu> menuList = inv008Service.getMenuId("/inv/inv012/init");
		int menuId = 0;
		if (menuList.size() > 0) {
			menuId = menuList.get(0).getMENU_ID().intValue();
		}

		List<RoleMenuFunDTO> list = roleMenuFunService.getFuncitonByPsnAndMenuId(getLoginUser().getUserId(), menuId);
		boolean butCompetence = false;

		for (RoleMenuFunDTO dto : list) {

			if (dto.getBtnName().equals("發料")) {
				butCompetence = true;
			}
		}
		Set<String> statusList = new HashSet<String>();
		statusList.add("MR02");
		statusList.add("MR04");
		
		if(butCompetence){		
			List<TbInvMaterialOptDTO> optList = invPublicUtilService.searchTodo("MRQ", statusList, getLoginUser().getUsername(),"INV_MR_STATUS");
			for (TbInvMaterialOptDTO dto : optList) {
				// 取的姓名
				List<TbAuthPersonnel> personnelList = personnelService
						.searchPsnByPsnNo(dto.getCR_USER());
				TodoOrdersDTO ordersDto = new TodoOrdersDTO();
				ordersDto.setOrderId(dto.getOPT_ID());
				ordersDto.setOrderTypeDesc("發料");
				ordersDto.setOrderInfo(dto.getBTS_SITE_ID());
				ordersDto.setAppUser(dto.getCR_USER());
				ordersDto.setAppUserName(personnelList.size() > 0 ? personnelList
						.get(0).getCHN_NM() : "");
				ordersDto.setAppTime(new Timestamp(dto.getCR_TIME().getTime()));
				ordersDto.setOrderStatus(String.valueOf(dto.getSTATUS()));
				ordersDto.setOrderStatusDesc(dto.getStatusDscr());
				ordersDto.setLinkURL("/inv/inv012/msPage?workType=S&optId="+dto.getOPT_ID());			
				todoDtoList.add(ordersDto);
			}
		}
		return todoDtoList;
	}	
	
	/**
	 * 取得該料號庫存數
	 */
	@RequestMapping(value = "/inv012/getStdQtyByMatNo")
	@ResponseBody
	public BaseJasonObject<TbInvInvDTO> getStdQtyByMatNo(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "matNo", required = false) String matNo,
			@RequestParam(value = "whId", required = false) String whId)
			throws Exception {
		return new BaseJasonObject<>(inv012Service.getStdQtyByMatNo(matNo, whId), AJAX_SUCCESS);
	}
}
