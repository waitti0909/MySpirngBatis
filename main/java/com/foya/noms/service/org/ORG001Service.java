package com.foya.noms.service.org;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbOrgPosition;
import com.foya.dao.mybatis.model.TbOrgPositionExample;
import com.foya.exception.DataExistsException;
import com.foya.noms.dao.auth.PersonnelDao;
import com.foya.noms.dao.org.ORG001Dao;
import com.foya.noms.service.BaseService;
@Service
public class ORG001Service extends BaseService{
	
	@Autowired
	private ORG001Dao org001Dao;
	@Autowired
	private PersonnelDao personnelDao;
	
	/**
	 * 用code和name查詢Position其餘欄位
	 * @param code
	 * @param name
	 * @return
	 */
	public List<TbOrgPosition> searchPositionByCodeName(String posId , String posName){
		return org001Dao.searchPositionByCodeName(posId, posName);
	}
	
	/**
	 * 用ID查詢Position所有欄位
	 * @param POS_ID
	 * @return
	 */
	public TbOrgPosition getPositionById(String POS_ID){
		return org001Dao.getPositionById(POS_ID);
	}
	
	/**
	 * 查詢所有position
	 * @param example
	 * @return
	 */
	public List<TbOrgPosition> searchAllPosition(){
		TbOrgPositionExample example = new TbOrgPositionExample();
		example.setOrderByClause("POS_ID");
		return org001Dao.searchPositionByExample(example);
	}
	
	/**
	 * 新增Position
	 * @param record
	 * @return
	 */
	@Transactional
	public boolean saveNewPosition(TbOrgPosition position,String user)throws DataExistsException{
		boolean result = false;
		position.setMD_USER(user);
		position.setMD_TIME(new Date());
		TbOrgPosition pos = org001Dao.getPositionById(position.getPOS_ID());
		if(pos==null){
			org001Dao.saveNewPosition(position);
			result = true ;
		}else{
			throw new DataExistsException("職務名稱重複，無法新增");
		}
		return result ; 
	}
	
	/**
	 * 修改Position
	 * @param position
	 * @param mdUser
	 * @return
	 */
	@Transactional
	public boolean saveUpdatePosition(TbOrgPosition position,String mdUser,Integer mdUserId){
		boolean result = false;
		position.setMD_USER(mdUser);
		position.setMD_TIME(new Date());
//		position.setMD_DEPT(personnelDao.getPsnDeptById(mdUserId).getDeptName());
		int item = org001Dao.saveUpdatePosition(position);
		if(item>0){
			result = true;
		}
		return result ; 
	}
	
}
