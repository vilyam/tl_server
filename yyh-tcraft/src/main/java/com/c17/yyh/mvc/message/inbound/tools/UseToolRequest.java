package com.c17.yyh.mvc.message.inbound.tools;

import com.c17.yyh.server.message.BaseInboundMessage;

public class UseToolRequest extends BaseInboundMessage {
    private int id;
    private int level_number;
    private int levelset_number;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    private int[] boostsIds = null;
	public int[] getBoostsIds() {
		return boostsIds;
	}
	public void setBoostsIds(int[] boostsIds) {
		this.boostsIds = boostsIds;
	}
    public int getLevelset_number() {
        return levelset_number;
    }
    public void setLevelset_number(int levelset_number) {
        this.levelset_number = levelset_number;
    }
    public int getLevel_number() {
        return level_number;
    }
    public void setLevel_number(int level_number) {
        this.level_number = level_number;
    }
}
