package com.foya.noms.dao.pay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.ProcedureNomsMapper;
import com.foya.dao.mybatis.mapper.TbAuthPersonnelMapper;
import com.foya.dao.mybatis.mapper.TbLsLocPaymentMapper;
import com.foya.dao.mybatis.mapper.TbPayCashRequirementMapper;
import com.foya.dao.mybatis.mapper.TbPayContractInfoMapper;
import com.foya.dao.mybatis.mapper.TbPayElectricityMapper;
import com.foya.dao.mybatis.mapper.TbPayImpFileMapper;
import com.foya.dao.mybatis.mapper.TbPayLocationInfoMapper;
import com.foya.dao.mybatis.mapper.TbPayLookupCodeMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentRequestDtlMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentRequestMapper;
import com.foya.dao.mybatis.mapper.TbPayVoucherCreditMapper;
import com.foya.dao.mybatis.mapper.UTbLsLocationMapper;
import com.foya.dao.mybatis.mapper.UTbPayCashRequirementMapper;
import com.foya.dao.mybatis.mapper.UTbPayContractInfoMapper;
import com.foya.dao.mybatis.mapper.UTbPayElectricityMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentRequestMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentRequestVoucherMapper;
import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbLsLocPayment;
import com.foya.dao.mybatis.model.TbLsLocPaymentExample;
import com.foya.dao.mybatis.model.TbLsLocation;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayCashRequirementExample;
import com.foya.dao.mybatis.model.TbPayContractInfo;
import com.foya.dao.mybatis.model.TbPayContractInfoExample;
import com.foya.dao.mybatis.model.TbPayElectricity;
import com.foya.dao.mybatis.model.TbPayElectricityExample;
import com.foya.dao.mybatis.model.TbPayImpFile;
import com.foya.dao.mybatis.model.TbPayImpFileExample;
import com.foya.dao.mybatis.model.TbPayLocationInfo;
import com.foya.dao.mybatis.model.TbPayLocationInfoExample;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbPayLookupCodeExample;
import com.foya.dao.mybatis.model.TbPayPayment;
import com.foya.dao.mybatis.model.TbPayPaymentExample;
import com.foya.dao.mybatis.model.TbPayPaymentRequest;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtl;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtlExample;
import com.foya.dao.mybatis.model.TbPayPaymentRequestExample;
import com.foya.dao.mybatis.model.TbPayVoucherCredit;
import com.foya.dao.mybatis.model.TbPayVoucherCreditExample;
import com.foya.dao.mybatis.model.UTbPayPaymentRequestVoucherExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.pay.TbLsLocElecDTO;
import com.foya.noms.dto.pay.TbLsSiteDTO;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;
import com.foya.noms.dto.pay.TbPayElectricityDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestVoucherDTO;
import com.foya.noms.util.DateUtils;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

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
 * <td>2016/02/01</td>
 * <td>新建檔案</td>
 * <td>Smile</td>
 * </tr>
 * </table>
 * 
 * @author Smile
 * 
 */
@Repository
public class Pay003Dao extends BaseDao {
	
	@Autowired
	private TbPayElectricityMapper tbPayElectricityMapper;
	
	@Autowired
	private TbPayImpFileMapper tbPayImpFileMapper;
	
	@Autowired
	private TbPayLookupCodeMapper tbPayLookupCodeMapper;
	
	@Autowired
	private TbAuthPersonnelMapper tbAuthPersonnelMapper;
	
	@Autowired
	private ProcedureNomsMapper procedureNomsMapper;
	
	@Autowired
	private TbPayVoucherCreditMapper tbPayVoucherCreditMapper;
	
	@Autowired
	private TbPayPaymentRequestDtlMapper tbPayPaymentRequestDtlMapper;
	
	@Autowired
	private TbPayContractInfoMapper tbPayContractInfoMapper;
	
	@Autowired
	private TbPayLocationInfoMapper tbPayLocationInfoMapper;
	
