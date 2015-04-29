package com.foya.noms.dao.ls;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbLsAppFormMapper;
import com.foya.dao.mybatis.mapper.TbLsAppMapper;
import com.foya.dao.mybatis.mapper.TbLsLessorAddedMapper;
import com.foya.dao.mybatis.mapper.TbLsLessorMapper;
import com.foya.dao.mybatis.mapper.TbLsLocElecAddedMapper;
import com.foya.dao.mybatis.mapper.TbLsLocElecMapper;
import com.foya.dao.mybatis.mapper.TbLsLocPaymentAddedMapper;
import com.foya.dao.mybatis.mapper.TbLsLocationAddedMapper;
import com.foya.dao.mybatis.mapper.TbLsLocationMapper;
import com.foya.dao.mybatis.mapper.TbLsMainAddedMapper;
import com.foya.dao.mybatis.mapper.TbLsMainMapper;
import com.foya.dao.mybatis.mapper.TbLsResExchAddedMapper;
import com.foya.dao.mybatis.mapper.TbLsResExchAlocAddedMapper;
import com.foya.dao.mybatis.mapper.TbLsResExchAlocMapper;
import com.foya.dao.mybatis.mapper.TbLsRewardAddedMapper;
import com.foya.dao.mybatis.mapper.TbLsRewardMapper;
import com.foya.dao.mybatis.mapper.TbLsSiteAddedMapper;
import com.foya.dao.mybatis.mapper.TbLsSiteMapper;
import com.foya.dao.mybatis.mapper.TbPayLookupCodeMapper;
import com.foya.dao.mybatis.mapper.UTbLsAppMapper;
import com.foya.dao.mybatis.mapper.UTbLsLessorAddedMapper;
import com.foya.dao.mybatis.mapper.UTbLsLessorMapper;
import com.foya.dao.mybatis.mapper.UTbLsLocElecAddedMapper;
import com.foya.dao.mybatis.mapper.UTbLsLocElecMapper;
import com.foya.dao.mybatis.mapper.UTbLsLocPaymentAddedMapper;
import com.foya.dao.mybatis.mapper.UTbLsLocPaymentMapper;
import com.foya.dao.mybatis.mapper.UTbLsLocationAddedMapper;
import com.foya.dao.mybatis.mapper.UTbLsLocationMapper;
import com.foya.dao.mybatis.mapper.UTbLsMainMapper;
import com.foya.dao.mybatis.mapper.UTbLsRewardAddedMapper;
import com.foya.dao.mybatis.mapper.UTbLsRewardMapper;
import com.foya.dao.mybatis.mapper.UTbLsSiteMapper;
import com.foya.dao.mybatis.mapper.UTbSiteMainMapper;
import com.foya.dao.mybatis.model.TbLsApp;
import com.foya.dao.mybatis.model.TbLsAppExample;
import com.foya.dao.mybatis.model.TbLsAppForm;
import com.foya.dao.mybatis.model.TbLsAppFormExample;
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
import com.foya.dao.mybatis.model.TbLsLocation;
import com.foya.dao.mybatis.model.TbLsLocationAdded;
import com.foya.dao.mybatis.model.TbLsLocationAddedExample;
import com.foya.dao.mybatis.model.TbLsLocationExample;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbLsMainAdded;
import com.foya.dao.mybatis.model.TbLsMainAddedExample;
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
import com.foya.dao.mybatis.model.TbLsSiteExample;
import com.foya.dao.mybatis.model.TbLsSiteExample.Criteria;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbPayLookupCodeExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.ls.LeaseDTO;
import com.foya.noms.dto.ls.LeaseLocationDTO;
import com.foya.noms.dto.ls.LessorChDTO;
import com.foya.noms.dto.ls.TbLsLocPaymentDTO;
import com.foya.noms.dto.ls.TbLsLocationAddedDTO;
import com.foya.noms.dto.ls.TbLsRewardAddedDTO;
import com.foya.noms.dto.ls.TbLsRewardDTO;
import com.foya.noms.dto.pay.TbLsLocElecDTO;
import com.foya.noms.dto.pay.TbLsSiteDTO;
import com.foya.noms.dto.st.SiteDTO;

