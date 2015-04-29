package com.foya.noms.service.common;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foya.dao.mybatis.model.TbComItemCat;
import com.foya.dao.mybatis.model.TbComItemCatExample;
import com.foya.dao.mybatis.model.TbComItemMain;
import com.foya.dao.mybatis.model.TbComItemMainExample;
import com.foya.dao.mybatis.model.TbComPoItem;
import com.foya.dao.mybatis.model.TbComPoItemExample;
import com.foya.dao.mybatis.model.TbComPoLineId;
import com.foya.dao.mybatis.model.TbComPoLineIdExample;
import com.foya.dao.mybatis.model.TbComPoLineIdMap;
import com.foya.dao.mybatis.model.TbComPoMain;
import com.foya.dao.mybatis.model.TbComPoQuota;
import com.foya.dao.mybatis.model.TbComPoQuotaExample;
import com.foya.dao.mybatis.model.TbComPoQuotaKey;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.dao.mybatis.model.TbSiteOutsourcingExample;
import com.foya.exception.CreateFailException;
import com.foya.exception.DataExistsException;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dao.basic.CM003Dao;
import com.foya.noms.dao.common.CM004Dao;
import com.foya.noms.dao.st.OutsourcingDao;
import com.foya.noms.dto.ExcelImportDTO;
import com.foya.noms.dto.basic.ItemMainDTO;
import com.foya.noms.dto.basic.PoItemDTO;
import com.foya.noms.dto.basic.PoLineIdMapDTO;
import com.foya.noms.dto.common.PoMainDTO;
import com.foya.noms.dto.common.PoQuotaDTO;
import com.foya.noms.enums.OutSourceStatus;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.st.OutsourcingService;
import com.foya.noms.util.ReadExcelService;

@Service
public class CM004Service extends ReadExcelService<TbComItemMain> {
	@Autowired
	private CM004Dao cM004Dao;
	@Autowired
	private CM003Dao cM003Dao;
	@Autowired
	private DomainService domainService;
	@Autowired
	private OutsourcingDao outsourcingDao;
	@Autowired
	private OutsourcingService outsourcingService;
	
	/**
	 * 查詢
	 * @param poNo
	 * @param year
	 * @param poName
	 * @param co_vat_No
	 * @param isTemp
	 * @return
	 */
	public List<PoMainDTO> searchPoByCond(String poNo, String year, String poName,
			String co_vat_No, String isTemp) {
		String poDomain = getLoginUser().getDomain() == null ? null : getLoginUser().getDomain().getID();
		if (poDomain != null && !StringUtils.equals("HQ", poDomain)) {
			poDomain = StringUtils.substring(poDomain, 0, 1);
		}
		return cM004Dao.searchPoByCond(poNo, year, poName, co_vat_No, isTemp, poDomain);
	}
	
	/**
	 * 用Example查詢PoQuot
	 * @param example
	 * @return
	 */
	public List<TbComPoQuota> searchPoQuotaByExample(TbComPoQuotaExample example){
		return cM004Dao.searchPoQuotaByExample(example);
	}
	
	/**
	 * 用key查詢PoMain
	 * @param example
	 * @return
	 */
	public TbComPoMain searchPoMainByPoId(String poId){
		return cM004Dao.getPoByPoId(Long.parseLong(poId));
	}
	
