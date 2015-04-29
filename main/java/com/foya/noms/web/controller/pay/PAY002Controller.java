package com.foya.noms.web.controller.pay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.foya.dao.mybatis.model.TbLsLocElec;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbPayElectricity;
import com.foya.dao.mybatis.model.TbPayFileFormatDtl;
import com.foya.exception.NomsException;
import com.foya.noms.dto.pay.OriginalPowerBillDTO;
import com.foya.noms.dto.pay.TbLsLocElecDTO;
import com.foya.noms.dto.pay.TbPayElectricityDTO;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.pay.Pay002Service;
import com.foya.noms.service.pay.PayPublicUtilService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.util.JqGridData;
import com.foya.noms.web.controller.BaseController;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Controller
@RequestMapping(value = "/pay")
public class PAY002Controller extends BaseController {

	@Autowired
	private DomainService domainService;
	@Autowired
	private PayPublicUtilController payPublicUtilController;
	@Autowired
	private Pay002Service pay002Service;
	@Autowired 
	private PayPublicUtilService payPublicUtilService;
	
	/**
	 * 初始頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay002/init")
	public String init(HttpServletRequest request,Map<String, Object> model) throws Exception{
		List<String> values = new ArrayList<String>();
		values.add("ELE02");
		values.add("ELE03");
		values.add("ELE04");
		model.put("domainSelect", domainService.getStandardDomainList());
		model.put("electricityTypeSelect", pay002Service.getPayLookupByTypeAndCodeIn("ELECTRICITY_TYPE" , values));// 用電方式下拉選單
		return "ajaxPage/pay/PAY002";
	}

	/**
	 * 查詢
	 */
	@RequestMapping(value = "/pay002/search")
	@ResponseBody
	public JqGridData<TbPayElectricityDTO> search(
			@RequestParam(value = "domain", required = false) String domain,
			@RequestParam(value = "contractNo", required = false) String contractNo,
			@RequestParam(value = "electricityMeterNbr", required = false) String electricityMeterNbr,
			@RequestParam(value = "dataType", required = false) String dataType,
			@RequestParam(value = "appStartDate", required = false) Date appStartDate,
			@RequestParam(value = "appEndDate", required = false) Date appEndDate,
			@RequestParam(value = "appUser", required = false) String appUser,
			@RequestParam(value = "electricityType", required = false) String electricityType)throws Exception {
		
		//截止日期會轉成00:00:00  需加1 才能拿到截止日當天的數據
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
        Calendar cd = Calendar.getInstance();    
        cd.setTime(appEndDate);    
        cd.add(Calendar.DATE, 1);//增加一天    
        appEndDate = sdf.parse(sdf.format(cd.getTime()));
        
		List<TbPayElectricityDTO> payList = pay002Service.selectTbPayElectricity(domain, contractNo,
						electricityMeterNbr, dataType, appStartDate, appEndDate, appUser, electricityType);
		PageList<TbPayElectricityDTO> page = (PageList<TbPayElectricityDTO>) payList;
		return new JqGridData<TbPayElectricityDTO>(page.getPaginator().getTotalCount(), payList);
	}
	
	/**
	 * 查詢用電明細表資料
	 */
	@RequestMapping(value = "/pay002/searchElectricityDtlData")
	@ResponseBody
	public JqGridData<TbPayElectricityDTO> searchElectricityDtlData(
			@RequestParam("cashReqNo") String cashReqNo,
			@RequestParam("contractNo") String contractNo,
			@RequestParam("electricityMeterNbr") String electricityMeterNbr) throws Exception{
		List<TbPayElectricityDTO> payList = pay002Service.selectTbPayElectricityDtl(cashReqNo,contractNo,electricityMeterNbr);
		PageList<TbPayElectricityDTO> page = (PageList<TbPayElectricityDTO>) payList;
		return new JqGridData<TbPayElectricityDTO>(page.getPaginator().getTotalCount(),payList);
	}
	
