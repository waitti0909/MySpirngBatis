package com.foya.noms.dto;

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
 * <td>2014/8/25</td>
 * <td>本DTO使用於配對JsTree元素</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */

public class JsTreeDTO {
	//{ "id" : "ajson2", "parent" : "#", "text" : "Root node 2" },
	private String id;
	private String parent;
	private String text;
	private String icon = "glyphicon glyphicon-tree";//"/noms/WebContent/resources/img/tree-icon.png";
	private JsTreeStateDTO state;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public JsTreeStateDTO getState() {
		return state;
	}
	public void setState(JsTreeStateDTO state) {
		this.state = state;
	}
}
