package com.c17.yyh.core.error;

public interface IErrorLogDao {
	public int log_error(String user_sn_id, String browser, String flash, String error_msg);
}
