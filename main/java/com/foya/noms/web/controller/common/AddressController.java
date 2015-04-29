package com.foya.noms.web.controller.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.foya.dao.mybatis.model.TbComCity;
import com.foya.dao.mybatis.model.TbComRoad;
import com.foya.dao.mybatis.model.TbComTown;
import com.foya.noms.dto.common.AddressDTO;
import com.foya.noms.dto.common.TownDomainTeamDTO;
import com.foya.noms.service.common.AddressService;
import com.foya.noms.util.BaseJasonObject;
import com.foya.noms.web.controller.BaseController;
/**
 * 地址處理Controller
 * 
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2014/9/17</td>
 * <td>地址處理Controller</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Controller
public class AddressController extends BaseController {
	
	@Autowired
	private AddressService addressService;
	
	/**
	 * 地址處理頁
	 * @param address
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/common/address/initLoad")
	public String initLoadPage(@RequestBody AddressDTO address, HttpServletRequest request, Map<String, Object> modelMap) {
		modelMap.put("cityMap", addressService.getCityKeyValueMap());
		modelMap.put("address", address);
		return "/ajaxPage/common/addressM";
	}
	
	@RequestMapping(value = "/common/address/getAreas")
	@ResponseBody
	public BaseJasonObject<TbComTown> getAreas(@RequestParam(value="city") String city) {
		return new BaseJasonObject<>(addressService.getTownByCondition(city), AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	@RequestMapping(value = "/common/address/getRoads")
	@ResponseBody
	public BaseJasonObject<TbComRoad> getRoads(@RequestParam(value="city") String city, @RequestParam(value="area") String area) {
		return new BaseJasonObject<>(addressService.getRoadByCondition(city, area), AJAX_SUCCESS, AJAX_EMPTY);
	}
	
	@RequestMapping(value = "/common/address/getZip")
	@ResponseBody
	public String getZip(@RequestParam(value="city") String city, @RequestParam(value="area") String area) {
		List<TbComTown> result = addressService.getZipFromTown(city, area);
		return result.get(0).getZIP_CODE();
	}
	
	@RequestMapping(value = "/common/address/combineAddress")
	@ResponseBody
	public BaseJasonObject<AddressDTO> combineAddress(@RequestBody AddressDTO address) {
		String fullAddress = addressService.combineAddress(address);
		address.setFullAddress(fullAddress);
		return new BaseJasonObject<AddressDTO>(address, AJAX_SUCCESS);
	}
	
	
	/**
	 * get all City
	 * @param none
	 * @return
	 */
	@RequestMapping(value = "/common/address/allCities")
	@ResponseBody
	public  List<TbComCity> getAllCity() {
		return addressService.getCityByCondition(null);
	}
	
	/**
	 * get all area
	 * @return
	 */
	@RequestMapping(value = "/common/address/allTowns")
	@ResponseBody
	public  List<TbComTown> getCity() {
		return addressService.getTownByCondition(null);
	}
	
	/**
	 * get all area
	 * @return
	 */
	@RequestMapping(value = "/common/address/allCityTownsDomainTeam")
	@ResponseBody
	public  List<TownDomainTeamDTO> getAllCityTownsDomainTeam() {
		return addressService.getAllCityTownsDomainTeam();
	}
	
	
}
