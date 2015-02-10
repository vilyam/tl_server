/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.c17.yyh.models.tool;

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
@XmlRootElement(name = "toolitems")
@XmlAccessorType(XmlAccessType.FIELD)
public class ToolItems implements EntitiesList<ToolItem>{

    @XmlElement(name = "toolitem")
    private List<ToolItem> toolItems = null;

    @Override
    public List<ToolItem> getList() {
        return toolItems;
    }

    @Override
    public void setList(List<ToolItem> toolItems) {
        this.toolItems = toolItems;
    }

}
