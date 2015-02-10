package com.c17.yyh.exceptions;

@SuppressWarnings("serial")
public class ServerException extends RuntimeException {
	private int error_code;
	private String error_msg;
	private Boolean critical;
	
	public ServerException(int error_code, String error_msg, Boolean critical) {
		super();
		this.error_code = error_code;
		this.error_msg = error_msg;
		this.critical = critical;
	}

	public ServerException(int error_code, String error_msg, Boolean critical, Throwable e) {
		super(e);
		this.error_code = error_code;
		this.error_msg = error_msg;
		this.critical = critical;
	}
	
	public int getErrorCode() {
		return error_code;
	}

	public Boolean getCritical() {
		return critical;
	}

	public void setCritical(Boolean critical) {
		this.critical = critical;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
}
