package com.c17.yyh.mvc.controllers;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.db.dao.IPurchasingDao;
import com.c17.yyh.db.entities.adventure.Award;
import com.c17.yyh.db.entities.adventure.Level;
import com.c17.yyh.db.entities.adventure.LevelSet;
import com.c17.yyh.db.entities.adventure.Pet;
import com.c17.yyh.db.entities.adventure.XmlStartBonus;
import com.c17.yyh.db.entities.adventure.XmlTreasure;
import com.c17.yyh.db.entities.adventure.treasure.Collection;
import com.c17.yyh.db.entities.purchasing.GameItemPrice;
import com.c17.yyh.managers.UserManager;
import com.c17.yyh.managers.game.PetManager;
import com.c17.yyh.managers.game.ToolsManager;
import com.c17.yyh.managers.game.adventure.AdventureLevelManager;
import com.c17.yyh.managers.game.adventure.AdventureStatisticManager;
import com.c17.yyh.models.LevelStatistic;
import com.c17.yyh.models.User;
import com.c17.yyh.models.purchasing.PurchasingOrder;
import com.c17.yyh.mvc.message.inbound.BuyPetRequest;
import com.c17.yyh.mvc.message.inbound.EndSessionAdventureRequest;
import com.c17.yyh.mvc.message.inbound.StartSessionAdventureRequest;
import com.c17.yyh.mvc.message.inbound.TakeCollectionAwardRequest;
import com.c17.yyh.mvc.message.outbound.BuyPetResponse;
import com.c17.yyh.mvc.message.outbound.SessionEndedAdventureResponse;
import com.c17.yyh.mvc.message.outbound.SessionStartAdventureResponse;
import com.c17.yyh.mvc.message.outbound.TakeCollectionAwardResponse;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.exceptions.ServerException;
import com.c17.yyh.server.message.BaseMessage;
import com.c17.yyh.server.message.outbound.ErrorResponse;
import com.c17.yyh.type.CurrencyType;
import com.c17.yyh.type.GameType;

import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/adventure")
public class AdventureController {
    
    @Autowired
    private ServerConfig serverConfig;
    
    @Autowired
    private UserManager userManager;
    
    @Autowired
    private AdventureStatisticManager adventureStatisticManager;
    
    @Autowired
    private AdventureLevelManager adventureLevelManager;
    
    @Autowired
    private ToolsManager toolsManager;
    
    @Autowired
    private PetManager petManager;
    
