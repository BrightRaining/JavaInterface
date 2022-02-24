package com.pumpink.demo.bean;


import com.pumpink.demo.bean.pojo.CheckResponseResult;
import com.pumpink.demo.bean.pojo.InfCallSequence;
import com.pumpink.demo.mapper.ChcekResultMapper;
import com.pumpink.demo.utils.BeanTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UrlParams {

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

    private String type;

    private String token;

    private String testCaseName;

    //用来判断post请求时使用json还是form-data 默认false使用json形式
    private boolean data;

    //传输文件时使用，不用传文件直接传空即可
    private String filePath = null;

    private Map<String, File> bodyMapFile = new HashMap<>();

    private boolean pic = false;
    private Map<String,List<String>> filePicMap = new HashMap<>();

    public Map<String, List<String>> getFilePicMap() {
        return filePicMap;
    }

    public boolean isPic() {
        return pic;
    }

    public void setPic(boolean pic) {
        this.pic = pic;
    }

    public void setFilePicMap(Map<String, List<String>> filePicMap) {
        this.filePicMap = filePicMap;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

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
//        updateCheckResponseResult(url);
    }

//    /**
//     * 接口调用时进行自动注入校验类
//     * @param url
//     */
//    public  void updateCheckResponseResult(String url){
//        long millis = System.currentTimeMillis();
//        CheckResponseResult responseResult = BeanTools.getBean(CheckResponseResult.class);
//        String millis1 = responseResult.getMillis();
//        ChcekResultMapper mapper = BeanTools.getBean(ChcekResultMapper.class);
//        String tableMiller = mapper.getTableMiller(millis1);
//        if(millis1 == null || tableMiller != null){
//            //关联字段插入
//            responseResult.setMillis(millis+"");
//        }
//        List<InfCallSequence> resultInfList = responseResult.getResultInfList();
//        //用来判断该接口有没有已经存在报告中如果存在了就不再添加
//        boolean flag = false;
//        InfCallSequence inf = new InfCallSequence();
//        if(resultInfList != null){
//            int size = resultInfList.size();
//            for (int i = 0; i < size; i++) {
//                InfCallSequence infCallSequence = resultInfList.get(i);
//                if(!url.equals(infCallSequence.getInterfaceName())){
//                    boolean aTrue = isTrue(resultInfList, url);
//                    if(!aTrue){
//                        inf.setInfStart("开始调用："+url);
//                        inf.setInterfaceName(url);
//                        resultInfList.add(inf);
//                        flag = true;
//                    }
//                }
//            }
//        }
//
//
//        if(!flag){
//            //如果传进来的地址有http的不用记录
//            if(!url.contains("http")) {
//                inf.setInfStart("开始调用：" + url);
//                inf.setInterfaceName(url);
//                resultInfList.add(inf);
//            }
//
//        }
//
//
//    }
//
//    private boolean isTrue(List<InfCallSequence> resultInfList,String url){
//        boolean f = false;
//        for (InfCallSequence infCallSequence : resultInfList) {
//            if(infCallSequence.getInterfaceName().contains(url)){
//                f = true;
//            }else if(url.contains(infCallSequence.getInterfaceName())){
//                f = true;
//            }
//        }
//        return f;
//    }

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

    @Override
    public String toString() {
        return "UrlParams{" +
                "url='" + url + '\'' +
                ", param='" + param + '\'' +
                ", paramMap=" + paramMap +
                ", headerMap=" + headerMap +
                '}';
    }
}
