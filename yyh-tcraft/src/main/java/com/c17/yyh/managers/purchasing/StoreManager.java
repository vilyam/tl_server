package com.c17.yyh.managers.purchasing;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c17.yyh.db.dao.IConfigDao;
import com.c17.yyh.db.entities.purchasing.ComplexItem;
import com.c17.yyh.db.entities.purchasing.store.PriceItem;
import com.c17.yyh.db.entities.purchasing.store.StoreItem;

@Service
public class StoreManager {

    @Autowired
    private IConfigDao      configService;

    private Logger          logger = LoggerFactory.getLogger(getClass());

    private List<StoreItem> items;
    private List<PriceItem> prices;

    @PostConstruct
    protected void initialize() {
        loadItems();
    }

    public void loadItems() {
        items = configService.getStoreItemsNew();
        prices = configService.getStorePrices();

        for (PriceItem price : prices) {
            List<ComplexItem> items = price.getItems();
            for (ComplexItem complexItem : items) {
                complexItem.setStoreItem(getItem(complexItem.getStore_item()));
            }
        }

        logger.info("Loaded {} store items", items.size());
        logger.info("Loaded {} store prices", prices.size());
    }

    public List<StoreItem> getItems() {
        return items;
    }

    public List<PriceItem> getPrices() {
        return prices;
    }

    public StoreItem getItem(int id) {
        for (StoreItem item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public PriceItem getPrice(int id) {
        for (PriceItem price : prices) {
            if (price.getId() == id) {
                return price;
            }
        }
        return null;
    }
}
