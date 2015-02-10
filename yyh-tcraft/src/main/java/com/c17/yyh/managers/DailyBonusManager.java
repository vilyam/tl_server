package com.c17.yyh.managers;

import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.constants.Constants;
import com.c17.yyh.core.CacheService;
import com.c17.yyh.db.dao.IDailyBonusDao;
import com.c17.yyh.db.entities.XmlDailyBonus;
import com.c17.yyh.models.DailyBonus;
import com.c17.yyh.models.User;

@Service("dailyBonusManager")
public class DailyBonusManager {

    private HashMap<Integer, XmlDailyBonus> dailyBonuses;
    
    @Autowired
    private ServerConfig serverConfig;
    
    @Autowired
    IDailyBonusDao dailyBonusService;
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    CacheService cacheService;
    
    private long dayDuration;
    private long dayLimit;
    
    @PostConstruct
    protected void initialize() {
        LoggerFactory.getLogger(getClass()).info("Initialize Daily Bonus Manager");
        
        dailyBonuses = dailyBonusService.getDailyBonuses(serverConfig.dailyBonus.dayQuantity);
        
        dayDuration = serverConfig.dailyBonus.dayDuration * Constants.MSEC_IN_MIN;
        dayLimit = serverConfig.dailyBonus.dayLimit * Constants.MSEC_IN_MIN;
    }
    
    public DailyBonus checkDailyBonus(User user, boolean isFirstLogin) {
        //user can take daily bonus only after 1 day
        if (isFirstLogin || System.currentTimeMillis() - user.getFirstTimeDailyLogin() < Constants.MSEC_IN_DAY) {
            return null;
        }
        
        long now = System.currentTimeMillis();

        //last time when user was take daily bonus
        long dbDiff = (now - user.getLastTimeDailyBonusLogin());
        
        int dbMarker = user.getDailyBonusMarker();
        
        boolean isUpdate = false;

        //when time is between day bonders from config
        if (dbDiff < dayDuration + dayLimit && dbDiff > dayDuration) {
            if (dbMarker < serverConfig.dailyBonus.dayQuantity) {
                dbMarker++;
            } else {
                dbMarker = 1;
            }
            
            isUpdate = true;

            //user must take daily bonus form first day
        } else if (dbDiff > dayDuration + dayLimit) {
            dbMarker = 1;
            
            isUpdate = true;
        }
        
        if (user.isDebug() && !isUpdate) {
            getLogger().info("User id {} must wait for getting Daily Bonus {} minutes", user.getUserId(), dayDuration - dbDiff);
        }
        
        if (isUpdate) {
            XmlDailyBonus xmlDb = dailyBonuses.get(dbMarker);
            if (xmlDb == null) {
                return null;
            }
            
            DailyBonus db = updateDailyBonus(user, xmlDb, now);
            
            if (user.isDebug()) {
                getLogger().info("User id {} getted Daily Bonus {} day", user.getUserId(), db.getDay());
            }
            
            return db;
        } else {
            return null;
        }
    }
    
    private DailyBonus updateDailyBonus(User user, XmlDailyBonus xmlDb, long dailyBonusLogin) {
        DailyBonus db = xmlDb.getDailyBonus();
        int deltaMoney = db.getMoney();
        int deltaCrystals = db.getCrystal();
        
        if (deltaMoney > 0 && deltaCrystals > 0) {
            userManager.updateBalance(user, deltaMoney, deltaCrystals, false);
        }
        if (deltaMoney > 0) {
            userManager.incrementMoney(user, deltaMoney, false);
        }
        if (deltaCrystals > 0) {
            userManager.incrementCrystals(user, deltaCrystals, false);
        }
        
        user.setDailyBonusMarker(db.getDay());
        user.setLastTimeDailyBonusLogin(dailyBonusLogin);
        dailyBonusService.updateDailyBonusState(user);

        cacheService.updateUserInCache(user);
        
        return db;
    }
    
    private Logger getLogger() {
        return LoggerFactory.getLogger(String.valueOf(Thread.currentThread().getId()) + getClass());
    }
}
