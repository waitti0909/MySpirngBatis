package com.foya.noms.dao.pay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.ProcedureNomsMapper;
import com.foya.dao.mybatis.mapper.TbLsCollectUnitMapper;
import com.foya.dao.mybatis.mapper.TbLsCollectUnitSubMapper;
import com.foya.dao.mybatis.mapper.TbLsLessorMapper;
import com.foya.dao.mybatis.mapper.TbOrgDeptMapper;
import com.foya.dao.mybatis.mapper.TbOrgDomainMapper;
import com.foya.dao.mybatis.mapper.TbPayLookupCodeMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentItemMapper;
import com.foya.dao.mybatis.mapper.TbPayProvisionalAttachmentMapper;
import com.foya.dao.mybatis.mapper.TbSiteMainMapper;
import com.foya.dao.mybatis.mapper.TbSysLookupMapper;
import com.foya.dao.mybatis.mapper.UTbAuthPersonnelMapper;
import com.foya.dao.mybatis.mapper.UTbOrgDeptMapper;
import com.foya.dao.mybatis.mapper.UTbOrgDomainMapper;
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
import com.foya.dao.mybatis.model.TbPayLookupCodeExample;
import com.foya.dao.mybatis.model.TbPayLookupCodeKey;
import com.foya.dao.mybatis.model.TbPayPaymentItem;
import com.foya.dao.mybatis.model.TbPayPaymentItemExample;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.org.DeptDTO;
@Repository
public class PayPublicUtilDao extends BaseDao {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UTbOrgDeptMapper uTbOrgDeptMapper;
	@Autowired
	private UTbAuthPersonnelMapper uTbAuthPersonnelMapper;
	@Autowired
	private TbSysLookupMapper tbSysLookupMapper;
	@Autowired
	private TbOrgDomainMapper tbOrgDomainMapper;
	@Autowired
	private UTbOrgDomainMapper uTbOrgDomainMapper;
	@Autowired
	private TbOrgDeptMapper tbOrgDeptMapper;
	@Autowired
	private TbPayLookupCodeMapper tbPayLookupCodeMapper;
	@Autowired
	private TbPayProvisionalAttachmentMapper tbPayProvisionalAttachmentMapper;
	@Autowired
	private TbSiteMainMapper tbSiteMainMapper;
	@Autowired
	private TbLsLessorMapper tbLsLessorMapper;
	@Autowired
	private TbLsCollectUnitMapper tbLsCollectUnitMapper;
	@Autowired
	private TbLsCollectUnitSubMapper tbLsCollectUnitSubMapper;
	@Autowired
	private ProcedureNomsMapper procedureNomsMapper;
	@Autowired
	private TbPayPaymentItemMapper tbPayPaymentItemMapper;
	/*查詢網路服務事業部組織清單*/
	public List<TbOrgDomain> getAllDomain() {
		return uTbOrgDomainMapper.getAllDomain();
	}
	
	/*查詢網路服務事業部組織清單*/
	public List<DeptDTO> getAllFourthDept() {
		return uTbOrgDeptMapper.searchDeptByIdLike();
	}
	
	/*查詢登入用戶的部門編號*/
	public UserDTO getPersonnelDeptId(String userId) {
		return uTbAuthPersonnelMapper.selectByPsn(userId);
	}
	
	/*以Type,Code查詢系統設定資料*/
	public List<TbSysLookup> getLookupByTypeAndCode(String type, String code) {
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo(type).andCODEEqualTo(code);
		return tbSysLookupMapper.selectByExample(example);
	}
	
	/*以Type查詢系統設定資料*/
	public List<TbSysLookup> getLookupByType(String type) {
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo(type);
		example.setOrderByClause("DISPLAY_ORDER");
		return tbSysLookupMapper.selectByExample(example);
	}
	
	/*查詢Dept資料*/
	public DeptDTO searchDeptById(String deptId) {
		return uTbOrgDeptMapper.searchDeptById(deptId);
	}
	
	public TbOrgDomain selectTbOrgDomainByPrimaryKey(String id){
		return tbOrgDomainMapper.selectByPrimaryKey(id);
	}
	
	/*以Type,Name查詢系統設定資料*/
	public List<TbSysLookup> getLookupByTypeAndName(String type, String name) {
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo(type).andNAMEEqualTo(name);
		return tbSysLookupMapper.selectByExample(example);
	}
	
	public List<TbOrgDept> getDeptByDomain(TbOrgDeptExample example){
		return tbOrgDeptMapper.selectByExample(example);
	}	

