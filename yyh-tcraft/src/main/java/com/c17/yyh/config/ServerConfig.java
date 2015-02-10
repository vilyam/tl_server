package com.c17.yyh.config;

import com.c17.yyh.constants.Constants;
import com.c17.yyh.core.social.type.SocialNetwork;
import com.c17.yyh.exceptions.ServerException;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.util.OSHelper;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ServerConfig {

    public class DailyBonusConfig {

        public int dayLimit;
        public int dayDuration;
        public int dayQuantity;
        public String dailyBonusConfigDirName;
    }

    @JsonIgnore
    private String SERVER_FULLNAME = null;

    @JsonIgnore
    CompositeConfiguration config;
    @JsonIgnore
    PropertiesConfiguration commonConfig;
    @JsonIgnore
    PropertiesConfiguration secureConfig;
    @JsonIgnore
    PropertiesConfiguration adventureConfig;

    @JsonIgnore
    private ServletContext servletContext;

    @JsonIgnore
    public DailyBonusConfig dailyBonus;

    @JsonIgnore
    public String serverVersion;

    @JsonIgnore
    public String levelSetVersion;

    @JsonIgnore
    public String adventureConfigDirName;

    @JsonIgnore
    public int roadblockUnlockDays;

    @JsonIgnore
    public String roadblockUnlockMessage;

    @JsonIgnore
    private String configRootPath;

    @JsonIgnore
    private boolean convertToCouchbase;

    @JsonIgnore
    public boolean useNoSql;

    @JsonIgnore
    public boolean useSql;

    @JsonIgnore
    private boolean enableDump;

    @JsonIgnore
    public SocialNetwork socialNetwork;

    @JsonIgnore
    private static String notificationServerAddress;

    @JsonIgnore
    public String socialSecretKey;

    @JsonIgnore
    public String socialPublicKey;

    @JsonIgnore
    public int socialAppId;

    @JsonIgnore
    public String socialRefreshToken;

    @JsonIgnore
    public int pigId;

    @JsonIgnore
    public boolean encriptionEnabled;

    @JsonIgnore
    public boolean userSecretkeyEnabled;

    @JsonIgnore
    public boolean userAuthSigEnabled;

    @JsonIgnore
    public int socialQuestAward;

    public int maxLivesCount;

    public int timeTillUpdateLive;

    public int countFriendsToOpenRoadBlock;

    public int timeToBlockGift;

    @JsonIgnore
    private StringBuilder configPath = null;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected void initialize(ServletContext servletContext) throws ConfigurationException {
        this.servletContext = servletContext;
        LoggerFactory.getLogger(getClass()).info("Initialize serverconfig");
        config = new CompositeConfiguration();
        commonConfig = new PropertiesConfiguration(getConfigRootPath() + "yyh.common.properties");
        secureConfig = new PropertiesConfiguration(getConfigRootPath() + "yyh.secure.properties");
        adventureConfig = new PropertiesConfiguration(getConfigRootPath() + "config/adventure/adventure.properties");
        config.addConfiguration(new SystemConfiguration());
        config.addConfiguration(commonConfig);
        config.addConfiguration(secureConfig);
        config.addConfiguration(adventureConfig);
        initParams();
    }

    private void initParams() {
        this.dailyBonus = new DailyBonusConfig();
        this.dailyBonus.dailyBonusConfigDirName = config.getString("daily.bonus.config.dir.name");
        this.dailyBonus.dayDuration = config.getInt("daily.bonus.day.duration");
        this.dailyBonus.dayLimit = config.getInt("daily.bonus.day.limit");
        this.dailyBonus.dayQuantity = config.getInt("daily.bonus.day.quantity");

        this.serverVersion = config.getString("server.version");
        this.adventureConfigDirName = config.getString("adventure.config.dir.name");
        this.configRootPath = config.getString("config.path");
        this.convertToCouchbase = config.getInt("server.dao.convertToCouchbase") > 0;
        this.useNoSql = config.getInt("server.dao.useNoSql") > 0;
        this.useSql = config.getInt("server.dao.useSql") > 0;
        this.enableDump = config.getInt("server.dao.enableDump") > 0;
        this.levelSetVersion = config.getString("adventure.levelset.version");
        this.socialNetwork = SocialNetwork.fromString(config.getString("config.appProfile"));
        this.socialSecretKey = config.getString("server.social.secretkey");
        this.socialPublicKey = config.getString("server.social.publickey");
        this.socialAppId = config.getInt("server.social.app.id");
        this.pigId = config.getInt("adventure.levelset.pig.id");
        this.maxLivesCount = config.getInt("adventure.live.max.count");
        this.timeTillUpdateLive = config.getInt("adventure.live.update.time");
        this.countFriendsToOpenRoadBlock = config.getInt("adventure.levelset.count.roadblock");
        this.encriptionEnabled = config.getInt("server.encription.enabled") > 0;
        this.userSecretkeyEnabled = config.getInt("server.user.secretkey.enabled") > 0;
        this.userAuthSigEnabled = config.getInt("server.user.authsig.enabled") > 0;
        this.socialQuestAward = config.getInt("social.quest.crystals");
        this.timeToBlockGift = config.getInt("gift.time.to.block");
        this.roadblockUnlockDays = config.getInt("adventure.roadblock.unlock.days");
        this.roadblockUnlockMessage = config.getString("adventure.roadblock.unlock.message");
        ServerConfig.notificationServerAddress = config.getString("server.notification.host");
    }

    @JsonIgnore
    public synchronized void reloadConfig() {
        try {
            commonConfig.refresh();
        } catch (ConfigurationException e) {
            logger.error(e.getMessage());
        }
        this.levelSetVersion = config.getString("adventure.levelset.version");
    }

    @JsonIgnore
    public int getTimeToBlockGift() {
        return timeToBlockGift;
    }

    public void setTimeToBlockGift(int timeToBlockGift) {
        this.timeToBlockGift = timeToBlockGift;
    }

    public void setSocialSecretKey(String socialSecretKey) {
        this.socialSecretKey = socialSecretKey;
    }

    public void setSocialAppId(int socialAppId) {
        this.socialAppId = socialAppId;
    }

    public int getRoadblockUnlockDays() {
        return roadblockUnlockDays;
    }

    public void setRoadblockUnlockDays(int roadblockUnlockDays) {
        this.roadblockUnlockDays = roadblockUnlockDays;
    }

    public String getRoadblockUnlockMessage() {
        return roadblockUnlockMessage;
    }

    public void setRoadblockUnlockMessage(String roadblockUnlockMessage) {
        this.roadblockUnlockMessage = roadblockUnlockMessage;
    }

    public static String getNotificationServerAddress() {
        return notificationServerAddress;
    }

    public static void setNotificationServerAddress(String notificationServerAddress) {
        ServerConfig.notificationServerAddress = notificationServerAddress;
    }

    @JsonIgnore
    public String getSocialRefreshToken() {
        return socialRefreshToken;
    }

    public void setSocialRefreshToken(String socialRefreshToken) {
        this.socialRefreshToken = socialRefreshToken;
    }

    public void setPigId(int pigId) {
        this.pigId = pigId;
    }

    public void setSocialPublicKey(String socialPublicKey) {
        this.socialPublicKey = socialPublicKey;
    }

    public void setMaxLivesCount(int maxLivesCount) {
        this.maxLivesCount = maxLivesCount;
    }

    public void setTimeTillUpdateLive(int timeTillUpdateLive) {
        this.timeTillUpdateLive = timeTillUpdateLive;
    }

    public void setCountFriendsToOpenRoadBlock(int countFriendsToOpenRoadBlock) {
        this.countFriendsToOpenRoadBlock = countFriendsToOpenRoadBlock;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public void setAdventureConfigDirName(String adventureConfigDirName) {
        this.adventureConfigDirName = adventureConfigDirName;
    }

    public void setLevelSetVersion(String levelSetVersion) {
        this.levelSetVersion = levelSetVersion;
    }

    @JsonIgnore
    public boolean isConvertToCouchbase() {
        return useNoSql && convertToCouchbase;
    }

    @JsonIgnore
    public boolean getEncriptionEnabled() {
        return encriptionEnabled;
    }

    public void setConvertToCouchbase(boolean convertToCouchbase) {
        this.convertToCouchbase = convertToCouchbase;
    }

    public boolean isEnableDump() {
        return enableDump;
    }

    public void setEnableDump(boolean enableDump) {
        this.enableDump = enableDump;
    }

    public void setEncriptionEnabled(boolean encriptionEnabled) {
        this.encriptionEnabled = encriptionEnabled;
    }

    public void setUserSecretkeyEnabled(boolean userSecretkeyEnabled) {
        this.userSecretkeyEnabled = userSecretkeyEnabled;
    }

    public void setUserAuthSigEnabled(boolean userAuthSigEnabled) {
        this.userAuthSigEnabled = userAuthSigEnabled;
    }

    public void setSocialQuestAward(int socialQuestAward) {
        this.socialQuestAward = socialQuestAward;
    }

    public void setUseNoSql(boolean useNoSql) {
        this.useNoSql = useNoSql;
    }

    public void setUseSql(boolean useSql) {
        this.useSql = useSql;
    }

    @JsonIgnore
    public String getServerVersion() {
        if (SERVER_FULLNAME != null) {
            return SERVER_FULLNAME;
        }
        if (OSHelper.isUnix()) {
            SERVER_FULLNAME = "linux";
        }
        if (OSHelper.isWindows()) {
            SERVER_FULLNAME = "windows";
        }
        SERVER_FULLNAME += " " + serverVersion;
        return SERVER_FULLNAME;
    }

    @JsonIgnore
    public String getConfigPath() {
        if (configPath == null) {
            configPath = new StringBuilder(getConfigRootPath()).append("config").append(Constants.separator);

            if (!new File(configPath.toString()).exists()) {
                LoggerFactory.getLogger(getClass()).error("{}: There is mistake in config dir path. It must be local dir name in WEB-INF or absolutely filesystem path", configRootPath);
                throw new ServerException(ErrorCodes.CONFIG_ERROR, "There is mistake in config dir path. It must be local dir name in WEB-INF or absolutely filesystem path.", true);
            }

        }
        return configPath.toString();
    }

    @JsonIgnore
    public String getConfigRootPath() {
        if (StringUtils.isEmpty(configRootPath)) {
            configRootPath = servletContext.getRealPath("/WEB-INF") + Constants.separator;
        }
        return configRootPath;
    }

}
