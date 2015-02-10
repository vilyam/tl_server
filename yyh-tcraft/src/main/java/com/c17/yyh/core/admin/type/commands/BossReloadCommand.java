package com.c17.yyh.core.admin.type.commands;

import java.util.Map;

import com.c17.yyh.core.admin.type.AdminCommandType;
import com.c17.yyh.core.admin.type.Command;
import com.c17.yyh.core.admin.type.GrantLevelType;
import com.c17.yyh.managers.game.BossManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AdminCommandType(name = "boss_reload", level = GrantLevelType.ADMIN)
public class BossReloadCommand extends Command {

    @Autowired
    private BossManager bossManager;

    @Override
    public void execute(Map<String, String> data) {
        bossManager.loadBosses();
        
        logger.info("Bosses was reloaded");
    }

}
