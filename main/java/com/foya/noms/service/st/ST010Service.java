package com.foya.noms.service.st;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbSiteAntCfg;
import com.foya.dao.mybatis.model.TbSiteAntCfgExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.dao.mybatis.model.TbSiteOutsourcingExample;
import com.foya.dao.mybatis.model.TbSiteStatusLog;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkAntCfgTemp;
import com.foya.dao.mybatis.model.TbSiteWorkAntCfgTempExample;
import com.foya.dao.mybatis.model.TbSiteWorkLocTemp;
import com.foya.dao.mybatis.model.TbSiteWorkLocTempKey;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSiteWorkOrderExample;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTemp;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTempKey;
import com.foya.exception.CreateFailException;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dao.common.TownDomainTeamDao;
import com.foya.noms.dao.st.AntCfgDao;
import com.foya.noms.dao.st.AntCfgTempDao;
import com.foya.noms.dao.st.LocTempDao;
import com.foya.noms.dao.st.LocationDao;
import com.foya.noms.dao.st.MeterialOptDao;
import com.foya.noms.dao.st.OutsourcingDao;
import com.foya.noms.dao.st.ST005Dao;
import com.foya.noms.dao.st.ST010Dao;
import com.foya.noms.dao.st.SiteMainDao;
import com.foya.noms.dao.st.SiteStatusLogDao;
import com.foya.noms.dao.st.SiteTempDao;
import com.foya.noms.dao.st.WorkDao;
import com.foya.noms.dao.st.WorkOrderDao;
import com.foya.noms.dao.system.OrderTypeDao;
import com.foya.noms.dto.common.TownDomainTeamDTO;
import com.foya.noms.dto.st.MeterialRtnDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.foya.noms.dto.system.OrderTypeDTO;
import com.foya.noms.enums.EmailType;
import com.foya.noms.enums.OrderStatus;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.enums.WorkStatus;
import com.foya.noms.resources.AppConstants;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.inv.INVService;
import com.foya.noms.service.system.OrderTypeService;
import com.foya.noms.service.system.RespUserService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.workflow.exception.WorkflowException;

@Service
public class ST010Service extends BaseService{
	
	@Autowired
	private ST010Dao st010Dao;
	@Autowired
	private WorkOrderDao workOrderDao;
	@Autowired
	private WorkDao workDao;
	@Autowired
	private ST005Dao st005Dao;
	@Autowired
	private MeterialOptDao meterialOptDao;
	@Autowired
	private OrderTypeDao orderTypeDao;
	@Autowired
	private OrderTypeService orderTypeService;
	@Autowired
	private TownDomainTeamDao townDomainTeamDao;
	@Autowired
	private UniqueSeqService uniqueSeqService;
	@Autowired
	private WorkflowActionService workflowActionService;
	@Autowired
	private EmailTemplateService emailTemplateService;
	@Autowired
	private OutsourcingDao outsourcingDao;
	@Autowired
	private LocTempDao locTempDao;
	@Autowired
	private SiteStatusLogDao siteStatusLogDao;
	@Autowired
	private LocationDao locationDao;
	@Autowired
	private SiteTempDao siteTempDao;
	@Autowired
	private SiteMainDao siteMainDao;
	@Autowired
	private AntCfgDao antCfgDao;
	@Autowired
	private AntCfgTempDao antCfgTempDao;
	@Autowired
	private RespUserService respUserService;
	@Autowired
	private INVService iNVService;
	@Autowired
	private SiteEmailService siteEmailService;
	
	/**
	 * 查詢全部siteWorkOrder
	 * 
	 * @param map
	 * @return
	 */
	public List<SiteWorkOrderDTO> getWorkLOrderist(Map<String, Object> map) {
		return st010Dao.findWorkOrderList(map);
	}
	
