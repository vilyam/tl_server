package com.c17.yyh.core.admin.type.commands;

import java.util.Map;

import com.c17.yyh.core.admin.type.AdminCommandType;
import com.c17.yyh.core.admin.type.Command;
import com.c17.yyh.core.admin.type.GrantLevelType;
import com.c17.yyh.managers.game.TreasureManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AdminCommandType(name = "treasure_reload", level = GrantLevelType.ADMIN)
public class TreasureReloadCommand extends Command {

    @Autowired
    private TreasureManager treasureManager;

    @Override
    public void execute(Map<String, String> data) {
        treasureManager.loadTreasures();
        
        logger.info("Treasures was reloaded");
    }

}
