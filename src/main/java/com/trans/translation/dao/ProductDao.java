package com.trans.translation.dao;

import com.trans.translation.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProductDao extends JpaRepository<Product,String>,JpaSpecificationExecutor<Product> {

    @Query(value = "SELECT t1.* FROM trans_product t1,trans_translation t2 WHERE t2.userid=? AND t1.pid=t2.product_id ORDER BY t2.createtime DESC", nativeQuery = true)
    public List<Product> findProductByUserId(String userId);

    @Query(value = "SELECT t1.* FROM trans_product t1,trans_task t2 WHERE t2.userid=? AND t1.pid=t2.product_id ORDER BY t2.createtime DESC", nativeQuery = true)
    public List<Product> findTaskByUserId(String userId);


    @Modifying
    @Transactional
    @Query(value = "UPDATE trans_product SET overdue=1 where unix_timestamp(now())>=unix_timestamp(deadline) AND overdue!=1", nativeQuery=true)
    int updateStatusByDateline();
}
