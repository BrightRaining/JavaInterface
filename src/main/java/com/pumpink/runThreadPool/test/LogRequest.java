package com.pumpink.runThreadPool.test;

import com.alibaba.fastjson.JSON;
import com.pumpink.demo.utils.LoggerUtil;
import com.pumpink.runThreadPool.bean.UrlParam;
import com.pumpink.runThreadPool.requestService.HttpRequest;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class LogRequest {

    public static void main(String[] args) {
        HttpRequest httpRequest = new HttpRequest();
        UrlParam param = new UrlParam();
        Map<String,String> map = new HashMap<>();
        map.put("ips","172.0.0.1");
        param.setHeaderMap(map);
        //param.setUrl("https://dev-environmental.vcinema.cn:8888/realtime/get_terminal_logs.json?user_id=7953005&password=zhuangxulin&date=2021-09-14");
        param.setUrl("http://p.doras.log.vcinema.cn/realtime/get_terminal_logs.json?user_id=48267971&password=zhuangxulin&date=2021-09-23");
        Response method = httpRequest.getMethod(param);
//        LoggerUtil.info("哈哈哈"+method.asString());
        System.out.println(JSON.toJSONString(method.asString()));
    }

}
