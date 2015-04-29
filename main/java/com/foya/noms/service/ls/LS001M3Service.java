package com.foya.noms.service.ls;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbLsApp;
import com.foya.dao.mybatis.model.TbLsAppExample;
import com.foya.dao.mybatis.model.TbLsAppForm;
import com.foya.dao.mybatis.model.TbLsLessor;
import com.foya.dao.mybatis.model.TbLsLessorAdded;
import com.foya.dao.mybatis.model.TbLsLessorAddedExample;
import com.foya.dao.mybatis.model.TbLsLessorExample;
import com.foya.dao.mybatis.model.TbLsLocElec;
import com.foya.dao.mybatis.model.TbLsLocElecAdded;
import com.foya.dao.mybatis.model.TbLsLocElecAddedExample;
import com.foya.dao.mybatis.model.TbLsLocElecExample;
import com.foya.dao.mybatis.model.TbLsLocPayment;
import com.foya.dao.mybatis.model.TbLsLocPaymentAdded;
import com.foya.dao.mybatis.model.TbLsLocPaymentAddedExample;
import com.foya.dao.mybatis.model.TbLsLocPaymentExample;
import com.foya.dao.mybatis.model.TbLsLocation;
import com.foya.dao.mybatis.model.TbLsLocationAdded;
import com.foya.dao.mybatis.model.TbLsLocationAddedExample;
import com.foya.dao.mybatis.model.TbLsLocationExample;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbLsMainAdded;
import com.foya.dao.mybatis.model.TbLsMainAddedExample;
import com.foya.dao.mybatis.model.TbLsMainExample;
import com.foya.dao.mybatis.model.TbLsMainKey;
import com.foya.dao.mybatis.model.TbLsResExch;
import com.foya.dao.mybatis.model.TbLsResExchAdded;
import com.foya.dao.mybatis.model.TbLsResExchAddedExample;
import com.foya.dao.mybatis.model.TbLsResExchAloc;
import com.foya.dao.mybatis.model.TbLsResExchAlocAdded;
import com.foya.dao.mybatis.model.TbLsResExchAlocAddedExample;
import com.foya.dao.mybatis.model.TbLsResExchAlocExample;
import com.foya.dao.mybatis.model.TbLsReward;
import com.foya.dao.mybatis.model.TbLsRewardAdded;
import com.foya.dao.mybatis.model.TbLsRewardAddedExample;
import com.foya.dao.mybatis.model.TbLsSite;
import com.foya.dao.mybatis.model.TbLsSiteAdded;
import com.foya.dao.mybatis.model.TbLsSiteAddedExample;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.dao.mybatis.model.TbWorkflowCfg;
import com.foya.dao.mybatis.model.TbWorkflowCfgExample;
import com.foya.exception.NomsException;
import com.foya.noms.dao.ls.LS001Dao;
import com.foya.noms.dao.ls.LS001M3Dao;
import com.foya.noms.dao.st.SiteMainDao;
import com.foya.noms.dao.workflow.WorkflowCfgDao;
import com.foya.noms.dto.ls.LeaseLocationDTO;
import com.foya.noms.dto.ls.LeaseMainDTO;
import com.foya.noms.dto.ls.LessorChDTO;
import com.foya.noms.dto.ls.TbLsLocElecLocPaymentDTO;
import com.foya.noms.dto.ls.TbLsLocPaymentDTO;
import com.foya.noms.dto.ls.TbLsLocationAddedDTO;
import com.foya.noms.dto.ls.TbLsRewardAddedDTO;
import com.foya.noms.dto.ls.TbLsRewardDTO;
import com.foya.noms.dto.pay.TbLsLocElecDTO;
import com.foya.noms.dto.pay.TbLsSiteDTO;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.enums.LsEnumCommon;
import com.foya.noms.enums.LsEnumCommon.AddTypeEnum;
import com.foya.noms.enums.LsEnumCommon.AppStatusEnum;
import com.foya.noms.enums.LsEnumCommon.LsStatusEnum;
import com.foya.noms.enums.LsPaymentItem;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.noms.util.DateUtils;
import com.foya.workflow.exception.WorkflowException;

@Service
public class LS001M3Service extends BaseService {

	@Autowired
	private LS001M3Dao lS001M3Dao;

	@Autowired
	private LS001Dao lS001Dao;

	@Autowired
	private UniqueSeqService uniqueSeqService;

	@Autowired
	private LS001Service ls001Service;
	
	@Autowired
	private WorkflowActionService workflowActionService;

	@Autowired
	private WorkflowCfgDao workflowCfgDao;
	
	@Autowired
	private EmailTemplateService emailTemplateService;
	
	@Autowired
	private LsCommonService lsCommonService;
	
	@Autowired
	private SiteMainDao siteMainDao;
	
	/**
	 * 取得搜尋條件的所有站點
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param domain
	 *            區域
	 * @return
	 */
	public List<LeaseLocationDTO> getTbLsLocation(String lsNo, String domain) {
		List<LeaseLocationDTO> leaseLocationDTOList = lS001M3Dao.searchTbLsLocation(lsNo, domain);
		return leaseLocationDTOList;
	}

	/**
	 * 使用合約編號找尋最大版次的出租人資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @return
	 */
	public List<TbLsLessor> getLsLessorByNoVer(String lsNo) {
		List<TbLsLessor> list = lS001M3Dao.getLsLessorByNoVer(lsNo);
		return list;
	}

	
	/**
	 * 查詢合約
	 * 
	 * @param lsNo
	 * @param rewardType
	 * @return
	 */
	public List<TbLsRewardDTO> getLsRewardByLsNoMaxVer(String lsNo, String rewardType)
	{
		HashMap<String, String> dataObj = new HashMap<String, String>();
		dataObj.put("lsNo", lsNo);
		dataObj.put("rewardType", rewardType);
		List<TbLsRewardDTO> list = lS001M3Dao.getLsRewardByLsNoMaxVer(dataObj);
		Map<String, String> rewardResn = ls001Service.getRewardResn();
		for (TbLsRewardDTO tbLsRewardDTO : list)
		{
			tbLsRewardDTO.setResnDesc(rewardResn.get(tbLsRewardDTO.getREWARD_RESN()));
		}
		return list;
	}

	/**
	 * 撈取 TbLsLocElec 與 TbLsLocPaymen 資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param locId
	 *            站點編號
	 * @param appMode
	 *            申請狀態
	 * @param appSql
	 *            申請流水號
	 * @param item
	 *            項目
	 * @return
	 */
	public TbLsLocElecLocPaymentDTO searchTbLsLocElecTbLsLocPaymen(String lsNo, String locId, String appMode,String appSql,String item) {
		String[] paymentItems ={"E"};
		TbLsLocElecLocPaymentDTO tbLsLocElecLocPaymentDTO = new TbLsLocElecLocPaymentDTO();
		if (StringUtils.equals(item, "電表歸零")) {
			tbLsLocElecLocPaymentDTO.setTbLsLocElecDTOList(lS001M3Dao.searchTbLsLocElecDTO(lsNo, locId));
			tbLsLocElecLocPaymentDTO.setTbLsLocPaymentDTOList(lS001M3Dao.searchTbLsLocPaymen(lsNo, locId, paymentItems));
			return tbLsLocElecLocPaymentDTO;
		}
		if (StringUtils.equals("NEW", appMode)) {
			tbLsLocElecLocPaymentDTO.setTbLsLocElecDTOList(lS001M3Dao.searchTbLsLocElecDTO(lsNo, locId));
			tbLsLocElecLocPaymentDTO.setTbLsLocPaymentDTOList(lS001M3Dao.searchTbLsLocPaymen(lsNo, locId, paymentItems));
		} else {
			if(StringUtils.isNotEmpty(lsNo) && StringUtils.isNotEmpty(locId) && StringUtils.isNotEmpty(appSql) ){
				List<TbLsLocElecDTO> oldList = lS001M3Dao.searchTbLsLocElecAdded(lsNo, locId, appSql, "BEFORE");
				if(oldList.size() > 0){
					List<TbLsLocElecDTO> newList = lS001M3Dao.searchTbLsLocElecAdded(lsNo, locId, appSql, "AFTER");
					tbLsLocElecLocPaymentDTO.setOldTbLsLocElecAddedList(oldList);
					tbLsLocElecLocPaymentDTO.setNewTbLsLocElecAddedList(newList);
				}else{
					tbLsLocElecLocPaymentDTO.setTbLsLocElecDTOList(lS001M3Dao.searchTbLsLocElecDTO(lsNo, locId));
				}
				List<TbLsLocPaymentDTO> oldTbLsLocPaymentAddedList = lS001M3Dao.searchTbLsLocPaymentAdded(lsNo, locId, appSql, "BEFORE");
				if(oldTbLsLocPaymentAddedList.size() > 0){
					tbLsLocElecLocPaymentDTO.setOldTbLsLocPaymentAddedList(oldTbLsLocPaymentAddedList);
					List<TbLsLocPaymentDTO> newTbLsLocPaymentAddedList = lS001M3Dao.searchTbLsLocPaymentAdded(lsNo, locId, appSql, "AFTER");
					tbLsLocElecLocPaymentDTO.setNewTbLsLocPaymentAddedList(newTbLsLocPaymentAddedList);
				}else{
					tbLsLocElecLocPaymentDTO.setTbLsLocPaymentDTOList(lS001M3Dao.searchTbLsLocPaymen(lsNo, locId, paymentItems));
				}
			}
		}
		return tbLsLocElecLocPaymentDTO;
	}

	/**
	 * 使用合約編號和站點編號找尋最大版次的站台資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param locId
	 *            站點編號
	 * @return
	 */
	public List<TbLsSiteDTO> getLsSiteByLocId(String lsNo, String locId) {
		HashMap<String, String> dataObj = new HashMap<String, String>();
		dataObj.put("lsNo", lsNo);
		dataObj.put("locId", locId);
		List<TbLsSiteDTO> list = lS001M3Dao.getLsSiteByLocId(dataObj);
		return list;
	}

