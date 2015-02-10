package com.c17.yyh.core.error;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.server.SQLReq;
import com.c17.yyh.exceptions.ServerException;

@Repository
public class ErrorLogDaoImpl implements IErrorLogDao {

	private DataSource dataSource;
	
	@Autowired
    public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
    }
	
	@Override
	public int log_error(String user_sn_id, String browser, String flash,
			String error_msg) {
		
		PreparedStatement insertErrorLogRequestsPrpStatement = null;
		Connection con = null;
		int result = 0;
		try {
			con = dataSource.getConnection();
			try {
				try {
					insertErrorLogRequestsPrpStatement = con.prepareStatement(SQLReq.INSERT_ERROR_LOG);

					insertErrorLogRequestsPrpStatement.setString(1, user_sn_id);
					insertErrorLogRequestsPrpStatement.setString(2, browser);
					insertErrorLogRequestsPrpStatement.setString(3, flash);
					insertErrorLogRequestsPrpStatement.setString(4, error_msg);
					
					result = insertErrorLogRequestsPrpStatement.executeUpdate();
				} finally {
					if (insertErrorLogRequestsPrpStatement != null)
						insertErrorLogRequestsPrpStatement.close();
				}
			} finally {
				con.close();
			}
		} catch (SQLException e) {
			LoggerFactory.getLogger(Thread.currentThread().getName()) .error(e.toString());
			throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
		}
		return result;
	}

}
