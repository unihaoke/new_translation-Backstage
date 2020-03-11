package com.trans.translation.test;

import com.trans.translation.dao.TaskDao;
import com.trans.translation.vo.TaskVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TaskDaoTest {

    @Autowired
    private TaskDao taskDao;

    @Test
    public void  test(){
        List<TaskVo> list = taskDao.findByUserId("o5pU-5ZzEslFcjkN8MDEStIs3u7k");
        System.out.println(list.get(0).getT_describe());
    }

}
