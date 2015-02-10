package com.c17.yyh.mvc.message.outbound.socialQuest;

import com.c17.yyh.models.User;
import com.c17.yyh.server.message.BaseOutboundMessage;

public class GetSocialBonusAwardResponse extends BaseOutboundMessage {
	private User user;

	public GetSocialBonusAwardResponse(User user) {
		this();
		this.user = user;
	}

	public GetSocialBonusAwardResponse(String action) {
        super("/social/claim_bonus");
    }

    public GetSocialBonusAwardResponse() {
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
