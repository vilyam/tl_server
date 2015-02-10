package com.c17.yyh.db.entities.friends;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.c17.yyh.server.EntitiesList;

@XmlRootElement(name = "bonusitems")
@XmlAccessorType(XmlAccessType.FIELD)
public class FriendBonusItems implements EntitiesList<FriendBonusItem>{
    @XmlElement
    private FriendBonusMin min;

    @XmlElement(name = "bonusitem")
    private List<FriendBonusItem> bonusItems = null;

    @Override
    public List<FriendBonusItem> getList() {
        return bonusItems;
    }

    @Override
    public void setList(List<FriendBonusItem> list) {
        bonusItems = list;
    }

    public FriendBonusMin getMin() {
        return min;
    }

    public void setMin(FriendBonusMin min) {
        this.min = min;
    }
}
