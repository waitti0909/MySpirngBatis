package com.foya.noms.service.system;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foya.dao.mybatis.model.TbSiteWorkOrder;
import com.foya.dao.mybatis.model.TbSysRespUser;
import com.foya.dao.mybatis.model.TbSysRespUserKey;
import com.foya.noms.dao.st.WorkOrderDao;
import com.foya.noms.dao.system.RespUserDao;
import com.foya.noms.enums.OrderStatus;
import com.foya.noms.service.BaseService;

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
 * <td>2015/1/9</td>
 * <td>TB_SYS_RESP_USER 關於自動派工作業設定</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Service
public class RespUserService extends BaseService {

	@Autowired
	private RespUserDao respUserDao;
	
	@Autowired
	private WorkOrderDao workOrderDao;
	
	/**
	 * 工單申請核可後，進行系統自動派工動作
	 * @param townId
	 * @param order (to be assign)
	 * @return
	 * @author Charlie Woo
	 */
	@Transactional
	public boolean autoAssignOrder(String townId, TbSiteWorkOrder order) {
		
		boolean result = false;
		log.debug("TOWN_ID=" + townId + ", 接工部門=" + order.getREP_DEPT() + ", 系統準備派工.....");
		if (StringUtils.isNotEmpty(townId)) {				
			TbSysRespUserKey key = new TbSysRespUserKey();
			key.setTOWN_ID(townId);
			key.setDEPT_ID(order.getREP_DEPT());
			TbSysRespUser respUser = respUserDao.findByPk(key);
			
			if (respUser != null) {
				order.setREP_TEAM(respUser.getDEPT_TEAM());
				order.setREP_USER(respUser.getRESP_USER());
				order.setASSIGN_TIME(new Date());
				order.setASSIGN_USER("SYSTEM");
				order.setODR_STATUS(OrderStatus.OR04.name());
				result = workOrderDao.updateSelective(order) > 0;
			} else {
				log.info("TOWN_ID="+townId+", DEPT_ID="+order.getREP_DEPT()+"，沒有設定，不進行自動派工");
			}				
		}
		
		if (result) log.debug("系統已進行自動派工");
		else log.debug("系統自動派工無效");
		return result;
	}
}
