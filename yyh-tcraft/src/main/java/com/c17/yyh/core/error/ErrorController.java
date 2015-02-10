package com.c17.yyh.core.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.c17.yyh.server.message.BaseMessage;

@Component
public class ErrorController {
	
	@Autowired
	private ErrorLogManager errorLogManager;

	public BaseMessage process(ErrorLogRequest msg) {
		errorLogManager.log_error(msg.getSnId(), msg.getBrowser(), msg.getFlash_version(), msg.getError_msg());
		return new ErrorLogResponse();
	}
}
