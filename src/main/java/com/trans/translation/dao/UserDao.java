package com.trans.translation.dao;

import com.trans.translation.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User> {
    @Query(value = "SELECT count(1) FROM trans_user WHERE id =?", nativeQuery = true)
    public int hasUser(String id);

}
