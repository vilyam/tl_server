package com.c17.yyh.mvc.controllers;

import com.c17.yyh.db.dao.IPurchasingDao;
import com.c17.yyh.db.entities.purchasing.GameItemPrice;
import com.c17.yyh.exceptions.ServerException;
import com.c17.yyh.managers.UserManager;
import com.c17.yyh.managers.game.ToolsManager;
import com.c17.yyh.models.User;
import com.c17.yyh.models.purchasing.PurchasingOrder;
import com.c17.yyh.mvc.message.inbound.tools.BuyToolRequest;
import com.c17.yyh.mvc.message.inbound.tools.UseToolRequest;
import com.c17.yyh.mvc.message.outbound.tools.*;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.server.message.BaseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/tool")
public class ToolsController {

    @Autowired
    private UserManager userManager;

    @Autowired
    private ToolsManager toolsManager;

    @Autowired
    private IPurchasingDao purchasingService;

    @RequestMapping(value = "/use", method= RequestMethod.POST)
    public BaseMessage process(@RequestBody UseToolRequest request) {
        User user = userManager.getUserBySnId(request.getSnId());
        int[] boostsIds = request.getBoostsIds() != null ? request.getBoostsIds() : new int[]{request.getId()};

        for (int boostId : boostsIds) {
            toolsManager.usingUserTool(user, boostId, request.getLevel_number(), request.getLevelset_number());
        }

        UseToolResponse response = new UseToolResponse();
        response.setUser(user);
        return response;
    }
    
    @RequestMapping(value = "/buy", method= RequestMethod.POST)
    public BaseMessage process(@RequestBody BuyToolRequest request) {
        int id = request.getId();
        int priceId = request.getPrice_id();

        GameItemPrice price = toolsManager.getToolPriceById(id, priceId);
        if (price == null) {throw new ServerException(ErrorCodes.RESOURCE_NOT_FOUND, "id not found", false);}

        User user = userManager.getUserBySnId(request.getSnId());
        int userFounds = 0;
        int toolCost = price.getValue();

        switch (price.getCurrency()) {
            case CRYSTALS:
                userFounds = user.getCrystals();
                if (userFounds < toolCost) throw new ServerException(ErrorCodes.NOT_ENOUGH_RESOURCES, "not enough balance", false);
                userManager.incrementCrystals(user, -toolCost, false);
                break;
            case MONEY:
                userFounds = user.getMoney();
                if (userFounds < toolCost) throw new ServerException(ErrorCodes.NOT_ENOUGH_RESOURCES, "not enough balance", false);
                userManager.incrementMoney(user, -toolCost, false);
                break;
        } 

        toolsManager.increaseUserTool(user, id, price.getCount());

        purchasingService.addNewPurchasingOrder(new PurchasingOrder(user.getUserId(), price, id, "TOOL",request.getLevel_number(),request.getLevelset_number()));

        return new BuyToolResponse(user);
    }
}
