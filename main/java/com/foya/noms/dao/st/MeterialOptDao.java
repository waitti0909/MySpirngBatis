package com.foya.noms.dao.st;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComTownDomainTeamMapper;
import com.foya.dao.mybatis.mapper.TbInvBookingMapper;
import com.foya.dao.mybatis.mapper.TbInvInsRemDMapper;
import com.foya.dao.mybatis.mapper.TbInvInvMapper;
import com.foya.dao.mybatis.mapper.TbInvMaterialOptMapper;
import com.foya.dao.mybatis.mapper.TbInvMrDMapper;
import com.foya.dao.mybatis.mapper.TbInvRtDMapper;
import com.foya.dao.mybatis.mapper.TbInvWarehouseMapper;
import com.foya.dao.mybatis.mapper.TbOrgDeptMapper;
import com.foya.dao.mybatis.mapper.TbSiteLocationMapper;
import com.foya.dao.mybatis.mapper.TbSiteMainMapper;
import com.foya.dao.mybatis.mapper.TbSiteOutsourcingMapper;
import com.foya.dao.mybatis.mapper.TbSiteWorkMapper;
import com.foya.dao.mybatis.mapper.TbSiteWorkOrderMapper;
import com.foya.dao.mybatis.mapper.TbSysLookupMapper;
import com.foya.dao.mybatis.mapper.UTbInvInsRemDMapper;
import com.foya.dao.mybatis.mapper.UTbInvInvMapper;
import com.foya.dao.mybatis.mapper.UTbInvMaterialOptMapper;
import com.foya.dao.mybatis.mapper.UTbInvMrDMapper;
import com.foya.dao.mybatis.mapper.UTbInvRtDMapper;
import com.foya.dao.mybatis.model.TbComTownDomainTeam;
import com.foya.dao.mybatis.model.TbComTownDomainTeamExample;
import com.foya.dao.mybatis.model.TbInvBooking;
import com.foya.dao.mybatis.model.TbInvBookingExample;
import com.foya.dao.mybatis.model.TbInvInsRemD;
import com.foya.dao.mybatis.model.TbInvInv;
import com.foya.dao.mybatis.model.TbInvInvExample;
import com.foya.dao.mybatis.model.TbInvMaterialOpt;
import com.foya.dao.mybatis.model.TbInvMaterialOptExample;
import com.foya.dao.mybatis.model.TbInvMrD;
import com.foya.dao.mybatis.model.TbInvMrDExample;
import com.foya.dao.mybatis.model.TbInvRtD;
import com.foya.dao.mybatis.model.TbInvWarehouse;
import com.foya.dao.mybatis.model.TbInvWarehouseExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvMaterialOptDTO;
import com.foya.noms.dto.st.MeterialRtnDTO;
import com.foya.noms.dto.st.SiteMtDetailImportDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Repository
public class MeterialOptDao extends BaseDao {

	@Autowired
	private TbInvMaterialOptMapper tbInvMaterialOptMapper;
	
	@Autowired
	private UTbInvMaterialOptMapper uTbInvMaterialOptMapper;
	
	@Autowired
	private UTbInvMrDMapper uTbInvMrDMapper;
	
	@Autowired
	private TbSiteWorkOrderMapper tbSiteWorkOrderMapper;
	
	@Autowired
	private TbSiteWorkMapper tbSiteWorkMapper;
	
	@Autowired
	private TbSiteMainMapper tbSiteMainMapper;
	
	@Autowired
	private TbComTownDomainTeamMapper tbComTownDomainTeamMapper;
	
	@Autowired
	private UTbInvRtDMapper uTbInvRtDMapper;
	
	@Autowired
	private TbSiteLocationMapper tbSiteLocationMapper;
	
	@Autowired
	private TbInvWarehouseMapper tbInvWarehouseMapper;
	
	@Autowired
	private TbInvInvMapper tbInvInvMapper;
	
	@Autowired
	private TbInvMrDMapper tbInvMrDMapper;
	
