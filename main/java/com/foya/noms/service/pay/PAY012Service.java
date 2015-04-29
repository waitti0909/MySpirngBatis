package com.foya.noms.service.pay;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbLsMainExample;
import com.foya.dao.mybatis.model.TbPayContractInfo;
import com.foya.dao.mybatis.model.TbPayContractInfoKey;
import com.foya.dao.mybatis.model.TbPayElectricityExample;
import com.foya.dao.mybatis.model.TbPayExpenseEstimate;
import com.foya.dao.mybatis.model.TbPayExpenseEstimateDtl;
import com.foya.dao.mybatis.model.TbPayExpenseEstimateExample;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbSiteLineApply;
import com.foya.dao.mybatis.model.TbSiteLineApplyExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.exception.NomsException;
import com.foya.noms.dao.pay.Pay012Dao;
import com.foya.noms.dto.ls.LeaseMainDTO;
import com.foya.noms.dto.ls.SiteAlocDetailDTO;
import com.foya.noms.dto.pay.EstimateExcelDTO;
import com.foya.noms.dto.pay.TbPayLocationInfoDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDTO;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.enums.SiteType;
import com.foya.noms.poi.Writer;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.PoiService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
@Service
public class PAY012Service extends BaseService {
	@Autowired
	private PoiService poiService;
	@Autowired 
	private PayPublicUtilService payPublicUtilService;
	@Autowired
	private Pay012Dao pay012Dao;
	public void exportExcel(HttpServletResponse response, String sheetName, String fileName, String result,String type) throws JSONException {
		HSSFSheet worksheet = poiService.exportXLS(response, sheetName, fileName, 0, 0, 4);
		// 建立報表的 標題、日期、欄位名稱
		buildExcel(worksheet, result,type);
		// 輸出流
		Writer.write(poiService.setResponse(response,fileName), worksheet);
	}
	/** 
     * 建立欄位名稱與樣式
	 * @throws JSONException 
     */  
    private void  buildExcel(HSSFSheet worksheet, String result,String type) throws JSONException {  
        // 儲存格樣式 headerCellStyle
        setCellStyle(worksheet);         
        //標題
        String[] title = genExcelTitle(type);
        //建立欄位名稱 
		HSSFRow header = worksheet.createRow(0);
		for(int i=0; i<title.length; i++){
			header.createCell(i).setCellValue(title[i]);
		}
		//重串資料
		List<EstimateExcelDTO> list = makeExcelData(new JSONArray(result), type);
		//寫入資料
		int rowIdx = 1;
		for(int i=0; i<list.size(); i++){
			HSSFRow excelRow = worksheet.createRow(rowIdx);
			buildCell(excelRow, setCellValue(type,list.get(i))//待傳入Excel欄位內容
					,title.length);
			rowIdx++;
		}	
    }
    
