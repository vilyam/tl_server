package com.c17.yyh.db.entities.purchasing.stocks;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.c17.yyh.server.EntitiesList;

@XmlRootElement(name = "stocks")
@XmlAccessorType(XmlAccessType.FIELD)
public class StockItems implements EntitiesList<StockItem>{

	@XmlElement(name = "stock")
	private List<StockItem> stockItems;
	
	@Override
	public List<StockItem> getList() {
		return stockItems;
	}

	@Override
	public void setList(List<StockItem> list) {
		stockItems = list;
	}

}
