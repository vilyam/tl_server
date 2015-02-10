package com.c17.yyh.managers.game.adventure;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.db.entities.adventure.LevelSet;
import com.c17.yyh.db.entities.adventure.Level;
import com.c17.yyh.db.dao.IConfigDao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("adventureLevelManager")
public class AdventureLevelManager {

    public List<LevelSet> getLevelSets() {
        return levelSets;
    }

    private List<LevelSet> levelSets = null;
    private final List<Level> allLevels = new ArrayList<>();
    private String[] fencingLevelsets;

    @Autowired
    private IConfigDao configService;

    @Autowired
    private ServerConfig serverConfig;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    protected void initialize() {
        logger.debug("Initialize Adventure Level Manager");
        loadLevels();
    }

    public void loadLevels() {
        levelSets = configService.getAdventureLevelSets();
        logger.info("Loaded {} adventure levelsets", levelSets.size());
        for (LevelSet lvlSet : levelSets) {
            allLevels.addAll(lvlSet.getLevels());
        }

        List<String> set = new ArrayList<>();
        for (LevelSet lvlSet : levelSets) {
            if (lvlSet.getFencing() > 0) {
                set.add(lvlSet.getNumber() + ":" + lvlSet.getFencing());
            }
        }
        fencingLevelsets = set.toArray(new String[0]);
        serverConfig.reloadConfig();
    }

    //ok
    public LevelSet getLevelSetById(int levelSet_number) {
        for (LevelSet lvlSet : levelSets) {
            if (lvlSet.getNumber() == levelSet_number) {
                return lvlSet;
            }
        }
        return null;
    }

    public Level getLevelById(int levelId, int levelSetId) {
        LevelSet lvlSet = getLevelSetById(levelSetId);
        if (lvlSet != null) {
            return lvlSet.getLevelById(levelId);
        } else {
            return null;
        }
    }

    public List<Level> getAllLevels() {
        return allLevels;
    }

    public Object[] getlevelSetsWithFencing() {
        return fencingLevelsets;
    }

}
