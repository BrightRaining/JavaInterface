package com.pumpink.demo.bean.pojo;

import org.springframework.stereotype.Component;

@Component
public class InfCallSequence {

    private int id;

    private String guanlian;

    //用例名称
    private String testCaseName;

    //接口名称
    private String interfaceName;

    //接口请求头
    private String infRequestHeader;

    //接口请求参数
    private String infRequestParam;

    //xxx接口开始执行
    private String infStart;

    //xxx接口校验结果
    private boolean infEnd = true;

    //接口返回的结果
    private String infReturnMsg;

    //时间戳-用来做与执行集合做关联
    private String millis = null;

    //错误信息
    private String errorMsg;


    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public String getMillis() {
        return millis;
    }

    public void setMillis(String millis) {
        this.millis = millis;
    }

    public String getInfRequestHeader() {
        return infRequestHeader;
    }

    public void setInfRequestHeader(String infRequestHeader) {
        this.infRequestHeader = infRequestHeader;
    }

    public String getInfRequestParam() {
        return infRequestParam;
    }

    public void setInfRequestParam(String infRequestParam) {
        this.infRequestParam = infRequestParam;
    }

    public String getInfReturnMsg() {
        return infReturnMsg;
    }

    public void setInfReturnMsg(String infReturnMsg) {
        this.infReturnMsg = infReturnMsg;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    public String getInfStart() {
        return infStart;
    }

    public void setInfStart(String infStart) {
        this.infStart = infStart;
    }

    public boolean getInfEnd() {
        return infEnd;
    }

    public void setInfEnd(boolean infEnd) {
        this.infEnd = infEnd;
    }

    public boolean isInfEnd() {
        return infEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuanlian() {
        return guanlian;
    }

    public void setGuanlian(String guanlian) {
        this.guanlian = guanlian;
    }

    @Override
    public String toString() {
        return "{" +
                "interfaceName:'" + interfaceName + '\'' +
                ", infRequestHeader:'" + infRequestHeader + '\'' +
                ", infRequestParam:'" + infRequestParam + '\'' +
                ", infStart:'" + infStart + '\'' +
                ", infEnd:" + infEnd +
                ", infReturnMsg:'" + infReturnMsg + '\'' +
                ", errorMsg:'" + errorMsg + '\'' +
                '}';
    }
}
