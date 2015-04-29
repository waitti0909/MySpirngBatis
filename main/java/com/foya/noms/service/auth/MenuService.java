package com.foya.noms.service.auth;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbAuthMenu;
import com.foya.dao.mybatis.model.TbAuthMenuExample;
import com.foya.dao.mybatis.model.TbAuthMenuFun;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.exception.NomsException;
import com.foya.noms.dao.auth.MenuDao;
import com.foya.noms.dao.auth.MenuFunDao;
import com.foya.noms.dao.auth.RoleMenuDao;
import com.foya.noms.dao.auth.RoleMenuFunDao;
import com.foya.noms.dao.system.LookupDao;
import com.foya.noms.dto.auth.MenuDTO;
import com.foya.noms.dto.auth.MenuFunDTO;
import com.foya.noms.dto.auth.RoleMenuFunDTO;
import com.foya.noms.dto.auth.RoleMenuFunDepDTO;
import com.foya.noms.service.BaseService;

@Service
public class MenuService extends BaseService {

	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private MenuFunDao menuFunDao;
	
	@Autowired
	private RoleMenuDao roleMenuDao;
	
	@Autowired
	private RoleMenuFunDao roleMenuFunDao;
	
	@Autowired
	private LookupDao sysLookupDao;
	
	/**
	 * 取得所有 menu
	 * */
	public List<TbAuthMenu> selectByExample(TbAuthMenuExample example){
		return menuDao.selectByExample(example);
	}
	
	/**
	 * 用id取得指定menu其餘的欄位 
	 * */
	public TbAuthMenu selectByPrimaryKey(BigDecimal MENU_ID){
		return menuDao.selectByPrimaryKey(MENU_ID);
	}
	
	public List<MenuDTO> selectDTOByPrimaryKeyGrid(BigDecimal MENU_ID){
		if (!new BigDecimal(-1).equals(MENU_ID)) {
			return menuDao.selectDTOByPrimaryKeyGrid(MENU_ID);
		}else {
			return menuDao.selectDTOByGrid(new TbAuthMenuExample());
		}
	
	}
	
	/**
	 * 產生新的menu
	 * */
	@Transactional
	public void createMenu(TbAuthMenu menu,String[] lookupButtons,String user){
		Date date = new Date();
		menu.setCR_USER(user);
        menu.setCR_TIME(date);
        menu.setMD_USER(user);
        menu.setMD_TIME(date);
		menuDao.insertSelective(menu);
		if (ArrayUtils.isNotEmpty(lookupButtons)) {
			TbAuthMenuFun menuFun = new TbAuthMenuFun();
			for(int i =0;i<=lookupButtons.length-1;i++){
				menuFun.setMENU_ID(menu.getMENU_ID());
				menuFun.setFUN_CODE(lookupButtons[i]);
				menuFun.setDISPLAY_ORDER(BigDecimal.valueOf(i+1));
				menuFunDao.insertSelective(menuFun);
			}
		}
	}
	
