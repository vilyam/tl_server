package com.c17.yyh.mvc.message.outbound.tools;

import com.c17.yyh.models.User;
import com.c17.yyh.server.message.BaseOutboundMessage;

public class BuyToolResponse extends BaseOutboundMessage {
    private User user;
    public BuyToolResponse(User user) {
        this.setAction("/tool/buy");
        this.setUser(user);
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
