package com.c17.yyh.mvc.controllers;

import com.c17.yyh.managers.FriendsManager;
import com.c17.yyh.managers.UserManager;
import com.c17.yyh.mvc.message.inbound.friends.FriendsCheckBonusRequest;
import com.c17.yyh.mvc.message.inbound.friends.FriendsGetBonusRequest;
import com.c17.yyh.mvc.message.inbound.friends.FriendsLevelInfoRequest;
import com.c17.yyh.mvc.message.inbound.friends.FriendsProgressRequest;
import com.c17.yyh.mvc.message.outbound.friends.FriendsLevelInfoResponse;
import com.c17.yyh.mvc.message.outbound.friends.FriendsProgressResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c17.yyh.server.message.BaseMessage;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(value = "/friends")
@Api(value = "friends", description = "Friends API")
public class FriendsInfoController {

    @Autowired
    private UserManager userManager;
    
    @Autowired
    FriendsManager friendsManager;

    @ApiOperation(value = "Get Friends progress", notes = "Returns friends progress with levelSetNumber and levelNumber", response= FriendsProgressResponse.class)
    @RequestMapping(value = "/progress", method = RequestMethod.POST)
    public @ResponseBody BaseMessage process(@RequestBody FriendsProgressRequest msg) {
        return friendsManager.getFriendsProgress(msg.getFriendsSnIds());
    }

    @ApiOperation(value = "Get Friends info", notes = "Returns friends info on the specific level", response= FriendsLevelInfoResponse.class)
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public @ResponseBody BaseMessage process(@RequestBody FriendsLevelInfoRequest request) {
        return friendsManager.getFriendsInfo(
                request.getFriendsSnIds(), request.getLevelNumber(), request.getLevelSetNumber());
    }

    @RequestMapping(value = "/bonus/check", method = RequestMethod.POST)
    public @ResponseBody BaseMessage process(@RequestBody FriendsCheckBonusRequest request) {
        return friendsManager.checkBonus(userManager.getUserBySnId(request.getSnId()));
    }

    @RequestMapping(value = "/bonus/accept", method = RequestMethod.POST)
    public @ResponseBody BaseMessage process(@RequestBody FriendsGetBonusRequest request) {
        return friendsManager.acceptBonus(userManager.getUserBySnId(request.getSnId()));
    }
}
