package com.c17.yyh.core.social;

import com.c17.yyh.core.social.type.ErrorCode;


public class PaymentException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 128662291243181536L;
	
	private ErrorCode code;
	private String message;
	private int status;
	private String uid;

	public PaymentException(ErrorCode code, int status, String message, String uid) {
		super();
		this.code = code;
		this.status = status;
		this.message = message;
		this.uid = uid;
	}
	
	public ErrorCode getCode() {
		return code;
	}
	public void setCode(ErrorCode code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

}