@Repository
public class LS001M3Dao extends BaseDao {

	@Autowired
	private UTbLsLocationMapper uTbLsLocationMapper;

	@Autowired
	private TbLsMainMapper tbLsMainMapper;

	@Autowired
	private UTbLsLessorMapper uTbLsLessorMapper;

	@Autowired
	private UTbLsLocElecMapper uTbLsLocElecMapper;

	@Autowired
	private UTbLsSiteMapper uTbLsSiteMapper;

	@Autowired
	private UTbLsLocPaymentMapper uTbLsLocPaymentMapper;

	@Autowired
	private UTbLsRewardMapper uTbLsRewardMapper;
	
	@Autowired
	private TbLsAppFormMapper  tbLsAppFormMapper;
	
	@Autowired
	private TbLsLocElecAddedMapper tbLsLocElecAddedMapper;
	
	@Autowired
	private TbLsLocPaymentAddedMapper tbLsLocPaymentAddedMapper;
	
	@Autowired
	private UTbLsLocPaymentAddedMapper uTbLsLocPaymentAddedMapper;
	
	@Autowired
	private TbLsAppMapper tbLsAppMapper;
	
	@Autowired
	private TbLsLocationAddedMapper  tbLsLocationAddedMapper;
	
	@Autowired
	private UTbLsMainMapper uTbLsMainMapper;
	
	@Autowired
	private TbLsLocElecMapper tbLsLocElecMapper;

	@Autowired
	private UTbLsLocationAddedMapper uTbLsLocationAddedMapper;
	
	@Autowired
	private TbLsRewardAddedMapper tbLsRewardAddedMapper;
	
	@Autowired
	private UTbLsRewardAddedMapper uTbLsRewardAddedMapper;
	
	@Autowired
	private UTbSiteMainMapper uTbSiteMainMapper;
	
	@Autowired
	private  TbLsSiteMapper  tbLsSiteMapper;
	
	@Autowired
	private  TbLsMainAddedMapper tbLsMainAddedMapper;
	
	@Autowired
	private TbPayLookupCodeMapper tbPayLookupCodeMapper;
	
	@Autowired
	private TbLsLessorAddedMapper tbLsLessorAddedMapper;
	
	@Autowired
	private TbLsLessorMapper tbLsLessorMapper;
	
	@Autowired
	private UTbLsLessorAddedMapper uTbLsLessorAddedMapper;
	
	@Autowired
	private UTbLsLocElecAddedMapper uTbLsLocElecAddedMapper;
	
	@Autowired
	private TbLsResExchAlocMapper tbLsResExchAlocMapper;
	
	@Autowired
	private TbLsRewardMapper tbLsRewardMapper;
	
	@Autowired
	private TbLsLocationMapper tbLsLocationMapper;
	
	@Autowired
	private UTbLsAppMapper uTbLsAppMapper;
	
	@Autowired
	private TbLsSiteAddedMapper tbSiteAddedMapper;
	
	@Autowired
	private TbLsResExchAddedMapper tbResExchAddedMapper;
	
	@Autowired
	private TbLsResExchAlocAddedMapper tbLsResExchAlocAddedMapper;
	
	
	/**
	 * 撈取 tb_ls_location 資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param domain
	 *            區域
	 * @return
	 */
	public List<LeaseLocationDTO> searchTbLsLocation(String lsNo, String domain) {
		return uTbLsLocationMapper.getLocationVerMax(lsNo, domain);
	}

	/**
	 * 查詢合約主檔 By 合約最大版次
	 * 
	 * @param lsNo  合約編號
	 * @return
	 */
	public List<TbLsMain> selectLsMainBylsNoVerMax(String lsNo) {
		return uTbLsMainMapper.selectLsMainBylsNoVerMax(lsNo);
	}
	public List<TbLsMain> selectEffectivesMainBylsNoVerMax(String lsNo) {
		return uTbLsMainMapper.selectEffectivesMainBylsNoVerMax(lsNo);
	}
	

