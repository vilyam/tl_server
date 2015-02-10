package com.c17.yyh.mvc.message.outbound.friends;

import java.util.List;

import com.c17.yyh.models.friends.FriendBonusItemWrapper;
import com.c17.yyh.server.message.BaseOutboundMessage;

public class FriendsGetBonusResponse extends BaseOutboundMessage {
    private List<FriendBonusItemWrapper> bonus;
    public FriendsGetBonusResponse() {
        super("/friends/bonus/get");
    }
    public List<FriendBonusItemWrapper> getBonus() {
        return bonus;
    }
    public void setBonus(List<FriendBonusItemWrapper> bonus) {
        this.bonus = bonus;
    }
}
