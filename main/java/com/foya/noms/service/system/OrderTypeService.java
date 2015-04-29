package com.foya.noms.service.system;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbOrgDept;
import com.foya.dao.mybatis.model.TbOrgDeptExample;
import com.foya.dao.mybatis.model.TbSysOrderResp;
import com.foya.dao.mybatis.model.TbSysOrderRespKey;
import com.foya.dao.mybatis.model.TbSysOrderType;
import com.foya.dao.mybatis.model.TbSysOrderTypeExample;
import com.foya.exception.NomsException;
import com.foya.noms.dao.common.TownDomainTeamDao;
import com.foya.noms.dao.org.DeptDao;
import com.foya.noms.dao.system.OrderRestDao;
import com.foya.noms.dao.system.OrderTypeDao;
import com.foya.noms.dto.common.TownDomainTeamDTO;
import com.foya.noms.dto.system.OrderTypeDTO;
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
 * <td>2015/1/5</td>
 * <td>尋找工單ORDER Service</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Service
public class OrderTypeService extends BaseService {

	@Autowired
	private OrderTypeDao orderTypeDao;
	
	@Autowired
	private OrderRestDao orderRestDao;
	
	@Autowired
	private DeptDao deptDao;
	
	@Autowired
	private TownDomainTeamDao townDomainTeamDao;
	
	public TbSysOrderType getByPk(String orderTypeId) {
		return orderTypeDao.findByPk(orderTypeId);
	}
	
	public List<TbSysOrderType> getByCondition(TbSysOrderTypeExample example){
		return orderTypeDao.findByConditions(example);
	}
	
	public List<TbSysOrderType> getAllOrderTypes(){
		return getByCondition(new TbSysOrderTypeExample());
	}
	
	/**
	 * 以工單類型找顯示頁籤
	 * @param orderTypeId
	 * @return
	 */
	public String getShowTabPagesByOrderTypeId(String orderTypeId) {
		TbSysOrderType model = getByPk(orderTypeId);
		return model != null ? model.getSHOW_PAGES() : null;
	}
	
	/**
	 * 以工務類型找工單種類清單
	 * @param wkTypeId
	 * @return
	 */
	public List<OrderTypeDTO> getOrderTypesByWkTypeId(String wkTypeId) {
		return orderTypeDao.findOrderTypeByWorkTypeId(wkTypeId, null);
	}
	
	/**
	 * 以工務類型找物料處理清單
	 * @param wkTypeId
	 * @return
	 */
	public List<OrderTypeDTO> getMtOptOrderTypeByWkTypeId(String wkTypeId) {
		return orderTypeDao.findOrderTypeByWorkTypeId(wkTypeId, 99);
	}
	
	/**
	 * 依工單種類找工單指派部門關鍵KEY
	 * @param odTypeId
	 * @param domain
	 * @param eqpType
	 * @return
	 */
	public String getOrdersDeptKey(String odTypeId, String domain, String eqpType) {
		TbSysOrderRespKey key = new TbSysOrderRespKey();
		key.setDOMAIN(domain);
		key.setEQP_TYPE(eqpType);
		key.setOD_TYPE_ID(odTypeId);
		TbSysOrderResp resp = orderRestDao.findByPk(key);
		// 先找TB_SYS_ORDER_RESP找特例，如果沒被設定就回原本的TB_SYS_ORDER_TYPE拿關鍵字
		return resp != null ? resp.getDEPT_KEY() : orderTypeDao.findByPk(odTypeId).getDEPT_KEY();
	}
	
	/**
	 * 以工務相關關鍵KEY、區域找工單應指派單位
	 * @param wkTypeId
	 * @param cityId
	 * @param townId
	 * @return 
	 * @throws Exception 
	 */
	public TbOrgDept getOrderTypeForWorkArea(String deptKey, TownDomainTeamDTO domainTeam) throws NomsException {
		
		try {			
			if (domainTeam != null) {			
				TbOrgDeptExample example = new TbOrgDeptExample();
				List<String> relationDomains = new LinkedList<String>();
				String team = domainTeam.getTEAM();
				TbOrgDept teamDept = deptDao.findByPk(team);
				if(teamDept != null){
					String domain = teamDept.getDOMAIN();
					do {
						relationDomains.add(domain);
						domain = StringUtils.substring(domain, 0, domain.length() - 1);
					} while (domain.length() >= 1);
					
					example.createCriteria().andDEPT_LEVELEqualTo("DP").andDEPT_NAMELike("%"+deptKey+"%").andDOMAINIn(relationDomains);
					List<TbOrgDept> depts = deptDao.findByCondition(example);
					
					//先用本區找,找不到才找全區--Kevin20150326
					if (depts.isEmpty() ) {
//						throw new NomsException("部門DOMAIN設定錯誤");
//						relationDomains = new LinkedList<String>();
						relationDomains.add("HQ");
						example.createCriteria().andDEPT_LEVELEqualTo("DP").andDEPT_NAMELike("%"+deptKey+"%").andDOMAINIn(relationDomains);
						depts = deptDao.findByCondition(example);
						if (depts.isEmpty() || depts.size() > 1) {
							throw new NomsException("部門DOMAIN設定錯誤");
						}
					} else if(depts.size() > 1){
						throw new NomsException("部門DOMAIN設定錯誤");
					}
					
					return depts.get(0);
				}else{
					throw new NomsException("查無此部門 ,部門代碼為"+domainTeam.getTEAM());
				}
				
			} else {
				throw new NomsException("查無DomainTeam資料");
			}
		} catch (NomsException e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
	
}
