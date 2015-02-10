package com.c17.yyh.mvc.message.inbound;

import java.sql.Timestamp;

import com.c17.yyh.server.message.BaseInboundMessage;

public class EndSessionAdventureRequest extends BaseInboundMessage {
	
	private int level_number;
	private int level_set_number;
	private int score = 0;
	private int treasure_collected = 0;
	private int stars = 0;
	private boolean additional_moves = false;

	private Timestamp time_start ;
	private Timestamp time_stop;
	private int update_max_level = 0;
	
	public int getUpdate_max_level() {
		return update_max_level;
	}
	public void setUpdate_max_level(int update_max_level) {
		this.update_max_level = update_max_level;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getLevel_number() {
		return level_number;
	}
	public void setLevel_number(int level_number) {
		this.level_number = level_number;
	}
	public int getLevel_set_number() {
		return level_set_number;
	}
	public void setLevel_set_number(int level_set_number) {
		this.level_set_number = level_set_number;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	public Timestamp getTime_start() {
		return time_start;
	}
	public void setTime_start(Timestamp time_start) {
		this.time_start = time_start;
	}
	public Timestamp getTime_stop() {
		return time_stop;
	}
	public void setTime_stop(Timestamp time_stop) {
		this.time_stop = time_stop;
	}
	public boolean isAdditional_moves() {
		return additional_moves;
	}
	public void setAdditional_moves(boolean additional_moves) {
		this.additional_moves = additional_moves;
	}
	public int getTreasure_collected() {
		return treasure_collected;
	}
	public void setTreasure_collected(int treasure_collected) {
		this.treasure_collected = treasure_collected;
	}
}
