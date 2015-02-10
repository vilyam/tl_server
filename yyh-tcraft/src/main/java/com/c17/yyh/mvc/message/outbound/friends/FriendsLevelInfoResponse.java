package com.c17.yyh.mvc.message.outbound.friends;

import com.c17.yyh.models.friends.FriendInfo;
import com.c17.yyh.server.message.BaseOutboundMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: nord
 * Date: 20.08.13
 * Time: 1:29
 */
public class FriendsLevelInfoResponse extends BaseOutboundMessage {
    
    public FriendsLevelInfoResponse() {
        super("/friends/info");
    }

    private List<FriendInfo> friendInfos;

    public List<FriendInfo> getFriendInfos() {
        return friendInfos;
    }

    public void setFriendInfos(List<FriendInfo> friendInfos) {
        this.friendInfos = friendInfos;
    }
}
