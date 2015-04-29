package com.foya.noms.service.profile;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.noms.dao.profile.TodoOrdersDao;
import com.foya.noms.dao.st.WorkOrderDao;
import com.foya.noms.dto.auth.UserDTO;
import com.foya.noms.dto.profile.TodoOrdersDTO;
import com.foya.noms.service.BaseService;

@Service
public class TodoOrdersService extends BaseService {

	@Autowired
	private TodoOrdersDao dao;
	
	@Autowired
	private WorkOrderDao workOrderDao;
	
	/**
	 * 依登入人員取得個人所屬之待辦工單(包含站台、資材、租約、請款...等工務)
	 * @param user
	 */
	public List<TodoOrdersDTO> getTodoOrders(UserDTO user) {
		List<TodoOrdersDTO> orderList = new LinkedList<>();
		
		// 站台類
		return getSiteTodoOrders(orderList, user);
	}

	/**
	 * 取得站台類工單
	 * @param orderList
	 * @param user
	 * @return
	 */
	private List<TodoOrdersDTO> getSiteTodoOrders(List<TodoOrdersDTO> orderList, UserDTO user) {
		Map<String, String> map = new HashMap<String, String>();
		
		try {
			map.put("repUser", user.getUsername());
			log.debug("user.getUsername() = " + user.getUsername());
			
			// (尋點+建站)工單(已派工)→部門員工
			orderList.addAll(dao.selectOR04orders(map));
			
			// (尋點+建站)工單(待派工)→部門主管
			orderList.addAll(dao.selectOR03orders(map));
			
			// (尋點+建站)委外派工(待派工)→部門員工
			orderList.addAll(dao.selectOS04orders(map));
			
			// (建站)核網設定→核網部門人員
			orderList.addAll(dao.selectLA04LineApply(map));
			
		} catch (Exception e) {
			log.error("取得站點類工單發生錯誤!! " + ExceptionUtils.getFullStackTrace(e));
		}
		
		return orderList;
	}

}
