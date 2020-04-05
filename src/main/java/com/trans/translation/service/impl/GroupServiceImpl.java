package com.trans.translation.service.impl;

import com.sun.xml.internal.bind.v2.TODO;
import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.dao.GroupDao;
import com.trans.translation.dao.UserGroupDao;
import com.trans.translation.pojo.Group;
import com.trans.translation.pojo.UserGroup;
import com.trans.translation.service.GroupService;
import com.trans.translation.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

/**
 * @author cwh
 * @date 2020/3/11 17:01
 */
@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private UserGroupDao userGroupDao;

    /**
     * 创建小组
     * @param userId
     * @param map
     * @return
     */
    @Override
    public Result create(String userId, Map<String, String> map) {
        Group group = new Group();
        group.setCount(1);
        group.setId(idWorker.nextId()+"");
        group.setGroup_name(map.get("name"));
        group.setCreatetime(new Date());
        group.setUserid(userId);
        groupDao.save(group);
        return new Result(true,StatusCode.OK,"创建成功");
    }

    /**
     * 加入小组
     * @param userId
     * @param map
     * @return
     */
    @Override
    public Result join(String userId, Map<String, String> map) {
        UserGroup userGroup = userGroupDao.check(map.get("groupId"),userId);
        if (userGroup!=null){
            return new Result(false,StatusCode.ERROR,"已加入该小组或正在审核中");
        }
        Optional<Group> group = groupDao.findById(map.get("groupId"));
        if (!group.isPresent()){
            return new Result(false,StatusCode.ERROR,"小组不存在");
        }
        userGroup = new UserGroup();
        userGroup.setGroupid(map.get("groupId"));
        userGroup.setId(idWorker.nextId()+"");
        userGroup.setStatus(0);//审核中，审核通过为1
        userGroup.setUserid(userId);
        userGroup.setPosition("组员");
        userGroup.setJointime(new Date());
        userGroupDao.save(userGroup);
        return new Result(true,StatusCode.OK,"加入成功，等待组长审核");
    }

    /**
     * 踢出小组
     * @param map
     * @return
     */
    @Override
    public Result kickOut(String userId,Map<String, String> map) {
        Optional<Group> optional = groupDao.findById(map.get("groupid"));
        Group group = optional.get();
        if (!userId.equals(group.getUserid())){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }
        userGroupDao.deleteByUandG(map.get("userid"),map.get("groupid"));
        return new Result(true,StatusCode.OK,"踢出成功");
    }

    /**
     * 退出小组
     * @param userId
     * @param map
     * @return
     */
    @Override
    public Result exit(String userId, Map<String, String> map) {
        userGroupDao.deleteByUandG(userId,map.get("groupid"));
        return new Result(true,StatusCode.OK,"退出成功");
    }

    /**
     * 同意加入小组
     * @param userId
     * @param map
     * @return
     */
    @Override
    public Result agreeAudit(String userId, Map<String, String> map) {
        Optional<Group> optional = groupDao.findById(map.get("groupId"));
        Group group = optional.get();
        if (!userId.equals(group.getUserid())){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }
        userGroupDao.updateStatus(map.get("userGroupId"));
        groupDao.updateCount(map.get("groupId"));
        return new Result(true,StatusCode.OK,"操作成功");
    }

    /**
     * 拒绝加入小组
     * @param userId
     * @param map
     * @return
     */
    @Override
    public Result rejectAudit(String userId, Map<String, String> map) {
        Optional<Group> optional = groupDao.findById(map.get("groupId"));
        Group group = optional.get();
        if (!userId.equals(group.getUserid())){
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }
        userGroupDao.deleteById(map.get("userGroupId"));
        return new Result(true,StatusCode.OK,"操作成功");
    }

    /**
     * 通过小组id获取组长信息
     * @param groupId
     * @return
     */
    @Override
    public Result findLeaderById(String groupId) {
        return new Result(true,StatusCode.OK,"查询成功",groupDao.findLeaderById(groupId));
    }


    /**
     * 组长获取所有属于自己的小组
     * @param userId
     * @return
     */
    @Override
    public Result findAllGroup(String userId) {
        return new Result(true,StatusCode.OK,"查询成功",groupDao.findAllGroup(userId));
    }

    /**
     * 查询组员的所有小组
     * @param userId
     * @return
     */
    @Override
    public Result findAllMemberGroup(String userId) {
        return new Result(true,StatusCode.OK,"查询成功",groupDao.findAllMemberGroup(userId));
    }

    /**
     * 获取小组所有组员信息
     * @return
     */
    @Override
    public Result findAllMember(String groupId) {
        return new Result(true,StatusCode.OK,"查询成功",userGroupDao.findAllGroupMember(groupId));
    }

    /**
     * 获取所有加入的请求
     * @param userId
     * @return
     */
    @Override
    public Result findAllAudit(String userId,String groupId) {
        return new Result(true,StatusCode.OK,"查询成功",groupDao.findAllAudit(userId,groupId));
    }




}
