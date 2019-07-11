package com.trans.translation.service.impl;

import com.trans.translation.common.PageResult;
import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.dao.SubpackageDao;
import com.trans.translation.pojo.Subpackage;
import com.trans.translation.pojo.Translation;
import com.trans.translation.service.SubpackageService;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 服务层
 *
 * @author Administrator
 *
 */
@Service
@Transactional
public class SubpackageServiceImpl implements SubpackageService {

    @Autowired
    private SubpackageDao subpackageDao;

    @Override
    public Result findAll() {
        Sort sort = new Sort(Sort.Direction.DESC,"createtime");
        return new Result(true,StatusCode.OK,"查询成功",subpackageDao.findAll(sort));
    }

    /**
     * 分页查找全部记录
     * @param page
     * @param size
     * @return
     */
    @Override
    public Result pageQuery(int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC,"createtime");
        Pageable pageable = PageRequest.of(page-1, size,sort);
        Page<Subpackage> pageData = subpackageDao.findAll(pageable);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

    @Override
    public Result findByTerritory(String territory) {
        return new Result(true,StatusCode.OK,"查询成功",subpackageDao.findByTerritory(territory));
    }

    /**
     * 分页查找匹配某个领域的任务
     * @param page
     * @param size
     * @param territory
     * @return
     */
    @Override
    public Result pageQueryByTerritory(int page, int size, String territory) {
        Sort sort = new Sort(Sort.Direction.DESC,"createtime");
        Pageable pageable = PageRequest.of(page-1, size,sort);
        Page<Subpackage> pageData = subpackageDao.pageQueryByTerritory(territory,pageable);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

    @Override
    public Result findByContext(String taskId,Integer section) {
        return new Result(true,StatusCode.OK,"查询成功",subpackageDao.findBySection(taskId,section));
    }


}
