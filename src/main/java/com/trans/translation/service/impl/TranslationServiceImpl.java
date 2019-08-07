package com.trans.translation.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trans.translation.common.PageResult;
import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.dao.ProductDao;
import com.trans.translation.dao.TranslationDao;
import com.trans.translation.pojo.Translation;
import com.trans.translation.service.TranslationService;
import com.trans.translation.utils.HttpClientUtil;
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
import java.io.IOException;
import java.util.*;

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

    @Autowired
    private ProductDao productDao;

    @Override
    public Result findAll() {
        List<Translation> list = translationDao.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public Result pageQuery(int page, int size) {
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Translation> pageData = translationDao.findAll(pageable);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Translation>(pageData.getTotalElements(), pageData.getContent()));
    }

    @Override
    public Result findByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return new Result(false, StatusCode.ERROR, "查询失败");
        }
        return new Result(true, StatusCode.OK, "查询成功", translationDao.findByUserId(userId));
    }

    @Override
    public Result add(Translation translation) {
        if (translation == null) {
            return new Result(false, StatusCode.ERROR, "添加失败");
        }
        translation.setStatus(0);
        translation.setCreatetime(new Date());
        return new Result(true, StatusCode.OK, "添加成功", translationDao.save(translation));
    }

    @Override
    public Result findTranslate(String userId, String subpackageId) {
        return new Result(true, StatusCode.OK, "查询成功", translationDao.findTranslate(userId, subpackageId));
    }

    /**
     * 请求有道API翻译
     * @param text
     * @return
     */
    @Override
    public Result translateText(String text) {
        String url = "http://fanyi.youdao.com/translate";
        Map<String, String> param = new HashMap<>();
        param.put("doctype", "json");
        param.put("type", "AUTO");
        param.put("i", text);
        String json = HttpClientUtil.doGet(url, param);
        System.out.println(json);
        StringBuilder builder = new StringBuilder();
        JsonNode arrNode = null;
        try {
            arrNode = new ObjectMapper().readTree(json).get("translateResult").get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (JsonNode objNode : arrNode) {
            builder.append(objNode.get("tgt").asText());
        }
        return new Result(true,StatusCode.OK,"翻译请求成功",builder);
    }

    /**
     * 请求Goole翻译API
     * @param text
     * @param tl,当tl为zh-CN时是英译中，当tl为en时是中译英
     * @return
     */
    public Result GooleTranslate(String tl,String text){
        String url = "http://translate.google.cn/translate_a/single";
        Map<String, String> param = new HashMap<>();
        param.put("client", "gtx");
        param.put("dt", "t");
        param.put("dj", "1");
        param.put("ie", "UTF-8");
        param.put("sl", "auto");
        param.put("tl", tl);
        param.put("q", text);
        String json = HttpClientUtil.doGet(url, param);
        System.out.println(json);
        JsonNode arrNode = null;
        StringBuilder trans = new StringBuilder();
        try {
            arrNode = new ObjectMapper().readTree(json).get("sentences");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(StringUtils.isEmpty(arrNode)){
            return new Result(false, StatusCode.ERROR, "查询失败");
        }
        for (JsonNode objNode : arrNode) {
            trans.append(objNode.get("trans").asText());
        }
        return new Result(true,StatusCode.OK,"翻译请求成功",trans);
    }

    @Override
    public Result findBySubpackageId(String subpackageId) {
        if (StringUtils.isEmpty(subpackageId)){
            return new Result(false, StatusCode.ERROR, "subpackageId为空");
        }
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        return new Result(true,StatusCode.OK,"查询成功",translationDao.findBySubpackageId(subpackageId));
    }

}
