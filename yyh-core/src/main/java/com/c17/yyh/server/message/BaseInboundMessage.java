package com.c17.yyh.server.message;

public abstract class BaseInboundMessage extends BaseMessage {
	private String tp;
	private String snId;

	public String getSnId() {
		return snId;
	}

	public void setSnId(String snId) {
		this.snId = snId;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = tp;
	}
}
