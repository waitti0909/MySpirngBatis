package com.foya.noms.service.pay;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbLsCollectUnit;
import com.foya.dao.mybatis.model.TbLsCollectUnitExample;
import com.foya.dao.mybatis.model.TbLsCollectUnitSub;
import com.foya.dao.mybatis.model.TbLsCollectUnitSubExample;
import com.foya.dao.mybatis.model.TbLsLessor;
import com.foya.dao.mybatis.model.TbLsLessorExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbOrgDomain;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.exception.NomsException;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.pay.PayPublicUtilDao;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.service.BaseService;
@Service
public class PayPublicUtilService extends BaseService{

	@Autowired
	public PayPublicUtilDao payPublicUtilDao;
	@Autowired
	public PersonnelDao personnelDao;
	
	/*查詢網路服務事業部組織清單*/
	public List<TbOrgDomain> getAllDomain() {
		return payPublicUtilDao.getAllDomain();
	}
	/*查詢網路服務事業部組織清單*/
	public List<DeptDTO> getAllFourthDept() {
		return payPublicUtilDao.getAllFourthDept();
	}
	
	/*查詢登入用戶的部門編號*/
	public UserDTO getPersonnelDeptId(String userId) {
		return payPublicUtilDao.getPersonnelDeptId(userId);
	}
	
	/*以Type,Code查詢系統設定資料*/
	public List<TbSysLookup> getLookupByTypeAndCode(String type, String code) {
		return payPublicUtilDao.getLookupByTypeAndCode(type, code);
	}
	
	/*以Type查詢系統設定資料*/
	public List<TbSysLookup> getLookupByType(String type) {
		return payPublicUtilDao.getLookupByType(type);	
	}
	
	public List<TbAuthPersonnel> selectPersonnelByDeptId(String deptId){
		return personnelDao.selectPersonnelByDeptId(deptId);
	}
	
	/**
	 * 查詢申請人透過 維運區代碼
	 * 
	 * @param Domain
	 * @return list of TbAuthPersonnel
	 */
	public List<TbAuthPersonnel> selectAppUserByDomain(String domain){
		return personnelDao.selectAppUserByDomain(domain);
	}
	
	/**
	 * 查詢申請人透過  psn no 
	 * 
	 * @param Domain
	 * @return UserDTO
	 */
	public TbAuthPersonnel searchByPsnNo(String psnNo){
		return personnelDao.searchByPsnNo(psnNo);
	}
	
	/*以區域查詢管理單位*/
	public List<TbOrgDept> getDeptByDomain(TbOrgDeptExample example){
		return payPublicUtilDao.getDeptByDomain(example);
	}
	
