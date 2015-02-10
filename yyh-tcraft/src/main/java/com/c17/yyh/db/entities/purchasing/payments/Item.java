package com.c17.yyh.db.entities.purchasing.payments;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "paymentitem")
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
    private int product_code;
    private String product_name;
    private String photo_url;
	private int price;
    private int price_other;
    private int value;
    private String description;

    public int getProduct_code() {
		return product_code;
	}

	public void setProduct_code(int product_code) {
		this.product_code = product_code;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getPhoto_url() {
		return photo_url;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPrice_other() {
		return price_other;
	}

	public void setPrice_other(int price_other) {
		this.price_other = price_other;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.product_code != other.product_code) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Item{" + "product_code=" + product_code + ", value=" + value + '}';
    }

}
