package com.foya.noms.dao.auth;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbAuthMenuFunMapper;
import com.foya.dao.mybatis.mapper.UTbAuthMenuFunMapper;
import com.foya.dao.mybatis.model.TbAuthMenuFun;
import com.foya.dao.mybatis.model.TbAuthMenuFunExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.auth.MenuFunDTO;

/**
 * The Class MenuDao.
 */
@Repository
public class MenuFunDao extends BaseDao{


	@Autowired
	private TbAuthMenuFunMapper tbAuthMenuFunMapper;
	@Autowired
	private UTbAuthMenuFunMapper uTbAuthMenuFunMapper;

	
	/**
	 * 新增LookupButton與menu對應的資料
	 */
	public int insertSelective(TbAuthMenuFun record){
		return tbAuthMenuFunMapper.insertSelective(record);
	}
	
	
	/**
	 * 用MENU_ID取得MENU_FUN其他欄位
	 */
	public List<MenuFunDTO> selectByMenuID(BigDecimal MENU_ID){
		return uTbAuthMenuFunMapper.selectByMenuID(MENU_ID);
	}
	/**
	 * 用MENU_ID刪除MENU_FUN
	 */
	public int deleteByMenuID(BigDecimal MENU_ID){
		return uTbAuthMenuFunMapper.deleteByMenuID(MENU_ID);
	}
	/**
	 * 查詢MENU_FUN
	 */
	public List<TbAuthMenuFun> selectByExample(TbAuthMenuFunExample example){
		return tbAuthMenuFunMapper.selectByExample(example);
	}
	
	public List<MenuFunDTO> findFunctionsByMenuId(BigDecimal menuId) {
		return uTbAuthMenuFunMapper.findFunctionsByMenuId(menuId);
	}
	/**
     * 用menuID和FunCode來刪除相對應的MenuFun
     * @param MENU_ID
     * @param FUN_CODE
     * @return
     */
	public int deleteByMenuIDAndFunCode(Map<String,Object> menuFun){
		return uTbAuthMenuFunMapper.deleteByMenuIDAndFunCode(menuFun);
	}
	/**
	 * 修改原有的menuFun
	 * @param record
	 * @return
	 */
	public int updateMenuFun(TbAuthMenuFun record){
		return tbAuthMenuFunMapper.updateByPrimaryKeySelective(record);
	}
}
