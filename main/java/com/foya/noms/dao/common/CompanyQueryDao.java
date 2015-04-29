package com.foya.noms.dao.common;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComCompanyMapper;
import com.foya.dao.mybatis.mapper.UTbComCompanyMapper;
import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.basic.CompanyDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class CompanyQueryDao extends BaseDao {
	
	@Autowired
	private UTbComCompanyMapper uTbComCompanyMapper;
	@Autowired
	private TbComCompanyMapper tbComCompanyMapper;
	/**
	 * 查詢
	 * @param map
	 * @return
	 */
	public List<CompanyDTO> findCompanyList(Map<String,String> map) {
		PageBounds pageBounds = getPageBounds();
		List<CompanyDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbComCompanyMapper.selectCompanyByCondition", map, pageBounds);
		PageList<CompanyDTO> pageList = (PageList<CompanyDTO>)list;
		return pageList;
	}
	
	public TbComCompany findByCoVatNo(String coVatNo) {
		return tbComCompanyMapper.selectByPrimaryKey(coVatNo);
	}
}
