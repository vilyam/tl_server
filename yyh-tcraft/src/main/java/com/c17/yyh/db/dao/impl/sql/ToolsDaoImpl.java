package com.c17.yyh.db.dao.impl.sql;

import com.c17.yyh.db.dao.IToolsDao;
import com.c17.yyh.exceptions.ServerException;
import com.c17.yyh.models.tool.Tool;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.server.SQLReq;
import com.c17.yyh.util.NamedParameterStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("toolsServiceSql")
public class ToolsDaoImpl extends IToolsDao {

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int updateUserTool(int userId, Tool tool) {
        Connection con = null;
        NamedParameterStatement nps = null;
        try {
            con = dataSource.getConnection();
            nps = new NamedParameterStatement(con, SQLReq.TOOLS_UPDATE_OR_INSERT);
            nps.setInt("uId", userId);
            nps.setInt("toolId", tool.getId());
            nps.setInt("count", tool.getCount());
            nps.setTimestamp("last_using", new Timestamp(tool.getLastUsingTool()));
            return nps.executeUpdate();
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        } finally {
            closeQuietly(con,nps,null);
        }
    }

    @Override
    public int increaseUserTool(int userId, int toolId, int deltaValue) {
        Connection con = null;
        NamedParameterStatement nps = null;
        try {
            con = dataSource.getConnection();
            nps = new NamedParameterStatement(con, SQLReq.TOOLS_INCREMENT);
            nps.setInt("uId", userId);
            nps.setInt("toolId", toolId);
            nps.setInt("count", deltaValue);
            return nps.executeUpdate();
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        } finally {
            closeQuietly(con,nps,null);
        }
    }

    @Override
    public int[] addToolsBatch(int userId, List<Tool> tools) {
        Connection con = null;
        NamedParameterStatement nps = null;
        try {
            con = dataSource.getConnection();
            con.setAutoCommit(false);
            nps = new NamedParameterStatement(con, SQLReq.TOOLS_INCREMENT);
            
            for (Tool tool : tools) {
                nps.setInt("uId", userId);
                nps.setInt("toolId", tool.getId());
                nps.setInt("count", tool.getCount());
                nps.addBatch();
            }
            int[] status = nps.executeBatch();
            con.commit();
            return status;
        } catch (SQLException e) {
            try {
                con.rollback();
                LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
                throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
            } 
            catch (SQLException e1) {
                LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
                throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
            }
        } finally {
            try {con.setAutoCommit(true);} 
            catch (SQLException e) {
                LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
                throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
            }
            closeQuietly(con,nps,null);
        }
    }

    public static void closeQuietly(Connection connection, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException logOrIgnore) {
                LoggerFactory.getLogger(Thread.currentThread().getName()).error(logOrIgnore.toString());
                throw new ServerException(ErrorCodes.TEMP_DB_ERROR, logOrIgnore.toString(), true);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException logOrIgnore) {
                LoggerFactory.getLogger(Thread.currentThread().getName()).error(logOrIgnore.toString());
                throw new ServerException(ErrorCodes.TEMP_DB_ERROR, logOrIgnore.toString(), true);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException logOrIgnore) {
                LoggerFactory.getLogger(Thread.currentThread().getName()).error(logOrIgnore.toString());
                throw new ServerException(ErrorCodes.TEMP_DB_ERROR, logOrIgnore.toString(), true);
            }
        }
    }
}
