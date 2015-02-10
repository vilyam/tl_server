package com.c17.yyh.db.entities.adventure.treasure;

import java.util.LinkedList;
import java.util.List;

import com.c17.yyh.db.entities.adventure.Award;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "collection")
@XmlAccessorType(XmlAccessType.FIELD)
public class Collection {

    private int id;
    private String name, description, type, award_string, treasures;
    @XmlTransient
    private List<Treasure> treasureCollection = new LinkedList<Treasure>();
    @XmlTransient
    private Award award;

    public List<Treasure> getTreasureCollection() {
        return treasureCollection;
    }

    public void setTreasureCollection(List<Treasure> treasureCollection) {
        this.treasureCollection = treasureCollection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTreasures() {
        return treasures;
    }

    public void setTreasures(String treasures) {
        this.treasures = treasures;
    }

    public void addTreasure(Treasure treasure) {
        if (treasureCollection == null) {
            treasureCollection = new LinkedList<Treasure>();
        }
        treasureCollection.add(treasure);
    }

    public String getAward_string() {
        return award_string;
    }

    public void setAward_string(String award_string) {
        this.award_string = award_string;
    }

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }
}
