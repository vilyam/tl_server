package com.c17.yyh.db.entities.purchasing.stocks;

import com.c17.yyh.constants.Constants;
import com.c17.yyh.db.entities.purchasing.ComplexItem;
import com.c17.yyh.db.entities.purchasing.payments.Item;
import com.c17.yyh.misc.DateAdapter;
import com.c17.yyh.type.CurrencyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "stock")
@XmlAccessorType(XmlAccessType.FIELD)
public class StockItem {
    @XmlAttribute
    protected int           id;

    @JsonInclude(Include.NON_NULL)
    protected Integer           paymentId;

    @XmlAttribute
    protected String        name;

    @XmlAttribute
    protected String type;

    @XmlElement(name = "item")
    protected List<ComplexItem> items;

    @XmlAttribute
    protected int           price;

    @XmlAttribute
    protected CurrencyType currency;

    @XmlAttribute
    protected String        message;

    @XmlAttribute
    public String        description;
    
    @XmlAttribute
    protected String restriction;

	@JsonIgnore
    @XmlJavaTypeAdapter(DateAdapter.class)
    @XmlAttribute
    private Date          startDate;

    @JsonIgnore
    private Long          startTime = null;
    
    @JsonIgnore
    private Long          endTime = null;

    @JsonIgnore
    @XmlAttribute
    private int           amountHours;

    @JsonIgnore
    @XmlAttribute
    private int startFromFirstLoginDays;

    @JsonIgnore
    @XmlAttribute
    private int           count;
    
    @JsonIgnore
    @XmlAttribute
    private String photoUrl = "";

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ComplexItem> getItems() {
        return items;
    }

    public void setItems(List<ComplexItem> items) {
        this.items = items;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public int getAmountHours() {
        return amountHours;
    }

    public void setAmountHours(int amountHours) {
        this.amountHours = amountHours;
    }

    public int getStartFromFirstLoginDays() {
        return startFromFirstLoginDays;
    }

    public void setStartFromFirstLoginDays(int startFromFirstLoginDays) {
        this.startFromFirstLoginDays = startFromFirstLoginDays;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @JsonIgnore
    public boolean isValid() {
        if (count == 0)
            return true;

        long time = System.currentTimeMillis();
        if (time >= getStartTime() && time <= getEndTime()) {
            return true;
        } else {
            return false;
        }
    }

    @JsonIgnore
    public boolean isValidFromDate(long startDate) {
        if (Math.abs(startDate - getStartTime()) >= startFromFirstLoginDays * Constants.MSEC_IN_DAY) 
            return true;
        else
            return false;
    }

    @JsonIgnore
    public StockItemWrapper getWrapper() {
        if(getCount() == 0) {
            return new StockItemWrapper(this, Constants.MSEC_IN_DAY);
        } else {
            return new StockItemWrapper(this, getEndTime() - System.currentTimeMillis());
        }
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }
    
    public String getRestriction() {
		return restriction;
	}

	public void setRestriction(String restriction) {
		this.restriction = restriction;
	}

    @JsonIgnore
    public Item getPaymentItem () {
        Item item = new Item();
        item.setProduct_code(Constants.STOCK_PAYMENT_MAGIC_NUMBER+id);
        item.setPhoto_url(photoUrl);
        item.setProduct_name(name);
        item.setPrice(price);
        item.setValue(1);
        item.setDescription(description);
        item.setPhoto_url(photoUrl);
        return item;
    }

    @JsonIgnore
    public Long getStartTime() {
        if (startTime == null) {
            startTime = getStartDate().getTime();
        }
        return startTime;
    }

    @JsonIgnore
    public Long getEndTime() {
        if (endTime == null) {
            endTime = getStartTime() + getAmountHours()*Constants.MSEC_IN_HOUR;
        }
        return endTime;
    }
}
