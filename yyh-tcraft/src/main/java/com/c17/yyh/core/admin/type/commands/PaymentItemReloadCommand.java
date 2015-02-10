package com.c17.yyh.core.admin.type.commands;

import java.util.Map;

import com.c17.yyh.core.admin.type.AdminCommandType;
import com.c17.yyh.core.admin.type.Command;
import com.c17.yyh.core.admin.type.GrantLevelType;
import com.c17.yyh.managers.purchasing.PaymentsManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AdminCommandType(name = "paymentitem_reload", level = GrantLevelType.ADMIN)
public class PaymentItemReloadCommand extends Command {

    @Autowired
    private PaymentsManager paymentsManager;

    @Override
    public void execute(Map<String, String> data) {
        paymentsManager.loadItems();
        
        logger.info("Payment items was reloaded");
    }

}
