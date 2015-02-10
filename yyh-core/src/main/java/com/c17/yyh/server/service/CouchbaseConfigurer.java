package com.c17.yyh.server.service;

import com.c17.yyh.exceptions.ServerException;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.server.SQLReq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CouchbaseConfigurer {
/*
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    private final String[] viewNames = ArrayUtils.toArray("byUser_id", "userInfo");
    
    //TODO read views from external source
    private final String[] viewCode = ArrayUtils.toArray("function (doc, meta) {\n"
            + "   if (doc._class == \"com.c17.yyh.db.entities.adventure.statistic.LevelStatistic\") {\n"
            + "  emit(doc.user_id, null);\n"
            + "   }\n"
            + "}", "function (doc, meta) {\n"
            + "   if (doc._class == \"com.c17.yyh.db.entities.adventure.statistic.LevelStatistic\") {\n"
            + "  emit([doc.user_id,doc.levelset_number,doc.level_number], null);\n"
            + "   }\n"
            + "}");

    
    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private CouchbaseClient couchbaseClient;
     @Autowired
    private CouchbaseTemplate couchbaseTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    protected void initialize() {
        if (serverConfig.isConvertToCouchbase()) {
            convertStatistic();
            createViews();
        }
        //createRandomRecords();
    }

    private void createRandomRecords() {
        List<LevelStatistic> levelStatList = new LinkedList<>();
        Random rand = new Random();
        logger.info("Start recording");
        //for (int i = 0; i < 4000; i++) {
        // levelStatList.clear();
        for (long j = 0; j < 100000; j++) {
            LevelStatistic levelStatistic = new LevelStatistic();
            levelStatistic.setUniqueId(UUID.randomUUID().toString());
            levelStatistic.setUser_id(rand.nextInt(100000));
            levelStatistic.setLevelset_number(rand.nextInt(100));
            levelStatistic.setLevel_number(rand.nextInt(100));
            levelStatistic.setGame_type(GameType.DEFAULT);
            levelStatList.add(levelStatistic);
        }
        //logger.info("10000 records has been added");
        long startTime = System.currentTimeMillis();
        couchbaseTemplate.save(levelStatList);
        long endTime = System.currentTimeMillis() - startTime;

        // }
        logger.info("Couchase save time " + endTime);
        PreparedStatement insertUserFirstLevelStatistic = null;
        Connection con = null;
        int status = 0;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    logger.info("Savin SQL " + levelStatList.size());
                    insertUserFirstLevelStatistic = con.prepareStatement(SQLReq.StatDao.INSERT);
                    for (LevelStatistic stat : levelStatList) {
                        insertUserFirstLevelStatistic.setInt(1, stat.getUser_id());
                        insertUserFirstLevelStatistic.setInt(2, stat.getLevel_number());
                        insertUserFirstLevelStatistic.setInt(3, stat.getLevelset_number());
                        insertUserFirstLevelStatistic.setString(4, stat.getGame_type().name());
                        insertUserFirstLevelStatistic.addBatch();
                    }
                    startTime = System.currentTimeMillis();
                    insertUserFirstLevelStatistic.executeBatch();
                    endTime = System.currentTimeMillis() - startTime;
                    logger.info("Couchase save time " + endTime);
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
    }

    private void convertStatistic() {
        List<LevelStatistic> levelStatList = new LinkedList<>();
        long statCount = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM statistic_levels");
        long iterCount = Math.round(statCount / 10000) + 1;
        long recordCount = 0;
        for (int i = 0; i < iterCount; i++) {
            levelStatList.clear();
            SqlRowSet rs = jdbcTemplate.queryForRowSet("SELECT statistic_levels.* FROM statistic_levels LIMIT 10000 OFFSET " + recordCount);
            while (rs.next()) {
                int levelSetNumber = rs.getInt("levelset_number");
                int level_number = rs.getInt("level_number");
                LevelStatistic levelStat = new LevelStatistic();
                levelStat.setUniqueId(UUID.randomUUID().toString());
                levelStat.setUser_id(rs.getInt("user_id"));
                levelStat.setLevelset_number(levelSetNumber);
                levelStat.setLevel_number(level_number);
                levelStat.setStars(rs.getInt("stars"));
                levelStat.setScore(rs.getInt("score"));
                levelStat.setGame_type(GameType.valueOf(rs.getString("game_type")));
                levelStatList.add(levelStat);
            }
            couchbaseTemplate.save(levelStatList);
            recordCount += levelStatList.size();
        }
        logger.info("{} records has been converted", recordCount);
    }

    private void createViews() {
        DesignDocument designDoc = new DesignDocument("levelStatistic");
        for (int i = 0; i < viewNames.length; i++) {
            ViewDesign viewDesign = new ViewDesign(viewNames[i], viewCode[i]);
            designDoc.getViews().add(viewDesign);
        }
        couchbaseClient.createDesignDoc(designDoc);
    }
*/
}
