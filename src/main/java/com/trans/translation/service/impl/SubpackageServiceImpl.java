package com.trans.translation.service.impl;

import com.trans.translation.common.PageResult;
import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.dao.SubpackageDao;
import com.trans.translation.dao.TaskDao;
import com.trans.translation.pojo.Subpackage;
import com.trans.translation.pojo.Translation;
import com.trans.translation.service.RedisService;
import com.trans.translation.service.SubpackageService;
import com.trans.translation.utils.IdWorker;
import com.trans.translation.vo.SubpackageVo;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.*;
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

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Value("${filePath}")
    private String filePath;

    @Autowired
    private TaskDao taskDao;

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
    public Result pageQuery(int page, int size,String userId) {
        Sort sort = new Sort(Sort.Direction.DESC,"createtime");
        Pageable pageable = PageRequest.of(page-1, size,sort);
        Page<SubpackageVo> pageData = subpackageDao.findAllSubpackage(pageable,userId);
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
    public Result pageQueryByTerritory(int page, int size, String territory,String userId) {
        Sort sort = new Sort(Sort.Direction.DESC,"createtime");
        Pageable pageable = PageRequest.of(page-1, size,sort);
        Page<SubpackageVo> pageData = subpackageDao.pageQueryByTerritory(territory,pageable,userId);
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
//        SubpackageVo subpackageVo = (SubpackageVo) redisTemplate.opsForValue().get("sub"+id);
//        if (subpackageVo==null){
//            subpackageVo = subpackageDao.findByIdToVo(id);
//            redisTemplate.opsForValue().set("sub"+id,subpackageVo);
//        }
        return new Result(true,StatusCode.OK,"查询成功",subpackageDao.findByIdToVo(id));
    }

    /**
     * 通过任务id获取分包信息
     * @param taskId
     * @return
     */
    @Override
    public Result findByTaskId(String taskId) {
        if (StringUtils.isEmpty(taskId)){
            return new Result(false,StatusCode.ERROR,"查询失败");
        }
        List<Subpackage> list = subpackageDao.findByTaskId(taskId);
        int count = subpackageDao.checkFinish(taskId);
        return new Result(true,StatusCode.OK,count+"",list);
    }

    /**
     * 分包合并
     * @param userId 判断操作用户是否为任务发布者
     * @param taskId 根据taskId将分包译文合并
     * @return
     */
    @Override
    public String merge(String userId, String taskId) {
        List<String> list = subpackageDao.merge(userId,taskId);
        StringBuffer sb = new StringBuffer();
        list.stream().forEach(str->sb.append(str+"\r\n"));
        String fileName = idWorker.nextId()+".doc";
        String path = filePath+fileName;
        //将字符串写入文档中
        BufferedOutputStream out = null;
        File file = new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            out =  new BufferedOutputStream(fos);
            out.write(sb.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.flush();
                out.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //更新任务中翻译文件
        taskDao.updateMergeFile(path,taskId);
        return path;
    }

    /**
     * 检查是否有分包任务没有完成
     * @param taskId
     * @return
     */
    @Override
    public int checkMerge(String taskId) {
        return subpackageDao.checkStatus(taskId);//检查是否各个分包任务都已经完成;
    }



}
