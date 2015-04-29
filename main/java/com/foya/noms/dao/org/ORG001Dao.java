package com.foya.noms.dao.org;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbOrgPositionMapper;
import com.foya.dao.mybatis.model.TbOrgDeptPos;
import com.foya.dao.mybatis.model.TbOrgPosition;
import com.foya.dao.mybatis.model.TbOrgPositionExample;
import com.foya.noms.dao.BaseDao;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
@Repository
public class ORG001Dao extends BaseDao{
	
	/** The position mapper. */
	@Autowired
	private TbOrgPositionMapper tbOrgPositionMapper;
	
	/**
	 * 用ID查詢Position所有欄位
	 * @param POS_ID
	 * @return
	 */
	public TbOrgPosition getPositionById(String posId){
		return tbOrgPositionMapper.selectByPrimaryKey(posId);
	}
	
	/**
	 * 用code和name查詢Position其餘欄位
	 * @param code
	 * @param name
	 * @return
	 */
	public List<TbOrgPosition> searchPositionByCodeName(String posId , String posName){
		TbOrgPositionExample example = new TbOrgPositionExample();
		if(!StringUtils.isEmpty(posId)){
			example.createCriteria().andPOS_IDEqualTo(posId);
		}
		if(!StringUtils.isEmpty(posName)){
			example.createCriteria().andPOS_NAMELike("%"+posName+"%");
		}
		PageBounds pageBounds = getPageBounds();
		List<TbOrgPosition> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.TbOrgPositionMapper.selectByExample", example, pageBounds);
		PageList<TbOrgPosition> pageList = (PageList<TbOrgPosition>)list;
		return pageList;
	}

	/**
	 * 查詢所有position
	 * @param example
	 * @return
	 */
	public List<TbOrgPosition> searchPositionByExample(TbOrgPositionExample example){
		return tbOrgPositionMapper.selectByExample(example);
	}
	
	/**
	 * 新增Position
	 * @param record
	 * @return
	 */
	public int saveNewPosition(TbOrgPosition position){
		return tbOrgPositionMapper.insertSelective(position);
	}
	
	/**
	 * 修改Position
	 * @param record
	 * @return
	 */
	public int saveUpdatePosition(TbOrgPosition position){
		return tbOrgPositionMapper.updateByPrimaryKeySelective(position);
	}
	
	
	
	/**
	 * 新增部門職稱
	 * @param dptPos
	 */
	public void saveNewDeptPos(TbOrgDeptPos dptPos){
		
	}
}

