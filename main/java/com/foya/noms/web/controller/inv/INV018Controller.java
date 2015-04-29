package com.foya.noms.web.controller.inv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.dao.mybatis.model.UTbInvSrlExample;
import com.foya.dao.mybatis.model.UTbInvStatTranExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.exception.NomsException;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvStatTranDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.service.inv.INV018Service;
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
@RequestMapping(value = "/inv/inv018")
public class INV018Controller extends BaseController {

	@Autowired
	private INV018Service inv018Service;

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
		String deptId = super.getLoginUser().getDeptId();
		boolean mainGroup = false;

		TbSysLookupExample lookupExample = new TbSysLookupExample();
		TbSysLookupExample.Criteria lookupCr = lookupExample.createCriteria();
		lookupCr.andTYPEEqualTo("ORG_SPECIFIC_DEPT_ID");
		lookupCr.andCODEEqualTo("LOGISTICS_DEPT_ID");
		List<TbSysLookup> lookupList = inv018Service.selectSysLookupByExample(lookupExample);
		for (int i = 0; i < lookupList.size(); i++) {
			TbSysLookup tb = lookupList.get(i);
			if (StringUtils.equals(tb.getVALUE1(),deptId)) {
				mainGroup = true;
				break;
			}
		}
		
		List<TbInvWarehouseDTO> warehouseList = null;
		if (mainGroup) {
			UTbInvWarehouseExample warehouseExample = new UTbInvWarehouseExample();
			warehouseExample.createCriteria().andIs_effectiveEqualTo(true);
			warehouseList = inv018Service.selectInvWarehouseByExample(warehouseExample);
		} else {
			TbOrgDeptExample example = new TbOrgDeptExample();
			example.createCriteria().andDEPT_IDEqualTo(deptId);
			List<TbOrgDept> domainList = inv018Service.selectOrgDeptByExample(example);
			if (domainList.size() > 0) {
				UTbInvWarehouseExample warehouseExample = new UTbInvWarehouseExample();
				UTbInvWarehouseExample.Criteria cr = warehouseExample.createCriteria();
				cr.andDomainEqualTo(domainList.get(0).getDOMAIN());
				cr.andIs_effectiveEqualTo(true);
				warehouseList = inv018Service.selectInvWarehouseByExample(warehouseExample);
			}
		}
		session.setAttribute("inv018_warehouseList", this.getLookup("warehouse", warehouseList)); // 倉庫

		lookupExample = new TbSysLookupExample();
		lookupExample.createCriteria().andTYPEEqualTo("INV_TRAN_AUDIT_STAT");
		session.setAttribute("inv018_statusList", inv018Service.selectSysLookupByExample(lookupExample)); // 狀態

		lookupExample = new TbSysLookupExample();
		lookupExample.createCriteria().andTYPEEqualTo("MAT_STATUS");
		session.setAttribute("inv018_matStatusList", inv018Service.selectSysLookupByExample(lookupExample)); // 異動前後狀態

		lookupExample = new TbSysLookupExample();
		lookupExample.createCriteria().andTYPEEqualTo("INV_STAT_TRAN_RESN");
		session.setAttribute("inv018_tranResnList", inv018Service.selectSysLookupByExample(lookupExample)); // 異動原因

