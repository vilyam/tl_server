package com.c17.yyh.processors;

import java.security.MessageDigest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.db.dao.IDumpDao;
import com.c17.yyh.managers.DailyBonusManager;
import com.c17.yyh.managers.FriendsManager;
import com.c17.yyh.managers.UserManager;
import com.c17.yyh.managers.game.ToolsManager;
import com.c17.yyh.managers.purchasing.PaymentsManager;
import com.c17.yyh.models.DailyBonus;
import com.c17.yyh.models.User;
import com.c17.yyh.mvc.message.inbound.login.BaseLoginRequest;
import com.c17.yyh.mvc.message.outbound.login.UserLoadResponse;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.exceptions.ServerException;

@Service
public class UserProcessor {
    
    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private IDumpDao dumpService;

    @Autowired
    private UserManager userManager;

    @Autowired
    private PaymentsManager paymentsManager;

    @Autowired
    private ToolsManager toolsManager;

    @Autowired
    private DailyBonusManager dailyBonusManager;
    
    @Autowired
    FriendsManager friendsManager;

    public void loginUser(BaseLoginRequest request, UserLoadResponse response) {
        String userSnId = request.getSnId();
        User user = userManager.getUserBySnId(userSnId);

        //create new user
        if (user == null) {
            user = userManager.createUser(userSnId, request.getName(),
                    request.getLastName(), request.getSex(), request.getCity(),
                    request.getSecretCode(), request.getCountry(), request.getBirthday(),
                    request.getLanguage(), request.getHash(), request.getUser_page(), request.getReferrer());

            response.setFirst_login(true);

            //if hash for "user info" changed - then update hash and "user info"
        } else if (!MessageDigest.isEqual(user.getHash().getBytes(), request.getHash().getBytes())) {
            userManager.updateUserInfo(user, request.getName(), request.getLastName(),
                    request.getSex(), request.getCity(), request.getCountry(),
                    request.getBirthday(), request.getLanguage(), request.getHash(), request.getUser_page());
            if (user.isDebug()) {
                LoggerFactory.getLogger(Thread.currentThread().getName()).info("User id {} was updated info", user.getUserId());
            }
        }

        //check secret key
        if (serverConfig.userSecretkeyEnabled
                && !MessageDigest.isEqual(user.getSecretCode().getBytes(), request.getSecretCode().getBytes())) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).warn("Wrong secret code for user id {}", user.getUserId());
            throw new ServerException(ErrorCodes.MISMATCH_CALC_AND_TRANSMITTED_SIG, "Wrong secret code", true);
        }

        //friends bonus
        if (response.isFirst_login() && request.getRef() != null && !request.getRef().isEmpty()) {
            friendsManager.insertReferal(userSnId, request.getRef());
        }
        
        long now = System.currentTimeMillis();

        //validate game lives from last game session
        userManager.validateGameLives(user);
        userManager.validateRoadBlock(user);
        toolsManager.validateLoading(user);

        //daily
        DailyBonus db = null;
        if (!request.isNoDailyBonus()) {
            db = dailyBonusManager.checkDailyBonus(user, response.isFirst_login());
        }
        response.setDaily_bonus(db);


        user.setLastTimeDailyLogin(now);

        userManager.updateUserSession(user);
        dumpService.writeLoginDump(user, db != null ? db.getDay() : 0);

        if (user.isDebug()) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).info("User id {} was logged in", user.getUserId());
        }

        response.setUser(user);
    }
}
