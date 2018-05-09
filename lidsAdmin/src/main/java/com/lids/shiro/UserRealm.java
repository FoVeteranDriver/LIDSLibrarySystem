package com.lids.shiro;


import com.lids.po.User;
import com.lids.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    UserService userService;

    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String libraryCardNum = (String) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> role = new HashSet<String>();
        User user = userService.selectUserByLibraryCardNumber(libraryCardNum);
        role.add(user.getUserTypeName());
        authorizationInfo.setRoles(role);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String LibraryCardNum = (String) token.getPrincipal();
        User user = userService.selectUserByLibraryCardNumber(LibraryCardNum);

        if (user == null){
            throw new UnknownAccountException();
        }

        if (!user.getBanDuration().equals("0")){
            throw new LockedAccountException();
        }

        UsernamePasswordToken token2 = (UsernamePasswordToken) token;
        String credentials = new String(token2.getPassword());
        if (user.getPassword().equals(credentials)){
            SecurityUtils.getSubject().getSession().setAttribute("user",user);
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getLibraryCardNumber(),
                user.getPassword(), getName()
        );
        return authenticationInfo;

    }

}
