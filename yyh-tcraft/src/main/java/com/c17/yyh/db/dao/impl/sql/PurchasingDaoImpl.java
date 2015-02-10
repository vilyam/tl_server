package com.c17.yyh.db.dao.impl.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.db.dao.IPurchasingDao;
import com.c17.yyh.models.purchasing.PurchasingOrder;
import com.c17.yyh.server.ErrorCodes;
import com.c17.yyh.server.SQLReq;
import com.c17.yyh.exceptions.ServerException;

@Repository("purchasingService")
public class PurchasingDaoImpl implements IPurchasingDao {

    @Autowired
    ServerConfig serverConfig;

    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int addNewPurchasingOrder(PurchasingOrder order) {
        PreparedStatement inserNewOrderPrpStatement = null;
        Connection con = null;
        int state = 0;
        try {
            con = dataSource.getConnection();
                    inserNewOrderPrpStatement = con.prepareStatement(SQLReq.PURCHASING_ORDER_INSERT_NEW);
                    inserNewOrderPrpStatement.setInt(1, order.getUser_id());
                    inserNewOrderPrpStatement.setString(2, order.getSource());
                    inserNewOrderPrpStatement.setInt(3, order.getSource_id());
                    inserNewOrderPrpStatement.setInt(4, order.getPrice());
                    inserNewOrderPrpStatement.setString(5, order.getCurrency());
                    inserNewOrderPrpStatement.setString(6, order.getResources());
                    inserNewOrderPrpStatement.setString(7, order.getValues());
                    inserNewOrderPrpStatement.setString(8, serverConfig.levelSetVersion);
                    inserNewOrderPrpStatement.setInt(9, order.getLevel_number());
                    inserNewOrderPrpStatement.setInt(10, order.getLevelset_number());
                    state = inserNewOrderPrpStatement.executeUpdate();

        } catch (SQLException e) {
            LoggerFactory.getLogger(Thread.currentThread().getName()).error(e.toString());
            throw new ServerException(ErrorCodes.TEMP_DB_ERROR, e.toString(), true);
        } finally {
                try {
                    if (inserNewOrderPrpStatement != null) {
                        inserNewOrderPrpStatement.close();
                    }
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return state;
    }

}