	/**
	 * 撈取 TB_LS_LOC_ELEC 資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param locId
	 *            站點編號
	 * @return
	 */
	public List<TbLsLocElecDTO> searchTbLsLocElecDTO(String lsNo, String locId) {
		return uTbLsLocElecMapper.searchTbLsLocElec(lsNo, locId);
	}

	/**
	 * 使用合約編號找尋最大版次的出租人資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @return
	 */
	public List<TbLsLessor> getLsLessorByNoVer(String lsNo) {
		return uTbLsLessorMapper.searchLsLessorByNoVer(lsNo,null);
	}

	
	
	/**
	 * 查詢站台資料
	 * 
	 * @param lsNo 合約編號
	 * @param locId 站點編號
	 * @return
	 */
	public List<TbLsSiteDTO> getLsSiteByLocId(HashMap<String, String > dataObj) {
		return uTbLsSiteMapper.getLsSiteByLocId(dataObj);
	}
	

	/**
	 * 
	 * @param dataObj
	 * @return
	 */
	public List<TbLsRewardDTO> getLsRewardByLsNoMaxVer(HashMap<String, String> dataObj)
	{
		return uTbLsRewardMapper.getLsRewardByLsNoMaxVer(dataObj);
	}
	
	/**
	 * 查詢 TbLsLocPaymen 資料
	 * @param lsNo
	 * 			合約編號
	 * @param locId
	 * 			站點編號
	 * @param paymentItems
	 * 			支付項目
	 * @return
	 */
	public List<TbLsLocPaymentDTO> searchTbLsLocPaymen(String lsNo, String locId,String[] paymentItems) {
		return uTbLsLocPaymentMapper.searchTbLsLocPaymen(lsNo, locId,paymentItems);
	}
	
	/**
	 * 查詢增補協議套表資訊
	 * @param appType
	 * @param subType
	 * @return
	 */
	public List<TbLsAppForm> searchLsAppFORM(String appType,List<String> subType){
		TbLsAppFormExample example=new TbLsAppFormExample();
		example.createCriteria().andAPP_TYPEEqualTo(appType).andSUB_TYPEIn(subType);
		return tbLsAppFormMapper.selectByExample(example);
	}
	
	/**
	 * 撈取暫存資料
	 * 
	 * @param lsNo
	 * 			合約編號
	 * @param locId
	 * 			站點編號
	 * @param appSql
	 * 			申請流水號
	 * @param addedState
	 * 			暫存狀態
	 * @return
	 */
	public List<TbLsLocElecDTO> searchTbLsLocElecAdded(String lsNo,String locId,String appSql,String addedState){
		return uTbLsLocElecAddedMapper.searchTbLsLocElec(lsNo, locId, appSql, addedState);
	}
	/**
	 * 撈取  TbLsLocPaymentAdded 資料
	 * @param lsNo
	 * 			合約編號
	 * @param locId
	 * 			站點編號
	 * @param appSql
	 * 			申請流水號
	 * @param addedState
	 * 			暫存狀態
	 * @return
	 */
	public List<TbLsLocPaymentDTO> searchTbLsLocPaymentAdded(String lsNo,String locId,String appSql,String addedState){
		return uTbLsLocPaymentAddedMapper.searchTbLsLocPaymentAdded(lsNo, locId, appSql, addedState);
	}
	/**
	 * 新增資料至  tbLsApp
	 * @param record
	 */
	public void insertLsApp(TbLsApp record){
		tbLsAppMapper.insert(record);
	}
	/**
	 * 更新資料至  tbLsApp
	 * @param record
	 * @param example
	 */
	public void updateLsApp(TbLsApp record,TbLsAppExample example){
		tbLsAppMapper.updateByExampleSelective(record, example);
	}
	/**
	 * 刪除 TbLsLocElecAdded 的資料 
	 * @param elecExample
	 */
	public void delTbLsLocElecAdded(TbLsLocElecAddedExample elecExample){
		tbLsLocElecAddedMapper.deleteByExample(elecExample);
		
	}
	/**
	 * 刪除 TbLsLocPaymentAdded 的資料 
	 * @param paymentExample
	 */
	public void delTbLsLocPaymentAdded(TbLsLocPaymentAddedExample paymentExample){
		tbLsLocPaymentAddedMapper.deleteByExample(paymentExample);
	}
	/**
	 * 新增 TbLsLocElecAdded 資料
	 * @param elecRecord
	 */
	public void insertTbLsLocElecAdded(TbLsLocElecAdded elecRecord){
		tbLsLocElecAddedMapper.insertSelective(elecRecord);
		
	}
	/**
	 * query TbLsLocElecAdded 
	 * @param example
	 * @return
	 */
	public List<TbLsLocElecAdded> selectTbLsLocElecAddedByExample(TbLsLocElecAddedExample example){
		return tbLsLocElecAddedMapper.selectByExample(example);
	}
	/**
	 * 新增 TbLsLocPaymentAdded 資料
	 * @param paymentRecord
	 */
	public void insertTbLsLocPaymentAdded(TbLsLocPaymentAdded paymentRecord){
		tbLsLocPaymentAddedMapper.insertSelective(paymentRecord);
	}
	
