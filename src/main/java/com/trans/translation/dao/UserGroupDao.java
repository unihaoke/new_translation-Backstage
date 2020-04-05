package com.trans.translation.dao;


import com.trans.translation.pojo.User;
import com.trans.translation.pojo.UserGroup;
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
 * T 需要类型化为实体类(Entity)User，ID需要实体类User中Id的类型
 *
 */
public interface UserGroupDao extends JpaRepository<UserGroup,String>,JpaSpecificationExecutor<UserGroup> {

    @Query(value = "SELECT * FROM trans_user_group where groupid = ? and userid=?",nativeQuery = true)
    UserGroup check(String groupid,String userid);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM trans_user_group where userid = ? and groupid=?",nativeQuery = true)
    void deleteByUandG(String userid, String groupid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE trans_user_group Set status = 1 WHERE id = ?",nativeQuery = true)
    int updateStatus(String userGroupId);

    @Query(value = "SELECT new com.trans.translation.pojo.User(tu.id,tu.username,tu.phone,tu.u_status,tu.email,tu.integral,tu.totality,tu.createtime,tu.updatetime) FROM UserGroup tug LEFT JOIN User tu ON tug.userid = tu.id where tug.groupid =:groupid AND tug.status=1")
    List<User> findAllGroupMember(@Param(value = "groupid") String groupid);
}
