package com.c17.yyh.mvc.message.inbound;

import com.c17.yyh.server.message.BaseInboundMessage;

public class StartSessionAdventureRequest extends BaseInboundMessage {
	private int level;
	private int levelset;

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getLevelset() {
		return levelset;
	}
	public void setLevelset(int levelset) {
		this.levelset = levelset;
	}

}
