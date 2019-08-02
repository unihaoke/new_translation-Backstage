package com.trans.translation.test;

import com.trans.translation.dao.SubpackageDao;
import com.trans.translation.vo.SubpackageVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class SubpackageDaoTest {

    @Autowired
    SubpackageDao subpackageDao;

    @Test
    public void test(){
         subpackageDao.findByTerritory("计算机");
    }
}
