package com.foya.noms.web.controller.inv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpModelExample;
import com.foya.dao.mybatis.model.TbComEqpType;
import com.foya.dao.mybatis.model.TbComEqpTypeExample;
import com.foya.dao.mybatis.model.TbInvCategory;
import com.foya.dao.mybatis.model.UTbInvBomExample;
import com.foya.dao.mybatis.model.UTbInvCategoryExample;
import com.foya.dao.mybatis.model.UTbInvMaterialExample;
import com.foya.noms.dto.inv.TbInvBomDTO;
import com.foya.noms.dto.inv.TbInvMaterialDTO;
import com.foya.noms.service.inv.INV027Service;
import com.foya.noms.service.inv.InvPublicUtilService;
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
@RequestMapping(value = "/inv/inv027")
public class INV027Controller extends BaseController {

	@Autowired
	private INV027Service inv027Service;
	@Autowired
	private InvPublicUtilService invPublicUtilService;

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
		session.setAttribute("inv027_EqpModelList", inv027Service.selectEqpModelByExample(new TbComEqpModelExample())); // 設備型態
		session.setAttribute("inv027_Category1List", invPublicUtilService.getAllCategory()); // 大分類
		return "ajaxPage/inv/INV027";
	}

	/**
	 * 查詢設備主檔資料
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/query")
	@ResponseBody
	public JqGridData<TbInvBomDTO> query(HttpServletRequest request) {
		String eqpModelId = request.getParameter("eqp_model_id");
		String eqpTypeId = request.getParameter("eqp_type_id");
		
		UTbInvBomExample example = new UTbInvBomExample();
		UTbInvBomExample.Criteria cr = example.createCriteria();
		if (eqpModelId != null && eqpModelId != "") {
			cr.andEqp_model_idEqualTo(eqpModelId);
		}
		if (eqpTypeId != null && eqpTypeId != "") {
			cr.andEqp_type_idEqualTo(eqpTypeId);
		}
		
		List<TbInvBomDTO> list = inv027Service.selectDistinctInvBomByExample(example);
		PageList<TbInvBomDTO> page = (PageList<TbInvBomDTO>) list;
		return new JqGridData<TbInvBomDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 查詢設備明細資料
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryDetail")
	@ResponseBody
	public JqGridData<TbInvBomDTO> queryDetail(HttpServletRequest request) {
		String eqpModelId = request.getParameter("eqp_model_id");
		String eqpTypeId = request.getParameter("eqp_type_id");
		
		UTbInvBomExample example = new UTbInvBomExample();
		UTbInvBomExample.Criteria cr = example.createCriteria();
		if (eqpModelId != null && eqpModelId != "") {
			cr.andEqp_model_idEqualTo(eqpModelId);
		}
		if (eqpTypeId != null && eqpTypeId != "") {
			cr.andEqp_type_idEqualTo(eqpTypeId);
		}
		
		List<TbInvBomDTO> list = inv027Service.selectInvBomByExample(example);
		PageList<TbInvBomDTO> page = (PageList<TbInvBomDTO>) list;
		return new JqGridData<TbInvBomDTO>(page.getPaginator().getTotalCount(), list);
	}

	/**
	 * 導頁至BOM設備新增與修改頁面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addEdit")
	public String addEdit(HttpServletRequest request, Map<String, Object> model) {
		HttpSession session = request.getSession();
		session.removeAttribute("inv027_ExcelReport");

		if (request.getParameter("btn_type").equals("add")) {
			return "ajaxPage/inv/INV027A";
		} else {
			TbInvBomDTO tb = new TbInvBomDTO();
			tb.setEqp_model_id(request.getParameter("eqp_model_id"));
			tb.setEqp_model_name(request.getParameter("eqp_model_name"));
			tb.setEqp_type_id(request.getParameter("eqp_type_id"));
			tb.setEqp_type_name(request.getParameter("eqp_type_name"));
			model.put("element", tb);

			return "ajaxPage/inv/INV027E";
		}
	}

	/**
	 * 匯入Excel報表
	 * @param mutipartRequest
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
	@ResponseBody
	public BaseJasonObject<TbInvBomDTO> uploadExcel(MultipartHttpServletRequest mutipartRequest) throws Exception {
		HttpSession session = mutipartRequest.getSession();
		session.removeAttribute("inv027_ExcelReport");
		
		try {
			List<TbInvBomDTO> list = null;
			// 讀取報表
			for (Iterator<String> itr = mutipartRequest.getFileNames(); itr.hasNext();) {
				MultipartFile file = mutipartRequest.getFile(itr.next());
				list = inv027Service.readReport(file.getInputStream());
			}

			// 取得對應的料號資料
			boolean saveFlag = true; // 是否可執行儲存
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; list != null && i < list.size(); i++) {
				UTbInvMaterialExample example = new UTbInvMaterialExample();
				example.createCriteria().andMat_noEqualTo(list.get(i).getMat_no());
				List<TbInvMaterialDTO> results = inv027Service.selectInvMaterialByExample(example);

				if (results.size() > 0) {
					list.get(i).setMat_name(results.get(0).getMat_name());
					list.get(i).setCatg1_code(results.get(0).getCatg1_code());
					list.get(i).setCatg1_name(results.get(0).getCatg1_name());
					list.get(i).setCatg2_code(results.get(0).getCatg2_code());
					list.get(i).setCatg2_name(results.get(0).getCatg2_name());
					list.get(i).setCatg3_code(results.get(0).getCatg3_code());
					list.get(i).setCatg3_name(results.get(0).getCatg3_name());
					if (!map.containsKey(list.get(i).getMat_no())) {
						list.get(i).setCheck_status("Y");
						list.get(i).setCheck_status_name("通過");
					} else {
						saveFlag = false;
						list.get(i).setCheck_status("N");
						list.get(i).setCheck_status_name("匯入檔案有重複的料號");
					}
					if (list.get(i).getQty() <= 0) {
						saveFlag = false;
						list.get(i).setCheck_status("N");
						list.get(i).setCheck_status_name("數量必須大於零");
					}
					
					map.put(list.get(i).getMat_no(), list.get(i).getMat_name());
				} else {
					saveFlag = false;
					list.get(i).setMat_name(list.get(i).getMat_no());
					list.get(i).setCheck_status("N");
					list.get(i).setCheck_status_name("料號不存在");
				}
			}

			session.setAttribute("inv027_ExcelReport", list);
			if (saveFlag) {
				return new BaseJasonObject<TbInvBomDTO>(true, getMessageDetail("msg.add.success"));
			} else {
				return new BaseJasonObject<TbInvBomDTO>(false, "error");
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<TbInvBomDTO>(false, e.getMessage());
		}
	}

	/**
	 * 取得Excel匯入結果
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryExcel")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public JqGridData<Object> queryExcel(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		String flag = request.getParameter("flag");
		if (flag == null || flag.equals("")) {
			session.removeAttribute("inv027_ExcelReport");
		}

		List<TbInvBomDTO> list = (List<TbInvBomDTO>) session.getAttribute("inv027_ExcelReport");
		if (list == null) {
			list = new ArrayList<TbInvBomDTO>();
		}
		
		return this.getJqGridData(request.getParameter("page"), request.getParameter("size"), new ArrayList<Object>(list));
	}

	/**
	 * 儲存新增內容
	 * @param request
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/insert")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public BaseJasonObject<TbInvBomDTO> insert(HttpServletRequest request, @RequestBody TbInvBomDTO record) {
		HttpSession session = request.getSession();

		try {
			List<TbInvBomDTO> list = (List<TbInvBomDTO>) session.getAttribute("inv027_ExcelReport");

			// 檢核資料是否已存在
			boolean saveFlag = true;
			for (int i = 0; i < list.size(); i++) {
				TbInvBomDTO tb = list.get(i);
				
				if (tb.getCheck_status() == null || tb.getCheck_status().equals("N")) {
					return new BaseJasonObject<TbInvBomDTO>(false, "error匯入檔案檢核有誤，無法進行儲存");
				}
				
				UTbInvBomExample example = new UTbInvBomExample();
				UTbInvBomExample.Criteria cr = example.createCriteria();
				cr.andEqp_model_idEqualTo(record.getEqp_model_id());
				cr.andEqp_type_idEqualTo(record.getEqp_type_id());
				cr.andMat_noEqualTo(tb.getMat_no());
				int count = inv027Service.countInvBomByExample(example);
				if (count > 0) {
					saveFlag = false;
					tb.setCheck_status("N");
					tb.setCheck_status_name(getMessageDetail("msg.add.dataExists"));
				}
			}
			if (saveFlag == false) {
				return new BaseJasonObject<TbInvBomDTO>(false, "error匯入資料已存在，請重新上傳");
			}

			// 儲存新增資料
			for (int i = 0; i < list.size(); i++) {
				TbInvBomDTO tb = list.get(i);
				tb.setEqp_model_id(record.getEqp_model_id());
				tb.setEqp_type_id(record.getEqp_type_id());
				tb.setEffective_date(new Date());
				tb.setCr_user(getLoginUser().getUsername());
				tb.setCr_time(new Date());
				tb.setMd_user(getLoginUser().getUsername());
				tb.setMd_time(new Date());
				inv027Service.insertInvBomSelective(tb);
			}

			return new BaseJasonObject<TbInvBomDTO>(true, getMessageDetail("msg.add.success"));
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<TbInvBomDTO>(false, e.getMessage());
		}
	}

	/**
	 * 儲存修改內容
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public BaseJasonObject<TbInvBomDTO> update(HttpServletRequest request) {
		String btnType = request.getParameter("btn_type");
		String eqpModelId = request.getParameter("eqp_model_id");
		String eqpTypeId = request.getParameter("eqp_type_id");
		String matNo = request.getParameter("mat_no");
		String qty = request.getParameter("qty");
		String failureDate = request.getParameter("failure_date");
		
		if (btnType == null || btnType.equals("")) {
			return new BaseJasonObject<TbInvBomDTO>(false, "未指定資料儲存類型");
		}

		// 檢核資料是否已存在
		UTbInvBomExample example = new UTbInvBomExample();
		UTbInvBomExample.Criteria cr = example.createCriteria();
		cr.andEqp_model_idEqualTo(eqpModelId);
		cr.andEqp_type_idEqualTo(eqpTypeId);
		cr.andMat_noEqualTo(matNo);
		int count = inv027Service.countInvBomByExample(example);

		String message = null;
		if (btnType == null || btnType.equals("")) {
			return new BaseJasonObject<TbInvBomDTO>(false, "未指定資料儲存類型");
		} else if (btnType.equals("add")) {
			// 新增
			if (count > 0) {
				return new BaseJasonObject<TbInvBomDTO>(false, getMessageDetail("msg.add.dataExists"));
			}

			TbInvBomDTO tb = new TbInvBomDTO();
			tb.setEqp_model_id(eqpModelId);
			tb.setEqp_type_id(eqpTypeId);
			tb.setMat_no(matNo);
			tb.setQty(Integer.valueOf(qty));
			tb.setEffective_date(new Date());
			tb.setCr_user(getLoginUser().getUsername());
			tb.setCr_time(new Date());
			tb.setMd_user(getLoginUser().getUsername());
			tb.setMd_time(new Date());
			inv027Service.insertInvBomSelective(tb);
			
			message = getMessageDetail("msg.add.success");
		} else if (btnType.equals("edit")) {
			// 修改
			if (count == 0) {
				return new BaseJasonObject<TbInvBomDTO>(false, "資料不存在無法更新");
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			TbInvBomDTO tb = new TbInvBomDTO();
			tb.setEqp_model_id(eqpModelId);
			tb.setEqp_type_id(eqpTypeId);
			tb.setMat_no(matNo);
			tb.setQty(Integer.valueOf(qty));
			if (failureDate != null && !failureDate.equals("")) {
				try {
					tb.setFailure_date(sdf.parse(failureDate));
				} catch (Exception e) {
					log.error(ExceptionUtils.getFullStackTrace(e));
					return new BaseJasonObject<TbInvBomDTO>(false, e.getMessage());
				}
			}
			tb.setMd_user(getLoginUser().getUsername());
			tb.setMd_time(new Date());
			inv027Service.updateInvBomByPrimaryKeySelective(tb);
			
			message = getMessageDetail("msg.update.success");
		}
		return new BaseJasonObject<TbInvBomDTO>(true, message);
	}

	/**
	 * 取得設備型態
	 * @param eqpModelId
	 * @return
	 */
	@RequestMapping(value = "/search/eqpType")
	@ResponseBody
	public Map<String, String> getEqpType(
			@RequestParam("eqpModelId") String eqpModelId) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		
		TbComEqpModelExample example = new TbComEqpModelExample();
		example.createCriteria().andEQP_MODEL_IDEqualTo(eqpModelId);
		List<TbComEqpModel> list = inv027Service.selectEqpModelByExample(example);
		
		if (list != null && list.size() > 0) {
			TbComEqpTypeExample example2 = new TbComEqpTypeExample();
			example2.createCriteria().andEQP_TYPE_IDEqualTo(list.get(0).getEQP_TYPE_ID());
			List<TbComEqpType> list2 = inv027Service.selectEqpTypeByExample(example2);
			for (int i = 0; i < list2.size(); i++) {
				TbComEqpType tb = list2.get(i);
				map.put(tb.getEQP_TYPE_ID(), tb.getEQP_NAME());
			}
		}

		return map;
	}

	/**
	 * 取得中分類
	 * @param catg1Code
	 * @return
	 */
	@RequestMapping(value = "/search/category2")
	@ResponseBody
	public Map<String, String> getCategory2(
			@RequestParam("catg1_code") String catg1Code) {
		List<TbInvCategory> list = invPublicUtilService.getCategoryMidByCatgcode1(catg1Code);

		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			TbInvCategory tb = list.get(i);
			map.put(tb.getCatg2_code(), tb.getCatg2_name());
		}

		return map;
	}

	/**
	 * 取得小分類
	 * @param catg1Code
	 * @param catg2Code
	 * @return
	 */
	@RequestMapping(value = "/search/category3")
	@ResponseBody
	public Map<String, String> getCategory3(
			@RequestParam("catg1_code") String catg1Code,
			@RequestParam("catg2_code") String catg2Code) {

		UTbInvCategoryExample example = new UTbInvCategoryExample();
		UTbInvCategoryExample.Criteria cr = example.createCriteria();
		cr.andCatg1_codeEqualTo(catg1Code);
		cr.andCatg2_codeEqualTo(catg2Code);
		List<TbInvCategory> list = inv027Service.selectInvCategoryByExample(example);

		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			TbInvCategory tb = list.get(i);
			map.put(tb.getCatg3_code(), tb.getCatg3_name());
		}

		return map;
	}

	/**
	 * 取得料號
	 * @param catg1Code
	 * @param catg2Code
	 * @param catg3Code
	 * @return
	 */
	@RequestMapping(value = "/search/material")
	@ResponseBody
	public Map<String, String> getMaterial(
			@RequestParam("catg1_code") String catg1Code,
			@RequestParam("catg2_code") String catg2Code,
			@RequestParam("catg3_code") String catg3Code) {

		UTbInvMaterialExample example = new UTbInvMaterialExample();
		UTbInvMaterialExample.Criteria cr = example.createCriteria();
		cr.andCatg1_codeEqualTo(catg1Code);
		cr.andCatg2_codeEqualTo(catg2Code);
		cr.andCatg3_codeEqualTo(catg3Code);
		List<TbInvMaterialDTO> list = inv027Service.selectInvMaterialByExample(example);

		Map<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			TbInvMaterialDTO tb = list.get(i);
			map.put(tb.getMat_no(), tb.getMat_name());
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
