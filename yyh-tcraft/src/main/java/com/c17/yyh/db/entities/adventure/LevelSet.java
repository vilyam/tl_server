package com.c17.yyh.db.entities.adventure;

import java.util.SortedSet;
import java.util.TreeSet;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "levelset", namespace = "")
public class LevelSet {

    private String name;
    private String description;
    private int number;
    private int fencing = 0;
    private String type;
    private String parent;
    private String world;

    @XmlElementWrapper(name = "levels")
    @XmlElement(name = "level")
    private SortedSet<Level> adventureLevels = new TreeSet<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }
    @XmlTransient
    private int[] money_prize;

    @JsonIgnore
    private String moneyPrizeString;

    public String getMoneyPrizeString() {
        return moneyPrizeString;
    }

    @XmlElement(name = "money_prize")
    public void setMoneyPrizeString(String moneyPrizeString) {
        this.moneyPrizeString = moneyPrizeString;
        if (moneyPrizeString != null) {
            String[] array = moneyPrizeString.split(",");
            this.money_prize = new int[3];
            for (int i = 0; i < array.length; i++) {
                this.money_prize[i] = Integer.parseInt(array[i].trim());
            }
        }
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

    public SortedSet<Level> getLevels() {
        return adventureLevels;
    }

    public void setLevels(SortedSet<Level> levels) {
        this.adventureLevels = levels;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Level getLevelById(int levelId) {
        for (Level lvl : adventureLevels) {
            if (lvl.getNumber() == levelId) {
                return lvl;
            }
        }
        return null;
    }

    public int getFencing() {
        return fencing;
    }

    public void setFencing(int fencing) {
        this.fencing = fencing;
    }

    public int[] getMoney_prize() {
        return money_prize;
    }

    public void setMoney_prize(int[] money_prize) {
        this.money_prize = money_prize;
    }
}
