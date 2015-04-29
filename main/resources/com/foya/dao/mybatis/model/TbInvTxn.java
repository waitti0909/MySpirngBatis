package com.foya.dao.mybatis.model;

import java.util.Date;

public class TbInvTxn {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.inv_txn_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private Long inv_txn_no;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.txn_type
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private Byte txn_type;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.txn_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private String txn_no;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.wh_id
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private String wh_id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.site_id
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private String site_id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.order_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private String order_no;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.ref_act_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private String ref_act_no;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.mat_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private String mat_no;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.mat_status
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private Byte mat_status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.qty
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private Integer qty;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.fa_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private String fa_no;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.srl_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private Long srl_no;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.rn_resn
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private String rn_resn;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.rn_memo
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private String rn_memo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.cr_user
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private String cr_user;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.cr_time
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private Date cr_time;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.po_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private String po_no;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.po_line_nbr
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private Short po_line_nbr;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.tr_dtl_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private Long tr_dtl_no;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.dtl_seq
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private Long dtl_seq;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.mro_rt_user
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private String mro_rt_user;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column TB_INV_TXN.send_rcv_user
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	private String send_rcv_user;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.inv_txn_no
	 * @return  the value of TB_INV_TXN.inv_txn_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public Long getInv_txn_no() {
		return inv_txn_no;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.inv_txn_no
	 * @param inv_txn_no  the value for TB_INV_TXN.inv_txn_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setInv_txn_no(Long inv_txn_no) {
		this.inv_txn_no = inv_txn_no;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.txn_type
	 * @return  the value of TB_INV_TXN.txn_type
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public Byte getTxn_type() {
		return txn_type;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.txn_type
	 * @param txn_type  the value for TB_INV_TXN.txn_type
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setTxn_type(Byte txn_type) {
		this.txn_type = txn_type;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.txn_no
	 * @return  the value of TB_INV_TXN.txn_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public String getTxn_no() {
		return txn_no;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.txn_no
	 * @param txn_no  the value for TB_INV_TXN.txn_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setTxn_no(String txn_no) {
		this.txn_no = txn_no == null ? null : txn_no.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.wh_id
	 * @return  the value of TB_INV_TXN.wh_id
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public String getWh_id() {
		return wh_id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.wh_id
	 * @param wh_id  the value for TB_INV_TXN.wh_id
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setWh_id(String wh_id) {
		this.wh_id = wh_id == null ? null : wh_id.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.site_id
	 * @return  the value of TB_INV_TXN.site_id
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public String getSite_id() {
		return site_id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.site_id
	 * @param site_id  the value for TB_INV_TXN.site_id
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setSite_id(String site_id) {
		this.site_id = site_id == null ? null : site_id.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.order_no
	 * @return  the value of TB_INV_TXN.order_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public String getOrder_no() {
		return order_no;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.order_no
	 * @param order_no  the value for TB_INV_TXN.order_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setOrder_no(String order_no) {
		this.order_no = order_no == null ? null : order_no.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.ref_act_no
	 * @return  the value of TB_INV_TXN.ref_act_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public String getRef_act_no() {
		return ref_act_no;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.ref_act_no
	 * @param ref_act_no  the value for TB_INV_TXN.ref_act_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setRef_act_no(String ref_act_no) {
		this.ref_act_no = ref_act_no == null ? null : ref_act_no.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.mat_no
	 * @return  the value of TB_INV_TXN.mat_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public String getMat_no() {
		return mat_no;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.mat_no
	 * @param mat_no  the value for TB_INV_TXN.mat_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setMat_no(String mat_no) {
		this.mat_no = mat_no == null ? null : mat_no.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.mat_status
	 * @return  the value of TB_INV_TXN.mat_status
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public Byte getMat_status() {
		return mat_status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.mat_status
	 * @param mat_status  the value for TB_INV_TXN.mat_status
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setMat_status(Byte mat_status) {
		this.mat_status = mat_status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.qty
	 * @return  the value of TB_INV_TXN.qty
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public Integer getQty() {
		return qty;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.qty
	 * @param qty  the value for TB_INV_TXN.qty
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setQty(Integer qty) {
		this.qty = qty;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.fa_no
	 * @return  the value of TB_INV_TXN.fa_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public String getFa_no() {
		return fa_no;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.fa_no
	 * @param fa_no  the value for TB_INV_TXN.fa_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setFa_no(String fa_no) {
		this.fa_no = fa_no == null ? null : fa_no.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.srl_no
	 * @return  the value of TB_INV_TXN.srl_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public Long getSrl_no() {
		return srl_no;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.srl_no
	 * @param srl_no  the value for TB_INV_TXN.srl_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setSrl_no(Long srl_no) {
		this.srl_no = srl_no;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.rn_resn
	 * @return  the value of TB_INV_TXN.rn_resn
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public String getRn_resn() {
		return rn_resn;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.rn_resn
	 * @param rn_resn  the value for TB_INV_TXN.rn_resn
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setRn_resn(String rn_resn) {
		this.rn_resn = rn_resn == null ? null : rn_resn.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.rn_memo
	 * @return  the value of TB_INV_TXN.rn_memo
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public String getRn_memo() {
		return rn_memo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.rn_memo
	 * @param rn_memo  the value for TB_INV_TXN.rn_memo
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setRn_memo(String rn_memo) {
		this.rn_memo = rn_memo == null ? null : rn_memo.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.cr_user
	 * @return  the value of TB_INV_TXN.cr_user
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public String getCr_user() {
		return cr_user;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.cr_user
	 * @param cr_user  the value for TB_INV_TXN.cr_user
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setCr_user(String cr_user) {
		this.cr_user = cr_user == null ? null : cr_user.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.cr_time
	 * @return  the value of TB_INV_TXN.cr_time
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public Date getCr_time() {
		return cr_time;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.cr_time
	 * @param cr_time  the value for TB_INV_TXN.cr_time
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setCr_time(Date cr_time) {
		this.cr_time = cr_time;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.po_no
	 * @return  the value of TB_INV_TXN.po_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public String getPo_no() {
		return po_no;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.po_no
	 * @param po_no  the value for TB_INV_TXN.po_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setPo_no(String po_no) {
		this.po_no = po_no == null ? null : po_no.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.po_line_nbr
	 * @return  the value of TB_INV_TXN.po_line_nbr
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public Short getPo_line_nbr() {
		return po_line_nbr;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.po_line_nbr
	 * @param po_line_nbr  the value for TB_INV_TXN.po_line_nbr
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setPo_line_nbr(Short po_line_nbr) {
		this.po_line_nbr = po_line_nbr;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.tr_dtl_no
	 * @return  the value of TB_INV_TXN.tr_dtl_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public Long getTr_dtl_no() {
		return tr_dtl_no;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.tr_dtl_no
	 * @param tr_dtl_no  the value for TB_INV_TXN.tr_dtl_no
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setTr_dtl_no(Long tr_dtl_no) {
		this.tr_dtl_no = tr_dtl_no;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.dtl_seq
	 * @return  the value of TB_INV_TXN.dtl_seq
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public Long getDtl_seq() {
		return dtl_seq;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.dtl_seq
	 * @param dtl_seq  the value for TB_INV_TXN.dtl_seq
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setDtl_seq(Long dtl_seq) {
		this.dtl_seq = dtl_seq;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.mro_rt_user
	 * @return  the value of TB_INV_TXN.mro_rt_user
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public String getMro_rt_user() {
		return mro_rt_user;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.mro_rt_user
	 * @param mro_rt_user  the value for TB_INV_TXN.mro_rt_user
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setMro_rt_user(String mro_rt_user) {
		this.mro_rt_user = mro_rt_user == null ? null : mro_rt_user.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column TB_INV_TXN.send_rcv_user
	 * @return  the value of TB_INV_TXN.send_rcv_user
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public String getSend_rcv_user() {
		return send_rcv_user;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column TB_INV_TXN.send_rcv_user
	 * @param send_rcv_user  the value for TB_INV_TXN.send_rcv_user
	 * @mbggenerated  Wed Dec 17 14:12:15 CST 2014
	 */
	public void setSend_rcv_user(String send_rcv_user) {
		this.send_rcv_user = send_rcv_user == null ? null : send_rcv_user
				.trim();
	}
}