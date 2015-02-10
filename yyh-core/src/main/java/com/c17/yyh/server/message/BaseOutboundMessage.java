package com.c17.yyh.server.message;

public class BaseOutboundMessage extends BaseMessage {

    public BaseOutboundMessage(String action) {
        super();
        this.action = action;
    }

    public BaseOutboundMessage() {
        super();
    }

    private String action;

    public String getAction() {
        return action;
    }

    public BaseOutboundMessage setAction(String action) {
        this.action = action;
        return this;
    }

    @Override
    public String toString() {
        return "BaseOutboundMessage{" + "action=" + action + '}';
    }

}
