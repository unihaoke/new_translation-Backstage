package com.trans.translation.test;


import com.trans.translation.dao.TranslationDao;
import com.trans.translation.pojo.Translation;
import com.trans.translation.service.TranslationService;

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
        List<Translation>translation = translationDao.findByUserid("1", sort);
        System.out.println(translation.toString());
    }
}
