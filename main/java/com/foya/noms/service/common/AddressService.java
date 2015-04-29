package com.foya.noms.service.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbComCity;
import com.foya.dao.mybatis.model.TbComCityExample;
import com.foya.dao.mybatis.model.TbComRoad;
import com.foya.dao.mybatis.model.TbComRoadExample;
import com.foya.dao.mybatis.model.TbComTown;
import com.foya.dao.mybatis.model.TbComTownExample;
import com.foya.noms.dao.common.CityDao;
import com.foya.noms.dao.common.RoadDao;
import com.foya.noms.dao.common.TownDao;
import com.foya.noms.dao.common.TownDomainTeamDao;
import com.foya.noms.dto.common.AddressDTO;
import com.foya.noms.dto.common.TownDomainTeamDTO;
import com.foya.noms.service.BaseService;

/**
 * The Class AddressService.
 */
@Service
public class AddressService extends BaseService {


	/** The city dao. */
	@Autowired
	private CityDao cityDao;
	
	/** The town dao. */
	@Autowired
	private TownDao townDao;
	
	/** The road dao. */
	@Autowired
	private RoadDao roadDao;
	
	/** The town domain team dao. */
	@Autowired
	private TownDomainTeamDao townDomainTeamDao;
	
	/**
	 * get City.
	 *
	 * @return the city key value map
	 */
	public Map<String, String> getCityKeyValueMap() {
		Map<String, String> map = new LinkedHashMap<>();
		List<TbComCity> rows = this.getCityByCondition(null);
		for (TbComCity row : rows) {
			map.put(row.getCITY_ID(), row.getCITY_NAME());
		}
		return map;
	}
	
	/**
	 * get Town.
	 *
	 * @param city the city
	 * @return the town key value map
	 */
	public Map<String, String> getTownKeyValueMap(String city) {
		Map<String, String> map = new LinkedHashMap<>();
		List<TbComTown> rows = this.getTownByCondition(city);	
		TbComTownExample example = new TbComTownExample();
		List<TbComTown> townRows = null;		
		for (TbComTown row : rows) {
			example.createCriteria().andCITY_IDEqualTo(row.getCITY_ID());
			townRows = townDao.findTownByCondition(example);			
			for(int i=0;i<townRows.size();i++){
				map.put(townRows.get(i).getTOWN_ID(), townRows.get(i).getTOWN_NAME());
			}
		}

		
		return map;
	}
	
	/**
	 * Gets the city by condition.
	 *
	 * @param city the city
	 * @return the city by condition
	 */
	public List<TbComCity> getCityByCondition(String city) {
		TbComCityExample example = new TbComCityExample();
		example.setOrderByClause("CITY_ID");
		if (StringUtils.isNotEmpty(city)) {			
			example.createCriteria().andCITY_IDEqualTo(city);
		}
		return cityDao.findCityByCondition(example);
	}
	
	
	/**
	 * Gets the town by condition.
	 *
	 * @param city the city
	 * @return the town by condition
	 */
	public List<TbComTown> getTownByCondition(String city) {
		TbComTownExample example = new TbComTownExample();
		example.setOrderByClause("CITY_ID");
		if (StringUtils.isNotEmpty(city)) {			
			example.createCriteria().andCITY_IDEqualTo(city);
		}
		return townDao.findTownByCondition(example);
	}
	
	/**
	 * Gets the zip from town.
	 *
	 * @param city the city
	 * @param area the area
	 * @return the zip from town
	 */
	public List<TbComTown> getZipFromTown(String city, String area) {
		TbComTownExample example = new TbComTownExample();
		example.createCriteria().andCITY_IDEqualTo(city).andTOWN_IDEqualTo(area);		
		return townDao.findTownByCondition(example);
	}
	
	/**
	 * Gets the road by condition.
	 *
	 * @param city the city
	 * @param area the area
	 * @return the road by condition
	 */
	public List<TbComRoad> getRoadByCondition(String city, String area) {
		TbComRoadExample example = new TbComRoadExample();
		example.setOrderByClause("city, area, road");
		example.createCriteria().andCITY_IDEqualTo(city).andTOWN_IDEqualTo(area);
		return roadDao.findByCondition(example);
	}
	
