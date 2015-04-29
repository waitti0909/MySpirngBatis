package com.foya.noms.dto.org;

import com.foya.dao.mybatis.model.TbOrgDept;

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
 * <td>2014/9/23</td>
 * <td>新建檔案</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public class DeptDTO extends TbOrgDept {
	private String DOMAIN_NAME;
	
	private String level;

	private String posName ;
	
	private String posId ;
	
	private String posType ;
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getDOMAIN_NAME() {
		return DOMAIN_NAME;
	}

	public void setDOMAIN_NAME(String dOMAIN_NAME) {
		DOMAIN_NAME = dOMAIN_NAME;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	@Override
	public String getDEPT_NAME() {
		
		return super.getDEPT_NAME();
	}

	public String getPosType() {
		return posType;
	}

	public void setPosType(String posType) {
		this.posType = posType;
	}
	
	
	

}
