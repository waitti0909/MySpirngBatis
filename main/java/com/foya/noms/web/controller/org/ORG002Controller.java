package com.foya.noms.web.controller.org;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbOrgDeptPos;
import com.foya.dao.mybatis.model.TbOrgPosition;
import com.foya.dao.mybatis.model.TbOrgPositionExample;
import com.foya.dao.mybatis.model.TbOrgPsnPos;
import com.foya.exception.DataExistsException;
import com.foya.exception.NomsException;
import com.foya.noms.dto.JsTreeDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.dto.org.PsnPosDTO;
import com.foya.noms.service.org.ORG002Service;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/org")
public class ORG002Controller extends BaseController {

	@Inject
	private ORG002Service org002Service;

	
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dept/main")
	public String deptInit(HttpServletRequest request, Map<String, Object> model) {
		return "ajaxPage/org/ORG002";
	}

	/**
	 * 查詢頁面
	 */
	@RequestMapping(value = "/dept/search")
	@ResponseBody
	public JqGridData<DeptDTO> searchDeptByIdName(
			@RequestParam("deptId") String deptId,
			@RequestParam("deptName") String deptName) {
		List<DeptDTO> deptList = org002Service.searchDeptByIdName(deptId,
				deptName);
		PageList<DeptDTO> page = (PageList<DeptDTO>) deptList;
		return new JqGridData<>(page.getPaginator().getTotalCount(), deptList);
	}

	/**
	 * 新增/修改頁
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dept/modify")
	public String deptModify(@RequestParam(value = "showType") String showType, @RequestParam(value = "deptId") String deptId,HttpServletRequest request,
			Map<String, Object> model) {
		model.put("ckdeptId", deptId);	
		model.put("showType", showType);
		return "/ajaxPage/org/ORG002M";
	}

	/**
	 * 部門職稱的頁面
	 * 
	 * @param request
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/dept/deptPos")
	@ResponseBody
	public Map<String, Object> deptPosPage(HttpServletRequest request,
			@RequestParam("deptId") String deptId) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<TbOrgDept> deptList=org002Service.searchDeptByDeptId(deptId);
		TbOrgDept dept = org002Service.getByPk(deptId);
		
//		List<TbOrgPosition> posList = org001Service
//				.searchAllPosition();
		String manager = "";
		if(dept != null && dept.getMANAGER()!=null && dept.getMANAGER().split("__").length>1){
			manager = dept.getMANAGER().split("__")[1];
		}
		model.put("deptId", deptList.get(0).getDEPT_ID());
		model.put("deptName", deptList.get(0).getDEPT_NAME());
//		model.put("posList", posList);
		model.put("manager", manager);
		return model;
	}

	/**
	 * 重新產生表格
	 * 
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/dept/deptPos/deptPosTable")
	@ResponseBody
	public BaseJasonObject<DeptDTO> createTable(
			@RequestParam("deptId") String deptId, Map<String, Object> model) {
		List<DeptDTO> deptList = org002Service.searchPositionByDeptId(deptId);
		model.put("deptList", deptList);
		return new BaseJasonObject<>(deptList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 導向新增頁面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dept/deptPos/add")
	@ResponseBody
	public BaseJasonObject<Object> addNewDeptPosPage() {
		return new BaseJasonObject<>(new HashMap<String, Object>(),
				AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 新增部門職稱並儲存
	 * 
	 * @param dptPos
	 * @return
	 */
	@RequestMapping(value = "/dept/deptPos/addSave")
	@ResponseBody
	public boolean saveNewDeptPos(TbOrgDeptPos dptPos) {
		boolean result = false;
		try {
			result = org002Service.saveNewDeptPos(dptPos);
		} catch (DataExistsException ex) {

		}
		return result;
	}

