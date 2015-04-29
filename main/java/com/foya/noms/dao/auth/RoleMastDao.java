package com.foya.noms.dao.auth;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbAuthRoleMastMapper;
import com.foya.dao.mybatis.mapper.UTbAuthRoleMastMapper;
import com.foya.dao.mybatis.model.TbAuthRoleMast;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.auth.RoleDTO;
import com.foya.noms.dto.auth.RoleMastDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class RoleMastDao extends BaseDao {

	@Autowired
	private UTbAuthRoleMastMapper uMapper;
	
	@Autowired
	private TbAuthRoleMastMapper tbAuthRoleMastMapper;
	
	
	/**
	 * 根據用戶id獲取角色集合
	 * @param userId
	 * @return
	 */
	public List<RoleDTO> selectByUserId(Integer userId){
		
		return uMapper.selectByUserId(userId);
		
	}
	
	/**
	 * 查詢所有角色
	 * @return
	 */
	public List<RoleDTO> selectAll(){
		return uMapper.selectAll();
	}
	
	/**
	 * 根據角色id查詢角色
	 * @return
	 */
	public TbAuthRoleMast selectByRoleId(BigDecimal roleId){
		TbAuthRoleMast list = tbAuthRoleMastMapper.selectByPrimaryKey(roleId);
		return list;
	}
	
		
	/**
	 * 根據角色id查詢角色與包含之使用者(分頁)
	 * @return
	 */
	public List<RoleMastDTO> selectById(Integer roleId){	
		PageBounds pageBounds = getPageBounds();
		//List<RoleMastDTO> list =this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbAuthRoleMastMapper.searchRole",roleId, pageBounds);
		List<RoleMastDTO> list =this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbAuthRoleMastMapper.searchRoleWithPsn",roleId, pageBounds);
		PageList<RoleMastDTO> pageList = (PageList<RoleMastDTO>)list;
		log.debug("totaql="+pageList.getPaginator().getTotalCount());
		return pageList;
	}

		
	/**
	 * 新增角色
	 * @return
	 */
	public void insert(TbAuthRoleMast record) {
		tbAuthRoleMastMapper.insertSelective(record);
	}
	
	/**
	 * 更新角色
	 * @return
	 */
	public void update(TbAuthRoleMast record) {
		tbAuthRoleMastMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 刪除角色
	 * @return
	 */
	public void delete(BigDecimal roleId) {
		tbAuthRoleMastMapper.deleteByPrimaryKey(roleId);
	}

}