	/**
	 * 修改頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay002/editPage")
	public String editPage(HttpServletRequest request,Map<String, Object> model,
			@RequestParam(value = "seqNbr", required = false) String seqNbr,
			@RequestParam(value = "contractNo", required = false) String contractNo,
			@RequestParam(value = "electricityMeterNbr", required = false) String electricityMeterNbr,
			@RequestParam(value = "contractName", required = false) String contractName,
			@RequestParam(value = "electricityDscr", required = false) String electricityDscr,
			@RequestParam(value = "lsSDate", required = false) Date lsSDate,
			@RequestParam(value = "paymentReqBeginDate", required = false) Date paymentReqBeginDate,
			@RequestParam(value = "paymentReqEndDate", required = false) Date paymentReqEndDate,
			@RequestParam(value = "totalUsedDegree", required = false) BigDecimal totalUsedDegree,
			@RequestParam(value = "usedDegree", required = false) BigDecimal usedDegree,
			@RequestParam(value = "memo", required = false) String memo,
			@RequestParam(value = "paymentReqAmt", required = false) BigDecimal paymentReqAmt,
			@RequestParam(value = "ifAutoDeduct", required = false) String ifAutoDeduct,
			@RequestParam(value = "ifNoSiteMapping", required = false) String ifNoSiteMapping,
			@RequestParam(value = "electricityType", required = false) String electricityType,
			@RequestParam(value = "usedDegreeDay", required = false) BigDecimal usedDegreeDay,
			@RequestParam(value = "lastUsedDegree", required = false) BigDecimal lastUsedDegree,
			@RequestParam(value = "ratio", required = false) BigDecimal ratio) throws Exception{
		double countAmt= 0;
		TbLsLocElecDTO chrgModeData = new TbLsLocElecDTO();
		// 借電_抄表
		if (electricityType.equals("ELE02")){
			// 取得租約的每度金額資料
			//chrgModeData = pay002Service.getChrgMode(contractNo, electricityMeterNbr);
			chrgModeData = pay002Service.getLocationId(contractNo, electricityMeterNbr);
			// 計算借電_抄錶的請款金額
			countAmt = pay002Service.getPaymentReqAmt(contractNo,electricityMeterNbr,paymentReqBeginDate, paymentReqEndDate , totalUsedDegree,chrgModeData);
			paymentReqAmt = BigDecimal.valueOf(countAmt);
		}
		model.put("seqNbrEdit", seqNbr);// for Update使用
		model.put("contractNoEdit", contractNo);// 租約編號
		model.put("electricityMeterNbrEdit", electricityMeterNbr);// 電錶號碼
		model.put("contractNameEdit", contractName);// 租約名稱
		model.put("electricityDscrEdit", electricityDscr);// 用電/預付方式說明
		model.put("lsSDateEdit", lsSDate);// 起帳日
		model.put("paymentReqBeginDateEdit", paymentReqBeginDate);// 本次請款起始日
		model.put("paymentReqEndDateEdit", paymentReqEndDate);// 本次請款結束日
		model.put("usedDegreeDayEdit", usedDegreeDay);// 平均用電數/日
		model.put("lastUsedDegreeEdit", lastUsedDegree);// 上期度數
		model.put("totalUsedDegreeEdit", totalUsedDegree);// 本次度數
		model.put("usedDegreeEdit", usedDegree);// 本次用電度數
		model.put("ratioEdit", ratio);// 超出/減少比率
		model.put("memoEdit", memo);// 用電補充說明
		model.put("paymentReqAmtEdit", paymentReqAmt);// 請款金額
		model.put("ifAutoDeductEdit", ifAutoDeduct);// 自動扣款代碼
		model.put("ifNoSiteMappingEdit", ifNoSiteMapping);// 站台對應代碼
		model.put("useElectricityRatio", 1);// 設定用電比應該來自租約模組,待確認,暫時將給設定用電比=1
		model.put("electricityType", electricityType);// 用電/預付方式代碼
		
		return "ajaxPage/pay/PAY002E";
	}
	
	/**
	 * 修改頁面:更新TB_PAY_ELECTRICITY資料
	 */
	@RequestMapping(value = "/pay002/updateData")
	@ResponseBody
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonProperty("wrapper")
	public boolean updateData(
			@RequestParam(value = "seqNbrEdit", required = false) String seqNbrEdit,
			@RequestParam(value = "totalUsedDegreeEdit", required = false) String totalUsedDegreeEdit,
			@RequestParam(value = "usedDegreeEdit", required = false) String usedDegreeEdit,
			@RequestParam(value = "paymentReqAmtEdit", required = false) String paymentReqAmtEdit,
			@RequestParam(value = "memoEdit", required = false) String memoEdit,
			@RequestParam(value = "paymentReqBeginDateEdit", required = false) Date paymentReqBeginDateEdit,
			@RequestParam(value = "paymentReqEndDateEdit", required = false) Date paymentReqEndDateEdit,
			@RequestParam(value = "ifAutoDeductSelectEdit", required = false) String ifAutoDeductSelectEdit,
			@RequestParam(value = "ifNoSiteMappingSelectEdit", required = false) String ifNoSiteMappingSelectEdit) {
		Date today = payPublicUtilService.getToday();
		TbPayElectricity exportObject = new TbPayElectricity();
		try {
			exportObject.setSEQ_NBR(Long.valueOf(seqNbrEdit));
			exportObject.setTOTAL_USED_DEGREE(BigDecimal.valueOf(Long.valueOf(totalUsedDegreeEdit)));
			exportObject.setUSED_DEGREE(BigDecimal.valueOf(Long.valueOf(usedDegreeEdit)));
			//exportObject.setPAYMENT_REQ_AMT(BigDecimal.valueOf(Long.valueOf(paymentReqAmtEdit)));
			exportObject.setPAYMENT_REQ_AMT(new BigDecimal(paymentReqAmtEdit));
			exportObject.setMEMO(memoEdit);
			exportObject.setPAYMENT_REQ_BEGIN_DATE(paymentReqBeginDateEdit);
			exportObject.setPAYMENT_REQ_END_DATE(paymentReqEndDateEdit);
			exportObject.setIF_AUTO_DEDUCT(ifAutoDeductSelectEdit);
			exportObject.setIF_NO_SITE_MAPPING(ifNoSiteMappingSelectEdit);
			exportObject.setMD_USER(String.valueOf(getLoginUser().getUserId()));
			exportObject.setMD_TIME(today);
			pay002Service.updateTbPayElectricity(exportObject);
		} catch (NomsException throwable) {
			log.error(throwable.getMessage());
			throwable.printStackTrace();
			return false;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 顯示用電/預付明細頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay002/dtlPage")
	public String dtlPage(HttpServletRequest request,Map<String, Object> model,
			@RequestParam(value = "contractNo", required = false) String contractNo,
			@RequestParam(value = "electricityMeterNbr", required = false) String electricityMeterNbr,
			@RequestParam(value = "contractName", required = false) String contractName,
			@RequestParam(value = "electricityDscr", required = false) String electricityDscr,
			@RequestParam(value = "lsSDate", required = false) Date lsSDate,
			@RequestParam(value = "paymentReqBeginDate", required = false) Date paymentReqBeginDate,
			@RequestParam(value = "paymentReqEndDate", required = false) Date paymentReqEndDate,
			@RequestParam(value = "totalUsedDegree", required = false) BigDecimal totalUsedDegree,
			@RequestParam(value = "usedDegree", required = false) BigDecimal usedDegree,
			@RequestParam(value = "memo", required = false) String memo,
			@RequestParam(value = "paymentReqAmt", required = false) BigDecimal paymentReqAmt,
			@RequestParam(value = "ifAutoDeduct", required = false) String ifAutoDeduct,
			@RequestParam(value = "ifNoSiteMapping", required = false) String ifNoSiteMapping,
			@RequestParam(value = "electricityType", required = false) String electricityType,
			@RequestParam(value = "usedDegreeDay", required = false) BigDecimal usedDegreeDay,
			@RequestParam(value = "lastUsedDegree", required = false) BigDecimal lastUsedDegree,
			@RequestParam(value = "ratio", required = false) BigDecimal ratio) throws Exception{
		double countAmt= 0;
		TbLsLocElecDTO chrgModeData = new TbLsLocElecDTO();
		// 借電_抄表
		if (electricityType.equals("ELE02")){
			// 取得租約的每度金額
			//chrgModeData = pay002Service.getChrgMode(contractNo, electricityMeterNbr);
			try {
				chrgModeData = pay002Service.getLocationId(contractNo, electricityMeterNbr);
				// 計算借電_抄錶的請款金額
				countAmt = pay002Service.getPaymentReqAmt(contractNo,electricityMeterNbr,paymentReqBeginDate, paymentReqEndDate , totalUsedDegree,chrgModeData);
				paymentReqAmt = BigDecimal.valueOf(countAmt);
			} catch (Exception e) {
				throw new NomsException("請款金額取得 錯誤："+e.getMessage());
			}
		}
		model.put("contractNoDtl", contractNo);// 租約編號
		model.put("electricityMeterNbrDtl", electricityMeterNbr);// 電錶號碼
		model.put("contractNameDtl", contractName);// 租約名稱
		model.put("electricityDscrDtl", electricityDscr);// 用電/預付方式說明
		model.put("lsSDateDtl", lsSDate);// 起帳日
		model.put("paymentReqBeginDateDtl", paymentReqBeginDate);// 本次請款起始日
		model.put("paymentReqEndDateDtl", paymentReqEndDate);// 本次請款結束日
		model.put("usedDegreeDayDtl", usedDegreeDay);// 平均用電數/日
		model.put("lastUsedDegreeDtl", lastUsedDegree);// 上期度數
		model.put("totalUsedDegreeDtl", totalUsedDegree);// 本次度數
		model.put("usedDegreeDtl", usedDegree);// 本次用電度數
		model.put("ratioDtl", ratio);// 超出/減少比率
		model.put("memoDtl", memo);// 用電補充說明
		model.put("paymentReqAmtDtl", paymentReqAmt);// 請款金額
		model.put("ifAutoDeductDtl", ifAutoDeduct);// 自動扣款代碼
		model.put("ifNoSiteMappingDtl", ifNoSiteMapping);// 站台對應代碼
		
		return "ajaxPage/pay/PAY002D";
	}
	
	/**
	 * 用電輸入頁面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay002/addPage")
	public String addPage(HttpServletRequest request, Map<String, Object> model)
			throws Exception {
		List<String> values = new ArrayList<String>();
		values.add("ELE02");
		values.add("ELE03");
		values.add("ELE06");
		model.put("contractNoSelectAdd", pay002Service.getLsNoList());// 租約編號下拉選單
		model.put("electricityTypeSelectAdd", pay002Service.getPayLookupByTypeAndCodeIn("ELECTRICITY_TYPE" , values));// 用電/預付方式下拉選單
		return "ajaxPage/pay/PAY002A";
	}

	/**
	 * 用電輸入頁面:依選擇租約編號連動電錶號碼
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay002/getEnergyMeter")
	@ResponseBody
	public BaseJasonObject<TbLsLocElec> energyMeterSelect(HttpServletRequest reques, String contractNoSelectAdd)
			throws Exception {
		List<TbLsLocElec> energyMeterList = pay002Service.getEnergyMeterList(contractNoSelectAdd);
		return new BaseJasonObject<TbLsLocElec>(energyMeterList, AJAX_SUCCESS,AJAX_EMPTY);
	}
	
	/**
	 * 用電輸入頁面:依選擇租約編號連動取得租約名稱、起帳日
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay002/getContractValue")
	@ResponseBody
	public BaseJasonObject<TbLsMain> getContractValue(HttpServletRequest reques, String contractNoSelectAdd)
			throws Exception {
		List<TbLsMain> tbLsMainList = pay002Service.getContractValue(contractNoSelectAdd);
		return new BaseJasonObject<TbLsMain>(tbLsMainList, AJAX_SUCCESS,AJAX_EMPTY);
	}
	
	/**
	 * 用電輸入頁面:依選擇租約編號、電錶號碼取得平均用電數/日、上期度數
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay002/getUsedDegreeDayData")
	@ResponseBody
	public BaseJasonObject<Object> getUsedDegreeDayData(HttpServletRequest reques, String contractNoSelectAdd,
			String electricityMeterNbrSelectAdd) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		try{
			map = pay002Service.getUsedDegreeDayData(contractNoSelectAdd, electricityMeterNbrSelectAdd);
		}catch (NomsException throwable) {
			log.error(throwable.getMessage());
			throwable.printStackTrace();
			return new BaseJasonObject<>(false,throwable.getMessage());
		}
		catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
			return new BaseJasonObject<>(false,e.getMessage());
		}
		return new BaseJasonObject<>(map, AJAX_SUCCESS, AJAX_EMPTY);
	}

	/**
	 * 用電修改/輸入頁面:計算請款金額(本次用電度數*租約每度金額)
	 * 
	 * @param request
	 * @param model
	 * @return
	 *//*
	@RequestMapping(value = "/pay002/getPaymentReqAmt")
	@ResponseBody
	public BaseJasonObject<Object> getPaymentReqAmt(HttpServletRequest request,Map<String, Object> model,
			@RequestParam(value = "contractNoVal", required = false) String contractNoVal,
			@RequestParam(value = "electricityMeterNbrVal", required = false) String electricityMeterNbrVal,
			@RequestParam(value = "paymentReqBeginDateVal", required = false) Date paymentReqBeginDateVal,
			@RequestParam(value = "paymentReqEndDateVal", required = false) Date paymentReqEndDateVal,
			@RequestParam(value = "usedDegreeVal", required = false) BigDecimal usedDegreeVal) throws Exception {
		int countAmt= 0;
		TbLsLocElecDTO chrgModeData = new TbLsLocElecDTO();
			// 取得租約的每度金額
			chrgModeData = pay002Service.getChrgMode(contractNoVal, electricityMeterNbrVal);
			// 計算借電_抄錶的請款金額
			countAmt = pay002Service.getPaymentReqAmt(paymentReqBeginDateVal, paymentReqEndDateVal , usedDegreeVal , chrgModeData);
		return new BaseJasonObject<Object>(true, String.valueOf(countAmt));
	}*/
	
	/**
	 * 用電修改/輸入頁面:計算請款金額(本次用電度數*租約每度金額)
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay002/getPaymentReqAmt")
	@ResponseBody
	public BaseJasonObject<Object> getPaymentReqAmt(HttpServletRequest request,Map<String, Object> model,
			@RequestParam(value = "contractNoVal", required = false) String contractNoVal,
			@RequestParam(value = "electricityMeterNbrVal", required = false) String electricityMeterNbrVal,
			@RequestParam(value = "paymentReqBeginDateVal", required = false) Date paymentReqBeginDateVal,
			@RequestParam(value = "paymentReqEndDateVal", required = false) Date paymentReqEndDateVal,
			@RequestParam(value = "totalUsedDegreeVal", required = false) BigDecimal totalUsedDegreeAdd) throws Exception {
		double countAmt= 0;
		TbLsLocElecDTO chrgModeData = new TbLsLocElecDTO();
			// 取得LocationId
			chrgModeData = pay002Service.getLocationId(contractNoVal, electricityMeterNbrVal);
			// 計算借電_抄錶的請款金額
			countAmt = pay002Service.getPaymentReqAmt(contractNoVal,electricityMeterNbrVal,paymentReqBeginDateVal, paymentReqEndDateVal , totalUsedDegreeAdd,chrgModeData);
		return new BaseJasonObject<Object>(true, String.valueOf(countAmt));
	}
	
	
	/**
	 * 匯入txt讀取檔案內容
	 * @return
	 */
	@RequestMapping(value = "/pay002/readBatchApply", method = RequestMethod.POST)
	@ResponseBody
	public BaseJasonObject<OriginalPowerBillDTO> readFileProcess(
			MultipartHttpServletRequest mutipartRequest,
			Map<String, Object> model) throws Exception {
		String fileName = "";
		// 取得檔案內容Parsing規則
		List<TbPayFileFormatDtl> parsingRuleList = pay002Service.getParsingRuleList();
		
		// 讀取txt檔案內容
		List<String> fileValues = new ArrayList<String>();
		for (Iterator<String> itr = mutipartRequest.getFileNames(); itr.hasNext();) {
			MultipartFile file = mutipartRequest.getFile(itr.next());
			fileName = file.getOriginalFilename();// 檔案名稱
	        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(),"BIG5"));
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	// 將取得資料列塞至ArrayList存放
	        	fileValues.add(line);
	        }
	        reader.close();
		}

