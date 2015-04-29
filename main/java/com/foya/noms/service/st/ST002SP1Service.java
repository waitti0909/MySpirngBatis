package com.foya.noms.service.st;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbComEqpType;
import com.foya.dao.mybatis.model.TbComEqpTypeExample;
import com.foya.dao.mybatis.model.TbComSiteFeq;
import com.foya.dao.mybatis.model.TbComSiteFeqExample;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.noms.dao.st.ST002SP1Dao;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.service.BaseService;
import com.foya.noms.util.LabelValueModel;

@Service
public class ST002SP1Service extends BaseService {
	@Autowired
	private ST002SP1Dao st002SP1Dao;
	/**
	 * 查詢site
	 * @param map
	 * @return
	 */
	public List<SiteDTO> getWorkSiteList(Map<String ,String> map){
		return st002SP1Dao.findWorkSiteList(map);
	}
	
	/**
	 * 用TbSiteMainExample查詢site
	 * @param example
	 * @return
	 */
	public List<TbSiteMain> getWorkSiteListByExample(TbSiteMainExample example){
		return st002SP1Dao.findWorkSiteListByExample(example);
	}
	
	/**
	 * 用siteId查詢siteMain
	 * @param siteId
	 * @return
	 */
	public TbSiteMain getWorkSiteBySiteId(String siteId){
		return st002SP1Dao.findWorkSiteBySiteId(siteId);
	}
	
	/**
	 * 用locationId查詢Site
	 * @param locationId
	 * @return
	 */
	public List<SiteDTO> getSiteByLocationId(String locationId){
		return st002SP1Dao.getSiteByLocationId(locationId);
	}
	
	/**
	 * 查詢設備型態
	 * @return
	 */
	public List<LabelValueModel> getEqpTypes(){
		TbComEqpTypeExample example = new TbComEqpTypeExample();
		List<TbComEqpType> eqpTypes = st002SP1Dao.findEqpTypes(example);
		List<LabelValueModel> labelValuemodelList = new ArrayList<LabelValueModel>();
		for(TbComEqpType eqpType : eqpTypes){
			labelValuemodelList.add(LabelValueModel.getLabelValueModel(eqpType.getEQP_NAME(), eqpType.getEQP_TYPE_ID()));
		}
		return labelValuemodelList;
	}
	/**
	 * 查詢基站頻段
	 * @return
	 */
	public List<LabelValueModel> getSiteFeqs(){
		TbComSiteFeqExample example = new  TbComSiteFeqExample();
		List<TbComSiteFeq> siteFeqs = st002SP1Dao.findSiteFeqs(example);
		List<LabelValueModel> labelValuemodelList = new ArrayList<LabelValueModel>();
		for(TbComSiteFeq siteFeq : siteFeqs){
			labelValuemodelList.add(LabelValueModel.getLabelValueModel(siteFeq.getFEQ_NAME(), siteFeq.getFEQ_ID()));
		}
		return labelValuemodelList;
	}
	
	/**
	 * 查詢基站頻段
	 * @param example
	 * @return
	 */
	public List<TbComSiteFeq> getSiteFeqsByExample(TbComSiteFeqExample example){
		return st002SP1Dao.findSiteFeqs(example);
	}
	/**
	 * 查詢設備型態
	 * @return
	 */
	public List<TbComEqpType> getEqpTypesByExample(TbComEqpTypeExample example){
		return st002SP1Dao.findEqpTypes(example);
	}
}
