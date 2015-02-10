package com.c17.yyh.core.admin.type.commands;

import com.c17.yyh.core.admin.type.AdminCommandType;
import com.c17.yyh.core.admin.type.Command;
import com.c17.yyh.core.admin.type.GrantLevelType;
import com.c17.yyh.managers.game.ToolsManager;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AdminCommandType(name = "toolitems_reload", level = GrantLevelType.ADMIN)
public class ToolItemReloadCommand extends Command {

    @Autowired
    private ToolsManager toolsManager;

    @Override
    public void execute(Map<String, String> data) {
        toolsManager.loadToolItems();
        logger.info("Tool items were reloaded");
    }

}
