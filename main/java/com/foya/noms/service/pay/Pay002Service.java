package com.foya.noms.service.pay;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbLsLocElec;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbPayElectricity;
import com.foya.dao.mybatis.model.TbPayFileFormatDtl;
import com.foya.dao.mybatis.model.TbPayImpFile;
import com.foya.dao.mybatis.model.TbPayImpFileContent;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.exception.NomsException;
import com.foya.noms.dao.pay.Pay002Dao;
import com.foya.noms.dto.pay.OriginalPowerBillDTO;
import com.foya.noms.dto.pay.TbLsLocElecDTO;
import com.foya.noms.dto.pay.TbPayElectricityDTO;
import com.foya.noms.service.BaseService;
import com.foya.noms.util.DateUtils;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service
public class Pay002Service extends BaseService {
	@Inject 
	private Pay002Dao pay002Dao;
	
	public List<TbPayElectricityDTO> selectTbPayElectricity(String domain,
			String contractNo, String electricityMeterNbr, String dataType,
			Date appStartDate, Date appEndDate,
			String appUser, String electricityType) throws ParseException {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("domain", domain);
		dataObj.put("contractNo", contractNo);
		dataObj.put("electricityMeterNbr", electricityMeterNbr);
		dataObj.put("appStartDate", appStartDate);
		dataObj.put("appEndDate", appEndDate);
		dataObj.put("appUser", appUser);
		if (dataType.equals("A")){
			dataObj.put("electricityType", electricityType);
		} else {
			dataObj.put("electricityType", "ELE06");
		}
		List<TbPayElectricityDTO> list = pay002Dao.selectTbPayElectricity(dataObj);
		// 取得用電度數、用電天數、上期度數 
		for(int i = 0 ; i < list.size() ; i++){
			Map<String, Object> payMap = this.getUsedDegreeDayData(list.get(i).getCONTRACT_NO(),list.get(i).getELECTRICITY_METER_NBR());
			list.get(i).setUsed_degree_day((BigDecimal)payMap.get("usedDegreeDay"));
			list.get(i).setM_day((BigDecimal)payMap.get("mDay"));
			list.get(i).setLast_used_degree((BigDecimal)payMap.get("lastUsedDegree"));
		}
		return list;
	}
	
