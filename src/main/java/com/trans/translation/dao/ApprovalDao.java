package com.trans.translation.dao;

import com.trans.translation.pojo.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据访问接口
 * @author Administrator
 * T 需要类型化为实体类(Entity)User，ID需要实体类User中Id的类型
 *
 */
public interface ApprovalDao extends JpaRepository<Approval,String>,JpaSpecificationExecutor<Approval> {

}
