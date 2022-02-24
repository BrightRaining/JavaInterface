package com.pumpink.runThreadPool.taskController;

import com.pumpink.demo.utils.CheckResponseValue;
import com.pumpink.runThreadPool.bean.RequestParam;
import com.pumpink.runThreadPool.bean.UrlParam;
import com.pumpink.runThreadPool.requestService.HttpRequest;
import com.pumpink.runThreadPool.utils.HeaderParmterHandle;
import com.pumpink.runThreadPool.utils.ThreadPoolUtils;
import io.restassured.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@RestController
public class ThreadPoolController {

    static int num = 0;

    @GetMapping("/doPool")
    public String testQiang(RequestParam requestParam){
        checkRunMethod(requestParam);
        return "任务正在处理-请观察APP";
    }


    @GetMapping("/qiangHongBao")
    public void qiangHongBao(RequestParam requestParam){
        String threadNum = requestParam.getThreadNum();
        int i = Integer.parseInt(threadNum);
        for (int j = 0; j < i; j++) {
            qiangHongbao(requestParam.getPackId(),requestParam.getChannelId());

        }
    }

    private   void qiangHongbao(String packId,String channelId){
        UrlParam urlParam = new UrlParam();
        Map<String, String> map = CheckResponseValue.readFileProperties("user.properties");

        Set<String> strings = map.keySet();
        ArrayList<String> arrayList = new ArrayList<>(strings);
        Map<String, String> hedeMap = HeaderParmterHandle.handlHeadMap(arrayList.get(num));
//      Map<String, String> hedeMap = HeaderParmterHandle.handlHeadMap("3631");
        urlParam.setHeaderMap(hedeMap);
        urlParam.setUrl("https://dev-environmental.vcinema.cn:1003/v5.0/red_packet/receive_red_packet"+"?user_id="+arrayList.get(num)+"&red_packet_id="+packId+"&channel_id="+channelId);
//        urlParam.setUrl("https://dev-environmental.vcinema.cn:1002/v5.0/red_packet/receive_red_packet"+"?user_id=3631&red_packet_id="+packId+"&channel_id="+channelId);
        num++;
        HttpRequest httpRequest = new HttpRequest();
        Response response = httpRequest.postMethod(urlParam);
        String s = response.asString();
    }


    /**
     * 方法分发
     * @param requestParam
     */
    private void checkRunMethod(RequestParam requestParam){
       // Map<String, String> threadClass = CheckResponseValue.getThreadClass();
        Map<String, String> map = CheckResponseValue.readFileProperties("threadPoolClass.properties");
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> next = it.next();
            String key = next.getKey();
            if(key.equals(requestParam.getMethodName())){
                String value = next.getValue();
                new ThreadPoolUtils().runThread(key,value,requestParam);
                break;
            }
        }
    }

}
