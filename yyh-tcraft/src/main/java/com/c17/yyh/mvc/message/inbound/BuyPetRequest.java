package com.c17.yyh.mvc.message.inbound;

import com.c17.yyh.server.message.BaseInboundMessage;

public class BuyPetRequest extends BaseInboundMessage {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
