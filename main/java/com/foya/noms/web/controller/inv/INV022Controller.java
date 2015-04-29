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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.noms.dto.inv.TbInvMaterialDTO;
import com.foya.noms.service.common.PoiService;
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
@RequestMapping(value = "/inv/inv022")
public class INV022Controller extends BaseController {
	
	@Inject
	private InvPublicUtilController invPublicUtilController;
	@Autowired
	private INV022Service inv022Service;
	@Autowired
	private PoiService poiService;

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
		model.put("catg1Select", invPublicUtilController.initCategorySelect(request));
		return "ajaxPage/inv/INV022";
	}
	
	/**
	 * 查詢
	 */
	@RequestMapping(value = "/Search")
	@ResponseBody
	public JqGridData<TbInvMaterialDTO> search(HttpServletRequest request,
			@RequestParam("catgCode1") String catgCode1,
			@RequestParam("catgCode2") String catgCode2,
			@RequestParam("catgCode3") String catgCode3,
			@RequestParam("matNo") String matNo
			) throws Exception{	
		
		HashMap<String,String> dataObj = new HashMap<String,String>();
		dataObj.put("catgCode1", catgCode1);
		dataObj.put("catgCode2", catgCode2);
		dataObj.put("catgCode3", catgCode3);
		dataObj.put("matNo", matNo);
		
		List<TbInvMaterialDTO> InvMaterialList = inv022Service.search(dataObj);

		PageList<TbInvMaterialDTO> page = (PageList<TbInvMaterialDTO>) InvMaterialList;		

		if(InvMaterialList.size() >= 1000){
			return new JqGridData<TbInvMaterialDTO>(-1, null);
		}else{
			return new JqGridData<TbInvMaterialDTO>(page.getPaginator().getTotalCount(), InvMaterialList);
		}
	}

	/**
	 * 匯出excel
	 * @return
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "/getExcel", method = RequestMethod.GET) 
    public void getExcel(HttpServletRequest request, HttpServletResponse response, 
    		@RequestParam("catgCode1") String catgCode1,
    		@RequestParam("catgCode2") String catgCode2,
			@RequestParam("catgCode3") String catgCode3,
			@RequestParam("matNo") String matNo) { 

		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("catgCode1", catgCode1);
		dataObj.put("catgCode2", catgCode2);
		dataObj.put("catgCode3", catgCode3);
		dataObj.put("matNo", matNo);		
		dataObj.put("orderCatg1", true);
		
		List<TbInvMaterialDTO> InvMaterialList = inv022Service.getMaterialDtoData(dataObj);
		
		//file name
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fileName = "INV022-" + sdf.format(new Date());
		
		inv022Service.exportExcel(response, "sheet", fileName, InvMaterialList);
    } 
	
}
