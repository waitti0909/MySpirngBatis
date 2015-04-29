package com.foya.noms.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foya.dao.mybatis.model.TbSysUserAudit;
import com.foya.noms.dao.system.UserAuditDao;
import com.foya.noms.service.BaseService;

/**
 * 紀錄使用者行為
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
 * <td>2014/8/6</td>
 * <td>新建檔案</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@Service
public class UserAuditService extends BaseService {

	@Autowired
	private UserAuditDao sysUserAuditDao;
	
	public void insert(TbSysUserAudit model) {
		sysUserAuditDao.insert(model);
	}

}
