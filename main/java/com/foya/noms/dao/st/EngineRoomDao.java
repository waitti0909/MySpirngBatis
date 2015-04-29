package com.foya.noms.dao.st;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.UTbSiteLocationMapper;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.st.SiteLocationDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class EngineRoomDao extends BaseDao {
	@Autowired
	private UTbSiteLocationMapper uTbSiteLocationMapper;
	
	/**
	 * 查詢siteIdPool
	 * @param map
	 * @return
	 */
   public  List<SiteLocationDTO> findByCondition(Map<String ,String> map){
	    PageBounds pageBounds = getPageBounds();
		List<SiteLocationDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbSiteLocationMapper.selectByCondition", map, pageBounds);
		PageList<SiteLocationDTO> pageList = (PageList<SiteLocationDTO>)list;
		return pageList;
   }
}
