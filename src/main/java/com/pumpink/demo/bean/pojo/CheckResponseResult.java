package com.pumpink.demo.bean.pojo;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试结果记录类
 */
@Component
public class CheckResponseResult{

    private int id;

    //标记当前的执行地点
    private String flag;

    //结果描述
    private String totalCase;

    //执行的方法名称-记录对外开放运行的方法名
    private String methodName;

    //方法执行前的描述 如：xxxx接口校验开始执行
    private String msg;

    //接口测试结果
    private boolean result = true;

    //拓展字段-备用  作为执行时间使用了
    private String expand;

    //时间戳-用来做与执行集合做关联
    private String millis;

    //接口执行流程存放点
    List<InfCallSequence> resultInfList = new ArrayList<>();



    public String getTotalCase() {
        return totalCase;
    }

    public void setTotalCase(String totalCase) {
        this.totalCase = totalCase;
    }

    public String getMillis() {
        return millis;
    }

    public void setMillis(String millis) {
        this.millis = millis;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<InfCallSequence> getResultInfList() {
        return resultInfList;
    }

    public void setResultInfList(List<InfCallSequence> resultInfList) {
        this.resultInfList = resultInfList;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }


    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "{" +
                "methodName:'" + methodName + '\'' +
                ", msg:'" + msg + '\'' +
                ", result:" + result +
                ", expand:'" + expand + '\'' +
                ", resultInfList:" + resultInfList +
                '}';
    }
}
