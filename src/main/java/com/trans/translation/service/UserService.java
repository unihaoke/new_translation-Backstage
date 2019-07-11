package com.trans.translation.service;

import com.trans.translation.common.Result;
import com.trans.translation.pojo.User;

public interface UserService {

    Result getOpenId(String code);

    Result add(User user);


    Result findAll();

    Result update(User user);

    Result findById(String id);
}
