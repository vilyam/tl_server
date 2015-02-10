package com.c17.yyh.core.social.controllers;

import com.c17.yyh.config.ServerConfig;
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
import com.c17.yyh.models.purchasing.PaymentOrder;
import com.c17.yyh.processors.PaymentProcessor;
import com.c17.yyh.processors.StockManager;

import javax.servlet.http.HttpServletRequest;

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
public class VKPaymentsController {

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private PaymentsManager paymentsService;

    @Autowired
    private UserManager userManager;

    @Autowired
    StockManager stockManager;

    @Autowired
    private PaymentProcessor paymentProcessor;

    @PaymentCheckMD5(sn = SocialNetwork.VK)
    @PaymentCheckAppKey(sn = SocialNetwork.VK)
    @RequestMapping(value = "/vk", method = RequestMethod.POST)
    public @ResponseBody HttpEntity<byte[]> process(HttpServletRequest request) {
        int debug = 0;

        String uId = request.getParameter("user_id");
        User user = userManager.getUserBySnId(uId);
        if (user == null) {
            throw new PaymentException(ErrorCode.USER_NOT_FOUND, 1, "User was not found", uId);
        }

        String itemName = request.getParameter("item");
        String notification_type = request.getParameter("notification_type");

        boolean isTest = false;
        if (notification_type.contains("_test")) {
            isTest = true;
            debug = 1;
        }

        Item item = paymentsService.getItemByName(itemName);
        StockItem stockItem = null;
        if (item == null) {
            stockItem = stockManager.getStockItemByName(itemName);
            item = stockManager.checkPaymetItem(user, itemName);
            if (item == null) {
                throw new PaymentException(ErrorCode.ITEM_NOT_FOUND, 0 , "User send not correct ServiceId: " + itemName, uId);
            }
        }

        if (notification_type.contains("get_item")) {
            String title = item.getDescription();
            if (isTest) {
                title += " (test mode)";
            }

            return getItemResponse(uId, title, item.getPhoto_url(), item.getPrice(), item.getProduct_code(), 0);
        } else if (notification_type.contains("order_status_change")) {
            String order_id = request.getParameter("order_id");
            String item_price_str = request.getParameter("item_price");
            int item_price = item_price_str == null ? 0: Integer.parseInt(item_price_str);

            String status = request.getParameter("status");
            //int item_id = Integer.parseInt(request.getParameter("item_id"));
            //String item_title = request.getParameter("item_title");
            //Long date = Long.parseLong(request.getParameter("date"));

            if (!status.equalsIgnoreCase("chargeable")) {
                throw new PaymentException(ErrorCode.OTHER, 1, "Передано непонятно что вместо chargeable", uId);
            }

            if (item.getPrice() != item_price) {
                throw new PaymentException(ErrorCode.ITEM_INCORRECT_PRICE, 1, "Mismatch in price: " + item_price,  uId);
            }

            PaymentOrder order = paymentsService.checkOrder(order_id);
            if (order != null) {
                return getOrderResponse(uId, order.getTransaction_id(), order.getApp_order_id());
            }

            int appOrderId = paymentProcessor.producePayment(order_id, user, item, stockItem, debug);
            return getOrderResponse(uId, String.valueOf(order_id), appOrderId);
        }
        throw new PaymentException(ErrorCode.OTHER, 1, "Запрос не выполнен", uId);
    }

    private HttpEntity<byte[]> getItemResponse(String uId, String title, String photo_url, int price, int item_id, int expiration) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json"));

        byte[] documentBody = null;
        try {
            documentBody = new JSONObject()
            .put("response", new JSONObject()
            .put("title", title)
            .put("photo_url", photo_url)
            .put("price", price)
            .put("item_id", item_id)
            .put("expiration", expiration))
            .toString().getBytes();
        } catch (JSONException e) {
            throw new PaymentException(ErrorCode.GENERAL, 1, "Error building outgoing message", uId);
        }

        header.setContentLength(documentBody.length);
        return new HttpEntity<>(documentBody, header);
    }
    
    private HttpEntity<byte[]> getOrderResponse(String uId, String order_id, int app_order_id) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json"));

        byte[] documentBody = null;
        try {
            documentBody = new JSONObject()
            .put("response", new JSONObject()
            .put("order_id", order_id)
            .put("app_order_id", app_order_id))
            .toString().getBytes();
        } catch (JSONException e) {
            throw new PaymentException(ErrorCode.GENERAL, 1, "Error building outgoing message", uId);
        }

        header.setContentLength(documentBody.length);
        return new HttpEntity<>(documentBody, header);
    }
}
