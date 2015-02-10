package com.c17.yyh.server;

public final class SQLReq {
    // USER DAO
    public static final String GET_USER_SQL                 = "SELECT user.* FROM user WHERE user_sn_id = ?";
    public static final String GET_USER_INFO_SQL            = "SELECT user_info.* FROM user_info WHERE user_id = ?";
    public static final String GET_USER_STATE_SQL           = "SELECT user_state.* FROM user_state WHERE user_id = ?";
    public static final String GET_USER_TOOL_SQL            = "SELECT user_tools.* FROM user_tools WHERE user_id = ?";
    public static final String GET_USER_PETS_SQL            = "SELECT user_pets.pet_id FROM user_pets WHERE user_pets.user_id = ?";
    public static final String GET_USER_TREASURE_SQL        = "SELECT user_treasures.treasure_id FROM user_treasures WHERE user_treasures.user_id = ?";
    public static final String GET_USER_STATISTIC_SQL       = "SELECT statistic_levels.* from statistic_levels WHERE user_id = ?";
    public static final String GET_USER_WON_COLLECTION_SQL  = "SELECT user_treasures_collections.collection_id FROM user_treasures_collections WHERE user_id = ?";
    public static final String GET_USER_BOSSES_SQL          = "SELECT user_boss.boss_id FROM user_boss WHERE user_boss.user_id = ?";
    public static final String GET_USER_SECRET_LEVELS_SQL   = "SELECT user_block_secret_levels.* FROM user_block_secret_levels WHERE user_block_secret_levels.user_id = ?";
    public static final String GET_USER_SETTINGS_SQL        = "SELECT user_settings.* FROM user_settings WHERE user_settings.user_id = ?";
    public static final String GET_USER_IS_PAYER            = "SELECT EXISTS(SELECT 1 FROM payments_orders WHERE user_sn_id = ? LIMIT 1)";
    public static final String GET_USER_SOCIAL_QUEST        = "SELECT state, is_done FROM user_social_quest WHERE user_id = ?";
    public static final String GET_USER_PROGRESS            = "SELECT user_max_level.* FROM user_max_level WHERE user_id = ?";
    public static final String GET_USER_STOCKS_SQL          = "SELECT user_stocks.* FROM user_stocks WHERE user_id = ?";
   
    public static final String INSERT_USER_PET              = "INSERT INTO user_pets (user_id, pet_id) VALUES (?, ?)";
    public static final String INSERT_USER_BOSS             = "INSERT INTO user_boss (user_id, boss_id) VALUES (?, ?)";
    public static final String INSERT_USER_TREASURE         = "INSERT INTO user_treasures (user_id, treasure_id) VALUES (?, ?)";
    public static final String INSERT_USER_COLLECTION       = "INSERT INTO user_treasures_collections (user_id, collection_id) VALUES (?,?)";
    public static final String INSERT_USER_SETINGS          = "INSERT INTO user_settings (user_id, param) VALUES (?, ?)";
    public static final String INSERT_USER_STOCKS           = "INSERT INTO user_stocks (user_id, stock_id) values (?, ?)";
    public static final String UPDATE_USER_STOCKS           = "UPDATE user_stocks SET count = (count + 1) WHERE user_id = ? AND stock_id = ?";

    public static final String INSERT_NEW_USER              = "INSERT INTO user (user_sn_id, secret_code, hash) VALUES (?, ?, ?)";
    public static final String INSERT_NEW_USER_INFO         = "INSERT INTO user_info (user_id, first_name, last_name, sex, city, country, birthday, lang, user_page, referrer) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String INSERT_NEW_USER_TOOLS        = "INSERT INTO user_tools (user_id) VALUES (?)";
    public static final String INSERT_NEW_USER_STATE        = "INSERT INTO user_state (user_id, first_daily_login) VALUES (?,?)";
    public static final String INSERT_NEW_USER_SOCIAL_QUEST = "INSERT INTO user_social_quest (user_id) VALUES (?)";