	/**
	 * 新增
	 * @param poMain
	 */
	@Transactional
	public void saveNewPo(TbComPoMain poMain ,String[] domainQuotaArray,
				String crUser)throws CreateFailException,DataExistsException{
		List<TbComPoMain> poMainList = cM004Dao.getPOByPoNO(poMain.getPO_NO());
		if(poMainList.size() == 0){
			Date crDate = new Date();
			if(!"Y".equals(poMain.getENABLED())){
				poMain.setENABLED("N");
			}
			poMain.setCR_USER(crUser);
			poMain.setCR_TIME(crDate);
			poMain.setMD_USER(crUser);
			poMain.setMD_TIME(crDate);
			poMain.setOS_AMOUNT(0);
			poMain.setAP_AMOUNT(0);
			poMain.setPAY_AMOUNT(0);
			int savePoMainItem =  cM004Dao.saveNewPoMain(poMain);
			if(savePoMainItem == 0){
				log.error("insert fail savePoMainItem count = "+savePoMainItem+" poId = "+poMain.getPO_ID());
				throw new CreateFailException("poMain 新增失敗");
			}
			for(String domainQuota : domainQuotaArray){
				String domain = domainQuota.split("_")[0];
				String quota = null;
				if(domainQuota.split("_").length >1){
					quota = domainQuota.split("_")[1];
				}
				List<TbOrgDomain> orgDomainList = domainService.getStandardDomainList();
				for(TbOrgDomain orgDomain : orgDomainList){
					if(orgDomain.getID().equals(domain)){
						TbComPoQuota poQuota = new TbComPoQuota();
						poQuota.setPO_ID(poMain.getPO_ID());
						poQuota.setDOMAIN(orgDomain.getID());
						poQuota.setQUOTA(Long.valueOf(quota));
						poQuota.setOS_AMOUNT(0);
						poQuota.setAP_AMOUNT(0);
						poQuota.setPAY_AMOUNT(0);
						poQuota.setCR_USER(crUser);
						poQuota.setCR_TIME(crDate);
						poQuota.setMD_USER(crUser);
						poQuota.setMD_TIME(crDate);
						int poQuotaItem = cM004Dao.saveNewPoQuota(poQuota);
						if(poQuotaItem == 0){
							log.error("insert fail poQuotaItem count = "+poQuotaItem+" poId = "+poMain.getPO_ID());
							throw new CreateFailException("poQuota 新增失敗");
						}
						break;
					}
				}
			}
		}else{
			throw new DataExistsException("PO單號重複");
		}
	}
	
	/**
	 * 根據Po_NO查詢
	 * @param poNo
	 * @return
	 */
	public List<TbComPoMain> searchByPoNO(String poNo) {
		return cM004Dao.getPOByPoNO(poNo);
	}
	
	/**
	 * 取得各區poQuota
	 * @param poID
	 * @return
	 */
	public List<PoQuotaDTO> getpoQuotaDTOList(String poID){
				
		List<TbOrgDomain> domainList = domainService.getStandardDomainList();
		List<PoQuotaDTO> poQuotaDTOList = new ArrayList<PoQuotaDTO>();
		int i=0;
		long l=i;
		
		for (TbOrgDomain domain : domainList) {
			PoQuotaDTO poQuota = new PoQuotaDTO();
			poQuota.setPoDomainId(domain.getID());
			poQuota.setPoDomainName(domain.getNAME());
			poQuota.setOS_AMOUNT(0);
			poQuota.setAP_AMOUNT(0);
			poQuota.setQUOTA(l);
			poQuota.setUnUsedQuota(0L);
			poQuotaDTOList.add(poQuota);
		}
				
		if (StringUtils.isNotEmpty(poID)) {

			TbComPoQuotaExample example = new TbComPoQuotaExample();
			example.createCriteria().andPO_IDEqualTo(Long.parseLong(poID));
			List<TbComPoQuota> poQuotaList = cM004Dao.searchPoQuotaByExample(example);
			
			if(poQuotaList.size()!=0){
				poQuotaDTOList.clear();
				HashMap<String, String> map = new HashMap<String, String>();
				
				for (TbOrgDomain domain : domainList) {
					for (TbComPoQuota poQuotaObj : poQuotaList) {
						if (domain.getID().equals(poQuotaObj.getDOMAIN())) {
							map.put("poId", poID);
							map.put("domain", domain.getID());
							
							PoQuotaDTO poQuota = new PoQuotaDTO();
							poQuota.setPoDomainId(domain.getID());
							poQuota.setPoDomainName(domain.getNAME());
							poQuota.setOS_AMOUNT(poQuotaObj.getOS_AMOUNT());
							poQuota.setAP_AMOUNT(poQuotaObj.getAP_AMOUNT());
							poQuota.setQUOTA(poQuotaObj.getQUOTA());
							poQuota.setTempBookingQuota(outsourcingDao.sumPayAmountbyOutcourcing(map));
							poQuota.setUnUsedQuota(outsourcingService.getUnusePoQuota(Long.valueOf(poID), domain.getID()));
							poQuotaDTOList.add(poQuota);
						}
					}
				}
			}	
		}
		return poQuotaDTOList;
	}
	
