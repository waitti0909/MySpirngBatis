package com.foya.noms.dao.st;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComAntennaMapper;
import com.foya.dao.mybatis.mapper.TbComEqpModelMapper;
import com.foya.dao.mybatis.mapper.TbSiteLocationMapper;
import com.foya.dao.mybatis.mapper.TbSiteMainMapper;
import com.foya.dao.mybatis.mapper.TbSiteSearchMapper;
import com.foya.dao.mybatis.mapper.TbSiteShareSearchMapper;
import com.foya.dao.mybatis.mapper.TbSiteSrAntCfgTempMapper;
import com.foya.dao.mybatis.mapper.TbSiteWorkMapper;
import com.foya.dao.mybatis.mapper.UTbSiteSearchMapper;
import com.foya.dao.mybatis.model.TbComAntenna;
import com.foya.dao.mybatis.model.TbComAntennaExample;
import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpModelExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.dao.mybatis.model.TbSiteLocationExample;
import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteSearch;
import com.foya.dao.mybatis.model.TbSiteSearchExample;
import com.foya.dao.mybatis.model.TbSiteSearchKey;
import com.foya.dao.mybatis.model.TbSiteShareSearchExample;
import com.foya.dao.mybatis.model.TbSiteShareSearchKey;
import com.foya.dao.mybatis.model.TbSiteSrAntCfgTemp;
import com.foya.dao.mybatis.model.TbSiteSrAntCfgTempExample;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.st.SiteSearchDTO;

@Repository
public class SearchRecordDao extends BaseDao{
	
	@Autowired
	private  UTbSiteSearchMapper uTbSiteSearchMapper;
	
	@Autowired
	private TbSiteSearchMapper tbSiteSearchMapper;
	
	@Autowired
	private TbComEqpModelMapper tbComEqpModelMapper;
	
	@Autowired
	private TbSiteMainMapper tbSiteMainMapper;
	
	@Autowired
	private TbComAntennaMapper tbComAntennaMapper;
	
	@Autowired
	private TbSiteSrAntCfgTempMapper tbSiteSrAntCfgTempMapper;

	@Autowired
	private TbSiteWorkMapper tbSiteWorkMapper;
	
	@Autowired
	private TbSiteLocationMapper tbSiteLocationMapper;
	
	@Autowired
	private TbSiteShareSearchMapper tbSiteShareSearchMapper;
	
	/**
	 * 探勘記錄查詢
	 * @param example
	 * @return
	 */
	public List<SiteSearchDTO> findSiteSearchByExample(TbSiteSearchExample example){
		return uTbSiteSearchMapper.selectByExample(example);
	}

	/**
	 * 設備機型查詢
	 * @param example
	 * @return
	 */
	public List<TbComEqpModel> findEqpModelByExample(TbComEqpModelExample example){
		return tbComEqpModelMapper.selectByExample(example);
	}
	
	/**
	 * 基站狀態查詢
	 * @param example
	 * @return
	 */
	public TbSiteMain findSiteStatusIdByExample(String workId){
		return tbSiteMainMapper.selectByPrimaryKey(workId);
	}
	
	/**
	 * 天線型號查詢
	 * @param example
	 * @return
	 */
	public List<TbComAntenna> findAntennaByExample(TbComAntennaExample example){
		return tbComAntennaMapper.selectByExample(example);
	}
	
   /**
    * 用Example查詢SITE_SEARCH
    * 利用探勘序號 srSeq 查詢 出 該筆資料 
    * @param Example
    * @return
    */
   public List<SiteSearchDTO> findSearchTableByExample(Map<String, String> map){
	   return uTbSiteSearchMapper.selectSearchTable(map);
   }
   
   /**
    * 用Example查詢SITE_SHARE_SEARCH
    * 利用探勘序號 srSeq 查詢 出 該筆資料 
    * @param Example
    * @return
    */
   public List<TbSiteShareSearchKey> findShareSearchByExample(TbSiteShareSearchExample example){
	   return tbSiteShareSearchMapper.selectByExample(example);
   }	
   
   /**
    * 用Example查詢TbSiteSrAntCfgTemp
    * 利用探勘序號 srSeq 查詢 天線組態
    * @param Example
    * @return
    */
   public List<TbSiteSrAntCfgTemp> findSiteSrAntCfgTempByExample(TbSiteSrAntCfgTempExample example){
	   return tbSiteSrAntCfgTempMapper.selectByExample(example);
   }	
   
   /**
    * 查探勘資料
    * @param example
    * @return
    */
   public List<TbSiteSearch> selectByExample(TbSiteSearchExample example ){
	   return tbSiteSearchMapper.selectByExample(example);
   }
   
   //=============================================================================
   // 尋找siteID
   public List<TbSiteMain> selectByExampleTbMain(TbSiteMainExample example ){
	   return tbSiteMainMapper.selectByExample(example);
   }
   
   //
   public List<TbSiteWork> selectByExampleTbWork(TbSiteWorkExample example ){
	   return tbSiteWorkMapper.selectByExample(example);
   } 
   
   public List<TbSiteLocation> selectByExampleTbLoc(TbSiteLocationExample example ){
	   return tbSiteLocationMapper.selectByExample(example);
   } 
   //===============================================================================
	/**
	 * 探勘 更新
	 * @return
	 */
	public int updateSearchRecord(Map<String, Object> map){
		return uTbSiteSearchMapper.updateSearchRecord(map);
	}
	
	/**
	 * 探勘 新增
	 * @return
	 */
	public int insertSearchRecord(TbSiteSearch record){
		return tbSiteSearchMapper.insertSelective(record);
	}
	

	/**
	 * 探勘 更新ByPrimaryKeySelective
	 * @return
	 */
	public int updateSearchRecordByPrimaryKeySelective(TbSiteSearch record){
		return tbSiteSearchMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * 依照TbSiteSearchKey查詢探勘
	 * @param key
	 * @return
	 */
	public TbSiteSearch findSearchRecordByPrimaryKey(TbSiteSearchKey key){
		return tbSiteSearchMapper.selectByPrimaryKey(key);
	}

}
