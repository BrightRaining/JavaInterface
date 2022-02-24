package com.pumpink.demo.service.interfaces;

import com.pumpink.demo.bean.UrlParams;
import io.restassured.response.Response;

/**
 * @author:Bright
 * @date:2021/3/10
 * @notes： Fight for the beauty of the world
 */
public interface RestAssuredUtilsInf {

     Response getMethod(UrlParams params);

     Response postMethod(UrlParams params);

}
