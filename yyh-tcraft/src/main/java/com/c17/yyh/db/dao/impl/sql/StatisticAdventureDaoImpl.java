package com.c17.yyh.db.dao.impl.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.c17.yyh.db.dao.IStatisticAdventureDao;
import com.c17.yyh.models.LevelStatistic;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.server.SQLReq;
import com.c17.yyh.exceptions.ServerException;
import com.c17.yyh.type.GameType;

@Repository("statisticAdventureServiceSql")
public class StatisticAdventureDaoImpl implements IStatisticAdventureDao {
	
    private DataSource dataSource;

	@Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
	@PostConstruct
	protected void initialize() {
		LoggerFactory.getLogger(getClass()).info("Initialize statisticAdventureServiceSql");
	}

    @Override
    public LevelStatistic startLevelStatistic(int userId, int levelSetId, int levelId, String game_type) {

        PreparedStatement insertUserFirstLevelStatistic = null;
        Connection con = null;
        int status = 0;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    insertUserFirstLevelStatistic = con.prepareStatement(SQLReq.StatDao.INSERT);
                    insertUserFirstLevelStatistic.setInt(1, userId);
                    insertUserFirstLevelStatistic.setInt(2, levelId);
                    insertUserFirstLevelStatistic.setInt(3, levelSetId);
                    insertUserFirstLevelStatistic.setString(4, game_type);
                    status = insertUserFirstLevelStatistic.executeUpdate();

                } finally {
                    if (insertUserFirstLevelStatistic != null) {
                        insertUserFirstLevelStatistic.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return status > 0 ? new LevelStatistic(userId, levelId, levelSetId, 0, 0, 0, GameType.valueOf(game_type)) : null;
    }

	@Override
	public LevelStatistic updateLevelsStatistic(LevelStatistic levelStatistic) {
		Connection con = null;
        PreparedStatement cs = null;

        try {
            con = dataSource.getConnection();
            try {
                try {
                	//stars = ?, score = ?, treasure_collected = ? WHERE user_id = ? AND level_number = ? AND levelset_number = ?"
                    cs = con.prepareStatement(SQLReq.StatDao.UPDATE);
                    cs.setInt(1, levelStatistic.getStars());
                    cs.setInt(2, levelStatistic.getScore());
                    cs.setInt(3, levelStatistic.getTreasure_collected());
                    cs.setInt(4, levelStatistic.getUser_id());
                    cs.setInt(5, levelStatistic.getLevel_number());
                    cs.setInt(6, levelStatistic.getLevelset_number());
                    
                    cs.executeUpdate();
                } finally {
                    if (cs != null) {
                        cs.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return levelStatistic;
	}

}
