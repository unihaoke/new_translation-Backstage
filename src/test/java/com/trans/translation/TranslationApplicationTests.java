package com.trans.translation;

import com.trans.translation.dao.SubpackageDao;
import com.trans.translation.dao.TranslationDao;
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
		Sort sort = new Sort(Sort.Direction.DESC,"createtime");
		List<Translation> translation = translationDao.findByUserid("1",sort);
		for (Translation t:translation) {
			System.out.println(t.getCreatetime());
		}
	}

}