	@Autowired
	private TbPayPaymentMapper tbPayPaymentMapper;
	
	@Autowired
	private TbPayCashRequirementMapper tbPayCashRequirementMapper;
	
	@Autowired
	private TbPayPaymentRequestMapper tbPayPaymentRequestMapper;
	
	@Autowired
	private UTbPayPaymentRequestVoucherMapper uTbPayPaymentRequestVoucherMapper;
	
	@Autowired
	private UTbPayCashRequirementMapper uTbPayCashRequirementMapper;
	
	@Autowired
	private UTbPayPaymentRequestMapper uTbPayPaymentRequestMapper;
	
	@Autowired
	private UTbPayElectricityMapper uTbPayElectricityMapper;
	
	@Autowired
	private UTbPayContractInfoMapper uTbPayContractInfoMapper;
	
	@Autowired
	private UTbLsLocationMapper uTbLsLocationMapper;
	
	@Autowired
	private TbLsLocPaymentMapper tbLsLocPaymentMapper;

	public List<TbPayCashRequirementDTO> searchForPay003(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("CASH_REQ_NO");// 設定排序欄位
		List<TbPayCashRequirementDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayCashRequirementMapper.searchForPay003",dataObj,  pageBounds);
		PageList<TbPayCashRequirementDTO> pageList = (PageList<TbPayCashRequirementDTO>)list;
		return pageList;
	}
	
	public List<TbPayPaymentRequestDTO> searchForPay003DtlMaster(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("CASH_REQ_NO");// 設定排序欄位
		List<TbPayPaymentRequestDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayPaymentRequestMapper.searchForPay003DtlMaster",dataObj,  pageBounds);
		PageList<TbPayPaymentRequestDTO> pageList = (PageList<TbPayPaymentRequestDTO>)list;
		return pageList;
	}
	
	public List<TbPayElectricityDTO> searchForPay003DtlTable2(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds();// 設定排序欄位
		List<TbPayElectricityDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayElectricityMapper.selectDtlForPAY003Table2",dataObj,  pageBounds);
		PageList<TbPayElectricityDTO> pageList = (PageList<TbPayElectricityDTO>)list;
		return pageList;
	}
	
	public List<TbPayElectricityDTO> selectDtl4PAY003ATb2(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds();// 設定排序欄位
		List<TbPayElectricityDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayElectricityMapper.selectDtl4PAY003ATb2",dataObj,  pageBounds);
		PageList<TbPayElectricityDTO> pageList = (PageList<TbPayElectricityDTO>)list;
		return pageList;
	}
	
	public List<TbLsLocElecDTO> selectDtl4PAY003ATb2loc(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds();// 設定排序欄位
		List<TbLsLocElecDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbLsLocElecMapper.selectDtl4PAY003ATb2",dataObj,  pageBounds);
		PageList<TbLsLocElecDTO> pageList = (PageList<TbLsLocElecDTO>)list;
		return pageList;
	}
	
	public List<TbLsSiteDTO> selectDtlForPAY003Table3(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("location_id");// 設定排序欄位
		List<TbLsSiteDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayPaymentRequestDtlMapper.selectDtlForPAY003Table3",dataObj,  pageBounds);
		PageList<TbLsSiteDTO> pageList = (PageList<TbLsSiteDTO>)list;
		return pageList;
	}
	
	public List<TbPayPaymentRequestVoucherDTO> selectPay003EditVoucher(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("ap_no");// 設定排序欄位
		List<TbPayPaymentRequestVoucherDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayPaymentRequestVoucherMapper.selectPay003EditVoucher",dataObj,  pageBounds);
		PageList<TbPayPaymentRequestVoucherDTO> pageList = (PageList<TbPayPaymentRequestVoucherDTO>)list;
		return pageList;
	}
	
