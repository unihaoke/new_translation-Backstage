package com.trans.translation.service;

import com.trans.translation.common.Result;

import java.util.Map;

/**
 * @author cwh
 * @date 2020/3/29 0:34
 */
public interface ApprovalService {

    Result add(Map<String,String> map, String userId);

    Result findByApproval(String userId);

    Result agree(Map<String,String> map);

    Result reject(Map<String,String> map);
}
