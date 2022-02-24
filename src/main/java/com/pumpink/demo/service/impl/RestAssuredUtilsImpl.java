package com.pumpink.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.pumpink.demo.bean.UrlParams;
import com.pumpink.demo.bean.pojo.CheckResponseResult;
import com.pumpink.demo.bean.pojo.InfCallSequence;
import com.pumpink.demo.service.interfaces.RestAssuredUtilsInf;
import com.pumpink.demo.utils.BeanTools;
import com.pumpink.demo.utils.LoggerUtil;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;

import io.restassured.http.ContentType;
import io.restassured.http.Header;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

import static io.restassured.RestAssured.given;


/**
 * @author:Bright
 * @date:2021/3/10
 * @notes： Fight for the beauty of the world
 */

@Service
public class RestAssuredUtilsImpl  implements RestAssuredUtilsInf{

    static UrlParams urlParamsRes = null;

    /**
     * 配置SSL 让所有请求支持所有的主机名
     */
    public static RequestSpecification config;

    static Response  responsePs;

    //代码块 加载一次
     {
        config = given().config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())));
     }


    /**
     * get请求
     * @param params 包含需要传输的参数
     * @return
     */
    @Override
    public Response getMethod(UrlParams params) {
        //用来返回测试结果
        urlParamsRes = params;

        Response response = null;
        if(params.getHeaderMap() != null){
            //将map值转换成list集成的header
            List<Header> list = halderMapData(params.getHeaderMap());
            //批量添加进入请求和请求参数
            RequestSpecification specification = handleQuestHeaderBody(list, params);
            String filePath = params.getFilePath();
            if(filePath != null && !"".equals(filePath)){
                
            }
            response = specification.get(params.getUrl());
            responsePs = response;
        }else {
            LoggerUtil.info("无参请求");
            response = config.get(params.getUrl());
            responsePs = response;
        }
        config = given().config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())));

        updateResultResonse(params,response.asString());
        return response;
    }

    /**
     * Post请求
     * @param
     * @return
     */
    public Response postMethod(UrlParams params){

        //用来返回测试结果
        urlParamsRes = params;

        Response response = null;
        if(params.getHeaderMap() != null){
            //将map值转换成list集成的header
            List<Header> list = halderMapData(params.getHeaderMap());
            //批量添加进入请求头/请求体 和请求参数
            RequestSpecification specification = handleQuestHeaderBody(list, params);
            response = specification.post(params.getUrl());
            responsePs = response;
        }else {
            LoggerUtil.info("无参请求");
            response = config.post(params.getUrl());
            responsePs = response;
        }
        config = given().config((RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation())));

        updateResultResonse(params,response.asString());
        return response;

    }




    /**
     * 批量添加请求头/请求体
     * @param list
     * @param params
     * @return
     */
    public RequestSpecification handleQuestHeaderBody(List<Header> list,UrlParams params){
        LoggerUtil.info("批量添加请求头进入URL:"+params.getUrl());
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
        Map<String, List<String>> filePicMap = params.getFilePicMap();

        if(params.isPic()){
            specification.body(JSON.toJSONString(filePicMap));
        }

        //判断使用json形式还是使用data形式 默认使用json
        if(params.isData()){

            if(bodyMapFile != null && bodyMapFile.size() > 0){
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
            if(map != null && map.size() > 0){
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    String s = JSON.toJSONString(map);
                    specification.body(s);
                    break;
                }
                specification.contentType(ContentType.JSON);

            }else {
                if(params.getParam()!=null&&!"".equals(params.getParam())){
                    specification.body(params.getParam());
                    specification.contentType(ContentType.JSON);
                }
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
    public RequestSpecification checkParamesType(UrlParams params){
        LoggerUtil.info("处理请求参数,使用Map/单参数");
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
        LoggerUtil.info("处理请求头转为List<Header>"+map.toString());
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
    /**
     * 修改报告集合中的值
     * @param urlParams
     * @param res
     */
    private void updateResultResonse(UrlParams urlParams,String res){
        CheckResponseResult result = BeanTools.getBean(CheckResponseResult.class);
        List<InfCallSequence> resultInfList = result.getResultInfList();
        String url = urlParams.getUrl();
        for (int i = 0; i < resultInfList.size(); i++) {
            InfCallSequence infCallSequence = resultInfList.get(i);
            String interfaceName = infCallSequence.getInterfaceName();
            if(url.contains(interfaceName)){
                Map<String, String> headerMap = urlParams.getHeaderMap();
                infCallSequence.setInfRequestHeader(headerMap.toString());
                infCallSequence.setInfReturnMsg(res);
                Map<String, String> bodyMap = urlParams.getBodyMap();
                if(bodyMap.size() > 0 && bodyMap.toString() != null){
                    infCallSequence.setInfRequestParam(bodyMap.toString());
                }else {
                    infCallSequence.setInfRequestParam(urlParams.getUrl());
                }
            }
        }
    }


    public static Response getResponsePs() {
        return responsePs;
    }

    public static void setResponsePs(Response responsePs) {
        RestAssuredUtilsImpl.responsePs = responsePs;
    }

    public static UrlParams getUrlParamsRes() {
        return urlParamsRes;
    }

    public static void setUrlParamsRes(UrlParams urlParamsRes) {
        RestAssuredUtilsImpl.urlParamsRes = urlParamsRes;
    }

}