	/**
	 * 導向修改頁面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dept/deptPos/updatePage")
	@ResponseBody
	public BaseJasonObject<Object> updateDeptPosPage(
			@RequestParam("posId") String posId,
			@RequestParam("deptId") String deptId) {
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		TbOrgDeptPos deptPos = org002Service.serachDeptPosByDeptPosId(deptId
				+ "__" + posId);
		model.put(posId, deptPos.getPARENT_DEPT_POS_ID());
		model.put("pos_type", deptPos.getPARENT_DEPT_POS_ID());
		return new BaseJasonObject<Object>(deptPos, AJAX_SUCCESS);
	}

	/**
	 * 修改部門職稱並儲存
	 * 
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/dept/deptPos/updateSave")
	@ResponseBody
	public Map<String, Object> saveUpdateDeptPos(TbOrgDeptPos dptPos,
			@RequestParam("oldDeptPosId") String oldDeptPosId) {
		Map<String, Object> model = new HashMap<String, Object>();
		boolean result = false;
		dptPos.setDEPT_POS_ID(dptPos.getDEPT_ID() + "__" + dptPos.getPOS_ID());
		try {
			result = org002Service.saveUpdateDeptPos(dptPos, oldDeptPosId);
			model.put("result", result);
			model.put("podId", dptPos.getPOS_ID());
		} catch (DataExistsException ex) {
			model.put("result", result);
		}

		return model;
	}

	/**
	 * 刪除部門職稱
	 * 
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/dept/deptPos/delete")
	@ResponseBody
	public Map<String ,Object> deleteByDeptPos(
			@RequestParam("deptPosId") String deptPosId) {
		Map<String ,Object> model = new HashMap<String ,Object>();
		boolean result =false;
		try{
			result = org002Service.deleteByDeptPos(deptPosId);
			model.put("result", result);
		}catch(NomsException ex){
			model.put("result", result);
			model.put("message", getMessageDetail(ex.getMessage()));
		}
		return model;
	}

	/**
	 * 修改部門主管
	 * 
	 * @param deptId
	 * @param dpetPosId
	 * @return
	 */
	@RequestMapping(value = "/dept/deptPos/updateManager")
	@ResponseBody
	public Map<String, Object> saveUpdateDeptManager(
			@RequestParam("deptId") String deptId,
			@RequestParam("deptPosId") String deptPosId) {
		Map<String, Object> model = new HashMap<String, Object>();
		if(deptPosId == ""){
			deptPosId = null;
		}
		boolean result = org002Service.saveUpdateDeptManager(deptId, deptPosId);
		TbOrgDept dept = org002Service.getByPk(deptId);
		String manager = "";
		if(dept != null && dept.getMANAGER()!=null && dept.getMANAGER().split("__").length>1){
			manager = dept.getMANAGER().split("__")[1];
		}
		model.put("result", result);
		model.put("manager", manager);
		return model;
	}

	/**
	 * 產生部門Tree
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dept/getDeptTree")
	public @ResponseBody
	BaseJasonObject<JsTreeDTO> getDeptTree(@RequestParam(value = "deptId") String deptId) {
		List<JsTreeDTO> rows = null;
		rows = org002Service.getDeptTree(deptId);		
		return new BaseJasonObject<>(rows, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * Dept新增/編輯頁面
	 * 
	 * @param request
	 * @param btnType
	 * @param deptId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/dept/getDeptM")
	public @ResponseBody
	BaseJasonObject<Object> getOrgM(HttpServletRequest request,
			@RequestParam("btnType") String btnType,
			@RequestParam("deptId") String deptId, Map<String, Object> modelMap) {

		// modelMap.put("orgPosition", org002Service.selectAllOrgPosition());
		// //所有部門主管
		modelMap.put("orgDomain", org002Service.selectAllOrgDomain()); // 所有區域
		modelMap.put("deptLevel", org002Service.getDeptLevel()); //取得所有部門層級資訊

		if (btnType.equals("add")) {
			modelMap.put("btn01M", "add");
			modelMap.put("orgDept",
					org002Service.selectAllOrgDept(new TbOrgDeptExample())); // 所有部門資訊(從屬部門用)
			modelMap.put("orgDeptInfo", new TbOrgDept()); // 部門資訊
		} else {
			modelMap.put("btn01M", "edit");
			TbOrgDeptExample example = new TbOrgDeptExample();
			example.createCriteria().andDEPT_IDNotEqualTo(deptId);
			modelMap.put("orgDept", org002Service.selectAllOrgDept(example)); // 所有部門資訊(從屬部門用)
			// 依部門ID取資訊
			TbOrgDept deptInfo = org002Service.selectOrgDeptById(deptId);
			// 拆解部門主管(ID__職位名稱)
			// String manager = deptInfo.getMANAGER();
			// if(StringUtils.isNotEmpty(manager)){
			// deptInfo.setMANAGER(manager.substring(manager.indexOf("__")+2,
			// manager.length()));
			// }
			modelMap.put("orgDeptInfo", deptInfo);
		}
		return new BaseJasonObject<>(modelMap, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * Dept儲存
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dept/getDeptM/save")
	public @ResponseBody
	BaseJasonObject<TbOrgDept> deptSave(HttpServletRequest request) {
		try {
			// 新增
			if (request.getParameter("btn01M").equals("add")) {
				saveNewDept(request);
				return new BaseJasonObject<>(true,
						getMessageDetail("msg.add.success"));
			} else { // 修改
				saveUpdateDept(request);
				return new BaseJasonObject<>(true,
						getMessageDetail("msg.update.success"));
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(false, e.getMessage());
		}
	}

	/**
	 * 儲存_Dept新增
	 * @param request
	 */
	public void saveNewDept(HttpServletRequest request) {

		TbOrgDept orgDept = new TbOrgDept();
		String currentUserName = getLoginUser().getUsername();
		orgDept.setMD_USER(currentUserName); // 建立&修改者
		orgDept.setMD_TIME(new Date()); // 建立&修改時間

		orgDept.setDEPT_ID(request.getParameter("dept_ID")); // 部門代號
		orgDept.setDEPT_NAME(request.getParameter("dept_NAME")); // 部門名稱
		orgDept.setDEPT_LEVEL(request.getParameter("dept_LEVEL")); // 部門層級
		// 從屬部門 & HIERARCHY
		if (StringUtils.isNotEmpty(request.getParameter("org_DEPT"))){
			orgDept.setPARENT_DEPT_ID(request.getParameter("org_DEPT"));
			//HIERARCHY
			String[] fatherNode=org002Service.getParentByChild(request.getParameter("org_DEPT"));
			String hierarchy=null;						
			if(fatherNode.length!=0){
				hierarchy = org002Service.mergerHierarchy(fatherNode);
			}
			hierarchy=hierarchy+"_"+request.getParameter("dept_ID");
		   orgDept.setHIERARCHY(hierarchy);
		}else{
			orgDept.setHIERARCHY(request.getParameter("dept_ID"));
		}			
		// 部門主管
		// String deptid_mandger = "";
		// if (StringUtils.isNotEmpty(request.getParameter("org_POS"))) {
		// deptid_mandger = request.getParameter("dept_ID") + "__"
		// + request.getParameter("org_POS");
		// orgDept.setMANAGER(deptid_mandger);
		// }
		// 區域
		if (StringUtils.isNotEmpty(request.getParameter("org_DOM")))
			orgDept.setDOMAIN(request.getParameter("org_DOM"));
		// 類型
		orgDept.setTYPE(request.getParameter("org_TYPE"));
		orgDept.setHR_DEPT_ID(request.getParameter("hr_dept_ID")); // HR部門
				
		org002Service.saveNewDept(orgDept); // ID或Name如有重複拋出異常
	}

