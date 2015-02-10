package com.c17.yyh.models.friends;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FriendInfo {
    @JsonIgnore
	private int userId;
	private String snId;
    private int stars;
    private int score;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getSnId() {
		return snId;
	}
	public void setSnId(String snId) {
		this.snId = snId;
	}
    public int getStars() {
        return stars;
    }
    public void setStars(int stars) {
        this.stars = stars;
    }
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
