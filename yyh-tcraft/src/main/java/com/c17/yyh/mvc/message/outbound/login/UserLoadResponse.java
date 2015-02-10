package com.c17.yyh.mvc.message.outbound.login;

import java.util.List;

import com.c17.yyh.db.entities.purchasing.stocks.StockItemWrapper;
import com.c17.yyh.models.DailyBonus;
import com.c17.yyh.models.User;
import com.c17.yyh.server.message.BaseOutboundMessage;

public class UserLoadResponse extends BaseOutboundMessage {
    private User                   user;
    private boolean                first_login;
    private List<StockItemWrapper> stocks;
    private Object[]               payments_store_prices;
    private DailyBonus             daily_bonus;

    public UserLoadResponse() {
        super("/user/load");
    }

    public UserLoadResponse(String action) {
        super(action);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<StockItemWrapper> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockItemWrapper> stocks) {
        this.stocks = stocks;
    }

    public DailyBonus getDaily_bonus() {
        return daily_bonus;
    }

    public void setDaily_bonus(DailyBonus daily_bonus) {
        this.daily_bonus = daily_bonus;
    }

    public boolean isFirst_login() {
        return first_login;
    }

    public void setFirst_login(boolean first_login) {
        this.first_login = first_login;
    }

    public Object[] getPayments_store_prices() {
        return payments_store_prices;
    }

    public void setPayments_store_prices(Object[] payments_store_prices) {
        this.payments_store_prices = payments_store_prices;
    }
}
