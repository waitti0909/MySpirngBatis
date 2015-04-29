package com.foya.noms.service.ls;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.hornetq.utils.json.JSONArray;
import org.hornetq.utils.json.JSONException;
import org.hornetq.utils.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpModelExample;
import com.foya.dao.mybatis.model.TbComEqpType;
import com.foya.dao.mybatis.model.TbComEqpTypeExample;
import com.foya.dao.mybatis.model.TbLsApp;
import com.foya.dao.mybatis.model.TbLsAppExample;
import com.foya.dao.mybatis.model.TbLsAppForm;
import com.foya.dao.mybatis.model.TbLsAppFormExample;
import com.foya.dao.mybatis.model.TbLsAvgRent;
import com.foya.dao.mybatis.model.TbLsAvgRentExample;
import com.foya.dao.mybatis.model.TbLsClause;
import com.foya.dao.mybatis.model.TbLsElecRange;
import com.foya.dao.mybatis.model.TbLsLessor;
import com.foya.dao.mybatis.model.TbLsLessorAdded;
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
import com.foya.dao.mybatis.model.TbLsResExchAloc;
import com.foya.dao.mybatis.model.TbLsResExchAlocAdded;
import com.foya.dao.mybatis.model.TbLsResExchAlocAddedExample;
import com.foya.dao.mybatis.model.TbLsReward;
import com.foya.dao.mybatis.model.TbLsRewardAdded;
import com.foya.dao.mybatis.model.TbLsRewardExample;
import com.foya.dao.mybatis.model.TbLsSite;
import com.foya.dao.mybatis.model.TbLsSiteAdded;
import com.foya.dao.mybatis.model.TbLsSiteAddedExample;
import com.foya.dao.mybatis.model.TbLsSiteExample;
import com.foya.dao.mybatis.model.TbLsTrmn;
import com.foya.dao.mybatis.model.TbLsTrmnElec;
import com.foya.dao.mybatis.model.TbLsTrmnElecExample;
import com.foya.dao.mybatis.model.TbLsTrmnKey;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.exception.DataNotFoundException;
import com.foya.exception.NomsException;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.ls.AppFormDao;
import com.foya.noms.dao.ls.LS001Dao;
import com.foya.noms.dao.ls.LS001M3Dao;
import com.foya.noms.dao.ls.LsClauseDao;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.org.DomainDao;
import com.foya.noms.dao.system.LookupDao;
import com.foya.noms.dto.ls.LeaseDTO;
import com.foya.noms.dto.ls.LeaseDomainMoneyDTO;
import com.foya.noms.dto.ls.LeaseLocElecDTO;
import com.foya.noms.dto.ls.LeaseLocationDTO;
import com.foya.noms.dto.ls.LeaseMainDTO;
import com.foya.noms.dto.ls.LeaseTerminalDTO;
import com.foya.noms.dto.ls.SiteAlocDetailDTO;
import com.foya.noms.dto.ls.TbLsLocPaymentDTO;
import com.foya.noms.dto.ls.TbLsResExchDTO;
import com.foya.noms.dto.pay.TbLsLocElecDTO;
import com.foya.noms.dto.pay.TbLsSiteDTO;
import com.foya.noms.enums.LsEnumCommon;
import com.foya.noms.enums.LsEnumCommon.LsTypeEnum;
import com.foya.noms.enums.LsPaymentItem;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.org.DeptService;
import com.foya.noms.service.org.DomainService;
import com.foya.noms.service.system.LookupService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.util.DateUtils;

/**
 * The Class LS001Service.
 */
@Service
public class LS001Service extends BaseService {

	/** The l s001 dao. */
	@Autowired
	private LS001Dao lS001Dao;
	
	/** The lookup dao. */
	@Autowired
	private LookupDao lookupDao;
	
	/** The app form dao. */
	@Autowired
	private AppFormDao appFormDao;
	
	/** The dept dao. */
	@Autowired
	private DeptDao deptDao;
	
	/** The domain dao. */
	@Autowired
	private DomainDao domainDao;
	
	/** The clause dao. */
	@Autowired
	private LsClauseDao clauseDao;
	
	/** The unique seq service. */
	@Autowired
	private UniqueSeqService uniqueSeqService;
	
	/** The site aloc service. */
	@Autowired
	private SiteAlocService siteAlocService;
	
	/** The personnel dao. */
	@Autowired
	private PersonnelDao personnelDao;
	
	/** The domain service. */
	@Autowired
	private DomainService domainService;
	
	/** The Dept service. */
	@Autowired
	private DeptService deptService;
	
	/** The lookup service. */
	@Autowired
	private LookupService lookupService;
	/** The lscommon service. */
	@Autowired
	private LsCommonService lsCommonService;
	
	@Autowired
	private LS001M3Dao ls001m3Dao;
	
	/**
	 * 取得進入Ls001M頁面所需資訊.
	 *
	 * @param btnType the btn type
	 * @param lsNo the ls no
	 * @param lsVer the ls ver
	 * @param modelMap the model map
	 * @throws JSONException the JSON exception
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public void initLs001M(String btnType, String lsNo, 
			String lsVer, Map<String, Object> modelMap) throws JSONException, JsonGenerationException, JsonMappingException, IOException {
		String appUserDeptId = "";
		String appUserDeptName = "";
		String appUserNo = "";
		String appUserName = "";
		String keepDeptId = "";
		String lsType = "";
		Date oriLsSDate = null;
		Date oriLsEDate = null;
		LeaseMainDTO lease = null;
		String appSeq =(String) modelMap.get("appSeq");
		String extendType = "";
		//洽談人員的區域
		List<TbOrgDomain> subDomainList = new ArrayList<TbOrgDomain>();
		//新約
		if ("new".equals(btnType)) {
			lsType=LsEnumCommon.LsTypeEnum.NewLease.getLsType();
			subDomainList = domainDao.getDomainIdTreeByStandard(domainService.getTopDomainId(getLoginUser().getDomain().getID()));
			appUserDeptId = getLoginUser().getDeptId();
			keepDeptId = getLoginUser().getDeptId();
			appUserDeptName = getLoginUser().getDeptName();
			TbAuthPersonnelExample personExample = new TbAuthPersonnelExample();
			personExample.createCriteria().andPSN_IDEqualTo(new BigDecimal(getLoginUser().getUserId()));
			appUserNo = personnelDao.getPersonnelsByExample(personExample).get(0).getPSN_NO();
			appUserName = getLoginUser().getChName();
		} else if ("edit".equals(btnType) || "view".equals(btnType) || "change".equals(btnType) ||"ReSign".equals(btnType)
				||"continue".equals(btnType)) {
		//修改 or 檢視合約
			boolean isContinueFlag = false;
			if(("edit".equals(btnType)||"view".equals(btnType)) && StringUtils.isNotBlank(appSeq)){
				TbLsApp app =  ls001m3Dao.searchTbLsApp(appSeq);
				if(app==null){
					throw new NomsException(appSeq+" not found .");
				}
				lsType = app.getLS_TYPE();
				extendType = app.getADD_DATA();
				if( LsEnumCommon.LsTypeEnum.ContinueLease.getLsType().equals( app.getLS_TYPE())){
					//續約
					isContinueFlag = true;
					TbLsMainAddedExample example =new TbLsMainAddedExample();
					example.createCriteria().andAPP_SEQEqualTo(appSeq);
					List<TbLsMainAdded> mainAddList =ls001m3Dao.searchTbLsMainAdded(example);
					if(mainAddList.size()==0){
						throw new NomsException(appSeq+" not found in Main Add");
					}
					lease=new LeaseMainDTO();
					org.springframework.beans.BeanUtils.copyProperties(mainAddList.get(0),lease); 
					
					
				}
			}
			if(lease==null){ 	
				lease = getLsMainByNo(lsNo,( "edit".equals(btnType) ||"view".equals(btnType))? lsVer :null);
			}
			
			if(lease==null){
				throw new NomsException("app:"+appSeq+" and LSNO:"+lsNo+" not found in Main Add");
			}
			
			
			TbOrgDept orgDept = deptDao.getDeptById(lease.getAPP_USER_DEPT());
			TbOrgDomain domain = domainDao.getDomainById(orgDept.getDOMAIN());
			subDomainList = domainDao.getDomainIdTreeByStandard(domainService.getTopDomainId(domain.getID()));
			
			appUserDeptId = lease.getAPP_USER_DEPT();
			keepDeptId = lease.getKEEP_DEPT();
			appUserDeptName = lease.getAPP_USER_DEPT_NAME();
			appUserName = lease.getAPP_USER_NAME();
			appUserNo = lease.getAPP_USER();
			
		    if("change".equals(btnType)){
				lsType = LsEnumCommon.LsTypeEnum.ChangeLease.getLsType();
			}else if("ReSign".equals(btnType)){
				lsType = LsEnumCommon.LsTypeEnum.ReSignLease.getLsType();
			}else if("continue".equals(btnType)){
				lsType = LsEnumCommon.LsTypeEnum.ContinueLease.getLsType();
				
			}
			lease.setLS_TYPE(lsType);
			lease.setCONTI_EXTEND_TYPE(extendType);
			List<TbLsReward> rewardList = new ArrayList<>();
			List<TbLsLessor> lessorList= new ArrayList<>();
			
			if(isContinueFlag){
				rewardList = lS001Dao.getLsRewardAddedByAppSeq(appSeq, null);
				
				lessorList = lS001Dao.getLsLessorAddedByAppSeq(appSeq);
			}else{
				rewardList = getLsRewardByNo(lease.getLS_NO(), "edit".equals(btnType) ? lease.getLS_VER() :null);
				
				lessorList =  getLsLessorByNo(lease.getLS_NO(), "edit".equals(btnType) ? lease.getLS_VER() :null);
			}
			
			lease.setLessors(lessorList);
			lease.setRewardList(rewardList);// 回饋門號 與 回饋手機
			
			
			//出租人轉 JSon
			parseLessorToJsonStr(lease);
			// 增修條文轉 JSon
			parseClauseToJsonStr(lease);
			
			oriLsSDate = lease.getLS_S_DATE();
			oriLsEDate = lease.getLS_E_DATE();
			
			if (!"edit".equals(btnType) && !"view".equals(btnType)) {
			
				lease.setORI_LS_NO(lease.getLS_NO());
			
				//續約 承租起日為原合約承租迄日＋1
				if ("continue".equals(btnType) || StringUtils.equals(lsType, LsEnumCommon.LsTypeEnum.ContinueLease.getLsType())) {
					Date defaultSDate = org.apache.commons.lang.time.DateUtils.addDays(oriLsEDate, 1);
					lease.setLS_S_DATE(defaultSDate);
					Long diffDays =(oriLsEDate.getTime() - oriLsSDate.getTime())/(24*60*60*1000);  
					log.info("新原始終止日: " + org.apache.commons.lang.time.DateUtils.addDays(defaultSDate, diffDays.intValue()));
					modelMap.put("CONTI_E_DATE", org.apache.commons.lang.time.DateUtils.addDays(defaultSDate, diffDays.intValue()-1));
				}
				//一解一簽 新約的承租起日不可小於原約的解約日
				if ("ReSign".equals(btnType)) {
					TbLsTrmnKey key = new TbLsTrmnKey();
					key.setLS_NO(lsNo);
					key.setLS_VER(lsVer);
					TbLsTrmn tbLsTrmn = lS001Dao.searchLsTrmn(key);
					lease.setLS_S_DATE(org.apache.commons.lang.time.DateUtils.addDays(tbLsTrmn.getTRMN_DATE(), 1));
				}
				
				if (!"continue".equals(btnType)){
					lease.setLS_NO("");
					lease.setLS_VER("");
				}
				
				if ("change".equals(btnType)) {
					lease.setLS_S_DATE(null);
				}
				lease.setLS_E_DATE(null);
				lease.setLS_STATUS(LsEnumCommon.LsStatusEnum.lsStatus0.getLsStatus());
			}
			
			modelMap.put("lease", lease);
			
			// List<LeaseDomainMoneyDTO>
			List<LeaseDomainMoneyDTO> lsDomainMoneyList = getLsDomainMoneyList(lsNo, lsVer);
			if (lsDomainMoneyList != null && lsDomainMoneyList.size() >0) {
				modelMap.put("domainMoneylist", new ObjectMapper().writeValueAsString(lsDomainMoneyList));
			}
		}
		
		List<String> domainList = new ArrayList<String>();
		for (int i = 0; i < subDomainList.size(); i++) {
			domainList.add(subDomainList.get(i).getID());
		}
		
		//合約有原契約編號時，將原契約起訖日紀錄下來
		if (lease != null && StringUtils.isNotBlank(lease.getORI_LS_NO())) {
			modelMap.put("ORI_LS_S_DATE", oriLsSDate);
			modelMap.put("ORI_LS_E_DATE", oriLsEDate);
		}
		
		modelMap.put("domainDept", deptDao.selectDeptByDomainList(domainList));// 洽談人員區域部門
		
		modelMap.put("domain", domainDao.getStandardDomain());// 維運區域
		modelMap.put("lessorList", selectLessor(lsNo));// 出租人
		modelMap.put("appFormList", getLsAppFormForLsContract());
		modelMap.put("rewardResn", getRewardResn());// 回饋原因
		modelMap.put("lsSealMap", getLookupByType("LS_SEAL"));// 印鑑種類
		modelMap.put("lsTypeMap", getLookupByType("LS_TYPE"));// 合約類型
		modelMap.put("phoneNumList", getRewardInfo(lsNo, "1", "1"));// 回饋門號
		modelMap.put("mobPhoneList", getRewardInfo(lsNo, "1", "2"));// 回饋手機
		modelMap.put("appUserDeptId", appUserDeptId);
		modelMap.put("appUserDeptName", appUserDeptName);
		modelMap.put("appUserName", appUserName);
		modelMap.put("appUserNo", appUserNo);
		modelMap.put("keepDeptId", keepDeptId);
		modelMap.put("lsType", lsType);		//合約型態
		
		modelMap.put("btnType", btnType);
		
		//=====出租人TAB===
		modelMap.put("lsLessorList", lookupService.getByType("LS_LESSOR"));//出租人類型
		modelMap.put("lsRelationMap", getLookupByType("LS_LEASE_RELATION"));// 與所有權人關係
		
	}
	
	//取得 租金 / 押金 / 電費 總額
	public List<LeaseDomainMoneyDTO> getLsDomainMoneyList(String lsNo, String lsVer) {
		return lS001Dao.getLeaseAllRentPledgeByDomain(lsNo, lsVer);
	}
	
	/**
	 * 增修條文轉 Json String.
	 *
	 * @param lease the lease
	 * @throws JSONException the JSON exception
	 */
	public void parseClauseToJsonStr(LeaseMainDTO lease) throws JSONException {
		JSONArray arr = new JSONArray();
		JSONObject tmp;
		// 增修條文
		String[] claIdArray = StringUtils.split(lease.getCLA_ID(), ",");
		if (claIdArray != null && claIdArray.length > 0) {
			for (String claId : claIdArray) {
				tmp = new JSONObject();
				tmp.put("id", claId);
				TbLsClause clause = clauseDao.findClauseByPk(claId);
				tmp.put("content", claId + "." + clause.getCLAUSE());
				arr.put(tmp);
			}
			lease.setJsonClause((arr.toString()));
		}
	}
	
