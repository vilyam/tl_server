package com.c17.yyh.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RoadBlock implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = 5289613402469639952L;

    public RoadBlock() {
		super();
	}
	public RoadBlock(int userId, int levelSetId, List<String> friends, int isPay) {
		super();
		this.userId = userId;
		this.levelSetId = levelSetId;
		this.friends = friends;
		this.isPay = isPay;
	}
	private int userId;
	private int levelSetId;
	@JsonIgnore
	private String friendsString;
	private List<String> friends;
	private int isPay;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getLevelSetId() {
		return levelSetId;
	}
	public void setLevelSetId(int levelSetId) {
		this.levelSetId = levelSetId;
	}
	public String getFriendsString() {
		return friendsString;
	}
	public void setFriendsString(String friendsString) {
		this.friendsString = friendsString;
		tryParse(friendsString);
	}
	public int getIsPay() {
		return isPay;
	}
	public void setIsPay(int isPay) {
		this.isPay = isPay;
	}
	
	private void tryParse(String friendsString) {
		friendsString = friendsString.substring(friendsString.indexOf(",") + 1);
		if (friendsString.equalsIgnoreCase("HEAD")) {
			setFriends(new ArrayList<String>(3));
			return;
		}
		setFriends(new ArrayList<String>(Arrays.asList(friendsString.split(","))));
	}
	public List<String> getFriends() {
		return friends;
	}
	public void setFriends(List<String> friends) {
		this.friends = friends;
	}
	public boolean addToFriends(String from) {
		return friends.add(from);
	}
}
