package com.c17.yyh.db.entities.purchasing;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.c17.yyh.db.entities.purchasing.store.StoreItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComplexItem {
    @XmlAttribute(name = "store_item")
    private int store_item;
    @XmlAttribute(name = "inner_id")
    private Integer inner_id;
    @XmlAttribute(name = "value")
    private int value;
    @JsonIgnore
    private StoreItem storeItem;
    public int getStore_item() {
        return store_item;
    }
    public void setStore_item(int store_item) {
        this.store_item = store_item;
    }
    public Integer getInner_id() {
        return inner_id;
    }
    public void setInner_id(Integer inner_id) {
        this.inner_id = inner_id;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public StoreItem getStoreItem() {
        return storeItem;
    }
    public void setStoreItem(StoreItem storeItem) {
        this.storeItem = storeItem;
    }
}
