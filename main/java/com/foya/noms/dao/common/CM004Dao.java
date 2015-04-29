package com.foya.noms.dao.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComPoItemMapper;
import com.foya.dao.mybatis.mapper.TbComPoLineIdMapMapper;
import com.foya.dao.mybatis.mapper.TbComPoLineIdMapper;
import com.foya.dao.mybatis.mapper.TbComPoMainMapper;
import com.foya.dao.mybatis.mapper.TbComPoQuotaMapper;
import com.foya.dao.mybatis.mapper.UTbComPoItemMapper;
import com.foya.dao.mybatis.mapper.UTbComPoLineIdMapMapper;
import com.foya.dao.mybatis.mapper.UTbComPoMainMapper;
import com.foya.dao.mybatis.model.TbComPoItem;
import com.foya.dao.mybatis.model.TbComPoItemExample;
import com.foya.dao.mybatis.model.TbComPoItemKey;
import com.foya.dao.mybatis.model.TbComPoLineId;
import com.foya.dao.mybatis.model.TbComPoLineIdExample;
import com.foya.dao.mybatis.model.TbComPoLineIdMap;
import com.foya.dao.mybatis.model.TbComPoLineIdMapExample;
import com.foya.dao.mybatis.model.TbComPoMain;
import com.foya.dao.mybatis.model.TbComPoMainExample;
import com.foya.dao.mybatis.model.TbComPoQuota;
import com.foya.dao.mybatis.model.TbComPoQuotaExample;
import com.foya.dao.mybatis.model.TbComPoQuotaKey;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.basic.PoItemDTO;
import com.foya.noms.dto.basic.PoLineIdMapDTO;
import com.foya.noms.dto.common.PoMainDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class CM004Dao extends BaseDao {

	@Autowired
	private UTbComPoMainMapper uTbComPoMainMapper;
	@Autowired
	private TbComPoMainMapper tbComPoMainMapper;
	@Autowired
	private TbComPoQuotaMapper tbComPoQuotaMapper;
	@Autowired
	private TbComPoItemMapper tbComPoItemMapper;
	@Autowired
	private UTbComPoItemMapper uTbComPoItemMapper;
	@Autowired
	private TbComPoLineIdMapper tbComPoLineIdMapper;
	@Autowired
	private TbComPoLineIdMapMapper tbComPoLineIdMapMapper;
	@Autowired
	private UTbComPoLineIdMapMapper uTbComPoLineIdMapMapper;
	/**
	 * 查詢PoMain
	 * @param poNo
	 * @param year
	 * @param poName
	 * @param co_vat_No
	 * @param isTemp
	 * @return
	 */
	public List<PoMainDTO> searchPoByCond(String poNo, String year, String poName,
			String co_vat_No, String isTemp, String poDomain) {
		Map<String,String> map =new HashMap<String,String>();
		map.put("poNo", poNo);
		map.put("year", year);
		map.put("poName", poName);
		map.put("co_vat_No", co_vat_No);
		map.put("isTemp", isTemp);
		if (StringUtils.isNotEmpty(poDomain)) map.put("poDomain", poDomain);
		PageBounds pageBounds = getPageBounds();
		List<PoMainDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbComPoMainMapper.searchPoByCond", map, pageBounds);
		PageList<PoMainDTO> pageList = (PageList<PoMainDTO>)list;
		return pageList;
	}
	
	/**
	 * 用poNO查詢PoMain
	 * @param poNO
	 * @return
	 */
	public List<TbComPoMain> getPOByPoNO(String poNO){
		TbComPoMainExample example = new TbComPoMainExample();
		example.createCriteria().andPO_NOEqualTo(poNO);
		return tbComPoMainMapper.selectByExample(example);
	}
	
	/**
	 * 用Example查詢PoQuot
	 * @param example
	 * @return
	 */
	public List<TbComPoQuota> searchPoQuotaByExample(TbComPoQuotaExample example){
		return tbComPoQuotaMapper.selectByExample(example);
	}
	
	/**
	 * 新增PoMain
	 * @param record
	 * @return
	 */
	public int saveNewPoMain(TbComPoMain record){
		return tbComPoMainMapper.insertSelective(record);
	}
	
	/**
	 * 新增PoQuota
	 * @param record
	 * @return
	 */
	public int saveNewPoQuota(TbComPoQuota record){
		return tbComPoQuotaMapper.insertSelective(record);
	}
	
	/**
	 * 由KEY查詢PO配額設定檔
	 * @param tbComPoQuota
	 * @return
	 */
	public  TbComPoQuota selectByPrimaryKey(TbComPoQuotaKey tbComPoQuota){
		return tbComPoQuotaMapper.selectByPrimaryKey(tbComPoQuota);
	}
	
	/**
	 * 由KEY更新PO配額設定檔
	 * @param tbComPoQuota
	 * @return
	 */
	public int updateByPrimaryKeySelective(TbComPoQuota tbComPoQuota){
		return tbComPoQuotaMapper.updateByPrimaryKeySelective(tbComPoQuota);
	}
	
	/**
	 * update PoMain By PK
	 * @param record
	 * @return
	 */
	public int saveUpdatePoMain(TbComPoMain record){
		return tbComPoMainMapper.updateByPrimaryKeySelective(record);
	}
	
	/**
	 * update PoQutota
	 * @param record
	 * @param example
	 * @return
	 */
	public int saveUpdatePoQuota(TbComPoQuota record,TbComPoQuotaExample example){
		return tbComPoQuotaMapper.updateByExampleSelective(record,example);
	}
	
	/**
	 * 由KEY查詢PO主檔
	 * @param PoId
	 * @return
	 */
	public TbComPoMain getPoByPoId(Long PoId){
		return tbComPoMainMapper.selectByPrimaryKey(PoId);
	}
	
	/**
	 * 用Example查詢PoItem
	 * @param example
	 * @return
	 */
	public List<TbComPoItem> searchPoItemByExample(TbComPoItemExample example){
		return tbComPoItemMapper.selectByExample(example);
	}
	
	/**
	 * 新增PoQuota
	 * @param record
	 * @return
	 */
	public int saveNewPoItem(TbComPoItem record){
		return tbComPoItemMapper.insertSelective(record);
	}
	
	/**
	 * 以poId查詢有使用到的小項以及被 TB_SITE_OS_ITEM參考到次數
	 * @param poId
	 * @return
	 */
	public List<PoItemDTO> getPoitemByPoIdAndUsedBySite(String poId){
		
		return uTbComPoItemMapper.getPoitemByPoIdAndUsedBySite(poId);
	}
	
	public List<TbComPoItem> selectItemForDelete(Map<String, String> map) {
		
		return uTbComPoItemMapper.selectItemForDelete(map);
	}
	
	public void deleteByPrimaryKey(Long poId, String itemId){
		TbComPoItemKey key = new TbComPoItemKey();
		key.setITEM_ID(itemId);
		key.setPO_ID(poId);
		tbComPoItemMapper.deleteByPrimaryKey(key);
	}
	
	public List<String> selectFaCategoryByCat(HashMap<String,String> map) {
		
		return uTbComPoItemMapper.selectFaCategoryByCat(map);
	}
	
	public List<TbComPoLineId> selectPoLineIdByExample(TbComPoLineIdExample example) {
		
		return tbComPoLineIdMapper.selectByExample(example);
	}
	
	/**
	 * 依PO單號查詢PoLineIdMap
	 * @param poNo
	 * @return
	 */
	public List<PoLineIdMapDTO> selectPoLineIdMapByPoNo(String poNo) {
		return uTbComPoLineIdMapMapper.selectPoLineIdMapByPoNo(poNo);
	}
	
	/**依PO單號刪除PoLineIdMap
	 * @param poNo
	 * @return
	 */
	public int deletePoLineMapByPoNo(String poNo) {
		TbComPoLineIdMapExample example = new TbComPoLineIdMapExample();
		example.createCriteria().andPO_NOEqualTo(poNo);
		return uTbComPoLineIdMapMapper.deleteByExample(example);
	}
	
	public int saveUpdatePoLineMapping(TbComPoLineIdMap record) {
		
		return tbComPoLineIdMapMapper.insert(record);
	}
	
	/**
	 * 取PoMain最小po_year
	 * @return
	 */
	public List<PoMainDTO> selectMinYear(){
		return uTbComPoMainMapper.selectMinYear();
	}
}