	/**
	 * 寫入TbLsLocationAdded
	 * 
	 * @param tbLsLocationAdded
	 * @return
	 */
	public int addTbLsLocationAdded(TbLsLocationAdded tbLsLocationAdded)
	{
		return tbLsLocationAddedMapper.insertSelective(tbLsLocationAdded);
	}
	
	/**
	 * 寫入增補協議申請檔
	 * 
	 * @param tbLsApp
	 * @return
	 */
	public int addTbLsApp(TbLsApp tbLsApp)
	{
		return tbLsAppMapper.insertSelective(tbLsApp);
	}
	/**
	 * 撈取 TbLsLocElec 資料
	 * @param example
	 * @return
	 */
	public List<TbLsLocElec> searchtbLsLocElec(TbLsLocElecExample example){
		return tbLsLocElecMapper.selectByExample(example);
	}
	/**
	 * 更新 TbLsLocElec 資料
	 * @param record
	 * @param example
	 */
	public void updateElecCh(TbLsLocElec record,TbLsLocElecExample example){
		tbLsLocElecMapper.updateByExampleSelective(record, example);
	}
	/**
	 * 新增 一筆 TbLsLocElec 資料
	 * @param record
	 */
	public void insertElecCh(TbLsLocElec record){
		tbLsLocElecMapper.insertSelective(record);
	}
	
	/**
	 * 寫入TbLsRewardAdded
	 * 
	 * @param tbLsRewardAdded
	 * @return
	 */
	public int addTbLsRewardAdded(TbLsRewardAdded tbLsRewardAdded)
	{
		return tbLsRewardAddedMapper.insertSelective(tbLsRewardAdded);
	}
	
	/**
	 * 刪除 TbLsRewardAdded 的資料 
	 * @param elecExample
	 */
	public void delTbLsRewardAdded(TbLsRewardAddedExample example){
		tbLsRewardAddedMapper.deleteByExample(example);
	}
	
	/**
	 * 取得手機回饋暫存檔  By合約，申請單流水號，狀態
	 * @param dataObj
	 * @return
	 */
	public List<TbLsRewardAddedDTO> searchLsRewardAddedByAppSeq(HashMap<String, String > dataObj) {
		return uTbLsRewardAddedMapper.getLsRewardAddedByAppSeq(dataObj);
	}
	
