package com.foya.noms.web.controller.pay;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.pay.Pay013Service;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/pay")
public class PAY013Controller extends BaseController {
	
	@Autowired
	private Pay013Service pay013Service;
	@Autowired
	private PersonnelService personnelService;
	
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay013/init")
	public String init(HttpServletRequest request,Map<String, Object> model) throws Exception{
		model.put("appUserName", getLoginUser().getChName());
		model.put("appUser", getLoginUser().getUserId());
		return "ajaxPage/pay/PAY013";
	}
	
	/**
	 * 查詢
	 */
	@RequestMapping(value = "/pay013/search")
	@ResponseBody
	public JqGridData<TbPayCashRequirementDTO> search(
			@RequestParam(value = "yearMonth", required = false)String yearMonth) throws Exception{
		List<TbPayCashRequirementDTO> cashList=null;
		try{
			cashList=pay013Service.selectCashAllData(yearMonth);
			PageList<TbPayCashRequirementDTO> page = (PageList<TbPayCashRequirementDTO>) cashList;
			return new JqGridData<TbPayCashRequirementDTO>(page.getPaginator().getTotalCount(),cashList);
		}catch(NullPointerException e){
			return new JqGridData<TbPayCashRequirementDTO>(10,cashList);
		}
	}
	
	/**
	 * 查詢分攤資訊筆數
	 */
	@RequestMapping(value = "/pay013/getCount")
	@ResponseBody
	public BaseJasonObject<Object> getCount(
			HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam(value = "yearMonth", required = false) String yearMonth) {
		return new BaseJasonObject<Object>(true, String.valueOf(pay013Service.countPayByExample(yearMonth)));
	}
	
	/**
	 * 刪除TB_PAY_REPARTITION已分攤資料
	 */
	@RequestMapping(value = "/pay013/delData")
	@ResponseBody
	public BaseJasonObject<Object> delData(
			@RequestParam("yearMonth") String yearMonth) {
		BaseJasonObject<Object> json=null;
		 if("success".equals(pay013Service.deleteByExample(yearMonth)))
			 json=new BaseJasonObject<Object>(true,"");
		 else
			 json=new BaseJasonObject<Object>(false,"");
		 return json;
	}
	
	/**
	 * 產生分攤單號
	 */
	@RequestMapping(value = "/pay013/getRptNo")
	@ResponseBody
	public BaseJasonObject<Object> getRptNo(HttpServletRequest request,
			Map<String, Object> model, @RequestParam("appDate") String appDate)
			throws Exception {
		String rptNo = "";
		try {
			rptNo = pay013Service.selectRptNoToday(appDate);
		} catch (NullPointerException e) {
			rptNo = "產生分攤單號為空值";
		}
		return new BaseJasonObject<Object>(true, rptNo);
	}

	/**
	 * 存儲分攤資料
	 */
	@RequestMapping(value = "/pay013/saveData")
	@ResponseBody
	public BaseJasonObject<Object> saveData(HttpServletRequest request,
			Map<String, Object> model,
			@RequestParam("repartitionNo") String repartitionNo,
			@RequestParam("paymentReqNo") String paymentReqNo,
			@RequestParam("paymentReqItemNo") String paymentReqItemNo,
			@RequestParam("paymentSeqNbr") String paymentSeqNbr,
			@RequestParam("locationId") String locationId,
			@RequestParam("yearMonth") String yearMonth,
			@RequestParam("repartitionAmt") String repartitionAmt,
			@RequestParam("siteId") String siteId,
			@RequestParam("expType") String expType,
			@RequestParam("appUser") String appUser,
			@RequestParam("appDate") Date appDate) throws Exception{
		String returnMsg="success",payMsg="";
		BaseJasonObject<Object> json=null;
		// 寫入資料
		payMsg = pay013Service.insertPayRepartition(repartitionNo,
				paymentReqNo, paymentReqItemNo, paymentSeqNbr, locationId,
				yearMonth, repartitionAmt, siteId, expType, appUser, appDate);
		if("fail".equals(payMsg))
			returnMsg="基站金額分攤記錄檔寫入錯誤";
		if("success".equals(returnMsg))
			 json=new BaseJasonObject<Object>(true,repartitionNo);
		 else
			 json=new BaseJasonObject<Object>(false,returnMsg);
		return json;
	}

}
