package com.pumpink.runThreadPool.test;

import com.pumpink.demo.utils.LoggerUtil;
import com.pumpink.runThreadPool.bean.UrlParam;
import com.pumpink.runThreadPool.requestService.HttpRequest;
import com.pumpink.runThreadPool.utils.HeaderParmterHandle;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class SendRedPack {


    public static void main(String[] args) {
       //sendRedPackType("7952722", "lc2-i1is052g02", "days_all", 5 + "", "5", "0","now", "0");
        praisHallGood("7952722","1","lc2-108vv3w2e5","","","7952722");
    }

    public static void praisHallGood(String ownerId,String thanksType,String channelId,String redPackId,String giftId,String giftUserId){

        UrlParam urlParams = new UrlParam();
        urlParams.setUrl("https://dev-environmental.vcinema.cn:1001/v5.0/pumpkin_online/give_thanks"+"?owner_id="+ownerId+"&thanks_type="+thanksType+"&channel_id="+channelId+"&red_packet_id="+redPackId+"&gift_id="+giftId+"&gift_user_id="+giftUserId);
        Map<String, String> hedeMap = HeaderParmterHandle.handlHeadMap("7952722");
        urlParams.setHeaderMap(hedeMap);
        HttpRequest httpRequest = new HttpRequest();
        Response method = httpRequest.getMethod(urlParams);
        String s = method.asString();
        LoggerUtil.info("赠送礼物"+s);

    }

    public static void sendRedPackType(String userId,String channelId,String packType,String packCount,String recevieCount,String subsidy,String sendType,String time){

        UrlParam urlParam = new UrlParam();
        Map<String, String> hedeMap = HeaderParmterHandle.handlHeadMap(userId);
        urlParam.setHeaderMap(hedeMap);
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("user_id",userId);
        bodyMap.put("channel_id",channelId);
        bodyMap.put("red_packet_type",packType);
        bodyMap.put("red_packet_count",packCount);
        bodyMap.put("red_receive_count",recevieCount);
        //bodyMap.put("subsidy",subsidy);
        bodyMap.put("send_time_type",sendType);
        bodyMap.put("send_time_start",time);
        urlParam.setBodyMap(bodyMap);
        urlParam.setUrl("https://dev-environmental.vcinema.cn:1001/v5.0/red_packet/send_red_packet");
        HttpRequest httpRequest = new HttpRequest();
        Response response = httpRequest.postMethod(urlParam);
        String s = response.asString();


    }

}
