package com.foya.noms.service.st;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSiteWorkOrderExample;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTemp;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTempKey;
import com.foya.dao.mybatis.model.TbSysOrderType;
import com.foya.dao.mybatis.model.TbSysOrderTypeExample;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.common.TownDomainTeamDao;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.st.AntCfgDao;
import com.foya.noms.dao.st.AntCfgTempDao;
import com.foya.noms.dao.st.LocTempDao;
import com.foya.noms.dao.st.LocationDao;
import com.foya.noms.dao.st.MeterialOptDao;
import com.foya.noms.dao.st.OutsourcingDao;
import com.foya.noms.dao.st.ST003Dao;
import com.foya.noms.dao.st.ST005Dao;
import com.foya.noms.dao.st.SiteMainDao;
import com.foya.noms.dao.st.SiteStatusLogDao;
import com.foya.noms.dao.st.SiteTempDao;
import com.foya.noms.dao.st.WorkDao;
import com.foya.noms.dao.st.WorkOrderDao;
import com.foya.noms.dao.st.line.LineApplyDao;
import com.foya.noms.dao.system.OrderTypeDao;
import com.foya.noms.dao.system.WorkOrderTypeDao;
import com.foya.noms.dto.basic.CompanyDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.foya.noms.dto.system.OrderTypeDTO;
import com.foya.noms.service.common.EmailTemplateService;
import com.foya.noms.service.st.base.BaseOrderProcessService;
import com.foya.noms.service.system.OrderTypeService;
import com.foya.noms.service.system.RespUserService;
import com.foya.noms.service.system.UniqueSeqService;
import com.foya.noms.service.workflow.WorkflowActionService;
import com.foya.noms.util.LabelValueModel;

@Service
public class ST005Service extends BaseOrderProcessService {

	@Autowired
	private DeptDao deptDao;
	
	@Autowired
	private ST005Dao st005Dao;
	
	@Autowired
	private PersonnelDao personnelDao;
	
	@Autowired
	private SiteTempDao siteTempDao;
	
	@Autowired
	private WorkOrderDao workOrderDao;
	
	@Autowired
	private WorkOrderTypeDao workOrderTypeDao;
	
	@Autowired
	private ST003Dao st003Dao;
	
	@Autowired
	private OrderTypeDao orderTypeDao;
	
	@Autowired
	private OutsourcingDao outsourcingDao;
	
	@Autowired
	private MeterialOptDao meterialOptDao;
	
	@Autowired
	private LineApplyDao lineApplyDao;
	
	@Autowired
	private WorkDao workDao;
	
	@Autowired
	private SiteStatusLogDao siteStatusLogDao;
	
	@Autowired
	private LocationDao locationDao;
	
	@Autowired
	private SiteMainDao siteMainDao;
	
	@Autowired
	private LocTempDao locTempDao;
	
	@Autowired
	private AntCfgDao antCfgDao;
	
	@Autowired
	private AntCfgTempDao antCfgTempDao;
	
	@Autowired
	private WorkflowActionService workflowActionService;
	
	@Autowired
	private EmailTemplateService emailTemplateService;
	
	@Autowired
	private SiteEmailService siteEmailService;
	
	@Autowired
	private RespUserService respUserService;
	
//	@Autowired
//	private INVService iNVService;
	
	@Autowired
	private OrderTypeService orderTypeService;
	
	@Autowired
	private TownDomainTeamDao townDomainTeamDao;
	
	@Autowired
	private UniqueSeqService uniqueSeqService;
	
	@Autowired
	private ST003Service st003Service;
	
	
	/**
	 * 查詢頁面-接工單位下拉選單
	 * 
	 * @param deptList
	 * @return
	 */
	public List<LabelValueModel> getDeptAll(List<String> deptIdList) {
		//TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		List<String> allDeptTemp = new ArrayList<String>();
		
		List<String> deptList = st003Dao.findDeptAll(); //查出接工單位
		List<String> teamList = st003Dao.findTeamAll(); //查出負責單位
		//將兩個單位合併
		allDeptTemp.addAll(teamList);
		allDeptTemp.addAll(deptList);
			
			//搜尋出 交集 的 接工 & 負責單位
			Set<String> allDeptList = new HashSet<>(allDeptTemp);
			allDeptList.retainAll(deptIdList);
			//System.out.println("測試 >>>>>>" + deptIdList.size());
			
			//利用 查詢出來的 交集條件 組合出 代碼中文
			TbOrgDeptExample example = new TbOrgDeptExample();
			example.createCriteria().andDEPT_IDIn(deptIdList);
			List<TbOrgDept> list = deptDao.findByCondition(example);
			List<LabelValueModel> deptTemp = new ArrayList<LabelValueModel>();
			for (TbOrgDept temp : list) {
				deptTemp.add(new LabelValueModel(temp.getDEPT_NAME(), temp.getDEPT_ID()));
			}
			return deptTemp;
	}
	
	/**
	 * 委外廠商
	 * 
	 * @return
	 */
	public List<CompanyDTO> getCompany(String coVatNo) {
		return st005Dao.findCompany(coVatNo);
	}
	
	/**
	 * 委外廠商
	 * 
	 * @return
	 */
	public List<CompanyDTO> getCompanyAll() {
		return st005Dao.findCompanyAll();
	}
	
	/**
	 * 查詢全部SiteWorkOrder
	 * 
	 * @param map
	 * @return
	 */
	public List<SiteWorkOrderDTO> getWorkLOrderist(Map<String, Object> map) {
		return st005Dao.findWorkOrderList(map);
	}
	
	/**
	 * 負責人 (利用 psnNo找出 ChnNM)
	 */
//	public List<TbAuthPersonnel> findPsnName(String psnNo) {
//		return personnelDao.searchPsnsByPsnNo(psnNo);
//	}
	
	/**
	 * 用Example查詢SiteSearch
	 * @param Example
	 * @return
	 */
	public List<TbSiteWorkOrder> getSiteWorkOrderByOrderId(TbSiteWorkOrderExample example){
		return st005Dao.getSiteWorkOrderByOrderId(example);
	}
	
	/**
	 * 用Example查詢SiteSearch
	 * @param Example
	 * @return
	 */
	public List<TbSysOrderType> getSysOrderTypeByOrderType(TbSysOrderTypeExample example){
		return st005Dao.getSysOrderTypeByOrderType(example);
	}
	
