package com.c17.yyh.models.friends;

import java.util.List;

import com.c17.yyh.db.entities.friends.FriendBonusItem;
import com.c17.yyh.db.entities.purchasing.ComplexItem;

public class FriendBonusItemWrapper {
    private String referal;//for response
    private int id;
    private List<ComplexItem> items;

    public FriendBonusItemWrapper(String referal, int id,
            List<ComplexItem> items) {
        super();
        this.referal = referal;
        this.id = id;
        this.items = items;
    }

    public FriendBonusItemWrapper(String referal, FriendBonusItem bonus) {
        super();
        this.referal = referal;
        this.id = bonus.getId();
        this.items = bonus.getItems();
    }
    
    public List<ComplexItem> getItems() {
        return items;
    }

    public void setItems(List<ComplexItem> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReferal() {
        return referal;
    }

    public void setReferal(String referal) {
        this.referal = referal;
    }
}
