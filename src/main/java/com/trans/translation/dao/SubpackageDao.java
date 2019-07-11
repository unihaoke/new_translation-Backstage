package com.trans.translation.dao;


import com.trans.translation.pojo.Subpackage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface SubpackageDao extends JpaRepository<Subpackage,String> ,JpaSpecificationExecutor<Subpackage> {

    @Query(value = "SELECT * FROM trans_subpackage WHERE territory =?", nativeQuery = true)
    List<Subpackage> findByTerritory(String territory);

    @Query(value = "SELECT * FROM trans_subpackage WHERE territory =?", nativeQuery = true)
    Page<Subpackage> pageQueryByTerritory(String territory,Pageable pageable);

    @Query(value = "SELECT content FROM trans_subpackage WHERE taskid =? AND section =?", nativeQuery = true)
    String findBySection(String taskId,Integer section);

}