	/*以Type,code查詢Pay lookup code*/
	public List<TbPayLookupCode> getPayLookupByTypeAndName(String type, String code) {
		TbPayLookupCodeExample example = new TbPayLookupCodeExample();
		example.createCriteria().andLOOKUP_TYPEEqualTo(type).andLOOKUP_CODEEqualTo(code);
		return tbPayLookupCodeMapper.selectByExample(example);
	}
	
	/*以Type,code查詢Pay lookup code*/
	public TbPayLookupCode getPayLookupByTypeAndCode(String type, String code) {
		TbPayLookupCodeKey key = new TbPayLookupCodeKey();
		key.setLOOKUP_CODE(code);
		key.setLOOKUP_TYPE(type);
		return tbPayLookupCodeMapper.selectByPrimaryKey(key);
	}
	
	/*以Type查詢Pay lookup code*/
	public List<TbPayLookupCode> getPayLookupByType(String type) {
		TbPayLookupCodeExample example = new TbPayLookupCodeExample();
		example.createCriteria().andLOOKUP_TYPEEqualTo(type);
		return tbPayLookupCodeMapper.selectByExample(example);
	}
	
	public TbSiteMain getgetSiteMainByPk(String siteId){
		return tbSiteMainMapper.selectByPrimaryKey(siteId);
	}
	
	/*以Type查詢Pay lookup code like*/
	public List<TbPayLookupCode> getPayLookupByTypeAndCodeLike(String type, String code) {
		TbPayLookupCodeExample example = new TbPayLookupCodeExample();
		example.createCriteria().andLOOKUP_TYPEEqualTo(type).andLOOKUP_CODELike("%"+code+"%");
		return tbPayLookupCodeMapper.selectByExample(example);
	}	
	
	public List<TbLsLessor> getTbLsLessor(TbLsLessorExample example){
		return tbLsLessorMapper.selectByExample(example);
	}
	
	public List<TbLsCollectUnit> getTbLsCollectUnit(TbLsCollectUnitExample example){
		return tbLsCollectUnitMapper.selectByExample(example);
	}
	
	public List<TbLsCollectUnitSub> getTbLsCollectUnitSub(TbLsCollectUnitSubExample example){
		return tbLsCollectUnitSubMapper.selectByExample(example);
	}
	/**
	 * 計算所得稅/健保費/營業稅  Call PAY_FN_GET_TAX
	 * 
	 * @param type 1:所得稅/2:健保費/3: 營業稅
	 * @param amount 傳入金額
	 * @param includeTax Y:PI_AMT為含稅金額   N:PI_AMT為未含稅金額
	 * @return 計算後金額
	 */
	public Integer payFnGetTax(String type, BigDecimal amount, String includeTax){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("PI_TYPE", type);
		map.put("PI_AMT", amount);
		map.put("PI_INCLUDE_TAX", includeTax);		
		procedureNomsMapper.PAY_FN_GET_TAX(map);
		return (Integer) map.get("RETURN_VALUE");
	}
	/**
	 * 取得類別名稱 TB_PAY_PAYMENT_ITEM.ITEM_DESC
	 * 
	 * @param paymentReqItem 請款類別
	 * @param expItem 費用項目(代碼)
	 * @return 費用項目-名稱
	 */
	public String getPayPaymentItemDescByExpItem(String paymentReqItem, String expItem){
		TbPayPaymentItemExample example = new TbPayPaymentItemExample();
		example.createCriteria().andPAYMENT_REQ_ITEMEqualTo(paymentReqItem)
		.andEXP_ITEMEqualTo(expItem);
		List<TbPayPaymentItem> list = tbPayPaymentItemMapper.selectByExample(example);
		if(list.size()>0){
			return list.get(0).getEXP_ITEM_DESC();
		}
		return expItem;
	}	
	/**
	 * 租約異動-取出資料   --Call PAY_PC_GET_APP_AMT
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 * @return List<Map<String, Object>>
	 */	
	public List<Map<String, Object>> payPcGetAppAmt(HashMap<String,Object> dataObj){
		return procedureNomsMapper.PAY_PC_GET_APP_AMT(dataObj);
	}
	/**
	 * 租約異動-新增資料   --Call PAY_PC_PROC_APP_AMT
	 * 
	 * @param dataObj
	 *            查詢條件
	 * 
	 */	
	public void payPcProcAppAmt(HashMap<String,Object> dataObj){
		procedureNomsMapper.PAY_PC_PROC_APP_AMT(dataObj);
	}	
}

