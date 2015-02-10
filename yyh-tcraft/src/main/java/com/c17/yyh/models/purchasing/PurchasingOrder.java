package com.c17.yyh.models.purchasing;

import java.sql.Timestamp;
import java.util.List;

import com.c17.yyh.db.entities.purchasing.ComplexItem;
import com.c17.yyh.db.entities.purchasing.GameItemPrice;
import com.c17.yyh.db.entities.purchasing.stocks.StockItem;
import com.c17.yyh.db.entities.purchasing.store.PriceItem;
import com.c17.yyh.type.StoreResourceType;

public class PurchasingOrder {
    public PurchasingOrder() {
    }

    public PurchasingOrder(int userId, PriceItem priceItem) {
        this.user_id = userId;
        this.source_id = priceItem.getId();
        this.source = "store";
        this.price = priceItem.getPrice();
        this.currency = priceItem.getCurrency().name();

        StringBuilder sbValues = new StringBuilder();
        StringBuilder sbResources = new StringBuilder();
        List<ComplexItem> items = priceItem.getItems();

        for (ComplexItem cItem : items) {
            StoreResourceType res = cItem.getStoreItem().getResource();
            sbResources.append(res.name());
            if (res.equals(StoreResourceType.TOOL)) {
                sbResources.append(" ").append(cItem.getInner_id());
            }
            sbResources.append(", ");
            sbValues.append(cItem.getValue()).append(", ");
        }

        this.resources = sbResources.substring(0, sbResources.length()-2);
        this.values = sbValues.substring(0, sbValues.length()-2);

        sbValues = null;
        sbResources = null;
        items = null;
    }

    public PurchasingOrder(int userId,  StockItem item) {
        this.user_id = userId;
        this.price = item.getPrice();
        this.currency = item.getCurrency().name();
        this.source = "stock";
        this.source_id = item.getId();
        
        StringBuilder sbValues = new StringBuilder();
        StringBuilder sbResources = new StringBuilder();
        List<ComplexItem> items = item.getItems();

        for (ComplexItem cItem : items) {
            StoreResourceType res = cItem.getStoreItem().getResource();
            sbResources.append(res.name());
            if (res.equals(StoreResourceType.TOOL)) {
                sbResources.append(" ").append(cItem.getInner_id());
            }
            sbResources.append(", ");
            sbValues.append(cItem.getValue()).append(", ");
        }

        this.resources = sbResources.substring(0, sbResources.length()-2);
        this.values = sbValues.substring(0, sbValues.length()-2);

        sbValues = null;
        sbResources = null;
        items = null;
    }

    public PurchasingOrder(int userId, GameItemPrice priceItem, int gameItemId, String gameItem) {
        this.user_id = userId;
        this.source_id = priceItem.getId();
        this.source = "controller";
        this.price = priceItem.getValue();
        this.currency = priceItem.getCurrency().name();

        this.resources = gameItem + " " + gameItemId;
        this.values = String.valueOf(priceItem.getCount());
    }

    public PurchasingOrder(int userId, GameItemPrice priceItem, int gameItemId, String gameItem, Integer level_number, Integer levelset_number) {
        this(userId, priceItem, gameItemId, gameItem);
        this.level_number = level_number;
        this.levelset_number = levelset_number;
    }
    
    public PurchasingOrder(int userId, PriceItem priceItem, Integer level_number, Integer levelset_number) {
        this(userId, priceItem);
        this.level_number = level_number;
        this.levelset_number = levelset_number;
    }

    public PurchasingOrder(int userId, StockItem item, Integer level_number, Integer levelset_number) {
        this(userId, item);
        this.level_number = level_number;
        this.levelset_number = levelset_number;
    }

    private int       order_id;
    private int       user_id;
    private String source;
    private int source_id;
    private Timestamp date;
    private int       price;
    private String    currency;
    private String    resources;
    private String    values;
    private int   level_number;
    private int   levelset_number;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public int getSource_id() {
        return source_id;
    }

    public void setSource_id(int source_id) {
        this.source_id = source_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getLevelset_number() {
        return levelset_number;
    }

    public void setLevelset_number(int levelset_number) {
        this.levelset_number = levelset_number;
    }

    public int getLevel_number() {
        return level_number;
    }

    public void setLevel_number(int level_number) {
        this.level_number = level_number;
    }
}
