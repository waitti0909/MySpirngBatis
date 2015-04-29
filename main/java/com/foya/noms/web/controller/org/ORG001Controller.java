package com.foya.noms.web.controller.org;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbOrgPosition;
import com.foya.exception.DataExistsException;
import com.foya.noms.service.org.ORG001Service;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/org")
public class ORG001Controller extends BaseController {

	@Autowired
	private ORG001Service org001Service;
	
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ORG001")
	public String positionInit(HttpServletRequest request,
			Map<String, Object> model) {
		return "ajaxPage/org/ORG001";
	}

	/**
	 * 查詢頁面
	 */
	@RequestMapping(value = "/ORG001/search")
	@ResponseBody
	public JqGridData<TbOrgPosition> searchPositionByCodeName(
			@RequestParam("positionCode") String code,
			@RequestParam("positionName") String name) {
		List<TbOrgPosition>	positionList = org001Service.searchPositionByCodeName(code, name);
		PageList<TbOrgPosition> page= (PageList<TbOrgPosition>) positionList;
		return new JqGridData<>(page.getPaginator().getTotalCount(),positionList);
	}

	/**
	 * 新增頁面
	 */
	@RequestMapping(value = "/ORG001/add")
	public String addPosition(HttpServletRequest request) {
		return "ajaxPage/org/ORG001M";
	}

	/**
	 * 將資料寫入後導向成功頁面
	 */
	@RequestMapping(value = "/ORG001/addSave")
	@ResponseBody
	public boolean saveNewPosition(TbOrgPosition position) {
		boolean result =false;
		try{
			result = org001Service.saveNewPosition(position,this.getLoginUser().getUsername());
		}catch(DataExistsException ex){
			result =false;
		}
		return result;
	}

	/**
	 * 修改頁面
	 */
	@RequestMapping(value = "/ORG001/update")
	public String getPositionById(@RequestParam("showType") String showType,@RequestParam("positionID") String positionID,
			HttpServletRequest request, Map<String, Object> model) {
		model.put("showType", showType);
		model.put("position", org001Service.getPositionById(positionID));
		return "ajaxPage/org/ORG001M";
	}
	
	
	/**
	 * 將資料寫入後導向成功頁面
	 */
	@RequestMapping(value = "/ORG001/updateSave")
	@ResponseBody
	public boolean saveUpdatePosition(TbOrgPosition position,
			HttpServletRequest request) {
		boolean result = org001Service.saveUpdatePosition(position, getLoginUser().getUsername(), getLoginUser().getUserId());
		return result;
	}
	
}
