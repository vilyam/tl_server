package com.c17.yyh.db.dao;

import com.c17.yyh.db.entities.UserBase;
import com.c17.yyh.models.User;

import java.sql.Timestamp;
import java.util.Map;

public interface IUserDao {

    public User getUserBySnId(String userSnId);

    public User createUser(String userSnId, String name, String lastName,
            int sex, String city, String secretCode, String country,
            String birthday, int language, String hash, String userPage, String referrer);

    public int updateUserSession(User user);

    public void updateUserInfo(int id, String name, String lastName,
            int sex, String city, String country, String birthday,
            int language, String hash, String userPage);

    int updateLives(User user, int lives);

    int incrementMoney(User user, int deltaMoney);

    int incrementLives(User user, int deltaLives);

    int incrementUserBalance(User user, int money, int crystals, int lives);

    int addPet(User user, int pet_id);

    int addTreasure(User user, int treasure_id);

    int addBoss(User user, int boss_id);

    int addCollection(User user, int collection_id);

    public int releaseRoadBlock(int userId, int levelSet_id, String from);

    public int addSecret(int userId, int levelId, int levelSetId, int isPay);

    int unlockRoadBlock(int userId, int levelSetId);

    int incrementCrystals(User user, int deltaCrystals);

    int updateUserSettings(int userId, String param);

    int incrementUserBalance(User user, int money, int crystals);

    int updateMaxLevel(User user, int levelSetId, int levelId, Timestamp time_stop);

    int addStock(User user, int stock_id);
}
