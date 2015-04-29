package com.foya.noms.dao.st;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComEqpModelMapper;
import com.foya.dao.mybatis.mapper.TbOrgDeptMapper;
import com.foya.dao.mybatis.mapper.TbSiteWorkMapper;
import com.foya.dao.mybatis.mapper.TbSiteWorkOrderMapper;
import com.foya.dao.mybatis.mapper.UTbComCompanyMapper;
import com.foya.dao.mybatis.mapper.UTbOrgDeptMapper;
import com.foya.dao.mybatis.mapper.UTbSiteWorkMapper;
import com.foya.dao.mybatis.mapper.UTbSiteWorkOrderMapper;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.basic.CompanyDTO;
import com.foya.noms.dto.org.DeptDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class ST010Dao extends BaseDao{
	
	@Autowired
	private UTbSiteWorkMapper uTbSiteWorkMapper;
	@Autowired
	private TbSiteWorkMapper tbSiteWorkMapper;
	@Autowired
	private TbComEqpModelMapper tbComEqpModelMapper;
	@Autowired
	private UTbSiteWorkOrderMapper uTbSiteWorkOrderMapper;
	@Autowired
	private TbSiteWorkOrderMapper tbSiteWorkOrderMapper;
	@Autowired
	private UTbOrgDeptMapper uTbOrgDeptMapper;
	@Autowired
	private UTbComCompanyMapper uTbComCompanyMapper;
	@Autowired
	private TbOrgDeptMapper tbOrgDeptMapper;
	
	
	
	/**
	 * 查詢全部SiteWorkOrder
	 * @param map
	 * @return
	 */
	public List<SiteWorkOrderDTO> findWorkOrderList(Map<String ,Object> map){
		PageBounds pageBounds = getPageBounds();
		List<SiteWorkOrderDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbSiteWorkOrderMapper.selectWorkOrderListSgl", map, pageBounds);
		PageList<SiteWorkOrderDTO> pageList = (PageList<SiteWorkOrderDTO>)list;
		return pageList;
	}
	
	/**
	 * 查詢接工單位
	 * @param deptIdList
	 * @return
	 */
	public List<String> findDeptAll(){
		return uTbSiteWorkOrderMapper.selectWorkOrderDept();
	}
	
	/**
	 * 查詢負責單位
	 * @param deptIdList
	 * @return
	 */
	public List<String> findTeamAll(){
		return uTbSiteWorkOrderMapper.selectWorkOrderTeam();
	}
	
	/**
	 * 查詢TbOrgDept
	 * @param deptIdList
	 * @return
	 */
	public List<CompanyDTO> findCompany(String coVatNo){
		return uTbComCompanyMapper.selectCompanyList(coVatNo);
	}
	
	/**
	 * 查詢TbOrgDept
	 * @param deptIdList
	 * @return
	 */
	public List<CompanyDTO> findCompanyAll(){
		return uTbComCompanyMapper.selectCompanyAllList();
	}
	
	/**
	 * 用workId查詢siteWork
	 * @return
	 */
	public SiteWorkDTO findSiteWorkByWorkId(String workId){
		return uTbSiteWorkMapper.selectSiteWorkByWorkId(workId);
	}
	
	/**
	 * 用deptID查詢Hierarchy
	 * @return
	 */
	public DeptDTO searchDeptById(String repDept){
		return  uTbOrgDeptMapper.searchDeptById(repDept);
	}
	
	/**
	 * 已派工
	 * @return
	 */
	public int updateAssignOrder(Map<String, Object> map){
		return uTbSiteWorkOrderMapper.updateAssignOrder(map);
	}	
	
	/**
	 * 已接工
	 * @return
	 */
	public int updatePickWork(Map<String, String> map){
		return uTbSiteWorkOrderMapper.updatePickWork(map);
	}
	
	/**
	 * 已接工(儲存)
	 * @param record 
	 * @return
	 */
	public int updateSave(TbSiteWorkOrder record){
		return tbSiteWorkOrderMapper.updateByPrimaryKeySelective(record);
//		return uTbSiteWorkOrderMapper.updateSave(map);
	}
	
	/**
	 * 更新工單
	 * @param record
	 * @return
	 */
	public int updateByPrimaryKeySelective(TbSiteWorkOrder tbSiteWorkOrder){
		return tbSiteWorkOrderMapper.updateByPrimaryKeySelective(tbSiteWorkOrder);
	}

	public TbSiteWorkOrder selectByPrimaryKey(String orderId) {
		// TODO Auto-generated method stub
		return tbSiteWorkOrderMapper.selectByPrimaryKey(orderId);
	}

	public TbOrgDept selectOrgDept(String rep_DEPT) {
		// TODO Auto-generated method stub
		return tbOrgDeptMapper.selectByPrimaryKey(rep_DEPT);
	}

}
