package com.trans.translation.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trans.translation.common.Result;
import com.trans.translation.common.StatusCode;
import com.trans.translation.utils.HttpClientUtil;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.springframework.util.StringUtils;

import java.io.Console;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TranslationApiTest {


    
    public static void main(String[] args) {
        String url = "http://translate.google.cn/translate_a/single";
        Map<String, String> param = new HashMap<>();
        param.put("client", "gtx");
        param.put("dt", "t");
        param.put("dj", "1");
        param.put("ie", "UTF-8");
        param.put("sl", "auto");
        param.put("tl", "zh-CN");
        param.put("q", "The happiest of people don’t necessarily have the best of everything;they just make the most of everything that comes along their way.Happiness lies for those who cry,those who hurt, those who have searched,and those who have tried,for only they can appreciate the importance of people");
        String json = HttpClientUtil.doGet(url, param);
        System.out.println(json);
        JsonNode arrNode = null;
        StringBuilder builder = new StringBuilder();
        try {
            arrNode = new ObjectMapper().readTree(json).get("sentences");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(StringUtils.isEmpty(arrNode)){
            System.out.println("null");
        }
        for (JsonNode objNode : arrNode) {
            builder.append(objNode.get("trans").asText());
        }
        System.out.println(builder);
    }
}