    public static final String INCREMENT_USER_GAME_LIVES    = "UPDATE user_state SET game_lives = (game_lives + ?) WHERE user_id = ?";
    public static final String INCREMENT_USER_MONEY         = "UPDATE user SET money = (money + ?) WHERE user_id = ?";
    public static final String INCREMENT_USER_BALANCE       = "UPDATE user SET money = (money + ?), crystals = (crystals + ?) WHERE user_id = ?";
    public static final String INCREMENT_USER_CRYSTALS      = "UPDATE user SET crystals = (crystals + ?) WHERE user_id = ?";
    public static final String UPDATE_USER_STATE            = "UPDATE user_state SET last_game_session = ?, game_lives = ?, last_daily_login = ? WHERE user_id = ?";
    public static final String UPDATE_USER_GAME_LIVES       = "UPDATE user_state SET game_lives = ? WHERE user_id = ?";
    public static final String UPDATE_USER_SETINGS          = "UPDATE user_settings SET param = ? WHERE user_id=?";
    public static final String GET_USERS                    = "SELECT user.* FROM usere WHERE user_id = (?);";
    public static final String GET_USERS_STATISTIC          = "SELECT statistic_levels.* FROM statistic_levels WHERE user_id IN (?) AND level_number = ? AND levelset_number = ?;";

    //tools
    public static final String TOOLS_UPDATE_OR_INSERT       = "INSERT INTO user_tools (user_id, tool_id, count, last_using) VALUES (:uId, :toolId, :count, :last_using) ON DUPLICATE KEY UPDATE count = :count, last_using = :last_using";
    public static final String TOOLS_INCREMENT              = "INSERT INTO user_tools (user_id, tool_id, count) VALUES (:uId, :toolId, :count) ON DUPLICATE KEY UPDATE count = (count + :count)";

    // STATISTIC DAO
    public static class StatDao {
        public static final String INSERT = "INSERT INTO statistic_levels (user_id, level_number, levelset_number, game_type) VALUES (?, ?, ?, ?)";
        public static final String UPDATE = "UPDATE statistic_levels SET stars = ?, score = ?, treasure_collected = ? WHERE user_id = ? AND level_number = ? AND levelset_number = ?";
    }

    public static class Dump {
        private static final String INSERT_STATISTIC = "INSERT INTO $tableName (user_id, level_number, levelset_number, "
                                                             + "stars, money, score, game_type, additional_moves, time_start, time_stop, "
                                                             + " treasure_collected, pet_collected) "
                                                             + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        private static final String CREATE_STATISTIC = "CREATE TABLE IF NOT EXISTS $tableName ("
                                                             + "id bigint(20) NOT NULL AUTO_INCREMENT,"
                                                             + "user_id int(11) DEFAULT NULL,"
                                                             + "level_number int(11) DEFAULT NULL,"
                                                             + "levelset_number int(11) DEFAULT NULL,"
                                                             + "stars int(11) DEFAULT NULL,"
                                                             + "score int(11) DEFAULT NULL,"
                                                             + "game_type varchar(20) DEFAULT NULL,"
                                                             + "additional_moves tinyint(1) DEFAULT NULL,"
                                                             + "time_start timestamp NULL DEFAULT CURRENT_TIMESTAMP,"
                                                             + "time_stop timestamp NULL DEFAULT '0000-00-00 00:00:00',"
                                                             + "treasure_collected tinyint(1) DEFAULT NULL,"
                                                             + "pet_collected tinyint(1) DEFAULT NULL,"
                                                             + "money int(11) DEFAULT NULL,"
                                                             + "PRIMARY KEY (id)"
                                                             + ") ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8";
        private static final String INSERT_LOGIN     = "INSERT INTO $tableName (user_sn_id, time, money, crystals, game_lives, bonus_day) "
                                                             + "VALUES (?, ?, ?, ?, ?, ?)";
        private static final String CREATE_LOGIN     = "CREATE TABLE IF NOT EXISTS $tableName ("
                                                             + "id bigint(20) NOT NULL AUTO_INCREMENT,"
                                                             + "user_sn_id varchar(255) DEFAULT NULL,"
                                                             + "time timestamp NULL DEFAULT CURRENT_TIMESTAMP,"
                                                             + "money int(11) DEFAULT NULL,"
                                                             + "crystals int(11) DEFAULT NULL,"
                                                             + "game_lives int(11) DEFAULT NULL,"
                                                             + "bonus_day int(11) DEFAULT NULL,"
                                                             + "PRIMARY KEY (id)"
                                                             + ") ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8";
        private static final String INSERT_TOOL     = "INSERT INTO $tableName (user_id, tool_id, level_number, levelset_number) "
                                                             + "VALUES (?, ?, ?, ?)";
        private static final String CREATE_TOOL     = "CREATE TABLE IF NOT EXISTS $tableName ("
                                                             + "id bigint(20) NOT NULL AUTO_INCREMENT,"
                                                             + "user_id int(11) NOT NULL,"
                                                             + "tool_id int(11) NOT NULL,"
                                                             + "level_number int(11) DEFAULT NULL,"
                                                             + "levelset_number int(11) DEFAULT NULL,"
                                                             + "`date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
                                                             + "PRIMARY KEY (id)"
                                                             + ") ENGINE=MyISAM AUTO_INCREMENT=0 DEFAULT CHARSET=utf8";