	/**
	 * 儲存_Dept修改
	 * @param request
	 */
	public void saveUpdateDept(HttpServletRequest request) {
		TbOrgDept orgDept = new TbOrgDept();
		String currentUserName = getLoginUser().getUsername();
		orgDept.setMD_USER(currentUserName); // 修改者
		orgDept.setMD_TIME(new Date()); // 修改時間

		orgDept.setDEPT_ID(request.getParameter("dept_ID")); //部門代碼
		// 部門名稱
		orgDept.setDEPT_NAME(request.getParameter("dept_NAME"));
		// 部門層級
		orgDept.setDEPT_LEVEL(request.getParameter("dept_LEVEL"));

		// 從屬部門
		if ("".equals(request.getParameter("org_DEPT"))) {
			orgDept.setPARENT_DEPT_ID(null);
		} else {
			orgDept.setPARENT_DEPT_ID(request.getParameter("org_DEPT"));
		}
		// 部門主管
		// String deptid_mandger = "";
		// if (StringUtils.isNotEmpty(request.getParameter("org_POS"))) {
		// deptid_mandger = request.getParameter("dept_ID") + "__"+
		// request.getParameter("org_POS");
		// if(StringUtils.equals(deptid_mandger, rsList.getMANAGER())){
		// orgDept.setMANAGER(null);
		// }else{
		// orgDept.setMANAGER(deptid_mandger);
		// }
		// }else{
		// orgDept.setMANAGER("");
		// }
				
		orgDept.setDOMAIN(request.getParameter("org_DOM"));  // 區域		
		orgDept.setTYPE(request.getParameter("org_TYPE"));  // 類型		
		orgDept.setHR_DEPT_ID(request.getParameter("hr_dept_ID"));  // HR部門		
		//HIERARCHY要在service處理，Transactional(此部門的子部門都要修改HIERARCHY)
					
		org002Service.saveUpdateDept(orgDept);  // Name如有重複拋出異常
	}

