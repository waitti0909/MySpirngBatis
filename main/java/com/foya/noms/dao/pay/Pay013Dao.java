package com.foya.noms.dao.pay;

import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.foya.dao.mybatis.mapper.TbPayRepartitionMapper;
import com.foya.dao.mybatis.mapper.UTbPayRepartitionMapper;
import com.foya.dao.mybatis.model.TbPayRepartition;
import com.foya.dao.mybatis.model.TbPayRepartitionExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.pay.TbLsSiteDTO;
import com.foya.noms.dto.pay.TbPayCashRequirementDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Repository
public class Pay013Dao extends BaseDao {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TbPayRepartitionMapper tbPayRepartitionMapper;
	@Autowired
	private UTbPayRepartitionMapper uTbPayRepartitionMapper;

	public List<TbPayCashRequirementDTO> selectTbPayCashRequirementPay013(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("CONTRACT_NO");// 設定排序欄位
		List<TbPayCashRequirementDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbPayCashRequirementMapper.selectTbPayCashRequirementPay013",dataObj,  pageBounds);
		return list;
	}
	
	public List<TbLsSiteDTO> selectTbLsSitePay013(HashMap<String,Object> dataObj){
		PageBounds pageBounds = getPageBounds("SITE_ID");// 設定排序欄位
		List<TbLsSiteDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbLsSiteMapper.selectTbLsSitePay013",dataObj,  pageBounds);
		return list;
	}
	
	public int countPayByExample(TbPayRepartitionExample example) {
		return tbPayRepartitionMapper.countByExample(example);
	}

	public String deleteByExample(TbPayRepartitionExample example) {
		String returnMsg="success";
		try{			
			tbPayRepartitionMapper.deleteByExample(example);
		}catch (Exception e){
			returnMsg="fail";
		}
		return returnMsg;
	}
	
	public TbPayRepartition selectMaxSeq(String appDate) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("appDate", appDate);
		return uTbPayRepartitionMapper.selerctMaxSeq(map);
	}

	public String insertPayRepartition(TbPayRepartition record){
		String returnMsg="success";
		try{
			tbPayRepartitionMapper.insertSelective(record);
		}catch (Exception e){
			returnMsg="fail";
		}
		return returnMsg;
	}
	
}