	/**
	 * 接工.
	 *
	 * @param siteWorkOrder the site work order
	 * @return the int
	 */
//	public int updateSiteWorkOrderSelective(TbSiteWorkOrder siteWorkOrder){
//		return st005Dao.updateSiteWorkOrderSelective(siteWorkOrder);
//	}
	
//	public int updateSiteWorkOrder(TbSiteWorkOrder record) {
//		return st005Dao.updateSiteWorkOrder(record);
//	}

	/**
	 * 由PK查詢工單
	 * @param orderId
	 * @return
	 */
//	public TbSiteWorkOrder getOrderByKey(String orderId){
//		return st005Dao.selectOrderByKey(orderId);
//	}
	
	public List<TbSiteWorkOrder> getOrderByCondition(TbSiteWorkOrderExample example) {
		return workOrderDao.findByConditions(example);
	}
	
//	public TbSiteWork getWorkByKey(String workId) {
//		return st005Dao.selectWorkByKey(workId);
//	}
	
	/**
	 * 由Conditions查詢工單種類.
	 *
	 * @param example the example
	 * @return the list
	 */
//	public List<TbSysOrderPage> selectByExample(TbSysOrderPageExample example){
//		return st005Dao.selectByExample(example);
//	}
	/**
	 * 依照PrimaryKey查詢SiteWorkSiteTemp
	 * @param key
	 */
	public TbSiteWorkSiteTemp getSiteTempByPrimaryKey(TbSiteWorkSiteTempKey key){
		return siteTempDao.findByPrimaryKey(key);
	}
	
	/**
	 * 用workId和isActive查詢SiteWorkOrder
	 * @param workId
	 * @param isActive
	 * @return
	 */
//	public List<SiteWorkOrderDTO> getSiteWorkOrderByWorkIdAndIsActive(String workId,String isActive){
//		return st005Dao.findSiteWorkOrderByWorkIdAndIsActive(workId, isActive);
//	}
	
	/**
	 * 取得建站所有派工目的
	 */
	public List<LabelValueModel> getWorkOrderTypesForBuild() {
		List<OrderTypeDTO> types = orderTypeDao.findAllOrderTypesForBuild();
		
		List<LabelValueModel> models = new LinkedList<>();
		for (OrderTypeDTO type : types) {
			models.add(new LabelValueModel(type.getOD_TYPE_NAME(), type.getOD_TYPE_ID()));
		}
		return models;
	}
	
	/**
	 * 重啟工單
	 * @param orderId
	 * @param reopenOrderDesc
	 * @return
	 */
//	@Transactional
//	public void reopenOrder(String orderId,String reopenOrderDesc,String workType)throws UpdateFailException{
//		TbSiteWorkOrder workOrder = workOrderDao.findOrderByPk(orderId);
//		workOrder.setODR_STATUS(OrderStatus.OR04.name());
//		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//		String reponeUserInfo = "重啟工單者: "+this.getLoginUser().getChName()+" ,重啟時間 :" + sdFormat.format(new Date());
//		if(StringUtils.isNotEmpty(reopenOrderDesc)){
//			workOrder.setORDER_DESC(reopenOrderDesc+"\n"+reponeUserInfo);
//		}else{
//			String orderType = orderTypeDao.findByPk(workOrder.getORDER_TYPE()).getOD_TYPE_NAME();
//			workOrder.setORDER_DESC(WorkType.detectLabel(workType)+"-"+orderType+"\n"+reponeUserInfo);
//		}
//		int workOrderItem = workOrderDao.updateSelective(workOrder);
//		if(workOrderItem == 0){
//			log.error("update fail workOrderItem count = "+workOrderItem+", orderId = "+orderId);
//			throw new UpdateFailException("更新失敗");
//		}
//	}
	
	/**
	 * 退工
	 * @param workId
	 * @return
	 * @throws NomsException
	 * @throws UpdateFailException
	 */
//	@Transactional
//	public List<String> dropOrder(String workId, String endDesc)throws UpdateFailException{
//		TbSiteWorkOrderExample workOrderExample = new TbSiteWorkOrderExample();
//		workOrderExample.createCriteria().andWORK_IDEqualTo(workId).andIS_ACTIVEEqualTo("Y");
//		List<String> errorMessageListe = new ArrayList<String>();
//		List<String> osStatusList = new ArrayList<String>();
//		osStatusList.add(OutSourceStatus.OS07.name());
//		osStatusList.add(OutSourceStatus.OS08.name());
//		List<TbSiteWorkOrder> workOrderList = workOrderDao.findByConditions(workOrderExample);
//		for(TbSiteWorkOrder workOrder : workOrderList){
//			//退工時需檢核其下之委外工單(TB_SITE_OUTSOURCING)是否OS07已驗收或OS08取消。
//			TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
//			example.createCriteria().andORDER_IDEqualTo(workOrder.getORDER_ID()).andSTATUSNotIn(osStatusList);
//			List<TbSiteOutsourcing> outSourcingList = outsourcingDao.findSiteOutSoureByExample(example);
//			if(outSourcingList.size()!=0){
//				errorMessageListe.add("工單編號 : "+workOrder.getORDER_ID()+" 有委外工單未"+OutSourceStatus.OS07.getLocalName()+"或"+OutSourceStatus.OS08.getLocalName());
//			}
//			//退工時需檢核其下之領料之料件(TB_INV_MATERIAL_OPT)需全數進行退料申請(狀態為RT02退料完成)
//			TbInvMaterialOptExample materialOptexample = new TbInvMaterialOptExample();
//			materialOptexample.createCriteria().andORDER_ID_INEqualTo(workOrder.getORDER_ID()).andSTATUSNotEqualTo("RT02");
//			List<TbInvMaterialOpt> materialOptList = meterialOptDao.findInvMaterialOptByOrderId(materialOptexample);//.findMaterialOptByConditions(materialOptexample);
//			if(materialOptList.size()!=0){
//				errorMessageListe.add("工單編號 : "+workOrder.getORDER_ID()+" 有物料操作單未退料完成");
//			}
//			//退工時需檢核其下之專線申請(TB_SITE_LINE_APPLY)需全數狀態為:LA07已取消
//			TbSiteLineApplyExample lineApplyExample = new TbSiteLineApplyExample();
//			lineApplyExample.createCriteria().andORDER_IDEqualTo(workOrder.getORDER_ID()).andAPP_STATUSNotEqualTo(LineApplyStatus.LA07.name());
//			List<TbSiteLineApply> lineApplyList = lineApplyDao.findByConditions(lineApplyExample);
//			if(lineApplyList.size()!=0){
//				errorMessageListe.add("工單編號 : "+workOrder.getORDER_ID()+" 其下之專線申請還有未取消的");
//			}
//		}
//		if(errorMessageListe.size() == 0 ){
//			//Update TB_SITE_WORK_ORDER to OR07 where IS_ACTIVE=Y by workId
//			int workOrderItem = workOrderDao.updateStatusByWork(workId, OrderStatus.OR07);
//			if(workOrderItem == 0){
//				log.error("update fail workOrderItem count = "+workOrderItem+" , workId = "+workId);
//				throw new UpdateFailException("更新失敗");
//			}
//			//Update TB_SITE_WORK set status to W06 by workId
//			int updateWorkItem = workDao.updateStatusByWork(workId, WorkStatus.W06);
//			if(updateWorkItem == 0){
//				log.error("update fail updateWorkItem count = "+updateWorkItem+" , workId = "+workId);
//				throw new UpdateFailException("更新失敗");
//			}
//			//update TB_SITE_MAIN set status to S04
//			TbSiteWork work = workDao.findByPk(workId);
//			int updateSiteItem = siteMainDao.updateSiteStatusBySiteId(work.getSITE_ID(), SiteStatus.valueOf(work.getSTART_SITE_STATUS()));	// 轉為取得作業前狀態 modify by Charlie 201250318
//			if(updateSiteItem == 0){
//				log.error("update fail updateSiteItem count = "+updateSiteItem+" , siteId = "+ work.getSITE_ID());
//				throw new UpdateFailException("更新失敗");
//			}
//		}
//		return errorMessageListe;
//	}
	
