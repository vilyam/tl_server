package com.c17.yyh.mvc.message.outbound.login;

import java.util.List;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.db.entities.friends.FriendBonusItems;
import com.c17.yyh.db.entities.purchasing.store.PriceItem;
import com.c17.yyh.db.entities.purchasing.store.StoreItem;

public class LoginResponse extends UserLoadResponse {

    public class Store {

        private List<StoreItem> items;
        private List<PriceItem> prices;

        public List<StoreItem> getItems() {
            return items;
        }

        public void setItems(List<StoreItem> items) {
            this.items = items;
        }

        public List<PriceItem> getPrices() {
            return prices;
        }

        public void setPrices(List<PriceItem> list) {
            this.prices = list;
        }
    }

    public LoginResponse() {
        super("/login");
    }

    private Store store = new Store();

    private ServerConfig serverConfig;

    private Object[] levelSets;
    private Object[] fencingLevelsets;
    private Object[] allPets;
    private Object[] allCollections;
    private Object[] allBosses;
    private Object[] toolTypes;
    private FriendBonusItems friendsBonuses;

    private long server_time;

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public Object[] getFencingLevelsets() {
        return fencingLevelsets;
    }

    public void setFencingLevelsets(Object[] fencingLevelsets) {
        this.fencingLevelsets = fencingLevelsets;
    }

    public Object[] getAllPets() {
        return allPets;
    }

    public void setAllPets(Object[] allPets) {
        this.allPets = allPets;
    }

    public Object[] getLevelSets() {
        return levelSets;
    }

    public void setLevelSets(Object[] levelSets) {
        this.levelSets = levelSets;
    }

    public Object[] getAllBosses() {
        return allBosses;
    }

    public void setAllBosses(Object[] allBosses) {
        this.allBosses = allBosses;
    }

    public Object[] getAllCollections() {
        return allCollections;
    }

    public void setAllCollections(Object[] allCollections) {
        this.allCollections = allCollections;
    }

    public Object[] getToolTypes() {
        return toolTypes;
    }

    public void setToolTypes(Object[] toolTypes) {
        this.toolTypes = toolTypes;
    }

    public long getServer_time() {
        return server_time;
    }

    public void setServer_time(long server_time) {
        this.server_time = server_time;
    }

    public Store getStore() {
        return store;
    }

    public void setStorePrices(List<PriceItem> list) {
        this.store.setPrices(list);
    }

    public void setStoreItems(List<StoreItem> list) {
        this.store.setItems(list);
    }

    public FriendBonusItems getFriendsBonuses() {
        return friendsBonuses;
    }

    public void setFriendsBonuses(FriendBonusItems friendsBonuses) {
        this.friendsBonuses = friendsBonuses;
    }

}