	/**
	 * 完工送審
	 * @param workId
	 * @param orderId
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void verifyOrder(String orderId, String workId, String mdUser) {
		Date currentDate = new Date();
		TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);// new TbSiteWorkOrder();
		TbSiteWork work = workDao.findByPk(workId);
		order.setORDER_ID(orderId);
		order.setODR_STATUS(OrderStatus.OR08.name());
		order.setMD_USER(mdUser);
		order.setMD_TIME(currentDate);
		st005Dao.updateSiteWorkOrderSelective(order);
		
		try {		
			// 該工單類型為土木工單類(ORDER_TYPE=SC、SCH、SCN)時，完工時額外檢核工單下尚有沒有在途物料
			boolean allMaterialHandled = true;	// 先預設所有物料皆已處理完畢
			String hasMtOptOrderType = "SC,SCH,SCN";
			if (StringUtils.contains(hasMtOptOrderType, order.getORDER_TYPE())) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("orderId", orderId);
				List<MeterialRtnDTO> list = meterialOptDao.selectRemInsItemQuery(map);
				for (MeterialRtnDTO model : list) {
					if (model.getAssetQty() > 0) {	// 若尚有一筆未處理完的在途物料
						allMaterialHandled = false;
						break;
					}
				}
			}			
			// 若有在途物料，新增一筆物料處理工單，作業順序為99
			if (!allMaterialHandled) {
				List<OrderTypeDTO> orderList = orderTypeDao.findOrderTypeByWorkTypeId(work.getWORK_TYPE(), 99);
				if (!orderList.isEmpty()) {	
					TownDomainTeamDTO domainTeam = townDomainTeamDao.getTownDomainTeamByCityIdTownId(work.getCITY(), work.getAREA());
					for (OrderTypeDTO orderType : orderList) {						
						TbSiteWorkOrder siteWorkOrder = new TbSiteWorkOrder();
						siteWorkOrder.setORDER_ID(uniqueSeqService.getNextOrderId(workId));
						siteWorkOrder.setWORK_ID(workId);
						siteWorkOrder.setODR_STATUS(OrderStatus.OR03.name());
						siteWorkOrder.setPRIORITY(work.getPRIORITY());
						siteWorkOrder.setODR_SEQ(orderType.getOrderSeq());
						siteWorkOrder.setORDER_TYPE(orderType.getOD_TYPE_ID());
						siteWorkOrder.setIS_ACTIVE("Y");
						TbOrgDept dept = orderTypeService.getOrderTypeForWorkArea(orderType.getDEPT_KEY(), domainTeam);
						siteWorkOrder.setREP_DEPT(dept.getDEPT_ID());
						
						siteWorkOrder.setCR_USER(work.getAPL_USER());
						siteWorkOrder.setCR_TIME(currentDate);
						siteWorkOrder.setMD_USER(work.getAPL_USER());
						siteWorkOrder.setMD_TIME(currentDate);
						workOrderDao.insert(siteWorkOrder);
					}
				}
			}		

			workflowActionService.apply("SiteBuildSingleWorkCompletionApply", orderId, "單張工單完工申請",
					emailTemplateService.getMailVarMapForWorkflow(EmailType.WORKFLOW_TODO_WORK_COMPLETION_APPLY, orderId));
		} catch (WorkflowException e) {		
			log.error(e.getMessage(), e);
			throw e;
		}		
	}
	
	
	/**
	 * 單張工單作業完工-簽核
	 * 
	 * @param orderId
	 *            作業編號
	 * @return
	 * @throws NomsException
	 */
	@Transactional
	public void finishSiteBuildOrder(String orderId, String action) throws NomsException {
		Date mdTime = new Date();
		String mdUser = getLoginUser().getUsername();
		
		try {
			TbSiteWorkOrder tbSiteWorkOrder = workOrderDao.findOrderByPk(orderId);
			
			if (StringUtils.equals(action, AppConstants.WORKFLOW_REST_REJECT)) {
				
				TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
				example.createCriteria().andORDER_IDEqualTo(orderId);
				List<TbSiteOutsourcing> outSourcingList = outsourcingDao
						.findSiteOutSoureByExample(example);
				int updateStatusItem = 0;
				if (outSourcingList.size() == 0) {
					updateStatusItem = workOrderDao.updateStatusByOrder(orderId, OrderStatus.OR05);
				} else {
					updateStatusItem = workOrderDao.updateStatusByOrder(orderId, OrderStatus.OR06);
				}
				if (updateStatusItem == 0) {
					log.error("update fail updateStatusItem count=" + updateStatusItem + " orderId ="
							+ "orderId");
					throw new UpdateFailException("update fail");
				} 
				
			} else {	// 完工核可
				tbSiteWorkOrder.setODR_STATUS(OrderStatus.OR09.name());
				tbSiteWorkOrder.setMD_USER(mdUser);
				tbSiteWorkOrder.setMD_TIME(mdTime);
				tbSiteWorkOrder.setEND_TIME(mdTime);
				tbSiteWorkOrder.setEND_USER(mdUser);
				//更新成 已完工
				st005Dao.updateSiteWorkOrderSelective(tbSiteWorkOrder);
				
				TbSiteWork tbSiteWork = workDao.findByPk(tbSiteWorkOrder.getWORK_ID());	
				tbSiteWork.setMD_USER(mdUser);
				tbSiteWork.setMD_TIME(mdTime);
				tbSiteWork.setEND_TIME(mdTime);
				tbSiteWork.setEND_DATE(mdTime);
				// 3.寫入站點主檔存檔
				log.debug("=====站點主檔 start =====");
				TbSiteWorkLocTempKey key = new TbSiteWorkLocTempKey();
				key.setWORK_ID(tbSiteWork.getWORK_ID());
				key.setLOCATION_ID(tbSiteWork.getLOCATION_ID());
				TbSiteWorkLocTemp locationTemp = locTempDao.selectByPrimaryKey(key);
				TbSiteLocation siteLoc = this.getSiteLocation(locationTemp, tbSiteWork);
				siteLoc.setCR_TIME(mdTime);
				siteLoc.setCR_USER(mdUser);
				siteLoc.setMD_TIME(mdTime);
				siteLoc.setMD_USER(mdUser);
				int locationData = locationDao.update(siteLoc);
				
				if (locationData == 0) {
					log.error("站點主檔 update fail workLoc count=" + locationData + " locationId ="
							+ tbSiteWork.getLOCATION_ID());
					throw new CreateFailException("update fail");
				}
				log.debug("=====站點主檔 end =====");
				
				// 4.寫入站台主檔
				log.debug("=====站台主檔 start =====");
				
				TbSiteWorkSiteTempKey siteKey = new TbSiteWorkSiteTempKey();
				siteKey.setWORK_ID(tbSiteWork.getWORK_ID());
				siteKey.setSITE_ID(tbSiteWork.getSITE_ID());
				TbSiteWorkSiteTemp siteTempData = siteTempDao.findByPrimaryKey(siteKey);
				TbSiteMain siteMain = this.getSiteWorkSiteTemp(siteTempData, tbSiteWork);
				siteMain.setCR_USER(mdUser);
				siteMain.setCR_TIME(mdTime);
				siteMain.setMD_USER(mdUser);
				siteMain.setMD_TIME(mdTime);	
				//siteMain.setSITE_STATUS(SiteStatus.S06.name());
				int main = siteMainDao.updateByPrimaryKeySelective(siteMain);

				if (main == 0) {
					log.error("站台主檔存檔 update fail workSite count=" + main + " siteId ="
							+ tbSiteWork.getSITE_ID());
					throw new CreateFailException("update fail");
				}
				log.debug("=====站台主檔存檔 end =====");
				
				
				// 5.寫入天線主檔
				log.debug("=====天線主檔存檔 start =====");
				TbSiteWorkAntCfgTempExample cfgExample = new TbSiteWorkAntCfgTempExample();
				cfgExample.createCriteria().andSITE_IDEqualTo(tbSiteWork.getSITE_ID());
				List<TbSiteWorkAntCfgTemp> siteAntCfgTempList = antCfgTempDao.findByCondition(cfgExample);
				for (TbSiteWorkAntCfgTemp cfgTempList : siteAntCfgTempList) {
					//先將 主檔 資料刪除後，再將 暫存檔資料新增
					TbSiteAntCfgExample antCfgExample = new TbSiteAntCfgExample();
					antCfgExample.createCriteria().andSITE_IDEqualTo(tbSiteWork.getSITE_ID());
					antCfgDao.deleteSiteAntCfg(antCfgExample);
					
					TbSiteAntCfg tbSiteAntCfg = this.getSiteWorkAntCfgTemp(cfgTempList);
					tbSiteAntCfg.setCR_USER(mdUser);
					tbSiteAntCfg.setCR_TIME(mdTime);
					tbSiteAntCfg.setMD_USER(mdUser);
					tbSiteAntCfg.setMD_TIME(mdTime);
					int siteSrAntCfg = antCfgDao.insert(tbSiteAntCfg);
					if (siteSrAntCfg == 0) {
						log.error("天線主檔 insert fail siteSrAntCfg count=" + siteSrAntCfg + " siteId ="
								+ tbSiteWork.getSITE_ID());
						throw new CreateFailException("insert fail");
					}
				}
				
				
				log.debug("=====天線主檔存檔 end =====");
				
				// 6.呼叫物料安裝完工API(INVService.completion)
				iNVService.completion(action, action, action, action, action, action, null, null, action, action);
				// 7.最後再檢查是否有下一張待施作工單(STATUS=OR03, NEXT_ODR_SEQ)，若有待施作工單，則以先行透過系統自動派工方式
				// 8.EMAIL通知
				selectWorkOrder(orderId, tbSiteWork.getAREA(), tbSiteWork.getWORK_ID());
			}
			
		} catch(NomsException e) {		
			log.error(e.getMessage(), e);
			throw e;
		}
		
	}
	
