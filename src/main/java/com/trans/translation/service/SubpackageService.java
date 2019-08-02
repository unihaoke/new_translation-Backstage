package com.trans.translation.service;

import com.trans.translation.common.Result;

public interface SubpackageService {
    Result findAll();

    Result pageQuery(int page, int size);

    Result findByTerritory(String territory);

    Result pageQueryByTerritory(int page, int size, String territory);

    Result findByContext(String taskId,Integer section);

    Result findById(String id);
}
