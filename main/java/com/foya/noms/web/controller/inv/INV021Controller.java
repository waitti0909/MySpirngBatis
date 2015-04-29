package com.foya.noms.web.controller.inv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.service.inv.INV021Service;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;

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
@RequestMapping(value = "/inv/inv021")
public class INV021Controller extends BaseController {

	@Autowired
	private INV021Service inv021Service;

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
		/*
		String deptId = super.getLoginUser().getDeptId();
		boolean mainGroup = false;

		TbSysLookupExample lookupExample = new TbSysLookupExample();
		TbSysLookupExample.Criteria lookupCr = lookupExample.createCriteria();
		lookupCr.andTYPEEqualTo("ORG_SPECIFIC_DEPT_ID");
		lookupCr.andCODEEqualTo("LOGISTICS_DEPT_ID");
		List<TbSysLookup> lookupList = inv021Service.selectSysLookupByExample(lookupExample);
		for (int i = 0; i < lookupList.size(); i++) {
			TbSysLookup tb = lookupList.get(i);
			if (StringUtils.equals(tb.getVALUE1(), deptId)) {
				mainGroup = true;
				break;
			}
		}

		List<TbInvWarehouseDTO> list = null;
		if (mainGroup) {
			UTbInvWarehouseExample example = new UTbInvWarehouseExample();
			example.createCriteria().andIs_effectiveEqualTo(true);
			list = inv021Service.selectInvWarehouseByExample(example);
		} else {
			TbOrgDeptExample example = new TbOrgDeptExample();
			example.createCriteria().andDEPT_IDEqualTo(deptId);
			List<TbOrgDept> domainList = inv021Service.selectOrgDeptByExample(example);
			if (domainList.size() > 0) {
				UTbInvWarehouseExample warehouseExample = new UTbInvWarehouseExample();
				UTbInvWarehouseExample.Criteria cr = warehouseExample.createCriteria();
				cr.andDomainEqualTo(domainList.get(0).getDOMAIN());
				cr.andIs_effectiveEqualTo(true);
				list = inv021Service.selectInvWarehouseByExample(warehouseExample);
			}
		}
		session.setAttribute("inv021_warehouseList", this.getLookup("warehouse", list)); // 倉庫
		*/
		UTbInvWarehouseExample example = new UTbInvWarehouseExample();
		example.createCriteria().andIs_effectiveEqualTo(true);
		List<TbInvWarehouseDTO> list = inv021Service.selectInvWarehouseByExample(example);
		session.setAttribute("inv021_warehouseList", this.getLookup("warehouse", list)); // 倉庫
		session.setAttribute("inv021_orgDomainList", domainService.getStandardAndTopDomain()); // 區域
		session.removeAttribute("inv021_queryList");

