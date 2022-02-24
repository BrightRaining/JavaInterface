package com.pumpink.demo.config;

import com.pumpink.demo.bean.UrlParams;
import com.pumpink.demo.bean.pojo.CheckResponseResult;
import com.pumpink.demo.bean.pojo.InfCallSequence;
import com.pumpink.demo.mapper.ChcekResultMapper;
import com.pumpink.demo.service.impl.RestAssuredUtilsImpl;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class HandleMethodClass {



    @Autowired
    CheckResponseResult checkResponseResult;

    @Autowired
    ChcekResultMapper chcekResultMapper;

    public void processingReport(String errorInfo){

        UrlParams urlParamsRes = RestAssuredUtilsImpl.getUrlParamsRes();
        //当前接口返回的响应信息
        Response responsePs = RestAssuredUtilsImpl.getResponsePs();

        //获取当前请求返回的响应信息
        String url = urlParamsRes.getUrl();
        String sb = null;
        if(url != null && url.contains("?")){
            sb = url.substring(0, url.indexOf("?"));
        }else {
            sb = url;
        }
        //这个是把域名删掉的东西
        String replace = "";

        //有错误说明运行失败 -全局设置为错误
        checkResponseResult.setResult(false);
        //找到错误的地址，设置错误信息和请求头等数据
        List<InfCallSequence> resultInfList = checkResponseResult.getResultInfList();
        if(resultInfList != null && resultInfList.size() > 0){
            for (InfCallSequence infCallSequence : resultInfList) {
                String interfaceName = infCallSequence.getInterfaceName();
                if(interfaceName.equals(replace)){
                    infCallSequence.setInfEnd(false);
                    infCallSequence.setErrorMsg("错误信息："+errorInfo +" 接口返回数据："+responsePs.asString());
                }

            }
        }

    }

    /**
     * 设置总报告
     */
    public void processingReport(){

            UrlParams urlParamsRes = RestAssuredUtilsImpl.getUrlParamsRes();
            Response responsePs = RestAssuredUtilsImpl.getResponsePs();

            //有错误说明运行失败 -全局设置为错误
            checkResponseResult.setResult(false);
            //找到错误的地址，设置错误信息和请求头等数据
            List<InfCallSequence> resultInfList = checkResponseResult.getResultInfList();
            List<InfCallSequence> list2 = new ArrayList<>();

            if(resultInfList != null){
                for (InfCallSequence infCallSequence : resultInfList) {
                    String interfaceName = infCallSequence.getInterfaceName();
                    //请求路径中包含了servletPath则进行设置报错信息s
                    if(urlParamsRes.getUrl().contains(interfaceName)){
                        infCallSequence.setInfEnd(false);
                        Map<String, String> headerMap = urlParamsRes.getHeaderMap();
                        infCallSequence.setInfRequestHeader(headerMap.toString());
                        infCallSequence.setInfReturnMsg(responsePs.asString());
                        infCallSequence.setErrorMsg("返回信息："+responsePs.asString());
                        Map<String, String> bodyMap = urlParamsRes.getBodyMap();
                        //有bodyMap说明是post请求将请求体返回-没有是get请求把url返回出去
                        if(bodyMap.size() > 0 && bodyMap.toString() != null){
                            infCallSequence.setInfRequestParam(bodyMap.toString());
                        }else {
                            infCallSequence.setInfRequestParam(urlParamsRes.getUrl());
                        }
                        infCallSequence.setMillis(checkResponseResult.getMillis());
                    }
                    list2.add(infCallSequence);
                }
                checkResponseResult.setResultInfList(list2);
            }
    }

    /**
     * 设置请求接口后响应的数据进入报告结果中
     * @param urlParams
     */
    public void endGetResponseSet(UrlParams urlParams){

        //获取当前请求返回的响应信息
        Response responsePs = RestAssuredUtilsImpl.getResponsePs();
        String url = urlParams.getUrl();
        String sb = null;
        if(url != null && url.contains("?")){
            sb = url.substring(0, url.indexOf("?"));
        }else {
            sb = url;
        }
        String replace = "";

        List<InfCallSequence> resultInfList = checkResponseResult.getResultInfList();
        for (InfCallSequence infCallSequence : resultInfList) {
            String interfaceName = infCallSequence.getInterfaceName();
            if(interfaceName.equals(replace)){
                //获取请求头中的数据
                Map<String, String> headerMap = urlParams.getHeaderMap();
                //设置进去请求头参数
                infCallSequence.setInfRequestHeader(headerMap.toString());
                //设置响应参数
                infCallSequence.setInfReturnMsg(responsePs.asString());
                Map<String, String> bodyMap = urlParams.getBodyMap();
                if(bodyMap.size() > 0 && bodyMap.toString() != null){
                    infCallSequence.setInfRequestParam(bodyMap.toString());
                }else {
                    infCallSequence.setInfRequestParam(urlParams.getUrl());
                }

            }
        }



    }

    /**
     * 接口调用时进行注入 在初始调用前执行 --监控每个接口调用
     * @param urlParams
     */
    public  void beginGetInfAdd(UrlParams urlParams){
        //处理一下链接，保持数据库中有且只有一条数据即可
        String url = urlParams.getUrl();
        String sb = null;
        if(url != null && url.contains("?")){
            sb = url.substring(0, url.indexOf("?"));
        }else {
            sb = url;
        }
//        String replace = sb.replace(domainName.getEnvPrifx()+domainName.getMiddleParmDomain(), "");
        String replace = "";
        //检查该接口是否已经存在 --若已存在则不进行后续操作
        boolean f = isTrue(checkResponseResult.getResultInfList(), replace);
        if(!f){
            //生成时间戳用来做唯一标识
            long millis = System.currentTimeMillis();
            //调错方法了
            String tableMiller = chcekResultMapper.getTableMiller(millis+"");
            //查询数据库中没有该时间戳时则可以使用
            if("".equals(tableMiller) || tableMiller == null){
                //关联字段插入
                checkResponseResult.setMillis(millis+"");
            }
            //取出 接口请求清单
            List<InfCallSequence> resultInfList = checkResponseResult.getResultInfList();
            InfCallSequence inf = new InfCallSequence();
            //开始执行某个接口的介绍
            inf.setInfStart("开始调用："+replace);
            //接口链接
            inf.setInterfaceName(replace);
            //如果没有查到接口用例名称 --则默认使用接口地址
            if(HttpTestAspect.getTestCaseName() == null){
                inf.setTestCaseName(replace);
            }else {
                inf.setTestCaseName(HttpTestAspect.getTestCaseName());
            }

            //将该请求模板加入接口报告列表中
            resultInfList.add(inf);
        }
    }

    private boolean isTrue(List<InfCallSequence> resultInfList,String url){
        boolean f = false;
        for (InfCallSequence infCallSequence : resultInfList) {
            if(infCallSequence.getInterfaceName().equals(url)){
                f = true;
            }else if(url.equals(infCallSequence.getInterfaceName())){
                f = true;
            }
        }
        return f;
    }

}