	/**
	 * 由pk查詢po配額設定檔
	 * @param tbComPoQuota
	 * @return
	 */
	public TbComPoQuota selectByPrimaryKey(TbComPoQuotaKey tbComPoQuota){
		return cM004Dao.selectByPrimaryKey(tbComPoQuota);
	}
	
	/**
	 * 由KEY更新PO配額設定檔
	 * @param tbComPoQuota
	 */
	public int updateByPrimaryKeySelective(TbComPoQuota tbComPoQuota){
		return cM004Dao.updateByPrimaryKeySelective(tbComPoQuota);
	}
	
	/**
	 * 修改儲存
	 * @param poMain
	 * @param domainQuotaArray
	 */
	@Transactional
	public void saveUpdatePo(TbComPoMain poMain ,String[] domainQuotaArray) throws UpdateFailException, Exception{
		String mdUser=this.getLoginUser().getUsername();
		Date date=new Date();
		poMain.setMD_USER(mdUser);
		poMain.setMD_TIME(date);
				
		int poMainRe = cM004Dao.saveUpdatePoMain(poMain);
		if (poMainRe == 0) {
			log.error("PoMain update count= " + poMainRe + " , POId = " + poMain.getPO_ID());
			throw new UpdateFailException("更新失敗");
		}
						
		for(int i=0;i<domainQuotaArray.length;i++){		
			TbComPoQuotaExample example = new TbComPoQuotaExample();
			TbComPoQuota record = new TbComPoQuota();			
			record.setDOMAIN(domainQuotaArray[i]);
			
			if(StringUtils.isNotEmpty(domainQuotaArray[i+1])){
				record.setQUOTA(Long.valueOf(domainQuotaArray[i+1]));
			}else{
				record.setQUOTA(0L);
			}			
			record.setMD_USER(mdUser);
			record.setMD_TIME(date);
			example.createCriteria().andPO_IDEqualTo(poMain.getPO_ID()).andDOMAINEqualTo(domainQuotaArray[i]);
			int poQuotaRe = cM004Dao.saveUpdatePoQuota(record, example);
			if (poQuotaRe == 0) {
				log.error("PoQuota update count= " + poQuotaRe + " , POId = " + record.getPO_ID() + " , Domain = " + record.getDOMAIN());
				throw new UpdateFailException("更新失敗");
			}
			i=i+1;
		}		
	}
	
