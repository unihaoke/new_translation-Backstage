package com.trans.translation.dao;


import com.trans.translation.pojo.Authority;
import com.trans.translation.vo.TaskVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 * @author Administrator
 * T 需要类型化为实体类(Entity)User，ID需要实体类User中Id的类型
 *
 */
public interface AuthorityDao extends JpaRepository<Authority,String>,JpaSpecificationExecutor<Authority> {

    @Query(value = "SELECT new com.trans.translation.vo.TaskVo(t2.title,t2.t_describe,t2.t_language,t2.territory,t2.deadline,t2.overdue,t1.userid,t1.id,t1.filename,t1.t_status,t1.translatefile,t1.total,t1.createtime) FROM Task t1 LEFT JOIN Product t2 ON t1.product_id = t2.pid LEFT JOIN Authority a1 ON t1.id = a1.taskid WHERE a1.userid=:userid ORDER BY t1.createtime DESC")
    List<TaskVo> findTaskByUserId(@Param("userid")String userid);

    Authority findByUseridAndTaskid(String userId,String taskId);

    @Query(value = "SELECT t1.group_id,t3.title,t2.createtime,t4.username,t1.id FROM trans_authority t1 LEFT JOIN trans_task t2 ON t1.taskid = t2.id LEFT JOIN trans_product t3 ON t3.pid = t2.product_id LEFT JOIN  trans_user t4 ON t1.userid = t4.id WHERE authorized_personid=?",nativeQuery = true)
    List<Map<String,String>> findFinishAuthority(String userId);
}
