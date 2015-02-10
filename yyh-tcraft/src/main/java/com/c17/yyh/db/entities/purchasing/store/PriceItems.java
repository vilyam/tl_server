package com.c17.yyh.db.entities.purchasing.store;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.c17.yyh.server.EntitiesList;

@XmlRootElement(name = "prices")
@XmlAccessorType(XmlAccessType.FIELD)
public class PriceItems implements EntitiesList<PriceItem>{
	
	@XmlElement(name = "price")
	private List<PriceItem> priceItems;

	@Override
	public List<PriceItem> getList() {
		return priceItems;
	}

	@Override
	public void setList(List<PriceItem> list) {
		this.priceItems = list;
	}
}
