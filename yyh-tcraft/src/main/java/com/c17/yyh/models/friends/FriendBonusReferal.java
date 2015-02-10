package com.c17.yyh.models.friends;

import com.c17.yyh.db.entities.friends.FriendBonusItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class FriendBonusReferal {
    private String snId;
    @JsonIgnore
    private int max_level_number;
    @JsonIgnore
    private int max_levelset_number;

    private int status;
    private FriendBonusItem bonus;
    
    public String getSnId() {
        return snId;
    }

    public void setSnId(String snId) {
        this.snId = snId;
    }

    public int getMax_levelset_number() {
        return max_levelset_number;
    }

    public void setMax_levelset_number(int max_levelset_number) {
        this.max_levelset_number = max_levelset_number;
    }

    public int getMax_level_number() {
        return max_level_number;
    }

    public void setMax_level_number(int max_level_number) {
        this.max_level_number = max_level_number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public FriendBonusItem getBonus() {
        return bonus;
    }

    public void setBonus(FriendBonusItem bonus) {
        this.bonus = bonus;
    }
}
