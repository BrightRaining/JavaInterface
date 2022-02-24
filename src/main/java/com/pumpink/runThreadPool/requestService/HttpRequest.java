package com.pumpink.runThreadPool.requestService;

import com.alibaba.fastjson.JSON;
import com.pumpink.demo.bean.UrlParams;
import com.pumpink.demo.utils.LoggerUtil;
import com.pumpink.runThreadPool.bean.UrlParam;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class HttpRequest {



    /**
     * 配置SSL 让所有请求支持所有的主机名
     */
    public  RequestSpecification config;


    //代码块 加载一次
    {
        config = given().config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())));
    }


    /**
     * get请求
     * @param params 包含需要传输的参数
     * @return
     */
    public Response getMethod(UrlParam params) {


        Response response = null;
        if(params.getHeaderMap() != null){
            //将map值转换成list集成的header
            List<Header> list = halderMapData(params.getHeaderMap());
            //批量添加进入请求和请求参数
            RequestSpecification specification = handleQuestHeaderBody(list, params);
            LoggerUtil.info("请求头："+params.getHeaderMap().toString());
            LoggerUtil.info("请求前准备"+System.currentTimeMillis());
            response = specification.get(params.getUrl());
            LoggerUtil.info("请求后数据"+response.asString()+"  "+System.currentTimeMillis());

        }else {
            LoggerUtil.info("请求头："+params.getHeaderMap().toString());
            LoggerUtil.info("请求前准备"+System.currentTimeMillis());
            response = config.get(params.getUrl());
            LoggerUtil.info("请求后数据"+response.asString()+"  "+System.currentTimeMillis());
        }
        config = given().config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())));

        return response;
    }

    /**
     * Post请求
     * @param
     * @return
     */
    public Response postMethod(UrlParam urlParam){

        Response response = null;
        if(urlParam.getHeaderMap() != null){
            //将map值转换成list集成的header
            List<Header> list = halderMapData(urlParam.getHeaderMap());
            //批量添加进入请求头/请求体 和请求参数
            RequestSpecification specification = handleQuestHeaderBody(list, urlParam);
            LoggerUtil.info("请求头："+urlParam.getHeaderMap().toString());
            LoggerUtil.info("请求前准备"+System.currentTimeMillis());
            response = specification.post(urlParam.getUrl());
            LoggerUtil.info("请求后数据"+response.asString()+"  "+System.currentTimeMillis());

        }else {
            LoggerUtil.info("请求头："+urlParam.getHeaderMap().toString());
            LoggerUtil.info("请求前准备"+System.currentTimeMillis());
            response = config.post(urlParam.getUrl());
            LoggerUtil.info("请求后数据"+response.asString()+"  "+System.currentTimeMillis());
        }
        config = given().config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())));

        return response;

    }

    /**
     * 批量添加请求头/请求体
     * @param list
     * @param params
     * @return
     */
    public RequestSpecification handleQuestHeaderBody(List<Header> list,UrlParam params){

        int num = list.size();
        //对请求参数进行判断并设置请求参数
        RequestSpecification specification = config;
        Map<String, String> paramMap = params.getParamMap();
        if(paramMap != null && paramMap.size() > 0 ){
            specification = checkParamesType(params);
        }

        //处理多个/单个请求体
        Map<String, String> map = params.getBodyMap();
        Map<String, File> bodyMapFile = params.getBodyMapFile();
        //判断使用json形式还是使用data形式 默认使用json
        if(params.isData()){

            if(bodyMapFile != null&& bodyMapFile.size() > 0){
                Iterator<Map.Entry<String, File>> it = bodyMapFile.entrySet().iterator();
                Map.Entry<String, File> entry = it.next();
                String key = entry.getKey();
                File value = entry.getValue();
                specification.header("Content-Type","multipart/form-data");
                specification.multiPart(key,value);
            }

            if(map != null && map.size() > 0){
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                Map.Entry<String, String> entry = it.next();
                String key = entry.getKey();
                String value = entry.getValue();
                specification.header("Content-Type","multipart/form-data");
                specification.multiPart(key,value);

            }

        }else {
            if(map != null){
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    String s = JSON.toJSONString(map);
                    specification.body(s);
                    break;
                }
                specification.contentType(ContentType.JSON);

            }
        }

        //处理多个/单个header
        specification.headers(params.getHeaderMap());

        LoggerUtil.info("请求数据处理完毕...");
        return specification;
    }


    /**
     * 判断使用哪种参数进行传输：1.单参数 2.map参数
     * @param params
     * @return
     */
    public RequestSpecification checkParamesType(UrlParam params){
        String param = params.getParam();
        RequestSpecification specification;
        Map<String, String> paramMap = params.getParamMap();
        if("".equals(param) && param != null){
            specification = config.param(param);
        }else if(paramMap != null && paramMap.size() > 0){
            specification =  config.params(paramMap);
        }else {
            specification =  config;
        }
        return specification;
    }


    /**
     * 处理map中的请求头参数
     * @param map
     * @return
     */
    private List<Header> halderMapData(Map<String,String> map){

        List<Header> list = new ArrayList();
        if(map == null){
            return null;
        }else {
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                String key = entry.getKey();
                String value = entry.getValue();
                Header header = new Header(key,value);
                list.add(header);
            }
        }
        return list;
    }


}
