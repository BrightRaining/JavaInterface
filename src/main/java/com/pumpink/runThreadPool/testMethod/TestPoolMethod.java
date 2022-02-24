package com.pumpink.runThreadPool.testMethod;

import com.alibaba.fastjson.JSON;
import com.pumpink.demo.utils.CheckResponseValue;
import com.pumpink.demo.utils.LoggerUtil;

import com.pumpink.runThreadPool.bean.RequestParam;
import com.pumpink.runThreadPool.bean.UrlParam;
import com.pumpink.runThreadPool.requestService.HttpRequest;
import com.pumpink.runThreadPool.utils.HeaderParmterHandle;
import io.restassured.response.Response;


import java.util.*;

/**
 * 提供外部的线程并发方法
 */

public class TestPoolMethod {

    //计数器
    static int num = 0;


    /**
     * 抢红包
     * @param requestParam
     */
public  void qiangHongbao(RequestParam requestParam){
        UrlParam urlParam = new UrlParam();
        Map<String, String> map = CheckResponseValue.readFileProperties("user.properties");
        Set<String> strings = map.keySet();
        ArrayList<String> arrayList = new ArrayList<>(strings);
        Map<String, String> hedeMap = HeaderParmterHandle.handlHeadMap(arrayList.get(num));
        urlParam.setHeaderMap(hedeMap);
        urlParam.setUrl(requestParam.getEnvPort()+"/v5.0/red_packet/receive_red_packet"+"?user_id="+arrayList.get(num)+"&red_packet_id="+requestParam.getPackId()+"&channel_id="+requestParam.getChannelId());
        num++;
        HttpRequest httpRequest = new HttpRequest();
        Response response = httpRequest.postMethod(urlParam);
        num = 0;
        }



public void searchHot(RequestParam requestParam){
        Map<String, String> map = HeaderParmterHandle.handlHeadMap("7952722");
        UrlParam params = new UrlParam();
        params.setHeaderMap(map);
        params.setUrl("https://dev-environmental.vcinema.cn:1001/v5.0/search/hot");
        HttpRequest httpRequest = new HttpRequest();
        Response rs = httpRequest.getMethod(params);
        String result = rs.asString();
        }

/**
 * 发送南瓜籽
 */
public void sendNanGuaZi(){
        UrlParam params = new UrlParam();
        Map<String,String> map = new HashMap<>();
        map.put("ips","172.0.0.1");
        map.put("userId","5903");
        params.setHeaderMap(map);
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("user_id","5903");
        bodyMap.put("channel_id","lc2-f01cn74ww6");
        bodyMap.put("emoji_id","castle");
        bodyMap.put("emoji_img","https://s-image.vcinema.cn/lb_gift/castle.png");
        params.setBodyMap(bodyMap);
        params.setUrl("https://dev-environmental.vcinema.cn:1001/v5.0/pumpkin_online/send_emoji_v2");
        HttpRequest httpRequest = new HttpRequest();
        Response response = httpRequest.postMethod(params);
        String s = JSON.toJSONString(response.asString());
//        LoggerUtil.info("发送南瓜籽1"+s);
        LoggerUtil.info(s);

        }


}