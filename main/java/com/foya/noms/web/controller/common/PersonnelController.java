package com.foya.noms.web.controller.common;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.exception.DataExistsException;
import com.foya.noms.dto.auth.PersonnelDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.common.PoiService;
import com.foya.noms.service.org.ORG002Service;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
public class PersonnelController extends BaseController {

	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private PoiService poiService;
	@Autowired
	private ORG002Service org002Service;

	@RequestMapping(value = "/common/personnel/initLoad")
	public String initPersonnelPage(HttpServletRequest request, Map<String, Object> model,
			PersonnelDTO person) {
		model.put("person", person);
		return "/ajaxPage/common/PersonnelM";
	}

	@RequestMapping(value = "/common/personnel/search")
	@ResponseBody
	public JqGridData<TbAuthPersonnel> searchPersonnel(@RequestParam("psnNo") String psnNo,
			@RequestParam("chnName") String chnName, @RequestParam("email") String email) {
		List<TbAuthPersonnel> psnList = personnelService.searchPsnByNoChnNameEmail(psnNo, chnName,
				email);
		PageList<TbAuthPersonnel> page = (PageList<TbAuthPersonnel>) psnList;
		return new JqGridData<>(page.getPaginator().getTotalCount(), psnList);
	}

	/**
	 * 匯入匯出主頁
	 * 
	 * @return
	 */
	@RequestMapping(value = "/excelUpDown/")
	public String excelUpDown() {
		return "excelReport";
	}

	/**
	 * 匯出空報表
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/poi/exportPSNEmpty", method = RequestMethod.GET)
	public void getXLSEmpty(HttpServletResponse response) {
		personnelService.exportPSNXLSEmpty(response, "sheetName", "EmptyReport.xls", 0, 0, 15);
	}

	/**
	 * 匯出Personnel excel報表
	 * 
	 * @param response
	 */
	@RequestMapping(value = "/poi/exportPSNExcel", method = RequestMethod.GET)
	public void exportPSNXLS(HttpServletResponse response) {
		personnelService.exportPSNXLS(response, "Personnel", "PersonnelReport.xls", 0, 0, 15);
	}

	/**
	 * 匯入Personnel excel報表
	 * 
	 * @param mutipartRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/poi/readPerson", method = RequestMethod.POST)
	@ResponseBody
	public BaseJasonObject<TbAuthPersonnel> handleUploadProcess(
			MultipartHttpServletRequest mutipartRequest) throws Exception {
		BaseJasonObject<TbAuthPersonnel> result = null;
		boolean isSuccess = true;
		// 讀取報表
		for (Iterator<String> itr = mutipartRequest.getFileNames(); itr.hasNext();) {
			MultipartFile file = mutipartRequest.getFile(itr.next());
			result = new PersonnelService().readReport(file.getInputStream());
		}
		// 是否匯入DB
		if (result.getMapList().get("error").size() == 0) {
			personnelService.insertTbAuthPersonnelDB(result.getMapList().get("normal"));
		} else {
			isSuccess = false;
		}
		return new BaseJasonObject<>(isSuccess, isSuccess ? AJAX_SUCCESS
				: "部分資料有誤，請調整後重新上傳!\n錯誤訊息: " + result.getMsg());
	}
	
	/**
	 * 使用者維護查詢頁
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/auth/personnel/initial")
	public String systemMenuInit(HttpServletRequest request,Map<String, Object> model) {
		return "ajaxPage/auth/AUTH001";
	}
	
	/**
	 * 使用者維護查詢結果(分頁用)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/autn/personnel/search")
	@ResponseBody
	public JqGridData<PersonnelDTO> searchPersonnel(HttpServletRequest request) {	
		String psnNO = request.getParameter("psn_NO");
		String engNM = request.getParameter("eng_NM");
		String email = request.getParameter("e_MAIL");
		List<PersonnelDTO> rsList = personnelService.searchPsnByCondition(psnNO, engNM, email);
		 
		PageList<PersonnelDTO> page= (PageList<PersonnelDTO>) rsList;		
		return new JqGridData<>(page.getPaginator().getTotalCount(), rsList);
	}
	
	/**
	 * 使用者維護_新增編輯頁面
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/auth/personnel/editAddPage")
	public String editAddPagePSN(HttpServletRequest request, Map<String, Object> modelMap) {
		Map<String, String> deptMap = new LinkedHashMap<>();
		List<TbOrgDept> deptRows = org002Service.selectAllOrgDept(new TbOrgDeptExample());
		for (TbOrgDept row : deptRows) {
			deptMap.put(row.getDEPT_ID(), row.getDEPT_NAME());
		}		
		modelMap.put("orgDept",deptMap); // 所有部門資訊
		
//		Map<String, String> posMap = new LinkedHashMap<>();
//		TbOrgPositionExample example = new TbOrgPositionExample();
//		example.createCriteria().andPOS_TYPEEqualTo("N");
//		List<TbOrgPosition> posRows = org002Service.selectAllOrgPosition(example);
//		for (TbOrgPosition row : posRows) {
//			posMap.put(row.getPOS_ID(), row.getPOS_NAME());
//		}		
//		modelMap.put("orgPos",posMap); // 所有職稱資訊
		
		TbAuthPersonnel personnel = new TbAuthPersonnel();
		if("add".equals(request.getParameter("btnType"))){		
			modelMap.put("personnel", personnel);
		}
		else if("edit".equals(request.getParameter("btnType"))||"view".equals(request.getParameter("btnType"))){
			personnel = personnelService.findPersonnelByPsnId(BigDecimal.valueOf(Long.parseLong(request.getParameter("psn_ID"))));

			String dateFormat = null;
			if (personnel.getEXPIRE_DATE()!= null ){
				SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
				dateFormat = sdFormat.format(personnel.getEXPIRE_DATE());				
			}
			modelMap.put("psnExpireDATE", dateFormat);
			modelMap.put("personnel", personnel);
		}
			
		modelMap.put("btnType", request.getParameter("btnType"));
		return "ajaxPage/auth/AUTH001M";
	}
	
	/**
	 * 用部門ID查詢POS_TYPE='N'相對應的職務
	 * @param deptID
	 * @return
	 */
	@RequestMapping(value = "/auth/getDeptPos")
	@ResponseBody
	public BaseJasonObject<DeptDTO> getAreas(@RequestParam(value="deptID") String deptID) {
		return new BaseJasonObject<>(personnelService.selecPosByDept(deptID), AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 使用者帳號儲存
	 * @param personnel
	 * @param btnType
	 * @return
	 */
	@RequestMapping(value = "/auth/personnel/Save")
	public @ResponseBody BaseJasonObject<Object> savePersonnel(TbAuthPersonnel personnel,@RequestParam("btnType")String btnType) {
		try {
			// 新增
			if ("add".equals(btnType)) {
				saveNewPersonnel(personnel);
				return new BaseJasonObject<>(true, getMessageDetail("msg.add.success"));
			} else { // 修改
				saveUpdatePersonnel(personnel);
				return new BaseJasonObject<>(true, getMessageDetail("msg.update.success"));
			}
		} catch (DataExistsException ex) {
			return new BaseJasonObject<>(false, ex.getMessage());
		}
	}
	
	/**
	 * 使用者帳號新增
	 * @param personnel
	 */
	public void saveNewPersonnel(TbAuthPersonnel personnel) {
		personnelService.saveNewPersonnel(personnel);
	}
	
	/**
	 * 使用者帳號修改
	 * @param personnel
	 */
	public void saveUpdatePersonnel(TbAuthPersonnel personnel){
		personnelService.saveUpdatePersonnel(personnel);
	}
	
}
