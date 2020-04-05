package com.trans.translation.service.impl;

import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.dao.ApprovalDao;
import com.trans.translation.dao.SubpackageDao;
import com.trans.translation.dao.TranslationDao;
import com.trans.translation.dao.UserDao;
import com.trans.translation.pojo.Approval;
import com.trans.translation.service.ApprovalService;
import com.trans.translation.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author cwh
 * @date 2020/3/29 0:35
 */
@Service
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    private ApprovalDao approvalDao;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private TranslationDao translationDao;
    @Autowired
    private SubpackageDao subpackageDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Result add(Map<String, String> map, String userId) {
        Approval approval = approvalDao.findByUseridAndSubpackageid(userId,map.get("subpackageId"));
        if (approval!=null){
            if (approval.getStatus()==0){
                return new Result(false,StatusCode.ERROR,"等待组长审核中");
            }else if (approval.getStatus()==1){
                return new Result(false,StatusCode.ERROR,"已通过审核");
            }else{
                return new Result(false,StatusCode.ERROR,"审核不通过");
            }

        }
        approval = new Approval();
        approval.setCreatetime(new Date());
        approval.setGroupid(map.get("groupId"));
        approval.setStatus(0);
        approval.setSubpackageid(map.get("subpackageId"));
        approval.setUserid(userId);
        approval.setTaskid(map.get("taskId"));
        approval.setTransid(map.get("transId"));
        approval.setTransid(map.get("translation"));
        approval.setId(idWorker.nextId()+"");
        approvalDao.save(approval);
        return new Result(true,StatusCode.OK,"采纳成功，等待组长审核");
    }

    /**
     * 通过用户id获取等待审核的译文
     * @param userId
     * @return
     */
    @Override
    public Result findByApproval(String userId) {
        return new Result(true,StatusCode.OK,"查询成功",approvalDao.findByApproval(userId));
    }

    /**
     * 审核通过
     * @param map
     * @return
     */
    @Override
    public Result agree(Map<String, String> map) {
        approvalDao.updateStatus(map.get("id"));
        translationDao.adopt(map.get("transId"));
        subpackageDao.updateAdopt(map.get("transId"),map.get("translation"),map.get("subpackageId"));
        userDao.addIntegralBytransId(map.get("transId"));//增加用户积分
        return new Result(true,StatusCode.OK,"审批通过");
    }

    /**
     * 审核拒绝
     * @param map
     * @return
     */
    @Override
    public Result reject(Map<String, String> map) {
        approvalDao.reject(map.get("id"));
        return new Result(true,StatusCode.OK,"审批不通过");
    }
}
