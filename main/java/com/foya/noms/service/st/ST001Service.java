package com.foya.noms.service.st;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbComTown;
import com.foya.dao.mybatis.model.TbComTownExample;
import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteShareExample;
import com.foya.dao.mybatis.model.TbSiteShareKey;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkExample;
import com.foya.exception.CreateFailException;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dao.common.TownDao;
import com.foya.noms.dao.common.TownDomainTeamDao;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.org.DeptPosDao;
import com.foya.noms.dao.st.LocationDao;
import com.foya.noms.dao.st.ST001Dao;
import com.foya.noms.dao.st.SiteMainDao;
import com.foya.noms.dao.st.SiteShareDao;
import com.foya.noms.dao.st.WorkDao;
import com.foya.noms.dto.common.TownDomainTeamDTO;
import com.foya.noms.dto.org.DeptPosDTO;
import com.foya.noms.dto.st.SiteLocationDTO;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.system.UniqueSeqService;

@Service
public class ST001Service extends BaseService {

	@Autowired
	private ST001Dao st001Dao;
	@Autowired
	private LocationDao locationDao;
	@Autowired
	private TownDomainTeamDao townDomainTeamDao;
	@Autowired
	private DeptDao deptDao;
	@Autowired
	private UniqueSeqService uniqueSeqService;
	@Autowired
	private TownDao townDao;
	@Autowired
	private DeptPosDao deptPosDao;
	@Autowired
	private SiteMainDao siteMainDao;
	@Autowired
	private ST002Service sT002Service;
	@Autowired
	private SiteShareDao siteShareDao;
	@Autowired
	private WorkDao workDao;
	/**
	 * 查詢siteLocation
	 * 
	 * @param map
	 * @return
	 */
	public List<SiteLocationDTO> getByCondition(Map<String, String> map) {
		return st001Dao.findByCondition(map);
	}

	/**
	 * 用locationId查詢siteLocation
	 * 
	 * @param locationId
	 * @return
	 */
	public TbSiteLocation getSiteLocationByLocationId(String locationId) {
		// return st001Dao.findSiteLocationByLocationId(locationId);
		return locationDao.findByPk(locationId);
	}

	/**
	 * 用cityId,townId查詢MaintainArea
	 * 
	 * @param cityId
	 * @param townId
	 * @return
	 */
	public Map<String, String> getMaintainAreaByCityIdTownId(String cityId, String townId) {
		TownDomainTeamDTO team = townDomainTeamDao.getTownDomainTeamByCityIdTownId(cityId, townId);
		Map<String, String> map = new LinkedHashMap<>();
		if (team != null) map.put(team.getDOMAIN(), team.getDomainName());
		return map;
	}

	/**
	 * 用domain查詢MaintainDept
	 * 
	 * @param domain
	 * @return
	 */
	public Map<String, String> getMaintainDeptByDomain(String domain) {
		Map<String, String> map = new LinkedHashMap<>();
		TbOrgDeptExample example = new TbOrgDeptExample();
		example.createCriteria().andDOMAINLike(domain + "%").andDEPT_LEVELEqualTo("DP");
		List<TbOrgDept> deptList = deptDao.selectAllOrgDept(example);
		for (TbOrgDept dept : deptList) {
			map.put(dept.getDEPT_ID(), dept.getDEPT_NAME());
		}
		return map;
	}

	/**
	 * 用deptId查詢MaintainTeam
	 * 
	 * @param deptId
	 * @return
	 */
	public Map<String, String> getMaintainTeamByDeptId(String deptId) {
		Map<String, String> map = new LinkedHashMap<>();
		TbOrgDept dept = deptDao.findByPk(deptId);

		TbOrgDeptExample example = new TbOrgDeptExample();
		example.createCriteria().andHIERARCHYLike(dept.getHIERARCHY() + "%")
				.andDEPT_LEVELEqualTo("TM");
		List<TbOrgDept> deptList = deptDao.selectAllOrgDept(example);
		for (TbOrgDept unit : deptList) {
			map.put(unit.getDEPT_ID(), unit.getDEPT_NAME());
		}
		return map;
	}

