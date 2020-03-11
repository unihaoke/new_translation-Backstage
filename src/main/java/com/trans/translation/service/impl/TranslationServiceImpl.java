package com.trans.translation.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trans.translation.common.PageResult;
import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.controller.BaseExceptionHandler;
import com.trans.translation.dao.ProductDao;
import com.trans.translation.dao.SubpackageDao;
import com.trans.translation.dao.TranslationDao;
import com.trans.translation.dao.UserDao;
import com.trans.translation.pojo.Translation;
import com.trans.translation.service.TranslationService;
import com.trans.translation.utils.CosineSimilarity;
import com.trans.translation.utils.HttpClientUtil;
import com.trans.translation.utils.IdWorker;
import com.trans.translation.vo.SubpackageVo;
import com.trans.translation.vo.TranslationVo;
import jdk.net.SocketFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
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
    private RedisTemplate redisTemplate;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private SubpackageDao subpackageDao;

    @Autowired
    private UserDao userDao;

    private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);


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
        String transId = idWorker.nextId()+"";
        translation.setId(transId);
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
    public String GooleTranslate(String tl,String text){
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
            logger.error("json解析失败");
        }
        for (JsonNode objNode : arrNode) {
            trans.append(objNode.get("trans").asText());
        }
        return trans.toString();
    }

    @Override
    public Result findBySubpackageId(String subpackageId) {
        if (StringUtils.isEmpty(subpackageId)){
            return new Result(false, StatusCode.ERROR, "subpackageId为空");
        }
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        return new Result(true,StatusCode.OK,"查询成功",translationDao.findBySubpackageId(subpackageId));
    }

    /**
     * 文本相似度检测
     * @param map
     * @return
     */
    @Override
    public Result textSimilarity(Map<String, String> map) {
        String goole_trans = GooleTranslate(map.get("tl"),map.get("text"));
        double  score=CosineSimilarity.getSimilarity(goole_trans,map.get("trans"))*100;
        System.out.println(score);
        if (score>90){//文本相似度大于95.55时则认为存在抄袭
            return new Result(false,StatusCode.ERROR,"您的译文与goole翻译的译文相似度过高，请您重新检查后提交");
        }
        //检查提交的译文与其他用户的译文相似度
        List<TranslationVo> list = translationDao.findBySubpackageId(map.get("subpackageId"));
        if (list!=null){
            for (TranslationVo vo:list){
                score=CosineSimilarity.getSimilarity(vo.getTranslation(),map.get("trans"))*100;
                System.out.println(vo.getTranslation());
                if (score>90)return new Result(false,StatusCode.ERROR,"您的译文与其他用户翻译的译文相似度过高，请您重新检查后提交");
            }
        }
        return new Result(true,StatusCode.OK,"检测通过，正在提交中");
    }

    /**
     * 采纳译文
     * @param map
     * @return
     */
    @Override
    public Result adopt(Map<String, String> map) {
        //将该译文id和译文存入分包任务中
        subpackageDao.updateAdopt(map.get("transId"),map.get("trans_text"),map.get("subpackageid"));
        SubpackageVo subpackageVo = subpackageDao.findByIdToVo(map.get("subpackageid"));
        redisTemplate.delete("sub"+map.get("subpackageid"));
        redisTemplate.opsForValue().set("sub"+map.get("subpackageid"),subpackageVo);
        //将对应的译文状态改为被采纳
        translationDao.adopt(map.get("transId"));
        //TODO: 2020/3/2  增加用户的积分
        userDao.addIntegral(map.get("userId"));
        return new Result(true,StatusCode.OK,"采纳成功");
    }

}
