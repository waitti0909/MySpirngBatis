package com.foya.noms.service.inv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbInvBooking;
import com.foya.dao.mybatis.model.TbInvBookingExample;
import com.foya.dao.mybatis.model.TbInvInv;
import com.foya.dao.mybatis.model.TbInvInvExample;
import com.foya.dao.mybatis.model.TbInvMaterial;
import com.foya.dao.mybatis.model.TbInvMaterialExample;
import com.foya.dao.mybatis.model.TbInvTr;
import com.foya.dao.mybatis.model.TbInvTrDtl;
import com.foya.dao.mybatis.model.TbInvTrDtlExample;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.exception.NomsException;
import com.foya.noms.dao.inv.INV007Dao;
import com.foya.noms.dao.inv.InvPublicUtilDao;
import com.foya.noms.dto.ExcelImportDTO;
import com.foya.noms.dto.inv.TbInvInvDTO;
import com.foya.noms.dto.inv.TbInvTrDTO;
import com.foya.noms.dto.inv.TbInvTrDtlDTO;
import com.foya.noms.dto.inv.TbInvTrReadExcelDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.poi.Writer;
import com.foya.noms.service.common.PoiService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.noms.util.ReadExcelService;
import com.foya.workflow.exception.WorkflowException;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Service
public class INV007Service extends ReadExcelService<TbInvTrReadExcelDTO>{
	@Autowired
	private INV007Dao inv007Dao;
	@Autowired
	public InvPublicUtilDao invPublicUtilDao;
	@Autowired
	private PoiService poiService;
	@Autowired
	private WorkflowActionService workflowActionService;
	@Autowired
	private InvPublicUtilService invPublicUtilService;
	@Autowired
	private INV016Service inv016Service;
	public List<TbInvTr> search(HashMap<String,Object> dataObj){
		return inv007Dao.search(dataObj);
	}
	
	public TbInvTrDTO searchTrMasterDscr(HashMap<String,String> dataObj){
		return inv007Dao.searchTrMasterDscr(dataObj);
	}
	
	public int searchDtlAmt(String trNo,String matNo,Byte matStatus){
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("trNo", trNo);
		dataObj.put("matNo", matNo);
		dataObj.put("matStatus", new Byte(matStatus));
		return inv007Dao.searchDtlAmt(dataObj);
	}	
	public int searchTrActAmt(String trNo,String  matNo,int  trIn,Long dtlSeq,String returnTr){
		int returnCnt=0;
		try{
			returnCnt=inv007Dao.searchTrActAmt(trNo, matNo, trIn,dtlSeq,returnTr);
		}catch(Exception e){returnCnt=0;}
		return returnCnt;
	}
	
	public List<TbInvTrDtlDTO> searchDtlSeq(String trNo,String matNo,Byte matStatus){
		HashMap<String,Object> dataObj = new HashMap<String,Object>();
		dataObj.put("trNo", trNo);
		dataObj.put("matNo", matNo);
		dataObj.put("matStatus", new Byte(matStatus));
		return inv007Dao.searchDtlSeq(dataObj);
	}
	