	/**
	 * 合併Po單
	 * @param tempPoId
	 * @param targetPoId
	 */
	@Transactional
	public void mergePo(String tempPoId, String targetPoId) throws DataExistsException {
		String mdUser=this.getLoginUser().getUsername();
		Date date=new Date();
		//1.PO主檔
		//臨時Po，單號更改為正式PO單號,並更改isMerge=Y為已合併,啟用=N
		TbComPoMain tempPo = this.searchPoMainByPoId(tempPoId);
		//正式Po，派工金額+=臨時派工金額 (PAY_AMOUNT += 臨時PAY_AMOUNT，委外申請時檢控金額欄位也要合併)
		TbComPoMain targetPo = this.searchPoMainByPoId(targetPoId);
		
		//更新臨時Po主檔
		tempPo.setPO_NO(targetPo.getPO_NO());
		tempPo.setIS_MERGE("Y");
		tempPo.setENABLED("N");
		tempPo.setMD_USER(mdUser);
		tempPo.setMD_TIME(date);
		cM004Dao.saveUpdatePoMain(tempPo);
		
		//更新正式Po主檔
		int tempPo_OS=0, tempPo_PAY=0;
		int targetPo_OS=0, targetPo_PAY=0;
		if(tempPo.getOS_AMOUNT()!=null){
			tempPo_OS=tempPo.getOS_AMOUNT();
		}
		if(targetPo.getOS_AMOUNT()!=null){
			targetPo_OS=targetPo.getOS_AMOUNT();
		}
		if(tempPo.getPAY_AMOUNT()!=null){
			tempPo_PAY = tempPo.getPAY_AMOUNT();
		}
		if(targetPo.getPAY_AMOUNT()!=null){
			targetPo_PAY = targetPo.getPAY_AMOUNT();
		}
		int targetAmount = targetPo_OS+tempPo_OS;
		int targetPay = targetPo_PAY+tempPo_PAY;
		targetPo.setOS_AMOUNT(targetAmount);
		targetPo.setPAY_AMOUNT(targetPay);
		targetPo.setMD_USER(mdUser);
		targetPo.setMD_TIME(date);
		cM004Dao.saveUpdatePoMain(targetPo);
		
		//2.Po配額檔處理，if正式無配額，則insert正式Po配額，值為臨時配額; else 正式有配額，則正式與臨時彼此比對，必須兩方相等(不做動作)，否則拋異常
		TbComPoQuotaExample tempPoQuotaEx = new TbComPoQuotaExample();
		tempPoQuotaEx.createCriteria().andPO_IDEqualTo(Long.parseLong(tempPoId));
		List<TbComPoQuota> tempPoQuota = cM004Dao.searchPoQuotaByExample(tempPoQuotaEx);
				
		TbComPoQuotaExample targetPoQuotaEx = new TbComPoQuotaExample();
		targetPoQuotaEx.createCriteria().andPO_IDEqualTo(Long.parseLong(targetPoId));
		List<TbComPoQuota> targetPoQuota = cM004Dao.searchPoQuotaByExample(targetPoQuotaEx);	
		
		// if正式無配額，則insert正式Po配額，值為臨時配額
		if (targetPoQuota.size() == 0) {
			TbComPoQuota targetPoQuotaIn = new TbComPoQuota();
			for (int i = 0; i < tempPoQuota.size(); i++) {
				targetPoQuotaIn.setPO_ID(Long.parseLong(targetPoId)); // 正式POID
				targetPoQuotaIn.setDOMAIN(tempPoQuota.get(i).getDOMAIN()); // 區域
				targetPoQuotaIn.setQUOTA(tempPoQuota.get(i).getQUOTA()); // 配額
				targetPoQuotaIn.setOS_AMOUNT(tempPoQuota.get(i).getOS_AMOUNT());//已派工數量 add by Charlie
				targetPoQuotaIn.setPAY_AMOUNT(tempPoQuota.get(i).getPAY_AMOUNT());	// add by Charlie
				targetPoQuotaIn.setCR_USER(mdUser); // 建立者
				targetPoQuotaIn.setCR_TIME(date); // 建立時間
				targetPoQuotaIn.setMD_USER(mdUser);
				targetPoQuotaIn.setMD_TIME(date);
				cM004Dao.saveNewPoQuota(targetPoQuotaIn);
			}
		} else {	
			if(tempPoQuota.size()!=targetPoQuota.size()){
				throw new DataExistsException("正式PO單與臨時PO單之各區配額不一致，無法合併!!");
			}else{
				for (int i = 0; i < tempPoQuota.size(); i++) {	
					boolean isSame = false;
					for (int j = 0; j < targetPoQuota.size(); j++) {									
						if (tempPoQuota.get(i).getDOMAIN().equals(targetPoQuota.get(j).getDOMAIN())&&tempPoQuota.get(i).getQUOTA().equals(targetPoQuota.get(j).getQUOTA())){
							isSame = true;
							break;
						}								
					}
					if(!isSame){
						throw new DataExistsException("正式PO單與臨時PO單之各區配額不一致，無法合併!!");
					}				
				}
			}			
		}

	  //3.Po工項處理，if正式無工項，則insert正式Po工項，值為臨時工項; else 正式有工項，則正式與臨時彼此比對，必須兩方相等(不做動作)，否則拋異常
		TbComPoItemExample tempPoItemEx = new TbComPoItemExample();
		tempPoItemEx.createCriteria().andPO_IDEqualTo(Long.parseLong(tempPoId));
		List<TbComPoItem> tempPoItem = cM004Dao.searchPoItemByExample(tempPoItemEx);
				
		TbComPoItemExample targetPoItemaEx = new TbComPoItemExample();
		targetPoItemaEx.createCriteria().andPO_IDEqualTo(Long.parseLong(targetPoId));
		List<TbComPoItem> targetPoItem = cM004Dao.searchPoItemByExample(targetPoItemaEx);	
		
		// if正式無配額，則insert正式Po配額，值為臨時配額
		if (targetPoItem.size() == 0) {
			TbComPoItem targetPoItemIn = new TbComPoItem();
			for (int i = 0; i < tempPoItem.size(); i++) {
				targetPoItemIn.setPO_ID(Long.parseLong(targetPoId)); // 正式POID
				targetPoItemIn.setITEM_ID(tempPoItem.get(i).getITEM_ID()); // 工項ID
				targetPoItemIn.setPRICE(tempPoItem.get(i).getPRICE()); // 單價
				targetPoItemIn.setCR_USER(mdUser); // 建立者
				targetPoItemIn.setCR_TIME(date); // 建立時間
				cM004Dao.saveNewPoItem(targetPoItemIn);
			}
		} else {		
			if(tempPoItem.size()!=targetPoItem.size()){
				throw new DataExistsException("正式PO單與臨時PO單之工項不一致，無法合併!!");
			}else{
				for (int i = 0; i < tempPoItem.size(); i++) {	
					boolean isSame = false;
					for (int j = 0; j < targetPoItem.size(); j++) {									
						if (tempPoItem.get(i).getITEM_ID().equals(targetPoItem.get(j).getITEM_ID())&&tempPoItem.get(i).getPRICE().equals(targetPoItem.get(j).getPRICE())){
							isSame = true;
							break;
						}								
					}
					if(!isSame){
						throw new DataExistsException("正式PO單與臨時PO單之工項不一致，無法合併!!");
					}				
				}
			}			
		}		
	}
	
