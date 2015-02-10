DROP TABLE IF EXISTS `errors_dump`;
CREATE TABLE `errors_dump` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_sn_id` varchar(255) DEFAULT NULL,
  `browser` varchar(255) DEFAULT NULL,
  `flash` varchar(255) DEFAULT NULL,
  `error_msg` text,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `gifts_dump`;
CREATE TABLE `gifts_dump` (
  `id` int(11) NOT NULL,
  `from_sn_id` varchar(255) DEFAULT NULL,
  `to_sn_id` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT '0',
  `level_set_id` int(11) DEFAULT '0',
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  KEY `IDX_gifts_from_sn_id` (`from_sn_id`),
  KEY `IDX_gifts_to_sn_id` (`to_sn_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AVG_ROW_LENGTH=399;

DROP TABLE IF EXISTS `login_dump`;
CREATE TABLE `login_dump` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_sn_id` varchar(255) DEFAULT NULL,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `money` int(11) DEFAULT NULL,
  `crystals` int(11) DEFAULT NULL,
  `game_lives` int(11) DEFAULT NULL,
  `bonus_day` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `statistic_levels_dump`;
CREATE TABLE `statistic_levels_dump` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `level_number` int(11) DEFAULT NULL,
  `levelset_number` int(11) DEFAULT NULL,
  `stars` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `game_type` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `additional_moves` tinyint(1) DEFAULT NULL,
  `time_start` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `time_stop` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `bought_instruments` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `used_instruments` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `used_boosts` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `used_pets` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `available_pets` varchar(255) CHARACTER SET latin1 DEFAULT NULL,
  `treasure_collected` tinyint(1) DEFAULT NULL,
  `pet_collected` tinyint(1) DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;