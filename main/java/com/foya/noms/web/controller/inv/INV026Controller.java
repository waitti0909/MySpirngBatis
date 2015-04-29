package com.foya.noms.web.controller.inv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.UTbInvSiteTxnExample;
import com.foya.noms.dto.inv.TbInvSiteTxnDTO;
import com.foya.noms.service.inv.INV026Service;
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
@RequestMapping(value = "/inv/inv026")
public class INV026Controller extends BaseController {

	@Autowired
	private INV026Service inv026Service;

	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/init")
	public String init(HttpServletRequest request, Map<String, Object> model) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -7);
		model.put("md_time_s", cal.getTime());
		model.put("md_time_e", new Date());
		return "ajaxPage/inv/INV026";
	}

	/**
	 * 查詢資材歷程筆數
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getDataCount")
	@ResponseBody
	public BaseJasonObject<Object> getDataCount(HttpServletRequest request) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

		String maxRowNum = request.getParameter("max_row_num");
		if (maxRowNum == null || maxRowNum.trim().equals("")) {
			maxRowNum = "35001";
		}
		String btsSiteId = request.getParameter("bts_site_id");
		String mdTimeS = request.getParameter("md_time_s");
		String mdTimeE = request.getParameter("md_time_e");

		UTbInvSiteTxnExample example = new UTbInvSiteTxnExample();
		UTbInvSiteTxnExample.Criteria cr = example.createCriteria();
		if (btsSiteId != null && !btsSiteId.equals("")) {
			cr.andEqualTo("c.bts_site_id", btsSiteId);
			String[] siteStatus = { "S01", "S02", "S03", "S04", "S05", "S06", "S07", "S08" };
			List<String> siteStatusList = new ArrayList<String>();
			for (int i = 0; i < siteStatus.length; i++) {
				siteStatusList.add(siteStatus[i]);
			}
			cr.andIn("c.site_status", siteStatusList);
		}
		if (mdTimeS != null && !mdTimeS.equals("") && mdTimeE != null && !mdTimeE.equals("")) {
			cr.andMd_timeBetween(sdf.parse(mdTimeS + " 00:00:00.000"), sdf.parse(mdTimeE + " 23:59:59.999"));
		}

		List<TbInvSiteTxnDTO> list = inv026Service.selectInvSiteTxnByExampleForLimit(example, Integer.valueOf(maxRowNum));
		request.getSession().setAttribute("inv026_queryList", list);
		
		BaseJasonObject<Object> bjo = new BaseJasonObject<Object>(true, getMessageDetail("msg.query.success"));
		Map<String, Object> messageMap = new HashMap<String, Object>();
		messageMap.put("count", list.size());
		bjo.setMaps(messageMap);
		return bjo;
	}

	/**
	 * 查詢資材歷程
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/query")
	@ResponseBody
	public JqGridData<TbInvSiteTxnDTO> query(HttpServletRequest request) throws Exception {
		List<TbInvSiteTxnDTO> list = (List<TbInvSiteTxnDTO>) request.getSession().getAttribute("inv026_queryList");
		return this.getJqGridData(request.getParameter("page"), request.getParameter("size"), list);
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
		List<TbInvSiteTxnDTO> list = (List<TbInvSiteTxnDTO>) request.getSession().getAttribute("inv026_queryList");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "INV026_" + sdf.format(new Date()) + ".xls";
		inv026Service.exportExcel(response, fileName, list);
	}

	/**
	 * 手動設定jqGrid換頁功能
	 * @param inputPage
	 * @param inputSize
	 * @param list
	 * @return
	 */
	private JqGridData<TbInvSiteTxnDTO> getJqGridData(String inputPage, String inputSize, List<TbInvSiteTxnDTO> list) {
		if (list.size() == 0) {
			return new JqGridData<TbInvSiteTxnDTO>(list.size(), list);
		}

		int page = Integer.valueOf(inputPage == null || inputPage.equals("") ? "1" : inputPage);
		int size = Integer.valueOf(inputSize == null || inputSize.equals("") ? "10" : inputSize);
		int startRow = (page - 1) * size;
		int endRow = (page * size) - 1;

		List<TbInvSiteTxnDTO> results = new ArrayList<TbInvSiteTxnDTO>();
		while (startRow <= endRow && startRow < list.size()) {
			results.add(list.get(startRow++));
		}

		return new JqGridData<TbInvSiteTxnDTO>(list.size(), results);
	}
}
