package com.c17.yyh.processors;

import java.sql.Timestamp;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.core.social.PaymentException;
import com.c17.yyh.core.social.type.ErrorCode;
import com.c17.yyh.db.entities.purchasing.payments.Item;
import com.c17.yyh.db.entities.purchasing.stocks.StockItem;
import com.c17.yyh.managers.UserManager;
import com.c17.yyh.managers.purchasing.PaymentsManager;
import com.c17.yyh.models.User;
import com.c17.yyh.models.purchasing.PaymentOrder;

@Service
public class PaymentProcessor{

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private PaymentsManager paymentsManager;
    
    @Autowired
    StockProcessor stockProcessor;

    @Autowired
    private UserManager userManager;

    public int producePayment(String tranzactionId, User user, Item item, StockItem stockItem, Integer isDebug) {
        try {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            String pr_name;
            int stock_id = 0;
            int status = 0;

            if (stockItem != null) {
                stock_id = stockItem.getId();
                pr_name = "Stock_" + item.getProduct_name();
                stockProcessor.produceStockWithoutDecrementBalance(user, stockItem);
            } else {
                pr_name = item.getProduct_name();
                userManager.incrementCrystals(user, item.getValue(), true);
            }

            PaymentOrder paymentOrder = new PaymentOrder(tranzactionId, user.getSnId(), 
                    ts, pr_name, "", item.getProduct_code(), item.getPrice(), isDebug, stock_id);

            status = paymentsManager.addNewOrder(paymentOrder);

            if (status > 0) {
                if (isDebug == 0) {
                    LoggerFactory.getLogger(getClass()).info("Payments: Ok. User snId {} payed for ServiceId {}. TransactonId: {}, Price: {}",
                            new Object[]{user.getSnId(), item.getProduct_code(), tranzactionId, item.getPrice()});
                } else {
                    LoggerFactory.getLogger(getClass()).info("Payments: Debug. User snId {} payed for ServiceId {}. TransactonId: {}, Price: {}",
                            new Object[]{user.getSnId(), item.getProduct_code(), tranzactionId, item.getPrice()});
                }
                if (stockItem != null) {
                    LoggerFactory.getLogger(getClass()).info("by stock Id {}", new Object[]{stockItem.getId()});
                }
            }
            return status;
        } catch (Exception ex){
            throw new PaymentException(ErrorCode.DB_ERROR, 1, "Unknown error in db when try to put order. ServiceId: " + item.getProduct_code(), user.getSnId());
        }

    }
}