	public List<TbInvTrDtlDTO> searchDtlPage(HashMap<String,Object> dataObj){
		List<TbInvTrDtlDTO> InvTrDtlList= inv007Dao.searchDtlPage(dataObj);
		for(int i=0;i<InvTrDtlList.size();i++){
			int trInCnt=0,trOutCnt=0;
			TbInvTrDtlDTO dto = (TbInvTrDtlDTO)InvTrDtlList.get(i);
			dto.setApp_qty(searchDtlAmt(dto.getTr_no(),dto.getMat_no(),dto.getMat_status()));
			List<TbInvTrDtlDTO> seqList=searchDtlSeq(dto.getTr_no(),dto.getMat_no(),dto.getMat_status());
			for(int seqI=0;seqI<seqList.size();seqI++){
				TbInvTrDtlDTO seqDto = seqList.get(seqI);
				
				trInCnt +=searchTrActAmt((String)dataObj.get("trNo"), dto.getMat_no(), 1,seqDto.getTr_dtl_no(),"-1");
				trOutCnt +=searchTrActAmt((String)dataObj.get("trNo"), dto.getMat_no(), 0,seqDto.getTr_dtl_no(),"-1");
			}
			dto.setTrInCnt(trInCnt);//調入
			dto.setTrOutCnt(trOutCnt);//調出
			
			TbInvMaterialExample materialExample = new TbInvMaterialExample();
//			materialExample.createCriteria().andIs_assetEqualTo(false).andMat_noEqualTo(dto.getMat_no());//物料狀態 is asset 0=true 1=false
			materialExample.createCriteria().andMat_noEqualTo(dto.getMat_no());
			List<TbInvMaterial> matList=inv007Dao.selectMaterial(materialExample);
			try{
			if(matList.size()>0){
				TbInvMaterial matObj = (TbInvMaterial)matList.get(0);
				dto.setCtrlType(matObj.getCtrl_type());
				}
			}catch(NullPointerException e){}			
			InvTrDtlList.set(i,dto);
		}
		return InvTrDtlList;
	}

	public Integer getInvQty(String whId,String matNo,String matStatus){
		Integer invQty=0;
		TbInvInvExample example = new TbInvInvExample();
		example.createCriteria().andWh_idEqualTo(whId).andMat_noEqualTo(matNo);
		List<TbInvInv> list=inv007Dao.getInvQty(example);
		for(int i=0;i<list.size();i++){
			if("1".equals(matStatus)){
				invQty+=list.get(i).getStd_qty();
			}else if("2".equals(matStatus)){
				invQty+=list.get(i).getWrd_qty();
			}else{
				invQty+=list.get(i).getWsp_qty();
			}
		}
		return invQty;
	}
	public List<TbInvInvDTO> addDtlRowCheck(HashMap<String,String> dataObj){
		return inv007Dao.addDtlRowCheck(dataObj);
	}
	
	public String insertTbInvTrSelective(TbInvTr data) throws NomsException{
		return inv007Dao.insertTbInvTrSelective(data);
	}
	//將儲存及申請動作合併成單一transaction
	@Transactional
	public void applyData(String saveMode, TbInvTr masData,
			 List<TbInvTrDtlDTO> dtlArray,
			 String userId,String clickType) throws NomsException{
		Date today=invPublicUtilService.getToday();
		TbInvBooking invBooking = new TbInvBooking();
		for (int i = 0; i < dtlArray.size(); i++) {
			TbInvTrDtlDTO exportObject = dtlArray.get(i);
			exportObject.setCr_user(userId);
			exportObject.setMd_user(userId);
			exportObject.setCr_time(today);
			exportObject.setMd_time(today);
			int appQty=exportObject.getApp_qty();
			if("editAdd".equals(saveMode)){//更新先刪除DTL資料
				TbInvTrDtlExample dtlExample = new TbInvTrDtlExample();
				dtlExample.createCriteria().andTr_noEqualTo(exportObject.getTr_no()).
				andMat_noEqualTo(exportObject.getMat_no()).andMat_statusEqualTo(exportObject.getMat_status());
				inv007Dao.deleteTbInvTrDtlByPrimarykey(dtlExample);
			}
//			if("S".equals(exportObject.getCtrlType())){//若是序號件要拆開一筆一筆寫入DTL
//				
//				for(int iApp=0;iApp<appQty;iApp++){
//					exportObject.setApp_qty(1);
//					inv007Dao.insertTbInvTrDtlSelective(exportObject);
//				}
//			}else{
				inv007Dao.insertTbInvTrDtlSelective(exportObject);
//			}
			if("apply".equals(clickType)){//申請動作要多寫入booking及invinv
				//處理booking
				invBooking=this.returnInvBooking(masData.getTr_out_wh_id(), exportObject.getMat_no(), 
						exportObject.getTr_no(), appQty, userId, today);
				inv007Dao.insertTbInvBookingSelective(invBooking);
				//處理inv
				TbInvInv invInv = new TbInvInv();
				invInv.setBooking_qty(appQty);
				invInv.setMd_user(userId);
				invInv.setMd_time(today);
				inv007Dao.updateTbInvInvSelective(invInv,masData.getTr_out_wh_id(), exportObject.getMat_no());
			}
		}
		
		//處理主檔
		masData.setCr_user(userId);
		masData.setMd_user(userId);
		masData.setCr_time(today);
		masData.setMd_time(today);
		masData.setApp_date(today);
		//簽核意見類別
		List<TbSysLookup> lookupList=invPublicUtilDao.getLookupByTypeAndCode("ORG_SPECIFIC_DEPT_ID", "LOGISTICS_DEPT_ID");
		TbSysLookup lookupData=(TbSysLookup)lookupList.get(0);
		if(StringUtils.equals(lookupData.getVALUE1(), getLoginUser().getHrDeptId()))
			masData.setProcessType("TransferApplyForLogistics");
		else
			masData.setProcessType("TransferApply");
		
		if("apply".equals(clickType)){
			//申請動作 要呼叫workflow
			this.insertTbInvTrSelective(masData, getLoginUser().getHrDeptId(),saveMode);
		}else if("save".equals(clickType)){
			//儲存動作
			if("add".equals(saveMode)){
				this.insertTbInvTrSelective(masData);
			}else if("editAdd".equals(saveMode)){
				this.updateTbInvTrSelective(masData);
			}
		}
	}
	