	/**
	 * 完工送審前驗證
	 * 
	 * @param workId
	 * @param orderId
	 * @return
	 */
//	public String doValidate(String workId, String orderId) {
//		
//		log.debug("送完工前驗證：workId=" + workId + ", orderId=" + orderId);
//		TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);
//		// modify by Charlie 20150306
//		Integer notFinished = st003Service.getNotFinishedOrders(workId, orderId);
//		if (notFinished > 0) return "尚有前置工單未完工！";
//		
//		Integer odrSeq = order.getODR_SEQ();
//		if (odrSeq == 99) {	// 物料處理工單
//			String mtOrderId = getOriginalMaterialOrderId(order);	// 取得舊有土木工單的ORDER_ID
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("orderId", mtOrderId);
//			List<MeterialRtnDTO> list = meterialOptDao.selectRemInsItemQuery(map);
//			for (MeterialRtnDTO model : list) {
//				if (model.getAssetQty() > 0) {	// 若尚有一筆未處理完的在途物料
//					log.debug("****物料處理工單尚有在途料件**** orderId=" + order.getORDER_ID());
//					return "工單編號：" + orderId + ", 所屬物料操作單尚有在途物料未處理完畢！料號：" + model.getMatNo();
//				}
//			}
//		} else {			
//			//領料單申請 已發料完成(STATUS=MR05)
//			TbInvMaterialOptExample optExampleA = new TbInvMaterialOptExample();
//			optExampleA.createCriteria().andORDER_IDEqualTo(orderId).andOPT_TYPEEqualTo(MaterialOptType.MRQ.name()).andSTATUSNotEqualTo("MR05");
//			List<TbInvMaterialOpt> optListMRQ = meterialOptDao.findInvMaterialOptByOrderId(optExampleA);//.findMaterialOptByConditions(optExampleA);
//			//退料單申請 已退料完成(STATUS=RT02)
//			TbInvMaterialOptExample optExampleR = new TbInvMaterialOptExample();
//			optExampleR.createCriteria().andORDER_IDEqualTo(orderId).andOPT_TYPEEqualTo(MaterialOptType.RTN.name()).andSTATUSNotEqualTo("RT02");
//			List<TbInvMaterialOpt> optListRTN = meterialOptDao.findInvMaterialOptByOrderId(optExampleR);//.findMaterialOptByConditions(optExampleR);
//			
//			log.debug("驗證物料需全數發料OR退料：MRQ=" + optListMRQ.size() + ", RTN=" + optListRTN.size());
//			if (!optListMRQ.isEmpty() || !optListRTN.isEmpty()) {
//				return "工單編號：" + orderId + ", 所屬物料操作單需全數發料完成或退料完成！";
//			}
//		}
//		
//		//委外申請
//		List<String> status = new ArrayList<String>();
//		status.add(OutSourceStatus.OS07.name());
//		status.add(OutSourceStatus.OS08.name());
//		TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
//		example.createCriteria().andORDER_IDEqualTo(orderId).andSTATUSNotIn(status);
//		List<TbSiteOutsourcing> list = outsourcingDao.findSiteOutSoureByExample(example);
//		if (list != null && list.size() > 0) {
//			return "工單編號：" + orderId + ", 所屬委外派工申請單須皆已驗收或已取消！";
//		}
//		
//		//專線需要皆已竣工LA06或已取消LA07 add by Charlie 20150313
//		List<String> appStatus = new ArrayList<String>();
//		appStatus.add(LineApplyStatus.LA06.name());
//		appStatus.add(LineApplyStatus.LA07.name());
//		TbSiteLineApplyExample lineCriteria = new TbSiteLineApplyExample();
//		lineCriteria.createCriteria().andORDER_IDEqualTo(orderId).andAPP_STATUSNotIn(appStatus);
//		List<TbSiteLineApply> lines = lineApplyDao.findByConditions(lineCriteria);
//		if (lines != null && lines.size() > 0) {
//			return "工單編號：" + orderId + ", 所屬專線申請單須皆已竣工或已取消！";
//		}
//		
//		return null;	
//	}
	
	/**
	 * 完工送審
	 * @param workId
	 * @param orderId
	 * @return
	 * @throws Exception 
	 */
//	@Transactional
//	public void verifyOrder(String orderId, String workId, String mdUser) {
//		Date currentDate = new Date();
//		
//		try {		
//			TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);// new TbSiteWorkOrder();
//			order.setORDER_ID(orderId);
//			order.setODR_STATUS(OrderStatus.OR08.name());
//			order.setMD_USER(mdUser);
//			order.setMD_TIME(currentDate);
//			st005Dao.updateSiteWorkOrderSelective(order);
//			
//			workflowActionService.apply("SiteBuildCompletionApply", orderId, "建站工單完工申請",
//					emailTemplateService.getMailVarMapForWorkflow(EmailType.WORKFLOW_TODO_WORK_COMPLETION_APPLY, orderId));
//		} catch (WorkflowException e) {		
//			log.error(e.getMessage(), e);
//			throw e;
//		}		
//	}
	
