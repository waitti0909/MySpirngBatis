package com.foya.noms.dao.pay;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.foya.dao.mybatis.mapper.ProcedureNomsMapper;
import com.foya.dao.mybatis.mapper.UTbLsLocElecMapper;
import com.foya.dao.mybatis.mapper.UTbPayPaymentRequestVoucherMapper;
import com.foya.dao.mybatis.model.TbLsLocElec;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.pay.TbLsLocElecDTO;
import com.foya.noms.dto.pay.TbPayElectricityDTO;
import com.foya.noms.dto.pay.TbPayPaymentRequestDtlDTO;
import com.foya.noms.dto.pay.TbPayRepartitionDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class Pay011Dao extends BaseDao {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UTbPayPaymentRequestVoucherMapper uTbPayPaymentRequestVoucherMapper;
	@Autowired
	private UTbLsLocElecMapper uTbLsLocElecMapper;
	@Autowired
	private ProcedureNomsMapper procedureNomsMapper;
	
	public List<TbPayElectricityDTO> selectTbPayElectricity(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("cashReqNo");// 設定排序欄位
		List<TbPayElectricityDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayElectricityMapper.selectTbPayElectricityByPay011",dataObj,  pageBounds);
		PageList<TbPayElectricityDTO> pageList = (PageList<TbPayElectricityDTO>)list;
		return pageList;
	}

	public List<TbPayPaymentRequestDtlDTO> selectTbPayPaymentRequestDtlByPAY011(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("LOCATION_ID");// 設定排序欄位
		List<TbPayPaymentRequestDtlDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayPaymentRequestDtlMapper.selectTbPayPaymentRequestDtlByPAY011",dataObj,  pageBounds);
		PageList<TbPayPaymentRequestDtlDTO> pageList = (PageList<TbPayPaymentRequestDtlDTO>)list;
		return pageList;
	}
	
	public List<TbLsLocElecDTO> getSiteIdList(HashMap<String,Object> dataObj){
		return uTbLsLocElecMapper.getSiteIdList(dataObj);
	}
	
	public TbLsLocElec getTbLsLocElec(HashMap<String,Object> dataObj) {
		return uTbLsLocElecMapper.getTbLsLocElec(dataObj);
	}
	
	/**
	 * 自動產生單號
	 * @param map
	 */
	public void callPayPcGetSeqnoByMap(Map<String, Object> map) {
		procedureNomsMapper.PAY_PC_GET_SEQNO(map);
	}
	
	public List<TbPayRepartitionDTO> selectTbPayRepartitionByPay011(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds();
		List<TbPayRepartitionDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayRepartitionMapper.selectTbPayRepartitionByPay011",dataObj,  pageBounds);
		PageList<TbPayRepartitionDTO> pageList = (PageList<TbPayRepartitionDTO>)list;
		return pageList;
	}
	
}
