package com.foya.exception;

public class CreateFailException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4256340663837311792L;
	private String errCode="msg.add.fail";
	private String errMsg;

	public CreateFailException(String message) {
		super(message);
	}

	public CreateFailException(String errCode, String errMsg) {
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
