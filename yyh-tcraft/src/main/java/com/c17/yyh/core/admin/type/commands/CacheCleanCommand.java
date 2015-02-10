package com.c17.yyh.core.admin.type.commands;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.core.CacheService;
import com.c17.yyh.core.admin.type.AdminCommandType;
import com.c17.yyh.core.admin.type.Command;
import com.c17.yyh.core.admin.type.GrantLevelType;
import com.c17.yyh.exceptions.ServerException;

@Component
@AdminCommandType(name = "cache_clean", level = GrantLevelType.ADMIN)
public class CacheCleanCommand extends Command{
	@Autowired
	CacheService cacheService;
	
    @Override
    public void execute(Map<String, String> data) {
        if (data == null || data.isEmpty()) {
        	cacheService.clearCache();
        	
            logger.info("Cache was cleared");
        } else {
        	String userSnId = data.get("snId");
        	
        	if (userSnId == null) {
        		throw new ServerException(ErrorCodes.ADMIN.PARAMETERS_NOT_CORRECT, "You send not correct 'data' parameters", true);
        	}
        	
        	cacheService.clearCacheForUser(userSnId);
        	
            logger.info("Cache was cleared for user: {}", userSnId);
        }   
    }

}
