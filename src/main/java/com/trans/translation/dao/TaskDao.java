package com.trans.translation.dao;

import com.trans.translation.pojo.Task;
import com.trans.translation.vo.TaskVo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface TaskDao extends JpaRepository<Task,String>,JpaSpecificationExecutor<Task> {

    List<Task> findByUserid(String userid, Sort sort);

    @Query(value = "SELECT new com.trans.translation.vo.TaskVo(t2.title,t2.t_describe,t2.t_language,t2.territory,t2.deadline,t2.overdue,t1.userid,t1.id,t1.filename,t1.t_status,t1.translatefile,t1.total,t1.createtime) FROM Task t1 LEFT JOIN Product t2 ON t1.product_id = t2.pid WHERE t1.userid=:userid ORDER BY t1.createtime DESC")
    List<TaskVo> findByUserId(@Param("userid")String userid);

    @Modifying
    @Transactional
    @Query(value = "UPDATE trans_task SET translatefile =  ? WHERE id = ?",nativeQuery = true)
    void updateMergeFile(String path,String taskId);

    @Query(value = "SELECT translatefile FROM trans_task WHERE id = ?",nativeQuery = true)
    String findMergeById(String taskId);
}
