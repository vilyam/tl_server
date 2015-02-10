package com.c17.yyh.mvc.message.outbound;

import com.c17.yyh.server.message.BaseOutboundMessage;

import java.util.Set;

public class SessionStartAdventureResponse extends BaseOutboundMessage {

    public SessionStartAdventureResponse() {
        super("/adventure/start");
    }

    private Set<String> availableAbilities;
    private String levelData;

    public Set<String> getAvailableAbilities() {
        return availableAbilities;
    }

    public void setAvailableAbilities(Set<String> availableAbilities) {
        this.availableAbilities = availableAbilities;
    }

    public String getLevelData() {
        return levelData;
    }

    public void setLevelData(String levelData) {
        this.levelData = levelData;
    }

}
