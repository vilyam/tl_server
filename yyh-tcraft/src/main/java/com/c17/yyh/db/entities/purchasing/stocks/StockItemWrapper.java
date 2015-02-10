package com.c17.yyh.db.entities.purchasing.stocks;

import com.c17.yyh.constants.Constants;
import com.c17.yyh.type.CurrencyType;

public class StockItemWrapper extends StockItem{

    public StockItemWrapper(StockItem item, long duration) {
        super();
        //this.getClass().getSuperclass().getFields();
        this.description = item.description;
        
        this.id = item.id;
        this.message = item.message;
        this.name = item.name;
        this.type = item.type;
        this.price = item.price;
        this.currency = item.currency;
        this.items = item.items;
        this.restriction = item.restriction;

        this.durationTime = duration;
        
        if (item.currency == CurrencyType.REAL) {
            this.paymentId = Constants.STOCK_PAYMENT_MAGIC_NUMBER + item.id;
        }
    }

    private Long durationTime;

    public Long getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Long durationTime) {
        this.durationTime = durationTime;
    }
}
