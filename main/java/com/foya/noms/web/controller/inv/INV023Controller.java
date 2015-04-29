package com.foya.noms.web.controller.inv;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.service.inv.INV023Service;
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
@RequestMapping(value = "/inv/inv023")
public class INV023Controller extends BaseController {

	@Autowired
	private INV023Service inv023Service;

	/**
	 * controller
	 */
	@Inject
	private InvPublicUtilController publicUtilController;
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/init")
	public String init(HttpServletRequest request, Map<String, Object> model) throws Exception {
		//model.put("mat_no", request.getParameter("paramMatNo"));
		//model.put("wh_id", request.getParameter("paramWhId"));
		// 需求(申請)單位
		model.put("demandDept", publicUtilController.initDeptSelect(null));
		// 領料倉庫
		model.put("wareHouseSelect", publicUtilController.initWareHouseSelectIsActive(request));

		return "ajaxPage/inv/INV023";
	}

	/**
	 * 查詢主檔資料
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query")
	@ResponseBody
	public JqGridData<TbInvMaterialOptDTO> query(HttpServletRequest request) {
		String wh_id = request.getParameter("wh_id");
		String dept_id = request.getParameter("dept_id");
		String mat_no= request.getParameter("matNoMaterial");
		String reqStartDateStr = request.getParameter("reqStartDate");//cr_time
		String reqEndDateStr = request.getParameter("reqEndDate");//cr_time
		String appStartDateStr = request.getParameter("appStartDate");//cr_time
		String appEndDateStr = request.getParameter("appEndDate");//cr_time
		String psn_no = request.getParameter("psn_no");
		
		
		HashMap<String, String> dataObj=new HashMap<String, String>();
		dataObj.put("wh_id", wh_id);
		dataObj.put("dept_id", dept_id);
		dataObj.put("mat_no",(mat_no!=null&&!mat_no.equals(""))?"%"+mat_no+"%":null);
		dataObj.put("psn_no", psn_no);
		dataObj.put("reqStartDateStr", reqStartDateStr);
		dataObj.put("reqEndDateStr", reqEndDateStr);
		dataObj.put("appStartDateStr", appStartDateStr);
		dataObj.put("appEndDateStr", appEndDateStr);
		log.info(wh_id);
		log.info(dept_id);
		log.info(mat_no);
		log.info(psn_no);
		log.info(reqStartDateStr);
		log.info(reqEndDateStr);
		log.info(appStartDateStr);
		log.info(appEndDateStr);
		List<TbInvMaterialOptDTO> list =(List<TbInvMaterialOptDTO>) inv023Service.search(dataObj);
		PageList<TbInvMaterialOptDTO> page = (PageList<TbInvMaterialOptDTO>) list;
		if(page.getPaginator().getTotalCount()>=3000){
			return new JqGridData<TbInvMaterialOptDTO>(-1, null);//page.getPaginator().getTotalCount()
		}else{
			return new JqGridData<TbInvMaterialOptDTO>(page.getPaginator().getTotalCount(), list);
		}
		
	}

	
	/**
	 * 匯出excel
	 * @return
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/genExcel")
	public void genExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String wh_id = request.getParameter("wh_id");
		String dept_id = request.getParameter("dept_id");
		String mat_no= request.getParameter("matNoMaterial");
		String reqStartDateStr = request.getParameter("reqStartDate");//cr_time
		String reqEndDateStr = request.getParameter("reqEndDate");//cr_time
		String appStartDateStr = request.getParameter("appStartDate");//cr_time
		String appEndDateStr = request.getParameter("appEndDate");//cr_time
		String psn_no = request.getParameter("psn_no");
		
		
		HashMap<String, Object> dataObj=new HashMap<String, Object>();
		dataObj.put("wh_id", wh_id);
		dataObj.put("dept_id", dept_id);
		dataObj.put("mat_no",(mat_no!=null&&!mat_no.equals(""))?"%"+mat_no+"%":null);
		dataObj.put("psn_no", psn_no);
		dataObj.put("reqStartDateStr", reqStartDateStr);
		dataObj.put("reqEndDateStr", reqEndDateStr);
		dataObj.put("appStartDateStr", appStartDateStr);
		dataObj.put("appEndDateStr", appEndDateStr);
		dataObj.put("orderOptId", true);
		log.info(wh_id);
		log.info(dept_id);
		log.info(mat_no);
		log.info(psn_no);
		log.info(reqStartDateStr);
		log.info(reqEndDateStr);
		log.info(appStartDateStr);
		log.info(appEndDateStr);
		List<TbInvMaterialOptDTO> list =(List<TbInvMaterialOptDTO>) inv023Service.getMaterialOptDtoData(dataObj);
//		PageList<TbInvMaterialOptDTO> page = (PageList<TbInvMaterialOptDTO>) list;
		//return new JqGridData<TbInvMaterialOptDTO>(page.getPaginator().getTotalCount(), list);
		
		
		//file name
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fileName = "INV023-" + sdf.format(new Date())+".xls";
		
		
		inv023Service.exportExcel(response,  fileName, list);
    } 
}
