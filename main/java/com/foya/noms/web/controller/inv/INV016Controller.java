package com.foya.noms.web.controller.inv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComCompanyExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.service.inv.INV016Service;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.system.LookupService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

/**
 * 
 * 
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2014/10/31</td>
 * <td>新建檔案</td>
 * <td>Markee</td>
 * </tr>
 * </table>
 * 
 * @author Markee
 * 
 */
@Controller
@RequestMapping(value = "/inv/inv016")
public class INV016Controller extends BaseController {

	@Autowired
	private INV016Service inv016Service;

	@Autowired
	private LookupService lookupService;

	@Autowired
	private DomainService domainService;

	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String init(HttpServletRequest request, Map<String, Object> model) {
		HttpSession session = request.getSession();

		UTbInvWarehouseExample example = new UTbInvWarehouseExample();
		example.createCriteria().andIs_effectiveEqualTo(true);
		List<TbInvWarehouseDTO> list = inv016Service.selectInvWarehouseByExample(example);

		session.setAttribute("inv016_warehouseList", this.getLookup("warehouse", list)); // 倉庫
		session.setAttribute("inv016_whAttributeList", lookupService.getLabelValueList("WH_ATTR")); // 倉庫屬性
		session.setAttribute("inv016_whTypeList", lookupService.getLabelValueList("WH_TYPE")); // 倉庫類別
		session.setAttribute("inv016_orgDomainList", domainService.getStandardAndTopDomain()); // 區域
		session.setAttribute("inv016_companyList", inv016Service.selectComCompanyByExample(new TbComCompanyExample())); // 廠商
		session.setAttribute("inv016_isEffectiveList", lookupService.getLabelValueList("WH_EFFECTIVE")); // 啟用狀況
		session.setAttribute("inv016_failureResnList", lookupService.getLabelValueList("WH_FAIL_RESN")); // 失效原因

		return "ajaxPage/inv/INV016";
	}

	/**
	 * 查詢倉庫資料
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query")
	@ResponseBody
	public JqGridData<TbInvWarehouseDTO> query(HttpServletRequest request) {
		String whId = request.getParameter("wh_id");
		String whAttribute = request.getParameter("wh_attribute");
		String whType = request.getParameter("wh_type");
		String isEffective = request.getParameter("is_effective");
		String domain = request.getParameter("domain");
		String deptId = request.getParameter("dept_id");
		
		UTbInvWarehouseExample example = new UTbInvWarehouseExample();
		UTbInvWarehouseExample.Criteria cr = example.createCriteria();
		if (whId != null && whId != "") {
			cr.andWh_idEqualTo(whId);
		}
		if (whAttribute != null && whAttribute != "") {
			cr.andWh_attributeEqualTo(whAttribute);
		}
		if (whType != null && whType != "") {
			cr.andWh_typeEqualTo(whType);
		}
		if (isEffective != null && isEffective != "") {
			cr.andIs_effectiveEqualTo(isEffective.equals("0") ? false : true);
		}
		if (domain != null && domain != "") {
			cr.andDomainEqualTo(domain);
		}
		if (deptId != null && deptId != "") {
			cr.andDept_idEqualTo(deptId);
		}
		
		List<TbInvWarehouseDTO> list = inv016Service.selectWarehouse(example);
		PageList<TbInvWarehouseDTO> page = (PageList<TbInvWarehouseDTO>) list;
		return new JqGridData<TbInvWarehouseDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 導頁至倉庫資料新增與修改頁面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addEdit")
	public String addEdit(HttpServletRequest request, Map<String, Object> model) {
		model.put("btn_type", request.getParameter("btn_type")); // 新增/修改
		
		if (request.getParameter("btn_type").equals("add")) {
			// 新增
			TbInvWarehouseDTO tb = new TbInvWarehouseDTO();
			model.put("element", tb);
		} else {
			// 修改
			TbInvWarehouseDTO tb = inv016Service.selectWarehouseByPrimaryKey(request.getParameter("wh_id"));
			model.put("element", tb);
		}
		
		return "ajaxPage/inv/INV016M";
	}

	/**
	 * 儲存新增/修改內容
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public BaseJasonObject<TbInvWarehouseDTO> save(@RequestBody TbInvWarehouseDTO record) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			if (record.getStr_failure_date() != null && !record.getStr_failure_date().equals("")) {
				record.setFailure_date(sdf.parse(record.getStr_failure_date()));
			}
			record.setIs_effective(record.getIs_effective_str().equals("0") ? false : true);
			record.setMd_user(getLoginUser().getUsername());
			record.setMd_time(new Date());
			
			if (record.getBtn_type().equals("add")) {
				if (record.getWh_attribute().equals("M") && record.getWh_type().equals("C")) {
					UTbInvWarehouseExample example = new UTbInvWarehouseExample();
					example.createCriteria().andWh_attributeEqualTo("M").andWh_typeEqualTo("C");
					int count = inv016Service.countInvWarehouseByExample(example);
					if (count > 0) {
						Exception e = new Exception("error倉庫類別=公司、倉庫屬性=總倉，資料只能有一筆，請重新設定");
						throw e;
					}
				} else {
					UTbInvWarehouseExample example = new UTbInvWarehouseExample();
					UTbInvWarehouseExample.Criteria cr = example.createCriteria();
					cr.andWh_idEqualTo(record.getWh_id());
					int count = inv016Service.countInvWarehouseByExample(example);
					if (count > 0) {
						Exception e = new Exception("error資料已存在，請重新設定");
						throw e;
					}
				}
				
				// 新增
				record.setCr_user(getLoginUser().getUsername());
				record.setCr_time(new Date());
				inv016Service.insertInvWarehouseSelective(record);
				return new BaseJasonObject<TbInvWarehouseDTO>(true, getMessageDetail("msg.add.success"));
			} else {
				// 修改
				inv016Service.updateByPrimaryKeySelective(record);
				return new BaseJasonObject<TbInvWarehouseDTO>(true, getMessageDetail("msg.update.success"));
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<TbInvWarehouseDTO>(false, e.getMessage());
		}
	}

	/**
	 * 取得管理單位
	 * 
	 * @param cityArea
	 * @return
	 */
	@RequestMapping(value = "/search/cityArea")
	@ResponseBody
	public Map<String, String> getCityArea(
			@RequestParam("cityArea") String cityArea) {
		TbOrgDeptExample example = new TbOrgDeptExample();
		example.createCriteria().andDOMAINEqualTo(cityArea);
		List<TbOrgDept> list = inv016Service.selectAllOrgDept(example);

		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			TbOrgDept tb = list.get(i);
			map.put(tb.getDEPT_ID(), tb.getDEPT_NAME());
		}

