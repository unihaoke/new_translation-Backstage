package com.trans.translation.dao;

import com.trans.translation.pojo.Translation;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface TranslationDao extends JpaRepository<Translation,String>,JpaSpecificationExecutor<Translation> {


    public List<Translation> findByUserid(String userid, Sort sort);

    @Query(value = "SELECT * FROM trans_translation WHERE userid =? AND subpackageid =?", nativeQuery = true)
    Translation findTranslate(String userid, String subpackageid);

}
