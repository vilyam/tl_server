package com.c17.yyh.mvc.message.outbound.friends;

import java.util.List;

import com.c17.yyh.models.friends.FriendProgress;
import com.c17.yyh.server.message.BaseOutboundMessage;

public class FriendsProgressResponse extends BaseOutboundMessage {
    
	public FriendsProgressResponse() {
        super("/friends/progress");
    }

    private List<FriendProgress> friendsProgress;

	public List<FriendProgress> getFriendsProgress() {
		return friendsProgress;
	}

	public void setFriendsProgress(List<FriendProgress> friendsProgress) {
		this.friendsProgress = friendsProgress;
	}
}
