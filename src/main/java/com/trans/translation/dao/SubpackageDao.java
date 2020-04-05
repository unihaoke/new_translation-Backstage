package com.trans.translation.dao;


import com.trans.translation.pojo.Subpackage;
import com.trans.translation.vo.SubpackageVo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public interface SubpackageDao extends JpaRepository<Subpackage,String> ,JpaSpecificationExecutor<Subpackage> {

    @Query(value = "SELECT new com.trans.translation.vo.SubpackageVo(t1.id,t2.title,t2.t_describe,t2.t_language,t2.territory,t2.deadline,t2.overdue,t1.userid,t1.taskid,t1.product_id,t1.content,t1.section,t1.translation,t1.t_status,t1.count,t1.text_length,t1.createtime) FROM Subpackage t1 LEFT JOIN Product t2 ON t1.product_id = t2.pid WHERE t2.territory=:territory")
    List<SubpackageVo> findByTerritory(@Param("territory")String territory);

    @Query(value = "SELECT new com.trans.translation.vo.SubpackageVo(t1.id,t2.title,t2.t_describe,t2.t_language,t2.territory,t2.deadline,t2.overdue,t1.userid,t1.taskid,t1.product_id,t1.content,t1.section,t1.translation,t1.t_status,t1.count,t1.text_length,t1.createtime) FROM Subpackage t1 LEFT JOIN Product t2 ON t1.product_id = t2.pid WHERE t2.territory=:territory AND t1.id NOT IN (SELECT t3.subpackageid FROM Translation t3 WHERE t3.userid=:userid)",
            countQuery = "SELECT COUNT(t1) from  Subpackage t1 left join Product t2 ON t1.product_id = t2.pid WHERE t2.territory=:territory")
    Page<SubpackageVo> pageQueryByTerritory(@Param("territory")String territory, Pageable pageable,@Param("userid") String userid);

    @Query(value = "SELECT content FROM trans_subpackage WHERE taskid =? AND section =?", nativeQuery = true)
    String findBySection(String taskId,Integer section);

    @Query(value = "SELECT new com.trans.translation.vo.SubpackageVo(t1.id,t2.title,t2.t_describe,t2.t_language,t2.territory,t2.deadline,t2.overdue,t1.userid,t1.taskid,t1.product_id,t1.content,t1.section,t1.translation,t1.t_status,t1.count,t1.text_length,t1.createtime) FROM Subpackage t1 LEFT JOIN Product t2 ON t1.product_id = t2.pid WHERE t1.id NOT IN (SELECT t3.subpackageid FROM Translation t3 WHERE t3.userid=:userid)",
            countQuery = "SELECT COUNT(t1) from  Subpackage t1 left join Product t2 ON t1.product_id = t2.pid")
    Page<SubpackageVo> findAllSubpackage(Pageable pageable,@Param("userid") String userid);

    @Query(value = "SELECT new com.trans.translation.vo.SubpackageVo(t1.id,t2.title,t2.t_describe,t2.t_language,t2.territory,t2.deadline,t2.overdue,t1.userid,t1.taskid,t1.product_id,t1.content,t1.section,t1.translation,t1.t_status,t1.count,t1.text_length,t1.createtime) FROM Subpackage t1 LEFT JOIN Product t2 ON t1.product_id = t2.pid WHERE t1.id=:id")
    SubpackageVo findByIdToVo(@Param("id") String id);

    @Query(value = "SELECT * FROM trans_subpackage WHERE taskid=? ORDER BY section ASC ",nativeQuery = true)
    List<Subpackage> findByTaskId(String taskId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Subpackage SET transid=:transid , translation=:translation , t_status = 1 WHERE id =:id")
    void updateAdopt( @Param("transid")String transid,@Param("translation") String translation,@Param("id")String id);

    @Query(value = "SELECT COUNT(1) FROM trans_subpackage WHERE t_status=0 AND taskid=?",nativeQuery = true)
    int checkStatus(String taskId);

    @Query(value = "SELECT COUNT(1) FROM trans_subpackage WHERE t_status=1 AND taskid=?",nativeQuery = true)
    int checkFinish(String taskId);

    @Query(value = "SELECT translation FROM trans_subpackage WHERE userid = ? AND taskid = ? ORDER BY section ",nativeQuery = true)
    List<String> merge(String userid, String taskid);
}
