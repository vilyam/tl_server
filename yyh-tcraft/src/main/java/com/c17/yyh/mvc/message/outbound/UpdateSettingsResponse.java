package com.c17.yyh.mvc.message.outbound;

import com.c17.yyh.models.User;
import com.c17.yyh.server.message.BaseOutboundMessage;

public class UpdateSettingsResponse extends BaseOutboundMessage {
	private User user;
	
	public UpdateSettingsResponse() {
	    super("/user/update_settings");
	}

	public UpdateSettingsResponse(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
