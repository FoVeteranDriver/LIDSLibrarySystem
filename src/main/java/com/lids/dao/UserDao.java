package com.lids.dao;

import com.lids.po.User;

public interface UserDao {

    Integer signUp(User user);

    User selectUserByOpenId(String openid);

}
