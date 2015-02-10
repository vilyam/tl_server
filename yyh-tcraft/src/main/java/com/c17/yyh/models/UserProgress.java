package com.c17.yyh.models;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserProgress implements Serializable{

    private static final long serialVersionUID = -2641270641014629388L;
    
    private int levelSetNumber;
    private int levelNumber;
    private Timestamp date;

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

    @Override
    public String toString() {
        return "UserProgress{" + "levelSetNumber=" + levelSetNumber + ", levelNumber=" + levelNumber + '}';
    }

}
