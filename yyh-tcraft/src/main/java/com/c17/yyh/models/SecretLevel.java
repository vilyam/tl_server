package com.c17.yyh.models;

import java.io.Serializable;

public class SecretLevel implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = -7783673075250626896L;

    public SecretLevel() {
		super();
	}
	public SecretLevel(int userId, int levelId, int levelSetId, int isPay) {
		super();
		this.userId = userId;
		this.levelId = levelId;
		this.levelSetId = levelSetId;
		this.isPay = isPay;
	}
	
	private int userId;
	private int levelId;
	private int levelSetId;
	private int isPay;

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	public int getLevelSetId() {
		return levelSetId;
	}
	public void setLevelSetId(int levelSetId) {
		this.levelSetId = levelSetId;
	}
	public int getIsPay() {
		return isPay;
	}
	public void setIsPay(int isPay) {
		this.isPay = isPay;
	}
}
