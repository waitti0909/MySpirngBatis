package com.foya.noms.dao.st;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComEqpModelMapper;
import com.foya.dao.mybatis.mapper.TbComEqpTypeMapper;
import com.foya.dao.mybatis.mapper.TbComSiteFeqMapper;
import com.foya.dao.mybatis.mapper.TbSiteMainMapper;
import com.foya.dao.mybatis.mapper.UTbComCompanyMapper;
import com.foya.dao.mybatis.mapper.UTbSiteWorkMapper;
import com.foya.dao.mybatis.mapper.UTbSiteWorkOrderMapper;
import com.foya.dao.mybatis.mapper.UTbSysOrderTypeMapper;
import com.foya.dao.mybatis.model.TbComEqpType;
import com.foya.dao.mybatis.model.TbComEqpTypeExample;
import com.foya.dao.mybatis.model.TbComSiteFeq;
import com.foya.dao.mybatis.model.TbComSiteFeqExample;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.basic.CompanyDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class ST004Dao extends BaseDao {

	@Autowired
	private UTbSiteWorkMapper uTbSiteWorkMapper;
	
	@Autowired
	private TbComSiteFeqMapper tbComSiteFeqMapper;
	
	@Autowired
	private TbComEqpTypeMapper tbComEqpTypeMapper;
	
	@Autowired
	private UTbComCompanyMapper uTbComCompanyMapper;
	
	@Autowired
	private TbComEqpModelMapper tbComEqpModelMapper;
	
	@Autowired
	private TbSiteMainMapper tbSiteMainMapper;
	
	@Autowired
	private UTbSiteWorkOrderMapper uTbSiteWorkOrderMapper;
	
	@Autowired
	private UTbSysOrderTypeMapper uMapper;
	
	/**
	 * 用DeptId查詢siteWork
	 * @param deptIdList
	 * @return
	 */
	public List<SiteWorkDTO> findWorkListByDeptId(List<String> deptIdList){
		return uTbSiteWorkMapper.selectWorkListByDeptId(deptIdList);
	}
	
	/**
	 * 查詢基站頻段
	 * @param example
	 * @return
	 */
	public List<TbComSiteFeq> findSiteFeqs(TbComSiteFeqExample example){
		return tbComSiteFeqMapper.selectByExample(example);
	}
	
	/**
	 * 查詢設備型態
	 * @param example
	 * @return
	 */
	public List<TbComEqpType> findEqpTypes(TbComEqpTypeExample example){
		return tbComEqpTypeMapper.selectByExample(example);
	}
	
	/**
	 * 查詢全部siteWork
	 * @param map
	 * @return
	 */
	public List<SiteWorkDTO> findWorkList(Map<String ,Object> map){
		PageBounds pageBounds = getPageBounds();
		List<SiteWorkDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbSiteWorkMapper.selectWorkListBySiteBuild", map, pageBounds);
		PageList<SiteWorkDTO> pageList = (PageList<SiteWorkDTO>)list;
		return pageList;
	}
	
	/**
	 * 用workId查詢siteWork
	 * @return
	 */
	public SiteWorkDTO findSiteWorkByWorkId(String workId){
		return uTbSiteWorkMapper.selectSiteWorkDTOByWorkId(workId);
	}
	
	/**
	 * 查詢設備機型
	 * @param example
	 * @return
	 */
//	public List<TbComEqpModel> findEqpModelList(TbComEqpModelExample example){
//		return tbComEqpModelMapper.selectByExample(example);
//	}
	
	/**
	 *  查詢委外廠商
	 * @param opType
	 * @return
	 */
	public List<CompanyDTO> findOsVen(String opType){
		List<CompanyDTO> list=new ArrayList<CompanyDTO>();
//		if(opType.equals("P"))
		if("P".equals(opType))
		{
			list=this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbComCompanyMapper.selectTbComCompanybyGeneral");
		}
		else
		{
			list=this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbComCompanyMapper.selectTbComCompanybyOutsourcing");
		}
		return list;
	}
	
	/**
	 * 用TbSiteMainExample查詢site
	 * @param example
	 * @return
	 */
	public List<TbSiteMain> findWorkSiteListByExample(TbSiteMainExample example){
		return tbSiteMainMapper.selectByExample(example);
	}
	
	/**
	 * 用workId查詢SiteWorkOrder
	 * @param workId
	 * @return
	 */
	public List<SiteWorkOrderDTO> findSiteWorkOrderByWorkId(String workId){
		return uTbSiteWorkOrderMapper.selectSiteWorkOrderByWorkId(workId);
	}
	
	/**
	 * 用workTypeId查詢OrderType
	 * @param map
	 * @return
	 */
//	public List<OrderTypeDTO> findOrderTypeByWorkTypeId(Map<String ,String> map){
//		return uMapper.selectOrderTypeByBuild(map);
//	}
}
