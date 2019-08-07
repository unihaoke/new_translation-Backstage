package com.trans.translation.test;


import com.trans.translation.dao.TranslationDao;
import com.trans.translation.pojo.Translation;
import com.trans.translation.service.TranslationService;

import com.trans.translation.vo.TranslationVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TranslationTest {

    @Autowired
    TranslationService translationService1;

    @Autowired
    TranslationDao translationDao;

    @Test
    public void test(){
        Sort sort = new Sort(Sort.Direction.DESC,"createtime");
        List<TranslationVo>translation = translationDao.findBySubpackageId("1158751055836745728");
        System.out.println(translation.get(0).getTranslation());
    }
}
