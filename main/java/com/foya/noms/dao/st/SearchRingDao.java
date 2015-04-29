
package com.foya.noms.dao.st;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteSearchMapper;
import com.foya.dao.mybatis.mapper.TbSiteSearchRingMapper;
import com.foya.dao.mybatis.mapper.UTbSiteSearchMapper;
import com.foya.dao.mybatis.mapper.UTbSiteSearchRingMapper;
import com.foya.dao.mybatis.model.TbSiteSearch;
import com.foya.dao.mybatis.model.TbSiteSearchExample;
import com.foya.dao.mybatis.model.TbSiteSearchRing;
import com.foya.dao.mybatis.model.TbSiteSearchRingExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.st.SearchRingDTO;
import com.foya.noms.dto.st.SiteSearchDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class SearchRingDao extends BaseDao {
	@Autowired
	private UTbSiteSearchRingMapper uTbSiteSearchRingMapper;
	@Autowired
	private TbSiteSearchRingMapper tbSiteSearchRingMapper;
	@Autowired
	private TbSiteSearchMapper tbSiteSearchMapper;
	@Autowired
	private UTbSiteSearchMapper uTbSiteSearchMapper;
	/**
	 * 查詢所有search Ring
	 * @param map
	 * @return
	 */
   public List<SearchRingDTO> findSearchRingList(Map<String ,String> map){
	    PageBounds pageBounds = getPageBounds();
		List<SearchRingDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbSiteSearchRingMapper.selectSearchRingList", map, pageBounds);
		PageList<SearchRingDTO> pageList = (PageList<SearchRingDTO>)list;
		return pageList;
   }

   /**
    * 用PrimaryKey查詢SearchRing
    * @param SR_ID
    * @return
    */
   public TbSiteSearchRing findSearchRingByPrimaryKey(String SR_ID){
	   return tbSiteSearchRingMapper.selectByPrimaryKey(SR_ID);
   }
   
   /**
    *查詢SearchRing
    * @param example
    * @return
    */
   public List<TbSiteSearchRing> findSearchRingByExample(TbSiteSearchRingExample example){
	   return tbSiteSearchRingMapper.selectByExample(example);
   }
   
   /**
    * 用Example查詢SiteSearch
    * @param Example
    * @return
    */
   public List<TbSiteSearch> findSiteSearchByExample(TbSiteSearchExample example){
	   return tbSiteSearchMapper.selectByExample(example);
   }
   
   /**
    * 用PrimaryKey查詢SiteSearch
    * @param key
    * @return
    */
   public SiteSearchDTO findSiteSearchByPk(String srId, String srSeq){
	   return uTbSiteSearchMapper.selectByPk(srId, srSeq);
   }
   
   /**
    * 新增SearchRing
    * @param record
    * @return
    */
   public int insert(TbSiteSearchRing record){
	   return tbSiteSearchRingMapper.insertSelective(record);
   }
   
   /**
    * 修改SearchRing
    * @param record
    * @return
    */
   public int update(TbSiteSearchRing record){
	   return tbSiteSearchRingMapper.updateByPrimaryKeySelective(record);
   }
   
   /**
    * 修改SiteSearch
    * @param record
    * @return
    */
   public int updateSiteSearch(TbSiteSearch record){
	   return tbSiteSearchMapper.updateByPrimaryKeySelective(record);
   }
   
}

