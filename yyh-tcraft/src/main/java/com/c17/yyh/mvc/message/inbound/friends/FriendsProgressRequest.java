package com.c17.yyh.mvc.message.inbound.friends;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.c17.yyh.server.message.BaseInboundMessage;

public class FriendsProgressRequest extends BaseInboundMessage{
	@JsonProperty("friends_sn_ids")
    private String[] friendsSnIds;

    public String[] getFriendsSnIds() {
        return friendsSnIds;
    }

    public void setFriendsSnIds(String[] friendsSnIds) {
        this.friendsSnIds = friendsSnIds;
    }
}
