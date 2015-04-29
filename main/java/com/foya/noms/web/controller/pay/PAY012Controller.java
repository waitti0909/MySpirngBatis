package com.foya.noms.web.controller.pay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbPayExpenseEstimate;
import com.foya.exception.NomsException;
import com.foya.noms.dto.pay.TbPayLocationInfoDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDTO;
import com.foya.noms.service.auth.PersonnelService;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.pay.PAY012Service;
import com.foya.noms.service.pay.PayPublicUtilService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;


@Controller
@RequestMapping(value = "/pay")
public class PAY012Controller extends BaseController {
	@Autowired
	private PayPublicUtilController payPublicUtilController;
	@Autowired
	private PAY012Service pay012Service;
	@Autowired
	private DomainService domainService;
	@Autowired
	private PersonnelService personnelService;
	@Autowired
	private PayPublicUtilService payPublicUtilService;
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay012/init")
	public String init(HttpServletRequest request,
			Map<String, Object> model) throws Exception{
		model.put("estimateType", payPublicUtilController.getPayLookupByType(request, "estimate_type"));
		model.put("appUserName", getLoginUser().getChName());
		model.put("appUser", getLoginUser().getUserId());
		model.put("saveType", "N");//預設儲存類型
		return "ajaxPage/pay/PAY012";
	}
	
	/**
	 * 匯出excel
	 * @return
	 */
	@RequestMapping(value = "/pay012/genExcel", method = RequestMethod.GET) 
	public void getExcel(HttpServletRequest request,HttpServletResponse response,
			Map<String, Object> model,
			@RequestParam("type") String type,
			@RequestParam("rowArray") String rowArray) throws Exception { 
		//file name
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String fileName = "PAY012-" + sdf.format(new Date())+".xls";
		pay012Service.exportExcel(response, "sheet", fileName, rowArray, type);
    } 
	
	/**
	 * 取得預估單號
	 */
	@RequestMapping(value = "/pay012/getEstimateNo")
	@ResponseBody
	public BaseJasonObject<Object> getEstimateNo(@RequestParam("type") String type){
		return new BaseJasonObject<Object>(true,pay012Service.getEstimateNo(type));
	}
	
	/**
	 * 檢核預估該年月區間是否有值
	 */
	@RequestMapping(value = "/pay012/selectEstimate")
	@ResponseBody
	public BaseJasonObject<TbPayExpenseEstimate> selectEstimate(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "startVal", required = false) String startVal,
			@RequestParam(value = "endVal", required = false) String endVal) throws Exception{
		return new BaseJasonObject<TbPayExpenseEstimate>(pay012Service.selectEstimate(type, startVal, endVal), AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	/**
	 * 檢核預估該年月區間是否有值
	 */
	@RequestMapping(value = "/pay012/deleteEstimate")
	@ResponseBody
	public boolean deleteEstimate(
			@RequestParam(value = "estimateNo", required = false) String estimateNo) throws Exception{
		try{
			pay012Service.deleteEstimate(estimateNo);
		}catch (NomsException throwable) {
			log.error(throwable.getMessage());
			throwable.printStackTrace();
			return false;
		}
		catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 新增資料
	 */
	@RequestMapping(value = "/pay012/processEstimate")
	@ResponseBody
	public boolean processEstimate(@RequestParam("detail") String detail,
			@RequestParam("master") String master,
			@RequestParam("estNoArray") String estNoArray) {
		Date today=payPublicUtilService.getToday();
			try{
				pay012Service.processEstimate(detail,master,today,estNoArray);
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
				return false;
			}
			catch(Exception e){
				log.error(e.getMessage());
				e.printStackTrace();
				return false;
			}
		return true;
	}
	
	/**
	 * 租金預估
	 */ 
	@RequestMapping(value = "/pay012/selectReeData")
	@ResponseBody
	public JqGridData<TbPayLocationInfoDTO> selectReeData(
			@RequestParam(value = "startVal", required = false) String startVal,
			@RequestParam(value = "endVal", required = false) String endVal,
			@RequestParam(value = "appDate", required = false) Date appDate) throws Exception{
		List<TbPayLocationInfoDTO> reeList=null;
		try{
			reeList=pay012Service.selectReeData(startVal, endVal,appDate);
			PageList<TbPayLocationInfoDTO> page = (PageList<TbPayLocationInfoDTO>) reeList;
			return new JqGridData<TbPayLocationInfoDTO>(page.getPaginator().getTotalCount(),reeList);
		}catch(NullPointerException e){
			log.debug(e.getMessage());
			return new JqGridData<TbPayLocationInfoDTO>(10,reeList);
		}
	}
	
	/**
	 * 電費預估
	 */ 
	@RequestMapping(value = "/pay012/selectEleData")
	@ResponseBody
	public JqGridData<TbPayPaymentRequestDTO> selectEleData(
			@RequestParam(value = "startVal", required = false) String startVal,
			@RequestParam(value = "endVal", required = false) String endVal) throws Exception{
		List<TbPayPaymentRequestDTO> reeList=null;
		try{
			reeList=pay012Service.selectEleData(startVal, endVal);
			PageList<TbPayPaymentRequestDTO> page = (PageList<TbPayPaymentRequestDTO>) reeList;
			return new JqGridData<TbPayPaymentRequestDTO>(page.getPaginator().getTotalCount(),reeList);
		}catch(NullPointerException e){
			return new JqGridData<TbPayPaymentRequestDTO>(10,reeList);
		}
	}
	
	/**
	 * 專線預估
	 */ 
	@RequestMapping(value = "/pay012/selectLieData")
	@ResponseBody
	public JqGridData<TbPayPaymentRequestDTO> selectLieData(
			@RequestParam(value = "startVal", required = false) String startVal,
			@RequestParam(value = "endVal", required = false) String endVal) throws Exception{
		List<TbPayPaymentRequestDTO> reeList=null;
		try{
			reeList=pay012Service.selectLieData(startVal, endVal);
			PageList<TbPayPaymentRequestDTO> page = (PageList<TbPayPaymentRequestDTO>) reeList;
			return new JqGridData<TbPayPaymentRequestDTO>(page.getPaginator().getTotalCount(),reeList);
		}catch(NullPointerException e){
			return new JqGridData<TbPayPaymentRequestDTO>(10,reeList);
		}
	}
}
