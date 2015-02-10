CREATE TABLE PUBLIC.USER 
 (user_id int(11) NOT NULL AUTO_INCREMENT,
  user_sn_id VARCHAR(50) NOT NULL,
  money int(11) DEFAULT 0,
  crystals int(11) NOT NULL DEFAULT 25,
  bonus_day int(11) NOT NULL DEFAULT 0,
  secret_code VARCHAR(128) DEFAULT NULL,
  hash VARCHAR(255) DEFAULT NULL,
  is_debug_mode int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (user_id));




CREATE TABLE IF NOT EXISTS user_block_levelsets 
(user_id INTEGER NOT NULL,
  levelset_id INTEGER DEFAULT NULL,
  friends varchar(255) NOT NULL,
  is_pay INTEGER DEFAULT 0);



CREATE TABLE IF NOT EXISTS user_block_secret_levels (
  user_id int(11) DEFAULT 0,
  levelset_id int(11) DEFAULT 0,
  level_id int(11) DEFAULT 0,
  is_pay tinyint(4) DEFAULT 0);



CREATE TABLE IF NOT EXISTS user_boss (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) DEFAULT 0,
  boss_id int(11) DEFAULT 0,
  PRIMARY KEY (id));



CREATE TABLE IF NOT EXISTS user_pets (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(10) DEFAULT 0,
  pet_id int(11) DEFAULT 0,
  PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS user_settings (
  user_id int(11) NOT NULL,
  param text DEFAULT NULL);



CREATE TABLE IF NOT EXISTS user_treasures (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) DEFAULT 0,
  treasure_id int(11) DEFAULT 0,
  PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS user_treasures_collections (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) DEFAULT 0,
  collection_id int(11) DEFAULT 0,
  PRIMARY KEY (id));



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
  REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE RESTRICT);



CREATE TABLE IF NOT EXISTS user_state (
  user_id int(11) NOT NULL,
  last_daily_login datetime NOT NULL DEFAULT '2001-01-01 00:00:00',
  last_daily_bonus_login datetime DEFAULT '2001-01-01 00:00:00',
  last_game_session datetime DEFAULT '2001-01-01 00:00:00',
  game_lives int(11) DEFAULT 5,
  PRIMARY KEY (user_id),
  CONSTRAINT FK_user_state_user_user_id FOREIGN KEY (user_id)
  REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE RESTRICT);



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
  CONSTRAINT user_tools_ibfk_1 FOREIGN KEY (user_id)
  REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE RESTRICT);


