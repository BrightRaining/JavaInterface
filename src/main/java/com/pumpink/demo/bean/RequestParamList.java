package com.pumpink.demo.bean;

public class RequestParamList {

    /**
     * 获取签名的端口连接
     */
    private String apiPrifx;
    /**
     * 获取验证码接口链接
     */
    private String dorasPrifx;
    /**
     * 接口访问链接 + 端口
     */
    private String envPrifxPort;
    /**
     * 安全校验域名
     */
    private String secretApiPrifx;

    /**
     * 电话
     */
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSecretApiPrifx() {
        return secretApiPrifx;
    }

    public void setSecretApiPrifx(String secretApiPrifx) {
        this.secretApiPrifx = secretApiPrifx;
    }

    public String getApiPrifx() {
        return apiPrifx;
    }

    public void setApiPrifx(String apiPrifx) {
        this.apiPrifx = apiPrifx;
    }

    public String getDorasPrifx() {
        return dorasPrifx;
    }

    public void setDorasPrifx(String dorasPrifx) {
        this.dorasPrifx = dorasPrifx;
    }

    public String getEnvPrifxPort() {
        return envPrifxPort;
    }

    public void setEnvPrifxPort(String envPrifxPort) {
        this.envPrifxPort = envPrifxPort;
    }
}
