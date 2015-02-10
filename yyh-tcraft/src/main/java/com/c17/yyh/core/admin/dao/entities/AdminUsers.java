package com.c17.yyh.core.admin.dao.entities;

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
@XmlRootElement(name = "users")
@XmlAccessorType(XmlAccessType.FIELD)
public class AdminUsers implements EntitiesList<AdminUser>{

    @XmlElement(name = "user")
    private List<AdminUser> users = null;

    @Override
    public List<AdminUser> getList() {
        return users;
    }

    @Override
    public void setList(List<AdminUser> users) {
        this.users = users;
    }

}
