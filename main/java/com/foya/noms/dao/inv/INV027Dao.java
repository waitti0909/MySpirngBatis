package com.foya.noms.dao.inv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbComEqpModelMapper;
import com.foya.dao.mybatis.mapper.TbComEqpTypeMapper;
import com.foya.dao.mybatis.mapper.UTbInvBomMapper;
import com.foya.dao.mybatis.mapper.UTbInvCategoryMapper;
import com.foya.dao.mybatis.mapper.UTbInvMaterialMapper;
import com.foya.dao.mybatis.model.TbComEqpModel;
import com.foya.dao.mybatis.model.TbComEqpModelExample;
import com.foya.dao.mybatis.model.TbComEqpType;
import com.foya.dao.mybatis.model.TbComEqpTypeExample;
import com.foya.dao.mybatis.model.TbInvCategory;
import com.foya.dao.mybatis.model.UTbInvBomExample;
import com.foya.dao.mybatis.model.UTbInvCategoryExample;
import com.foya.dao.mybatis.model.UTbInvMaterialExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvBomDTO;
import com.foya.noms.dto.inv.TbInvMaterialDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class INV027Dao extends BaseDao {

	@Autowired
	private TbComEqpModelMapper tbComEqpModelMapper;

	@Autowired
	private TbComEqpTypeMapper tbComEqpTypeMapper;

	@Autowired
	private UTbInvBomMapper tbInvBomMapper;

	@Autowired
	private UTbInvMaterialMapper tbInvMaterialMapper;

	@Autowired
	private UTbInvCategoryMapper tbInvCategoryMapper;

	/**
	 * 取得設備型態
	 * @param example
	 * @return
	 */
	public List<TbComEqpModel> selectEqpModelByExample(TbComEqpModelExample example) {
		return tbComEqpModelMapper.selectByExample(example);
	}

	/**
	 * 取得設備機型
	 * @param example
	 * @return
	 */
	public List<TbComEqpType> selectEqpTypeByExample(TbComEqpTypeExample example) {
		return tbComEqpTypeMapper.selectByExample(example);
	}

	/**
	 * 取得設備資料Group by
	 * @param example
	 * @return
	 */
	public List<TbInvBomDTO> selectDistinctInvBomByExample(UTbInvBomExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbInvBomDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbInvBomMapper.selectDistinctByExample",
						example, pageBounds);

		PageList<TbInvBomDTO> pageList = (PageList<TbInvBomDTO>) list;
		return pageList;
	}

	/**
	 * 取得設備資料
	 * @param example
	 * @return
	 */
	public List<TbInvBomDTO> selectInvBomByExample(UTbInvBomExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbInvBomDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbInvBomMapper.selectByExample",
						example, pageBounds);

		PageList<TbInvBomDTO> pageList = (PageList<TbInvBomDTO>) list;
		return pageList;
	}

	/**
	 * 計算設備筆數
	 * @param example
	 * @return
	 */
	public int countInvBomByExample(UTbInvBomExample example) {
		return tbInvBomMapper.countByExample(example);
	}

	/**
	 * 新增設備資料
	 * @param record
	 * @return
	 */
	public int insertInvBomSelective(TbInvBomDTO record) {
		return tbInvBomMapper.insertSelective(record);
	}

	/**
	 * 修改設備資料
	 * @param record
	 * @return
	 */
	public int updateInvBomByPrimaryKeySelective(TbInvBomDTO record) {
		return tbInvBomMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 取得料號資料
	 * @param example
	 * @return
	 */
	public List<TbInvMaterialDTO> selectInvMaterialByExample(UTbInvMaterialExample example) {
		return tbInvMaterialMapper.selectByExample(example);
	}

	/**
	 * 取得設備分類資料
	 * @param example
	 * @return
	 */
	public List<TbInvCategory> selectInvCategoryByExample(UTbInvCategoryExample example) {
		return tbInvCategoryMapper.selectByExample(example);
	}
}
