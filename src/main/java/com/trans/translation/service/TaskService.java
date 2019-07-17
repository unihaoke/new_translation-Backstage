package com.trans.translation.service;

import com.trans.translation.common.Result;
import com.trans.translation.pojo.Task;
import org.springframework.web.multipart.MultipartFile;

public interface TaskService {

    Result findByUserId(String userId);

    Result addTaskAndFile(Task task, MultipartFile file);
}
