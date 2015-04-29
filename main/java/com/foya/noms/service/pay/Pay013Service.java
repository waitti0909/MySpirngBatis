package com.foya.noms.service.pay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.foya.dao.mybatis.model.TbPayRepartition;
import com.foya.dao.mybatis.model.TbPayRepartitionExample;
import com.foya.dao.mybatis.model.TbPayRepartitionExample.Criteria;
import com.foya.exception.NomsException;
import com.foya.noms.dao.pay.Pay013Dao;
import com.foya.noms.dto.pay.TbLsSiteDTO;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;
import com.foya.noms.service.BaseService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.foya.noms.enums.SiteType;

@Service
public class Pay013Service extends BaseService {
	@Inject 
	private Pay013Dao pay013Dao;

	// 查詢主SQL資料
	public List<TbPayCashRequirementDTO> selectTbPayCashRequirementPay013(String yearMonth){
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("yearMonth", yearMonth);
		return pay013Dao.selectTbPayCashRequirementPay013(dataObj);
	}
	
	// 查詢子SQL資料
	public List<TbLsSiteDTO> selectTbLsSitePay013(String contractNo, String locationId){
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("contractNo", contractNo);
		dataObj.put("locationId", locationId);
		return pay013Dao.selectTbLsSitePay013(dataObj);
	}
	
	// 主查詢
	public List<TbPayCashRequirementDTO> selectCashAllData(String yearMonth) {
		// 取得主SQL資料
		List<TbPayCashRequirementDTO> cashList = this.selectTbPayCashRequirementPay013(yearMonth);
		PageList<TbPayCashRequirementDTO> DTOlist = new PageList<TbPayCashRequirementDTO>();
		TbPayCashRequirementDTO dto = null;
		BigDecimal taxExclusiveAmt = new BigDecimal(0);
		BigDecimal alocRatio = new BigDecimal(0);
		try {
			for (int i = 0; i < cashList.size(); i++) {
				TbPayCashRequirementDTO cash = (TbPayCashRequirementDTO) cashList.get(i);
				String contractNoTemp = cash.getCONTRACT_NO();
				String locationIdTemp = cash.getLOCATION_ID();
				// 取得子SQL資料
				List<TbLsSiteDTO> lsList = this.selectTbLsSitePay013(contractNoTemp, locationIdTemp);
				for (int s = 0; s < lsList.size(); s++) {// 回傳多筆
					TbLsSiteDTO ls = (TbLsSiteDTO) lsList.get(s);
					dto = new TbPayCashRequirementDTO();
					taxExclusiveAmt = cash.getTAX_EXCLUSIVE_AMT();
					alocRatio = ls.getALOC_RATIO();
					dto.setSiteId(ls.getSITE_ID());// 站台編號
					dto.setLocTypeDscr(SiteType.detectLabel(ls.getLOC_TYPE()));// 類別
					dto.setAlocItemDscr(ls.getALOC_ITEM_DSCR());// 分攤基準(租金/電費)
					dto.setTaxExclusiveAmt(taxExclusiveAmt);// 請款金額
					dto.setExchSiteId(ls.getEXCH_SITE_ID());// 分攤站台編號
					dto.setAlocRatio(ls.getALOC_RATIO());// 分攤比例
					dto.setExchAmt(BigDecimal.valueOf((taxExclusiveAmt.longValue()*alocRatio.doubleValue()*0.01)));// (攤分)金額=請款金額*分攤比例
					dto.setPaymentReqNo(cash.getPAYMENT_REQ_NO());// 請款編號
					dto.setItemNo(cash.getITEM_NO());// 請款細項編號
					dto.setSeqNbr(cash.getSEQ_NBR());// 付款序號
					dto.setLocationId(cash.getLOCATION_ID());// 站點代碼
					dto.setAlocItem(ls.getALOC_ITEM());// 分攤費用類別		
					DTOlist.add(dto);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			log.error("[/pay/PAY013/selectPaymentAllData] ERROR :"
					+ e.getMessage());
			e.printStackTrace();
			return DTOlist;
		} catch (NullPointerException e) {
			log.error("[/pay/PAY013/selectPaymentAllData] ERROR :"
					+ e.getMessage());
			e.printStackTrace();
			return DTOlist;
		}
		return DTOlist;
	}

	/**
	 * 查詢分攤資訊筆數
	 * @param example
	 * @return
	 */
	public int countPayByExample(String yearMonth) {
		TbPayRepartitionExample example = new TbPayRepartitionExample();
		Criteria criteria = example.createCriteria();
		if("" != yearMonth)
			criteria.andYEAR_MONTHEqualTo(yearMonth);
		    criteria.andREPARTITION_NOLike("AUT%");
		return pay013Dao.countPayByExample(example);
	}
	
	@Transactional
	public String deleteByExample(String yearMonth) throws NomsException{
		TbPayRepartitionExample example = new TbPayRepartitionExample();
		Criteria criteria = example.createCriteria();
		if("" != yearMonth)
			criteria.andYEAR_MONTHEqualTo(yearMonth);
		    criteria.andREPARTITION_NOLike("AUT%");
		return pay013Dao.deleteByExample(example);
	}
	
	public String selectRptNoToday(String appDate){
		String todayMaxRptNo="",returnSeqNo="",genInitSeq="0001",genInitFirstWord="AUT",plusOneSeqString="";
		TbPayRepartition tbPay = pay013Dao.selectMaxSeq(appDate);// 依當日申請日期取得當日最大單號
		try {
			todayMaxRptNo=tbPay.getREPARTITION_NO();// 取得當日最大分攤單號
			if ("".equals(todayMaxRptNo))
				returnSeqNo = genInitFirstWord + appDate + genInitSeq;
			else
				todayMaxRptNo = todayMaxRptNo.substring(12,15 );
			
			int plusOneSeq = Integer.valueOf(todayMaxRptNo);
			plusOneSeqString = String.format("%04d",plusOneSeq + 1);// 若已有單號則依此單號往後+1編碼	
			returnSeqNo = genInitFirstWord + appDate + plusOneSeqString;
		} catch (NullPointerException e) {
			returnSeqNo = genInitFirstWord +appDate + genInitSeq;// 代表取無單號直接從001開始編碼
		}
		return returnSeqNo;
	}

	@Transactional
	public String insertPayRepartition(String repartitionNo,
			String paymentReqNo, String paymentReqItemNo, String paymentSeqNbr,
			String locationId, String yearMonth, String repartitionAmt,
			String siteId, String expType, String appUser, Date appDate) {
		TbPayRepartition record = new TbPayRepartition();
		record.setREPARTITION_NO(repartitionNo);
		record.setPAYMENT_REQ_NO(Long.valueOf(paymentReqNo));
		record.setPAYMENT_REQ_ITEM_NO(Short.valueOf(paymentReqItemNo));
		record.setPAYMENT_SEQ_NBR(Long.valueOf(paymentSeqNbr));
		record.setLOCATION_ID(locationId);
		record.setYEAR_MONTH(yearMonth);
		record.setREPARTITION_AMT(new BigDecimal(repartitionAmt));
		record.setSITE_ID(siteId);
		record.setEXP_TYPE(expType);
		record.setCR_USER(appUser);
		record.setCR_TIME(appDate);
		record.setMD_USER(appUser);
		record.setMD_TIME(appDate);
		return pay013Dao.insertPayRepartition(record);
	}
}
