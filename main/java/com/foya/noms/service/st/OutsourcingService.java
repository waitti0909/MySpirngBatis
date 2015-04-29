package com.foya.noms.service.st;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComItemCat;
import com.foya.dao.mybatis.model.TbComItemCatExample;
import com.foya.dao.mybatis.model.TbComItemMain;
import com.foya.dao.mybatis.model.TbComPoMain;
import com.foya.dao.mybatis.model.TbComPoMainExample;
import com.foya.dao.mybatis.model.TbComPoQuota;
import com.foya.dao.mybatis.model.TbComPoQuotaKey;
import com.foya.dao.mybatis.model.TbSiteOsItem;
import com.foya.dao.mybatis.model.TbSiteOsItemKey;
import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.dao.mybatis.model.TbSiteOutsourcingExample;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbWorkflowCfg;
import com.foya.dao.mybatis.model.TbWorkflowCfgExample;
import com.foya.exception.DataNotFoundException;
import com.foya.exception.NomsException;
import com.foya.noms.dao.common.CompanyQueryDao;
import com.foya.noms.dao.common.PoMainDao;
import com.foya.noms.dao.common.PoQuotaDao;
import com.foya.noms.dao.common.TownDomainTeamDao;
import com.foya.noms.dao.st.OsItemDao;
import com.foya.noms.dao.st.OutsourcingDao;
import com.foya.noms.dao.st.WorkDao;
import com.foya.noms.dao.st.WorkOrderDao;
import com.foya.noms.dao.workflow.WorkflowCfgDao;
import com.foya.noms.dto.basic.CompanyDTO;
import com.foya.noms.dto.basic.ItemMainDTO;
import com.foya.noms.dto.basic.PoItemDTO;
import com.foya.noms.dto.common.PoMainDTO;
import com.foya.noms.dto.common.TownDomainTeamDTO;
import com.foya.noms.dto.st.TbSiteOutsourcingDTO;
import com.foya.noms.enums.EmailType;
import com.foya.noms.enums.OrderStatus;
import com.foya.noms.enums.OutSourceStatus;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.workflow.exception.WorkflowException;
/**
 * 
 * 
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2015/3/10</td>
 * <td>委外超強完美第二版</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Service
public class OutsourcingService extends BaseService {
	
	@Autowired
	private OutsourcingDao outsourcingDao;
	
	@Autowired
	private PoQuotaDao poQuotaDao;
	
	@Autowired
	private WorkflowActionService workflowActionService;
	
	@Autowired
	private EmailTemplateService emailTemplateService;
	
	@Autowired
	private WorkDao workDao;
	
	@Autowired
	private WorkOrderDao workOrderDao;
	
	@Autowired
	private TownDomainTeamDao domainTeamDao;
	
	@Autowired
    private UniqueSeqService uniqueSeqService;
	
	@Autowired
    private PoMainDao poMainDao;
	
	@Autowired
    private OsItemDao osItemDao;
	
	@Autowired
    private CompanyQueryDao companyDao;
	
	@Autowired
	private WorkflowCfgDao workflowCfgDao;
	
	/**
	 * 委外工單-存檔
	 * @param outsource
	 * @param itemIds
	 * @return
	 * @author Charlie Woo
	 */
	@Transactional
	public String save(TbSiteOutsourcing outsource, String itemIds, BigDecimal[] osNumber) {
		String itemIdStr[] = null, osId = null;
		Date currentDate = new Date();
		
		if (StringUtils.isNotBlank(itemIds)){
			itemIds = itemIds.substring(0, itemIds.length()-1);
			itemIdStr = itemIds.split(",");
		}
		
		if (StringUtils.isBlank(outsource.getOS_ID())) {
			osId = uniqueSeqService.getNextOsId(outsource.getORDER_ID());
			outsource.setOS_ID(osId);
			
			// TB_SITE_OUTSOURCING.PO_DOMAIN不是由TB_COM_PO_MAIN來的，是要由該站台所處縣市找區域 notice by Charlie 20150108
			TbSiteWorkOrder order = selectByPrimaryKey(outsource.getORDER_ID());
			String poDomain = getPoDomainByWork(order.getWORK_ID());
			
			outsource.setPO_DOMAIN(poDomain);			
			outsource.setAP_AMOUNT(BigDecimal.ZERO);
			outsource.setPAY_AMOUNT(BigDecimal.ZERO);
			outsource.setSTATUS(OutSourceStatus.OS01.name());
			outsource.setCR_USER(getLoginUser().getUsername());
			outsource.setCR_TIME(currentDate);
			outsource.setMD_USER(getLoginUser().getUsername());
			outsource.setMD_TIME(currentDate);
			insert(outsource);
			
		} else {
			osId = outsource.getOS_ID();
			outsource.setMD_USER(getLoginUser().getUsername());
			outsource.setMD_TIME(new Date());
			updateByPrimaryKeySelective(outsource);
		} 	
		
		//TB_SITE_OS_ITEM
		if (StringUtils.isNotBlank(itemIds)){			
			// 直接丟map 只有 osId 而已 先刪除 後新增
			Map<String, String> map = new HashMap<String, String>();
			map.put("osId", osId);
			deleteSiteOsItem(map);
			for(int i = 0; i < itemIdStr.length; i ++){
				Map<String, String> itemIdMap = new HashMap<String, String>();
				itemIdMap.put("itemId", itemIdStr[i]);
				itemIdMap.put("poId", outsource.getPO_ID().toString());
				TbComItemMain mainItem = selectMainItem(itemIdMap);
				Integer mgrFee = mainItem.getMGR_FEE();	// 管理費
				
				TbSiteOsItem osItem = new TbSiteOsItem();
				osItem.setOS_ID(osId);
				osItem.setPO_ID(outsource.getPO_ID());
				osItem.setITEM_ID(itemIdStr[i]);
				osItem.setNUMBER(osNumber[i]);
				osItem.setAMOUNT(sumItemAmountWithMgrfee(osNumber[i], mainItem.getPRICE(), mgrFee));
				osItem.setAP_NUMBER(osNumber[i]);
				osItem.setAP_AMOUNT(sumItemAmountWithMgrfee(osNumber[i], mainItem.getPRICE(), mgrFee));
				osItem.setPAY_NUMBER(osNumber[i]);
				osItem.setPAY_AMOUNT(sumItemAmountWithMgrfee(osNumber[i], mainItem.getPRICE(), mgrFee));
				osItem.setCR_USER(getLoginUser().getUsername());
				osItem.setCR_TIME(currentDate);
				osItem.setMD_USER(getLoginUser().getUsername());
				osItem.setMD_TIME(currentDate);
				osItemDao.insert(osItem);
			}
		}		
		return outsource.getOS_ID();
	}
	
	/**
	 * 委外申請-申請
	 * @param osId
	 * @param aplDept
	 * @param processType
	 * @return
	 * @throws WorkflowException
	 * @author Charlie Woo
	 */
	@Transactional
	public String outsourceApply(String osId, String aplDept, String processType) throws WorkflowException {
		
		try {
			TbWorkflowCfgExample example = new TbWorkflowCfgExample();
			example.createCriteria().andPROCESS_TYPEEqualTo(processType);
			List<TbWorkflowCfg> cfgs = workflowCfgDao.findByCondition(example);
			TbWorkflowCfg cfg = null;
			if (cfgs.isEmpty()) {
				return "ProcessType : " + processType + "於資料庫未設定！";
			} 
			
			TbSiteOutsourcing outSourcing = getByPrimaryKey(osId);
			if (outSourcing != null) {
				// 檢查目前委外單狀態為有效
				String osStatus = outSourcing.getSTATUS();
				if (!OutSourceStatus.OS01.name().equals(osStatus) && !OutSourceStatus.OS03.name().equals(osStatus)) {
					return "無效的委外單狀態，請刷新至最新資料";
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("poId", outSourcing.getPO_ID().toString());
				map.put("domain", outSourcing.getPO_DOMAIN());
				
				log.debug("==== 委外申請額度檢核開始：PO_ID=" + outSourcing.getPO_ID() + ", DOMAIN=" + outSourcing.getPO_DOMAIN() + " =====");
				// 分區總額檢查(本張)
				Long unUsedQuota = getUnusePoQuota(outSourcing.getPO_ID(), outSourcing.getPO_DOMAIN(), outSourcing.getAMOUNT().longValue());
				if (unUsedQuota == null) return "該廠商PO單未設定分區額度上限資料！區域：" + outSourcing.getPO_DOMAIN();
				if (unUsedQuota < 0) return "該廠商派工總額度(含本次)已超過該區域所設上限！";
				
				// PO單總額檢查(本張)
				Long unUsedAmount = getUnusePoMainPayment(outSourcing.getPO_ID(), outSourcing.getAMOUNT().longValue());
				if (unUsedAmount == null) return "查無此PO單主檔資料！";
				if (unUsedAmount < 0) return "該廠商派工總額度(含本次)已超過PO單總金額！";
				
				if (StringUtils.isEmpty(aplDept)) {
					aplDept = workOrderDao.findOrderByPk(outSourcing.getORDER_ID()).getREP_DEPT();
				}
				// 準備update
				outSourcing.setAPL_USER(getLoginUser().getUsername());
				outSourcing.setAPL_DEPT(aplDept);
				outSourcing.setAPL_TIME(new Date());
				outSourcing.setPAY_AMOUNT(outSourcing.getAMOUNT());
				outSourcing.setMD_USER(getLoginUser().getUsername());
				outSourcing.setMD_TIME(new Date());
				outSourcing.setSTATUS(OutSourceStatus.OS02.name());
				
				cfg = cfgs.get(0);
				// update
				outsourcingDao.updateByPrimaryKeySelective(outSourcing);
				log.debug("apply OS_ID = " + outSourcing.getOS_ID() + " Prepare to call outsource apply workflow.");
				// 呼叫委外申請流程
				workflowActionService.apply(cfg.getPROCESS_TYPE(), outSourcing.getOS_ID(), cfg.getPROCESS_NAME(), 
						emailTemplateService.getMailVarMapForWorkflow(EmailType.WORKFLOW_TODO_WORK_OUTSOURCING_APPLY, outSourcing.getOS_ID()));
			}	
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException(e.getMessage());
		}
			
		return null;
	}
	
    /**
     * 委外申請-取消
     * @param osId
     * @throws WorkflowException
     * @author Charlie Woo
     */
	@Transactional
	public String outsourceCancel(String osId) throws WorkflowException {
		Date current = new Date();
		TbSiteOutsourcing outSourcing = getByPrimaryKey(osId);
		
		try {
			// 當狀態為非草稿與駁回時，皆需算為有效申請派工金額，取消時必須扣回以減少booking量
			if (!StringUtils.equals(OutSourceStatus.OS01.name(), outSourcing.getSTATUS()) && !StringUtils.equals(OutSourceStatus.OS03.name(), outSourcing.getSTATUS())) {
				
				// 分區的金額(扣回)
				TbComPoQuotaKey key = new TbComPoQuotaKey();
				key.setPO_ID(outSourcing.getPO_ID());
				key.setDOMAIN(outSourcing.getPO_DOMAIN());
				TbComPoQuota poQuota = poQuotaDao.findByPk(key);
				poQuota.setMD_USER(getLoginUser().getUsername());	
				poQuota.setMD_TIME(current);
				poQuota.setOS_AMOUNT(poQuota.getOS_AMOUNT() - outSourcing.getAMOUNT().intValue());
				poQuotaDao.update(poQuota);
				
				// 不分區的金額(扣回)
				TbComPoMain poMain = getTbComPoMain(outSourcing.getPO_ID());
				poMain.setMD_USER(getLoginUser().getUsername());	
				poMain.setMD_TIME(current);
				poMain.setOS_AMOUNT(poMain.getOS_AMOUNT() - outSourcing.getAMOUNT().intValue());
				poMainDao.updateByPrimaryKeySelective(poMain);
			}
			// 委外主檔更新狀態
			outSourcing.setSTATUS(OutSourceStatus.OS08.name());
			outSourcing.setMD_USER(getLoginUser().getUsername());	
			outSourcing.setMD_TIME(current);
			outsourcingDao.updateByPrimaryKeySelective(outSourcing);
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 委外派工驗收送審 
	 * @param osId
	 * @param poId
	 * @param apUser
	 * @param apNumber
	 * @param apAmountA
	 * @param itemIdA
	 * @throws WorkflowException
	 */
	@Transactional
	public void checkOsOrder(String osId, Long poId, String apUser, BigDecimal[] apNumber,
			BigDecimal[] apAmountA, String[] itemIdA, String processType) throws WorkflowException {
		Date currentDate = new Date();
		Double totalApAmount = 0.0;
		TbSiteOsItem tbSiteOsItem = null;

		try {
			TbWorkflowCfgExample example = new TbWorkflowCfgExample();
			example.createCriteria().andPROCESS_TYPEEqualTo(processType);
			List<TbWorkflowCfg> cfgs = workflowCfgDao.findByCondition(example);
			TbWorkflowCfg cfg = null;
			if (cfgs.isEmpty()) {
				throw new WorkflowException("ProcessType : " + processType + "於資料庫未設定！");
			} else {	
				TbSiteOutsourcing outsource = outsourcingDao.findByPrimaryKey(osId);
				
				for(int i=0 ; i< itemIdA.length ; i++){
					TbSiteOsItemKey key = new TbSiteOsItemKey();
					key.setPO_ID(poId);
					key.setOS_ID(osId);
					key.setITEM_ID(itemIdA[i]);
					
					tbSiteOsItem = osItemDao.findByKey(key);
					tbSiteOsItem.setOS_ID(osId);
					tbSiteOsItem.setPO_ID(poId);
					tbSiteOsItem.setITEM_ID(itemIdA[i]);
					tbSiteOsItem.setAP_NUMBER(apNumber[i]);
					tbSiteOsItem.setAP_AMOUNT(apAmountA[i]);
					tbSiteOsItem.setPAY_NUMBER(apNumber[i]);
					tbSiteOsItem.setPAY_AMOUNT(apAmountA[i]);
					tbSiteOsItem.setMD_TIME(currentDate);
					tbSiteOsItem.setMD_USER(apUser);
					osItemDao.updateByPrimaryKeySelective(tbSiteOsItem);	
					log.debug("Item Id 單項驗收金額：" + apAmountA[i].doubleValue());
					totalApAmount += apAmountA[i].doubleValue();	// 細項驗收金額加總
				}
				
				log.debug("本次送審驗收金額加總：" + totalApAmount);
				outsource.setMD_USER(apUser);
				outsource.setAP_USER(apUser);
				outsource.setMD_TIME(currentDate);
				outsource.setAP_DATE(currentDate);
				outsource.setSTATUS(OutSourceStatus.OS06.name());
				outsource.setAP_AMOUNT(new BigDecimal(totalApAmount).setScale(0, RoundingMode.HALF_UP));
				outsource.setPAY_AMOUNT(new BigDecimal(totalApAmount).setScale(0, RoundingMode.HALF_UP));
				outsourcingDao.updateByPrimaryKeySelective(outsource);
				
				cfg = cfgs.get(0);
				workflowActionService.apply(cfg.getPROCESS_TYPE(), osId, cfg.getPROCESS_NAME(), 
						emailTemplateService.getMailVarMapForWorkflow(EmailType.WORKFLOW_TODO_WORK_OUTSOURCING_ACCEPT, osId));
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException(e.getMessage());
		}		
	}
	
	/**
	 * 委外申請(簽核)
	 * @param osId
	 * @param mUser
	 * @throws Exception
	 * @throws NomsException
	 * @author Charlie Woo
	 */
	@Transactional
	public void finishOsApply(String osId, String mUser) throws Exception, NomsException {
		
		try{			
			Date currentDate = new Date();
			//update TB_SITE_OUTSOURCING
			TbSiteOutsourcing outsource = outsourcingDao.findByPrimaryKey(osId);
			Integer osAmount = outsource.getAMOUNT().intValue();
			outsource.setMD_TIME(currentDate);
			outsource.setMD_USER(mUser);
			outsource.setSTATUS(OutSourceStatus.OS04.name());
			outsourcingDao.updateByPrimaryKeySelective(outsource);
	
			//update TB_SITE_WORK_ORDER
			TbSiteWorkOrder tbSiteWorkOrder = workOrderDao.findOrderByPk(outsource.getORDER_ID());
			tbSiteWorkOrder.setODR_STATUS(OrderStatus.OR06.name());
			workOrderDao.update(tbSiteWorkOrder);
			
			//update TB_COM_PO_MAIN
			TbComPoMain tbComPoMain = poMainDao.selectByPrimaryKey(outsource.getPO_ID());
			tbComPoMain.setOS_AMOUNT(tbComPoMain.getOS_AMOUNT() == null ? osAmount : osAmount + tbComPoMain.getOS_AMOUNT());
			tbComPoMain.setMD_TIME(currentDate);
			tbComPoMain.setMD_USER(mUser);
			poMainDao.updateByPrimaryKeySelective(tbComPoMain);
			
			//update TB_COM_PO_QUOTA
			TbComPoQuotaKey key = new TbComPoQuotaKey();
			key.setPO_ID(outsource.getPO_ID());
			key.setDOMAIN(outsource.getPO_DOMAIN());
			TbComPoQuota tbComPoQuota = poQuotaDao.findByPk(key);
			if (tbComPoQuota == null) throw new DataNotFoundException("No data! CO_VAT_NO:" + outsource.getCO_VAT_NO() 
					+ " PO_ID:" + outsource.getPO_ID() + " PO_DOMAIN:" + outsource.getPO_DOMAIN());
			tbComPoQuota.setMD_TIME(currentDate);
			tbComPoQuota.setMD_USER(mUser);
			tbComPoQuota.setOS_AMOUNT(tbComPoQuota.getOS_AMOUNT() == null ? osAmount : osAmount + tbComPoQuota.getOS_AMOUNT());
			poQuotaDao.update(tbComPoQuota);
			
		} catch (Exception e){
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException(e.getMessage());
		}
	}
	
	/**
	 * 委外驗收(簽核)
	 * @param osId
	 * @param apUser
	 * @param outSourceStatus
	 * @throws NomsException
	 * @throws Exception
	 * @author Charlie Woo
	 */
	@Transactional
	public void finishOsVerify(String osId, String apUser, String outSourceStatus) throws NomsException,Exception {
		
		try{
			Date currentDate = new Date();
			TbSiteOutsourcing outsource = outsourcingDao.findByPrimaryKey(osId);
			TbComPoMain poMain = poMainDao.selectByPrimaryKey(outsource.getPO_ID());
			Integer osApAmount = outsource.getAP_AMOUNT().intValue();
			Integer osPayAmount = outsource.getPAY_AMOUNT().intValue();
			// update TB_SITE_OUTSOURCING
			outsource.setMD_TIME(currentDate);
			outsource.setMD_USER("SYSTEM");
			outsource.setSTATUS(outSourceStatus);
			outsourcingDao.update(outsource);
			
			TbComPoMain normalPoIn = null;
			// 如果是臨時PO單驗收過的話，就需將本次驗收金額壓至正式PO單上 TODO 那原本的臨時PO驗收金額還要壓嗎?
			if (StringUtils.equals("Y", poMain.getIS_TEMP())) {
				// find 正式PO單踹共
				TbComPoMainExample poMainExample = new TbComPoMainExample();
				poMainExample.createCriteria().andPO_NOEqualTo(poMain.getPO_NO()).andPO_IDNotEqualTo(poMain.getPO_ID()).andIS_TEMPEqualTo("N");
				List<TbComPoMain> poMains = poMainDao.selectByExample(poMainExample);	// 常理只會有一筆正式PO單
				if (!poMains.isEmpty()) {					
					normalPoIn = poMains.get(0);
					log.debug("驗收為臨時PO單：" + normalPoIn.getPO_ID() + ", 區域：" + normalPoIn.getPO_DOMAIN());
				} else {
					throw new NomsException("該筆臨時PO單查無正式PO單資料，驗收金額回壓失敗！");
				}
				
			} else {	// 本身是正式PO單
				normalPoIn = poMainDao.selectByPrimaryKey(outsource.getPO_ID());
				log.debug("驗收為正式PO單：" + normalPoIn.getPO_ID() + ", 區域：" + normalPoIn.getPO_DOMAIN());
			}
			// update TB_COM_PO_MAIN
			normalPoIn.setAP_AMOUNT(normalPoIn.getAP_AMOUNT() == null ? osApAmount : osApAmount + normalPoIn.getAP_AMOUNT());
			normalPoIn.setPAY_AMOUNT(normalPoIn.getPAY_AMOUNT() == null ? osPayAmount : osPayAmount + normalPoIn.getPAY_AMOUNT());
			normalPoIn.setMD_TIME(currentDate);
			normalPoIn.setMD_USER(apUser);
			poMainDao.updateByPrimaryKeySelective(normalPoIn);
			
			// update TB_COM_PO_QUOTA 若該張PO單屬於全區性質，則DOMAIN以委外的DOMAIN申請為主
			Long normalPoId = normalPoIn.getPO_ID(); 
			String normalPoDomain = StringUtils.equals("HQ", normalPoIn.getPO_DOMAIN()) ? outsource.getPO_DOMAIN() : normalPoIn.getPO_DOMAIN();
			TbComPoQuotaKey tbComPoQuotaKey = new TbComPoQuotaKey();
			tbComPoQuotaKey.setPO_ID(normalPoId);
			tbComPoQuotaKey.setDOMAIN(normalPoDomain);
			TbComPoQuota tbComPoQuota = poQuotaDao.findByPk(tbComPoQuotaKey);
			if (tbComPoQuota == null) throw new NomsException("該筆PO單查無正式PO單(配額)資料，驗收金額回壓失敗！");
			tbComPoQuota.setMD_TIME(currentDate);
			tbComPoQuota.setMD_USER(apUser);
			tbComPoQuota.setAP_AMOUNT(tbComPoQuota.getAP_AMOUNT() == null ? osApAmount : osApAmount + tbComPoQuota.getAP_AMOUNT());
			tbComPoQuota.setPAY_AMOUNT(tbComPoQuota.getPAY_AMOUNT() == null ? osPayAmount : osPayAmount + tbComPoQuota.getPAY_AMOUNT());
			poQuotaDao.update(tbComPoQuota);
			
		} catch(Exception e){
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException(ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	/**
	 * 回壓請款相關欄位
	 * @param osId
	 * @param payNo
	 * @param itemList(只需要塞ITEM_ID, PAY_NUMBER, PAY_AMOUNT)
	 * @return
	 * @author Charlie Woo
	 */
	@Transactional
	public String feedbackFromPayment(String osId, String payNo, List<TbSiteOsItem> itemList) throws NomsException {
		String resultMsg = "failed: 發生不明錯誤！";
		Integer count = 0;
		Assert.notNull(itemList);
		
		// check value
		if (StringUtils.isNotEmpty(osId) && StringUtils.isNotEmpty(payNo)) {
			
			if (!itemList.isEmpty()) {
				Double totalPayment = 0.0;	// 所有工項明細請款總額
				TbSiteOutsourcing outsource = outsourcingDao.findByPrimaryKey(osId);
				
				// update TB_SITE_OS_ITEM (委外工項請款數量、請款金額)
				for (TbSiteOsItem item : itemList) {
					if (StringUtils.isNotEmpty(item.getITEM_ID())) {
						item.setOS_ID(osId);
						item.setPO_ID(outsource.getPO_ID());
						count += osItemDao.updateByPrimaryKeySelective(item);
						
						totalPayment += item.getPAY_AMOUNT().doubleValue();
					} else {
						throw new NomsException("failed: 工項明細資訊主鍵不完全(itemList)！");
					}
				}
				Integer oldTotalPayment = outsource.getPAY_AMOUNT().intValue();	// 回壓前的委外請款金額
				BigDecimal newTotalPayment = new BigDecimal(totalPayment).setScale(0, RoundingMode.HALF_UP); // 回壓後要加總的請款金額
				
				// update TB_COM_PO_MAIN.PAY_AMOUNT (PO單請款總額)
				TbComPoMain poMain = poMainDao.selectByPrimaryKey(outsource.getPO_ID());
				poMain.setPAY_AMOUNT(poMain.getPAY_AMOUNT() - oldTotalPayment + newTotalPayment.intValue());
				poMainDao.updateByPrimaryKeySelective(poMain);
				
				// update TB_COM_PO_QUOTA.PAY_AMOUNT (分區配額請款總額)
				TbComPoQuotaKey key = new TbComPoQuotaKey();
				key.setPO_ID(outsource.getPO_ID());
				key.setDOMAIN(outsource.getPO_DOMAIN());
				
				TbComPoQuota poQuota = poQuotaDao.findByPk(key);
				poQuota.setPAY_AMOUNT(poQuota.getPAY_AMOUNT() - oldTotalPayment + newTotalPayment.intValue());
				poQuotaDao.update(poQuota);
				
				// update TB_SITE_OUTSOURCING (委外主檔請款單號、請款總額)
				outsource.setPAY_NO(payNo);
				outsource.setPAY_AMOUNT(newTotalPayment);
				outsourcingDao.updateByPrimaryKeySelective(outsource);
				
				resultMsg = "success. 完成資料筆數：" + count + ", 本次回壓請款總額：" + newTotalPayment.intValue();
			} else {
				throw new NomsException("failed: 委外工項明細錯誤！");
			}
			
		} else {
			throw new NomsException("failed: 派工單或請款單號錯誤，請確認！osId = " + osId + " payNo = " + payNo);
		}
		return resultMsg;
	}
	
	public int insert(TbSiteOutsourcing record) {
		return outsourcingDao.insert(record);
	}
	
	public int update(TbSiteOutsourcing record) {
		return outsourcingDao.update(record);
	}
	
	public int updateByPrimaryKeySelective(TbSiteOutsourcing record) {
		return outsourcingDao.updateByPrimaryKeySelective(record);
	}
	
	public List<TbComItemCat> selectComItemCatByExample(
			TbComItemCatExample itemCat) {
		return  outsourcingDao.selectComItemCatByExample(itemCat);
	}

	public TbComCompany getTbComCompany(String coVatNo) {
		return companyDao.findByCoVatNo(coVatNo);
	}

	public TbComPoMain getTbComPoMain(Long po_ID) {
		return poMainDao.getTbComPoMain(po_ID);
	}
	
	/**
	 * 由PK查詢工單
	 * @param orderId
	 * @return
	 */
	public TbSiteWorkOrder selectByPrimaryKey(String orderId){
		return outsourcingDao.selectByPrimaryKey(orderId);
	}
	
	/**
	 * 由PK查詢作業
	 * @param orderId
	 * @return
	 */
	public TbSiteWork getWorkById(String workId){
		return workDao.findByPk(workId);
	}
	
	/**
	 * 計算已使用的分區配額總和(含本次)
	 * @param Example
	 * @return
	 */
	public Long sumPayAmountbyOutcourcing(Map<String, String> map, Long amount){
		return outsourcingDao.sumPayAmountbyOutcourcing(map) + amount;
	}
	
	/**
	 * 計算已使用的PO單總和(含本次)
	 * @param Example
	 * @return
	 */
	public Long sumPayAmountNoAreabyOutcourcing(Map<String, String> map, Long amount){
		return outsourcingDao.sumPayAmountNoAreabyOutcourcing(map) + amount;
	}
	
	/**
	 * 用map查詢
	 * 工項設定按鈕(確定)
	 * @param Example
	 * @return
	 */
	public ItemMainDTO selectMainItem(Map<String, String> map){
		return outsourcingDao.selectMainItem(map);
	}
	
	/**
	 * 委外申請
	 * 當有資料 
	 * 更新OutSourcing
	 */
	public int updateOutSourcing(Map<String, String> map) {
		return outsourcingDao.updateOutSourcing(map);
	}
	 
	/**
	 * 委外申請
	 * 當沒有資料 
	 * 新增OutSourcing
	 */
	public int saveOutSourcing(Map<String, String> map) {
		return outsourcingDao.saveOutSourcing(map);
	}
	
	/**
	 * 委外申請
	 * 更新SiteOsItem
	 */
	public int updateSiteOsItem(Map<String, String> map) {
		return outsourcingDao.updateSiteOsItem(map);
	}
	 
	/**
	 * 委外申請-
	 * 儲存-新增SiteOsItem
	 * @param record 
	 */
	public int saveSiteOsItem(TbSiteOsItem record) {
		return osItemDao.insert(record);
//		return outsourcingDao.saveSiteOsItem(map);
	}
	
	/**
	 * 委外申請
	 * 儲存
	 * 刪除SiteOsItem
	 */
	public int deleteSiteOsItem(Map<String, String> map) {
		return outsourcingDao.deleteSiteOsItem(map);
	}
	
	/**
	 * 由PK查詢PO單主檔
	 * @param poId
	 * @return
	 */
	public TbComPoMain selectByPrimaryKey(Long poId){
		return poMainDao.selectByPrimaryKey(poId);
	}
	
	/**
	 * 用Example查詢
	 * TB_SITE_OUTSOURCING
	 * @param Example
	 * @return *test*
	 */
	public List<TbSiteOutsourcing> getSiteOutSoureByExample(TbSiteOutsourcingExample example){
		return outsourcingDao.findSiteOutSoureByExample(example);
	}
	
	/**
	 * 用map查詢待簽事項-委外申請/委外驗收
	 * @param Example
	 * @return *test*
	 */
	public List<TbSiteOutsourcingDTO> getSiteWorkOrderSearchByExample(Map<String, String> map){
		return outsourcingDao.findSiteWorkOrderSearchByExample(map);
	}
	
	/**
	 * 由派工單號取得委外派工申請單
	 * @author Miles Chang 2014.12.18
	 * @param osId
	 * @return*test*
	 */
	public TbSiteOutsourcing getByPrimaryKey(String osId) {
		return outsourcingDao.findByPrimaryKey(osId);
	}
	
	/**
	 * 用workId查詢 廠商下拉選單
	 * @param workId 
	 * 
	 * @return
	 */
	public TbSiteWork selectTbSiteWorkByOrderId(String workId) {
		return workDao.findByPk(workId);
	}
	
	/**
	 * 查詢統包廠商
	 * @param Example
	 * @return
	 */
	public List <CompanyDTO> getCompanyForTypeT(){
		return outsourcingDao.selectTbComCompany();
	}
	
	/**
	 * 查詢一般廠商
	 * @param Example
	 * @return
	 */
	public List <CompanyDTO> getCompanyForTypeP(){
		return outsourcingDao.selectTbComCompanybyGeneral();
	}
	
	/**
	 * 以廠商編號與工程類型查詢PO單
	 * @param Example
	 * @return
	 */
	public List<PoMainDTO> getPoMainByCompany(Map<String, String> map){
		return outsourcingDao.selectTbComPoMain(map);
	}
	
	/**
	 * 用map查詢查詢 總金額(已註解)
	 * @param Example
	 * @return
	 */
	public List <PoItemDTO> selectTotailAccount(Map<String, String> map){
		return outsourcingDao.selectTotailAccount(map);
	}
	
	/**
	 * 用map查詢 工項明細(table)
	 * @param Example
	 * @return
	 */
	public List <PoItemDTO> selectItemIdByOsId(Map<String, String> map){
		return outsourcingDao.selectItemIdByOsId(map);
	}
	
	/**
	 * 用map查詢TbComItemMain
	 * @param Example
	 * @return
	 */
	public List<ItemMainDTO> selectItemMainItem(Map<String, String> map){
		return outsourcingDao.selectItemMainItem(map);
	}
	
	/** for PO單查詢目前分區可用餘額呼叫 **/
	public Long getUnusePoQuota(Long poId, String poDomain) {
		return getUnusePoQuota(poId, poDomain, 0L);
	}
	
	/**
	 * 取得未使用之分區配額額度
	 * @param poId
	 * @param poDomain
	 * @param osAmountForPlus	(optional:是否要納入計算該次派工金額)
	 * @return 正數有餘，負數不足，null為未設定PO_QUOTA檔
	 * @author Charlie Woo
	 */
	private Long getUnusePoQuota(Long poId, String poDomain, Long osAmountForPlus) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("poId", poId + "");
		map.put("domain", poDomain);
		
		// 分區總額
		Long usedQuota = sumPayAmountbyOutcourcing(map, osAmountForPlus); // 相同區域下已使用的PAY_AMOUNT有效總額(TB_SITE_OUTSOURCING)	
		TbComPoQuotaKey key = new TbComPoQuotaKey();
		key.setPO_ID(poId);
		key.setDOMAIN(poDomain);
		TbComPoQuota osQuotaLimit = poQuotaDao.findByPk(key);
		if (osQuotaLimit == null) return null;
		
		if (usedQuota != null && osQuotaLimit != null) {
			long osQuota = osQuotaLimit.getQUOTA();
			Integer currentUsedPayment = osQuotaLimit.getPAY_AMOUNT();
			log.debug("該分區配額度上限* = " + osQuota);
			log.debug("Quota已驗收使用的分區配額* = " + currentUsedPayment);
			log.debug("Outsource已Booking分區額度* = " + usedQuota);
			return osQuota - currentUsedPayment - usedQuota;
		}
		return 0L;
	}
	
	/** for PO單查詢目前可用餘額呼叫 **/
	public Long getUnusePoMainPayment(Long poId) {
		return getUnusePoMainPayment(poId, 0L);
	}
	
	/**
	 * 取得未使用之PO單全部總額度
	 * @param poId
	 * @param osAmountForPlus	(optional:是否要納入計算該次派工金額)
	 * @return 正數有餘，負數不足，null為未設定PO_MAIN檔
	 * @author Charlie Woo
	 */
	private Long getUnusePoMainPayment(Long poId, Long osAmountForPlus) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("poId", poId + "");
		
		Long usedAmount = sumPayAmountNoAreabyOutcourcing(map, osAmountForPlus); // 同張PO單已使用的PAY_AMOUNT有效總額(TB_SITE_OUTSOURCING)
		TbComPoMain poMain = poMainDao.getTbComPoMain(poId);
		if (poMain == null) return null;
		
		if (usedAmount != null && poMain != null) {
			long poTotalAmount = poMain.getAMOUNT() == null ? 0L : poMain.getAMOUNT().longValue();
			Integer currentUsedPayment = poMain.getPAY_AMOUNT() == null ? 0 : poMain.getPAY_AMOUNT();
			log.debug("該PO單總額度上限* = " + poMain.getAMOUNT());
			log.debug("PO單已驗收使用總額度* = " + currentUsedPayment);
			log.debug("Outsource已Booking總額度* = " + usedAmount);
			return poTotalAmount - currentUsedPayment - usedAmount;
		}
		return 0L;
	}
	
	/**
	 * 計算工項明細金額
	 * @param number	數量
	 * @param price	   單價
	 * @param mgrFee   管理費(%)
	 * 公式：數量 * 單價 * (1 + 管理費 / 100)
	 * @return
	 * @author Charlie Woo
	 */
	private BigDecimal sumItemAmountWithMgrfee(BigDecimal number, BigDecimal price, Integer mgrFee) {
		BigDecimal fee = new BigDecimal(1 + (mgrFee / 100)).setScale(2);
		return number.multiply(price).multiply(fee).setScale(6, RoundingMode.HALF_UP);
	}
	
	public String getPoDomainByWork(String workId) {
		TbSiteWork work = getWorkById(workId);
		TownDomainTeamDTO domainTeamDTO = domainTeamDao.getTownDomainTeamByCityIdTownId(work.getCITY(), work.getAREA());
		return domainTeamDTO == null ? "" : domainTeamDTO.getDOMAIN();
	}
}
