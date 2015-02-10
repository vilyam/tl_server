package com.c17.yyh.models;

import java.io.Serializable;
import java.util.UUID;

import com.c17.yyh.type.GameType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@JsonIgnoreProperties({"user_id", "id", "uniqueId", "levelSet", "level"})
@Document
public class LevelStatistic implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = -2950548597523287362L;

    @Id
    private String uniqueId;

    private int id;
    @Field
    private int user_id;
    @Field
    private int level_number;
    @Field
    private int levelset_number;
    @Field
    private int stars;
    @Field
    private int treasure_collected;
    @Field
    private int score;
    @Field
    private GameType game_type;

    public LevelStatistic(int user_id, int level_number, int levelset_number, 
    		int stars, int treasure_collected, int score, GameType game_type) {
    	this();
		this.user_id = user_id;
		this.level_number = level_number;
		this.levelset_number = levelset_number;
		this.stars = stars;
		this.treasure_collected = treasure_collected;
		this.score = score;
		this.game_type = game_type;
	}

	public LevelStatistic() {
		super();
		this.uniqueId = UUID.randomUUID().toString();
	}
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public int getLevel_number() {
        return level_number;
    }

    public void setLevel_number(int level_number) {
        this.level_number = level_number;
    }

    public int getLevelset_number() {
        return levelset_number;
    }

    public void setLevelset_number(int levelset_number) {
        this.levelset_number = levelset_number;
    }

    public GameType getGame_type() {
        return game_type;
    }

    public void setGame_type(GameType game_type) {
        this.game_type = game_type;
    }

    public int getTreasure_collected() {
        return treasure_collected;
    }

    public void setTreasure_collected(int treasure_collected) {
        this.treasure_collected = treasure_collected;
    }
}
