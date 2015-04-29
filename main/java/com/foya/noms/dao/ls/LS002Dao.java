package com.foya.noms.dao.ls;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbLsLessorMapper;
import com.foya.dao.mybatis.mapper.TbLsLocationAddedMapper;
import com.foya.dao.mybatis.mapper.TbLsLocationMapper;
import com.foya.dao.mybatis.mapper.TbLsMainMapper;
import com.foya.dao.mybatis.mapper.TbLsSiteMapper;
import com.foya.dao.mybatis.mapper.TbSiteLocationMapper;
import com.foya.dao.mybatis.mapper.UTbLsAppMapper;
import com.foya.dao.mybatis.model.TbLsLessor;
import com.foya.dao.mybatis.model.TbLsLessorExample;
import com.foya.dao.mybatis.model.TbLsLocation;
import com.foya.dao.mybatis.model.TbLsLocationAdded;
import com.foya.dao.mybatis.model.TbLsLocationAddedExample;
import com.foya.dao.mybatis.model.TbLsLocationExample;
import com.foya.dao.mybatis.model.TbLsMain;
import com.foya.dao.mybatis.model.TbLsMainKey;
import com.foya.dao.mybatis.model.TbLsSite;
import com.foya.dao.mybatis.model.TbLsSiteExample;
import com.foya.dao.mybatis.model.TbSiteLocation;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.ls.LeaseDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class LS002Dao extends BaseDao {

	@Autowired
	private UTbLsAppMapper uTbLsAppMapper;

	@Autowired
	private TbLsMainMapper tbLsMainMapper;

	@Autowired
	private TbLsLessorMapper tbLsLessorMapper;

	@Autowired
	private TbLsLocationAddedMapper TbLsLocationAddedMapper;

	@Autowired
	private TbSiteLocationMapper tbSiteLocationMapper;

	@Autowired
	private TbLsSiteMapper tbLsSiteMapper;

	/**
	 * 依合約編號模糊查詢
	 * @param dataObj
	 * 			搜尋修件 map
	 * @return
	 */
	public List<LeaseDTO> searchByLeaseNo(Map<String, Object> dataObj) {

		String sortString = "CR_TIME.DESC, LS_NO";
		PageBounds pageBounds = getPageBounds(sortString);

		List<LeaseDTO> list = this.sqlSession.selectList("com.foya.dao.mybatis.mapper.UTbLsAppMapper.searchByCond", dataObj, pageBounds);

		PageList<LeaseDTO> pageList = (PageList<LeaseDTO>) list;

		return pageList;
	}

	/**
	 * TbLsLessor 依合約編號查詢
	 * 
	 * @param example
	 * @return
	 */
	public List<TbLsLessor> searchTbLsLessorByLsNo(TbLsLessorExample example) {
		return tbLsLessorMapper.selectByExample(example);
	}

	/**
	 * TbLsMain 依合約編號查詢
	 * 
	 * @param leaseNo
	 * @return
	 */
	public TbLsMain searchTbLsMainByLsNo(TbLsMainKey key) {
		return tbLsMainMapper.selectByPrimaryKey(key);
	}

	public List<TbLsLocationAdded> searchTbLsLocationAdded(TbLsLocationAddedExample example) {
		return TbLsLocationAddedMapper.selectByExample(example);
	}

	public TbSiteLocation searchTbSiteLocationByLsNo(String LOCATION_ID) {
		return tbSiteLocationMapper.selectByPrimaryKey(LOCATION_ID);
	}

	/**
	 * 更新 TB_LS_LESSOR 的 房屋稅籍編號、更新人員 與 更新日期
	 * 
	 * @param lsNo
	 *            合約編號
	 * @param lsVer
	 *            版次
	 * @param lessorId
	 *            出租人證號
	 * @param newTaxNo
	 *            房屋稅籍編號
	 * @param userName
	 *            更新人員
	 */
	public void updateHouseTaxNo(String lsNo, String lsVer, String lessorId, String newTaxNo, String userName) {
		TbLsLessor record = new TbLsLessor();
		record.setLS_NO(lsNo);
		record.setLS_VER(lsVer);
		record.setLESSOR_ID(lessorId);
		record.setHOUSE_TAX_NO(newTaxNo);
		record.setMD_USER(userName);
		record.setMD_TIME(new Date());
		tbLsLessorMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 更新 TB_LS_LOCATION 的 租金起算日、更新人員 與 更新日期
	 * 
	 * @param rentLsNoArray
	 *            陣列-合約編號
	 * @param rentLsVerArray
	 *            陣列-版次
	 * @param rentLocationIdArray
	 *            陣列-站點編號
	 * @param payBeginTimeArray
	 *            陣列-租金起算日
	 * @param userName
	 *            更新人員
	 */
	public void updayeLocationAddedDate(TbLsLocationAdded record,TbLsLocationAddedExample example) {
		TbLsLocationAddedMapper.updateByExampleSelective(record, example);
//		TbLsLocationExample example = new TbLsLocationExample();
//		for (int i = 0; i < rentLsNoArray.length; i++) {
//			example.createCriteria().andLS_NOEqualTo(rentLsNoArray[i]).andLS_VEREqualTo(rentLsVerArray[i]).andLOCATION_IDEqualTo(rentLocationIdArray[i]);
//			TbLsLocation record = new TbLsLocation();
//			record.setLS_NO(rentLsNoArray[i]);
//			record.setLS_VER(rentLsVerArray[i]);
//			record.setLOCATION_ID(rentLocationIdArray[i]);
//			record.setPAY_BEGIN_DATE(payBeginTimeArray[i]);
//			record.setMD_TIME(new Date());
//			record.setMD_USER(userName);
//			tbLsLocationMapper.updateByExampleSelective(record, example);
//		}
	}

	/**
	 * 更新 TB_LS_SITE 的 租金起算日、更新人員 與 更新日期
	 * 
	 * @param rentLsNoArray
	 *            陣列-合約編號
	 * @param rentLsVerArray
	 *            陣列-版次
	 * @param rentLocationIdArray
	 *            陣列-站點編號
	 * @param payBeginTimeArray
	 *            陣列-租金起算日
	 * @param userName
	 *            更新人員
	 */
	public void updayeSitePayBeginDate(String[] rentLsNoArray, String[] rentLsVerArray, String[] rentLocationIdArray, Date[] payBeginTimeArray, String userName) {
		for (int i = 0; i < rentLsNoArray.length; i++) {
			TbLsSiteExample example = new TbLsSiteExample();
			example.createCriteria().andLS_NOEqualTo(rentLsNoArray[i]).andLS_VEREqualTo(rentLsVerArray[i]).andLOCATION_IDEqualTo(rentLocationIdArray[i]);
			TbLsSite record = new TbLsSite();
			record.setPAY_BEGIN_DATE(payBeginTimeArray[i]);
			record.setMD_TIME(new Date());
			record.setMD_USER(userName);
			tbLsSiteMapper.updateByExampleSelective(record, example);
		}
	}

}
