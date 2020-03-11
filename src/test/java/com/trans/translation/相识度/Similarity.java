package com.trans.translation.相识度;

/**
 * @author cwh
 * @date 2020/3/1 18:23
 */
public class Similarity {
    public static final  String content1="this is my eove";

    public static final  String content2="this is my love";


    public static void main(String[] args) {

        double  score=CosineSimilarity.getSimilarity(content1,content2);
        System.out.println("相似度："+score);

        score=CosineSimilarity.getSimilarity(content1,content1);
        System.out.println("相似度："+score);
    }
}
