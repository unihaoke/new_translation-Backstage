package com.trans.translation.controller;

import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.pojo.User;
import com.trans.translation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 用户登录
     * @param params
     * @return
     */
    @PostMapping(value = "/login")
    public Result getOpenId(@RequestBody Map<String,Object> params){
        return userService.getOpenId(params.get("code").toString());
    }

    /**
     * 添加用户
     */
    @PostMapping
    public Result add(@RequestBody User user){
        return userService.add(user);
    }

    /**
     * 查询全部数据
     */
    @GetMapping
    public Result findAll(){
        return userService.findAll();
    }

    /**
     * 修改用户信息
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody User user,@PathVariable String id){
        user.setId(id);
        return userService.update(user);
    }

    /**
     * 根据ID查询
     * @param id ID
     * @return
     */
    @GetMapping(value="/{id}")
    public Result findById(@PathVariable String id){
        return userService.findById(id);
    }

}
