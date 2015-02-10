package com.c17.yyh.db;

import com.c17.yyh.db.dao.IConfigDao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.c17.yyh.db.entities.adventure.treasure.Collection;
import com.c17.yyh.db.entities.adventure.Boss;
import com.c17.yyh.db.entities.adventure.Pet;
import com.c17.yyh.db.entities.adventure.treasure.Treasure;
import com.c17.yyh.db.entities.adventure.LevelSet;
import com.c17.yyh.db.entities.friends.FriendBonusItems;
import com.c17.yyh.db.entities.purchasing.stocks.StockItem;
import com.c17.yyh.db.entities.purchasing.store.PriceItem;

import java.util.LinkedList;

@Component
public class ConfigDaoMock implements IConfigDao {

    @Override
    public List<LevelSet> getAdventureLevelSets() {
        List<LevelSet> lst = new LinkedList<LevelSet>();
        lst.add(new LevelSet());
        return lst;
    }

    @Override
    public List<Pet> getPets() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Treasure> getTreasures() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Collection> getCollections() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Boss> getBosses() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveBosses(List<Boss> bosses) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveTreasures(List<Treasure> treasures) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveCollections(List<Collection> collections) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<com.c17.yyh.db.entities.purchasing.store.StoreItem> getStoreItemsNew() {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public List<PriceItem> getStorePrices() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StockItem> getStockItems() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public FriendBonusItems getFriendBonusItems() {
        // TODO Auto-generated method stub
        return null;
    }

}