	/**
	 * 修改原有的menu
	 * */
	public int updateByPrimaryKeySelective(TbAuthMenu record){
		return menuDao.updateByPrimaryKeySelective(record);
	}
	@Transactional
	public boolean updateMenu(TbAuthMenu menu,String[] lookupButtons,String mdUser){	
		boolean result =false;
		BigDecimal menuID = menu.getMENU_ID();
		menu.setMD_USER(mdUser);
        menu.setMD_TIME(new Date());
        TbAuthMenu menuSrc = menuDao.selectByPrimaryKey(menuID);
        String[] ignoreProperties = {"CR_USER", "CR_TIME"};
       	BeanUtils.copyProperties(menu, menuSrc, ignoreProperties);
		menuDao.updateByPrimaryKey(menuSrc);
		int displayOrder = 0;
		List<MenuFunDTO> menuFuns = this.selectMenuFunByMenuID(menuID);
		List<MenuFunDTO> buttons = new ArrayList<MenuFunDTO>();
		List<MenuFunDTO> menuFuns_delete = new ArrayList<>(menuFuns);
		for(MenuFunDTO menuFun : menuFuns){
			displayOrder = menuFun.getDISPLAY_ORDER().intValue();
		}
		for(String lookupButton : lookupButtons){
			MenuFunDTO menuFun = new MenuFunDTO();
			menuFun.setMENU_ID(menuID);
			menuFun.setFUN_CODE(lookupButton);
			buttons.add(menuFun);
		}
		List<MenuFunDTO> menuFuns_insert = new ArrayList<MenuFunDTO>(buttons);
		menuFuns_insert.removeAll(menuFuns);
		menuFuns_delete.removeAll(buttons);
    	if(menuFuns_delete.size()>0){
    		List<RoleMenuFunDTO> roleMenuFuns = roleMenuFunDao.selectRoleMenuFunByMenuId(menuID.intValue());
			for(MenuFunDTO delete : menuFuns_delete){
				for(RoleMenuFunDTO roleMenuFun : roleMenuFuns){
					if(delete.getFUN_CODE().equals(roleMenuFun.getFunCode())){
						throw new NomsException("已被使用不允許刪除");
					}
				}
			}
			for(MenuFunDTO delete : menuFuns_delete){
				Map<String ,Object> menuFun = new HashMap<String ,Object>();
				menuFun.put("MENU_ID",menuID);
				menuFun.put("FUN_CODE",delete.getFUN_CODE());
				menuFunDao.deleteByMenuIDAndFunCode(menuFun);
			}
			result = true;
		}
    	
		if(menuFuns_insert.size()>0){
			TbAuthMenuFun tbMenuFun = new TbAuthMenuFun();
			for(MenuFunDTO menuFun : menuFuns_insert){
				tbMenuFun.setMENU_ID(menuID);
				tbMenuFun.setFUN_CODE(menuFun.getFUN_CODE());
				tbMenuFun.setDISPLAY_ORDER(BigDecimal.valueOf(displayOrder+=1));
				tbMenuFun.setMD_USER(mdUser);
				tbMenuFun.setMD_TIME(new Date());
				menuFunDao.insertSelective(tbMenuFun);
			}
			result = true;
		}else if(menuFuns_insert.size()==0){
			List<MenuFunDTO> menufuns = menuFunDao.selectByMenuID(menuID);
			for(MenuFunDTO menufun : menufuns){
				menufun.setMD_USER(mdUser);
				menufun.setMD_TIME(new Date());
				menuFunDao.updateMenuFun(menufun);
			}
			result = true;
		}
		return result;
	}	
	
