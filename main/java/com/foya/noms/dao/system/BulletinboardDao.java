package com.foya.noms.dao.system;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSysBulletinboardMapper;
import com.foya.dao.mybatis.mapper.UTbSysBulletinboardMapper;
import com.foya.dao.mybatis.model.TbSysBulletinboard;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.system.BulletinboardDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class BulletinboardDao extends BaseDao{
	
	@Autowired
	private UTbSysBulletinboardMapper uTbSysBulletinboardMapper;
	
	@Autowired
	private TbSysBulletinboardMapper tbSysBulletinboardMapper;
	
	/**
	 * By登入日期查詢公佈欄
	 * @param loginDate
	 * @return
	 */
	public List<BulletinboardDTO> selectBulletinboardByDate(Timestamp loginDate){
		
		return uTbSysBulletinboardMapper.searchBulletinboardByDate(loginDate);
		
	}
	
	/**
	 * By公佈欄ID查詢詳細資料
	 * @param loginDate
	 * @return
	 */
	public BulletinboardDTO selectBulletinboardById(BigDecimal bulletinId){
		
		return uTbSysBulletinboardMapper.searchBulletinboardById(bulletinId);
		
	}
	
	/**
	 * 根據查詢條件查詢公佈欄(分頁)
	 * @param
	 * @return
	 */
	public List<BulletinboardDTO> selectBulletinboardByCond(Map<String, String> record){
		PageBounds pageBounds = getPageBounds(record.get("sort")+"."+record.get("order"));
		List<BulletinboardDTO> list =this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbSysBulletinboardMapper.searchBulletinboardByCond",record, pageBounds);
		PageList<BulletinboardDTO> pageList = (PageList<BulletinboardDTO>)list;
		log.debug("totaql="+pageList.getPaginator().getTotalCount());
		return pageList;
	}
	
	/**
	 * 公佈欄新增
	 * @param
	 * @return
	 */
	public int addBulletinboard(TbSysBulletinboard bulletinRecod){		
		return tbSysBulletinboardMapper.insertSelective(bulletinRecod);		
	}
	
	/**
	 * 公佈欄修改
	 * @param
	 * @return
	 */
	public int editBulletinboard(TbSysBulletinboard bulletinRecod){		
		return tbSysBulletinboardMapper.updateByPrimaryKeySelective(bulletinRecod);		
	}
	
	/**
	 * 公佈欄刪除
	 * @param
	 * @return
	 */
	public void deleteBulletinboard(BigDecimal bulletinID){		
		tbSysBulletinboardMapper.deleteByPrimaryKey(bulletinID);		
	}

}
