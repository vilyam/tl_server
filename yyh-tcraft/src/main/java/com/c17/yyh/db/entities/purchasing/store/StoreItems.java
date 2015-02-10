/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.c17.yyh.db.entities.purchasing.store;

import com.c17.yyh.server.EntitiesList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class StoreItems implements EntitiesList<StoreItem>{

    @XmlElement(name = "item")
    private List<StoreItem> storeItems = null;

    @Override
    public List<StoreItem> getList() {
        return storeItems;
    }

    @Override
    public void setList(List<StoreItem> storeItems) {
        this.storeItems = storeItems;
    }

}
