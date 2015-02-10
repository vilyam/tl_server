package com.c17.yyh.db.dao.impl.sql;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.db.dao.IDumpDao;
import com.c17.yyh.models.User;
import com.c17.yyh.server.SQLReq;
import com.c17.yyh.type.GameType;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
public class DumpDaoImpl implements IDumpDao {

    private DataSource dataSourceDump;

    @Autowired
    ServerConfig serverConfig;

    private static String PREFIX = "dump_";
    private static String SUFIX_STATISTIC_TABLE = "statistic_levels";
    private static String SUFIX_LOGIN_TABLE = "login";
    private static String SUFIX_TOOL_TABLE = "tools";
    private static String TEMPLATE_STATISTIC_TABLE = PREFIX + SUFIX_STATISTIC_TABLE;
    private static String TEMPLATE_LOGIN_TABLE = PREFIX + SUFIX_LOGIN_TABLE;
    private static String TEMPLATE_TOOL_TABLE = PREFIX + SUFIX_TOOL_TABLE;
    
    private Map<String,String> tables = new HashMap<String, String>(3);
    
    private String statisticDbName = "";
    private String loginDbName = "";
    private String toolDbName = "";

    @Autowired
    public void setDataSourceDump(DataSource dataSourceDump) {
        this.dataSourceDump = dataSourceDump;
    }

    @PostConstruct
    protected void initialize() {
        tables.put(SUFIX_STATISTIC_TABLE, TEMPLATE_STATISTIC_TABLE);
        tables.put(SUFIX_LOGIN_TABLE, TEMPLATE_LOGIN_TABLE);
        tables.put(SUFIX_TOOL_TABLE, TEMPLATE_TOOL_TABLE);
        
        updateDbName();
    }

    public synchronized String getStatisticDbName() {
        return statisticDbName;
    }

    public synchronized void setStatisticDbName(String statisticDbName) {
        this.statisticDbName = statisticDbName;
    }

    public synchronized String getLoginDbName() {
        return loginDbName;
    }

    public synchronized void setLoginDbName(String loginDbName) {
        this.loginDbName = loginDbName;
    }

    public synchronized String getToolsDbName() {
        return toolDbName;
    }

    public synchronized void setToolsDbName(String toolsDbName) {
        this.toolDbName = toolsDbName;
    }

    @Override
    @Async
    public void writeStatisticDump(int user_id, int level_id, int level_set_id,
            int stars, int score, GameType game_type, int treasure_collected,
            int p_pet_collected, int p_money, boolean pd_additional_moves,
            Timestamp pd_time_start, Timestamp pd_time_stop) {

        if (serverConfig.isEnableDump()) {
            Connection con = null;
            PreparedStatement ps = null;

            String statPrpSt = SQLReq.Dump.getInsertStatistic(getStatisticDbName());

            try {
                con = dataSourceDump.getConnection();
                try {
                    try {
                        ps = con.prepareStatement(statPrpSt);
                        ps.setInt(1, user_id);
                        ps.setInt(2, level_id);
                        ps.setInt(3, level_set_id);
                        ps.setInt(4, stars);
                        ps.setInt(5, p_money);
                        ps.setInt(6, score);
                        ps.setString(7, game_type.name());
                        ps.setBoolean(8, pd_additional_moves);
                        ps.setTimestamp(9, pd_time_start);
                        ps.setTimestamp(10, pd_time_stop);
                        ps.setInt(11, treasure_collected);
                        ps.setInt(12, p_pet_collected);

                        ps.executeUpdate();
                    } finally {
                        if (ps != null) {
                            ps.close();
                        }
                    }
                } finally {
                    con.close();
                }
            } catch (Exception e) {
                LoggerFactory.getLogger(getStatisticDbName()).error(e.getMessage());
            }
        }
    }

    @Override
    @Async
    public void writeLoginDump(User user, int bDay) {
        if (serverConfig.isEnableDump()) {
            PreparedStatement dumpPs = null;
            Connection con = null;

            String statPrpSt = SQLReq.Dump.getInsertLogin(getLoginDbName());
            try {
                con = dataSourceDump.getConnection();
                try {
                    try {
                        dumpPs = con.prepareStatement(statPrpSt);
                        dumpPs.setString(1, user.getSnId());
                        dumpPs.setTimestamp(2, new Timestamp(user.getLastTimeDailyLogin()));
                        dumpPs.setInt(3, user.getMoney());
                        dumpPs.setInt(4, user.getCrystals());
                        dumpPs.setInt(5, user.getGameLives());
                        dumpPs.setInt(6, bDay);
                        dumpPs.executeUpdate();
                    } finally {
                        if (dumpPs != null) {
                            dumpPs.close();
                        }
                    }
                } finally {
                    con.close();
                }
            } catch (Exception e) {
                LoggerFactory.getLogger(getLoginDbName()).error(e.getMessage());
            }
        }
    }

