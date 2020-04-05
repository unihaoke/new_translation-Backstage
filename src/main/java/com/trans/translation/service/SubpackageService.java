package com.trans.translation.service;

import com.trans.translation.common.Result;

public interface SubpackageService {
    Result findAll();

    Result pageQuery(int page, int size,String userId);

    Result findByTerritory(String territory);

    Result pageQueryByTerritory(int page, int size, String territory,String userId);

    Result findByContext(String taskId,Integer section);

    Result findById(String id);

    Result findByTaskId(String taskId);

    String merge(String userId, String taskId);

    int checkMerge(String taskId);

}
