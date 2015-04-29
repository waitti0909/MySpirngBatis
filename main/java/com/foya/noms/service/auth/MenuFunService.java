package com.foya.noms.service.auth;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.noms.dao.auth.MenuFunDao;
import com.foya.noms.dto.auth.MenuFunDTO;
import com.foya.noms.service.BaseService;

@Service
public class MenuFunService extends BaseService {

	@Autowired
	private MenuFunDao menuFunDao;
	
	/**
	 * 用MENU_ID取得MENU_FUN其他欄位
	 * @param example 
	 */
	public List<MenuFunDTO> selectByMenuID(BigDecimal menuId){
		return menuFunDao.findFunctionsByMenuId(menuId);
	}
}
