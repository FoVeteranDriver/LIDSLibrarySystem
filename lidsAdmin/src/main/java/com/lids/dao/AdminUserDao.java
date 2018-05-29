package com.lids.dao;

import com.lids.po.AdminPermission;
import com.lids.po.AdminUser;

import java.util.List;
import java.util.Map;

public interface AdminUserDao{

    AdminUser selectUserByUserName(String userName);

    Map selectPermission(String userName);

    Integer insertNewAdmin(AdminUser adminUser);

    Integer insertPermission(AdminPermission adminPermission);

    List<AdminUser> selectAllAdmin();

    List<AdminUser> searchAdmin(Map map);

    Integer deleteAdmin(String account);

    Integer editAdmin(AdminUser adminUser);

    Integer deletePerm(int id);
}