	private TbSiteLocation getSiteLocation(TbSiteWorkLocTemp siteLocTemp, TbSiteWork siteWork) {
		TbSiteLocation tbSiteLocation = new TbSiteLocation();
		//siteWorkLocTemp.setWORK_ID(siteWork.getWORK_ID());
		tbSiteLocation.setLOCATION_ID(siteWork.getLOCATION_ID());
		tbSiteLocation.setLOC_NAME(siteLocTemp.getLOC_NAME());
		tbSiteLocation.setLOC_TYPE(siteLocTemp.getLOC_TYPE());
		tbSiteLocation.setLON(siteLocTemp.getLON());
		tbSiteLocation.setLAT(siteLocTemp.getLAT());
		tbSiteLocation.setMAINT_AREA(siteLocTemp.getMAINT_AREA());
		tbSiteLocation.setMAINT_DEPT(siteLocTemp.getMAINT_DEPT());
		tbSiteLocation.setMAINT_TEAM(siteLocTemp.getMAINT_TEAM());
		tbSiteLocation.setMAINT_USER(siteLocTemp.getMAINT_PSN());
		tbSiteLocation.setLOC_DESC(siteLocTemp.getLOC_DESC());
		tbSiteLocation.setBASE_TYPE(siteLocTemp.getBASE_TYPE());
		tbSiteLocation.setINDOOR_OPTION(siteLocTemp.getINDOOR_OPTION());
		tbSiteLocation.setNS_LEVEL(siteLocTemp.getNS_LEVEL());
		tbSiteLocation.setBUILDING_HEIGHT(siteLocTemp.getBUILDING_HEIGHT());
		tbSiteLocation.setSHARE_TYPE(siteLocTemp.getSHARE_TYPE());
		// siteWorkLocTemp.setSHARE_COM(siteLoc.getSHARE_COM());
		tbSiteLocation.setIS_FREEENTER(siteLocTemp.getIS_FREEENTER());
		tbSiteLocation.setIS_KEY(siteLocTemp.getIS_KEY());
		tbSiteLocation.setIS_INDOOR(siteLocTemp.getIS_INDOOR());
		tbSiteLocation.setNEED_MISC_LIC(siteLocTemp.getNEED_MISC_LIC());
		tbSiteLocation.setHAS_MISC_LIC(siteLocTemp.getHAS_MISC_LIC());
		tbSiteLocation.setADDR(siteWork.getADDR());
		tbSiteLocation.setADD_OTHER(siteLocTemp.getADD_OTHER());
		tbSiteLocation.setZIP(siteWork.getZIP());
		tbSiteLocation.setCITY(siteWork.getCITY());
		tbSiteLocation.setAREA(siteWork.getAREA());
		tbSiteLocation.setVILLAGE(siteWork.getVILLAGE());
		tbSiteLocation.setADJACENT(siteWork.getADJACENT());
		tbSiteLocation.setROAD(siteWork.getROAD());
		tbSiteLocation.setLANE(siteWork.getLANE());
		tbSiteLocation.setALLEY(siteWork.getALLEY());
		tbSiteLocation.setSUB_ALLEY(siteWork.getSUB_ALLEY());
		tbSiteLocation.setDOOR_NO(siteWork.getDOOR_NO());
		tbSiteLocation.setDOOR_NO_EX(siteWork.getDOOR_NO_EX());
		tbSiteLocation.setFLOOR(siteWork.getFLOOR());
		tbSiteLocation.setFLOOR_EX(siteWork.getFLOOR_EX());
		tbSiteLocation.setROOM(siteWork.getROOM());
		tbSiteLocation.setADD_OTHER(siteWork.getADD_OTHER());
		tbSiteLocation.setSPACE_ROOM(siteLocTemp.getSPACE_ROOM());
		tbSiteLocation.setSPACE_ROOF(siteLocTemp.getSPACE_ROOF());
		tbSiteLocation.setSPACE_TOP(siteLocTemp.getSPACE_TOP());
		tbSiteLocation.setSPACE_PLATFORM(siteLocTemp.getSPACE_PLATFORM());
		tbSiteLocation.setSPACE_LAND(siteLocTemp.getSPACE_LAND());
		tbSiteLocation.setSPACE_INDOOR(siteLocTemp.getSPACE_INDOOR());
		tbSiteLocation.setSPACE_OUTDOOR(siteLocTemp.getSPACE_OUTDOOR());
		tbSiteLocation.setFOOTAGE(siteLocTemp.getFOOTAGE());
		tbSiteLocation.setEMP_RELATION(siteLocTemp.getEMP_RELATION());

		return tbSiteLocation;
	}
	
