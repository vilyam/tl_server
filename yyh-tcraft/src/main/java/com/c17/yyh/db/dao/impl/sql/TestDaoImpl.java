package com.c17.yyh.db.dao.impl.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.c17.yyh.db.dao.ITestDao;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.exceptions.ServerException;

@Repository
public class TestDaoImpl implements ITestDao {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;

	@Autowired
    public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }	
	
	@Override
	public void put(long threadId, String param) {
		PreparedStatement testPrpStDel = null;
		PreparedStatement testPrpStIns = null;
		Connection con = null;
		try {
			con = dataSource.getConnection();
			try {
				try {
					testPrpStDel = con.prepareStatement("DELETE FROM test WHERE id = ?");
					testPrpStDel.setLong(1, Thread.currentThread().getId());
					testPrpStDel.executeUpdate();
					
					testPrpStIns = con.prepareStatement("INSERT INTO test (test_param, id) VALUES (?, ?)");
					testPrpStIns.setString(1, param);
					testPrpStIns.setLong(2, Thread.currentThread().getId());
					testPrpStIns.executeUpdate();
				} finally {
					if (testPrpStDel != null)
						testPrpStDel.close();
					if (testPrpStIns != null)
						testPrpStIns.close();
				}
			} finally {
				con.close();
			}
		} catch (SQLException e) {
			LoggerFactory.getLogger(Thread.currentThread().getName()) .error(e.toString());
			throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
		}
	}

	@Override
	public String get(long threadId) {
		PreparedStatement testPrpStSel = null;
		ResultSet rs = null;
		Connection con = null;
		String param = null;
		
		try {
			con = dataSource.getConnection();
			try {
				try {
					testPrpStSel = con.prepareStatement("SELECT test.* FROM test WHERE test.id = ?");
					testPrpStSel.setLong(1, Thread.currentThread().getId());
					rs = testPrpStSel.executeQuery();
					if(rs.next()) {
						param = rs.getString("test_param");
					}
				} finally {
					if (rs != null)
						rs.close();
					if (testPrpStSel != null)
						testPrpStSel.close();
				}
			} finally {
				con.close();
			}
		} catch (SQLException e) {
			LoggerFactory.getLogger(Thread.currentThread().getName()) .error(e.toString());
			throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
		}
		
		return param;
	}

	@Override
	public void put_1(long threadId, String param) {
		jdbcTemplate.update("DELETE FROM test WHERE id = " + threadId);
		jdbcTemplate.update("INSERT INTO test (test_param, id) VALUES ('" + param + "', " + threadId + ")");
	}

	@Override
	public String get_1(long threadId) {
		SqlRowSet set = jdbcTemplate.queryForRowSet("SELECT test.* FROM test WHERE test.id = " + threadId);
		if (set.next())
			return set.getString("test_param");
		else
			return "";
	}

}
