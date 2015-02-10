package com.c17.yyh.models.purchasing;

import java.sql.Timestamp;

public class PaymentOrder {

    public PaymentOrder(String transaction_id, String user_sn_id, Timestamp date,
			String product_name, String product_title, int product_id, int price, int debug, int stock_id) {
		super();
		this.transaction_id = transaction_id;
		this.user_sn_id = user_sn_id;
		this.date = date;
		this.product_name = product_name;
		this.product_title = product_title;
		this.product_id = product_id;
		this.price = price;
		this.debug = debug;
		this.setStock_id(stock_id);
	}
    
    public PaymentOrder(int transaction_id, String user_sn_id, Timestamp date,
			String product_name, String product_title, int product_id, int price, int debug, int stock_id) {
		super();
		this.transaction_id = String.valueOf(transaction_id);
		this.user_sn_id = user_sn_id;
		this.date = date;
		this.product_name = product_name;
		this.product_title = product_title;
		this.product_id = product_id;
		this.price = price;
		this.debug = debug;
		this.setStock_id(stock_id);
	}
    
    public PaymentOrder() {
		super();
	}

	private int app_order_id;

    private String transaction_id;

    private String user_sn_id;

    private Timestamp date;

    private String product_name;
    
    private String product_title;

    private int product_id;

    private int price;
    
	private int debug;
	
	private int stock_id;
    
    public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

    public int getDebug() {
        return debug;
    }

    public void setDebug(int debug) {
        this.debug = debug;
    }

    public int getApp_order_id() {
        return app_order_id;
    }

    public void setApp_order_id(int app_order_id) {
        this.app_order_id = app_order_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }
    
    public void setTransaction_id(int transaction_id) {
        this.transaction_id = String.valueOf(transaction_id);
    }
    
    public void setTransaction_id(long transaction_id) {
        this.transaction_id = String.valueOf(transaction_id);
    }

    public String getUser_sn_id() {
        return user_sn_id;
    }

    public void setUser_sn_id(String user_sn_id) {
        this.user_sn_id = user_sn_id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

	public String getProduct_title() {
		return product_title;
	}

	public void setProduct_title(String product_title) {
		this.product_title = product_title;
	}

    public int getStock_id() {
        return stock_id;
    }

    public void setStock_id(int stock_id) {
        this.stock_id = stock_id;
    }
}
