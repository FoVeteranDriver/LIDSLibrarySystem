package com.lids.service.impl;

import com.lids.dao.AdminUserDao;
import com.lids.po.AdminPermission;
import com.lids.po.AdminUser;
import com.lids.service.AdminUserService;
import com.lids.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service("AdminUserService")
public class AdminUserServiceImpl extends BaseService implements AdminUserService {

    @Resource
    private AdminUserDao adminUserDao;

    @Override
    public AdminUser selectAdminUserByUserName(String userName) {
        return adminUserDao.selectUserByUserName(userName);
    }

    @Override
    public Map selectUserPermission(String userName) {
        return adminUserDao.selectPermission(userName);
    }

    @Override
    public Set<String> findPermision(String userName) {
        Map permissionList = adminUserDao.selectPermission(userName);
        Set<String> permissionSet = new HashSet<>();

        Iterator iterator = permissionList.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            String key = (String)entry.getKey();
            if (key.equals("admin_user_name") || key.equals("admin_user_id")){
                continue;
            }
            Integer value = (Integer)entry.getValue();
            switch (value){
                case 1:
                    permissionSet.add(key+":read");
                    break;
                case 2:
                    permissionSet.add(key+":read");
                    permissionSet.add(key+":write");
                    break;
            }
        }

        return permissionSet;
    }

    @Override
    @Transactional
    public boolean addNewAdminUser(Map params) {
        AdminUser newUser = new AdminUser();
        newUser.setAccount((String)params.get("account"));
        newUser.setPassword((String)params.get("password"));
        newUser.setEmail((String)params.get("email"));
        newUser.setPhone((String)params.get("phone"));
        newUser.setName((String)params.get("name"));
        int result = adminUserDao.insertNewAdmin(newUser);

        if (result == 1){
            newUser = adminUserDao.selectUserByUserName((String)params.get("account"));
        }else {
            return false;
        }
        AdminPermission adminPermission = new AdminPermission();
        List permissionList = (ArrayList)params.get("accessList");
        adminPermission.setdata(permissionList);
        adminPermission.setAdmin_user_name(newUser.getAccount());
        adminPermission.setAdmin_user_id(newUser.getId());
        int resultTwo = adminUserDao.insertPermission(adminPermission);

        if (resultTwo == 1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<AdminUser> selectAdminList() {
        List<AdminUser> adminList = adminUserDao.selectAllAdmin();
        for (AdminUser user:
             adminList) {
            user.setAccessList(generateMap(user.getAccount()));
        }
        return adminList;
    }

    @Override
    public boolean deleteAdmin(String account) {
        int result = adminUserDao.deleteAdmin(account);
        if (result == 1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<AdminUser> searchAdmin(String key) {
        Map param = new HashMap();
        param.put("key",key);
        List<AdminUser> adminList = adminUserDao.searchAdmin(param);
        for (AdminUser user:
                adminList) {
            user.setAccessList(generateMap(user.getAccount()));
        }
        return adminList;
    }

    @Override
    public List generateMap(String userName){
        Map permission = selectUserPermission(userName);

        List result = new ArrayList();

        //添加admin-settings
        Map admin_settings = new HashMap();
        admin_settings.put("name","admin-settings");
        admin_settings.put("access",permission.get("admin_settings"));

        List settingsChildren = new ArrayList();

        Map admin_authority = new HashMap();
        admin_authority.put("name","admin-authority");
        admin_authority.put("access",permission.get("admin_authority"));
        settingsChildren.add(admin_authority);

        Map admin_rules = new HashMap();
        admin_rules.put("name","admin-rules");
        admin_rules.put("access",permission.get("admin_rules"));
        settingsChildren.add(admin_rules);

        Map admin_display = new HashMap();
        admin_display.put("name","admin-display");
        admin_display.put("access",permission.get("admin_display"));
        settingsChildren.add(admin_display);

        Map admin_space = new HashMap();
        admin_space.put("name","admin-space");
        admin_space.put("access",permission.get("admin_space"));
        settingsChildren.add(admin_space);

        admin_settings.put("children",settingsChildren);
        result.add(admin_settings);

        //添加admin-management
        Map admin_management = new HashMap();
        admin_management.put("name","admin-management");
        admin_management.put("access",permission.get("admin_management"));

        List managementChildren = new ArrayList();

        Map admin_mSpace = new HashMap();
        admin_mSpace.put("name","admin-mSpace");
        admin_mSpace.put("access",permission.get("admin_mSpace"));
        managementChildren.add(admin_mSpace);
        Map admin_credits = new HashMap();
        admin_credits.put("name","admin-credits");
        admin_credits.put("access",permission.get("admin_credits"));
        managementChildren.add(admin_credits);
        Map admin_activity = new HashMap();
        admin_activity.put("name","admin-activity");
        admin_activity.put("access",permission.get("admin_activity"));
        managementChildren.add(admin_activity);

        admin_management.put("children",managementChildren);
        result.add(admin_management);

        //添加admin-data
        Map admin_data = new HashMap();
        admin_data.put("name","admin-data");
        admin_data.put("access",permission.get("admin_data"));

        List dataChildren = new ArrayList();

        Map admin_data1 = new HashMap();
        admin_data1.put("name","admin-data1");
        admin_data1.put("access",permission.get("admin_data1"));
        dataChildren.add(admin_data1);
        Map admin_data2 = new HashMap();
        admin_data2.put("name","admin-data2");
        admin_data2.put("access",permission.get("admin_data2"));
        dataChildren.add(admin_data2);

        admin_data.put("children",dataChildren);
        result.add(admin_data);

        return result;
    }
}
