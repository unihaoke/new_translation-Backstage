package com.trans.translation.service;

import com.trans.translation.common.Result;
import com.trans.translation.pojo.Task;
import com.trans.translation.vo.TaskVo;
import org.springframework.web.multipart.MultipartFile;

public interface TaskService {

    Result findByUserId(String userId);

    Result addTaskAndFile(TaskVo taskVo, MultipartFile file);

    Result addTask(TaskVo taskVo);

    Result uploadFile(MultipartFile file);
}
