package com.c17.yyh.mvc.message.outbound;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.c17.yyh.db.entities.adventure.Award;
import com.c17.yyh.models.User;
import com.c17.yyh.server.message.BaseOutboundMessage;

public class SessionEndedAdventureResponse extends BaseOutboundMessage {
    
	public SessionEndedAdventureResponse() {
        super("/adventure/end");
    }

    private User user;
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
	private Award award;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Award getAward() {
		return award;
	}

	public void setAward(Award award) {
		this.award = award;
	}
}
