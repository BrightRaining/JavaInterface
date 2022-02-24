package com.pumpink.demo.config;

import com.pumpink.demo.bean.UrlParams;
import com.pumpink.demo.bean.pojo.CheckResponseResult;
import com.pumpink.demo.mapper.RuntimeDB;
import com.pumpink.demo.utils.CheckResponseValue;
import com.pumpink.demo.utils.HtmlGenerate;
import com.pumpink.demo.utils.LoggerUtil;
import com.pumpink.demo.utils.file.SendEmail;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Map;

//https://blog.csdn.net/u012050154/article/details/77370297

@Aspect
@Component
public class HttpTestAspect {

    static String testCaseName;

    @Autowired
    CheckResponseResult checkResponseResult;

    @Autowired
    HandleMethodClass handleMethodClass;

    @Autowired
    RuntimeDB runtimeDB;

    @Autowired
    CheckResponseResult responseResult;

    @Autowired
    SendEmail sendEmail;

    //切入点即程序中通用的逻辑业务
    @Pointcut("execution(public * com.pumpink.demo.testsPac.*..*(..))")
    public void ponitCut(){

    }

    //剖析请求的方法 --判断什么时候方法运行结束
    @Pointcut("execution(public * com.pumpink.demo.RunAllTestPac.*..*(..))")
    public void pointMian(){

    }

    //监听发送请求的请求参数和响应参数
    @Pointcut("execution(public * com.pumpink.demo.service.impl.*..*(..))")
    public void pointResponse(){

    }


    /**
     * 当前方法是在执行具体的请求方法的时候执行环绕通知 --即使出现错误也会继续运行下一个方法
     * @param joinPoint
     * @return
     */
    @Around("ponitCut()")
    public Object around(ProceedingJoinPoint joinPoint){

        // before 监听运行的方法名称对应的用例名称
        LoggerUtil.info("目标方法名为:" + joinPoint.getSignature().getName());
        String name = joinPoint.getSignature().getName();
        Map<String, String> map = CheckResponseValue.readFileProperties("testName.properties");
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> next = it.next();
            String key = next.getKey();
            String value = next.getValue();
            if (name.equals(key)) {
                //设置该方法下的所有接口请求均为该
                testCaseName = value;
                break;
            }else {
                testCaseName = null;
            }
        }


        //下方监听方法运行
        Object proceed = null;
        try {
            // do action
            LoggerUtil.info("===== Around do action "+joinPoint.getSignature().getName()+" begin =======");
            // 执行方法,返回接口
            proceed = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {

            LoggerUtil.info("===== Around do action "+joinPoint.getSignature().getName()+" error Msg +"+ e +" =======");
            handleMethodClass.processingReport(e.toString());
        } finally {
            LoggerUtil.info("===== Around do action "+joinPoint.getSignature().getName()+" finally =======");
        }
        return proceed;
    }



    /**
     * 当接口调用方法结束时将报告插入数据库 --同时兼顾运行过程中报错的处理
     * @param joinPoint
     */
    @Around("pointMian()")
    public Object listenRequestPoint(ProceedingJoinPoint joinPoint){
        LoggerUtil.info("接收到请求 -- 执行方法名为："+joinPoint.getSignature().getName());
        Object proceed = null;
        try {
            LoggerUtil.info("===== Around do action "+joinPoint.getSignature().getName()+" begin =======");
            proceed = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            LoggerUtil.info("===== Around do action "+joinPoint.getSignature().getName()+" error =======");
            e.printStackTrace();
        }finally {
            LoggerUtil.info("===== Around do action "+joinPoint.getSignature().getName()+" finally =======");
            //向数据库中插入报告 --测试时记得注释,打包上传时打开
//            runtimeDB.insertResult(responseResult);
//            String s1 = HtmlGenerate.htmlFileGener(checkResponseResult);
//            sendEmail.send(checkResponseResult,s1);
        }
        return proceed;
    }




    /**
     * 接口请求列表设值 监听发送和响应请求的包即可
     * @param joinPoint
     */
    @Around("pointResponse()")
    public Object obtainGetParams(ProceedingJoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();
        UrlParams urlParams = (UrlParams) args[0];

        LoggerUtil.info("请求参数预备："+urlParams.toString());
        Object proceed = null;
        try {
            LoggerUtil.info("===== Around do action "+joinPoint.getSignature().getName()+" begin =======");
            //进行请求参数设置进入接口列表模板中
            handleMethodClass.beginGetInfAdd(urlParams);
            proceed = joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            LoggerUtil.info("===== Around do action "+joinPoint.getSignature().getName()+" error =======");
            e.printStackTrace();
        }finally {
            LoggerUtil.info("===== Around do action "+joinPoint.getSignature().getName()+" finally =======");
            //结束时将报告更新将返回参数设置进去
            handleMethodClass.endGetResponseSet(urlParams);
        }
        return proceed;
    }




    public static String getTestCaseName() {
        return testCaseName;
    }

    public static void setTestCaseName(String testCaseName) {
        HttpTestAspect.testCaseName = testCaseName;
    }
}
