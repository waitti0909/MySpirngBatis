package com.foya.noms.service.system;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.dao.mybatis.model.TbSysLookupExample.Criteria;
import com.foya.noms.dao.system.LookupDao;
import com.foya.noms.service.BaseService;
import com.foya.noms.util.LabelValueModel;

@Service
public class LookupService extends BaseService {

	@Autowired
	private LookupDao dao;

	/**
	 * 查詢檔案分類
	 * 
	 * @param type
	 * @param typePath
	 * @return
	 */
	public Map<String, String> getFileTypeKeyValues(String type, String typePath) {
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo(type).andVALUE2Like("%" + typePath + "%")
				.andDISPLAY_FLAGEqualTo("Y");
		example.setOrderByClause("DISPLAY_ORDER ASC");
		List<TbSysLookup> rows = dao.selectByExample(example);

		Map<String, String> keyValue = new LinkedHashMap<String, String>();
		for (TbSysLookup model : rows) {
			keyValue.put(model.getCODE(), model.getNAME());
		}
		return keyValue;
	}

	public List<LabelValueModel> getLabelValueList(String type) {
		List<LabelValueModel> labelList = new LinkedList<LabelValueModel>();
		TbSysLookupExample example = new TbSysLookupExample();
		example.createCriteria().andTYPEEqualTo(type);
		example.setOrderByClause("DISPLAY_ORDER ASC");
		List<TbSysLookup> rows = dao.selectByExample(example);
		for (TbSysLookup row : rows) {
			labelList.add(new LabelValueModel(row.getNAME(), row.getCODE()));
		}
		return labelList;
	}

	public TbSysLookup getByTypeAndCode(String type, String code) {
		TbSysLookupExample example = new TbSysLookupExample();
		Criteria criteria = example.createCriteria();
		criteria.andTYPEEqualTo(type);
		if (null != code && code.length() > 0) {

			criteria.andCODEEqualTo(code);
		}

		List<TbSysLookup> rows = dao.selectByExample(example);
		if (!rows.isEmpty()) {
			return rows.get(0);
		}
		return null;
	}

	public List<TbSysLookup> getByType(String type) {
		TbSysLookupExample example = new TbSysLookupExample();
		Criteria criteria = example.createCriteria();
		criteria.andTYPEEqualTo(type);

		List<TbSysLookup> rows = dao.selectByExample(example);

		return rows;
	}
}
