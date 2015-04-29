package com.foya.noms.dao.st;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComItemCatMapper;
import com.foya.dao.mybatis.mapper.TbSiteOutsourcingMapper;
import com.foya.dao.mybatis.mapper.TbSiteWorkOrderMapper;
import com.foya.dao.mybatis.mapper.TbSysOrderPageMapper;
import com.foya.dao.mybatis.mapper.UTbComCompanyMapper;
import com.foya.dao.mybatis.mapper.UTbComItemMainMapper;
import com.foya.dao.mybatis.mapper.UTbComPoItemMapper;
import com.foya.dao.mybatis.mapper.UTbComPoMainMapper;
import com.foya.dao.mybatis.mapper.UTbComPoQuotaMapper;
import com.foya.dao.mybatis.mapper.UTbSiteOsItemMapper;
import com.foya.dao.mybatis.mapper.UTbSiteOutsourcingMapper;
import com.foya.dao.mybatis.mapper.UTbSiteWorkMapper;
import com.foya.dao.mybatis.mapper.UTbSiteWorkOrderMapper;
import com.foya.dao.mybatis.model.TbComItemCat;
import com.foya.dao.mybatis.model.TbComItemCatExample;
import com.foya.dao.mybatis.model.TbSiteOutsourcing;
import com.foya.dao.mybatis.model.TbSiteOutsourcingExample;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSysOrderPage;
import com.foya.dao.mybatis.model.TbSysOrderPageExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.basic.CompanyDTO;
import com.foya.noms.dto.basic.ItemMainDTO;
import com.foya.noms.dto.basic.PoItemDTO;
import com.foya.noms.dto.common.PoMainDTO;
import com.foya.noms.dto.st.TbSiteOutsourcingDTO;

/**
 * The Class OutsourcingDao.
 */
@Repository
public class OutsourcingDao extends BaseDao {

	/** The tb site outsourcing mapper. */
	@Autowired
	private TbSiteOutsourcingMapper tbSiteOutsourcingMapper;

	/** The u tb site work order mapper. */
	@Autowired
	private UTbSiteWorkOrderMapper uTbSiteWorkOrderMapper;

	/** The u tb site work mapper. */
	@Autowired
	private UTbSiteWorkMapper uTbSiteWorkMapper;

	/** The u tb com company mapper. */
	@Autowired
	private UTbComCompanyMapper uTbComCompanyMapper;

	/** The u tb com po main mapper. */
	@Autowired
	private UTbComPoMainMapper uTbComPoMainMapper;

	/** The u tb com po item mapper. */
	@Autowired
	private UTbComPoItemMapper uTbComPoItemMapper;

	/** The u tb com item main mapper. */
	@Autowired
	private UTbComItemMainMapper uTbComItemMainMapper;

	/** The u tb site outsourcing mapper. */
	@Autowired
	private UTbSiteOutsourcingMapper uTbSiteOutsourcingMapper;

	/** The u tb site os item mapper. */
	@Autowired
	private UTbSiteOsItemMapper uTbSiteOsItemMapper;

	/** The u tb com po quota mapper. */
	@Autowired
	private UTbComPoQuotaMapper uTbComPoQuotaMapper;
	/** The u tb com po quota mapper. */

	/** The tb site work order mapper. */
	@Autowired
	private TbSiteWorkOrderMapper tbSiteWorkOrderMapper;

	/** The tb sys order page mapper. */
	@Autowired
	private TbSysOrderPageMapper tbSysOrderPageMapper;

	/** The TbComItemCatMapper. */
	@Autowired
	private TbComItemCatMapper tbComItemCatMapper;

	public int insert(TbSiteOutsourcing record) {
		return tbSiteOutsourcingMapper.insertSelective(record);
	}

	public int update(TbSiteOutsourcing record) {
		return tbSiteOutsourcingMapper.updateByPrimaryKey(record);
	}

	/**
	 * 用Example查詢OutSourcing.
	 * 
	 * @param example
	 *            the example
	 * @return the list
	 */
	public List<TbSiteOutsourcing> findSiteOutSoureByExample(TbSiteOutsourcingExample example) {
		return tbSiteOutsourcingMapper.selectByExample(example);
	}

	/**
	 * 用Example查詢SITE_WORKID_ORDER.
	 * 
	 * @param map
	 *            the map
	 * @return the list
	 */
	public List<TbSiteOutsourcingDTO> findSiteWorkOrderSearchByExample(Map<String, String> map) {
		return uTbSiteWorkOrderMapper.selectSiteWorkOrderByOutSourcing(map);
	}

	/**
	 * 由派工單號取得委外派工申請單.
	 * 
	 * @author Miles Chang 2014.12.18
	 * @param osId
	 *            the os id
	 * @return the tb site outsourcing
	 */
	public TbSiteOutsourcing findByPrimaryKey(String osId) {
		return tbSiteOutsourcingMapper.selectByPrimaryKey(osId);
	}

