package com.foya.noms.service.pay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComCompanyExample;
import com.foya.dao.mybatis.model.UTbSiteOsItemExample;
import com.foya.dao.mybatis.model.UTbSiteOutsourcingExample;
import com.foya.noms.dao.pay.PAY015Dao;
import com.foya.noms.dto.st.TbSiteOsItemDTO;
import com.foya.noms.dto.st.TbSiteOutsourcingDTO;
import com.foya.noms.service.BaseService;

@Service
public class PAY015Service extends BaseService {

	@Autowired
	private PAY015Dao pay015Dao;

	/**
	 * 取得廠商清單
	 * @param example
	 * @return
	 */
	public List<TbComCompany> selectComCompanyByExample(TbComCompanyExample example) {
		return pay015Dao.selectComCompanyByExample(example);
	}

	/**
	 * 取得工程驗收清單
	 * @param example
	 * @return
	 */
	public List<TbSiteOutsourcingDTO> selectSiteOutsourcingByExamplePage(UTbSiteOutsourcingExample example) {
		return pay015Dao.selectSiteOutsourcingByExamplePage(example);
	}

	/**
	 * 取得工單資訊
	 * @param example
	 * @return
	 */
	public List<TbSiteOsItemDTO> selectSiteOsItemByExamplePage(UTbSiteOsItemExample example) {
		return pay015Dao.selectSiteOsItemByExamplePage(example);
	}

	/**
	 * 更新工程驗收清單資料
	 * @param record
	 * @param example
	 * @return
	 */
	public int updateSiteOutsourcinByExampleSelective(TbSiteOutsourcingDTO record, UTbSiteOutsourcingExample example) {
		return pay015Dao.updateSiteOutsourcinByExampleSelective(record, example);
	}
}
