package com.c17.yyh.db.entities.adventure;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "boss")
@XmlAccessorType(XmlAccessType.FIELD)
public class Boss implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1309733021571601418L;
    private int id;
    private String name;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        Boss boss;
        try {
            boss = (Boss) obj;
        } catch (Exception e) {
            return false;
        }

        if (this.id == boss.getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.id;
        return hash;
    }

    @Override
    public String toString() {
        return "Boss{" + "id=" + id + ", name=" + name + '}';
    }
    
    

}
