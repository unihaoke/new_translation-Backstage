package com.trans.translation.dao;

import com.trans.translation.pojo.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 数据访问接口
 * @author Administrator
 * T 需要类型化为实体类(Entity)User，ID需要实体类User中Id的类型
 *
 */
public interface ApprovalDao extends JpaRepository<Approval,String>,JpaSpecificationExecutor<Approval> {
    Approval findByUseridAndSubpackageid(String userId,String subpackageId);

    @Query(value = "SELECT ta.id,ta.subpackageid,ta.transid,tp.title,ta.createtime,tu.username,t1.translation FROM trans_approval ta LEFT JOIN trans_task tt ON ta.taskid = tt.id LEFT JOIN trans_product tp ON tt.product_id = tp.pid LEFT JOIN trans_user tu ON ta.userid = tu.id LEFT JOIN trans_translation t1 ON ta.subpackageid =  t1.subpackageid WHERE tt.userid = ? AND ta.status = 0 ORDER BY ta.createtime DESC",nativeQuery = true)
    List<Map<String,String>> findByApproval(String userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE trans_approval Set status = 1 WHERE id = ?",nativeQuery = true)
    void updateStatus(String id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE trans_approval Set status = 2 WHERE id = ?",nativeQuery = true)
    void reject(String id);
}
