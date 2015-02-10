package com.c17.yyh.mvc.message.outbound.tools;

import com.c17.yyh.server.message.BaseOutboundMessage;

/**
 * Created with IntelliJ IDEA.
 * User: DRybak
 * Date: 7/11/13
 * Time: 2:32 AM
 */
public class UseToolFailResponse extends BaseOutboundMessage {
    private String errorDescription;

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }
}
