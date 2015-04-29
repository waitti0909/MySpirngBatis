package com.foya.noms.dao.pay;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.ProcedureNomsMapper;
import com.foya.dao.mybatis.mapper.TbAuthPersonnelMapper;
import com.foya.dao.mybatis.mapper.TbComCompanyMapper;
import com.foya.dao.mybatis.mapper.TbComItemMainMapper;
import com.foya.dao.mybatis.mapper.TbComPoMainMapper;
import com.foya.dao.mybatis.mapper.TbLsSiteMapper;
import com.foya.dao.mybatis.mapper.TbOrgDeptMapper;
import com.foya.dao.mybatis.mapper.TbPayLookupCodeMapper;
import com.foya.dao.mybatis.mapper.TbPayVoucherCreditMapper;
import com.foya.dao.mybatis.mapper.TbSiteWorkOrderMapper;
import com.foya.dao.mybatis.mapper.UTbPayAcceptanceOrderMapper;
import com.foya.dao.mybatis.mapper.UTbPayOutsourceAcceptanceDtlMapper;
import com.foya.dao.mybatis.mapper.UTbPayOutsourceAcceptanceMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentRequestVoucherMapper;
import com.foya.dao.mybatis.mapper.UTbSiteOsItemMapper;
import com.foya.dao.mybatis.mapper.UTbSiteOutsourcingMapper;
import com.foya.dao.mybatis.model.TbAuthPersonnel;
import com.foya.dao.mybatis.model.TbAuthPersonnelExample;
import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComCompanyExample;
import com.foya.dao.mybatis.model.TbComItemMain;
import com.foya.dao.mybatis.model.TbComItemMainExample;
import com.foya.dao.mybatis.model.TbComPoMain;
import com.foya.dao.mybatis.model.TbComPoMainExample;
import com.foya.dao.mybatis.model.TbLsSite;
import com.foya.dao.mybatis.model.TbLsSiteExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbPayAcceptanceOrderKey;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbPayLookupCodeExample;
import com.foya.dao.mybatis.model.TbPayVoucherCredit;
import com.foya.dao.mybatis.model.TbPayVoucherCreditExample;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.UTbPayAcceptanceOrderExample;
import com.foya.dao.mybatis.model.UTbPayOutsourceAcceptanceDtlExample;
import com.foya.dao.mybatis.model.UTbPayOutsourceAcceptanceExample;
import com.foya.dao.mybatis.model.UTbPayPaymentRequestVoucherExample;
import com.foya.dao.mybatis.model.UTbSiteOsItemExample;
import com.foya.dao.mybatis.model.UTbSiteOutsourcingExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.pay.TbPayAcceptanceOrderDTO;
import com.foya.noms.dto.pay.TbPayOutsourceAcceptanceDTO;
import com.foya.noms.dto.pay.TbPayOutsourceAcceptanceDtlDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestVoucherDTO;
import com.foya.noms.dto.st.TbSiteOsItemDTO;
import com.foya.noms.dto.st.TbSiteOutsourcingDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class PAY006Dao extends BaseDao {

	@Autowired
	private TbComCompanyMapper tbComCompanyMapper;

	@Autowired
	private TbPayLookupCodeMapper tbPayLookupCodeMapper;

	@Autowired
	private UTbPayOutsourceAcceptanceMapper uTbPayOutsourceAcceptanceMapper;

	@Autowired
	private UTbPayAcceptanceOrderMapper uTbPayAcceptanceOrderMapper;

	@Autowired
	private UTbPayOutsourceAcceptanceDtlMapper uTbPayOutsourceAcceptanceDtlMapper;

	@Autowired
	private TbAuthPersonnelMapper tbAuthPersonnelMapper;

	@Autowired
	private UTbPayPaymentRequestVoucherMapper uTbPayPaymentRequestVoucherMapper;

	@Autowired
	private TbPayVoucherCreditMapper tbPayVoucherCreditMapper;

	@Autowired
	private TbComItemMainMapper tbComItemMainMapper;

	@Autowired
	private UTbSiteOutsourcingMapper uTbSiteOutsourcingMapper;

	@Autowired
	private UTbSiteOsItemMapper uTbSiteOsItemMapper;

	@Autowired
	private TbSiteWorkOrderMapper tbSiteWorkOrderMapper;

	@Autowired
	private TbLsSiteMapper tbLsSiteMapper;

	@Autowired
	private TbComPoMainMapper tbComPoMainMapper;

	@Autowired
	private TbOrgDeptMapper tbOrgDeptMapper;

	@Autowired
	private ProcedureNomsMapper procedureNomsMapper;

	/**
	 * 取得廠商清單
	 * @param example
	 * @return
	 */
	public List<TbComCompany> selectComCompanyByExample(TbComCompanyExample example) {
		return tbComCompanyMapper.selectByExample(example);
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
	 * 取得人員清單
	 * @param example
	 * @return
	 */
	public List<TbAuthPersonnel> selectAuthPersonnelByExample(TbAuthPersonnelExample example) {
		return tbAuthPersonnelMapper.selectByExample(example);
	}

	/**
	 * 取得驗收清單資料 by Page
	 * @param example
	 * @return
	 */
	public List<TbPayOutsourceAcceptanceDTO> selectPayOutsourceAcceptanceByExamplePage(UTbPayOutsourceAcceptanceExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbPayOutsourceAcceptanceDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbPayOutsourceAcceptanceMapper.selectByExample",
						example, pageBounds);

		PageList<TbPayOutsourceAcceptanceDTO> pageList = (PageList<TbPayOutsourceAcceptanceDTO>) list;
		return pageList;
	}

	/**
	 * 取得驗收清單資料
	 * @param example
	 * @return
	 */
	public List<TbPayOutsourceAcceptanceDTO> selectPayOutsourceAcceptanceByExample(UTbPayOutsourceAcceptanceExample example) {
		return uTbPayOutsourceAcceptanceMapper.selectByExample(example);
	}

	/**
	 * 取得驗收清單資料by key
	 * @param ap_no
	 * @return
	 */
	public TbPayOutsourceAcceptanceDTO selectPayOutsourceAcceptanceByPrimaryKey(String ap_no) {
		return uTbPayOutsourceAcceptanceMapper.selectByPrimaryKey(ap_no);
	}

	/**
	 * 新增驗收清單資料
	 * @param record
	 * @return
	 */
	public int insertPayOutsourceAcceptanceSelective(TbPayOutsourceAcceptanceDTO record) {
		return uTbPayOutsourceAcceptanceMapper.insertSelective(record);
	}

	/**
	 * 修改驗收清單資料by key
	 * @param record
	 * @return
	 */
	public int updatePayOutsourceAcceptanceByPrimaryKeySelective(TbPayOutsourceAcceptanceDTO record) {
		return uTbPayOutsourceAcceptanceMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 刪除驗收清單資料
	 * @param example
	 * @return
	 */
	public int deletePayOutsourceAcceptanceByExample(UTbPayOutsourceAcceptanceExample example) {
		return uTbPayOutsourceAcceptanceMapper.deleteByExample(example);
	}

	/**
	 * 取得工程驗收清單資料 by page
	 * @param example
	 * @return
	 */
	public List<TbPayAcceptanceOrderDTO> selectPayAcceptanceOrderByExamplePage(
			UTbPayAcceptanceOrderExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbPayAcceptanceOrderDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbPayAcceptanceOrderMapper.selectByExample",
						example, pageBounds);

		PageList<TbPayAcceptanceOrderDTO> pageList = (PageList<TbPayAcceptanceOrderDTO>) list;
		return pageList;
	}

	/**
	 * 取得工程驗收清單資料
	 * @param example
	 * @return
	 */
	public List<TbPayAcceptanceOrderDTO> selectPayAcceptanceOrderByExample(
			UTbPayAcceptanceOrderExample example) {
		return uTbPayAcceptanceOrderMapper.selectByExample(example);
	}

	/**
	 * 新增工程驗收資料
	 * @param record
	 * @return
	 */
	public int insertPayAcceptanceOrderSelective(TbPayAcceptanceOrderDTO record) {
		return uTbPayAcceptanceOrderMapper.insertSelective(record);
	}

	/**
	 * 刪除工程驗收資料
	 * @param key
	 * @return
	 */
	public int deletePayAcceptanceOrderByPrimaryKey(TbPayAcceptanceOrderKey key) {
		return uTbPayAcceptanceOrderMapper.deleteByPrimaryKey(key);
	}

	/**
	 * 取得工程驗收清單筆數
	 * @param example
	 * @return
	 */
	public int countPayAcceptanceOrderByExample(UTbPayAcceptanceOrderExample example) {
		return uTbPayAcceptanceOrderMapper.countByExample(example);
	}

	/**
	 * 取得工程驗收工單資料 by page
	 * @param example
	 * @return
	 */
	public List<TbPayOutsourceAcceptanceDtlDTO> selectPayOutsourceAcceptanceDtlByExamplePage (
			UTbPayOutsourceAcceptanceDtlExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbPayOutsourceAcceptanceDtlDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbPayOutsourceAcceptanceDtlMapper.selectByExample",
						example, pageBounds);

		PageList<TbPayOutsourceAcceptanceDtlDTO> pageList = (PageList<TbPayOutsourceAcceptanceDtlDTO>) list;
		return pageList;
	}

	/**
	 * 取得工程驗收工單資料
	 * @param example
	 * @return
	 */
	public List<TbPayOutsourceAcceptanceDtlDTO> selectPayOutsourceAcceptanceDtlByExample(
			UTbPayOutsourceAcceptanceDtlExample example) {
		return uTbPayOutsourceAcceptanceDtlMapper.selectByExample(example);
	}

	/**
	 * 新增工程驗收工單資料
	 * @param record
	 * @return
	 */
	public int insertPayOutsourceAcceptanceDtlSelective(TbPayOutsourceAcceptanceDtlDTO record) {
		return uTbPayOutsourceAcceptanceDtlMapper.insertSelective(record);
	}

	/**
	 * 修改工程驗收工單資料
	 * @param record
	 */
	public int updatePayOutsourceAcceptanceDtlByPrimaryKeySelective(TbPayOutsourceAcceptanceDtlDTO record) {
		return uTbPayOutsourceAcceptanceDtlMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 刪除工程驗收工單資料
	 * @param example
	 * @return
	 */
	public int deletePayOutsourceAcceptanceDtlByExample(UTbPayOutsourceAcceptanceDtlExample example) {
		return uTbPayOutsourceAcceptanceDtlMapper.deleteByExample(example);
	}

	/**
	 * 取得工單項目資料
	 * @param example
	 * @return
	 */
	public List<TbComItemMain> selectComItemMainByExample(TbComItemMainExample example) {
		return tbComItemMainMapper.selectByExample(example);
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
	 * 取得憑證明細資料
	 * @param example
	 * @return
	 */
	public List<TbPayVoucherCredit> selectPayVoucherCreditByExample(TbPayVoucherCreditExample example) {
		return tbPayVoucherCreditMapper.selectByExample(example);
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
	 * 取得站點工程資料 by page
	 * @param example
	 * @return
	 */
	public List<TbSiteOutsourcingDTO> selectSiteOutsourcingByExamplePage(UTbSiteOutsourcingExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbSiteOutsourcingDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbSiteOutsourcingMapper.selectByExample",
						example, pageBounds);

		PageList<TbSiteOutsourcingDTO> pageList = (PageList<TbSiteOutsourcingDTO>) list;
		return pageList;
	}

	/**
	 * 取得站點工程資料
	 * @param example
	 * @return
	 */
	public List<TbSiteOutsourcingDTO> selectSiteOutsourcingByExample(
			UTbSiteOutsourcingExample example) {
		return uTbSiteOutsourcingMapper.selectByExample(example);
	}

	/**
	 * 取得站點工程明細資料
	 * @param example
	 * @return
	 */
	public List<TbSiteOsItemDTO> selectSiteOsItemByExample(UTbSiteOsItemExample example) {
		return uTbSiteOsItemMapper.selectByExample(example);
	}

	/**
	 * 取得站點工程相關金額加總
	 * @param example
	 * @return
	 */
	public TbSiteOutsourcingDTO selectSiteOutsourcingSumAmountByExample(UTbSiteOutsourcingExample example) {
		return uTbSiteOutsourcingMapper.selectSumAmountByExample(example);
	}

	/**
	 * 取得站點訂單編號資料
	 * @param ORDER_ID
	 * @return
	 */
	public TbSiteWorkOrder selectSiteWorkOrderByPrimaryKey(String ORDER_ID) {
		return tbSiteWorkOrderMapper.selectByPrimaryKey(ORDER_ID);
	}

	/**
	 * 取得站點租約資料
	 * @param example
	 * @return
	 */
	public List<TbLsSite> selectLsSiteByExample(TbLsSiteExample example) {
		return tbLsSiteMapper.selectByExample(example);
	}

	/**
	 * 取得合約資料
	 * @param example
	 * @return
	 */
	public List<TbComPoMain> selectComPoMainByExample(TbComPoMainExample example) {
		return tbComPoMainMapper.selectByExample(example);
	}

	/**
	 * 取得部門資料
	 * @param example
	 * @return
	 */
	public List<TbOrgDept> selectOrgDeptByExample(TbOrgDeptExample example) {
		return tbOrgDeptMapper.selectByExample(example);
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
}