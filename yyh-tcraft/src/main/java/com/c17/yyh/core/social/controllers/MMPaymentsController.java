package com.c17.yyh.core.social.controllers;

import javax.servlet.http.HttpServletRequest;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.constants.Constants;
import com.c17.yyh.core.social.PaymentException;
import com.c17.yyh.core.social.type.ErrorCode;
import com.c17.yyh.core.social.type.PaymentCheckAppKey;
import com.c17.yyh.core.social.type.PaymentCheckMD5;
import com.c17.yyh.core.social.type.SocialNetwork;
import com.c17.yyh.db.entities.purchasing.payments.Item;
import com.c17.yyh.db.entities.purchasing.stocks.StockItem;
import com.c17.yyh.exceptions.ServerException;
import com.c17.yyh.managers.UserManager;
import com.c17.yyh.managers.purchasing.PaymentsManager;
import com.c17.yyh.models.User;
import com.c17.yyh.processors.PaymentProcessor;
import com.c17.yyh.processors.StockManager;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/billing")
public class MMPaymentsController {

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private PaymentsManager paymentsService;

    @Autowired
    private PaymentProcessor paymentProcessor;

    @Autowired
    private UserManager userManager;

    @Autowired
    StockManager stockManager;

    @PaymentCheckMD5(sn = SocialNetwork.MM)
    @PaymentCheckAppKey(sn = SocialNetwork.MM)
    @RequestMapping(value = "/mm", method = RequestMethod.GET)
    public @ResponseBody
    HttpEntity<byte[]> process(HttpServletRequest request) {
        String uid = request.getParameter("uid");
        String str_service_id = request.getParameter("service_id");
        String transaction_id = request.getParameter("transaction_id");
        String str_debug = request.getParameter("debug");
        String str_mailiki_price = request.getParameter("mailiki_price");

        int service_id = str_service_id != null ? Integer.parseInt(str_service_id) : 0;
        int debug = str_debug != null ? Integer.parseInt(str_debug) : 0;
        int mailiki_price = str_mailiki_price != null ? Integer.parseInt(str_mailiki_price) : 0;
        //int profit = Integer.parseInt(request.getParameter("profit"));

        User user = userManager.getUserBySnId(uid);

        if (user == null) {
            throw new PaymentException(ErrorCode.USER_NOT_FOUND, 2 , "User was not found in DB", uid);
        }

        Item item = paymentsService.getItemByCode(service_id);
        StockItem stockItem = null;
        if (item == null) {
            stockItem = stockManager.getStockItemById(service_id - Constants.STOCK_PAYMENT_MAGIC_NUMBER);
            item = stockManager.checkPaymetItem(user, service_id);
            if (item == null) {
                throw new PaymentException(ErrorCode.ITEM_NOT_FOUND, 0 , "User send not correct ServiceId: " + service_id, uid);
            }
        }

        if (item.getPrice() != mailiki_price) {
            throw new PaymentException(ErrorCode.ITEM_INCORRECT_PRICE, 0 , "User send not correct price: " + mailiki_price, uid);
        }

        paymentProcessor.producePayment(transaction_id, user, item, stockItem, debug);
        return getBuyOkResponse(uid);
    }

    private HttpEntity<byte[]> getBuyOkResponse(String uid) {
        try {
            byte[] documentBody = new JSONObject().put("status", 1).toString().getBytes();
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json"));
            header.setContentLength(documentBody.length);
            return new HttpEntity<>(documentBody, header);
        } catch (JSONException e) {
            throw new PaymentException(ErrorCode.OTHER, 0 , "Unknown processing error", uid);
        }
    }
}
