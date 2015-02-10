package com.c17.yyh.models;

import java.io.Serializable;

public class SocialQuest implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = 4663290211862206743L;
    private String state;
	private int isDone;
	
	public int getIsDone() {
		return isDone;
	}
	public void setIsDone(int isDone) {
		this.isDone = isDone;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
}
