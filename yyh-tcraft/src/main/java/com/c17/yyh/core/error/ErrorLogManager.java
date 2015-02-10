package com.c17.yyh.core.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErrorLogManager {
	
	@Autowired
	private IErrorLogDao errorLogService;
	
	public int log_error(String user_sn_id, String browser, String flash, String error_msg) {
		return errorLogService.log_error(user_sn_id, browser, flash, error_msg);
	}
}
