package com.c17.yyh.db.dao.impl.nosql;

import com.c17.yyh.db.dao.IStatisticAdventureDao;
import com.c17.yyh.models.LevelStatistic;
import com.c17.yyh.type.GameType;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;

public class StatisticAdventureDaoImplNosql implements IStatisticAdventureDao {

    @Autowired
    private CouchbaseTemplate couchbaseTemplate;

    @PostConstruct
    protected void initialize() {
        LoggerFactory.getLogger(getClass()).info("Initialize statisticAdventureServiceNosql");
    }

    @Override
    public LevelStatistic startLevelStatistic(int userId, int levelSetId, int levelId, String game_type) {
        LevelStatistic statistic = new LevelStatistic(userId, levelId, levelSetId, 0, 0, 0, GameType.valueOf(game_type));
        couchbaseTemplate.insert(statistic);
        return statistic;
    }

    //TODO change return to void
    @Override
    public LevelStatistic updateLevelsStatistic(LevelStatistic levelStatistic) {
        couchbaseTemplate.update(levelStatistic);
        return levelStatistic;
    }

}
