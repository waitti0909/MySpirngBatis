package com.foya.noms.service.st;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpModelExample;
import com.foya.dao.mybatis.model.TbComEqpType;
import com.foya.dao.mybatis.model.TbComEqpTypeExample;
import com.foya.dao.mybatis.model.TbComSiteFeq;
import com.foya.dao.mybatis.model.TbComSiteFeqExample;
import com.foya.noms.dao.st.ST002Dao;
import com.foya.noms.dao.st.ST002SP1Dao;
import com.foya.noms.service.BaseService;
import com.foya.noms.util.LabelValueModel;

@Service
public class SiteService extends BaseService {
	@Autowired
	private ST002SP1Dao sT002SP1Dao;
	@Autowired
	private ST002Dao sT002Dao;
	/**
	 * 查詢設備型態
	 * @return
	 */
	public List<LabelValueModel> getEqpTypes(){
		TbComEqpTypeExample example = new TbComEqpTypeExample();
		List<TbComEqpType> eqpTypes = sT002SP1Dao.findEqpTypes(example);
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
		List<TbComSiteFeq> siteFeqs = sT002SP1Dao.findSiteFeqs(example);
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
		return sT002SP1Dao.findSiteFeqs(example);
	}
	/**
	 * 查詢設備型態
	 * @return
	 */
	public List<TbComEqpType> getEqpTypesByExample(TbComEqpTypeExample example){
		return sT002SP1Dao.findEqpTypes(example);
	}
	/**
	 * 查詢設備機型
	 * 
	 * @param example
	 * @return
	 */
	public List<TbComEqpModel> getEqpModelList(TbComEqpModelExample example) {
		return sT002Dao.findEqpModelList(example);
	}
	
	/**
	 * 查詢設備機型
	 * 
	 * @param example
	 * @return
	 */
	public List<TbComEqpModel> getEqpModelListByEqpTypeId(String eqpTypeId) {
		TbComEqpModelExample example = new TbComEqpModelExample();
		example.createCriteria().andEQP_TYPE_IDEqualTo(eqpTypeId);
		return sT002Dao.findEqpModelList(example);
	}
}
