package com.trans.translation.service.impl;

import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.dao.AuthorityDao;
import com.trans.translation.pojo.Authority;
import com.trans.translation.service.AuthorityService;
import com.trans.translation.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author cwh
 * @date 2020/3/28 17:42
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private AuthorityDao authorityDao;

    /**
     * 添加一条授权记录
     * @param map
     * @return
     */
    @Override
    public Result add(Map<String, String> map,String userId) {

        Authority authority = authorityDao.findByUseridAndTaskid(map.get("userId"),map.get("taskId"));
        if (authority!=null){
            return new Result(false,StatusCode.ERROR,"授权失败，已授权");
        }
        authority = new Authority();
        authority.setGroup_id(map.get("groupId"));
        authority.setTaskid(map.get("taskId"));
        authority.setUserid(map.get("userId"));
        authority.setId(idWorker.nextId()+"");
        authority.setAuthorized_personid(userId);//授权人id
        authorityDao.save(authority);
        return new Result(true,StatusCode.OK,"授权成功");
    }

    /**
     * 获取用户被授权的任务
     * @param userId
     * @return
     */
    @Override
    public Result findTaskByUserId(String userId) {
        return new Result(true,StatusCode.OK,"查询成功",authorityDao.findTaskByUserId(userId));
    }

    /**
     * 查找完成的授权
     * @param userId
     * @return
     */
    @Override
    public Result findFinishAuthority(String userId) {

        return new Result(true,StatusCode.OK,"查询成功",authorityDao.findFinishAuthority(userId));
    }

    /**
     * 取消授权
     * @param map
     * @return
     */
    @Override
    public Result delete(Map<String, String> map) {
        authorityDao.deleteById(map.get("id"));
        return new Result(true,StatusCode.OK,"取消授权");
    }
}
