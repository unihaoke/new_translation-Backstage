package com.trans.translation.dao;

import com.trans.translation.pojo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProductDao extends JpaRepository<Product,String>,JpaSpecificationExecutor<Product> {

}
