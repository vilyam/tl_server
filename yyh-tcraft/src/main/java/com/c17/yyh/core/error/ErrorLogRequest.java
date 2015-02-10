package com.c17.yyh.core.error;

import com.c17.yyh.server.message.BaseInboundMessage;

public class ErrorLogRequest extends BaseInboundMessage {
	private String secret_key;
	private String browser;
	private String flash_version;
	private String error_msg;
	
	public String getSecret_key() {
		return secret_key;
	}
	public void setSecret_key(String secret_key) {
		this.secret_key = secret_key;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getFlash_version() {
		return flash_version;
	}
	public void setFlash_version(String flash_version) {
		this.flash_version = flash_version;
	}
	public String getError_msg() {
		return error_msg;
	}
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
}