        public static synchronized String getInsertLogin(String tableName) {
            return INSERT_LOGIN.replace("$tableName", tableName);
        }

        public static String getInsertStatistic(String tableName) {
            return INSERT_STATISTIC.replace("$tableName", tableName);
        }

        public static String getInsertTool(String tableName) {
            return INSERT_TOOL.replace("$tableName", tableName);
        }

        public static String getCreateStatement(String hardDbName) {
            if (hardDbName.contains("statistic"))
                return CREATE_STATISTIC.replace("$tableName", hardDbName);
            if (hardDbName.contains("login"))
                return CREATE_LOGIN.replace("$tableName", hardDbName);
            if (hardDbName.contains("tool"))
                return CREATE_TOOL.replace("$tableName", hardDbName);
            return hardDbName;
        }

    }

    public static final String SECRET_LEVEL_GET               = "SELECT EXISTS(SELECT 1 FROM user_block_secret_levels WHERE user_id = ? AND levelset_id = ? AND level_id = ? LIMIT 1)";
    public static final String SECRET_LEVEL_INSERT            = " INSERT INTO user_block_secret_levels (user_id, levelset_id, level_id, is_pay) VALUES (?, ?, ?, ?)";

    // GIFT DAO
    public static final String GIFT_INSERT_ASK_REQUEST        = "INSERT INTO gifts (from_sn_id, to_sn_id, level_set_id, type, status, request_time, response_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String GIFT_GET_LIST_RECEIVED_REQUEST = "SELECT gifts.* FROM gifts WHERE gifts.to_sn_id = ? AND (status = 'SUSPENDED' OR status = 'DIRECT')";
    public static final String GIFT_GET_LIST_SENDED_REQUEST   = "SELECT gifts.* FROM gifts WHERE gifts.from_sn_id = ? AND status != 'DIRECT'";
    public static final String GIFT_DELETE_REQUEST            = "DELETE FROM gifts WHERE gifts.id = ?";
    public static final String GIFT_GET_GIFT_BY_ID            = "SELECT gifts.* FROM gifts WHERE gifts.id = ?";
    public static final String GIFT_UPDATE_GIFT_STATUS        = "UPDATE gifts SET status = ?, response_time = ? WHERE id = ?";
    public static final String GIFT_ACCEPT                    = "UPDATE gifts SET status = 'PROCESSED' WHERE id = ?";
    public static final String GIFT_CLEAR_OLD                 = "DELETE FROM gifts WHERE request_time < DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -2 DAY)";

    public static final String INSERT_STORED_GIFT             = "INSERT INTO stored_gifts (from_sn_id, to_sn_id, level_set_id, level_id, type, request_time, response_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_GIFTS_BY_STATUS            = "SELECT * FROM stored_gifts WHERE to_sn_id = ? AND status = ?";
    public static final String GET_GIFTS_BY_LEVEL_AND_STATUS  = "SELECT * FROM stored_gifts WHERE to_sn_id = ? AND level_set_id = ? AND level_id = ? AND status = ?";
    public static final String UPDATE_STOREDGIFT_STATUS       = "UPDATE stored_gifts SET status = ? WHERE id = ?";
    public static final String STORED_GIFT_CLEAR_OLD          = "DELETE FROM stored_gifts WHERE status = 'ASKED' AND request_time < DATE_ADD(CURRENT_TIMESTAMP, INTERVAL -1 DAY)";
    public static final String DELETE_STORED_GIFTS            = "DELETE FROM stored_gifts WHERE id = ?";