	public String insertTbInvTrSelective(TbInvTr data,String userDeptId,String saveMode) throws NomsException{
		String returnWord = "";
		List<TbSysLookup> lookupList=invPublicUtilDao.getLookupByTypeAndCode("ORG_SPECIFIC_DEPT_ID", "LOGISTICS_DEPT_ID");
		TbSysLookup lookupData=(TbSysLookup)lookupList.get(0);
		try{
			if("add".equals(saveMode)){
				this.insertTbInvTrSelective(data);
			}else if("editAdd".equals(saveMode)){
				this.updateTbInvTrSelective(data);
			}		
		//簽核申請
		if(StringUtils.equals(lookupData.getVALUE1(), userDeptId))
			workflowActionService.apply("TransferApplyForLogistics", data.getTr_no(), "調撥申請");	
		else
			workflowActionService.apply("TransferApply", data.getTr_no(), "調撥申請");
		}
		catch (WorkflowException e) {
			//如果產生簽核錯誤 一並刪除Dtl資料
			TbInvTrDtlExample dtlData = new TbInvTrDtlExample();
			dtlData.createCriteria().andTr_noEqualTo(data.getTr_no());
			deleteTbInvTrDtlByPrimarykey(dtlData);
			throw new NomsException(e.getMessage());
		}
		return returnWord;
	}
	@Transactional
	public String insertTbInvTrDtlSelective(TbInvTrDtl data) throws NomsException{
		return inv007Dao.insertTbInvTrDtlSelective(data);
	}
	
