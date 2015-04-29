package com.foya.noms.dao.st;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComEqpModelMapper;
import com.foya.dao.mybatis.mapper.TbSiteWorkMapper;
import com.foya.dao.mybatis.mapper.TbSiteWorkOrderMapper;
import com.foya.dao.mybatis.mapper.UTbSiteWorkMapper;
import com.foya.dao.mybatis.mapper.UTbSiteWorkOrderMapper;
import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpModelExample;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class ST002Dao extends BaseDao {
	@Autowired
	private UTbSiteWorkMapper uTbSiteWorkMapper;
	@Autowired
	private TbSiteWorkMapper tbSiteWorkMapper;
	@Autowired
	private TbComEqpModelMapper tbComEqpModelMapper;
	@Autowired
	private TbSiteWorkOrderMapper tbSiteWorkOrderMapper;
	@Autowired
	private UTbSiteWorkOrderMapper uTbSiteWorkOrderMapper;
	/**
	 * 查詢全部siteWork
	 * @param map
	 * @return
	 */
	public List<SiteWorkDTO> findWorkList(Map<String ,Object> map){
		PageBounds pageBounds = getPageBounds();
		List<SiteWorkDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbSiteWorkMapper.selectWorkList", map, pageBounds);
		PageList<SiteWorkDTO> pageList = (PageList<SiteWorkDTO>)list;
		return pageList;
	}
	
	/**
	 * 查詢設備機型
	 * @param example
	 * @return
	 */
	public List<TbComEqpModel> findEqpModelList(TbComEqpModelExample example){
		return tbComEqpModelMapper.selectByExample(example);
	}
	
	/**
	 * 用DeptId查詢siteWork
	 * @param deptIdList
	 * @return
	 */
	public List<SiteWorkDTO> findWorkListByDeptId(List<String> deptIdList){
		return uTbSiteWorkMapper.selectWorkListByDeptId(deptIdList);
	}
	
	/**
	 * 用workId查詢siteWork
	 * @return
	 */
	public SiteWorkDTO findSiteWorkByWorkId(String workId){
		return uTbSiteWorkMapper.selectSiteWorkByWorkId(workId);
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
	 * 用orderId查工單 
	 * @param orderId
	 * @return
	 */
	public TbSiteWorkOrder selectSiteWorkOrderByOrderId(String orderId){
//		return uTbSiteWorkOrderMapper.selectSiteWorkOrderByOrderId(orderId);
		return tbSiteWorkOrderMapper.selectByPrimaryKey(orderId);
	}
	
}