	/**
	 * 出租人轉Json String.
	 *
	 * @param lease the lease
	 * @throws JSONException the JSON exception
	 */
	public void parseLessorToJsonStr (LeaseMainDTO lease) throws JSONException {
		JSONArray arr = new JSONArray();
		JSONObject tmp;
		for (TbLsLessor lessor : lease.getLessors()) {
			tmp = new JSONObject();
			tmp.put("ls_NO", lessor.getLS_NO());
			tmp.put("ls_VER", lessor.getLS_VER());
			tmp.put("lessor_ID", lessor.getLESSOR_ID());
			tmp.put("owner_ID", lessor.getOWNER_ID());
			tmp.put("owner_NAME", lessor.getOWNER_NAME());
			tmp.put("owner_NBR", lessor.getOWNER_NBR());
			tmp.put("owner_ADDR", lessor.getOWNER_ADDR());
			tmp.put("owner_ADDR_STD", lessor.getOWNER_ADDR_STD());
			tmp.put("payment_MODE", lessor.getPAYMENT_MODE());
			tmp.put("lessor_TYPE", lessor.getLESSOR_TYPE());
			tmp.put("voucher_TYPE", lessor.getVOUCHER_TYPE());
			//以上必填
			tmp.put("lessor_NAME", StringUtils.isBlank(lessor.getLESSOR_NAME()) ? "" : lessor.getLESSOR_NAME());
			tmp.put("residence_ADD", StringUtils.isBlank(lessor.getRESIDENCE_ADD()) ? "" : lessor.getRESIDENCE_ADD());
			tmp.put("residence_ADD_STD", StringUtils.isBlank(lessor.getRESIDENCE_ADD_STD()) ? "" : lessor.getRESIDENCE_ADD_STD());
			tmp.put("owner_RELATION", StringUtils.isBlank(lessor.getOWNER_RELATION()) ? "" : lessor.getOWNER_RELATION());
			tmp.put("income_TAX", StringUtils.isBlank(lessor.getINCOME_TAX()) ? "" : lessor.getINCOME_TAX());
			tmp.put("ctact_NAME", StringUtils.isBlank(lessor.getCTACT_NAME()) ? "" : lessor.getCTACT_NAME());
			tmp.put("ctact_NBR", StringUtils.isBlank(lessor.getCTACT_NBR()) ? "" : lessor.getCTACT_NBR());
			tmp.put("ctact_ADDR", StringUtils.isBlank(lessor.getCTACT_ADDR()) ? "" : lessor.getCTACT_ADDR());
			tmp.put("ctact_ADDR_STD", StringUtils.isBlank(lessor.getCTACT_ADDR_STD()) ? "" : lessor.getCTACT_ADDR_STD());
			tmp.put("tax_ADDR_SET", StringUtils.isBlank(lessor.getTAX_ADDR_SET()) ? "" : lessor.getTAX_ADDR_SET());
			tmp.put("doc_ADDR", StringUtils.isBlank(lessor.getDOC_ADDR()) ? "" : lessor.getDOC_ADDR());
			tmp.put("doc_ADDR_STD", StringUtils.isBlank(lessor.getDOC_ADDR_STD()) ? "" : lessor.getDOC_ADDR_STD());
			tmp.put("business_TAX", lessor.getBUSINESS_TAX() == null ? "" : lessor.getBUSINESS_TAX());
			tmp.put("per_MONTH", StringUtils.isBlank(lessor.getPER_MONTH()) ? "" : lessor.getPER_MONTH());
			tmp.put("per_MONTH", StringUtils.isBlank(lessor.getPER_MONTH()) ? "" : lessor.getPER_MONTH());
			tmp.put("pldg_PT", StringUtils.isBlank(lessor.getPLDG_PT()) ? "" : lessor.getPLDG_PT());
			tmp.put("printamr", StringUtils.isBlank(lessor.getPRINTAMR()) ? "" : lessor.getPRINTAMR());
			tmp.put("house_TAX_NO", StringUtils.isBlank(lessor.getHOUSE_TAX_NO()) ? "" : lessor.getHOUSE_TAX_NO());
			
			arr.put(tmp);
		}
		lease.setJsonLessors(arr.toString().replace("\"", "\\\""));
	}
	
	/**
	 * 取得回饋原因.
	 *
	 * @param lookupType the lookup type
	 * @return the reward resn
	 */
	public Map<String, String> getRewardResn() {
		Map<String, String> map = new LinkedHashMap<>();
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo("REWARD_RESN");
		List<TbSysLookup> rows = lookupDao.selectByExample(example);
		for (TbSysLookup row : rows) {
			String[] resnID = row.getVALUE1().split(";");
			String[] resnName = row.getVALUE2().split(";");
			for (int i = 0; i < resnID.length; i++) {
				map.put(resnID[i], resnName[i]);
			}
		}
		return map;
	}
	

	/**
	 * 取得互換業者.
	 *
	 * @param lookupType the lookup type
	 * @return the carriers resn
	 */
	public Map<String, String> getCarriers() {
		Map<String, String> map = new LinkedHashMap<>();
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo("CARRIERS");
		List<TbSysLookup> rows = lookupDao.selectByExample(example);
		for (TbSysLookup row : rows) {
			String[] resnID = row.getVALUE1().split(",");
			String[] resnName = row.getVALUE2().split(",");
			for (int i = 0; i < resnID.length; i++) {
				map.put(resnID[i], resnName[i]);
			}
		}
		return map;
	}

	/**
	 * 取得Lookup Map.
	 *
	 * @param lookType the look type
	 * @return the lookup by type
	 */
	public Map<String, String> getLookupByType(String lookType) {
		Map<String, String> map = new LinkedHashMap<>();
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo(lookType);
		List<TbSysLookup> rows = lookupDao.selectByExample(example);
		for (TbSysLookup row : rows) {
			map.put(row.getCODE(), row.getNAME());
		}
		return map;
	}

	/**
	 * 取得合約紙本保管地點.
	 *
	 * @param deptId the dept id
	 * @return the keep place
	 */
	public TbOrgDomain getKeepPlace(String deptId) {
		try {
			TbOrgDept dept = deptDao.selectByPrimaryKey(deptId);
			TbOrgDomain domain = domainDao.getDomainById(dept.getDOMAIN());
			if (StringUtils.equalsIgnoreCase(domain.getPARENT(), "HQ") ||
					StringUtils.equalsIgnoreCase(domain.getPARENT(), "A")) {
				return domain;
			} else {
				return domainDao.getDomainById(domain.getPARENT());
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			return null;
		}
	}

	/**
	 * 取得套表格式.
	 *
	 * @return the ls app form for ls contract
	 */
	public List<TbLsAppForm> getLsAppFormForLsContract() {
		return appFormDao.getTbLsAppFormForLsContract();
	}

	/**
	 * 取得回饋資訊.
	 *
	 * @param lsNo the ls no
	 * @param lsVer the ls ver
	 * @param rewardType the reward type
	 * @return the reward info
	 */
	public List<TbLsReward> getRewardInfo(String lsNo, String lsVer, String rewardType) {
		TbLsRewardExample example = new TbLsRewardExample();
		if (StringUtils.isNotEmpty(rewardType)) {
			example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer).andREWARD_TYPEEqualTo(rewardType);
		} else {
			example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		}
		List<TbLsReward> result = lS001Dao.lsRewardSelByEx(example);
		Map<String, String> map = getRewardResn();
		for(TbLsReward list :result){
			list.setREWARD_RESN(StringUtils.isNotEmpty(map.get(list.getREWARD_RESN())) ? map.get(list.getREWARD_RESN()):"");
		}
		return result;
	}

	/**
	 * 查詢合約清單.
	 *
	 * @param map the map
	 * @return the list
	 */
	public List<LeaseDTO> searchByCond(Map<String, Object> map) {
		return lS001Dao.searchByCond(map);
	}

	/**
	 * 取得增修條文.
	 *
	 * @return the ls clause
	 */
	public Map<String, String> getLsClause() {
		Map<String, String> map = new LinkedHashMap<>();

		List<TbLsClause> clauseList = clauseDao.getLsClause();
		for (TbLsClause clause : clauseList) {
			map.put(clause.getCLA_ID().toString(), clause.getCLA_ID() + "." + clause.getCLAUSE());
		}
		return map;
	}

	/**
	 * 新增or更新草稿.
	 *
	 * @param lsMain the ls main
	 * @param lessorJson the lessor json
	 * @param appSeq the app seq
	 * @param rewardJsonArr the reward json arr
	 * @param insertOrUpdate the insert or update
	 * @throws JSONException the JSON exception
	 * @throws ParseException the parse exception
	 */
	@Transactional
	public void saveUpdateLeaseMain(String btnType, LeaseMainDTO lsMain, String lessorJson,
			String rewardJsonArr) throws JSONException, ParseException {

		if (StringUtils.equals("new", btnType) || StringUtils.equals("change", btnType) 
				|| StringUtils.equals("ReSign", btnType) ) {
			TbOrgDept tbOrgDept = deptService.getDeptByPrimaryKey(lsMain.getDEAL_DEPT_ID());
			String lsNo = uniqueSeqService.getNextLeaseNo(tbOrgDept.getDOMAIN());
			String appSeq = uniqueSeqService.getNextLeaseAppNo(lsNo);
			lsMain.setLS_NO(lsNo);
			LsTypeEnum lsTypeEnum = null;
			
			if (StringUtils.equals("new", btnType)) {
				lsTypeEnum = LsTypeEnum.NewLease;
			} else if (StringUtils.equals("change", btnType)) {
				lsTypeEnum = LsTypeEnum.ChangeLease;
			}else if (StringUtils.equals("ReSign", btnType)) {
				lsTypeEnum = LsTypeEnum.ReSignLease;
			}
			
			insertDraft(lsMain, lessorJson, rewardJsonArr, appSeq, lsTypeEnum);
			if(!StringUtils.equals("new", btnType)){
				insertMaxLeaseExcludeAppMain(lsMain,lsTypeEnum);
			}
			
		}else if(StringUtils.equals("continue", btnType)){ 
			insertContinueLease(lsMain,lessorJson,rewardJsonArr);
		
		}else if (StringUtils.equals("edit", btnType)) {
			
			if(LsEnumCommon.LsTypeEnum.ContinueLease.getLsType().equals( lsMain.getLS_TYPE())){
				//續約要更新 TB_LS_MAIN_ADD
				//
				updateLeaseMainAdded(lsMain);
				
			}else{
				updateDraft(lsMain, lessorJson, rewardJsonArr);
				
			}
			
		}
	}

	/**
	 * 新增草稿.
	 *
	 * @param lsMain the ls main
	 * @param lessorJson the lessor json
	 * @param rewardJsonArr the reward json arr
	 * @param appSeq the app seq
	 * @throws JSONException the JSON exception
	 * @throws ParseException the parse exception
	 */
	@Transactional
	public void insertDraft(LeaseMainDTO lsMain, String lessorJson,
			String rewardJsonArr, String appSeq, LsTypeEnum lsTypeEnum) throws JSONException, ParseException {
		log.info("insertDraft entery");
		// 版次
		String lsVer = "1";
		String lsNo = lsMain.getLS_NO();
		// 合約申請檔
		TbLsApp lsApp = generateTbLsApp(lsNo, lsVer, appSeq, lsMain.getAPP_USER(),lsMain.getAPP_USER_DEPT(), lsTypeEnum);
		lsApp.setADD_DATA(lsMain.getCONTI_EXTEND_TYPE());
		lS001Dao.insertLeaseApp(lsApp);

		// 合約主檔
		String pldgCl = lsMain.getPLDG_CL();
		lsMain.setPLDG_CL(StringUtils.equals("on", pldgCl) ? "Y" : "N");
		String unfrmt = lsMain.getUNFRMT();
		lsMain.setUNFRMT(StringUtils.equals("on", unfrmt) ? "Y" : "N");
		String formId = lsMain.getFORM_ID();
		lsMain.setFORM_ID(StringUtils.equals("on", unfrmt) ? "" : formId);
		lsMain.setLS_STATUS(LsEnumCommon.LsStatusEnum.lsStatus0.getLsStatus());
		lsMain.setLS_VER(lsVer);
		if (lsMain.getLS_E_DATE() == null) {
			lsMain.setLS_E_DATE(DateUtils.ULIMITEDDATE);
		}
		
		if (!StringUtils.equals(lsMain.getLS_KIND(), "RESCHG")) {
			lsMain.setEXCH_CARRIERS(null);
			lsMain.setEXCH_ITEM(null);
		}
		lsMain.setCR_USER(getLoginUser().getUsername());
		lsMain.setCR_TIME(new Date());
		lsMain.setMD_USER(getLoginUser().getUsername());
		lsMain.setMD_TIME(new Date());
		lsMain.setAPP_SEQ(appSeq);
		lS001Dao.insertLsMain(lsMain);

		// 合約出租人檔
		List<TbLsLessor> lessList = lsMain.getLessors();
		if (StringUtils.isNotBlank(lessorJson)) {
			String[] lessorArray = StringUtils.split(lessorJson, ";");
			for (String lessorStr : lessorArray) {
				TbLsLessor tbLsLessor = parseLessorFromJson(lessorStr, lsMain);
				log.info(tbLsLessor.toString());
				lS001Dao.insertLsLessor(tbLsLessor);
				lessList.add(tbLsLessor);
			}
		}
		lsMain.setLessors(lessList);

		// 合約回饋檔
		List<TbLsReward> rewardList = new ArrayList<TbLsReward>();
		if (StringUtils.isNotBlank(rewardJsonArr)) {
			log.info("rewardJsonArr: " + rewardJsonArr);
			rewardList = parseRewardFromJson(rewardJsonArr, lsNo, lsVer);
		}

		if (rewardList.size() > 0) {
			for (TbLsReward tlr : rewardList) {
				log.info(tlr.toString());
				lS001Dao.insertLsReward(tlr);
				
			}
			
			lsMain.setRewardList(rewardList);
		}
	}

	/**
	 * Parses the reward from json.
	 *
	 * @param rewardJsonArr the reward json arr
	 * @param lsNo the ls no
	 * @param lsVer the ls ver
	 * @return the list
	 * @throws JSONException the JSON exception
	 */
	public List<TbLsReward> parseRewardFromJson(String rewardJsonArr, String lsNo, String lsVer) throws JSONException {
		JSONArray jsonArray = new JSONArray(rewardJsonArr);
		List<TbLsReward> rewardList = new ArrayList<TbLsReward>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			TbLsReward tlr = new TbLsReward();
			// 以下必填欄位
			tlr.setLS_NO(lsNo);
			tlr.setLS_VER(lsVer);
			tlr.setREWARD_TYPE(obj.getString("REWARD_TYPE"));
			tlr.setREWARD_ID(obj.getString("REWARD_ID"));
			tlr.setREWARD_RESN(obj.getString("REWARD_RESN"));
			tlr.setCR_USER(getLoginUser().getUsername());
			tlr.setCR_TIME(new Date());
			tlr.setMD_USER(getLoginUser().getUsername());
			tlr.setMD_TIME(new Date());

			tlr.setCUST_NAME(obj.isNull("CUST_NAME") ? null : obj.getString("CUST_NAME"));
			tlr.setPHONE_NBR(obj.isNull("PHONE_NBR") ? null : obj.getString("PHONE_NBR"));
			tlr.setPRCING(obj.isNull("PRCING") ? null : obj.getString("PRCING"));
			if (!obj.isNull("REWARD_QTY")) {
				tlr.setREWARD_QTY(new BigDecimal(obj.getString("REWARD_QTY")));
			}
			if (!obj.isNull("ESTIMATE_AMT")) {
				tlr.setESTIMATE_AMT(new BigDecimal(obj.getString("ESTIMATE_AMT")));
			}
			tlr.setREWARD_DESC(obj.isNull("REWARD_DESC") ? null : obj.getString("REWARD_DESC"));

			rewardList.add(tlr);
		}