	private TbSiteMain getSiteWorkSiteTemp(TbSiteWorkSiteTemp siteTemp, TbSiteWork siteWork) {
		TbSiteMain main = new TbSiteMain();
		//siteWorkSiteTemp.setWORK_ID(siteWork.getWORK_ID());
		main.setSITE_ID(siteTemp.getSITE_ID());
		main.setFEQ_ID(siteTemp.getFEQ_ID());
		if (siteTemp.getSR_ID() == null) {
			main.setSR_ID("");
		} else {
			main.setSR_ID(siteTemp.getSR_ID());
		}
		main.setBTS_SITE_ID(siteTemp.getBTS_SITE_ID());
		main.setSITE_NAME(siteTemp.getSITE_NAME());
		main.setLOCATION_ID(StringUtils.trimToNull(siteTemp.getLOCATION_ID()));
		main.setPURPOSE(siteTemp.getPURPOSE());
		main.setEQP_TYPE_ID(StringUtils.trimToNull(siteTemp.getEQP_TYPE_ID()));
		main.setEQP_MODEL_ID(StringUtils.trimToNull(siteTemp.getEQP_MODEL_ID()));
		main.setCOVERAGE_TYPE(StringUtils.trimToNull(siteTemp.getCOVERAGE_TYPE()));
		main.setBTS_CONFIG(StringUtils.trimToNull(siteTemp.getBTS_CONFIG()));
		main.setDONOR_SITE(StringUtils.trimToNull(siteTemp.getDONOR_SITE()));
		main.setFEEDERLESS(StringUtils.trimToNull(siteTemp.getFEEDERLESS()));
		main.setMASTER_SITE(StringUtils.trimToNull(siteTemp.getMASTER_SITE()));
		main.setCLUSTER(StringUtils.trimToNull(siteTemp.getMASTER_SITE()));
		main.setRNC_BTS(StringUtils.trimToNull(siteTemp.getRNC_BTS()));
		main.setNIOS_RPT_STATUS(StringUtils.trimToNull(siteTemp.getNIOS_RPT_STATUS()));
		main.setCELL_STATUS(StringUtils.trimToNull(siteTemp.getCELL_STATUS()));
		main.setOMCU_OBJ(StringUtils.trimToNull(siteTemp.getOMCU_OBJ()));
		main.setSPEED_UPLOAD(StringUtils.trimToNull(siteTemp.getSPEED_UPLOAD()));
		main.setSPEED_DOWNLOAD(StringUtils.trimToNull(siteTemp.getSPEED_DOWNLOAD()));
		main.setPOWER(StringUtils.trimToNull(siteTemp.getPOWER()));
		main.setCST_ID_CARD_NUM(StringUtils.trimToNull(siteTemp.getCST_ID_CARD_NUM()));
		main.setCST_MOBILE(StringUtils.trimToNull(siteTemp.getCST_MOBILE()));
		main.setCST_ID(StringUtils.trimToNull(siteTemp.getCST_ID()));
		main.setCOVERAGE_INDOOR(StringUtils.trimToNull(siteTemp.getCOVERAGE_INDOOR()));
		main.setHIDDEN(StringUtils.trimToNull(siteTemp.getHIDDEN()));
		main.setNOIS_ON_AIR(StringUtils.trimToNull(siteTemp.getNOIS_ON_AIR()));
		main.setL2_SWITCH(StringUtils.trimToNull(siteTemp.getL2_SWITCH()));
		return main;
	}
	private TbSiteAntCfg getSiteWorkAntCfgTemp(TbSiteWorkAntCfgTemp siteAntCfgTemp) {
		TbSiteAntCfg tbSiteWorkAntCfg = new TbSiteAntCfg();
		tbSiteWorkAntCfg.setSITE_ID(siteAntCfgTemp.getSITE_ID());
		tbSiteWorkAntCfg.setANT_NO(siteAntCfgTemp.getANT_NO());
		tbSiteWorkAntCfg.setCELL_ID(siteAntCfgTemp.getCELL_ID());
		tbSiteWorkAntCfg.setSECTOR_ID(siteAntCfgTemp.getSECTOR_ID());
		tbSiteWorkAntCfg.setP_CODE(siteAntCfgTemp.getP_CODE());
		tbSiteWorkAntCfg.setSEG_SOURCE(siteAntCfgTemp.getSEG_SOURCE());
		tbSiteWorkAntCfg.setANT_MODEL(siteAntCfgTemp.getANT_MODEL());
		tbSiteWorkAntCfg.setANT_HIGH(siteAntCfgTemp.getANT_HIGH());
		tbSiteWorkAntCfg.setANT_ORIENT(siteAntCfgTemp.getANT_ORIENT());
		tbSiteWorkAntCfg.setE_TILT(siteAntCfgTemp.getE_TILT());
		tbSiteWorkAntCfg.setM_TILT(siteAntCfgTemp.getM_TILT());
		tbSiteWorkAntCfg.setSETUP_STYLE(siteAntCfgTemp.getSETUP_STYLE());
		tbSiteWorkAntCfg.setCELL_RADIUS(siteAntCfgTemp.getCELL_RADIUS());
		tbSiteWorkAntCfg.setANT_SIZE(siteAntCfgTemp.getANT_SIZE());
		tbSiteWorkAntCfg.setANT_MFGR(siteAntCfgTemp.getANT_MFGR());
		tbSiteWorkAntCfg.setDOWNTILT_R(siteAntCfgTemp.getDOWNTILT_R());
		tbSiteWorkAntCfg.setFLOOR_LEVEL(siteAntCfgTemp.getFLOOR_LEVEL());
		tbSiteWorkAntCfg.setTOWER_HEIGHT(siteAntCfgTemp.getTOWER_HEIGHT());
		tbSiteWorkAntCfg.setPENTHOUSE_HEIGHT(siteAntCfgTemp.getPENTHOUSE_HEIGHT());
		tbSiteWorkAntCfg.setADDR(siteAntCfgTemp.getADDR());
		tbSiteWorkAntCfg.setLON(siteAntCfgTemp.getLON());
		tbSiteWorkAntCfg.setLAT(siteAntCfgTemp.getLAT());
		tbSiteWorkAntCfg.setF_CABLE_TYPE(siteAntCfgTemp.getF_CABLE_TYPE());
		tbSiteWorkAntCfg.setF_CABLE_LEN(siteAntCfgTemp.getF_CABLE_LEN());
		tbSiteWorkAntCfg.setJ_CABLE_LEN(siteAntCfgTemp.getJ_CABLE_LEN());
		tbSiteWorkAntCfg.setCOVER_REG(siteAntCfgTemp.getCOVER_REG());
		return tbSiteWorkAntCfg;
	}
	
