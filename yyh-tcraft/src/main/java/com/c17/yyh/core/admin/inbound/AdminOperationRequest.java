package com.c17.yyh.core.admin.inbound;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminOperationRequest extends BaseAdminMessage {

    @JsonProperty("command")
    private String command;
    
    private Map<String, String> data;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

	public Map<String, String> getData() {
		return data;
	}

	public void setData(Map<String, String> data) {
		this.data = data;
	}

}
