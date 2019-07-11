package com.trans.translation.service.impl;

import com.trans.translation.common.PageResult;
import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.dao.TranslationDao;
import com.trans.translation.pojo.Translation;
import com.trans.translation.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务层
 *
 * @author Administrator
 *
 */
@Service
@Transactional
public class TranslationServiceImpl implements TranslationService {

    @Autowired
    private TranslationDao translationDao;

    @Override
    public Result findAll() {
        List<Translation> list = translationDao.findAll();
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public Result pageQuery( int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC,"createtime");
        Pageable pageable = PageRequest.of(page-1, size,sort);
        Page<Translation> pageData = translationDao.findAll(pageable);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<Translation>(pageData.getTotalElements(),pageData.getContent()));
    }

    @Override
    public Result findByUserId(String userId) {
        if(StringUtils.isEmpty(userId)){
            return new Result(false,StatusCode.ERROR,"查询失败");
        }
        Sort sort = new Sort(Sort.Direction.DESC,"createtime");
        return new Result(true,StatusCode.OK,"查询成功",translationDao.findByUserid(userId,sort));
    }

    @Override
    public Result add(Translation translation) {
        return new Result(true,StatusCode.OK,"添加成功",translationDao.save(translation));
    }

    @Override
    public Result findTranslate(String userId, String subpackageId) {
        return new Result(true,StatusCode.OK,"查询成功",translationDao.findTranslate(userId,subpackageId));
    }
}
