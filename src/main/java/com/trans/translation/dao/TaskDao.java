package com.trans.translation.dao;

import com.trans.translation.pojo.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface TaskDao extends JpaRepository<Task,String>,JpaSpecificationExecutor<Task> {
}
