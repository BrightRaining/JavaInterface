package com.pumpink.runThreadPool.utils;

import java.util.HashMap;
import java.util.Map;

public class HeaderParmterHandle {

    /**
     * 填充请求头
     * @param userId
     * @return
     */
    public static Map<String,String> handlHeadMap(String userId){
        Map<String,String> map = new HashMap<>();
        if(userId.contains("-")){
            String replace = userId.replace("-", "");
            map.put("userId",replace);
        }else {
            map.put("userId",userId);
        }
        map.put("format", "json");
        map.put("platform", "1");
        map.put("ips","172.0.0.1");

//        map.put("api_version", headerClass.getApiVersion());
//        map.put("app_version", headerClass.getAppVersion());
//        map.put("cid", headerClass.getCid());
//        map.put("device_id",headerClass.getDeviceId());
//        map.put("device_info",headerClass.getDeviceInfo());
//        map.put("device_type",headerClass.getDeviceType());

        return map;
    }



}
