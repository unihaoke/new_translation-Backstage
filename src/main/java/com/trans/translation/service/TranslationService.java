package com.trans.translation.service;

import com.trans.translation.common.Result;
import com.trans.translation.pojo.Translation;

import java.util.Map;

public interface TranslationService {

    Result findAll();

    Result pageQuery( int page, int size);

    Result findByUserId(String userId);

    Result add(Translation translation);

    Result findTranslate(String userId, String subpackageId);

    Result translateText(String text);

    String GooleTranslate(String tl, String text);

    Result findBySubpackageId(String subpackageId);

    Result textSimilarity(Map<String,String> map);

    Result adopt(Map<String,String> map);
}
