package com.c17.yyh.core.admin.dao;

import java.util.List;

import com.c17.yyh.core.admin.dao.entities.AdminUser;

/**
 *
 * @author sigurd
 */
public interface IAdminConfigDao {

    public List<AdminUser> getUsers();
}
