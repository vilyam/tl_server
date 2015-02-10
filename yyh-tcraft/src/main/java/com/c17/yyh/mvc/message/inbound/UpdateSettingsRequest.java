package com.c17.yyh.mvc.message.inbound;

import com.c17.yyh.server.message.BaseInboundMessage;

public class UpdateSettingsRequest extends BaseInboundMessage {
	private String param;
	
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
}
