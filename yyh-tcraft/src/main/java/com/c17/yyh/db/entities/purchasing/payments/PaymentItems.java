/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.c17.yyh.db.entities.purchasing.payments;

import com.c17.yyh.server.EntitiesList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sigurd
 */
@XmlRootElement(name = "paymentitems")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentItems implements EntitiesList<Item>{

    @XmlElement(name = "paymentitem")
    private List<Item> paymentItems = null;

    @Override
    public List<Item> getList() {
        return paymentItems;
    }

    @Override
    public void setList(List<Item> paymentItems) {
        this.paymentItems = paymentItems;
    }

}
