package com.trans.translation.dao;

import com.trans.translation.pojo.Group;
import com.trans.translation.pojo.User;
import com.trans.translation.pojo.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 * @author Administrator
 * T 需要类型化为实体类(Entity)User，ID需要实体类User中Id的类型
 *
 */
public interface GroupDao extends JpaRepository<Group,String>,JpaSpecificationExecutor<Group> {
    @Query(value = "SELECT * FROM trans_group t1 WHERE t1.userid= ?",nativeQuery = true)
    List<Group> findAllGroup(String userid);

    @Query(value = "SELECT t1.* FROM trans_group t1,trans_user_group t2 WHERE t2.userid =? AND t1.id = t2.groupid AND t2.status=1 ORDER BY t1.createtime DESC",nativeQuery = true)
    List<Group> findAllMemberGroup(@Param("userid")String userid);

    @Query(value = "SELECT t3.username,t2.jointime,t2.id FROM trans_group t1,trans_user_group t2 ,trans_user t3 WHERE t1.userid=? AND t1.id=? AND t1.id=t2.groupid AND t2.status=0 AND t2.userid = t3.id ORDER BY t2.jointime DESC",nativeQuery = true)
    List<Map<String,String>> findAllAudit(@Param("userid")String userid, @Param("id") String id);

    @Query(value = "SELECT t2.* FROM trans_group t1,trans_user t2 WHERE  t1.id =? AND t1.userid = t2.id",nativeQuery = true)
    Map<String,String> findLeaderById(String groupId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE trans_group Set count=count+1 WHERE id = ?",nativeQuery = true)
    void updateCount(String groupId);
}