	/*以Type,name查詢系統設定資料*/
	public List<TbSysLookup> getLookupByTypeAndName(String type, String name) {
		return payPublicUtilDao.getLookupByTypeAndName(type, name);
	}
	/*取得時間*/
	public Date getToday() {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 0);
		date = calendar.getTime();
		return date;
	}
	/*取得時間字串格式*/
	public String getTodayString() {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 0);
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String dateString = formatter.format(date);
		log.debug(dateString+" date: "+date);
		return dateString;
	}
	
	/*取得時間字串格式*/
	public String getTodayStringWithoutTime() {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 0);
		date = calendar.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(date);
		return dateString;
	}
	public Date plusOneDay(Date day) throws ParseException{
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		cal.add(Calendar.DATE, 1);
		Date returnDate=cal.getTime();
		return returnDate;
	}
	
	public String calulateDate(String dataStr,int year,int month,int day) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
        Date dt=sdf.parse(dataStr);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.YEAR,year);
        rightNow.add(Calendar.MONTH,month);
        rightNow.add(Calendar.DAY_OF_YEAR,day);
        Date dt1=rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
	}
	
	public int calulateMonthDiff(Date startDate,Date endDate) throws ParseException{
		Calendar startCal = new GregorianCalendar();
	    Calendar dealCal = new GregorianCalendar();
	    startCal.setTime(startDate);
	    dealCal.setTime(endDate);
	    return (dealCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR)) * 12 + dealCal.get(Calendar.MONTH) - startCal.get(Calendar.MONTH);
	}
	/*以Type,name查詢系統設定資料*/
	public List<TbPayLookupCode> getPayLookupByTypeAndName(String type, String code) {
		return payPublicUtilDao.getPayLookupByTypeAndName(type, code);
	}
	
	/*以Type,name查詢系統設定資料 單一*/
	public TbPayLookupCode getPayLookupByTypeAndCode(String type, String code) {
		return payPublicUtilDao.getPayLookupByTypeAndCode(type, code);
	}
	
	/*以Type查詢系統設定資料*/
	public List<TbPayLookupCode> getPayLookupByType(String type) {
		return payPublicUtilDao.getPayLookupByType(type);
	}
	/*取得站台資料*/
	public TbSiteMain getSiteMainByPk(String siteId){
		return payPublicUtilDao.getgetSiteMainByPk(siteId);
	}
	/*取得區域資料*/
	public TbOrgDomain selectTbOrgDomainByPrimaryKey(String id){
		return payPublicUtilDao.selectTbOrgDomainByPrimaryKey(id);
	}
	
	/*以Type, code like 查詢系統設定資料*/
	public List<TbPayLookupCode> getPayLookupByTypeAndCodeLike(String type, String code) {
		return payPublicUtilDao.getPayLookupByTypeAndCodeLike(type, code);
	}	
	/*查詢付款證號名稱*/
	public List<TbLsLessor> getTbLsLessor(String lessorId){
		TbLsLessorExample example = new TbLsLessorExample();
		example.createCriteria().andLESSOR_IDEqualTo(lessorId);
		return payPublicUtilDao.getTbLsLessor(example);
	}
	
	/*取得銀行資料*/
	public List<TbLsCollectUnit> getTbLsCollectUnit(){
		TbLsCollectUnitExample example = new TbLsCollectUnitExample();
		return payPublicUtilDao.getTbLsCollectUnit(example);
	}
	
	/*取得分行資料*/
	public List<TbLsCollectUnitSub> getTbLsCollectUnitSub(String bankCode){
		TbLsCollectUnitSubExample example = new TbLsCollectUnitSubExample();
		example.createCriteria().andUNIT_CODEEqualTo(bankCode);
		return payPublicUtilDao.getTbLsCollectUnitSub(example);
	}
	/**
	 * 計算所得稅/健保費/營業稅  Call PAY_FN_GET_TAX
	 * 
	 * @param type 1:所得稅/2:健保費/3: 營業稅
	 * @param amount 傳入金額
	 * @return 計算後金額
	 */
	public Integer payFnGetTax(String type, BigDecimal amount, String includeTax){
		return payPublicUtilDao.payFnGetTax(type, amount, includeTax);
	}
	//暫時功能,將移除....
	public Integer payFnGetTax(String type, BigDecimal amount){
		return payPublicUtilDao.payFnGetTax(type, amount, null);
	}
	/**
	 * 取得類別名稱 TB_PAY_PAYMENT_ITEM.ITEM_DESC
	 * 
	 * @param paymentReqItem 請款類別
	 * @param expItem 費用項目(代碼)
	 * @return 費用項目-名稱
	 */
	public String getPayPaymentItemDescByExpItem(String paymentReqItem, String expItem){
		return payPublicUtilDao.getPayPaymentItemDescByExpItem(paymentReqItem, expItem);
	}
	/**
	 * 租約異動  Call PAY_PC_GET_APP_AMT
	 * 
	 * @param key 暫存資料KEY,以此作為第二次執行的標準
	 * @param domain 維運區
	 * @param contractNo 租約編號
	 * @param locationId  站點代碼
	 * @param paymentItem  付款項目R:租金/RD:租金押金/E:用電/ED:用電押金
	 * @param appDate 執行請款日期     
	 * @param paymentPeriod 付款週期              
	 * @param apYYYYMM 請款年月 YYYYMM    
	 * @param appType 首期(F)/例行(R)/補請(B) 
	 * 
	 * @return 執行結果:
	 	PO_CURSOR --list
	 	PO_TIME_STAMP		-- 暫存資料KEY
	 	PO_TOT_APP_AMT     -- 總含稅金額                           
		PO_TOT_BS_TAX_AMT  -- 總營業稅金額                         
		PO_TOT_IN_TAX_AMT  -- 總所得稅金額                         
		PO_TOT_HS_AMT        -- 總健保補充保費                     
		PO_APP_START_DATE   --請款起始日期                                     
		PO_APP_END_DATE  	--請款迄日期
	 * @throws NomsException
	              
	 */
	public Map<String, Object> payPcGetAppAmt(String key, String domain, String contractNo, 
			String locationId, String energyMeter, String paymentItem,
			Date appDate, String paymentPeriod, String apYYYYMM, String appType) throws NomsException{
		HashMap<String, Object> dataObj = new HashMap<String,Object>();
		dataObj.put("PI_TIME_STAMP", key);
		dataObj.put("PI_DOMAIN", domain);
		dataObj.put("PI_CONTRACT_NO", contractNo);			
		dataObj.put("PI_LOCATION_ID", locationId);
		dataObj.put("PI_ENERGY_METER", energyMeter);		
		dataObj.put("PI_PAYMENT_ITEM", paymentItem);
		dataObj.put("PI_APP_DATE", appDate);
		dataObj.put("PI_PAYMENT_PERIOD", paymentPeriod);
		dataObj.put("PI_AP_YYYYMM", apYYYYMM);
		dataObj.put("PI_APP_TYPE", appType);
		List<Map<String, Object>> list = payPublicUtilDao.payPcGetAppAmt(dataObj);
		if (!dataObj.get("PO_ERR_CDE").equals("00")) {
			log.error("租約異動 Call PAY_PC_GET_APP_AMT Error：ERR_CDE="
					+ dataObj.get("PO_ERR_CDE") + ", PO_ERR_MSG="
					+ dataObj.get("PO_ERR_MSG"));
			throw new NomsException("99", "租約異動 執行錯誤：" + dataObj.get("PO_ERR_MSG"));
		}
		dataObj.put("PO_CURSOR", list);
		log.info("===PAY_PC_GET_APP_AMT======"+dataObj);
		return dataObj;
	}
	//暫時功能,將移除.....
	public Map<String, Object> payPcGetAppAmt(String domain, String contractNo, 
			String locationId, String paymentItem, Date reqBeginDate, Date reqEndDate,
			Date appDate, String apYYYYMM, String appType, String paymentPeriod) throws NomsException{
		HashMap<String, Object> dataObj = new HashMap<String,Object>();
		dataObj.put("PI_TIMESTAMP", null);
		dataObj.put("PI_DOMAIN", domain);
		dataObj.put("PI_CONTRACT_NO", contractNo);			
		dataObj.put("PI_LOCATION_ID", locationId);
		dataObj.put("PI_PAYMENT_ITEM", paymentItem);
		dataObj.put("PI_REQUEST_DATES", reqBeginDate);
		dataObj.put("PI_REQUEST_DATEE", reqEndDate);
		dataObj.put("PI_APP_DATE", appDate);
		dataObj.put("PI_AP_YYYYMM", apYYYYMM);
		dataObj.put("PI_APP_TYPE", appType);
		dataObj.put("PI_PAYMENT_PERIOD", paymentPeriod);
		List<Map<String, Object>> list = payPublicUtilDao.payPcGetAppAmt(dataObj);
		if (!dataObj.get("PO_ERR_CDE").equals("00")) {
			log.error("租約異動 Call PAY_PC_GET_APP_AMT Error：ERR_CDE="
					+ dataObj.get("PO_ERR_CDE") + ", PO_ERR_MSG="
					+ dataObj.get("PO_ERR_MSG"));
			throw new NomsException("99", "租約異動 執行錯誤：" + dataObj.get("PO_ERR_MSG"));
		}
		dataObj.put("PO_CURSOR", list);
		return dataObj;
	}
	/**
	 * 租約異動  Call PAY_PC_PROC_APP_AMT
	 * 
	 * @param key 暫存資料KEY,以此作為第二次執行的標準
	 * @param procType 請款模組產單號:前置碼
	 * @param processType 處理類別     
	 * @param paymentPeriod 付款週期    
	 * @param apYYYYMM 請款年月 YYYYMM
	 * @param appUser 申請人員代碼
	 * @param appDate 執行請款日期   
	 * */
	public Map<String, Object> payPcProcAppAmt(String key, String procType, 
			String processType, String paymentPeriod,
			String apYYYYMM, String appUser, Date appDate) throws NomsException{
		HashMap<String, Object> dataObj = new HashMap<String,Object>();
		dataObj.put("PI_TIME_STAMP", key);
		dataObj.put("PI_PROC_TYPE", procType);
		dataObj.put("PI_PROCESS_TYPE", processType);
		dataObj.put("PI_PAYMENT_PERIOD", paymentPeriod);
		dataObj.put("PI_AP_YYYYMM", apYYYYMM);		
		dataObj.put("PI_APP_USER", appUser);	
		dataObj.put("PI_APP_DATE", appDate);
		payPublicUtilDao.payPcProcAppAmt(dataObj);
		if (!dataObj.get("PO_ERR_CDE").equals("00")) {
			log.error("租約異動 Call PAY_PC_PROC_APP_AMT Error：ERR_CDE="
					+ dataObj.get("PO_ERR_CDE") + ", PO_ERR_MSG="
					+ dataObj.get("PO_ERR_MSG"));
			throw new NomsException("99", "租約異動 執行錯誤：" + dataObj.get("PO_ERR_MSG"));
		}
		return dataObj;
	}
}
