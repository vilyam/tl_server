package com.c17.yyh.db;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.c17.yyh.db.dao.IStatisticAdventureDao;
import com.c17.yyh.models.LevelStatistic;
import com.c17.yyh.models.User;

@Component
public class StatisticAdventureDaoMock implements IStatisticAdventureDao {

	@Override
	public com.c17.yyh.models.LevelStatistic startLevelStatistic(
			int userId, int levelSetId, int levelId, String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LevelStatistic updateLevelsStatistic(LevelStatistic levelStatistic) {
		// TODO Auto-generated method stub
		return null;
	}


}
