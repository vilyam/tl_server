package com.c17.yyh.models;

import com.c17.yyh.db.entities.UserBase;
import com.c17.yyh.db.entities.adventure.Boss;
import com.c17.yyh.db.entities.adventure.Pet;
import com.c17.yyh.db.entities.adventure.treasure.Treasure;
import com.c17.yyh.models.friends.FriendInfo;
import com.c17.yyh.models.purchasing.Stock;
import com.c17.yyh.models.tool.Tool;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User extends UserBase implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9082881968800886343L;

    private int money;
    private int crystals;
    private int gameLives;
    private long timeRemaining = 0;
    private String settings;
    private List<Tool> tools;
    private List<Pet> pets;
    private List<Treasure> treasures;
    private List<WonCollection> wonCollections;
    private List<Boss> bosses;
    private List<RoadBlock> roadBloks;
    private List<SecretLevel> secretLevels;
    private List<String> referals;
    private SocialQuest socialQuest;
    private UserProgress progress;

    private List<LevelStatistic> levelsStatistic = new ArrayList<LevelStatistic>();

    private int isPayer;

    @JsonIgnore
    private int bonusDay;
    @JsonIgnore
    private int userId;
    @JsonIgnore
    private String snId;
    @JsonIgnore
    private long lastTimeDailyLogin;
    @JsonIgnore
    private boolean isDebug;
    @JsonIgnore
    private String firstName;
    @JsonIgnore
    private String lastName;
    @JsonIgnore
    private int sex;
    @JsonIgnore
    private String city;
    @JsonIgnore
    private String country;
    @JsonIgnore
    private String birthday;
    @JsonIgnore
    private int language;
    @JsonIgnore
    private String secretCode;
    @JsonIgnore
    private String hash;
    @JsonIgnore
    private String userPage;
    @JsonIgnore
    private long timerStartDecrementLives;
    @JsonIgnore
    private long lastTimeDailyBonusLogin;
    @JsonIgnore
    private long firstTimeDailyLogin;
    @JsonIgnore
    private int dailyBonusMarker;
    @JsonIgnore
    private List<Stock> stocks;

    public long getFirstTimeDailyLogin() {
        return firstTimeDailyLogin;
    }

    public void setFirstTimeDailyLogin(long firstTimeDailyLogin) {
        this.firstTimeDailyLogin = firstTimeDailyLogin;
    }

    public int getDailyBonusMarker() {
        return dailyBonusMarker;
    }

    public void setDailyBonusMarker(int dailyBonusMarker) {
        this.dailyBonusMarker = dailyBonusMarker;
    }

    public List<LevelStatistic> getLevelsStatistic() {
        return levelsStatistic;
    }

    public void setLevelsStatistic(List<LevelStatistic> levelsStatistic) {
        this.levelsStatistic = levelsStatistic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((snId == null) ? 0 : snId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        if (snId == null) {
            if (other.snId != null) {
                return false;
            }
        } else if (!snId.equals(other.snId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[snId=" + snId + "]";
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCrystals() {
        return crystals;
    }

    public void setCrystals(int crystals) {
        this.crystals = crystals;
    }

    @JsonIgnore
    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public void setDebug(String isDebug) {
        if (!isDebug.isEmpty() && (isDebug.equalsIgnoreCase("true")) || isDebug.equalsIgnoreCase("1")) {
            this.isDebug = true;
        }
    }

    public long getLastTimeDailyLogin() {
        return lastTimeDailyLogin;
    }

    @JsonIgnore
    public int getLastTimeDailyLoginDelta() {
        return (int) ((System.currentTimeMillis() - lastTimeDailyLogin) / 86400000);
    }

    public void setLastTimeDailyLogin(long timeLastDailyLogin) {
        this.lastTimeDailyLogin = timeLastDailyLogin;
    }

    public int getBonusDay() {
        return bonusDay;
    }

    public void setBonusDay(int bonusDay) {
        this.bonusDay = bonusDay;
    }

    public long getLastTimeDailyBonusLogin() {
        return lastTimeDailyBonusLogin;
    }

    public void setLastTimeDailyBonusLogin(long timeLastDailyBonusLogin) {
        this.lastTimeDailyBonusLogin = timeLastDailyBonusLogin;
    }

    @JsonIgnore
    public int getLastTimeDailyBonusDelta() {
        return (int) ((System.currentTimeMillis() - lastTimeDailyBonusLogin) / 86400000);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @JsonIgnore
    public FriendInfo getInfoInFriendFormat() {
        FriendInfo fr = new FriendInfo();

        fr.setSnId(this.snId);
        fr.setUserId(this.userId);
        return fr;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getGameLives() {
        return gameLives;
    }

    public void setGameLives(int gameLives) {
        this.gameLives = gameLives;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Treasure> getTreasures() {
        return treasures;
    }

    public void setTreasures(List<Treasure> treasures) {
        this.treasures = treasures;
    }

    public List<WonCollection> getWonCollections() {
        return wonCollections;
    }

    public void setWonCollections(List<WonCollection> wonCollections) {
        this.wonCollections = wonCollections;
    }

    public List<Boss> getBosses() {
        return bosses;
    }

    public void setBosses(List<Boss> bosses) {
        this.bosses = bosses;
    }

    public List<RoadBlock> getRoadBloks() {
        return roadBloks;
    }

    public void setRoadBloks(List<RoadBlock> roadBloks) {
        this.roadBloks = roadBloks;
    }

    public List<SecretLevel> getSecretLevels() {
        return secretLevels;
    }

    public void setSecretLevels(List<SecretLevel> secretLevels) {
        this.secretLevels = secretLevels;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(long timeDiffMod) {
        this.timeRemaining = timeDiffMod;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public long getTimerStartDecrementLives() {
        return timerStartDecrementLives;
    }

    public void setTimerStartDecrementLives(long timerStartDecrementLives) {
        this.timerStartDecrementLives = timerStartDecrementLives;
    }

    public String getUserPage() {
        return userPage;
    }

    public void setUserPage(String userPage) {
        this.userPage = userPage;
    }

    public int getIsPayer() {
        return isPayer;
    }

    public void setIsPayer(int isPayer) {
        this.isPayer = isPayer;
    }

    public SocialQuest getSocialQuest() {
        return socialQuest;
    }

    public void setSocialQuest(SocialQuest socialQuest) {
        this.socialQuest = socialQuest;
    }

    public UserProgress getProgress() {
        return progress;
    }

    public void setProgress(UserProgress progress) {
        this.progress = progress;
    }

	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}
	
	public Stock getStockById(int id) {
	    for (Stock stock : stocks) {
            if (stock.getId() == id)
                return stock;
        }
	    return null;
	}

    public List<String> getReferals() {
        return referals;
    }

    public void setReferals(List<String> referals) {
        this.referals = referals;
    }

}
