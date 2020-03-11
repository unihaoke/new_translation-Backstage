package com.trans.translation.service.impl;

import com.trans.translation.common.PageResult;
import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.dao.SubpackageDao;
import com.trans.translation.pojo.Subpackage;
import com.trans.translation.pojo.Translation;
import com.trans.translation.service.RedisService;
import com.trans.translation.service.SubpackageService;
import com.trans.translation.vo.SubpackageVo;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private RedisTemplate redisTemplate;

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
        Page<SubpackageVo> pageData = subpackageDao.findAllSubpackage(pageable);
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
        Page<SubpackageVo> pageData = subpackageDao.pageQueryByTerritory(territory,pageable);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

    @Override
    public Result findByContext(String taskId,Integer section) {
        return new Result(true,StatusCode.OK,"查询成功",subpackageDao.findBySection(taskId,section));
    }

    /**
     * 通过id查找分包信息
     * @param id
     * @return
     */
    @Override
    public Result findById(String id) {
        if(StringUtils.isEmpty(id)){
            return new Result(false,StatusCode.ERROR,"查询失败");
        }
        SubpackageVo subpackageVo = (SubpackageVo) redisTemplate.opsForValue().get("sub"+id);
        if (subpackageVo==null){
            subpackageVo = subpackageDao.findByIdToVo(id);
            redisTemplate.opsForValue().set("sub"+id,subpackageVo);
        }
        return new Result(true,StatusCode.OK,"查询成功",subpackageVo);
    }

    @Override
    public Result findByTaskId(String taskId) {
        if (StringUtils.isEmpty(taskId)){
            return new Result(false,StatusCode.ERROR,"查询失败");
        }
        List<Subpackage> list = subpackageDao.findByTaskId(taskId);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 分包合并
     * @param userId 判断操作用户是否为任务发布者
     * @param taskId 根据taskId将分包译文合并
     * @return
     */
    @Override
    public Result merge(String userId, String taskId) {
        int count = subpackageDao.checkStatus(taskId);//检查是否各个分包任务都已经完成
        if(count>0){
            return new Result(false,StatusCode.ERROR,"还有分包任务未完成");
        }
        List<String> list = subpackageDao.merge(userId,taskId);
        StringBuffer sb = new StringBuffer();
        list.stream().forEach(str->sb.append(str));
        System.out.println(sb.toString());
        return new Result(true,StatusCode.OK,"合并成功",sb.toString());
    }


}
