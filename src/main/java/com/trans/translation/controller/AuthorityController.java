package com.trans.translation.controller;

import com.trans.translation.common.Result;
import com.trans.translation.service.AuthorityService;
import com.trans.translation.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author cwh
 * @date 2020/3/28 17:38
 */
@RestController
@RequestMapping(value = "/authority")
public class AuthorityController {


    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @PostMapping
    public Result add(@RequestBody Map<String,String> map,HttpServletRequest request){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return authorityService.add(map,userId);
    }

    @GetMapping(value = "/search")
    public Result findByUserId(HttpServletRequest request){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return authorityService.findTaskByUserId(userId);
    }

    @GetMapping(value = "/search/authority")
    public Result findFinishAuthority(HttpServletRequest request){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return authorityService.findFinishAuthority(userId);
    }


    @DeleteMapping
    public Result delete(@RequestBody Map<String,String> map){
        return authorityService.delete(map);
    }



}
