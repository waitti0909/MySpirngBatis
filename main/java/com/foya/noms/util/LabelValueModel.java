package com.foya.noms.util;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

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
 * <td>2014/10/9</td>
 * <td>新建檔案</td>
 * <td>Charlie Woo</td>
 * </tr>
 * </table> 
 *
 * @author Charlie Woo
 *
 */
@SuppressWarnings("serial")
public class LabelValueModel implements Serializable {

	/** The label. */
	private String label;

	/** The value. */
	private String value;
	
	private String attr1;
	
	private String attr2;

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("label", label).append("value", value).toString();
	}

	/**
	 * Instantiates a new label value model.
	 */
	public LabelValueModel() {
		super();
	}

	/**
	 * Instantiates a new label value model.
	 * 
	 * @param label
	 *            the label
	 * @param value
	 *            the value
	 */
	public LabelValueModel(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}

	/**
	 * Gets the label.
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 * 
	 * @param label
	 *            the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets the label value model.
	 * 
	 * @param label
	 *            the label
	 * @param value
	 *            the value
	 * @return the label value model
	 */
	public static LabelValueModel getLabelValueModel(String label, String value) {
		return new LabelValueModel(label, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LabelValueModel other = (LabelValueModel) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}
	
	
}
