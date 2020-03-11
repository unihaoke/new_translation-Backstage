package com.trans.translation.controller;

import com.trans.translation.common.Result;
import com.trans.translation.service.TaskService;
import com.trans.translation.utils.JwtTokenUtil;
import com.trans.translation.vo.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;


    @GetMapping
    public Result findByUserId(HttpServletRequest request){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        return taskService.findByUserId(userId);
    }

    @PostMapping("/upload")
    public Result addTaskAndFile(TaskVo taskVo, @RequestParam("file") MultipartFile file,HttpServletRequest request){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        taskVo.setUserid(userId);
        return taskService.addTaskAndFile(taskVo,file);
    }

    @PostMapping
    public Result addTask(@RequestBody TaskVo taskVo,HttpServletRequest request){
        String userId = jwtTokenUtil.getUserIdFromToken(request.getHeader(tokenHeader).substring(tokenHead.length()));
        taskVo.setUserid(userId);
        System.out.println(userId);
        return taskService.addTask(taskVo);
    }

    @PostMapping("/upload/file")
    public Result upload(@RequestParam("file") MultipartFile file){
        return taskService.uploadFile(file);
    }

}
