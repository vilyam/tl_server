package com.c17.yyh.db.dao.impl.sql;

import com.c17.yyh.db.dao.IUserDao;
import com.c17.yyh.db.entities.adventure.Boss;
import com.c17.yyh.db.entities.adventure.Pet;
import com.c17.yyh.db.entities.adventure.treasure.Treasure;
import com.c17.yyh.managers.game.BossManager;
import com.c17.yyh.managers.game.PetManager;
import com.c17.yyh.managers.game.TreasureManager;
import com.c17.yyh.models.LevelStatistic;
import com.c17.yyh.models.RoadBlock;
import com.c17.yyh.models.SecretLevel;
import com.c17.yyh.models.SocialQuest;
import com.c17.yyh.models.User;
import com.c17.yyh.models.UserProgress;
import com.c17.yyh.models.WonCollection;
import com.c17.yyh.models.purchasing.Stock;
import com.c17.yyh.models.tool.Tool;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.server.SQLReq;
import com.c17.yyh.exceptions.ServerException;
import com.c17.yyh.type.GameType;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements IUserDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    @Autowired
    private PetManager petManager;

    @Autowired
    private BossManager bossManager;

    @Autowired
    private TreasureManager treasureManager;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public User getUserBySnId(String userSnId) {
        User user = null;
        try {
            user = getUserInfoStatic(userSnId);
            if (user == null) {
                return null;
            }
            getStatistic(user);
            getTools(user);
            getPets(user);
            getTreasures(user);
            getWonCollections(user);
            getBosses(user);
            getRoadBlocks(user);
            getSecretLevels(user);
            getSocialQuest(user);
            getStocks(user);
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return user;
    }

    @Override
    public User createUser(final String userSnId, final String name, final String lastName, final int sex,
            final String city, final String secretCode, final String country, final String birthday,
            final int language, final String hash, final String userPage, final String referrer) {
        int last_insert_id = 0;
        PreparedStatement insertNewUser = null;
        PreparedStatement insertNewUserInfo = null;
        PreparedStatement insertNewUserState = null;
        PreparedStatement insertNewUserSocialQuest = null;
        ResultSet rs = null;
        Connection con = null;

        try {
            try {
                con = dataSource.getConnection();
                con.setAutoCommit(false);

                insertNewUser = con.prepareStatement(SQLReq.INSERT_NEW_USER, new String[]{"user_id"});
                insertNewUser.setString(1, userSnId);
                insertNewUser.setString(2, secretCode);
                insertNewUser.setString(3, hash);
                insertNewUser.executeUpdate();

                rs = insertNewUser.getGeneratedKeys();

                if (rs.next()) {
                    last_insert_id = rs.getInt(1);
                } else {
                    return null;
                }

                insertNewUserInfo = con.prepareStatement(SQLReq.INSERT_NEW_USER_INFO);
                insertNewUserInfo.setInt(1, last_insert_id);
                insertNewUserInfo.setString(2, name);
                insertNewUserInfo.setString(3, lastName);
                insertNewUserInfo.setInt(4, sex);
                insertNewUserInfo.setString(5, city);
                insertNewUserInfo.setString(6, country);
                insertNewUserInfo.setString(7, birthday);
                insertNewUserInfo.setInt(8, language);
                insertNewUserInfo.setString(9, userPage);
                insertNewUserInfo.setString(10, referrer);
                insertNewUserInfo.executeUpdate();

                insertNewUserState = con.prepareStatement(SQLReq.INSERT_NEW_USER_STATE);
                insertNewUserState.setInt(1, last_insert_id);
                insertNewUserState.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
                insertNewUserState.executeUpdate();

                insertNewUserSocialQuest = con.prepareStatement(SQLReq.INSERT_NEW_USER_SOCIAL_QUEST);
                insertNewUserSocialQuest.setInt(1, last_insert_id);
                insertNewUserSocialQuest.executeUpdate();

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw new SQLException(e);
            } finally {
                closeQuietly(insertNewUser, rs);
                closeQuietly(insertNewUserInfo, null);
                closeQuietly(insertNewUserState, null);
                closeQuietly(insertNewUserSocialQuest, null);
                con.setAutoCommit(true);
                closeQuietly(con);
            }
        } catch (Exception e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return getUserBySnId(userSnId);
    }

    private User getUserInfoStatic(String userSnId) throws SQLException {
        User user = null;
        int user_id = 0;
        Connection con = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection();

            PreparedStatement getUserPrpStatement = con.prepareStatement(SQLReq.GET_USER_SQL);
            getUserPrpStatement.setString(1, userSnId);
            rs = getUserPrpStatement.executeQuery();

            if (rs.next()) {
                user = new User();
                user_id = rs.getInt("user_id");
                user.setUserId(user_id);
                user.setSnId(rs.getString("user_sn_id"));
                user.setMoney(rs.getInt("money"));
                user.setCrystals(rs.getInt("crystals"));
                user.setBonusDay(rs.getInt("bonus_day"));
                user.setSecretCode(rs.getString("secret_code"));
                user.setHash(rs.getString("hash"));
                user.setDebug(rs.getBoolean("is_debug_mode"));
            } else {
                closeQuietly(getUserPrpStatement, rs);
                return null;
            }
            closeQuietly(getUserPrpStatement, rs);

            //user info
            PreparedStatement getUserInfoPrpStatement = con.prepareStatement(SQLReq.GET_USER_INFO_SQL);
            getUserInfoPrpStatement.setInt(1, user_id);
            rs = getUserInfoPrpStatement.executeQuery();

            rs.next();
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setCity(rs.getString("city"));
            user.setSex(rs.getInt("sex"));
            user.setBirthday(rs.getString("birthday"));
            user.setCountry(rs.getString("country"));
            user.setLanguage(rs.getInt("lang"));
            closeQuietly(getUserInfoPrpStatement, rs);

            //user state
            PreparedStatement getUserStatePrpStatement = con.prepareStatement(SQLReq.GET_USER_STATE_SQL);
            getUserStatePrpStatement.setInt(1, user_id);
            rs = getUserStatePrpStatement.executeQuery();

            rs.next();
            user.setLastTimeDailyLogin(rs.getTimestamp("last_daily_login").getTime());
            user.setTimerStartDecrementLives(rs.getTimestamp("last_game_session").getTime());
            user.setLastTimeDailyBonusLogin(rs.getTimestamp("last_daily_bonus_login").getTime());
            user.setFirstTimeDailyLogin(rs.getTimestamp("first_daily_login").getTime());
            user.setDailyBonusMarker(rs.getInt("daily_bonus_marker"));
            user.setGameLives(rs.getInt("game_lives"));
            closeQuietly(getUserStatePrpStatement, rs);

            //user settings
            PreparedStatement getUserSettingsPrpStatement = con.prepareStatement(SQLReq.GET_USER_SETTINGS_SQL);
            getUserSettingsPrpStatement.setInt(1, user_id);
            rs = getUserSettingsPrpStatement.executeQuery();

            if (rs.next()) {
                String settings = rs.getString("param");
                user.setSettings(settings != null ? settings : "");
            } else {
                user.setSettings("");
            }
            closeQuietly(getUserSettingsPrpStatement, rs);

            //is payer
            PreparedStatement getUserIsPayer = con.prepareStatement(SQLReq.GET_USER_IS_PAYER);
            getUserIsPayer.setString(1, userSnId);
            rs = getUserIsPayer.executeQuery();
            if (rs.next()) {
                user.setIsPayer(rs.getInt(1));
            }
            closeQuietly(getUserIsPayer, rs);
        } finally {
            closeQuietly(con);
        }
        return user;
    }

    private SocialQuest getSocialQuest(User user) throws SQLException {
        SocialQuest sq = new SocialQuest();
        Connection con = null;
        PreparedStatement getUserSocialQuest = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            getUserSocialQuest = con.prepareStatement(SQLReq.GET_USER_SOCIAL_QUEST);
            getUserSocialQuest.setInt(1, user.getUserId());
            rs = getUserSocialQuest.executeQuery();

            if (rs.next()) {
                sq.setState(rs.getString("state"));
                sq.setIsDone(rs.getInt("is_done"));
            }
            user.setSocialQuest(sq);
        } finally {
            closeQuietly(con, getUserSocialQuest, rs);
        }
        return sq;
    }

    private List<SecretLevel> getSecretLevels(User user) throws SQLException {
        List<SecretLevel> secretLevels = new ArrayList<SecretLevel>();
        Connection con = null;
        PreparedStatement getSecretLevelsPrpStatement = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            getSecretLevelsPrpStatement = con.prepareStatement(SQLReq.GET_USER_SECRET_LEVELS_SQL);
            getSecretLevelsPrpStatement.setInt(1, user.getUserId());
            rs = getSecretLevelsPrpStatement.executeQuery();
            while (rs.next()) {
                SecretLevel sl = new SecretLevel();
                sl.setUserId(user.getUserId());
                sl.setLevelSetId(rs.getInt("levelset_id"));
                sl.setLevelId(rs.getInt("level_id"));
                sl.setIsPay(rs.getInt("is_pay"));
                secretLevels.add(sl);
            }
            user.setSecretLevels(secretLevels);
        } finally {
            closeQuietly(con, getSecretLevelsPrpStatement, rs);
        }
        return secretLevels;
    }

    private List<RoadBlock> getRoadBlocks(User user) throws SQLException {
        List<RoadBlock> blocks = new LinkedList<RoadBlock>();
        Connection con = null;
        PreparedStatement getRoadBlocksPrpStatement = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            getRoadBlocksPrpStatement = con.prepareStatement(SQLReq.ROADBLOCK_GET_BY_USER_ID);
            getRoadBlocksPrpStatement.setInt(1, user.getUserId());
            rs = getRoadBlocksPrpStatement.executeQuery();

            while (rs.next()) {
                RoadBlock rb = new RoadBlock();
                rb.setUserId(user.getUserId());
                rb.setLevelSetId(rs.getInt("levelset_id"));
                rb.setFriendsString(rs.getString("friends"));
                rb.setIsPay(rs.getInt("is_pay"));
                blocks.add(rb);
            }
            user.setRoadBloks(blocks);
        } finally {
            closeQuietly(con, getRoadBlocksPrpStatement, rs);
        }
        return blocks;
    }

    private User getStatistic(User user) throws SQLException {
        List<LevelStatistic> levelStatList = new ArrayList<LevelStatistic>();
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement getUserStatisticPrpStatement = null;
        PreparedStatement getUserProgress = null;

        try {
            con = dataSource.getConnection();
            getUserStatisticPrpStatement = con.prepareStatement(SQLReq.GET_USER_STATISTIC_SQL);
            getUserStatisticPrpStatement.setInt(1, user.getUserId());
            rs = getUserStatisticPrpStatement.executeQuery();

            while (rs.next()) {
                int levelSetNumber = rs.getInt("levelset_number");
                int level_number = rs.getInt("level_number");

                LevelStatistic levelStat = new LevelStatistic();

                levelStat.setId(rs.getInt("id"));
                levelStat.setUser_id(rs.getInt("user_id"));
                levelStat.setLevelset_number(levelSetNumber);
                levelStat.setLevel_number(level_number);
                levelStat.setStars(rs.getInt("stars"));
                levelStat.setScore(rs.getInt("score"));
                levelStat.setGame_type(GameType.valueOf(rs.getString("game_type")));
                levelStatList.add(levelStat);
            }
            closeQuietly(getUserStatisticPrpStatement, rs);

            user.setLevelsStatistic(levelStatList);

            //user progress
            getUserProgress = con.prepareStatement(SQLReq.GET_USER_PROGRESS);
            getUserProgress.setInt(1, user.getUserId());
            rs = getUserProgress.executeQuery();

            UserProgress up = new UserProgress();
            if (rs.next()) {
                up.setLevelNumber(rs.getInt("max_level_number"));
                up.setLevelSetNumber(rs.getInt("max_levelset_number"));
                up.setDate(rs.getTimestamp("date"));
            }
            closeQuietly(getUserProgress, rs);
            user.setProgress(up);
        } finally {
            closeQuietly(con);
        }

        return user;
    }

    private List<Tool> getTools(User user) throws SQLException {
        Connection con = null;
        List<Tool> tools = new ArrayList<>();
        PreparedStatement getUserToolsPrpStatement = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection();
            getUserToolsPrpStatement = con.prepareStatement(SQLReq.GET_USER_TOOL_SQL);
            getUserToolsPrpStatement.setInt(1, user.getUserId());
            rs = getUserToolsPrpStatement.executeQuery();
            Timestamp ts;
            while (rs.next()) {
                Tool tool = new Tool();
                tool.setId(rs.getInt("tool_id"));
                tool.setCount(rs.getInt("count"));
                ts = rs.getTimestamp("last_using");
                tool.setLastUsingTool(ts != null ? ts.getTime() : 0);
                tools.add(tool);
            }
        } finally {
            closeQuietly(con, getUserToolsPrpStatement, rs);
        }
        user.setTools(tools);
        return tools;
    }

    /**
     * @param user
     * @return
     * @throws SQLException
     */
    private List<Pet> getPets(User user) throws SQLException {
        Connection con = null;
        List<Pet> pets = new ArrayList<Pet>();
        PreparedStatement getUserPetPrpStatement = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection();
            getUserPetPrpStatement = con.prepareStatement(SQLReq.GET_USER_PETS_SQL);
            getUserPetPrpStatement.setLong(1, user.getUserId());
            rs = getUserPetPrpStatement.executeQuery();

            int pet_id = -1;

            while (rs.next()) {
                pet_id = rs.getInt("pet_id");
                pets.add(petManager.getPetById(pet_id));
            }
            user.setPets(pets);
        } finally {
            closeQuietly(con, getUserPetPrpStatement, rs);
        }
        return pets;
    }

    /**
     * @param user
     * @return
     * @throws SQLException
     */
    private List<Treasure> getTreasures(User user) throws SQLException {
        List<Treasure> treasures = new ArrayList<Treasure>();
        PreparedStatement getUserTreasurePrpStatement = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            getUserTreasurePrpStatement = con.prepareStatement(SQLReq.GET_USER_TREASURE_SQL);
            getUserTreasurePrpStatement.setLong(1, user.getUserId());
            rs = getUserTreasurePrpStatement.executeQuery();

            int treasure_id = -1;

            while (rs.next()) {
                treasure_id = rs.getInt("treasure_id");
                treasures.add(treasureManager.getTreasureById(treasure_id));
            }
            user.setTreasures(treasures);
        } finally {
            closeQuietly(con, getUserTreasurePrpStatement, rs);
        }
        return treasures;
    }

    /**
     * @param user
     * @return
     * @throws SQLException
     */
    private List<WonCollection> getWonCollections(User user) throws SQLException {
        List<WonCollection> collection = new ArrayList<WonCollection>();
        PreparedStatement getUserWonCollectionsPrpStatement = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            getUserWonCollectionsPrpStatement = con.prepareStatement(SQLReq.GET_USER_WON_COLLECTION_SQL);
            getUserWonCollectionsPrpStatement.setLong(1, user.getUserId());
            rs = getUserWonCollectionsPrpStatement.executeQuery();

            while (rs.next()) {
                WonCollection won = new WonCollection();
                won.setCollection_id(rs.getInt("collection_id"));
                collection.add(won);
            }
            user.setWonCollections(collection);
        } finally {
            closeQuietly(con, getUserWonCollectionsPrpStatement, rs);
        }
        return collection;
    }

    /**
     * @param user
     * @return
     * @throws SQLException
     */
    private List<Boss> getBosses(User user) throws SQLException {
        List<Boss> bosses = new ArrayList<Boss>();
        PreparedStatement getUserBossesPrpStatement = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            getUserBossesPrpStatement = con.prepareStatement(SQLReq.GET_USER_BOSSES_SQL);
            getUserBossesPrpStatement.setLong(1, user.getUserId());
            rs = getUserBossesPrpStatement.executeQuery();

            int boss_id = -1;

            while (rs.next()) {
                boss_id = rs.getInt("boss_id");
                bosses.add(bossManager.getBossById(boss_id));
            }
            user.setBosses(bosses);

        } finally {
            closeQuietly(con, getUserBossesPrpStatement, rs);
        }
        return bosses;
    }

    private List<Stock> getStocks(User user) throws SQLException {
        List<Stock> stocks = new ArrayList<Stock>();
        PreparedStatement getUserStocksPrpStatement = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            getUserStocksPrpStatement = con.prepareStatement(SQLReq.GET_USER_STOCKS_SQL);
            getUserStocksPrpStatement.setLong(1, user.getUserId());
            rs = getUserStocksPrpStatement.executeQuery();

            while (rs.next()) {
                Stock stock = new Stock();
                stock.setId(rs.getInt("stock_id"));
                stock.setCount(rs.getInt("count"));
                stocks.add(stock);
            }

            user.setStocks(stocks);

        } finally {
            closeQuietly(con, getUserStocksPrpStatement, rs);
        }
        return stocks;
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int incrementUserBalance(User user, int money, int crystals, int lives) {
        PreparedStatement updateUserBalancePrpStatement = null;
        PreparedStatement incrementUserLivesPrpStatement = null;
        Connection con = null;
        int state1 = -1;
        int state2 = -1;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    updateUserBalancePrpStatement = con.prepareStatement(SQLReq.INCREMENT_USER_BALANCE);
                    updateUserBalancePrpStatement.setInt(1, money);
                    updateUserBalancePrpStatement.setInt(2, crystals);
                    updateUserBalancePrpStatement.setInt(3, user.getUserId());
                    state1 = updateUserBalancePrpStatement.executeUpdate();
                } finally {
                    if (updateUserBalancePrpStatement != null) {
                        updateUserBalancePrpStatement.close();
                    }
                }
                //incrementLives(user,lives);
                try {
                    incrementUserLivesPrpStatement = con.prepareStatement(SQLReq.INCREMENT_USER_GAME_LIVES);
                    incrementUserLivesPrpStatement.setInt(1, lives);
                    incrementUserLivesPrpStatement.setInt(2, user.getUserId());
                    state2 = incrementUserLivesPrpStatement.executeUpdate();
                } finally {
                    if (incrementUserLivesPrpStatement != null) {
                        incrementUserLivesPrpStatement.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        if (state1 > 0 && state2 > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int incrementUserBalance(User user, int money, int crystals) {
        PreparedStatement updateUserBalancePrpStatement = null;
        Connection con = null;
        int state = -1;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    updateUserBalancePrpStatement = con.prepareStatement(SQLReq.INCREMENT_USER_BALANCE);
                    updateUserBalancePrpStatement.setInt(1, money);
                    updateUserBalancePrpStatement.setInt(2, crystals);
                    updateUserBalancePrpStatement.setInt(3, user.getUserId());
                    state = updateUserBalancePrpStatement.executeUpdate();
                } finally {
                    if (updateUserBalancePrpStatement != null) {
                        updateUserBalancePrpStatement.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return state;
    }

    /**
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public int incrementMoney(User user, int deltaMoney) {
        PreparedStatement incrementUserMoneyPrpStatement = null;
        Connection con = null;
        int state = -1;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    incrementUserMoneyPrpStatement = con.prepareStatement(SQLReq.INCREMENT_USER_MONEY);
                    incrementUserMoneyPrpStatement.setInt(1, deltaMoney);
                    incrementUserMoneyPrpStatement.setInt(2, user.getUserId());
                    state = incrementUserMoneyPrpStatement.executeUpdate();
                } finally {
                    if (incrementUserMoneyPrpStatement != null) {
                        incrementUserMoneyPrpStatement.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return state;
    }

    @Override
    public int incrementCrystals(User user, int deltaCrystals) {
        PreparedStatement incrementUserCrystalsPrpStatement = null;
        Connection con = null;
        int state = -1;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    incrementUserCrystalsPrpStatement = con.prepareStatement(SQLReq.INCREMENT_USER_CRYSTALS);
                    incrementUserCrystalsPrpStatement.setInt(1, deltaCrystals);
                    incrementUserCrystalsPrpStatement.setInt(2, user.getUserId());
                    state = incrementUserCrystalsPrpStatement.executeUpdate();
                } finally {
                    if (incrementUserCrystalsPrpStatement != null) {
                        incrementUserCrystalsPrpStatement.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return state;
    }

    /**
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public int incrementLives(User user, int deltaLives) {
        PreparedStatement incrementUserLivesPrpStatement = null;
        Connection con = null;
        int state = -1;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    incrementUserLivesPrpStatement = con.prepareStatement(SQLReq.INCREMENT_USER_GAME_LIVES);
                    incrementUserLivesPrpStatement.setInt(1, deltaLives);
                    incrementUserLivesPrpStatement.setInt(2, user.getUserId());
                    state = incrementUserLivesPrpStatement.executeUpdate();
                } finally {
                    if (incrementUserLivesPrpStatement != null) {
                        incrementUserLivesPrpStatement.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return state;
    }

    /**
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public int updateUserSession(User user) {
        Timestamp lastGameSession = new Timestamp(user.getTimerStartDecrementLives());
        Timestamp lastTimeDailyLogin = new Timestamp(user.getLastTimeDailyLogin());
        PreparedStatement updateUserStatePrpStatement = null;
        Connection con = null;
        try {
            con = dataSource.getConnection();
            try {
                updateUserStatePrpStatement = con.prepareStatement(SQLReq.UPDATE_USER_STATE);
                updateUserStatePrpStatement.setTimestamp(1, lastGameSession);
                updateUserStatePrpStatement.setInt(2, user.getGameLives());
                updateUserStatePrpStatement.setTimestamp(3, lastTimeDailyLogin);
                updateUserStatePrpStatement.setInt(4, user.getUserId());
                return updateUserStatePrpStatement.executeUpdate();
            } finally {
                closeQuietly(con, updateUserStatePrpStatement, null);
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
    }

    @Override
    public void updateUserInfo(int id, String name, String lastName,
            int sex, String city, String country, String birthday,
            int language, String hash, String userPage) {
        jdbcTemplate.update("UPDATE user, user_info SET user_info.first_name = ?, user_info.last_name = ?, user_info.sex = ?, user_info.city = ?, user_info.country = ?, user_info.birthday = ?, user_info.lang = ?, user_info.user_page = ?, user.hash = ? WHERE user_info.user_id = user.user_id AND user.user_id = ?  ",
                name, lastName,
                sex, city, country, birthday,
                language, userPage, hash, id);
    }

    @Override
    public int updateUserSettings(int userId, String param) {
        PreparedStatement insertUserSettings = null;
        PreparedStatement updateUserSettings = null;
        Connection con = null;
        int status = 0;

        try {
            con = dataSource.getConnection();
            try {
                try {
                    updateUserSettings = con.prepareStatement(SQLReq.UPDATE_USER_SETINGS);
                    updateUserSettings.setString(1, param);
                    updateUserSettings.setInt(2, userId);
                    status = updateUserSettings.executeUpdate();
                } finally {
                    if (updateUserSettings != null) {
                        updateUserSettings.close();
                    }
                }

                if (status == 0) {
                    try {
                        insertUserSettings = con.prepareStatement(SQLReq.INSERT_USER_SETINGS);
                        insertUserSettings.setInt(1, userId);
                        insertUserSettings.setString(2, param);
                        status = insertUserSettings.executeUpdate();
                    } finally {
                        if (insertUserSettings != null) {
                            insertUserSettings.close();
                        }
                    }
                }
            } finally {
                con.close();
            }
            return status;
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
    }

    /**
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public int updateLives(User user, int lives) {
        PreparedStatement updateUserGameLivesPrpStatement = null;
        Connection con = null;
        int state = -1;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    updateUserGameLivesPrpStatement = con.prepareStatement(SQLReq.UPDATE_USER_GAME_LIVES);
                    updateUserGameLivesPrpStatement.setInt(1, user.getGameLives());
                    updateUserGameLivesPrpStatement.setInt(2, user.getUserId());
                    state = updateUserGameLivesPrpStatement.executeUpdate();
                } finally {
                    if (updateUserGameLivesPrpStatement != null) {
                        updateUserGameLivesPrpStatement.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return state;
    }

    @Override
    public int unlockRoadBlock(int userId, int levelSetId) {
        PreparedStatement updateRoadBlockPrpStatement = null;
        Connection con = null;
        int state = 0;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    updateRoadBlockPrpStatement = con.prepareStatement(SQLReq.ROADBLOCK_UNLOCK);
                    updateRoadBlockPrpStatement.setInt(1, userId);
                    updateRoadBlockPrpStatement.setInt(2, levelSetId);
                    state = updateRoadBlockPrpStatement.executeUpdate();
                } finally {
                    if (updateRoadBlockPrpStatement != null) {
                        updateRoadBlockPrpStatement.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return state;
    }

    @Override
    public int releaseRoadBlock(int userId, int levelSetId, String from) {
        PreparedStatement insertRoadBlockPrpStatement = null;
        PreparedStatement updateRoadBlockPrpStatement = null;
        Connection con = null;
        int status = 0;

        try {
            con = dataSource.getConnection();
            try {
                try {
                    updateRoadBlockPrpStatement = con.prepareStatement(SQLReq.ROADBLOCK_UPDATE_FRIEND);
                    updateRoadBlockPrpStatement.setString(1, from);
                    updateRoadBlockPrpStatement.setInt(2, userId);
                    updateRoadBlockPrpStatement.setInt(3, levelSetId);
                    status = updateRoadBlockPrpStatement.executeUpdate();
                } finally {
                    if (updateRoadBlockPrpStatement != null) {
                        updateRoadBlockPrpStatement.close();
                    }
                }

                if (status == 0) {
                    try {
                        insertRoadBlockPrpStatement = con.prepareStatement(SQLReq.ROADBLOCK_INSERT_FRIEND);
                        insertRoadBlockPrpStatement.setInt(1, userId);
                        insertRoadBlockPrpStatement.setInt(2, levelSetId);
                        insertRoadBlockPrpStatement.setString(3, from);
                        status = insertRoadBlockPrpStatement.executeUpdate();
                    } finally {
                        if (insertRoadBlockPrpStatement != null) {
                            insertRoadBlockPrpStatement.close();
                        }
                    }
                }
            } finally {
                con.close();
            }
            return status;
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
    }

    /**
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public int addPet(final User user, final int pet_id) {
        PreparedStatement insertUserPetPrpStatement = null;
        Connection con = null;
        int state = -1;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    insertUserPetPrpStatement = con.prepareStatement(SQLReq.INSERT_USER_PET);
                    insertUserPetPrpStatement.setInt(1, user.getUserId());
                    insertUserPetPrpStatement.setInt(2, pet_id);
                    state = insertUserPetPrpStatement.executeUpdate();
                } finally {
                    if (insertUserPetPrpStatement != null) {
                        insertUserPetPrpStatement.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return state;
    }

    /**
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public int addTreasure(final User user, final int treasure_id) {
        PreparedStatement insertUserTreasurePrpStatement = null;
        Connection con = null;
        int state = -1;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    insertUserTreasurePrpStatement = con.prepareStatement(SQLReq.INSERT_USER_TREASURE);
                    insertUserTreasurePrpStatement.setInt(1, user.getUserId());
                    insertUserTreasurePrpStatement.setInt(2, treasure_id);
                    state = insertUserTreasurePrpStatement.executeUpdate();
                } finally {
                    if (insertUserTreasurePrpStatement != null) {
                        insertUserTreasurePrpStatement.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return state;
    }

    /**
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public int addBoss(final User user, final int boss_id) {
        PreparedStatement insertUserBossPrpStatement = null;
        Connection con = null;
        int state = -1;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    insertUserBossPrpStatement = con.prepareStatement(SQLReq.INSERT_USER_BOSS);
                    insertUserBossPrpStatement.setInt(1, user.getUserId());
                    insertUserBossPrpStatement.setInt(2, boss_id);
                    state = insertUserBossPrpStatement.executeUpdate();
                } finally {
                    if (insertUserBossPrpStatement != null) {
                        insertUserBossPrpStatement.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return state;
    }

    /**
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public int addCollection(User user, int collection_id) {
        PreparedStatement insertUserCollectionPrpStatement = null;
        Connection con = null;
        int state = -1;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    insertUserCollectionPrpStatement = con.prepareStatement(SQLReq.INSERT_USER_COLLECTION);
                    insertUserCollectionPrpStatement.setInt(1, user.getUserId());
                    insertUserCollectionPrpStatement.setInt(2, collection_id);
                    state = insertUserCollectionPrpStatement.executeUpdate();
                } finally {
                    if (insertUserCollectionPrpStatement != null) {
                        insertUserCollectionPrpStatement.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return state;
    }

    /**
     * @param user
     * @return
     * @throws SQLException
     */
    @Override
    public int addStock(User user, int stock_id) {
        PreparedStatement updateUserStockPrpStatement = null;
        PreparedStatement insertUserStockPrpStatement = null;
        Connection con = null;
        int state = -1;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    updateUserStockPrpStatement = con.prepareStatement(SQLReq.UPDATE_USER_STOCKS);
                    updateUserStockPrpStatement.setInt(1, user.getUserId());
                    updateUserStockPrpStatement.setInt(2, stock_id);
                    state = updateUserStockPrpStatement.executeUpdate();
                } finally {
                    if (updateUserStockPrpStatement != null) {
                        updateUserStockPrpStatement.close();
                    }
                }
                if (state <= 0) {
                    try {
                        insertUserStockPrpStatement = con.prepareStatement(SQLReq.INSERT_USER_STOCKS);
                        insertUserStockPrpStatement.setInt(1, user.getUserId());
                        insertUserStockPrpStatement.setInt(2, stock_id);
                        state = insertUserStockPrpStatement.executeUpdate();
                    } finally {
                        if (insertUserStockPrpStatement != null) {
                            insertUserStockPrpStatement.close();
                        }
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return state;
    }

    @Override
    public int addSecret(int userId, int levelId, int levelSetId, int isPay) {
        PreparedStatement insertUserSecret;
        Connection con = null;
        int state = -1;

        try {
            con = dataSource.getConnection();
            try {

                PreparedStatement getUserSecret = con.prepareStatement(SQLReq.SECRET_LEVEL_GET);
                getUserSecret.setInt(1, userId);
                getUserSecret.setInt(2, levelSetId);
                getUserSecret.setInt(3, levelId);
                ResultSet rs = getUserSecret.executeQuery();

                if (rs.next()) {
                    state = rs.getInt(1);
                }

                closeQuietly(getUserSecret, rs);

                if (state <= 0) {
                    insertUserSecret = con.prepareStatement(SQLReq.SECRET_LEVEL_INSERT);
                    insertUserSecret.setInt(1, userId);
                    insertUserSecret.setInt(2, levelSetId);
                    insertUserSecret.setInt(3, levelId);
                    insertUserSecret.setInt(4, isPay);
                    state = insertUserSecret.executeUpdate();
                    closeQuietly(insertUserSecret, rs);
                }

            } finally {
                closeQuietly(con);
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return state;
    }

    public static void closeQuietly(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException logOrIgnore) {
                LoggerFactory.getLogger(Thread.currentThread().getName()).error(logOrIgnore.toString());
                throw new ServerException(ErrorCodes.TEMP_DB_ERROR, logOrIgnore.toString(), true);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException logOrIgnore) {
                LoggerFactory.getLogger(Thread.currentThread().getName()).error(logOrIgnore.toString());
                throw new ServerException(ErrorCodes.TEMP_DB_ERROR, logOrIgnore.toString(), true);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException logOrIgnore) {
                LoggerFactory.getLogger(Thread.currentThread().getName()).error(logOrIgnore.toString());
                throw new ServerException(ErrorCodes.TEMP_DB_ERROR, logOrIgnore.toString(), true);
            }
        }
    }

    public static void closeQuietly(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException logOrIgnore) {
                LoggerFactory.getLogger(Thread.currentThread().getName()).error(logOrIgnore.toString());
                throw new ServerException(ErrorCodes.TEMP_DB_ERROR, logOrIgnore.toString(), true);
            }
        }
    }

    public static void closeQuietly(Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException logOrIgnore) {
                LoggerFactory.getLogger(Thread.currentThread().getName()).error(logOrIgnore.toString());
                throw new ServerException(ErrorCodes.TEMP_DB_ERROR, logOrIgnore.toString(), true);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException logOrIgnore) {
                LoggerFactory.getLogger(Thread.currentThread().getName()).error(logOrIgnore.toString());
                throw new ServerException(ErrorCodes.TEMP_DB_ERROR, logOrIgnore.toString(), true);
            }
        }
    }

    @Override
    public int updateMaxLevel(User user, int levelSetId, int levelId,
            Timestamp time_stop) {
        PreparedStatement insertMaxLevelPrpStatement = null;
        PreparedStatement updateMaxLevelPrpStatement = null;
        Connection con = null;
        int status = 0;

        try {
            con = dataSource.getConnection();
            try {
                try {
                    updateMaxLevelPrpStatement = con.prepareStatement(SQLReq.UPDATE_USER_MAX_LEVEL);
                    updateMaxLevelPrpStatement.setInt(1, levelSetId);
                    updateMaxLevelPrpStatement.setInt(2, levelId);
                    updateMaxLevelPrpStatement.setTimestamp(3, time_stop);
                    updateMaxLevelPrpStatement.setInt(4, user.getUserId());

                    status = updateMaxLevelPrpStatement.executeUpdate();
                } finally {
                    if (updateMaxLevelPrpStatement != null) {
                        updateMaxLevelPrpStatement.close();
                    }
                }

                if (status == 0) {
                    try {
                        insertMaxLevelPrpStatement = con.prepareStatement(SQLReq.INSERT_USER_MAX_LEVEL);
                        insertMaxLevelPrpStatement.setInt(1, levelSetId);
                        insertMaxLevelPrpStatement.setInt(2, levelId);
                        insertMaxLevelPrpStatement.setTimestamp(3, time_stop);
                        insertMaxLevelPrpStatement.setInt(4, user.getUserId());

                        status = insertMaxLevelPrpStatement.executeUpdate();
                    } finally {
                        if (insertMaxLevelPrpStatement != null) {
                            insertMaxLevelPrpStatement.close();
                        }
                    }
                }
            } finally {
                con.close();
            }
            return status;
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
    }
}