	/**
	 * Combine address.
	 *
	 * @param dto the dto
	 * @return the string
	 */
	public String combineAddress(AddressDTO dto) {
		StringBuffer sb = new StringBuffer();
			
		sb.append(halftoFull(dto.getZip()) + " " + dto.getCityName() + dto.getAreaName());
		if (StringUtils.isNotEmpty(dto.getVillage())) {
			//判斷數字則轉全形
			sb.append(" " + isNum(dto.getVillage()) + "(村里)");
		}
		if (StringUtils.isNotEmpty(dto.getAdjacent())) {	
			//判斷數字則轉全形
			sb.append(" " + isNum(dto.getAdjacent()) + "鄰");
		}	
		if (StringUtils.isNotEmpty(dto.getRoad())) {
			sb.append(" " + dto.getRoad());
		}			
		if (StringUtils.isNotEmpty(dto.getLane())) {
			//判斷數字則轉全形
			sb.append(" " + isNum(dto.getLane()) + "巷");
		}
		if (StringUtils.isNotEmpty(dto.getAlley())) {
			//判斷數字則轉全形
			sb.append(" " + isNum(dto.getAlley()) + "弄");
		}
		if (StringUtils.isNotEmpty(dto.getSubAlley())) {
			//判斷數字則轉全形
			sb.append(" " + isNum(dto.getSubAlley()) + "衖");
		}
		if (StringUtils.isNotEmpty(dto.getDoorNo())) {				
			//判斷數字則轉全形
			sb.append(" " + isNum(dto.getDoorNo()) + "號");
			if (StringUtils.isNotEmpty(dto.getDoorNoEx())) {
				//判斷數字則轉全形
				sb.append("之" + isNum(dto.getDoorNoEx()) + " ");
			}
		}
		if (StringUtils.isNotEmpty(dto.getFloor())) {
			//判斷數字則轉全形
			sb.append(" " + isNum(dto.getFloor()) + "樓");
			if (StringUtils.isNotEmpty(dto.getFloorEx())) {
				//判斷數字則轉全形
				sb.append("之" + isNum(dto.getFloorEx()));
			}
		}
		if (StringUtils.isNotEmpty(dto.getRoom())) {
			//判斷數字則轉全形
			sb.append(" " + isNum(dto.getRoom()) + "室");
		}		
		if (StringUtils.isNotEmpty(dto.getRoad())) {
			if (StringUtils.isNotEmpty(dto.getRemark())) {
				//判斷數字則轉全形
				sb.append(" (" + isNum(dto.getRemark()) + ")");
			}
		}else{
			if (StringUtils.isNotEmpty(dto.getRemark())) {
				//判斷數字則轉全形
				sb.append(" " + isNum(dto.getRemark()));
			}
		}
		return sb.toString();
	}
	
	/**
	 * Gets the zone.
	 *
	 * @param city the city
	 * @return the zone
	 */
	public String getZone(String city){
		TbComCityExample example = new TbComCityExample();
		example.createCriteria().andCITY_IDEqualTo(city);
		List<TbComCity> zone = cityDao.findCityByCondition(example);
		return zone.get(0).getDOMAIN();
	}
	
	/**
	 * Halfto full.
	 *
	 * @param halfChars the half chars
	 * @return the string
	 */
	public String halftoFull (String halfChars){
		String outStr = "";
		char[] chars = halfChars.toCharArray();
		int tranTemp = 0;
		for(int i = 0; i < chars.length; i++){ 
		 tranTemp = (int)chars[i];
		 if(tranTemp != 45) //ASCII碼:45 是減號 -
		 tranTemp += 65248; //此數字是 Unicode編碼轉為十進位 和 ASCII碼的 差
		 outStr += (char)tranTemp;
		}
		return outStr;
	}
	
	/**
	 * Gets the all city towns domain team.
	 *
	 * @return the all city towns domain team
	 */
	public List<TownDomainTeamDTO> getAllCityTownsDomainTeam() {
		
		return townDomainTeamDao.getAll();
	}
	
	/**
	 * 字串中判斷是否為數字轉全形
	 * @param str
	 * @return
	 */
	public String isNum(String str) {
		char[] chars = str.toCharArray();
		String outStr = "";
		int tranTemp = 0;
		for (int index = 0; index < str.length(); index++) {
			if (Character.isDigit(chars[index])) {
				tranTemp = (int) chars[index];
				//數字是半形才轉全形
				if(tranTemp>=48 && tranTemp<=57){
						tranTemp += 65248; // 此數字是 Unicode編碼轉為十進位 和 ASCII碼的 差
				}
				outStr += (char) tranTemp;								
			} else {
				tranTemp = (int) chars[index];
				outStr += (char) tranTemp;
			}
		}
		return outStr;
	}

}
