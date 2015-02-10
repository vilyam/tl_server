package com.c17.yyh.db.dao.impl.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.c17.yyh.db.dao.IFriendsDao;
import com.c17.yyh.exceptions.ServerException;
import com.c17.yyh.models.friends.FriendBonusReferal;
import com.c17.yyh.models.friends.FriendInfo;
import com.c17.yyh.models.friends.FriendProgress;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.server.SQLReq;

@Repository
public class FriendsDaoImpl implements IFriendsDao {

    private static final String GET_FRIENDS_PROGRESS_PREFIX = "SELECT u.user_sn_id, u.user_id, "
            + "uml.max_level_number, "
            + "uml.max_levelset_number, "
            + "uml.date "
            + "FROM user u join user_max_level uml on u.user_id = uml.user_id "
            + "WHERE u.user_sn_id in (";
   private static final String GET_FRIENDS_PROGRESS_SUFIX = ") GROUP BY u.user_id ORDER BY u.user_id";

   private static final String GET_FRIENDS_INFO_PREFIX = "SELECT u.user_id, u.user_sn_id, sl.score, sl.stars "
        + "FROM user u JOIN statistic_levels sl on sl.user_id = u.user_id "
        + "where u.user_sn_id in(";
   private static final String GET_FRIENDS_INFO_SUFIX = ") and sl.level_number = ? and sl.levelset_number= ? "
        + "GROUP BY u.user_id ORDER BY sl.score DESC";
	
	private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

	@Autowired
    public void setDataSource(DataSource dataSource) {
	    this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }		
	
	@Override
	public List <FriendInfo> getFriendsInfo(String[] arrayFriendsIds, int levelNumber, int levelSetNumber) {
        List<FriendInfo> friendsList = new LinkedList<FriendInfo>();
        int[] argTypes = new int[arrayFriendsIds.length + 2];
        int i = 0;

        StringBuilder sqlSb = new StringBuilder(GET_FRIENDS_INFO_PREFIX);
        for (@SuppressWarnings("unused") String uid : arrayFriendsIds) {
            sqlSb.append("?");
            sqlSb.append(", ");

            argTypes[i++] = java.sql.Types.VARCHAR;
        }
        argTypes[i++] = java.sql.Types.INTEGER;
        argTypes[i++] = java.sql.Types.INTEGER;

        sqlSb.deleteCharAt(sqlSb.length()-1).deleteCharAt(sqlSb.length()-1);
        sqlSb.append(GET_FRIENDS_INFO_SUFIX);

        List<String> args = new ArrayList<String>(Arrays.asList(arrayFriendsIds));
        args.add(String.valueOf(levelNumber));
        args.add(String.valueOf(levelSetNumber));
        
        SqlRowSet rws = jdbcTemplate.queryForRowSet(sqlSb.toString(), args.toArray(), argTypes);

		while(rws.next()) {
		    FriendInfo friendInfo = new FriendInfo();
		    friendInfo.setUserId(rws.getInt("user_id"));
		    friendInfo.setSnId(rws.getString("user_sn_id"));
            friendInfo.setStars(rws.getInt("stars"));
            friendInfo.setScore(rws.getInt("score"));

            friendsList.add(friendInfo);
        }

		return friendsList;
	}

    @Override
	public List<FriendProgress> getFriendsProgress(String[] arrayFriendsIds) {
		if (arrayFriendsIds.length == 0) {
			return Collections.emptyList();
		}

	    List <FriendProgress> friendsList = new LinkedList<FriendProgress>();
        int[] argTypes = new int[arrayFriendsIds.length];
        int i = 0;

        StringBuilder sqlSb = new StringBuilder(GET_FRIENDS_PROGRESS_PREFIX);
        for (@SuppressWarnings("unused") String uid : arrayFriendsIds) {
            sqlSb.append("?");
            sqlSb.append(", ");

            argTypes[i++] = java.sql.Types.VARCHAR;
        }

        sqlSb.deleteCharAt(sqlSb.length()-1).deleteCharAt(sqlSb.length()-1);
        sqlSb.append(GET_FRIENDS_PROGRESS_SUFIX);

        SqlRowSet rws = jdbcTemplate.queryForRowSet(sqlSb.toString(), (Object[])arrayFriendsIds, argTypes);

        while(rws.next()) {
            FriendProgress friendPr = new FriendProgress();
            friendPr.setUserId(rws.getInt("user_id"));
            friendPr.setSnId(rws.getString("user_sn_id"));
            friendPr.setLevelNumber(rws.getInt("max_level_number"));
            friendPr.setLevelSetNumber(rws.getInt("max_levelset_number"));
            friendPr.setDate(rws.getTimestamp("date"));
            friendsList.add(friendPr);
        }

        return friendsList;
	}

    @Override
    public int insertReferal(String from, String to) {
        PreparedStatement insertReferalPrpStatement = null;
        Connection con = null;
        int state = -1;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    insertReferalPrpStatement = con.prepareStatement(SQLReq.FRIENDS_BONUS_INSERT_REFERAL);
                    insertReferalPrpStatement.setString(1, to);
                    insertReferalPrpStatement.setString(2, from);
                    state = insertReferalPrpStatement.executeUpdate();
                } finally {
                    if (insertReferalPrpStatement != null) {
                        insertReferalPrpStatement.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return state;
    }

    @Override
    public int acceptReferal(String uid, String ref) {
        PreparedStatement acceptReferalPrpStatement = null;
        Connection con = null;
        int state = -1;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    acceptReferalPrpStatement = con.prepareStatement(SQLReq.FRIENDS_BONUS_ACCEPT_REFERAL);
                    acceptReferalPrpStatement.setString(1, uid);
                    acceptReferalPrpStatement.setString(2, ref);
                    state = acceptReferalPrpStatement.executeUpdate();
                } finally {
                    if (acceptReferalPrpStatement != null) {
                        acceptReferalPrpStatement.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return state;
    }
    
    @Override
    public List<FriendBonusReferal> getReferalFriends(String snId) {
        Connection con = null;
        List<FriendBonusReferal> friends = new ArrayList<FriendBonusReferal>();
        PreparedStatement getReferalFriendsStatement = null;
        ResultSet rs = null;

        try {
            con = dataSource.getConnection();
            try {
                getReferalFriendsStatement = con.prepareStatement(SQLReq.FRIENDS_BONUS_GET_LIST);
                getReferalFriendsStatement.setString(1, snId);
                rs = getReferalFriendsStatement.executeQuery();
    
                while (rs.next()) {
                    FriendBonusReferal ref = new FriendBonusReferal();
                    ref.setSnId(rs.getString("ref"));
                    ref.setMax_level_number(rs.getInt("max_level_number"));
                    ref.setMax_levelset_number(rs.getInt("max_levelset_number"));
                    ref.setStatus(rs.getInt("status"));
                    friends.add(ref);
                }
    
            } finally {
                closeQuietly(con, getReferalFriendsStatement, rs);
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return friends;
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
