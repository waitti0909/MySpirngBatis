package com.foya.dao.mybatis.model;

public class TbComPoItemKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_COM_PO_ITEM.PO_ID
	 * @mbggenerated  Fri Jan 23 10:24:01 GMT+08:00 2015
	 */
	private Long PO_ID;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_COM_PO_ITEM.ITEM_ID
	 * @mbggenerated  Fri Jan 23 10:24:01 GMT+08:00 2015
	 */
	private String ITEM_ID;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_COM_PO_ITEM.PO_ID
	 * @return  the value of TB_COM_PO_ITEM.PO_ID
	 * @mbggenerated  Fri Jan 23 10:24:01 GMT+08:00 2015
	 */
	public Long getPO_ID() {
		return PO_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_COM_PO_ITEM.PO_ID
	 * @param PO_ID  the value for TB_COM_PO_ITEM.PO_ID
	 * @mbggenerated  Fri Jan 23 10:24:01 GMT+08:00 2015
	 */
	public void setPO_ID(Long PO_ID) {
		this.PO_ID = PO_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_COM_PO_ITEM.ITEM_ID
	 * @return  the value of TB_COM_PO_ITEM.ITEM_ID
	 * @mbggenerated  Fri Jan 23 10:24:01 GMT+08:00 2015
	 */
	public String getITEM_ID() {
		return ITEM_ID;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_COM_PO_ITEM.ITEM_ID
	 * @param ITEM_ID  the value for TB_COM_PO_ITEM.ITEM_ID
	 * @mbggenerated  Fri Jan 23 10:24:01 GMT+08:00 2015
	 */
	public void setITEM_ID(String ITEM_ID) {
		this.ITEM_ID = ITEM_ID == null ? null : ITEM_ID.trim();
	}
}