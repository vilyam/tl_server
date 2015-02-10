package com.c17.yyh.core.social.controllers;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.constants.Constants;
import com.c17.yyh.core.social.PaymentException;
import com.c17.yyh.core.social.type.ErrorCode;
import com.c17.yyh.core.social.type.PaymentCheckAppKey;
import com.c17.yyh.core.social.type.PaymentCheckMD5;
import com.c17.yyh.core.social.type.SocialNetwork;
import com.c17.yyh.db.entities.purchasing.payments.Item;
import com.c17.yyh.db.entities.purchasing.stocks.StockItem;
import com.c17.yyh.managers.UserManager;
import com.c17.yyh.managers.purchasing.PaymentsManager;
import com.c17.yyh.models.User;
import com.c17.yyh.processors.PaymentProcessor;

 @RequestMapping("/billing")
public class OKPaymentsController {

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private PaymentsManager paymentsService;

    @Autowired
    private UserManager userManager;

    @Autowired
    private PaymentProcessor paymentProcessor;

    @PaymentCheckMD5(sn = SocialNetwork.OK)
    @PaymentCheckAppKey(sn = SocialNetwork.OK)
    @RequestMapping(value = "/ok", method = RequestMethod.GET)
    public @ResponseBody HttpEntity<byte[]> process(HttpServletRequest request) {
        String uid = request.getParameter("uid");
        User user = userManager.getUserBySnId(uid);

        if (user == null) {
            throw new PaymentException(ErrorCode.USER_NOT_FOUND, 0, "Пользователь не найден", uid);
        }
        
        int prCode = request.getParameter("product_code") != null ? Integer.parseInt(request.getParameter("product_code")) : 0;
        String transaction_id = request.getParameter("transaction_id");
        int amount = request.getParameter("amount") != null ? Integer.parseInt(request.getParameter("amount")) : 0;

        Item item = paymentsService.getItemByCode(prCode);

        if (item.getPrice() != amount) {
            throw new PaymentException(ErrorCode.ITEM_INCORRECT_PRICE, 1, "Mismatch in price: " + amount,  uid);
        }

        paymentProcessor.producePayment(transaction_id, user, item, stockItem, 0);

        return newBuyOkResponse();
    }

    private HttpEntity<byte[]> newBuyOkResponse() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><callbacks_payment_response xmlns=\"http://api.forticom.com/1.0/\">true</callbacks_payment_response>";

        byte[] documentBody = xml.getBytes();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "xml"));
        header.setContentLength(documentBody.length);
        return new HttpEntity<>(documentBody, header);
    }
}