	/**
	 * 建站作業完工-簽核
	 * 
	 * @param orderId
	 *            作業編號
	 * @return
	 * @throws NomsException
	 */
//	@Transactional
//	public void finishSiteBuildOrder(String orderId, String action, String mdUser) throws NomsException {
//		Date mdTime = new Date();
//		try {
//			TbSiteWorkOrder tbSiteWorkOrder = workOrderDao.findOrderByPk(orderId);
//			
//			if (StringUtils.equals(action, AppConstants.WORKFLOW_REST_REJECT)) {
//				
//				TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
//				example.createCriteria().andORDER_IDEqualTo(orderId);
//				List<TbSiteOutsourcing> outSourcingList = outsourcingDao
//						.findSiteOutSoureByExample(example);
//				int updateStatusItem = 0;
//				if (outSourcingList.size() == 0) {
//					updateStatusItem = workOrderDao.updateStatusByOrder(orderId, OrderStatus.OR05);
//				} else {
//					updateStatusItem = workOrderDao.updateStatusByOrder(orderId, OrderStatus.OR06);
//				}
//				if (updateStatusItem == 0) {
//					log.error("update fail updateStatusItem count=" + updateStatusItem + " orderId ="
//							+ "orderId");
//					throw new UpdateFailException("update fail");
//				} 
//				
//			} else {	// 完工核可
//				tbSiteWorkOrder.setODR_STATUS(OrderStatus.OR09.name());
//				tbSiteWorkOrder.setMD_USER(mdUser);
//				tbSiteWorkOrder.setMD_TIME(mdTime);
//				tbSiteWorkOrder.setEND_TIME(mdTime);
//				tbSiteWorkOrder.setEND_USER(mdUser);
//				// 1.更新成 已完工
//				st005Dao.updateSiteWorkOrderSelective(tbSiteWorkOrder);
//				
//				TbSiteWork tbSiteWork = workDao.findByPk(tbSiteWorkOrder.getWORK_ID());	
//				// 2.該工單類型若為土木工單類(ORDER_TYPE=SC、SCH、SCN)時，完工時額外檢查工單下若有在途物料，則新增99處理工單
//				if (tbSiteWorkOrder.getODR_SEQ() != 99 && !StringUtils.equals("SGL", tbSiteWork.getWORK_TYPE())) {	
//					// 非單張工單和非物料操作單狀況下，才需要新增
//					boolean isNewAnOrder = toNewMaterialOptionOrder(tbSiteWork, tbSiteWorkOrder);
//					log.debug("====>> 是否有新增土木物料處理工單：" + isNewAnOrder);
//				}
//				
//				if (StringUtils.equals(tbSiteWork.getWORK_TYPE(), "SGL")) {
//					tbSiteWork.setWORK_STATUS(WorkStatus.W07.name());
//					tbSiteWork.setMD_USER(mdUser);
//					tbSiteWork.setMD_TIME(mdTime);
//					tbSiteWork.setEND_TIME(mdTime);
//					tbSiteWork.setEND_DATE(mdTime);
//					workDao.updateByPrimaryKey(tbSiteWork);
//				}
//				
//				// 3.寫入站點主檔存檔
//				log.debug("=====站點主檔 start =====");
//				TbSiteWorkLocTempKey key = new TbSiteWorkLocTempKey();
//				key.setWORK_ID(tbSiteWork.getWORK_ID());
//				key.setLOCATION_ID(tbSiteWork.getLOCATION_ID());
//				TbSiteWorkLocTemp locationTemp = locTempDao.selectByPrimaryKey(key);
//				TbSiteLocation siteLoc = this.getSiteLocation(locationTemp, tbSiteWork);
//				siteLoc.setCR_TIME(mdTime);
//				siteLoc.setCR_USER(mdUser);
//				siteLoc.setMD_TIME(mdTime);
//				siteLoc.setMD_USER(mdUser);
//				int locationData = locationDao.update(siteLoc);
//				
//				if (locationData == 0) {
//					log.error("站點主檔 update fail workLoc count=" + locationData + " locationId ="
//							+ tbSiteWork.getLOCATION_ID());
//					throw new CreateFailException("update fail");
//				}
//				log.debug("=====站點主檔 end =====");
//				
//				// 4.寫入站台主檔
//				log.debug("=====站台主檔 start =====");
//				TbSiteWorkSiteTempKey siteKey = new TbSiteWorkSiteTempKey();
//				siteKey.setWORK_ID(tbSiteWork.getWORK_ID());
//				siteKey.setSITE_ID(tbSiteWork.getSITE_ID());
//				TbSiteWorkSiteTemp siteTempData = siteTempDao.findByPrimaryKey(siteKey);
//				TbSiteMain siteMain = this.getSiteWorkSiteTemp(siteTempData, tbSiteWork);
//				siteMain.setCR_USER(mdUser);
//				siteMain.setCR_TIME(mdTime);
//				siteMain.setMD_USER(mdUser);
//				siteMain.setMD_TIME(mdTime);	
//				//siteMain.setSITE_STATUS(SiteStatus.S06.name());
//				int main = siteMainDao.updateByPrimaryKeySelective(siteMain);
//
//				if (main == 0) {
//					log.error("站台主檔存檔 update fail workSite count=" + main + " siteId ="
//							+ tbSiteWork.getSITE_ID());
//					throw new CreateFailException("update fail");
//				}
//				log.debug("=====站台主檔存檔 end =====");
//				
//				// 5.寫入天線主檔
//				log.debug("=====天線主檔存檔 start =====");
//				TbSiteWorkAntCfgTempExample cfgExample = new TbSiteWorkAntCfgTempExample();
//				cfgExample.createCriteria().andSITE_IDEqualTo(tbSiteWork.getSITE_ID()).andWORK_IDEqualTo(tbSiteWork.getWORK_ID());			
//				List<TbSiteWorkAntCfgTemp> siteAntCfgTempList = antCfgTempDao.findByCondition(cfgExample);
//				
//				//先將 主檔 資料刪除後，再將 暫存檔資料新增
//				TbSiteAntCfgExample antCfgExample = new TbSiteAntCfgExample();
//				antCfgExample.createCriteria().andSITE_IDEqualTo(tbSiteWork.getSITE_ID());
//				antCfgDao.deleteSiteAntCfg(antCfgExample);
//				for (TbSiteWorkAntCfgTemp cfgTempList : siteAntCfgTempList) {
//					
//					TbSiteAntCfg tbSiteAntCfg = this.getSiteWorkAntCfgTemp(cfgTempList);
//					tbSiteAntCfg.setCR_USER(mdUser);
//					tbSiteAntCfg.setCR_TIME(mdTime);
//					tbSiteAntCfg.setMD_USER(mdUser);
//					tbSiteAntCfg.setMD_TIME(mdTime);
//					int siteSrAntCfg = antCfgDao.insert(tbSiteAntCfg);
//					if (siteSrAntCfg == 0) {
//						log.error("天線主檔 insert fail siteSrAntCfg count=" + siteSrAntCfg + " siteId ="
//								+ tbSiteWork.getSITE_ID());
//						throw new CreateFailException("insert fail");
//					}
//				}
//				
//				log.debug("=====天線主檔存檔 end =====");
//				// 6.最後再檢查是否有下一張待施作工單(STATUS=OR03, NEXT_ODR_SEQ)，若有待施作工單，則以先行透過系統自動派工方式
//				// 7.EMAIL通知
//				selectWorkOrder(orderId, tbSiteWork.getAREA(), tbSiteWork.getWORK_ID());
//			}
//			
//		} catch(NomsException e) {		
//			log.error(e.getMessage(), e);
//			throw e;
//		} catch(Exception ex) {
//			log.error(ex.getMessage(), ex);
//			throw new NomsException(ExceptionUtils.getFullStackTrace(ex));
//		}
//	}
	
//	public String getOriginalMaterialOrderId(TbSiteWorkOrder siteWorkOrder) {
//		Map<String, String> materialOrderMap = new HashMap<>();
//		materialOrderMap.put("MO", "SC");
//		materialOrderMap.put("MOH", "SCH");
//		materialOrderMap.put("MON", "SCN");
//		
//		if (materialOrderMap.containsKey(siteWorkOrder.getORDER_TYPE())) {
//			String toSearchOrderType = materialOrderMap.get(siteWorkOrder.getORDER_TYPE());
//			TbSiteWorkOrderExample orderExample = new TbSiteWorkOrderExample();
//			orderExample.createCriteria().andWORK_IDEqualTo(siteWorkOrder.getWORK_ID()).andORDER_TYPEEqualTo(toSearchOrderType);
//			List<TbSiteWorkOrder> mtOrders = getOrderByCondition(orderExample);	// 同張作業下照理只有一張獨立TYPE種類工單
//			if (!mtOrders.isEmpty()) {				
//				return mtOrders.get(0).getORDER_ID();
//			}
//		}
//		return "";
//	}
	
//	private TbSiteLocation getSiteLocation(TbSiteWorkLocTemp siteLocTemp, TbSiteWork siteWork) {
//		TbSiteLocation tbSiteLocation = new TbSiteLocation();
//		//siteWorkLocTemp.setWORK_ID(siteWork.getWORK_ID());
//		tbSiteLocation.setLOCATION_ID(siteWork.getLOCATION_ID());
//		tbSiteLocation.setLOC_NAME(siteLocTemp.getLOC_NAME());
//		tbSiteLocation.setLOC_TYPE(siteLocTemp.getLOC_TYPE());
//		tbSiteLocation.setLON(siteLocTemp.getLON());
//		tbSiteLocation.setLAT(siteLocTemp.getLAT());
//		tbSiteLocation.setMAINT_AREA(siteLocTemp.getMAINT_AREA());
//		tbSiteLocation.setMAINT_DEPT(siteLocTemp.getMAINT_DEPT());
//		tbSiteLocation.setMAINT_TEAM(siteLocTemp.getMAINT_TEAM());
//		tbSiteLocation.setCNT_PSN(siteLocTemp.getMAINT_PSN());
//		tbSiteLocation.setLOC_DESC(siteLocTemp.getLOC_DESC());
//		tbSiteLocation.setBASE_TYPE(siteLocTemp.getBASE_TYPE());
//		tbSiteLocation.setINDOOR_OPTION(siteLocTemp.getINDOOR_OPTION());
//		tbSiteLocation.setNS_LEVEL(siteLocTemp.getNS_LEVEL());
//		tbSiteLocation.setBUILDING_HEIGHT(siteLocTemp.getBUILDING_HEIGHT());
//		tbSiteLocation.setSHARE_TYPE(siteLocTemp.getSHARE_TYPE());
//		// siteWorkLocTemp.setSHARE_COM(siteLoc.getSHARE_COM());
//		tbSiteLocation.setIS_FREEENTER(siteLocTemp.getIS_FREEENTER());
//		tbSiteLocation.setIS_KEY(siteLocTemp.getIS_KEY());
//		tbSiteLocation.setIS_INDOOR(siteLocTemp.getIS_INDOOR());
//		tbSiteLocation.setNEED_MISC_LIC(siteLocTemp.getNEED_MISC_LIC());
//		tbSiteLocation.setHAS_MISC_LIC(siteLocTemp.getHAS_MISC_LIC());
//		tbSiteLocation.setADDR(siteWork.getADDR());
//		tbSiteLocation.setADD_OTHER(siteLocTemp.getADD_OTHER());
//		tbSiteLocation.setZIP(siteWork.getZIP());
//		tbSiteLocation.setCITY(siteWork.getCITY());
//		tbSiteLocation.setAREA(siteWork.getAREA());
//		tbSiteLocation.setVILLAGE(siteWork.getVILLAGE());
//		tbSiteLocation.setADJACENT(siteWork.getADJACENT());
//		tbSiteLocation.setROAD(siteWork.getROAD());
//		tbSiteLocation.setLANE(siteWork.getLANE());
//		tbSiteLocation.setALLEY(siteWork.getALLEY());
//		tbSiteLocation.setSUB_ALLEY(siteWork.getSUB_ALLEY());
//		tbSiteLocation.setDOOR_NO(siteWork.getDOOR_NO());
//		tbSiteLocation.setDOOR_NO_EX(siteWork.getDOOR_NO_EX());
//		tbSiteLocation.setFLOOR(siteWork.getFLOOR());
//		tbSiteLocation.setFLOOR_EX(siteWork.getFLOOR_EX());
//		tbSiteLocation.setROOM(siteWork.getROOM());
//		tbSiteLocation.setADD_OTHER(siteWork.getADD_OTHER());
//		tbSiteLocation.setSPACE_ROOM(siteLocTemp.getSPACE_ROOM());
//		tbSiteLocation.setSPACE_ROOF(siteLocTemp.getSPACE_ROOF());
//		tbSiteLocation.setSPACE_TOP(siteLocTemp.getSPACE_TOP());
//		tbSiteLocation.setSPACE_PLATFORM(siteLocTemp.getSPACE_PLATFORM());
//		tbSiteLocation.setSPACE_LAND(siteLocTemp.getSPACE_LAND());
//		tbSiteLocation.setSPACE_INDOOR(siteLocTemp.getSPACE_INDOOR());
//		tbSiteLocation.setSPACE_OUTDOOR(siteLocTemp.getSPACE_OUTDOOR());
//		tbSiteLocation.setFOOTAGE(siteLocTemp.getFOOTAGE());
//		tbSiteLocation.setEMP_RELATION(siteLocTemp.getEMP_RELATION());
//		tbSiteLocation.setMAINT_USER(siteLocTemp.getMAINT_PSN());
//		return tbSiteLocation;
//	}
//	
//	private TbSiteMain getSiteWorkSiteTemp(TbSiteWorkSiteTemp siteTemp, TbSiteWork siteWork) {
//		TbSiteMain main = new TbSiteMain();
//		//siteWorkSiteTemp.setWORK_ID(siteWork.getWORK_ID());
//		main.setSITE_ID(siteTemp.getSITE_ID());
//		main.setFEQ_ID(siteTemp.getFEQ_ID());
//		if (siteTemp.getSR_ID() == null) {
//			main.setSR_ID("");
//		} else {
//			main.setSR_ID(siteTemp.getSR_ID());
//		}
//		main.setBTS_SITE_ID(siteTemp.getBTS_SITE_ID());
//		main.setSITE_NAME(siteTemp.getSITE_NAME());
//		main.setLOCATION_ID(StringUtils.trimToNull(siteTemp.getLOCATION_ID()));
//		main.setPURPOSE(siteTemp.getPURPOSE());
//		main.setEQP_TYPE_ID(StringUtils.trimToNull(siteTemp.getEQP_TYPE_ID()));
//		main.setEQP_MODEL_ID(StringUtils.trimToNull(siteTemp.getEQP_MODEL_ID()));
//		main.setCOVERAGE_TYPE(StringUtils.trimToNull(siteTemp.getCOVERAGE_TYPE()));
//		main.setBTS_CONFIG(StringUtils.trimToNull(siteTemp.getBTS_CONFIG()));
//		main.setDONOR_SITE(StringUtils.trimToNull(siteTemp.getDONOR_SITE()));
//		main.setFEEDERLESS(StringUtils.trimToNull(siteTemp.getFEEDERLESS()));
//		main.setMASTER_SITE(StringUtils.trimToNull(siteTemp.getMASTER_SITE()));
//		main.setCLUSTER(StringUtils.trimToNull(siteTemp.getMASTER_SITE()));
//		main.setRNC_BTS(StringUtils.trimToNull(siteTemp.getRNC_BTS()));
//		main.setNIOS_RPT_STATUS(StringUtils.trimToNull(siteTemp.getNIOS_RPT_STATUS()));
//		main.setCELL_STATUS(StringUtils.trimToNull(siteTemp.getCELL_STATUS()));
//		main.setOMCU_OBJ(StringUtils.trimToNull(siteTemp.getOMCU_OBJ()));
//		main.setSPEED_UPLOAD(StringUtils.trimToNull(siteTemp.getSPEED_UPLOAD()));
//		main.setSPEED_DOWNLOAD(StringUtils.trimToNull(siteTemp.getSPEED_DOWNLOAD()));
//		main.setPOWER(StringUtils.trimToNull(siteTemp.getPOWER()));
//		main.setCST_ID_CARD_NUM(StringUtils.trimToNull(siteTemp.getCST_ID_CARD_NUM()));
//		main.setCST_MOBILE(StringUtils.trimToNull(siteTemp.getCST_MOBILE()));
//		main.setCST_ID(StringUtils.trimToNull(siteTemp.getCST_ID()));
//		main.setCOVERAGE_INDOOR(StringUtils.trimToNull(siteTemp.getCOVERAGE_INDOOR()));
//		main.setHIDDEN(StringUtils.trimToNull(siteTemp.getHIDDEN()));
//		main.setNOIS_ON_AIR(StringUtils.trimToNull(siteTemp.getNOIS_ON_AIR()));
//		main.setL2_SWITCH(StringUtils.trimToNull(siteTemp.getL2_SWITCH()));
//		return main;
//	}
//
//	private TbSiteAntCfg getSiteWorkAntCfgTemp(TbSiteWorkAntCfgTemp siteAntCfgTemp) {
//		TbSiteAntCfg tbSiteWorkAntCfg = new TbSiteAntCfg();
//		tbSiteWorkAntCfg.setSITE_ID(siteAntCfgTemp.getSITE_ID());
//		tbSiteWorkAntCfg.setANT_NO(siteAntCfgTemp.getANT_NO());
//		tbSiteWorkAntCfg.setCELL_ID(siteAntCfgTemp.getCELL_ID());
//		tbSiteWorkAntCfg.setSECTOR_ID(siteAntCfgTemp.getSECTOR_ID());
//		tbSiteWorkAntCfg.setP_CODE(siteAntCfgTemp.getP_CODE());
//		tbSiteWorkAntCfg.setSEG_SOURCE(siteAntCfgTemp.getSEG_SOURCE());
//		tbSiteWorkAntCfg.setANT_MODEL(siteAntCfgTemp.getANT_MODEL());
//		tbSiteWorkAntCfg.setANT_HIGH(siteAntCfgTemp.getANT_HIGH());
//		tbSiteWorkAntCfg.setANT_ORIENT(siteAntCfgTemp.getANT_ORIENT());
//		tbSiteWorkAntCfg.setE_TILT(siteAntCfgTemp.getE_TILT());
//		tbSiteWorkAntCfg.setM_TILT(siteAntCfgTemp.getM_TILT());
//		tbSiteWorkAntCfg.setSETUP_STYLE(siteAntCfgTemp.getSETUP_STYLE());
//		tbSiteWorkAntCfg.setCELL_RADIUS(siteAntCfgTemp.getCELL_RADIUS());
//		tbSiteWorkAntCfg.setANT_SIZE(siteAntCfgTemp.getANT_SIZE());
//		tbSiteWorkAntCfg.setANT_MFGR(siteAntCfgTemp.getANT_MFGR());
//		tbSiteWorkAntCfg.setDOWNTILT_R(siteAntCfgTemp.getDOWNTILT_R());
//		tbSiteWorkAntCfg.setFLOOR_LEVEL(siteAntCfgTemp.getFLOOR_LEVEL());
//		tbSiteWorkAntCfg.setTOWER_HEIGHT(siteAntCfgTemp.getTOWER_HEIGHT());
//		tbSiteWorkAntCfg.setPENTHOUSE_HEIGHT(siteAntCfgTemp.getPENTHOUSE_HEIGHT());
//		tbSiteWorkAntCfg.setADDR(siteAntCfgTemp.getADDR());
//		tbSiteWorkAntCfg.setLON(siteAntCfgTemp.getLON());
//		tbSiteWorkAntCfg.setLAT(siteAntCfgTemp.getLAT());
//		tbSiteWorkAntCfg.setF_CABLE_TYPE(siteAntCfgTemp.getF_CABLE_TYPE());
//		tbSiteWorkAntCfg.setF_CABLE_LEN(siteAntCfgTemp.getF_CABLE_LEN());
//		tbSiteWorkAntCfg.setJ_CABLE_LEN(siteAntCfgTemp.getJ_CABLE_LEN());
//		tbSiteWorkAntCfg.setCOVER_REG(siteAntCfgTemp.getCOVER_REG());
//		return tbSiteWorkAntCfg;
//	}
	
