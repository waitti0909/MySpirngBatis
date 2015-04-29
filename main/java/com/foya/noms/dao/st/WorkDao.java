package com.foya.noms.dao.st;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteWorkMapper;
import com.foya.dao.mybatis.mapper.UTbSiteWorkMapper;
import com.foya.dao.mybatis.model.TbSiteWork;
import com.foya.dao.mybatis.model.TbSiteWorkExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.enums.WorkStatus;

/**
 * 
 * 
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2015/4/7</td>
 * <td>作業相關DAO</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Repository
public class WorkDao extends BaseDao {

	@Autowired
	private TbSiteWorkMapper mapper;
	
	@Autowired
	private UTbSiteWorkMapper umapper;
	
	public int insert(TbSiteWork record) {
		return mapper.insertSelective(record);
	}
	
	public int update(TbSiteWork record) {
		return mapper.updateByPrimaryKey(record);
	}
	
	public TbSiteWork findByPk(String workId) {
		return mapper.selectByPrimaryKey(workId);
	}
	
	public List<TbSiteWork> findByConditions(TbSiteWorkExample example) {
		return mapper.selectByExample(example);
	}
	
	public TbSiteWork findSiteWorkByOrderId(String orderId){
		return umapper.selectSiteWorkByOrderId(orderId);
	}
	
	public int updateByPrimaryKey(TbSiteWork record){
		
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	public int updateStatusByWork(String workId,WorkStatus status){
		TbSiteWork work = this.findByPk(workId);
		work.setWORK_STATUS(status.name());
		return mapper.updateByPrimaryKeySelective(work);
	}
}
