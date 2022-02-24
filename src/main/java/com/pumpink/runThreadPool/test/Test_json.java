package com.pumpink.runThreadPool.test;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pumpink.runThreadPool.bean.UrlParam;
import com.pumpink.runThreadPool.requestService.HttpRequest;
import com.pumpink.runThreadPool.utils.HeaderParmterHandle;
import io.restassured.response.Response;

import java.util.*;

public class Test_json {

    public static StringBuffer getAllKey(JSONObject jsonObject) {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer sb  = new StringBuffer();
        Iterator<String> keys = jsonObject.keySet().iterator();// jsonObject.keys();

        while (keys.hasNext()) {
            String key = keys.next();
            stringBuffer.append(key.toString()).append("=");
            if (jsonObject.get(key) instanceof JSONObject) {
                JSONObject innerObject = (JSONObject) jsonObject.get(key);
                stringBuffer.append(getAllKey(innerObject));
            } else if (jsonObject.get(key) instanceof JSONArray) {
                JSONArray innerObject = (JSONArray) jsonObject.get(key);
                stringBuffer.append(getAllKey(innerObject));
            }
        }

        return stringBuffer;
    }

    public static StringBuffer getAllKey(JSONArray json1) {
        StringBuffer stringBuffer = new StringBuffer();
        if (json1 != null ) {
            Iterator i1 = json1.iterator();
            while (i1.hasNext()) {
                Object key = i1.next();
                if (key instanceof  JSONObject) {
                    JSONObject innerObject = (JSONObject) key;
                    stringBuffer.append(getAllKey(innerObject));
                } else if (key instanceof JSONArray) {
                    JSONArray innerObject = (JSONArray) key;
                    stringBuffer.append(getAllKey(innerObject));
                }else{
                }
            }
        }
        return stringBuffer;
    }

    private final static String st1 = "{\"username\":\"tom\",\"age\":18,\"address\":[{\"province\":\"上海市\"},{\"city\":\"上海市\"},{\"disrtict\":\"静安区\"}]}";
    private final static String st2 = "{username:\"tom\",age:18}";

    public static void main(String[] args) {
        HttpRequest httpRequest = new HttpRequest();
        UrlParam urlParam = new UrlParam();
        urlParam.setUrl("https://dev-environmental.vcinema.cn:8888/realtime/get_terminal_logs.json?user_id=3977&password=zhuangxulin&date=2021-09-06");
        Map<String, String> headerMap = HeaderParmterHandle.handlHeadMap("11");
        urlParam.setHeaderMap(headerMap);
        Response method = httpRequest.getMethod(urlParam);

//        System.out.println(method.asString());
        JSONObject jsonObject1 = JSONObject.parseObject(method.asString());
        StringBuffer stb = getAllKey(jsonObject1);
        System.err.println(stb);

    }


}