	/**
	 * 以poId查詢有使用到的小項以及被 TB_SITE_OS_ITEM參考到次數
	 * @param poId
	 * @return
	 */
	public List<PoItemDTO> getPoitemByPoIdAndUsedBySite(String poId){
		
		return cM004Dao.getPoitemByPoIdAndUsedBySite(poId);
	}
	
	public void savePoItem(List<TbComPoItem> records, String poId, String catNo, String faCategory) {
		
		//先刪除所有的資料，再重新insert
		//查詢需要刪除的PO Items 1.有限定中項  2.無限定中項
		List<TbComPoItem> delPoItemList = new ArrayList<TbComPoItem>();
		String catId = "";
		if (!StringUtils.isBlank(catNo)) {
			TbComItemCatExample example = new TbComItemCatExample();
			example.createCriteria().andCAT_NOEqualTo(catNo);
			TbComItemCat cat = cM003Dao.selectByExample(example).get(0);
			cat.setFA_CATEGORY(faCategory);
			cM003Dao.updateTbComItemCat(cat);
			catId = String.valueOf(cat.getCAT_ID());
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("poId", poId);
		map.put("catId", String.valueOf(catId));
		delPoItemList = cM004Dao.selectItemForDelete(map);
		
		for (TbComPoItem poItem : delPoItemList) {
			cM004Dao.deleteByPrimaryKey(poItem.getPO_ID(), poItem.getITEM_ID());
		}
		
		for (TbComPoItem record : records) {
			cM004Dao.saveNewPoItem(record);
		}
	}
	
	/**
	 * 用map查詢TbComItemMain.
	 * @param map
	 * @return
	 */
	public List<ItemMainDTO> selectItemMainItem(Map<String, String> map){
		return outsourcingDao.selectItemMainItem(map);
	}

	/**
	 * 將EXCEL中資料匯入DB
	 * @param poId
	 * @param mutipartRequest
	 * @throws IOException
	 * @throws Exception
	 */
	public void importPoItem(String poId, MultipartHttpServletRequest mutipartRequest) throws IOException, Exception {
		
		//如果poId已存在TB_SITE_OUTSOURCING則不允許做 工項匯入
		TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
		example.createCriteria().andPO_IDEqualTo(Long.parseLong(poId)).andSTATUSNotEqualTo(OutSourceStatus.OS08.name());
		List<TbSiteOutsourcing> poList = outsourcingDao.findSiteOutSoureByExample(example);
		if (poList != null && poList.size() > 0) {
			throw new DataExistsException("error此PO已被委派不允許做整張工項匯入，請使用工項維護調整");
		}
		
		//將Excel資料轉換為TbComItemMain
		List<TbComItemMain> list = new ArrayList<TbComItemMain>();
		for (Iterator<String> itr = mutipartRequest.getFileNames(); itr.hasNext();) {	
			MultipartFile file = mutipartRequest.getFile(itr.next());
			list = readReport(file.getInputStream());
		}
		
		if (list == null || list.size() == 0) {
			throw new NomsException("error檔案中無符合格式資料可匯入!");
		}
		
		List<TbComPoItem> records = new ArrayList<TbComPoItem>();
		//將EXCEL中的資料存入DB
		for (TbComItemMain item : list) {
			TbComItemMainExample example1 = new TbComItemMainExample();
			example1.createCriteria().andITEM_NOEqualTo(item.getITEM_NO());
			TbComItemMain data = cM003Dao.selectItemMainByExample(example1).get(0);
			
			//查詢中項取得FA_CATEGORY存入
			TbComItemCatExample catExample = new TbComItemCatExample();
			catExample.createCriteria().andPARENT_CATEqualTo(data.getMAIN_ITEM()).andCAT_IDEqualTo(data.getSUB_ITEM());
			TbComItemCat cat = cM003Dao.selectByExample(catExample).get(0);
			TbComPoItem poItem = new TbComPoItem();
			poItem.setFA_CATEGORY(cat.getFA_CATEGORY());
			poItem.setITEM_ID(data.getITEM_ID());
			poItem.setPO_ID(Long.parseLong(poId));
			poItem.setPRICE(item.getPRICE());
			poItem.setCR_TIME(new Date());
			poItem.setCR_USER(getLoginUser().getUsername());
			poItem.setMD_TIME(new Date());
			poItem.setMD_USER(getLoginUser().getUsername());
			
			records.add(poItem);
		}
		
		savePoItem(records, poId, "", "");
	}
	
	/**
	 * 將Excel資料轉為List
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public List<TbComItemMain> readReport(InputStream is) throws Exception {
		List<TbComItemMain> results = new ArrayList<TbComItemMain>();

		List<ExcelImportDTO<TbComItemMain>> models = readExcel(is, 0, 1);
		for (ExcelImportDTO<TbComItemMain> model : models) {
			if (model.getRecord() == null) {
				continue;
			}
			if (!StringUtils.isBlank(model.getErrorMsgs())) {
				throw new NomsException(model.getErrorMsgs());
			}
			results.add(model.getRecord());
		}

		return results;
	}
	
	@Override
	protected TbComItemMain setEntity(String[] oneRowCellsArray) throws Exception {
		if (oneRowCellsArray.length == 0) {
			return null;
		}

		TbComItemMain item = new TbComItemMain();
		try {
			item.setITEM_NO(oneRowCellsArray[0]);
			
			if (StringUtils.isBlank(oneRowCellsArray[0])) {
				throw new NullPointerException("error項目編號不得為空");
			} else {
				item.setITEM_NO(oneRowCellsArray[0]);
				//項目編號最後為0就認定為主項or中項
				Pattern pattern = Pattern.compile("^[1-9][0-9]*.[0-9][0-9]*.0$");
				Matcher matcher = pattern.matcher(item.getITEM_NO());
				if (matcher.find()) {
					log.info("項目編號: " + item.getITEM_NO() + " 判定為主項或中項");
					//如果是主項跟中項則回傳null
					return null;
				}
			}
			item.setITEM_NAME(oneRowCellsArray[1]);
			//單位工時若為空設定為-1，在validateModel做判斷
			item.setUNIT_HOUR(new BigDecimal(StringUtils.isBlank(oneRowCellsArray[2]) ? "-1" : oneRowCellsArray[2]));
			item.setUNIT(oneRowCellsArray[3]);
			//投標單價若為空設定為-1，在validateModel做判斷
			item.setPRICE(new BigDecimal(StringUtils.isBlank(oneRowCellsArray[4]) ? "-1" : oneRowCellsArray[4]));
		} catch (NumberFormatException nfex) {
			throw new NumberFormatException("error匯入的單位工時/投標單價格式有誤");
		}
		
		return item;
	}
	
	@Override
	protected String validateModel(Integer rowLine, TbComItemMain record)
			throws Exception {
		
		String msg = "";
		if (record == null) {
			//空白row
			return msg;
		}
		
		if (StringUtils.isBlank(record.getITEM_NAME())) {
			return "匯入的項目名稱不得為空!";
		}
		
		if (record.getUNIT_HOUR().compareTo(new BigDecimal(0)) < 0) {
			return "匯入的單位工時不得為空!";
		}
		
		if (StringUtils.isBlank(record.getUNIT())) {
			return "匯入的單位不得為空!";
		}
		
		if (record.getPRICE().compareTo(new BigDecimal(0)) < 0) {
			return "匯入的投標單價不得為空!";
		}
		
		//itemNo and itName 不存在或不一致 或 ENABLED = N => 檢核失敗
		log.info("檢核上傳項目: ITEM_NO: " + record.getITEM_NO() + ", ITEM_NAME(): " + record.getITEM_NAME());
		TbComItemMainExample example = new TbComItemMainExample();
		example.createCriteria().andITEM_NOEqualTo(record.getITEM_NO()).andENABLEDEqualTo("Y");
		List<TbComItemMain> itemList = cM003Dao.selectItemMainByExample(example);
		
		if (itemList == null || itemList.size() == 0) {
			return "項目:" + record.getITEM_NO() + "\r\n名稱:" + record.getITEM_NAME() + "\r\n不存在(未啟用)或是不一致!";
		} else {
			TbComItemMain data = itemList.get(0);
			//ItemName 將換行符號替換成空白後比對
			if (!StringUtils.equals(data.getITEM_NAME().replaceAll("(\n|\r\n)", ""), record.getITEM_NAME().replaceAll("(\n|\r\n)", ""))){
				return "項目:" + record.getITEM_NO() + "\r\n名稱:" + record.getITEM_NAME() + "\r\n不存在(未啟用)或是不一致!";
			}
		}
		
		return msg;
	}
	
	
	public List<String> selectFaCategoryByCat(HashMap<String,String> map) {
		
		return cM004Dao.selectFaCategoryByCat(map);
	}
	
	/**
	 * 依poNo取出PoLineId下拉選項
	 * @param poNo
	 * @return
	 */
	public List<TbComPoLineId> selectLineIdByPoNo(String poNo) {
		TbComPoLineIdExample example = new TbComPoLineIdExample();
		example.createCriteria().andPO_NOEqualTo(poNo);
				
		return cM004Dao.selectPoLineIdByExample(example);
	}
	
	/**
	 * 依poNo取出PO_LINE Mapping
	 * @param poNo
	 * @return
	 */
	public List<PoLineIdMapDTO> selectPoLineIdMapByPoNo(String poNo) {
		return cM004Dao.selectPoLineIdMapByPoNo(poNo);
	}
	
	public void saveUpdatePoLineMapping(String jLineIdMap, String poNo) throws Exception {
		try {
			//先依PO_NO刪除TB_COM_PO_LINE_ID_MAP
			log.info("delete PoLineMap ==> poNo :" + poNo);
			cM004Dao.deletePoLineMapByPoNo(poNo);
			
			//重新insert
			JSONArray jsonArray = new JSONArray(jLineIdMap);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				TbComPoLineIdMap poLineIdMap = new TbComPoLineIdMap();
				poLineIdMap.setPO_NO(poNo);
				poLineIdMap.setCAT_ID(obj.getLong("catId"));
				poLineIdMap.setPO_LINE_ID(new BigDecimal(obj.getString("poLineId")));
				log.info("insert PoLineMap ==> poNo: " + poLineIdMap.getPO_NO() + ", catId: " + poLineIdMap.getCAT_ID() + ", poLineId: " + poLineIdMap.getPO_LINE_ID());
				cM004Dao.saveUpdatePoLineMapping(poLineIdMap);
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw ex;
		}

	}
	
	/**
	 * 取PoMain最小年度
	 * @return
	 */
	public Map<String, String> getMinYear(){
		Map<String, String> map = new LinkedHashMap<>();
		List<PoMainDTO> rows = cM004Dao.selectMinYear();
		int minYear = Integer.parseInt(rows.get(0).getPO_YEAR());
		Calendar calendar = Calendar.getInstance();
		int currYear = calendar.get(Calendar.YEAR)+1;
		for(int i=minYear;i<=currYear;i++){
			map.put(String.valueOf(i), String.valueOf(i));
		}
		return map;
	}
}
