package com.c17.yyh.core.admin.type.commands;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.c17.yyh.core.admin.type.AdminCommandType;
import com.c17.yyh.core.admin.type.Command;
import com.c17.yyh.core.admin.type.GrantLevelType;
import com.c17.yyh.managers.UserManager;
import com.c17.yyh.models.User;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.exceptions.ServerException;

@Component
@AdminCommandType(name = "user_add_crystals", level = GrantLevelType.ADMIN)
public class UserAddCrystalsCommand extends Command{
    @Autowired
    private UserManager userManager;
	
    @Override
    public void execute(Map<String, String> data) {
        if (data == null) {
        	throw new ServerException(ErrorCodes.ADMIN.PARAMETERS_NOT_CORRECT, "You send not correct 'data' parameters", true);
        }
        String userSnId = data.get("snId");
        Integer value = Integer.parseInt(data.get("value"));
        
        if (userSnId == null || value == null) {
        	throw new ServerException(ErrorCodes.ADMIN.PARAMETERS_NOT_CORRECT, "You send not correct 'data' parameters", true);
        }
        
        User user = userManager.getUserBySnId(userSnId);
        
        userManager.incrementCrystals(user, value, true);
        
        logger.info("User {}: {} crystals was added", userSnId, value);
    }

}
