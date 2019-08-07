package com.trans.translation.dao;


import com.trans.translation.pojo.Subpackage;
import com.trans.translation.vo.SubpackageVo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface SubpackageDao extends JpaRepository<Subpackage,String> ,JpaSpecificationExecutor<Subpackage> {

    @Query(value = "SELECT new com.trans.translation.vo.SubpackageVo(t1.id,t2.title,t2.t_describe,t2.t_language,t2.territory,t2.deadline,t2.overdue,t1.userid,t1.taskid,t1.product_id,t1.content,t1.section,t1.translation,t1.t_status,t1.count,t1.text_length) FROM Subpackage t1 LEFT JOIN Product t2 ON t1.product_id = t2.pid WHERE t2.territory=:territory")
    List<SubpackageVo> findByTerritory(@Param("territory")String territory);

    @Query(value = "SELECT new com.trans.translation.vo.SubpackageVo(t1.id,t2.title,t2.t_describe,t2.t_language,t2.territory,t2.deadline,t2.overdue,t1.userid,t1.taskid,t1.product_id,t1.content,t1.section,t1.translation,t1.t_status,t1.count,t1.text_length) FROM Subpackage t1 LEFT JOIN Product t2 ON t1.product_id = t2.pid WHERE t2.territory=:territory",
            countQuery = "SELECT COUNT(t1) from  Subpackage t1 left join Product t2 ON t1.product_id = t2.pid WHERE t2.territory=:territory")
    Page<SubpackageVo> pageQueryByTerritory(@Param("territory")String territory, Pageable pageable);

    @Query(value = "SELECT content FROM trans_subpackage WHERE taskid =? AND section =?", nativeQuery = true)
    String findBySection(String taskId,Integer section);

    @Query(value = "SELECT new com.trans.translation.vo.SubpackageVo(t1.id,t2.title,t2.t_describe,t2.t_language,t2.territory,t2.deadline,t2.overdue,t1.userid,t1.taskid,t1.product_id,t1.content,t1.section,t1.translation,t1.t_status,t1.count,t1.text_length) FROM Subpackage t1 LEFT JOIN Product t2 ON t1.product_id = t2.pid",
            countQuery = "SELECT COUNT(t1) from  Subpackage t1 left join Product t2 ON t1.product_id = t2.pid")
    Page<SubpackageVo> findAllSubpackage(Pageable pageable);

    @Query(value = "SELECT new com.trans.translation.vo.SubpackageVo(t1.id,t2.title,t2.t_describe,t2.t_language,t2.territory,t2.deadline,t2.overdue,t1.userid,t1.taskid,t1.product_id,t1.content,t1.section,t1.translation,t1.t_status,t1.count,t1.text_length) FROM Subpackage t1 LEFT JOIN Product t2 ON t1.product_id = t2.pid WHERE t1.id=:id")
    SubpackageVo findByIdToVo(@Param("id") String id);
}
