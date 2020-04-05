package com.trans.translation.service;

import com.trans.translation.common.Result;

import java.util.Map;

public interface AuthorityService {
    Result add(Map<String,String> map,String userId);

    Result findTaskByUserId(String userId);

    Result findFinishAuthority(String userId);

    Result delete(Map<String,String> map);
}
