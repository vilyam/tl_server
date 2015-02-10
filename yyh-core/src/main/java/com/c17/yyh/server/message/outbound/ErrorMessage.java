package com.c17.yyh.server.message.outbound;

public class ErrorMessage {
	private int error_code;
	private String error_msg;
	private Boolean critical;
	
	public ErrorMessage(int error_code, String error_msg, Boolean critical) {
		super();
		this.error_code = error_code;
		this.error_msg = error_msg;
		this.critical = critical;
	}

	public ErrorMessage(int errorCode) {
		this.setError_code(errorCode);
	}

	public int getError_code() {
		return error_code;
	}

	void setError_code(int error_code) {
		this.error_code = error_code;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public Boolean getCritical() {
		return critical;
	}

	public void setCritical(Boolean critical) {
		this.critical = critical;
	}
}