        //  檔案資訊:(單筆)Insert TB_PAY_IMP_FILE、(多筆) Insert TB_PAY_IMP_FILE_CONTENT、ORIGINAL_POWER_BILL
		Date today=payPublicUtilService.getToday();
		List<OriginalPowerBillDTO> returnList = new PageList<OriginalPowerBillDTO> ();
		try{
			returnList = pay002Service.getFileDataInsertToTable(fileName, today, parsingRuleList, fileValues);
		}catch (NomsException throwable) {
			log.error(throwable.getMessage());
			throwable.printStackTrace();
			return new BaseJasonObject<>(false,throwable.getMessage());
		}
		return new BaseJasonObject<>(returnList, "匯入檔案成功", AJAX_EMPTY);
	}
	
	/**
	 * 用電輸入頁面:依電錶號碼取得租約編號
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay002/getContractNo")
	@ResponseBody
	public BaseJasonObject<TbLsLocElec> getContractNo(HttpServletRequest reques, String electricityMeterNbrAdd)
			throws Exception {
		List<TbLsLocElec> tbLsLocElecList = pay002Service.getContractNo(electricityMeterNbrAdd);
		return new BaseJasonObject<TbLsLocElec>(tbLsLocElecList, AJAX_SUCCESS,AJAX_EMPTY);
	}

	/**
	 * 用電輸入頁面:儲存用電輸入&請電電子檔匯入資料至TB_PAY_ELECTRICITY
	 * 
	 * @param request
	 * @return json
	 */
	@RequestMapping(value = "/pay002/saveToTable")
	@ResponseBody
	public BaseJasonObject<Object> saveToTable(@RequestParam("payElectricity") String payElectricity) {
		Date today=payPublicUtilService.getToday();
		BaseJasonObject<Object> json=new BaseJasonObject<Object>(AJAX_SUCCESS,"success");
			try{
				pay002Service.insertToTbPayElectricity(payElectricity, today);
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
				json=new BaseJasonObject<Object>(false,"新增專線/用電使用紀錄檔錯誤 原因:"+throwable.getMessage());
			}catch(Exception e){
				log.error(e.getMessage());
				e.printStackTrace();
				json=new BaseJasonObject<Object>(false,"新增專線/用電使用紀錄檔錯誤 原因:"+e.getMessage());
			}
		return json;
	}

}