	/**
	 * 儲存為草稿
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param addType
	 *            類別
	 * @param addItme
	 *            項目
	 * @param formId
	 *            套表格式
	 * @param locId
	 *            站點編號
	 * @param lsVers
	 *            版次
	 * @param editOldElecs
	 *            舊用電
	 * @param editNewElecs
	 *            新用電
	 * @param oldElecEndDegree
	 *            舊期末度數
	 * @param newElecEndDegree
	 *            新期末度數
	 * @param oldPayment
	 *            舊付款資訊
	 * @param newPayment
	 *            新付款資訊
	 * @param editPayAloc
	 *            分攤比率
	 * @param editPayAmt
	 *            金額
	  * @param editFlags
	 *            修改記錄
	 */ 
	@Transactional
	public void insertLsApp(String lsNo, String addType, String addItme, String formId, String locId, String[] lsVers, String[] editOldElecs, String[] editNewElecs, String[] oldElecEndDegree, String[] newElecEndDegree, String[] oldPayment, String[] newPayment, String[] editPayAloc, String[] editPayAmt,String[] editFlags) {
		try {
			String appSql = uniqueSeqService.getNextLeaseAppNo(lsNo);
			lS001M3Dao.insertLsApp(setInsertTbLsAppData(appSql, "0", lsNo, addType, addItme, formId));
			String strDate = insertTbLsLocElecAdded(appSql, lsNo, locId, lsVers, editOldElecs, editNewElecs, oldElecEndDegree, newElecEndDegree,editFlags);
			insertTbLsLocPaymentAdded(appSql, lsNo, locId, oldPayment, newPayment, editPayAloc, editPayAmt,strDate);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 更新資料
	 * 
	 * @param appSql
	 *            申請流水號
	 * @param lsNo
	 *            合約編號
	 * @param formId
	 *            套表格式
	 * @param locId
	 *            站點編號
	 * @param lsVers
	 *            版次
	 * @param editOldElecs
	 *            舊用電
	 * @param editNewElecs
	 *            新用電
	 * @param oldElecEndDegree
	 *            舊期末度數
	 * @param newElecEndDegree
	 *            新期末度數
	 * @param oldPayment
	 *            舊付款資訊
	 * @param newPayment
	 *            新付款資訊
	 * @param editPayAloc
	 *            分攤比率
	 * @param editPayAmt
	 *            金額
	 * @param editFlags
	 *            修改記錄
	 */            
	@Transactional
	public void updateLsApp(String appSql, String lsNo, String formId, String locId, String[] lsVers, String[] editOldElecs, String[] editNewElecs, String[] oldElecEndDegree, String[] newElecEndDegree, String[] oldPayment, String[] newPayment, String[] editPayAloc, String[] editPayAmt,String[] editFlags) {
		try {
			TbLsAppExample example = new TbLsAppExample();
			example.createCriteria().andAPP_SEQEqualTo(appSql).andLS_NOEqualTo(lsNo);
			lS001M3Dao.updateLsApp(setUpdateTbLsAppData(formId), example);
			String strDate = insertTbLsLocElecAdded(appSql, lsNo, locId, lsVers, editOldElecs, editNewElecs, oldElecEndDegree, newElecEndDegree,editFlags);
			insertTbLsLocPaymentAdded(appSql, lsNo, locId, oldPayment, newPayment, editPayAloc, editPayAmt,strDate);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 新增至 TbLsLocElecAdded 暫存
	 * 
	 * @param appSql
	 *            申請流水號
	 * @param lsNo
	 *            合約編號
	 * @param locId
	 *            站點編號
	 * @param lsVers
	 *            版次
	 * @param editOldElecs
	 *            舊用電
	 * @param editNewElecs
	 *            新用電
	 * @param oldElecEndDegree
	 *            舊期末度數
	 * @param newElecEndDegree
	 *            新期末度數
	 * @param editFlags
	 *            修改記錄
	 */
	private String insertTbLsLocElecAdded(String appSql, String lsNo, String locId, String[] lsVers, String[] editOldElecs, String[] editNewElecs, String[] oldElecEndDegree, String[] newElecEndDegree,String[] editFlags) {
		String strDate = null;
		TbLsLocElecAddedExample elecExample = new TbLsLocElecAddedExample();
		elecExample.createCriteria().andAPP_SEQEqualTo(StringUtils.trim(appSql));
		lS001M3Dao.delTbLsLocElecAdded(elecExample);

		TbLsLocElecAdded elecRecord;
		
		for (int i = 0; i < editOldElecs.length; i++) {
			elecRecord = new TbLsLocElecAdded();
			String[] temp = editOldElecs[i].split(",");
			elecRecord.setAPP_SEQ(StringUtils.trim(appSql));
			elecRecord.setADDED_STATE("BEFORE");
			elecRecord.setLS_NO(StringUtils.trim(lsNo));
			elecRecord.setLOCATION_ID(StringUtils.trim(locId));
			elecRecord.setLS_VER(StringUtils.trim(lsVers[i]));
			elecRecord.setCR_USER(getLoginUser().getUsername());
			elecRecord.setMD_USER(getLoginUser().getUsername());
			elecRecord.setCR_TIME(DateUtils.today());
			elecRecord.setMD_TIME(DateUtils.today());
			if (StringUtils.isNotEmpty(oldElecEndDegree[i])) {
				elecRecord.setEND_DEGREE(new BigDecimal(StringUtils.trim(oldElecEndDegree[i])));
			}
			elecRecord.setELEC_MODE(StringUtils.trim(temp[0]));
			elecRecord.setENERGY_METER(StringUtils.trim(temp[1]));
			if(StringUtils.isNotEmpty(temp[2]) && (!StringUtils.equals("null", temp[2]))){
				elecRecord.setCHRG_MODE(StringUtils.trim(temp[2]));
			}
			if (StringUtils.isNotEmpty(temp[3])) {
				elecRecord.setBEGIN_DEGREE(new BigDecimal(StringUtils.trim(temp[3])));
			}
			if (StringUtils.isNotEmpty(temp[4])) {
				elecRecord.setELEC_BEGIN_DATE(DateUtils.paserDate(StringUtils.trim(temp[4]), "yyyy/MM/dd"));
			}
			if (StringUtils.isNotEmpty(temp[5])) {
				elecRecord.setELEC_END_DATE(DateUtils.paserDate(StringUtils.trim(temp[5]), "yyyy/MM/dd"));
			}
			if(StringUtils.isNotEmpty(temp[6])){
				elecRecord.setELEC_ADDR(temp[6]);
			}
			lS001M3Dao.insertTbLsLocElecAdded(elecRecord);
		}
		for (int i = 0; i < editNewElecs.length; i++) {
			elecRecord = new TbLsLocElecAdded();
			String[] temp = editNewElecs[i].split(",");
			elecRecord.setAPP_SEQ(StringUtils.trim(appSql));
			elecRecord.setADDED_STATE("AFTER");
			elecRecord.setLS_NO(StringUtils.trim(lsNo));
			elecRecord.setLOCATION_ID(StringUtils.trim(locId));
			elecRecord.setLS_VER(StringUtils.trim(lsVers[i]));
			elecRecord.setCR_USER(getLoginUser().getUsername());
			elecRecord.setMD_USER(getLoginUser().getUsername());
			elecRecord.setCR_TIME(DateUtils.today());
			elecRecord.setMD_TIME(DateUtils.today());
			if (StringUtils.isNotEmpty(newElecEndDegree[i])) {
				elecRecord.setEND_DEGREE(new BigDecimal(StringUtils.trim(newElecEndDegree[i])));
			}
			elecRecord.setELEC_MODE(StringUtils.trim(temp[0]));
			elecRecord.setENERGY_METER(StringUtils.trim(temp[1]));
			elecRecord.setCHRG_MODE(StringUtils.trim(temp[2]));
			if (StringUtils.isNotEmpty(temp[3])) {
				elecRecord.setBEGIN_DEGREE(new BigDecimal(StringUtils.trim(temp[3])));
			}
			if (StringUtils.isNotEmpty(temp[4])) {
				elecRecord.setELEC_BEGIN_DATE(DateUtils.paserDate(StringUtils.trim(temp[4]), "yyyy/MM/dd"));
				if(StringUtils.equals("true", editFlags[i])){
					strDate = temp[4];
					elecRecord.setCHG_DATE(DateUtils.paserDate(StringUtils.trim(temp[4]), "yyyy/MM/dd"));	
				}
			}
			if (StringUtils.isNotEmpty(temp[5])) {
				elecRecord.setELEC_END_DATE(DateUtils.paserDate(StringUtils.trim(temp[5]), "yyyy/MM/dd"));
			}
			if(StringUtils.isNotEmpty(temp[6])){
				elecRecord.setELEC_ADDR(temp[6]);
			}
			lS001M3Dao.insertTbLsLocElecAdded(elecRecord);
		}
		return strDate;
	}

	/**
	 * 新增至 TbLsLocPaymentAdded 暫存
	 * 
	 * @param appSql
	 *            申請流水號
	 * @param lsNo
	 *            合約編號
	 * @param locId
	 *            站點編號
	 * @param oldPayment
	 *            舊付款資訊
	 * @param newPayment
	 *            新付款資訊
	 * @param editPayAloc
	 *            分攤比率
	 * @param editPayAmt
	 *            金額
	 * @param strDate
	 *            變更起始日
	 */
	private void insertTbLsLocPaymentAdded(String appSql, String lsNo, String locId, String[] oldPayment, String[] newPayment, String[] editPayAloc, String[] editPayAmt,String strDate) {
		TbLsLocPaymentAddedExample paymentExample = new TbLsLocPaymentAddedExample();
		paymentExample.createCriteria().andAPP_SEQEqualTo(StringUtils.trim(appSql));
		lS001M3Dao.delTbLsLocPaymentAdded(paymentExample);
		TbLsLocPaymentAdded paymentRecord;
		if(oldPayment != null){
			for (String payment : oldPayment) {
				paymentRecord = new TbLsLocPaymentAdded();
				String[] temp = payment.split(",");
				paymentRecord.setAPP_SEQ(StringUtils.trim(appSql));
				paymentRecord.setLS_NO(StringUtils.trim(lsNo));
				paymentRecord.setCR_USER(getLoginUser().getUsername());
				paymentRecord.setMD_USER(getLoginUser().getUsername());
				paymentRecord.setCR_TIME(DateUtils.today());
				paymentRecord.setMD_TIME(DateUtils.today());
				paymentRecord.setLOCATION_ID(StringUtils.trim(temp[0]));
				paymentRecord.setLESSOR_NAME(StringUtils.trim(temp[1]));
				paymentRecord.setRECIPIENT_ID(StringUtils.trim(temp[2]));
				paymentRecord.setRECIPIENT_NAME(StringUtils.trim(temp[3]));
				paymentRecord.setLESSOR_RELATION(StringUtils.trim(temp[4]));
				paymentRecord.setPAYMENT_MODE(StringUtils.trim(temp[5]));
				paymentRecord.setUNIT_CODE(StringUtils.trim(temp[6]));
				paymentRecord.setSUB_UNIT_CODE(StringUtils.trim(temp[7]));
				paymentRecord.setACCOUNT_NO(StringUtils.trim(temp[8]));
				if (StringUtils.isNotEmpty(temp[9])) {
					paymentRecord.setBUSINESS_TAX(new BigDecimal(StringUtils.trim(temp[9])));
				}
				paymentRecord.setLS_VER(StringUtils.trim(temp[10]));
				paymentRecord.setPAYMENT_ITEM(StringUtils.trim(temp[11]));
				if (StringUtils.isNotEmpty(temp[12])) {
					paymentRecord.setPAY_ALOC(new BigDecimal(StringUtils.trim(temp[12])));
				}
				if (StringUtils.isNotEmpty(temp[13])) {
					paymentRecord.setPAY_AMT(new BigDecimal(StringUtils.trim(temp[13])));
				}
				paymentRecord.setADDED_STATE("BEFORE");
				lS001M3Dao.insertTbLsLocPaymentAdded(paymentRecord);
			}
			for (int i = 0; i < newPayment.length; i++) {
				paymentRecord = new TbLsLocPaymentAdded();
				String[] temp = newPayment[i].split(",");
				paymentRecord.setAPP_SEQ(StringUtils.trim(appSql));
				paymentRecord.setLS_NO(StringUtils.trim(lsNo));
				paymentRecord.setCR_USER(getLoginUser().getUsername());
				paymentRecord.setMD_USER(getLoginUser().getUsername());
				paymentRecord.setCR_TIME(DateUtils.today());
				paymentRecord.setMD_TIME(DateUtils.today());
				paymentRecord.setLOCATION_ID(StringUtils.trim(temp[0]));
				paymentRecord.setLESSOR_NAME(StringUtils.trim(temp[1]));
				paymentRecord.setRECIPIENT_ID(StringUtils.trim(temp[2]));
				paymentRecord.setRECIPIENT_NAME(StringUtils.trim(temp[3]));
				paymentRecord.setLESSOR_RELATION(StringUtils.trim(temp[4]));
				paymentRecord.setPAYMENT_MODE(StringUtils.trim(temp[5]));
				paymentRecord.setUNIT_CODE(StringUtils.trim(temp[6]));
				paymentRecord.setSUB_UNIT_CODE(StringUtils.trim(temp[7]));
				paymentRecord.setACCOUNT_NO(StringUtils.trim(temp[8]));
				if(StringUtils.isNotEmpty(strDate)){
					paymentRecord.setCHG_DATE(DateUtils.paserDate(StringUtils.trim(strDate), "yyyy/MM/dd"));
				}
				if (StringUtils.isNotEmpty(temp[9])) {
					paymentRecord.setBUSINESS_TAX(new BigDecimal(StringUtils.trim(temp[9])));
				}
				paymentRecord.setLS_VER(StringUtils.trim(temp[10]));
				paymentRecord.setPAYMENT_ITEM(StringUtils.trim(temp[11]));
				if (StringUtils.isNotEmpty(editPayAloc[i])) {
					paymentRecord.setPAY_ALOC(new BigDecimal(StringUtils.trim(editPayAloc[i])));
				}
				if (StringUtils.isNotEmpty(editPayAmt[i])) {
					paymentRecord.setPAY_AMT(new BigDecimal(StringUtils.trim(editPayAmt[i])));
				}
				paymentRecord.setADDED_STATE("AFTER");
				lS001M3Dao.insertTbLsLocPaymentAdded(paymentRecord);
			}
		}
	}

	/**
	 * 用電歸零-儲存草稿
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param addType
	 *            類別
	 * @param addItme
	 *            項目
	 * @param formId
	 *            套表格式
	 * @param locId
	 *            站點編號
	 * @param lsVers
	 *            版次
	 * @param editOldElecs
	 *            舊用電
	 * @param editNewElecs
	 *            新用電
	 * @param oldElecEndDegree
	 *            舊期末度數
	 * @param newElecEndDegree
	 *            新期末度數
	 */
	@Transactional
	public void saveElecCh(String lsNo, String addType, String addItme, String formId, String locId, String[] lsVers, String[] editOldElecs, String[] editNewElecs, String[] oldElecEndDegree, String[] newElecEndDegree) {
		try {
			String appSql = uniqueSeqService.getNextLeaseAppNo(lsNo);
			lS001M3Dao.insertLsApp(setInsertTbLsAppData(appSql, "2", lsNo, addType, addItme, formId));
			String tempLocId = null;
			int tempInt = 0;
			for (int i = 0; i < newElecEndDegree.length; i++) {
				if(StringUtils.isNotEmpty(newElecEndDegree[i])){
					String[] oldTemp = editOldElecs[i].split(",");
					tempLocId = oldTemp[1];
					tempInt = i;
				}
			}
			TbLsLocElecExample example = new TbLsLocElecExample();
			example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVers[0]);
			List<TbLsLocElec> tbLsLocElecList = lS001M3Dao.searchtbLsLocElec(example);
			for(TbLsLocElec list:tbLsLocElecList){
				if(StringUtils.equals(list.getENERGY_METER(), tempLocId)){
					String[] newTemp = editNewElecs[tempInt].split(",");
					if (StringUtils.isNotEmpty(newTemp[3])) {
						list.setBEGIN_DEGREE(new BigDecimal(StringUtils.trim(newTemp[3])));
					}
					list.setELEC_BEGIN_DATE(DateUtils.today());
				}
				list.setLS_VER(String.valueOf((Integer.valueOf(list.getLS_VER()) + 1)));
				list.setMD_USER(getLoginUser().getUsername());
				list.setMD_TIME(DateUtils.today());
				lS001M3Dao.insertElecCh(list);
			}
			TbLsLocElecExample exampleElec = new TbLsLocElecExample();
			exampleElec.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVers[0]).andLOCATION_IDEqualTo(locId).andENERGY_METEREqualTo(tempLocId);
			List<TbLsLocElec> elecList = lS001M3Dao.searchtbLsLocElec(exampleElec);
			for(TbLsLocElec list:elecList){
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, -1);
				list.setELEC_END_DATE(cal.getTime());
				list.setEND_DEGREE(new BigDecimal(newElecEndDegree[tempInt]));
				list.setMD_USER(getLoginUser().getUsername());
				list.setMD_TIME(DateUtils.today());
				lS001M3Dao.updateElecCh(list, exampleElec);
			}
//			for (int i = 0; i < newElecEndDegree.length; i++) {
//				TbLsLocElecExample example = new TbLsLocElecExample();
//				String[] oldTemp = editOldElecs[i].split(",");
//				example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVers[i]).andLOCATION_IDEqualTo(locId).andENERGY_METEREqualTo(oldTemp[1]);
//				if (StringUtils.isNotEmpty(newElecEndDegree[i])) {
//					List<TbLsLocElec> elecList = lS001M3Dao.searchtbLsLocElec(example);
//					if (elecList != null && elecList.size() > 0) {
//						for(TbLsLocElec list : elecList){
//							String[] newTemp = editNewElecs[i].split(",");
//							Calendar cal = Calendar.getInstance();
//							cal.add(Calendar.DAY_OF_MONTH, -1);
//							list.setELEC_END_DATE(cal.getTime());
//							list.setEND_DEGREE(new BigDecimal(newElecEndDegree[i]));
//							lS001M3Dao.updateElecCh(list, example);
//							list.setLS_VER(String.valueOf((Integer.valueOf(elecList.get(0).getLS_VER()) + 1)));
//							list.setELEC_MODE(StringUtils.trim(newTemp[0]));
//							list.setENERGY_METER(StringUtils.trim(newTemp[1]));
//							list.setCHRG_MODE(StringUtils.trim(newTemp[2]));
//							list.setCR_USER(getLoginUser().getUsername());
//							list.setMD_USER(getLoginUser().getUsername());
//							list.setCR_TIME(DateUtils.today());
//							list.setMD_TIME(DateUtils.today());
//							if (StringUtils.isNotEmpty(newTemp[3])) {
//								list.setBEGIN_DEGREE(new BigDecimal(StringUtils.trim(newTemp[3])));
//							}
//							if (StringUtils.isNotEmpty(newTemp[4])) {
//								list.setELEC_BEGIN_DATE(DateUtils.paserDate(StringUtils.trim(newTemp[4]), "yyyy/MM/dd"));
//							} else {
//								list.setELEC_BEGIN_DATE(DateUtils.today());
//							}
//							if (StringUtils.isNotEmpty(newTemp[5])) {
//								list.setELEC_END_DATE(DateUtils.paserDate(StringUtils.trim(newTemp[5]), "yyyy/MM/dd"));
//							}
//							lS001M3Dao.insertElecCh(list);
//						}
//					}
//				} else {
//					List<TbLsLocElec> elecList = lS001M3Dao.searchtbLsLocElec(example);
//					if (elecList != null && elecList.size() > 0) {
//						for (TbLsLocElec elec : elecList) {
//							elec.setLS_VER(String.valueOf((Integer.valueOf(elec.getLS_VER()) + 1)));
//							elec.setMD_USER(getLoginUser().getUsername());
//							elec.setMD_TIME(DateUtils.today());
//							lS001M3Dao.updateElecCh(elec, example);
//						}
//					}
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	// ================Wei End====================

	/**
	 * 查詢增補協議套表資訊
	 * 
	 * @param AddItem
	 *            增補協議項目
	 * @return
	 */
	public List<TbLsAppForm> searchLsAppFORM(String AddItem) {
		List<TbLsAppForm> appFromList = new ArrayList<TbLsAppForm>();
		String appType = "";
		List<String> subType = new ArrayList<String>();

		if (AddItem.equals("更改計費方式－一般"))
		{
			appType = "ELECUPD";
			subType.add("EU1");
			subType.add("EU2");
		} else if (AddItem.equals("更改計費方式－實報實銷"))
		{
			appType = "ELECUPD";
			subType.add("EU3");
		} else if (AddItem.equals("租金停付"))
		{
			appType = "RENTUPD";
			subType.add("RU1");
		} else if (AddItem.equals("租金調降"))
		{
			appType = "RENTUPD";
			subType.add("RU2");
		} else if (AddItem.equals("租金調漲"))
		{
			appType = "RENTUPD";
			subType.add("RU3");
		} else if (AddItem.equals("管理費變更租金"))
		{
			appType = "RENTUPD";
			subType.add("RU4");
		} else if (AddItem.equals("不調租金"))
		{
			appType = "EQUIPUPD";
			subType.add("RU5");
		} else if (AddItem.equals("調漲租金"))
		{
			appType = "EQUIPUPD";
			subType.add("RU6");
		} else if (AddItem.equals("調漲租金及電費"))
		{
			appType = "EQUIPUPD";
			subType.add("RU7");
		} else if (AddItem.equals("不調租金調電費"))
		{
			appType = "EQUIPUPD";
			subType.add("RU8");
		} else if (AddItem.equals("繼受出租人"))
		{
			appType = "LESSORUPD";
			subType.add("LU1");
		} else if (AddItem.equals("變更負責人"))
		{
			appType = "LESSORUPD";
			subType.add("LU2");
		} else if (AddItem.equals("變更出租人"))
		{
			appType = "LESSORUPD";
			subType.add("LU3");
		} else if (AddItem.equals("出租人更名"))
		{
			appType = "LESSORUPD";
			subType.add("LU4");
		} else if (AddItem.equals("變更印鑑"))
		{
			appType = "LESSORUPD";
			subType.add("LU5");
		} else if (AddItem.equals("租金抵扣所得稅"))
		{
			appType = "LESSORUPD";
			subType.add("LU6");
		} else if (AddItem.equals("變更電匯帳戶"))
		{
			appType = "LESSORUPD";
			subType.add("LU7");
			subType.add("LU8");
		}

		if (subType.size() > 0) {
			appFromList = lS001M3Dao.searchLsAppFORM(appType, subType);
		}
		return appFromList;
	}

	/**
	 * 寫入增補協議-租金起算日異動資料
	 * 
	 * @param tbLsapp
	 * @param map
	 * @param locationId
	 * @return 申請單號
	 * @throws ParseException
	 */
	@Transactional
	public String addRentDate(String lsNo, TbLsApp tbLsapp, Map<String, String[]> map, String[] locationId) throws Exception {

		String lsAppSeq = uniqueSeqService.getNextLeaseAppNo(lsNo);
		tbLsapp.setAPP_SEQ(lsAppSeq);

		// 寫入申請單號
		int lsAppCount = lS001M3Dao.addTbLsApp(tbLsapp);

		Date date = new Date();
		// 組成寫入Temp檔資料
		for (int i = 0; i < locationId.length; i++)
		{
			TbLsLocationAdded tbLsLocationAdded = new TbLsLocationAdded();
			tbLsLocationAdded.setAPP_SEQ(tbLsapp.getAPP_SEQ());
			tbLsLocationAdded.setLS_NO(tbLsapp.getLS_NO());
			tbLsLocationAdded.setLS_VER(map.get("ls_VER")[i]);
			tbLsLocationAdded.setLOCATION_ID(map.get("location_ID")[i]);
			tbLsLocationAdded.setZIP_CODE(map.get("zip_CODE")[i]);
			tbLsLocationAdded.setDOMAIN(map.get("domain")[i]);
			if (map.get("olsPayBeginDate")[i] != null && !"".equals(map.get("olsPayBeginDate")[i]))
			{
				tbLsLocationAdded.setPAY_BEGIN_DATE(DateUtils.paserDate(StringUtils.trim(map.get("olsPayBeginDate")[i]), "yyyy/MM/dd"));
			}
			tbLsLocationAdded.setADDED_STATE("BEFORE");
			if (map.get("lsEffDate")[i] != null && !"".equals(map.get("lsEffDate")[i]))
			{
				tbLsLocationAdded.setEFF_DATE(DateUtils.paserDate(StringUtils.trim(map.get("lsEffDate")[i]), "yyyy/MM/dd"));
			}

			if (map.get("lsEndDate")[i] != null && !"".equals(map.get("lsEndDate")[i]))
			{
				tbLsLocationAdded.setLS_E_DATE(DateUtils.paserDate(StringUtils.trim(map.get("lsEndDate")[i]), "yyyy/MM/dd"));
			}
			
			tbLsLocationAdded.setCR_TIME(date);
			tbLsLocationAdded.setCR_USER(this.getLoginUser().getUsername());

			// 寫入原本資料
			int orgCunt = lS001M3Dao.addTbLsLocationAdded(tbLsLocationAdded);

			if (map.get("lsPayBeginDate")[i] != null && !"".equals(map.get("lsPayBeginDate")[i]))
			{
				tbLsLocationAdded.setPAY_BEGIN_DATE(DateUtils.paserDate(StringUtils.trim(map.get("lsPayBeginDate")[i]), "yyyy/MM/dd"));
			}
			tbLsLocationAdded.setADDED_STATE("AFTER");
			tbLsLocationAdded.setLS_VER(String.valueOf(Integer.valueOf(map.get("ls_VER")[i]) + 1));

			// 寫入新的資料
			int newCunt = lS001M3Dao.addTbLsLocationAdded(tbLsLocationAdded);
		}

		return lsAppSeq;
	}

	/**
	 * 取得站點資訊 By合約，申請單流水號，狀態
	 * 
	 * @param lsNo
	 * @param appSeq
	 * @param addState
	 * @return
	 */
	public List<TbLsLocationAddedDTO> searchLsLocationAddedByAppSeq(String lsNo, String appSeq, String addState) {
		HashMap<String, String> dataObj = new HashMap<String, String>();
		dataObj.put("lsNo", lsNo);
		dataObj.put("appSeq", appSeq);
		dataObj.put("addState", addState);
		List<TbLsLocationAddedDTO> lsLocationAddedDTO = lS001M3Dao.searchLsLocationAddedByAppSeq(dataObj);
		return lsLocationAddedDTO;
	}

	/**
	 * 取得手機回饋暫存檔 By合約，申請單流水號，狀態
	 * 
	 * @param lsNo
	 * @param appSeq
	 * @param addState
	 * @return
	 */
	public List<TbLsRewardAddedDTO> searchLsRewardAddedByAppSeq(String lsNo, String appSeq, String addState) {
		HashMap<String, String> dataObj = new HashMap<String, String>();
		dataObj.put("lsNo", lsNo);
		dataObj.put("appSeq", appSeq);
		dataObj.put("addState", addState);
		List<TbLsRewardAddedDTO> list = lS001M3Dao.searchLsRewardAddedByAppSeq(dataObj);
		Map<String, String> rewardResn = ls001Service.getRewardResn();
		for (TbLsRewardAddedDTO tbLsRewardAddedDTO : list)
		{
			if (tbLsRewardAddedDTO.getREWARD_RESN() != null && !tbLsRewardAddedDTO.getREWARD_RESN().equals("")) {
				tbLsRewardAddedDTO.setResnDesc(rewardResn.get(tbLsRewardAddedDTO.getREWARD_RESN()));
			}
		}
		return list;
	}

	/**
	 * 更新增補協議-租金起算日異動資料
	 * 
	 * @param appSeq
	 * @param lsNo
	 * @param map
	 * @param locationId
	 * @return
	 */
	@Transactional
	public String updateRentDate(String appSeq, String lsNo, Map<String, String[]> map, String[] locationId)
	{
		// 更新申請檔資料
		TbLsAppExample example = new TbLsAppExample();
		example.createCriteria().andAPP_SEQEqualTo(appSeq);
		TbLsApp tbLsApp = new TbLsApp();
		tbLsApp.setMD_TIME(new Date());
		tbLsApp.setMD_USER(getLoginUser().getUsername());
		lS001M3Dao.updateLsApp(tbLsApp, example);

		// 刪除暫存檔
		TbLsLocationAddedExample locationAddedExample = new TbLsLocationAddedExample();
		locationAddedExample.createCriteria().andLS_NOEqualTo(lsNo).andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER");
		lS001M3Dao.delTbLsLocationAdded(locationAddedExample);

		// 組成寫入Temp檔資料
		Date date = new Date();
		for (int i = 0; i < locationId.length; i++)
		{
			TbLsLocationAdded tbLsLocationAdded = new TbLsLocationAdded();
			tbLsLocationAdded.setAPP_SEQ(appSeq);
			tbLsLocationAdded.setLS_NO(lsNo);
			tbLsLocationAdded.setLS_VER(map.get("ls_VER")[i]);
			tbLsLocationAdded.setLOCATION_ID(map.get("location_ID")[i]);
			tbLsLocationAdded.setZIP_CODE(map.get("zip_CODE")[i]);
			tbLsLocationAdded.setDOMAIN(map.get("domain")[i]);
			tbLsLocationAdded.setADDED_STATE("AFTER");

			if (map.get("lsEffDate")[i] != null && !"".equals(map.get("lsEffDate")[i]))
			{
				tbLsLocationAdded.setEFF_DATE(DateUtils.paserDate(StringUtils.trim(map.get("lsEffDate")[i]), "yyyy/MM/dd"));
			}
			if (map.get("lsEndDate")[i] != null && !"".equals(map.get("lsEndDate")[i]))
			{
				tbLsLocationAdded.setLS_E_DATE(DateUtils.paserDate(StringUtils.trim(map.get("lsEndDate")[i]), "yyyy/MM/dd"));
			}
			if (map.get("lsPayBeginDate")[i] != null && !"".equals(map.get("lsPayBeginDate")[i]))
			{
				tbLsLocationAdded.setPAY_BEGIN_DATE(DateUtils.paserDate(StringUtils.trim(map.get("lsPayBeginDate")[i]), "yyyy/MM/dd"));
			}

			tbLsLocationAdded.setCR_TIME(date);
			tbLsLocationAdded.setCR_USER(this.getLoginUser().getUsername());
			// 寫入Temp檔
			int newCunt =lS001M3Dao.addTbLsLocationAdded(tbLsLocationAdded);
		}

		return appSeq;
	}

	/**
	 * 寫入增補協議-手機回饋異動資料
	 * 
	 * @param lsNo
	 * @param tbLsapp
	 * @param map
	 * @param orewardId
	 * @param rewardId
	 * @return
	 */
	@Transactional
	public String addPhoneReward(String lsNo, TbLsApp tbLsapp, Map<String, String[]> map, String[] orewardId, String[] rewardId) {

		// 取得申請單Seq
		String lsAppSeq = uniqueSeqService.getNextLeaseAppNo(lsNo);
		tbLsapp.setAPP_SEQ(lsAppSeq);
		// 寫入申請單號
		int lsAppCount = lS001M3Dao.addTbLsApp(tbLsapp);
		int ver = 0;

		Date date = new Date();

		// 組成寫入Temp檔資料-原手機回饋資料
		for (int i = 0; i < orewardId.length; i++)
		{
			ver = Integer.valueOf(map.get("ols_VER")[i]);

			TbLsRewardAdded tbLsRewardAdded = new TbLsRewardAdded();
			tbLsRewardAdded.setAPP_SEQ(tbLsapp.getAPP_SEQ());
			tbLsRewardAdded.setLS_NO(lsNo);
			tbLsRewardAdded.setLS_VER(map.get("ols_VER")[i]);
			tbLsRewardAdded.setREWARD_TYPE(map.get("oreward_TYPE")[i]);
			tbLsRewardAdded.setREWARD_ID(map.get("oreward_ID")[i]);
			if (map.get("oreward_QTY")[i] != null && !"".equals(map.get("oreward_QTY")[i]))
			{
				tbLsRewardAdded.setREWARD_QTY(new BigDecimal(map.get("oreward_QTY")[i]));
			}
			if (map.get("oestimate_AMT")[i] != null && !"".equals(map.get("oestimate_AMT")[i]))
			{
				tbLsRewardAdded.setESTIMATE_AMT(new BigDecimal(map.get("oestimate_AMT")[i]));
			}

			tbLsRewardAdded.setREWARD_RESN(map.get("oreward_RESN")[i]);
			tbLsRewardAdded.setREWARD_DESC(map.get("oreward_DESC")[i]);

			tbLsRewardAdded.setADDED_STATE("BEFORE");

			tbLsRewardAdded.setCR_TIME(date);
			tbLsRewardAdded.setCR_USER(this.getLoginUser().getUsername());
			tbLsRewardAdded.setMD_TIME(date);
			tbLsRewardAdded.setMD_USER(this.getLoginUser().getUsername());

			// 寫入Temp檔-新的資料
			
			int orgCunt = lS001M3Dao.addTbLsRewardAdded(tbLsRewardAdded);
		}

		// 組成寫入Temp檔資料-新手機回饋資料
		ver += 1;
		for (int i = 0; i < rewardId.length; i++)
		{
			TbLsRewardAdded tbLsRewardAdded = new TbLsRewardAdded();
			tbLsRewardAdded.setAPP_SEQ(tbLsapp.getAPP_SEQ());
			tbLsRewardAdded.setLS_NO(lsNo);
			tbLsRewardAdded.setLS_VER(String.valueOf(ver));
			tbLsRewardAdded.setREWARD_TYPE("2");
			tbLsRewardAdded.setREWARD_ID(map.get("REWARD_ID")[i]);
			if (map.get("REWARD_QTY")[i] != null && !"".equals(map.get("REWARD_QTY")[i]))
			{
				tbLsRewardAdded.setREWARD_QTY(new BigDecimal(map.get("REWARD_QTY")[i]));
			}
			if (map.get("ESTIMATE_AMT")[i] != null && !"".equals(map.get("ESTIMATE_AMT")[i]))
			{
				tbLsRewardAdded.setESTIMATE_AMT(new BigDecimal(map.get("ESTIMATE_AMT")[i]));
			}
			if (map.get("GET_DATE")[i] != null && !"".equals(map.get("GET_DATE")[i]))
			{
				tbLsRewardAdded.setGET_DATE(DateUtils.paserDate(StringUtils.trim(map.get("GET_DATE")[i]), "yyyy/MM/dd"));
			}

			tbLsRewardAdded.setADDED_STATE("AFTER");

			tbLsRewardAdded.setCR_TIME(date);
			tbLsRewardAdded.setCR_USER(this.getLoginUser().getUsername());
			tbLsRewardAdded.setMD_TIME(date);
			tbLsRewardAdded.setMD_USER(this.getLoginUser().getUsername());

			// 寫入Temp檔-新的資料
			int newCunt = lS001M3Dao.addTbLsRewardAdded(tbLsRewardAdded);
		}

		return lsAppSeq;
	}

	/**
	 * 更新增補協議-手機回饋異動資料
	 * 
	 * @param lsNo
	 * @param appSeq
	 * @param lsVer
	 * @param tbLsapp
	 * @param map
	 * @param rewardId
	 * @return
	 */
	@Transactional
	public String updatePhoneReward(String lsNo, String appSeq, int lsVer, Map<String, String[]> map, String[] rewardId)
	{
		// 更新申請檔資料
		TbLsAppExample example = new TbLsAppExample();
		example.createCriteria().andAPP_SEQEqualTo(appSeq);
		TbLsApp tbLsApp = new TbLsApp();
		tbLsApp.setMD_TIME(new Date());
		tbLsApp.setMD_USER(getLoginUser().getUsername());
		lS001M3Dao.updateLsApp(tbLsApp, example);

		// 刪除暫存檔
		TbLsRewardAddedExample lsRewardAddedExample = new TbLsRewardAddedExample();
		lsRewardAddedExample.createCriteria().andLS_NOEqualTo(lsNo).andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER");
		lS001M3Dao.delTbLsRewardAdded(lsRewardAddedExample);

		// 組成寫入Temp檔資料-新手機回饋資料
		Date date = new Date();
		for (int i = 0; i < rewardId.length; i++)
		{
			TbLsRewardAdded tbLsRewardAdded = new TbLsRewardAdded();
			tbLsRewardAdded.setAPP_SEQ(appSeq);
			tbLsRewardAdded.setLS_NO(lsNo);
			tbLsRewardAdded.setLS_VER(String.valueOf((lsVer + 1)));
			tbLsRewardAdded.setREWARD_TYPE("2");
			tbLsRewardAdded.setREWARD_ID(map.get("REWARD_ID")[i]);
			if (map.get("REWARD_QTY")[i] != null && !"".equals(map.get("REWARD_QTY")[i]))
			{
				tbLsRewardAdded.setREWARD_QTY(new BigDecimal(map.get("REWARD_QTY")[i]));
			}
			if (map.get("ESTIMATE_AMT")[i] != null && !"".equals(map.get("ESTIMATE_AMT")[i]))
			{
				tbLsRewardAdded.setESTIMATE_AMT(new BigDecimal(map.get("ESTIMATE_AMT")[i]));
			}
			if (map.get("GET_DATE")[i] != null && !"".equals(map.get("GET_DATE")[i]))
			{
				tbLsRewardAdded.setGET_DATE(DateUtils.paserDate(StringUtils.trim(map.get("GET_DATE")[i]), "yyyy/MM/dd"));
			}

			tbLsRewardAdded.setADDED_STATE("AFTER");

			tbLsRewardAdded.setCR_TIME(date);
			tbLsRewardAdded.setCR_USER(this.getLoginUser().getUsername());
			tbLsRewardAdded.setMD_TIME(date);
			tbLsRewardAdded.setMD_USER(this.getLoginUser().getUsername());

			// 寫入Temp檔-新的資料
			int newCunt = lS001M3Dao.addTbLsRewardAdded(tbLsRewardAdded);
		}

		return appSeq;
	}

	/**
	 * 增補協議-站台編號異動
	 * 
	 * @param lsNo
	 * @param tbLsapp
	 * @param map
	 * @param orewardId
	 * @return
	 */
	@Transactional
	public String addSiteIdCh(String lsNo, String[] siteVer, String[] locId, TbLsApp tbLsapp, Map<String, String[]> map, String[] siteId) {
		// 取得申請單Seq
		String lsAppSeq = uniqueSeqService.getNextLeaseAppNo(lsNo);

		tbLsapp.setAPP_STATUS(AppStatusEnum.appStatus2.getAppStatus());
		tbLsapp.setAPP_SEQ(lsAppSeq);

		// 寫入申請單號
		int lsAppCount = lS001M3Dao.addTbLsApp(tbLsapp);

		
		// 修改站台資料
		for (int i = 0; i < siteId.length; i++)
		{
			Date date = new Date();
			Boolean flag=false;
			// 找出原有站台資料
			List<TbLsSite> lsSite = lS001M3Dao.searchTbLsSiteByLSNoIDVer(lsNo, siteVer[i], siteId[i], locId[i]);

			if (!("Original".equals(map.get("changeSite")[i])))
			{				
				flag=true;				
				
				//更新資源互換分攤檔
				TbLsResExchAloc tbLsResExchAloc=new TbLsResExchAloc();
				tbLsResExchAloc.setSITE_ID(map.get("changeSite")[i]);
				tbLsResExchAloc.setMD_TIME(date);
				tbLsResExchAloc.setMD_USER(this.getLoginUser().getUsername());
				
				TbLsResExchAlocExample example=new TbLsResExchAlocExample();
				example.createCriteria().andLS_NOEqualTo(lsNo).andLOCATION_IDEqualTo(locId[i]).andSITE_IDEqualTo(siteId[i]);
				lS001M3Dao.updateLsResExchAloc(tbLsResExchAloc, example);
			}

			for(int j=0;j<lsSite.size();j++)
			{
				if (flag)
				{
					TbSiteMain tbSiteMain=siteMainDao.findByPk(map.get("changeSite")[i]);
					
					lsSite.get(j).setSITE_ID(map.get("changeSite")[i]);
					lsSite.get(j).setBTS_SITE_ID(tbSiteMain.getBTS_SITE_ID());
				}
				lsSite.get(j).setLS_VER(String.valueOf(Integer.valueOf(lsSite.get(j).getLS_VER()) + 1));
				lsSite.get(j).setEFF_DATE(date);
				lsSite.get(j).setCR_TIME(date);
				lsSite.get(j).setCR_USER(this.getLoginUser().getUsername());
				lsSite.get(j).setMD_TIME(date);
				lsSite.get(j).setMD_USER(this.getLoginUser().getUsername());

				// 新增TbLsSite
				int insertCount = lS001M3Dao.addTbLsSite(lsSite.get(j));
			}
			
			date.setDate(date.getDate() - 1);
			TbLsSite tbLsSite = new TbLsSite();
			tbLsSite.setEND_DATE(date);

			// 更新原有TbLsSite資料
			int updateCount = lS001M3Dao.updateTbLsSiteByChangeSiteId(lsNo, siteVer[i], siteId[i], locId[i], tbLsSite);
		}
		
		//補齊站點底下資料
		List<LeaseLocationDTO> list=getTbLsLocation(lsNo,null);
		for(LeaseLocationDTO leaseLocationDTO:list)
		{
			if(!leaseLocationDTO.getLOCATION_ID().equals(locId[0]))
			{
				List<TbLsSite> lsSiteList = lS001M3Dao.searchLsSiteByLsNoLocIdVer(lsNo,leaseLocationDTO.getLOCATION_ID());
				for(TbLsSite tbLsSite:lsSiteList)
				{
					Date date = new Date();				
					String ver=tbLsSite.getLS_VER();
					tbLsSite.setLS_VER(String.valueOf(Integer.valueOf(ver) + 1));
					tbLsSite.setEFF_DATE(date);
					tbLsSite.setCR_TIME(date);
					tbLsSite.setCR_USER(this.getLoginUser().getUsername());
					tbLsSite.setMD_TIME(date);
					tbLsSite.setMD_USER(this.getLoginUser().getUsername());
					
					// 新增TbLsSite
					int insertCount = lS001M3Dao.addTbLsSite(tbLsSite);
					
					date.setDate(date.getDate() - 1);
					TbLsSite lsSite = new TbLsSite();
					lsSite.setEND_DATE(date);

					// 更新原有TbLsSite資料
					int updateCount = lS001M3Dao.updateTbLsSiteByChangeSiteId(lsNo, ver,tbLsSite.getSITE_ID(), leaseLocationDTO.getLOCATION_ID(), lsSite);
				}
				
			}
		}
		
		
		return lsAppSeq;
	}

	/**
	 * 
	 * @param locationId
	 * @param siteStatus
	 * @param siteId
	 * @return
	 */
	public List<SiteDTO> getSiteMainByLocationId(String locationId, String[] siteStatus, List<String> siteId)
	{
		return lS001M3Dao.getSiteMainByLocationId(locationId, siteStatus, siteId);
	}

	public TbLsApp searchTbLsApp(String appSeq)
	{
		return lS001M3Dao.searchTbLsApp(appSeq);
	}

	/**
	 * 撈取下拉值
	 * 
	 * @param type
	 *            Lookup Type
	 * @return
	 */
	public List<TbSysLookup> searchTbSysLookup(String type,List<String> codes) {
		TbSysLookupExample example = new TbSysLookupExample();
		if(codes == null){
			example.createCriteria().andTYPEEqualTo(type);
			example.setOrderByClause("DISPLAY_ORDER");
		}else{
			example.createCriteria().andTYPEEqualTo(type).andCODEIn(codes);
		}
		return lS001Dao.getLsLeaseBldgtype(example);
	}

	/**
	 * 撈取站點付款明細資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param locId
	 *            站點編號
	 * @param paymentItem
	 *            支付項目
	 * @return
	 */
	public List<TbLsLocPaymentDTO> searchTbLsLocPaymen(String lsNo, String locId, String[] paymentItems) {
		return lS001M3Dao.searchTbLsLocPaymen(lsNo, locId, paymentItems);
	}
	/**
	 * 撈取站點付款明細資料
	 * @param lsNo
	 * 			合約編號
	 * @param locId
	 * 			站點編號
	 * @param appSeq
	 * 			申請單號
	 * @param mainLsVer
	 * 			合約主檔版次
	 * @return
	 */
	public TbLsLocElecLocPaymentDTO searchRentChDate(String lsNo, String locId,String appSeq, String mainLsVer){
		TbLsLocElecLocPaymentDTO dto = new TbLsLocElecLocPaymentDTO();
		dto.setNewTbLsLocPaymentAddedList(lS001M3Dao.searchTbLsLocPaymentAdded(lsNo, locId, appSeq, "AFTER"));//新
		dto.setOldTbLsLocPaymentAddedList(lS001M3Dao.searchTbLsLocPaymentAdded(lsNo, locId, appSeq, "BEFORE"));//舊
		dto.setTbLsMainAdded(searchTbLsMainAdded(lsNo, appSeq, mainLsVer));
		HashMap<String, String> dataObj = new HashMap<String, String>();
		dataObj.put("lsNo", lsNo);
		dataObj.put("appSeq", appSeq);
		dataObj.put("locId", locId);
		dataObj.put("addState", "AFTER");
		List<TbLsLocationAddedDTO> tbLsLocationAddedDTOList = lS001M3Dao.searchLsLocationAddedByAppSeq(dataObj);
		if(tbLsLocationAddedDTOList.size() > 0){
			dto.setTbLsLocationAdded(tbLsLocationAddedDTOList.get(0));
		}
		return dto;
		
	}
	
	public TbLsMainAdded searchTbLsMainAdded(String lsNo, String appSeq, String mainLsVer){
		TbLsMainAddedExample mainExample = new TbLsMainAddedExample();
		mainExample.createCriteria().andLS_VEREqualTo(mainLsVer).andLS_NOEqualTo(lsNo).andAPP_SEQEqualTo(appSeq);
		List<TbLsMainAdded> tbLsMainAddedList=lS001M3Dao.searchTbLsMainAdded(mainExample);
		if(tbLsMainAddedList.size() > 0){
			return tbLsMainAddedList.get(0);
		}
		return null;
	}
	
	/**
	 * 
	 * 新增資料
	 * 
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			主檔版次
	 * @param addType
	 * 			類別
	 * @param addItme
	 * 			項目
	 * @param formId
	 * 			套表格式
	 * @param changeRent
	 * 			變更後租金
	 * @param oldPayData
	 * 			舊資料
	 * @param newPayAmt
	 * 			變更金額
	 * @param changeStrDay
	 * 			變更起始日
	 * @param lsRentTypeChgSelect
	 * 			變更租金類別
	 * @param lsStopResnSelect
	 * 			停付原因
	 * @param stopBDate
	 * 			停付起
	 * @param stopEDate
	 * 			停付迄
	 * @param totalMonth
	 * 			共幾月
	 * @param editLocId
	 * 			站點編號
	 * @param editLocData
	 * 			站點資料
	 */
	@Transactional
	public void saveRentCh(String lsNo, String lsVer, String addType, String addItme, String formId, String changeRent, String[] oldPayData, String[] newPayAmt, String changeStrDay, String lsRentTypeChgSelect, String lsStopResnSelect, String stopBDate, String stopEDate, String totalMonth, String editLocId, String editLocData) {
		try {
			String appSeq = uniqueSeqService.getNextLeaseAppNo(lsNo);
			lS001M3Dao.insertLsApp(setInsertTbLsAppData(appSeq, "0", lsNo, addType, addItme, formId));
			saveRentChData(appSeq, lsNo, lsVer, addItme, changeRent, oldPayData, newPayAmt, changeStrDay, lsRentTypeChgSelect, lsStopResnSelect, stopBDate, stopEDate, totalMonth, editLocId, editLocData);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	/**
	 * 更新資料
	 * @param appSeq
	 * 			申請流水號
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			主檔版次
	 * @param addItme
	 * 			項目
	 * @param formId
	 * 			套表格式
	 * @param changeRent
	 * 			變更後租金
	 * @param oldPayData
	 * 			舊資料
	 * @param newPayAmt
	 * 			變更金額
	 * @param changeStrDay
	 * 			變更起始日
	 * @param lsRentTypeChgSelect
	 * 			變更租金類別
	 * @param lsStopResnSelect
	 * 			停付原因
	 * @param stopBDate
	 * 			停付起
	 * @param stopEDate
	 * 			停付迄
	 * @param totalMonth
	 * 			總月數
	 * @param editLocId
	 * 			站點編號
	 * @param editLocData
	 * 			站點資料
	 */
	@Transactional
	public void updateLsAppRentCh(String appSeq, String lsNo, String lsVer, String addItme, String formId, String changeRent, String[] oldPayData, String[] newPayAmt, String changeStrDay, String lsRentTypeChgSelect, String lsStopResnSelect, String stopBDate, String stopEDate, String totalMonth, String editLocId, String editLocData) {
		try {
			TbLsAppExample example = new TbLsAppExample();
			example.createCriteria().andAPP_SEQEqualTo(appSeq).andLS_NOEqualTo(lsNo);
			lS001M3Dao.updateLsApp(setUpdateTbLsAppData(formId), example);
			saveRentChData(appSeq, lsNo, lsVer, addItme, changeRent, oldPayData, newPayAmt, changeStrDay, lsRentTypeChgSelect, lsStopResnSelect, stopBDate, stopEDate, totalMonth, editLocId, editLocData);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	/**
	 * 寫入暫存資料
	 * @param appSeq
	 * 			申請流水號
	 * @param lsNo
	 * 			合約編號
	 * @param lsVer
	 * 			版次
	 * @param addItme
	 * 			項目
	 * @param changeRent
	 * 			變更後租金
	 * @param oldPayData
	 * 			舊資料
	 * @param newPayAmt
	 * 			變更金額
	 * @param changeStrDay
	 * 			變更起始日
	 * @param lsRentTypeChgSelect
	 * 			變更租金類別
	 * @param lsStopResnSelect
	 * 			停付原因
	 * @param stopBDate
	 * 			停付起
	 * @param stopEDate
	 * 			停付迄
	 * @param totalMonth
	 * 			總月數
	 * @param editLocId
	 * 			站點編號
	 * @param editLocData
	 * 			站點資料
	 */
	private void saveRentChData(String appSeq, String lsNo, String lsVer, String addItme, String changeRent, String[] oldPayData, String[] newPayAmt, String changeStrDay, String lsRentTypeChgSelect, String lsStopResnSelect, String stopBDate, String stopEDate, String totalMonth, String editLocId, String editLocData) {
		if (StringUtils.equals(StringUtils.trim(addItme), "租金調降") || StringUtils.equals(StringUtils.trim(addItme), "租金調漲")) {
			TbLsLocPaymentAddedExample paymentExample = new TbLsLocPaymentAddedExample();
			paymentExample.createCriteria().andAPP_SEQEqualTo(StringUtils.trim(appSeq));
			lS001M3Dao.delTbLsLocPaymentAdded(paymentExample);
			for (int i = 0; i < oldPayData.length; i++) {
				String[] temp = oldPayData[i].split(",");
				TbLsLocPaymentAdded paymentAdded = new TbLsLocPaymentAdded();

				paymentAdded.setAPP_SEQ(StringUtils.trim(appSeq));
				paymentAdded.setLS_NO(StringUtils.trim(lsNo));
				paymentAdded.setPAYMENT_ITEM("R");
				paymentAdded.setLOCATION_ID(StringUtils.trim(temp[0]));
				paymentAdded.setLESSOR_NAME(StringUtils.trim(temp[1]));
				paymentAdded.setRECIPIENT_NAME(StringUtils.trim(temp[2]));
				paymentAdded.setPAYMENT_MODE(StringUtils.trim(temp[3]));
				paymentAdded.setUNIT_CODE(StringUtils.trim(temp[4]));
				paymentAdded.setSUB_UNIT_CODE(StringUtils.trim(temp[5]));
				paymentAdded.setACCOUNT_NO(StringUtils.trim(temp[6]));
				paymentAdded.setLS_VER(StringUtils.trim(temp[7]));
				if (StringUtils.isNotEmpty(temp[8])) {
					paymentAdded.setPAY_AMT(new BigDecimal(temp[8]));
				}
				paymentAdded.setCR_USER(getLoginUser().getUsername());
				paymentAdded.setMD_USER(getLoginUser().getUsername());
				paymentAdded.setCR_TIME(DateUtils.today());
				paymentAdded.setMD_TIME(DateUtils.today());
				paymentAdded.setADDED_STATE("BEFORE");
				paymentAdded.setCR_USER(getLoginUser().getUsername());
				paymentAdded.setMD_USER(getLoginUser().getUsername());
				paymentAdded.setCR_TIME(DateUtils.today());
				paymentAdded.setMD_TIME(DateUtils.today());
				lS001M3Dao.insertTbLsLocPaymentAdded(paymentAdded);
				if (StringUtils.isNotEmpty(newPayAmt[i])) {
					paymentAdded.setPAY_AMT(new BigDecimal(newPayAmt[i]));
				}
				paymentAdded.setADDED_STATE("AFTER");
				lS001M3Dao.insertTbLsLocPaymentAdded(paymentAdded);
			}
			TbLsLocationAddedExample locExample = new TbLsLocationAddedExample();
			String[] tempLoc = editLocData.split(",");
			locExample.createCriteria().andAPP_SEQEqualTo(appSeq);
			lS001M3Dao.delTbLsLocationAdded(locExample);
			TbLsLocationAdded tbLsLocationAdded = new TbLsLocationAdded();
			tbLsLocationAdded.setLS_NO(lsNo);
			tbLsLocationAdded.setLOCATION_ID(editLocId);
			tbLsLocationAdded.setLS_VER(tempLoc[0]);
			tbLsLocationAdded.setZIP_CODE(tempLoc[1]);
			tbLsLocationAdded.setDOMAIN(tempLoc[2]);
			tbLsLocationAdded.setLS_E_DATE(DateUtils.paserDate(tempLoc[3], "yyyy/MM/dd"));
			tbLsLocationAdded.setEFF_DATE(DateUtils.paserDate(tempLoc[4], "yyyy/MM/dd"));
			tbLsLocationAdded.setAPP_SEQ(appSeq);
			if (StringUtils.isNotEmpty(changeRent)) {
				tbLsLocationAdded.setRENT_AMT(new BigDecimal(changeRent));
			}
			tbLsLocationAdded.setADDED_STATE("AFTER");
			tbLsLocationAdded.setCHG_DATE(DateUtils.paserDate(StringUtils.trim(changeStrDay), "yyyy/MM/dd"));
			tbLsLocationAdded.setCR_USER(getLoginUser().getUsername());
			tbLsLocationAdded.setMD_USER(getLoginUser().getUsername());
			tbLsLocationAdded.setCR_TIME(DateUtils.today());
			tbLsLocationAdded.setMD_TIME(DateUtils.today());
			lS001M3Dao.addTbLsLocationAdded(tbLsLocationAdded);
		}else if(StringUtils.equals(StringUtils.trim(addItme), "租金停付")) {
			TbLsMainAddedExample example = new TbLsMainAddedExample();
			example.createCriteria().andAPP_SEQEqualTo(appSeq);
			lS001M3Dao.delTbLsMainAdded(example);
			TbLsMainAdded tbLsMainAdded = new TbLsMainAdded();
			tbLsMainAdded.setADDED_STATE("AFTER");
			tbLsMainAdded.setLS_NO(lsNo);
			tbLsMainAdded.setLS_VER(lsVer);
			tbLsMainAdded.setAPP_SEQ(appSeq);
			
			tbLsMainAdded.setSTOP_RESN(lsStopResnSelect);
			tbLsMainAdded.setSTOP_B_DATE(DateUtils.paserDate(StringUtils.trim(stopBDate), "yyyy/MM/dd"));
			tbLsMainAdded.setSTOP_E_DATE(DateUtils.paserDate(StringUtils.trim(stopEDate), "yyyy/MM/dd"));
			if (StringUtils.isNotEmpty(totalMonth)) {
				tbLsMainAdded.setSTOP_MONTH(new BigDecimal(totalMonth));
			}
			tbLsMainAdded.setRENT_CHG_DATE(DateUtils.paserDate(StringUtils.trim(changeStrDay), "yyyy/MM/dd"));
			tbLsMainAdded.setCR_USER(getLoginUser().getUsername());
			tbLsMainAdded.setMD_USER(getLoginUser().getUsername());
			tbLsMainAdded.setCR_TIME(DateUtils.today());
			tbLsMainAdded.setMD_TIME(DateUtils.today());
			lS001M3Dao.insertTbLsMainAdded(tbLsMainAdded);
		}else{
			TbLsMainAddedExample example = new TbLsMainAddedExample();
			example.createCriteria().andAPP_SEQEqualTo(appSeq);
			lS001M3Dao.delTbLsMainAdded(example);
			TbLsMainAdded tbLsMainAdded = new TbLsMainAdded();
			tbLsMainAdded.setADDED_STATE("AFTER");
			tbLsMainAdded.setLS_NO(lsNo);
			tbLsMainAdded.setLS_VER(lsVer);
			tbLsMainAdded.setAPP_SEQ(appSeq);
			tbLsMainAdded.setRENT_CHG_DATE(DateUtils.paserDate(StringUtils.trim(changeStrDay), "yyyy/MM/dd"));
			tbLsMainAdded.setRENT_TYPE_CHG(lsRentTypeChgSelect);
			tbLsMainAdded.setCR_USER(getLoginUser().getUsername());
			tbLsMainAdded.setMD_USER(getLoginUser().getUsername());
			tbLsMainAdded.setCR_TIME(DateUtils.today());
			tbLsMainAdded.setMD_TIME(DateUtils.today());
			lS001M3Dao.insertTbLsMainAdded(tbLsMainAdded);
		}
	}
	/**
	 * 塞 TbLsApp 資料
	 * @param formId
	 * 			套表格式
	 * @return
	 */
	private TbLsApp setUpdateTbLsAppData(String formId) {
		TbLsApp tbLsApp = new TbLsApp();
		tbLsApp.setFORM_ID(StringUtils.trim(formId));
		tbLsApp.setAPP_DEPT_ID(getLoginUser().getDeptId());
		tbLsApp.setMD_TIME(new Date());
		tbLsApp.setMD_USER(getLoginUser().getUsername());
		return tbLsApp;
	}

	/**
	 * 塞 TbLsApp 資料
	 * @param appSql
	 * 			申請流水號
	 * @param appStatus
	 * 			申請狀態
	 * @param lsNo
	 * 			合約編號
	 * @param addType
	 * 			類別
	 * @param addItme
	 * 			項目
	 * @param formId
	 * 			套表格式
	 * @return
	 */
	private TbLsApp setInsertTbLsAppData(String appSql, String appStatus, String lsNo, String addType, String addItme, String formId) {
		TbLsApp tbLsApp = new TbLsApp();
		tbLsApp.setAPP_SEQ(StringUtils.trim(appSql.trim()));
		tbLsApp.setLS_NO(StringUtils.trim(lsNo));
		tbLsApp.setLS_VER("1");
		tbLsApp.setLS_TYPE("5");
		tbLsApp.setAPP_STATUS(appStatus);
		tbLsApp.setADD_TYPE(StringUtils.trim(addType));
		tbLsApp.setADD_ITEM(StringUtils.trim(addItme));
		tbLsApp.setFORM_ID(StringUtils.trim(formId));
		tbLsApp.setAPP_DEPT_ID(getLoginUser().getDeptId());
		tbLsApp.setAPP_USER(getLoginUser().getUsername());
		tbLsApp.setCR_USER(getLoginUser().getUsername());
		tbLsApp.setCR_TIME(new Date());
		tbLsApp.setMD_TIME(new Date());
		tbLsApp.setMD_USER(getLoginUser().getUsername());
		return tbLsApp;
	}
	
	/**
	 * 查詢TB_PAY_LOOKUP_CODE項目
	 * @param LookupType
	 * @return
	 */
	public List<TbPayLookupCode> searchPayLookupByType(String LookupType)
	{
		return lS001M3Dao.getPayLookupByType(LookupType);
	}
	
	/**
	 * 查詢出租人暫存檔筆數
	 * 
	 * @param appSeq 申請流水號
	 * @param lsNo 合約編號
	 * @param lessorId 原出租人編號
	 * @param addedState 暫存檔狀態
	 * @return
	 */
	public int lessorCountByLsNoIdAppSeq(String appSeq,String lsNo,String lessorId,String addedState,String itemSel){
		TbLsLessorAddedExample example =new TbLsLessorAddedExample();
		if(itemSel.equals("出租人更名")||itemSel.equals("變更印鑑"))
		{
			example.createCriteria().andAPP_SEQEqualTo(appSeq).andLS_NOEqualTo(lsNo).andLESSOR_IDEqualTo(lessorId).andADDED_STATEEqualTo(addedState);
		}
		else
		{
			example.createCriteria().andAPP_SEQEqualTo(appSeq).andLS_NOEqualTo(lsNo).andORG_LESSOR_IDEqualTo(lessorId).andADDED_STATEEqualTo(addedState);
		}

		
		return lS001M3Dao.lessorCountByLsNoIdAppSeq(example);
	}
	
	/**
	 * 查詢出租人資訊
	 * @param lsNo 合約編號
	 * @param lsVer 出租人資料版本
	 * @param lessorId 出租人編號
	 * @return
	 */
	public List<LessorChDTO> serarchLsLessorBylsNoLessorId(String lsNo,String lsVer,String lessorId)throws JSONException
	{
		HashMap<String, String> dataObj=new HashMap<String, String>();
		
		dataObj.put("lsNo", lsNo);
		dataObj.put("lsVer", lsVer);
		dataObj.put("lessorId", lessorId);
		List<LessorChDTO> list=lS001M3Dao.serarchLsLessorBylsNoLessorId(dataObj);
		lessorToJsonStr(list);
		return list;
	}
	
	
	/**
	 * 查詢出租人暫存檔資料
	 * 
	 * @param appSeq 申請單流水號
	 * @param orgLessorId 原出租人編號
	 * @param lsNo 合約編號
	 * @param addState 暫存檔狀態
	 * @return
	 */
	public List<LessorChDTO> searchLsLessorAddByAppSeqState(String appSeq,String orgLessorId,String lsNo,String addState)throws JSONException
	{
		HashMap<String, String> dataObj=new HashMap<String, String>();
		
		dataObj.put("appSeq", appSeq);
		dataObj.put("orgLessorId", orgLessorId);
		dataObj.put("lsNo", lsNo);
		dataObj.put("addState", addState);
		List<LessorChDTO> list=lS001M3Dao.searchLsLessorAddByAppSeqState(dataObj);
		lessorToJsonStr(list);
		return list;
	}
	
	/**
	 * 查詢出租人暫存檔資料
	 * 
	 * @param appSeq 申請單流水號
	 * @param lsNo 合約編號
	 * @param addState 暫存檔狀態
	 * @return
	 */
	public List<TbLsLessor> searchLsLessorAddByAppSeq(String appSeq,String lsNo,String addState)
	{
		HashMap<String, String> dataObj=new HashMap<String, String>();
		
		dataObj.put("appSeq", appSeq);
		dataObj.put("lsNo", lsNo);
		dataObj.put("addState", addState);
		List<TbLsLessor> list=lS001M3Dao.searchLsLessorAddByAppSeq(dataObj);
		return list;
	}
	
	/**
	 * 將出租人資訊轉為Json格式字串
	 * @param lessorList
	 * @throws JSONException
	 */
	public void lessorToJsonStr(List<LessorChDTO> lessorList)throws JSONException
	{
		for(LessorChDTO lessorChDTO:lessorList)
		{
			JSONObject tmp = new JSONObject();
			JSONArray arr = new JSONArray();
			tmp.put("ls_NO", lessorChDTO.getLS_NO());
			tmp.put("ls_VER",lessorChDTO.getLS_VER());
			tmp.put("lessor_ID", lessorChDTO.getLESSOR_ID());
			tmp.put("lessor_NAME", lessorChDTO.getLESSOR_NAME());
			tmp.put("lessor_TYPE", lessorChDTO.getLESSOR_TYPE());
			tmp.put("owner_ID", lessorChDTO.getOWNER_ID());
			tmp.put("owner_NAME", lessorChDTO.getOWNER_NAME());
			tmp.put("owner_NBR", lessorChDTO.getOWNER_NBR());
			tmp.put("owner_ADDR", lessorChDTO.getOWNER_ADDR());
			tmp.put("owner_ADDR_STD", lessorChDTO.getOWNER_ADDR_STD());
			tmp.put("voucher_TYPE", lessorChDTO.getVOUCHER_TYPE());
			tmp.put("house_TAX_NO", lessorChDTO.getHOUSE_TAX_NO());
			tmp.put("income_TAX", lessorChDTO.getINCOME_TAX());
			tmp.put("payment_MODE", lessorChDTO.getPAYMENT_MODE());
			tmp.put("per_MONTH", lessorChDTO.getPER_MONTH());
			tmp.put("business_TAX", lessorChDTO.getBUSINESS_TAX());
			tmp.put("owner_RELATION", lessorChDTO.getOWNER_RELATION());
			tmp.put("residence_ADD", lessorChDTO.getRESIDENCE_ADD());
			tmp.put("residence_ADD_STD", lessorChDTO.getRESIDENCE_ADD_STD());
			tmp.put("ctact_NAME", lessorChDTO.getCTACT_NAME());
			tmp.put("ctact_NBR", lessorChDTO.getCTACT_NBR());
			tmp.put("ctact_ADDR", lessorChDTO.getCTACT_ADDR());
			tmp.put("ctact_ADDR_STD", lessorChDTO.getCTACT_ADDR_STD());
			if(lessorChDTO.getTAX_ADDR_SET()!=null && !"".equals(lessorChDTO.getTAX_ADDR_SET()))
			{
				tmp.put("tax_ADDR_SET", lessorChDTO.getTAX_ADDR_SET());
			}
			tmp.put("doc_ADDR", lessorChDTO.getDOC_ADDR());
			tmp.put("doc_ADDR_STD", lessorChDTO.getDOC_ADDR_STD());
			arr.put(tmp);
			lessorChDTO.setLessorToJson(arr.toString());
		}
	}
	
	/**
	 * 資料異動_出租人相關異動_新增草稿
	 * 
	 * @param lsNo
	 * @param itemSEL
	 * @param tbLsapp
	 * @param map
	 * @return
	 */
	@Transactional
	public String addLessorCh(String lsNo,String lsVer,String itemSEL,TbLsApp tbLsapp, Map<String, String[]> map,String lessorJson,HttpServletRequest request) throws JSONException, ParseException{
		Date date=new Date();
		
		// 1.取得申請單Seq
		String lsAppSeq = uniqueSeqService.getNextLeaseAppNo(lsNo);
		tbLsapp.setAPP_SEQ(lsAppSeq);
		// 2.寫入申請單
		int lsAppCount = lS001M3Dao.addTbLsApp(tbLsapp);
		
		//3.寫入出租人相關資訊
//		if(!"租金抵扣所得稅".equals(itemSEL))
//		{
			
			//3.a寫入出租人編輯暫存檔_原資料
			String[] orgLessorId = map.get("orglessor_ID");
			String[] lssorVer = map.get("orgls_VER");
			for(int i=0;i<orgLessorId.length;i++)
			{
				TbLsLessorAdded tbLsLessorAdded=new TbLsLessorAdded();
				tbLsLessorAdded.setAPP_SEQ(lsAppSeq);
				tbLsLessorAdded.setLS_NO(lsNo);
				tbLsLessorAdded.setLS_VER(map.get("orgls_VER")[i]);
				tbLsLessorAdded.setLESSOR_ID(orgLessorId[i]);
				tbLsLessorAdded.setLESSOR_NAME(map.get("orglessor_NAME")[i]);
				tbLsLessorAdded.setPAYMENT_MODE(map.get("orgPAYMENT_MODE")[i]);
				if(map.get("orgBUSINESS_TAX")[i]!=null && !"".equals(map.get("orgBUSINESS_TAX")[i]))
				{
					tbLsLessorAdded.setBUSINESS_TAX(new BigDecimal(map.get("orgBUSINESS_TAX")[i]));
				}
				tbLsLessorAdded.setADDED_STATE("BEFORE");
				tbLsLessorAdded.setORG_LESSOR_ID(request.getParameter("modifyLessorId"));
				tbLsLessorAdded.setCR_USER(getLoginUser().getUsername());
				tbLsLessorAdded.setCR_TIME(date);
				tbLsLessorAdded.setMD_USER(getLoginUser().getUsername());
				tbLsLessorAdded.setMD_TIME(date);
				
				if("變更印鑑".equals(itemSEL))
				{
					if(orgLessorId[i].equals(request.getParameter("pldg_IN_LESSOR")))
					{
						tbLsLessorAdded.setSEAL_CHG_DATE(DateUtils.paserDate(StringUtils.trim(request.getParameter("rent_CHG_DATE")), "yyyy/MM/dd"));
					}
				}
				
				if("繼受出租人".equals(itemSEL)|| "變更出租人".equals(itemSEL))
				{
					if(orgLessorId[i].equals(request.getParameter("pldg_IN_LESSOR")))
					{
						tbLsLessorAdded.setCHG_RESN(request.getParameter("chg_RESN"));
						tbLsLessorAdded.setCHG_DESC(request.getParameter("chg_DESC"));
						if(request.getParameter("pldg_IN")!=null&&!"".equals(request.getParameter("pldg_IN"))&&!"null".equals(request.getParameter("pldg_IN")))
						{
							tbLsLessorAdded.setPLDG_IN(new BigDecimal(request.getParameter("pldg_IN")));
						}
						if(request.getParameter("pldg_IN_DATE")!=null&&!"".equals(request.getParameter("pldg_IN_DATE"))&&!"null".equals(request.getParameter("pldg_IN_DATE")))
						{
							tbLsLessorAdded.setPLDG_IN_DATE(DateUtils.paserDate(StringUtils.trim(request.getParameter("pldg_IN_DATE")), "yyyy/MM/dd"));
						}
						tbLsLessorAdded.setPLDG_IN_LESSOR(request.getParameter("pldg_IN_LESSOR"));
					}
					else
					{
						tbLsLessorAdded.setCHG_RESN(map.get("orgchg_RESN")[i]);
						tbLsLessorAdded.setCHG_DESC(map.get("orgchg_DESC")[i]);
						if(map.get("orgpldg_IN")!=null)
						{
							if(map.get("orgpldg_IN")[i]!=null&&!"".equals(map.get("orgpldg_IN")[i])&&!"null".equals(map.get("orgpldg_IN")[i]))
							{
								tbLsLessorAdded.setPLDG_IN(new BigDecimal(map.get("orgpldg_IN")[i]));
							}
						}
						if(map.get("orgpldg_IN_DATE")!=null)
						{
							if(map.get("orgpldg_IN_DATE")[i]!=null&&!"".equals(map.get("orgpldg_IN_DATE")[i])&&!"null".equals(map.get("orgpldg_IN_DATE")[i]))
							{
								tbLsLessorAdded.setPLDG_IN_DATE(DateUtils.paserDate(StringUtils.trim(map.get("orgpldg_IN_DATE")[i]), "yyyy/MM/dd"));
							}
						}

						tbLsLessorAdded.setPLDG_IN_LESSOR(map.get("orgpldg_IN_LESSOR")[i]);
					}
				}
				if("租金抵扣所得稅".equals(itemSEL))
				{
					if(orgLessorId[i].equals(request.getParameter("pldg_IN_LESSOR")))
					{
						if(request.getParameter("tax_YEAR_B")!=null && !"".equals(request.getParameter("tax_YEAR_B")))
						{
							tbLsLessorAdded.setTAX_YEAR_B(new BigDecimal(request.getParameter("tax_YEAR_B")));
						}
						
						if(request.getParameter("tax_YEAR_E")!=null && !"".equals(request.getParameter("tax_YEAR_E")))
						{
							tbLsLessorAdded.setTAX_YEAR_E(new BigDecimal(request.getParameter("tax_YEAR_E")));
						}
						
						if(request.getParameter("tax_AMT")!=null && !"".equals(request.getParameter("tax_AMT")))
						{
							tbLsLessorAdded.setTAX_AMT(new BigDecimal(request.getParameter("tax_AMT")));
							tbLsLessorAdded.setPAY_TAX(new BigDecimal(request.getParameter("tax_AMT")));
							
						}
						
						if(request.getParameter("deductYear")!=null && request.getParameter("deductMon")!=null)
						{
							tbLsLessorAdded.setDEDUCT_DATE(new BigDecimal(request.getParameter("deductYear")+request.getParameter("deductMon")));
						}
					}
				}
				int lessorOrgCount = lS001M3Dao.addLsLessorAdded(tbLsLessorAdded);
			}
			
			
			//3.b寫入出租人_編輯資訊暫存檔資訊
			if(!"變更印鑑".equals(itemSEL) && !"租金抵扣所得稅".equals(itemSEL) && !"變更電匯帳戶".equals(itemSEL))
			{
				if (StringUtils.isNotBlank(lessorJson)) {
					String[] lessorArray = StringUtils.split(lessorJson, ";");
					for (String lessorJsonStr : lessorArray) {
						TbLsLessorAdded LessorAdded=getLessorAddedByJson(lessorJsonStr,lsNo,lssorVer[0],lsAppSeq);
						LessorAdded.setADDED_STATE("AFTER");
						LessorAdded.setORG_LESSOR_ID(request.getParameter("pldg_IN_LESSOR"));
						
						if("繼受出租人".equals(itemSEL)|| "變更出租人".equals(itemSEL))
						{
							LessorAdded.setCHG_RESN(request.getParameter("chg_RESN"));
							LessorAdded.setCHG_DESC(request.getParameter("chg_DESC"));
							if(request.getParameter("pldg_IN")!=null&&!"".equals(request.getParameter("pldg_IN"))&&!"null".equals(request.getParameter("pldg_IN")))
							{
								LessorAdded.setPLDG_IN(new BigDecimal(request.getParameter("pldg_IN")));
							}
							if(request.getParameter("pldg_IN_DATE")!=null&&!"".equals(request.getParameter("pldg_IN_DATE"))&&!"null".equals(request.getParameter("pldg_IN_DATE")))
							{
								LessorAdded.setPLDG_IN_DATE(DateUtils.paserDate(StringUtils.trim(request.getParameter("pldg_IN_DATE")), "yyyy/MM/dd"));
							}
							LessorAdded.setPLDG_IN_LESSOR(request.getParameter("pldg_IN_LESSOR"));
						}
						
						int lessorOrgCount = lS001M3Dao.addLsLessorAdded(LessorAdded);
					}
				}
			}
//		}
		
		//4.寫入付款資訊
		if(!"變更印鑑".equals(itemSEL) && !"租金抵扣所得稅".equals(itemSEL))
		{
			//4.1取得付款人相關資訊
			String[] paymentItem = request.getParameterValues("PAYMENT_ITEM");//費用項目
			String[] lessorId = request.getParameterValues("LESSOR_ID");//出租人ID
			String[] LessorName = request.getParameterValues("LESSOR_NAME");//出租人姓名
			String[] recipientId = request.getParameterValues("RECIPIENT_ID");//付款對象
			String[] recipientName = request.getParameterValues("RECIPIENT_NAME");//付款對象姓名
			String[] lessorRelation = request.getParameterValues("LESSOR_RELATION");//與出租人關係
			String[] paymentMode = request.getParameterValues("payment_Mode");//付款方式
			String[] unitCode = request.getParameterValues("UNIT_CODE");//銀行
			String[] subUnitCode = request.getParameterValues("SUB_UNIT_CODE");//分行
			String[] accountNo = request.getParameterValues("ACCOUNT_NO");//帳號
			String[] payAloc = request.getParameterValues("PAY_ALOC");//分攤比
			String[] payAmt = request.getParameterValues("PAY_AMT");//金額
			String[] businessTax = request.getParameterValues("BUSINESS_TAX");//營業稅
			String[] siteLocationId = request.getParameterValues("siteLocationId");//站點
			
			//4.2 寫入付款人相關資訊暫存檔
			if(siteLocationId!=null)
			{
				//取得合約資訊
				LeaseMainDTO  mainDTO=lS001Dao.getLsMainByNo(lsNo, lsVer);
				
				
				for(int i=0;i<siteLocationId.length;i++)
				{
					TbLsLocPaymentAdded tbLsLocPaymentAdded=new TbLsLocPaymentAdded();
					tbLsLocPaymentAdded.setPAYMENT_ITEM(paymentItem[i]);
					tbLsLocPaymentAdded.setLESSOR_ID(lessorId[i]);
					tbLsLocPaymentAdded.setLESSOR_NAME(LessorName[i]);
					tbLsLocPaymentAdded.setRECIPIENT_ID(recipientId[i]);
					tbLsLocPaymentAdded.setRECIPIENT_NAME(recipientName[i]);
					tbLsLocPaymentAdded.setLESSOR_RELATION(lessorRelation[i]);
					tbLsLocPaymentAdded.setPAYMENT_MODE(paymentMode[i]);
					tbLsLocPaymentAdded.setUNIT_CODE(unitCode[i]);
					tbLsLocPaymentAdded.setSUB_UNIT_CODE(subUnitCode[i]);
					tbLsLocPaymentAdded.setACCOUNT_NO(accountNo[i]);
					tbLsLocPaymentAdded.setPAY_ALOC(new BigDecimal(payAloc[i]));
					tbLsLocPaymentAdded.setPAY_AMT(new BigDecimal(payAmt[i]));
				    if(businessTax[i]!=null && !"".equals(businessTax[i]))
				    {
				    	tbLsLocPaymentAdded.setBUSINESS_TAX(new BigDecimal(businessTax[i]));
				    }
				    else
				    {
				    	tbLsLocPaymentAdded.setBUSINESS_TAX(new BigDecimal("0"));
				    }
					tbLsLocPaymentAdded.setLOCATION_ID(siteLocationId[i]);
					tbLsLocPaymentAdded.setEFF_DATE(mainDTO.getLS_S_DATE());
					tbLsLocPaymentAdded.setEND_DATE(mainDTO.getLS_E_DATE());
					tbLsLocPaymentAdded.setAPP_SEQ(lsAppSeq);
					tbLsLocPaymentAdded.setLS_NO(lsNo);
					tbLsLocPaymentAdded.setLS_VER(lsVer);//合約的編號
					tbLsLocPaymentAdded.setORG_LESSOR_ID(request.getParameter("pldg_IN_LESSOR"));
					tbLsLocPaymentAdded.setADDED_STATE("AFTER");
					tbLsLocPaymentAdded.setCR_USER(getLoginUser().getUsername());
					tbLsLocPaymentAdded.setCR_TIME(date);
					tbLsLocPaymentAdded.setMD_USER(getLoginUser().getUsername());
					tbLsLocPaymentAdded.setMD_TIME(date);
					lS001M3Dao.insertLsLocPaymentAdded(tbLsLocPaymentAdded);
				}
			}
		}
		
		//5.寫入合約主檔暫存檔
		if(!"變更印鑑".equals(itemSEL))
		{
			TbLsMainAdded tbLsMainAdded=new TbLsMainAdded();
			tbLsMainAdded.setLS_NO(lsNo);
			tbLsMainAdded.setLS_VER(lsVer);
			tbLsMainAdded.setAPP_SEQ(lsAppSeq);
			tbLsMainAdded.setCR_USER(getLoginUser().getUsername());
			tbLsMainAdded.setCR_TIME(date);
			tbLsMainAdded.setMD_USER(getLoginUser().getUsername());
			tbLsMainAdded.setMD_TIME(date);
			tbLsMainAdded.setADDED_STATE("AFTER");
			
			if(request.getParameter("rent_CHG_DATE")!=null && !"".equals(request.getParameter("rent_CHG_DATE")))
			{
				tbLsMainAdded.setRENT_CHG_DATE(DateUtils.paserDate(StringUtils.trim(request.getParameter("rent_CHG_DATE")), "yyyy/MM/dd"));
			}
			
//			if("租金抵扣所得稅".equals(itemSEL))
//			{
//				if(request.getParameter("tax_YEAR_B")!=null && !"".equals(request.getParameter("tax_YEAR_B")))
//				{
//					tbLsMainAdded.setTAX_YEAR_B(new BigDecimal(request.getParameter("tax_YEAR_B")));
//				}
//				
//				if(request.getParameter("tax_YEAR_E")!=null && !"".equals(request.getParameter("tax_YEAR_E")))
//				{
//					tbLsMainAdded.setTAX_YEAR_E(new BigDecimal(request.getParameter("tax_YEAR_E")));
//				}
//				
//				if(request.getParameter("tax_AMT")!=null && !"".equals(request.getParameter("tax_AMT")))
//				{
//					tbLsMainAdded.setTAX_AMT(new BigDecimal(request.getParameter("tax_AMT")));
//				}
//				
//				if(request.getParameter("deductYear")!=null && request.getParameter("deductMon")!=null)
//				{
//					tbLsMainAdded.setDEDUCT_YEAR(new BigDecimal(request.getParameter("deductYear")+request.getParameter("deductMon")));
//				}
//			}
			lS001M3Dao.insertTbLsMainAdded(tbLsMainAdded);
		}

		//6.回傳申請單號
		return lsAppSeq;
	}
	
	/**
	 * 資料異動_出租人相關異動_更新草稿
	 * @param lsNo
	 * @param lsVer
	 * @param itemSEL
	 * @param appSeq
	 * @param map
	 * @param lessorJson
	 * @param request
	 * @return
	 * @throws JSONException
	 * @throws ParseException
	 */
	@Transactional
	public String updateLessorCh(String lsNo,String lsVer,String itemSEL,String appSeq, Map<String, String[]> map,String lessorJson,HttpServletRequest request) throws JSONException, ParseException
	{
		Date date=new Date();
		
		// 1.寫入申請單
		TbLsAppExample example = new TbLsAppExample();
		example.createCriteria().andAPP_SEQEqualTo(appSeq);
		TbLsApp tbLsApp = new TbLsApp();
		tbLsApp.setMD_TIME(new Date());
		tbLsApp.setMD_USER(getLoginUser().getUsername());
		lS001M3Dao.updateLsApp(tbLsApp, example);

		//2.寫入出租人相關資訊
//		if(!"租金抵扣所得稅".equals(itemSEL))
//		{
			//2.a.1刪除出租人編輯暫存檔_原資料暫存檔
			TbLsLessorAddedExample lessorAddexample=new TbLsLessorAddedExample();
			lessorAddexample.createCriteria().andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("BEFORE");
			int delLessorAdd=lS001M3Dao.delTbLsLessorAdded(lessorAddexample);
			
			//2.a.2寫入出租人編輯暫存檔_原資料
			String[] orgLessorId = map.get("orglessor_ID");
			String[] lssorVer = map.get("orgls_VER");
			for(int i=0;i<orgLessorId.length;i++)
			{
				TbLsLessorAdded tbLsLessorAdded=new TbLsLessorAdded();
				tbLsLessorAdded.setAPP_SEQ(appSeq);
				tbLsLessorAdded.setLS_NO(lsNo);
				tbLsLessorAdded.setLS_VER(map.get("orgls_VER")[i]);
				tbLsLessorAdded.setLESSOR_ID(orgLessorId[i]);
				tbLsLessorAdded.setLESSOR_NAME(map.get("orglessor_NAME")[i]);
				tbLsLessorAdded.setPAYMENT_MODE(map.get("orgPAYMENT_MODE")[i]);
				if(map.get("orgBUSINESS_TAX")[i]!=null && !"".equals(map.get("orgBUSINESS_TAX")[i]))
				{
					tbLsLessorAdded.setBUSINESS_TAX(new BigDecimal(map.get("orgBUSINESS_TAX")[i]));
				}
				tbLsLessorAdded.setADDED_STATE("BEFORE");
				tbLsLessorAdded.setORG_LESSOR_ID(request.getParameter("modifyLessorId"));
				tbLsLessorAdded.setCR_USER(getLoginUser().getUsername());
				tbLsLessorAdded.setCR_TIME(date);
				tbLsLessorAdded.setMD_USER(getLoginUser().getUsername());
				tbLsLessorAdded.setMD_TIME(date);
				
				if("變更印鑑".equals(itemSEL))
				{
					if(orgLessorId[i].equals(request.getParameter("pldg_IN_LESSOR")))
					{
						tbLsLessorAdded.setSEAL_CHG_DATE(DateUtils.paserDate(StringUtils.trim(request.getParameter("rent_CHG_DATE")), "yyyy/MM/dd"));
					}
				}
				
				if("繼受出租人".equals(itemSEL)|| "變更出租人".equals(itemSEL))
				{
					if(orgLessorId[i].equals(request.getParameter("pldg_IN_LESSOR")))
					{
						tbLsLessorAdded.setCHG_RESN(request.getParameter("chg_RESN"));
						tbLsLessorAdded.setCHG_DESC(request.getParameter("chg_DESC"));
						if(request.getParameter("pldg_IN")!=null&&!"".equals(request.getParameter("pldg_IN"))&&!"null".equals(request.getParameter("pldg_IN")))
						{
							tbLsLessorAdded.setPLDG_IN(new BigDecimal(request.getParameter("pldg_IN")));
						}
						if(request.getParameter("pldg_IN_DATE")!=null&&!"".equals(request.getParameter("pldg_IN_DATE"))&&!"null".equals(request.getParameter("pldg_IN_DATE")))
						{
							tbLsLessorAdded.setPLDG_IN_DATE(DateUtils.paserDate(StringUtils.trim(request.getParameter("pldg_IN_DATE")), "yyyy/MM/dd"));
						}
						tbLsLessorAdded.setPLDG_IN_LESSOR(request.getParameter("pldg_IN_LESSOR"));
					}
					else
					{
						tbLsLessorAdded.setCHG_RESN(map.get("orgchg_RESN")[i]);
						tbLsLessorAdded.setCHG_DESC(map.get("orgchg_DESC")[i]);
						if(map.get("orgpldg_IN")!=null)
						{
							if(map.get("orgpldg_IN")[i]!=null&&!"".equals(map.get("orgpldg_IN")[i])&&!"null".equals(map.get("orgpldg_IN")[i]))
							{
								tbLsLessorAdded.setPLDG_IN(new BigDecimal(map.get("orgpldg_IN")[i]));
							}
						}
						if(map.get("orgpldg_IN_DATE")!=null)
						{
							if(map.get("orgpldg_IN_DATE")[i]!=null&&!"".equals(map.get("orgpldg_IN_DATE")[i])&&!"null".equals(map.get("orgpldg_IN_DATE")[i]))
							{
								tbLsLessorAdded.setPLDG_IN_DATE(DateUtils.paserDate(StringUtils.trim(map.get("orgpldg_IN_DATE")[i]), "yyyy/MM/dd"));
							}
						}

						tbLsLessorAdded.setPLDG_IN_LESSOR(map.get("orgpldg_IN_LESSOR")[i]);
					}
				}
				if("租金抵扣所得稅".equals(itemSEL))
				{
					if(orgLessorId[i].equals(request.getParameter("pldg_IN_LESSOR")))
					{
						if(request.getParameter("tax_YEAR_B")!=null && !"".equals(request.getParameter("tax_YEAR_B")))
						{
							tbLsLessorAdded.setTAX_YEAR_B(new BigDecimal(request.getParameter("tax_YEAR_B")));
						}
						
						if(request.getParameter("tax_YEAR_E")!=null && !"".equals(request.getParameter("tax_YEAR_E")))
						{
							tbLsLessorAdded.setTAX_YEAR_E(new BigDecimal(request.getParameter("tax_YEAR_E")));
						}
						
						if(request.getParameter("tax_AMT")!=null && !"".equals(request.getParameter("tax_AMT")))
						{
							tbLsLessorAdded.setTAX_AMT(new BigDecimal(request.getParameter("tax_AMT")));
							tbLsLessorAdded.setPAY_TAX(new BigDecimal(request.getParameter("tax_AMT")));
						}
						
						if(request.getParameter("deductYear")!=null && request.getParameter("deductMon")!=null)
						{
							tbLsLessorAdded.setDEDUCT_DATE(new BigDecimal(request.getParameter("deductYear")+request.getParameter("deductMon")));
						}
					}
				}
				int lessorOrgCount = lS001M3Dao.addLsLessorAdded(tbLsLessorAdded);
			}
			
			//2.b出租人_編輯資訊暫存檔資訊
			if(!"變更印鑑".equals(itemSEL) && !"租金抵扣所得稅".equals(itemSEL) && !"變更電匯帳戶".equals(itemSEL))
			{
				if (StringUtils.isNotBlank(lessorJson)) {
					String[] lessorArray = StringUtils.split(lessorJson, ";");
					for (String lessorJsonStr : lessorArray) {
						TbLsLessorAdded LessorAdded=getLessorAddedByJson(lessorJsonStr,lsNo,lssorVer[0],appSeq);
						LessorAdded.setADDED_STATE("AFTER");
						LessorAdded.setORG_LESSOR_ID(request.getParameter("pldg_IN_LESSOR"));
						
						if("繼受出租人".equals(itemSEL)|| "變更出租人".equals(itemSEL))
						{
							LessorAdded.setCHG_RESN(request.getParameter("chg_RESN"));
							LessorAdded.setCHG_DESC(request.getParameter("chg_DESC"));
							if(request.getParameter("pldg_IN")!=null&&!"".equals(request.getParameter("pldg_IN"))&&!"null".equals(request.getParameter("pldg_IN")))
							{
								LessorAdded.setPLDG_IN(new BigDecimal(request.getParameter("pldg_IN")));
							}
							if(request.getParameter("pldg_IN_DATE")!=null&&!"".equals(request.getParameter("pldg_IN_DATE"))&&!"null".equals(request.getParameter("pldg_IN_DATE")))
							{
								LessorAdded.setPLDG_IN_DATE(DateUtils.paserDate(StringUtils.trim(request.getParameter("pldg_IN_DATE")), "yyyy/MM/dd"));
							}
							LessorAdded.setPLDG_IN_LESSOR(request.getParameter("pldg_IN_LESSOR"));
						}
						
						//2.b.1刪除暫存檔
						TbLsLessorAddedExample lessorAddexampleB=new TbLsLessorAddedExample();
						lessorAddexampleB.createCriteria().andAPP_SEQEqualTo(appSeq).andLESSOR_IDEqualTo(LessorAdded.getLESSOR_ID()).andADDED_STATEEqualTo("AFTER");
						int delLessorAddB=lS001M3Dao.delTbLsLessorAdded(lessorAddexampleB);
						
						//2.b.2寫入暫存檔
						int lessorOrgCount = lS001M3Dao.addLsLessorAdded(LessorAdded);
					}
				}
			}
//		}

		//3.寫入付款資訊
		if(!"變更印鑑".equals(itemSEL) && !"租金抵扣所得稅".equals(itemSEL))
		{
			//3.1取得付款人相關資訊
			String[] paymentItem = request.getParameterValues("PAYMENT_ITEM");//費用項目
			String[] lessorId = request.getParameterValues("LESSOR_ID");//出租人ID
			String[] LessorName = request.getParameterValues("LESSOR_NAME");//出租人姓名
			String[] recipientId = request.getParameterValues("RECIPIENT_ID");//付款對象
			String[] recipientName = request.getParameterValues("RECIPIENT_NAME");//付款對象姓名
			String[] lessorRelation = request.getParameterValues("LESSOR_RELATION");//與出租人關係
			String[] paymentMode = request.getParameterValues("payment_Mode");//付款方式
			String[] unitCode = request.getParameterValues("UNIT_CODE");//銀行
			String[] subUnitCode = request.getParameterValues("SUB_UNIT_CODE");//分行
			String[] accountNo = request.getParameterValues("ACCOUNT_NO");//帳號
			String[] payAloc = request.getParameterValues("PAY_ALOC");//分攤比
			String[] payAmt = request.getParameterValues("PAY_AMT");//金額
			String[] businessTax = request.getParameterValues("BUSINESS_TAX");//營業稅
			String[] siteLocationId = request.getParameterValues("siteLocationId");//站點
			
			//3.2刪除付款人相關資訊暫存檔
			TbLsLocPaymentAddedExample tbLsLocPaymentExample=new TbLsLocPaymentAddedExample();
			tbLsLocPaymentExample.createCriteria().andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER");
			lS001M3Dao.delTbLsLocPaymentAdded(tbLsLocPaymentExample);
			
			
			//3.3 寫入付款人相關資訊暫存檔
			if(siteLocationId!=null)
			{
				//取得合約資訊
				LeaseMainDTO  mainDTO=lS001Dao.getLsMainByNo(lsNo, lsVer);
				
				
				for(int i=0;i<siteLocationId.length;i++)
				{
					TbLsLocPaymentAdded tbLsLocPaymentAdded=new TbLsLocPaymentAdded();
					tbLsLocPaymentAdded.setPAYMENT_ITEM(paymentItem[i]);
					tbLsLocPaymentAdded.setLESSOR_ID(lessorId[i]);
					tbLsLocPaymentAdded.setLESSOR_NAME(LessorName[i]);
					tbLsLocPaymentAdded.setRECIPIENT_ID(recipientId[i]);
					tbLsLocPaymentAdded.setRECIPIENT_NAME(recipientName[i]);
					tbLsLocPaymentAdded.setLESSOR_RELATION(lessorRelation[i]);
					tbLsLocPaymentAdded.setPAYMENT_MODE(paymentMode[i]);
					tbLsLocPaymentAdded.setUNIT_CODE(unitCode[i]);
					tbLsLocPaymentAdded.setSUB_UNIT_CODE(subUnitCode[i]);
					tbLsLocPaymentAdded.setACCOUNT_NO(accountNo[i]);
					tbLsLocPaymentAdded.setPAY_ALOC(new BigDecimal(payAloc[i]));
					tbLsLocPaymentAdded.setPAY_AMT(new BigDecimal(payAmt[i]));
				    if(businessTax[i]!=null && !"".equals(businessTax[i]))
				    {
				    	tbLsLocPaymentAdded.setBUSINESS_TAX(new BigDecimal(businessTax[i]));
				    }
				    else
				    {
				    	tbLsLocPaymentAdded.setBUSINESS_TAX(new BigDecimal("0"));
				    }
					tbLsLocPaymentAdded.setLOCATION_ID(siteLocationId[i]);
					tbLsLocPaymentAdded.setEFF_DATE(mainDTO.getLS_S_DATE());
					tbLsLocPaymentAdded.setEND_DATE(mainDTO.getLS_E_DATE());
					tbLsLocPaymentAdded.setAPP_SEQ(appSeq);
					tbLsLocPaymentAdded.setLS_NO(lsNo);
					tbLsLocPaymentAdded.setLS_VER(lsVer);//合約的編號
					tbLsLocPaymentAdded.setORG_LESSOR_ID(request.getParameter("pldg_IN_LESSOR"));
					tbLsLocPaymentAdded.setADDED_STATE("AFTER");
					tbLsLocPaymentAdded.setCR_USER(getLoginUser().getUsername());
					tbLsLocPaymentAdded.setCR_TIME(date);
					tbLsLocPaymentAdded.setMD_USER(getLoginUser().getUsername());
					tbLsLocPaymentAdded.setMD_TIME(date);
					lS001M3Dao.insertLsLocPaymentAdded(tbLsLocPaymentAdded);
				}
			}
		}
		
		//4.刪除合約主檔暫存檔
		if("變更印鑑".equals(itemSEL))
		{
			TbLsMainAddedExample mainAddedExample=new TbLsMainAddedExample();
			mainAddedExample.createCriteria().andADDED_STATEEqualTo("AFTER").andAPP_SEQEqualTo(appSeq);
			lS001M3Dao.delTbLsMainAdded(mainAddedExample);
			
			//寫入合約主檔暫存檔
			TbLsMainAdded tbLsMainAdded=new TbLsMainAdded();
			tbLsMainAdded.setLS_NO(lsNo);
			tbLsMainAdded.setLS_VER(lsVer);
			tbLsMainAdded.setAPP_SEQ(appSeq);
			tbLsMainAdded.setCR_USER(getLoginUser().getUsername());
			tbLsMainAdded.setCR_TIME(date);
			tbLsMainAdded.setMD_USER(getLoginUser().getUsername());
			tbLsMainAdded.setMD_TIME(date);
			tbLsMainAdded.setADDED_STATE("AFTER");
			
			if(request.getParameter("rent_CHG_DATE")!=null && !"".equals(request.getParameter("rent_CHG_DATE")))
			{
				tbLsMainAdded.setRENT_CHG_DATE(DateUtils.paserDate(StringUtils.trim(request.getParameter("rent_CHG_DATE")), "yyyy/MM/dd"));
			}
			
//			if("租金抵扣所得稅".equals(itemSEL))
//			{
//				if(request.getParameter("tax_YEAR_B")!=null && !"".equals(request.getParameter("tax_YEAR_B")))
//				{
//					tbLsMainAdded.setTAX_YEAR_B(new BigDecimal(request.getParameter("tax_YEAR_B")));
//				}
//				
//				if(request.getParameter("tax_YEAR_E")!=null && !"".equals(request.getParameter("tax_YEAR_E")))
//				{
//					tbLsMainAdded.setTAX_YEAR_E(new BigDecimal(request.getParameter("tax_YEAR_E")));
//				}
//				
//				if(request.getParameter("tax_AMT")!=null && !"".equals(request.getParameter("tax_AMT")))
//				{
//					tbLsMainAdded.setTAX_AMT(new BigDecimal(request.getParameter("tax_AMT")));
//				}
//				
//				if(request.getParameter("deductYear")!=null && request.getParameter("deductMon")!=null)
//				{
//					tbLsMainAdded.setDEDUCT_YEAR(new BigDecimal(request.getParameter("deductYear")+request.getParameter("deductMon")));
//				}
//			}
			
			lS001M3Dao.insertTbLsMainAdded(tbLsMainAdded);
		}

		return appSeq;
	}
	
	/**
	 * 查詢合約主檔暫存檔
	 * @param lsNo
	 * @param appSeq
	 * @return
	 */
	public List<TbLsMainAdded> searchTbLsMainAdded(String lsNo,String appSeq)
	{
		TbLsMainAddedExample example=new TbLsMainAddedExample();
		example.createCriteria().andLS_NOEqualTo(lsNo).andAPP_SEQEqualTo(appSeq);
		return lS001M3Dao.searchTbLsMainAdded(example);
	}
	
	/**
	 * 取得出租人暫存檔資訊
	 * @param lessorJson
	 * @param lsNo
	 * @param lsVer
	 * @param appSeq
	 * @return
	 * @throws JSONException
	 * @throws ParseException
	 */
	public TbLsLessorAdded  getLessorAddedByJson(String lessorJson,String lsNo,String lsVer,String appSeq) throws JSONException, ParseException {
		TbLsLessorAdded lessorAdded = new TbLsLessorAdded();
		JSONObject obj = new JSONObject(lessorJson);
		lessorAdded.setAPP_SEQ(appSeq);
		lessorAdded.setLS_NO(lsNo);		
		lessorAdded.setLS_VER(obj.isNull("ls_VER") ? lsVer : obj.getString("ls_VER"));
		lessorAdded.setLESSOR_TYPE(obj.getString("lessor_TYPE"));
		lessorAdded.setPAYMENT_MODE(obj.getString("payment_MODE"));
		lessorAdded.setRESIDENCE_ADD(obj.isNull("residence_ADD") ? null : obj.getString("residence_ADD"));
		lessorAdded.setRESIDENCE_ADD_STD(obj.isNull("residence_ADD_STD") ? null : obj.getString("residence_ADD_STD"));
		
		lessorAdded.setOWNER_ID(obj.isNull("owner_ID") ? null : obj.getString("owner_ID"));
		lessorAdded.setOWNER_NAME(obj.isNull("owner_NAME") ? null : obj.getString("owner_NAME"));
		lessorAdded.setOWNER_NBR(obj.isNull("owner_NBR") ? null : obj.getString("owner_NBR"));
		lessorAdded.setOWNER_ADDR(obj.isNull("owner_ADDR") ? null : obj.getString("owner_ADDR"));
		lessorAdded.setOWNER_ADDR_STD(obj.isNull("owner_ADDR_STD") ? null : obj.getString("owner_ADDR_STD"));
		lessorAdded.setLESSOR_ID(obj.isNull("lessor_ID") ? null : obj.getString("lessor_ID"));
		lessorAdded.setLESSOR_NAME(obj.isNull("lessor_NAME") ? null : obj.getString("lessor_NAME"));
		lessorAdded.setVOUCHER_TYPE(obj.isNull("voucher_TYPE") ? null : obj.getString("voucher_TYPE"));
		lessorAdded.setHOUSE_TAX_NO(obj.isNull("house_TAX_NO") ? null : obj.getString("house_TAX_NO"));
		lessorAdded.setINCOME_TAX(obj.isNull("income_TAX") ? null : obj.getString("income_TAX"));
		lessorAdded.setPER_MONTH(obj.isNull("per_MONTH") ? null : obj.getString("per_MONTH"));
		if (!obj.isNull("business_TAX") && StringUtils.isNotBlank(obj.getString("business_TAX"))) {
			lessorAdded.setBUSINESS_TAX(new BigDecimal(obj.getString("business_TAX")));
		}
		lessorAdded.setOWNER_RELATION(obj.isNull("owner_RELATION") ? null : obj.getString("owner_RELATION"));
		lessorAdded.setCTACT_NAME(obj.isNull("ctact_NAME") ? null : obj.getString("ctact_NAME"));
		lessorAdded.setCTACT_NBR(obj.isNull("ctact_NBR") ? null : obj.getString("ctact_NBR"));
		lessorAdded.setCTACT_ADDR(obj.isNull("ctact_ADDR") ? null : obj.getString("ctact_ADDR"));
		lessorAdded.setCTACT_ADDR_STD(obj.isNull("ctact_ADDR_STD") ? null : obj.getString("ctact_ADDR_STD"));
		lessorAdded.setTAX_ADDR_SET(obj.isNull("tax_ADDR_SET") ? null : obj.getString("tax_ADDR_SET"));
		lessorAdded.setCR_USER(getLoginUser().getUsername());
		lessorAdded.setCR_TIME(new Date());
		lessorAdded.setMD_USER(getLoginUser().getUsername());
		lessorAdded.setMD_TIME(new Date());

		return lessorAdded;
	}
	

	/**
	 * 撈取付款資訊 By 出租人
	 * 
	 * @param lsNo  合約編號
	 * @param lessorId 出租人編號
	 * @param pItem 費用類型
	 * @return
	 */
	public List<TbLsLocPaymentDTO> getLsLocPaymentByLsNoVerLocIdLessorId(String lsNo,String lessorId,LsPaymentItem[] pItem)
	{
		//取得費用類型
		String[] itemList = new String[pItem.length];
		for(int i=0;i<itemList.length;i++){
			itemList[i]=pItem[i].getItemType();
		}
		
		//查詢條件值
		HashMap<String,Object> object=new HashMap<String,Object>();
		object.put("lsNo", lsNo);
		object.put("lessorId", lessorId);
		object.put("itemType", itemList);
		
		return lS001M3Dao.getLsLocPaymentByLsNoVerLocIdLessorId(object);	
	}
	
	/**
	 * 查詢出租人暫存檔筆數
	 * 
	 * @param appSeq 申請流水號
	 * @param lsNo 合約編號
	 * @param lessorId 出租人編號
	 * @param addedState 暫存檔狀態
	 * @return
	 */
	public int paymentCountByLsNoAppSeq(String appSeq,String lsNo,String lessorId,String addedState){
		TbLsLocPaymentAddedExample example =new TbLsLocPaymentAddedExample();
		example.createCriteria().andAPP_SEQEqualTo(appSeq).andLS_NOEqualTo(lsNo).andORG_LESSOR_IDEqualTo(lessorId).andADDED_STATEEqualTo(addedState);
		return lS001M3Dao.paymentCountByExample(example);
	}
	
	/**
	 * 撈取付款暫存資訊 By 出租人
	 * 
	 * @param lsNo  合約編號
	 * @param lessorId 出租人編號
	 * @param pItem 費用類型
	 * @return
	 */
	public List<TbLsLocPaymentDTO> getLsLocPaymentByLsNoVerLocIdLessorId(String appSeq,String lsNo,String lessorId,LsPaymentItem[] pItem,String addedState)
	{
		//取得費用類型
		String[] itemList = new String[pItem.length];
		for(int i=0;i<itemList.length;i++){
			itemList[i]=pItem[i].getItemType();
		}
		
		//查詢條件值
		HashMap<String,Object> object=new HashMap<String,Object>();
		object.put("appSeq", appSeq);
		object.put("lsNo", lsNo);
		object.put("orglessorId", lessorId);
		object.put("addedState", addedState);
		object.put("itemType", itemList);
		
		return lS001M3Dao.getLsLocPaymentAddedByLsNoVerLocIdLessorId(object);	
	}
	
	/**
	 * 申請
	 * @param osId 申請流水編號
	 * @return String
	 * @throws WorkflowException
	 * @author Nelson lee
	 */
	@Transactional
	public String ls001M3Apply(String appSeq,String lsNo,String userName,String type) throws NomsException {
		String processType = "";
		TbWorkflowCfgExample example = new TbWorkflowCfgExample();
		TbWorkflowCfg cfg = null;
		int count = 0 ;
		try {
			if("M3".equals(type)){//增補協議
				processType = "ExtraLease";
			}else if("T".equals(type)){//解約
				processType = "CancelLease";
			}else if("M".equals(type)){//新約/續約/換約/一解一簽
				TbLsAppExample exampleAppQuery = new TbLsAppExample();
				List<TbLsApp> listApp = new ArrayList<TbLsApp>();
				exampleAppQuery.createCriteria().andAPP_SEQEqualTo(appSeq);
				listApp.addAll(lS001M3Dao.selectTbLsAppByExampleWithBLOBs(exampleAppQuery));
				String lsType = listApp.get(0).getLS_TYPE();
				processType = LsEnumCommon.LsTypeEnum.detectType(lsType);
			}
			List<TbWorkflowCfg> cfgs = workflowCfgDao.findByCondition(example);
			if (cfgs.isEmpty()) {
				return "ProcessType : "+ processType + " 於資料庫未設定！";
			}
			//驗證 同租約編號是否已送審
			count = lsCommonService.getCountAppStatus(lsNo,"1");
			if(count > 0 ){
				return "此合約編號已送審中，無法重複送審 ";
			}
			//update APP_STATUS 
			TbLsApp temp = new TbLsApp();
			TbLsAppExample exampleApp = new TbLsAppExample();	
			exampleApp.createCriteria().andAPP_SEQEqualTo(appSeq);//申請流水號
			temp.setAPP_STATUS("1");//送審
			temp.setMD_USER(userName);
			temp.setMD_TIME(new Date());
		    lS001M3Dao.updateLsApp(temp,exampleApp);
		    cfg = cfgs.get(0);
		    workflowActionService.apply(processType, appSeq, cfg.getPROCESS_NAME());
		} catch (Exception e) {
			throw new  NomsException(e.getMessage());
		}
		return "";
	}
	
	/**
	 * 租約-增補協議簽核
	 * 
	 * @param appSeq  申請單流水號
	 * 
	 * @throws NomsException
	 */
	@Transactional
	public void doExtraLeaseApply(String appSeq ,String userName)throws NomsException {
		//1.找尋申請單
		TbLsApp tbLsApp=lS001M3Dao.searchTbLsApp(appSeq);
		//2.更新申請單狀態
		lsCommonService.updateLsAppStatus(AppStatusEnum.appStatus2.getAppStatus(), LsStatusEnum.lsStatus01.getLsStatus() ,appSeq);
		
		//3.根據不同增補協議修改相關
		if(AddTypeEnum.addType0.getAddType().equals(tbLsApp.getADD_TYPE())){ // 借電變更
			
			doExtraLeaseApplyUpdateAddLsLocElec(appSeq,userName);//處理LsLocElec
			doExtraLeaseApplyUpdateAddTbLsLocation(appSeq,userName);//處理TbLsLocation
			doExtraLeaseApplyUpdateAddTbLsLocPayment(appSeq,userName , "E");//處理TbLsLocPayment
			
			}else if(AddTypeEnum.addType1.getAddType().equals(tbLsApp.getADD_TYPE())){// 租金變更
				
			 if("租金停付".equals(tbLsApp.getADD_ITEM())){
					doExtraLeaseApplyForMain(appSeq,userName);
			 }else if("租金調降".equals(tbLsApp.getADD_ITEM()) || "租金調漲".equals(tbLsApp.getADD_ITEM())){
				doExtraLeaseApplyUpdateAddTbLsLocation(appSeq,userName); //處理TbLsLocation
				doExtraLeaseApplyUpdateAddTbLsLocPayment(appSeq,userName , "R");//處理TbLsLocPayment
			 }else if("管理費變更租金".equals(tbLsApp.getADD_ITEM())){
				doExtraLeaseApplyCha(appSeq , userName);
			}
			
		}else if(AddTypeEnum.addType2.getAddType().equals(tbLsApp.getADD_TYPE())){ // 加裝設備
			doExtraLeaseApplyAddDevice(appSeq,userName);			
		}else if(AddTypeEnum.addType3.getAddType().equals(tbLsApp.getADD_TYPE())){// 出租人及付款對象變更
			
			if("繼受出租人".equals(tbLsApp.getADD_ITEM()) || "變更負責人".equals(tbLsApp.getADD_ITEM())|| "變更出租人".equals(tbLsApp.getADD_ITEM())){
				doExtraLeaseApplyUpdateAddForResponsible(appSeq , userName);
			}else if("出租人更名".equals(tbLsApp.getADD_ITEM())){
				doExtraLeaseApplyUpdateAddForPayBank(appSeq , userName,tbLsApp.getADD_ITEM());
				doExtraLeaseApplyUpdateAddForRename(appSeq,userName);
			}else if("變更印鑑".equals(tbLsApp.getADD_ITEM())){
				doExtraLeaseApplyUpdateAddTbLsLoessor(appSeq , userName , "BEFORE");
			}else if("租金抵扣所得稅".equals(tbLsApp.getADD_ITEM())){
				doExtraLeaseApplyUpdateAddTbLsLoessor(appSeq , userName , "BEFORE");
			}else if("變更電匯帳戶".equals(tbLsApp.getADD_ITEM())){
				doExtraLeaseApplyUpdateAddForPayBank(appSeq , userName,tbLsApp.getADD_ITEM());
			}
		}else if(AddTypeEnum.addType4.getAddType().equals(tbLsApp.getADD_TYPE())){// 異動類
		
			if("租金起算日".equals(tbLsApp.getADD_ITEM())){
				updatePayBeginDate(tbLsApp.getLS_NO(),appSeq,userName);
			}else if("手機回饋".equals(tbLsApp.getADD_ITEM())){
				upPhoneReward(tbLsApp.getLS_NO(), tbLsApp.getAPP_SEQ());
			}else{
				throw new NomsException("Unvaliable ADD_ITEM value:" + tbLsApp.getADD_ITEM()
						+ ", Please ExtraLeaseApply.");
			}
		}else{
			throw new NomsException("Unvaliable ADD_TYPE value:" + tbLsApp.getADD_TYPE()
					+ ", Please check ExtraLeaseApply.");
		}
	}
	
	/**
	 * 增補協議簽核-更新手機回饋資料
	 * @param lsNo 租約編號
	 * @param appSeq 申請單流水號
	 */
	@Transactional
	private void upPhoneReward(String lsNo,String appSeq)
	{
		String ver="";
		//取得合約回櫃檔(排除手機)
		List<TbLsReward> lsRewardList=ls001Service.getLsRewardByNo(lsNo, null);
		//取得合約手機回饋暫存檔
		List<TbLsRewardAddedDTO> dtoList =searchLsRewardAddedByAppSeq(lsNo, appSeq, "AFTER");
		
		//寫入合約回櫃檔
		for(TbLsReward tbLsReward:lsRewardList)
		{
			if(!"2".equals(tbLsReward.getREWARD_TYPE()))
			{
				ver=String.valueOf(Integer.valueOf(tbLsReward.getLS_VER())+1);
				tbLsReward.setLS_VER(ver);
				lS001M3Dao.addTbLsReward(tbLsReward);
			}
		}
		//寫入手機回櫃檔
		for(TbLsRewardAddedDTO tbLsRewardAddedDTO:dtoList)
		{
			
			TbLsReward tbLsReward=new TbLsReward();
			tbLsReward.setLS_NO(tbLsRewardAddedDTO.getLS_NO());
			tbLsReward.setLS_VER(ver);
			tbLsReward.setREWARD_TYPE(tbLsRewardAddedDTO.getREWARD_TYPE());
			tbLsReward.setREWARD_ID(tbLsRewardAddedDTO.getREWARD_ID());
			tbLsReward.setREWARD_QTY(tbLsRewardAddedDTO.getREWARD_QTY());
			tbLsReward.setESTIMATE_AMT(tbLsRewardAddedDTO.getESTIMATE_AMT());
			tbLsReward.setREWARD_RESN(tbLsRewardAddedDTO.getREWARD_RESN());
			tbLsReward.setREWARD_DESC(tbLsRewardAddedDTO.getREWARD_DESC());
			tbLsReward.setCR_TIME(tbLsRewardAddedDTO.getCR_TIME());
			tbLsReward.setCR_USER(tbLsRewardAddedDTO.getCR_USER());
			tbLsReward.setMD_TIME(tbLsRewardAddedDTO.getMD_TIME());
			tbLsReward.setMD_USER(tbLsRewardAddedDTO.getMD_USER());
			lS001M3Dao.addTbLsReward(tbLsReward);
		}
	}
	
	/**
 	* 增補-異動類租金起算日
 	* @param lsNo
 	* @param appSeq
 	* @param userName
 	* @throws NomsException
 	*/
	@Transactional
	public void updatePayBeginDate(String lsNo,String appSeq , String userName) throws NomsException{
		TbLsLocationAddedExample tbLsLocationAddedExample = new TbLsLocationAddedExample();
		tbLsLocationAddedExample.createCriteria().andLS_NOEqualTo(lsNo).andAPP_SEQEqualTo(appSeq)
		          .andADDED_STATEEqualTo("AFTER").andPAY_BEGIN_DATEIsNotNull();
		List<TbLsLocationAdded> tbLsLocationAddedList = new ArrayList<TbLsLocationAdded>();
		tbLsLocationAddedList.addAll(lS001M3Dao.selectTbLsLocationAddedByExampleWithBLOBs(tbLsLocationAddedExample));
		
		if(!tbLsLocationAddedList.isEmpty()){
			Date payBeginDateTemp ;
			String locateionId ;
			TbLsLocationExample upateTbLsLocationAddedExample; //update tb_ls_location
			
			for (TbLsLocationAdded tempObj:tbLsLocationAddedList){
				// update tb_ls_loaction
				
				payBeginDateTemp = tempObj.getPAY_BEGIN_DATE();//取PAY_BEGIN_DATE
				locateionId = tempObj.getLOCATION_ID();//取LOCATION_ID
				
				upateTbLsLocationAddedExample = new TbLsLocationExample();
				//取Max(ls_var)
				String maxLsvar = lS001M3Dao.selectMaLsvarTbLsLocation(lsNo,locateionId).getLS_VER();
				
				//update tb_ls_location
				TbLsLocation temp = new TbLsLocation();
				temp.setPAY_BEGIN_DATE(payBeginDateTemp);
				temp.setMD_USER(userName);
				temp.setMD_TIME(new Date());
				upateTbLsLocationAddedExample.createCriteria().andLS_NOEqualTo(lsNo).andLOCATION_IDEqualTo(locateionId).andLS_VEREqualTo(maxLsvar);
				lS001M3Dao.updateTbLsLocationByExample(temp, upateTbLsLocationAddedExample);
				
				//update tb_ls_site
				TbLsSite tbLsSiteObj = new TbLsSite();
				tbLsSiteObj.setPAY_BEGIN_DATE(payBeginDateTemp);
				tbLsSiteObj.setMD_USER(userName);
				tbLsSiteObj.setMD_TIME(new Date());
				lS001M3Dao.updateTbLsSiteByChangeSiteId(lsNo,maxLsvar,"",locateionId,tbLsSiteObj);
				
			}			
		}
	}
	
	
	/**
	 * 申請前檢核(解約無須)
	 * validator 租金、電費、合約類型(資源分攤)
	 * validator 合約類型(資源分攤)
	 * validator 區域
	 * validator 分攤比
	 * validator 租金、租金押金、電費、電費押金
	 * @param appSeq 合約流水號
	 * @param lsNo 合約編號
	 * @return msg
	 */
	public String validatorApply (String appSeq,String lsNo){
		TbLsApp appObj = new TbLsApp();
		appObj = ls001Service.selectLsAppByAppSeq(appSeq);//search tb_ls_app
		String lsVer = appObj.getLS_VER();
	
		//validator 租金、電費、合約類型(資源分攤)
		TbLsMainKey tbLsMainKey = new TbLsMainKey();
		TbLsMain mainObj = new  TbLsMain();
		tbLsMainKey.setLS_NO(lsNo);
		tbLsMainKey.setLS_VER(lsVer);
		mainObj = lS001Dao.searchLsMainKey(tbLsMainKey);//search tb_ls_main
		String expenseCat = mainObj.getEXPENSE_CAT();//費用項目
		String lsKind = mainObj.getLS_KIND(); //資源分攤 (RESCHG)
		if(StringUtils.isBlank(expenseCat) && !"RESCHG".equals(lsKind)){
			return "申請失敗：租金、電費、合約類型(資源分攤)，有問題";
		}
		
		//validator 合約類型(資源分攤)
		if("RESCHG".equals(lsKind)){
			TbLsResExchAlocExample tbLsResExchAlocExample = new TbLsResExchAlocExample();
			tbLsResExchAlocExample.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(mainObj.getLS_VER());
			int count = lS001M3Dao.countbLsResExchAloctByExample(tbLsResExchAlocExample);
			if(count <= 0){
				return "申請失敗：合約類型(資源分攤)設定不完全";
			}
		}
		
		//validator 區域
		String opDomain = mainObj.getOP_DOMAIN();//main 區域
		Set<String> domainSet = new HashSet<String>(); 
		TbLsLocationExample exampleDomain = new TbLsLocationExample();
		exampleDomain.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		List<TbLsLocation> domainList = new ArrayList<TbLsLocation>();
		domainList.addAll(lS001Dao.getTbLsLocationByExample(exampleDomain));//serch TB_LS_LOCATION
		String domain ="";
		for (TbLsLocation domainObj : domainList){
			domainSet.add(domainObj.getDOMAIN());
		}	
		List<String> tempList1 = new ArrayList<String>(domainSet);
		Collections.sort(tempList1);
		for (int i =0 ; i < tempList1.size() ; i++){
			domain = domain.concat(tempList1.get(i));
		}
		
		
		List<String> tempList2 = new ArrayList<String>();
		String [] tempArray = opDomain.split(",");
		for (String temp : tempArray){
			tempList2.add(temp);
		}
		Collections.sort(tempList2);
		String opDomainTemp = "";
		for (int i =0 ; i < tempList2.size() ; i++){
		    opDomainTemp = opDomainTemp.concat(tempList2.get(i));
		}
		
		if(opDomainTemp.indexOf(domain) == -1){
			return "申請失敗：區域，有問題";
		}
				
		//validator 是否有設定出租資料
		TbLsLessorExample exampleLessor = new TbLsLessorExample(); 
		exampleLessor.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		int countLessor = lS001M3Dao.countLessorByExample(exampleLessor);//count tb_ls_Lessor
		if(countLessor <=  0 ){
			return "申請失敗：無設定出租資料";
		}
		
		//validator 分攤比
		List<TbLsLocPayment>  payalocList = new ArrayList<TbLsLocPayment>();
		payalocList.addAll(lS001Dao.sumPayalocgroupbylocationId(lsNo,lsVer));//serch TB_LS_LOC_PAYMENT
		for (TbLsLocPayment tempObj : payalocList){
			if((new BigDecimal(100).compareTo(tempObj.getPAY_ALOC())!= 0)){
				return "申請失敗：分攤比有問題";
			}
		}
		
		//validator 租金、租金押金、電費、電費押金
		List<TbLsLocPayment> TbLsLocPaymentList ;
		TbLsLocationExample tbLsLocationKey;
		List<TbLsLocElec> tbLsLocElecList;//電費押金
		List<TbLsLocElec> tbLsLocElecListbyE;//電費
		List<TbLsLocation> tbLsLocation;
		List<TbLsLocPayment> locationList = new ArrayList<TbLsLocPayment>();
		Set<String> locationSet = new HashSet<String>();
		TbLsLocPaymentExample exampleLocation = new TbLsLocPaymentExample();
		exampleLocation.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		locationList.addAll(lS001Dao.selectLocPaymentByExample(exampleLocation));//search TB_LS_LOC_PAYMENT
		for (TbLsLocPayment loacPayTmpe : locationList){
			locationSet.add(loacPayTmpe.getLOCATION_ID());//location_id
		}
		
		for(String locationTemp : locationSet){
			tbLsLocElecListbyE = new ArrayList<TbLsLocElec>();//電費
			tbLsLocElecList = new ArrayList<TbLsLocElec>();//電費押金
			TbLsLocPaymentList = new ArrayList<TbLsLocPayment>();
			tbLsLocationKey = new TbLsLocationExample();
			tbLsLocation = new ArrayList<TbLsLocation>();
			tbLsLocationKey.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer).andLOCATION_IDEqualTo(locationTemp);
			TbLsLocPaymentList.addAll(lS001Dao.sumPayamtgroupbyPaymentItem(lsNo,lsVer,locationTemp));//search TB_LS_LOC_PAYMENT 
			tbLsLocation = lS001Dao.getTbLsLocationByExample(tbLsLocationKey);// search TB_LS_LOCATION主檔的金額
			tbLsLocElecList.addAll(lS001Dao.sumElecPledgegroupbylocationId(lsNo,  lsVer , locationTemp,""));//sum 電費押金
			tbLsLocElecListbyE.addAll(lS001Dao.sumElecPledgegroupbylocationId(lsNo,  lsVer , locationTemp,"E"));
			for (TbLsLocPayment loacPayObj : TbLsLocPaymentList){
				if("R".equals(loacPayObj.getPAYMENT_ITEM())){//租金
					if(tbLsLocation.get(0).getRENT_AMT().compareTo(loacPayObj.getPAY_AMT())!=0){
						return "申請失敗：租金有問題";
					}
				}else if ("RD".equals(loacPayObj.getPAYMENT_ITEM())){//租金押金
					if(tbLsLocation.get(0).getPLDG_AMT().compareTo(loacPayObj.getPAY_AMT())!=0){
						return "申請失敗：租金押金有問題";
					}	
				}else if ("E".equals(loacPayObj.getPAYMENT_ITEM()) && !tbLsLocElecListbyE.isEmpty()){//電費
					if(tbLsLocElecListbyE.get(0).getELEC_PLEDGE().compareTo(loacPayObj.getPAY_AMT())!=0){
						return "申請失敗：電費有問題";
					}
				}else if ("ED".equals(loacPayObj.getPAYMENT_ITEM())){//用電押金
					if(tbLsLocElecList.get(0).getELEC_PLEDGE().compareTo(loacPayObj.getPAY_AMT())!=0){
						return "申請失敗：用電押金有問題";
					}
				}
			}
			
		}
		return "";
	}
	
	/**
	 * 解約
	 * @param appSeq
	 * @param userName
	 */
	@Transactional
	public void doCancelLeaseApply(String appSeq,String userName){
		//update 狀態
		lsCommonService.updateLsAppStatus(AppStatusEnum.appStatus4.getAppStatus(), LsStatusEnum.lsStatus09.getLsStatus(),appSeq);
		//更新各項table
		lsCommonService.updateEndDate(appSeq,userName,new Date());
		
	}
	
	/**
	 * 換約
	 * @param appSeq
	 * @param userName
	 * @throws NomsException
	 */
	@Transactional
	public void doChangeLeaseApply(String appSeq,String userName)throws NomsException{
		//update 新合約狀態
		lsCommonService.updateLsAppStatus(AppStatusEnum.appStatus2.getAppStatus(),LsStatusEnum.lsStatus01.getLsStatus(), appSeq);
		//update 舊合約狀態
		String oAppSeq =  lsCommonService.queryOldAppSeq(appSeq);
		lsCommonService.updateLsAppStatus(AppStatusEnum.appStatus4.getAppStatus(), LsStatusEnum.lsStatus09.getLsStatus(),oAppSeq);
		//更新各項table
		lsCommonService.updateEndDate(oAppSeq,userName,lsCommonService.getSndDate(appSeq,-1));
	}
	
	public TbLsLocation initEditElecCh(String appSeq,String lsNo){
		return lS001M3Dao.getEditElecCh(appSeq, lsNo);
	}
	
	/**
	 * update add TbLsLocation
	 * update add TbLsLocPayment
	 * @param appSeq
	 * @param userName
	 * @param type
	 */
	@Transactional
	public void doExtraLeaseApplyUpdateAddTbLsLocation(String appSeq, String userName){
		//處理 TbLsLocationAdded、TbLsLocation//
		//暫存
		TbLsLocationAddedExample tbLsLocationAddedExample = new TbLsLocationAddedExample();
		tbLsLocationAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER");
		List<TbLsLocationAdded> tbLsLocationAddedList = new ArrayList<TbLsLocationAdded>();
		tbLsLocationAddedList.addAll(lS001M3Dao.selectTbLsLocationAddedByExampleWithBLOBs(tbLsLocationAddedExample));
		//主檔
		if(!tbLsLocationAddedList.isEmpty()){
			String maxVer = lS001M3Dao.selectMaLsvarTbLsLocation(tbLsLocationAddedList.get(0).getLS_NO(),"").getLS_VER();
			if(StringUtils.isNotBlank(maxVer)){
			TbLsLocationExample tbLsLocationExample = new TbLsLocationExample();
			tbLsLocationExample.createCriteria().andLS_NOEqualTo(tbLsLocationAddedList.get(0).getLS_NO()).andLS_VEREqualTo(maxVer);
			List<TbLsLocation> tbLsLocationList = new ArrayList<TbLsLocation>();
			tbLsLocationList.addAll(lS001Dao.getTbLsLocationByExample(tbLsLocationExample));
			if(!tbLsLocationList.isEmpty() && StringUtils.isNotBlank(maxVer)){
			//外層 主檔
			for (TbLsLocation tbLsLocationTemp : tbLsLocationList){
				TbLsLocation tbLsLocationForAdd = new TbLsLocation();//Add
				TbLsLocation tbLsLocationForUpdate = new TbLsLocation();//Update
				//內層 暫存檔
				for(TbLsLocationAdded tbLsLocationAddedTemp:tbLsLocationAddedList){
					//比對LOCATION_ID
					if(tbLsLocationAddedTemp.getLOCATION_ID().equals(tbLsLocationTemp.getLOCATION_ID())){
						//LOCATION_ID相同 -新增
						tbLsLocationForAdd = new TbLsLocation();
						BeanUtils.copyProperties(tbLsLocationTemp,tbLsLocationForAdd);//copy 
						tbLsLocationForAdd.setLS_VER(String.valueOf(Integer.valueOf(StringUtils.isNotBlank(maxVer)? maxVer : "-1")+1));
						tbLsLocationForAdd.setEFF_DATE(tbLsLocationAddedTemp.getEFF_DATE());
						tbLsLocationForAdd.setRENT_AMT(tbLsLocationAddedTemp.getRENT_AMT());
						tbLsLocationForAdd.setCR_TIME(new Date());
						tbLsLocationForAdd.setCR_USER(userName);
						tbLsLocationForAdd.setMD_TIME(new Date());
						tbLsLocationForAdd.setMD_USER(userName);
						lS001Dao.saveNewLeaseLocationByTbLsLocation(tbLsLocationForAdd);
						
						//LOCATION_ID相同 -修改
						tbLsLocationForUpdate = new TbLsLocation();
						tbLsLocationForUpdate.setEND_DATE(lsCommonService.getYesterDay(tbLsLocationAddedTemp.getEFF_DATE() !=null ? tbLsLocationAddedTemp.getEFF_DATE() : new Date(), -1));
						tbLsLocationForUpdate.setMD_TIME(new Date());
						tbLsLocationForUpdate.setMD_USER(userName);
						TbLsLocationExample tbLsLocationExampleUpdate = new TbLsLocationExample();
						tbLsLocationExampleUpdate.createCriteria().andLS_NOEqualTo(tbLsLocationTemp.getLS_NO())
							.andLS_VEREqualTo(tbLsLocationTemp.getLS_VER()).andLOCATION_IDEqualTo(tbLsLocationTemp.getLOCATION_ID());
						lS001Dao.updateLeaseLocation(tbLsLocationForUpdate,tbLsLocationExampleUpdate);
					}else{
						//LOCATION_ID不相同 - 新增
						tbLsLocationForAdd = new TbLsLocation();
						BeanUtils.copyProperties(tbLsLocationTemp,tbLsLocationForAdd);//copy 
						tbLsLocationForAdd.setLS_VER(String.valueOf(Integer.valueOf(StringUtils.isNotBlank(maxVer)? maxVer : "-1")+1));
						tbLsLocationForAdd.setEFF_DATE(new Date());
						tbLsLocationForAdd.setCR_TIME(new Date());
						tbLsLocationForAdd.setCR_USER(userName);
						tbLsLocationForAdd.setMD_TIME(new Date());
						tbLsLocationForAdd.setMD_USER(userName);
						lS001Dao.saveNewLeaseLocationByTbLsLocation(tbLsLocationForAdd);
						
						//LOCATION_ID不相同 - 修改
						 tbLsLocationForUpdate = new TbLsLocation();
						tbLsLocationForUpdate.setEND_DATE(lsCommonService.getYesterDay(new Date(), -1));
						tbLsLocationForUpdate.setMD_TIME(new Date());
						tbLsLocationForUpdate.setMD_USER(userName);
						TbLsLocationExample tbLsLocationExampleUpdate = new TbLsLocationExample();
						tbLsLocationExampleUpdate.createCriteria().andLS_NOEqualTo(tbLsLocationTemp.getLS_NO())
							.andLS_VEREqualTo(tbLsLocationTemp.getLS_VER()).andLOCATION_IDEqualTo(tbLsLocationTemp.getLOCATION_ID());
						lS001Dao.updateLeaseLocation(tbLsLocationForUpdate,tbLsLocationExampleUpdate);
						}
					}
			  	}
		    }
		 }	
	}
}
	
	/**
	 * pdateAddTbLsLocPayment
	 * @param appSeq
	 * @param userName
	 * @param type
	 */
	@Transactional
    public void doExtraLeaseApplyUpdateAddTbLsLocPayment(String appSeq, String userName ,String type){
       //處理 TbLsLocPaymentAdded、TbLsLocPayment//
       //暫存
       TbLsLocPaymentAddedExample tbLsLocPaymentAddedExample = new TbLsLocPaymentAddedExample();
       tbLsLocPaymentAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER");
       List<TbLsLocPaymentAdded> tbLsLocPaymentAddedList = new ArrayList<TbLsLocPaymentAdded>();
       tbLsLocPaymentAddedList.addAll(lS001M3Dao.selectTbLsLocPaymentAddedByExample(tbLsLocPaymentAddedExample));
    	if(!tbLsLocPaymentAddedList.isEmpty()){
    		String maxVer = lS001M3Dao.selectTblslocpaymentMaxVer(tbLsLocPaymentAddedList.get(0).getLS_NO(),"").getLS_VER();
    		if(StringUtils.isNotBlank(maxVer)){
    		//主檔
    		TbLsLocPaymentExample tbLsLocPaymentExample = new TbLsLocPaymentExample();
			tbLsLocPaymentExample.createCriteria().andLS_NOEqualTo(tbLsLocPaymentAddedList.get(0).getLS_NO()).andLS_VEREqualTo(maxVer);
			List<TbLsLocPayment> tbLsLocPaymentList = new ArrayList<TbLsLocPayment>();
			tbLsLocPaymentList.addAll(lS001Dao.selectTbLsLocPaymentByExample(tbLsLocPaymentExample));
			if(!tbLsLocPaymentList.isEmpty() && StringUtils.isNotBlank(maxVer)){
			//外層 主檔
			for(TbLsLocPayment tbLsLocPaymentTemp : tbLsLocPaymentList){
				TbLsLocPayment tbLsLocPaymentForAdd = new TbLsLocPayment();//Add
				TbLsLocPayment tbLsLocPaymentForUpdate = new TbLsLocPayment();//Update
				//內層 暫存檔
				for(TbLsLocPaymentAdded tbLsLocPaymentAddedTemp :tbLsLocPaymentAddedList){
					//比對LOCATION_ID
					if(tbLsLocPaymentAddedTemp.getLOCATION_ID().equals(tbLsLocPaymentTemp.getLOCATION_ID())){
						//LOCATION_ID相同-新增
						tbLsLocPaymentForAdd = new TbLsLocPayment();
						BeanUtils.copyProperties(tbLsLocPaymentTemp,tbLsLocPaymentForAdd);//copy
						tbLsLocPaymentForAdd.setLS_VER(String.valueOf(Integer.valueOf(StringUtils.isNotBlank(maxVer)? maxVer: "-1")+1));
						tbLsLocPaymentForAdd.setCR_TIME(new Date());
						tbLsLocPaymentForAdd.setCR_USER(userName);
						tbLsLocPaymentForAdd.setMD_TIME(new Date());
						tbLsLocPaymentForAdd.setMD_USER(userName);

						if("R".equals(type)){
							if(type.equals(tbLsLocPaymentTemp.getPAYMENT_ITEM())){
								tbLsLocPaymentForAdd.setEFF_DATE(tbLsLocPaymentAddedTemp.getEFF_DATE());
								tbLsLocPaymentForAdd.setPAY_AMT(tbLsLocPaymentAddedTemp.getPAY_AMT());
							}else {
								tbLsLocPaymentForAdd.setEFF_DATE(new Date());
							}
						}else if ("E".equals(type)){
							if("E".equals(tbLsLocPaymentTemp.getPAYMENT_ITEM())){
								tbLsLocPaymentForAdd.setEFF_DATE(tbLsLocPaymentAddedTemp.getEFF_DATE());
								tbLsLocPaymentForAdd.setPAY_AMT(tbLsLocPaymentAddedTemp.getPAY_AMT());
								tbLsLocPaymentForAdd.setPAY_ALOC(tbLsLocPaymentAddedTemp.getPAY_ALOC());
							}else {
								tbLsLocPaymentForAdd.setEFF_DATE(new Date());
							}
						}
						//insert
						lS001Dao.insertLsLocPayment(tbLsLocPaymentForAdd);
						
						//LOCATION_ID相同-修改
						 tbLsLocPaymentForUpdate = new TbLsLocPayment();
 						tbLsLocPaymentForUpdate.setEND_DATE(lsCommonService.getYesterDay(tbLsLocPaymentAddedTemp.getEFF_DATE() != null ? tbLsLocPaymentAddedTemp.getEFF_DATE() : new Date() , -1));
 						tbLsLocPaymentForUpdate.setMD_TIME(new Date());
 						tbLsLocPaymentForUpdate.setMD_USER(userName);
 						if("R".equals(type)){//租金
 							if(type.equals(tbLsLocPaymentTemp.getPAYMENT_ITEM())){
 								tbLsLocPaymentForAdd.setEFF_DATE(lsCommonService.getYesterDay(tbLsLocPaymentAddedTemp.getEFF_DATE() !=null ? tbLsLocPaymentAddedTemp.getEFF_DATE():new Date() ,-1));
 							}else{
 								tbLsLocPaymentForAdd.setEFF_DATE(lsCommonService.getYesterDay(new Date() ,-1));
 							}
 						}else if("E".equals(type)){//電費
 							if("E".equals(tbLsLocPaymentTemp.getPAYMENT_ITEM())){
 								tbLsLocPaymentForAdd.setEFF_DATE(lsCommonService.getYesterDay(tbLsLocPaymentAddedTemp.getEFF_DATE() !=null ? tbLsLocPaymentAddedTemp.getEFF_DATE(): new Date() ,-1));
 							}else{
 								tbLsLocPaymentForAdd.setEFF_DATE(lsCommonService.getYesterDay(new Date() ,-1));
 							}
 						}

 						TbLsLocPaymentExample tbLsLocPaymentExampleUpdate = new TbLsLocPaymentExample();
 						tbLsLocPaymentExampleUpdate.createCriteria().andLS_NOEqualTo(tbLsLocPaymentTemp.getLS_NO())
 							.andLS_VEREqualTo(tbLsLocPaymentTemp.getLS_VER()).andLOCATION_IDEqualTo(tbLsLocPaymentTemp.getLOCATION_ID());
 						lS001Dao.updateLsLocPayment(tbLsLocPaymentForUpdate,tbLsLocPaymentExampleUpdate);
					}else{
						//LOCATION_ID不相同-新增
						    tbLsLocPaymentForAdd = new TbLsLocPayment();
							BeanUtils.copyProperties(tbLsLocPaymentTemp,tbLsLocPaymentForAdd);//copy
    						//版號+1
    						tbLsLocPaymentForAdd.setLS_VER(String.valueOf(Integer.valueOf(StringUtils.isNotBlank(maxVer)? maxVer: "-1") +1));
    						tbLsLocPaymentForAdd.setEFF_DATE(new Date());		
    						tbLsLocPaymentForAdd.setCR_TIME(new Date());
    						tbLsLocPaymentForAdd.setCR_USER(userName);
    						tbLsLocPaymentForAdd.setMD_TIME(new Date());
    						tbLsLocPaymentForAdd.setMD_USER(userName);
    						//insert
    						lS001Dao.insertLsLocPayment(tbLsLocPaymentForAdd);
    						
    						////LOCATION_ID不相同-修改
    						tbLsLocPaymentForUpdate = new TbLsLocPayment();
    						tbLsLocPaymentForUpdate.setEND_DATE(lsCommonService.getYesterDay(new Date() , -1));
    						tbLsLocPaymentForUpdate.setMD_TIME(new Date());
    						tbLsLocPaymentForUpdate.setMD_USER(userName);
    						TbLsLocPaymentExample tbLsLocPaymentExampleUpdate = new TbLsLocPaymentExample();
    						tbLsLocPaymentExampleUpdate.createCriteria().andLS_NOEqualTo(tbLsLocPaymentTemp.getLS_NO())
    							.andLS_VEREqualTo(tbLsLocPaymentTemp.getLS_VER()).andLOCATION_IDEqualTo(tbLsLocPaymentTemp.getLOCATION_ID());
    						lS001Dao.updateLsLocPayment(tbLsLocPaymentForUpdate,tbLsLocPaymentExampleUpdate);
							}
						}
					}
				}	
			}
    	}
  	}
    /**
     * 變更印鑑、租金抵扣所得稅
     * @param appSeq
     * @param userName
     * @param addedState BEFROE(變更印鑑)、AFTER(租金抵扣所得稅)
     */
	@Transactional
    public void doExtraLeaseApplyUpdateAddTbLsLoessor(String appSeq , String userName , String addedState){
    	//暫存
    	TbLsLessorAddedExample tbLsLessorAddedExample = new TbLsLessorAddedExample();
    	tbLsLessorAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo(addedState);
    	List<TbLsLessorAdded> tbLsLessorAddedList = new ArrayList<TbLsLessorAdded>();
    	tbLsLessorAddedList.addAll(lS001M3Dao.searchLsLessorAddedByExample(tbLsLessorAddedExample));
    	if(!tbLsLessorAddedList.isEmpty()){
    		String maxVer = lS001M3Dao.getLsLessorByNoVer(tbLsLessorAddedList.get(0).getLS_NO()).get(0).getLS_VER();
    		if(StringUtils.isNotBlank(maxVer)){
    		//主檔
    		TbLsLessorExample tbLsLessorExample = new TbLsLessorExample();
    		tbLsLessorExample.createCriteria().andLS_NOEqualTo(tbLsLessorAddedList.get(0).getLS_NO()).andLS_VEREqualTo(maxVer);
    		List<TbLsLessor> tbLsLessorList = new ArrayList<TbLsLessor>();
    		tbLsLessorList.addAll(lS001M3Dao.searchLsLessorByExample(tbLsLessorExample));
    		
    		//外層 主檔
    		for(TbLsLessor tbLsLessorTemp : tbLsLessorList){
    			TbLsLessor tbLsLessorForadd = new TbLsLessor();
    			BeanUtils.copyProperties(tbLsLessorTemp,tbLsLessorForadd);//copy
    			//內層 暫存檔
    			for(TbLsLessorAdded tbLsLessorAddedTemp : tbLsLessorAddedList){
    				//比對LESSOR_ID && ORG_LESSOR_ID
    				tbLsLessorForadd.setLS_VER(String.valueOf(Integer.valueOf(maxVer)+1));
    				tbLsLessorForadd.setSEAL_CHG_DATE(tbLsLessorAddedTemp.getSEAL_CHG_DATE());
        			if(tbLsLessorAddedTemp.getORG_LESSOR_ID().equals(tbLsLessorTemp.getLESSOR_ID())){
        				//比對LESSOR_ID && ORG_LESSOR_ID 成立 - 新增
        				if(tbLsLessorAddedTemp.getTAX_AMT()!=null)
        				{
        					tbLsLessorForadd.setTAX_AMT(tbLsLessorAddedTemp.getTAX_AMT());
        					tbLsLessorForadd.setPAY_TAX(tbLsLessorAddedTemp.getTAX_AMT());
        				}
        				if(tbLsLessorAddedTemp.getTAX_YEAR_B()!=null)
        				{
        					tbLsLessorForadd.setTAX_YEAR_B(tbLsLessorAddedTemp.getTAX_YEAR_B());
        				}
        				if(tbLsLessorAddedTemp.getTAX_YEAR_E()!=null)
        				{
        					tbLsLessorForadd.setTAX_YEAR_E(tbLsLessorAddedTemp.getTAX_YEAR_E());
        				}
        				if(tbLsLessorAddedTemp.getDEDUCT_DATE()!=null)
        				{
        					tbLsLessorForadd.setDEDUCT_DATE(tbLsLessorAddedTemp.getDEDUCT_DATE());
        				}
        			}else{
        				//比對LESSOR_ID && ORG_LESSOR_ID 不成立 - 新增
        				//原本的Ver
        				String ver = tbLsLessorForadd.getLS_VER();
        				ver = String.valueOf(Integer.valueOf(ver)+1);
        				tbLsLessorForadd.setLS_VER(ver);
        			}
        			tbLsLessorForadd.setCR_TIME(new Date());
    				tbLsLessorForadd.setCR_USER(userName);
    				tbLsLessorForadd.setMD_TIME(new Date());
    				tbLsLessorForadd.setMD_USER(userName);
    				//insert
    				lS001Dao.insertLsLessor(tbLsLessorForadd);
    				}
    			}
    		}
    	}
    }
    
    /**
     * 增補-借電變更  處理 LsLocElecAdded && LsLocElec 
     * @param appSeq
     * @param userName
     */
    @Transactional
    public void doExtraLeaseApplyUpdateAddLsLocElec(String appSeq , String userName){
    	// 處理 LsLocElecAdded && LsLocElec //
    	//暫存檔
    	TbLsLocElecAddedExample tbLsLocElecAddedExample = new TbLsLocElecAddedExample();
    	tbLsLocElecAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER");
    	List<TbLsLocElecAdded> tbLsLocElecAddedList = new ArrayList<TbLsLocElecAdded>();
    	tbLsLocElecAddedList.addAll(lS001M3Dao.selectTbLsLocElecAddedByExample(tbLsLocElecAddedExample));
    	if(!tbLsLocElecAddedList.isEmpty()){
    		String maxVer = lS001M3Dao.selectTblslocelecMAxVerByLsNo(tbLsLocElecAddedList.get(0).getLS_NO(),"").getLS_VER();
    			if(StringUtils.isNotBlank(maxVer)){
    	//主檔
    	TbLsLocElecExample tbLsLocElecExample = new TbLsLocElecExample();
  	    tbLsLocElecExample.createCriteria().andLS_NOEqualTo(tbLsLocElecAddedList.get(0).getLS_NO()).andLS_VEREqualTo(maxVer);
  	  	List<TbLsLocElec> tbLsLocElecList = new ArrayList<TbLsLocElec>();
  	    tbLsLocElecList.addAll(lS001M3Dao.searchtbLsLocElec(tbLsLocElecExample));
  	    if(!tbLsLocElecList.isEmpty() && StringUtils.isNotBlank(maxVer)){
  	    //外層 主檔
    	  for (TbLsLocElec tbLsLocElecTemp : tbLsLocElecList){
  			  TbLsLocElec tbLsLocElecAdd = new TbLsLocElec();//Add
  			  TbLsLocElec tbLsLocElecUpdate = new TbLsLocElec();//Update
  			  //內層 暫存檔
    		  for(TbLsLocElecAdded tbLsLocElecAddedTemp : tbLsLocElecAddedList){
    			  //比對 LOCATION_ID && 比對電表ID
    			  if((tbLsLocElecTemp.getLOCATION_ID().equals(tbLsLocElecAddedTemp.getLOCATION_ID())) &&
      					(tbLsLocElecTemp.getENERGY_METER().equals(tbLsLocElecAddedTemp.getENERGY_METER()))){
  					//比對 LOCATION_ID && 比對電表ID - 新增
  					tbLsLocElecAdd = new TbLsLocElec();
  					BeanUtils.copyProperties(tbLsLocElecTemp,tbLsLocElecAdd);//copy 
  					tbLsLocElecAdd.setLS_NO(String.valueOf(Integer.valueOf(maxVer)+1));
  					tbLsLocElecAdd.setELEC_BEGIN_DATE(tbLsLocElecAddedTemp.getELEC_BEGIN_DATE());
  					tbLsLocElecAdd.setELEC_END_DATE(tbLsLocElecAddedTemp.getELEC_END_DATE());
  					tbLsLocElecAdd.setCR_TIME(new Date());
  					tbLsLocElecAdd.setCR_USER(userName);
  					tbLsLocElecAdd.setMD_TIME(new Date());
  					tbLsLocElecAdd.setMD_USER(userName);
  					lS001M3Dao.insertElecCh(tbLsLocElecAdd);
  								
  					//比對 LOCATION_ID && 比對電表ID - 修改
  				    tbLsLocElecUpdate = new TbLsLocElec();
  					tbLsLocElecUpdate.setELEC_END_DATE(lsCommonService.getYesterDay(tbLsLocElecAddedTemp.getELEC_BEGIN_DATE() != null ? tbLsLocElecAddedTemp.getELEC_BEGIN_DATE() : new Date(),-1));
  					tbLsLocElecUpdate.setMD_TIME(new Date());
  					tbLsLocElecUpdate.setMD_USER(userName);
  					TbLsLocElecExample tbLsLocElecExampleUpdate = new TbLsLocElecExample();
  					tbLsLocElecExampleUpdate.createCriteria().andLS_NOEqualTo(tbLsLocElecTemp.getLS_NO()).andLS_VEREqualTo(tbLsLocElecTemp.getLS_VER())
  						.andLOCATION_IDEqualTo(tbLsLocElecTemp.getLOCATION_ID()).andENERGY_METEREqualTo(tbLsLocElecTemp.getENERGY_METER());
  					lS001M3Dao.updateElecCh(tbLsLocElecUpdate,tbLsLocElecExampleUpdate);
  								
  					// 比對(LOCATION_ID && ! 比對電表ID) ||  比對( ! LOCATION_ID &&  比對電表ID)
    		  }else if (((tbLsLocElecTemp.getLOCATION_ID().equals(tbLsLocElecAddedTemp.getLOCATION_ID())) && !(tbLsLocElecTemp.getENERGY_METER().equals(tbLsLocElecAddedTemp.getENERGY_METER()))) 
    									|| 	(!(tbLsLocElecTemp.getLOCATION_ID().equals(tbLsLocElecAddedTemp.getLOCATION_ID())) && (tbLsLocElecTemp.getENERGY_METER().equals(tbLsLocElecAddedTemp.getENERGY_METER())))){
    			  		// 比對(LOCATION_ID && ! 比對電表ID) ||  比對( ! LOCATION_ID &&  比對電表ID) - 新增
    					tbLsLocElecAdd = new TbLsLocElec();
    				    BeanUtils.copyProperties(tbLsLocElecTemp,tbLsLocElecAdd);//copy 
    					tbLsLocElecAdd.setLS_NO(String.valueOf(Integer.valueOf(maxVer)+1));
    					tbLsLocElecAdd.setELEC_BEGIN_DATE(new Date());
    					tbLsLocElecAdd.setCR_TIME(new Date());
    					tbLsLocElecAdd.setMD_TIME(new Date());
    					tbLsLocElecAdd.setCR_USER(userName);
    					tbLsLocElecAdd.setMD_USER(userName);
    					lS001M3Dao.insertElecCh(tbLsLocElecAdd);

    					// 比對(LOCATION_ID && ! 比對電表ID) ||  比對( ! LOCATION_ID &&  比對電表ID) - 修改
    					tbLsLocElecUpdate = new TbLsLocElec();
    					tbLsLocElecUpdate.setELEC_END_DATE(lsCommonService.getYesterDay(new Date(),-1));
    					tbLsLocElecUpdate.setMD_TIME(new Date());
    					tbLsLocElecUpdate.setMD_USER(userName);
    					TbLsLocElecExample tbLsLocElecExampleUpdate = new TbLsLocElecExample();
    					tbLsLocElecExampleUpdate.createCriteria().andLS_NOEqualTo(tbLsLocElecTemp.getLS_NO()).andLS_VEREqualTo(tbLsLocElecTemp.getLS_VER())
    					.andLOCATION_IDEqualTo(tbLsLocElecTemp.getLOCATION_ID()).andENERGY_METEREqualTo(tbLsLocElecTemp.getENERGY_METER());
    					 lS001M3Dao.updateElecCh(tbLsLocElecUpdate,tbLsLocElecExampleUpdate);
    		  					}
    		  				}
    	  				}
  	    			}	
    			}
    		}	
    	}
    
    /**
     * 增補 - 變更電匯帳號 & 出租人更名
     * @param appSeq
     * @param userName
     */
    @Transactional
    public void doExtraLeaseApplyUpdateAddForPayBank(String appSeq , String userName,String addItem){
    	//處理 TbLsLocPaymentAdded//
		//暫存
		TbLsLocPaymentAddedExample tbLsLocPaymentAddedExample = new TbLsLocPaymentAddedExample();
		tbLsLocPaymentAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER");
		List<TbLsLocPaymentAdded> tbLsLocPaymentAddedList = new ArrayList<TbLsLocPaymentAdded>();
		tbLsLocPaymentAddedList.addAll(lS001M3Dao.selectTbLsLocPaymentAddedByExample(tbLsLocPaymentAddedExample));
		if(!tbLsLocPaymentAddedList.isEmpty()){
			String maxVer = lS001M3Dao.selectTblslocpaymentMaxVer(tbLsLocPaymentAddedList.get(0).getLS_NO(),"").getLS_VER();
			if(StringUtils.isNotBlank(maxVer)){
			//取主TB_LS_MAIN RENT_CHG_DATE
			TbLsMainKey tbLsMainKey = new TbLsMainKey();
			TbLsMain mainObj = new  TbLsMain();
			tbLsMainKey.setLS_NO(tbLsLocPaymentAddedList.get(0).getLS_NO());
			tbLsMainKey.setLS_VER(maxVer);
			mainObj = lS001Dao.searchLsMainKey(tbLsMainKey);
			Date mainRentChgDate = new Date();
			if(mainObj != null){
				 mainRentChgDate = mainObj.getRENT_CHG_DATE() != null ?  mainObj.getRENT_CHG_DATE(): new Date();
			}
			//主檔
			TbLsLocPaymentExample tbLsLocPaymentExample = new TbLsLocPaymentExample();
			tbLsLocPaymentExample.createCriteria().andLS_NOEqualTo(tbLsLocPaymentAddedList.get(0).getLS_NO()).andLS_VEREqualTo(maxVer);
			List<TbLsLocPayment> tbLsLocPaymentList = new ArrayList<TbLsLocPayment>();
			tbLsLocPaymentList.addAll(lS001Dao.selectTbLsLocPaymentByExample(tbLsLocPaymentExample));
			//外層 主檔
			for(TbLsLocPayment tbLsLocPaymentTemp : tbLsLocPaymentList){
				TbLsLocPayment tbLsLocPaymentForAdd = new TbLsLocPayment();//Add
				TbLsLocPayment tbLsLocPaymentForUpdate = new TbLsLocPayment();//Update
				//內層 暫存檔
				for(TbLsLocPaymentAdded tbLsLocPaymentAddedTemp : tbLsLocPaymentAddedList){
					//判斷LESSOR_ID & ORG_LESSOR_ID
					if(tbLsLocPaymentTemp.getLESSOR_ID().equals(tbLsLocPaymentAddedTemp.getORG_LESSOR_ID())){
						//LESSOR_ID & ORG_LESSOR_ID 相同-新增
						tbLsLocPaymentForAdd = new TbLsLocPayment();
						BeanUtils.copyProperties(tbLsLocPaymentTemp,tbLsLocPaymentForAdd);//copy
						tbLsLocPaymentForAdd.setLS_VER(String.valueOf(Integer.valueOf(StringUtils.isNotBlank(maxVer)? maxVer: "-1")+1));
						tbLsLocPaymentForAdd.setUNIT_CODE(tbLsLocPaymentAddedTemp.getUNIT_CODE());//付款銀行
						tbLsLocPaymentForAdd.setSUB_UNIT_CODE(tbLsLocPaymentAddedTemp.getSUB_UNIT_CODE());//付款分行
						tbLsLocPaymentForAdd.setACCOUNT_NO(tbLsLocPaymentAddedTemp.getACCOUNT_NO());//付款帳號
						tbLsLocPaymentForAdd.setEFF_DATE(mainRentChgDate);
						tbLsLocPaymentForAdd.setCR_TIME(new Date());
						tbLsLocPaymentForAdd.setCR_USER(userName);
						tbLsLocPaymentForAdd.setMD_TIME(new Date());
						tbLsLocPaymentForAdd.setMD_USER(userName);
						if("出租人更名".equals(addItem)){
							tbLsLocPaymentForAdd.setLESSOR_NAME(tbLsLocPaymentAddedTemp.getLESSOR_NAME());
						}
						lS001Dao.insertLsLocPayment(tbLsLocPaymentForAdd);
						
						//LESSOR_ID & ORG_LESSOR_ID 相同-修改
						tbLsLocPaymentForUpdate = new TbLsLocPayment();
						tbLsLocPaymentForUpdate.setEND_DATE(lsCommonService.getYesterDay(mainRentChgDate,-1));
						tbLsLocPaymentForUpdate.setMD_TIME(new Date());
						tbLsLocPaymentForUpdate.setMD_USER(userName);
						TbLsLocPaymentExample tbLsLocPaymentExampleUpdate = new TbLsLocPaymentExample();
						tbLsLocPaymentExampleUpdate.createCriteria().andLS_NOEqualTo(tbLsLocPaymentTemp.getLS_NO())
							.andLS_VEREqualTo(tbLsLocPaymentTemp.getLS_VER()).andLOCATION_IDEqualTo(tbLsLocPaymentTemp.getLOCATION_ID());
						lS001Dao.updateLsLocPayment(tbLsLocPaymentForUpdate,tbLsLocPaymentExampleUpdate);
					}else{
						//LESSOR_ID & ORG_LESSOR_ID 不同-新增
						tbLsLocPaymentForAdd = new TbLsLocPayment();
						BeanUtils.copyProperties(tbLsLocPaymentTemp,tbLsLocPaymentForAdd);//copy
						tbLsLocPaymentForAdd.setLS_VER(String.valueOf(Integer.valueOf(StringUtils.isNotBlank(maxVer)? maxVer: "-1")+1));
						tbLsLocPaymentForAdd.setEFF_DATE(new Date());
						tbLsLocPaymentForAdd.setEFF_DATE(mainRentChgDate);
						tbLsLocPaymentForAdd.setCR_TIME(new Date());
						tbLsLocPaymentForAdd.setCR_USER(userName);
						tbLsLocPaymentForAdd.setMD_TIME(new Date());
						tbLsLocPaymentForAdd.setMD_USER(userName);
						lS001Dao.insertLsLocPayment(tbLsLocPaymentForAdd);
						//LESSOR_ID & ORG_LESSOR_ID 不同-修改
						tbLsLocPaymentForUpdate = new TbLsLocPayment();
						tbLsLocPaymentForUpdate.setEND_DATE(lsCommonService.getYesterDay(new Date(),-1));
						tbLsLocPaymentForUpdate.setMD_TIME(new Date());
						tbLsLocPaymentForUpdate.setMD_USER(userName);
						TbLsLocPaymentExample tbLsLocPaymentExampleUpdate = new TbLsLocPaymentExample();
						tbLsLocPaymentExampleUpdate.createCriteria().andLS_NOEqualTo(tbLsLocPaymentTemp.getLS_NO())
							.andLS_VEREqualTo(tbLsLocPaymentTemp.getLS_VER()).andLOCATION_IDEqualTo(tbLsLocPaymentTemp.getLOCATION_ID());
						lS001Dao.updateLsLocPayment(tbLsLocPaymentForUpdate,tbLsLocPaymentExampleUpdate);
						}
					}
				}
			}	
		}
   }
    
    /**
     * 變更出租人姓名 Tb_Ls_Lessor
     * @param appSeq
     * @param userName
     */
    @Transactional
    public void doExtraLeaseApplyUpdateAddForRename(String appSeq , String userName){
    	//暫存
    	TbLsLessorAddedExample tbLsLessorAddedExample = new TbLsLessorAddedExample();
    	tbLsLessorAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER");
    	List<TbLsLessorAdded> TbLsLessorAddedList = new ArrayList<TbLsLessorAdded>();
    	TbLsLessorAddedList.addAll(lS001M3Dao.searchLsLessorAddedByExample(tbLsLessorAddedExample));
    	String maxVer = lS001M3Dao.getLsLessorByNoVer(TbLsLessorAddedList.get(0).getLS_NO()).get(0).getLS_VER();
    	if(!TbLsLessorAddedList.isEmpty() && StringUtils.isNotBlank(maxVer)){
    		//主檔
    		TbLsLessorExample tbLsLessorExample = new TbLsLessorExample();
    		tbLsLessorExample.createCriteria().andLS_NOEqualTo(TbLsLessorAddedList.get(0).getLS_NO()).andLS_VEREqualTo(maxVer);
    		List<TbLsLessor> tbLsLessorList = new ArrayList<TbLsLessor>();
    		tbLsLessorList.addAll(lS001M3Dao.searchLsLessorByExample(tbLsLessorExample));
    		//外層 主檔
    		for(TbLsLessor tbLsLessorTemp : tbLsLessorList){
    			TbLsLessor tbLsLessorForadd = new TbLsLessor();
    			BeanUtils.copyProperties(tbLsLessorTemp,tbLsLessorForadd);//copy
    			//內層 暫存檔
    			for(TbLsLessorAdded tbLsLessorAddedTemp : TbLsLessorAddedList){
    				if(tbLsLessorAddedTemp.getORG_LESSOR_ID().equals(tbLsLessorTemp.getLESSOR_ID())){
            			tbLsLessorForadd.setLS_VER(String.valueOf(Integer.valueOf(maxVer)+1));
        				tbLsLessorForadd.setLESSOR_NAME(tbLsLessorAddedTemp.getLESSOR_NAME());
        			}else{
        				tbLsLessorForadd.setLS_VER(String.valueOf(Integer.valueOf(tbLsLessorTemp.getLS_VER())+1));
        			}
        			tbLsLessorForadd.setCR_TIME(new Date());
    				tbLsLessorForadd.setCR_USER(userName);
    				tbLsLessorForadd.setMD_TIME(new Date());
    				tbLsLessorForadd.setMD_USER(userName);
        			lS001Dao.insertLsLessor(tbLsLessorForadd);
    			}
    		}
    	}    		
    }
    
    /**
     * 增補-變更負責人、繼受出租人、更出租人
     * @param appSeq
     * @param userName
     */
    @Transactional
    public void doExtraLeaseApplyUpdateAddForResponsible(String appSeq , String userName){
    	//處理 TB_LS_Lessor_Added
    	TbLsLessorAddedExample tbLsLessorAddedExample = new TbLsLessorAddedExample();
    	tbLsLessorAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER");
    	List<TbLsLessorAdded> tbLsLessorAddedList = new ArrayList<TbLsLessorAdded>();
    	tbLsLessorAddedList.addAll(lS001M3Dao.searchLsLessorAddedByExample(tbLsLessorAddedExample));
    	if(!tbLsLessorAddedList.isEmpty()){
    		String maxVerForLsLessor = lS001M3Dao.getLsLessorByNoVer(tbLsLessorAddedList.get(0).getLS_NO()).get(0).getLS_VER();
    		if(StringUtils.isNotBlank(maxVerForLsLessor)){
    		TbLsLessorExample tbLsLessorExample = new TbLsLessorExample();
    		tbLsLessorExample.createCriteria().andLS_NOEqualTo(tbLsLessorAddedList.get(0).getLS_NO()).andLS_VEREqualTo(maxVerForLsLessor);
    		List<TbLsLessor> tbLsLessorList = new ArrayList<TbLsLessor>();
    		tbLsLessorList.addAll(lS001M3Dao.searchLsLessorByExample(tbLsLessorExample));
    		if(!tbLsLessorList.isEmpty()){
    		//外層 主檔
    		for(TbLsLessor tbLsLessorTemp : tbLsLessorList){
    			TbLsLessor tbLsLessorForadd = new TbLsLessor();
    			//外層 暫存檔
    			for(TbLsLessorAdded tbLsLessorAddedTemp : tbLsLessorAddedList){
    				if(tbLsLessorAddedTemp.getORG_LESSOR_ID().equals(tbLsLessorTemp.getLESSOR_ID())){
       				 tbLsLessorForadd = new TbLsLessor();
       				BeanUtils.copyProperties(tbLsLessorAddedTemp,tbLsLessorForadd);//temp copy to tb_Ls_Lessor
       				tbLsLessorForadd.setLS_VER(String.valueOf(Integer.valueOf(StringUtils.isNotBlank(maxVerForLsLessor)? maxVerForLsLessor:"-1")+1));  				
    				}else{
       				 tbLsLessorForadd = new TbLsLessor();
       				 BeanUtils.copyProperties(tbLsLessorTemp,tbLsLessorForadd);//原本資料copy
        			     tbLsLessorForadd.setLS_VER(String.valueOf(Integer.valueOf(tbLsLessorTemp.getLS_VER())+1));  
    				}
    			}
    			tbLsLessorForadd.setCR_TIME(new Date());
				tbLsLessorForadd.setCR_USER(userName);
				tbLsLessorForadd.setMD_TIME(new Date());
				tbLsLessorForadd.setMD_USER(userName);
				//insert
    			lS001Dao.insertLsLessor(tbLsLessorForadd);
    			}
    		}
    	}
    	
    	//處理 TbLsLocPaymentAdded、TbLsLocPayment//
		//暫存
		TbLsLocPaymentAddedExample tbLsLocPaymentAddedExample = new TbLsLocPaymentAddedExample();
		tbLsLocPaymentAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER");
		List<TbLsLocPaymentAdded> tbLsLocPaymentAddedList = new ArrayList<TbLsLocPaymentAdded>();
		tbLsLocPaymentAddedList.addAll(lS001M3Dao.selectTbLsLocPaymentAddedByExample(tbLsLocPaymentAddedExample));
		if(!tbLsLocPaymentAddedList.isEmpty()){
			String maxVerForTblslocpayment = lS001M3Dao.selectTblslocpaymentMaxVer(tbLsLocPaymentAddedList.get(0).getLS_NO(),"").getLS_VER();
			if(StringUtils.isNotBlank(maxVerForTblslocpayment)){
			//主檔
			TbLsLocPaymentExample tbLsLocPaymentExample = new TbLsLocPaymentExample();
			tbLsLocPaymentExample.createCriteria().andLS_NOEqualTo(tbLsLocPaymentAddedList.get(0).getLS_NO()).andLS_VEREqualTo(maxVerForTblslocpayment);
			List<TbLsLocPayment> tbLsLocPaymentList = new ArrayList<TbLsLocPayment>();
			tbLsLocPaymentList.addAll(lS001Dao.selectTbLsLocPaymentByExample(tbLsLocPaymentExample));
			//外層 主檔
			for(TbLsLocPayment tbLsLocPaymentTemp : tbLsLocPaymentList){
				TbLsLocPayment tbLsLocPaymentForAdd = new TbLsLocPayment();//Add
				//內層 暫存檔
				for(TbLsLocPaymentAdded tbLsLocPaymentAddedTemp :tbLsLocPaymentAddedList){
					if(tbLsLocPaymentAddedTemp.getLOCATION_ID().equals(tbLsLocPaymentTemp.getLOCATION_ID())){
						tbLsLocPaymentForAdd = new TbLsLocPayment();//Add
						BeanUtils.copyProperties(tbLsLocPaymentAddedTemp,tbLsLocPaymentForAdd);//copy
						tbLsLocPaymentForAdd.setLS_VER(String.valueOf(Integer.valueOf(StringUtils.isNotBlank(maxVerForTblslocpayment)? maxVerForTblslocpayment: "-1")+1));
					}else {
						tbLsLocPaymentForAdd = new TbLsLocPayment();//Add
						BeanUtils.copyProperties(tbLsLocPaymentTemp,tbLsLocPaymentForAdd);//copy
						tbLsLocPaymentForAdd.setLS_VER(String.valueOf(Integer.valueOf(tbLsLocPaymentTemp.getLS_VER())+1));
					}
					tbLsLocPaymentForAdd.setCR_TIME(new Date());
					tbLsLocPaymentForAdd.setCR_USER(userName);
					tbLsLocPaymentForAdd.setMD_TIME(new Date());
					tbLsLocPaymentForAdd.setMD_USER(userName);
					lS001Dao.insertLsLocPayment(tbLsLocPaymentForAdd);
						}
					}
				}		
			}
		}
    }
    
    /**
     * 增補-租金停付
     * @param appSeq
     * @param userName
     */
    @Transactional
    public void doExtraLeaseApplyForMain(String appSeq , String userName){
		TbLsMainAddedExample tbLsMainAddedExample  = new TbLsMainAddedExample();
		tbLsMainAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER");
		List<TbLsMainAdded> tbLsMainAddedList = new ArrayList<TbLsMainAdded>();
		tbLsMainAddedList.addAll(lS001M3Dao.searchTbLsMainAdded(tbLsMainAddedExample));
			if(!tbLsMainAddedList.isEmpty()){
				for(TbLsMainAdded tbLsMainAddedTemp : tbLsMainAddedList){
					LeaseMainDTO leaseMainDTO = new LeaseMainDTO();
					TbLsMainExample tbLsMainExample = new TbLsMainExample();
					leaseMainDTO.setRENT_CHG_DATE(tbLsMainAddedTemp.getRENT_CHG_DATE());
					leaseMainDTO.setRENT_TYPE(tbLsMainAddedTemp.getRENT_TYPE());
					leaseMainDTO.setMD_TIME(new Date());
					leaseMainDTO.setMD_USER(userName);
					tbLsMainExample.createCriteria().andLS_NOEqualTo(tbLsMainAddedTemp.getLS_NO()).andLS_VEREqualTo(tbLsMainAddedTemp.getLS_VER());
					lS001Dao.updatetblsMainNotAll(leaseMainDTO,tbLsMainExample);
				}
		}
    }
    
    
    /**
     * 增補 核可-租金停付
     * @param appSeq
     * @param userName
     */
    @Transactional
    public void doExtraLeaseApplyCha(String appSeq , String userName){
		TbLsMainAddedExample tbLsMainAddedExample  = new TbLsMainAddedExample() ;
		tbLsMainAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER");
		List<TbLsMainAdded> tbLsMainAddedList = new ArrayList<TbLsMainAdded>();
		tbLsMainAddedList.addAll(lS001M3Dao.searchTbLsMainAdded(tbLsMainAddedExample));
		if(!tbLsMainAddedList.isEmpty()){
			for(TbLsMainAdded tbLsMainAddedTemp : tbLsMainAddedList){
				LeaseMainDTO leaseMainDTO = new LeaseMainDTO();
				TbLsMainExample tbLsMainExample = new TbLsMainExample();
				leaseMainDTO.setRENT_CHG_DATE(tbLsMainAddedTemp.getRENT_CHG_DATE());
				leaseMainDTO.setSTOP_B_DATE(tbLsMainAddedTemp.getSTOP_B_DATE());
				leaseMainDTO.setSTOP_E_DATE(tbLsMainAddedTemp.getSTOP_E_DATE());
				leaseMainDTO.setSTOP_MONTH(tbLsMainAddedTemp.getSTOP_MONTH());
				leaseMainDTO.setSTOP_RESN(tbLsMainAddedTemp.getSTOP_RESN());
				leaseMainDTO.setMD_TIME(new Date());
				leaseMainDTO.setMD_USER(userName);
				tbLsMainExample.createCriteria().andLS_NOEqualTo(tbLsMainAddedTemp.getLS_NO()).andLS_VEREqualTo(tbLsMainAddedTemp.getLS_VER());
				lS001Dao.updatetblsMainNotAll(leaseMainDTO,tbLsMainExample);
			}
		}
    }
    
    /**
     * 增補-加裝設備
     * @param appSeq
     * @param userName
     */
    @Transactional
    public void doExtraLeaseApplyAddDevice(String appSeq , String userName){
    	//暫存
    	TbLsLocationAddedExample tbLsLocationAddedExample = new TbLsLocationAddedExample();
    	tbLsLocationAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER");
    	List<TbLsLocationAdded> tbLsLocationAddedList = new ArrayList<TbLsLocationAdded>();
    	tbLsLocationAddedList.addAll(lS001M3Dao.selectTbLsLocationAddedByExampleWithBLOBs(tbLsLocationAddedExample));
    	//主檔
    	if(!tbLsLocationAddedList.isEmpty()){
    		String maxVer = lS001M3Dao.selectMaLsvarTbLsLocation(tbLsLocationAddedList.get(0).getLS_NO(),"").getLS_VER();
			TbLsLocationExample tbLsLocationExample = new TbLsLocationExample();
			tbLsLocationExample.createCriteria().andLS_NOEqualTo(tbLsLocationAddedList.get(0).getLS_NO()).andLS_VEREqualTo(maxVer);
			List<TbLsLocation> tbLsLocationList = new ArrayList<TbLsLocation>();
			tbLsLocationList.addAll(lS001Dao.getTbLsLocationByExample(tbLsLocationExample));
			String maxVerForPayment = lS001M3Dao.selectTblslocpaymentMaxVer(tbLsLocationAddedList.get(0).getLS_NO(),tbLsLocationList.get(0).getLOCATION_ID()).getLS_VER();
			String maxVerForElec = lS001M3Dao.selectTblslocelecMAxVerByLsNo(tbLsLocationAddedList.get(0).getLS_NO(),tbLsLocationList.get(0).getLOCATION_ID()).getLS_VER();
			if(!tbLsLocationList.isEmpty() && StringUtils.isNotBlank(maxVerForPayment) &&  StringUtils.isNotBlank(maxVerForElec)){
			//外層 主檔
			for(TbLsLocation tbLsLocationTemp :tbLsLocationList){
				//內層 暫存檔
				for(TbLsLocationAdded tbLsLocationAddedTemp : tbLsLocationAddedList){
					//比對LOCATION_ID
					if(tbLsLocationTemp.getLOCATION_ID().equals(tbLsLocationAddedTemp.getLOCATION_ID())){
						//處理tb_ls_loc_payment
						//撈取 tb_ls_loc_payment 暫存檔
						TbLsLocPaymentAddedExample tbLsLocPaymentAddedExample = new TbLsLocPaymentAddedExample();
					    tbLsLocPaymentAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER")
					    	.andLOCATION_IDEqualTo(tbLsLocationTemp.getLOCATION_ID()).andLS_VEREqualTo(maxVerForPayment);
					    List<TbLsLocPaymentAdded> tbLsLocPaymentAddedList = new ArrayList<TbLsLocPaymentAdded>();
					    tbLsLocPaymentAddedList.addAll(lS001M3Dao.selectTbLsLocPaymentAddedByExample(tbLsLocPaymentAddedExample));
					    
					    if(!tbLsLocPaymentAddedList.isEmpty()){
					    	TbLsLocPayment TbLsLocPaymentForAdd = new TbLsLocPayment();
					    	TbLsLocPayment TbLsLocPaymentForupdate = new TbLsLocPayment();
					    	 for(TbLsLocPaymentAdded tbLsLocPaymentAddedTemp : tbLsLocPaymentAddedList){
							    	//LOCATION_ID 相符-新增 tb_ls_loc_payment
					    		 	TbLsLocPaymentForAdd = new TbLsLocPayment();
					    		 	BeanUtils.copyProperties(tbLsLocPaymentAddedTemp,TbLsLocPaymentForAdd);//copy
					    		 	TbLsLocPaymentForAdd.setLS_VER(String.valueOf(Integer.valueOf(maxVerForPayment)+1));
					    		 	TbLsLocPaymentForAdd.setCR_TIME(new Date());
					    		 	TbLsLocPaymentForAdd.setCR_USER(userName);
					    		 	TbLsLocPaymentForAdd.setMD_TIME(new Date());
					    		 	TbLsLocPaymentForAdd.setMD_USER(userName);
					    		 	lS001Dao.insertLsLocPayment(TbLsLocPaymentForAdd);
					    		 	
								    //LOCATION_ID 相符-修改 tb_ls_loc_payment
					    		 	TbLsLocPaymentForupdate = new TbLsLocPayment();
					    		 	//BeanUtils.copyProperties(tbLsLocPaymentAddedTemp,TbLsLocPaymentForupdate);//copy
					    		 	TbLsLocPaymentForupdate.setEND_DATE(lsCommonService.getYesterDay(tbLsLocPaymentAddedTemp.getEFF_DATE() != null ? tbLsLocPaymentAddedTemp.getEFF_DATE() : new Date(),-1));
					    		 	TbLsLocPaymentForupdate.setMD_TIME(new Date());
					    		 	TbLsLocPaymentForupdate.setMD_USER(userName);
					    		 	TbLsLocPaymentExample tbLsLocPaymentExampleUpdate = new TbLsLocPaymentExample();
			 						tbLsLocPaymentExampleUpdate.createCriteria().andLS_NOEqualTo(tbLsLocPaymentAddedTemp.getLS_NO())
			 							.andLS_VEREqualTo(tbLsLocPaymentAddedTemp.getLS_VER()).andLOCATION_IDEqualTo(tbLsLocPaymentAddedTemp.getLOCATION_ID());
			 						lS001Dao.updateLsLocPayment(TbLsLocPaymentForupdate,tbLsLocPaymentExampleUpdate);
							    }
					    }
					   
						//處理tb_ls_loc_elec
					    //處理tb_ls_loc_elec 暫存檔
					    TbLsLocElecAddedExample tbLsLocElecAddedExample = new TbLsLocElecAddedExample();
				    	tbLsLocElecAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq).andADDED_STATEEqualTo("AFTER")
				    		.andLOCATION_IDEqualTo(tbLsLocationTemp.getLOCATION_ID()).andLS_VEREqualTo(maxVerForElec);
				    	List<TbLsLocElecAdded> tbLsLocElecAddedList = new ArrayList<TbLsLocElecAdded>();
				    	tbLsLocElecAddedList.addAll(lS001M3Dao.selectTbLsLocElecAddedByExample(tbLsLocElecAddedExample));
				    	if(!tbLsLocElecAddedList.isEmpty()){
				    		TbLsLocElec tbLsLocElecForAdd = new TbLsLocElec();
				    		TbLsLocElec tbLsLocElecForUpdate = new TbLsLocElec();
				    		for(TbLsLocElecAdded tbLsLocElecAddedTemp : tbLsLocElecAddedList){
				    			//LOCATION_ID 相符-新增 tb_ls_loc_elec
				    			tbLsLocElecForAdd = new TbLsLocElec();
				    			BeanUtils.copyProperties(tbLsLocElecAddedTemp,tbLsLocElecForAdd);//copy
				    			tbLsLocElecForAdd.setLS_VER(String.valueOf((Integer.valueOf(maxVerForElec)+1)));
				    			tbLsLocElecForAdd.setCR_TIME(new Date());
				    			tbLsLocElecForAdd.setCR_USER(userName);
				    			tbLsLocElecForAdd.setMD_TIME(new Date());
				    			tbLsLocElecForAdd.setMD_USER(userName);
				    			lS001M3Dao.insertElecCh(tbLsLocElecForAdd);
				    			
							    //LOCATION_ID 相符-修改 tb_ls_loc_elec
				    			tbLsLocElecForUpdate = new TbLsLocElec();
				    			//BeanUtils.copyProperties(tbLsLocElecAddedTemp,tbLsLocElecForUpdate);//copy
				    			tbLsLocElecForUpdate.setELEC_END_DATE(lsCommonService.getYesterDay(tbLsLocElecAddedTemp.getELEC_BEGIN_DATE() != null ? tbLsLocElecAddedTemp.getELEC_BEGIN_DATE() : new Date(),-1));
				    			tbLsLocElecForUpdate.setMD_TIME(new Date());
				    			tbLsLocElecForUpdate.setMD_USER(userName);
				    			
				    			TbLsLocElecExample tbLsLocElecExampleUpdate = new TbLsLocElecExample();
		    					tbLsLocElecExampleUpdate.createCriteria().andLS_NOEqualTo(tbLsLocElecAddedTemp.getLS_NO()).andLS_VEREqualTo(tbLsLocElecAddedTemp.getLS_VER())
		    							.andLOCATION_IDEqualTo(tbLsLocElecAddedTemp.getLOCATION_ID()).andENERGY_METEREqualTo(tbLsLocElecAddedTemp.getENERGY_METER());
		    					 lS001M3Dao.updateElecCh(tbLsLocElecForUpdate,tbLsLocElecExampleUpdate);
				    		}
				    	}
					}else{
						//比對LOCATION_ID不相符
						//處理tb_ls_loc_payment
						TbLsLocPaymentExample tbLsLocPaymentExample = new TbLsLocPaymentExample();
						tbLsLocPaymentExample.createCriteria().andLS_NOEqualTo(tbLsLocationTemp.getLS_NO())
							.andLS_VEREqualTo(maxVerForPayment).andLOCATION_IDEqualTo(tbLsLocationTemp.getLOCATION_ID());
						List<TbLsLocPayment> tbLsLocPaymentList = new ArrayList<TbLsLocPayment>();
						tbLsLocPaymentList.addAll(lS001Dao.selectTbLsLocPaymentByExample(tbLsLocPaymentExample));
						if(!tbLsLocPaymentList.isEmpty()){
							TbLsLocPayment tbLsLocPaymentForAdd = new TbLsLocPayment();
							TbLsLocPayment tbLsLocPaymentForupdate = new TbLsLocPayment();
							for(TbLsLocPayment tbLsLocPaymentTemp :tbLsLocPaymentList){
								//新增
								tbLsLocPaymentForAdd = new TbLsLocPayment();
								BeanUtils.copyProperties(tbLsLocPaymentTemp,tbLsLocPaymentForAdd);//copy
								tbLsLocPaymentForAdd.setLS_VER(String.valueOf(Integer.valueOf(maxVerForPayment)+1));
								tbLsLocPaymentForAdd.setEFF_DATE(new Date());
								tbLsLocPaymentForAdd.setCR_TIME(new Date());
								tbLsLocPaymentForAdd.setCR_USER(userName);
								tbLsLocPaymentForAdd.setMD_TIME(new Date());
								tbLsLocPaymentForAdd.setMD_USER(userName);
								lS001Dao.insertLsLocPayment(tbLsLocPaymentForAdd);
								
								//修改
								tbLsLocPaymentForupdate = new TbLsLocPayment();
								//BeanUtils.copyProperties(tbLsLocPaymentTemp,tbLsLocPaymentForupdate);//copy
								tbLsLocPaymentForupdate.setEND_DATE(lsCommonService.getYesterDay(new Date(), -1));
								tbLsLocPaymentForupdate.setMD_TIME(new Date());
								tbLsLocPaymentForupdate.setMD_USER(userName);
								TbLsLocPaymentExample tbLsLocPaymentExampleUpdate = new TbLsLocPaymentExample();
	    						tbLsLocPaymentExampleUpdate.createCriteria().andLS_NOEqualTo(tbLsLocPaymentTemp.getLS_NO())
	    							.andLS_VEREqualTo(tbLsLocPaymentTemp.getLS_VER()).andLOCATION_IDEqualTo(tbLsLocPaymentTemp.getLOCATION_ID());
	    						lS001Dao.updateLsLocPayment(tbLsLocPaymentForupdate,tbLsLocPaymentExampleUpdate);
							}
						}
						//處理tb_ls_loc_elec
						TbLsLocElecExample tbLsLocElecExample = new TbLsLocElecExample();
				  	    tbLsLocElecExample.createCriteria().andLS_NOEqualTo(tbLsLocationTemp.getLS_NO())
				  	    	.andLS_VEREqualTo(maxVerForElec).andLOCATION_IDEqualTo(tbLsLocationTemp.getLOCATION_ID());
				  	  	List<TbLsLocElec> tbLsLocElecList = new ArrayList<TbLsLocElec>();
				  	    tbLsLocElecList.addAll(lS001M3Dao.searchtbLsLocElec(tbLsLocElecExample));
				  	    if(!tbLsLocElecList.isEmpty()){
				  	    	TbLsLocElec tbLsLocElecForAdd = new TbLsLocElec();
				  	    	TbLsLocElec tbLsLocElecForupate = new TbLsLocElec();
				  	    	for(TbLsLocElec tbLsLocElecTemp : tbLsLocElecList){
				  	    		//新增
				  	    	    tbLsLocElecForAdd = new TbLsLocElec();
				  	    	    BeanUtils.copyProperties(tbLsLocElecTemp,tbLsLocElecForAdd);//copy
				  	    	    tbLsLocElecForAdd.setLS_VER(String.valueOf(Integer.valueOf(tbLsLocElecTemp.getLS_VER()+1)));
				  	    	    tbLsLocElecForAdd.setELEC_BEGIN_DATE(new Date());
				  	    	    tbLsLocElecForAdd.setCR_TIME(new Date());
				  	    	    tbLsLocElecForAdd.setCR_USER(userName);
				  	    	    tbLsLocElecForAdd.setMD_TIME(new Date());
				  	    	  	tbLsLocElecForAdd.setMD_USER(userName);
				  	    	  	lS001M3Dao.insertElecCh(tbLsLocElecForAdd);
				  	    		//修改
				  	    		tbLsLocElecForupate = new TbLsLocElec();
				  	    		//BeanUtils.copyProperties(tbLsLocElecTemp,tbLsLocElecForupate);//copy
				  	    		tbLsLocElecForupate.setELEC_END_DATE(lsCommonService.getYesterDay(new Date(), -1));
				  	    		tbLsLocElecForupate.setMD_TIME(new Date());
				  	    		tbLsLocElecForupate.setMD_USER(userName);	
				  	    		TbLsLocElecExample tbLsLocElecExampleUpdate = new TbLsLocElecExample();
		    					tbLsLocElecExampleUpdate.createCriteria().andLS_NOEqualTo(tbLsLocElecTemp.getLS_NO()).andLS_VEREqualTo(tbLsLocElecTemp.getLS_VER())
    					 		  .andLOCATION_IDEqualTo(tbLsLocElecTemp.getLOCATION_ID()).andENERGY_METEREqualTo(tbLsLocElecTemp.getENERGY_METER());
    					        lS001M3Dao.updateElecCh(tbLsLocElecForupate,tbLsLocElecExampleUpdate);
				  	    	}
				  	    }
					}
				}
			}
		   }
    	}
    				
    }
    
    /**
     * 儲存時檢核付款人資訊
     * @param request
     * @param lsNo
     * @return
     */
    public String locPaymentValidator (HttpServletRequest request,String lsNo){
		String[] paymentItem = request.getParameterValues("PAYMENT_ITEM");//費用項目
		String[] payAmt = request.getParameterValues("PAY_AMT");//金額
		String[] siteLocationId = request.getParameterValues("siteLocationId");//站點
		
		String msg="";
		
		List<String> list=new ArrayList<String>();
		for(int i=0;i<siteLocationId.length;i++)
		{
			if(!list.contains(siteLocationId[i]))
			{
				list.add(siteLocationId[i]);
			}
		}
		
		HashMap<String, Object > dataObj=new HashMap<String, Object >();
		
		for(int i=0;i<list.size();i++)
		{
			String errorMsg="";
			dataObj.put("lsNo",lsNo);
			dataObj.put("lessorId",request.getParameter("pldg_IN_LESSOR"));
			dataObj.put("locId",list.get(i));
			dataObj.put("item", "R");
			int amtR=lS001M3Dao.queryPaymentAmtByLocItem(dataObj);
			dataObj.put("item", "RD");
			int amtRD=lS001M3Dao.queryPaymentAmtByLocItem(dataObj);
			dataObj.put("item", "E");
			int amtE=lS001M3Dao.queryPaymentAmtByLocItem(dataObj);
			dataObj.put("item", "ED");
			int amtED=lS001M3Dao.queryPaymentAmtByLocItem(dataObj);
			
			int payAmtR=0;
			int payAmtRD=0; 
			int payAmtE=0; 
			int payAmtED=0; 
			
			for(int j=0;j<siteLocationId.length;j++)
			{
				if(siteLocationId[j].equals(list.get(i)))
				{
					if(paymentItem[j].equals("R"))
					{
						payAmtR+=Integer.valueOf(payAmt[j]);
					}
					else if(paymentItem[j].equals("RD"))
					{
						payAmtRD+=Integer.valueOf(payAmt[j]);
					}
					else if(paymentItem[j].equals("E"))
					{
						payAmtE+=Integer.valueOf(payAmt[j]);
					}
					else if(paymentItem[j].equals("ED"))
					{
						payAmtED+=Integer.valueOf(payAmt[j]);
					}
				}
			}
			
			if(payAmtR!=amtR)
			{
				errorMsg+="租金總和需為"+amtR+",";
			}
			if(payAmtRD!=amtRD)
			{
				errorMsg+="租金押金總和需為"+amtRD+",";
			}
			if(payAmtE!=amtE)
			{
				errorMsg+="電費總和需為"+amtE+",";
			}
			if(payAmtED!=amtED)
			{
				errorMsg+="電費押金總和需為"+amtED+",";
			}
			
			if(!"".equals(errorMsg))
			{
				msg+="站點"+list.get(i)+":"+errorMsg+"\n";
			}
			
		}
		return msg;
    }
    
    
    /**
     * 續約 核可
     * 更新各項table
     * TB_LS_MAIN
   	 * TB_LS_LESSOR
     * TB_LS_REWARD
     * TB_LS_LOCATION
     * TB_LS_LOC_ELEC
     * TB_LS_LOC_PAYMENT
     * TB_LS_SITE
     * TB_LS_RES_EXCH
     * TB_LS_RES_EXCH_ALOC
     * @param appSeq
     * @param userName
     */
    @Transactional
    public void doContinueLeaseApply(String appSeq,String userName) throws NomsException {
    	//更新狀態
    	lsCommonService.updateLsAppStatus(AppStatusEnum.appStatus2.getAppStatus(),
				LsStatusEnum.lsStatus01.getLsStatus(), appSeq);
    	//更新各項table 暫存 -> 主檔

    	//TB_LS_MAIN
    	TbLsMainAddedExample tbLsMainAddedExample = new TbLsMainAddedExample();
    	tbLsMainAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq);
    	List<TbLsMainAdded> tbLsMainAddedList = new ArrayList<TbLsMainAdded>();
    	tbLsMainAddedList.addAll(lS001M3Dao.searchTbLsMainAdded(tbLsMainAddedExample));
    	if(!tbLsMainAddedList.isEmpty()){
    		String maxVerForTbLsMain = lS001M3Dao.selectLsMainBylsNoVerMax(tbLsMainAddedList.get(0).getLS_NO()).get(0).getLS_VER();
    		if(StringUtils.isNotBlank(maxVerForTbLsMain)){
    			TbLsMain TbLsMainForAdd = new TbLsMain();
    			for(TbLsMainAdded tbLsMainAddedTemp : tbLsMainAddedList){
    				TbLsMainForAdd = new TbLsMain();
    				BeanUtils.copyProperties(tbLsMainAddedTemp,TbLsMainForAdd);//copy
    				TbLsMainForAdd.setLS_VER(String.valueOf((Integer.valueOf(maxVerForTbLsMain)+1)));
    				TbLsMainForAdd.setCR_TIME(new Date());
    				TbLsMainForAdd.setCR_USER(userName);
    				TbLsMainForAdd.setMD_TIME(new Date());
    				TbLsMainForAdd.setMD_USER(userName);
    				lS001Dao.insertLsMain(TbLsMainForAdd);
        		}
    		}
    	}
    	
    	//TB_LS_LESSOR
    	TbLsLessorAddedExample tbLsLessorAddedExample = new TbLsLessorAddedExample();
    	tbLsLessorAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq);
    	List<TbLsLessorAdded>  tbLsLessorAddedList = new ArrayList<TbLsLessorAdded>(); 
    	tbLsLessorAddedList.addAll(lS001M3Dao.searchLsLessorAddedByExample(tbLsLessorAddedExample));
    	if(!tbLsLessorAddedList.isEmpty()){
    		String maxVerForLsLessor = lS001M3Dao.getLsLessorByNoVer(tbLsLessorAddedList.get(0).getLS_NO()).get(0).getLS_VER();
    		  if(StringUtils.isNotBlank(maxVerForLsLessor)){
    			  TbLsLessor TbLsLessorForAdd = new TbLsLessor(); 
    			  for(TbLsLessorAdded tbLsLessorAddedTemp : tbLsLessorAddedList){
    				  TbLsLessorForAdd = new TbLsLessor(); 
    				  BeanUtils.copyProperties(tbLsLessorAddedTemp,TbLsLessorForAdd);//copy
    				  TbLsLessorForAdd.setLS_VER(String.valueOf((Integer.valueOf(maxVerForLsLessor)+1)));
    				  TbLsLessorForAdd.setMD_TIME(new Date());
    				  TbLsLessorForAdd.setMD_USER(userName);
    				  TbLsLessorForAdd.setCR_TIME(new Date());
    				  TbLsLessorForAdd.setCR_USER(userName);
    				  lS001Dao.insertLsLessor(TbLsLessorForAdd);
    			  }
    		  }
    	}

    	//TB_LS_REWARD
    	List<TbLsRewardAdded> tbLsRewardAddedList = new ArrayList<TbLsRewardAdded>();
    	TbLsRewardAddedExample tbLsRewardAddedExample = new TbLsRewardAddedExample();
    	tbLsRewardAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq);
    	tbLsRewardAddedList.addAll(lS001M3Dao.selectTbLsRewardAddedByExample(tbLsRewardAddedExample));
    	if(!tbLsRewardAddedList.isEmpty()){
    		String maxVerForTbLsRewar = lS001M3Dao.selectTbLsRewardMaxVerbyLsNo(tbLsRewardAddedList.get(0).getLS_NO()).get(0).getLS_VER();
    		if(StringUtils.isNotBlank(maxVerForTbLsRewar)){
    			TbLsReward TbLsRewarForAdd = new TbLsReward();
    			for(TbLsRewardAdded TbLsRewardAddedTemp : tbLsRewardAddedList){
    				TbLsRewarForAdd = new TbLsReward();
    				BeanUtils.copyProperties(TbLsRewardAddedTemp,TbLsRewarForAdd);//copy
    				TbLsRewarForAdd.setLS_VER(String.valueOf((Integer.valueOf(maxVerForTbLsRewar)+1)));
    				TbLsRewarForAdd.setCR_TIME(new Date());
    				TbLsRewarForAdd.setCR_USER(userName);
    				TbLsRewarForAdd.setMD_TIME(new Date());
    				TbLsRewarForAdd.setMD_USER(userName);
    				lS001Dao.insertLsReward(TbLsRewarForAdd);
    			}
    			
    		}
    	}

    	//TB_LS_LOCATION
    	List<TbLsLocationAdded> tbLsLocationAddedList = new ArrayList<TbLsLocationAdded>();
    	TbLsLocationAddedExample tbLsLocationAddedExample = new TbLsLocationAddedExample();
    	tbLsLocationAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq);
    	tbLsLocationAddedList.addAll(lS001M3Dao.selectTbLsLocationAddedByExampleWithBLOBs(tbLsLocationAddedExample));
    	if(!tbLsLocationAddedList.isEmpty()){
    		String maxVerForTbLsLocation = lS001M3Dao.selectMaLsvarTbLsLocation(tbLsLocationAddedList.get(0).getLS_NO(),"").getLS_VER();
    		if(StringUtils.isNotBlank(maxVerForTbLsLocation)){
    			TbLsLocation tbLsLocationForAdd =new TbLsLocation();
    			for(TbLsLocationAdded tbLsLocationAddedTemp : tbLsLocationAddedList){
    				tbLsLocationForAdd =new TbLsLocation();
    				BeanUtils.copyProperties(tbLsLocationAddedTemp,tbLsLocationForAdd);//copy
    				tbLsLocationForAdd.setLS_VER(String.valueOf((Integer.valueOf(maxVerForTbLsLocation)+1)));
    				tbLsLocationForAdd.setMD_TIME(new Date());
    				tbLsLocationForAdd.setMD_USER(userName);
    				tbLsLocationForAdd.setCR_TIME(new Date());
    				tbLsLocationForAdd.setCR_USER(userName);
    				lS001Dao.saveNewLeaseLocationByTbLsLocation(tbLsLocationForAdd);
    			}
    		}
    	}
	
    	//TB_LS_LOC_ELEC
    	List<TbLsLocElecAdded> tbLsLocElecAddedList = new ArrayList<TbLsLocElecAdded>();
    	TbLsLocElecAddedExample tbLsLocElecAddedExample = new TbLsLocElecAddedExample();
    	tbLsLocElecAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq);
    	tbLsLocElecAddedList.addAll(lS001M3Dao.selectTbLsLocElecAddedByExample(tbLsLocElecAddedExample));
    	if(!tbLsLocElecAddedList.isEmpty()){
    		String maxVerForLsLocElec = lS001M3Dao.getLsLocElecMaxVerbyLsNo(tbLsLocElecAddedList.get(0).getLS_NO()).getLS_VER();
    		if(StringUtils.isNotBlank(maxVerForLsLocElec)){
    			TbLsLocElec tbLsLocElecForAdd = new TbLsLocElec();
    			for(TbLsLocElecAdded tbLsLocElecAddedTemp :  tbLsLocElecAddedList){
    				tbLsLocElecForAdd = new TbLsLocElec();
    				BeanUtils.copyProperties(tbLsLocElecAddedTemp,tbLsLocElecForAdd);//copy
    				tbLsLocElecForAdd.setLS_VER(String.valueOf((Integer.valueOf(maxVerForLsLocElec)+1)));
    				tbLsLocElecForAdd.setMD_TIME(new Date());
    				tbLsLocElecForAdd.setMD_USER(userName);
    				tbLsLocElecForAdd.setCR_TIME(new Date());
    				tbLsLocElecForAdd.setCR_USER(userName);
    				lS001Dao.insertLsLocElec(tbLsLocElecForAdd);
    			}
    		}
    	}

    	//TB_LS_LOC_PAYMENT
    	 TbLsLocPaymentAddedExample tbLsLocPaymentAddedExample = new TbLsLocPaymentAddedExample();
         tbLsLocPaymentAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq);
         List<TbLsLocPaymentAdded> tbLsLocPaymentAddedList = new ArrayList<TbLsLocPaymentAdded>();
         tbLsLocPaymentAddedList.addAll(lS001M3Dao.selectTbLsLocPaymentAddedByExample(tbLsLocPaymentAddedExample));
         if(!tbLsLocPaymentAddedList.isEmpty()){
        	 String maxVerForTblslocpayment = lS001M3Dao.selectTblslocpaymentMaxVer(tbLsLocPaymentAddedList.get(0).getLS_NO(),"").getLS_VER();
        	 if(StringUtils.isNotBlank(maxVerForTblslocpayment)){
        		 TbLsLocPayment tbLsLocPaymentForAdd = new TbLsLocPayment();
        		 for(TbLsLocPaymentAdded tbLsLocPaymentAddedTemp : tbLsLocPaymentAddedList){
        			 tbLsLocPaymentForAdd = new TbLsLocPayment();
        			 BeanUtils.copyProperties(tbLsLocPaymentAddedTemp,tbLsLocPaymentForAdd);//copy
        			 tbLsLocPaymentForAdd.setLS_VER(String.valueOf((Integer.valueOf(maxVerForTblslocpayment)+1)));
        			 tbLsLocPaymentForAdd.setMD_TIME(new Date());
        			 tbLsLocPaymentForAdd.setMD_USER(userName);
        			 tbLsLocPaymentForAdd.setCR_TIME(new Date());
        			 tbLsLocPaymentForAdd.setCR_USER(userName);
        			 lS001Dao.insertLsLocPayment(tbLsLocPaymentForAdd);
        		 }
        	 }
         }