	/**
	 * 取得手機回饋暫存檔  By examples
	 * @param example
	 * @return
	 */
	public List<TbLsRewardAdded> selectTbLsRewardAddedByExample(TbLsRewardAddedExample example){
		return tbLsRewardAddedMapper.selectByExample(example);
	}
	
	
	/**
	 * 取得站點資訊  By合約，申請單流水號，狀態
	 * @param lsNo
	 * @param appSeq
	 * @param addState
	 * @return
	 */
	public List<TbLsLocationAddedDTO> searchLsLocationAddedByAppSeq(HashMap<String, String > dataObj) {
		return uTbLsLocationAddedMapper.getLsLocationAddedByAppSeq(dataObj);
	}
	
	/**
	 * 刪除 TbLsLocElecAdded 的資料 
	 * @param elecExample
	 */
	public void delTbLsLocationAdded(TbLsLocationAddedExample example){
		tbLsLocationAddedMapper.deleteByExample(example);
	}
	
	/**
	 * 查詢申請檔
	 * @param appSeq  申請檔seq
	 * @return
	 */
	public TbLsApp searchTbLsApp(String appSeq)
	{
		return tbLsAppMapper.selectByPrimaryKey(appSeq);
	}
	

	/**
	 * TB_SITE_Location下站點的站台查詢使用站點(排除某些站台) 
	 * @param locationId  站點編號
	 * @param siteStatus  站台狀態
	 * @param siteId 排除的站台編號
	 * @return
	 */
	public List<SiteDTO> getSiteMainByLocationId(String locationId,String[] siteStatus,List<String> siteId) {
		return uTbSiteMainMapper.selectSiteMainByLocIdSiteIdStatus (locationId,siteStatus,siteId);
	}
	