	/**
	 * 用deptId查詢該部門下所有員工
	 * 
	 * @param deptId
	 * @return
	 */
	public Map<String, String> getMaintainUserByDeptId(String deptId) {
		Map<String, String> map = new LinkedHashMap<>();
		List<DeptPosDTO> deptPosList = deptPosDao.findMaintainUserByDeptId(deptId);
		if (deptPosList.size() != 0) {
			for (DeptPosDTO deptPos : deptPosList) {
				map.put(deptPos.getPsnNo(), deptPos.getChnName());
			}
		}
		return map;
	}

	/**
	 * 新增siteLocation
	 * 
	 * @param recprd
	 * @return
	 */
	@Transactional
	public TbSiteLocation insert(TbSiteLocation record, String[] shareComArray, String user)
			throws CreateFailException {
		Date date = new Date();
		TbComTownExample example = new TbComTownExample();
		example.createCriteria().andCITY_IDEqualTo(record.getCITY())
				.andTOWN_IDEqualTo(record.getAREA());
		List<TbComTown> townList = townDao.findTownByCondition(example);
		String locationId = uniqueSeqService.getNextLocationId(record.getLOC_TYPE(), townList
				.get(0).getZIP_CODE());
		record.setLOCATION_ID(locationId);
		record.setCR_USER(user);
		record.setCR_TIME(date);
		record.setMD_USER(user);
		record.setMD_TIME(date);
		this.setCheckboxValue(record);
		int item = locationDao.insert(record);
		if (item == 0) {
			log.error("insert is fail , locationId = " + record.getLOCATION_ID());
			throw new CreateFailException("新增失敗");
		}
		if (shareComArray.length != 0) {
			for (String shareCom : shareComArray) {
				TbSiteShareKey siteShare = new TbSiteShareKey();
				siteShare.setLOCATION_ID(locationId);
				siteShare.setSHARE_COM(shareCom);
				int siteShareItem = siteShareDao.insert(siteShare);
				if (siteShareItem == 0) {
					log.error("insert is fail , locationId = " + record.getLOCATION_ID()
							+ " , shareCom = " + shareCom);
					throw new CreateFailException("新增失敗");
				}
			}
		}
		return record;
	}

	/**
	 * 修改siteLocation
	 * 
	 * @param recprd
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	@Transactional
	public TbSiteLocation update(TbSiteLocation record,String[] shareComArray) throws UpdateFailException {
		record.setMD_USER(this.getLoginUser().getUsername());
		record.setMD_TIME(new Date());
		this.setCheckboxValue(record);
		TbSiteLocation location = locationDao.findByPk(record.getLOCATION_ID());
		BeanUtils.copyProperties(record, location, "CR_USER", "CR_TIME");
		// int item = st001Dao.update(record);
		int item = locationDao.updateByPrimaryKey(location);
		if (item == 0) {
			log.error("update fail count = " + item + " , locationId = "
					+ location.getLOCATION_ID());
			throw new UpdateFailException("更新失敗");
		}
		TbSiteShareExample example = new TbSiteShareExample();
		example.createCriteria().andLOCATION_IDEqualTo(record.getLOCATION_ID());
		List<TbSiteShareKey> siteShareList = siteShareDao.findByConditions(example);
		if(siteShareList.size() != 0){
			int siteSharedeleteItem = siteShareDao.deleteByConditions(example);
			if(siteSharedeleteItem == 0){
				log.error("delete is fail , siteSharedeleteItem count = " + siteSharedeleteItem);
				throw new UpdateFailException("更新失敗");
			}
		}
		if (shareComArray.length != 0) {
			for (String shareCom : shareComArray) {
				TbSiteShareKey siteShare = new TbSiteShareKey();
				siteShare.setLOCATION_ID(record.getLOCATION_ID());
				siteShare.setSHARE_COM(shareCom);
				int siteShareItem = siteShareDao.insert(siteShare);
				if (siteShareItem == 0) {
					log.error("insert is fail , locationId = " + record.getLOCATION_ID()
							+ " , shareCom = " + shareCom);
					throw new UpdateFailException("更新失敗");
				}
			}
		}
		return record;
	}

	/**
	 * 新增siteMain
	 * 
	 * @param siteMain
	 * @throws UpdateFailException
	 */
	@Transactional
	public void insertSiteMain(TbSiteMain siteMain, String crUser) throws CreateFailException {
		siteMain.setSITE_ID(uniqueSeqService.getNextSiteId());
		siteMain.setSITE_STATUS(SiteStatus.S04.name());
		siteMain.setEQP_MODEL_ID(StringUtils.trimToNull(siteMain.getEQP_MODEL_ID()));
		siteMain.setSITE_NAME(StringUtils.trimToNull(siteMain.getSITE_NAME()));
		siteMain.setCR_USER(crUser);
		siteMain.setCR_TIME(new Date());
		int item = siteMainDao.insert(siteMain);
		if (item == 0) {
			throw new CreateFailException("新增失敗");
		}
	}
	/**
	 * 刪除基站
	 * */
	@Transactional
	public void deleteSiteMain(String siteId)throws NomsException{
		int item = siteMainDao.deleteByPrimaryKey(siteId);
		if(item == 0){
			throw new NomsException("刪除失敗");
		}
	}
	