	//最後再檢查是否有下一張待施作工單(STATUS=OR03, NEXT_ODR_SEQ)，若有待施作工單，則以先行透過系統自動派工方式
	private void selectWorkOrder(String orderId,String area,String workId) {
		Date date = new Date();
		// 檢查是否有下一張排隊工單，若後續還有工單則進行自動派工與通知，反之則作業完工W07
		TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);
		TbSiteWorkOrderExample orderExample = new TbSiteWorkOrderExample();
		orderExample.createCriteria().andODR_SEQGreaterThanOrEqualTo(order.getODR_SEQ())
				.andWORK_IDEqualTo(order.getWORK_ID()).andIS_ACTIVEEqualTo("Y")
				.andORDER_IDNotEqualTo(order.getORDER_ID());
		List<TbSiteWorkOrder> orderList = workOrderDao.findByConditions(orderExample);
		if (orderList.size() != 0) {
			boolean isSameOrderSeqFinish = true;
			// 檢查同一層是否完工
			for (TbSiteWorkOrder orderObj : orderList) {
				if (orderObj.getODR_SEQ() == order.getODR_SEQ()) {
					if (!OrderStatus.OR09.name().equals(orderObj.getODR_STATUS())) {
						isSameOrderSeqFinish = false;
						break;
					}
				}
			}
			// 如果同層完工檢查下一層
			if (isSameOrderSeqFinish) {
				for (TbSiteWorkOrder orderObj : orderList) {
					if (orderObj.getODR_SEQ() == order.getODR_SEQ() + 1) {
						// 如果是0R03就派工
						if (OrderStatus.OR03.name().equals(orderObj.getODR_STATUS())) {
							boolean autoAssign = respUserService.autoAssignOrder(area, orderObj); // 是否自動派工成功
							if (autoAssign) {
								siteEmailService
										.sendOrderAutoAssignMail(orderObj.getORDER_ID());
								siteEmailService.sendOrderPickupMail(orderObj.getORDER_ID());
							} else {
								siteEmailService.sendOrderAssignMail(orderObj.getORDER_ID());
							}
						}
					}
				}
			}

		} else {// 是最後一層就驗證全部工單是否完工
			orderExample = new TbSiteWorkOrderExample();
			orderExample.createCriteria().andWORK_IDEqualTo(order.getWORK_ID())
					.andODR_STATUSNotEqualTo(OrderStatus.OR09.name()).andIS_ACTIVEEqualTo("Y");
			orderList = workOrderDao.findByConditions(orderExample);
			log.debug("orderList.size() = " + orderList.size());
			TbSiteWork tbSiteWork = workDao.findByPk(workId);
			if (orderList.isEmpty()) {
				log.debug("=====更改 TbSiteMain start =====");
				TbSiteMain siteMain = siteMainDao.findByPk(tbSiteWork.getSITE_ID());
				if (siteMain != null) {					
					siteMain.setSITE_STATUS(SiteStatus.S06.name());
					siteMainDao.updateByPrimaryKeySelective(siteMain);
				}
				log.debug("=====更改 TbSiteMain end =====");
				
				//TB_SITE_STATUS_LOG
				log.debug("=====更改 STATUS LOG start =====");
				TbSiteStatusLog statusLog = new TbSiteStatusLog();
				statusLog.setBTS_SITE_ID(tbSiteWork.getBTS_SITE_ID());
				statusLog.setSITE_ID(tbSiteWork.getSITE_ID());
				statusLog.setSITE_STATUS(SiteStatus.S06.name());
				statusLog.setSTART_TIME(date);
				statusLog.setEND_TIME(date);
				siteStatusLogDao.insert(statusLog);
				log.debug("=====更改 STATUS LOG end =====");				
				
				TbSiteWork record = workDao.findByPk(workId);
				if (record != null) {
					record.setWORK_STATUS(WorkStatus.W07.name());
					record.setEND_TIME(date);
					int updateApprItem = workDao.updateByPrimaryKey(record);
					if (updateApprItem != 0) {
						siteEmailService.sendWorkApplyFinishMail(record.getWORK_ID());
					} else {
						log.error("update fail updateApprItem count=" + updateApprItem
								+ " workId =" + record.getWORK_ID());
						throw new UpdateFailException("update fail");
					}
				} else {
					log.error("SiteWork is null ,workId = " + workId);
					throw new NomsException("更新狀態失敗");
				}
			}
		}		
	}
}