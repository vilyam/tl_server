package com.c17.yyh.managers;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.core.CacheService;
import com.c17.yyh.db.dao.IFriendsDao;
import com.c17.yyh.db.dao.IUserDao;
import com.c17.yyh.db.entities.adventure.Boss;
import com.c17.yyh.db.entities.adventure.LevelSet;
import com.c17.yyh.db.entities.adventure.Pet;
import com.c17.yyh.db.entities.adventure.treasure.Collection;
import com.c17.yyh.db.entities.adventure.treasure.Treasure;
import com.c17.yyh.exceptions.ServerException;
import com.c17.yyh.managers.game.BossManager;
import com.c17.yyh.managers.game.PetManager;
import com.c17.yyh.managers.game.TreasureManager;
import com.c17.yyh.managers.game.adventure.AdventureLevelManager;
import com.c17.yyh.models.RoadBlock;
import com.c17.yyh.models.SecretLevel;
import com.c17.yyh.models.User;
import com.c17.yyh.models.UserProgress;
import com.c17.yyh.models.WonCollection;
import com.c17.yyh.models.purchasing.Stock;
import com.c17.yyh.server.ErrorCodes;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserManager {

    @Autowired
    private IUserDao userService;
    @Autowired
    private IFriendsDao friendsService;
    @Autowired
    private PetManager petManager;
    @Autowired
    private TreasureManager treasureManager;
    @Autowired
    private BossManager bossManager;
    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    CacheService cacheService;
    @Autowired
    private AdventureLevelManager adventureLevelManager;

    @Cacheable(value = "users", key = "#p0", unless = "#result == null")
    public User getUserBySnId(String userSnId) {
        return userService.getUserBySnId(userSnId);
    }
    
    public User createUser(String userSnId, String name, String lastName,
            int sex, String city, String secretCode, String country,
            String birthday, int language, String hash, String userPage, String referrer) {

        User user = userService.createUser(userSnId, name, lastName, sex, city,
                secretCode, country, birthday, language, hash, userPage, referrer);
        return user;
    }

    public void updateUserInfo(User user, String name, String lastName,
            int sex, String city, String country, String birthday,
            int language, String hash, String userPage) {
        user.setFirstName(name);
        user.setLastName(lastName);
        user.setSex(sex);
        user.setCity(city);
        user.setCountry(country);
        user.setBirthday(birthday);
        user.setLanguage(language);
        user.setHash(hash);
        user.setUserPage(userPage);

        userService.updateUserInfo(user.getUserId(), name, lastName, sex, city,
                country, birthday, language, hash, userPage);

        cacheService.updateUserInCache(user);

    }

    public void updateBalance(User user, int addMoney, int addCrystals, int addLives, boolean updateCache) {
        if (userService.incrementUserBalance(user, addMoney, addCrystals, addLives) > 0) {
            user.setMoney(user.getMoney() + addMoney);
            user.setCrystals(user.getCrystals() + addCrystals);
            user.setGameLives(user.getGameLives() + addLives);

            if (updateCache) {
                cacheService.updateUserInCache(user);
            }
        }
    }

    public void updateBalance(User user, int addMoney, int addCrystals, boolean updateCache) {
        if (userService.incrementUserBalance(user, addMoney, addCrystals) > 0) {
            user.setMoney(user.getMoney() + addMoney);
            user.setCrystals(user.getCrystals() + addCrystals);

            if (updateCache) {
                cacheService.updateUserInCache(user);
            }
        }
    }

    public int incrementMoney(User user, int deltaMoney, boolean updateCache) {
        int status = userService.incrementMoney(user, deltaMoney);

        if (status > 0) {
            user.setMoney(user.getMoney() + deltaMoney);

            if (updateCache) {
                cacheService.updateUserInCache(user);
            }
        }
        return status;
    }

    public int incrementLives(User user, int deltaLives, boolean updateCache) {
        int status = userService.incrementLives(user, deltaLives);

        if (status > 0) {
            user.setGameLives(user.getGameLives() + deltaLives);

            if (updateCache) {
                cacheService.updateUserInCache(user);
            }
        }
        return status;
    }

    public int incrementCrystals(User user, int deltaCrystals, boolean updateCache) {
        int status = userService.incrementCrystals(user, deltaCrystals);

        if (status > 0) {
            user.setCrystals(user.getCrystals() + deltaCrystals);

            if (updateCache) {
                cacheService.updateUserInCache(user);
            }
        }
        return status;
    }

    public Boolean checkUserAvailableCrystals(User user, int crystals) {
        if (user.getCrystals() >= crystals) {
            return true;
        } else {
            return false;
        }
    }

    public int updateLives(User user, int addLives) {
        int lives = user.getGameLives();

        int state = userService.incrementLives(user, addLives);
        if (state > 0) {
            user.setGameLives(lives + addLives);

            cacheService.updateUserInCache(user);
        }
        return state;
    }

    public int addPet(User user, int pet_id) {
        Pet pet = petManager.getPetById(pet_id);
        int status = 0;

        if (pet == null) {
            throw new ServerException(ErrorCodes.PET_NOT_EXISTS, "Такого пета не существует. ошибка конфиг-файла", true);
        }

        if (!user.getPets().contains(pet)) {
            status = userService.addPet(user, pet_id);

            if (status > 0) {
                user.getPets().add(pet);

                cacheService.updateUserInCache(user);
            }
        }

        return status;

    }

    public int addTreasure(User user, int treasure_id) {
        Treasure tr = treasureManager.getTreasureById(treasure_id);
        int status = 0;

        if (tr == null) {
            throw new ServerException(ErrorCodes.TREASURE_NOT_EXISTS, "Такого сокровища не существует. ошибка конфиг-файла", true);
        }

        if (!user.getTreasures().contains(tr)) {
            status = userService.addTreasure(user, treasure_id);

            if (status > 0) {
                user.getTreasures().add(tr);

                cacheService.updateUserInCache(user);
            }
        }

        return status;
    }

    public int addBoss(User user, int boss_id) {
        Boss boss = bossManager.getBossById(boss_id);
        int status = 0;

        if (boss == null) {
            throw new ServerException(ErrorCodes.BOSS_NOT_EXISTS, "Такого босса не существует. ошибка конфиг-файла", true);
        }

        if (!user.getBosses().contains(boss)) {
            status = userService.addBoss(user, boss_id);
            if (status > 0) {
                user.getBosses().add(boss);

                cacheService.updateUserInCache(user);
            }
        }
        return status;
    }

    public int addSecret(User user, int levelId, int levelSetId, int isPay) {
        int status = userService.addSecret(user.getUserId(), levelId, levelSetId, isPay);

        if (status > 0) {
            SecretLevel sl = new SecretLevel(user.getUserId(), levelId, levelSetId, isPay);
            user.getSecretLevels().add(sl);

            cacheService.updateUserInCache(user);
        }

        return status;
    }

    public int addCollection(User user, int collection_id) {
        int status = userService.addCollection(user, collection_id);

        if (status > 0) {
            WonCollection col = new WonCollection();
            col.setCollection_id(collection_id);
            user.getWonCollections().add(col);

            cacheService.updateUserInCache(user);
        }

        return status;
    }

    public List<Collection> checkTreasureCollection(User user) {
        return treasureManager.checkTreasureCollection(user);
    }

    public boolean checkWonCollectionByCollectionId(User user, int collectionId) {
        for (WonCollection wonCol : user.getWonCollections()) {
            if (wonCol.getCollection_id() == collectionId) {
                return true;
            }
        }
        return false;
    }

    public boolean checkAvailableCollectionToWin(User user, int collectionId) {
        List<Collection> collectedCollections = checkTreasureCollection(user);
        for (Collection collection : collectedCollections) {
            if (collection.getId() == collectionId) {
                return true;
            }
        }
        return false;
    }

    public Collection getCollectionById(int collectionId) {
        return treasureManager.getCollectionById(collectionId);
    }

    public int updateRoadBlock(User user, int levelSet_id, String from) {
        List<RoadBlock> rBlocks = user.getRoadBloks();
        RoadBlock rBlock = null;

        int status = userService.releaseRoadBlock(user.getUserId(), levelSet_id, from);

        if (status > 0) {
            for (RoadBlock rb : rBlocks) {
                if (rb.getLevelSetId() == levelSet_id) {
                    rBlock = rb;
                }
            }

            if (rBlock == null) {
                List<String> friends = new LinkedList<String>();
                friends.add(from);
                rBlock = new RoadBlock(user.getUserId(), levelSet_id, friends, 0);
                rBlocks.add(rBlock);
            } else {
                rBlock.addToFriends(from);
            }

            cacheService.updateUserInCache(user);
        }

        return status;

    }

    public int unlockRoadblock(User user, int levelSet_id) {
        return unlockRoadblock(user, levelSet_id, false);
    }

    public int unlockRoadblock(User user, int levelSet_id, boolean dbOnly) {
        int status = userService.unlockRoadBlock(user.getUserId(), levelSet_id);
        if (!dbOnly && status > 0) {
            RoadBlock rb = new RoadBlock(user.getUserId(), levelSet_id, new LinkedList<String>(), 1);
            user.getRoadBloks().add(rb);

            cacheService.updateUserInCache(user);
        }

        return status;
    }

    public boolean isRoadblockLocked(String snId, int levelsetId) {
        return !hasPayedRoadblocks(getUserBySnId(snId), levelsetId);
    }

    public boolean hasPayedRoadblocks(User user, int levelsetId) {
        for (RoadBlock block : user.getRoadBloks()) {
            if (block.getLevelSetId() == levelsetId && block.getIsPay() == 1) {
                return true;
            }
        }
        return false;
    }

    public void updateUserSession(User user) {
        userService.updateUserSession(user);

        cacheService.updateUserInCache(user);
    }

    public boolean validateGameLives(User user) {
        int availableLives = user.getGameLives();
        long timer = user.getTimerStartDecrementLives();
        long timeDiff = (System.currentTimeMillis() - timer) / 1000;

        try {
            long addLives = timeDiff / serverConfig.timeTillUpdateLive;
            if (availableLives >= serverConfig.maxLivesCount) {
                user.setTimeRemaining(0);
                user.setTimerStartDecrementLives(0);
                return false;
            } else {
                if (availableLives + addLives >= serverConfig.maxLivesCount) {
                    user.setTimeRemaining(0);
                    user.setTimerStartDecrementLives(0);
                    user.setGameLives(serverConfig.maxLivesCount);
                    return true;
                } else {
                    long timeRemaning = timeDiff - addLives * serverConfig.timeTillUpdateLive;
                    user.setTimeRemaining(timeRemaning);
                    user.setTimerStartDecrementLives(timer + addLives * serverConfig.timeTillUpdateLive * 1000);
                    user.setGameLives(availableLives + (int) addLives);
                    return true;
                }
            }
        } finally {
            cacheService.updateUserInCache(user);
        }

    }

    public void updateUserSettings(User user, String param) {
        int status = userService.updateUserSettings(user.getUserId(), param);

        if (status > 0) {
            user.setSettings(param);

            cacheService.updateUserInCache(user);
        }
    }

    public int updateMaxLevel(User user, int levelSetId, int levelId, Timestamp time_stop) {
        int status = userService.updateMaxLevel(user, levelSetId, levelId, time_stop);

        if (status > 0) {
            UserProgress up = user.getProgress();
            up.setLevelSetNumber(levelSetId);
            up.setLevelNumber(levelId);
            up.setDate(time_stop);
            //           updateUsers(user);
            cacheService.updateUserInCache(user);
        }

        return status;
    }

    public boolean validateRoadBlock(User user) {
        UserProgress progress = user.getProgress();
        LevelSet currentLevelset = adventureLevelManager.getLevelSetById(progress.getLevelSetNumber());
        int maxLevelNumber = currentLevelset != null && currentLevelset.getLevels().size() > 0 ? currentLevelset.getLevels().last().getNumber() : -1;
        if (maxLevelNumber != -1) {
            LevelSet nextLevelset = adventureLevelManager.getLevelSetById(progress.getLevelSetNumber() + 1);
            if (progress.getLevelNumber() == maxLevelNumber && nextLevelset != null && nextLevelset.getFencing() > 0 && DateUtils.addDays(progress.getDate(), serverConfig.getRoadblockUnlockDays()).before(new Date())
                    && this.isRoadblockLocked(user.getSnId(), nextLevelset.getNumber())) {
                this.unlockRoadblock(user, nextLevelset.getNumber(), true);
                LoggerFactory.getLogger(getClass()).info("Roadblock for user {} and levelset {} has been unlocked", user.getSnId(), nextLevelset.getNumber());
                //requestSender.sendNotification(Collections.singletonList(user.getSnId()), serverConfig.getRoadblockUnlockMessage().replace(LEVELSET_PLACEHOLDER, String.valueOf(nextLevelset.getNumber())));
                return true;
            }
        }
        return false;
    }
}
