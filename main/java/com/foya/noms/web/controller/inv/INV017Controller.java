package com.foya.noms.web.controller.inv;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbInvSnCor;
import com.foya.dao.mybatis.model.UTbInvSrlExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.service.inv.INV017Service;
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
@RequestMapping(value = "/inv/inv017")
public class INV017Controller extends BaseController {

	@Autowired
	private INV017Service inv017Service;

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

		List<TbInvWarehouseDTO> list = inv017Service.selectInvWarehouseByExample(example);
		session.setAttribute("inv017_warehouseList", this.getLookup("warehouse", list)); // 倉庫
		return "ajaxPage/inv/INV017";
	}

	/**
	 * 查詢序號資料
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query")
	@ResponseBody
	public JqGridData<TbInvSrlDTO> query(HttpServletRequest request) {
		String matNo = request.getParameter("mat_no"); // 料號
		String venSn = request.getParameter("ven_sn"); // 廠商序號
		String btsSiteId = request.getParameter("bts_site_id"); // 基站編號
		String whId = request.getParameter("wh_id"); // 倉庫

		UTbInvSrlExample example = new UTbInvSrlExample();
		UTbInvSrlExample.Criteria cr = example.createCriteria();
		if (matNo != null && !matNo.equals("")) {
			cr.andMat_noLike("%" + matNo + "%");
		}
		if (venSn != null && !venSn.equals("")) {
			cr.andVen_snLike("%" + venSn + "%");
		}
		if (btsSiteId != null && !btsSiteId.equals("")) {
			cr.andEqualTo("c.bts_site_id", btsSiteId);
			
			String[] siteStatus = { "S01", "S02", "S03", "S04", "S05", "S06", "S07", "S08" };
			List<String> siteStatusList = new ArrayList<String>();
			for (int i = 0; i < siteStatus.length; i++) {
				siteStatusList.add(siteStatus[i]);
			}
			cr.andIn("c.site_status", siteStatusList);
		}
		if (whId != null && !whId.equals("")) {
			cr.andWh_idEqualTo(whId);
		}
		
		List<TbInvSrlDTO> list = inv017Service.selectInvSrlByExample(example);
		PageList<TbInvSrlDTO> page = (PageList<TbInvSrlDTO>) list;
		return new JqGridData<TbInvSrlDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 導頁至廠商序號修改頁面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addEdit")
	public String addEdit(HttpServletRequest request, Map<String, Object> model) {
		String srlNo = request.getParameter("srl_no");
		TbInvSrlDTO tb = inv017Service.selectInvSrlByPrimaryKey(Long.valueOf(srlNo));
		model.put("element", tb);
		return "ajaxPage/inv/INV017M";
	}

	/**
	 * 儲存修改內容
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public BaseJasonObject<TbInvSrlDTO> update(HttpServletRequest request) {
		String srlNo = request.getParameter("srl_no");
		String matNo = request.getParameter("mat_no");
		String oldVenSn = request.getParameter("old_ven_sn");
		String venSn = request.getParameter("ven_sn");

		try {
			UTbInvSrlExample example = new UTbInvSrlExample();
			UTbInvSrlExample.Criteria cr = example.createCriteria();
			cr.andMat_noEqualTo(matNo);
			cr.andVen_snEqualTo(venSn);
			int count = inv017Service.countInvSrlByExample(example);
			if (count > 0) {
				throw new Exception("廠商序號已存在，請確認");
			}
			
			TbInvSrlDTO record = new TbInvSrlDTO();
			record.setSrl_no(Long.valueOf(srlNo));
			record.setVen_sn(venSn);
			record.setMd_user(getLoginUser().getUsername());
			record.setMd_time(new Date());
			int status = inv017Service.updateInvSrlByPrimaryKeySelective(record);
			
			if (status == 1) {
				TbInvSnCor record2 = new TbInvSnCor();
				record2.setSrl_no(Long.valueOf(srlNo));
				record2.setVen_sn(venSn);
				record2.setOld_ven_sn(oldVenSn);
				record2.setTran_dept_id(getLoginUser().getDeptId());
				record2.setCr_user(getLoginUser().getUsername());
				record2.setCr_time(new Date());
				inv017Service.insertInvSnCorSelective(record2);
			}
			
			return new BaseJasonObject<TbInvSrlDTO>(true, getMessageDetail("msg.update.success"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<TbInvSrlDTO>(false, e.getMessage());
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
