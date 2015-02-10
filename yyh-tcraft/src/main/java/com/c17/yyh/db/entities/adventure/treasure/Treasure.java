package com.c17.yyh.db.entities.adventure.treasure;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "treasure")
@XmlAccessorType(XmlAccessType.FIELD)
public class Treasure implements Serializable{
	/**
     * 
     */
    private static final long serialVersionUID = 5464066012143731954L;
    private int id;
	private String name, type;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		Treasure tr;
		try {
			tr = (Treasure) obj;
		} catch (Exception e) {
			return false;
		}

		if (this.id == tr.getId())
			return true;
		else
			return false;
	}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public String toString() {
        return "Treasure{" + "id=" + id + ", name=" + name + ", type=" + type + '}';
    }
    
    
}
