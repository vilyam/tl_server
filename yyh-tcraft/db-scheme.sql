-- Скрипт сгенерирован Devart dbForge Studio for MySQL, Версия 6.0.493.0
-- Домашняя страница продукта: http://www.devart.com/ru/dbforge/mysql/studio
-- Дата скрипта: 28.12.2013 18:25:49
-- Версия сервера: 5.5.31-0+wheezy1-log
-- Версия клиента: 4.1

--
-- Описание для таблицы boss_all
--
CREATE TABLE IF NOT EXISTS boss_all (
  id int(10) UNSIGNED NOT NULL,
  name varchar(50) DEFAULT NULL
)
ENGINE = INNODB
AVG_ROW_LENGTH = 2340
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы errors_dump
--
CREATE TABLE IF NOT EXISTS errors_dump (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_sn_id varchar(255) DEFAULT NULL,
  browser varchar(255) DEFAULT NULL,
  flash varchar(255) DEFAULT NULL,
  error_msg text DEFAULT NULL,
  date timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 3
AVG_ROW_LENGTH = 8192
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы gifts
--
CREATE TABLE IF NOT EXISTS gifts (
  id int(11) NOT NULL AUTO_INCREMENT,
  from_sn_id varchar(255) DEFAULT NULL,
  to_sn_id varchar(255) DEFAULT NULL,
  type varchar(255) DEFAULT NULL,
  count int(11) DEFAULT 0,
  level_set_id int(11) DEFAULT 0,
  status enum ('SUSPENDED', 'WAITING') DEFAULT 'SUSPENDED',
  PRIMARY KEY (id),
  INDEX IDX_gifts_from_sn_id (from_sn_id),
  INDEX IDX_gifts_to_sn_id (to_sn_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 219
AVG_ROW_LENGTH = 409
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы gifts_dump
--
CREATE TABLE IF NOT EXISTS gifts_dump (
  id int(11) NOT NULL,
  from_sn_id varchar(255) DEFAULT NULL,
  to_sn_id varchar(255) DEFAULT NULL,
  type varchar(255) DEFAULT NULL,
  count int(11) DEFAULT 0,
  level_set_id int(11) DEFAULT 0,
  time timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX IDX_gifts_from_sn_id (from_sn_id),
  INDEX IDX_gifts_to_sn_id (to_sn_id)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 399
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы payments_items
--
CREATE TABLE IF NOT EXISTS payments_items (
  service_id int(11) NOT NULL AUTO_INCREMENT,
  mailiki_price int(11) DEFAULT 0 COMMENT 'in miail.ru votes',
  other_price int(11) DEFAULT 0,
  value int(11) DEFAULT 0,
  service_name varchar(255) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  PRIMARY KEY (service_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 11
AVG_ROW_LENGTH = 3276
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы payments_orders
--
CREATE TABLE IF NOT EXISTS payments_orders (
  app_order_id int(11) NOT NULL AUTO_INCREMENT,
  transaction_id int(11) NOT NULL,
  user_sn_id varchar(255) NOT NULL,
  date timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  service_name varchar(255) DEFAULT NULL,
  service_id int(11) NOT NULL,
  mailiki_price int(11) NOT NULL,
  PRIMARY KEY (app_order_id),
  UNIQUE INDEX UK_payments_orders_app_order_id (app_order_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 118
AVG_ROW_LENGTH = 140
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы pets_all
--
CREATE TABLE IF NOT EXISTS pets_all (
  id int(10) UNSIGNED NOT NULL,
  name varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 3276
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы statistic_levels
--
CREATE TABLE IF NOT EXISTS statistic_levels (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL,
  level_number int(11) DEFAULT 0,
  levelset_number int(11) DEFAULT 0,
  stars int(11) DEFAULT 0,
  score int(11) DEFAULT 0,
  treasure_collected tinyint(1) UNSIGNED NOT NULL DEFAULT 0,
  game_type enum ('DEFAULT', 'BOSS', 'PET', 'SECRET') DEFAULT 'DEFAULT',
  PRIMARY KEY (id, user_id),
  INDEX IX_adventure_statistic_levels_user_id (user_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1276
AVG_ROW_LENGTH = 1820
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы statistic_levels_dump
--
CREATE TABLE IF NOT EXISTS statistic_levels_dump (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  user_id int(11) DEFAULT NULL,
  level_number int(11) DEFAULT NULL,
  levelset_number int(11) DEFAULT NULL,
  stars int(11) DEFAULT NULL,
  score int(11) DEFAULT NULL,
  game_type varchar(255) DEFAULT NULL,
  additional_moves tinyint(1) DEFAULT NULL,
  time_start timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  time_stop timestamp NULL DEFAULT '0000-00-00 00:00:00',
  bought_instruments varchar(255) DEFAULT NULL,
  used_instruments varchar(255) DEFAULT NULL,
  used_boosts varchar(255) DEFAULT NULL,
  used_pets varchar(255) DEFAULT NULL,
  available_pets varchar(255) DEFAULT NULL,
  treasure_collected tinyint(1) DEFAULT NULL,
  pet_collected tinyint(1) DEFAULT NULL,
  money int(11) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 4211
AVG_ROW_LENGTH = 77
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы store_items
--
CREATE TABLE IF NOT EXISTS store_items (
  item_id int(11) NOT NULL AUTO_INCREMENT,
  resource varchar(255) NOT NULL,
  value int(11) NOT NULL,
  price int(11) NOT NULL,
  currency enum ('MONEY', 'CRYSTALS') NOT NULL DEFAULT 'CRYSTALS',
  param_1 varchar(255) DEFAULT NULL,
  param_2 varchar(255) DEFAULT NULL,
  PRIMARY KEY (item_id)
)
ENGINE = MYISAM
AUTO_INCREMENT = 24
AVG_ROW_LENGTH = 33
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы store_orders
--
CREATE TABLE IF NOT EXISTS store_orders (
  order_id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL DEFAULT 0,
  date timestamp DEFAULT CURRENT_TIMESTAMP,
  item_id int(11) NOT NULL DEFAULT 0,
  resource varchar(255) NOT NULL,
  value int(11) NOT NULL,
  price int(11) NOT NULL,
  currency enum ('MONEY', 'CRYSTALS') NOT NULL DEFAULT 'CRYSTALS',
  param_1 varchar(255) DEFAULT NULL,
  param_2 varchar(255) DEFAULT NULL,
  PRIMARY KEY (order_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 1240
AVG_ROW_LENGTH = 90
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы test
--
CREATE TABLE IF NOT EXISTS test (
  id bigint(20) DEFAULT NULL,
  test_param varchar(255) DEFAULT NULL
)
ENGINE = INNODB
AVG_ROW_LENGTH = 16384
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы tools_prices
--
CREATE TABLE IF NOT EXISTS tools_prices (
  tool_id int(2) NOT NULL,
  number int(4) DEFAULT NULL,
  price int(20) DEFAULT NULL,
  PRIMARY KEY (tool_id)
)
ENGINE = MYISAM
AVG_ROW_LENGTH = 13
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы tools_settings
--
CREATE TABLE IF NOT EXISTS tools_settings (
  tool_id int(2) NOT NULL,
  is_loading tinyint(4) DEFAULT 0,
  loading_time bigint(20) DEFAULT NULL,
  start_from_level int(5) DEFAULT 0,
  default_tool_count int(5) DEFAULT 0,
  PRIMARY KEY (tool_id)
)
ENGINE = MYISAM
AVG_ROW_LENGTH = 22
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы tools_type
--
CREATE TABLE IF NOT EXISTS tools_type (
  tool_id int(2) NOT NULL,
  tool_name varchar(20) DEFAULT NULL,
  PRIMARY KEY (tool_id)
)
ENGINE = MYISAM
AVG_ROW_LENGTH = 20
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы treasures_all
--
CREATE TABLE IF NOT EXISTS treasures_all (
  id int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  name varchar(50) DEFAULT NULL,
  type varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 46
AVG_ROW_LENGTH = 468
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы treasures_collections_all
--
CREATE TABLE IF NOT EXISTS treasures_collections_all (
  id int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  treasures varchar(255) DEFAULT NULL,
  name varchar(50) DEFAULT NULL,
  description varchar(255) DEFAULT NULL,
  type varchar(255) DEFAULT NULL,
  award_string varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB
AUTO_INCREMENT = 10
AVG_ROW_LENGTH = 2340
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы user
--
CREATE TABLE IF NOT EXISTS user (
  user_id int(11) NOT NULL AUTO_INCREMENT,
  user_sn_id varchar(50) NOT NULL,
  money int(11) DEFAULT 0,
  crystals int(11) NOT NULL DEFAULT 25,
  bonus_day int(11) NOT NULL DEFAULT 0,
  secret_code varchar(128) DEFAULT NULL,
  hash varchar(255) DEFAULT NULL,
  is_debug_mode tinyint(3) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (user_id, user_sn_id),
  INDEX IX_user_user_sn_id (user_sn_id),
  UNIQUE INDEX UK_user_user_id (user_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 9300
AVG_ROW_LENGTH = 96
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы user_block_levelsets
--
CREATE TABLE IF NOT EXISTS user_block_levelsets (
  user_id int(11) NOT NULL,
  levelset_id int(11) DEFAULT NULL,
  friends varchar(255) NOT NULL DEFAULT 'HEAD',
  is_pay tinyint(4) DEFAULT 0,
  INDEX IDX_block_levelsets_levelset_id (levelset_id),
  INDEX IDX_block_levelsets_user_id (user_id)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 260
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы user_block_secret_levels
--
CREATE TABLE IF NOT EXISTS user_block_secret_levels (
  user_id int(11) DEFAULT 0,
  levelset_id int(11) DEFAULT 0,
  level_id int(11) DEFAULT 0,
  is_pay tinyint(4) DEFAULT 0,
  INDEX IDX_block_secret_levels_user_id (user_id),
  INDEX IDX_user_block_secret_levels (level_id, levelset_id)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 218
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы user_boss
--
CREATE TABLE IF NOT EXISTS user_boss (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) DEFAULT 0,
  boss_id int(11) DEFAULT 0,
  PRIMARY KEY (id),
  INDEX IDX_boss_user_id (user_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 93
AVG_ROW_LENGTH = 178
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы user_pets
--
CREATE TABLE IF NOT EXISTS user_pets (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(10) DEFAULT 0,
  pet_id int(11) DEFAULT 0,
  PRIMARY KEY (id),
  INDEX user_id (user_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 51
AVG_ROW_LENGTH = 327
CHARACTER SET latin1
COLLATE latin1_swedish_ci;

--
-- Описание для таблицы user_settings
--
CREATE TABLE IF NOT EXISTS user_settings (
  user_id int(11) NOT NULL,
  param text DEFAULT NULL,
  UNIQUE INDEX UK_user_settings_user_id (user_id)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 420
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы user_treasures
--
CREATE TABLE IF NOT EXISTS user_treasures (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) DEFAULT 0,
  treasure_id int(11) DEFAULT 0,
  PRIMARY KEY (id),
  INDEX IDX_treasures_user_id (user_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 427
AVG_ROW_LENGTH = 38
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы user_treasures_collections
--
CREATE TABLE IF NOT EXISTS user_treasures_collections (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) DEFAULT 0,
  collection_id int(11) DEFAULT 0,
  PRIMARY KEY (id),
  INDEX IDX_treasures_collections_user_id (user_id)
)
ENGINE = INNODB
AUTO_INCREMENT = 43
AVG_ROW_LENGTH = 390
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы user_info
--
CREATE TABLE IF NOT EXISTS user_info (
  user_id int(11) NOT NULL,
  first_name varchar(255) DEFAULT NULL,
  last_name varchar(64) DEFAULT NULL,
  sex int(11) DEFAULT NULL,
  city varchar(30) DEFAULT NULL,
  country varchar(30) DEFAULT NULL,
  birthday varchar(10) DEFAULT NULL,
  lang int(3) DEFAULT NULL,
  PRIMARY KEY (user_id),
  CONSTRAINT user_info_ibfk_1 FOREIGN KEY (user_id)
  REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE RESTRICT
)
ENGINE = INNODB
AVG_ROW_LENGTH = 80
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы user_state
--
CREATE TABLE IF NOT EXISTS user_state (
  user_id int(11) NOT NULL,
  last_daily_login datetime NOT NULL DEFAULT '2001-01-01 00:00:00',
  last_daily_bonus_login datetime DEFAULT '2001-01-01 00:00:00',
  last_game_session datetime DEFAULT '2001-01-01 00:00:00',
  game_lives int(11) DEFAULT 5,
  PRIMARY KEY (user_id),
  INDEX UK_user_states_user_id (user_id),
  CONSTRAINT FK_user_state_user_user_id FOREIGN KEY (user_id)
  REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE RESTRICT
)
ENGINE = INNODB
AVG_ROW_LENGTH = 163
CHARACTER SET utf8
COLLATE utf8_general_ci;

--
-- Описание для таблицы user_tools
--
CREATE TABLE IF NOT EXISTS user_tools (
  user_id int(11) NOT NULL,
  shovel int(5) NOT NULL DEFAULT 0,
  last_using_shovel_ts datetime DEFAULT NULL,
  tractor int(5) NOT NULL DEFAULT 0,
  last_using_tractor_ts datetime DEFAULT NULL,
  magnet int(5) NOT NULL DEFAULT 0,
  last_using_magnet_ts datetime DEFAULT NULL,
  firework int(5) NOT NULL DEFAULT 0,
  last_using_firework_ts datetime DEFAULT NULL,
  shuffle int(5) NOT NULL DEFAULT 0,
  last_using_shuffle_ts datetime DEFAULT NULL,
  plus5 int(5) NOT NULL DEFAULT 0,
  last_using_plus5_ts datetime DEFAULT NULL,
  gold_pot int(5) NOT NULL DEFAULT 0,
  last_using_gold_pot_ts datetime DEFAULT NULL,
  star_mode int(5) NOT NULL DEFAULT 0,
  last_using_star_mode_ts datetime DEFAULT NULL,
  PRIMARY KEY (user_id),
  INDEX IDX_user_tools_user_id (user_id),
  CONSTRAINT user_tools_ibfk_1 FOREIGN KEY (user_id)
  REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE RESTRICT
)
ENGINE = INNODB
AVG_ROW_LENGTH = 165
CHARACTER SET utf8
COLLATE utf8_general_ci;

DELIMITER $$

--
-- Описание для процедуры update_levels_statistic
--
CREATE DEFINER = 'yyh_user'@'%'
PROCEDURE update_levels_statistic (IN p_user_id int, IN p_level_set_number int, IN p_level_number int, IN p_stars int, IN p_treasure_collected tinyint, IN p_pet_collected tinyint, IN p_money int, IN p_score int, IN p_game_type varchar(255), IN pd_additional_moves smallint, IN pd_time_start timestamp, IN pd_time_stop timestamp, IN pd_bought_instruments varchar(255), IN pd_used_instruments varchar(255), IN pd_used_boosts varchar(255), IN pd_used_pets varchar(255), IN pd_available_pets varchar(255))
BEGIN
  DECLARE l_tmp_stars integer;
  DECLARE l_tmp_score integer;
  DECLARE l_tmp_treasure_collected integer;

  IF (p_stars > 0) THEN
    SELECT
      stars,
      score,
      treasure_collected INTO l_tmp_stars, l_tmp_score, l_tmp_treasure_collected
    FROM statistic_levels
    WHERE level_number = p_level_number AND
    levelset_number = p_level_set_number AND
    user_id = p_user_id
    LIMIT 1;

    IF l_tmp_stars IS NOT NULL AND l_tmp_score IS NOT NULL
      THEN
      UPDATE statistic_levels
      SET stars = CASE
            WHEN l_tmp_stars > p_stars THEN l_tmp_stars ELSE p_stars
          END,
          score = CASE
            WHEN l_tmp_score > p_score THEN l_tmp_score ELSE p_score
          END,
          treasure_collected = CASE
            WHEN l_tmp_treasure_collected > p_treasure_collected THEN l_tmp_treasure_collected ELSE p_treasure_collected
          END
      WHERE user_id = p_user_id AND level_number = p_level_number AND levelset_number = p_level_set_number;
    ELSE
      INSERT INTO statistic_levels
      SET
      user_id = p_user_id, stars = p_stars, treasure_collected = p_treasure_collected, score = p_score, levelset_number = p_level_set_number, level_number = p_level_number, game_type = p_game_type;
    END IF;
  ELSE
    IF NOT EXISTS (SELECT
          1
        FROM statistic_levels
        WHERE level_number = p_level_number AND
        levelset_number = p_level_set_number AND
        user_id = p_user_id
        LIMIT 1) THEN
      INSERT INTO statistic_levels
      SET
      user_id = p_user_id, stars = 0, treasure_collected = 0, score = 0, levelset_number = p_level_set_number, level_number = p_level_number, game_type = p_game_type;
    END IF;
  END IF;

  INSERT INTO statistic_levels_dump (user_id, level_number, levelset_number, stars, score, game_type, additional_moves, time_start, time_stop, bought_instruments, used_instruments, used_boosts, used_pets, available_pets, treasure_collected, pet_collected, money)
    VALUES (p_user_id, p_level_number, p_level_set_number, p_stars, p_score, p_game_type, pd_additional_moves, pd_time_start, pd_time_stop, pd_bought_instruments, pd_used_instruments, pd_used_boosts, pd_used_pets, pd_available_pets, p_treasure_collected, p_pet_collected, p_money);
END
$$

--
-- Описание для процедуры update_secret_level
--
CREATE DEFINER = 'yyh_user'@'%'
PROCEDURE update_secret_level (IN p_user_id int, IN p_level_set_number int, IN p_level_number int, IN p_is_pay int)
BEGIN
  IF NOT EXISTS (SELECT
        1
      FROM user_block_secret_levels
      WHERE user_id = p_user_id
      AND levelset_id = p_level_set_number
      AND level_id = p_level_number
      LIMIT 1) THEN
    INSERT INTO user_block_secret_levels (user_id, levelset_id, level_id, is_pay)
      VALUES (p_user_id, p_level_set_number, p_level_number, p_is_pay);
  END IF;
END
$$

--
-- Описание для процедуры user_create
--
CREATE DEFINER = 'yyh_user'@'%'
PROCEDURE user_create (IN p_user_sn_id varchar(50), IN p_user_name varchar(64), IN p_user_last_name varchar(64), IN p_user_sex int, IN p_user_city varchar(30), IN p_user_secret_code varchar(128), IN p_country varchar(30), IN p_birthday varchar(30), IN p_language int, IN p_hash varchar(255))
BEGIN
  DECLARE l_new_user_id integer;
  INSERT INTO user (user_sn_id, secret_code, HASH)
    VALUES (p_user_sn_id, p_user_secret_code, p_hash);
  SET l_new_user_id = LAST_INSERT_ID();
  INSERT INTO user_info (user_id, first_name, last_name, sex, city, country, birthday, lang)
    VALUES (l_new_user_id, p_user_name, p_user_last_name, p_user_sex, p_user_city, p_country, p_birthday, p_language);
  INSERT INTO user_tools (user_id)
    VALUES (l_new_user_id);
  INSERT INTO user_state (user_id)
    VALUES (l_new_user_id);


END
$$

--
-- Описание для процедуры user_delete
--
CREATE DEFINER = 'yyh_user'@'%'
PROCEDURE user_delete (IN p_user_sn_id int)
BEGIN
  DELETE
    FROM user
  WHERE user_sn_id = p_user_sn_id;
END
$$

--
-- Описание для триггера gift_dump
--
CREATE
DEFINER = 'yyh_user'@'%'
TRIGGER gift_dump
BEFORE DELETE
ON gifts
FOR EACH ROW
BEGIN
  INSERT INTO gifts_dump SET
  `id` = OLD.`id`,
  `from_sn_id` = OLD.`from_sn_id`,
  `to_sn_id` = OLD.`to_sn_id`,
  `type` = OLD.`type`,
  `count` = OLD.`count`,
  `level_set_id` = OLD.`level_set_id`;
END
$$

DELIMITER ;