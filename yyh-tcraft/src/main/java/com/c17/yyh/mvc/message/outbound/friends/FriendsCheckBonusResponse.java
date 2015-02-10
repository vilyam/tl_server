package com.c17.yyh.mvc.message.outbound.friends;

import java.util.List;

import com.c17.yyh.models.friends.FriendBonusReferal;
import com.c17.yyh.server.message.BaseOutboundMessage;

public class FriendsCheckBonusResponse extends BaseOutboundMessage {
    private List<FriendBonusReferal>  bonus;
    private int old_count;
    public FriendsCheckBonusResponse() {
        super("/friends/bonus/check");
    }
    public List<FriendBonusReferal> getBonus() {
        return bonus;
    }
    public void setBonus(List<FriendBonusReferal> friends_bonus) {
        this.bonus = friends_bonus;
    }
	public int getOld_count() {
		return old_count;
	}
	public void setOld_count(int old_count) {
		this.old_count = old_count;
	}

}