    @Override
    @Async
    public void writeToolDump(User user, int tool_id, int level_number, int levelset_number) {
        if (serverConfig.isEnableDump()) {
            PreparedStatement dumpPs = null;
            Connection con = null;

            String statPrpSt = SQLReq.Dump.getInsertTool(getToolsDbName());
            try {
                con = dataSourceDump.getConnection();
                try {
                    try {
                        dumpPs = con.prepareStatement(statPrpSt);
                        dumpPs.setInt(1, user.getUserId());
                        dumpPs.setInt(2, tool_id);
                        dumpPs.setInt(3, level_number);
                        dumpPs.setInt(4, levelset_number);
                        dumpPs.executeUpdate();
                    } finally {
                        if (dumpPs != null) {
                            dumpPs.close();
                        }
                    }
                } finally {
                    con.close();
                }
            } catch (Exception e) {
                LoggerFactory.getLogger(getToolsDbName()).error(e.getMessage());
            }
        }
    }

    private String getTableName(String hardDbName) {
        if (hardDbName.contains(SUFIX_STATISTIC_TABLE))
            return getStatisticTableName();
        if (hardDbName.contains(SUFIX_LOGIN_TABLE))
            return getLoginTableName();
        if (hardDbName.contains(SUFIX_TOOL_TABLE))
            return getToolsTableName();
        return hardDbName;
    }


    private void setTableName(String hardDbName) {
        if (hardDbName.contains(SUFIX_STATISTIC_TABLE))
            setStatisticDbName(hardDbName);
        if (hardDbName.contains(SUFIX_LOGIN_TABLE))
            setLoginDbName(hardDbName);
        if (hardDbName.contains(SUFIX_TOOL_TABLE))
            setToolsDbName(hardDbName);
    }
    
    public String getLoginTableName() {
        Calendar cal = Calendar.getInstance();

        //calculate first day of week
        cal.add(Calendar.DAY_OF_WEEK,
                cal.getFirstDayOfWeek() - cal.get(Calendar.DAY_OF_WEEK));

        return TEMPLATE_LOGIN_TABLE + "_dt_"
                + cal.get(Calendar.YEAR) + "_"
                + (cal.get(Calendar.MONTH) + 1) + "_"
                + cal.get(Calendar.DAY_OF_MONTH);
    }
    
    public String getStatisticTableName() {
        return TEMPLATE_STATISTIC_TABLE + "_v_" + getLevelsetVersion();
    }
    
    public String getToolsTableName() {
        return TEMPLATE_TOOL_TABLE + "_v_" + getLevelsetVersion();
    }

    private String getLevelsetVersion() {
        return serverConfig.levelSetVersion.replace('.', '_');
    }
    private boolean createDbHard(String dbName) {
        Connection con = null;
        PreparedStatement createPs = null;

        int state = 0;
        boolean exist = false;

        String createSql = SQLReq.Dump.getCreateStatement(dbName);
        try {
            con = dataSourceDump.getConnection();
            try {
                createPs = con.prepareStatement(createSql);
                state = createPs.executeUpdate();
            } finally {
                if (createPs != null) {
                    createPs.close();
                }
            }
            DatabaseMetaData dbm = con.getMetaData();
            ResultSet resultSet = dbm.getTables(null, null, dbName, null);
            if (resultSet.next()) {
                exist = true;
            }

        } catch (Exception e) {
            LoggerFactory.getLogger(dbName + Thread.currentThread().getId()).error(e.getMessage());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
            }
        }

        if (state > 0 || exist) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Scheduled(cron = "0 0 4 ? * MON")
    public void updateDbName() {
        if (serverConfig.isEnableDump()) {
            try {
                for (String table : tables.values()) {
                    String dbTableName = getTableName(table);
                    if (createDbHard(dbTableName)) {
                        setTableName(dbTableName);
                        LoggerFactory.getLogger(this.getClass()).info("Created " + dbTableName);
                    }
                }
            } catch (Exception e) {
                LoggerFactory.getLogger(this.getClass()).error("can't create connection to dataSourceDump");
                serverConfig.setEnableDump(false);
            }
        }
    }

}
