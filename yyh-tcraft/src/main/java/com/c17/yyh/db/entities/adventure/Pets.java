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
@XmlRootElement(name = "pets")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pets implements EntitiesList<Pet>{

    @XmlElement(name = "pet")
    private List<Pet> pets = null;

    @Override
    public List<Pet> getList() {
        return pets;
    }

    @Override
    public void setList(List<Pet> pets) {
        this.pets = pets;
    }

}