	/**
	 * Dept刪除
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/dept/getDeptM/delete")
	public @ResponseBody
	BaseJasonObject<TbOrgDept> deleteDept(@RequestParam("deptId") String deptId) {
		try {
			// 刪除
			org002Service.deleteDept(deptId);
			return new BaseJasonObject<>(true, getMessageDetail("msg.delete.success"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(false, e.getMessage());
		}
	}

	/**
	 * 部門職稱人員頁面
	 * 
	 * @param request
	 * @param deptPosId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dept/psnPos")
	@ResponseBody
	public BaseJasonObject<PsnPosDTO> posPsnPage(HttpServletRequest request,
			@RequestParam("deptPosId") String deptPosId,
			Map<String, Object> model) {
		
		List<PsnPosDTO> psnPosList = org002Service
				.getPsnPosDTOByDeptPosId(deptPosId);
	
		return new BaseJasonObject<>(psnPosList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 新增psnPos
	 * 
	 * @param request
	 * @param psnList
	 * @return
	 */
	@RequestMapping(value = "/dept/psnPos/addSave")
	@ResponseBody
	public boolean saveNewPsnPos(HttpServletRequest request,
			@RequestBody List<TbAuthPersonnel> psnList) {
		String deptPosId = request.getParameter("deptPosId");
		List<TbOrgPsnPos> psnPosList = new ArrayList<TbOrgPsnPos>();
		for (TbAuthPersonnel psn : psnList) {
			TbOrgPsnPos psnPos = new TbOrgPsnPos();
			psnPos.setPSN_NO(psn.getPSN_NO());
			psnPos.setDEPT_POS_ID(deptPosId);
			psnPosList.add(psnPos);
		}
		boolean result = false;
		try{
			result = org002Service.saveNewPsnPos(deptPosId, psnPosList);
		}catch(DataExistsException ex){
			
		}
		return result;
	}

	/**
	 * 刪除PosPsn
	 * 
	 * @param deptPosId
	 * @param psnNo
	 * @return
	 */
	@RequestMapping(value = "/dept/psnPos/delete")
	@ResponseBody
	public Map<String ,Object> deletePosPsnByPrimary(
			@RequestParam("deptPosId") String deptPosId,
			@RequestParam("psnNo") String[] psnNo,
			@RequestParam("posType") String posType) {
		Map<String ,Object> map = new HashMap<String ,Object>();
		try{
			org002Service.deletePosPsnByPrimary(deptPosId, psnNo,posType);
			map.put("result", this.getMessageDetail("msg.delete.success"));
		}catch(NomsException ex){
			map.put("result", this.getMessageDetail("msg.delete.fail"));
			log.error(ExceptionUtils.getFullStackTrace(ex));
		}
		return map;
	}

	/**
	 * 更新主要職務
	 * 
	 * @param deptPosId
	 * @param posNo
	 * @return
	 */
	@RequestMapping(value = "/dept/psnPos/updatePrimaryJobPos")
	@ResponseBody
	public boolean updatePrimaryJobByDeptPosIdPSNNo(String deptPosId,
			String psnNo) {
		if(deptPosId == ""){
			deptPosId = null;
		}
		boolean result = org002Service.updatePrimaryJobByDeptPosIdPSNNo(deptPosId, psnNo);
		return result;
	}
	
	/**
	 * (塞HIERARCHY用)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/org200/insertHIERARCHY")
	public @ResponseBody
	String test(HttpServletRequest request) {
		List<TbOrgDept> results = org002Service.getHierarchy(); //取HIERARCHY為空者
		for(int i=0;i<results.size();i++){			
			String[] fatherNode=org002Service.getParentByChild(results.get(i).getDEPT_ID());
			String hierarchy = org002Service.mergerHierarchy(fatherNode);
		    org002Service.updateHierarchy(results.get(i).getDEPT_ID(), hierarchy);
		}
		return AJAX_SUCCESS;
	}
	
	/**
	 * 更新主要職務
	 * 
	 * @param deptPosId
	 * @param posNo
	 * @return
	 */
	@RequestMapping(value = "/dept/deptPos/searchPosition")
	@ResponseBody
	public List<TbOrgPosition> searchDept(@RequestParam("posType")String posType) {
		TbOrgPositionExample example = new TbOrgPositionExample();
		if(!"all".equals(posType)){
			example.createCriteria().andPOS_TYPEEqualTo(posType);
		}
		List<TbOrgPosition> posList = org002Service.getPositionByCondition(example);
		return  posList;
	}
	
	/**
	 * 驗證此人員的主要工作
	 * @param psnNo
	 * @param deptPosId
	 * @return
	 */
	@RequestMapping(value = "/dept/psnPos/doValidatePrimaryJob")
	@ResponseBody
	public BaseJasonObject<String> doValidatePrimaryJob(String deptPosId,
			String psnNo) {
		return new BaseJasonObject<String>(true, org002Service.doValidatePrimaryJob(psnNo, deptPosId));
	}
}
