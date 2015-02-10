package com.c17.yyh.db.entities.adventure;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.c17.yyh.db.entities.purchasing.GameItemPrice;

@XmlRootElement(name = "pet")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pet implements Serializable{
    private static final long serialVersionUID = 6089320380058767652L;
    @XmlAttribute
    private int id;
    @XmlAttribute
    private String name;
    @XmlElement
    private GameItemPrice price;

    public Pet() {
        super();
    }

    public Pet(int id) {
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameItemPrice getPrice() {
        return price;
    }

    public void setPrice(GameItemPrice price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        Pet pet;
        try {
            pet = (Pet) obj;
        } catch (Exception e) {
            return false;
        }

        if (this.id == pet.getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Pet{" + "id=" + id + ", name=" + name + '}';
    }

}
