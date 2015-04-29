package com.foya.noms.dao.pay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.ProcedureNomsMapper;
import com.foya.dao.mybatis.mapper.TbLsLocPaymentMapper;
import com.foya.dao.mybatis.mapper.TbPayCashRequirementMapper;
import com.foya.dao.mybatis.mapper.TbPayContractInfoMapper;
import com.foya.dao.mybatis.mapper.TbPayLocationInfoMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentRequestDtlMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentRequestMapper;
import com.foya.dao.mybatis.mapper.UTbLsLocationMapper;
import com.foya.dao.mybatis.mapper.UTbPayContractInfoMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentRequestDtlMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentRequestMapper;
import com.foya.dao.mybatis.model.TbLsLocPayment;
import com.foya.dao.mybatis.model.TbLsLocPaymentExample;
import com.foya.dao.mybatis.model.TbLsLocation;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayCashRequirementExample;
import com.foya.dao.mybatis.model.TbPayContractInfo;
import com.foya.dao.mybatis.model.TbPayContractInfoExample;
import com.foya.dao.mybatis.model.TbPayLocationInfo;
import com.foya.dao.mybatis.model.TbPayLocationInfoExample;
import com.foya.dao.mybatis.model.TbPayPayment;
import com.foya.dao.mybatis.model.TbPayPaymentExample;
import com.foya.dao.mybatis.model.TbPayPaymentRequest;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtl;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtlExample;
import com.foya.dao.mybatis.model.TbPayPaymentRequestExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;
import com.foya.noms.dto.pay.TbPayPaymentDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlDTO;
import com.foya.noms.util.DateUtils;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
@Repository
public class PAY001Dao extends BaseDao {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TbPayCashRequirementMapper tbPayCashRequirementMapper;
	@Autowired
	private UTbPayPaymentMapper uTbPayPaymentMapper;
	@Autowired
	private UTbLsLocationMapper uTbLsLocationMapper;
	@Autowired
	private TbPayPaymentRequestDtlMapper tbPayPaymentRequestDtlMapper;
	@Autowired
	private TbPayPaymentRequestMapper tbPayPaymentRequestMapper;
	@Autowired
	private UTbPayPaymentRequestMapper uTbPayPaymentRequestMapper;
	@Autowired
	private TbPayPaymentMapper tbPayPaymentMapper;
	@Autowired
	private UTbPayContractInfoMapper uTbPayContractInfoMapper;
	@Autowired
	private TbLsLocPaymentMapper tbLsLocPaymentMapper;
	@Autowired
	private UTbPayPaymentRequestDtlMapper uTbPayPaymentRequestDtlMapper;
	@Autowired
	private ProcedureNomsMapper procedureNomsMapper;
	@Autowired
	private TbPayContractInfoMapper tbPayContractInfoMapper;
	@Autowired
	private TbPayLocationInfoMapper tbPayLocationInfoMapper;
	/**
	 * 查詢請款資料 
	 * 
	 * @param dataObj	查詢條件
	 * 
	 * @return List<TbPayCashRequirementDTO>
	 */
	public List<TbPayCashRequirementDTO> selectByExample(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("CASH_REQ_NO");
		List<TbPayCashRequirementDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayCashRequirementMapper.selectTbPayCashRequirementPay001",dataObj,  pageBounds);
		PageList<TbPayCashRequirementDTO> pageList = (PageList<TbPayCashRequirementDTO>)list;
		return pageList;
	}
	/**
	 * 查詢請款資料-Detail Page Master資料
	 * 
	 * @param cashReqNo
	 *            請款單號
	 * 
	 * @return TbPayCashRequirement
	 */
	public TbPayCashRequirement selectByPrimaryKey(String cashReqNo){
		return tbPayCashRequirementMapper.selectByPrimaryKey(cashReqNo);
	}
	/**
	 * 查詢請款清單資料-Detail Page DetailTable1  
	 * 
	 * @param dataObj
	 *            查詢條件          
	 * 
	 * @return List<TbPayPaymentRequestDTO>
	 */	
	public List<TbPayPaymentRequestDTO> selectDetail1(HashMap<String,Object> dataObj){
		List<TbPayPaymentRequestDTO> list = uTbPayPaymentRequestMapper.selectTbPayPaymentRequest(dataObj);
		return list;
	}
	/**
	 * 查詢租約基站清單資料-Detail Page DetailTable2
	 * 
	 * @param dataObj
	 *            查詢條件          
	 * 
	 * @return List<TbPayPaymentRequestDtlDTO>
	 */		
	public List<TbPayPaymentRequestDtlDTO> selectDetail2(HashMap<String,Object> dataObj){
		List<TbPayPaymentRequestDtlDTO> list = uTbPayPaymentRequestDtlMapper.select4Pay001Detail2(dataObj);
		return list;
	}
	/**
	 * 查詢 基站請款紀錄 資料-Detail Page DetailTable3
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return List<TbPayPaymentDTO>
	 */		
	public List<TbPayPaymentDTO> selectDetail3(HashMap<String,Object> dataObj){
		List<TbPayPaymentDTO> list = uTbPayPaymentMapper.selectTbPayPaymentByLocationId(dataObj);
		return list;
	}
	/**
	 * 刪除 TbPayPaymentRequestDtl 資料
	 * 
	 * @param paymentReqNo
	 *            多筆請款編號(ID)
	 * 
	 * @return int 刪除筆數
	 */		
	public int deletePayPaymentRequestDtl(List<Long> paymentReqNo){
		TbPayPaymentRequestDtlExample example = new TbPayPaymentRequestDtlExample();
		example.createCriteria().andPAYMENT_REQ_NOIn(paymentReqNo);
		return tbPayPaymentRequestDtlMapper.deleteByExample(example);
	}
	/**
	 * 刪除 TbPayPaymentRequest 資料
	 * 
	 * @param paymentReqNo
	 *            多筆請款編號(ID)
	 * 
	 * @return int 刪除筆數
	 */			
	public int deletePayPaymentRequest(List<Long> paymentReqNo){
		TbPayPaymentRequestExample example = new TbPayPaymentRequestExample();
		example.createCriteria().andPAYMENT_REQ_NOIn(paymentReqNo);
		return tbPayPaymentRequestMapper.deleteByExample(example);
	}
	/**
	 * 刪除 TbPayPayment 資料
	 * 
	 * @param paymentReqNo
	 *            多筆請款編號(ID)
	 * 
	 * @return int 刪除筆數
	 */			
	public int deletePayPayment(List<Long> paymentReqNo){
		TbPayPaymentExample example = new TbPayPaymentExample();
		example.createCriteria().andPAYMENT_REQ_NOIn(paymentReqNo);
		return tbPayPaymentMapper.deleteByExample(example);
	}
	/**
	 * 取得 TbPayPaymentRequest 筆數
	 * 
	  * @param cashReqNo
	 *           請款單號
	 * 
	 * @return int 資料筆數
	 */			
	public int getPayPaymentRequestCnt(String cashReqNo){
		TbPayPaymentRequestExample example = new TbPayPaymentRequestExample();
		example.createCriteria().andCASH_REQ_NOEqualTo(cashReqNo);
		return tbPayPaymentRequestMapper.countByExample(example);
	}
	/**
	 * 刪除 PayCashRequirement 資料
	 * 
	 * @param cashReqNo
	 *           請款單號
	 * 
	 * @return int 刪除筆數
	 */				
	public int deletePayCashRequirement(String cashReqNo){
		TbPayCashRequirementExample example = new TbPayCashRequirementExample();
		example.createCriteria().andCASH_REQ_NOEqualTo(cashReqNo);
		return tbPayCashRequirementMapper.deleteByExample(example);
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
		example.createCriteria().andCASH_REQ_NOEqualTo(cashReqNo);		;
		return tbPayCashRequirementMapper.updateByExampleSelective(record, example);
	}
	/**
	 * 新增- 檢查是否有重覆請款清單資料
	 * 
	 * @param example of TbPayCashRequirementExample
	 *            查詢條件
	 * 
	 * @return 筆數
	 */	
	public int countPayCashRequirement(TbPayCashRequirementExample example){
		return tbPayCashRequirementMapper.countByExample(example);
	}	
	/**
	 * 新增- 查詢  請款清單資料-Detail Page DetailTable1 For [押金] 
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return List<TbPayPaymentRequestDTO>
	 */	
	public List<TbPayPaymentRequestDTO> selectDetail1ByREN01(HashMap<String,Object> dataObj){
		List<TbPayPaymentRequestDTO> list = uTbPayContractInfoMapper.selectPayContractInfo4REN01(dataObj);
		return list;
	}
	/**
	 * 新增- 查詢  請款清單資料-Detail Page DetailTable1 For [首期]
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return List<TbPayPaymentRequestDTO>
	 */	
	public List<TbPayPaymentRequestDTO> selectDetail1ByREN02(HashMap<String,Object> dataObj){
		List<TbPayPaymentRequestDTO> list = uTbPayContractInfoMapper.selectPayContractInfo4REN02(dataObj);
		return list;
	}	
	/**
	 * 新增- 查詢  請款清單資料-Detail Page DetailTable1 For [例行]   
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return List<TbPayPaymentRequestDTO>
	 */	
	public List<TbPayPaymentRequestDTO> selectDetail1ByREN03(HashMap<String,Object> dataObj){
		List<TbPayPaymentRequestDTO> list = uTbPayContractInfoMapper.selectPayContractInfo4REN03(dataObj);
		return list;
	}	
	/**
	 * 新增- 查詢  請款清單資料-Detail Page DetailTable1 For [補請]   
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return List<TbPayPaymentRequestDTO>
	 */	
	public List<TbPayPaymentRequestDTO> selectDetail1ByREN04(HashMap<String,Object> dataObj){
		List<TbPayPaymentRequestDTO> list = uTbPayContractInfoMapper.selectPayContractInfo4REN04(dataObj);
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
	 * 自動產生單號 For 租金類別(REP)
	 * @param map
	 */
	public void payPcGetSeqnoByMap(Map<String, Object> map) {
		procedureNomsMapper.PAY_PC_GET_SEQNO(map);
	}
	/**
	 * 請款 - 新增請款單
	 * 
	 * @param 請款單 資料
	 * @return 異動筆數
	 * 
	 */		
	public int insertPayCashRequirement(TbPayCashRequirementDTO record){
		return tbPayCashRequirementMapper.insertSelective(record);
	}
	/**
	 * 請款 - 新增請款資料
	 * 
	 * @param 請款  資料
	 * 
	 * @return 異動筆數
	 */		
	public int insertPayPaymentRequest(TbPayPaymentRequest record){		
		return uTbPayPaymentRequestMapper.insertSelective(record);
	}
	/**
	 * 請款 - 更新 TB_PAY_CONTRACT_INFO
	 * 
	 * @param nextYearMonth 下次請款年月
	 * @param domain 條件-維運區(代碼)
	 * @param contractNo 條件-租約編號
	 * @return 異動筆數
	 * 
	 */		
	public int updatePayContractInfo(String nextYearMonth, String domain, String contractNo){
		TbPayContractInfo record = new TbPayContractInfo();
		record.setNEXT_YEAR_MONTH(nextYearMonth);
		TbPayContractInfoExample example = new TbPayContractInfoExample();
		example.createCriteria().andDOMAINEqualTo(domain)
		.andCONTRACT_NOEqualTo(contractNo);
		return tbPayContractInfoMapper.updateByExampleSelective(record, example);
	}
	/**
	 * 請款 - 新增請款明細資料
	 * 
	 * @param 請款明細  資料
	 * @return 異動筆數
	 * 
	 */		
	public int insertPayPaymentRequestDtl(TbPayPaymentRequestDtl record){
		return tbPayPaymentRequestDtlMapper.insertSelective(record);
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
	 * @return 異動筆數
	 * 
	 */		
	public int updatePayLocationInfo(Date prePrBeginDate, Date prePrEndDate, String cashReqNo,
			String domain, String contractNo, String locationId){
		TbPayLocationInfo record = new TbPayLocationInfo();
		record.setPRE_PR_BEGIN_DATE(prePrBeginDate);
		record.setPRE_PR_END_DATE(prePrEndDate);
		record.setCASH_REQ_NO(cashReqNo);
		TbPayLocationInfoExample example = new TbPayLocationInfoExample();
		example.createCriteria().andDOMAINEqualTo(domain)
		.andCONTRACT_NOEqualTo(contractNo)
		.andLOCATION_IDEqualTo(locationId);
		return tbPayLocationInfoMapper.updateByExampleSelective(record, example);
	}
	/**
	 * 請款 - 新增 付款資料
	 * 
	 * @param 付款資料
	 * @return 異動筆數
	 * 
	 */		
	public int insertPayPayment(TbPayPayment record){
		return tbPayPaymentMapper.insertSelective(record);
	}	
}
