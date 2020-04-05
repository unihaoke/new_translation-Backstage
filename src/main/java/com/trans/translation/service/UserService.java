package com.trans.translation.service;

import com.trans.translation.common.Result;
import com.trans.translation.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    Result getOpenId(String code);

    Result add(User user);


    Result findAll();

    Result update(User user);

    Result findById(String id,String name);

    /**
     * 获取用户信息
     */
    UserDetails loadUserById(String id);

    /**
     * 登录后获取token
     */
    String login(String code);

    /**
     * 根据会员编号获取会员
     */
    User getById(String id);
}
