package com.c17.yyh.db.dao;

import java.util.List;

import com.c17.yyh.db.entities.purchasing.payments.Item;
import com.c17.yyh.models.purchasing.PaymentOrder;

public interface IPaymentsDao {

    public List<Item> getItems();

    public List<PaymentOrder> getOrders();

    public int addOrder(PaymentOrder paymentOrder);
    
    public PaymentOrder getOrder(String orderId);

    void saveItems(List<Item> items);
}
