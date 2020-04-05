package com.trans.translation.service;

import com.trans.translation.common.Result;

import java.util.Map;

public interface GroupService {
    Result create(String userId, Map<String,String> map);

    Result join(String userId, Map<String,String> map);

    Result kickOut(String userId,Map<String,String> map);

    Result exit(String userId, Map<String,String> map);

    Result agreeAudit(String userId, Map<String,String> map);

    Result findAllGroup(String userId);

    Result findAllMemberGroup(String userId);

    Result findAllMember(String groupId);

    Result findAllAudit(String userId,String groupId);

    Result rejectAudit(String userId, Map<String,String> map);

    Result findLeaderById(String groupId);
}
