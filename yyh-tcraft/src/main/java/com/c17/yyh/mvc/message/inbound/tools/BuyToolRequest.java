package com.c17.yyh.mvc.message.inbound.tools;

import com.c17.yyh.server.message.BaseInboundMessage;

public class BuyToolRequest extends BaseInboundMessage{
    private int id;
    private int price_id;
    private int level_number;
    private int levelset_number;

    public int getPrice_id() {
        return price_id;
    }
    public void setPrice_id(int price_id) {
        this.price_id = price_id;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
}
