package com.foya.noms.web.controller.ls;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbLsCollectUnit;
import com.foya.dao.mybatis.model.TbLsCollectUnitSub;
import com.foya.noms.service.ls.LsCommonService;
import com.foya.noms.web.controller.BaseController;


@Controller
public class LsCommonController extends BaseController {
	
	@Autowired
	private LsCommonService lsCommonService;
	
	
	@RequestMapping(value = "/ls/common/allCollectUnit")
	@ResponseBody
	public  List<TbLsCollectUnit> getAllCollectUnit() {
		return lsCommonService.getAllCollectionUnit();
	}
	
	
	@RequestMapping(value = "/ls/common/allCollectUnitSub")
	@ResponseBody
	public  List<TbLsCollectUnitSub> getAllCollectUnitSub() {
		return lsCommonService.getAllCollectionUnitSub();
	}
	
	
	
}
