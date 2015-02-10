package com.c17.yyh.models.tool;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.c17.yyh.db.entities.purchasing.GameItemPrice;

@XmlRootElement(name = "toolitem")
@XmlAccessorType(XmlAccessType.FIELD)
public class ToolItem {
    @XmlAttribute
    private int id;
    @XmlAttribute
    private String name;
    @XmlAttribute(name = "start_from_level")
    private int start_from_level;
    @XmlAttribute(name = "default_count")
    private int default_count;

    @XmlElement
    private ToolLoading loading = null;
    @XmlElement
    private List<GameItemPrice> price;
    
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

    public int getStart_from_level() {
        return start_from_level;
    }

    public void setStart_from_level(int start_from_level) {
        this.start_from_level = start_from_level;
    }

    public int getDefault_count() {
        return default_count;
    }

    public void setDefault_count(int default_count) {
        this.default_count = default_count;
    }

    public ToolLoading getLoading() {
        return loading;
    }

    public void setLoading(ToolLoading loading) {
        this.loading = loading;
    }

    public List<GameItemPrice> getPrice() {
        return price;
    }

    public void setPrice(List<GameItemPrice> price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.id;
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
        final ToolItem other = (ToolItem) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ToolItem{" + "id=" + id + ", name=" + name + '}';
    }

}
