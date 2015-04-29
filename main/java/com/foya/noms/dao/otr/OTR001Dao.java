package com.foya.noms.dao.otr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbInvMrDMapper;
import com.foya.dao.mybatis.mapper.TbInvRtDMapper;
import com.foya.dao.mybatis.mapper.TbInvTroDMapper;
import com.foya.dao.mybatis.mapper.TbInvWarehouseMapper;
import com.foya.dao.mybatis.mapper.TbSiteOutsourcingMapper;
import com.foya.dao.mybatis.mapper.UTbInvInsRemDMapper;
import com.foya.dao.mybatis.mapper.UTbSiteLineApplyMapper;
import com.foya.dao.mybatis.mapper.UTbSiteWorkMapper;
import com.foya.dao.mybatis.mapper.UTbSiteWorkOrderMapper;
import com.foya.dao.mybatis.model.TbInvMrD;
import com.foya.dao.mybatis.model.TbInvMrDExample;
import com.foya.dao.mybatis.model.TbInvRtD;
import com.foya.dao.mybatis.model.TbInvRtDExample;
import com.foya.dao.mybatis.model.TbInvTroD;
import com.foya.dao.mybatis.model.TbInvWarehouse;
import com.foya.dao.mybatis.model.TbInvWarehouseExample;
import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.dao.mybatis.model.TbSiteOutsourcingExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.st.MeterialRtnDTO;
import com.foya.noms.dto.st.SiteWorkDTO;
import com.foya.noms.dto.st.SiteWorkOrderDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Repository
public class OTR001Dao  extends BaseDao {
    
	@Autowired
	private UTbSiteWorkOrderMapper uTbSiteWorkOrderMapper;
	
	@Autowired
	private UTbSiteWorkMapper uTbSiteWorkMapper;
	
	@Autowired
	private TbSiteOutsourcingMapper tbSiteOutsourcingMapper;
	
	@Autowired
	private TbInvWarehouseMapper tbInvWarehouseMapper;
	
	@Autowired
	private UTbInvInsRemDMapper uTbInvInsRemDMapper;
	
	@Autowired
	private UTbSiteLineApplyMapper uTbSiteLineApplyMapper;
	
	
	
	@Autowired
	private TbInvTroDMapper tbInvTroDMapper;
	
	@Autowired
	private TbInvRtDMapper tbInvRtDMapper;
	
	@Autowired
	private TbInvMrDMapper tbInvMrDMapper;
	
	/**
	 * 接工單位、負責單位
	 * @param deptList
	 * @return
	 */
	public List<SiteWorkOrderDTO> selectWorkOrderDeptInUserDept(List<String> deptList){
		return uTbSiteWorkOrderMapper.selectWorkOrderDeptInUserDept(deptList);
	}
	
	public List<SiteWorkDTO> selectSiteWorkByConditions(Map<String, Object> map){
		PageBounds pageBounds = getPageBounds();
		List<SiteWorkDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbSiteWorkMapper.selectSiteWorkByConditions", map, pageBounds);
		return list;
	}
	
	public List<TbSiteOutsourcing> selectByExample(TbSiteOutsourcingExample example){
		return tbSiteOutsourcingMapper.selectByExample(example);
	}
	
	public List<TbInvWarehouse> selectByExample(TbInvWarehouseExample example){
		return tbInvWarehouseMapper.selectByExample(example);
	}
	
	public List<MeterialRtnDTO> selectRemInsItemQuery(HashMap<String,Object> map){
		return uTbInvInsRemDMapper.selectRemInsItemQuery(map);
	}
	
	public int insert(TbInvTroD record){
		return tbInvTroDMapper.insertSelective(record);
	}
	
	public int insert(TbInvRtD record){
		return tbInvRtDMapper.insertSelective(record);
	}
	
	public int insert(TbInvMrD record){
		return tbInvMrDMapper.insertSelective(record);
	}
	
	public List<TbInvRtD> selectTbinvRtDByCondistions (TbInvRtDExample example){
		return tbInvRtDMapper.selectByExample(example);
	}
	
	public List<TbInvMrD> selectTbInvMrDByCondistions(TbInvMrDExample example){
		return tbInvMrDMapper.selectByExample(example);
	}
	
}