	public List<TbPayElectricityDTO> selectTbPayElectricityDtl(String cashReqNo, String contractNo, String electricityMeterNbr)throws ParseException {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("cashReqNo", cashReqNo);
		dataObj.put("contractNo", contractNo);
		dataObj.put("electricityMeterNbr", electricityMeterNbr);
		List<TbPayElectricityDTO> list = pay002Dao.selectTbPayElectricityDtl(dataObj);
		// 取得用電度數、用電天數、上期度數
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> payMap = this.getUsedDegreeDayData(list.get(i).getCONTRACT_NO(), list.get(i).getELECTRICITY_METER_NBR());
			list.get(i).setUsed_degree_day((BigDecimal) payMap.get("usedDegreeDay"));
			list.get(i).setM_day((BigDecimal) payMap.get("mDay"));
			list.get(i).setLast_used_degree((BigDecimal) payMap.get("lastUsedDegree"));
		}
		return list;
	}
	
	// 取得用電度數、用電天數、上期度數 
	public HashMap<String, Object> getUsedDegreeDayData(String contractNo,String electricityMeterNbr) throws ParseException {
			Map<String, Object> seqNoMap = new HashMap<String, Object>();
			seqNoMap.put("PI_contractNo", contractNo);
			seqNoMap.put("PI_electricity_Meter_Nbr", electricityMeterNbr);
			seqNoMap.put("PO_DEGREE", null);
			seqNoMap.put("PO_m_day", null);
			seqNoMap.put("PO_LAST_DEGREE", null);
			pay002Dao.callPayUsedDegreeMDayMap(seqNoMap);
			log.debug("callPayUsedDegreeMDayMap : PO_DEGREE [" + seqNoMap.get("PO_DEGREE") + "] PO_m_day [" + seqNoMap.get("PO_m_day") + "] PO_LAST_DEGREE [" + seqNoMap.get("PO_LAST_DEGREE") + "]");
			BigDecimal usedDegreeDay = (BigDecimal)seqNoMap.get("PO_DEGREE");
			BigDecimal mDay = (BigDecimal)seqNoMap.get("PO_m_day");
			BigDecimal lastUsedDegree = (BigDecimal)seqNoMap.get("PO_LAST_DEGREE");
			HashMap<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("usedDegreeDay", usedDegreeDay);
			returnMap.put("mDay", mDay);
			returnMap.put("lastUsedDegree", lastUsedDegree);
		return returnMap;
	}

	// 取得租約每度金額
	public TbLsLocElecDTO getChrgMode(String contractNo, String electricityMeterNbr)throws ParseException  {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("contractNo", contractNo);
		dataObj.put("electricityMeterNbr", electricityMeterNbr);
		return pay002Dao.getChrgMode(dataObj);
	}
	
	/*// 計算借電_抄錶的請款金額(本次用電度數*租約每度金額)並四捨五入至整數(舊版)
	public int getPaymentReqAmt(Date paymentReqBeginDate, Date paymentReqEndDate, BigDecimal usedDegree,
			TbLsLocElecDTO chrgModeData)throws ParseException  {
		int paymentReqAmt = 0;
		String price = "";
		try {
			   if (chrgModeData.getELEC_MODE().equals("C")){
				   price = chrgModeData.getCHRG_MODE();
			   } else {
				   // 當elec_mode='D'則進行請款日期(級距計算)
				   //log.debug("當elec_mode='D'則進行請款日期(級距計算):"+paymentReqBeginDate+","+paymentReqEndDate);
				   // 夏月(SPECIAL_PRICE)日期設定:0601~0930
				   SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
				   String specialStart = "06-01";
				   String specialEnd = "09-30";
				   // 判斷請款日期起迄是否有在夏月區間內
				   boolean dateSFlag = DateUtils.isValidPreiod(new Timestamp(sdf.parse(specialStart).getTime()),new Timestamp(sdf.parse(specialEnd).getTime()),
										  				   new Timestamp(sdf.parse(sdf.format(paymentReqBeginDate)).getTime()));
				   boolean dateEFlag = DateUtils.isValidPreiod(new Timestamp(sdf.parse(specialStart).getTime()),new Timestamp(sdf.parse(specialEnd).getTime()),
							  			   				   new Timestamp(sdf.parse(sdf.format(paymentReqEndDate)).getTime()));
				   //log.debug("判斷請款日期起迄是否有在夏月起迄區間內" + ",起:" + dateSFlag + ",迄"+ dateEFlag);
				   if (dateSFlag == true && dateEFlag == true) {
						price = chrgModeData.getSPECIAL_PRICE();
				   } else if (dateSFlag != true && dateEFlag != true) {
						price = chrgModeData.getNORMAL_PRICE();
				   } else {
						log.debug("該筆資料請款日期起迄 '跨了' 夏月起迄區間!");
						price = "99";
				   }
			   }
		} catch (NullPointerException e) {
			// 當取不到租約每度金額時給0
			price = "0";
		}
		// 計算請款金額(本次用電度數*租約每度金額)並四捨五入至整數
		paymentReqAmt = (int) Math.round(usedDegree.multiply(new BigDecimal(price)).doubleValue());
		return paymentReqAmt;
	}*/
	
	// 計算借電_抄錶的請款金額改call procedure PAY_PC_GET_ELEC_AMT
	public double getPaymentReqAmt(String contractNoVal,String electricityMeterNbrVal,
			Date paymentReqBeginDate, Date paymentReqEndDate, BigDecimal totalUsedDegree,TbLsLocElecDTO chrgModeData)throws Exception  {
		//int paymentReqAmt = 0;
		Map<String, Object> map = null;
		
		String price = "0";
		//System.out.println("test~~~" + chrgModeData.getLOCATION_ID());
		try {
			map = new HashMap<String, Object>();
			map.put("PI_LS_NO",contractNoVal);
			map.put("PI_LOCATION_ID",chrgModeData.getLOCATION_ID());
			map.put("PI_ENERGY_METER",electricityMeterNbrVal);
			map.put("PI_ELEC_DEGS",totalUsedDegree);
			map.put("PI_APP_DATES",paymentReqBeginDate);
			map.put("PI_APP_DATEE",paymentReqEndDate);
			map.put("PI_ELECTRICITY_TYPE","ELE02");
			//map.put("PI_USER", getLoginUser().getUsername());
			
			log.debug("PI_LS_NO~~" + contractNoVal);
			log.debug("PI_LOCATION_ID~~" + chrgModeData.getLOCATION_ID());
			log.debug("PI_ENERGY_METER~~" + electricityMeterNbrVal);
			log.debug("PI_ELEC_DEGS~~" + totalUsedDegree);
			log.debug("PI_APP_DATES~~" + paymentReqBeginDate);
			log.debug("PI_APP_DATEE~~" + paymentReqEndDate);
			log.debug("PI_ELECTRICITY_TYPE~~" + "ELE02");
			//log.debug("PI_USER~~" + getLoginUser().getUsername());
			
			pay002Dao.payPcGetElecAmt(map);
			price = map.get("PO_APP_ELEC_AMT").toString();
			log.debug("callsp取得請款金額：~" + price);
			log.debug("PO_ELEC_DEGS：~" + map.get("PO_ELEC_DEGS").toString());
			log.debug("PO_APP_ELEC_AMT：~" + map.get("PO_APP_ELEC_AMT").toString());
			//log.debug("PO_BTAX_AMT：~" + map.get("PO_BTAX_AMT").toString());
			log.debug("PO_ERR_CDE：~" + map.get("PO_ERR_CDE").toString());
			log.debug("PO_ERR_MSG：~" + map.get("PO_ERR_MSG").toString());
			
	
		
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw new NomsException("請款金額取得 錯誤："+e.getMessage());
			// 當取不到時給0
		}
		return Double.valueOf(price);
	}
	
	// 取得LOCATION_ID(站點編號)
	public TbLsLocElecDTO getLocationId(String contractNoVal,String electricityMeterNbrVal) throws ParseException {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("contractNo", contractNoVal);
		dataObj.put("electricityMeterNbr", electricityMeterNbrVal);
		return pay002Dao.getLocationId(dataObj);
	}
		
	
	@Transactional
	public int updateTbPayElectricity(TbPayElectricity record){
		return pay002Dao.updateTbPayElectricity(record);
	}

	// 取得租約編號下拉選單資料
	public List<TbLsLocElec> getLsNoList(){
		return pay002Dao.getLsNoList();
	}
	
	// 依租約編號取得電錶號碼下拉選單資料
	public List<TbLsLocElec> getEnergyMeterList(String contractNo){
		return pay002Dao.getEnergyMeterList(contractNo);
	}
	
	// 依租約編號取得租約名稱、起帳日
	public List<TbLsMain> getContractValue(String contractNo){
		return pay002Dao.getContractValue(contractNo);
	}

	/*以Type, code in 查詢系統設定資料*/
	public List<TbPayLookupCode> getPayLookupByTypeAndCodeIn(String type, List<String> values) {
		return pay002Dao.getPayLookupByTypeAndCodeIn(type, values);
	}
	
	// 取得檔案內容Parsing規則FROM TB_PAY_FILE_FORMAT、TB_PAY_FILE_FORMAT_DTL
	public List<TbPayFileFormatDtl> getParsingRuleList()throws ParseException {
		List<TbPayFileFormatDtl> list = pay002Dao.getParsingRuleList();
		return list;
	}
	
    //  檔案資訊及內容值分別寫入 TB_PAY_IMP_FILE、TB_PAY_IMP_FILE_CONTENT、ORIGINAL_POWER_BILL
	public List<OriginalPowerBillDTO> getFileDataInsertToTable(String fileName, Date today, List<TbPayFileFormatDtl> parsingRuleList, List<String> fileValues)
			throws NomsException, Exception {
		List<OriginalPowerBillDTO>  returnList = new PageList<OriginalPowerBillDTO> ();
		OriginalPowerBillDTO billData=new OriginalPowerBillDTO();
		// (單筆) Insert TB_PAY_IMP_FILE(請款資料匯入檔案資訊)
		TbPayImpFile record = new TbPayImpFile();
		record.setCASH_REQ_NO(null);
		record.setFILE_TYPE("E");
		record.setCO_VAT_NO(null);
		record.setFILE_NAME(fileName);
		record.setCR_USER(getLoginUser().getUsername());
		record.setCR_TIME(today);
		record.setMD_USER(getLoginUser().getUsername());
		record.setMD_TIME(today);
		try {
			pay002Dao.insertTbPayImpFile(record);
		} catch (Exception e) {
			throw new NomsException("寫入 TB_PAY_IMP_FILE 錯誤："+ e.getLocalizedMessage());
		}
		if (record.getSEQ_NBR() == null) {
			throw new NomsException("99", "無法取得新增TB_PAY_IMP_FILE後的檔案流水號");
		}
		log.debug("取得新增TB_PAY_IMP_FILE後的檔案流水號:"+record.getSEQ_NBR());
		String colName = "";
		String fillUpChar = "";
		String lineStr = "";
		String dataValue = "";
		int colOrder = 0;
		int colLength = 0;
		int index = 0;
		// 依欄位Parsing規則，取得該列資料對應欄位值
		//log.debug("txt檔資料筆數:"+fileValues.size()+" ,Parsing欄位數:"+parsingRuleList.size());
		HashMap<String, Object> originalMap = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
		for (int v = 0; v < fileValues.size(); v++) {
			index++;
			int valueIndexS = 0;
			int valueIndexE = 0;
			String FILE_CONTENT = "";
			lineStr = fileValues.get(v);
			//log.debug("第" + index + "列資料 ,字串總長度:" + lineStr.length());
			for (int i = 0; i < parsingRuleList.size(); i++) {// 只讀取1~125欄位規則
				TbPayFileFormatDtl parsingRule = parsingRuleList.get(i);
				colName = parsingRule.getCOL_NAME();
				colOrder = parsingRule.getCOL_ORDER();
				fillUpChar = parsingRule.getFILL_UP_CHAR();
				colLength = parsingRule.getCOL_LENGTH();
				//log.debug("第" + colOrder + "個欄位Parsing規則 ,colName:" + colName + " ,colOrder: " + colOrder + " ,fillUpChar:" + fillUpChar + " ,colLength:" + colLength);
				valueIndexE = valueIndexE + colLength;// 設定Index迄值
				//log.debug("Index起值:" + valueIndexS + " ,Index迄值: " + valueIndexE);
				dataValue = (lineStr.substring(valueIndexS, valueIndexE)).trim();// 取得對應欄位值
				// 若fillUpChar=Z代表為數值資料,需把左補0去掉
				if (fillUpChar.equals("Z")){
					if(!dataValue.equals("")){
						// 去除左邊0
						dataValue = dataValue.replaceFirst("^0*", "");
					}
				}
				//log.debug("取得Parsing對應欄位 ,第" + index + "列資料" + " ,第" + colOrder + "個欄位[" + colName + "]資料:" + dataValue);
				valueIndexS = valueIndexS + colLength;// 設定Index起值
				// 檔案中每筆資料列對應該col_name的值,多値用;串起,例如:POWER_NO=xxx;CON_TYPE=xxx;…
				FILE_CONTENT = FILE_CONTENT+colName+"="+dataValue+";";
				// 對應欄位值塞Map
				if (colName.equals("LOCK_TYHE") || colName.equals("POWER_CUT_TYPE") || colName.equals("REMARK")) {
					originalMap.put(colName, "");
				} else if (colName.equals("INSERT_YRMN")) {
					originalMap.put(colName, sdf.format(today));
				} else {
					originalMap.put(colName, dataValue);
				}
				// 取得回傳頁面所需欄位資料
				billData = getReturnData(originalMap);
			}
			// (多筆) Insert TB_PAY_IMP_FILE_CONTENT(請款檔案匯入原始內容資訊)
			TbPayImpFileContent recordContent = new TbPayImpFileContent();
			recordContent.setMST_SEQ_NBR(record.getSEQ_NBR());
			recordContent.setFILE_CONTENT(FILE_CONTENT);
			recordContent.setPAYMENT_REQ_NO(null);
			recordContent.setPAYMENT_REQ_ITEM_NO(null);
			recordContent.setPR_STATUS(null);
			recordContent.setCR_USER(getLoginUser().getUsername());
			recordContent.setCR_TIME(today);
			recordContent.setMD_USER(getLoginUser().getUsername());
			recordContent.setMD_TIME(today);
			try {
				pay002Dao.insertTbPayImpFileContent(recordContent);
			} catch (Exception e) {
				throw new NomsException("寫入 TB_PAY_IMP_FILE_CONTENT 錯誤："+ e.getLocalizedMessage());
			}
			//2015/04/07 不寫入ORIGINAL_POWER_BILL
		    // (多筆) Insert ORIGINAL_POWER_BILL (檔案中每筆資料列對應該col_name的值)
			//try {
			//	pay002Dao.insertOriginalPowerBill(originalMap);
			//} catch (Exception e) {
			//	throw new NomsException("寫入 ORIGINAL_POWER_BILL 錯誤："+ e.getLocalizedMessage());
			//}
			// 處理本次請款起迄日由民國年月日轉西元年月日
			Calendar lastRecordYMD = Calendar.getInstance();
			Calendar recordYMD = Calendar.getInstance();
			if (!billData.getLAST_RECORD_YR().equals("") && billData.getLAST_RECORD_YR() != null) {
				lastRecordYMD.set((Integer.valueOf(billData.getLAST_RECORD_YR()) + 1911),
									(Integer.valueOf(billData.getLAST_RECORD_MN())-1),
									(Integer.valueOf(billData.getLAST_RECORD_DY())+1));
				billData.setLAST_RECORD_YYYYMMDD(lastRecordYMD.getTime());
			}
			if (!billData.getRECORD_YR().equals("") && billData.getRECORD_YR() != null) {
				recordYMD.set((Integer.valueOf(billData.getRECORD_YR()) + 1911),
									(Integer.valueOf(billData.getRECORD_MN())-1),
									(Integer.valueOf(billData.getRECORD_DY())-1));
				billData.setRECORD_YYYYMMDD(recordYMD.getTime());
			}
			// 檔案序號:TB_PAY_IMP_FILE.seq_nbr
			billData.setSEQ_NBR(record.getSEQ_NBR());
			returnList.add(billData);
		}
		// Call MSSQL Procedure(PAY_PC_GEN_POWER_BILL_TRANS),將剛塞到ORIGINAL_POWER_BILL 的資料經換算塞到ORIGINAL_POWER_BILL_TRANS
		
		
		
		return returnList;
	}
	
	// 取得回傳頁面所需欄位資料
	public OriginalPowerBillDTO getReturnData(Map<String, Object> map){
		OriginalPowerBillDTO returnData=new OriginalPowerBillDTO();
		returnData.setPOWER_NO((String) map.get("POWER_NO"));
		returnData.setLAST_RECORD_YR((String) map.get("LAST_RECORD_YR"));
		returnData.setLAST_RECORD_MN((String) map.get("LAST_RECORD_MN"));
		returnData.setLAST_RECORD_DY((String) map.get("LAST_RECORD_DY"));
		returnData.setRECORD_YR((String) map.get("RECORD_YR"));
		returnData.setRECORD_MN((String) map.get("RECORD_MN"));
		returnData.setRECORD_DY((String) map.get("RECORD_DY"));
		returnData.setNORMAL_DIFF_VOL((String) map.get("NORMAL_DIFF_VOL"));
		returnData.setNORMAL_CHARGE_VOL((String) map.get("NORMAL_CHARGE_VOL"));
		returnData.setCHARGE_OVER_AMT((String) map.get("CHARGE_OVER_AMT"));
		returnData.setCHARGE_DISC_AMT((String) map.get("CHARGE_DISC_AMT"));
		returnData.setINSERT_YRMN((String) map.get("INSERT_YRMN"));
		return returnData;
	}
	
	// 依電錶號碼取得租約編號
	public List<TbLsLocElec> getContractNo(String electricityMeterNbr){
		return pay002Dao.getContractNo(electricityMeterNbr);
	}
	
	@Transactional
	public void insertToTbPayElectricity(String payElectricity, Date today)throws NomsException, Exception {
		JSONArray payElectricityArray = new JSONArray(payElectricity);
		for (int i = 0; i < payElectricityArray.length(); i++) {
			JSONObject obj = payElectricityArray.getJSONObject(i);
			TbPayElectricity payElectricityData = this.makeTbPayElectricity(obj, today);
			pay002Dao.insertTbPayElectricity(payElectricityData);
		}
	}

	public TbPayElectricity makeTbPayElectricity(JSONObject obj, Date today)throws JSONException {
		TbPayElectricity record = new TbPayElectricity();
		try {
			record.setUSE_TYPE("E");
			//來自檔案的INSERT_YRMN匯入年月,(如果用電方式!=請電),此欄位值為null
			if (obj.getString("electricityType").equals("ELE04")){
				record.setYEAR_MONTH(obj.getString("yearMonth"));
				record.setIMP_FILE_SEQ_NBR(Long.valueOf(obj.getString("impFileSeqNbr")));
			} else {
				record.setYEAR_MONTH(null);
			}
			record.setCONTRACT_NO(obj.getString("contractNo"));
			record.setELECTRICITY_METER_NBR(obj.getString("electricityMeterNbr"));
			record.setSITE_ID(null);
			record.setELECTRICITY_TYPE(obj.getString("electricityType"));
			// 當用電方式不為預付時才塞以下欄位值
			if (!obj.getString("electricityType").equals("ELE06")){
				record.setTOTAL_USED_DEGREE(new BigDecimal(obj.getString("totalUsedDegree")));
				record.setUSED_DEGREE(new BigDecimal(obj.getString("usedDegree")));
				record.setPAYMENT_REQ_BEGIN_DATE(DateUtils.parse(obj.getString("paymentReqBeginDate"), "yyyy/MM/dd"));
				record.setPAYMENT_REQ_END_DATE(DateUtils.parse(obj.getString("paymentReqEndDate"), "yyyy/MM/dd"));
				record.setMEMO(obj.getString("memo"));
			}
			record.setPAYMENT_REQ_NO(null);
			record.setPAYMENT_REQ_ITEM_NO(null);
			record.setPAYMENT_REQ_AMT(new BigDecimal(obj.getString("paymentReqAmt")));
			record.setSETTING_FEE(new BigDecimal(0));
			record.setTRANSACTION_FEE(new BigDecimal(0));
			record.setIF_AUTO_DEDUCT(obj.getString("ifAutoDeduct"));
			record.setIF_NO_SITE_MAPPING(obj.getString("ifNoSiteMapping"));
			record.setCR_TIME(today);
			record.setCR_USER(getLoginUser().getUsername());
			record.setMD_TIME(today);
			record.setMD_USER(getLoginUser().getUsername());
		} catch (JSONException e) {
			throw new NomsException(e.getMessage());
		}
		return record;
	}

}
