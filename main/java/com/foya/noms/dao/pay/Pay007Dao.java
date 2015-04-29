package com.foya.noms.dao.pay;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbNomsApInvoiceTempMapper;
import com.foya.dao.mybatis.mapper.TbPayCashRequirementMapper;
import com.foya.dao.mybatis.mapper.TbPayCheckDisregardMapper;
import com.foya.dao.mybatis.model.TbNomsApInvoiceTemp;
import com.foya.dao.mybatis.model.TbPayCashRequirement;
import com.foya.dao.mybatis.model.TbPayCheckDisregard;
import com.foya.dao.mybatis.model.TbPayCheckDisregardExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class Pay007Dao extends BaseDao {

	@Autowired
	private TbPayCashRequirementMapper tbPayCashRequirementMapper;
	@Autowired
	private TbNomsApInvoiceTempMapper tbNomsApInvoiceTempMapper;
	@Autowired
	private TbPayCheckDisregardMapper tbPayCheckDisregardMapper;

	public List<TbPayCashRequirementDTO> selectTbPayCashRequirementPay007ByType(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("CASH_REQ_NO");// 設定排序欄位
		List<TbPayCashRequirementDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayCashRequirementMapper.selectTbPayCashRequirementPay007ByType",dataObj,  pageBounds);
		PageList<TbPayCashRequirementDTO> pageList = (PageList<TbPayCashRequirementDTO>)list;
		return pageList;
	}

	public List<TbPayCashRequirementDTO> selectTbPayCashRequirementPay007(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("CASH_REQ_NO");// 設定排序欄位
		List<TbPayCashRequirementDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayCashRequirementMapper.selectTbPayCashRequirementPay007",dataObj,  pageBounds);
		PageList<TbPayCashRequirementDTO> pageList = (PageList<TbPayCashRequirementDTO>)list;
		return pageList;
	}

	public int updatePayCashRequirement(TbPayCashRequirement record){
		return tbPayCashRequirementMapper.updateByPrimaryKeySelective(record);
	}
	
	public int updatePayCheckDisregard(String cashReqNo, TbPayCheckDisregard record){
		TbPayCheckDisregardExample example = new TbPayCheckDisregardExample();
		example.createCriteria().andCASH_REQ_NOEqualTo(cashReqNo);
		return tbPayCheckDisregardMapper.updateByExampleSelective(record,example);
	}

	public int insertNomsApInvoiceTemp(TbNomsApInvoiceTemp record){
		return tbNomsApInvoiceTempMapper.insertSelective(record);
	}
	
}
