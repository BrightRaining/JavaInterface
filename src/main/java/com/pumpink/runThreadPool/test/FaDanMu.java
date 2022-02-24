package com.pumpink.runThreadPool.test;

import com.alibaba.fastjson.JSON;
import com.pumpink.demo.utils.CheckResponseValue;
import com.pumpink.runThreadPool.bean.UrlParam;
import com.pumpink.runThreadPool.requestService.HttpRequest;
import com.pumpink.runThreadPool.utils.HeaderParmterHandle;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class FaDanMu {




    public static void main(String[] args) {

        Map<String, String> map = CheckResponseValue.readFileProperties("user.properties");

        Set<String> strings = map.keySet();
        ArrayList<String> arrayList = new ArrayList<>(strings);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 25; i++) {
            testAdd(arrayList.get(i));
            hi(arrayList.get(i),i);
            sb.append("\""+arrayList.get(i)+"\",");
        }
        System.out.println(sb.toString());

    }

    public static void hi(String userId,int i){

        UrlParam params = new UrlParam();
        Map<String, String> map = HeaderParmterHandle.handlHeadMap(userId);
        params.setHeaderMap(map);
        params.setUrl("https://dev-environmental.vcinema.cn:1001/v5.0/bullet_screen/add_bullet_screen_for_emoji");
        //{"channel_id":"lc2-hl5vz398i9","content":"困哈哈搜索哦哈","movie_id":"10054","play_length":"858","user_id":1206}
        Map<String, String> bodyMap = params.getBodyMap();
        bodyMap.put("channel_id","lc2-iv4ncqsqss");
        bodyMap.put("content",System.currentTimeMillis()+"");
        bodyMap.put("movie_id","11404");

        bodyMap.put("play_length","199"+i*5);
        bodyMap.put("user_id",userId);
        params.setBodyMap(bodyMap);

        HttpRequest httpRequest = new HttpRequest();
        Response response = httpRequest.postMethod(params);
        String s = response.asString();
        String message = JSON.parseObject(s).getString("message");


    }

    public static void testAdd(String userId){
        UrlParam params = new UrlParam();
        Map<String, String> map = HeaderParmterHandle.handlHeadMap(userId);
        params.setHeaderMap(map);
        params.setUrl("https://dev-environmental.vcinema.cn:1001/v5.0/bullet_screen/add_welcome");

        Map<String, String> bodyMap = params.getBodyMap();
        bodyMap.put("channel_id","lc2-iv4ncqsqss");
        params.setBodyMap(bodyMap);
        params.setData(true);
        HttpRequest httpRequest = new HttpRequest();
        Response response = httpRequest.postMethod(params);
        String s = response.asString();
        String message = JSON.parseObject(s).getString("message");
    }
}
