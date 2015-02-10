package com.c17.yyh.mvc.message.inbound.socialQuest;

import com.c17.yyh.server.message.BaseInboundMessage;

public class UpdateSocialQuestRequest extends BaseInboundMessage{
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
