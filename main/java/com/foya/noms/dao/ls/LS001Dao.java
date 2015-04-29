package com.foya.noms.dao.ls;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComEqpModelMapper;
import com.foya.dao.mybatis.mapper.TbComEqpTypeMapper;
import com.foya.dao.mybatis.mapper.TbLsAppMapper;
import com.foya.dao.mybatis.mapper.TbLsAvgRentMapper;
import com.foya.dao.mybatis.mapper.TbLsClauseMapper;
import com.foya.dao.mybatis.mapper.TbLsElecRangeMapper;
import com.foya.dao.mybatis.mapper.TbLsLessorMapper;
import com.foya.dao.mybatis.mapper.TbLsLocElecMapper;
import com.foya.dao.mybatis.mapper.TbLsLocPaymentMapper;
import com.foya.dao.mybatis.mapper.TbLsLocationMapper;
import com.foya.dao.mybatis.mapper.TbLsMainMapper;
import com.foya.dao.mybatis.mapper.TbLsResExchAlocMapper;
import com.foya.dao.mybatis.mapper.TbLsResExchMapper;
import com.foya.dao.mybatis.mapper.TbLsRewardMapper;
import com.foya.dao.mybatis.mapper.TbLsSiteMapper;
import com.foya.dao.mybatis.mapper.TbLsTrmnElecMapper;
import com.foya.dao.mybatis.mapper.TbLsTrmnMapper;
import com.foya.dao.mybatis.mapper.TbSysLookupMapper;
import com.foya.dao.mybatis.mapper.UTbLsAppMapper;
import com.foya.dao.mybatis.mapper.UTbLsLessorMapper;
import com.foya.dao.mybatis.mapper.UTbLsLocElecMapper;
import com.foya.dao.mybatis.mapper.UTbLsLocPaymentMapper;
import com.foya.dao.mybatis.mapper.UTbLsLocationMapper;
import com.foya.dao.mybatis.mapper.UTbLsMainMapper;
import com.foya.dao.mybatis.mapper.UTbLsResExchAlocMapper;
import com.foya.dao.mybatis.mapper.UTbLsResExchMapper;
import com.foya.dao.mybatis.mapper.UTbLsRewardMapper;
import com.foya.dao.mybatis.mapper.UTbLsSiteMapper;
import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpModelExample;
import com.foya.dao.mybatis.model.TbComEqpType;
import com.foya.dao.mybatis.model.TbComEqpTypeExample;
import com.foya.dao.mybatis.model.TbLsApp;
import com.foya.dao.mybatis.model.TbLsAppExample;
import com.foya.dao.mybatis.model.TbLsAvgRent;
import com.foya.dao.mybatis.model.TbLsAvgRentExample;
import com.foya.dao.mybatis.model.TbLsElecRange;
import com.foya.dao.mybatis.model.TbLsElecRangeExample;
import com.foya.dao.mybatis.model.TbLsLessor;
import com.foya.dao.mybatis.model.TbLsLessorExample;
import com.foya.dao.mybatis.model.TbLsLocElec;
import com.foya.dao.mybatis.model.TbLsLocElecExample;
import com.foya.dao.mybatis.model.TbLsLocPayment;
import com.foya.dao.mybatis.model.TbLsLocPaymentExample;
import com.foya.dao.mybatis.model.TbLsLocation;
import com.foya.dao.mybatis.model.TbLsLocationExample;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbLsMainExample;
import com.foya.dao.mybatis.model.TbLsMainKey;
import com.foya.dao.mybatis.model.TbLsResExch;
import com.foya.dao.mybatis.model.TbLsResExchAloc;
import com.foya.dao.mybatis.model.TbLsResExchAlocExample;
import com.foya.dao.mybatis.model.TbLsResExchExample;
import com.foya.dao.mybatis.model.TbLsReward;
import com.foya.dao.mybatis.model.TbLsRewardExample;
import com.foya.dao.mybatis.model.TbLsSite;
import com.foya.dao.mybatis.model.TbLsSiteExample;
import com.foya.dao.mybatis.model.TbLsTrmn;
import com.foya.dao.mybatis.model.TbLsTrmnElec;
import com.foya.dao.mybatis.model.TbLsTrmnElecExample;
import com.foya.dao.mybatis.model.TbLsTrmnKey;
import com.foya.dao.mybatis.model.TbSysLookup;
import com.foya.dao.mybatis.model.TbSysLookupExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.ls.LeaseDTO;
import com.foya.noms.dto.ls.LeaseDomainMoneyDTO;
import com.foya.noms.dto.ls.LeaseLocationDTO;
import com.foya.noms.dto.ls.LeaseMainDTO;
import com.foya.noms.dto.ls.LeaseTerminalDTO;
import com.foya.noms.dto.ls.TbLsLocPaymentDTO;
import com.foya.noms.dto.ls.TbLsLocationCompleteDTO;
import com.foya.noms.dto.ls.TbLsResExchDTO;
import com.foya.noms.dto.ls.TbLsRewardDTO;
import com.foya.noms.dto.pay.TbLsLocElecDTO;
import com.foya.noms.dto.pay.TbLsSiteDTO;
import com.foya.noms.enums.LsEnumCommon;
import com.foya.noms.enums.LsPaymentItem;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class LS001Dao extends BaseDao {

	@Autowired
	private UTbLsAppMapper uTbLsAppMapper;
	@Autowired
	private TbLsRewardMapper tbLsRewardMapper;
	@Autowired
	private TbLsLessorMapper tbLsLessorMapper;
	@Autowired
	private TbLsMainMapper tbLsMainMapper;
	@Autowired
	private TbLsLocElecMapper tbLsLocElecMapper;
	@Autowired
	private UTbLsLocElecMapper uTbLsLocElecMapper;
	@Autowired
	private TbLsAppMapper tbLsAppMapper;
	@Autowired
	private TbLsTrmnMapper tbLsTrmnMapper;
	@Autowired
	private TbLsTrmnElecMapper tbLsTrmnElecMapper;
	@Autowired
	private UTbLsLocationMapper uTbLsLocationMapper;
	@Autowired
	private TbLsLocationMapper tbLsLocationMapper;
	@Autowired
	private TbLsSiteMapper tbLsSiteMapper;
	@Autowired
	private TbLsLocPaymentMapper tbLsLocPaymentMapper;
	@Autowired
	private TbLsResExchMapper tbLsResExchMapper;
	@Autowired
	private TbLsResExchAlocMapper tbLsResExchAlocMapper;
	@Autowired
	private UTbLsMainMapper uTbLsMainMapper;
	@Autowired
	private UTbLsSiteMapper uTbLsSiteMapper;
	@Autowired
	private TbSysLookupMapper tbSysLookupMapper;
	@Autowired
	private TbComEqpTypeMapper tbComEqpTypeMapper;
	@Autowired
	private TbComEqpModelMapper tbComEqpModelMapper;
	@Autowired
	private TbLsAvgRentMapper tbLsAvgRentMapper;
	@Autowired
	private UTbLsLocPaymentMapper uTbLsLocPaymentMapper;
	@Autowired
	private TbLsClauseMapper tbLsClauseMapper;
	@Autowired
	private TbLsElecRangeMapper tbLsElecRangeMapper;
	@Autowired
	private UTbLsResExchMapper uTbLsResExchMapper;
	@Autowired
	private UTbLsLessorMapper uTbLsLessorMapper;
	@Autowired
	private UTbLsRewardMapper uTbLsRewardMapper;
	@Autowired
	private UTbLsResExchAlocMapper uTbLsResExchAlocMapper;
	/**
	 * 依條件查詢合約清單
	 * 
	 * @param opDomain
	 * @param lsNo
	 * @param btsSiteId
	 * @param expiredMonths
	 * @param appUser
	 * @param dealUser
	 * @param lsType
	 * @param appStatus
	 * @param lsStatus
	 * @return
	 */
	public List<LeaseDTO> searchByCond(Map<String,Object> dataObj) {

		String sortString = "CR_TIME.DESC, LS_NO";
		PageBounds pageBounds = getPageBounds(sortString);

		List<LeaseDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbLsAppMapper.searchByCond", dataObj, pageBounds);

		PageList<LeaseDTO> pageList = (PageList<LeaseDTO>) list;
		
		return pageList;	
	}

	/**
	 * TB_LS_REWARD Example
	 * 
	 * @param example
	 * @return
	 */
	public List<TbLsReward> lsRewardSelByEx(TbLsRewardExample example) {
		return tbLsRewardMapper.selectByExample(example);
	}

	/**
	 * 新增一筆資料至 TB_LS_REWARD
	 * 
	 * @param tbLsReward
	 */
	public void insertLsReward(TbLsReward tbLsReward) {
		tbLsRewardMapper.insertSelective(tbLsReward);
	}

	/**
	 * 合約作廢
	 * 
	 * @param appSeq
	 * @return
	 */
	public int cancelLeaseApp(TbLsApp record) {
		return uTbLsAppMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * TB_LS_LESSOR Example 出租人查詢
	 * 
	 * @param example
	 * @return
	 */
	public List<TbLsLessor> selectLessorByExample(TbLsLessorExample example) {
		return tbLsLessorMapper.selectByExample(example);
	}
	
	/**
	 * TB_LS_LOC Example 站點付款明細檔查詢
	 * 
	 * @param example
	 * @return
	 */
	public List<TbLsLocPayment> selectLocPaymentByExample(TbLsLocPaymentExample example) {
		return tbLsLocPaymentMapper.selectByExample(example);
	}

	/**
	 * TB_LS_LOC_ELEC 查詢
	 * 
	 * @param example
	 * @return
	 */
	public List<TbLsLocElec> searchLsLocElecByExample(TbLsLocElecExample example) {
		return tbLsLocElecMapper.selectByExample(example);
	}

	/**
	 * 新增一筆資料至 TB_LS_APP
	 * 
	 * @param tbLsApp
	 */
	public void insertLeaseApp(TbLsApp tbLsApp) {
		tbLsAppMapper.insertSelective(tbLsApp);
	}

	/**
	 * 新增一筆資料至 TB_LS_TRMN
	 * 
	 * @param leaseTerminalDTO
	 */
	public void insertLsTrmn(LeaseTerminalDTO leaseTerminalDTO) {
		tbLsTrmnMapper.insertSelective(leaseTerminalDTO);
	}

	/**
	 * 新增一筆資料至 TB_LS_TRMN_ELEC
	 * 
	 * @param tbLsTrmnElec
	 */
	public void insertLsTrmnElec(TbLsTrmnElec tbLsTrmnElec) {
		tbLsTrmnElecMapper.insertSelective(tbLsTrmnElec);
	}

	/**
	 * 新增一筆資料至 TB_LS_MAIN
	 * 
	 * @param tbLsMain
	 */
	public void insertLsMain(TbLsMain tbLsMain) {
		tbLsMainMapper.insertSelective(tbLsMain);
	}

	/**
	 * 新增一筆資料至 TB_LS_LESSOR
	 * 
	 * @param tbLsMain
	 */
	public void insertLsLessor(TbLsLessor tbLsLessor) {
		tbLsLessorMapper.insertSelective(tbLsLessor);
	}

	// ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓Update↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	/**
	 * 依lsNo, lsVer取得LeaseMainDTO
	 * 
	 * @param lsNo
	 * @param lsVer
	 */
	public LeaseMainDTO getLsMainByNo(String lsNo, String lsVer) {

		return uTbLsMainMapper.getLsMainByNo(lsNo, lsVer);
	}
	
	
	/**
	 * 依lsNo, lsVer取得LeaseMainDTO
	 * 
	 * @param lsNo
	 * @param lsVer
	 */
	public LeaseMainDTO getLsMainAddedByAppSeq(String appSeq) {

		return uTbLsMainMapper.getLsMainAddedByAppSeq(appSeq);
	}

	/**
	 * 依lsNo, lsVer取得List<TbLsLessor>
	 * 
	 * @param lsNo
	 * @param lsVer
	 */
	public List<TbLsLessor> getLsLessorByNo(String lsNo, String lsVer) {
	
		return uTbLsLessorMapper.searchLsLessorByNoVer(lsNo, lsVer);
	}

	/**
	 * 依lsNo, lsVer取得List<TbLsReward>
	 * 
	 * @param lsNo
	 * @param lsVer
	 */
	public List<TbLsRewardDTO> getLsRewardByNo(String lsNo, String lsVer) {
		HashMap<String, String> dataObj = new HashMap<String, String>();
		dataObj.put("lsNo", lsNo);
		dataObj.put("lsVer", lsVer);
		return uTbLsRewardMapper.getLsRewardByLsNoMaxVer(dataObj);
	}
	/**
	 * 依lsNo, lsVer取得List<TbLsReward>
	 * 
	 * @param lsNo
	 * @param lsVer
	 */
	public List<TbLsReward> getLsRewardByNoVer(String lsNo, String lsVer) {
		HashMap<String, String> dataObj = new HashMap<String, String>();
		dataObj.put("lsNo", lsNo);
		dataObj.put("lsVer", lsVer);
		return uTbLsRewardMapper.getLsRewardByLsNoVer(dataObj);
	}
	/**
	 * 依lsNo, lsVer刪除 TbLsLessor
	 * 
	 * @param lsNo
	 * @param lsVer
	 */
	public void deleteLessorByLsNo(String lsNo, String lsVer) {
		TbLsLessorExample example = new TbLsLessorExample();
		example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		tbLsLessorMapper.deleteByExample(example);
	}

	/**
	 * 依lsNo, lsVer刪除 TbLsReward
	 * 
	 * @param lsNo
	 * @param lsVer
	 */
	public void deleteRewardByLsNo(String lsNo, String lsVer) {
		TbLsRewardExample example = new TbLsRewardExample();
		example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		tbLsRewardMapper.deleteByExample(example);
	}

	/**
	 * 更新一筆資料至 TB_LS_MAIN
	 * 
	 * @param record 更新值
	 */
	public void updateLeaseMain(LeaseMainDTO record, TbLsMainExample example) {
		
//		tbLsMainMapper.updateByExampleSelective(record, example);
		tbLsMainMapper.updateByExample(record, example);
	}
	// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑Update↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/**
	 * 更新一筆資料至 TB_LS_MAIN 會判斷如是空值不更新該欄位
	 * @param record 更新值
	 */
	public void updatetblsMainNotAll(LeaseMainDTO record, TbLsMainExample example) {
		tbLsMainMapper.updateByExampleSelective(record, example);
	}
	
	
	/**
	 * TB_LS_APP 資料查詢
	 * 
	 * @param example
	 *            查詢條件
	 * @return
	 */
	public List<TbLsApp> searchLsApp(TbLsAppExample example) {
		return tbLsAppMapper.selectByExample(example);
	}

	/**
	 * TB_LS_TRMN 資料查詢
	 * 
	 * @param key
	 *            用pk查詢
	 * @return
	 */
	public TbLsTrmn searchLsTrmn(TbLsTrmnKey key) {
		return tbLsTrmnMapper.selectByPrimaryKey(key);
	}

	/**
	 * TB_LS_TRMN_ELEC 資料查詢
	 * 
	 * @param example
	 *            查詢條件
	 * @return
	 */
	public List<TbLsTrmnElec> searchLsTrmnElec(TbLsTrmnElecExample example) {
		return tbLsTrmnElecMapper.selectByExample(example);
	}

	/**
	 * 更新一筆資料至 TB_LS_APP
	 * 
	 * @param record
	 *            更新值
	 * @param example
	 *            更新條件
	 */
	public void updateLeaseApp(TbLsApp record, TbLsAppExample example) {
		tbLsAppMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 更新一筆資料至 TB_LS_TRMN
	 * 
	 * @param dto
	 */
	public void updateLsTrmn(LeaseTerminalDTO dto) {
		tbLsTrmnMapper.updateByPrimaryKeySelective(dto);
	}

	/**
	 * 更新一筆資料至 TB_LS_TRMN_ELEC
	 * 
	 * @param record
	 */
	public void updateLsTrmnElec(TbLsTrmnElec record) {
		tbLsTrmnElecMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 取得搜尋條件的所有站點
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @return
	 */
	public List<LeaseLocationDTO> getLeaseLocation(String lsNo, String lsVer,String domain) {
		return uTbLsLocationMapper.getLeaseLocation(lsNo, lsVer,domain);
	}
	
	/**
	 * 取得搜尋條件的所有站點
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @return
	 */
	public List<LeaseLocationDTO> getLeaseLocationAdded(String appSeq,String domain) {
		return uTbLsLocationMapper.getLeaseLocationAdded(appSeq,domain);
	}

	
	

	/**
	 * 搜尋 tb_ls_location 與 tb_ls_main 的資料
	 * 
	 * @param locationId
	 *            站點編號
	 * @return
	 */
	public List<TbLsLocationCompleteDTO> getLeaseByLocation(String locationId) {
		return uTbLsLocationMapper.getLeaseByLocation(locationId, LsEnumCommon.LsStatusEnum.lsStatus01.getLsStatus());
	}

	/**
	 * 尋找 TB_LS_MAIN 的唯一資料
	 * 
	 * @param key
	 *            TbLsMainKey
	 * @return
	 */
	public TbLsMain searchLsMainKey(TbLsMainKey key) {
		return tbLsMainMapper.selectByPrimaryKey(key);
	}

	/**
	 * 新增 TB_LS_LOCATION 一筆資料
	 * 
	 * @param record
	 *            LeaseLocationDTO
	 */
	public void saveNewLeaseLocation(LeaseLocationDTO record) {
		uTbLsLocationMapper.insertSelective(record);
	}

	/**
	 * 新增 TB_LS_LOCATION 一筆資料
	 * 
	 * @param record
	 *            LeaseLocationDTO
	 */
	public void saveNewLeaseLocationByTbLsLocation(TbLsLocation record) {
		tbLsLocationMapper.insertSelective(record);
	}
	
	/**
	 * 刪除 TB_LS_LOCATION 指定條件的資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 */
	public void deleteLeaseLocation(String lsNo, String lsVer, String locId) {
		TbLsLocationExample example = new TbLsLocationExample();
		example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer).andLOCATION_IDEqualTo(locId);
		uTbLsLocationMapper.deleteByExample(example);
	}

	/**
	 * 刪除  TB_LS_SITE 指定條件的資料, null
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 */
	public void deleteLsSite(String lsNo, String lsVer, String locId,LsPaymentItem pItem) {
		TbLsSiteExample siteExample = new TbLsSiteExample();
		TbLsSiteExample.Criteria c = null;
		if (locId == null) {
			c = siteExample.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		} else {
			c = siteExample.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer).andLOCATION_IDEqualTo(locId);
		}
		
		if(pItem!=null && pItem.getItemType().startsWith(LsPaymentItem.R.getItemType())){
			c.andEXPN_TYPEEqualTo(LsPaymentItem.R.getItemType());
		}else if(pItem!=null && pItem.getItemType().startsWith(LsPaymentItem.E.getItemType())){
			c.andEXPN_TYPEEqualTo(LsPaymentItem.E.getItemType());
		}
		
		tbLsSiteMapper.deleteByExample(siteExample);
	}
	/**
	 * 刪除 TB_LS_LOC_ELEC 指定條件的資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 */
	public void deleteLsElec(String lsNo, String lsVer, String locId) {
		TbLsLocElecExample locElecexample = new TbLsLocElecExample();
		if (locId == null) {
			locElecexample.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		} else {
			locElecexample.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer).andLOCATION_IDEqualTo(locId);
		}
		
		tbLsLocElecMapper.deleteByExample(locElecexample);
	}
	

	/**
	 * 刪除 TB_LS_LOC_PAYMENT 指定條件的資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 */
	public void deleteLsLocPayment(String lsNo, String lsVer, String locId,LsPaymentItem[] pItems) {
		TbLsLocPaymentExample example = new TbLsLocPaymentExample();
		TbLsLocPaymentExample.Criteria c = null;
		if (locId == null) {
			c = example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		} else {
			c = example.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer).andLOCATION_IDEqualTo(locId);
		}
		if(pItems!=null){
			List<String> inPItem = new ArrayList<>(pItems.length);
			for(LsPaymentItem pItem:pItems){
				inPItem.add(pItem.getItemType());
			}
			c.andPAYMENT_ITEMIn(inPItem);
		}
		tbLsLocPaymentMapper.deleteByExample(example);
	}
   /**
	 * 刪除 TB_LS_LOC_PAYMENT
	 * 
	 * @param example
	 */
	public void deleteLsLocPaymentByExample(TbLsLocPaymentExample example) {
		tbLsLocPaymentMapper.deleteByExample(example);
	}
	/**
	 * 刪除 TB_LS_RES_EXCH_ALOC 與 TB_LS_RES_EXCH 指定條件的資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 */
	public void deleteLsResExch(String lsNo, String lsVer, String locId) {
		TbLsResExchExample resExchExample = new TbLsResExchExample();
		if (StringUtils.isBlank(locId)) {
			resExchExample.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		} else {
			resExchExample.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer).andLOCATION_IDEqualTo(locId);
		}
		tbLsResExchMapper.deleteByExample(resExchExample);
		TbLsResExchAlocExample resExchAlocExample = new TbLsResExchAlocExample();
		if (StringUtils.isBlank(locId)) {
			resExchAlocExample.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer);
		} else {
			resExchAlocExample.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer).andLOCATION_IDEqualTo(locId);
		}
		tbLsResExchAlocMapper.deleteByExample(resExchAlocExample);
	}
	
	/**
	 * 刪除 TB_LS_RES_EXCH_ALOC 指定條件的資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 */
	public void delLsResExchAloc(String lsNo, String lsVer, String locId, String exchLocId) {
		TbLsResExchAlocExample resExchAlocExample = new TbLsResExchAlocExample();
		resExchAlocExample.createCriteria().andLS_NOEqualTo(lsNo).andLS_VEREqualTo(lsVer).andLOCATION_IDEqualTo(locId).andEXCH_LOC_IDEqualTo(exchLocId);
		tbLsResExchAlocMapper.deleteByExample(resExchAlocExample);
	}

	/**
	 * 撈取站點資料
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 * @return
	 */
	public List<LeaseLocationDTO> getLsSiteLocationByLsNoVer(String lsNo, String lsVer, String locId) {
		return uTbLsLocationMapper.getLsSiteLocationByLsNoVer(lsNo, lsVer, locId);
	}
	
	public List<LeaseLocationDTO> getLsSiteLocationAddedByLsNoVer(String appSeq, String locId) {
		return uTbLsLocationMapper.getLsSiteLocationAddedByAppSeqLoc(appSeq, locId);
	}
	
	
	public List<TbLsLocation> getLsLocationByLsNoLoc(String lsNo, String locId) {
		
		return uTbLsLocationMapper.getLsLocationByLsNoVer(lsNo,  locId);
		
	}
	
	

	/**
	 * 撈取 站台資訊 資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 * @return
	 */
	public List<TbLsSiteDTO> getLsSiteByLsNoLocId(String lsNo, String lsVer, String locId,LsPaymentItem pItem) {
		return uTbLsSiteMapper.getLsSiteByLsNoLocId(lsNo, lsVer, locId,pItem==null?null:pItem.getItemType());
	}
	/**
	 * 撈取 站台資訊 資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 * @return
	 */
	public List<TbLsSiteDTO> getLsSiteAddedByLsNoLocId(String appSeq, String locId,LsPaymentItem pItem) {
		return uTbLsSiteMapper.getLsSiteAddedByLsNoLocId(appSeq, locId,pItem==null?null:pItem.getItemType());
	}
	/**
	 * 撈取 付款資訊 資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 * @return
	 */
	public List<TbLsLocPaymentDTO> getLsLocPaymentByLsNoVerLocId(String lsNo, String lsVer, String locId,LsPaymentItem[] pItem) {
		if(pItem==null){
			return uTbLsLocPaymentMapper.getLsLocPaymentByLsNoVerLocId(lsNo, lsVer, locId,null);
		}else{
			String[] itemList = new String[pItem.length];
			for(int i=0;i<itemList.length;i++){
				itemList[i]=pItem[i].getItemType();
			}
			return uTbLsLocPaymentMapper.getLsLocPaymentByLsNoVerLocId(lsNo, lsVer, locId,itemList);
		}
		
	
	}
	/**
	 * 撈取 付款資訊 資料
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param locId
	 *            站點編號
	 * @return
	 */
	public List<TbLsLocPaymentDTO> getLsLocPaymentAddedByLsNoVerLocId(String appSeq, String locId,LsPaymentItem[] pItem) {
		if(pItem==null){
			return uTbLsLocPaymentMapper.getLsLocPaymentAddedByAppSeqLocId(appSeq, locId,null);
		}else{
			String[] itemList = new String[pItem.length];
			for(int i=0;i<itemList.length;i++){
				itemList[i]=pItem[i].getItemType();
			}
			return uTbLsLocPaymentMapper.getLsLocPaymentAddedByAppSeqLocId(appSeq, locId,itemList);
		}
		
	
	}

	/**
	 * 撈取 TbSysLookup
	 * 
	 * @param example
	 * @return
	 */
	public List<TbSysLookup> getLsLeaseBldgtype(TbSysLookupExample example) {
		return tbSysLookupMapper.selectByExample(example);
	}

	/**
	 * 撈取 TbComEqpType
	 * 
	 * @param example
	 * @return
	 */
	public List<TbComEqpType> getTbComEqpType(TbComEqpTypeExample example) {
		return tbComEqpTypeMapper.selectByExample(example);
	}

	/**
	 * 撈取 TbComEqpModel
	 * 
	 * @param example
	 * @return
	 */
	public List<TbComEqpModel> getTbComEqpModel(TbComEqpModelExample example) {
		return tbComEqpModelMapper.selectByExample(example);
	}

	/**
	 * 撈取TbLsAvgRent 資料
	 * 
	 * @param example
	 *            TbLsAvgRentExample
	 * @return
	 */
	public List<TbLsAvgRent> getTbLsAvgRent(TbLsAvgRentExample example) {
		return tbLsAvgRentMapper.selectByExample(example);

	}
	
	/**
	 * 更新一筆資料至 TB_LS_SITE
	 * 
	 * @param record 更新值
	 */
	public void updateLeaseSite(TbLsSite record, TbLsSiteExample example) {
		
		tbLsSiteMapper.updateByExampleSelective(record, example);
	}
	
	/**
	 * 撈取 TbLsSite
	 * 
	 * @param example
	 * @return
	 */
	public List<TbLsSite> getTbLsSiteByExample(TbLsSiteExample example) {
		return tbLsSiteMapper.selectByExample(example);
	}
	
	/**
	 * 撈取 TbLsLocation
	 * 
	 * @param example
	 * @return
	 */
	public List<TbLsLocation> getTbLsLocationByExample(TbLsLocationExample example) {
		return tbLsLocationMapper.selectByExample(example);
	}
	//*************start Arda*****************
		/**
		 * 新增 LS_SITE
		 * @param  TBLSSite
		 * 			
		 */
		public void insertLsSite(TbLsSite tblsSite){
			tbLsSiteMapper.insertSelective(tblsSite);
		}
		
		/**
		 * 新增 LS_Payment
		 * @param  TBLSPayment
		 * 			
		 */
		public void insertLsLocPayment(TbLsLocPayment tbLsLocPayment){
			tbLsLocPaymentMapper.insertSelective(tbLsLocPayment);
		}
		
		/**
		 * 修改 LsLocPayment
		 * @param  TBLSPayment
		 * 			
		 */
		public void updateLsLocPayment(TbLsLocPayment tbLsLocPayment,TbLsLocPaymentExample example){
			tbLsLocPaymentMapper.updateByExampleSelective(tbLsLocPayment,example);
		}
		
		/**
		 * 修改 TbLsLocElec
		 * @param  TbLsLocElec
		 * 			
		 */
		public void updateLsLocElec(TbLsLocElec tbLsLocElec,TbLsLocElecExample example){
			tbLsLocElecMapper.updateByExampleSelective(tbLsLocElec, example);
		}
		
		
		/**
		 * 更新一筆資料至 TbLsLocation
		 * 
		 * @param record 更新值
		 */
		public void updateLeaseLocation(TbLsLocation record, TbLsLocationExample example) {
			
			uTbLsLocationMapper.updateByExampleSelective(record, example);
		}
		
		
		
		
		/**
		 * 更新一筆資料至 TB_LS_LESOOR
		 * 
		 * @param record 更新值
		 */
		public void updateLeaseLesoor(TbLsLessor record, TbLsLessorExample example) {
			
			tbLsLessorMapper.updateByExampleSelective(record, example);
		}
		
		/**
		 * 撈取站點電費付款資料
		 * @param lsNo
		 *            合約編號
		 * @param lsVer
		 *            版次
		 * @param locId
		 *            站點編號
		 * @return
		 */
		public List<TbLsLocElecDTO> getLsLocElecByLsNoVer(String lsNo, String lsVer, String locId) {

			return uTbLsLocElecMapper.getTbLsLocElecDTOByLsNoVerLoc(lsNo, lsVer,locId);
			
		}
		/**
		 * 撈取站點電費付款資料
		 * @param lsNo
		 *            合約編號
		 * @param lsVer
		 *            版次
		 * @param locId
		 *            站點編號
		 * @return
		 */
		public List<TbLsLocElecDTO> getLsLocElecAddedByAppSeqLoc(String appSeq, String locId) {

			return uTbLsLocElecMapper.getTbLsLocElecAddedDTOByLsNoVerLoc(appSeq,locId);
			
		}
		
		/**
		 * 新增合約站點電費資料
		 * @param lsNo
		 *            合約編號
		 * @param lsVer
		 *            版次
		 * @param locId
		 *            站點編號
		 * @return
		 */
		public void insertLsLocElec(TbLsLocElec tbLsLocElec) {
			
			 tbLsLocElecMapper.insertSelective(tbLsLocElec);
		}
		
		/**
		 * 新增合約站點電費資料
		 * @param lsNo
		 *            合約編號
		 * @param lsVer
		 *            版次
		 * @param locId
		 *            站點編號
		 * @return
		 */
		public List<TbLsElecRange> getEffectiveElecRangeSetting() {
			 TbLsElecRangeExample example =new TbLsElecRangeExample();
			 Date now = new Date();
			 example.createCriteria().andEFF_DATELessThanOrEqualTo(now).andEND_DATEGreaterThanOrEqualTo(now);
			 return tbLsElecRangeMapper.selectByExample(example);
		}
		/**
		 * 取得合約站點各區域租金押金資料
		 * @param lsNo
		 *            合約編號
		 * @param lsVer
		 *            版次
		 * @return
		 */
		public List<LeaseDomainMoneyDTO> getLeaseAllRentPledgeByDomain(String lsNo,String lsVer) {
			return uTbLsMainMapper.getLeaseAllRentPledgeByDomain(lsNo, lsVer);
		}
		
		/**
		 * 依appSeq取得List<TbLsRewardAdded>
		 * 
		 * @param lsNo
		 * @param lsVer
		 */
		public List<TbLsReward> getLsRewardAddedByAppSeq(String appSeq, String rewardType) {
			
			return uTbLsRewardMapper.getLsRewardAddedByAppSeq(appSeq, rewardType);
		}
		
		/**
		 * 依appSeq取得List<TbLsLessorAdded>
		 * 
		 * @param lsNo
		 * @param lsVer
		 */
		public List<TbLsLessor> getLsLessorAddedByAppSeq(String appSeq) {
		
			return uTbLsLessorMapper.searchLsLessorAddedByAppSeq(appSeq);
		}
		
		//*************end Arda*****************
		
		//*************START 資源互換*****************
		
		
		/**
		 * 撈取 TbLsResExch
		 * 
		 * @param example
		 * @return
		 */
		public List<TbLsResExchDTO> loadTbLsResExchList(String lsNo, String lsVer, String locId) {
			return uTbLsResExchMapper.loadTbLsResExchList(lsNo, lsVer, locId);
		}
		
		/**
		 * 撈取 TbLsResExch
		 * 
		 * @param example
		 * @return
		 */
		public List<TbLsResExchDTO> loadTbLsResExchAddedList(String appSeq, String locId) {
			return uTbLsResExchMapper.loadTbLsResExchAddedList(appSeq, locId);
		}
		
		/**
		 * 撈取 TbLsResExch
		 * 
		 * @param example
		 * @return
		 */
		public List<TbLsResExch> getLsResExchByLsNoVer(String lsNo, String lsVer, String locId) {
			return uTbLsResExchMapper.getLsResExchByLsNoVer(lsNo, lsVer, locId);
		}
		
		/**
		 * 儲存 TB_LS_RES_EXCH
		 * @param record
		 */
		public void insertTbLsResExch(TbLsResExch record) {
			tbLsResExchMapper.insert(record);
		}
		
		/**
		 * 儲存 TB_LS_RES_EXCH_ALOC
		 * @param record
		 */
		public void insertTbLsResExchAloc(TbLsResExchAloc record) {
			tbLsResExchAlocMapper.insert(record);
		}
		/**
		 * 尋找 TB_LS_RES_EXCH_ALOC
		 * @param record
		 */
		public List<TbLsResExchAloc> getTbLsResExchAlocByLsNoVerLoc(String lsNo,String lsVer,String locId) {
			return uTbLsResExchAlocMapper.getLsResExchAlocByLsNoVer(lsNo, lsVer, locId);
		}
		//*************END 資源互換*****************
		
		/**
		 * sum 租金、租金押金、電費、電費押金
		 * @param example 
		 * @return list
		 */
		public List<TbLsLocPayment> sumPayamtgroupbyPaymentItem (String lsNo,String lsVer ,String locationId){
			return uTbLsLocPaymentMapper.sumPayamtgroupbyPaymentItem(lsNo,lsVer,locationId);
		}
		
		/**
		 * search TB_LS_LOC_PAYMENT 
		 * @param example
		 * @return
		 */
		public List<TbLsLocPayment> sumPayalocgroupbylocationId (String lsNo, String lsVer){
			return uTbLsLocPaymentMapper.sumPayalocgroupbylocationId(lsNo,lsVer);
		}
		
		/**
		 * sum(ELEC_PLEDGE) 計算電費押金
		 * group by  LS_NO,LS_VER,LOCATION_ID
		 * @param example
		 * @return
		 */
		public List<TbLsLocElec> sumElecPledgegroupbylocationId(String lsNo, String lsVer ,String locationId,String elecMode){
			return uTbLsLocElecMapper.sumElecPledgegroupbylocationId(lsNo,  lsVer , locationId,elecMode);
		}
		
		public List<TbLsLocPayment> selectTbLsLocPaymentByExample(TbLsLocPaymentExample example){
			return tbLsLocPaymentMapper.selectByExample(example);
		}
		
		public TbLsResExchDTO getTbLsResExchMaxLsVer(String lsNo){
			return uTbLsResExchMapper.getMaxLsVer(lsNo);
		}
		
		public TbLsResExchAloc getTbLsResExchAlocMaxVer(String lsNo){
			return uTbLsResExchAlocMapper.getMaxVer(lsNo);
		}
}