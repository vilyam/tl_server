package com.c17.yyh.db.entities.purchasing.store;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.c17.yyh.type.StoreResourceType;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class StoreItem {

    @XmlAttribute
    private int id;
    @XmlAttribute
    private StoreResourceType resource;

    public StoreItem() {
    }

    public StoreItem(int id, StoreResourceType resource) {
        super();
        this.id = id;
        this.resource = resource;
    }

    public StoreResourceType getResource() {
        return resource;
    }

    public void setResource(StoreResourceType resource) {
        this.resource = resource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StoreItem other = (StoreItem) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StoreItem{" + "id=" + id + '}';
    }

}
