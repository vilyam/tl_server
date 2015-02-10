package com.c17.yyh.db.entities.adventure;

import java.util.List;

import com.c17.yyh.models.tool.Tool;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "awards")
public class Award {

    private int        money = 0;
    @XmlElement(name = "tool", type = Tool.class)
    private List<Tool> tools;
    @XmlElement(name = "pet", type = Pet.class)
    private Pet     pet;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public List<Tool> getTools() {
        return tools;
    }

    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
