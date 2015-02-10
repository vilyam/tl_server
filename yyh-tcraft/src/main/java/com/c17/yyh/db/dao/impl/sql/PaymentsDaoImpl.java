package com.c17.yyh.db.dao.impl.sql;

import com.c17.yyh.db.dao.IPaymentsDao;
import com.c17.yyh.db.dao.impl.xml.XMLConfigLoader;
import com.c17.yyh.db.entities.purchasing.payments.Item;
import com.c17.yyh.db.entities.purchasing.payments.PaymentItems;
import com.c17.yyh.models.purchasing.PaymentOrder;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.server.SQLReq;
import com.c17.yyh.exceptions.ServerException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentsDaoImpl implements IPaymentsDao {

    @Autowired
    private XMLConfigLoader configLoader;

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Item> getItems() {
        return configLoader.loadData(PaymentItems.class);
    }

    @Override
    public List<PaymentOrder> getOrders() {
        List<PaymentOrder> paymentOrders = jdbcTemplate.query("SELECT payments_orders.* FROM payments_orders", BeanPropertyRowMapper.newInstance(PaymentOrder.class));
        logger.info("Loaded {} payment orders", paymentOrders.size());
        return paymentOrders;
    }

    @Override
    public PaymentOrder getOrder(String orderId) {
        PreparedStatement getOrderPrpStatement = null;
        Connection con = null;
        ResultSet rs = null;
        PaymentOrder order = null;

        try {
            con = dataSource.getConnection();
            try {
                try {
                    getOrderPrpStatement = con.prepareStatement(SQLReq.PAYMENTS_ORDER_GET);
                    getOrderPrpStatement.setString(1, orderId);
                    rs = getOrderPrpStatement.executeQuery();

                    if (rs.next()) {
                        order = new PaymentOrder();
                        order.setApp_order_id(rs.getInt("app_order_id"));
                        order.setTransaction_id(rs.getString("transaction_id"));
                        order.setDate(rs.getTimestamp("date"));
                        order.setUser_sn_id(rs.getString("user_sn_id"));
                        order.setProduct_name(rs.getString("product_name"));
                        order.setProduct_title(rs.getString("product_title"));
                        order.setProduct_id(rs.getInt("product_id"));
                        order.setPrice(rs.getInt("price"));
                        order.setDebug(rs.getInt("debug"));
                    }

                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (getOrderPrpStatement != null) {
                        getOrderPrpStatement.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }

        return order;
    }

    @Override
    public int addOrder(PaymentOrder paymentOrder) {
        PreparedStatement inserNewOrderPrpStatement = null;
        Connection con = null;
        int last_inserted_id = 0;
        ResultSet rs = null;
        try {
            con = dataSource.getConnection();
            try {
                try {
                    inserNewOrderPrpStatement = con.prepareStatement(SQLReq.PAYMENTS_ORDER_INSERT_NEW, Statement.RETURN_GENERATED_KEYS);
                    inserNewOrderPrpStatement.setString(1, paymentOrder.getTransaction_id());
                    inserNewOrderPrpStatement.setString(2, paymentOrder.getUser_sn_id());
                    inserNewOrderPrpStatement.setTimestamp(3, paymentOrder.getDate());
                    inserNewOrderPrpStatement.setString(4, paymentOrder.getProduct_name());
                    inserNewOrderPrpStatement.setString(5, paymentOrder.getProduct_title());
                    inserNewOrderPrpStatement.setInt(6, paymentOrder.getProduct_id());
                    inserNewOrderPrpStatement.setInt(7, paymentOrder.getPrice());
                    inserNewOrderPrpStatement.setInt(8, paymentOrder.getDebug());
                    inserNewOrderPrpStatement.setInt(9, paymentOrder.getStock_id());
                    inserNewOrderPrpStatement.executeUpdate();

                    rs = inserNewOrderPrpStatement.getGeneratedKeys();
                    if (rs.next()) {
                        last_inserted_id = rs.getInt(1);
                    } else {
                        return 0;
                    }
                } finally {
                    if (rs != null) {
                        rs.close();
                    }
                    if (inserNewOrderPrpStatement != null) {
                        inserNewOrderPrpStatement.close();
                    }
                }
            } finally {
                con.close();
            }
        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        }
        return last_inserted_id;
    }

    @Override
    public void saveItems(List<Item> items) {
        configLoader.saveData(items, PaymentItems.class);
    }

}