		return rewardList;
	}

	/**
	 * 更新草稿.
	 *
	 * @param lsMain the ls main
	 * @param lessorJson the lessor json
	 * @param rewardJsonArr the reward json arr
	 * @throws JSONException the JSON exception
	 * @throws ParseException the parse exception
	 */
	@Transactional
	public void updateDraft(LeaseMainDTO lsMain, String lessorJson, String rewardJsonArr) throws JSONException, ParseException {
		//更新TB_LS_APP 
		updateLeaseApp(lsMain);
		updateLeaseMain(lsMain);
		updateLsLessor(lsMain, lessorJson);
		updateLsReward(lsMain.getLS_NO(), lsMain.getLS_VER(), rewardJsonArr);
	}
	
	//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓Update↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	/**
	 * Update lease app.
	 *
	 * @param lsApp the ls app
	 */
	public void updateLeaseApp(LeaseMainDTO lsMain) {
		try {
			TbLsAppExample example = new TbLsAppExample();
			example.createCriteria().andLS_NOEqualTo(lsMain.getLS_NO()).andLS_VEREqualTo(lsMain.getLS_VER());
			TbLsApp lsApp = new TbLsApp();
			lsApp.setADD_DATA(lsMain.getCONTI_EXTEND_TYPE());
			lsApp.setMD_USER(getLoginUser().getUsername());
			lsApp.setMD_TIME(new Date());
			
			lS001Dao.updateLeaseApp(lsApp, example);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new NomsException(ex.getMessage());
		}
	}
	

	/**
	 * Update lease main.
	 *
	 * @param lsMain the ls main
	 */
	@Transactional
	public void updateLeaseMain(LeaseMainDTO lsMain) {
		try {
			TbLsMainExample example = new TbLsMainExample();
			example.createCriteria().andLS_NOEqualTo(lsMain.getLS_NO()).andLS_VEREqualTo(lsMain.getLS_VER());
			
			String pldgCl = lsMain.getPLDG_CL();
			lsMain.setPLDG_CL(StringUtils.equals("on", pldgCl) ? "Y" : "N");
			String unfrmt = lsMain.getUNFRMT();
			lsMain.setUNFRMT(StringUtils.equals("on", unfrmt) ? "Y" : "N");
			String formId = lsMain.getFORM_ID();
			lsMain.setFORM_ID(StringUtils.equals("on", unfrmt) ? "" : formId);
			if (lsMain.getLS_E_DATE() == null) {
				lsMain.setLS_E_DATE(DateUtils.ULIMITEDDATE);
			}
			lsMain.setMD_USER(getLoginUser().getUsername());
			lsMain.setMD_TIME(new Date());
			
			TbLsAppExample example1 = new TbLsAppExample();
			example1.createCriteria().andLS_NOEqualTo(lsMain.getLS_NO()).andLS_VEREqualTo(lsMain.getLS_VER());
			TbLsApp lsApp = lS001Dao.searchLsApp(example1).get(0);
			lsMain.setAPP_SEQ(lsApp.getAPP_SEQ());
			
			//
			LeaseMainDTO currLease = lS001Dao.getLsMainByNo(lsMain.getLS_NO(), lsMain.getLS_VER());
			String currCarriers = currLease.getEXCH_CARRIERS();
			String currExchItem = currLease.getEXCH_ITEM();
			if (!StringUtils.equals(lsMain.getLS_KIND(), "RESCHG")) {
				lsMain.setEXCH_CARRIERS(null);
				lsMain.setEXCH_ITEM(null);
			}
			lS001Dao.updateLeaseMain(lsMain, example);
			
			//同步更新站台、站點，付款資訊, 站點用電檔起迄日
			//TB_LS_SITE 站台
			TbLsSiteExample siteExample = new TbLsSiteExample();
			siteExample.createCriteria().andLS_NOEqualTo(lsMain.getLS_NO()).andLS_VEREqualTo(lsMain.getLS_VER());
			TbLsSite site = new TbLsSite();
			site.setEFF_DATE(lsMain.getLS_S_DATE());	//金額生效起日:同合約承租起日
			site.setEND_DATE(lsMain.getLS_E_DATE());	//金額生效迄日:同合約承租起日
			site.setLS_E_DATE(lsMain.getLS_E_DATE());	//合約終止日:同合約承租迄日
			site.setMD_USER(getLoginUser().getUsername());
			site.setMD_TIME(new Date());
			lS001Dao.updateLeaseSite(site, siteExample);
			
			//TB_LS_LOCATION 站點
			TbLsLocationExample locationExample = new TbLsLocationExample();
			locationExample.createCriteria().andLS_NOEqualTo(lsMain.getLS_NO()).andLS_VEREqualTo(lsMain.getLS_VER());
			TbLsLocation location = new TbLsLocation();
			location.setEFF_DATE(lsMain.getLS_S_DATE());	//金額生效起日:同合約承租起日
			location.setEND_DATE(lsMain.getLS_E_DATE());	//金額生效迄日:同合約承租起日
			location.setLS_E_DATE(lsMain.getLS_E_DATE());	//合約終止日:同合約承租迄日
			location.setMD_USER(getLoginUser().getUsername());
			location.setMD_TIME(new Date());
			lS001Dao.updateLeaseLocation(location, locationExample);
			
			//TB_LS_LOC_PAYMENT 付款資訊
			TbLsLocPaymentExample locPaymentExample = new TbLsLocPaymentExample();
			locPaymentExample.createCriteria().andLS_NOEqualTo(lsMain.getLS_NO()).andLS_VEREqualTo(lsMain.getLS_VER());
			TbLsLocPayment locPayment = new TbLsLocPayment();
			locPayment.setEFF_DATE(lsMain.getLS_S_DATE());	//同合約承租起日
			locPayment.setEND_DATE(lsMain.getLS_E_DATE());	//同合約承租起
			locPayment.setMD_USER(getLoginUser().getUsername());
			locPayment.setMD_TIME(new Date());
			lS001Dao.updateLsLocPayment(locPayment, locPaymentExample);
			
			//TB_LS_LOC_ELEC 站點用電檔
			TbLsLocElecExample lsLocElecExample = new TbLsLocElecExample();
			lsLocElecExample.createCriteria().andLS_NOEqualTo(lsMain.getLS_NO()).andLS_VEREqualTo(lsMain.getLS_VER());
			TbLsLocElec lsLocElec = new TbLsLocElec();
			lsLocElec.setELEC_BEGIN_DATE(lsMain.getLS_S_DATE());//同合約承租起日
			lsLocElec.setELEC_END_DATE(lsMain.getLS_E_DATE());	//同合約承租起
			lsLocElec.setMD_USER(getLoginUser().getUsername());
			lsLocElec.setMD_TIME(new Date());
			lS001Dao.updateLsLocElec(lsLocElec, lsLocElecExample);
			
			//TODO
			String expenseCat = lsMain.getEXPENSE_CAT();
			//IF費用類別 沒勾租金 R:租金
			if (!StringUtils.contains(expenseCat, LsPaymentItem.R.getItemType())) {
				log.info("費用類別 沒勾租金");
				//update tb_ls_location 租金相關設定
				List<TbLsLocation> list = lS001Dao.getTbLsLocationByExample(locationExample);
				for (TbLsLocation source : list) {
					LeaseLocationDTO tmp = new LeaseLocationDTO();
					tmp.setLS_NO(source.getLS_NO());
					tmp.setLS_VER(source.getLS_VER());
					tmp.setLOCATION_ID(source.getLOCATION_ID());
					tmp.setEFF_DATE(source.getEFF_DATE());
					tmp.setEND_DATE(source.getEND_DATE());
					tmp.setZIP_CODE(source.getZIP_CODE());
					tmp.setDOMAIN(source.getDOMAIN());
					tmp.setLS_E_DATE(source.getLS_E_DATE());
					tmp.setCR_USER(source.getCR_USER());
					tmp.setCR_TIME(source.getCR_TIME());
					tmp.setMD_USER(getLoginUser().getUsername());
					tmp.setMD_TIME(new Date());
					lS001Dao.deleteLeaseLocation(source.getLS_NO(), source.getLS_VER(), source.getLOCATION_ID());
					lS001Dao.saveNewLeaseLocation(tmp);
				}
				
				//delete tb_ls_loc_payment where lsNo and lsver and payment_item = 'R' or payment_item = 'RD'
				lS001Dao.deleteLsLocPayment(lsMain.getLS_NO(),
						lsMain.getLS_VER(), null, LsPaymentItem.getRentItemType());
				//delete tb_ls_site where lsNo and lsver and EXPN_TYPE = 'R'
				lS001Dao.deleteLsSite(lsMain.getLS_NO(),
						lsMain.getLS_VER(), null,LsPaymentItem.R);
			}
			//IF費用類別 沒勾電費 E:電費
			if (!StringUtils.contains(expenseCat, LsPaymentItem.E.getItemType())) {
				log.info("費用類別 沒勾電費");
				//delete TB_LS_LOC_ELEC where lsNo and lsver 
				lS001Dao.deleteLsElec(lsMain.getLS_NO(), lsMain.getLS_VER(), null);
				//delete tb_ls_loc_payment where lsNo and lsver and payment_item = 'E' or payment_item = 'ED'
				lS001Dao.deleteLsLocPayment(lsMain.getLS_NO(),
						lsMain.getLS_VER(), null, LsPaymentItem.getElecItemType());
				//delete tb_ls_site where lsNo and lsver and EXPN_TYPE = 'E'
				lS001Dao.deleteLsSite(lsMain.getLS_NO(),
						lsMain.getLS_VER(), null,LsPaymentItem.E);
			}
			//if LS_KIND != RESCHG 資源互換 
			//delete TB_LS_RES_EXCH & TB_LS_RES_EXCH_ALOC where lsNo and lsver 
			if (!lsMain.hasExchangeCat()) {
				lS001Dao.deleteLsResExch(lsMain.getLS_NO(), lsMain.getLS_VER(), null);
			} else {
				if (!StringUtils.equals(lsMain.getEXCH_CARRIERS(), currCarriers) ||
					!StringUtils.equals(getAlocItem(lsMain.getEXCH_ITEM()), getAlocItem(currExchItem))) {
					//互換業者 or 互換項目 有變更的話UPDATE TB_LS_RES_EXCH_ALOC
					List<TbLsResExch> resExchList = lS001Dao.getLsResExchByLsNoVer(lsMain.getLS_NO(), lsMain.getLS_VER(), null);
					for (TbLsResExch resExch : resExchList) {
						List<SiteAlocDetailDTO> list = siteAlocService
								.getExchangeSiteAlocByLocationId(resExch.getEXCH_LOC_ID(), resExch.getLOCATION_ID()
										, resExch.getLS_NO(), resExch.getLS_VER(),null,null);
						lS001Dao.delLsResExchAloc(resExch.getLS_NO(), resExch.getLS_VER(), resExch.getLOCATION_ID(), resExch.getEXCH_LOC_ID());
						for (SiteAlocDetailDTO siteAlocDetail : list) {
							insertResExchAloc(lsMain, resExch, siteAlocDetail);
						}
					}
					
					
				}
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new NomsException(ex.getMessage());
		}
		
	}
	
	//轉換TB_LS_MAIN.EXCH_ITEM
	public String getAlocItem(String exchItem) {
		if (exchItem.indexOf("EX4") > -1) {
			String[] exchItemArry = exchItem.split(",");
			return exchItemArry.length > 1 ? SiteAlocService.ALOCITEM_RENT_ELEC : SiteAlocService.ALOCITEM_ELEC;
		} else {
			return SiteAlocService.ALOCITEM_RENT;
		}
	}
	/**
	 * Update ls lessor.
	 *
	 * @param lsNo the ls no
	 * @param lsVer the ls ver
	 * @param lessorJson the lessor json
	 * @throws JSONException the JSON exception
	 * @throws ParseException the parse exception
	 */
	@Transactional
	public void updateLsLessor(TbLsMain lsMain, String lessorJson) throws JSONException, ParseException {
		try {
			
			String lsNo = lsMain.getLS_NO();
			String lsVer = lsMain.getLS_VER();
			// 合約出租人檔
			if (StringUtils.isNotBlank(lessorJson)) {
				
				List<String> updateItems = new ArrayList<String>();
				List<TbLsLessor> updateLessorList = new ArrayList<TbLsLessor>();
				
				String[] lessorArray = StringUtils.split(lessorJson, ";");
				for (String lessorStr : lessorArray) {
					TbLsLessor tbLsLessor = parseLessorFromJson(lessorStr, lsMain);
					updateLessorList.add(tbLsLessor);
					updateItems.add(tbLsLessor.getLESSOR_ID());
				}
				
				//取得此次更新合約所要刪除的出租人列表
				TbLsLessorExample example = new TbLsLessorExample();
				example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer).andLESSOR_IDNotIn(updateItems);
				List<TbLsLessor> delLessorList = lS001Dao.selectLessorByExample(example);
				//刪除TB_LS_LOC_PAYMENT
				if (delLessorList != null && delLessorList.size() > 0) {
					for (TbLsLessor del :delLessorList) {
						TbLsLocPaymentExample locPaymentExample = new TbLsLocPaymentExample();
						locPaymentExample.createCriteria().andLS_NOEqualTo(del.getLS_NO())
								.andLS_VEREqualTo(del.getLS_VER())
								.andLESSOR_IDEqualTo(del.getLESSOR_ID());
						lS001Dao.deleteLsLocPaymentByExample(locPaymentExample);
					}
				}
				deleteLessorByLsNo(lsNo, lsVer);
				
				
				//更新TB_LS_LOC_PAYMENT
				if (updateLessorList != null && updateLessorList.size() > 0) {
					for (TbLsLessor update :updateLessorList) {
						TbLsLocPaymentExample locPaymentExample = new TbLsLocPaymentExample();
						locPaymentExample.createCriteria().andLS_NOEqualTo(update.getLS_NO())
								.andLS_VEREqualTo(update.getLS_VER())
								.andLESSOR_IDEqualTo(update.getLESSOR_ID());
						
						List<TbLsLocPayment> list = lS001Dao.selectLocPaymentByExample(locPaymentExample);
						if (list != null && list.size() > 0) {
							TbLsLocPayment payment = list.get(0);
							TbLsLocPayment updateData = new TbLsLocPayment();
							if (StringUtils.equals(LsPaymentItem.RD.getItemType(), payment.getPAYMENT_ITEM())) {
								updateData.setEFF_DATE(lsMain.getLS_S_DATE());
							}
							updateData.setEND_DATE(lsMain.getLS_E_DATE());
							updateData.setLESSOR_NAME(update.getLESSOR_NAME());
							updateData.setPAYMENT_MODE(update.getPAYMENT_MODE());
							updateData.setMD_USER(getLoginUser().getUsername());
							updateData.setMD_TIME(new Date());
							
							lS001Dao.updateLsLocPayment(updateData, locPaymentExample);
						}
						
						lS001Dao.insertLsLessor(update);
					}
				}
			} else {
				//取得此次更新合約所要刪除的出租人列表
				TbLsLessorExample example = new TbLsLessorExample();
				example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
				List<TbLsLessor> delLessorList = lS001Dao.selectLessorByExample(example);
				//刪除TB_LS_LOC_PAYMENT
				if (delLessorList != null && delLessorList.size() > 0) {
					for (TbLsLessor del :delLessorList) {
						TbLsLocPaymentExample locPaymentExample = new TbLsLocPaymentExample();
						locPaymentExample.createCriteria().andLS_NOEqualTo(del.getLS_NO())
								.andLS_VEREqualTo(del.getLS_VER())
								.andLESSOR_IDEqualTo(del.getLESSOR_ID());
						lS001Dao.deleteLsLocPaymentByExample(locPaymentExample);
					}
				}
				deleteLessorByLsNo(lsNo, lsVer);
			}
			
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new NomsException(ex.getMessage());
		}
	}
	
	/**
	 * 依lsNo, lsVer刪除 TbLsLessor.
	 *
	 * @param lsNo the ls no
	 * @param lsVer the ls ver
	 */
	public void deleteLessorByLsNo(String lsNo, String lsVer) {
		lS001Dao.deleteLessorByLsNo(lsNo, lsVer);
	}
	
	/**
	 * Update ls reward.
	 *
	 * @param lsNo the ls no
	 * @param lsVer the ls ver
	 * @param rewardJsonArr the reward json arr
	 * @throws JSONException the JSON exception
	 */
	public void updateLsReward(String lsNo, String lsVer, String rewardJsonArr) throws JSONException {
		try {
			deleteRewardByLsNo(lsNo, lsVer);
			// 合約回饋檔
			List<TbLsReward> rewardList = new ArrayList<TbLsReward>();
			if (StringUtils.isNotBlank(rewardJsonArr)) {
				log.info("rewardJsonArr: " + rewardJsonArr);
				rewardList = parseRewardFromJson(rewardJsonArr, lsNo, lsVer);
			}

			if (rewardList.size() > 0) {
				for (TbLsReward tlr : rewardList) {
					lS001Dao.insertLsReward(tlr);
				}
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new NomsException(ex.getMessage());
		}
	}
	
	/**
	 * 依lsNo, lsVer刪除 TbLsReward.
	 *
	 * @param lsNo the ls no
	 * @param lsVer the ls ver
	 */
	public void deleteRewardByLsNo(String lsNo, String lsVer) {
		lS001Dao.deleteRewardByLsNo(lsNo, lsVer);
	}
	
	/**
	 * 依lsNo, lsVer取得LeaseMainDTO.
	 *
	 * @param lsNo the ls no
	 * @param lsVer the ls ver
	 * @return the ls main by no
	 */
	public LeaseMainDTO getLsMainByNo(String lsNo, String lsVer) {
		
		
		return lS001Dao.getLsMainByNo(lsNo, lsVer);
	}
	/**
	 * 依lsNo, lsVer取得LeaseMainDTO.
	 *
	 * @param lsNo the ls no
	 * @param lsVer the ls ver
	 * @return the ls main by no
	 */
	public LeaseMainDTO getLsMainAddedByAppSeq(String appSeq) {
		
		return lS001Dao.getLsMainAddedByAppSeq(appSeq);
	}
	
	/**
	 * 依lsNo, lsVer取得List<TbLsLessor>.
	 *
	 * @param lsNo the ls no
	 * @param lsVer the ls ver
	 * @return the ls lessor by no
	 */
	public List<TbLsLessor> getLsLessorByNo(String lsNo, String lsVer) {
		
		
		return lS001Dao.getLsLessorByNo(lsNo, lsVer);
	}
	
	/**
	 * 依lsNo, lsVer取得List<TbLsReward>.
	 *
	 * @param lsNo the ls no
	 * @param lsVer the ls ver
	 * @return the ls reward by no
	 */
	public List<TbLsReward> getLsRewardByNo(String lsNo, String lsVer) {
		
		
		return lS001Dao.getLsRewardByNoVer(lsNo, lsVer);
	}
	
	//↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑Update↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
	/**
	 * Generate tb ls app.
	 *
	 * @param lsNo the ls no
	 * @param lsVer the ls ver
	 * @param appSeq the app seq
	 * @param lsMain the ls main
	 * @return the tb ls app
	 */
	public TbLsApp generateTbLsApp(String lsNo, String lsVer, String appSeq,String app_user,String appDepId,LsTypeEnum lsTypeEnum) {
		String  md_user = getLoginUser().getUsername();
		Date md_time = new Date();
		
		TbLsApp lsApp = new TbLsApp();
		lsApp.setLS_NO(lsNo);
		lsApp.setLS_VER(lsVer);
		lsApp.setAPP_SEQ(appSeq);
		lsApp.setLS_TYPE(lsTypeEnum.getLsType());
		lsApp.setAPP_STATUS(LsEnumCommon.AppStatusEnum.appStatus0.getAppStatus());
		lsApp.setAPP_DEPT_ID(appDepId);
		lsApp.setAPP_USER(app_user);
		lsApp.setCR_USER(md_user);
		lsApp.setCR_TIME(md_time);
		lsApp.setMD_USER(md_user);
		lsApp.setMD_TIME(md_time);
		return lsApp;
	}

	/**
	 * transfer json to TbLsLessor.
	 *
	 * @param lessorStr the lessor str
	 * @param lsNo the ls no
	 * @param lsVer the ls ver
	 * @return the tb ls lessor
	 * @throws JSONException the JSON exception
	 * @throws ParseException the parse exception
	 */
	public TbLsLessor parseLessorFromJson(String lessorStr, TbLsMain lsMain) throws JSONException, ParseException {
		TbLsLessor tll = new TbLsLessor();
		JSONObject obj = new JSONObject(lessorStr);
		String lsNo = lsMain.getLS_NO();
		String lsVer = lsMain.getLS_VER();
		// 以下必填欄位
		tll.setLS_NO(lsNo);
		tll.setLS_VER(lsVer);
		tll.setLESSOR_ID(obj.getString("lessor_ID"));
		tll.setLESSOR_TYPE(obj.getString("lessor_TYPE"));
		tll.setOWNER_ID(obj.getString("owner_ID"));
		tll.setOWNER_NAME(obj.getString("owner_NAME"));
		tll.setOWNER_NBR(obj.getString("owner_NBR"));
		tll.setOWNER_ADDR(obj.getString("owner_ADDR"));
		tll.setOWNER_ADDR_STD(obj.getString("owner_ADDR_STD"));
		tll.setPAYMENT_MODE(obj.getString("payment_MODE"));
		tll.setCR_USER(getLoginUser().getUsername());
		tll.setCR_TIME(new Date());
		tll.setMD_USER(getLoginUser().getUsername());
		tll.setMD_TIME(new Date());
		tll.setVOUCHER_TYPE(obj.getString("voucher_TYPE"));
		// 以上必填欄位
		tll.setLESSOR_NAME(obj.isNull("lessor_NAME") ? null : obj.getString("lessor_NAME"));
		tll.setRESIDENCE_ADD(obj.isNull("residence_ADD") ? null : obj.getString("residence_ADD"));
		tll.setRESIDENCE_ADD_STD(obj.isNull("residence_ADD_STD") ? null : obj.getString("residence_ADD_STD"));
		tll.setPRINTAMR((obj.isNull("printamr") ? null : obj.getString("printamr")));//簽訂委託管理使用收益暨授權簽約書
		tll.setOWNER_RELATION(obj.isNull("owner_RELATION") ? null : obj.getString("owner_RELATION"));
		tll.setINCOME_TAX(obj.isNull("income_TAX") ? null : obj.getString("income_TAX"));
		tll.setCTACT_NAME(obj.isNull("ctact_NAME") ? null : obj.getString("ctact_NAME"));
		tll.setCTACT_NBR(obj.isNull("ctact_NBR") ? null : obj.getString("ctact_NBR"));
		tll.setCTACT_ADDR(obj.isNull("ctact_ADDR") ? null : obj.getString("ctact_ADDR"));
		tll.setCTACT_ADDR_STD(obj.isNull("ctact_ADDR_STD") ? null : obj.getString("ctact_ADDR_STD"));
		tll.setTAX_ADDR_SET(obj.isNull("tax_ADDR_SET") ? null : obj.getString("tax_ADDR_SET"));
		if (StringUtils.equals(tll.getTAX_ADDR_SET(), "0")) {
			//同出租人聯絡地址
			tll.setDOC_ADDR(tll.getCTACT_ADDR());
			tll.setDOC_ADDR_STD(tll.getCTACT_ADDR_STD());
		}	else if ((StringUtils.equals(tll.getTAX_ADDR_SET(), "1"))){
			//TODO同租賃標的物地址??
			tll.setDOC_ADDR(lsMain.getLS_ADD());
			tll.setDOC_ADDR_STD(lsMain.getLS_ADD_STD());
		} else if ((StringUtils.equals(tll.getTAX_ADDR_SET(), "2"))){
			//另列如右
			tll.setDOC_ADDR(obj.isNull("doc_ADDR") ? null : obj.getString("doc_ADDR"));
			tll.setDOC_ADDR_STD(obj.isNull("doc_ADDR_STD") ? null : obj.getString("doc_ADDR_STD"));
		}
		
		if (!obj.isNull("business_TAX") && StringUtils.isNotBlank(obj.getString("business_TAX"))) {
			tll.setBUSINESS_TAX(new BigDecimal(obj.getString("business_TAX")));//營業稅
		}
		// obj.getString("chPayDate")?更換出租人租金起付日 :
		tll.setPER_MONTH(obj.isNull("per_MONTH") ? null : obj.getString("per_MONTH"));//是否按月開票
		tll.setPLDG_PT(obj.isNull("pldg_PT") ? null : obj.getString("pldg_PT"));//是否假扣押
		tll.setHOUSE_TAX_NO(obj.isNull("house_TAX_NO") ? null : obj.getString("house_TAX_NO"));//房屋稅籍編號
		
		return tll;
	}

	/**
	 * 合約作廢.
	 *
	 * @param appSeq the app seq
	 * @return the int
	 */
	public int cancelLeaseApp(String appSeq) {

		TbLsAppExample example = new TbLsAppExample();
		example.createCriteria().andAPP_SEQEqualTo(appSeq);
		TbLsApp record = lS001Dao.searchLsApp(example).get(0);
		record.setAPP_STATUS("4");
		record.setMD_TIME(new Date());
		record.setMD_USER(getLoginUser().getUsername());

		return lS001Dao.cancelLeaseApp(record);
	}

	/**
	 * 以lsNo查詢出租人.
	 *
	 * @param lsNo the ls no
	 * @return the list
	 */
	public List<TbLsLessor> selectLessor(String lsNo) {
		TbLsLessorExample example = new TbLsLessorExample();
		example.createCriteria().andLS_NOEqualTo(lsNo);

		return lS001Dao.selectLessorByExample(example);

	}

	/**
	 * 解約 套表格式 下拉值.
	 *
	 * @return the list
	 */
	public List<TbLsAppForm> selectAppFormTrmn() {
		TbLsAppFormExample example = new TbLsAppFormExample();
		example.createCriteria().andAPP_TYPEEqualTo("TRMN");
		return appFormDao.findByExample(example);
	}

	/**
	 * 解約 借電套表 下拉值.
	 *
	 * @return the list
	 */
	public List<TbLsAppForm> selectAppFormElecupd() {
		TbLsAppFormExample example = new TbLsAppFormExample();
		example.createCriteria().andAPP_TYPEEqualTo("ELECUPD").andSUB_TYPEEqualTo("EU1");
		return appFormDao.findByExample(example);
	}

	/**
	 * 用電方式 資料搜尋.
	 *
	 * @param lsNo            合約編號
	 * @param lsVer            版次
	 * @return the list
	 */
	public List<TbLsLocElec> searchLsLocElec(String lsNo, String lsVer) {
		TbLsLocElecExample example = new TbLsLocElecExample();
		example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		return lS001Dao.searchLsLocElecByExample(example);
	}

	/**
	 * 解約 儲存草稿 儲存 TB_LS_APP、TB_LS_TRMN與TB_LS_TRMN_ELEC新增資料.
	 *
	 * @param dto the dto
	 */
	@Transactional
	public void terminateLease(LeaseTerminalDTO dto) {
		String appNo = uniqueSeqService.getNextLeaseAppNo(dto.getLS_NO());
		TbLsApp tbLsApp = new TbLsApp();
		tbLsApp.setAPP_SEQ(appNo);
		tbLsApp.setLS_NO(dto.getLS_NO());
		tbLsApp.setLS_VER(dto.getLS_VER());
		tbLsApp.setLS_TYPE(LsEnumCommon.LsTypeEnum.CancelLease.getLsType());
		tbLsApp.setAPP_STATUS(LsEnumCommon.AppStatusEnum.appStatus0.getAppStatus());
		tbLsApp.setAPP_DEPT_ID(getLoginUser().getDeptId());
		tbLsApp.setAPP_USER(getLoginUser().getUsername());
		tbLsApp.setCR_USER(getLoginUser().getUsername());
		tbLsApp.setCR_TIME(dto.getCR_TIME());

		lS001Dao.insertLeaseApp(tbLsApp);
		lS001Dao.insertLsTrmn(dto);
		if (dto.getTrmnElecList().size() > 0) {
			for (TbLsTrmnElec list : dto.getTrmnElecList()) {
				lS001Dao.insertLsTrmnElec(list);
			}
		}
	}

	/**
	 * 查詢 TB_LS_APP 解約的資料.
	 *
	 * @param lsNo            合約編號
	 * @param lsVer            版次
	 * @return the list
	 */
	public List<TbLsApp> searchLsApp(String lsNo, String lsVer) {
		TbLsAppExample example = new TbLsAppExample();
		List<String> values = new ArrayList<String>();
		values.add(LsEnumCommon.AppStatusEnum.appStatus0.getAppStatus());
		values.add(LsEnumCommon.AppStatusEnum.appStatus1.getAppStatus());
		values.add(LsEnumCommon.AppStatusEnum.appStatus9.getAppStatus());
		if (StringUtils.isNotEmpty(lsVer)) {
			example.createCriteria().andLS_NOEqualTo(lsNo).andLS_TYPEEqualTo(LsEnumCommon.LsTypeEnum.CancelLease.getLsType()).andLS_VEREqualTo(lsVer);
		} else {
			example.createCriteria().andLS_NOEqualTo(lsNo).andLS_TYPEEqualTo(LsEnumCommon.LsTypeEnum.CancelLease.getLsType()).andAPP_STATUSIn(values);
		}
		return lS001Dao.searchLsApp(example);
	}

	/**
	 * 解約修改，至 TB_LS_TRMN 撈取修改資料.
	 *
	 * @param lsNo            合約編號
	 * @param lsVer            版次
	 * @param modelMap            返回UI所承接的map
	 */
	public void searchLsTrmn(String lsNo, String lsVer, Map<String, Object> modelMap) {
		TbLsTrmnKey key = new TbLsTrmnKey();
		key.setLS_NO(lsNo);
		key.setLS_VER(lsVer);
		TbLsTrmn tbLsTrmn = lS001Dao.searchLsTrmn(key);
		modelMap.put("editTrmnDate", DateUtils.formatDateUS(tbLsTrmn.getTRMN_DATE(), "yyyy/MM/dd"));// 解約日
		modelMap.put("editTrmnTyepe", tbLsTrmn.getTRMN_TYEPE());// 解約類型
		modelMap.put("editTrmnResn", tbLsTrmn.getTRMN_RESN());// 解約原因
		modelMap.put("editRecovDay", tbLsTrmn.getRECOV_DAY());// 房屋恢復原狀日
		modelMap.put("editTrmnFormId", tbLsTrmn.getFORM_ID());// 套表格式
		modelMap.put("editTrmnDesc", tbLsTrmn.getTRMN_DESC());// 解約說明deptName
	}

	/**
	 * Search ls trmn elec.
	 *
	 * @param lsNo            合約編號
	 * @param lsVer            版次
	 * @return the list
	 */
	public List<TbLsTrmnElec> searchLsTrmnElec(String lsNo, String lsVer) {
		TbLsTrmnElecExample example = new TbLsTrmnElecExample();
		example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		return lS001Dao.searchLsTrmnElec(example);
	}

	/**
	 * 解約 儲存草稿 儲存 TB_LS_APP、TB_LS_TRMN與TB_LS_TRMN_ELEC 更新資料.
	 *
	 * @param dto            LeaseTerminalDTO
	 * @param appSeq            申請流水號
	 */
	@Transactional
	public void updateTerminateLease(LeaseTerminalDTO dto, String appSeq) {
		TbLsAppExample example = new TbLsAppExample();
		example.createCriteria().andLS_NOEqualTo(dto.getLS_NO()).andLS_VEREqualTo(dto.getLS_VER()).andAPP_SEQEqualTo(appSeq);
		TbLsApp record = new TbLsApp();
		record.setMD_TIME(dto.getMD_TIME());
		record.setMD_USER(dto.getMD_USER());
		lS001Dao.updateLeaseApp(record, example);
		lS001Dao.updateLsTrmn(dto);
		if (dto.getTrmnElecList().size() > 0) {
			for (TbLsTrmnElec list : dto.getTrmnElecList()) {
				lS001Dao.updateLsTrmnElec(list);
			}
		}
	}

	/**
	 * 取得合約主檔站點區域.
	 *
	 * @param lsNo 			合約編號
	 * @param lsVer 			版次
	 * @return the lease main domain
	 */
	public List<TbOrgDomain> getLeaseMainDomain(String lsNo, String lsVer) {
		TbLsMain tbLsMain = searchLsMainKey(lsNo, lsVer);
		List<TbOrgDomain> tbOrgDomainList = domainDao.getStandardDomain();
		List<TbOrgDomain> addList = new ArrayList<TbOrgDomain>();
		if (tbLsMain != null) {
			if (StringUtils.isNotEmpty(tbLsMain.getOP_DOMAIN())) {
				String[] domains = tbLsMain.getOP_DOMAIN().split(",");
				for (String domain : domains) {
					for (TbOrgDomain tbOrgDomain : tbOrgDomainList) {
						if (StringUtils.equals(tbOrgDomain.getID(), domain)) {
							addList.add(tbOrgDomain);
						}
					}
				}
			}
		}
		return addList;
	}
	
	/**
	 * 尋找 TB_LS_MAIN 的唯一資料.
	 *
	 * @param lsNo 			合約編號
	 * @param lsVer 			版次
	 * @return the tb ls main
	 */
	private TbLsMain searchLsMainKey(String lsNo, String lsVer) {
		TbLsMainKey key = new TbLsMainKey();
		key.setLS_NO(lsNo);
		if(lsVer!=null && lsVer.length()>0){
			key.setLS_VER(lsVer);
		}
		return lS001Dao.getLsMainByNo(lsNo, lsVer);
	}
	
	/**
	 * 取得搜尋條件的所有站點.
	 *
	 * @param lsNo 			合約編號
	 * @param lsVer 			版次
	 * @param domain the domain
	 * @return the lease location
	 */
	public List<LeaseLocationDTO> getLeaseLocation(String lsNo, String lsVer,String domain) {
		// LS_MAIN 不存在表示 是 續約 且是 草稿 , 一切抓最大版號
//		LeaseMainDTO mainDTO = lS001Dao.getLsMainByNo(lsNo, lsVer);
//		if(mainDTO==null){
//			lsVer=null;
//			
//		}
//		
//		
		List<LeaseLocationDTO> leaseLocationDTOList = lsCommonService.getLeaseLocationByLsNo
				(lsNo, lsVer,domain);
	
		for (LeaseLocationDTO dtoList : leaseLocationDTOList) {
			getLeaseByLocation(dtoList);
		}
		return leaseLocationDTOList;
	}

	/**
	 * 取得搜尋條件的所有站點.
	 *
	 * @param lsNo 			合約編號
	 * @param lsVer 			版次
	 * @param domain the domain
	 * @return the lease location
	 */
	public List<LeaseLocationDTO> getLeaseAddedLocation(String appSeq,String domain) {
		// LS_MAIN 不存在表示 是 續約 且是 草稿 , 一切抓最大版號
//		LeaseMainDTO mainDTO = lS001Dao.getLsMainByNo(lsNo, lsVer);
//		if(mainDTO==null){
//			lsVer=null;
//			
//		}
//		
		
		List<LeaseLocationDTO> leaseLocationDTOList =lS001Dao.getLeaseLocationAdded(appSeq, domain);
		for (LeaseLocationDTO dtoList : leaseLocationDTOList) {
			getLeaseByLocation(dtoList);
		}
		return leaseLocationDTOList;
	}
	
	/**
	 * 判斷是否有效.
	 *
	 * @param dto 			暫存物件
	 * @return the lease by location
	 */
	private void getLeaseByLocation(LeaseLocationDTO dto) {
		if (lS001Dao.getLeaseByLocation(dto.getLOCATION_ID()).size() > 0) {
			dto.setHavingEffectiveLease(true);
		} else {
			dto.setHavingEffectiveLease(false);
		}
	}
	
	/**
	 * 新增站點.
	 *
	 * @param lsNo 			合約編號
	 * @param lsVer 			版次
	 * @param locId 			站點編號
	 * @param domain 			區域
	 * @param zipCode 			郵區號
	 * @return the lease location dto
	 */
	@Transactional
	public LeaseLocationDTO saveNewLeaseLocation(String lsNo, String lsVer, String locId, String domain, String zipCode) {
		LeaseLocationDTO dto = new LeaseLocationDTO();
		TbLsMain tbLsMain = searchLsMainKey(lsNo, lsVer);
		dto.setLS_NO(lsNo);
		dto.setLS_VER(lsVer);
		dto.setLOCATION_ID(locId);
		dto.setZIP_CODE(zipCode);
		dto.setDOMAIN(domain);
		dto.setLS_E_DATE(tbLsMain.getLS_E_DATE());
		dto.setEFF_DATE(tbLsMain.getLS_S_DATE());
		dto.setEND_DATE(tbLsMain.getLS_E_DATE());
		dto.setCR_USER(getLoginUser().getUsername());
		dto.setCR_TIME(DateUtils.today());
		dto.setMD_USER(getLoginUser().getUsername());
		dto.setMD_TIME(DateUtils.today());
		lS001Dao.saveNewLeaseLocation(dto);
		//解約清算
		dto.setPLDG_CL(tbLsMain.getPLDG_CL());
		getLeaseByLocation(dto);
		return dto;
	}
	
	/**
	 * 站點刪除.
	 *
	 * @param lsNo 			合約編號
	 * @param lsVer 			版次
	 * @param locId 			站點編號
	 */
	@Transactional
	public void deleteLeaseSite(String lsNo, String lsVer, String locId) {
		try {
			lS001Dao.deleteLeaseLocation(lsNo, lsVer, locId);
			lS001Dao.deleteLsSite(lsNo, lsVer, locId,null);
			lS001Dao.deleteLsElec(lsNo, lsVer, locId);
			lS001Dao.deleteLsLocPayment(lsNo, lsVer, locId,null);
			lS001Dao.deleteLsResExch(lsNo, lsVer, locId);
		} catch (Exception e) {
			throw new NomsException(e.getMessage());
		}
	}
	
	/**
	 * 點選站點，撈取資料.
	 *
	 * @param lsNo 			合約編號
	 * @param lsVer 			版次
	 * @param locId 			站點編號
	 * @param addFlag 			增補協議-加裝設備用
	 * @return the ls site location
	 * @throws Exception the exception
	 */
	public LeaseLocElecDTO getLocationSitePay4Elec(String lsNo, String lsVer,
			String locId,String addFlag) throws Exception {
		LeaseLocElecDTO leaseLocElecDTO = new LeaseLocElecDTO();
		try {
		
			
			List<TbLsLocElecDTO> lsLocElecList = lS001Dao.getLsLocElecByLsNoVer(
					lsNo, lsVer, locId);

			
			leaseLocElecDTO.setLS_NO(lsNo);
			leaseLocElecDTO.setLS_VER(lsVer);
			leaseLocElecDTO.setLOCATION_ID(locId);
			
			LeaseMainDTO main =lS001Dao.getLsMainByNo(lsNo, lsVer);
			if(main==null){throw new DataNotFoundException("LSNO="+lsNo+",lsVer="+lsVer+" 合約主檔資料找不到");}
			
			leaseLocElecDTO.setLS_S_DATE(main.getLS_S_DATE());
			leaseLocElecDTO.setLS_E_DATE(main.getLS_E_DATE());
			leaseLocElecDTO.setLS_ADD(main.getLS_ADD());
			leaseLocElecDTO.setLS_ADD_STD(main.getLS_ADD_STD());
			
			leaseLocElecDTO.setLsLocElecList(lsLocElecList);
			if(StringUtils.isNotEmpty(addFlag)){
				LsPaymentItem[] elecItems = new LsPaymentItem[]{LsPaymentItem.E};
				leaseLocElecDTO.setTbLsLocPaymentList(lS001Dao
						.getLsLocPaymentByLsNoVerLocId(lsNo, lsVer, locId,elecItems));
			}else{
				leaseLocElecDTO.setTbLsLocPaymentList(lS001Dao
						.getLsLocPaymentByLsNoVerLocId(lsNo, lsVer, locId,LsPaymentItem.getElecItemType()));
			}
//			
//			TbLsLessorExample example = new TbLsLessorExample();
//			example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
			List<TbLsLessor> tbLsLessorList = getLsLessorByNo(lsNo, lsVer);
			String tempLessorName = "";
			String tempLessorPaymentMode = "";
			for (TbLsLessor tbLsLessor : tbLsLessorList) {
				if (tempLessorName.length() > 0) {
					tempLessorName += ",";
					tempLessorPaymentMode += ",";
				}
				tempLessorPaymentMode += tbLsLessor.getLESSOR_ID() + ":"
						+ tbLsLessor.getPAYMENT_MODE()+":"+tbLsLessor.getBUSINESS_TAX();
				tempLessorName += tbLsLessor.getLESSOR_ID() + ":"
						+ tbLsLessor.getLESSOR_NAME();
			}
			leaseLocElecDTO.setLessorNames(tempLessorName);
			leaseLocElecDTO.setAllLessorPaymentMode(tempLessorPaymentMode);

			TbSysLookupExample tbSysLookupExample = new TbSysLookupExample();
			tbSysLookupExample.createCriteria().andTYPEEqualTo("LS_LEASE_RELATION");
			tbSysLookupExample.setOrderByClause("DISPLAY_ORDER");
			List<TbSysLookup> TbSysLookupList = lS001Dao
					.getLsLeaseBldgtype(tbSysLookupExample);
			String tempLsLeaseRelation = "";
			for (TbSysLookup tbSysLookup : TbSysLookupList) {
				if (tempLsLeaseRelation.length() > 0) {
					tempLsLeaseRelation += ";";
				}
				tempLsLeaseRelation += tbSysLookup.getCODE() + ","
						+ tbSysLookup.getNAME();
			}
			leaseLocElecDTO.setLsLeaseRelation(tempLsLeaseRelation);

			leaseLocElecDTO.setTbLsSiteDTOList(lS001Dao.getLsSiteByLsNoLocId(lsNo,
					lsVer, locId,LsPaymentItem.E));

			if (leaseLocElecDTO.getTbLsSiteDTOList() == null
					|| leaseLocElecDTO.getTbLsSiteDTOList().size() == 0) {

				List<SiteAlocDetailDTO> dtoList = siteAlocService
						.getNormalSiteAlocByLocationId(locId,
								SiteAlocService.ALOCITEM_ELEC);
				List<TbLsSiteDTO> tbLsSiteDTOList = new ArrayList<TbLsSiteDTO>();
				for (SiteAlocDetailDTO dto : dtoList) {
					TbLsSiteDTO tbLsSiteDTO = new TbLsSiteDTO();
					tbLsSiteDTO.setLOCATION_ID(dto.getLocationId());
					tbLsSiteDTO.setPAY_BEGIN_DATE(dto.getEFF_DATE());
					tbLsSiteDTO.setLS_E_DATE(dto.getEND_DATE());
					tbLsSiteDTO.setEXPN_TYPE(dto.getAlocItem());
					tbLsSiteDTO.setALOC_RATIO(dto.getALOC_RATIO());
					tbLsSiteDTO.setSITE_ID(dto.getSiteId());
					tbLsSiteDTO.setSITE_NAME(dto.getSiteName());
					tbLsSiteDTO.setBTS_SITE_ID(dto.getBtsSiteId());
					tbLsSiteDTO.setALOC_ID(dto.getALOC_ID());
					
					tbLsSiteDTOList.add(tbLsSiteDTO);
				}
				leaseLocElecDTO.setTbLsSiteDTOList(tbLsSiteDTOList);
			}
			
			leaseLocElecDTO.setTbLsElecRanges(lS001Dao.getEffectiveElecRangeSetting());
			
		} catch (DataNotFoundException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
		
		
		return leaseLocElecDTO;
	}
	
	/**
	 * 點選站點，撈取資料.
	 *
	 * @param lsNo 			合約編號
	 * @param lsVer 			版次
	 * @param locId 			站點編號
	 * @return the ls site location
	 * @throws Exception the exception
	 */
	public LeaseLocElecDTO getLocationSiteAddedPay4Elec(String appSeq,
			String locId) throws Exception {
		LeaseLocElecDTO leaseLocElecDTO = new LeaseLocElecDTO();
		try {
		
			
			List<TbLsLocElecDTO> lsLocElecList = lS001Dao.getLsLocElecAddedByAppSeqLoc(
					appSeq, locId);

			
			leaseLocElecDTO.setAPP_SEQ(appSeq);
			leaseLocElecDTO.setLOCATION_ID(locId);
			
			TbLsMainAddedExample example =new TbLsMainAddedExample();
			example.createCriteria().andAPP_SEQEqualTo(appSeq);
			List<TbLsMainAdded> mainAddList =ls001m3Dao.searchTbLsMainAdded(example);
			
			
			TbLsMainAdded main =mainAddList.get(0);
			if(main==null){throw new DataNotFoundException("APPSEQ="+appSeq+" 合約主檔資料找不到");}
			
			leaseLocElecDTO.setLS_S_DATE(main.getLS_S_DATE());
			leaseLocElecDTO.setLS_E_DATE(main.getLS_E_DATE());
			leaseLocElecDTO.setLS_ADD(main.getLS_ADD());
			leaseLocElecDTO.setLS_ADD_STD(main.getLS_ADD_STD());
			
			leaseLocElecDTO.setLsLocElecList(lsLocElecList);
			//LsPaymentItem.getElecItemType()
			
			leaseLocElecDTO.setTbLsLocPaymentList(lS001Dao
					.getLsLocPaymentAddedByLsNoVerLocId(appSeq, locId, LsPaymentItem.getElecItemType()));
//			
//			TbLsLessorExample example = new TbLsLessorExample();
//			example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
			List<TbLsLessor> tbLsLessorList = lS001Dao.getLsLessorAddedByAppSeq(appSeq);
			String tempLessorName = "";
			String tempLessorPaymentMode = "";
			for (TbLsLessor tbLsLessor : tbLsLessorList) {
				if (tempLessorName.length() > 0) {
					tempLessorName += ",";
					tempLessorPaymentMode += ",";
				}
				tempLessorPaymentMode += tbLsLessor.getLESSOR_ID() + ":"
						+ tbLsLessor.getPAYMENT_MODE()+":"+tbLsLessor.getBUSINESS_TAX();
				tempLessorName += tbLsLessor.getLESSOR_ID() + ":"
						+ tbLsLessor.getLESSOR_NAME();
			}
			leaseLocElecDTO.setLessorNames(tempLessorName);
			leaseLocElecDTO.setAllLessorPaymentMode(tempLessorPaymentMode);

			TbSysLookupExample tbSysLookupExample = new TbSysLookupExample();
			tbSysLookupExample.createCriteria().andTYPEEqualTo("LS_LEASE_RELATION");
			tbSysLookupExample.setOrderByClause("DISPLAY_ORDER");
			List<TbSysLookup> TbSysLookupList = lS001Dao
					.getLsLeaseBldgtype(tbSysLookupExample);
			String tempLsLeaseRelation = "";
			for (TbSysLookup tbSysLookup : TbSysLookupList) {
				if (tempLsLeaseRelation.length() > 0) {
					tempLsLeaseRelation += ";";
				}
				tempLsLeaseRelation += tbSysLookup.getCODE() + ","
						+ tbSysLookup.getNAME();
			}
			leaseLocElecDTO.setLsLeaseRelation(tempLsLeaseRelation);

			leaseLocElecDTO.setTbLsSiteDTOList(lS001Dao.getLsSiteAddedByLsNoLocId(appSeq, locId, LsPaymentItem.E));

			if (leaseLocElecDTO.getTbLsSiteDTOList() == null
					|| leaseLocElecDTO.getTbLsSiteDTOList().size() == 0) {

				List<SiteAlocDetailDTO> dtoList = siteAlocService
						.getNormalSiteAlocByLocationId(locId,
								SiteAlocService.ALOCITEM_ELEC);
				List<TbLsSiteDTO> tbLsSiteDTOList = new ArrayList<TbLsSiteDTO>();
				for (SiteAlocDetailDTO dto : dtoList) {
					TbLsSiteDTO tbLsSiteDTO = new TbLsSiteDTO();
					tbLsSiteDTO.setLOCATION_ID(dto.getLocationId());
					tbLsSiteDTO.setPAY_BEGIN_DATE(dto.getEFF_DATE());
					tbLsSiteDTO.setLS_E_DATE(dto.getEND_DATE());
					tbLsSiteDTO.setEXPN_TYPE(dto.getAlocItem());
					tbLsSiteDTO.setALOC_RATIO(dto.getALOC_RATIO());
					tbLsSiteDTO.setSITE_ID(dto.getSiteId());
					tbLsSiteDTO.setSITE_NAME(dto.getSiteName());
					tbLsSiteDTO.setBTS_SITE_ID(dto.getBtsSiteId());
					tbLsSiteDTO.setALOC_ID(dto.getALOC_ID());
					
					tbLsSiteDTOList.add(tbLsSiteDTO);
				}
				leaseLocElecDTO.setTbLsSiteDTOList(tbLsSiteDTOList);
			}
			
			leaseLocElecDTO.setTbLsElecRanges(lS001Dao.getEffectiveElecRangeSetting());
			
		} catch (DataNotFoundException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
		
		
		return leaseLocElecDTO;
	}
	
	/**
	 * 撈取 承租建物 資料.
	 *
	 * @return the ls lease bldgtype
	 */
	public List<TbSysLookup> getLsLeaseBldgtype(){
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo("LS_LEASE_BLDGTYPE");
		example.setOrderByClause("DISPLAY_ORDER");
		return lS001Dao.getLsLeaseBldgtype(example);
	}
	
	/**
	 * 撈取 設備類型.
	 *
	 * @return the tb com eqp type
	 */
	public List<TbComEqpType> getTbComEqpType(){
		TbComEqpTypeExample example = new TbComEqpTypeExample();
		example.createCriteria();
		return lS001Dao.getTbComEqpType(example);
		
	}
	
	/**
	 * 撈取 機型代碼.
	 *
	 * @param eqpTypeId 				設備類型ID
	 * @return the tb com eqp model
	 */
	public List<TbComEqpModel> getTbComEqpModel(String eqpTypeId){
		TbComEqpModelExample example = new TbComEqpModelExample();
		example.createCriteria().andEQP_TYPE_IDEqualTo(eqpTypeId);
		return lS001Dao.getTbComEqpModel(example);
	}
	
	/**
	 * 撈取均價資料.
	 *
	 * @param eqpTypeId 				設備類型ID
	 * @param eqpModelId 				機型代碼ID
	 * @param zipCode 				郵遞區號
	 * @return the tb ls avg rent
	 */
	public List<TbLsAvgRent> getTbLsAvgRent(String eqpTypeId,String eqpModelId,String zipCode){
		TbLsAvgRentExample example = new TbLsAvgRentExample();
		
		example.createCriteria().andEQP_MODEL_IDEqualTo(eqpModelId).andEQP_TYPE_IDEqualTo(eqpTypeId).andZIP_CODELike(zipCode.length()>3?zipCode.substring(0,3):zipCode);
		return lS001Dao.getTbLsAvgRent(example);
	}
	
	
	//*************start Arda*****************
		/**
	 * 租金Tab 存檔.
	 *
	 * @param leaseLocationDTO the lease location dto
	 */
		@Transactional
		public void saveUpdateLeaseLocation4Rent(LeaseLocationDTO leaseLocationDTO )throws NomsException{
			
			try {
				List<TbLsLocPaymentDTO> locPayList = leaseLocationDTO.getTbLsLocPaymentList();
				List<TbLsSiteDTO> siteAlocDetailList = leaseLocationDTO.getTbLsSiteDTOList();
				
				Date md_time = new Date();
//				String mdUser = getLoginUser().getUsername();
//				TbLsLocation loc = new TbLsLocation();
				
				if(!"Y".equals(leaseLocationDTO.getELEC_PLUS())){
					leaseLocationDTO.setELEC_PLUS("N");
				}
				if(leaseLocationDTO.getPAY_BEGIN_DATE()!=null){
					leaseLocationDTO.setAPP_DEPT_ID(getLoginUser().getDeptId());
					leaseLocationDTO.setAPP_USER(getLoginUser().getUsername());
					leaseLocationDTO.setAPP_TIME(md_time);
				}
				
				TbLsLocationExample tbLsLocationExample= new TbLsLocationExample();
				tbLsLocationExample.createCriteria().andLS_NOEqualTo(leaseLocationDTO.getLS_NO()).andLS_VEREqualTo(leaseLocationDTO.getLS_VER()).andLOCATION_IDEqualTo(leaseLocationDTO.getLOCATION_ID());
				lS001Dao.updateLeaseLocation(leaseLocationDTO, tbLsLocationExample);
			
				
				if("Y".equals(leaseLocationDTO.getELEC_PLUS())){
					//租金含電費 刪除電錶資訊	
					lS001Dao.deleteLsElec(leaseLocationDTO.getLS_NO(), leaseLocationDTO.getLS_VER(), leaseLocationDTO.getLOCATION_ID());
					lS001Dao.deleteLsSite(leaseLocationDTO.getLS_NO(), leaseLocationDTO.getLS_VER(), leaseLocationDTO.getLOCATION_ID(), LsPaymentItem.E);
					lS001Dao.deleteLsLocPayment(leaseLocationDTO.getLS_NO(), leaseLocationDTO.getLS_VER(), leaseLocationDTO.getLOCATION_ID(), LsPaymentItem.getElecItemType());
				}
				
				
				//站點資訊
				lS001Dao.deleteLsSite(leaseLocationDTO.getLS_NO(),leaseLocationDTO.getLS_VER(), leaseLocationDTO.getLOCATION_ID(),LsPaymentItem.R);
//				TbLsSite tblsSite;
				for(TbLsSiteDTO siteDTO:siteAlocDetailList){
//					tblsSite = new TbLsSite();
//					BeanUtils.copyProperties(siteDTO,tblsSite);
					siteDTO.setLOCATION_ID(leaseLocationDTO.getLOCATION_ID());
					siteDTO.setLS_VER(leaseLocationDTO.getLS_VER());
					siteDTO.setLS_NO(leaseLocationDTO.getLS_NO());
					siteDTO.setLS_E_DATE(leaseLocationDTO.getLS_E_DATE());
					siteDTO.setEFF_DATE(leaseLocationDTO.getEFF_DATE());
					siteDTO.setEND_DATE(leaseLocationDTO.getEND_DATE());
					
					lS001Dao.insertLsSite(siteDTO);
				}
				
				//付款資訊
				lS001Dao.deleteLsLocPayment(leaseLocationDTO.getLS_NO(),leaseLocationDTO.getLS_VER(), leaseLocationDTO.getLOCATION_ID(),LsPaymentItem.getRentItemType());

				for(TbLsLocPayment tblocPayment:locPayList){
					tblocPayment.setLOCATION_ID(leaseLocationDTO.getLOCATION_ID());
					tblocPayment.setLS_VER(leaseLocationDTO.getLS_VER());
					tblocPayment.setLS_NO(leaseLocationDTO.getLS_NO());
					tblocPayment.setFIRST_INCR(leaseLocationDTO.getNEXT_INCR());
					tblocPayment.setEFF_DATE(leaseLocationDTO.getEFF_DATE());
					tblocPayment.setEND_DATE(leaseLocationDTO.getEND_DATE());
					
					lS001Dao.insertLsLocPayment(tblocPayment);
				}
				

			} catch (Exception e) {
				log. error(ExceptionUtils.getFullStackTrace(e));
				throw new NomsException(e.getMessage());
			}
			
			
		}
		
		/**
		 *續約 租金Tab 存檔.
		 *
		 * @param leaseLocationDTO the lease location dto
		 */
			@Transactional
			public void saveUpdateLeaseLocation4RentAdded(LeaseLocationDTO leaseLocationDTO )throws NomsException{
				
				try {
			
					String appSeq = leaseLocationDTO.getAPP_SEQ();
					if(StringUtils.isBlank(appSeq)){
						throw new NomsException("APP SEQ 不得為空");
					}
					
					Date md_time = new Date();
//					String mdUser = getLoginUser().getUsername();
//					TbLsLocation loc = new TbLsLocation();
					
					if(!"Y".equals(leaseLocationDTO.getELEC_PLUS())){
						leaseLocationDTO.setELEC_PLUS("N");
					}
					if(leaseLocationDTO.getPAY_BEGIN_DATE()!=null){
						leaseLocationDTO.setAPP_DEPT_ID(getLoginUser().getDeptId());
						leaseLocationDTO.setAPP_USER(getLoginUser().getUsername());
						leaseLocationDTO.setAPP_TIME(md_time);
					}
					
					TbLsLocationAddedExample tbLsLocationAddExample= new TbLsLocationAddedExample();
					tbLsLocationAddExample.createCriteria().andAPP_SEQEqualTo(leaseLocationDTO.getAPP_SEQ());
//					lS001Dao.updateLeaseLocation(leaseLocationDTO, tbLsLocationExample);
					
					TbLsLocationAdded addedBean=  new TbLsLocationAdded();
					
					BeanUtils.copyProperties(leaseLocationDTO, addedBean);
					
					ls001m3Dao.updateLeaseLocationAdded(addedBean, tbLsLocationAddExample);
//					
				} catch (Exception e) {
					log. error(ExceptionUtils.getFullStackTrace(e));
					throw new NomsException(e.getMessage());
				}
				
				
			}
			
		
		/**
		 * Gets the lease domain list by user.
		 *
		 * @param lsNo the ls no
		 * @param lsVer the ls ver
		 * @param userOwnedDomain the user owned domain
		 * @return the lease domain list by user
		 */
		public List<TbOrgDomain> getLeaseDomainListByUser(String lsNo, String lsVer,TbOrgDomain userOwnedDomain){
		
			return getLeaseDomainListByUser(lsNo, lsVer, userOwnedDomain,null);
			
		}
		/**
		 * Gets the lease domain list by user.
		 *
		 * @param lsNo the ls no
		 * @param lsVer the ls ver
		 * @param userOwnedDomain the user owned domain
		 * @return the lease domain list by user
		 */
		public List<TbOrgDomain> getLeaseDomainListByUser(String lsNo, String lsVer,
				TbOrgDomain userOwnedDomain,Map<String, Object> modelMap){
			//ls_ver 設定 null
			TbLsMain tbLsMain = null;
			tbLsMain = searchLsMainKey(lsNo, lsVer);
			if(tbLsMain==null){
				lsVer=null;
				tbLsMain= searchLsMainKey(lsNo, lsVer);
			}
			
			
			List<TbOrgDomain> tbOrgDomainList = domainService.getAccessDomainByUser(userOwnedDomain);
			List<TbOrgDomain> addList = new ArrayList<TbOrgDomain>();
			if (tbLsMain != null) {
				if (StringUtils.isNotEmpty(tbLsMain.getOP_DOMAIN())) {
					String[] domains = tbLsMain.getOP_DOMAIN().split(",");
					for (String domain : domains) {
						for (TbOrgDomain tbOrgDomain : tbOrgDomainList) {
							if (StringUtils.equals(tbOrgDomain.getID(), domain)) {
								addList.add(tbOrgDomain);
							}
						}
					}
				}
			}
			if(modelMap!=null){
				modelMap.put("lsMain", tbLsMain);
				modelMap.put("siteDomain", addList);
			}
			return addList;
			
		}
		
		/**
		 * Gets the lease domain list by user.
		 *
		 * @param lsNo the ls no
		 * @param lsVer the ls ver
		 * @param userOwnedDomain the user owned domain
		 * @return the lease domain list by user
		 */
		public List<TbOrgDomain> getLeaseAddedDomainListByUser(String appSeq,
				TbOrgDomain userOwnedDomain,Map<String, Object> modelMap){
			//ls_ver 設定 null
			TbLsMainAdded tbLsMain = null;
			TbLsMainAddedExample example =new TbLsMainAddedExample();
			example.createCriteria().andAPP_SEQEqualTo(appSeq);
			List<TbLsMainAdded> mainAddList= ls001m3Dao.searchTbLsMainAdded(example);
			if(mainAddList==null || mainAddList.size()==0){
				throw new NomsException("MAIN ADDED 找不到 APP SEQ :"+appSeq);
			}
			tbLsMain = mainAddList.get(0);
			
			List<TbOrgDomain> tbOrgDomainList = domainService.getAccessDomainByUser(userOwnedDomain);
			List<TbOrgDomain> addList = new ArrayList<TbOrgDomain>();
			if (tbLsMain != null) {
				if (StringUtils.isNotEmpty(tbLsMain.getOP_DOMAIN())) {
					String[] domains = tbLsMain.getOP_DOMAIN().split(",");
					for (String domain : domains) {
						for (TbOrgDomain tbOrgDomain : tbOrgDomainList) {
							if (StringUtils.equals(tbOrgDomain.getID(), domain)) {
								addList.add(tbOrgDomain);
							}
						}
					}
				}
			}
			if(modelMap!=null){
				modelMap.put("lsMain", tbLsMain);
				modelMap.put("siteDomain", addList);
			}
			return addList;
			
		}
		
		
		/**
		 * 點選站點，撈取資料.
		 *
		 * @param lsNo 				合約編號
		 * @param lsVer 			版次
		 * @param locId 			站點編號
		 * @param addFlag 			增補協議-加裝設備用
		 * @return the ls site location
		 * @throws Exception the exception
		 */
		public LeaseLocationDTO getLocationSitePay4Rent(String lsNo, String lsVer,
				String locId,String addFlag) throws Exception {
			
			//找不到表示是 續約 草稿 ls_ver=null 抓最大版號
//			LeaseMainDTO mainDTO =lS001Dao.getLsMainByNo(lsNo, lsVer);
//			if(mainDTO==null){
//				lsVer=null;
//			}
//			
			//lsVer 忽略抓取最大板次
			List<LeaseLocationDTO> dtolist = lS001Dao.getLsSiteLocationByLsNoVer(
					lsNo, lsVer, locId);
			
			if (dtolist.size() == 0) {
				throw new DataNotFoundException("合約資料找不到或未齊全");
			}
			
			LeaseLocationDTO leaseLocationDTO = dtolist.get(0);
			//lsVer 忽略抓取最大板次
			if(StringUtils.isNotEmpty(addFlag)){
				LsPaymentItem[] rentItems = new LsPaymentItem[]{LsPaymentItem.R};
				leaseLocationDTO.setTbLsLocPaymentList(lS001Dao
						.getLsLocPaymentByLsNoVerLocId(lsNo, lsVer, locId,rentItems));
			}else{
				leaseLocationDTO.setTbLsLocPaymentList(lS001Dao
						.getLsLocPaymentByLsNoVerLocId(lsNo, lsVer, locId,LsPaymentItem.getRentItemType()));
			}
			
//			TbLsLessorExample example = new TbLsLessorExample();
//			//lsVer 忽略抓取最大板次
//			TbLsLessorExample.Criteria criteria_lessor= example.createCriteria().andLS_NOEqualTo(lsNo);
//			if(lsVer!=null && lsVer.length()>0){
//				criteria_lessor.andLS_VEREqualTo(lsVer);
//			}
			
			//.andLS_VEREqualTo(lsVer);
			List<TbLsLessor> tbLsLessorList = getLsLessorByNo(lsNo, lsVer);
			
			
			String tempLessorName = "";
			String tempLessorPaymentMode = "";
			for (TbLsLessor tbLsLessor : tbLsLessorList) {
				if (tempLessorName.length() > 0) {
					tempLessorName += ",";
					tempLessorPaymentMode += ",";
				}
				tempLessorPaymentMode += tbLsLessor.getLESSOR_ID() + ":"
						+ tbLsLessor.getPAYMENT_MODE()+":"+tbLsLessor.getBUSINESS_TAX();
				tempLessorName += tbLsLessor.getLESSOR_ID() + ":"
						+ tbLsLessor.getLESSOR_NAME();
			}
			leaseLocationDTO.setLessorNames(tempLessorName);
			leaseLocationDTO.setAllLessorPaymentMode(tempLessorPaymentMode);

			TbSysLookupExample tbSysLookupExample = new TbSysLookupExample();
			tbSysLookupExample.createCriteria().andTYPEEqualTo("LS_LEASE_RELATION");
			tbSysLookupExample.setOrderByClause("DISPLAY_ORDER");
			List<TbSysLookup> TbSysLookupList = lS001Dao
					.getLsLeaseBldgtype(tbSysLookupExample);
			String tempLsLeaseRelation = "";
			for (TbSysLookup tbSysLookup : TbSysLookupList) {
				if (tempLsLeaseRelation.length() > 0) {
					tempLsLeaseRelation += ";";
				}
				tempLsLeaseRelation += tbSysLookup.getCODE() + ","
						+ tbSysLookup.getNAME();
			}
			leaseLocationDTO.setLsLeaseRelation(tempLsLeaseRelation);

			leaseLocationDTO.setTbLsSiteDTOList(lS001Dao.getLsSiteByLsNoLocId(lsNo,
					lsVer, locId,LsPaymentItem.R));

			if (leaseLocationDTO.getTbLsSiteDTOList() == null
					|| leaseLocationDTO.getTbLsSiteDTOList().size() == 0) {

				List<SiteAlocDetailDTO> dtoList = siteAlocService
						.getNormalSiteAlocByLocationId(locId,
								SiteAlocService.ALOCITEM_RENT);
				List<TbLsSiteDTO> tbLsSiteDTOList = new ArrayList<TbLsSiteDTO>();
				for (SiteAlocDetailDTO dto : dtoList) {
					TbLsSiteDTO tbLsSiteDTO = new TbLsSiteDTO();
					tbLsSiteDTO.setLOCATION_ID(dto.getLocationId());
					tbLsSiteDTO.setPAY_BEGIN_DATE(dto.getEFF_DATE());
					tbLsSiteDTO.setLS_E_DATE(dto.getEND_DATE());
					tbLsSiteDTO.setEXPN_TYPE(dto.getAlocItem());
					tbLsSiteDTO.setALOC_RATIO(dto.getALOC_RATIO());
					tbLsSiteDTO.setSITE_ID(dto.getSiteId());
					tbLsSiteDTO.setSITE_NAME(dto.getSiteName());
					tbLsSiteDTO.setBTS_SITE_ID(dto.getBtsSiteId());
					tbLsSiteDTO.setALOC_ID(dto.getALOC_ID());
					
					tbLsSiteDTOList.add(tbLsSiteDTO);
				}
				leaseLocationDTO.setTbLsSiteDTOList(tbLsSiteDTOList);
			}
			return leaseLocationDTO;
		}
		
		
		/**
		 * 點選站點，撈取資料.
		 *
		 * @param lsNo 			合約編號
		 * @param lsVer 			版次
		 * @param locId 			站點編號
		 * @return the ls site location
		 * @throws Exception the exception
		 */
		public LeaseLocationDTO getLsLocationSiteAddedPay4Rent(String appSeq,
				String locId) throws Exception {
		
			
			//lsVer 忽略抓取最大板次
			List<LeaseLocationDTO> dtolist = lS001Dao.getLsSiteLocationAddedByLsNoVer(appSeq,locId);
				
			
			if (dtolist.size() == 0) {
				throw new DataNotFoundException("合約資料找不到或未齊全");
			}
			
			LeaseLocationDTO leaseLocationDTO = dtolist.get(0);
			//lsVer 忽略抓取最大板次
			leaseLocationDTO.setTbLsLocPaymentList(lS001Dao
					.getLsLocPaymentAddedByLsNoVerLocId(appSeq, locId,LsPaymentItem.getRentItemType()));
			
//			TbLsLessorExample example = new TbLsLessorExample();
//			//lsVer 忽略抓取最大板次
//			TbLsLessorExample.Criteria criteria_lessor= example.createCriteria().andLS_NOEqualTo(lsNo);
//			if(lsVer!=null && lsVer.length()>0){
//				criteria_lessor.andLS_VEREqualTo(lsVer);
//			}
			
			//.andLS_VEREqualTo(lsVer);
			List<TbLsLessor> tbLsLessorList = lS001Dao.getLsLessorAddedByAppSeq(appSeq);
			
			
			String tempLessorName = "";
			String tempLessorPaymentMode = "";
			for (TbLsLessor tbLsLessor : tbLsLessorList) {
				if (tempLessorName.length() > 0) {
					tempLessorName += ",";
					tempLessorPaymentMode += ",";
				}
				tempLessorPaymentMode += tbLsLessor.getLESSOR_ID() + ":"
						+ tbLsLessor.getPAYMENT_MODE()+":"+tbLsLessor.getBUSINESS_TAX();
				tempLessorName += tbLsLessor.getLESSOR_ID() + ":"
						+ tbLsLessor.getLESSOR_NAME();
			}
			leaseLocationDTO.setLessorNames(tempLessorName);
			leaseLocationDTO.setAllLessorPaymentMode(tempLessorPaymentMode);

			TbSysLookupExample tbSysLookupExample = new TbSysLookupExample();
			tbSysLookupExample.createCriteria().andTYPEEqualTo("LS_LEASE_RELATION");
			tbSysLookupExample.setOrderByClause("DISPLAY_ORDER");
			List<TbSysLookup> TbSysLookupList = lS001Dao
					.getLsLeaseBldgtype(tbSysLookupExample);
			String tempLsLeaseRelation = "";
			for (TbSysLookup tbSysLookup : TbSysLookupList) {
				if (tempLsLeaseRelation.length() > 0) {
					tempLsLeaseRelation += ";";
				}
				tempLsLeaseRelation += tbSysLookup.getCODE() + ","
						+ tbSysLookup.getNAME();
			}
			leaseLocationDTO.setLsLeaseRelation(tempLsLeaseRelation);

			leaseLocationDTO.setTbLsSiteDTOList(lS001Dao.getLsSiteAddedByLsNoLocId(appSeq, locId,LsPaymentItem.R));

			if (leaseLocationDTO.getTbLsSiteDTOList() == null
					|| leaseLocationDTO.getTbLsSiteDTOList().size() == 0) {

				List<SiteAlocDetailDTO> dtoList = siteAlocService
						.getNormalSiteAlocByLocationId(locId,
								SiteAlocService.ALOCITEM_RENT);
				List<TbLsSiteDTO> tbLsSiteDTOList = new ArrayList<TbLsSiteDTO>();
				for (SiteAlocDetailDTO dto : dtoList) {
					TbLsSiteDTO tbLsSiteDTO = new TbLsSiteDTO();
					tbLsSiteDTO.setLOCATION_ID(dto.getLocationId());
					tbLsSiteDTO.setPAY_BEGIN_DATE(dto.getEFF_DATE());
					tbLsSiteDTO.setLS_E_DATE(dto.getEND_DATE());
					tbLsSiteDTO.setEXPN_TYPE(dto.getAlocItem());
					tbLsSiteDTO.setALOC_RATIO(dto.getALOC_RATIO());
					tbLsSiteDTO.setSITE_ID(dto.getSiteId());
					tbLsSiteDTO.setSITE_NAME(dto.getSiteName());
					tbLsSiteDTO.setBTS_SITE_ID(dto.getBtsSiteId());
					tbLsSiteDTO.setALOC_ID(dto.getALOC_ID());
					
					tbLsSiteDTOList.add(tbLsSiteDTO);
				}
				leaseLocationDTO.setTbLsSiteDTOList(tbLsSiteDTOList);
			}
			return leaseLocationDTO;
		}
		
		
		
		
		/**
		 * 租金Tab 存檔.
		 *
		 * @param leaseLocationDTO the lease location dto
		 */
		@Transactional
		public void saveUpdateLeaseLocation4Elec(LeaseLocElecDTO leaseLocElecDTO )throws NomsException{
				
				try {
					List<TbLsLocPaymentDTO> locPayList = leaseLocElecDTO.getTbLsLocPaymentList();
					List<TbLsSiteDTO> siteAlocDetailList = leaseLocElecDTO.getTbLsSiteDTOList();
					List<TbLsLocElecDTO> lsLocElecList = leaseLocElecDTO.getLsLocElecList();
					
					if(lsLocElecList==null || lsLocElecList.size()==0){
						throw new NomsException("電錶不可為空");
					}
					
					LeaseMainDTO  mainDTO=lS001Dao.getLsMainByNo(leaseLocElecDTO.getLS_NO(), leaseLocElecDTO.getLS_VER());
					
					if(mainDTO==null){
						throw new NomsException("主約資訊不存在,編號:"+leaseLocElecDTO.getLS_NO()+","+leaseLocElecDTO.getLS_VER());
					}
//					TbLsAppFormExample appFormExample = new TbLsAppFormExample();
//					List<String> elecSubName = new ArrayList<>();
//					elecSubName.add("請電");
//					elecSubName.add("借電");
//					appFormExample.createCriteria().andAPP_TYPEEqualTo("NEW").andSUB_NAMEIn(elecSubName);
//					List<TbLsAppForm> elecFormList = appFormDao.findByExample(appFormExample);
//					
					//Form ID  lookup value1
					List<TbSysLookup> elecFormList = lookupService.getByType("LS_ELEC_MODE");
					
					
					lS001Dao.deleteLsElec(leaseLocElecDTO.getLS_NO(),leaseLocElecDTO.getLS_VER(), leaseLocElecDTO.getLOCATION_ID());
					for(TbLsLocElec elec :lsLocElecList){
						elec.setLS_NO(leaseLocElecDTO.getLS_NO());
						elec.setLS_VER(leaseLocElecDTO.getLS_VER());
						elec.setLOCATION_ID(leaseLocElecDTO.getLOCATION_ID());
					
						for(TbSysLookup aForm:elecFormList){
							if(aForm.getCODE().equals(elec.getELEC_MODE())){
								elec.setFORM_ID(aForm.getVALUE1());
								break;
							}
						}
						lS001Dao.insertLsLocElec(elec);
					}
					
					//站點資訊
					lS001Dao.deleteLsSite(leaseLocElecDTO.getLS_NO(),leaseLocElecDTO.getLS_VER(), leaseLocElecDTO.getLOCATION_ID(),LsPaymentItem.E);
//					TbLsSite tblsSite;
					for(TbLsSiteDTO siteDTO:siteAlocDetailList){
//						tblsSite = new TbLsSite();
//						BeanUtils.copyProperties(siteDTO,tblsSite);
						siteDTO.setLOCATION_ID(leaseLocElecDTO.getLOCATION_ID());
						siteDTO.setLS_VER(leaseLocElecDTO.getLS_VER());
						siteDTO.setLS_NO(leaseLocElecDTO.getLS_NO());
				
						siteDTO.setLS_E_DATE(mainDTO.getLS_E_DATE());
						siteDTO.setEFF_DATE(mainDTO.getLS_S_DATE());
						siteDTO.setEND_DATE(mainDTO.getLS_E_DATE());
						
				
						lS001Dao.insertLsSite(siteDTO);
					}
					
					//付款資訊
					lS001Dao.deleteLsLocPayment(leaseLocElecDTO.getLS_NO(),leaseLocElecDTO.getLS_VER(), leaseLocElecDTO.getLOCATION_ID(),LsPaymentItem.getElecItemType());

					for(TbLsLocPayment tblocPayment:locPayList){
						tblocPayment.setLOCATION_ID(leaseLocElecDTO.getLOCATION_ID());
						tblocPayment.setLS_VER(leaseLocElecDTO.getLS_VER());
						tblocPayment.setLS_NO(leaseLocElecDTO.getLS_NO());
//						tblocPayment.setFIRST_INCR(leaseLocationDTO.getNEXT_INCR());
//						
						tblocPayment.setEFF_DATE(mainDTO.getLS_S_DATE());
						tblocPayment.setEND_DATE(mainDTO.getLS_E_DATE());
						lS001Dao.insertLsLocPayment(tblocPayment);
					}
					

				} catch (Exception e) {
					log. error(ExceptionUtils.getFullStackTrace(e));
					throw new NomsException(e.getMessage());
				}
				
				
			}
			
		
		/**
		 * 
		 * 取得電費級距表資訊
		 * 
		 * */
		public List<TbLsElecRange> getLeaseElecRange(){
			return lS001Dao.getEffectiveElecRangeSetting();
		}
		
		/**
		 * clone others table exclude main for 續 換 一解一簽 
		 *
		 * @param leaseLocationDTO the lease location dto
		 */
	@Transactional
	public void insertMaxLeaseExcludeAppMain(
			LeaseMainDTO leaseMainDTO,LsEnumCommon.LsTypeEnum lsType) throws NomsException {
		if(leaseMainDTO.getORI_LS_NO()==null || leaseMainDTO.getORI_LS_NO().length()==0){
			throw new DataNotFoundException("來源合約號碼:"+leaseMainDTO.getORI_LS_NO()+" 為空");
		}
		if(leaseMainDTO.getLS_NO()==null || leaseMainDTO.getLS_NO().length()==0){
			throw new NomsException("合約編號錯誤為空");
		}
		String newLsVer= "1";
		try {
			
			//判斷如果是換約 版號Reset
			if(lsType.equals(LsEnumCommon.LsTypeEnum.ReSignLease) ){
				newLsVer= "1";
			}else{
				//續約 其他 就不改
				newLsVer=leaseMainDTO.getLS_VER();
			}
			
			
			String opDomain =leaseMainDTO.getOP_DOMAIN();
			
			// TB_LS_LOCATION
			List<LeaseLocationDTO> locDTOList = lS001Dao.getLeaseLocation(
					leaseMainDTO.getORI_LS_NO(), null, null);
			for (LeaseLocationDTO dto : locDTOList) {
				//如果不在允許區域內跳過
				if(opDomain.indexOf(dto.getDOMAIN())<0){
					continue;
				}
				
				dto.setLS_NO(leaseMainDTO.getLS_NO());
				dto.setLS_VER(newLsVer);
				dto.setLS_E_DATE(leaseMainDTO.getLS_E_DATE());
				dto.setEFF_DATE(leaseMainDTO.getLS_S_DATE());
				dto.setEND_DATE(leaseMainDTO.getLS_E_DATE());
				dto.setCR_USER(leaseMainDTO.getCR_USER());
				dto.setMD_USER(leaseMainDTO.getMD_USER());
				dto.setAPP_USER(leaseMainDTO.getAPP_USER());
				dto.setMD_TIME(leaseMainDTO.getMD_TIME());
				dto.setCR_TIME(leaseMainDTO.getCR_TIME());
				// dto.setAPP_TIME(leaseMainDTO.get_TIME());

				//判斷如果是續約 存檔對象 改為 Added
				if(lsType.equals(LsEnumCommon.LsTypeEnum.ContinueLease) ){
					TbLsLocationAdded loc_added = new TbLsLocationAdded();
					BeanUtils.copyProperties(dto, loc_added);	
					loc_added.setADDED_STATE("AFTER");
					loc_added.setAPP_SEQ(leaseMainDTO.getAPP_SEQ());
					ls001m3Dao.addTbLsLocationAdded(loc_added);
				}else{
					lS001Dao.saveNewLeaseLocation(dto);
				}
				
				
				
				
				// LOC_Payment
				List<TbLsLocPaymentDTO> rentPayList = lS001Dao.getLsLocPaymentByLsNoVerLocId(leaseMainDTO.getORI_LS_NO(),null,dto.getLOCATION_ID(), null);
				for (TbLsLocPaymentDTO payDTO : rentPayList) {
					//有租金選項且是租金項目 , 或是 有電費選項且是電費項目
					if ((leaseMainDTO.hasRentCat()&&LsPaymentItem.isRentItem(payDTO.getPAYMENT_ITEM()))
						|| (leaseMainDTO.hasElecCat()&&LsPaymentItem.isElecItem(payDTO.getPAYMENT_ITEM()))) {
						for (TbLsLessor l : leaseMainDTO.getLessors()) {
							//出租人存在
							if (l.getLESSOR_ID().equals(payDTO.getLESSOR_ID())) {
								payDTO.setLS_NO(leaseMainDTO.getLS_NO());
								payDTO.setLS_VER(newLsVer);
								payDTO.setLESSOR_NAME(l.getLESSOR_NAME());
								payDTO.setPAYMENT_MODE(l.getPAYMENT_MODE());
								payDTO.setBUSINESS_TAX(l.getBUSINESS_TAX());
								payDTO.setEFF_DATE(leaseMainDTO.getLS_S_DATE());
								payDTO.setEND_DATE(leaseMainDTO.getLS_E_DATE());
								payDTO.setCR_USER(leaseMainDTO.getCR_USER());
								payDTO.setMD_USER(leaseMainDTO.getMD_USER());
								payDTO.setMD_TIME(leaseMainDTO.getMD_TIME());
								payDTO.setCR_TIME(leaseMainDTO.getCR_TIME());
								if(lsType.equals(LsEnumCommon.LsTypeEnum.ContinueLease) ){
									TbLsLocPaymentAdded tbLsLocPaymentAdded = new TbLsLocPaymentAdded();
									BeanUtils.copyProperties(payDTO, tbLsLocPaymentAdded);	
									tbLsLocPaymentAdded.setADDED_STATE("AFTER");
									tbLsLocPaymentAdded.setAPP_SEQ(leaseMainDTO.getAPP_SEQ());
									ls001m3Dao.insertLsLocPaymentAdded(tbLsLocPaymentAdded);
								}else{
									lS001Dao.insertLsLocPayment(payDTO);
								}
								
								break;
							}

						}
					}

				}
				
				if(leaseMainDTO.hasElecCat()){
					//Elec
					List<TbLsLocElecDTO> elecList =lS001Dao.getLsLocElecByLsNoVer(leaseMainDTO.getORI_LS_NO(), null,dto.getLOCATION_ID());
					for(TbLsLocElecDTO elecDTO: elecList){
						elecDTO.setLS_NO(leaseMainDTO.getLS_NO());
						elecDTO.setLS_VER(newLsVer);
						elecDTO.setELEC_BEGIN_DATE(leaseMainDTO.getLS_S_DATE());
						elecDTO.setELEC_END_DATE(leaseMainDTO.getLS_E_DATE());
						elecDTO.setCR_USER(leaseMainDTO.getCR_USER());
						elecDTO.setMD_USER(leaseMainDTO.getMD_USER());
						elecDTO.setMD_TIME(leaseMainDTO.getMD_TIME());
						elecDTO.setCR_TIME(leaseMainDTO.getCR_TIME());
						if(lsType.equals(LsEnumCommon.LsTypeEnum.ContinueLease) ){
							TbLsLocElecAdded elecRecord = new TbLsLocElecAdded();
							BeanUtils.copyProperties(elecDTO, elecRecord);	
							elecRecord.setADDED_STATE("AFTER");
							elecRecord.setAPP_SEQ(leaseMainDTO.getAPP_SEQ());
							ls001m3Dao.insertTbLsLocElecAdded(elecRecord);
						}else{
							lS001Dao.insertLsLocElec(elecDTO);
						}
					}
				}
				//res exchange
				if(leaseMainDTO.hasExchangeCat()){
					List<TbLsResExch> resList=lS001Dao.getLsResExchByLsNoVer(leaseMainDTO.getORI_LS_NO(), null,dto.getLOCATION_ID());
					for(TbLsResExch resDTO: resList){
						resDTO.setLS_NO(leaseMainDTO.getLS_NO());
						resDTO.setLS_VER(newLsVer);
						resDTO.setCR_USER(leaseMainDTO.getCR_USER());
						resDTO.setMD_USER(leaseMainDTO.getMD_USER());
						resDTO.setMD_TIME(leaseMainDTO.getMD_TIME());
						resDTO.setCR_TIME(leaseMainDTO.getCR_TIME());
						if(lsType.equals(LsEnumCommon.LsTypeEnum.ContinueLease) ){
							TbLsResExchAdded tbLsResExchAdded = new TbLsResExchAdded();
							BeanUtils.copyProperties(resDTO, tbLsResExchAdded);	
							tbLsResExchAdded.setADDED_STATE("AFTER");
							tbLsResExchAdded.setAPP_SEQ(leaseMainDTO.getAPP_SEQ());
							ls001m3Dao.insertTbLsResExchAdded(tbLsResExchAdded);
						}else{
							lS001Dao.insertTbLsResExch(resDTO);
						}
						
					}
					
					List<TbLsResExchAloc> resAlocList = lS001Dao.getTbLsResExchAlocByLsNoVerLoc(leaseMainDTO.getORI_LS_NO(), null, dto.getLOCATION_ID());
					for(TbLsResExchAloc resDTO: resAlocList){
						resDTO.setLS_NO(leaseMainDTO.getLS_NO());
						resDTO.setLS_VER(newLsVer);
						resDTO.setCR_USER(leaseMainDTO.getCR_USER());
						resDTO.setMD_USER(leaseMainDTO.getMD_USER());
						resDTO.setMD_TIME(leaseMainDTO.getMD_TIME());
						resDTO.setCR_TIME(leaseMainDTO.getCR_TIME());
						
						if(lsType.equals(LsEnumCommon.LsTypeEnum.ContinueLease) ){
							TbLsResExchAlocAdded tbLsResExchAlocAdded = new TbLsResExchAlocAdded();
							BeanUtils.copyProperties(resDTO, tbLsResExchAlocAdded);	
							tbLsResExchAlocAdded.setADDED_STATE("AFTER");
							tbLsResExchAlocAdded.setAPP_SEQ(leaseMainDTO.getAPP_SEQ());
							ls001m3Dao.insertTbLsResExchAlocAdded(tbLsResExchAlocAdded);
							
						}else{
							lS001Dao.insertTbLsResExchAloc(resDTO);
						}
						
					}
				}
				
				
				
				//ls_site
				List<TbLsSiteDTO> siteList= lS001Dao.getLsSiteByLsNoLocId(leaseMainDTO.getORI_LS_NO(),null,dto.getLOCATION_ID(),null);
				for(TbLsSiteDTO siteDTO:siteList){
					if ((leaseMainDTO.hasRentCat()&&LsPaymentItem.isRentItem(siteDTO.getEXPN_TYPE()))
							|| (leaseMainDTO.hasElecCat()&&LsPaymentItem.isElecItem(siteDTO.getEXPN_TYPE()))) {
						siteDTO.setLS_NO(leaseMainDTO.getLS_NO());
						siteDTO.setLS_VER(newLsVer);
						siteDTO.setEFF_DATE(leaseMainDTO.getLS_S_DATE());
						siteDTO.setEND_DATE(leaseMainDTO.getLS_E_DATE());
						siteDTO.setCR_USER(leaseMainDTO.getCR_USER());
						siteDTO.setMD_USER(leaseMainDTO.getMD_USER());
						siteDTO.setMD_TIME(leaseMainDTO.getMD_TIME());
						siteDTO.setCR_TIME(leaseMainDTO.getCR_TIME());
						if(lsType.equals(LsEnumCommon.LsTypeEnum.ContinueLease) ){
							TbLsSiteAdded tbLsSiteAdded = new TbLsSiteAdded();
							BeanUtils.copyProperties(siteDTO, tbLsSiteAdded);	
							tbLsSiteAdded.setADDED_STATE("AFTER");
							tbLsSiteAdded.setAPP_SEQ(leaseMainDTO.getAPP_SEQ());
							ls001m3Dao.insertTbLsSiteAdded(tbLsSiteAdded);
						}else{
							lS001Dao.insertLsSite(siteDTO);	
						}
					}
					
				}
				
			}
			

		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException(e.getMessage());
		}

	}
	
	
	@Transactional
	public void insertContinueLease(LeaseMainDTO leaseMainDTO,String lessorJson,
			String rewardJsonArr)throws NomsException{
		
		if(leaseMainDTO==null || leaseMainDTO.getLS_NO()==null || StringUtils.isEmpty(leaseMainDTO.getLS_NO())){
			throw new NomsException("合約編號不存在");
		}
			
		try {
			String appSeq = uniqueSeqService.getNextLeaseAppNo(leaseMainDTO.getLS_NO());
			int newLsVer =2;
			
			newLsVer=Integer.parseInt(leaseMainDTO.getLS_VER())+1;
			
			leaseMainDTO.setLS_VER(String.valueOf(newLsVer));
			leaseMainDTO.setLS_NO(leaseMainDTO.getORI_LS_NO());
			
			Date md_time = new Date();
			
			
			String pldgCl = leaseMainDTO.getPLDG_CL();
			leaseMainDTO.setPLDG_CL(StringUtils.equals("on", pldgCl) ? "Y" : "N");
			String unfrmt = leaseMainDTO.getUNFRMT();
			leaseMainDTO.setUNFRMT(StringUtils.equals("on", unfrmt) ? "Y" : "N");
			String formId = leaseMainDTO.getFORM_ID();
			leaseMainDTO.setFORM_ID(StringUtils.equals("on", unfrmt) ? "" : formId);
			leaseMainDTO.setLS_STATUS(LsEnumCommon.LsStatusEnum.lsStatus0.getLsStatus());
			if (leaseMainDTO.getLS_E_DATE() == null) {
				leaseMainDTO.setLS_E_DATE(DateUtils.ULIMITEDDATE);
			}
		
//			TbLsApp lsApp=generateTbLsApp(leaseMainDTO.getLS_NO(), String.valueOf(newLsVer), appSeq, leaseMainDTO.getAPP_USER(),leaseMainDTO.getAPP_USER_DEPT(), LsTypeEnum.ContinueLease);
			TbLsApp lsApp=generateTbLsApp(leaseMainDTO.getLS_NO(), String.valueOf(newLsVer), appSeq, getLoginUser().getUsername(),getLoginUser().getDeptId(), LsTypeEnum.ContinueLease);
			lsApp.setADD_DATA(leaseMainDTO.getCONTI_EXTEND_TYPE());
			lS001Dao.insertLeaseApp(lsApp);
			TbLsMainAdded added= new TbLsMainAdded();
			added.setAPP_SEQ(appSeq);

			leaseMainDTO.setMD_USER(getLoginUser().getUsername());
			leaseMainDTO.setMD_TIME(md_time);
			leaseMainDTO.setCR_USER(getLoginUser().getUsername());
			leaseMainDTO.setCR_TIME(md_time);
			leaseMainDTO.setAPP_SEQ(appSeq);
			
			org.springframework.beans.BeanUtils.copyProperties( leaseMainDTO,added);
			added.setADDED_STATE("AFTER");
			
			
			ls001m3Dao.insertTbLsMainAdded(added);
			
			// 合約出租人檔
			List<TbLsLessor> lessList = leaseMainDTO.getLessors();
			if (StringUtils.isNotBlank(lessorJson)) {
				String[] lessorArray = StringUtils.split(lessorJson, ";");
				for (String lessorStr : lessorArray) {
					TbLsLessorAdded tbLsLessorAdd=new TbLsLessorAdded(); 
					TbLsLessor tbLsLessor=parseLessorFromJson(lessorStr, leaseMainDTO);
					BeanUtils.copyProperties(tbLsLessor,tbLsLessorAdd);
					log.info(tbLsLessor.toString());
					tbLsLessorAdd.setAPP_SEQ(appSeq);
					tbLsLessorAdd.setADDED_STATE("AFTER");
					ls001m3Dao.addLsLessorAdded(tbLsLessorAdd);
					lessList.add(tbLsLessor);
				}
			}
			leaseMainDTO.setLessors(lessList);

			// 合約回饋檔
			List<TbLsReward> rewardList = new ArrayList<TbLsReward>();
			if (StringUtils.isNotBlank(rewardJsonArr)) {
				log.info("rewardJsonArr: " + rewardJsonArr);
				rewardList = parseRewardFromJson(rewardJsonArr, leaseMainDTO.getLS_NO(), leaseMainDTO.getLS_VER());
			}

			if (rewardList.size() > 0) {
				for (TbLsReward tlr : rewardList) {
					log.info(tlr.toString());
					
					TbLsRewardAdded tbLsRewardAdded = new TbLsRewardAdded();
					BeanUtils.copyProperties(tlr,tbLsRewardAdded);
					tbLsRewardAdded.setAPP_SEQ(appSeq);
					tbLsRewardAdded.setADDED_STATE("AFTER");
					ls001m3Dao.addTbLsRewardAdded(tbLsRewardAdded);
					
				}
				
				leaseMainDTO.setRewardList(rewardList);
			}
			

			insertMaxLeaseExcludeAppMain(leaseMainDTO, LsTypeEnum.ContinueLease);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new NomsException(e.getMessage());
		}
		
		
	}	

	
	/**
	 * Update lease main.
	 *
	 * @param lsMain the ls main
	 */
	@Transactional
	public void updateLeaseMainAdded(LeaseMainDTO lsMain) {
		try {
			
			if(StringUtils.isBlank(lsMain.getAPP_SEQ())){
				throw new NomsException("APP SEQ 不得為空"); 
				
			}
			
			TbLsMainAddedExample mainAddExample = new TbLsMainAddedExample();
			mainAddExample.createCriteria().andAPP_SEQEqualTo(lsMain.getAPP_SEQ());
			
			String pldgCl = lsMain.getPLDG_CL();
			lsMain.setPLDG_CL(StringUtils.equals("on", pldgCl) ? "Y" : "N");
			String unfrmt = lsMain.getUNFRMT();
			lsMain.setUNFRMT(StringUtils.equals("on", unfrmt) ? "Y" : "N");
			String formId = lsMain.getFORM_ID();
			lsMain.setFORM_ID(StringUtils.equals("on", unfrmt) ? "" : formId);
			if (lsMain.getLS_E_DATE() == null) {
				lsMain.setLS_E_DATE(DateUtils.ULIMITEDDATE);
			}
			lsMain.setMD_USER(getLoginUser().getUsername());
			lsMain.setMD_TIME(new Date());
			
//			TbLsAppExample example1 = new TbLsAppExample();
//			example1.createCriteria().andLS_NOEqualTo(lsMain.getLS_NO()).andLS_VEREqualTo(lsMain.getLS_VER());
//			TbLsApp lsApp = lS001Dao.searchLsApp(example1).get(0);
//			lsMain.setAPP_SEQ(lsApp.getAPP_SEQ());
			
			//
//			LeaseMainDTO currLease = lS001Dao.getLsMainByNo(lsMain.getLS_NO(), lsMain.getLS_VER());
//			String currCarriers = currLease.getEXCH_CARRIERS();
//			String currExchItem = currLease.getEXCH_ITEM();
//			if (!StringUtils.equals(lsMain.getLS_KIND(), "RESCHG")) {
//				lsMain.setEXCH_CARRIERS(null);
//				lsMain.setEXCH_ITEM(null);
//			}
		
			ls001m3Dao.delTbLsMainAdded(mainAddExample);
			TbLsMainAdded mainAdded = new TbLsMainAdded();
			BeanUtils.copyProperties(lsMain, mainAdded);
			mainAdded.setAPP_SEQ(lsMain.getAPP_SEQ());
			mainAdded.setADDED_STATE("AFTER");
			ls001m3Dao.insertTbLsMainAdded(mainAdded);
			
			//同步更新站台、站點起迄日
			//TB_LS_SITE 站台
			TbLsSiteAddedExample siteAddedExample = new TbLsSiteAddedExample();
			siteAddedExample.createCriteria().andAPP_SEQEqualTo(lsMain.getAPP_SEQ());
			
			TbLsSiteAdded site = new TbLsSiteAdded();
			site.setEFF_DATE(lsMain.getLS_S_DATE());	//金額生效起日:同合約承租起日
			site.setEND_DATE(lsMain.getLS_E_DATE());	//金額生效迄日:同合約承租起日
			site.setLS_E_DATE(lsMain.getLS_E_DATE());	//合約終止日:同合約承租迄日
			site.setMD_USER(getLoginUser().getUsername());
			site.setMD_TIME(new Date());
			ls001m3Dao.updateLsSiteAdded(site, siteAddedExample);
			
			//TB_LS_LOCATION 站點
			TbLsLocationAddedExample locationExample = new TbLsLocationAddedExample();
			locationExample.createCriteria().andAPP_SEQEqualTo(lsMain.getAPP_SEQ());
			TbLsLocationAdded location = new TbLsLocationAdded();
			location.setEFF_DATE(lsMain.getLS_S_DATE());	//金額生效起日:同合約承租起日
			location.setEND_DATE(lsMain.getLS_E_DATE());	//金額生效迄日:同合約承租起日
			location.setLS_E_DATE(lsMain.getLS_E_DATE());	//合約終止日:同合約承租迄日
			location.setMD_USER(getLoginUser().getUsername());
			location.setMD_TIME(new Date());
			ls001m3Dao.updateLeaseLocationAdded(location, locationExample);
			
			
			//TB_LS_LOC_PAYMENT 站點
			TbLsLocPaymentAddedExample paymentAddedExample = new TbLsLocPaymentAddedExample();
			paymentAddedExample.createCriteria().andAPP_SEQEqualTo(lsMain.getAPP_SEQ());
			TbLsLocPaymentAdded loc_payment = new TbLsLocPaymentAdded();
			loc_payment.setEFF_DATE(lsMain.getLS_S_DATE());	//金額生效起日:同合約承租起日
			loc_payment.setEND_DATE(lsMain.getLS_E_DATE());	//金額生效迄日:同合約承租起日
			loc_payment.setMD_USER(getLoginUser().getUsername());
			loc_payment.setMD_TIME(new Date());
			ls001m3Dao.updateLsLocPaymentAdded(loc_payment, paymentAddedExample);
			
			
			//TB_LS_LOC_Elec 站點
			TbLsLocElecAddedExample locElecAddedExample = new TbLsLocElecAddedExample();
			locElecAddedExample.createCriteria().andAPP_SEQEqualTo(lsMain.getAPP_SEQ());
			TbLsLocElecAdded loc_elec_added = new TbLsLocElecAdded();
			loc_elec_added.setELEC_BEGIN_DATE(lsMain.getLS_S_DATE());	//金額生效起日:同合約承租起日
			loc_elec_added.setELEC_END_DATE(lsMain.getLS_E_DATE());	//金額生效迄日:同合約承租起日
			loc_elec_added.setMD_USER(getLoginUser().getUsername());
			loc_elec_added.setMD_TIME(new Date());
			ls001m3Dao.updateLsLocElecAdded(loc_elec_added, locElecAddedExample);
			
			
			
		
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new NomsException(ex.getMessage());
		}
		
	}
	
	
		//*************end Arda*****************
		
		//*************START 資源互換*****************
		/**
		 * 查詢互換站點.
		 *
		 * @param lsNo 			合約編號
		 * @param lsVer 			版次
		 * @param locId 			站點編號
		 * @param domain 			區域
		 * @param zipCode 			郵區號
		 * @return the lease location dto
		 */
		public LeaseLocationDTO queryResExchSite(String lsNo, String lsVer, String locId, String domain, String zipCode,String lsType,String appSeq) {
			LeaseLocationDTO dto = new LeaseLocationDTO();
			TbLsMain tbLsMain=null;
			
			if(!LsEnumCommon.LsTypeEnum.ContinueLease.getLsType().equals(lsType)){
				 tbLsMain = searchLsMainKey(lsNo, lsVer);
			}else{
				 tbLsMain = getLsMainAddedByAppSeq(appSeq);
			}
			
			dto.setLS_NO(lsNo);
			dto.setLS_VER(lsVer);
			dto.setLOCATION_ID(locId);
			dto.setZIP_CODE(zipCode);
			dto.setDOMAIN(domain);
			dto.setLS_E_DATE(tbLsMain.getLS_E_DATE());
			dto.setEFF_DATE(tbLsMain.getLS_S_DATE());
			dto.setEND_DATE(tbLsMain.getLS_E_DATE());
			dto.setCR_USER(getLoginUser().getUsername());
			dto.setCR_TIME(DateUtils.today());
			dto.setMD_USER(getLoginUser().getUsername());
			dto.setMD_TIME(DateUtils.today());
			//解約清算
			dto.setPLDG_CL(tbLsMain.getPLDG_CL());
			getLeaseByLocation(dto);
			return dto;
		}
		
		
		/**
		 * 新增or更新資源互換
		 *
		 * @param resExchJson the ResExch json arr
		 * @throws JSONException the JSON exception
		 * @throws ParseException the parse exception
		 * @throws IOException 
		 * @throws JsonMappingException 
		 * @throws JsonParseException 
		 */
		@Transactional
		public void saveUpdateResExch(String resExchJson, String lsNo, String lsVer, String mainLocId)
				 throws JSONException,ParseException, 
				 		JsonParseException, JsonMappingException,
				 		IOException {
			Date now = new Date();
			List<TbLsResExchDTO> list = new ArrayList<TbLsResExchDTO>();
			JSONArray jsonArray = new JSONArray(resExchJson);
			//存TB_LS_RES_EXCH
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				TbLsResExchDTO tlre = new ObjectMapper().readValue(obj.toString(), TbLsResExchDTO.class);
				tlre.setLOCATION_ID(mainLocId);
				tlre.setLS_NO(lsNo);
				tlre.setLS_VER(lsVer);
				tlre.setCR_TIME(now);
				tlre.setCR_USER(getLoginUser().getUsername());
				tlre.setMD_TIME(now);
				tlre.setMD_USER(getLoginUser().getUsername());
				lS001Dao.deleteLsResExch(tlre.getLS_NO(), tlre.getLS_VER(), tlre.getLOCATION_ID());
				list.add(tlre);
			}
//			List<SiteAlocDetailDTO> siteAlocDetalList = siteAlocService.getExchangeSiteAlocByLocationId(locId, mainLocId, alocItem, carrierId);
			
			//存TB_LS_RES_EXCH_ALOC
			LeaseMainDTO lsMain = lS001Dao.getLsMainByNo(lsNo, lsVer);
			for (TbLsResExchDTO data : list) {
				log.info(data.toString());
				lS001Dao.insertTbLsResExch(data);
				String jsonExchSiteAloc = data.getExchSiteAloc();
				JSONArray jsonExchSiteAlocArray = new JSONArray(jsonExchSiteAloc);
				for (int i = 0; i < jsonExchSiteAlocArray.length(); i++) {
					JSONObject objExchSiteAloc = jsonExchSiteAlocArray.getJSONObject(i);
					SiteAlocDetailDTO sadd = new ObjectMapper().readValue(objExchSiteAloc.toString(), SiteAlocDetailDTO.class);
					insertResExchAloc(lsMain, data, sadd);
				}
			}
			
		}
		
		/**
		 * 點選站點，撈取資源互換資料.
		 *
		 * @param lsNo 			合約編號
		 * @param lsVer 			版次
		 * @param locId 			站點編號
		 * @return the ls site location
		 * @throws Exception the exception
		 */
		public List<TbLsResExchDTO> getLocationSitePay4Exch(String lsNo, String lsVer,
				String locId) throws Exception {
			List<TbLsResExchDTO> list = new ArrayList<TbLsResExchDTO>();
			try {
				list = lS001Dao.loadTbLsResExchList(lsNo, lsVer, locId);
				
			} catch (DataNotFoundException e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				throw e;
			}
			
			
			return list;
		}
		/**
		 * 點選站點，撈取資源互換資料.
		 *
		 * @param lsNo 			合約編號
		 * @param lsVer 			版次
		 * @param locId 			站點編號
		 * @return the ls site location
		 * @throws Exception the exception
		 */
		public List<TbLsResExchDTO> getLocationSiteAddedPay4Exch(String appSeq,
				String locId) throws Exception {
			List<TbLsResExchDTO> list = new ArrayList<TbLsResExchDTO>();
			try {
				list = lS001Dao.loadTbLsResExchAddedList(appSeq, locId);
				
			} catch (DataNotFoundException e) {
				log.error(ExceptionUtils.getFullStackTrace(e));
				throw e;
			}
			
			
			return list;
		}
		
		
		
		public void insertResExchAloc(LeaseMainDTO lsMain, TbLsResExch data, SiteAlocDetailDTO sadd){
			Date now = new Date();
			TbLsResExchAloc tbLsResExchAloc = new TbLsResExchAloc();
			tbLsResExchAloc.setLOCATION_ID(data.getLOCATION_ID());
			tbLsResExchAloc.setEXCH_LOC_ID(data.getEXCH_LOC_ID());
			tbLsResExchAloc.setSITE_ID(sadd.getSiteId());
			tbLsResExchAloc.setALOC_ITEM(sadd.getAlocItem());
			tbLsResExchAloc.setALOC_ID(sadd.getALOC_ID());
			tbLsResExchAloc.setLS_NO(data.getLS_NO());
			tbLsResExchAloc.setLS_VER(data.getLS_VER());
			tbLsResExchAloc.setCR_TIME(now);
			tbLsResExchAloc.setCR_USER(getLoginUser().getUsername());
			tbLsResExchAloc.setMD_TIME(now);
			tbLsResExchAloc.setMD_USER(getLoginUser().getUsername());
			
			lS001Dao.insertTbLsResExchAloc(tbLsResExchAloc);
		}
		//*************END 資源互換*****************
		
		
		/**
		 * 用電方式 資料搜尋.
		 *
		 * @param lsNo            合約編號
		 * @param lsVer            版次
		 * @return the list
		 */
		public List<TbLsLocElec> searchLsLocElecToTermination(String lsNo, String lsVer) {
			TbLsLocElecExample example = new TbLsLocElecExample();
			example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
			List<TbLsLocElec> tbLsLocElecList=lS001Dao.searchLsLocElecByExample(example);
			for(TbLsLocElec list :tbLsLocElecList){
				TbSysLookupExample lookUpexample = new TbSysLookupExample();
				lookUpexample.createCriteria().andTYPEEqualTo("LS_ELEC_MODE").andCODEEqualTo(StringUtils.isNotEmpty(list.getELEC_MODE())?list.getELEC_MODE():"");
				List<TbSysLookup> lookupList = lS001Dao.getLsLeaseBldgtype(lookUpexample);
				if(lookupList.size() > 0){
					list.setELEC_MODE(lookupList.get(0).getNAME());
				}else{
					list.setELEC_MODE("");
				}
			}
			return tbLsLocElecList;
		}
		
		/**
		 * 
		 * @param appSeq 申請流水號
		 * @return object
		 */
		public TbLsApp selectLsAppByAppSeq(String  appSeq){
			return ls001m3Dao.searchTbLsApp(appSeq);
		}
}

