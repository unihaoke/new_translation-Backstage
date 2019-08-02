package com.trans.translation.controller;

import com.trans.translation.common.Result;
import com.trans.translation.pojo.Task;
import com.trans.translation.service.TaskService;
import com.trans.translation.vo.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{userId}")
    public Result findByUserId(@PathVariable String userId){
        return taskService.findByUserId(userId);
    }

    @PostMapping("/upload")
    public Result addTaskAndFile(TaskVo taskVo, @RequestParam("file") MultipartFile file){
        return taskService.addTaskAndFile(taskVo,file);
    }

}
