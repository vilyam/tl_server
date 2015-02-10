package com.c17.yyh.db.dao;

import java.util.List;

import com.c17.yyh.db.entities.adventure.Boss;
import com.c17.yyh.db.entities.adventure.Pet;
import com.c17.yyh.db.entities.adventure.treasure.Collection;
import com.c17.yyh.db.entities.adventure.treasure.Treasure;
import com.c17.yyh.db.entities.adventure.LevelSet;
import com.c17.yyh.db.entities.friends.FriendBonusItem;
import com.c17.yyh.db.entities.friends.FriendBonusItems;
import com.c17.yyh.db.entities.purchasing.stocks.StockItem;
import com.c17.yyh.db.entities.purchasing.store.PriceItem;

public interface IConfigDao {

    List<Pet> getPets();

    List<Treasure> getTreasures();

    List<Collection> getCollections();

    List<Boss> getBosses();

    List<LevelSet> getAdventureLevelSets();

    void saveBosses(List<Boss> bosses);

    void saveTreasures(List<Treasure> treasures);

    void saveCollections(List<Collection> collections);

    List<com.c17.yyh.db.entities.purchasing.store.StoreItem> getStoreItemsNew();

	List<PriceItem> getStorePrices();

	List<StockItem> getStockItems();

    FriendBonusItems getFriendBonusItems();
}
