package com.trans.translation.controller;

import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.service.ApprovalService;
import com.trans.translation.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author cwh
 * @date 2020/3/29 0:31
 */
@RestController
@RequestMapping(value = "/approval")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @PostMapping
    public Result add(@RequestBody Map<String,String> map, HttpServletRequest request){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return approvalService.add(map,userId);
    }

    @GetMapping(value = "/search/approval")
    public Result findByApproval(HttpServletRequest request){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return approvalService.findByApproval(userId);
    }

    @PutMapping(value = "/agree")
    public Result agree(@RequestBody Map<String,String> map){
        return approvalService.agree(map);
    }

    @PutMapping(value = "/reject")
    public Result reject(@RequestBody Map<String,String> map){
        return approvalService.reject(map);
    }


}