	//最後再檢查是否有下一張待施作工單(STATUS=OR03, NEXT_ODR_SEQ)，若有待施作工單，則以先行透過系統自動派工方式
//	private void selectWorkOrder(String orderId,String area,String workId) {
//		
//		// 檢查是否有下一張排隊工單，若後續還有工單則進行自動派工與通知，反之則作業完工W07
//		TbSiteWorkOrder order = workOrderDao.findOrderByPk(orderId);
//		TbSiteWorkOrderExample orderExample = new TbSiteWorkOrderExample();
//		orderExample.createCriteria().andODR_SEQGreaterThanOrEqualTo(order.getODR_SEQ())
//				.andWORK_IDEqualTo(order.getWORK_ID()).andIS_ACTIVEEqualTo("Y")
//				.andORDER_IDNotEqualTo(order.getORDER_ID());
//		List<TbSiteWorkOrder> orderList = workOrderDao.findByConditions(orderExample);
//		if (!orderList.isEmpty()) {
//			boolean isSameOrderSeqFinish = true;
//			// 檢查同一層是否完工
//			for (TbSiteWorkOrder orderObj : orderList) {
//				if (orderObj.getODR_SEQ() == order.getODR_SEQ()) {
//					if (!OrderStatus.OR09.name().equals(orderObj.getODR_STATUS())) {
//						isSameOrderSeqFinish = false;
//						break;
//					}
//				}
//			}
//			// 如果同層完工檢查下一層
//			if (isSameOrderSeqFinish) {
//				for (TbSiteWorkOrder orderObj : orderList) {
//					if (orderObj.getODR_SEQ() == order.getODR_SEQ() + 1) {
//						// 如果是0R03就派工
//						if (OrderStatus.OR03.name().equals(orderObj.getODR_STATUS())) {
//							boolean autoAssign = respUserService.autoAssignOrder(area, orderObj); // 是否自動派工成功
//							if (autoAssign) {
//								siteEmailService.sendOrderAutoAssignMail(orderObj.getORDER_ID());
//								siteEmailService.sendOrderPickupMail(orderObj.getORDER_ID());
//							} else {
//								siteEmailService.sendOrderAssignMail(orderObj.getORDER_ID());
//							}
//						}
//					}
//				}
//				
//				doFinalCheckOrdersAllFinished(workId);
//			}
//
//		} else {
//			// 是最後一層就驗證全部工單是否完工
//			doFinalCheckOrdersAllFinished(workId);
//		}		
//	}
	
//	private boolean doFinalCheckOrdersAllFinished(String workId) {
//		Date mdTime = new Date();		
//		TbSiteWorkOrderExample orderExample = new TbSiteWorkOrderExample();
//		orderExample.createCriteria().andWORK_IDEqualTo(workId)
//				.andODR_STATUSNotEqualTo(OrderStatus.OR09.name()).andIS_ACTIVEEqualTo("Y");
//		List<TbSiteWorkOrder> orderList = workOrderDao.findByConditions(orderExample);
//		log.debug("orderList.size() = " + orderList.size());
//		TbSiteWork work = workDao.findByPk(workId);
//		
//		if (orderList.isEmpty()) {
//			log.debug("=====更改 TbSiteMain start =====");
//			TbSiteMain siteMain = siteMainDao.findByPk(work.getSITE_ID());
//			if (siteMain != null) {					
//				siteMain.setSITE_STATUS(work.getEND_SITE_STATUS());	// modify by Charlie 20150318 轉為從作業取基站目的狀態
//				if (StringUtils.equals(work.getEND_SITE_STATUS(), SiteStatus.S06.name())) {
//					siteMain.setONAIR_DATE(mdTime);
//				} 
//				//停用狀態的話 回押 停用日期
//				if (StringUtils.equals(work.getEND_SITE_STATUS(), SiteStatus.S08.name())) {
//					siteMain.setSUSPEND_DATE(mdTime);
//				}
//				//拆站狀態的話 回押 拆站日期
//				if (StringUtils.equals(work.getEND_SITE_STATUS(), SiteStatus.S11.name())) {
//					siteMain.setDUMP_DATE(mdTime);
//				}
//				siteMainDao.updateByPrimaryKeySelective(siteMain);
//				
//				// add by Charlie 拆站需檢查並更新站台下專線的狀態=>L06閒置中( TO FIXME Phase II時需將拆站作業搬出新建的Service實作)
//				if (StringUtils.equals(SiteStatus.S11.name(), siteMain.getSITE_STATUS())) {
//					// check all sites dump or not in the same location
//					TbSiteMainExample example = new TbSiteMainExample();
//					example.createCriteria().andLOCATION_IDEqualTo(siteMain.getLOCATION_ID()).andSITE_STATUSNotEqualTo(SiteStatus.S11.name());
//					List<TbSiteMain> otherSites = siteMainDao.findByConditions(example);
//					if (otherSites.isEmpty()) {
//						// all sites dump
//						TbSiteLineApplyExample lineExample = new TbSiteLineApplyExample();
//						lineExample.createCriteria().andA_LOCEqualTo(siteMain.getLOCATION_ID()).andLINE_STATUSEqualTo(LineStatus.L02.name());
//						List<TbSiteLineApply> aLines = lineApplyDao.findByConditions(lineExample);
//						
//						lineExample.clear();
//						lineExample.createCriteria().andB_LOCEqualTo(siteMain.getLOCATION_ID()).andLINE_STATUSEqualTo(LineStatus.L02.name());
//						List<TbSiteLineApply> bLines = lineApplyDao.findByConditions(lineExample);
//						
//						aLines.addAll(bLines);	// add both
//						for (TbSiteLineApply line : aLines) {
//							line.setLINE_STATUS(LineStatus.L06.name());
//							lineApplyDao.update(line);
//						}
//					}
//				}
//			}
//			log.debug("=====更改 TbSiteMain end =====");
//			
//			//TB_SITE_STATUS_LOG
//			log.debug("=====更改 STATUS LOG start =====");
//			TbSiteStatusLog statusLog = new TbSiteStatusLog();
//			statusLog.setBTS_SITE_ID(work.getBTS_SITE_ID());
//			statusLog.setSITE_ID(work.getSITE_ID());
//			statusLog.setSITE_STATUS(work.getEND_SITE_STATUS());	// modify by Charlie 20150318 轉為從作業取基站目的狀態
//			statusLog.setSTART_TIME(mdTime);
//			statusLog.setEND_TIME(mdTime);
//			siteStatusLogDao.insert(statusLog);
//			log.debug("=====更改 STATUS LOG end =====");
//			
//			TbSiteWork record = workDao.findByPk(workId);
//			if (record != null) {
//				record.setWORK_STATUS(WorkStatus.W07.name());
//				record.setEND_TIME(mdTime);
//				record.setEND_DATE(mdTime);
//				int updateApprItem = workDao.updateByPrimaryKey(record);
//				
//				if (updateApprItem != 0) {
//					siteEmailService.sendWorkApplyFinishMail(record.getWORK_ID());
//				} else {
//					log.error("update fail updateApprItem count=" + updateApprItem
//							+ " workId =" + record.getWORK_ID());
//					throw new UpdateFailException("update fail");
//				}
//			} else {
//				log.error("SiteWork is null ,workId = " + workId);
//				throw new NomsException("更新狀態失敗");
//			}
//			
//			// fucking FIXME 呼叫物料安裝完工API(INVService.completion)
////			orderExample.clear();
////			orderExample.createCriteria().andWORK_IDEqualTo(workId).andIS_ACTIVEEqualTo("Y");
////			List<TbSiteWorkOrder> allFinishOrders = workOrderDao.findByConditions(orderExample);
////			TbSiteOutsourcingExample example = new TbSiteOutsourcingExample();
////			for (TbSiteWorkOrder order : allFinishOrders) {
////				example.clear();
////				example.createCriteria().andORDER_IDEqualTo(order.getORDER_ID()).andSTATUSEqualTo(OutSourceStatus.OS07.name());
////				List<TbSiteOutsourcing> outs = outsourcingDao.findSiteOutSoureByExample(example);	
////				if(!outs.isEmpty()) {
////					for (TbSiteOutsourcing out : outs) {						
////						boolean isSuccess = iNVService.completion(work.getOS_TYPE(), order.getORDER_ID(), work.getLOCATION_ID(), 
////								work.getSITE_ID(), getLoginUser().getUsername(), null, out.getAP_DATE(), 
////								out.getAP_AMOUNT(), null, out.getOS_ID());
////						if (!isSuccess) log.info("物料完工API：osID=" + out.getOS_ID() + ", 呼叫驗收失敗！");
////					}
////				}
////			}
//		}
//		
//		log.debug("全部工單完工？" + orderList.isEmpty()); 
//		return orderList.isEmpty();
//	}
	
//	private boolean toNewMaterialOptionOrder(TbSiteWork work, TbSiteWorkOrder order) {
//		boolean allMaterialHandled = true;	// 先預設所有物料皆已處理完畢
//		String hasMtOptOrderType = "SC,SCH,SCN";
//		if (StringUtils.contains(hasMtOptOrderType, order.getORDER_TYPE())) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("orderId", order.getORDER_ID());
//			List<MeterialRtnDTO> list = meterialOptDao.selectRemInsItemQuery(map);
//			for (MeterialRtnDTO model : list) {
//				if (model.getAssetQty() > 0) {	// 若尚有一筆未處理完的在途物料
//					allMaterialHandled = false;
//					log.debug("****土木工單尚有在途料件**** orderId=" + order.getORDER_ID());
//					break;
//				}
//			}
//		}			
//		if (!allMaterialHandled) {
//			// 若有在途物料，新增一筆物料處理工單，作業順序為99
//			List<OrderTypeDTO> orderList = orderTypeDao.findOrderTypeByWorkTypeId(work.getWORK_TYPE(), 99);
//			if (!orderList.isEmpty()) {	
//				TownDomainTeamDTO domainTeam = townDomainTeamDao.getTownDomainTeamByCityIdTownId(work.getCITY(), work.getAREA());
//				for (OrderTypeDTO orderType : orderList) {						
//					TbSiteWorkOrder siteWorkOrder = new TbSiteWorkOrder();
//					siteWorkOrder.setORDER_ID(uniqueSeqService.getNextOrderId(work.getWORK_ID()));
//					siteWorkOrder.setWORK_ID(work.getWORK_ID());
//					siteWorkOrder.setODR_STATUS(OrderStatus.OR03.name());
//					siteWorkOrder.setPRIORITY(work.getPRIORITY());
//					siteWorkOrder.setODR_SEQ(orderType.getOrderSeq());
//					siteWorkOrder.setORDER_TYPE(orderType.getOD_TYPE_ID());
//					siteWorkOrder.setIS_ACTIVE("Y");
//					TbOrgDept dept = orderTypeService.getOrderTypeForWorkArea(orderType.getDEPT_KEY(), domainTeam);
//					siteWorkOrder.setREP_DEPT(dept.getDEPT_ID());
//					
//					siteWorkOrder.setCR_USER("SYSTEM");
//					siteWorkOrder.setCR_TIME(new Date());
//					siteWorkOrder.setMD_USER("SYSTEM");
//					siteWorkOrder.setMD_TIME(new Date());
//					workOrderDao.insert(siteWorkOrder);
//				}
//			}
//		}
//		return !allMaterialHandled;
//	}
}
