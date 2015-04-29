package com.foya.noms.service.st;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbSiteMain;
import com.foya.dao.mybatis.model.TbSiteMainExample;
import com.foya.dao.mybatis.model.TbSiteWorkAntCfgTemp;
import com.foya.dao.mybatis.model.TbSiteWorkAntCfgTempExample;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTemp;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTempExample;
import com.foya.dao.mybatis.model.TbSiteWorkSiteTempKey;
import com.foya.exception.NomsException;
import com.foya.exception.UpdateFailException;
import com.foya.noms.dao.st.AntCfgTempDao;
import com.foya.noms.dao.st.SiteMainDao;
import com.foya.noms.dao.st.SiteTempDao;
import com.foya.noms.dto.st.SiteDTO;
import com.foya.noms.service.BaseService;

@Service
public class SiteLinesService extends BaseService {
	@Autowired
	private SiteTempDao siteTempDao;

	@Autowired
	private AntCfgTempDao antCfgTempDao;
	
	@Autowired
	private SiteMainDao siteMainDao;

	/**
	 * 儲存機房資訊到siteTemp
	 * 
	 * @param siteTemp
	 */
	@Transactional
	public void saveSite(TbSiteWorkSiteTemp siteTemp) throws UpdateFailException {
		TbSiteWorkSiteTempKey key = new TbSiteWorkSiteTempKey();
		key.setSITE_ID(siteTemp.getSITE_ID());
		key.setWORK_ID(siteTemp.getWORK_ID());
		TbSiteWorkSiteTemp siteTempSrc = siteTempDao.findByPrimaryKey(key);
		siteTempSrc.setCONSTR_KO_DATE(siteTemp.getCONSTR_KO_DATE());
		siteTempSrc.setCONSTR_DONE_DATE(siteTemp.getCONSTR_DONE_DATE());
		siteTempSrc.setSMR_READY_DATE(siteTemp.getSMR_READY_DATE());
		siteTempSrc.setUPS_READY_DATE(siteTemp.getUPS_READY_DATE());
		siteTempSrc.setAC_READY_DATE(siteTemp.getAC_READY_DATE());
		siteTempSrc.setDYNAMO_READY_DATE(siteTemp.getDYNAMO_READY_DATE());
		siteTempSrc.setADM_READY_DATE(siteTemp.getADM_READY_DATE());
		siteTempSrc.setPOWER_READY_DATE(siteTemp.getPOWER_READY_DATE());
		siteTempSrc.setTRANS_READY_DATE(siteTemp.getTRANS_READY_DATE());
		siteTempSrc.setCOVERAGE_IN_FLOOR(siteTemp.getCOVERAGE_IN_FLOOR());
		siteTempSrc.setCLUSTER(siteTemp.getCLUSTER());
		int siteTempItem = siteTempDao.update(siteTempSrc);
		if (siteTempItem == 0) {
			throw new UpdateFailException("修改失敗");
		}
	}

	/**
	 * 基站查詢
	 * 
	 * @param example
	 * @return
	 */
	public List<TbSiteWorkSiteTemp> getSiteTempByExample(TbSiteWorkSiteTempExample example) {
		return siteTempDao.findSiteTempByExample(example);
	}
	
	/**
	 * 基站查詢
	 * 
	 * @param example
	 * @return
	 */
	public List<TbSiteMain> getSiteMainByExample(TbSiteMainExample example) {
		return siteMainDao.findByConditions(example);
	}

	/**
	 * 查詢 天線組態
	 * 
	 * @param Example
	 * @return
	 */
	public List<TbSiteWorkAntCfgTemp> getSiteWorkAntCfgTempByExample(TbSiteWorkAntCfgTempExample example) {
		return antCfgTempDao.findByCondition(example);
	}

	/**
	 * 基站資訊更新
	 */
	@Transactional
	public boolean saveWorkTemp(TbSiteWorkSiteTemp record, TbSiteWorkSiteTempExample example, List<TbSiteWorkAntCfgTemp> recordAnt) {

		TbSiteWorkAntCfgTempExample exampleAnt = new TbSiteWorkAntCfgTempExample();
		exampleAnt.createCriteria().andWORK_IDEqualTo(record.getWORK_ID()).andSITE_IDEqualTo(record.getSITE_ID());
		try {
			// TbSiteWorkSiteTemp
			record.setMD_USER(getLoginUser().getUsername());
			record.setMD_TIME(new Date());
			TbSiteWorkSiteTempKey key = new TbSiteWorkSiteTempKey();
			key.setSITE_ID(record.getSITE_ID());
			key.setWORK_ID(record.getWORK_ID());
			TbSiteWorkSiteTemp targetSiteWorkTemp = siteTempDao.findByPrimaryKey(key);
			String[] ignoreProperties = { "FEQ_ID", "SR_ID", "BTS_SITE_ID", "LOCATION_ID", "PURPOSE", "CELL_STATUS", "CST_ID_CARD_NUM", "CST_MOBILE",
					"CST_ID", "INTEGRATED_DATE", "ONAIR_DATE", "DUMP_DATE", "SUSPEND_DATE", "RESTORE_DATE", "OWNER", "CNT_PSN", "CNT_TEL",
					"CONSTR_KO_DATE", "CONSTR_DONE_DATE", "SMR_READY_DATE", "UPS_READY_DATE", "AC_READY_DATE", "DYNAMO_READY_DATE", "ADM_READY_DATE",
					"POWER_READY_DATE", "TRANS_READY_DATE", "CPL_NO", "PERMIT_NO", "LICENSE_NO", "CR_USER", "CR_TIME" };

			BeanUtils.copyProperties(record, targetSiteWorkTemp, ignoreProperties);

			siteTempDao.updateSiteWorkSiteTemp(targetSiteWorkTemp, example);
			// TbSiteWorkAntCfgTemp
			siteTempDao.deleteSiteWorkAntCfgTemp(exampleAnt);
			for (TbSiteWorkAntCfgTemp antCfgTemp : recordAnt) {
				antCfgTemp.setMD_USER(getLoginUser().getUsername());
				antCfgTemp.setMD_TIME(new Date());
				antCfgTempDao.insert(antCfgTemp);
			}
		} catch (NomsException nomsException) {
			log.error(nomsException.getMessage());
			throw new NomsException("建站 -基站資訊 資料 更新錯誤 :" + nomsException.getMessage());
		}

		return true;
	}
}