	public String updateTbInvTrSelective(TbInvTr data) throws NomsException{
		return inv007Dao.updateTbInvTrSelective(data);
	}
	@Transactional
	public String updateTbInvTrSelective(TbInvTr data,String userDeptId) throws NomsException{
		String returnWord = "";
		List<TbSysLookup> lookupList=invPublicUtilDao.getLookupByTypeAndCode("ORG_SPECIFIC_DEPT_ID", "LOGISTICS_DEPT_ID");
		TbSysLookup lookupData=(TbSysLookup)lookupList.get(0);
		try{
			returnWord = inv007Dao.updateTbInvTrSelective(data);	
		//簽核申請
		if(StringUtils.equals(lookupData.getVALUE1(), userDeptId))
			workflowActionService.apply("TransferApplyForLogistics", data.getTr_no(), "調撥申請");	
		else
			workflowActionService.apply("TransferApply", data.getTr_no(), "調撥申請");
		}
		catch (WorkflowException e) {
			throw new NomsException(e.getMessage());
		}
		return returnWord;
	}
	@Transactional
	public String deleteTbInvTrDtlByPrimarykey(TbInvTrDtlExample data) throws NomsException{
		return inv007Dao.deleteTbInvTrDtlByPrimarykey(data);
	}
	//單筆新增檢覈
	public List<TbInvTrDtlDTO> searchInvDtl(List<TbInvTrDtlDTO> InvTrDtlList,String wareHouseOut){
		String result="",status="";
		HashMap<String,String> dataObj=new HashMap<String,String>();
		List<TbInvTrDtlDTO>  returnList=new PageList<TbInvTrDtlDTO> (); 
		
		for(int i=0;i<InvTrDtlList.size();i++){
			TbInvTrDtlDTO data=(TbInvTrDtlDTO)InvTrDtlList.get(i);
			dataObj.put("matNo", data.getMat_no());
			dataObj.put("wareHouseOut", wareHouseOut);
			
			List<TbInvInvDTO> tbInvInvList=inv007Dao.addDtlRowCheck(dataObj);
			
			if(tbInvInvList.size()==0){
				returnList.add(getReturnData(data,"查無庫存","0",0));				
			}else{
				for(int TbInvInvCnt=0;TbInvInvCnt<tbInvInvList.size();TbInvInvCnt++){
					
					TbInvInv TbInvInvData=(TbInvInv)tbInvInvList.get(TbInvInvCnt);
					data.setCtrlType(data.getCtrlType());
					HashMap<String,Object> compareMap=new HashMap<String,Object>();
					
					if("1".equals(data.getMat_status().toString())){//可用品
						compareMap.put("stockQty", TbInvInvData.getStd_qty());
						compareMap.put("bookingQty", TbInvInvData.getBooking_qty());
						data.setBookingQty(Integer.valueOf((int)compareMap.get("bookingQty")));
					}else if("2".equals(data.getMat_status().toString())){//待送修
						compareMap.put("stockQty", TbInvInvData.getWrd_qty());
					}else if("3".equals(data.getMat_status().toString())){//待報廢
						compareMap.put("stockQty", TbInvInvData.getWsp_qty());
					}		
					//log.debug("調撥數判斷："+data.getMat_no()+" 狀態： "+data.getMat_status()+" appQty: "+data.getApp_qty()+" stockQty: "+(int)compareMap.get("stockQty"));
					if(data.getApp_qty()>(int)compareMap.get("stockQty")){
						status="0";
						result="調撥數大於庫存數，無法申請";
					}else{
						status="1";
						result="通過";
					}					
					if(new Byte("1").equals(data.getMat_status()) && (data.getApp_qty()>((int)compareMap.get("stockQty")-(int)compareMap.get("bookingQty")))
							&& data.getApp_qty()<=(int)compareMap.get("stockQty")){
						status="1";
						result="庫存已有其他申請單Booking";
					}
					returnList.add(getReturnData(data,result,status,(int)compareMap.get("stockQty")));
				}
			}
		}
		return returnList;
	}
	
