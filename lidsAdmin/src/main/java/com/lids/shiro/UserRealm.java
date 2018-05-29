package com.lids.shiro;


import com.lids.po.AdminUser;
import com.lids.service.AdminUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class UserRealm extends AuthorizingRealm {

    AdminUserService adminUserService;

    public void setadminUserService(AdminUserService adminUserService){
        this.adminUserService = adminUserService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String)principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(adminUserService.findPermision(userName));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String user_name = (String) token.getPrincipal();
        AdminUser user = adminUserService.selectAdminUserByUserName(user_name);

        if (user == null){
            throw new UnknownAccountException();
        }

        UsernamePasswordToken token2 = (UsernamePasswordToken) token;
        String credentials = new String(token2.getPassword());
        if (user.getPassword().equals(credentials)){
            SecurityUtils.getSubject().getSession().setAttribute("adminUser",user);
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getAccount(),
                user.getPassword(), getName()
        );
        return authenticationInfo;

    }

}
