package com.pumpink.runThreadPool.test;

import com.alibaba.fastjson.JSON;
import com.pumpink.demo.utils.LoggerUtil;
import com.pumpink.runThreadPool.bean.UrlParam;
import com.pumpink.runThreadPool.requestService.HttpRequest;
import com.pumpink.runThreadPool.test.pojo.Content;
import com.pumpink.runThreadPool.test.pojo.Data;
import com.pumpink.runThreadPool.test.pojo.HallAll;
import com.pumpink.runThreadPool.utils.HeaderParmterHandle;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class GetReBao {

   static int nums = 0;

    public static void main(String[] args) {
        for (int i = 1; i <= 20; i++) {
            getHall(i);
        }

    }

    public static void getHall(int num){
        UrlParam urlParam = new UrlParam();
        System.out.println("请求路径："+"https://beta-api.vcinema.cn/v5.0/pumpkin_online/get_channels_by_type_v3?channel_type=1&search_key=&page_num="+num+"&page_size=30&epg_status=1");
        urlParam.setUrl("https://beta-api.vcinema.cn/v5.0/pumpkin_online/get_channels_by_type_v3?channel_type=1&search_key=&page_num="+num+"&page_size=30&epg_status=1");
        Map<String, String> map = HeaderParmterHandle.handlHeadMap("48667240");
        urlParam.setHeaderMap(map);
        HttpRequest httpRequest = new HttpRequest();
        Response method = httpRequest.getMethod(urlParam);
        String s = method.asString();
        HallAll hallAll = JSON.parseObject(s, HallAll.class);

        Content content = hallAll.getContent();
        if(content != null){
            List<Data> data = content.getData();
            if(data!=null){
                for (Data datum : data) {
                    String channel_id = datum.getChannel_id();
                    if(channel_id.equals("lc2-7s264rwe10")){
                        LoggerUtil.info("检查到了！"+datum.getChannel_id());
                    }

                }
                int size = data.size();
                nums+=size;
                LoggerUtil.info("当前接口返回的放映厅数量："+size);
            }else {
                System.exit(-1);
            }

        }
        LoggerUtil.info("接口返回的放映厅总数量"+nums);
    }

}