		return map;
	}
	
	
	/**
	 * 取得保管人
	 * 
	 * @param deptId
	 * @return
	 */
	@RequestMapping(value = "/search/personnel")
	@ResponseBody
	public Map<String, String> getPersonnel(
			@RequestParam("deptId") String deptId) {
		List<TbAuthPersonnel> list = inv016Service.selectPersonnelByDeptId(deptId);

		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			TbAuthPersonnel tb = list.get(i);
			map.put(tb.getPSN_ID().toString(), tb.getCHN_NM());
		}

		return map;
	}

	/**
	 * 取得廠商倉庫代碼
	 * 
	 * @param coVatNo
	 * @return
	 */
	@RequestMapping(value = "/search/coErpNo")
	@ResponseBody
	public String getCoErpNo(
			@RequestParam("coVatNo") String coVatNo) {
		TbComCompanyExample example = new TbComCompanyExample();
		example.createCriteria().andCO_VAT_NOEqualTo(coVatNo);
		List<TbComCompany> list = inv016Service.selectComCompanyByExample(example);
		if (list.size() > 0) {
			return list.get(0).getCO_ERP_NO();
		} else {
			return null;
		}
	}

	/**
	 * 取得下拉式選單資料 <br />
	 * warehouse:倉庫<br />
	 * 
	 * @param type
	 * @param tb
	 * @return
	 */
	private List<Map<String, String>> getLookup(String type, List<TbInvWarehouseDTO> tb) {
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < tb.size(); i++) {
			TbInvWarehouseDTO element = tb.get(i);
			if (type.equals("warehouse")) {
				if (!map.containsKey(element.getWh_id())) {
					Map<String, String> resultMap = new HashMap<String, String>();
					resultMap.put("wh_id", element.getWh_id());
					resultMap.put("wh_name", element.getWh_name());
					results.add(resultMap);
				}
				map.put(element.getWh_id(), element.getWh_name());
			}
		}

		return results;
	}
}