	public List<TbPayPaymentRequestDTO> selectCountPay003(String cashReqNo) {
		HashMap<String, Object> dataObj = new HashMap<String, Object>();
		dataObj.put("cashReqNo", cashReqNo);
		return uTbPayPaymentRequestMapper.selectTbPayPaymentRequest(dataObj);
	}
	
	/**
	 * 自動產生單號 For 租金類別(ELP)
	 * @param map
	 */
	public void payPcGetSeqnoByMap(Map<String, Object> map) {
		procedureNomsMapper.PAY_PC_GET_SEQNO(map);
	}
	
	/**
	 * 請款 - 新增請款單
	 * 
	 * @param 請款單 資料
	 * 
	 */		
	public void insertPayCashRequirement(TbPayCashRequirementDTO record){
		tbPayCashRequirementMapper.insertSelective(record);
	}
	/**
	 * 請款 - 新增請款資料
	 * 
	 * @param 請款  資料
	 * 
	 * @return 請款編號ID
	 */		
	public void insertPayPaymentRequest(TbPayPaymentRequest record){		
		uTbPayPaymentRequestMapper.insertSelective(record);
	}
	/**
	 * 請款 - 更新 TB_PAY_CONTRACT_INFO
	 * 
	 * @param nextYearMonth 下次請款年月
	 * @param domain 條件-維運區(代碼)
	 * @param contractNo 條件-租約編號
	 * 
	 */		
	public void updatePayContractInfo(String nextYearMonth, String domain, String contractNo){
		TbPayContractInfo record = new TbPayContractInfo();
		record.setNEXT_YEAR_MONTH(nextYearMonth);
		TbPayContractInfoExample example = new TbPayContractInfoExample();
		example.createCriteria().andDOMAINEqualTo(domain)
		.andCONTRACT_NOEqualTo(contractNo);
		tbPayContractInfoMapper.updateByExampleSelective(record, example);
	}
	/**
	 * 取得憑證資料 by page
	 * @param example
	 * @return
	 */
	public List<TbPayPaymentRequestVoucherDTO> selectPayPaymentRequestVoucherByExamplePage(UTbPayPaymentRequestVoucherExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbPayPaymentRequestVoucherDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbPayPaymentRequestVoucherMapper.selectByExample",
						example, pageBounds);

