package com.pumpink.runThreadPool.test;

import com.alibaba.fastjson.JSON;
import com.pumpink.demo.bean.UrlParams;
import com.pumpink.demo.service.impl.RestAssuredUtilsImpl;
import com.pumpink.demo.service.interfaces.RestAssuredUtilsInf;
import com.pumpink.demo.utils.CheckResponseValue;
import com.pumpink.demo.utils.LoggerUtil;
import com.pumpink.runThreadPool.bean.UrlParam;
import com.pumpink.runThreadPool.requestService.HttpRequest;
import com.pumpink.runThreadPool.utils.HeaderParmterHandle;
import io.restassured.response.Response;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QiangHongBao {

    /**
     * 制造线程数还与电脑配置有关系目前线程数在50误差在1s随着线程数的增多误差也会增多 -100以后就需要放在服务器上
     */
    static int num = 0;    //访问失败次数
    static int ThreadNum = 6;   //定义线程数

    //发令枪
   static CountDownLatch countDownLatch = new CountDownLatch(ThreadNum);

    public static void main(String[] args) {
       new QiangHongBao().runThread();
    }

    //将线程放入线程池提交
    public  void runThread(){
        ExecutorService executorService = Executors.newFixedThreadPool(ThreadNum);
        for (int i = 0; i < ThreadNum; i++) {
            executorService.submit(buildThread());
            executorService.execute(buildThread());
        }
    }

    public Thread buildThread(){
        //创建线程
        Thread thread = new Thread(new Runnable() {
            public void run() {
                synchronized (countDownLatch) {  //这一步不知道有没有必要，但是我还是加了
                    //发令枪减1
                    countDownLatch.countDown();
                }

                try {
                   LoggerUtil.info("线程：" + Thread.currentThread().getName() + "准备");
                    //线程阻塞
                    countDownLatch.await();
                    //这里进行请求方法的塞入
                    qiangHongbao("61387ddf80261f39a215b299","lc2-i1is052g02");
                    //countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        return thread;
 }


    /**
     * 抢红包
     * @param
     * @param packId
     * @param channelId
     */
    public  void qiangHongbao(String packId,String channelId){
        UrlParam urlParam = new UrlParam();
        Map<String, String> map = CheckResponseValue.readFileProperties("user.properties");

        Set<String> strings = map.keySet();
        ArrayList<String> arrayList = new ArrayList<>(strings);
        Map<String, String> hedeMap = HeaderParmterHandle.handlHeadMap(arrayList.get(num));
//        Map<String, String> hedeMap = HeaderParmterHandle.handlHeadMap("3631");
        urlParam.setHeaderMap(hedeMap);
        urlParam.setUrl("https://dev-environmental.vcinema.cn:1002/v5.0/red_packet/receive_red_packet"+"?user_id="+arrayList.get(num)+"&red_packet_id="+packId+"&channel_id="+channelId);
//        urlParam.setUrl("https://dev-environmental.vcinema.cn:1002/v5.0/red_packet/receive_red_packet"+"?user_id=3631&red_packet_id="+packId+"&channel_id="+channelId);
        num++;
        HttpRequest httpRequest = new HttpRequest();
        Response response = httpRequest.postMethod(urlParam);
        String s = response.asString();
    }


    /**
     * 发送南瓜籽
     */
    public void sendNanGuaZi(){

        UrlParams params = new UrlParams();

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
        RestAssuredUtilsInf assuredUtilsInf =new RestAssuredUtilsImpl();
        Response response = assuredUtilsInf.postMethod(params);
        String s = JSON.toJSONString(response.asString());
//        LoggerUtil.info("发送南瓜籽1"+s);
        LoggerUtil.info("hahah");



    }



}
