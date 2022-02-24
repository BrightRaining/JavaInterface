package com.pumpink.runThreadPool.utils;

import com.alibaba.fastjson.JSON;
import com.pumpink.demo.bean.UrlParams;
import com.pumpink.demo.service.impl.RestAssuredUtilsImpl;
import com.pumpink.demo.service.interfaces.RestAssuredUtilsInf;
import com.pumpink.demo.utils.CheckResponseValue;
import com.pumpink.demo.utils.LoggerUtil;
import com.pumpink.runThreadPool.bean.RequestParam;
import com.pumpink.runThreadPool.bean.UrlParam;
import com.pumpink.runThreadPool.requestService.HttpRequest;
import io.restassured.response.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.*;

public class ThreadPoolUtils {

    /**
     * 制造线程数还与电脑配置有关系目前线程数在50误差在1s随着线程数的增多误差也会增多 -100以后就需要放在服务器上
     */
    static int num = 0;    //访问失败次数
    static int ThreadNum = 10;   //定义线程数
    static ExecutorService executorService;
    //  发令枪
    static CountDownLatch countDownLatch ;

    //将线程放入线程池提交
    public void runThread(String methodName, String classType, RequestParam requestParam){

        if(!"".equals(requestParam.getThreadNum())&&requestParam.getThreadNum() != null){
            int i = Integer.parseInt(requestParam.getThreadNum());
            ThreadNum = i;
        }

        executorService = Executors.newFixedThreadPool(ThreadNum);
        countDownLatch = new CountDownLatch(ThreadNum);
        for (int i = 0; i < ThreadNum; i++) {
              executorService.submit(buildThread(methodName, classType,requestParam));
           // executorService.execute(buildThread(methodName, classType,requestParam));
        }

        boolean terminated = executorService.isTerminated();
        if(terminated){
            executorService.shutdown();
        }
    }

    private Thread buildThread(String methodName,String classType,RequestParam requestParam){
        //创建线程
        Thread thread = new Thread(new Runnable() {
            public void run() {
                synchronized (countDownLatch) {  //这一步不知道有没有必要，但是我还是加了
                    //发令枪减1
                    countDownLatch.countDown();
                }

                try {
                    LoggerUtil.info("线程：" + Thread.currentThread().getName() + "准备");
                    //线程阻塞
                    countDownLatch.await();
                    //这里进行请求方法的塞入
                    Class<?> aClass = Class.forName(classType);
                    Method[] methods = aClass.getMethods();
                    for (Method method : methods) {
                        String name = method.getName();
                        if(name.equals(methodName)){
                            //Class<?>[] parameterTypes = method.getParameterTypes();
                            method.invoke(Class.forName(classType).newInstance(),requestParam);
                        }
                    }
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

            }
        });

        return thread;
    }


}
