package com.c17.yyh.common.message.inbound.admin;

import com.c17.yyh.server.message.BaseInboundMessage;

public abstract class BaseAdminMessage extends BaseInboundMessage {

    private String user;
    private String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