		PageList<TbPayPaymentRequestVoucherDTO> pageList = (PageList<TbPayPaymentRequestVoucherDTO>) list;
		return pageList;
	}
	/**
	 * 取得驗收清單資料
	 * @param example
	 * @return
	 */
	public List<TbPayPaymentRequestVoucherDTO> selectPayPaymentRequestVoucherByExample(UTbPayPaymentRequestVoucherExample example) {
		return uTbPayPaymentRequestVoucherMapper.selectByExample(example);
	}
	
	/**
	 * 請款 - 新增請款明細資料
	 * 
	 * @param 請款明細  資料
	 * 
	 */		
	public void insertPayElectricity(TbPayElectricityDTO record){
		tbPayElectricityMapper.insertSelective(record);
	}
	/**
	 * 請款 - 更新 TB_PAY_ELECTRICITY
	 * 
	 * @param  
	 * 
	 */		
	public void updatePayElectricity(Long seq_nbr,Long payment_req_no,Short payment_req_item_no,String md_user,Date md_time){
		TbPayElectricity record = new TbPayElectricity();
		record.setPAYMENT_REQ_NO(payment_req_no);
		record.setPAYMENT_REQ_ITEM_NO(payment_req_item_no);
		record.setMD_USER(md_user);
		record.setMD_TIME(md_time);
		TbPayElectricityExample example = new TbPayElectricityExample();
		example.createCriteria().andSEQ_NBREqualTo(seq_nbr);
		tbPayElectricityMapper.updateByExampleSelective(record, example);
	}
	/**
	 * 請款 - 更新 TB_PAY_IMP_FILE
	 * 
	 * @param  
	 * 
	 */		
	public void updatePayImpFile(Long imp_file_seq_nbr,String cash_req_no,String md_user,Date md_time){
		TbPayImpFile record = new TbPayImpFile();
		record.setCASH_REQ_NO(cash_req_no);
		record.setMD_USER(md_user);
		record.setMD_TIME(md_time);
		TbPayImpFileExample example = new TbPayImpFileExample();
		example.createCriteria().andSEQ_NBREqualTo(imp_file_seq_nbr);
		tbPayImpFileMapper.updateByExampleSelective(record, example);
	}
	/**
	 * 請款 - 新增請款明細資料
	 * 
	 * @param 請款明細  資料
	 * 
	 */		
	public void insertPayPaymentRequestDtl(TbPayPaymentRequestDtl record){
		tbPayPaymentRequestDtlMapper.insertSelective(record);
	}
	/**
	 * 請款 - 更新 TB_PAY_LOCATION_INFO
	 * 
	 * @param prePrBeginDate 前次請款起始日期
	 * @param prePrEndDate 前次請款截止日期
	 * @param cashReqNo 租約編號
	 * @param domain 條件-維運區(代碼)
	 * @param contractNo 條件-租約編號
	 * @param locationId 條件-站點代碼
	 * 
	 */		
	public void updatePayLocationInfo(Date prePrBeginDate, Date prePrEndDate, String cashReqNo,
			String domain, String contractNo, String locationId){
		TbPayLocationInfo record = new TbPayLocationInfo();
		record.setPRE_PR_BEGIN_DATE(prePrBeginDate);
		record.setPRE_PR_END_DATE(prePrEndDate);
		record.setCASH_REQ_NO(cashReqNo);
		TbPayLocationInfoExample example = new TbPayLocationInfoExample();
		example.createCriteria().andDOMAINEqualTo(domain)
		.andCONTRACT_NOEqualTo(contractNo)
		.andLOCATION_IDEqualTo(locationId);
		tbPayLocationInfoMapper.updateByExampleSelective(record, example);
	}
	/**
	 * 請款 - 新增 付款資料
	 * 
	 * @param 付款資料
	 * 
	 */		
	public void insertPayPayment(TbPayPayment record){
		tbPayPaymentMapper.insertSelective(record);
	}	
	
	/**
	 * 新增- 查詢  請款清單資料-Detail Page DetailTable2 - PartI 取得站點資料 
	 * 
	 * @param lsNo 合約編號
	 * 
	 * @return List<TbLsLocation>
	 */		
	public List<TbLsLocation> getDistinctLocationId(String lsNo){
		List<TbLsLocation> list = uTbLsLocationMapper.select4Pay001Detail2(lsNo);
		return list;
	}
	
	/**
	 * 新增- 查詢  請款清單資料-Detail Page DetailTable3 -租金  
	 * 
	 * @param lsNo 租約編號
	 * @param locationId 站點編號
	 * @param recipientId 付款對象証號
	 * @param lessorId 出租人証號 
	 * @param payItem 處理類別
	 * 
	 * @return 租金pay_amt
	 */		
	public BigDecimal getPayAmt4Detail3(String lsNo, String locationId, String recipientId, String lessorId, String payItem){
		Date today = DateUtils.todayStart();
		TbLsLocPaymentExample example = new TbLsLocPaymentExample();
		example.createCriteria().andLS_NOEqualTo(lsNo)
		.andLOCATION_IDEqualTo(locationId)
		.andRECIPIENT_IDEqualTo(recipientId)
		.andPAYMENT_ITEMEqualTo(payItem)
		.andEFF_DATELessThanOrEqualTo(today)
		.andEND_DATEGreaterThan(today);
		List<TbLsLocPayment> list = tbLsLocPaymentMapper.selectByExample(example);
		if(list.size()>0){
			return list.get(0).getPAY_AMT();
		}
		return BigDecimal.ZERO;
	}
	
	/**
	 * 送審資料
	 * @param record
	 * @param example
	 * @return
	 */
	public int updateByPAY003(String cashReqNo,String appUser) {
		return uTbPayCashRequirementMapper.updateByPAY003(cashReqNo, appUser);
	}
	
	/**
	 * 新增憑證資料
	 * @param record
	 */
	public int insertPayPaymentRequestVoucherSelective(TbPayPaymentRequestVoucherDTO record) {
		return uTbPayPaymentRequestVoucherMapper.insertSelective(record);
	}

	/**
	 * 修改憑證資料
	 * @param record
	 * @param example
	 * @return
	 */
	public int updatePayPaymentRequestVoucherByExampleSelective(TbPayPaymentRequestVoucherDTO record,
			UTbPayPaymentRequestVoucherExample example) {
		return uTbPayPaymentRequestVoucherMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 刪除憑證資料
	 * @param example
	 * @return
	 */
	public int deletePayPaymentRequestVoucherByExample(UTbPayPaymentRequestVoucherExample example) {
		return uTbPayPaymentRequestVoucherMapper.deleteByExample(example);
	}
	
	/**
	 * 取得用電度數、用電天數、上期度數 
	 * @param map
	 */
	public void callPayUsedDegreeMDayMap(Map<String, Object> map) {
		uTbPayElectricityMapper.PAY_USED_DEGREE_M_DAY(map);
	}
	
	/**
	 * 自動產生單號
	 * @param map
	 */
	public void callPayPcGetSeqnoByMap(Map<String, Object> map) {
		procedureNomsMapper.PAY_PC_GET_SEQNO(map);
	}
	
	/**
	 * 取得所得稅/健保費/營業稅
	 * @param map
	 */
	public void callPayFnGetTax(Map<String, Object> map) {
		procedureNomsMapper.PAY_FN_GET_TAX(map);
	}
	
	/**
	 * 新增憑證明細資料
	 * @param record
	 * @return
	 */
	public int insertPayVoucherCreditSelective(TbPayVoucherCredit record) {
		return tbPayVoucherCreditMapper.insertSelective(record);
	}
	
	/**
	 * 修改憑證明細資料
	 * @param record
	 * @param example
	 * @return
	 */
	public int updatePayVoucherCreditByExampleSelective(TbPayVoucherCredit record, TbPayVoucherCreditExample example) {
		return tbPayVoucherCreditMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 刪除憑證明細資料
	 * @param example
	 * @return
	 */
	public int deletePayVoucherCreditByExample(TbPayVoucherCreditExample example) {
		return tbPayVoucherCreditMapper.deleteByExample(example);
	}
	
	
	/**
	 * 取得參數設定資料
	 * @param example
	 * @return
	 */
	public List<TbPayLookupCode> selectPayLookupCodeByExample(TbPayLookupCodeExample example) {
		return tbPayLookupCodeMapper.selectByExample(example);
	}
	
	/**
	 * 取得參數設定資料
	 * @param example
	 * @return
	 */
	public List<TbPayElectricity> selectPayElectricityExample(TbPayElectricityExample example) {
		return tbPayElectricityMapper.selectByExample(example);
	}
	
	/**
	 * 取得人員清單
	 * @param example
	 * @return
	 */
	public List<TbAuthPersonnel> selectAuthPersonnelByExample(TbAuthPersonnelExample example) {
		return tbAuthPersonnelMapper.selectByExample(example);
	}
	
	/**刪除明細資料 PAYMENT_REQUEST_DTL 
	 * @param example
	 * @return 
	 */
	public int deletePayPaymentRequestDtlByExample(TbPayPaymentRequestDtlExample example){
		return tbPayPaymentRequestDtlMapper.deleteByExample(example);
	}
	
	/**刪除明細資料 PAYMENT_REQUEST  
	 * @param example
	 * @return 
	 */
	public int deletePayPaymentRequestByExample(TbPayPaymentRequestExample example){
		return tbPayPaymentRequestMapper.deleteByExample(example);
	}

	/**刪除明細資料 PAYMENT_REQUEST_VOUCHER  
	 * @param example
	 * @return 
	 */
	public int deletePaymentRequestVoucherPay003(Long paymentReqNo){
		return uTbPayPaymentRequestVoucherMapper.deletePaymentRequestVoucherPay003(paymentReqNo);
	}
	
	/**刪除明細資料 PAYMENT
	 * @param example
	 * @return 
	 */
	public int deletePayPaymentByExample(TbPayPaymentExample example){
		return tbPayPaymentMapper.deleteByExample(example);
	}
	
	/**刪除明細資料 PAYMENT
	 * @param example
	 * @return 
	 */
	public int deletePayCashRequirementExample(TbPayCashRequirementExample example){
		return tbPayCashRequirementMapper.deleteByExample(example);
	}
	
	public List<TbLsLocPayment>  selectLsLocPayByExample(String lsNo, String locationId, String payItem){
		Date today = DateUtils.todayStart();
		TbLsLocPaymentExample example = new TbLsLocPaymentExample();
		example.createCriteria().andLS_NOEqualTo(lsNo)
		.andLOCATION_IDEqualTo(locationId)
		.andPAYMENT_ITEMEqualTo(payItem)
		.andEFF_DATELessThanOrEqualTo(today)
		.andEND_DATEGreaterThan(today);
		return tbLsLocPaymentMapper.selectByExample(example);
	}
	/**
	 * 新增- 查詢  請款清單資料-Detail Page DetailTable1 For [借電_固定金額]
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return List<TbPayPaymentRequestDTO>
	 */	
	public List<TbPayPaymentRequestDTO> selectPayContractInfo4ELE01(HashMap<String,Object> dataObj){
		List<TbPayPaymentRequestDTO> list = uTbPayContractInfoMapper.selectPayContractInfo4ELE01(dataObj);
		return list;
	}
	
	/**
	 * 新增- 查詢  請款清單資料-Detail Page DetailTable1 For [押金]
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return List<TbPayPaymentRequestDTO>
	 */	
	public List<TbPayPaymentRequestDTO> selectPayContractInfo4ELE05(HashMap<String,Object> dataObj){
		List<TbPayPaymentRequestDTO> list = uTbPayContractInfoMapper.selectPayContractInfo4ELE05(dataObj);
		return list;
	}
	
	
	/**
	 * 新增- 查詢  請款清單資料-Detail Page DetailTable1 For [請電] [借電_抄表] [借電_其他] [預付]
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return List<TbPayPaymentRequestDTO>
	 */	
	public List<TbPayPaymentRequestDTO> selectPayContractInfo4ELEOther(HashMap<String,Object> dataObj){
		List<TbPayPaymentRequestDTO> list = uTbPayContractInfoMapper.selectPayContractInfo4ELEOther(dataObj);
		return list;
	}
	/**
	 * 更新 PayCashRequirement 資料 - 資料送審
	 * 
	 * @param cashReqNo
	 *           請款單號 - 更新條件
	 * @param record
	 *           請款單 更新資料
	 * 
	 * @return int 刪除筆數
	 */		
	public int approved(String cashReqNo, TbPayCashRequirement record){
		TbPayCashRequirementExample example = new TbPayCashRequirementExample();
		example.createCriteria().andCASH_REQ_NOEqualTo(cashReqNo);
		return tbPayCashRequirementMapper.updateByExampleSelective(record, example);
	}
	
	/**
	 * @param example
	 * @return
	 */
	public List<TbPayCashRequirement> checkDataCount(TbPayCashRequirementExample example) {
		return tbPayCashRequirementMapper.selectByExample(example);
	}
	/**
	 * @param example
	 * @return
	 */
	public List<TbAuthPersonnel> selectTbAuthPersonnel(TbAuthPersonnelExample example) {
		return tbAuthPersonnelMapper.selectByExample(example);
	}
}
