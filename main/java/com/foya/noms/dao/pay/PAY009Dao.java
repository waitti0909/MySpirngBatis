package com.foya.noms.dao.pay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.ProcedureNomsMapper;
import com.foya.dao.mybatis.mapper.TbPayPaymentRequestVoucherMapper;
import com.foya.dao.mybatis.mapper.TbPayVoucherCreditMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentRequestDtlMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentRequestVoucherMapper;
import com.foya.dao.mybatis.mapper.UTbPayVoucherCreditMapper;
import com.foya.dao.mybatis.model.TbPayPaymentRequestDtl;
import com.foya.dao.mybatis.model.TbPayPaymentRequestVoucherExample;
import com.foya.dao.mybatis.model.TbPayVoucherCredit;
import com.foya.dao.mybatis.model.TbPayVoucherCreditExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestVoucherDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class PAY009Dao extends BaseDao {

	@Autowired
	private UTbPayPaymentRequestVoucherMapper uTbPayPaymentRequestVoucherMapper;
	@Autowired
	private UTbPayPaymentRequestDtlMapper uTbPayPaymentRequestDtlMapper;
	@Autowired
	private ProcedureNomsMapper procedureNomsMapper;
	@Autowired
	private TbPayVoucherCreditMapper tbPayVoucherCreditMapper;
	@Autowired
	private TbPayPaymentRequestVoucherMapper tbPayPaymentRequestVoucherMapper;
	@Autowired
	private UTbPayVoucherCreditMapper uTbPayVoucherCreditMapper;	

	/**
	 * 查詢	憑證資料  -金額加總
	 * 
	 * @param dataObj	查詢條件
	 * 
	 * @return List<TbPayPaymentRequestVoucherDTO>
	 */
	public List<TbPayPaymentRequestVoucherDTO> selectPaymentAmount(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("VOUCHER_NO");
		List<TbPayPaymentRequestVoucherDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayPaymentRequestVoucherMapper.selectPaymentAmount",dataObj,  pageBounds);
		PageList<TbPayPaymentRequestVoucherDTO> pageList = (PageList<TbPayPaymentRequestVoucherDTO>)list;
		return pageList;
	}
	/**
	 * 查詢憑證 明細資料
	 * 
	 * @param voucherNo 憑證處理單號
	 * 
	 * @return List<TbPayPaymentRequestVoucherDTO>
	 */
	public List<TbPayPaymentRequestVoucherDTO> selectPayPaymentRequestVoucherByVoucherNo(String voucherNo){
		return uTbPayPaymentRequestVoucherMapper.selectByVoucherNo(voucherNo);
	}

	/**
	 * 新增頁面 - 查詢 - Detail2 所需 站點編號 (PayPaymentRequestDtl.location_id)
	 * 
	 * @return List<TbPayPaymentRequestDtl>
	 */
	public List<TbPayPaymentRequestDtl> selectPayPaymentRequestDtlLocationId(){
		return uTbPayPaymentRequestDtlMapper.selectDistinctLocationId();
	}
	/**
	 * 新增頁面 - 查詢 - Detail2 所需相關金額 (PayPaymentRequestDtl.Tax Amount)
	 * 
	 * @param dataObj	查詢條件
	 * 
	 * @return List<TbPayPaymentRequestDtlDTO>
	 */
	public List<TbPayPaymentRequestDtlDTO> selectPayPaymentRequestDtlTaxSumAmt(HashMap<String,Object> dataObj){
		return uTbPayPaymentRequestDtlMapper.selectTaxSumAmt(dataObj);
	}
	/**
	  * 查詢 請款單 - Detail2所需資料
	 * 
	 * @param dataObj	查詢條件
	 * 
	 * @return List<TbPayPaymentRequestDtlDTO>
	 */
	public List<TbPayPaymentRequestDtlDTO> select4Pay009Detail2(HashMap<String,Object> dataObj){
		return uTbPayPaymentRequestDtlMapper.select4Pay009Detail2(dataObj);
	}		
	/**
	  * 查詢 請款單 - Detail3 所需資料
	 * 
	 * @param dataObj	查詢條件
	 * 
	 * @return List<TbPayPaymentRequestDtlDTO>
	 */
	public List<TbPayPaymentRequestDtlDTO> select4Pay009Detail3(HashMap<String,Object> dataObj){
		return uTbPayPaymentRequestDtlMapper.select4Pay009Detail3(dataObj);
	}	
	/**
	 * 自動產生單號 For 憑證處理類別(VOU)
	 * @param map
	 */
	public void callPayPcGetSeqnoByMap(Map<String, Object> map) {
		procedureNomsMapper.PAY_PC_GET_SEQNO(map);
	}
	/**
	 * 新增 - 請款憑證 
	 * @param record
	 * @return 異動筆數
	 */
	public int insertPaymentRequestVoucher(TbPayPaymentRequestVoucherDTO record) {
		return uTbPayPaymentRequestVoucherMapper.insertSelective(record);
	}
	/**
	 * 新增 - 請款憑證  - 憑證沖抵記錄
	 * @param record
	 * @return 異動筆數
	 */
	public int insertVoucherCreditTaxFromFn(TbPayVoucherCredit record) {
		return uTbPayVoucherCreditMapper.insertSelectiveTaxFromFn(record);
	}
	/**
	 * 更新 - 請款憑證  - 憑證沖抵記錄
	 * @param record
	 * @return 異動筆數
	 */
	public int updateVoucherCreditPlusTaxByRecord(TbPayVoucherCredit record) {
		return uTbPayVoucherCreditMapper.updatePlusTaxByRecord(record);
	}
	/**
	 * 檢查 - 請款憑證  - 憑證沖抵記錄
	 * @param mstSeqNbr Mst_Seq_Nbr
	 * @param paymentReqNo PAYMENT_REQ_NO
	 * @param paymentReqItemNo PAYMENT_REQ_ITEM_NO
	 * 
	 * @return cnt 筆數
	 */
	public int countVoucherCredit(Long mstSeqNbr, Long paymentReqNo, Short paymentReqItemNo) {
		TbPayVoucherCreditExample example = new TbPayVoucherCreditExample();
		example.createCriteria().andMST_SEQ_NBREqualTo(mstSeqNbr)
		.andPAYMENT_REQ_NOEqualTo(paymentReqNo)
		.andPAYMENT_REQ_ITEM_NOEqualTo(paymentReqItemNo);
		return tbPayVoucherCreditMapper.countByExample(example);
	}
	/**
	 * 刪除 - 請款憑證  - 憑證沖抵記錄
	 * @param mstSeqNbr 多筆Mst_Seq_Nbr
	 * @return 異動筆數
	 */
	public int deleteVoucherCreditByMstSeqNbr(List<Long> mstSeqNbr) {
		TbPayVoucherCreditExample example = new TbPayVoucherCreditExample();
		example.createCriteria().andMST_SEQ_NBRIn(mstSeqNbr);
		return tbPayVoucherCreditMapper.deleteByExample(example);
	}
	/**
	 * 刪除 - 請款憑證  - 憑證沖抵記錄
	 * @param mstSeqNbr Mst_Seq_Nbr
	 * @param prType 請款類別
	 * @param locationId 站點編號
	 * @return 異動筆數
	 */
	public int deleteVoucherCreditByPrTypeLocationId(Long mstSeqNbr, String prType, String locationId) {
		TbPayVoucherCreditExample example = new TbPayVoucherCreditExample();
		example.createCriteria().andMST_SEQ_NBREqualTo(mstSeqNbr)
		.andLOCATION_IDEqualTo(locationId)
		.andPR_TYPEEqualTo(prType);
		return tbPayVoucherCreditMapper.deleteByExample(example);
	}
	/**
	 * 刪除 - 請款憑證 
	 * @param seqNbr 多筆SeqNbr
	 * @return 異動筆數
	 */
	public int deletePaymentRequestVoucherBySeqNbr(List<Long> seqNbr) {
		TbPayPaymentRequestVoucherExample example = new TbPayPaymentRequestVoucherExample();
		example.createCriteria().andSEQ_NBRIn(seqNbr);
		return tbPayPaymentRequestVoucherMapper.deleteByExample(example);
	}
	/**
	 * 更新 - 請款憑證 
	 * @param record
	 */
	public int updateCreditMadeAmtTaxFromVoucherCredit(TbPayPaymentRequestVoucherDTO record) {
		return uTbPayPaymentRequestVoucherMapper.updateCreditMadeAmtTaxFromVoucherCredit(record);
	}
}
