package com.trans.translation.dao;

import com.trans.translation.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User> {
    @Query(value = "SELECT count(1) FROM trans_user WHERE id =?", nativeQuery = true)
    public int hasUser(String id);


    /**
     * @Modifying(clearAutomatically = true) 自动清除实体里保存的数据。它更新完数据库后会主动清理一级缓存
     */

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE trans_user SET username=?,email=?,phone=?,updatetime=? WHERE id = ?", nativeQuery = true)
    int update( String username, String email, String phone,Date updatetime, String id);
}
