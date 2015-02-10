package com.c17.yyh.server.message.outbound;

import com.c17.yyh.server.message.BaseOutboundMessage;

public class ErrorResponse extends BaseOutboundMessage {
	private ErrorMessage error;
	
	public ErrorResponse(int errorCode) {
		if (error == null) error = new ErrorMessage(errorCode);
		this.error.setError_code(errorCode);
	}

	public ErrorResponse(int error_code, String error_msg, Boolean critical) {
		super();
		if (error == null) {
			error = new ErrorMessage(error_code);
			error.setCritical(critical);
			error.setError_msg(error_msg);
		}
		this.error.setError_code(error_code);
		this.error.setError_msg(error_msg);
		this.error.setCritical(critical);
	}

	public ErrorMessage getError() {
		return this.error;
	}

	public void setError(ErrorMessage errorMessage) {
		this.error = errorMessage;
	}

	public void setError_msg(String error_msg) {
		this.error.setError_msg(error_msg) ;
	}

	public void setCritical(Boolean critical) {
		this.error.setCritical(critical);
	}

}
