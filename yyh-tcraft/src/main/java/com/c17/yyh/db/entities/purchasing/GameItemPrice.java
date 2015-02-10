package com.c17.yyh.db.entities.purchasing;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import com.c17.yyh.type.CurrencyType;

@XmlAccessorType(XmlAccessType.FIELD)
public class GameItemPrice implements Serializable{
    private static final long serialVersionUID = -646699754411022217L;
    
    @XmlAttribute
    private int id = 0;
    @XmlAttribute
    private int count = 1;
    @XmlAttribute
    private int value;
    @XmlAttribute
    private CurrencyType currency;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public CurrencyType getCurrency() {
        return currency;
    }
    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }
}
