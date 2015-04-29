package com.foya.noms.web.controller.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.noms.dto.basic.CompanyDTO;
import com.foya.noms.service.common.CompanyQueryService;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/common/companyQuery")
public class CompanyQueryController extends BaseController {

	@Autowired
	private CompanyQueryService companyQueryService;
	

	@RequestMapping(value = "/initLoad")
	public String initPersonnelPage(HttpServletRequest request, Map<String, Object> model,
			CompanyDTO company) {
		String targetFrameId = request.getParameter("targetFrameId");
		if (targetFrameId != null) {
			model.put("targetFrameId", targetFrameId);
		}
		model.put("company", company);
		return "/ajaxPage/common/CompanyQuery";
	}

	@RequestMapping(value = "/search")
	@ResponseBody
	public JqGridData<CompanyDTO> searchPersonnel(@RequestParam("ubnNo") String ubnNo,
			@RequestParam("erpNo") String erpNo, @RequestParam("comName") String comName) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("ubnNo", ubnNo);
		map.put("erpNo", erpNo);
		map.put("comName", comName);
		List<CompanyDTO> companyList = companyQueryService.getCompanyList(map);
		PageList<CompanyDTO> page = (PageList<CompanyDTO>) companyList;
		return new JqGridData<>(page.getPaginator().getTotalCount(), companyList);
	}

	
}
