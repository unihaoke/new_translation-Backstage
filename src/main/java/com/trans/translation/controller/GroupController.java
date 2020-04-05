package com.trans.translation.controller;

import com.trans.translation.common.Result;
import com.trans.translation.service.GroupService;
import com.trans.translation.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author cwh
 * @date 2020/3/11 16:59
 */
@RestController
@RequestMapping(value = "/group")
public class GroupController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private GroupService groupService;


    @PostMapping
    public Result createGroup(HttpServletRequest request, @RequestBody Map<String, String> map){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return groupService.create(userId,map);
    }

    @PostMapping(value = "/join")
    public Result joinGroup(HttpServletRequest request, @RequestBody Map<String, String> map){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return groupService.join(userId,map);
    }

    @PostMapping(value = "/kickout")
    public Result kickOut(HttpServletRequest request,@RequestBody Map<String, String> map){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return groupService.kickOut(userId,map);
    }

    @PostMapping(value = "/exit")
    public Result exit(HttpServletRequest request,@RequestBody Map<String, String> map){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return groupService.exit(userId,map);
    }

    @PutMapping(value = "/audit/agree")
    public Result agreeAudit(HttpServletRequest request,@RequestBody Map<String, String> map){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return groupService.agreeAudit(userId,map);
    }

    @DeleteMapping(value = "/audit/reject")
    public Result rejectAudit(HttpServletRequest request,@RequestBody Map<String, String> map){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return groupService.rejectAudit(userId,map);
    }


    @GetMapping
    public Result findAllGroup(HttpServletRequest request){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return groupService.findAllGroup(userId);
    }

    @GetMapping(value = "/member/group")
    public Result findAllMemberGroup(HttpServletRequest request){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return groupService.findAllMemberGroup(userId);
    }

    @GetMapping(value = "/member/{groupId}")
    public Result findAllMember(@PathVariable("groupId") String groupId){
        return groupService.findAllMember(groupId);
    }

    @GetMapping(value = "/audit/{groupId}")
    public Result findAllAudit(HttpServletRequest request,@PathVariable("groupId") String groupId){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return groupService.findAllAudit(userId,groupId);
    }

    @GetMapping(value = "/search/{groupId}")
    public Result findLeaderById(@PathVariable("groupId") String groupId){
        return groupService.findLeaderById(groupId);
    }
}
