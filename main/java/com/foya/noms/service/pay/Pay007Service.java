package com.foya.noms.service.pay;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.mapper.TbSysEmailTemplateMapper;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayCheckDisregard;
import com.foya.dao.mybatis.model.TbSysEmailTemplate;
import com.foya.exception.NomsException;
import com.foya.noms.dao.pay.Pay007Dao;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;
import com.foya.noms.service.BaseService;

@Service
public class Pay007Service extends BaseService {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Inject 
	private Pay007Dao pay007Dao;
	
	@Autowired
	private PayEmailService payEmailService;
	
	@Autowired
	private TbSysEmailTemplateMapper tbSysEmailTemplateMapper;
	
	@Autowired
	private PayInsertERPService payInsertERPService;
	
	
	// 取得請款單號清單資料(By處理類別區分)
	public List<TbPayCashRequirementDTO> selectTbPayCashRequirement(String domain,String processType, String appUser,
			Date appStartDate, Date appEndDate, String cashReqNo, String yearMonth)throws ParseException {
		String processTypeLikeVal = "";
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("domain", domain);
		dataObj.put("appUser", appUser);
		dataObj.put("appStartDate", appStartDate);
		dataObj.put("appEndDate", appEndDate);
		dataObj.put("cashReqNo",cashReqNo);
		dataObj.put("yearMonth",yearMonth);
		List<TbPayCashRequirementDTO> list = null;
		// 針對處理類別區分資料來源:
		if (!processType.equals("CKD")) {
			if (processType.equals("REN")) {
				processTypeLikeVal = "R%";
			} else if (processType.equals("ELE")) {
				processTypeLikeVal = "E%";
			} else if (processType.equals("LIN")) {
				processTypeLikeVal = "L%";
			} else if (processType.equals("MIS")) {
				processTypeLikeVal = "M%";
			}
			dataObj.put("processType", processTypeLikeVal);
			// 處理類別非支票作廢
			list = pay007Dao.selectTbPayCashRequirementPay007ByType(dataObj);
		} else {
			// 處理類別是支票作廢
			list = pay007Dao.selectTbPayCashRequirementPay007(dataObj);
		}
		return list;
	}

	@Transactional
	public void updatePayCashRequirement(List<TbPayCashRequirement> updateArray,Date today)throws NomsException{	
		
		//查詢email用的表頭和內容
		TbSysEmailTemplate tbSysEmailTemplate = tbSysEmailTemplateMapper.selectByPrimaryKey("ACCOUNT_APPROVAL");
		
		for (int i = 0; i < updateArray.size(); i++) {
			TbPayCashRequirement exportObject = updateArray.get(i);
			try{
				String status = exportObject.getSTATUS();
				//駁回 發mail
				if(status.equals("E")){
					payEmailService.sendMail(exportObject,tbSysEmailTemplate);
				}

				//因mail需求~前端把駁回原因的id和text組起來 這裡須把它還原
				if(exportObject.getREJECT_REASON()!=null){
					exportObject.setREJECT_REASON(exportObject.getREJECT_REASON().substring(0,exportObject.getREJECT_REASON().indexOf("_")));
				}
				
				log.debug("2exportObject.getREJECT_REASON~~~" + exportObject.getREJECT_REASON());
				if (status.equals("E") || status.equals("C")) {
					exportObject.setACCOUNT_APPROVAL_DATE(today);
					exportObject.setACCOUNT_APPROVAL_USER(String.valueOf(getLoginUser().getUserId()));
				}
				if (status.equals("F")) {
					exportObject.setTO_ERP_DATE(today);
					//將勾選請款相關資料塞到ERP中介檔
					//payInsertERPService.insertERP(exportObject);
					
				}
				exportObject.setMD_TIME(today);
				exportObject.setMD_USER(String.valueOf(getLoginUser().getUserId()));
				log.debug("when update  status~~~" + status);
			
				pay007Dao.updatePayCashRequirement(exportObject);

				//駁回和請款匯出時  需判斷是否為支票作廢 去 update TB_PAY_CHECK_DISREGARD
				if(("E").equals(status) || ("F").equals(status)){
					
					if(("CKD").equals(exportObject.getPROCESS_TYPE())){
						TbPayCheckDisregard tbPayCheckDisregard = new TbPayCheckDisregard();
						tbPayCheckDisregard.setSTATUS(status);
						tbPayCheckDisregard.setMD_USER(String.valueOf(getLoginUser().getUserId()));
						tbPayCheckDisregard.setMD_TIME(today);
						pay007Dao.updatePayCheckDisregard(exportObject.getCASH_REQ_NO(),tbPayCheckDisregard);
						
					}
				}
			}catch (NomsException throwable) {
				log.error(throwable.getMessage());
				throwable.printStackTrace();
			}
		}
	}
	
	
	
}
