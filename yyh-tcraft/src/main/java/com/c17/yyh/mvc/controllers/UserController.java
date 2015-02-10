package com.c17.yyh.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.managers.FriendsManager;
import com.c17.yyh.managers.UserManager;
import com.c17.yyh.managers.game.BossManager;
import com.c17.yyh.managers.game.PetManager;
import com.c17.yyh.managers.game.ToolsManager;
import com.c17.yyh.managers.game.TreasureManager;
import com.c17.yyh.managers.game.adventure.AdventureLevelManager;
import com.c17.yyh.managers.purchasing.StoreManager;
import com.c17.yyh.models.User;
import com.c17.yyh.mvc.message.inbound.UpdateSettingsRequest;
import com.c17.yyh.mvc.message.inbound.login.LoginRequest;
import com.c17.yyh.mvc.message.inbound.login.UserReloadRequest;
import com.c17.yyh.mvc.message.outbound.UpdateSettingsResponse;
import com.c17.yyh.mvc.message.outbound.login.LoginResponse;
import com.c17.yyh.mvc.message.outbound.login.UserLoadResponse;
import com.c17.yyh.processors.UserProcessor;
import com.c17.yyh.server.message.BaseMessage;

@Controller
public class UserController {

    @Autowired
    private ServerConfig          serverConfig;

    @Autowired
    private UserManager           userManager;

    @Autowired
    private StoreManager       storeManager;

    @Autowired
    private AdventureLevelManager adventureLevelManager;

    @Autowired
    private PetManager            petManager;

    @Autowired
    private BossManager           bossManager;

    @Autowired
    private TreasureManager       treasureManager;

    @Autowired
    private ToolsManager          toolsManager;
    
    @Autowired
    private FriendsManager        friendsManager;

    @Autowired
    UserProcessor                 userProcessor;

    @RequestMapping(value = "/login", method= RequestMethod.POST)
    public @ResponseBody BaseMessage process(@RequestBody LoginRequest msg) {
        LoginResponse response = new LoginResponse();

        userProcessor.loginUser(msg, response);

        response.setLevelSets(adventureLevelManager.getLevelSets().toArray());
        response.setFencingLevelsets(adventureLevelManager.getlevelSetsWithFencing());
        response.setAllPets(petManager.getPets().toArray());
        response.setAllBosses(bossManager.getBosses().toArray());
        response.setAllCollections(treasureManager.getCollections().toArray());
        response.setToolTypes(toolsManager.getToolItems().toArray());
        response.setStoreItems(storeManager.getItems());
        response.setStorePrices(storeManager.getPrices());
        response.setFriendsBonuses(friendsManager.getBonusItems());

        response.setServerConfig(serverConfig);
        response.setServer_time(System.currentTimeMillis());

        return response;
    }

    @RequestMapping(value = "/user/load", method= RequestMethod.POST)
    public @ResponseBody BaseMessage process(@RequestBody UserReloadRequest msg) {
        UserLoadResponse response = new UserLoadResponse();

        userProcessor.loginUser(msg, response);

        return response;
    }

    @RequestMapping(value = "/user/update_settings", method= RequestMethod.POST)
    public @ResponseBody BaseMessage process(@RequestBody UpdateSettingsRequest msg) {
        User user = userManager.getUserBySnId(msg.getSnId());

        userManager.updateUserSettings(user, msg.getParam());

        return new UpdateSettingsResponse(user);
    }

}