	@Transactional
	public boolean updateMenu2(TbAuthMenu menu,String[] lookupButtons,BigDecimal mdyPSNID){	
		BigDecimal menuID = menu.getMENU_ID();
        menu.setMD_TIME(new Date());
		int displayOrder = 0;
        if(ArrayUtils.isNotEmpty(lookupButtons)) {
        	List<MenuFunDTO> menuFuns_source = this.selectMenuFunByMenuID(menuID);
        	List<MenuFunDTO> menuFuns_delete = new ArrayList<>(menuFuns_source);
        	
        	List<MenuFunDTO> menuFuns_targetList = new ArrayList<MenuFunDTO>();
        	for(String lookupButton : lookupButtons){
				MenuFunDTO menuFun = new MenuFunDTO();
				menuFun.setMENU_ID(menuID);
				menuFun.setFUN_CODE(lookupButton);
				menuFuns_targetList.add(menuFun);
			}
        	List<MenuFunDTO> menuFuns_insert = new ArrayList<MenuFunDTO>(menuFuns_targetList);
        	//頁面比 DB 多表示要新增
        	menuFuns_insert.removeAll(menuFuns_source);
        	//DB比頁面多表示需要刪除
        	menuFuns_delete.removeAll(menuFuns_targetList);
        	
        	if(menuFuns_delete.size()>0){
	        	List<RoleMenuFunDTO> roleMenuFuns = roleMenuFunDao.selectRoleMenuFunByMenuId(menuID.intValue());
	        	//進行刪除
	        	Map<String, Object> menuFunMap_del = new HashMap<>();
	        	for(MenuFunDTO m:menuFuns_delete){
	        		
	        		log.debug("del list="+m.getMENU_ID()+","+m.getFUN_CODE());
	        		//檢查 roleMenuFuns 有無使用到
	        		for(RoleMenuFunDTO rmf:roleMenuFuns){
	        			if(rmf.getFunCode().equalsIgnoreCase(m.getFUN_CODE())){
	        				throw new NomsException("已被使用不允許刪除");
	        			}
	        		}
	        		menuFunMap_del.clear();
	        		menuFunMap_del.put("MENU_ID",menuID);
	        		menuFunMap_del.put("FUN_CODE",m.getFUN_CODE());
	        		menuFunDao.deleteByMenuIDAndFunCode(menuFunMap_del);
	        		
	        	}
        	}
        	TbAuthMenuFun tbMenuFun ;
        	for(MenuFunDTO m:menuFuns_insert){
        		tbMenuFun = new TbAuthMenuFun();
        		tbMenuFun.setMENU_ID(menuID);
				tbMenuFun.setFUN_CODE(m.getFUN_CODE());
				tbMenuFun.setDISPLAY_ORDER(BigDecimal.valueOf(displayOrder+1));
//				tbMenuFun.setMD_USER(mdyPSNID);
//				tbMenuFun.setMDY_TSTMP(new Date());
        		log.debug("insert list="+m.getMENU_ID()+","+m.getFUN_CODE());
        		menuFunDao.insertSelective(tbMenuFun);
        	}
        }	
        return true;
	}
	
	/**
	 * 刪除原有的menu
	 * */
	public int deleteByPrimaryKey(BigDecimal MENU_ID){
		return menuDao.deleteByPrimaryKey(MENU_ID);
	}
	
	@Transactional
	public boolean deleteMenu(String[] menuIDs)throws NomsException{
		boolean result=false;
		for( String menuId:menuIDs){
			List<RoleMenuFunDepDTO> roleMenus = roleMenuDao.selectRoleMenuByMenuId(Integer.valueOf(menuId));
			if(roleMenus.isEmpty()){
				List<RoleMenuFunDepDTO> roleMenuFuns =roleMenuFunDao.findRoleMenuFunByMenuId(Integer.valueOf(menuId));
				if(roleMenuFuns.isEmpty()){
					menuDao.deleteByPrimaryKey(BigDecimal.valueOf(Integer.valueOf(menuId)));
					menuFunDao.deleteByMenuID(BigDecimal.valueOf(Integer.valueOf(menuId)));
					result =true;
				}else{
					result =false;
					throw new  NomsException("該功能已被角色按鈕綁定使用不允許刪除");
				}
			}else{
				result =false;
				throw new NomsException("該功能已被角色綁定使用不允許刪除");
			}
		}
		return result;
	}
	
	/**
	 * 取得所有LookupButton
	 */
	public List<TbSysLookup> selectAllLookupButtons(TbSysLookupExample example){
		example.createCriteria().andTYPEEqualTo("FUNBTNGROUP");
		return sysLookupDao.selectByExample(example);
	}
	
	/**
	 * 新增LookupButton與menu對應的資料
	 */
	public int insertLookupButton(TbAuthMenuFun record){
		return menuFunDao.insertSelective(record);
	}
	
	/**
	 * 用MENU_ID取得MENU_FUN其他欄位
	*/
	public List<MenuFunDTO> selectMenuFunByMenuID(BigDecimal MENU_ID){
		return menuFunDao.selectByMenuID(MENU_ID);
	}
	
	/**
	 * 查詢所有是資料夾的menu
	 * @return
	 */
	public List<MenuDTO> selectIsFolderMenu(){
		return menuDao.selectIsFolderMenu();
	}
}
