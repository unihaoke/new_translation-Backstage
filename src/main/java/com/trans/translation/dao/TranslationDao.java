package com.trans.translation.dao;

import com.trans.translation.pojo.Translation;
import com.trans.translation.vo.TranslationVo;
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
public interface TranslationDao extends JpaRepository<Translation,String>,JpaSpecificationExecutor<Translation> {

    @Query(value = "SELECT * FROM trans_translation WHERE userid =? AND subpackageid =?", nativeQuery = true)
    Translation findTranslate(String userid, String subpackageid);

    @Query(value = "SELECT new com.trans.translation.vo.TranslationVo(t2.title,t2.t_describe,t2.t_language,t2.territory,t2.deadline,t2.overdue,t1.userid,t1.subpackageid,t1.product_id,t1.translator,t1.status,t1.translation,t1.createtime) FROM Translation t1 LEFT JOIN Product t2 ON t1.product_id = t2.pid WHERE t1.subpackageid=:subpackageid ORDER BY t1.createtime DESC")
    List<TranslationVo> findBySubpackageId(@Param("subpackageid")String subpackageid);

    @Query(value = "SELECT new com.trans.translation.vo.TranslationVo(t2.title,t2.t_describe,t2.t_language,t2.territory,t2.deadline,t2.overdue,t1.userid,t1.subpackageid,t1.product_id,t1.translator,t1.status,t1.translation,t1.createtime) FROM Translation t1 LEFT JOIN Product t2 ON t1.product_id = t2.pid WHERE t1.userid=:userid ORDER BY t1.createtime DESC")
    List<TranslationVo> findByUserId(@Param("userid")String userid);
}