    	//TB_LS_SITE
         List<TbLsSiteAdded> tbLsSiteAddedList = new ArrayList<TbLsSiteAdded>();
         TbLsSiteAddedExample tbLsSiteAddedExample = new TbLsSiteAddedExample();
         tbLsSiteAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq);
         tbLsSiteAddedList.addAll(lS001M3Dao.selectTbSiteAddedByExample(tbLsSiteAddedExample));
         if(!tbLsSiteAddedList.isEmpty()){
        	 String maxVerForTbSite = lS001M3Dao.getTbLsSiteMaxLsVer(tbLsSiteAddedList.get(0).getLS_NO()).getLS_VER();
        	 if(StringUtils.isNotBlank(maxVerForTbSite)){
        		 TbLsSite tbLsSiteForAdd = new TbLsSite();
        		 for(TbLsSiteAdded tbLsSiteAddedTemp : tbLsSiteAddedList){
        			 tbLsSiteForAdd = new TbLsSite();
        			 BeanUtils.copyProperties(tbLsSiteAddedTemp,tbLsSiteForAdd);//copy
        			 tbLsSiteForAdd.setLS_VER(String.valueOf((Integer.valueOf(maxVerForTbSite)+1)));
        			 tbLsSiteForAdd.setMD_TIME(new Date());
        			 tbLsSiteForAdd.setMD_USER(userName);
        			 tbLsSiteForAdd.setCR_TIME(new Date());
        			 tbLsSiteForAdd.setCR_USER(userName);
        			 lS001Dao.insertLsSite(tbLsSiteForAdd);
        		 }
        	 }
         }
         
    	//TB_LS_RES_EXCH
         TbLsResExchAddedExample tbLsResExchAddedExample = new TbLsResExchAddedExample();
         List<TbLsResExchAdded> TbLsResExchAddedList= new ArrayList<TbLsResExchAdded>();
         tbLsResExchAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq);
         TbLsResExchAddedList.addAll(lS001M3Dao.selectTbLsResExchAddedByExample(tbLsResExchAddedExample));
         if(!TbLsResExchAddedList.isEmpty()){
        	String maxVerForTbLsResExch = lS001Dao.getTbLsResExchMaxLsVer(TbLsResExchAddedList.get(0).getLS_NO()).getLS_VER();
        	if(StringUtils.isNotBlank(maxVerForTbLsResExch)){
        		TbLsResExch TbLsResExchForAdd = new TbLsResExch();
        		for(TbLsResExchAdded tbLsResExchAddedTemp : TbLsResExchAddedList){
        			TbLsResExchForAdd = new TbLsResExch();
        			BeanUtils.copyProperties(tbLsResExchAddedTemp,TbLsResExchForAdd);//copy
        			TbLsResExchForAdd.setLS_VER(String.valueOf((Integer.valueOf(maxVerForTbLsResExch)+1)));
        			TbLsResExchForAdd.setMD_TIME(new Date());
        			TbLsResExchForAdd.setMD_USER(userName);
        			TbLsResExchForAdd.setCR_TIME(new Date());
        			TbLsResExchForAdd.setCR_USER(userName);
        			lS001Dao.insertTbLsResExch(TbLsResExchForAdd);
        		}
        	}
         }
    	//TB_LS_RES_EXCH_ALOC
         List<TbLsResExchAlocAdded> tbLsResExchAlocAddedList = new ArrayList<TbLsResExchAlocAdded>();
         TbLsResExchAlocAddedExample tbLsResExchAlocAddedExample = new TbLsResExchAlocAddedExample();
         tbLsResExchAlocAddedExample.createCriteria().andAPP_SEQEqualTo(appSeq);
         tbLsResExchAlocAddedList.addAll(lS001M3Dao.selectTbLsResExchAlocAddedByExample(tbLsResExchAlocAddedExample));
         if(!tbLsResExchAlocAddedList.isEmpty()){
        	 String maxVerForTbLsResExchAloc =  lS001Dao.getTbLsResExchAlocMaxVer(tbLsResExchAlocAddedList.get(0).getLS_NO()).getLS_VER();
        	 if(StringUtils.isNotBlank(maxVerForTbLsResExchAloc)){
        		 TbLsResExchAloc tbLsResExchAlocForAdd = new TbLsResExchAloc();
        		 for(TbLsResExchAlocAdded tbLsResExchAlocAddedTemp : tbLsResExchAlocAddedList){
        			 tbLsResExchAlocForAdd = new TbLsResExchAloc();
        			 BeanUtils.copyProperties(tbLsResExchAlocAddedTemp,tbLsResExchAlocForAdd);//copy
        			 tbLsResExchAlocForAdd.setLS_VER(String.valueOf((Integer.valueOf(maxVerForTbLsResExchAloc)+1)));
        			 tbLsResExchAlocForAdd.setMD_TIME(new Date());
        			 tbLsResExchAlocForAdd.setMD_USER(userName);
        			 tbLsResExchAlocForAdd.setCR_TIME(new Date());
        			 tbLsResExchAlocForAdd.setCR_USER(userName);
        			 lS001Dao.insertTbLsResExchAloc(tbLsResExchAlocForAdd);
        		 }
        	 }
         }
    }
}
