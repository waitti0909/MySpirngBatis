package com.foya.noms.dao.st.line;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComEqpTypeMapper;
import com.foya.dao.mybatis.mapper.TbSiteLineApplyMapper;
import com.foya.dao.mybatis.mapper.TbSiteLineSiteDataMapper;
import com.foya.dao.mybatis.mapper.TbSiteMainMapper;
import com.foya.dao.mybatis.mapper.TbSiteWorkMapper;
import com.foya.dao.mybatis.mapper.TbSiteWorkOrderMapper;
import com.foya.dao.mybatis.mapper.TbSysLookupMapper;
import com.foya.dao.mybatis.mapper.UTbSiteLineApplyMapper;
import com.foya.dao.mybatis.mapper.UTbSiteLocationMapper;
import com.foya.dao.mybatis.mapper.UTbSiteMainMapper;
import com.foya.dao.mybatis.model.TbComEqpType;
import com.foya.dao.mybatis.model.TbSiteLineApply;
import com.foya.dao.mybatis.model.TbSiteLineApplyExample;
import com.foya.dao.mybatis.model.TbSiteLineSiteData;
import com.foya.dao.mybatis.model.TbSiteLineSiteDataExample;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.dto.st.SiteLocationDTO;
import com.foya.noms.dto.st.line.SiteLineApplyDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class LineApplyDao extends BaseDao {

	@Autowired
	private TbSysLookupMapper tbSyslookupMapper;
	
	@Autowired
	private TbSiteMainMapper tbSiteMainMapper;
	
	@Autowired
	private UTbSiteLocationMapper uTbSiteLocationMapper;
	
	@Autowired
	private TbSiteLineSiteDataMapper tbSiteLineSiteDataMapper;
	
	@Autowired
	private UTbSiteLineApplyMapper uTbSiteLineApplyMapper; 
	
	@Autowired
	private TbSiteLineApplyMapper tbSiteLineApplyMapper;
	
	@Autowired
	private UTbSiteMainMapper uTbSiteMainMapper;
	
	@Autowired
	private TbSiteWorkOrderMapper tbSiteWorkOrderMapper;
	
	@Autowired
	private TbSiteWorkMapper	tbSiteWorkMapper;
	
	@Autowired
	private TbComEqpTypeMapper tbComEqpTypeMapper;
	
	public TbSiteLineApply findByPk(String appId) {
		return tbSiteLineApplyMapper.selectByPrimaryKey(appId);
	}
	
	public List<TbSiteLineApply> findByConditions(TbSiteLineApplyExample example) {
		return tbSiteLineApplyMapper.selectByExample(example);
	}
	
	public SiteLineApplyDTO selectLineNumber(TbSiteLineApply siteLineApply) {
		return uTbSiteLineApplyMapper.selectLineNumber(siteLineApply);
	}
	
	public List<SiteLineApplyDTO> selectLineApply(Map<String,String> map) {
		
		return uTbSiteLineApplyMapper.selectLineApply(map);
	}
	
	
	public int insert(TbSiteLineApply record) {
		return tbSiteLineApplyMapper.insertSelective(record);
	}
	
	
	public int update(TbSiteLineApply record) {
		return tbSiteLineApplyMapper.updateByPrimaryKeySelective(record);
	}
	
	public int update(TbSiteLineApply record, TbSiteLineApplyExample example) {
		return tbSiteLineApplyMapper.updateByExampleSelective(record, example);
	}
	
	public List<TbSysLookup> selectByExample(TbSysLookupExample example){
		return tbSyslookupMapper.selectByExample(example);
	}
	
	public SiteLocationDTO selectByExample(String LocationId){
		return uTbSiteLocationMapper.selectByPrimaryKey(LocationId);
	}
	
	public List<TbSiteMain> selectByExample(TbSiteMainExample example){
		return tbSiteMainMapper.selectByExample(example);
	}
	
	public List<TbSiteLineSiteData> findLineDataBySiteId(String siteId){
		TbSiteLineSiteDataExample example = new TbSiteLineSiteDataExample();
		example.createCriteria().andSITE_IDEqualTo(siteId);
		return tbSiteLineSiteDataMapper.selectByExample(example);
	}
	
	public int insert(TbSiteLineSiteData tbSiteLineSiteData){
		return tbSiteLineSiteDataMapper.insertSelective(tbSiteLineSiteData);
	}
	
	public int delete(TbSiteLineSiteDataExample example){
		return tbSiteLineSiteDataMapper.deleteByExample(example);
	}
	
	public TbSiteLineApply findLineApplyByPK(String appId){
		return tbSiteLineApplyMapper.selectByPrimaryKey(appId);
	}
	
	public List<SiteLineApplyDTO> findLineApplyListByDeptId(List<String> deptIdList){
		return uTbSiteLineApplyMapper.selectlineAppleListByDeptId(deptIdList);
	}
	
	public List<SiteLineApplyDTO> findByConditions(Map<String, String> map){
		PageBounds pageBounds = getPageBounds();
		List<SiteLineApplyDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbSiteLineApplyMapper.selectByConditions", map, pageBounds);
		PageList<SiteLineApplyDTO> pageList = (PageList<SiteLineApplyDTO>)list;
		return pageList;
	}
	
	public List<SiteLineApplyDTO> findBySelectLine(Map<String, String> map){
		PageBounds pageBounds = getPageBounds();
		List<SiteLineApplyDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbSiteLineApplyMapper.selectLine", map, pageBounds);
		return list;
	}
	
	
	public SiteLineApplyDTO findLineApplyByAppId(String appId){
		return uTbSiteLineApplyMapper.selectLineApplyByAppId(appId);
	}
	
	public SiteLineApplyDTO findLineApplyByExcel(String appId){
		return uTbSiteLineApplyMapper.selectLineApplyByExcel(appId);
	}
	
	public SiteLineApplyDTO selectByExcelExport(String appId){
		return uTbSiteLineApplyMapper.selectByExcelExport(appId);
	}
	
	public List<SiteDTO> selectSiteMainByLocid(String siteId)
	{
		return uTbSiteMainMapper.selectSiteMainByLocid(siteId);
	}
	
	
	public TbSiteWorkOrder selectSiteWorkOrderByPrimaryKey(String orderId){
		return tbSiteWorkOrderMapper.selectByPrimaryKey(orderId);
	}
	
	public TbSiteWork selectSiteWorkByPrimaryKey(String workId){
		return tbSiteWorkMapper.selectByPrimaryKey(workId);
	}

	public TbComEqpType selectTbComEqpTypeByPrimaryKey(String eqpTypeId){
		return tbComEqpTypeMapper.selectByPrimaryKey(eqpTypeId);
	}
}