	public int updateSiteStatusBySiteId(String siteId,SiteStatus siteStatus){
		return siteMainDao.updateSiteStatusBySiteId(siteId, siteStatus);
	}
	
	/**
	 * 驗證
	 * @param eqpTypeId
	 * @param feqType
	 * @param btsSiteId
	 * @param city
	 * @param area
	 * @param coverageType
	 * @return
	 */
	public String doValidate(String eqpTypeId, String feqType, String btsSiteId, String city,
			String area, String coverageType) {
		String errorMessages = "";
		String domain = townDomainTeamDao.getTownDomainTeamByCityIdTownId(city, area).getDOMAIN();
		domain = domain.substring(0, 1);
		errorMessages = sT002Service.doValidateBTSSiteId(eqpTypeId, feqType, domain, btsSiteId,
				coverageType);
		return errorMessages;
	}
	/**
	 * 
	 * @param record
	 */
	public void setCheckboxValue(TbSiteLocation record) {
		if (!"Y".equals(record.getIS_FREEENTER())) {
			record.setIS_FREEENTER("N");
		}
		if (!"Y".equals(record.getIS_INDOOR())) {
			record.setIS_INDOOR("N");
		}
		if (!"Y".equals(record.getIS_KEY())) {
			record.setIS_KEY("N");
		}
		if (!"Y".equals(record.getNEED_MISC_LIC())) {
			record.setNEED_MISC_LIC("N");
		}
		if (!"Y".equals(record.getHAS_MISC_LIC())) {
			record.setHAS_MISC_LIC("N");
		}

		if (!"Y".equals(record.getSPACE_ROOM())) {
			record.setSPACE_ROOM("N");
		}
		if (!"Y".equals(record.getSPACE_ROOF())) {
			record.setSPACE_ROOF("N");
		}
		if (!"Y".equals(record.getSPACE_TOP())) {
			record.setSPACE_TOP("N");
		}
		if (!"Y".equals(record.getSPACE_PLATFORM())) {
			record.setSPACE_PLATFORM("N");
		}
		if (!"Y".equals(record.getSPACE_LAND())) {
			record.setSPACE_LAND("N");
		}
		if (!"Y".equals(record.getSPACE_INDOOR())) {
			record.setSPACE_INDOOR("N");
		}
		if (!"Y".equals(record.getSPACE_OUTDOOR())) {
			record.setSPACE_OUTDOOR("N");
		}
	}
	/**
	 * 查詢共構共站
	 * @param example
	 * @return
	 */
	public List<TbSiteShareKey> getSiteShareByConditions(TbSiteShareExample example){
		return siteShareDao.findByConditions(example);
	}
	/**
	 * 查詢work
	 * @param example
	 * @return
	 */
	public List<TbSiteWork> getSiteWorkByConditions(TbSiteWorkExample example){
		return workDao.findByConditions(example);
	}
	
	/**
	 * 查詢siteMain
	 * @param example
	 * @return
	 */
	public List<TbSiteMain> getSiteMainByConditions(TbSiteMainExample example){
		return siteMainDao.findByConditions(example);
	}
}
