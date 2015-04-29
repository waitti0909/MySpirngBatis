package com.foya.noms.web.controller.inv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.noms.dto.inv.TbInvMaterialDTO;
import com.foya.noms.service.inv.INV022Service;
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
 * <td>2014/11/03</td>
 * <td>新建檔案</td>
 * <td>Gran</td>
 * </tr>
 * </table>
 * 
 * @author Gran
 * 
 */
@Controller
@RequestMapping(value = "/inv/inv022SP")
public class INV022SPController extends BaseController {
	
	@Inject
	private InvPublicUtilController invPublicUtilController;
	@Autowired
	private INV022Service inv022Service;

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
		
		String targetFrameId = "";
		if(request.getParameter("targetFrameId")!=null && request.getParameter("targetFrameId").length()>0){
			targetFrameId = request.getParameter("targetFrameId");
		}
		model.put("targetFrameId", targetFrameId);
		model.put("catgCode1Select", invPublicUtilController.initCategorySelect(request));

		return "ajaxPage/inv/INV022SP";
	}
	
	/**
	 * 查詢
	 */
	@RequestMapping(value = "/Search")
	@ResponseBody
	public JqGridData<TbInvMaterialDTO> search(HttpServletRequest request,
			@RequestParam("catgCode1") String catgCode1,
			@RequestParam("catgCode2") String catgCode2,
			@RequestParam("catgCode3") String catgCode3
			) throws Exception{	
		
		HashMap<String,String> dataObj = new HashMap<String,String>();
		dataObj.put("catgCode1", catgCode1);
		dataObj.put("catgCode2", catgCode2);
		dataObj.put("catgCode3", catgCode3);
		
		List<TbInvMaterialDTO> InvMaterialList = inv022Service.search(dataObj);
		PageList<TbInvMaterialDTO> page = (PageList<TbInvMaterialDTO>) InvMaterialList;		
	
		return new JqGridData<TbInvMaterialDTO>(page.getPaginator().getTotalCount(), InvMaterialList);
	}	

}
