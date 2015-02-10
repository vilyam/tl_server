package com.c17.yyh.mvc.message.outbound;

import com.c17.yyh.models.User;
import com.c17.yyh.server.message.BaseOutboundMessage;

public class BuyPetResponse extends BaseOutboundMessage {
    private User user;
    
    public BuyPetResponse() {
        super("/adventure/pet/buy");
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    
}
