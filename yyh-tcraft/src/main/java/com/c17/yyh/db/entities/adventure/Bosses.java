/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.c17.yyh.db.entities.adventure;

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
@XmlRootElement(name = "bosses")
@XmlAccessorType(XmlAccessType.FIELD)
public class Bosses implements EntitiesList<Boss>{

    @XmlElement(name = "boss")
    private List<Boss> bosses = null;

    @Override
    public List<Boss> getList() {
        return bosses;
    }

    @Override
    public void setList(List<Boss> bosses) {
        this.bosses = bosses;
    }

}