    private List<EstimateExcelDTO> makeExcelData(JSONArray excelInitData,String type) throws JSONException{
    	List<EstimateExcelDTO> list = new ArrayList<EstimateExcelDTO>();
    	for (int i = 0; i < excelInitData.length(); i++) {
    		EstimateExcelDTO excelData = new EstimateExcelDTO();
    		JSONObject obj = excelInitData.getJSONObject(i);
			if(!"LIE".equals(type)){//非專線
				TbPayContractInfoKey key = new TbPayContractInfoKey();
				key.setCONTRACT_NO(obj.getString("contractNo"));
				key.setDOMAIN(obj.getString("domain"));
				TbPayContractInfo contractInfo=pay012Dao.selectContractInfo(key);
				excelData.setLeaseValidBeginDate(String.valueOf(contractInfo.getLEASE_VALID_BEGIN_DATE()));
				excelData.setLeaseValidEndDate(String.valueOf(contractInfo.getLEASE_VALID_END_DATE()));
				excelData.setDomain(contractInfo.getDOMAIN());					
				if("REE".equals(type)){//租金
					excelData.setPaymentPeriod(contractInfo.getPAYMENT_PERIOD());
					excelData.setYearMonth(obj.getString("yearMonth"));
				}
				if("ELE".equals(type)){//電費
					excelData.setElectricityMeterNbr(obj.getString("electricityMeterNbr"));
					excelData.setElectricityTypeDesc(obj.getString("electricityTypeDesc"));
				}
			}
			if("LIE".equals(type)){//專線
				TbSiteLineApplyExample siteApplyExample = new TbSiteLineApplyExample();
				List<TbSysLookup> lookupCircuitType_List = null,lookupCircuitTypeList=null,lookupIspList=null;
				siteApplyExample.createCriteria().andLINE_IDEqualTo(obj.getString("contractNo")).andLINE_STATUSEqualTo("2");
				List<TbSiteLineApply> lineApplyList=pay012Dao.getSiteLineApplyData(siteApplyExample);
				try{
					if(lineApplyList.size()>0){
						try{
							lookupIspList=payPublicUtilService.getLookupByTypeAndCode("LINE_ISP", lineApplyList.get(0).getLINE_PURPOSE());
							excelData.setVendorName(lookupIspList.get(0).getNAME());
						}catch(Exception e){log.error("專線業者取得為空!!!!!");}	
						try{
							lookupCircuitTypeList=payPublicUtilService.getLookupByTypeAndCode("CIRCUIT_TYPE", lineApplyList.get(0).getLINE_PURPOSE());
							excelData.setLineTypeDesc(lookupCircuitTypeList.get(0).getNAME());
						}catch(Exception e){log.error("專線類型取得為空!!!!!");}	
						try{
							lookupCircuitType_List=payPublicUtilService.getLookupByTypeAndCode("CIRCUIT_TYPE_"+lineApplyList.get(0).getLINE_TYPE(), lineApplyList.get(0).getLINE_PURPOSE());
							excelData.setVelocityDesc(lookupCircuitType_List.get(0).getNAME());
						}catch(Exception e){log.error("專線速率取得為空!!!!!");}
					}
					excelData.setLineSDate(String.valueOf(lineApplyList.get(0).getSTART_DATE()));
					excelData.setLineEDate(String.valueOf(lineApplyList.get(0).getEND_DATE()));
				}catch(Exception e){log.error("專線內容取得為空!!!!!");}
			}
			
			excelData.setLocationId(obj.getString("locationId"));
			excelData.setLocType(obj.getString("locType"));
			excelData.setThirdGSiteId(obj.getString("thirdGSiteId"));
			excelData.setFourthGSiteId(obj.getString("fourthGSiteId"));
			excelData.setThirdGstatusDscr(obj.getString("thirdGstatusDscr"));
			excelData.setFourthGstatusDscr(obj.getString("fourthGstatusDscr"));
			HashMap<String,Object> siteMap=new HashMap<String,Object>();
			siteMap.put("siteId", obj.getString("thirdGSiteId"));
			try{
				excelData.setThirdEqpName(pay012Dao.selectSiteEqpName(siteMap).getEqpName());
			}catch(Exception e){log.error("3G Equipment取得為空!!!!!");}
			try{
				excelData.setThirdEqpModel(pay012Dao.selectSiteEqpModel(siteMap).getEqpModel());
			}catch(Exception e){log.error("3G 設備形態取得為空!!!!!");}
			siteMap.clear();
			siteMap.put("siteId", obj.getString("fourthGSiteId"));
			try{
				excelData.setFourthEqpName(pay012Dao.selectSiteEqpName(siteMap).getEqpName());
			}catch(Exception e){log.error("4G Equipment取得為空!!!!!");}
			try{
				excelData.setFourthEqpModel(pay012Dao.selectSiteEqpModel(siteMap).getEqpModel());
			}catch(Exception e){log.error("4G 設備形態取得為空!!!!!");}
			TbSiteLocation locationData=pay012Dao.getLocationByKey(obj.getString("locationId"));
			excelData.setCity(locationData.getCITY());
			excelData.setArea(locationData.getAREA());
			excelData.setAddr(locationData.getADDR());
			excelData.setTotalTaxAmt(String.valueOf(obj.getInt("taxAmt")+obj.getInt("businessTax")));
			list.add(excelData);
		}
    	
    	return list;
    }
	private String[] genExcelTitle(String type){
    	String[] nonTitle={};
    	if("REE".equals(type)){//租金
    		String[] title={"租約編號","付款週期","租約生效日","租約終止日","區域","類型","3G基站編號","3G Equipment","3G設備型態","3G狀態","4G基站編號","4G設備型態","4G狀態","4G Equipment"
    				,"縣市","鄉鎮區市","地址","預估年月","基站租金"};
    		return title;
    	}else if("ELE".equals(type)){//電費
    		String[] title={"租約編號","電錶號碼","電費型態","租約生效日","租約終止日","區域","類型","3G基站編號","3G Equipment","3G設備型態","3G狀態","4G基站編號","4G設備型態","4G狀態","4G Equipment",
    				"縣市","鄉鎮區市","地址","電費"};
    		return title;
    	}else if("LIE".equals(type)){//專線
    		String[] title={"專線業者","專線類型","專線速率","專線生效日","專線終止日","區域","類型","3G基站編號","3G Equipment","3G設備型態","3G狀態","4G基站編號","4G設備型態","4G狀態","4G Equipment",
    				"縣市","鄉鎮區市","地址","專線租金"};
    		return title;
    	}
    	return nonTitle;
    }
	
