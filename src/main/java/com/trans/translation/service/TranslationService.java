package com.trans.translation.service;

import com.trans.translation.common.Result;
import com.trans.translation.pojo.Translation;

public interface TranslationService {

    Result findAll();

    Result pageQuery( int page, int size);

    Result findByUserId(String userId);

    Result add(Translation translation);

    Result findTranslate(String userId, String subpackageId);
}
