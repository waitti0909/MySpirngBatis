package com.foya.noms.dao.st;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.foya.dao.mybatis.mapper.TbSiteWorkOrderMapper;
import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSiteWorkOrderExample;
import com.foya.noms.dao.BaseDao;
import com.foya.noms.enums.OrderStatus;

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
 * <td>工單相關DAO</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Repository
public class WorkOrderDao extends BaseDao {

	@Autowired
	private TbSiteWorkOrderMapper mapper;
	
	public int insert(TbSiteWorkOrder record) {
		return mapper.insertSelective(record);
	}
	
	public int updateSelective(TbSiteWorkOrder record) {
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	public int update(TbSiteWorkOrder record) {
		return mapper.updateByPrimaryKey(record);
	}
	
	public int updateByExample(TbSiteWorkOrder record,TbSiteWorkOrderExample example) {
		return mapper.updateByExampleSelective(record, example);
	}
	
	public TbSiteWorkOrder findOrderByPk(String orderId) {
		return mapper.selectByPrimaryKey(orderId);
	}
	
	public List<TbSiteWorkOrder> findByConditions(TbSiteWorkOrderExample example) {
		return mapper.selectByExample(example);
	}
	
	public int deleteByExample(TbSiteWorkOrderExample example){
		return mapper.deleteByExample(example);
	}
	
	public int updateStatusByOrder(String orderId,OrderStatus status){
		TbSiteWorkOrder record = findOrderByPk(orderId);
		record.setORDER_ID(orderId);
		record.setODR_STATUS(status.toString());
		return mapper.updateByPrimaryKeySelective(record);
	}
	
	public int updateStatusByWork(String workId,OrderStatus status){
		TbSiteWorkOrderExample example = new TbSiteWorkOrderExample();
		example.createCriteria().andWORK_IDEqualTo(workId).andIS_ACTIVEEqualTo("Y");
		TbSiteWorkOrder record = new TbSiteWorkOrder();
		record.setODR_STATUS(status.toString());
		return mapper.updateByExampleSelective(record, example);
	}
	
}
