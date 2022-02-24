package com.pumpink.runThreadPool.bean;

public class RequestParam {


    /**
     * 环境域名
     */
    private String envPort;

    /**
     * 需要调用的方法名称
     */
    private String methodName;
    /**
     * 线程数
     */
    private String threadNum;
    /**
     * 红包id
     */
    private String packId;
    /**
     * 放映厅id
     */
    private String channelId;

    public String getEnvPort() {
        return envPort;
    }

    public void setEnvPort(String envPort) {
        this.envPort = envPort;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(String threadNum) {
        this.threadNum = threadNum;
    }

    public String getPackId() {
        return packId;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