	@Autowired
	private TbInvBookingMapper tbInvBookingMapper;
	
	@Autowired
	private TbSysLookupMapper tbSysLookupMapper;
	
	@Autowired
	private UTbInvInvMapper uTbInvInvMapper;
	
	@Autowired
	private TbInvRtDMapper tbInvRtDMapper;
	
	@Autowired
	private TbOrgDeptMapper tbOrgDeptMapper;
	
	@Autowired
	private UTbInvInsRemDMapper uTbInvInsRemDMapper;
	
	@Autowired
	private TbInvInsRemDMapper tbInvInsRemDMapper;
	
	@Autowired
	private TbSiteOutsourcingMapper tbSiteOutsourcingMapper;
	
	/**
	 * 查詢物料操作檔資料
	 * @param example
	 * @return
	 */
	public List<TbInvMaterialOpt> findInvMaterialOptByOrderId(TbInvMaterialOptExample example){
		return tbInvMaterialOptMapper.selectByExample(example);
	}
	
	public List<TbInvMaterialOptDTO> selectMaterialOptByCondistions(TbInvMaterialOptDTO tbInvMaterialOptDTO){
		return uTbInvMaterialOptMapper.selectMaterialOptByCondistions(tbInvMaterialOptDTO);
	}
	
	public List<TbInvMaterialOptDTO> selectForExportMaterialExeclTitle(TbInvMaterialOptDTO tbInvMaterialOptDTO){
		return uTbInvMaterialOptMapper.selectForExportMaterialExeclTitle(tbInvMaterialOptDTO);
	}
	
	public TbInvMaterialOpt findByPk(String optId) {
		return tbInvMaterialOptMapper.selectByPrimaryKey(optId);
	}
	
	public List<TbInvMrD> selectItemsByExample(TbInvMrDExample example) {
		return tbInvMrDMapper.selectByExample(example);
	}

	public List <SiteMtDetailImportDTO> selectItemMainById(Map<String, String> map){
		return uTbInvMrDMapper.selectItemQuery(map);
	}
	
	public List <MeterialRtnDTO> selectRemInsItemQuery(HashMap<String, Object> map){
		return uTbInvInsRemDMapper.selectRemInsItemQuery(map);
	}
	
	public List <MeterialRtnDTO> selectRemItemQuery(HashMap<String, Object> map){
		return uTbInvInsRemDMapper.selectRemItemQuery(map);
	}
	
	public List <MeterialRtnDTO> selectRemInsItemByOptId(HashMap<String, Object> map){
		return uTbInvInsRemDMapper.selectRemInsItemByOptId(map);
	}
	
	public List <MeterialRtnDTO> selectRemItemByOptId(HashMap<String, Object> map){
		return uTbInvInsRemDMapper.selectRemItemByOptId(map);
	}
	
	public List <MeterialRtnDTO> selectRtntrItemByOptId(HashMap<String, Object> map){
		return uTbInvRtDMapper.selectRtntrItemByOptId(map);
	}
	
	public TbSiteWorkOrder selectWorkorder(String  orderId){
		return  tbSiteWorkOrderMapper.selectByPrimaryKey(orderId);
	}
	
	public TbSiteWork selectSiteWork(String  workId){
		return  tbSiteWorkMapper.selectByPrimaryKey(workId);
	}
	
	public TbSiteMain selectSiteMain(String  siteId){
		return  tbSiteMainMapper.selectByPrimaryKey(siteId);
	}
	
	public TbSiteLocation selectSiteLocation(String  locationId){
		return  tbSiteLocationMapper.selectByPrimaryKey(locationId);
	}
	
	public List<TbComTownDomainTeam> selectTbComTownDomainTeam(TbComTownDomainTeamExample example){
		return  tbComTownDomainTeamMapper.selectByExample(example);
	}
	
	public List<TbInvWarehouse> selectWarehouse(TbInvWarehouseExample example){
		return  tbInvWarehouseMapper.selectByExample(example);
	}
	
