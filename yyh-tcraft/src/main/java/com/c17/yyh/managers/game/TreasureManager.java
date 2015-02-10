package com.c17.yyh.managers.game;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c17.yyh.db.dao.IConfigDao;
import com.c17.yyh.db.entities.adventure.treasure.Collection;
import com.c17.yyh.db.entities.adventure.treasure.Treasure;
import com.c17.yyh.db.entities.adventure.Award;
import com.c17.yyh.models.User;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.exceptions.ServerException;

@Service("treasureManager")
public class TreasureManager {

    @Autowired
    private IConfigDao configService;

    private ObjectMapper jsonMapper = new ObjectMapper();

    private List<Treasure> treasures;
    private List<Collection> collections;

    public List<Collection> getCollections() {
        return collections;
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    protected void initialize() {
        loadTreasures();
    }

    public void loadTreasures() {
        treasures = configService.getTreasures();
        collections = configService.getCollections();
        logger.info("Loaded {} treasures", treasures.size());
        logger.info("Loaded {} treasure collections", collections.size());
        fillCollection();
        parseAwards();
    }

    private void parseAwards() {

        for (Collection coll : collections) {
            try {
                JsonNode node = jsonMapper.readTree(coll.getAward_string());
                coll.setAward(jsonMapper.readValue(node, Award.class));
            } catch (JsonProcessingException e) {
                logger.warn("Can't parse collection award", e);
                throw new ServerException(ErrorCodes.PARSE_ERROR, "Can't parse collection award", true, e);
            } catch (IOException e) {
                logger.warn("IOException during parsing collection award", e);
                throw new ServerException(ErrorCodes.PARSE_ERROR, "IOException during parsing collection award", true, e);
            }
        }
    }

    public Treasure getTreasureById(int id) {
        for (Treasure treasure : treasures) {
            if (treasure.getId() == id) {
                return treasure;
            }
        }
        return null;
    }

    private void fillCollection() {
        String str;
        String[] arr;
        int treasureId;

        if (collections != null) {
            for (Collection collection : collections) {
                str = collection.getTreasures();
                arr = str.split(",");
                for (String element : arr) {
                    treasureId = Integer.parseInt(element);
                    Treasure treasure = getTreasureById(treasureId);
                    if (treasure != null) {
                        collection.addTreasure(treasure);
                    }
                }
            }
        }
    }

    public List<Collection> checkTreasureCollection(User user) {
        List<Collection> collections = new LinkedList<Collection>();
        List<Treasure> userTrList = user.getTreasures();

        for (Collection collection : getCollections()) {
            if (userTrList.containsAll(collection.getTreasureCollection())) {
                collections.add(collection);
            }
        }

        return collections;
    }

    public Collection getCollectionById(int collectionId) {
        for (Collection coll : collections) {
            if (coll.getId() == collectionId) {
                return coll;
            }
        }
        return null;
    }
}