	public String selectTrNoToday(String trNo,String personnel){
		String todayMaxTrNo="",returnSeqNo="",nowdate="",genInitSeq="001",genInitFirstWord="TR",plusOneSeqString="",domainId="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");    
		nowdate = sdf.format(new Date());    
		TbInvTr todayMaxTrNoDTO  = inv007Dao.selectMaxSeq(trNo);
		DeptDTO dept=invPublicUtilDao.searchDeptById(invPublicUtilDao.getPersonnelDeptId(personnel).getHrDeptId());
		TbOrgDomain domain=invPublicUtilDao.selectTbOrgDomainByPrimaryKey(dept.getDOMAIN());
		//domainId=domain.getID();
		domainId=getLoginUser().getDomain().getID();//一律使用登入者的domain
		try {
			todayMaxTrNo=todayMaxTrNoDTO.getTr_no();
			if ("".equals(todayMaxTrNo))
				returnSeqNo = genInitFirstWord + domainId + nowdate + genInitSeq;
			else
				todayMaxTrNo = todayMaxTrNo.substring(todayMaxTrNo.length()-3,
						todayMaxTrNo.length() );
			//log.debug("1 todayMaxTrNo:"+todayMaxTrNo);
			int plusOneSeq = Integer.valueOf(todayMaxTrNo);
			plusOneSeqString = String.format("%03d",plusOneSeq + 1);
			//log.debug("plusOneSeqString:"+plusOneSeqString);
			returnSeqNo = genInitFirstWord + domainId + nowdate + plusOneSeqString;
		} catch (NullPointerException e) {
			returnSeqNo = genInitFirstWord + domainId +nowdate + genInitSeq;
			//log.debug("returnSeqNo:"+returnSeqNo);
		}
		return returnSeqNo;
	}
	@Transactional
	public String deleteByPrimaryKey(String trNo) throws NomsException{
		return inv007Dao.deleteByPrimaryKey(trNo);
	}
	@Transactional
	public String inserttbInvBookingSelective(TbInvBooking data) throws NomsException{
		return inv007Dao.insertTbInvBookingSelective(data);
	}
	@Transactional
	public String updateTbInvInvSelective(TbInvInv data,String whId,String matNo) throws NomsException{
		return inv007Dao.updateTbInvInvSelective(data, whId, matNo);
	}	
	
	public void exportExcel(HttpServletResponse response, String sheetName, String fileName, List<TbInvInvDTO> result) {
		HSSFSheet worksheet = poiService.exportXLS(response, sheetName, fileName, 0, 0, 4);
		// 建立報表的 標題、日期、欄位名稱
		buildExcel(worksheet, result);
		// 輸出流
		Writer.write(poiService.setResponse(response,fileName), worksheet);
	}
	
	/** 
     * 建立欄位名稱與樣式
     */  
    private static void buildExcel(HSSFSheet worksheet, List<TbInvInvDTO> result) {  
        // 欄位字體  
        Font font = worksheet.getWorkbook().createFont();  
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);  //加粗字體
        // 儲存格樣式 headerCellStyle
        HSSFCellStyle headerCellStyle = worksheet.getWorkbook().createCellStyle(); 
        headerCellStyle.setFillBackgroundColor(HSSFColor.RED.index);  
        headerCellStyle.setFillPattern(CellStyle.FINE_DOTS);  
        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);  //左右置中
        headerCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);  //垂直置中
        headerCellStyle.setWrapText(true);  
        headerCellStyle.setFont(font);  
        headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);  //設定儲存格為粗邊框 
        //標題
        String[] title = {"料號", "品名", "物料狀態", "申請調撥數","庫存數","Booking數"};
        // 建立欄位名稱 
		HSSFRow header = worksheet.createRow(0);
		for(int i=0; i<title.length; i++){
			header.createCell(i).setCellValue(title[i]);
		}
		//寫入資料
		int rowIdx = 1;
		for(int i=0; i<result.size(); i++){
			HSSFRow excelRow = worksheet.createRow(rowIdx);
			TbInvInvDTO inv = (TbInvInvDTO) result.get(i);			
			excelRow.createCell(0).setCellValue(inv.getMat_no());
			excelRow.createCell(1).setCellValue(inv.getMatName());
			excelRow.createCell(2).setCellValue(inv.getMatStatus());
			excelRow.createCell(3).setCellValue("0");
			excelRow.createCell(4).setCellValue(inv.getInv_qty());
			excelRow.createCell(5).setCellValue(inv.getBooking_qty());
			rowIdx++;
		}	
    }
    //批次新增檢覈
    public List<TbInvTrDtlDTO> searchBatchApply(List<ExcelImportDTO<TbInvTrReadExcelDTO>> InvTrDtlList,String wareHouseOut){
    		String result="",status="";
    		HashMap<String,String> dataObj=new HashMap<String,String>();
		List<TbInvTrDtlDTO>  returnList=new PageList<TbInvTrDtlDTO> (); 
		List<TbInvTrReadExcelDTO> applyList = new ArrayList<TbInvTrReadExcelDTO>(); // 正確資料List
		List<TbInvTrReadExcelDTO> errorList = new ArrayList<TbInvTrReadExcelDTO>(); // 錯誤資料List
		
		for (ExcelImportDTO<TbInvTrReadExcelDTO> model : InvTrDtlList) {
			if (StringUtils.isNotEmpty(model.getErrorMsgs()) && !"不寫入".equals(model.getErrorMsgs())) {
				errorList.add(model.getRecord());
			} else {
				if(!"不寫入".equals(model.getErrorMsgs())){
					applyList.add(model.getRecord());
				}
			}
		}
		//錯誤清單處理
		for(int i=0;i<errorList.size();i++){
			TbInvTrReadExcelDTO data=(TbInvTrReadExcelDTO)errorList.get(i);
			data.setBookingQty("0");
			returnList.add(getReturnData(data,"輸入文件內容值有誤，請重新檢核","0",0));
		}
		//正確清單處理
		for(int i=0;i<applyList.size();i++){
			TbInvTrReadExcelDTO data=(TbInvTrReadExcelDTO)applyList.get(i);
			dataObj.put("matNo", data.getMatNo());
			dataObj.put("wareHouseOut", wareHouseOut);
			
			List<TbInvInvDTO> tbInvInvList=inv007Dao.addDtlRowCheck(dataObj);
			//如果查無庫存！
			if(tbInvInvList.size()==0){
				data.setBookingQty("0");
				returnList.add(getReturnData(data,"查無庫存","0",0));				
			}else{
				for(int TbInvInvCnt=0;TbInvInvCnt<tbInvInvList.size();TbInvInvCnt++){
					TbInvInvDTO TbInvInvData=(TbInvInvDTO)tbInvInvList.get(TbInvInvCnt);
					HashMap<String,Object> compareMap=new HashMap<String,Object>();
					data.setCtrlType(TbInvInvData.getCtrlType());
					//匯入的是中文名稱 只能用中文判斷 0rz...
					if("可用品".equals(data.getMatStatus().toString())){//可用品
						compareMap.put("stockQty", TbInvInvData.getStd_qty());
						compareMap.put("bookingQty", TbInvInvData.getBooking_qty());
						data.setBookingQty(String.valueOf(TbInvInvData.getBooking_qty()));
					}else if("待送修".equals(data.getMatStatus().toString())){//待送修
						compareMap.put("stockQty", TbInvInvData.getWrd_qty());
					}else if("待報廢".equals(data.getMatStatus().toString())){//待報廢
						compareMap.put("stockQty", TbInvInvData.getWsp_qty());
					}
					//log.debug("調撥數判斷："+data.getMatNo()+" 狀態： "+data.getMatStatus()+" appQty: "+data.getAppQty()+" stockQty: "+(int)compareMap.get("stockQty"));
					if(Integer.valueOf(data.getAppQty())>(int)compareMap.get("stockQty") ){
						result="調撥數大於庫存數，無法申請";
						status="0";
					}else{
						result="通過";
						status="1";
					}
					if("可用品".equals(data.getMatStatus()) && Integer.valueOf(data.getAppQty())>((int)compareMap.get("stockQty")-(int)compareMap.get("bookingQty"))
							&& (int)compareMap.get("stockQty")>=Integer.valueOf(data.getAppQty())){
						result="庫存已有其他申請單Booking";
						status="1";
					}
					returnList.add(getReturnData(data,result,status,(int)compareMap.get("stockQty")));
				}
			}
		}
		return returnList;
	}

	@Override
	protected TbInvTrReadExcelDTO setEntity(String[] oneRowCellsArray)
			throws Exception {
		TbInvTrReadExcelDTO dto = new TbInvTrReadExcelDTO();
		if (oneRowCellsArray.length == 0) {
			return null;
		}
			dto.setMatNo(oneRowCellsArray[0]);
			dto.setMatName(oneRowCellsArray[1]);
			dto.setMatStatus(oneRowCellsArray[2]);
			try{
			dto.setAppQty(oneRowCellsArray[3]);
			}catch(ArrayIndexOutOfBoundsException e){
				dto.setAppQty("");
			}catch(NumberFormatException e){
				dto.setAppQty("");			
			}
			return dto;
	}

	@Override
	protected String validateModel(Integer rowLine, TbInvTrReadExcelDTO record)
			throws Exception {
		String returnWord="";
		try{
			record.getMatNo().equals(null);
		}catch(NullPointerException e){
			returnWord="fail";
			record.setMatNo("錯誤，料號不得為空值");
		}
		try{
			record.getMatName().equals(null);
		}catch(NullPointerException e){
			returnWord="fail";
			record.setMatName("錯誤，品名不得為空值");
		}
		try{
			record.getMatStatus().equals(null);
		}catch(NullPointerException e){
			returnWord="fail";
			record.setMatStatus("錯誤，物料狀態不得為空值");
		}
		try{
			if(rowLine!=1){
				List<TbSysLookup> list=invPublicUtilDao.getLookupByTypeAndName("MAT_STATUS", record.getMatStatus());
				list.get(0).getCODE();
			}
		}catch(IndexOutOfBoundsException e){
			returnWord="fail";
			record.setMatStatus("錯誤，物料狀態不符合設定值");
		}
		catch(NullPointerException e){
			returnWord="fail";
			record.setMatStatus("錯誤，物料狀態不符合設定值");
		}
		try{
			record.getAppQty().equals(null);
			if(rowLine!=1){//檢查抬頭使用！
				Integer.valueOf(record.getAppQty());
			}
			
			if("0".equals(record.getAppQty()) && rowLine!=1){
				returnWord="不寫入";
			}
		}catch(NullPointerException e){
			returnWord="fail";
			record.setAppQty("錯誤，申請調撥數不得為空值");
		}catch(NumberFormatException e){
			returnWord="fail";
			record.setAppQty("錯誤，申請調撥數請輸入數字");
		}
		return returnWord;
	}
	//批次申請回傳申請資料
	public TbInvTrDtlDTO getReturnData(TbInvTrReadExcelDTO data,String result,String status,int stockQty){
		TbInvTrDtlDTO returnData=new TbInvTrDtlDTO();
		returnData.setMatStatusName(data.getMatStatus());
		returnData.setMat_no(data.getMatNo());
		if("可用品".equals(data.getMatStatus())){
			returnData.setMat_status(Byte.valueOf("1"));
			returnData.setBookingQty(Integer.valueOf(data.getBookingQty()));
		}
		if("待送修".equals(data.getMatStatus()))
			returnData.setMat_status(Byte.valueOf("2"));
		if("待報廢".equals(data.getMatStatus()))
			returnData.setMat_status(Byte.valueOf("3"));
		returnData.setStockQty(stockQty);
		returnData.setMatName(data.getMatName());
		returnData.setCheckResult(result);
		try{
			returnData.setApp_qty(Integer.valueOf(data.getAppQty()));
		}catch(NumberFormatException e){
			returnData.setCheckResult(data.getAppQty());			
		}
		returnData.setCheckStatus(status);
		returnData.setCtrlType(data.getCtrlType());
		return returnData;
	}
	//單筆申請回傳申請資料	
	public TbInvTrDtlDTO getReturnData(TbInvTrDtlDTO data,String result,String status,int stockQty){
		TbInvTrDtlDTO returnData=new TbInvTrDtlDTO();
		List<TbSysLookup> lookupList=invPublicUtilDao.getLookupByTypeAndCode("MAT_STATUS",String.valueOf(data.getMat_status()));
		TbSysLookup TbSysLookupData=(TbSysLookup)lookupList.get(0);
		returnData.setMatStatusName(TbSysLookupData.getNAME());
		returnData.setMat_no(data.getMat_no());
		if(new Byte("1").equals(data.getMat_status())){
			returnData.setMat_status(data.getMat_status());
			returnData.setBookingQty(data.getBookingQty());
		}else{
			returnData.setMat_status(data.getMat_status());
		}
		returnData.setMatName(data.getMatName());
		returnData.setCheckResult(result);
		try{
			returnData.setApp_qty(Integer.valueOf(data.getApp_qty()));
		}catch(NumberFormatException e){
			returnData.setCheckResult(result);			
		}
		returnData.setStockQty(stockQty);
		returnData.setCheckStatus(status);
		returnData.setTr_no(data.getTr_no());
		returnData.setCtrlType(data.getCtrlType());
		returnData.setTr_dtl_no(data.getTr_dtl_no());
		return returnData;
	}
	
	public List<TbInvInvDTO> selectForBatchExcel(HashMap<String,Object> dataObj){
		return inv007Dao.selectForBatchExcel(dataObj);
	}
	
	public TbInvTr selectByPrimaryKey(String trNo){
		return inv007Dao.selectByPrimaryKey(trNo);
	}
	
	public List<TbInvTrDtl> selectDtlByPrimaryKey(String trNo){
		TbInvTrDtlExample example = new TbInvTrDtlExample();
		example.createCriteria().andTr_noEqualTo(trNo);
		return inv007Dao.selectDtlByExample(example);
	}
	
	@Transactional
	public String updateTbInvInvAndBooking(String trNo) throws NomsException{
		String returnWord="success";
		TbInvTr tr=this.selectByPrimaryKey(trNo);
	  	List<TbInvTrDtl> dtlList=this.selectDtlByPrimaryKey(trNo);
	  	HashMap<String,Object> dataObj=new HashMap<String,Object>();
	  	for(TbInvTrDtl dtl : dtlList){
	  		TbInvBookingExample example = new TbInvBookingExample();
	  		dataObj.put("whId", tr.getTr_out_wh_id());
	  		dataObj.put("matNo", dtl.getMat_no());
	  		dataObj.put("appQty", dtl.getApp_qty());
	  		dataObj.put("trNo", trNo);
	  		example.createCriteria().andWh_idEqualTo(tr.getTr_out_wh_id()).andMat_noEqualTo(dtl.getMat_no()).andAct_noEqualTo(trNo);
	  		try{
	  		//	inv007Dao.updateBookingQtyMinusQtyByDtl(dataObj);
	  			inv007Dao.deleteBookingQty(example);
		  		inv007Dao.updateInvInvByExampleSelectiveMinusAppQty(dataObj);	
	  		}catch(NomsException e){
	  			throw new NomsException("更新預約檔及庫存檔失敗!!");
	  		}catch(Exception e){
	  			throw new NomsException("更新預約檔及庫存檔失敗!!");
	  		}
	  	}
	  	return returnWord;
	}	
	
	//設定TbInvBooking object
		public TbInvBooking returnInvBooking(String outWareHouse,String matNo,String trNo,Integer appQty,
				String userId,Date systemTime){
			TbInvBooking invBooking = new TbInvBooking();
			invBooking.setWh_id(outWareHouse);
			TbInvWarehouseDTO wareHouse;
			UTbInvWarehouseExample example = new UTbInvWarehouseExample();
			example.createCriteria().andIs_effectiveEqualTo(true).andWh_idEqualTo(outWareHouse);
			List<TbInvWarehouseDTO> list = inv016Service.selectInvWarehouseByExample(example);
			try{//是可用品需要增加寫入 domain dept_id from 調出倉
				if(list.size()>0){
					wareHouse = (TbInvWarehouseDTO) list.get(0);
				invBooking.setDept_id(wareHouse.getDept_id());
				invBooking.setDomain(wareHouse.getDomain());
				}
			}catch(Exception e){}
			invBooking.setMat_no(matNo);
			invBooking.setAct_no(trNo);
			invBooking.setAct_type(Byte.valueOf("2"));
			invBooking.setBooking_qty(appQty);
			invBooking.setCr_user(userId);
			invBooking.setCr_time(systemTime);
			invBooking.setMd_user(userId);
			invBooking.setMd_time(systemTime);
			return invBooking;
		}
}