	/**
	 * 更新委外派工申請單.
	 * 
	 * @author Miles Chang 2014.12.19
	 * @param tbSiteOutsourcing
	 *            the tb site outsourcing
	 * @return the int
	 */
	public int updateByPrimaryKeySelective(TbSiteOutsourcing tbSiteOutsourcing) {
		return tbSiteOutsourcingMapper.updateByPrimaryKeySelective(tbSiteOutsourcing);
	}

	/**
	 * 用Example查詢SITE_WORKID_ORDER.
	 * 
	 * @param map
	 *            the map
	 * @return the list *test*多
	 */
	public List<CompanyDTO> selectTbComCompany() {
		return uTbComCompanyMapper.selectTbComCompany();
	}

	/**
	 * 用map查詢TB_TbComCompany.
	 * 
	 * @param map
	 *            the map
	 * @return the list *test*.
	 */
	public List<PoMainDTO> selectTbComPoMain(Map<String, String> map) {
		return uTbComPoMainMapper.selectTbComPoMain(map);
	}

	/**
	 * 查詢一般廠商
	 * 
	 * @param map
	 *            the map
	 * @return the list *test*多
	 */
	public List<CompanyDTO> selectTbComCompanybyGeneral() {
		return uTbComCompanyMapper.selectTbComCompanybyGeneral();
	}

	/**
	 * 用map查詢TbComPoItem.
	 * 
	 * @param map
	 *            the map
	 * @return the list *test*.
	 */
	public List<PoItemDTO> selectTotailAccount(Map<String, String> map) {
		return uTbComPoItemMapper.selectTotailAccount(map);
	}

	/**
	 * 用map查詢TbComPoItem.
	 * 
	 * @param map
	 *            the map
	 * @return the list *test*多
	 */
	public List<PoItemDTO> selectItemIdByOsId(Map<String, String> map) {
		return uTbComPoItemMapper.selectItemIdByOsId(map);
	}

	/**
	 * 用map查詢TbComItemMain.
	 * 
	 * @param map
	 *            the map
	 * @return the list *test*多
	 */
	public List<ItemMainDTO> selectItemMainItem(Map<String, String> map) {
		return uTbComItemMainMapper.selectItemMainItem(map);
	}

	/**
	 * 用map查詢TbComItemMain.
	 * 
	 * @param map
	 *            the map
	 * @return the tb com item main *test*多
	 */
	public ItemMainDTO selectMainItem(Map<String, String> map) {
		return uTbComItemMainMapper.selectMainItem(map);
	}

	/**
	 * 委外申請- 儲存的時候 有資料 更新資料
	 * 
	 * @param map
	 *            the map
	 * @return the int *test*
	 */
	public int updateOutSourcing(Map<String, String> map) {
		return uTbSiteOutsourcingMapper.updateOutSourcing(map);
	}

	/**
	 * 委外申請- 儲存的資後 無資料 就新增
	 * 
	 * @param map
	 *            the map
	 * @return the int
	 */
	public int saveOutSourcing(Map<String, String> map) {
		return uTbSiteOutsourcingMapper.saveOutSourcing(map);
	}

	/**
	 * 委外申請-儲存.
	 * 
	 * @param map
	 *            the map
	 * @return the int
	 */
	public int updateSiteOsItem(Map<String, String> map) {
		return uTbSiteOsItemMapper.updateSiteOsItem(map);
	}

	/**
	 * 委外申請-儲存.
	 * 
	 * @param map
	 *            the map
	 * @return the int
	 */
	public int saveSiteOsItem(Map<String, String> map) {
		return uTbSiteOsItemMapper.saveSiteOsItem(map);
	}

	/**
	 * 委外申請-儲存(刪除).
	 * 
	 * @param map
	 *            the map
	 * @return the int
	 */
	public int deleteSiteOsItem(Map<String, String> map) {
		return uTbSiteOsItemMapper.deleteSiteOsItem(map);
	}

	/**
	 * 用map查詢TbSiteOutsourcing 分區.
	 * 
	 * @param map
	 *            the map
	 * @return the list
	 */
	public Long sumPayAmountbyOutcourcing(Map<String, String> map) {
		return uTbSiteOutsourcingMapper.sumPayAmountbyOutcourcing(map);
	}

	/**
	 * 用map查詢TbSiteOutsourcing 分區.
	 * 
	 * @param map
	 *            the map
	 * @return the list
	 */
	public Long sumPayAmountNoAreabyOutcourcing(Map<String, String> map) {
		return uTbSiteOutsourcingMapper.sumPayAmountNoAreabyOutcourcing(map);
	}

	/**
	 * 由PK查詢工單.
	 * 
	 * @param orderId
	 *            the order id
	 * @return the tb site work order
	 */
	public TbSiteWorkOrder selectByPrimaryKey(String orderId) {
		return tbSiteWorkOrderMapper.selectByPrimaryKey(orderId);
	}

	/**
	 * Select by example.
	 * 
	 * @param example
	 *            the example
	 * @return the list
	 */
	public List<TbSysOrderPage> selectByExample(TbSysOrderPageExample example) {
		return tbSysOrderPageMapper.selectByExample(example);
	}

	public List<TbComItemCat> selectComItemCatByExample(TbComItemCatExample itemCat) {
		return tbComItemCatMapper.selectByExample(itemCat);
	}

}