    @Autowired
    private IPurchasingDao purchasingService;

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public @ResponseBody
    BaseMessage process(@RequestBody StartSessionAdventureRequest msg) throws InterruptedException, ExecutionException {
        
        User user = userManager.getUserBySnId(msg.getSnId());
        
        int levelId = msg.getLevel();
        int levelSetId = msg.getLevelset();
        
        decrementLive(user);
        
        Level lvl = adventureLevelManager.getLevelById(levelId, levelSetId);
        if (lvl == null) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).warn("User {} entered wrong level ID - he has not enough level stars to start this level", user.getUserId());
            throw new ServerException(ErrorCodes.NOT_ENOUGH_ADVENTURE_STARS, "User entered wrong level ID - he has not enough level stars to start this level", true);
        }
        
        if (!adventureStatisticManager.getUserIsPlayed(user, levelSetId, levelId)) {
            checkStartBonus(user, lvl);
            adventureStatisticManager.startLevelStatistic(user, levelSetId, levelId, getGameType(lvl));
        }
        
        if (user.isDebug()) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).info("User id {} started game with levelSet {} and level {}", new Object[]{user.getUserId(), levelSetId, levelId});
        }
        
        SessionStartAdventureResponse response = new SessionStartAdventureResponse();
        response.setLevelData(lvl.getFile());
        return response;
    }
    
    @RequestMapping(value = "/end", method = RequestMethod.POST)
    public @ResponseBody
    BaseMessage process(@RequestBody EndSessionAdventureRequest msg) {
        SessionEndedAdventureResponse response = new SessionEndedAdventureResponse();
        
        User user = userManager.getUserBySnId(msg.getSnId());
        
        int levelId = msg.getLevel_number();
        int levelSetId = msg.getLevel_set_number();
        boolean additional_moves = msg.isAdditional_moves();
        Timestamp time_start = msg.getTime_start();
        Timestamp time_stop = msg.getTime_stop();
        int score = msg.getScore();
        int stars = msg.getStars();
        int treasure_collected = msg.getTreasure_collected();
        int pet_collected = 0;
        int addMoney = 0;//msg.getMonets();	
        int winMoney = 0;
        int addLife = 0;
        
        Level lvl = adventureLevelManager.getLevelById(levelId, levelSetId);
        
        if (lvl != null) {
            XmlTreasure treasure = lvl.getTreasure();
            Award award = lvl.getAwards();
            
            GameType game_type = getGameType(lvl);
            
            if (stars > 0) {
                
                addLife = 1;
                
                switch (game_type) {
                    case PET:
                        if (stars >= lvl.getPet().getThreshold()) {
                            pet_collected = userManager.addPet(user, lvl.getPet().getId());
                        }
                        break;
                    case BOSS:
                        userManager.addBoss(user, lvl.getBoss().getId());
                        break;
                    case SECRET:
                        userManager.addSecret(user, levelId, levelSetId, 0);
                        break;
                    case DEFAULT:
                        break;
                    default:
                        break;
                }
                
                if (treasure != null) {
                    if (stars >= lvl.getTreasure().getThreshold() && treasure_collected > 0) {
                        userManager.addTreasure(user, lvl.getTreasure().getId());
                    }
                }
                
                if (award != null) {
                    if (stars == 3) {
                        toolsManager.addToolsBatch(user, award.getTools());
                        addMoney += award.getMoney();
                        response.setAward(award);
                    }
                }
                winMoney = getDueMoney(user, levelId, levelSetId, stars);
                
                addMoney += winMoney;
                
                userManager.updateBalance(user, addMoney, 0, addLife, true);

                //MutableInt maxLevel = new MutableInt(0), maxLevelSet = new MutableInt(0);
                //adventureStatisticManager.getUserMaxLevel(user, maxLevel, maxLevelSet);
                //if (game_type != GameType.SECRET & levelId > maxLevel.intValue() & levelSetId >= maxLevelSet.intValue()) {
                if (msg.getUpdate_max_level() > 0) {
                    userManager.updateMaxLevel(user, levelSetId, levelId, new Timestamp(System.currentTimeMillis()));
                }
            }

            adventureStatisticManager.updateLevelsStatistic(user, levelSetId, levelId, stars, treasure_collected, pet_collected, winMoney, score, game_type, additional_moves, time_start, time_stop);

            userManager.validateGameLives(user);
            response.setUser(user);
            return response;
        } else {
            return new ErrorResponse(ErrorCodes.INCORRECT_LELEL_ID, "", true);
        }
        
    }
    
    @RequestMapping(value = "/take_collection_award", method = RequestMethod.POST)
    public @ResponseBody
    BaseMessage process(@RequestBody TakeCollectionAwardRequest msg) {
        
        User user = userManager.getUserBySnId(msg.getSnId());
        
        int collectionId = msg.getCollection_id();
        
        Collection coll = null;
        Award award = null;

        // check if this collection is not won yet(award not took)
        if (!userManager.checkWonCollectionByCollectionId(user, collectionId)) {
            // check if this collection is collected
            if (userManager.checkAvailableCollectionToWin(user, collectionId)) {
                coll = userManager.getCollectionById(collectionId);
                if (coll != null) {
                    award = coll.getAward();
                    toolsManager.addToolsBatch(user, award.getTools());

                    if (award.getMoney() > 0) {
                        userManager.incrementMoney(user, award.getMoney(), true);
                    }

                    if (award.getPet() != null) {
                        userManager.addPet(user, award.getPet().getId());
                    }

                    userManager.addCollection(user, collectionId);
                } else {
                    // collection is null
                    throw new ServerException(ErrorCodes.COLLECTION_IS_NULL, "Collection is null. bug", true);
                }
            } else {
                // collection is not collected. chit
                throw new ServerException(ErrorCodes.COLLECTION_NOT_COLLECTED, "Collection is not collected. chit!", true);
            }
        } else {
            // user geted award for collection. chit or bug
            throw new ServerException(ErrorCodes.COLLECTION_WAS_TAKED, "Award for collection was taked. chit!", true);
        }
        
        TakeCollectionAwardResponse response = new TakeCollectionAwardResponse();
        response.setUser(user);
        response.setAward(award);
        return response;
    }
    
    @RequestMapping(value = "/pet/buy", method = RequestMethod.POST)
    public @ResponseBody
    BaseMessage process(@RequestBody BuyPetRequest msg) {
        User user = userManager.getUserBySnId(msg.getSnId());
        Pet pet = petManager.getPetById(msg.getId());
        if(pet != null) {
            GameItemPrice price = pet.getPrice();
            if(price == null) throw new ServerException(ErrorCodes.CONFIG_ERROR, "Pet price not found", true);
            
            CurrencyType currency = price.getCurrency();
            int value = price.getValue();
            switch (currency) {
                case CRYSTALS:
                    if(value > user.getCrystals()) throw new ServerException(ErrorCodes.NOT_ENOUGH_RESOURCES, "not enough crystals", true);
                    userManager.incrementCrystals(user, -value, true);
                    break;
                case MONEY:
                    if(value > user.getMoney()) throw new ServerException(ErrorCodes.NOT_ENOUGH_MONEY, "not enough money", true);
                    userManager.incrementMoney(user, -value, true);
                    break;
            }
            userManager.addPet(user, pet.getId());
            purchasingService.addNewPurchasingOrder(new PurchasingOrder(user.getUserId(), price, pet.getId(), "PET"));
            BuyPetResponse response = new BuyPetResponse();
            response.setUser(user);
            return response;
        } else {
            throw new ServerException(ErrorCodes.PET_NOT_EXISTS, "Pet not found", true);
        }
    } 

    private GameType getGameType(Level lvl) {
        if (lvl.getPet() != null) {
            return GameType.PET;
        }
        if (lvl.getBoss() != null) {
            return GameType.BOSS;
        }
        if (lvl.getSecret() != null) {
            return GameType.SECRET;
        }
        return GameType.DEFAULT;
    }
    
    private int getDueMoney(User user, int levelId, int levelSetId, int stars) {
        LevelStatistic lvlStat = adventureStatisticManager.getLevelStatisticByIds(user, levelSetId, levelId);
        Level lvl = adventureLevelManager.getLevelById(levelId, levelSetId);
        LevelSet lvlSet = adventureLevelManager.getLevelSetById(levelSetId);
        
        int due_money = 0;
        int gathered_stars = 0;
        if (lvlStat != null) {
            gathered_stars = lvlStat.getStars();
        }
        
        due_money = lvlSet.getMoney_prize()[stars - 1];
        if (stars > gathered_stars) {
            if (checkBonusPetForMoney(lvl.getPets_list())) {
                due_money = (int) (due_money + due_money * 0.5);
            }
            return due_money;
        }
        return 0;
    }
    
    private boolean checkBonusPetForMoney(int[] pets_list) {
        if (pets_list != null) {
            for (int i : pets_list) {
                if (i == serverConfig.pigId) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * check start bonus if level play first time
     *
     * @param user
     * @param lvl
     */
    private XmlStartBonus checkStartBonus(User user, Level lvl) {
        XmlStartBonus startBonus = lvl.getStart_bonus();
        if (startBonus != null) {
            int count = startBonus.getCount();
            int toolId = startBonus.getTool_id();
            toolsManager.increaseUserTool(user, toolId, count);
        }
        return startBonus;
    }
    
    private void decrementLive(User user) {
        userManager.validateGameLives(user);
        int lives = user.getGameLives();
        
        if (lives > 0) {
            if (lives == serverConfig.maxLivesCount) {
                user.setTimerStartDecrementLives(System.currentTimeMillis());
            }
            
            user.setGameLives(lives - 1);
            userManager.updateUserSession(user);
        } else {
            LoggerFactory.getLogger(Thread.currentThread().getName()).warn("Not enough lives for user {}.", user.getUserId());
            throw new ServerException(ErrorCodes.NOT_ENOUGH_SESSION, "Not enough lives to start game session", false);
        }
    }
}
