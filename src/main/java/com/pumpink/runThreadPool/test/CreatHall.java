package com.pumpink.runThreadPool.test;

import com.alibaba.fastjson.JSON;
import com.pumpink.demo.utils.CheckResponseValue;
import com.pumpink.demo.utils.LoggerUtil;
import com.pumpink.runThreadPool.bean.UrlParam;
import com.pumpink.runThreadPool.requestService.HttpRequest;
import com.pumpink.runThreadPool.utils.HeaderParmterHandle;
import io.restassured.response.Response;

import java.util.*;

public class CreatHall {

    static Map<String, String> channelMap = new HashMap<>();
    static Map<String,String> closeHallMap = new HashMap<>();

    public static void main(String[] args) {
       creatHall();
        //closeHall();
    }

    /**
     * 测试环境批量创建放映厅
     */
    public static void creatHall() {

        Map<String, String> map = CheckResponseValue.readFileProperties("userLevevl.properties");
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        int i = 0;
        while (it.hasNext()) {
//            if(i < 3){
                Map.Entry<String, String> next = it.next();
                String userId = next.getKey();

                UrlParam params = new UrlParam();
                Map<String, String> headerMap = HeaderParmterHandle.handlHeadMap(userId);
                params.setHeaderMap(headerMap);

                params.setUrl("https://dev-environmental.vcinema.cn:1001/v5.0/pumpkin_online/create_channel_v2" + "?user_id=" + userId + "&movie_id=" + "10649");
                HttpRequest httpRequest = new HttpRequest();
                Response response = httpRequest.postMethod(params);
                String s = response.asString();
                if (s.contains("user_id")) {
                    String content = JSON.parseObject(s).getString("content");
                    String channel_id = JSON.parseObject(content).getString("channel_id");
                    String user_id = JSON.parseObject(content).getString("user_id");
                    channelMap.put(user_id, channel_id);
                }
                LoggerUtil.info(response.asString());
//            }else{
//                break;
//            }
//            i++;
        }

        //创建完毕后查看共创建的放映厅数量
        if (channelMap != null && channelMap.size() > 0) {
            Iterator<Map.Entry<String, String>> its = channelMap.entrySet().iterator();
            while (its.hasNext()){
                Map.Entry<String, String> entry = its.next();
                String key = entry.getKey();
                String value = entry.getValue();
                LoggerUtil.info("创建放映厅数据:" + key + "=" + value);
            }
        }
        LoggerUtil.info("共创建放映厅:" + channelMap.size());


}

    /**
     * 测试环境批量关闭放映厅
     */
    public static void closeHall(){
        int i = 0;
        Map<String, String> hallMap = CheckResponseValue.readFileProperties("hallInfo.properties");

        Iterator<Map.Entry<String, String>> iterator = hallMap.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<String, String> next = iterator.next();
            String userId = next.getKey();
            String channelId = next.getValue();
            LoggerUtil.info("开始关闭放映厅数据 "+userId+"="+channelId);
            UrlParam urlParams = new UrlParam();
            urlParams.setUrl("https://dev-environmental.vcinema.cn:1001/v5.0/pumpkin_online/dismiss"+"?user_id="+userId+"&channel_id="+channelId);
            Map<String, String> map = HeaderParmterHandle.handlHeadMap(userId);
            urlParams.setHeaderMap(map);
            HttpRequest httpRequest = new HttpRequest();
            Response method = httpRequest.postMethod(urlParams);
            String s = method.asString();
            if(s.contains("true")){
                i++;
            }else {
                closeHallMap.put(userId,channelId);
            }
            LoggerUtil.info("解散放映厅返回数据"+s);
        }
        LoggerUtil.info("一共关闭放映厅数量："+i);
        if(closeHallMap != null && closeHallMap.size() > 0){
            Iterator<Map.Entry<String, String>> its = closeHallMap.entrySet().iterator();
            while (its.hasNext()){
                Map.Entry<String, String> entry = its.next();
                String key = entry.getKey();
                String value = entry.getValue();
                LoggerUtil.info("未关闭成功放映厅数据:" + key + "=" + value);
            }
        }
    }


}
