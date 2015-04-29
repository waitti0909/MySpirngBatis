package com.foya.noms.web.controller.basic;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbComItemCat;
import com.foya.dao.mybatis.model.TbComItemMain;
import com.foya.noms.dto.basic.ItemMainDTO;
import com.foya.noms.service.basic.CM003Service;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/basic/cm003")
public class CM003Controller extends BaseController {
	
	@Autowired
	private CM003Service cM003Service;
	@Autowired
	private UniqueSeqService usService;

	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/init")
	public String init(HttpServletRequest request, Map<String, Object> model)
			throws Exception {
		HttpSession session = request.getSession();
		
		model.put("mainItemList", loadMainItemList());
		session.setAttribute("mainItemList", loadMainItemList()); // 主項
		
		return "ajaxPage/basic/CM003";
	}
	
	
	/**
	 * 查詢主項列表
	 * @return
	 */
	private List<TbComItemCat> loadMainItemList() {
		return cM003Service.searchMainItemList();
	}
	
	/**
	 * 查詢主項
	 * @param request
	 * @param parentCatId
	 * @return
	 */
	@RequestMapping(value = "/searchMainItem")
	@ResponseBody
	public BaseJasonObject<TbComItemCat> searchMainItem(HttpServletRequest request, String catId){
		
		HttpSession session = request.getSession();
		session.removeAttribute("mainItemList");
		session.setAttribute("mainItemList", loadMainItemList()); // 主項
		log.info("catId: " + catId);
		
		return new BaseJasonObject<TbComItemCat>(loadMainItemList(), AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 查詢中項
	 * @param request
	 * @param parentCatId
	 * @return
	 */
	@RequestMapping(value = "/subItem")
	@ResponseBody
	public BaseJasonObject<TbComItemCat> searchSubItem(HttpServletRequest request, String parentCatId){
		
		log.info("parentCatId: " + parentCatId);
		return new BaseJasonObject<TbComItemCat>(cM003Service.searchSubItem(parentCatId), AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 查詢
	 */
	@RequestMapping(value = "/searchByCat")
	@ResponseBody
	public JqGridData<ItemMainDTO> searchByCat(HttpServletRequest request,
			@RequestParam("mainItemCatId") String mainItemCatId,
			@RequestParam("subItemCatId") String subItemCatId
			) throws Exception{	
		
		log.info("mainItemCatId: " + mainItemCatId);
		log.info("subItemCatId: " + subItemCatId);
		
		HashMap<String,String> dataObj = new HashMap<String,String>();
		dataObj.put("mainItemCatId", mainItemCatId);
		dataObj.put("subItemCatId", subItemCatId);
		
		List<ItemMainDTO> itemList = cM003Service.searchByCat(dataObj);
		PageList<ItemMainDTO> page = (PageList<ItemMainDTO>) itemList;	
		
		if(itemList.size() >= 1000){
			return new JqGridData<ItemMainDTO>(-1, null);
		}else{
			return new JqGridData<ItemMainDTO>(page.getPaginator().getTotalCount(), itemList);
		}
		
	}
	
	/**
	 * 導頁至工項資料新增與修改頁面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addEdit")
	public String addEdit(HttpServletRequest request, Map<String, Object> model) {
		model.put("btn_type", request.getParameter("btn_type")); // 新增/修改
		log.info("addEdit entry btn_type: " + request.getParameter("btn_type"));
		
		if (request.getParameter("btn_type").equals("add")) {
			// 新增
			TbComItemMain tctm = new TbComItemMain();
			model.put("element", tctm);
		} else {
			log.info("view entry item_id: " + request.getParameter("item_id"));
			log.info("view entry main_item: " + request.getParameter("main_item"));
			//檢查是否有PO單使用  (若此工項已有ＰＯ單使用，單位工時，單位，項目名稱不可改變)
			model.put("isPoUsed", cM003Service.searchPoItemUsedByItemId(request.getParameter("item_id")));
			// view or 修改
			model.put("element", cM003Service.selectItemMainItemByItemId(request.getParameter("item_id")));
			model.put("subItemList", cM003Service.searchSubItem(request.getParameter("main_item")));
		}
		
		return "ajaxPage/basic/CM003M";
	}
	
	/**
	 * 儲存新增/修改內容
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save")
	@ResponseBody
	public BaseJasonObject<TbComItemMain> saveNewMainItem(@RequestBody TbComItemMain element) {
		log.info("save entry....");
		
		try {
			log.info("TbComItemMain: " + element.toString());
			
			if("on".equals(element.getENABLED())){
				element.setENABLED("Y");
			}else{
				element.setENABLED("N");
			}
			
			//新增
			if (StringUtils.isBlank(element.getITEM_ID())) {
				element.setITEM_ID(usService.getNextItemMainId());
				element.setCR_USER(getLoginUser().getUsername());
				element.setCR_TIME(new Date());
				if (element.getMGR_FEE() == null) {
					element.setMGR_FEE(new Integer(0));
				}
				element.setSORT(parseItemNo2Sort(element.getITEM_NO()));
				cM003Service.saveNewMainItem(element);
				
				return new BaseJasonObject<TbComItemMain>(true, getMessageDetail("msg.add.success"));
			} else {
			//修改	
				element.setMD_TIME(new Date());
				element.setMD_USER(getLoginUser().getUsername());
				cM003Service.updateMainItem(element);
				
				return new BaseJasonObject<TbComItemMain>(true, getMessageDetail("msg.update.success"));
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<TbComItemMain>(false, e.getMessage());
		}
	}
	
	/**
	 * 由itemNo轉換成sort格式
	 * @param itemNo
	 * @return
	 */
	private String parseItemNo2Sort(String itemNo){
		StringBuffer sb = new StringBuffer();
		
		String[] str = StringUtils.split(itemNo, '.');
		for (String value : str) {
			sb.append(String.format("%02d",Integer.valueOf(value)));
		}
		
		return sb.append(StringUtils.repeat("0", 12 - sb.length())).toString();
	}
	
	/**
	 * 導頁至主項新增頁面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/mainItemAdd")
	public String mainItemAdd(HttpServletRequest request, Map<String, Object> model) {
		log.info("mainItemAdd entry : ");
		TbComItemCat cat = new TbComItemCat();
		cat.setCAT_TYPE("0");
		cat.setPARENT_CAT(new Long(0));
		model.put("cat", cat);
		
		return "ajaxPage/basic/CM003M_1";
	}
	
	/**
	 * 導頁至中項新增頁面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/subItemAdd")
	public String subItemAdd(HttpServletRequest request, Map<String, Object> model) {
		log.info("subItemAdd entry parentId: " + request.getParameter("parentId"));
		TbComItemCat cat = new TbComItemCat();
		cat.setCAT_TYPE("1");
		cat.setPARENT_CAT(Long.parseLong(request.getParameter("parentId")));
		
		model.put("cat", cat);
		model.put("mainItem", cM003Service.selectItemCatByPrimaryKey(request.getParameter("parentId")));
		
		return "ajaxPage/basic/CM003M_2";
	}
	
	/**
	 * 儲存新增/修改項目內容
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/saveNewItemCategory")
	@ResponseBody
	public BaseJasonObject<TbComItemCat> saveNewItemCategory(@RequestBody TbComItemCat cat) {
		log.info("saveNewItemCategory entry....");
		
		try {
			log.info("TbComItemCat: " + cat.toString());
			cM003Service.saveNewItemCategory(cat);
			
			return new BaseJasonObject<TbComItemCat>(cat, getMessageDetail("msg.add.success"));
			
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			return new BaseJasonObject<TbComItemCat>(false, e.getMessage());
		}
	}
	
	/**
	 * 匯出excel
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getExcel", method = RequestMethod.GET) 
    public void getExcel(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("mainItemCatId") String mainItemCatId,
			@RequestParam("subItemCatId") String subItemCatId) { 

		log.info("mainItemCatId: " + mainItemCatId);
		log.info("subItemCatId: " + subItemCatId);
		
		HashMap<String,String> dataObj = new HashMap<String,String>();
		dataObj.put("mainItemCatId", mainItemCatId);
		dataObj.put("subItemCatId", subItemCatId);
		
		List<ItemMainDTO> itemList = cM003Service.searchByCatForExcel(dataObj);
		
		String fileName = "";
		try {
			fileName = new String("工項".getBytes(), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage(), e);
		}
		
		cM003Service.exportExcel(response, "sheet", fileName, itemList);
    } 
}
