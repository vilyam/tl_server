package com.c17.yyh.db.entities.friends;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "min")
@XmlAccessorType(XmlAccessType.FIELD)
public class FriendBonusMin {
    @XmlAttribute
    private int level;
    @XmlAttribute
    private int levelset;
    
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevelset() {
        return levelset;
    }
    public void setLevelset(int levelset) {
        this.levelset = levelset;
    }
}