	public TbInvWarehouse selectWarehouseByPrimaryKey(String whId){
		return  tbInvWarehouseMapper.selectByPrimaryKey(whId);
	}
	
	public int insert(TbInvMaterialOpt tbInvMaterialOpt){
		return  tbInvMaterialOptMapper.insertSelective(tbInvMaterialOpt);
	}
	
	public int insert(TbInvInsRemD tbInvInsRemD){
		return  tbInvInsRemDMapper.insertSelective(tbInvInsRemD);
	}
	
	public List<SiteMtDetailImportDTO> selectMeterialApplyExcel(Map<String,String> dataObj){
		return uTbInvMrDMapper.searchMeterialApplyExcel(dataObj);
	}
	
	public List<TbInvInv> selectInv(TbInvInvExample example){
		return tbInvInvMapper.selectByExample(example);
	}
	
	public List<TbInvBooking> selectBooking(TbInvBookingExample example){
		return tbInvBookingMapper.selectByExample(example);
	}
	
	public int insert(TbInvMrD tbInvMrD){
		return  tbInvMrDMapper.insertSelective(tbInvMrD);
	}
	public int insert(TbInvBooking tbInvBooking){
		return  tbInvBookingMapper.insertSelective(tbInvBooking);
	}
	public int delete(TbInvBookingExample example) {
		return tbInvBookingMapper.deleteByExample(example);
	}
	
	public List<TbSysLookup> selectMeterStatus(TbSysLookupExample example){
		return tbSysLookupMapper.selectByExample(example);
	}
	
	public int updateAppInv(HashMap<String,Object> data){
		return  uTbInvInvMapper.updateByMeterialOptAppQty(data);
	}
	
	public int updateRejInv(HashMap<String,Object> data){
		return  uTbInvInvMapper.updateByMeterialOptRejQty(data);
	}
	
	public int insert(TbInvRtD tbInvRtD){
		return  tbInvRtDMapper.insertSelective(tbInvRtD);
	}
	
	public TbOrgDept  selectDeptById(String deptId){
		return tbOrgDeptMapper.selectByPrimaryKey(deptId);
	}
	
	/**
	 * 工單物料轉出查詢
	 * @param map
	 * @return
	 */
	public List<TbInvMaterialOptDTO> selectMaterialTransferByConditions(Map<String, Object> map){
		PageBounds pageBounds = getPageBounds("ORDER_ID.ASC,CR_TIME.DESC");
		List<TbInvMaterialOptDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvMaterialOptMapper.selectMaterialTransferBySiteWork", map, pageBounds);
		return list;
	}
	
	public List<TbInvMaterialOpt> selectByConditions(TbInvMaterialOptExample example){
		return tbInvMaterialOptMapper.selectByExample(example);
	}
	
	
	public int updateByPrimaryKeySelective(TbInvMaterialOpt record){
		return tbInvMaterialOptMapper.updateByPrimaryKeySelective(record);
	}
	
	public int updateByPrimaryKeySelective(TbInvMrDExample example){
		return tbInvMrDMapper.deleteByExample(example);
	}
	
	public TbSiteOutsourcing selectByPrimaryKey(String osId){
		return tbSiteOutsourcingMapper.selectByPrimaryKey(osId);
	}
	
	public int  deleteByExample(TbInvMrDExample example){
		return tbInvMrDMapper.deleteByExample(example);
	}
	
	public List<TbInvMaterialOptDTO> selectTroDetail(String optId){
		return uTbInvMaterialOptMapper.selectTroDetail(optId);
	}
	
	public TbInvMaterialOptDTO selectTroHistory(String optId){
		return uTbInvMaterialOptMapper.selectTroHistory(optId);
	}
	
	// 找出本次工單安裝料件於站上的數量 add by Charlie 20150331
	public Integer findInsItemOnSiteForOrder(String orderId) {
		return uTbInvMaterialOptMapper.selectInsItemOnSiteForOrder(orderId);
	}
}
