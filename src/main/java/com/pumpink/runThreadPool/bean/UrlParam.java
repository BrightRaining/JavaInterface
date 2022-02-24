package com.pumpink.runThreadPool.bean;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class UrlParam {

    //请求链接
    private String url;
    //单请求参数
    private String param;
    //多参数
    private Map<String,String> paramMap = new HashMap<>();
    //请求头参数集合
    private Map<String,String> headerMap;
    //请求体参数Map集合
    private Map<String,String> bodyMap = new HashMap<>();

    private Map<String, File> bodyMapFile = new HashMap<>();

    private String type;

    private String token;

    private String testCaseName;

    //用来判断post请求时使用json还是form-data 默认false使用json形式
    private boolean data;


    public Map<String, File> getBodyMapFile() {
        return bodyMapFile;
    }

    public void setBodyMapFile(Map<String, File> bodyMapFile) {
        this.bodyMapFile = bodyMapFile;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getBodyMap() {
        return bodyMap;
    }

    public void setBodyMap(Map<String, String> bodyMap) {
        this.bodyMap = bodyMap;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }
}
