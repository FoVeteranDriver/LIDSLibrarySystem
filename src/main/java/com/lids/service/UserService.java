package com.lids.service;

import com.lids.po.User;

public interface UserService {

    boolean signUp(User user);

    User selectUserByOpenId(String openId);

    User selectUserByLibraryCardNumber(String libraryCardNumber);

}
