package com.c17.yyh.db.dao;

import org.springframework.stereotype.Component;

import com.c17.yyh.models.LevelStatistic;

@Component
public interface IStatisticAdventureDao {
    public LevelStatistic updateLevelsStatistic(LevelStatistic levelStatistic);

    public LevelStatistic startLevelStatistic(int userId, int levelSetId, int levelId,
            String string);
}
