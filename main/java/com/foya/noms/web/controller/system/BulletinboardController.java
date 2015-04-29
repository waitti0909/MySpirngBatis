package com.foya.noms.web.controller.system;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbSysBulletinboard;
import com.foya.noms.dto.system.BulletinboardDTO;
import com.foya.noms.service.system.BulletinboardService;
import com.foya.noms.util.DateUtils;
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
 * <td>2014/9/16</td>
 * <td>新建檔案</td>
 * <td>Zona</td>
 * </tr>
 * </table> 
 * 
 * @author Zona
 *
 */
@Controller
public class BulletinboardController extends BaseController {
	
	@Autowired
	private BulletinboardService bulletinboardService;
	
	
	/**
	 * 公告欄維護
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sys/bulletboard/")
	public String bulletBoardMaint(Map<String, Object> model) {
		model.put("bulletinType", bulletinboardService.getBulletinType());		
		return "ajaxPage/system/bulletinBoard";
	}
	
	
	/**
	 * 顯示公佈欄
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bulletboard/")
	public String getBulletboard(Map<String, Object> model) {
		Date date = new Date();       
		Timestamp nousedate = new Timestamp(date.getTime());
		model.put("bulletinboardList", bulletinboardService.getBulletinboardByLoginDate(nousedate));
		return "ajaxPage/system/bulletinBoardL";
	}
	
	
	/**
	 * 顯示公佈的詳細資訊
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sysBulletinboard/contents/")
	public String getBulletboardDetail(@RequestParam("b_ID") BigDecimal b_ID,Map<String, Object> model) {
		model.put("bulletinboardDetail", bulletinboardService.getBulletinboardById(b_ID));
		return "ajaxPage/system/bulletinBoardD";
	}
	
	
	/**
	 * 根據查詢條件查詢公佈欄
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sysBulletinboardSearch/")
	public @ResponseBody JqGridData<BulletinboardDTO> bulletboardSearch(HttpServletRequest request) {	
		String searchStartDate = request.getParameter("startDate");
		String searchEndDate = request.getParameter("endDate");
		String searchSubject = request.getParameter("subject");
		String searchType = request.getParameter("bBoardType");
		String sort = request.getParameter("sort");
		String order = request.getParameter("order");		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("subject",searchSubject);
		map.put("startDate",searchStartDate);
		map.put("endDate",searchEndDate);
		map.put("types",searchType);
		map.put("sort", sort);
		map.put("order", order);
		
		List<BulletinboardDTO> rsList = bulletinboardService.getBulletinboardByCond(map);	
		
		//用於頁面之jqGrid，公佈人員請改由顯示CHN_NM，當該欄位沒有值的時候再改取ENG_NM
		for (int i = 0; i < rsList.size(); i++) {
			if (StringUtils.isEmpty(rsList.get(i).getChnNM())) {
				rsList.get(i).setChnNM(rsList.get(i).getEngNM());
			}
		}
		
		PageList<BulletinboardDTO> page= (PageList<BulletinboardDTO>) rsList;		
		return new JqGridData<>(page.getPaginator().getTotalCount(), rsList);
	}
	
	/**
	 * 公佈欄新增與修改
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sysBulletinboardAddEdit/")
	public String bulletboardAdd(HttpServletRequest request,Map<String, Object> model) {	
		//有公告欄ID表示為修改，否為新增
		if(StringUtils.isNotEmpty(request.getParameter("bulletinID"))){
			BigDecimal bulletinId = BigDecimal.valueOf(Long.parseLong(request.getParameter("bulletinID")));
			model.put("editBulletin", bulletinboardService.getBulletinboardById(bulletinId));
			if(StringUtils.equals(request.getParameter("btnType"),"view")){
				model.put("showType", "view");
			}else{
				model.put("showType", "edit");
			}			
		}else{
			model.put("editBulletin", new BulletinboardDTO());
			model.put("showType", "add");
		}
		
		model.put("fileType", "NOTYPE");	// 預設附件不分類
		model.put("bulletinType", bulletinboardService.getBulletinType()); //全部公告類型
		return "ajaxPage/system/bulletinBoardM";
	}
	
	/**
	 * 儲存公佈欄新增與修改
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sysBulletinboard/AddEdit/Save/")
	public @ResponseBody String bulletboardAddSave(HttpServletRequest request) {		
		TbSysBulletinboard bulletinboard = new TbSysBulletinboard();
		String bulletinSubject = request.getParameter("addEdit_SUBJECT"); //主旨(必填)
		String bulletinContents = request.getParameter("addEdit_CONTENTS"); //內容(必填)
		
		int bulletinType = Integer.valueOf(request.getParameter("addEdit_TYPES")); //類型
		bulletinboard.setTYPES(bulletinType);
		
		
		int bulletinPriority = Integer.valueOf(request.getParameter("addEdit_PRIORITY")); //重要性
		bulletinboard.setPRIORITY(bulletinPriority);
			
		String currentUserName = getLoginUser().getUsername();
		bulletinboard.setSUBJECT(bulletinSubject);
		bulletinboard.setSTARTDATE(DateUtils.parse(request.getParameter("addEdit_STARTDATE"), "yyyy/MM/dd")); //起始日(必填)
		bulletinboard.setCONTENTS(bulletinContents);
		
		//是否輸入結束日，並將時分秒設為23:59:59
		if(StringUtils.isNotEmpty(request.getParameter("addEdit_ENDDATE"))){
			String enddate = request.getParameter("addEdit_ENDDATE")+" "+"23:59:59";
			bulletinboard.setENDDATE(DateUtils.parse(enddate, "yyyy/MM/dd hh:mm:ss"));
		}
			
		//當修改時有公告欄ID，新增則沒有
		BigDecimal bulletinId = null;
		if(StringUtils.isNotEmpty(request.getParameter("addEdit_ID")) && NumberUtils.isNumber(request.getParameter("addEdit_ID"))){
			//修改
			bulletinId = new BigDecimal(request.getParameter("addEdit_ID")); //公告欄ID
			bulletinboard.setBULLETIN_ID(bulletinId);
			bulletinboard.setMD_USER(currentUserName);
			bulletinboard.setMD_TIME(new Date());
			bulletinboardService.editBulletinboard(bulletinboard);
		} else {
			//新增
			bulletinboard.setCR_USER(currentUserName);
		    bulletinboard.setCR_TIME(new Date());
		    bulletinboardService.addBulletinboard(bulletinboard);
		    bulletinId = bulletinboard.getBULLETIN_ID();
		}
		
		return bulletinId.toString();
	}
	
	
	/**
	 * 公告欄刪除
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/sysBulletinboard/Delete/")
	public @ResponseBody String bulletboardDelete(@RequestParam("bulletinID") String bulletinID) {
		  String[] bulletinIdArray = bulletinID.split(","); 
		  bulletinboardService.deleteBulletinboard(bulletinIdArray);
		  return AJAX_SUCCESS;
	}
	
}
