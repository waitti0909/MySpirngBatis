package com.foya.noms.dao.basic;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComItemCatMapper;
import com.foya.dao.mybatis.mapper.TbComItemMainMapper;
import com.foya.dao.mybatis.mapper.TbComPoItemMapper;
import com.foya.dao.mybatis.mapper.UTbComItemMainMapper;
import com.foya.dao.mybatis.model.TbComItemCat;
import com.foya.dao.mybatis.model.TbComItemCatExample;
import com.foya.dao.mybatis.model.TbComItemMain;
import com.foya.dao.mybatis.model.TbComItemMainExample;
import com.foya.dao.mybatis.model.TbComPoItemExample;
import com.foya.exception.DataExistsException;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.basic.ItemCatDTO;
import com.foya.noms.dto.basic.ItemMainDTO;
import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class CM003Dao extends BaseDao {
	
	@Autowired
	private TbComItemCatMapper tbComItemCatMapper;
	@Autowired
	private TbComItemMainMapper tbComItemMainMapper;
	@Autowired
	private UTbComItemMainMapper uTbComItemMainMapper;
	@Autowired
	private TbComPoItemMapper tbComPoItemMapper;
	
	/**
	 * 依查詢條件查詢PO單
	 * @param example
	 * @return
	 */
	public int searchPoItemUsedByItemId(TbComPoItemExample example) {
		return tbComPoItemMapper.countByExample(example);
	}
	
	/**
	 * 查詢工項
	 * @param example
	 * @return
	 */
	public List<TbComItemCat> selectByExample(TbComItemCatExample example){
		return tbComItemCatMapper.selectByExample(example);
	}
	
	public List<TbComItemMain> selectItemMainByExample(TbComItemMainExample example){
		return tbComItemMainMapper.selectByExample(example);
	}
	
	public TbComItemCat selectByPrimaryKey(String catId) {
		return tbComItemCatMapper.selectByPrimaryKey(Long.parseLong(catId));
	}
	
	public int saveNewItemCategory(TbComItemCat cat) {
		return tbComItemCatMapper.insertSelective(cat);
	}
	
	public TbComItemMain selectItemMainItemByItemId(String itemId){
		return tbComItemMainMapper.selectByPrimaryKey(itemId);
		
	}
	
	public List<ItemMainDTO> searchByCat(HashMap<String,String> dataObj){
		String sortString = "SORT, ENABLED.DESC";
		PageBounds pageBounds = getPageBounds(sortString);

		List<ItemMainDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbComItemMainMapper.searchByCat", dataObj, pageBounds);

		PageList<ItemMainDTO> pageList = (PageList<ItemMainDTO>) list;
		
		return pageList;		
	}
	
	/**
	 * 查詢所有中項之清單
	 * @return
	 */
	public List<ItemCatDTO> getAllSubItemCat(){
//		String sortString = "CAT_NO";
//		HashMap<String,String> dataObj = new HashMap<String,String>();
//		PageBounds pageBounds = new PageBounds(Order.formString(sortString));

		List<ItemCatDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbComItemCatMapper.getAllSubItemCat");
		return list;		
	}
	
	public List<ItemMainDTO> searchByCatForExcel(HashMap<String,String> dataObj){
		String sortString = "SORT";
		PageBounds pageBounds = new PageBounds(Order.formString(sortString));

		List<ItemMainDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbComItemMainMapper.searchByCat", dataObj, pageBounds);

		return list;		
	}
	
	/**
	 * 以 itemNo 查詢 MainItem 且 ENBLED為Y
	 * @param itemNo
	 * @return
	 */
	public List<TbComItemMain> getMainItemByItemNo(String itemNo) {
		TbComItemMainExample example = new TbComItemMainExample();
		example.createCriteria().andITEM_NOEqualTo(itemNo).andENABLEDEqualTo("Y");
		
		return tbComItemMainMapper.selectByExample(example);
	}
	
	public int insertItemMain(TbComItemMain record) {
		return tbComItemMainMapper.insertSelective(record);
	}
	
	public int updateItemMain(TbComItemMain record) {
		return tbComItemMainMapper.updateByPrimaryKeySelective(record);
	}
	
	public int updateTbComItemCat(TbComItemCat record) {
		return tbComItemCatMapper.updateByPrimaryKeySelective(record);
	}
	
	public static void main(String[] args) {
		CM003Dao dao = new CM003Dao();
		List<TbComItemMain> list = dao.getMainItemByItemNo("100");
		
		for (TbComItemMain item : list) {
			if (("Y").equalsIgnoreCase(item.getENABLED())) {
				throw new DataExistsException("已有啟動的相同項次存在");
			}
		}
		
	}

}
