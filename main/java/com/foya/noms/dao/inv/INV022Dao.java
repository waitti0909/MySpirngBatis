package com.foya.noms.dao.inv;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.UTbInvMaterialMapper;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvMaterialDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;


@Repository
public class INV022Dao extends BaseDao {
	
	@Autowired
	private UTbInvMaterialMapper uMaterialMapper;
	
	public List<TbInvMaterialDTO> search(HashMap<String,String> dataObj){
		PageBounds pageBounds = getPageBounds("catg1_code");

		List<TbInvMaterialDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbInvMaterialMapper.searchMaterial", dataObj,  pageBounds);

		PageList<TbInvMaterialDTO> pageList = (PageList<TbInvMaterialDTO>) list;
		
		return pageList;		
	}
	
	public List<TbInvMaterialDTO> getMaterialDtoData(HashMap<String,Object> dataObj){
		
		return uMaterialMapper.searchMaterial(dataObj);		
	} 
}
