package com.foya.noms.dao.st;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteOsItemMapper;
import com.foya.dao.mybatis.model.TbSiteOsItem;
import com.foya.dao.mybatis.model.TbSiteOsItemKey;
import com.foya.noms.dao.BaseDao;

@Repository
public class OsItemDao extends BaseDao {

	@Autowired
	private TbSiteOsItemMapper mapper;
	
	public TbSiteOsItem findByKey(TbSiteOsItemKey key) {
		return mapper.selectByPrimaryKey(key);
	}
	
	public int insert(TbSiteOsItem record) {
		return mapper.insertSelective(record);
	}
	
	/**
	 * 由主鍵更新委外工項.
	 * 
	 * @param tbSiteOsItem
	 *            the tb site os item
	 * @return the int
	 */
	public int updateByPrimaryKeySelective(TbSiteOsItem tbSiteOsItem) {
		return mapper.updateByPrimaryKeySelective(tbSiteOsItem);
	}
}
