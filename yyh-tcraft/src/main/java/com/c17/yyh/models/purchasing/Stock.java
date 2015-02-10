package com.c17.yyh.models.purchasing;

import java.io.Serializable;

public class Stock implements Serializable{

    private static final long serialVersionUID = -5711326556642037701L;

    public Stock(int id, int count) {
		super();
		this.id = id;
		this.count = count;
	}
	public Stock() {
		super();
	}
	private int id = 0;
	private int count = 0;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
