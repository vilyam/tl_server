package com.c17.yyh.db;

import com.c17.yyh.db.dao.IUserDao;

import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.c17.yyh.db.entities.UserBase;
import com.c17.yyh.models.User;

@Component
public class UserDaoMock implements IUserDao {

    @SuppressWarnings("serial")
    private Map<String, User> users = new ConcurrentHashMap<String, User>() {
        {

            User user1 = new User();
            user1.setUserId(1);
            user1.setSnId("1");
            user1.setCrystals(0);
            user1.setMoney(111);
            user1.setGameLives(0);
            user1.setHash("1");

            put("1", user1);

            User user2 = new User();
            user2.setUserId(2);
            user2.setSnId("2");
            user2.setCrystals(0);
            user2.setGameLives(1);
            user2.setMoney(222);
            user2.setHash("2");

            put("2", user2);

            User user3 = new User();
            user3.setUserId(3);
            user3.setSnId("3");
            user3.setCrystals(0);
            user3.setGameLives(1);
            user3.setMoney(333);
            user3.setHash("3");

            put("3", user3);
        }
    };

    @Override
    public User getUserBySnId(String userSnId) {

        return users.get(userSnId);
    }

    @Override
    public int addCollection(User user, int collection_id) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int addPet(User user, int pet_id) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int addTreasure(User user, int treasure_id) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int addBoss(User user, int boss_id) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int updateUserSession(User user) {
        // TODO Auto-generated method stub
        return 0;
    }

//	@Override
//	public int updateUserDailyBonus(User user) throws SQLException {
//		// TODO Auto-generated method stub
//		return 0;
//	}
    @Override
    public int incrementUserBalance(User user, int money, int crystals, int lives) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int incrementMoney(User user, int money) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int updateLives(User user, int lives) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int incrementLives(User user, int deltaLives) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int addSecret(int userId, int levelId, int levelSetId, int isPay) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int unlockRoadBlock(int userId, int levelSetId) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int incrementCrystals(User user, int deltaCrystals) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int updateUserSettings(int userId, String param) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int releaseRoadBlock(int userId, int levelSet_id, String from) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void updateUserInfo(int id, String name, String lastName, int sex,
            String city, String country, String birthday, int language,
            String hash, String userPage) {
        // TODO Auto-generated method stub

    }

    @Override
    public int incrementUserBalance(User user, int money, int crystals) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public User createUser(String userSnId, String name, String lastName,
            int sex, String city, String secretCode, String country,
            String birthday, int language, String hash, String userPage,
            String referrer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int updateMaxLevel(User user, int levelSetId, int levelId, Timestamp time_stop) {
        return 0;
    }

    @Override
    public int addStock(User user, int stock_id) {
        // TODO Auto-generated method stub
        return 0;
    }

}
