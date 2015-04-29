package com.foya.noms.web.controller.ls;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.noms.dto.ls.LeaseDTO;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.ls.LS002Service;
import com.foya.noms.service.ls.LsCommonService;
import com.foya.noms.service.org.DeptService;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.org.ORG002Service;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.DateUtils;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/ls")
public class LS002Controller extends BaseController {

	@Autowired
	private LS002Service lS002Service;
	@Autowired
	private DeptService deptService;
	@Autowired
	private DomainService domainService;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private LsCommonService lsCommonService;
	@Autowired
	private ORG002Service oRG002Service;

	/**
	 * 合約維護作業初始頁
	 * 
	 * @return
	 */
	@RequestMapping(value = "/LS002/init")
	public String lS002Init(HttpServletRequest request, Map<String, Object> model) {
		return "ajaxPage/ls/LS002";
	}

	/**
	 * 依合約編號模糊查詢
	 * 
	 * @param lsNo
	 *       	合約編號
	 * @param type
	 * 			類型	
	 * @return
	 */
	@RequestMapping(value = "/LS002/searchByCond")
	@ResponseBody
	public JqGridData<LeaseDTO> searchByLeaseNo(String lsNo,String type) {
		List<LeaseDTO> list = lS002Service.searchByLeaseNo("%" + lsNo + "%",type);
		PageList<LeaseDTO> page = (PageList<LeaseDTO>) list;
		if (list.size() >= 1000) {
			return new JqGridData<LeaseDTO>(-1, null);
		} else {
			return new JqGridData<LeaseDTO>(page.getPaginator().getTotalCount(), list);
		}
	}

	/**
	 * 轉至修改頁，與查詢修改要需要的值
	 * 
	 * @param qType
	 *            修改頁的型態 1:租金起算日異動 2:房屋稅籍編號異動
	 * @param lsNo
	 *            合約編號
	 * @param flag
	 *            true:預覽 false:修改
	 * @param modelMap
	 *            返回UI所承接的map
	 * @return
	 */
	@RequestMapping(value = "/LS002M")
	public String ls002M(String qType,String appSeq, String lsNo, String lsVer, String flag, Map<String, Object> modelMap) {
		modelMap = setModelMap(qType, appSeq, lsNo, lsVer, flag, modelMap);
		return "ajaxPage/ls/LS002M";
	}

	@RequestMapping(value = "/LS002/confirmLs002M")
	@ResponseBody
	public BaseJasonObject<LeaseDTO> confirmLs002M(String qType,String appSeq ,String lsNo, String lsVer, Map<String, Object> modelMap) {
		modelMap = setModelMap(qType, appSeq, lsNo, lsVer, "", modelMap);
		if (modelMap.get("json") != null) {
			return new BaseJasonObject<>(true, "");
		} else {
			return new BaseJasonObject<>(false, "查無資料");
		}
	}
	
	private Map<String, Object> setModelMap(String qType,String appSeq, String lsNo, String lsVer, String flag, Map<String, Object> modelMap){
		modelMap.put("qType", StringUtils.equals("1", qType) ? "租金起算日異動" : "房屋稅籍編號異動");
		modelMap.put("flag", flag);
		modelMap.put("appSeq", appSeq);
		List<TbLsMain> mainList = lsCommonService.selectLsMainBylsNoVerMax(lsNo);
		if(mainList.size() > 0){
			TbOrgDept tbOrgDept = oRG002Service.getByPk(mainList.get(0).getAPP_USER_DEPT());
			modelMap.put("lsNo", mainList.get(0).getLS_NO());
			modelMap.put("lsName", mainList.get(0).getLS_NAME());// DateUtils.format(tbLsMain.getLS_S_DATE(), "yyyy/MM/dd")
			modelMap.put("S_Date", mainList.get(0).getLS_S_DATE() == null ? "" : DateUtils.formatDateUS(mainList.get(0).getLS_S_DATE(), "yyyy/MM/dd"));
			modelMap.put("E_Date", mainList.get(0).getLS_E_DATE() == null ? "" : DateUtils.formatDateUS(mainList.get(0).getLS_E_DATE(), "yyyy/MM/dd"));
			tbOrgDept = oRG002Service.getByPk(mainList.get(0).getKEEP_DEPT());
			modelMap.put("deptPlace", tbOrgDept.getDEPT_NAME() + "/" + (tbOrgDept.getDOMAIN().indexOf("N") >= 0 ? "北一合約室" : tbOrgDept.getDOMAIN().indexOf("M") >= 0 ? "北二合約室" : tbOrgDept.getDOMAIN().indexOf("C") >= 0 ? "中區合約室" : tbOrgDept.getDOMAIN().indexOf("S") >= 0 ? "南區合約室" : ""));
		}
		if (StringUtils.equals("1", qType)) {
			lS002Service.searchByHouseLs002M(false, lsNo, modelMap);
			lS002Service.searchByRentLs002M(appSeq,lsNo, modelMap);
		} else {
			lS002Service.searchByHouseLs002M(true, lsNo, modelMap);
		}
		return modelMap;
	}

	/**
	 * 由部門取得人員
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/LS002/getUsertByDeptId")
	@ResponseBody
	public BaseJasonObject getUsertByDeptId(String deptId) {
		List<TbAuthPersonnel> personnelList = personnelService.selectByDeptIdLike(deptId);
		return new BaseJasonObject<TbAuthPersonnel>(personnelList, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 更新 TB_LS_LESSOR
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param lessorId
	 *            出租人證號
	 * @param newTaxNo
	 *            房屋稅籍編號
	 */
	@RequestMapping(value = "/LS002/saveUpdateHouseTaxNo")
	@ResponseBody
	public BaseJasonObject<LeaseDTO> saveUpdateHouseTaxNo(String lsNo, String lsVer, String lessorId, String newTaxNo) {
		try {
			lS002Service.saveUpdateHouseTaxNo(lsNo, lsVer, lessorId, newTaxNo);
			return new BaseJasonObject<>(true, getMessageDetail("msg.update.success"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(false, e.getMessage());
		}
	}

	/**
	 * 更新 TB_LS_LOCATION 與 TB_LS_SITE 這兩個 Table
	 * 
	 * @param request
	 *            由http來取得更新用的參數
	 * @return
	 */
	@RequestMapping(value = "/LS002/updayeLocationSitePayBeginDate")
	@ResponseBody
	public BaseJasonObject<LeaseDTO> updayeLocationSitePayBeginDate(HttpServletRequest request) {
		try {
			String[] rentLsNoArray = request.getParameterValues("rentLsNo");
			String[] rentLsVerArray = request.getParameterValues("rentLsVer");
			String[] rentLocationIdArray = request.getParameterValues("rentLocationId");
			String[] rentTimeArray = request.getParameterValues("rentTime");
			String addedSeq = request.getParameter("addedSeq");
			lS002Service.updayeLocationSitePayBeginDate(addedSeq,rentLsNoArray, rentLsVerArray, rentLocationIdArray, rentTimeArray);
			return new BaseJasonObject<>(true, getMessageDetail("msg.update.success"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<>(false, e.getMessage());
		}
	}

}
