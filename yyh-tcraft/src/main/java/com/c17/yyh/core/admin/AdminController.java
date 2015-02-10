package com.c17.yyh.core.admin;

import com.c17.yyh.core.admin.inbound.AdminOperationRequest;
import com.c17.yyh.core.admin.outbound.AdminCommandOkResponse;
import com.c17.yyh.server.message.BaseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminManager adminManager;

    @RequestMapping(value = "/command", method= RequestMethod.POST)
    public @ResponseBody BaseMessage process(@RequestBody AdminOperationRequest msg) {
        adminManager.executeAdminCommand(msg.getCommand(), adminManager.new AdminCredentials(msg.getUser(), msg.getPassword()), msg.getData());
        AdminCommandOkResponse resp = new AdminCommandOkResponse();
        resp.setResourceReloadCommand(msg.getCommand());
        return resp;
    }

}
