package com.c17.yyh.db.dao.impl.xml;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.constants.Constants;
import com.c17.yyh.db.dao.IConfigDao;
import com.c17.yyh.db.entities.adventure.Boss;
import com.c17.yyh.db.entities.adventure.Bosses;
import com.c17.yyh.db.entities.adventure.Level;
import com.c17.yyh.db.entities.adventure.Pet;
import com.c17.yyh.db.entities.adventure.Pets;
import com.c17.yyh.db.entities.adventure.treasure.Collection;
import com.c17.yyh.db.entities.adventure.treasure.Treasure;
import com.c17.yyh.db.entities.adventure.treasure.Treasures;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.util.FileHelper;
import com.c17.yyh.db.entities.adventure.LevelSet;
import com.c17.yyh.db.entities.friends.FriendBonusItems;
import com.c17.yyh.db.entities.purchasing.stocks.StockItem;
import com.c17.yyh.db.entities.purchasing.stocks.StockItems;
import com.c17.yyh.db.entities.purchasing.store.PriceItem;
import com.c17.yyh.db.entities.purchasing.store.PriceItems;
import com.c17.yyh.exceptions.ServerException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigDaoImpl implements IConfigDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private XMLConfigLoader xmlConfigLoader;

    private String advConfPath = null;

    @Override
    public List<LevelSet> getAdventureLevelSets() {
        List<LevelSet> levelSets = new ArrayList<>();
        String dirPath = getAdventureConfigPath();
        File[] allFiles = null;
        List<File> setFiles = null;

        JAXBContext jc;
        Unmarshaller u;

        try {
            allFiles = FileHelper.fileFinder(dirPath, "xml");
        } catch (FileNotFoundException e1) {
            logger.error("Error loading adventure level sets: directory does not exists (\"" + dirPath + "\")");
        }

        if (allFiles != null) {
            setFiles = new ArrayList<>();

            for (File file : allFiles) {
                if (file.getName().contains("set")) {
                    setFiles.add(file);
                }
            }

            if (setFiles.isEmpty()) {
                logger.error("Error loading adventure level sets: there is no levelset's files in directory");
            }

            if (setFiles.size() > 0) {
                try {
                    jc = JAXBContext.newInstance(LevelSet.class);
                    u = jc.createUnmarshaller();
                    for (File file : setFiles) {
                        LevelSet levelSet = (LevelSet) u.unmarshal(file);
                        levelSet.setName(file.getName().substring(0, file.getName().indexOf(".")));
                        levelSets.add(levelSet);
                        for (Level level : levelSet.getLevels()) {
                            level.setAdventureConfigPath(getAdventureConfigPath());
                            level.setLevelSetDirPath(levelSet.getName());
                            level.setLevelset_number(levelSet.getNumber());
                        }
                    }
                } catch (JAXBException e) {
                    logger.error("Error loading adventure level sets: exception on unmarshalling adventure levelset from XML: " + e.getMessage());
                    throw new ServerException(ErrorCodes.PARSE_ERROR, e.toString(), true);
                }
            }
        }
        return levelSets;
    }

    @Override
    public List<com.c17.yyh.db.entities.purchasing.store.StoreItem> getStoreItemsNew() {
        return xmlConfigLoader.loadData(com.c17.yyh.db.entities.purchasing.store.StoreItems.class);
    }

    @Override
    public List<Pet> getPets() {
        return xmlConfigLoader.loadData(Pets.class);
    }

    @Override
    public void saveBosses(List<Boss> bosses) {
        xmlConfigLoader.saveData(bosses, Bosses.class);
    }

    @Override
    public void saveTreasures(List<Treasure> treasures) {
        xmlConfigLoader.saveData(treasures, Treasures.class);
    }

    @Override
    public List<Treasure> getTreasures() {
        return xmlConfigLoader.loadData(Treasures.class);
    }

    @Override
    public List<Collection> getCollections() {
        return xmlConfigLoader.loadData(com.c17.yyh.db.entities.adventure.treasure.Collections.class);
    }

    @Override
    public void saveCollections(List<Collection> collections) {
        xmlConfigLoader.saveData(collections, com.c17.yyh.db.entities.adventure.treasure.Collections.class);
    }

    @Override
    public List<Boss> getBosses() {
        return xmlConfigLoader.loadData(Bosses.class);
    }

    @Override
    public List<PriceItem> getStorePrices() {
        return xmlConfigLoader.loadData(PriceItems.class);
    }

    @Override
    public List<StockItem> getStockItems() {
        return xmlConfigLoader.loadData(StockItems.class);
    }

    public String getAdventureConfigPath() {
        if (advConfPath != null) {
            return advConfPath;
        }

        advConfPath = new StringBuilder(serverConfig.getConfigPath()).append(serverConfig.adventureConfigDirName).append(Constants.separator).toString();
        return advConfPath;
    }

    @Override
    public FriendBonusItems getFriendBonusItems() {
        return xmlConfigLoader.loadEntityData(FriendBonusItems.class);
    }

}
