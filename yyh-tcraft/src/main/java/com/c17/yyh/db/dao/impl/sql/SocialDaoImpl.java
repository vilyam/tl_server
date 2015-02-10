package com.c17.yyh.db.dao.impl.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.c17.yyh.db.dao.ISocialDao;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.server.SQLReq;
import com.c17.yyh.exceptions.ServerException;

@Repository("socialService")
public class SocialDaoImpl implements ISocialDao {

	private DataSource dataSource;

	@Autowired
    public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
    }	
	
	@Override
	public int updateState(int userId, String state) {
		PreparedStatement psUpdate = null;
		PreparedStatement psInsert = null;
		Connection con = null;
		int reqState = -1;
		try {
			con = dataSource.getConnection();
			try {
				try {
					psUpdate = con.prepareStatement(SQLReq.UPDATE_USER_SOCIAL_QUEST_STATE);
					psUpdate.setString(1, state);
					psUpdate.setInt(2, userId);
					reqState = psUpdate.executeUpdate();
				} finally {
					if (psUpdate != null)
						psUpdate.close();
				}
				if (reqState < 1) {
					try {
						psInsert = con.prepareStatement(SQLReq.INSERT_USER_SOCIAL_QUEST);
						psInsert.setInt(1, userId);
						psInsert.setString(2, state);
						reqState = psInsert.executeUpdate();
					} finally {
						if (psInsert != null)
							psInsert.close();
					}
				}
			} finally {
				con.close();
			}
		} catch (SQLException e) {
			LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
			throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
		}
		return reqState;
	}

	@Override
	public int getAward(int userId) {
		PreparedStatement ps = null;
		Connection con = null;
		int reqState = -1;
		try {
			con = dataSource.getConnection();
			try {
				try {
					ps = con.prepareStatement(SQLReq.UPDATE_USER_SOCIAL_QUEST_DONE);
					ps.setInt(1, 1);
					ps.setInt(2, userId);
					reqState = ps.executeUpdate();
				} finally {
					if (ps != null)
						ps.close();
				}
			} finally {
				con.close();
			}
		} catch (SQLException e) {
			LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
			throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
		}
		return reqState;
	}

}
