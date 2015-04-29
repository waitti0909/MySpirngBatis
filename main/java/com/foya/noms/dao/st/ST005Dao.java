package com.foya.noms.dao.st;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteWorkMapper;
import com.foya.dao.mybatis.mapper.TbSiteWorkOrderMapper;
import com.foya.dao.mybatis.mapper.TbSysOrderPageMapper;
import com.foya.dao.mybatis.mapper.TbSysOrderTypeMapper;
import com.foya.dao.mybatis.mapper.UTbComCompanyMapper;
import com.foya.dao.mybatis.mapper.UTbSiteWorkOrderMapper;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSiteWorkOrderExample;
import com.foya.dao.mybatis.model.TbSysOrderPage;
import com.foya.dao.mybatis.model.TbSysOrderPageExample;
import com.foya.dao.mybatis.model.TbSysOrderType;
import com.foya.dao.mybatis.model.TbSysOrderTypeExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.basic.CompanyDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class ST005Dao extends BaseDao {

	@Autowired
	private UTbComCompanyMapper uTbComCompanyMapper;
	@Autowired
	private TbSiteWorkMapper tbSiteWorkMapper;
	@Autowired
	private TbSiteWorkOrderMapper tbSiteWorkOrderMapper;
	@Autowired
	private TbSysOrderPageMapper tbSysOrderPageMapper;
	@Autowired
	private TbSysOrderTypeMapper tbSysOrderTypeMapper;
	@Autowired
	private UTbSiteWorkOrderMapper uTbSiteWorkOrderMapper;
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
	 * 查詢全部SiteWorkOrder
	 * @param map
	 * @return
	 */
	public List<SiteWorkOrderDTO> findWorkOrderList(Map<String ,Object> map){
		PageBounds pageBounds = getPageBounds();
		List<SiteWorkOrderDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbSiteWorkOrderMapper.selectWorkOrderListForBuild", map, pageBounds);
		PageList<SiteWorkOrderDTO> pageList = (PageList<SiteWorkOrderDTO>)list;
		return pageList;
	}
	
	public List<TbSiteWorkOrder> getSiteWorkOrderByOrderId(
			TbSiteWorkOrderExample example) {
		return tbSiteWorkOrderMapper.selectByExample(example);
	}

	public int updateSiteWorkOrderSelective(TbSiteWorkOrder siteWorkOrder) {
		return tbSiteWorkOrderMapper.updateByPrimaryKeySelective(siteWorkOrder);
	}
	
	public int updateSiteWorkOrder(TbSiteWorkOrder record) {
		return tbSiteWorkOrderMapper.updateByPrimaryKey(record);
	}
	
	/**
	 * 由PK查詢工單.
	 *
	 * @param orderId the order id
	 * @return the tb site work order
	 */
	public TbSiteWorkOrder selectOrderByKey(String orderId){
		return tbSiteWorkOrderMapper.selectByPrimaryKey(orderId);
	}
	
	/**
	 * 由PK查詢作業
	 */
	public TbSiteWork selectWorkByKey(String workId) {
		return tbSiteWorkMapper.selectByPrimaryKey(workId);
	}
	
	/**
	 * Select by example.
	 *
	 * @param example the example
	 * @return the list
	 */
	public List<TbSysOrderPage> selectByExample(
			TbSysOrderPageExample example) {
		return tbSysOrderPageMapper.selectByExample(example);
	}

	public List<TbSysOrderType> getSysOrderTypeByOrderType(
			TbSysOrderTypeExample example) {
		return tbSysOrderTypeMapper.selectByExample(example);
	}
	
	/**
	 * 用workId和isActive查詢SiteWorkOrder
	 * @param workId
	 * @param isActive
	 * @return
	 */
	public List<SiteWorkOrderDTO> findSiteWorkOrderByWorkIdAndIsActive(String workId,String isActive){
		return uTbSiteWorkOrderMapper.selectSiteWorkOrderByWorkIdAndIsActive(workId, isActive);
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
}
