package com.c17.yyh.models.friends;

import com.c17.yyh.constants.Constants;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class FriendProgress {

    @JsonIgnore
    private int userId;
    private String snId;
    private int levelSetNumber;
    private int levelNumber;
    private Timestamp date;

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

    public int getLevelSetNumber() {
        return levelSetNumber;
    }

    public void setLevelSetNumber(int levelSetNumber) {
        this.levelSetNumber = levelSetNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public boolean isStuck() {
        return date != null && System.currentTimeMillis() - date.getTime() > Constants.STUCK_TIME;
    }

}
