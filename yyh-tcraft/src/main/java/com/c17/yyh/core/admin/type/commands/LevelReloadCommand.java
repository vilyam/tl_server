package com.c17.yyh.core.admin.type.commands;

import java.util.Map;

import com.c17.yyh.core.admin.type.AdminCommandType;
import com.c17.yyh.core.admin.type.Command;
import com.c17.yyh.core.admin.type.GrantLevelType;
import com.c17.yyh.managers.game.adventure.AdventureLevelManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AdminCommandType(name = "level_reload", level = GrantLevelType.ADMIN)
public class LevelReloadCommand extends Command {

    @Autowired
    private AdventureLevelManager adventureLevelManager;

    @Override
    public void execute(Map<String, String> data) {
        adventureLevelManager.loadLevels();
        
        logger.info("Levelsets was reloaded");
    }

}
