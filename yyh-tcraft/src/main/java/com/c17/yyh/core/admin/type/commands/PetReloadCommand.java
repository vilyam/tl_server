package com.c17.yyh.core.admin.type.commands;

import java.util.Map;

import com.c17.yyh.core.admin.type.AdminCommandType;
import com.c17.yyh.core.admin.type.Command;
import com.c17.yyh.core.admin.type.GrantLevelType;
import com.c17.yyh.managers.game.PetManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AdminCommandType(name = "pet_reload", level = GrantLevelType.ADMIN)
public class PetReloadCommand extends Command {

    @Autowired
    private PetManager petManager;

    @Override
    public void execute(Map<String, String> data) {
        petManager.loadPets();
        
        logger.info("Pets was reloaded");
    }

}
