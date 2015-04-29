package com.foya.noms.web.controller.inv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.UTbInvBookingExample;
import com.foya.dao.mybatis.model.UTbInvInvExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dto.inv.TbInvBookingDTO;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.service.inv.INV024Service;
import com.foya.noms.service.org.DomainService;
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
@RequestMapping(value = "/inv/inv024")
public class INV024Controller extends BaseController {

	@Autowired
	private INV024Service inv024Service;

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

		model.put("mat_no", request.getParameter("paramMatNo"));
		model.put("wh_id", request.getParameter("paramWhId"));
		model.put("domain", request.getParameter("paramDomain"));
		boolean cancelSystemBut = false; // 是否從其他程式進入
		if (request.getParameter("cancelSystemBut") != null
				&& request.getParameter("cancelSystemBut").equals("true")) {
			cancelSystemBut = true;
		}
		model.put("cancelSystemBut", cancelSystemBut);

		if (cancelSystemBut == false) {
			session.setAttribute("inv024_orgDomainList", domainService.getStandardAndTopDomain()); // 區域

			UTbInvWarehouseExample example = new UTbInvWarehouseExample();
			example.createCriteria().andIs_effectiveEqualTo(true);
			List<TbInvWarehouseDTO> list = inv024Service.selectInvWarehouseByExample(example);
			session.setAttribute("inv024_warehouseList", this.getLookup("warehouse", list)); // 倉庫
		}

		return "ajaxPage/inv/INV024";
	}

	/**
	 * 查詢Booking主檔資料
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query")
	@ResponseBody
	public JqGridData<TbInvInvDTO> query(HttpServletRequest request) {
		String matNo = request.getParameter("mat_no");
		String whId = request.getParameter("wh_id");
		String domain = request.getParameter("domain");
		String cancelSystemBut = request.getParameter("cancelSystemBut"); // 是否從其他程式進入
		
		UTbInvInvExample example = new UTbInvInvExample();
		UTbInvInvExample.Criteria cr = example.createCriteria();
		if (matNo != null && !matNo.equals("")) {
			if (cancelSystemBut != null && cancelSystemBut.equals("true")) {
				cr.andMat_noEqualTo(matNo);
			} else {
				cr.andMat_noLike("%" + matNo + "%");
			}
		}
		if (whId != null && !whId.equals("")) {
			cr.andWh_idEqualTo(whId);
		}
		if (domain != null && !domain.equals("")) {
			cr.andEqualTo("b.domain", domain);
		}
		cr.andBooking_qtyGreaterThan(0);
		
		List<TbInvInvDTO> list = inv024Service.selectInvInvByExampleAndGroupBy(example);
		PageList<TbInvInvDTO> page = (PageList<TbInvInvDTO>) list;
		return new JqGridData<TbInvInvDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 查詢Booking明細資料
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryDetail")
	@ResponseBody
	public JqGridData<TbInvBookingDTO> queryDetail(HttpServletRequest request) {
		String matNo = request.getParameter("mat_no");
		String whId = request.getParameter("wh_id");

		UTbInvBookingExample example = new UTbInvBookingExample();
		UTbInvBookingExample.Criteria cr = example.createCriteria();
		if (matNo != null && !matNo.equals("")) {
			cr.andMat_noEqualTo(matNo);
		}
		if (whId != null && !whId.equals("")) {
			cr.andWh_idEqualTo(whId);
		}
		cr.andBooking_qtyGreaterThan(0);
		
		List<TbInvBookingDTO> list = inv024Service.selectInvBookingByExample(example);
		PageList<TbInvBookingDTO> page = (PageList<TbInvBookingDTO>) list;
		return new JqGridData<TbInvBookingDTO>(page.getPaginator().getTotalCount(), list);
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
