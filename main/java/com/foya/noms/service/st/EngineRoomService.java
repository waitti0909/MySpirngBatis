package com.foya.noms.service.st;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbComTown;
import com.foya.dao.mybatis.model.TbComTownExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.exception.CreateFailException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dao.common.TownDao;
import com.foya.noms.dao.st.EngineRoomDao;
import com.foya.noms.dao.st.LocationDao;
import com.foya.noms.dao.st.SiteMainDao;
import com.foya.noms.dto.st.SiteLocationDTO;
import com.foya.noms.enums.SiteStatus;
import com.foya.noms.enums.SiteType;
import com.foya.noms.service.BaseService;
import com.foya.noms.service.system.UniqueSeqService;

@Service
public class EngineRoomService extends BaseService {
	@Autowired
	private EngineRoomDao engineRoomDao;
	@Autowired
	private ST001Service sT001Service;
	@Autowired
	private UniqueSeqService uniqueSeqService;
	@Autowired
	private TownDao townDao;
	@Autowired
	private LocationDao locationDao;
	@Autowired
	private SiteMainDao siteMainDao;

	/**
	 * 機房查詢
	 * 
	 * @param map
	 * @return
	 */
	public List<SiteLocationDTO> getByCondition(Map<String, String> map) {
		return engineRoomDao.findByCondition(map);
	}

	/**
	 * 新增機房
	 * 
	 * @param engineRoom
	 * @param crUser
	 */
	@Transactional
	public TbSiteMain insert(SiteLocationDTO engineRoom, String user) throws CreateFailException {
		
		TbComTownExample example = new TbComTownExample();
		example.createCriteria().andCITY_IDEqualTo(engineRoom.getCITY())
				.andTOWN_IDEqualTo(engineRoom.getAREA());
		List<TbComTown> townList = townDao.findTownByCondition(example);
		String locationId = uniqueSeqService.getNextLocationId(engineRoom.getLOC_TYPE(), townList
				.get(0).getZIP_CODE());
		Date date = new Date();
		engineRoom.setLOCATION_ID(locationId);
		String engineRoomType= engineRoom.getLOC_TYPE();
		if(!SiteType.M.name().equals(engineRoomType)){
			engineRoom.setSiteStatus(SiteStatus.S04.name());
		}
		TbSiteLocation location =this.getLocationByEngineRoom(engineRoom);
		location.setCR_USER(user);
		location.setCR_TIME(date);
		location.setMD_USER(user);
		location.setMD_TIME(date);
		TbSiteMain site = this.getSiteByEngineRoom(engineRoom, user, date);
		int insertlocItem = locationDao.insert(location);
		int insertSiteItem = siteMainDao.insert(site);
		if (insertlocItem == 0 || insertSiteItem == 0) {
			throw new CreateFailException("新增失敗");
		}

		return site;
	}

	/**
	 * 修改
	 * 
	 * @param engineRoom
	 * @param mdUser
	 * @throws UpdateFailedException
	 */
	@Transactional
	public void update(SiteLocationDTO engineRoom, String mdUser) throws UpdateFailException {
		Date mdDate = new Date();
		engineRoom.setMD_USER(mdUser);
		engineRoom.setMD_TIME(mdDate);
		log.debug("engineRoom : "+engineRoom.toString());
		TbSiteLocation location = this.getLocationByEngineRoom(engineRoom);
		TbSiteMain site = this.getSiteByEngineRoom(engineRoom, mdUser, mdDate);
		log.debug("location : "+location.toString());
		log.debug("site : "+site.toString());
		int updateLocItem = locationDao.updateByPrimaryKey(location);
		int updateSiteItem = siteMainDao.update(site);
		if (updateLocItem == 0 || updateSiteItem == 0) {
			log.error("updateLocItem update count =" + updateLocItem + ", locationId = "
					+ engineRoom.getLOCATION_ID() + " , updateSiteItem update count ="
					+ updateSiteItem + ", siteId = " + engineRoom.getSiteId());
			throw new UpdateFailException("修改失敗");
		}
	}

	private TbSiteLocation getLocationByEngineRoom(SiteLocationDTO engineRoom) {
		TbSiteLocation location = locationDao.findByPk(engineRoom.getLOCATION_ID());
		if(location == null){
			location = new TbSiteLocation();
		}
//		String[] ignoreProperties = { "townName", "chnName", "btsSiteId", "siteStatus", 
//				"siteType", "SHARE_TYPE", "SHARE_COM", "SR_ID", "IS_EXCHANGE", "maintAreaName",
//				"maintDeptName", "maintTeamName", "CR_USER", "CR_TIME", "constrKoDate",
//				"constrDoneDate", "smrReadyDate", "upsReadyDate", "acReadyDate", "dynamoReadyDate",
//				"admReadyDate", "powerReadyDate", "transReadyDate" };
		 String[] ignoreProperties = {"SHARE_COM", "SR_ID",
		 "IS_EXCHANGE", "CR_USER",
		 "CR_TIME"};
		 
		BeanUtils.copyProperties(engineRoom, location, ignoreProperties);
//		location.setLOC_TYPE(StringUtils.trimToNull(location.getLOC_TYPE()));
//		
//		location.setNS_LEVEL(StringUtils.trimToNull(location.getNS_LEVEL()));
		sT001Service.setCheckboxValue(location);
		return location;
	}

	private TbSiteMain getSiteByEngineRoom(SiteLocationDTO engineRoom, String loginUserName,
			Date date) {
		TbSiteMain site = siteMainDao.findByPk(engineRoom.getSiteId());
		if (site == null) {
			site = new TbSiteMain();
			site.setSITE_ID(uniqueSeqService.getNextSiteId());
			site.setBTS_SITE_ID(uniqueSeqService.getNextMHCSiteId(SiteType.valueOf(engineRoom
					.getLOC_TYPE())));
			site.setCR_USER(loginUserName);
			site.setCR_TIME(date);
			site.setMD_USER(loginUserName);
			site.setMD_TIME(date);
		} else {
			site.setSITE_ID(engineRoom.getSiteId());
			site.setBTS_SITE_ID(engineRoom.getBtsSiteId());
			site.setMD_USER(loginUserName);
			site.setMD_TIME(date);
		}
		site.setEQP_TYPE_ID(engineRoom.getLOC_TYPE());
		site.setLOCATION_ID(engineRoom.getLOCATION_ID());
		site.setSITE_STATUS(engineRoom.getSiteStatus());
		site.setSITE_NAME(engineRoom.getLOC_NAME());
		site.setLON(engineRoom.getLON());
		site.setLAT(engineRoom.getLAT());
		site.setCONSTR_KO_DATE(engineRoom.getConstrKoDate());
		site.setCONSTR_DONE_DATE(engineRoom.getConstrDoneDate());
		site.setSMR_READY_DATE(engineRoom.getSmrReadyDate());
		site.setUPS_READY_DATE(engineRoom.getUpsReadyDate());
		site.setAC_READY_DATE(engineRoom.getAcReadyDate());
		site.setDYNAMO_READY_DATE(engineRoom.getDynamoReadyDate());
		site.setADM_READY_DATE(engineRoom.getAdmReadyDate());
		site.setPOWER_READY_DATE(engineRoom.getPowerReadyDate());
		site.setTRANS_READY_DATE(engineRoom.getTransReadyDate());
		if(engineRoom.getOnAirDate() != null){
			site.setONAIR_DATE(engineRoom.getOnAirDate());
		}
		return site;
	}
}