	/**
	 * 
	 * @param lsNo
	 * @param lsVer
	 * @param siteId
	 * @param locId
	 * @return
	 */
	public List<TbLsSite> searchTbLsSiteByLSNoIDVer(String lsNo,String lsVer,String siteId,String locId){
		TbLsSiteExample example =new TbLsSiteExample();
		example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer).andSITE_IDEqualTo(siteId).andLOCATION_IDEqualTo(locId);
		return tbLsSiteMapper.selectByExample(example);
	}

	/**
	 * 
	 * @param tbLsSite
	 * @return
	 */
	public int addTbLsSite(TbLsSite tbLsSite){
		return tbLsSiteMapper.insertSelective(tbLsSite);
	}
	
	/**
	 * 
	 * @param lsNo
	 * @param lsVer
	 * @param siteId
	 * @param locId
	 * @param lsSite
	 * @return
	 */
	public int updateTbLsSiteByChangeSiteId(String lsNo,String lsVer,String siteId,String locId,TbLsSite lsSite){
		TbLsSiteExample example =new TbLsSiteExample();
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(lsNo)){
			criteria.andLS_NOEqualTo(lsNo);
		}
		if(StringUtils.isNotBlank(lsVer)){
			criteria.andLS_VEREqualTo(lsVer);
		}
		if(StringUtils.isNotBlank(siteId)){
			criteria.andSITE_IDEqualTo(siteId);
		}
		if(StringUtils.isNotBlank(locId)){
			criteria.andLOCATION_IDEqualTo(locId);
		}
		return tbLsSiteMapper.updateByExampleSelective(lsSite, example);
	}
	/**
	 * 刪除 TbLsMainAdded 資料
	 * @param example
	 * 			條件值
	 */
	public void delTbLsMainAdded(TbLsMainAddedExample example){
		tbLsMainAddedMapper.deleteByExample(example);
	}
	/**
	 * 新增  TbLsMainAdded 資料
	 * @param record
	 */
	public void insertTbLsMainAdded(TbLsMainAdded record){
		tbLsMainAddedMapper.insertSelective(record);
	}
	/**
	 * 撈取 TbLsMainAdded 資料
	 * @param example
	 * 			條件值
	 * @return
	 */
	public List<TbLsMainAdded> searchTbLsMainAdded(TbLsMainAddedExample example){
		return tbLsMainAddedMapper.selectByExample(example);
	}
	
	/**
	 * 查詢TB_PAY_LOOKUP_CODE項目
	 * @param LookupType
	 * @return
	 */
	public List<TbPayLookupCode> getPayLookupByType(String LookupType){
		TbPayLookupCodeExample example=new TbPayLookupCodeExample();
		example.createCriteria().andLOOKUP_TYPEEqualTo(LookupType);
		
		return tbPayLookupCodeMapper.selectByExample(example);
	}
	
	/**
	 * 查詢出租人暫存檔筆數
	 * @param appSeq 申請流水號
	 * @param lsNo 合約編號
	 * @param addedState 暫存檔狀態
	 * @return
	 */
	public int lessorCountByLsNoAppSeq(String appSeq,String lsNo,String addedState){
		TbLsLocElecAddedExample example =new TbLsLocElecAddedExample();
		example.createCriteria().andAPP_SEQEqualTo(appSeq).andLS_NOEqualTo(lsNo).andADDED_STATEEqualTo(addedState);
		return tbLsLocElecAddedMapper.countByExample(example);
	}
	
	/**
	 * 查詢出租人資訊
	 * @param dataObj 查詢條件
	 * @return
	 */
	public List<LessorChDTO> serarchLsLessorBylsNoLessorId(HashMap<String, String > dataObj)
	{
		return uTbLsLessorMapper.serarchLsLessorBylsNoLessorId(dataObj);
	}
	
	/**
	 * 查詢出租人暫存檔資料
	 * @param dataObj
	 * @return
	 */
	public List<LessorChDTO> searchLsLessorAddByAppSeqState(HashMap<String, String > dataObj)
	{
		return uTbLsLessorAddedMapper.searchLsLessorAddByAppSeqState(dataObj);
	}
	
	
	/**
	 * 查詢出租人暫存檔資料
	 * @param example
	 * @return
	 */
	public List<TbLsLessorAdded> searchLsLessorAddedByExample(TbLsLessorAddedExample example){
		return tbLsLessorAddedMapper.selectByExample(example);
	}
	
	/**
	 * 查詢出租人主檔
	 * @param example
	 * @return
	 */
	public List<TbLsLessor> searchLsLessorByExample(TbLsLessorExample example){
		return tbLsLessorMapper.selectByExample(example);
	}
	
	
	/**
	 * 查詢出租人暫存檔筆數
	 * @param example 查詢條件值
	 * @return
	 */
	public int lessorCountByLsNoIdAppSeq(TbLsLessorAddedExample example){
		return tbLsLessorAddedMapper.countByExample(example);
	}
	
	
	/**
	 * 查詢出租人暫存檔資料
	 * @param dataObj
	 * @return
	 */
	public List<TbLsLessor> searchLsLessorAddByAppSeq(HashMap<String, String > dataObj)
	{
		return uTbLsLessorAddedMapper.searchLsLessorAddByAppSeq(dataObj);
	}
	
	/**
	 * 寫入出租人暫存檔資料
	 * @param tbLsLessorAdded
	 * @return
	 */
	public int addLsLessorAdded(TbLsLessorAdded tbLsLessorAdded)
	{
		return tbLsLessorAddedMapper.insertSelective(tbLsLessorAdded);
	}
	
	
	/**
	 * 刪除 TbLsLessorAdded 的資料 
	 * @param example 條件值
	 * @return
	 */
	public int delTbLsLessorAdded(TbLsLessorAddedExample example)
	{
		return tbLsLessorAddedMapper.deleteByExample(example);
	}
	
	/**
	 *  撈取 付款資訊 資料
	 * @param object 條件值
	 * @return
	 */
	public List<TbLsLocPaymentDTO> getLsLocPaymentByLsNoVerLocIdLessorId(HashMap<String,Object> object) {	   
			return uTbLsLocPaymentMapper.getLsLocPaymentByLsNoVerLocIdLessorId(object);
	}
	
	/**
	 * 查詢付款暫存檔筆數
	 * @param example 查詢條件值
	 * @return
	 */
	public int paymentCountByExample(TbLsLocPaymentAddedExample example){
		return tbLsLocPaymentAddedMapper.countByExample(example);
	}
	
	/**
	 *  撈取 付款資訊暫存 資料
	 * @param object 條件值
	 * @return
	 */
	public List<TbLsLocPaymentDTO> getLsLocPaymentAddedByLsNoVerLocIdLessorId(HashMap<String,Object> object) {	   
			return uTbLsLocPaymentAddedMapper.getLsLocPaymentAddedByLsNoVerLocIdLessorId(object);
	}
	
	/**
	 * 寫入 付款資訊暫存 資料
	 * @param tbLsLocPaymentAdded
	 */
	public void insertLsLocPaymentAdded(TbLsLocPaymentAdded tbLsLocPaymentAdded){
		tbLsLocPaymentAddedMapper.insertSelective(tbLsLocPaymentAdded);
	}
	
	
	/**
	 * 計算合約編號以及該狀態底下的資料筆數
	 * @param lsNo 合約編號
	 * @param status 狀態代碼
	 * @return int 
	 */
	public int getCountAppStatus(String lsNo,String status){
		TbLsAppExample example =new TbLsAppExample();
		example.createCriteria().andLS_NOEqualTo(lsNo).andAPP_STATUSEqualTo(status);
		return tbLsAppMapper.countByExample(example);
	}
	
	public List<TbLsApp> selectTbLsAppByExampleWithBLOBs(TbLsAppExample example){
		return tbLsAppMapper.selectByExampleWithBLOBs(example);
	}
	
	public List<TbLsSite> searchLsSiteByLsNoLocIdVer(String lsNo,String locId){
		return uTbLsSiteMapper.searchLsSiteByLsNoLocIdVer(lsNo, locId);
	}
	
	/**
	 * 更新資源互換分攤檔
	 * @param tbLsResExchAloc
	 * @param example
	 */
	public void updateLsResExchAloc(TbLsResExchAloc tbLsResExchAloc,TbLsResExchAlocExample example)
	{
		tbLsResExchAlocMapper.updateByExampleSelective(tbLsResExchAloc, example);
	}
	
	/**
	 * 寫入合約回饋檔
	 * @param tbLsReward
	 */
	public void addTbLsReward(TbLsReward tbLsReward)
	{
		tbLsRewardMapper.insertSelective(tbLsReward);
	}
	
	/**
	 * 計算Lessor筆數
	 * @param example example
	 * @return 筆數
	 */
	public int countLessorByExample(TbLsLessorExample example){
		return tbLsLessorMapper.countByExample(example);
	}
	
	public TbLsLocation getEditElecCh(String appSeq,String lsNo){
		List<TbLsLocation> list = uTbLsLocationMapper.getEditElecCh(lsNo, appSeq);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * search Tb_Ls_Location_Added
	 * @param example
	 * @return
	 */
	public List<TbLsLocationAdded> selectTbLsLocationAddedByExampleWithBLOBs(TbLsLocationAddedExample example){
		return tbLsLocationAddedMapper.selectByExampleWithBLOBs(example);
	}
	
	/**
	 * update Tb_Ls_Location
	 * @param record object 
	 * @param example key
	 * @return int
	 */
	public int updateTbLsLocationByExample(TbLsLocation record , TbLsLocationExample example){
		return tbLsLocationMapper.updateByExampleSelective(record,example);
	}
	
	/**
	 * max(ls_var)
	 * @param Example
	 * @return object
	 */
	public TbLsLocation selectMaLsvarTbLsLocation(String lsNo,String locateionId){
		return uTbLsLocationMapper.selectMaLsvar(lsNo ,locateionId);
	}
	
	/**
	 * count bLsResExchAloct  ByExample
	 * @param example
	 * @return
	 */
	public int countbLsResExchAloctByExample(TbLsResExchAlocExample example){
		return tbLsResExchAlocMapper.countByExample(example);
	}
	
	public LeaseDTO selectMaxVerForTbLsApp(String appSeq){
		return uTbLsAppMapper.selectMaxVerForTbLsApp(appSeq);
	}
	
	public List<TbLsLocPaymentAdded> selectTbLsLocPaymentAddedByExample(TbLsLocPaymentAddedExample example){
		return tbLsLocPaymentAddedMapper.selectByExample(example);
	}
	
	public TbLsLocPayment selectTblslocpaymentMaxVer (String lsNo,String locationId){
		return uTbLsLocPaymentMapper.selectTblslocpaymentMaxVer(lsNo,locationId);
	}
	
	/**
	 * query MAx Ver By Ls_No 
	 * @param lsNo lsNo
	 * @return
	 */
	public TbLsLocElec selectTblslocelecMAxVerByLsNo(String lsNo,String locationId){
		return uTbLsLocElecAddedMapper.selectTblslocelecMAxVerByLsNo(lsNo,locationId);
	}
	
	
	/*** Arda Start  ***/
	
	public void insertTbLsSiteAdded(TbLsSiteAdded tbLsSiteAdded){
		
		tbSiteAddedMapper.insertSelective(tbLsSiteAdded);
	}
	public void insertTbLsResExchAdded(TbLsResExchAdded tbLsResExchAdded){
		
		tbResExchAddedMapper.insertSelective(tbLsResExchAdded);
	}
	
	public void insertTbLsResExchAlocAdded(TbLsResExchAlocAdded tbLsResExchAlocAdded){
		
		tbLsResExchAlocAddedMapper.insertSelective(tbLsResExchAlocAdded);
	}
	
	public void updateLsSiteAdded(TbLsSiteAdded record, TbLsSiteAddedExample example) {
		
		tbSiteAddedMapper.updateByExampleSelective(record, example);
	}
	/**
	 * 更新一筆資料至 TbLsLocationAdded
	 * 
	 * @param record 更新值
	 */
	public void updateLeaseLocationAdded(TbLsLocationAdded record, TbLsLocationAddedExample example) {
		
		tbLsLocationAddedMapper.updateByExampleSelective(record, example);
	}
	/**
	 * 更新一筆資料至 TbLsLocationAdded
	 * 
	 * @param record 更新值
	 */
	public void updateLsLocPaymentAdded(TbLsLocPaymentAdded record,TbLsLocPaymentAddedExample example) {
		
		tbLsLocPaymentAddedMapper.updateByExampleSelective(record, example);
	}
	
	/**
	 * 更新一筆資料至 tblslocelecAdded
	 * 
	 * @param record 更新值
	 */
	public void updateLsLocElecAdded(TbLsLocElecAdded record,TbLsLocElecAddedExample example) {
		
		tbLsLocElecAddedMapper.updateByExampleSelective(record, example);
	}
	
	
	/*** Arda End  ***/
	
	
	public int queryPaymentAmtByLocItem(HashMap<String, Object > dataObj){
		return uTbLsLocPaymentMapper.queryPaymentAmtByLocItem(dataObj);
	}
	

	public List<TbLsReward> selectTbLsRewardMaxVerbyLsNo(String lsNo){
		return uTbLsRewardMapper.selectMaxVerbyLsNo(lsNo);
	}
	
	public TbLsLocElec getLsLocElecMaxVerbyLsNo(String lsNo){
		return uTbLsLocElecMapper.getMaxVerbyLsNo(lsNo);
	}
	
	public List<TbLsSiteAdded> selectTbSiteAddedByExample(TbLsSiteAddedExample example){
		return tbSiteAddedMapper.selectByExample(example);
	}
	
	public TbLsSite getTbLsSiteMaxLsVer(String lsNo){
		return uTbLsSiteMapper.getMaxLsVer(lsNo);
	}
	
	public List<TbLsResExchAdded> selectTbLsResExchAddedByExample(TbLsResExchAddedExample example){
		return tbResExchAddedMapper.selectByExample(example);
	}
	
	public  List<TbLsResExchAlocAdded> selectTbLsResExchAlocAddedByExample(TbLsResExchAlocAddedExample example){
		return tbLsResExchAlocAddedMapper.selectByExample(example);
	}
	
}
