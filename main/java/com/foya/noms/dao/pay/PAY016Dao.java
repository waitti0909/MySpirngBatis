package com.foya.noms.dao.pay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComCompanyMapper;
import com.foya.dao.mybatis.mapper.TbPayLookupCodeMapper;
import com.foya.dao.mybatis.mapper.UTbPayOutsourceAcceptanceMapper;
import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComCompanyExample;
import com.foya.dao.mybatis.model.TbPayLookupCode;
import com.foya.dao.mybatis.model.TbPayLookupCodeExample;
import com.foya.dao.mybatis.model.UTbPayOutsourceAcceptanceExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.pay.TbPayOutsourceAcceptanceDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class PAY016Dao extends BaseDao {

	@Autowired
	private TbComCompanyMapper tbComCompanyMapper;

	@Autowired
	private TbPayLookupCodeMapper tbPayLookupCodeMapper;

	@Autowired
	private UTbPayOutsourceAcceptanceMapper uTbPayOutsourceAcceptanceMapper;

	/**
	 * 取得廠商清單
	 * @param example
	 * @return
	 */
	public List<TbComCompany> selectComCompanyByExample(TbComCompanyExample example) {
		return tbComCompanyMapper.selectByExample(example);
	}

	/**
	 * 取得參數設定資料
	 * @param example
	 * @return
	 */
	public List<TbPayLookupCode> selectPayLookupCodeByExample(TbPayLookupCodeExample example) {
		return tbPayLookupCodeMapper.selectByExample(example);
	}

	/**
	 * 取得驗收清單資料 by Page
	 * @param example
	 * @return
	 */
	public List<TbPayOutsourceAcceptanceDTO> selectPayOutsourceAcceptanceByExamplePage(UTbPayOutsourceAcceptanceExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbPayOutsourceAcceptanceDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbPayOutsourceAcceptanceMapper.selectByExample",
						example, pageBounds);

		PageList<TbPayOutsourceAcceptanceDTO> pageList = (PageList<TbPayOutsourceAcceptanceDTO>) list;
		return pageList;
	}

	/**
	 * 修改驗收清單資料
	 * @param record
	 * @param example
	 * @return
	 */
	public int updatePayOutsourceAcceptanceByExampleSelective(
			TbPayOutsourceAcceptanceDTO record,
			UTbPayOutsourceAcceptanceExample example) {
		return uTbPayOutsourceAcceptanceMapper.updateByExampleSelective(record, example);
	}
}
