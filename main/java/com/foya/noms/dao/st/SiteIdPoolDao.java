package com.foya.noms.dao.st;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteIdPoolMapper;
import com.foya.dao.mybatis.mapper.UTbSiteIdPoolMapper;
import com.foya.dao.mybatis.model.TbSiteIdPool;
import com.foya.dao.mybatis.model.TbSiteIdPoolExample;
import com.foya.dao.mybatis.model.TbSiteIdPoolKey;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.st.SiteIdPoolDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class SiteIdPoolDao extends BaseDao {
	@Autowired
	private UTbSiteIdPoolMapper uTbSiteIdPoolMapper;
	@Autowired
	private TbSiteIdPoolMapper tbSiteIdPoolMapper;
	
	/**
	 * 查詢siteIdPool
	 * @param map
	 * @return
	 */
   public  List<SiteIdPoolDTO> findUnuseBTSSiteId(Map<String ,String> map){
	   PageBounds pageBounds = getPageBounds(map.get("sort")+"."+map.get("order"));
		List<SiteIdPoolDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbSiteIdPoolMapper.selectUnuseBTSSiteId", map, pageBounds);
		PageList<SiteIdPoolDTO> pageList = (PageList<SiteIdPoolDTO>)list;
		return pageList;
   }
   
   public List<TbSiteIdPool> findSiteIdPoolByConditions(TbSiteIdPoolExample example){
	   return tbSiteIdPoolMapper.selectByExample(example);
   }
   
   public TbSiteIdPool findSiteIdPoolByPrimaryKey(TbSiteIdPoolKey key){
	   return tbSiteIdPoolMapper.selectByPrimaryKey(key);
   }
}
