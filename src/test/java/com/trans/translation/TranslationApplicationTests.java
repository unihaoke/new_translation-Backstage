package com.trans.translation;

import com.trans.translation.dao.ProductDao;
import com.trans.translation.dao.SubpackageDao;
import com.trans.translation.dao.TranslationDao;
import com.trans.translation.pojo.Product;
import com.trans.translation.pojo.Subpackage;
import com.trans.translation.pojo.Translation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TranslationApplicationTests {

	@Autowired
	private SubpackageDao subpackageDao;

	@Autowired
	private TranslationDao translationDao;


	@Test
	public void contextLoads() {


	}

}
