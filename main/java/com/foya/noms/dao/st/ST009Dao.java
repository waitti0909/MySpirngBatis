package com.foya.noms.dao.st;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComSiteFeqMapper;
import com.foya.dao.mybatis.mapper.TbOrgDeptMapper;
import com.foya.dao.mybatis.mapper.TbSiteWorkMapper;
import com.foya.dao.mybatis.mapper.TbSysOrderTypeMapper;
import com.foya.dao.mybatis.mapper.UTbSiteWorkMapper;
import com.foya.dao.mybatis.model.TbComSiteFeq;
import com.foya.dao.mybatis.model.TbComSiteFeqExample;
import com.foya.dao.mybatis.model.TbSysOrderType;
import com.foya.dao.mybatis.model.TbSysOrderTypeExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class ST009Dao extends BaseDao{
	
	@Autowired
	private UTbSiteWorkMapper uTbSiteWorkMapper;
	@Autowired
	private TbSiteWorkMapper tbSiteWorkMapper;
	@Autowired
	private TbOrgDeptMapper tbOrgDeptMapper;
	@Autowired
	private TbComSiteFeqMapper tbComSiteFeqMapper;
	@Autowired
	private TbSysOrderTypeMapper tbSysOrderTypeMapper;
	
	
	/**
	 * 查詢接工單位
	 * @param deptIdList
	 * @return
	 */
	public List<SiteWorkDTO> findDeptAll(){
		return uTbSiteWorkMapper.selectWorkAppDept();
	}
	
	/**
	 * 查詢基站頻段
	 * @param deptIdList
	 * @return
	 */
	public List<TbComSiteFeq> findComSiteFeqAll(TbComSiteFeqExample example){
		return tbComSiteFeqMapper.selectByExample(example);
	}
	
	/**
	 * 查詢工單類別
	 * @param 
	 * @return
	 */
	public List<TbSysOrderType> findSysOrderTypeAll(TbSysOrderTypeExample example){
		return tbSysOrderTypeMapper.selectByExample(example);
	}
	
	/**
	 * 查詢 單張工單
	 * @return
	 */
	public List<SiteWorkDTO> findWorkSgnAll(Map<String ,Object> map){
		PageBounds pageBounds = getPageBounds();
		List<SiteWorkDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbSiteWorkMapper.selectWorkSgnList", map, pageBounds);
		PageList<SiteWorkDTO> pageList = (PageList<SiteWorkDTO>)list;
		return pageList;
	}
}
