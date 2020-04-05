package com.trans.translation.dao;

import com.trans.translation.pojo.Translation;
import com.trans.translation.vo.TranslationVo;
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
public interface TranslationDao extends JpaRepository<Translation,String>,JpaSpecificationExecutor<Translation> {

    @Query(value = "SELECT * FROM trans_translation WHERE userid =? AND subpackageid =?", nativeQuery = true)
    Translation findTranslate(String userid, String subpackageid);

    @Query(value = "SELECT new com.trans.translation.vo.TranslationVo(t1.id,t2.title,t2.t_describe,t2.t_language,t2.territory,t2.deadline,t2.overdue,t1.userid,t1.subpackageid,t1.product_id,t1.translator,t1.status,t1.translation,t1.createtime,t1.content,t1.t_length,u1.integral) FROM Translation t1 LEFT JOIN Product t2 ON t1.product_id = t2.pid LEFT JOIN User u1 ON t1.userid=u1.id WHERE t1.subpackageid=:subpackageid ORDER BY t1.status DESC,u1.integral DESC, t1.createtime DESC")
    List<TranslationVo> findBySubpackageId(@Param("subpackageid")String subpackageid);

    @Query(value = "SELECT new com.trans.translation.vo.TranslationVo(t1.id,t2.title,t2.t_describe,t2.t_language,t2.territory,t2.deadline,t2.overdue,t1.userid,t1.subpackageid,t1.product_id,t1.translator,t1.status,t1.translation,t1.createtime,t1.content,t1.t_length) FROM Translation t1 LEFT JOIN Product t2 ON t1.product_id = t2.pid  WHERE t1.userid=:userid ORDER BY t1.createtime DESC")
    List<TranslationVo> findByUserId(@Param("userid")String userid);

    @Modifying
    @Transactional
    @Query(value = "UPDATE trans_translation SET status=1 where id=?",nativeQuery = true)
    void adopt(String id);

    @Query(value = "SELECT COUNT(1) FROM trans_translation WHERE id= ? AND status=1",nativeQuery = true)
    int checkFinish(String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE trans_translation SET translation=? where id=?",nativeQuery = true)
    void update(String translation,String id);
}
