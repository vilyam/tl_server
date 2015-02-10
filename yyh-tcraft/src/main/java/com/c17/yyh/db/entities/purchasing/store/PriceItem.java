package com.c17.yyh.db.entities.purchasing.store;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.c17.yyh.db.entities.purchasing.ComplexItem;
import com.c17.yyh.type.CurrencyType;

@XmlRootElement(name = "price")
@XmlAccessorType(XmlAccessType.FIELD)
public class PriceItem {
	
	@XmlAttribute
	private int id;
	
	@XmlAttribute
	private String type;
	
	@XmlAttribute
	private String index;
	
    @XmlElement(name = "item")
    protected List<ComplexItem> items;
	
    @XmlAttribute
	private int price;
	
	@XmlAttribute
	private CurrencyType currency;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public CurrencyType getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyType currency) {
		this.currency = currency;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	
   public List<ComplexItem> getItems() {
        return items;
    }

    public void setItems(List<ComplexItem> items) {
        this.items = items;
    }

}
