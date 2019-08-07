package com.trans.translation.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trans.translation.utils.HttpClientUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class YouDaoApi {

    public static void main(String[] args) throws IOException {

        String url = "http://fanyi.youdao.com/translate";
        Map<String, String> param = new HashMap<>();
        param.put("doctype", "json");
        param.put("type", "AUTO");
        param.put("i", "May you have enough happiness to make you sweet,enough trials to make you strong,enough sorrow to keep you human,enough hope to make you happy? Always put yourself in others’shoes.If you feel that it hurts you,it probably hurts the other person, too.");
        String json = HttpClientUtil.doGet(url, param);

        System.out.println(json);
        StringBuilder builder = new StringBuilder();
        JsonNode arrNode = new ObjectMapper().readTree(json).get("translateResult").get(0);
        for (JsonNode objNode : arrNode) {
            builder.append(objNode.get("tgt").asText());
        }
        System.out.println(builder.toString());


    }
}
