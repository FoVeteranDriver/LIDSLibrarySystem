package com.lids.service;

import com.lids.po.AdminUser;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AdminUserService {

    AdminUser selectAdminUserByUserName(String userName);

    Map selectUserPermission(String userName);

    Set<String> findPermision(String userName);

    boolean addNewAdminUser(Map params);

    List<AdminUser> selectAdminList();

    List generateMap(String userName);

    boolean deleteAdmin(String account);

    List<AdminUser> searchAdmin(String key);

    boolean editAdminUser(Map params,int id);
}
