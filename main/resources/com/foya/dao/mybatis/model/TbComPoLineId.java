package com.foya.dao.mybatis.model;

import java.math.BigDecimal;

public class TbComPoLineId extends TbComPoLineIdKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_COM_PO_LINE_ID.ITEM
	 * @mbggenerated  Wed Jan 21 10:58:38 CST 2015
	 */
	private String ITEM;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_COM_PO_LINE_ID.UNIT_PRICE
	 * @mbggenerated  Wed Jan 21 10:58:38 CST 2015
	 */
	private BigDecimal UNIT_PRICE;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_COM_PO_LINE_ID.QTY
	 * @mbggenerated  Wed Jan 21 10:58:38 CST 2015
	 */
	private BigDecimal QTY;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_COM_PO_LINE_ID.AMOUNT
	 * @mbggenerated  Wed Jan 21 10:58:38 CST 2015
	 */
	private BigDecimal AMOUNT;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_COM_PO_LINE_ID.ITEM
	 * @return  the value of TB_COM_PO_LINE_ID.ITEM
	 * @mbggenerated  Wed Jan 21 10:58:38 CST 2015
	 */
	public String getITEM() {
		return ITEM;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_COM_PO_LINE_ID.ITEM
	 * @param ITEM  the value for TB_COM_PO_LINE_ID.ITEM
	 * @mbggenerated  Wed Jan 21 10:58:38 CST 2015
	 */
	public void setITEM(String ITEM) {
		this.ITEM = ITEM == null ? null : ITEM.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_COM_PO_LINE_ID.UNIT_PRICE
	 * @return  the value of TB_COM_PO_LINE_ID.UNIT_PRICE
	 * @mbggenerated  Wed Jan 21 10:58:38 CST 2015
	 */
	public BigDecimal getUNIT_PRICE() {
		return UNIT_PRICE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_COM_PO_LINE_ID.UNIT_PRICE
	 * @param UNIT_PRICE  the value for TB_COM_PO_LINE_ID.UNIT_PRICE
	 * @mbggenerated  Wed Jan 21 10:58:38 CST 2015
	 */
	public void setUNIT_PRICE(BigDecimal UNIT_PRICE) {
		this.UNIT_PRICE = UNIT_PRICE;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_COM_PO_LINE_ID.QTY
	 * @return  the value of TB_COM_PO_LINE_ID.QTY
	 * @mbggenerated  Wed Jan 21 10:58:38 CST 2015
	 */
	public BigDecimal getQTY() {
		return QTY;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_COM_PO_LINE_ID.QTY
	 * @param QTY  the value for TB_COM_PO_LINE_ID.QTY
	 * @mbggenerated  Wed Jan 21 10:58:38 CST 2015
	 */
	public void setQTY(BigDecimal QTY) {
		this.QTY = QTY;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_COM_PO_LINE_ID.AMOUNT
	 * @return  the value of TB_COM_PO_LINE_ID.AMOUNT
	 * @mbggenerated  Wed Jan 21 10:58:38 CST 2015
	 */
	public BigDecimal getAMOUNT() {
		return AMOUNT;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_COM_PO_LINE_ID.AMOUNT
	 * @param AMOUNT  the value for TB_COM_PO_LINE_ID.AMOUNT
	 * @mbggenerated  Wed Jan 21 10:58:38 CST 2015
	 */
	public void setAMOUNT(BigDecimal AMOUNT) {
		this.AMOUNT = AMOUNT;
	}
}