package com.c17.yyh.db.entities.friends;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.c17.yyh.db.entities.purchasing.ComplexItem;

@XmlRootElement(name = "bonusitem")
@XmlAccessorType(XmlAccessType.FIELD)
public class FriendBonusItem {
    @XmlAttribute
    private int id;
    
    @XmlElement(name = "item")
    private List<ComplexItem> items;

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
}
