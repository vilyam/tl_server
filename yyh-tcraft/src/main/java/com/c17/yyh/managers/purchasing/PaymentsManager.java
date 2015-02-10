package com.c17.yyh.managers.purchasing;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c17.yyh.db.dao.IPaymentsDao;
import com.c17.yyh.db.entities.purchasing.payments.Item;
import com.c17.yyh.models.purchasing.PaymentOrder;

@Service
public class PaymentsManager {

    @Autowired
    private IPaymentsDao paymentsService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private List<Item> items;
    private List<PaymentOrder> paymentOrders;

    @PostConstruct
    protected void initialize() {
        loadItems();
    }

    public Item getItemByName(String name) {
        for (Item item : items) {
            if (item.getProduct_name().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public Item getItemByCode(int id) {
        for (Item item : items) {
            if (item.getProduct_code() == id) {
                return item;
            }
        }
        return null;
    }

    public PaymentOrder checkOrder(String order_id) {
        return paymentsService.getOrder(order_id);
    }

    public int addNewOrder(PaymentOrder paymentOrder) {
        int state =  paymentsService.addOrder(paymentOrder);

        if (state > 0 && paymentOrders != null) {
            paymentOrders.add(paymentOrder);
        }

        return state;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<PaymentOrder> getOrders() {
    	if (paymentOrders == null) {
    		paymentOrders = paymentsService.getOrders();
    	}

        return paymentOrders;
    }

    public void setOrders(List<PaymentOrder> paymentOrders) {
        this.paymentOrders = paymentOrders;
    }

    public void loadItems() {
        items = paymentsService.getItems();
        logger.info("Loaded {} payments", items.size());
    }
}