	private HSSFCellStyle setCellStyle(HSSFSheet worksheet){
		Font font = worksheet.getWorkbook().createFont();  
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);  //加粗字體
		HSSFCellStyle headerCellStyle = worksheet.getWorkbook().createCellStyle(); 
        headerCellStyle.setFillBackgroundColor(HSSFColor.RED.index);  
        headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);  
        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);  //左右置中
        headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直置中
        headerCellStyle.setWrapText(true);  
        headerCellStyle.setFont(font);  
        headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);  //設定儲存格為粗邊框 
        return headerCellStyle;
	}
	
	 /**
     * 產生內容及儲存格樣式
     * @param excelRow
     * @param cellValue
     * @param cellNum
     * @param cellStyleMap
     */
	private void buildCell(HSSFRow excelRow, String[] cellValue,
			int cellNum) {
		for (int index = 0; index < cellNum; index++) {
			HSSFCell cell = excelRow.createCell(index);
			cell.setCellValue(cellValue[index]);
		}
	}
	
	private String[] setCellValue(String type,EstimateExcelDTO result){
		String[] nonTitle={};
    	if("REE".equals(type)){//租金
    		String[] title={result.getContractNo(),result.getPaymentPeriod(),result.getLeaseValidBeginDate(),result.getLeaseValidEndDate(),result.getDomain(),result.getLocType(),
    				result.getThirdGSiteId(),result.getThirdEqpName(),result.getThirdEqpModel(),result.getFourthGSiteId(),result.getFourthEqpName(),result.getFourthEqpModel(),
    				result.getCity(),result.getArea(),result.getAddr(),result.getYearMonth(),result.getTotalTaxAmt()};
    		return title;
    	}else if("ELE".equals(type)){//電費
    		String[] title={result.getContractNo(),result.getElectricityMeterNbr(),result.getElectricityTypeDesc(),result.getLeaseValidBeginDate(),result.getLeaseValidEndDate(),
    				result.getArea(),result.getLocType(),result.getThirdGSiteId(),result.getThirdEqpName(),result.getThirdEqpModel(),result.getFourthGSiteId(),result.getFourthEqpName(),result.getFourthEqpModel(),
    				result.getCity(),result.getArea(),result.getAddr(),result.getTotalTaxAmt()};
    		return title;
    	}else if("LIE".equals(type)){//專線
    		String[] title={result.getVendorName(),result.getLineTypeDesc(),result.getVelocityDesc(),result.getLineSDate(),result.getLineEDate(),result.getDomain(),result.getLocationId(),
    				result.getLocType(),result.getThirdGSiteId(),result.getThirdEqpName(),result.getThirdEqpModel(),result.getThirdGstatusDscr(),result.getFourthGSiteId(),result.getFourthEqpName(),result.getFourthEqpModel(),result.getFourthGstatusDscr(),
    				result.getCity(),result.getArea(),result.getAddr(),result.getTotalTaxAmt()};
    		return title;
    	}
    	return nonTitle;
	}
	 /**
     * 產生流水號
     */
	public String getEstimateNo(String type){
		//String tempNo="",returnNo="",genInitSeq="0001",genInitFirstWord=type,dateString="";
		String dateString="";
		dateString=payPublicUtilService.getTodayStringWithoutTime();
		/*
		String percentDateString="%"+dateString+"%";
		String percentYype=type+"%";
		TbPayExpenseEstimateExample example = new TbPayExpenseEstimateExample();
		example.createCriteria().andESTIMATE_NOLike(percentYype).andESTIMATE_NOLike(percentDateString);
		List<TbPayExpenseEstimate> list = pay012Dao.selectEstimate(example);
		try{
			TbPayExpenseEstimate check=(TbPayExpenseEstimate)list.get(list.size()-1);
			tempNo=check.getESTIMATE_NO();
			tempNo=tempNo.substring(tempNo.length()-4,tempNo.length());
			int plusOneSeq = Integer.valueOf(tempNo);
			log.debug("getEstimateNo plusOneSeqString:"+plusOneSeq);
			tempNo = String.format("%04d",plusOneSeq + 1);
			log.debug("getEstimateNo tempNo:"+tempNo);
			returnNo = genInitFirstWord + dateString + tempNo;
		}catch(ArrayIndexOutOfBoundsException e){
			log.debug("ArrayIndexOutOfBoundsException");
			returnNo=genInitFirstWord+dateString+genInitSeq;
		}
		catch(NullPointerException e){
			log.debug("NullPointerException");
			returnNo=genInitFirstWord+dateString+genInitSeq;
		}
		*/
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("PI_PREFIX", type);
		map.put("PI_APP_DATE", dateString);
		pay012Dao.payPcGetSeqnoByMap(map);
		if (!map.get("PO_ERR_CDE").equals("00")) {
			log.error("自動產生請款單號Call PAY_PC_GET_SEQNO Error：ERR_CDE="
					+ map.get("PO_ERR_CDE") + ", PO_ERR_MSG="
					+ map.get("PO_ERR_MSG"));
			throw new NomsException("99", "自動產生請款單號執行錯誤：" + map.get("PO_ERR_MSG"));
		}
		return (String)map.get("PO_SEQNO");
	}
	public double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	
	public double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	
	
	@Transactional
	public void deleteEstimate(String ESTIMATE_NO) throws NomsException,Exception{
		try{
			pay012Dao.deleteEstimate(ESTIMATE_NO);
		}catch(Exception e){
			throw new NomsException("刪除 TB_PAY_EXPENSE_ESTIMATE 錯誤："+e.getLocalizedMessage());
		}
	}
	//是否預估該年月區間
	public List<TbPayExpenseEstimate> selectEstimate(String type,String startVal,String endVal){
		TbPayExpenseEstimateExample example = new TbPayExpenseEstimateExample();
		example.createCriteria().andESTIMATE_TYPEEqualTo(type).andESTIMATE_BEGIN_YEAR_MONTHEqualTo(startVal).andESTIMATE_END_YEAR_MONTHEqualTo(endVal);
		return pay012Dao.selectEstimate(example);
	}
	//專線預估
	public List<TbPayPaymentRequestDTO> selectLieData(String startVal,String endVal) throws ParseException{
		PageList<TbPayPaymentRequestDTO> FullBacklist = new PageList<TbPayPaymentRequestDTO>();//回傳list
		PageList<TbPayPaymentRequestDTO> tempAmtlist = new PageList<TbPayPaymentRequestDTO>();//金額暫存list
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		List<TbPayPaymentRequestDTO> list=pay012Dao.selectLieData();
		for(int i=0;i<list.size();i++){//組合加入金額的暫存list
			LeaseMainDTO lsMainObject=pay012Dao.getMaxEndDate(list.get(i).getCONTRACT_NO());
			try{
				list.get(i).setMaxLsEDate(lsMainObject.getLS_E_DATE());
			}catch(Exception e){}
			try{//判斷與租約間 請款迄日
				if(list.get(i).getPAYMENT_REQ_END_DATE().after(list.get(i).getMaxLsEDate())){
					list.get(i).setPAYMENT_REQ_END_DATE(list.get(i).getMaxLsEDate());
				}
			}catch(Exception e){}
//			String makeStartDateStr=startVal+"/01";
//			String makeEndDateStr=endVal+"/01";
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
//			Date makeStartDate=null,makeEndDate=null;
//			try {
//				makeStartDate=formatter.parse(makeStartDateStr);
//				makeEndDate=formatter.parse(makeEndDateStr);
//			} catch (ParseException e) {}
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("sDate", payPublicUtilService.calulateDate(formatter.format(list.get(i).getPAYMENT_REQ_END_DATE()), 0, -1, 0));
			map.put("eDate", list.get(i).getPAYMENT_REQ_END_DATE());
			map.put("contractNo", list.get(i).getCONTRACT_NO());
			List<TbPayPaymentRequestDTO> eleAmtList=pay012Dao.getLieAmt(map);			
			try{
				if(eleAmtList.size()>0){
					for(int i2=0;i2<eleAmtList.size();i2++){
						try{
						}catch (NullPointerException e) {}
						tempAmtlist.add(this.makeEleAmtTempData(eleAmtList.get(i2)));
					}
				}
			}catch (NullPointerException e) {}
		}
		for(int i=0;i<tempAmtlist.size();i++){//組合完整list
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("contractNo", tempAmtlist.get(i).getCONTRACT_NO());
			map.put("locationId", tempAmtlist.get(i).getLocationId());
			List<SiteAlocDetailDTO> lsList=pay012Dao.getLsApplyData(map);
			//for(int i2=0;i2<lsList.size();i2++){
				FullBacklist.add(this.makeFullLieData(tempAmtlist.get(i), lsList));
			//}
		}
		return FullBacklist;
	}
	public TbPayPaymentRequestDTO makeFullLieData(TbPayPaymentRequestDTO listData1,List<SiteAlocDetailDTO> lsList){
		TbPayPaymentRequestDTO data=new TbPayPaymentRequestDTO();
		data.setCONTRACT_NO(listData1.getCONTRACT_NO());
		data.setLocationId(listData1.getLocationId());
		TbPayElectricityExample example =new TbPayElectricityExample();
		example.createCriteria().andCONTRACT_NOEqualTo(listData1.getCONTRACT_NO());
		try{
			data.setLineName(pay012Dao.getElectricityData(example).get(0).getLINE_NAME());//有多筆疑慮 只取第0筆 若無值就帶空= =a
		}catch(Exception e){}
		for(int i=0;i<lsList.size();i++){
			if("U".equals(lsList.get(i).getFEQ_TYPE())){//3G
				data.setThirdGSiteId(lsList.get(i).getSiteId());
				data.setThirdGstatusDscr(SiteStatus.detectLabel(lsList.get(i).getSiteId()));
			}
			if("L".equals(lsList.get(i).getFEQ_TYPE())){//4G
				data.setFourthGSiteId(lsList.get(i).getSiteId());
				data.setFourthGstatusDscr(SiteStatus.detectLabel(lsList.get(i).getSiteId()));
			}
		}
		try{
			data.setLocType(SiteType.detectLabel(pay012Dao.getLocationByKey(listData1.getLocationId()).getLOC_TYPE()));
		}catch(NullPointerException e){}
		//circuitUses
		try{
			TbSiteLineApplyExample siteApplyExample = new TbSiteLineApplyExample();
			siteApplyExample.createCriteria().andLINE_IDEqualTo(listData1.getCONTRACT_NO()).andLINE_STATUSEqualTo("2");
			List<TbSiteLineApply> lineApplyList=pay012Dao.getSiteLineApplyData(siteApplyExample);
			List<TbSysLookup> lookupList=payPublicUtilService.getLookupByTypeAndCode("CIRCUIT_USES", lineApplyList.get(0).getLINE_PURPOSE());
			data.setCircuitUses(lookupList.get(0).getNAME());
		}catch(Exception e){}
		data.setS_tax_exclusive_amt(new BigDecimal(listData1.getS_tax_exclusive_amt().doubleValue()));//未稅金額
		data.setS_business_tax(new BigDecimal(listData1.getS_business_tax().doubleValue()));//應稅金額
		return data;
	}
	//電費預估
	public List<TbPayPaymentRequestDTO> selectEleData(String startVal,String endVal) throws ParseException{
		PageList<TbPayPaymentRequestDTO> FullBacklist = new PageList<TbPayPaymentRequestDTO>();//回傳list
		PageList<TbPayPaymentRequestDTO> tempAmtlist = new PageList<TbPayPaymentRequestDTO>();//金額暫存list
		List<TbPayPaymentRequestDTO> list=pay012Dao.selectEleData();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		for(int i=0;i<list.size();i++){//組合加入金額的暫存list
			LeaseMainDTO lsMainObject=pay012Dao.getMaxEndDate(list.get(i).getCONTRACT_NO());
			try{
				list.get(i).setMaxLsEDate(lsMainObject.getLS_E_DATE());
			}catch(Exception e){}
			try{//判斷與租約間 請款迄日
				if(list.get(i).getPAYMENT_REQ_END_DATE().after(list.get(i).getMaxLsEDate())){
					list.get(i).setPAYMENT_REQ_END_DATE(list.get(i).getMaxLsEDate());
				}
			}catch(Exception e){}			
			/*改成只撈一個月份？ 會不會改回來 u never know
			String makeStartDateStr=startVal+"/01";
			String makeEndDateStr=endVal+"/01";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			Date makeStartDate=null,makeEndDate=null;
			try {
				makeStartDate=formatter.parse(makeStartDateStr);
				makeEndDate=formatter.parse(makeEndDateStr);
			} catch (ParseException e) {}
			*/
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("sDate", payPublicUtilService.calulateDate(formatter.format(list.get(i).getPAYMENT_REQ_END_DATE()), 0, -1, 0));
			map.put("eDate", list.get(i).getPAYMENT_REQ_END_DATE());
			map.put("contractNo", list.get(i).getCONTRACT_NO());
			List<TbPayPaymentRequestDTO> eleAmtList=pay012Dao.getEleAmt(map);
			try{
				if(eleAmtList.size()>0){
					for(int i2=0;i2<eleAmtList.size();i2++){
						tempAmtlist.add(this.makeEleAmtTempData(eleAmtList.get(i2)));
					}
				}
			}catch (NullPointerException e) {}
		}
		for(int i=0;i<tempAmtlist.size();i++){//組合完整list
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("lsNo", tempAmtlist.get(i).getCONTRACT_NO());
			map.put("locationId", tempAmtlist.get(i).getLocationId());
			List<SiteAlocDetailDTO> lsList=pay012Dao.getLsSiteData(map);
			//for(int i2=0;i2<lsList.size();i2++){
				FullBacklist.add(this.makeFullData(tempAmtlist.get(i), lsList));
			//}
		}
		return FullBacklist;
	}
	
	public TbPayPaymentRequestDTO makeFullData(TbPayPaymentRequestDTO listData1,List<SiteAlocDetailDTO> lsList){
		TbPayPaymentRequestDTO data=new TbPayPaymentRequestDTO();
		data.setCONTRACT_NO(listData1.getCONTRACT_NO());
		data.setLocationId(listData1.getLocationId());
		TbLsMainExample example =new TbLsMainExample();
		example.createCriteria().andLS_NOEqualTo(listData1.getCONTRACT_NO()).andLS_STATUSEqualTo("1");
		try{
			data.setLsName(pay012Dao.getLsMainByExample(example).get(0).getLS_NAME());//有多筆疑慮 只取第0筆 若無值就帶空= =a
		}catch(Exception e){}
		for(int i=0;i<lsList.size();i++){
			if("U".equals(lsList.get(i).getFEQ_TYPE())){//3G
				data.setThirdGSiteId(lsList.get(i).getSiteId());
				data.setThirdGstatusDscr(SiteStatus.detectLabel(lsList.get(i).getSiteId()));
			}
			if("L".equals(lsList.get(i).getFEQ_TYPE())){//4G
				data.setFourthGSiteId(lsList.get(i).getSiteId());
				data.setFourthGstatusDscr(SiteStatus.detectLabel(lsList.get(i).getSiteId()));
			}
		}
		try{
			data.setLocType(SiteType.detectLabel(pay012Dao.getLocationByKey(listData1.getLocationId()).getLOC_TYPE()));
		}catch(NullPointerException e){}
		try{
			TbPayLookupCode lookupData=payPublicUtilService.getPayLookupByTypeAndCode("ELECTRICITY_TYPE", listData1.getElectricityType());
			data.setElectricityTypeDesc(lookupData.getLOOKUP_CODE_DESC());
		}catch(NullPointerException e){}
		data.setS_tax_exclusive_amt(new BigDecimal(listData1.getS_tax_exclusive_amt().doubleValue()));//未稅金額
		data.setS_business_tax(new BigDecimal(listData1.getS_business_tax().doubleValue()));//應稅金額
		data.setElectricityMeterNbr(listData1.getElectricityMeterNbr());
		return data;
	}
	public TbPayPaymentRequestDTO makeEleAmtTempData(TbPayPaymentRequestDTO listData2){
		TbPayPaymentRequestDTO data=new TbPayPaymentRequestDTO();
		data.setCONTRACT_NO(listData2.getCONTRACT_NO());
		data.setLocationId(listData2.getLocationId());
		data.setDomain(listData2.getDomain());
		data.setElectricityType(listData2.getElectricityType());
		data.setS_tax_exclusive_amt(listData2.getS_tax_exclusive_amt());
		data.setS_business_tax(listData2.getS_business_tax());
		data.setElectricityMeterNbr(listData2.getElectricityMeterNbr());
		return data;
	}
	//租金預估
	@SuppressWarnings("deprecation")
	public List<TbPayLocationInfoDTO> selectReeData(String startVal,String endVal,Date appDate) throws ParseException {
		PageList<TbPayLocationInfoDTO> FullBacklist = new PageList<TbPayLocationInfoDTO>();//回傳list
		PageList<TbPayLocationInfoDTO> DTOlist = new PageList<TbPayLocationInfoDTO>();//暫存list
		Set<locationObject> set = new HashSet<locationObject>();//不重複的domain location contract
		List<TbPayLocationInfoDTO> list=pay012Dao.selectReeData();
		for(int i=0;i<list.size();i++){
			set.add(new locationObject(list.get(i).getDOMAIN(),list.get(i).getCONTRACT_NO(),list.get(i).getLOCATION_ID()));
		}
		//把不重複清單放到回傳list
		Iterator<locationObject> iter = set.iterator();
		while(iter.hasNext()){
			TbPayLocationInfoDTO dto = new TbPayLocationInfoDTO();
			locationObject setObj=iter.next();
			dto.setCONTRACT_NO(setObj.contractNo);
			dto.setDOMAIN(setObj.domain);
			dto.setLOCATION_ID(setObj.locationId);
			DTOlist.add(dto);
		}
		
		for(int i=0;i<list.size();i++){//逐筆檢查日期 寫入DTO清單
			for(int i2=0;i2<DTOlist.size();i2++){
				if(list.get(i).getCONTRACT_NO().equals(DTOlist.get(i2).getCONTRACT_NO()) &&
				   list.get(i).getDOMAIN().equals(DTOlist.get(i2).getDOMAIN())	&&
				   list.get(i).getLOCATION_ID().equals(DTOlist.get(i2).getLOCATION_ID())){
					TbPayLocationInfoDTO dto = new TbPayLocationInfoDTO();
					try{
						Date DtoDate=DTOlist.get(i2).getPRE_PR_END_DATE();
						Date DtoLeaseDate=DTOlist.get(i2).getLease_valid_end_date();
						Date listDate=list.get(i).getPRE_PR_END_DATE();//必定有值
						Date leaseDate=list.get(i).getLease_valid_end_date();//必定有值
						if(DtoDate.before(listDate)){
							//DTOlist.get(i2).setPRE_PR_END_DATE(list.get(i).getPRE_PR_END_DATE());
							dto.setPRE_PR_END_DATE(list.get(i).getPRE_PR_END_DATE());
						}
						if(DtoLeaseDate.before(leaseDate)){
							//DTOlist.get(i2).setLeaseValidEndDate(list.get(i).getLeaseValidEndDate());
							dto.setLeaseValidEndDate(list.get(i).getLeaseValidEndDate());
						}
						
						//DTOlist.get(i2).setPayment_period(list.get(i).getPayment_period());
						dto.setPayment_period(list.get(i).getPayment_period());
					}catch(NullPointerException e){
						log.error(e.getMessage());
						dto.setPRE_PR_END_DATE(list.get(i).getPRE_PR_END_DATE());
						dto.setLeaseValidEndDate(list.get(i).getLeaseValidEndDate());
						dto.setPayment_period(list.get(i).getPayment_period());
						//DTOlist.get(i2).setPRE_PR_BEGIN_DATE(list.get(i).getPRE_PR_BEGIN_DATE());
						//DTOlist.get(i2).setPRE_PR_END_DATE(list.get(i).getPRE_PR_END_DATE());
						//DTOlist.get(i2).setLeaseValidEndDate(list.get(i).getLeaseValidEndDate());
					}
					dto.setCONTRACT_NO(list.get(i).getCONTRACT_NO());
					dto.setDOMAIN(list.get(i).getDOMAIN());
					dto.setLOCATION_ID(list.get(i).getLOCATION_ID());
					DTOlist.set(i2, dto);
				}
			}
		}
		//將有日期的清單 計算出預估日 並呼叫Procedure 組成完整list
		for(int i=0;i<DTOlist.size();i++){
			//計算每一筆的日期起訖
			String makeStartDateStr=startVal+"/"+(DTOlist.get(i).getPRE_PR_END_DATE().getDay()+1);
			//String makeEndDateStr=endVal+"/"+(DTOlist.get(i).getPRE_PR_END_DATE().getDate()+1);
			//String dateStr=DTOlist.get(i).getPRE_PR_END_DATE().getYear()+"/"+DTOlist.get(i).getPRE_PR_END_DATE().getMonth()+"/"+DTOlist.get(i).getPRE_PR_END_DATE().getDay();
			//String makeEndDateStr=payPublicUtilService.calulateDate(dateStr, 0, 1, 0);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat formatterYM = new SimpleDateFormat("yyyyMM");
			Date makeStartDate=null;//,makeEndDate=null;
				makeStartDate=formatter.parse(makeStartDateStr);
//				makeEndDate=formatter.parse(makeEndDateStr);
//				if(makeEndDate.before(DTOlist.get(i).getLeaseValidEndDate())){
//					makeEndDate=DTOlist.get(i).getLeaseValidEndDate();
//				}
				
				//用月份區間算出每一段金額
				Date calStartDate=null;
				for(int iDate = 0;iDate<payPublicUtilService.calulateMonthDiff(makeStartDate, DTOlist.get(i).getPRE_PR_END_DATE());iDate++){	
					if(iDate==0){
						calStartDate=makeStartDate;
					}else{
						calStartDate=formatter.parse(payPublicUtilService.calulateDate(formatter.format(calStartDate), 0, 1, 0));
					}
					String dateString = formatterYM.format(calStartDate);
					//calEndDate=formatter.parse(payPublicUtilService.calulateDate(formatter.format(calStartDate), 0, 1, 0));
					Map<String,Object> dataObj=payPublicUtilService.payPcGetAppAmt("",
							DTOlist.get(i).getDOMAIN(), DTOlist.get(i).getCONTRACT_NO(), DTOlist.get(i).getLOCATION_ID(), "","R",appDate,DTOlist.get(i).getPayment_period(),
							dateString,"R" );
					HashMap<String,Object> map=new HashMap<String,Object>();
					
					map.put("lsNo", DTOlist.get(i).getCONTRACT_NO());
					map.put("locationId", DTOlist.get(i).getLOCATION_ID());
					List<SiteAlocDetailDTO> lsList=pay012Dao.getLsSiteData(map);
					//for(int i2=0;i2<lsList.size();i2++){
						FullBacklist.add(this.makeFullDto(DTOlist.get(i), lsList, dataObj,calStartDate));
					//}
				}
		}
		return FullBacklist; 
	}
	public TbPayLocationInfoDTO makeFullDto(TbPayLocationInfoDTO DTO,List<SiteAlocDetailDTO> lsList,Map<String, Object> dataObj,Date calStartDate){
		TbPayLocationInfoDTO dto=new TbPayLocationInfoDTO();
		Double sBusinessTax,sTaxExclusiveAmt;
		dto.setCONTRACT_NO(DTO.getCONTRACT_NO());
		TbLsMainExample example =new TbLsMainExample();
		example.createCriteria().andLS_NOEqualTo(DTO.getCONTRACT_NO()).andLS_STATUSEqualTo("1");
		try{
			dto.setLsName(pay012Dao.getLsMainByExample(example).get(0).getLS_NAME());//有多筆疑慮 只取第0筆 若無值就帶空= =a
		}catch(Exception e){}
		dto.setLOCATION_ID(DTO.getLOCATION_ID());
		for(int i=0;i<lsList.size();i++){
			if("U".equals(lsList.get(i).getFEQ_TYPE())){//3G
				dto.setThirdGSiteId(lsList.get(i).getSiteId());
				dto.setThirdGstatusDscr(SiteStatus.detectLabel(lsList.get(i).getSiteId()));
			}
			if("L".equals(lsList.get(i).getFEQ_TYPE())){//4G
				dto.setFourthGSiteId(lsList.get(i).getSiteId());
				dto.setFourthGstatusDscr(SiteStatus.detectLabel(lsList.get(i).getSiteId()));
			}
		}
		try{
			dto.setLocType(pay012Dao.getLocationByKey(DTO.getLOCATION_ID()).getLOC_DESC());
		}catch(NullPointerException e){}
		Double totAppAmt=0.0,totBsTaxAmt=0.0,totInTaxAmt=0.0,totHsAmt=0.0;
		try{
			totAppAmt=Double.valueOf(String.valueOf(dataObj.get("PO_TOT_APP_AMT")));//含稅
		}catch(NumberFormatException e){}
		try{
			totBsTaxAmt=Double.valueOf(String.valueOf(dataObj.get("PO_TOT_BS_TAX_AMT")));//營業稅
		}catch(NumberFormatException e){}
		try{
			totInTaxAmt=Double.valueOf(String.valueOf(dataObj.get("PO_TOT_IN_TAX_AMT")));//所得稅
		}catch(NumberFormatException e){}
		try{
			totHsAmt=Double.valueOf(String.valueOf(dataObj.get("PO_TOT_HS_AMT")));//健保補充費
		}catch(NumberFormatException e){}
		sBusinessTax=this.add(this.add(totBsTaxAmt, totInTaxAmt), totHsAmt);//應稅金額
		sTaxExclusiveAmt=this.sub(this.add(this.add(totAppAmt, totHsAmt), totInTaxAmt), totBsTaxAmt);//未稅金額
//		dto.setS_business_tax(new BigDecimal(totAppAmt*lsDto.getALOC_RATIO().doubleValue()));//應稅金額
		dto.setS_business_tax(new BigDecimal(sBusinessTax));//應稅金額
//		dto.setS_tax_exclusive_amt(new BigDecimal(totAppAmt*lsDto.getALOC_RATIO().doubleValue()));//未稅金額
		dto.setS_tax_exclusive_amt(new BigDecimal(sTaxExclusiveAmt));//未稅金額
		dto.setTotBsTaxAmt(new BigDecimal(totBsTaxAmt));
		dto.setTotHsAmt(new BigDecimal(totHsAmt));
		dto.setTotInTaxAmt(new BigDecimal(totInTaxAmt));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM");
		String dateString = formatter.format(calStartDate);
		dto.setYearMonth(dateString);
		log.debug("makeFullDto");
		return dto;
	}
	class locationObject {
	    private String domain;
	    private String contractNo;
	    private String locationId;
	    locationObject(String domain, String contractNo, String locationId) {
	        this.domain = domain;
	        this.contractNo = contractNo;
	        this.locationId = locationId;
	    }

	    @Override
	    public int hashCode() {
	        int hash = 7;
	        hash = 47 * hash + Objects.hashCode(this.domain);
	        hash = 47 * hash + Objects.hashCode(this.contractNo);
	        hash = 47 * hash + Objects.hashCode(this.locationId);
	        return hash;
	    }

	    @Override
	    public boolean equals(Object obj) {
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final locationObject other = (locationObject) obj;
	        if (!Objects.equals(this.domain, other.domain)) {
	            return false;
	        }
	        if (!Objects.equals(this.contractNo, other.contractNo)) {
	            return false;
	        }
	        if (!Objects.equals(this.locationId, other.locationId)) {
	            return false;
	        }
	        return true;
	    }
	    
	    @Override
	    public String toString()  {
	        return String.format("(%s, %s)", domain, contractNo,locationId);
	    }
	}
	
	@Transactional
	public void processEstimate(String detail,String master,Date today,String estNoArray)throws NomsException ,Exception{
		JSONArray estimateDtl = new JSONArray(detail);
		JSONArray estimate = new JSONArray(master);
		JSONArray deleteEstimate = new JSONArray(estNoArray);
		try{
			if(deleteEstimate.length()>0){
				for (int i = 0; i < estimate.length(); i++) {
					JSONObject obj = deleteEstimate.getJSONObject(i);
					pay012Dao.deleteEstimate(obj.getString("estimateNo"));
				}	
			}
		}catch(Exception e){throw new NomsException("刪除 TB_PAY_EXPENSE_ESTIMATE 錯誤："+e.getLocalizedMessage());}
		for (int i = 0; i < estimateDtl.length(); i++) {
			JSONObject obj = estimateDtl.getJSONObject(i);
			TbPayExpenseEstimateDtl dtlData = this.maketbPayExpenseEstimateDtl(obj, today);
			try{
				pay012Dao.insertDtl(dtlData);
			}catch(Exception e){throw new NomsException("寫入 TB_PAY_EXPENSE_ESTIMATE_DTL 錯誤："+e.getLocalizedMessage());}
		}
		for (int i = 0; i < estimate.length(); i++) {
			JSONObject obj = estimate.getJSONObject(i);
			TbPayExpenseEstimate estimateData = this.maketbPayExpenseEstimate(obj, today);
			try{
				pay012Dao.insertMas(estimateData);
			}catch(Exception e){throw new NomsException("寫入 TB_PAY_EXPENSE_ESTIMATE 錯誤："+e.getLocalizedMessage());}
		}
	}
	
	public TbPayExpenseEstimateDtl maketbPayExpenseEstimateDtl(JSONObject obj,Date today) throws NomsException{
		TbPayExpenseEstimateDtl expenseEstimateDtlData = new TbPayExpenseEstimateDtl();
		try{
			expenseEstimateDtlData.setESTIMATE_NO(obj.getString("estimate_no"));
			expenseEstimateDtlData.setCONTRACT_NO(obj.getString("contract_no"));
			expenseEstimateDtlData.setSITE_ID(obj.getString("site_id"));
			expenseEstimateDtlData.setESTIMATE_AMT(new BigDecimal(obj.getString("estimate_amt")));
			expenseEstimateDtlData.setCR_TIME(today);
			expenseEstimateDtlData.setMD_TIME(today);
			expenseEstimateDtlData.setCR_USER(getLoginUser().getUsername());
			expenseEstimateDtlData.setMD_USER(getLoginUser().getUsername());
		}catch(JSONException e){throw new NomsException("maketbPayExpenseEstimateDtl: "+e.getMessage());}
		return expenseEstimateDtlData;
	}
	
	public TbPayExpenseEstimate maketbPayExpenseEstimate(JSONObject obj,Date today) throws NomsException{
		TbPayExpenseEstimate expenseEstimateData = new TbPayExpenseEstimate();
		try{
			expenseEstimateData.setESTIMATE_NO(obj.getString("estimate_no"));
			expenseEstimateData.setESTIMATE_BEGIN_YEAR_MONTH(obj.getString("estimate_begin_year_month"));
			expenseEstimateData.setESTIMATE_END_YEAR_MONTH(obj.getString("estimate_end_year_month"));
			expenseEstimateData.setESTIMATE_TYPE(obj.getString("estimate_type"));
			expenseEstimateData.setESTIMATE_USER(obj.getString("estimate_user"));
			expenseEstimateData.setCR_TIME(today);
			expenseEstimateData.setMD_TIME(today);
			expenseEstimateData.setCR_USER(getLoginUser().getUsername());
			expenseEstimateData.setMD_USER(getLoginUser().getUsername());
		}catch(JSONException e){throw new NomsException("maketbPayExpenseEstimate: "+e.getMessage());}
		return expenseEstimateData;
	}
}
