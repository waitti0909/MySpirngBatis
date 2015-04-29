package com.foya.noms.enums;

/**
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
 * <td>2015年1月11日</td>
 * <td>寄送EMAIL之類型</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
public enum EmailType {

	WORK_APPLY,
	ORDER_START,
	ORDER_INFORM,
	ORDER_INFORM_M,
	WORK_FINISH,
	OUTSOURCE_START,
	LINE_SETUP,
	LINE_CANCEL,
	
	WORKFLOW_TODO_WORK_APPLY,
	WORKFLOW_TODO_WORK_COMPLETION_APPLY,
	WORKFLOW_TODO_WORK_LEASE_LINE_APPLY,
	WORKFLOW_TODO_WORK_MATERIAL_APPLY,
	WORKFLOW_TODO_WORK_OUTSOURCING_ACCEPT,
	WORKFLOW_TODO_WORK_OUTSOURCING_APPLY,
	;
		
}
