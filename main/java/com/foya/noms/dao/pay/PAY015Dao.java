package com.foya.noms.dao.pay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComCompanyMapper;
import com.foya.dao.mybatis.mapper.UTbSiteOutsourcingMapper;
import com.foya.dao.mybatis.model.TbComCompany;
import com.foya.dao.mybatis.model.TbComCompanyExample;
import com.foya.dao.mybatis.model.UTbSiteOsItemExample;
import com.foya.dao.mybatis.model.UTbSiteOutsourcingExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.st.TbSiteOsItemDTO;
import com.foya.noms.dto.st.TbSiteOutsourcingDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class PAY015Dao extends BaseDao {

	@Autowired
	private TbComCompanyMapper tbComCompanyMapper;

	@Autowired
	private UTbSiteOutsourcingMapper uTbSiteOutsourcingMapper;

	/**
	 * 取得廠商清單
	 * @param example
	 * @return
	 */
	public List<TbComCompany> selectComCompanyByExample(TbComCompanyExample example) {
		return tbComCompanyMapper.selectByExample(example);
	}

	/**
	 * 取得工程驗收清單 by page
	 * @param example
	 * @return
	 */
	public List<TbSiteOutsourcingDTO> selectSiteOutsourcingByExamplePage(UTbSiteOutsourcingExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbSiteOutsourcingDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbSiteOutsourcingMapper.selectByExample",
						example, pageBounds);

		PageList<TbSiteOutsourcingDTO> pageList = (PageList<TbSiteOutsourcingDTO>) list;
		return pageList;
	}

	/**
	 * 取得工單資訊 by page
	 * @param example
	 * @return
	 */
	public List<TbSiteOsItemDTO> selectSiteOsItemByExamplePage(UTbSiteOsItemExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbSiteOsItemDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbSiteOsItemMapper.selectByExample",
						example, pageBounds);

		PageList<TbSiteOsItemDTO> pageList = (PageList<TbSiteOsItemDTO>) list;
		return pageList;
	}

	/**
	 * 更新工程驗收清單資料
	 * @param record
	 * @param example
	 * @return
	 */
	public int updateSiteOutsourcinByExampleSelective(TbSiteOutsourcingDTO record, UTbSiteOutsourcingExample example) {
		return uTbSiteOutsourcingMapper.updateByExampleSelective(record, example);
	}
}
