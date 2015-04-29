package com.foya.noms.dao.inv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbInvSnCorMapper;
import com.foya.dao.mybatis.mapper.UTbInvSrlMapper;
import com.foya.dao.mybatis.mapper.UTbInvWarehouseMapper;
import com.foya.dao.mybatis.model.TbInvSnCor;
import com.foya.dao.mybatis.model.UTbInvSrlExample;
import com.foya.dao.mybatis.model.UTbInvWarehouseExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.dto.inv.TbInvSrlDTO;
import com.foya.noms.dto.inv.TbInvWarehouseDTO;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

@Repository
public class INV017Dao extends BaseDao {

	@Autowired
	private UTbInvSrlMapper uTbInvSrlMapper;

	@Autowired
	private UTbInvWarehouseMapper uTbInvWarehouseMapper;

	@Autowired
	private TbInvSnCorMapper tbInvSnCorMapper;

	/**
	 * 取得所有倉庫清單
	 * @param example
	 * @return
	 */
	public List<TbInvWarehouseDTO> selectInvWarehouseByExample(UTbInvWarehouseExample example) {
		return uTbInvWarehouseMapper.selectByExample(example);
	}

	/**
	 * 查詢序號資料
	 * @param example
	 * @return
	 */
	public List<TbInvSrlDTO> selectInvSrlByExample(UTbInvSrlExample example) {
		PageBounds pageBounds = getPageBounds();
		List<TbInvSrlDTO> list = this.sqlSession.selectList(
						"com.foya.dao.mybatis.mapper.UTbInvSrlMapper.selectByExample",
						example, pageBounds);

		PageList<TbInvSrlDTO> pageList = (PageList<TbInvSrlDTO>) list;
		return pageList;
	}

	/**
	 * 查詢序號資料筆數
	 * @param example
	 * @return
	 */
	public int countInvSrlByExample(UTbInvSrlExample example) {
		return uTbInvSrlMapper.countByExample(example);
	}

	/**
	 * 查詢序號資料by key
	 * @param srl_no
	 * @return
	 */
	public TbInvSrlDTO selectInvSrlByPrimaryKey(Long srl_no) {
		return uTbInvSrlMapper.selectByPrimaryKey(srl_no);
	}

	/**
	 * 更新序號資料by key
	 * @param record
	 * @return
	 */
	public int updateInvSrlByPrimaryKeySelective(TbInvSrlDTO record) {
		return uTbInvSrlMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 新增序號更正紀錄
	 * @param record
	 * @return
	 */
	public int insertInvSnCorSelective(TbInvSnCor record) {
		return tbInvSnCorMapper.insertSelective(record);
	}
}