    // ROADBLOCKS
    public static final String ROADBLOCK_INSERT_FRIEND        = "INSERT INTO user_block_levelsets (user_id, levelset_id, friends) VALUES (?, ?, CONCAT_WS(',', friends, ?))";
    public static final String ROADBLOCK_UPDATE_FRIEND        = "UPDATE user_block_levelsets SET friends = CONCAT_WS(',', friends, ?) WHERE user_id=? AND levelset_id=?";
    public static final String ROADBLOCK_UNLOCK               = "INSERT INTO user_block_levelsets (user_id, levelset_id, is_pay) VALUES (?, ?, 1)";
    public static final String ROADBLOCK_GET_BY_USER_ID       = "SELECT user_block_levelsets.* FROM user_block_levelsets WHERE user_block_levelsets.user_id = ?";

    /**
     * String transaction_id, String user_sn_id, Timestamp date, String
     * product_name, String product_title, int product_id, int price, int debug
     */
    public static final String PAYMENTS_ORDER_INSERT_NEW      = "INSERT INTO payments_orders (transaction_id, user_sn_id, date, product_name, product_title, product_id, price, debug, stock_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String PAYMENTS_ORDER_GET             = "SELECT payments_orders.* FROM payments_orders WHERE transaction_id = ?";
    public static final String PURCHASING_ORDER_INSERT_NEW    = "INSERT INTO purchasing_orders (user_id, source, source_id, price, currency, inc_resources, inc_values, levelset_version, level_number, levelset_number) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String INSERT_ERROR_LOG               = "INSERT INTO errors_dump (user_sn_id, browser, flash, error_msg) VALUES (?, ?, ?, ?)";

    public static final String UPDATE_DAILY_BONUS_STATE       = "UPDATE user_state SET last_daily_bonus_login = ?, daily_bonus_marker = ? WHERE user_id = ?";
    public static final String UPDATE_USER_MAX_LEVEL          = "UPDATE user_max_level SET max_levelset_number = ?, max_level_number = ?, date = ? WHERE user_id =?";
    public static final String INSERT_USER_MAX_LEVEL          = "INSERT INTO user_max_level (max_levelset_number, max_level_number, date, user_id) VALUES (?, ?, ?, ?)";
    public static final String UPDATE_USER_SOCIAL_QUEST_STATE = "UPDATE user_social_quest SET state = ? WHERE user_id = ?";
    public static final String UPDATE_USER_SOCIAL_QUEST_DONE  = "UPDATE user_social_quest SET is_done = ? WHERE user_id = ?";
    public static final String INSERT_USER_SOCIAL_QUEST       = "INSERT INTO user_social_quest (user_id, state) VALUES (?, ?)";

    public static final String FRIENDS_BONUS_INSERT_REFERAL   = "INSERT INTO user_refs (user_sn_id, ref) VALUES (?, ?) ON DUPLICATE KEY UPDATE ref = ref";
    public static final String FRIENDS_BONUS_ACCEPT_REFERAL   = "UPDATE user_refs SET status = 1 where user_sn_id = ? and ref = ?";
    public static final String FRIENDS_BONUS_GET_LIST         = "SELECT r.ref, "
                                                                + "COALESCE(uml.max_level_number, 0) as max_level_number, "
                                                                + "COALESCE(uml.max_levelset_number, 0) as max_levelset_number, "
                                                                + "r.status "
                                                                + "from user_refs r "
                                                                + "left outer join user u ON u.user_sn_id = r.ref "
                                                                + "left outer join user_max_level uml on uml.user_id = u.user_id "
                                                                + "WHERE r.user_sn_id = ?";
}
