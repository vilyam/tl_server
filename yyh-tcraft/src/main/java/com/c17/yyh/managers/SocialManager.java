package com.c17.yyh.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.core.CacheService;
import com.c17.yyh.db.dao.ISocialDao;
import com.c17.yyh.models.User;

@Service
public class SocialManager {
	
	@Autowired
	private ISocialDao socialService;
	
	@Autowired
	private ServerConfig serverConfig;
	
    @Autowired
    private UserManager userManager;
    
	@Autowired
	CacheService cacheService;

	public int updateState(User user, String state) {		
		int reqState = socialService.updateState(user.getUserId(), state);
		
		if(reqState > 0) {
			user.getSocialQuest().setState(state);
			
			cacheService.updateUserInCache(user);
		}
		
		return reqState;
	}
	
	//TODO: transactional
	public int getAward(User user) {
		int state = 0;
		state = userManager.incrementCrystals(user, serverConfig.socialQuestAward, false);
		if (state > 0) {
			state = socialService.getAward(user.getUserId());
			if (state > 0) {
				user.getSocialQuest().setIsDone(1);
				
				cacheService.updateUserInCache(user);
			}
		}
		
		return state;
	}

}
