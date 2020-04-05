package com.trans.translation.controller;

import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.pojo.User;
import com.trans.translation.service.UserService;
import com.trans.translation.utils.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
@Api(tags = "UserController", description = "用户登录注册管理")
public class UserController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UserService userService;


    /**
     * 用户登录
     * @param params
     * @return
     */
    @ApiOperation("用户登录")
    @PostMapping(value = "/login")
    public Result login(@RequestBody Map<String,Object> params){
        String code = (String) params.get("code");
        System.out.println(code);
        String token = userService.login(code);
        if (token == null) {
            return new Result(false,StatusCode.ERROR,"token失效请重新登录");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return new Result(true,StatusCode.OK,"登录成功",tokenMap);
    }

    /**
     * 添加用户
     */
    @PostMapping
    @ApiOperation("添加用户")
    public Result add(@RequestBody User user){
        return userService.add(user);
    }

    /**
     * 查询全部数据
     */
    @GetMapping(value = "/findAll")
    @ApiOperation("查询全部数据")
    public Result findAll(){
        return userService.findAll();
    }

    /**
     * 修改用户信息
     */
    @ApiOperation("修改用户信息")
    @PutMapping
    public Result update(@RequestBody User user,HttpServletRequest request){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        user.setId(userId);
        return userService.update(user);
    }

    /**
     * 根据ID查询
     * @return
     */
    @ApiOperation("根据ID查询")
    @GetMapping(value = "/{name}")
    public Result findById(HttpServletRequest request,@PathVariable(value = "name") String name){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return userService.findById(userId,name);
    }

}
