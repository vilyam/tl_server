/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.c17.yyh.db.entities.adventure.treasure;

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
@XmlRootElement(name = "treasures")
@XmlAccessorType(XmlAccessType.FIELD)
public class Treasures implements EntitiesList<Treasure>{

    @XmlElement(name = "treasure")
    private List<Treasure> treasures = null;

    @Override
    public List<Treasure> getList() {
        return treasures;
    }

    @Override
    public void setList(List<Treasure> treasures) {
        this.treasures = treasures;
    }

}
