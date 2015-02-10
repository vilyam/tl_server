package com.c17.yyh.mvc.message.inbound.friends;

import com.c17.yyh.server.message.BaseInboundMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: DRybak
 * Date: 8/14/13
 * Time: 11:39 AM
 */
public class FriendsLevelInfoRequest extends BaseInboundMessage {
    @JsonProperty("friends_sn_ids")
    private String[] friendsSnIds;

    @JsonProperty("level_set_number")
    int levelSetNumber;

    @JsonProperty("level_number")
    int levelNumber;

    public String[] getFriendsSnIds() {
        return friendsSnIds;
    }

    public void setFriendsSnIds(String[] friendsSnIds) {
        this.friendsSnIds = friendsSnIds;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public int getLevelSetNumber() {
        return levelSetNumber;
    }

    public void setLevelSetNumber(int levelSetNumber) {
        this.levelSetNumber = levelSetNumber;
    }
}
