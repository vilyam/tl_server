package com.c17.yyh.core.admin.dao;

import com.c17.yyh.core.admin.dao.entities.AdminUser;
import com.c17.yyh.core.admin.dao.entities.AdminUsers;
import com.c17.yyh.db.dao.impl.xml.XMLConfigLoader;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminConfigDaoImpl implements IAdminConfigDao {

    @Autowired
    private XMLConfigLoader xmlConfigLoader;

    @Override
    public List<AdminUser> getUsers() {
        return xmlConfigLoader.loadData(AdminUsers.class);
    }

}
