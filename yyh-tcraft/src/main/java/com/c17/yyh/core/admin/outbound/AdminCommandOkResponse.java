package com.c17.yyh.core.admin.outbound;

import com.c17.yyh.server.message.BaseOutboundMessage;

public class AdminCommandOkResponse extends BaseOutboundMessage {

    public AdminCommandOkResponse() {
        super("/admin/command");
    }

    private String resourceReloadCommand;

    public String getResourceReloadCommand() {
        return resourceReloadCommand;
    }

    public void setResourceReloadCommand(String resourceReloadCommand) {
        this.resourceReloadCommand = resourceReloadCommand;
    }

}
