package com.c17.yyh.mvc.message.outbound.tools;

import com.c17.yyh.models.User;
import com.c17.yyh.server.message.BaseOutboundMessage;

/**
 * Created with IntelliJ IDEA.
 * User: DRybak
 * Date: 7/11/13
 * Time: 12:27 AM
 */

public class UseToolResponse extends BaseOutboundMessage {
    public UseToolResponse() {
        super("/tool/use");
    }

    private User user = null;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UseToolResponse response = (UseToolResponse) o;

        if (user != null ? !user.equals(response.user) : response.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return user != null ? user.hashCode() : 0;
    }
}