		return "ajaxPage/inv/INV021";
	}

	/**
	 * 查詢資產分布筆數
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getDataCount")
	@ResponseBody
	public BaseJasonObject<Object> getDataCount(HttpServletRequest request) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("max_row_num", request.getParameter("max_row_num")); // 最大查詢筆數for TbInvInv
		map.put("mat_no", request.getParameter("mat_no")); // 料號
		map.put("tag_no", request.getParameter("tag_no")); // 貼標號碼
		map.put("asset_location", request.getParameter("asset_location")); // 資產位置
		map.put("wh_id", request.getParameter("wh_id")); // 倉庫
		map.put("domain", request.getParameter("domain")); // 區域
		map.put("mat_status", request.getParameter("mat_status")); // 物料狀態
		map.put("dept_id", request.getParameter("dept_id")); // 工程維護單位
		map.put("bts_site_id", request.getParameter("bts_site_id")); // 基站編號
		map.put("over_day", request.getParameter("over_day")); // 在途超過天數
		List<Object> list = inv021Service.selectAllAssetData(map);
		request.getSession().setAttribute("inv021_queryList", list);
		
		BaseJasonObject<Object> bjo = new BaseJasonObject<Object>(true, getMessageDetail("msg.query.success"));
		Map<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put("count", list.size());
		bjo.setMaps(messageMap);
		return bjo;
	}

	/**
	 * 查詢資產分布資料
	 * 
	 * @param request
	 * @return
	 * @throws CloneNotSupportedException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/query")
	@ResponseBody
	public JqGridData<Object> query(HttpServletRequest request) throws Exception {
		try {
			List<Object> list = (List<Object>) request.getSession().getAttribute("inv021_queryList");
			if (list == null) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("mat_no", request.getParameter("mat_no")); // 料號
				map.put("tag_no", request.getParameter("tag_no")); // 貼標號碼
				map.put("asset_location", request.getParameter("asset_location")); // 資產位置
				map.put("wh_id", request.getParameter("wh_id")); // 倉庫
				map.put("domain", request.getParameter("domain")); // 區域
				map.put("mat_status", request.getParameter("mat_status")); // 物料狀態
				map.put("dept_id", request.getParameter("dept_id")); // 工程維護單位
				map.put("bts_site_id", request.getParameter("bts_site_id")); // 基站編號
				map.put("over_day", request.getParameter("over_day")); // 在途超過天數
				list = inv021Service.selectAllAssetData(map);
			}
			return this.getJqGridData(request.getParameter("page"), request.getParameter("size"), list);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}

	/**
	 * 匯出Excel
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/genExcel")
	public void genExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Object> list = (List<Object>) request.getSession().getAttribute("inv021_queryList");
		if (list == null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("mat_no", request.getParameter("mat_no")); // 料號
			map.put("tag_no", request.getParameter("tag_no")); // 貼標號碼
			map.put("asset_location", request.getParameter("asset_location")); // 資產位置
			map.put("wh_id", request.getParameter("wh_id")); // 倉庫
			map.put("domain", request.getParameter("domain")); // 區域
			map.put("mat_status", request.getParameter("mat_status")); // 物料狀態
			map.put("dept_id", request.getParameter("dept_id")); // 工程維護單位
			map.put("bts_site_id", request.getParameter("bts_site_id")); // 基站編號
			map.put("over_day", request.getParameter("over_day")); // 在途超過天數
			list = inv021Service.selectAllAssetData(map);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "INV021_" + sdf.format(new Date()) + ".xls";
		inv021Service.exportExcel(response, fileName, list);
	}

	/**
	 * 取得下拉式選單資料 <br />
	 * warehouse:倉庫<br />
	 * 
	 * @param type
	 * @param tb
	 * @return
	 */
	private List<Map<String, String>> getLookup(String type, List<TbInvWarehouseDTO> list) {
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; list != null && i < list.size(); i++) {
			TbInvWarehouseDTO element = list.get(i);
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

	/**
	 * 取得工程維護單位
	 * 
	 * @param cityArea
	 * @return
	 */
	@RequestMapping(value = "/search/orgDept")
	@ResponseBody
	public Map<String, String> getOrgDept(@RequestParam("domain") String domain) {
		TbOrgDeptExample example = new TbOrgDeptExample();
		example.createCriteria().andDOMAINEqualTo(domain);
		List<TbOrgDept> list = inv021Service.selectOrgDeptByExample(example);

		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			TbOrgDept tb = list.get(i);
			map.put(tb.getDEPT_ID(), tb.getDEPT_NAME());
		}
		return map;
	}

	/**
	 * 手動設定jqGrid換頁功能
	 * @param inputPage
	 * @param inputSize
	 * @param list
	 * @return
	 */
	private JqGridData<Object> getJqGridData(String inputPage, String inputSize, List<Object> list) {
		if (list.size() == 0) {
			return new JqGridData<Object>(list.size(), list);
		}

		int page = Integer.valueOf(inputPage == null || inputPage.equals("") ? "1" : inputPage);
		int size = Integer.valueOf(inputSize == null || inputSize.equals("") ? "10" : inputSize);
		int startRow = (page - 1) * size;
		int endRow = (page * size) - 1;

		List<Object> results = new ArrayList<Object>();
		while (startRow <= endRow && startRow < list.size()) {
			results.add(list.get(startRow++));
		}

		return new JqGridData<Object>(list.size(), results);
	}
}
