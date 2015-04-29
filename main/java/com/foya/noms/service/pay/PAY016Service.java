package com.foya.noms.service.pay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComCompanyExample;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbPayLookupCodeExample;
import com.foya.dao.mybatis.model.UTbPayOutsourceAcceptanceExample;
import com.foya.exception.NomsException;
import com.foya.noms.dao.pay.PAY016Dao;
import com.foya.noms.dto.pay.TbPayOutsourceAcceptanceDTO;
import com.foya.noms.service.BaseService;

@Service
public class PAY016Service extends BaseService {

	@Autowired
	private PAY016Dao pay016Dao;

	/**
	 * 取得廠商清單
	 * @param example
	 * @return
	 */
	public List<TbComCompany> selectComCompanyByExample(TbComCompanyExample example) {
		return pay016Dao.selectComCompanyByExample(example);
	}

	/**
	 * 取得參數設定資料
	 * @param example
	 * @return
	 */
	public List<TbPayLookupCode> selectPayLookupCodeByExample(TbPayLookupCodeExample example) {
		return pay016Dao.selectPayLookupCodeByExample(example);
	}

	/**
	 * 取得驗收清單
	 * 
	 * @param example
	 * @return
	 */
	public List<TbPayOutsourceAcceptanceDTO> selectPayOutsourceAcceptanceByExamplePage(
			UTbPayOutsourceAcceptanceExample example) {
		return pay016Dao.selectPayOutsourceAcceptanceByExamplePage(example);
	}

	/**
	 * 儲存傳票號碼、日期
	 * @param map
	 * @throws NomsException
	 */
	@Transactional
	public void save(Map<String, String> map) throws NomsException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String[] apNo = map.get("ap_nos").split(",");
			String[] subpoenaNbr = map.get("subpoena_nbrs").split(",");
			String[] subpoenaDate = map.get("subpoena_dates").split(",");
			for (int i = 0; i < apNo.length; i++) {
				TbPayOutsourceAcceptanceDTO record = new TbPayOutsourceAcceptanceDTO();
				record.setSUBPOENA_NBR(subpoenaNbr[i]);
				record.setSUBPOENA_DATE(sdf.parse(subpoenaDate[i]));
				record.setSTATUS("P");
				record.setMD_USER(getLoginUser().getUsername());
				record.setMD_TIME(new Date());
				
				UTbPayOutsourceAcceptanceExample example = new UTbPayOutsourceAcceptanceExample();
				UTbPayOutsourceAcceptanceExample.Criteria cr = example.createCriteria();
				cr.andEqualTo("AP_NO", apNo[i]);
				pay016Dao.updatePayOutsourceAcceptanceByExampleSelective(record, example);
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw new NomsException("99", e.getMessage());
		}
	}
}