		return "ajaxPage/inv/INV018";
	}

	/**
	 * 查詢庫存狀態主檔資料
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query")
	@ResponseBody
	public JqGridData<TbInvStatTranDTO> query(HttpServletRequest request) {
		String invTranNo = request.getParameter("inv_tran_no"); // 異動單號
		String invTranAuditStatus = request.getParameter("inv_tran_audit_status"); // 狀態
		String whId = request.getParameter("wh_id"); // 倉庫

		UTbInvStatTranExample example = new UTbInvStatTranExample();
		UTbInvStatTranExample.Criteria cr = example.createCriteria();
		if (invTranNo != null && !invTranNo.equals("")) {
			cr.andInv_tran_noLike("%" + invTranNo + "%");
		}
		if (invTranAuditStatus != null && !invTranAuditStatus.equals("")) {
			cr.andInv_tran_audit_statusEqualTo(Byte.valueOf(invTranAuditStatus));
		}
		if (whId != null && !whId.equals("")) {
			cr.andWh_idEqualTo(whId);
		}
		
		List<TbInvStatTranDTO> list = inv018Service.selectInvStatTranByExample(example);
		PageList<TbInvStatTranDTO> page = (PageList<TbInvStatTranDTO>) list;
		return new JqGridData<TbInvStatTranDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 查詢庫存狀態明細資料
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryDetail")
	@ResponseBody
	public JqGridData<TbInvStatTranDTO> queryDetail(HttpServletRequest request) {
		String invTranNo = request.getParameter("inv_tran_no");
		
		UTbInvStatTranExample example = new UTbInvStatTranExample();
		UTbInvStatTranExample.Criteria cr = example.createCriteria();
		if (invTranNo != null && invTranNo != "") {
			cr.andInv_tran_noEqualTo(invTranNo);
		}
		
		List<TbInvStatTranDTO> list = inv018Service.selectInvStatTranByExample(example);
		PageList<TbInvStatTranDTO> page = (PageList<TbInvStatTranDTO>) list;
		return new JqGridData<TbInvStatTranDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 執行庫存狀態異動
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changeStatus")
	@ResponseBody
	public BaseJasonObject<Object> changeStatus(HttpServletRequest request) {
		try {
			String[] invTranNos = request.getParameter("inv_tran_nos").split(",");
			inv018Service.updateInvStatTranStatus(invTranNos, super.getLoginUser().getUsername());
			return new BaseJasonObject<Object>(true, getMessageDetail("msg.update.success"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<Object>(true, e.getMessage());
		}
	}

	/**
	 * 導頁至庫存狀態新增與修改頁面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addEdit")
	public String addEdit(HttpServletRequest request, Map<String, Object> model) {
		String invTranNo = request.getParameter("inv_tran_no");
		model.put("btn_type", request.getParameter("btn_type")); // 新增/修改

		if (request.getParameter("btn_type").equals("add")) {
			TbInvStatTranDTO tb = new TbInvStatTranDTO();
			model.put("element", tb);
		} else {
			UTbInvStatTranExample example = new UTbInvStatTranExample();
			example.createCriteria().andInv_tran_noEqualTo(invTranNo);
			List<TbInvStatTranDTO> list = inv018Service.selectInvStatTranByExample(example);
			model.put("element", list.get(0));
		}

		return "ajaxPage/inv/INV018M";
	}

	/**
	 * 導頁至庫存狀態明細頁面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view")
	public String view(HttpServletRequest request, Map<String, Object> model) {
		String invTranNo = request.getParameter("inv_tran_no");
		UTbInvStatTranExample example = new UTbInvStatTranExample();
		example.createCriteria().andInv_tran_noEqualTo(invTranNo);
		List<TbInvStatTranDTO> list = inv018Service.selectInvStatTranByExample(example);
		model.put("element", list.get(0));
		return "ajaxPage/inv/INV018L";
	}

	/**
	 * 儲存新增/修改內容
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public BaseJasonObject<Object> save(@RequestBody TbInvStatTranDTO record) {
		try {
			record.setTran_user(getLoginUser().getUsername());
			record.setTran_date(new Date());
			record.setMd_user(getLoginUser().getUsername());
			record.setMd_time(new Date());

			// 取得廠商序號資料
			if (record.getTag_no() != null) {
				UTbInvSrlExample example = new UTbInvSrlExample();
				UTbInvSrlExample.Criteria cr = example.createCriteria();
				cr.andSrl_noEqualTo(record.getSrl_no());
				List<TbInvSrlDTO> list = inv018Service.selectInvSrlByExample(example);
				if (list.size() == 0) {
					throw new Exception("error無法取得序號資料");
				}
			}

			if (record.getBtn_type().equals("add")) {
				// 新增
				// 取得使用者部門所屬區域
				TbOrgDept orgDept = inv018Service.selectOrgDeptByPrimaryKey(getLoginUser().getDeptId());
				String domain = orgDept.getDOMAIN().substring(0, 1);

				// 產生庫存序號
				// 1.先檢查當天是否已有產生過流水號
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String invTranNo = "IA" + domain + sdf.format(new Date());

				UTbInvStatTranExample example = new UTbInvStatTranExample();
				UTbInvStatTranExample.Criteria cr = example.createCriteria();
				cr.andInv_tran_noLike(invTranNo + "%");
				sdf = new SimpleDateFormat("yyyy/MM/dd");
				String today = sdf.format(new Date());
				sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
				cr.andCr_timeBetween(sdf.parse(today + " 00:00:00.000"), sdf.parse(today + " 23:59:59.999"));
				int count = inv018Service.countInvStatTranByExample(example);

				// 2.產生庫存序號
				invTranNo += String.format("%03d", count + 1);

				// 新增資料
				record.setInv_tran_no(invTranNo); // IA+區碼1碼 + YYMMDD + 流水號3碼
				record.setCr_user(getLoginUser().getUsername());
				record.setCr_time(new Date());
				inv018Service.insertInvStatTranSelective(record);

				return new BaseJasonObject<Object>(true, getMessageDetail("msg.add.success"));
			} else {
				// 修改
				record.setInv_tran_audit_status(Byte.valueOf("1"));
				inv018Service.updateInvStatTranByPrimaryKeySelective(record);
				return new BaseJasonObject<Object>(true, getMessageDetail("msg.update.success"));
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<Object>(false, e.getMessage());
		}
	}

	/**
	 * 申請庫存異動
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/apply")
	@ResponseBody
	@Transactional
	public BaseJasonObject<Object> apply(@RequestBody TbInvStatTranDTO record) {
		try {
			inv018Service.apply(record, getLoginUser().getUsername());
			return new BaseJasonObject<Object>(true, "申請成功，申請資料已送審");
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<Object>(true, e.getMessage());
		}
	}

	/**
	 * 執行庫存狀態異動
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/cancelApply")
	@ResponseBody
	public BaseJasonObject<Object> cancelApply(HttpServletRequest request) {
		try {
			String[] invTranNos = request.getParameter("inv_tran_nos").split(",");
			inv018Service.cancelApply(invTranNos, super.getLoginUser().getUsername());
			return new BaseJasonObject<Object>(true, getMessageDetail("msg.delete.success"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<Object>(true, e.getMessage());
		}
	}

	/**
     * 供WORKFLOW審核呼叫
     * @param orderId 庫存單號或調撥單號
     * @param action  SUCCESS/REJECT
     * @return String
     */
	@RequestMapping(value = "/applyByWF")
	@ResponseBody
	@Transactional
	public int applyByWF(@RequestParam("orderId") String invTranNo,
			@RequestParam("action") String action) throws NomsException {
		try {
			return inv018Service.applyByWF(invTranNo, action);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
	}

	/**
	 *  取得序號資料(貼標號碼、廠商序號)
	 * @param whId
	 * @param matNo
	 * @return
	 */
	@RequestMapping(value = "/search/tagNo")
	@ResponseBody
	public List<TbInvSrlDTO> getOrgDept(@RequestParam("wh_id") String whId,
			@RequestParam("mat_no") String matNo) {
		UTbInvSrlExample example = new UTbInvSrlExample();
		UTbInvSrlExample.Criteria cr = example.createCriteria();
		cr.andWh_idEqualTo(whId);
		cr.andMat_noEqualTo(matNo);
		return inv018Service.selectInvSrlByExample(example);
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
}
