package com.foya.exception;

public class DbException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4256340663837311792L;
	private String errCode;
	private String errMsg;

	public DbException(String message) {
		super(message);
	}

	public DbException(String errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
