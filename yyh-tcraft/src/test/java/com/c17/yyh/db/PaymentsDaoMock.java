package com.c17.yyh.db;

import com.c17.yyh.db.dao.IPaymentsDao;
import com.c17.yyh.db.entities.purchasing.payments.Item;
import com.c17.yyh.models.purchasing.PaymentOrder;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentsDaoMock implements IPaymentsDao {

    @Override
    public List<Item> getItems() {
        // TODO Auto-generated method stub
        return new ArrayList<Item>();
    }

    @Override
    public List<PaymentOrder> getOrders() {
        // TODO Auto-generated method stub
        return new ArrayList<PaymentOrder>();
    }

    @Override
    public int addOrder(PaymentOrder paymentOrder) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void saveItems(List<Item> items) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PaymentOrder getOrder(String orderId) {
        // TODO Auto-generated method stub
        return null;
    }

}
