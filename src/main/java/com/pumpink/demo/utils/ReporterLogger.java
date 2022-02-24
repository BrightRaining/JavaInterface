package com.pumpink.demo.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.testng.Reporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReporterLogger {
    private static Logger LOGGER ; //使用org.apache.log4j.Logger 而不是org.slf4j.Logger;
    static final String FQCN = ReporterLogger.class.getName();  // og4j把传递进来的callerFQCN在堆栈中一一比较，相等后，再往上一层即认为是用户的调用类


    /**
     * 打印进行的测试日志
     * @param message  日志内容
     * @param clazz 调用的类
     */
    public static void  info(String message,Class<?> clazz){
        LOGGER = Logger.getLogger(clazz);
        //使用slf4j打印到控制台或者文件
        LOGGER.log(FQCN, Level.INFO,message,null);
        message = getLogTag()+message;
        //记录到Reporter
        Reporter.log(message);
    }

    //根据堆栈信息，拿到调用类的名称、方法名、行号
    private static String getLogTag(){
        String logTag = "";
        Long timeStamp = System.currentTimeMillis();
        String dateString = timestampToDate(timeStamp);
        StackTraceElement stack[] = (new Throwable()).getStackTrace();
        for(int i=0;i<stack.length;i++) {
            StackTraceElement s = stack[i];
            if(s.getClassName().equals(LOGGER.getName())){
                logTag= "["+dateString+"]"+"["+classNameDeal(s.getClassName())+":"+s.getMethodName()+":"+s.getLineNumber()+"]";
            }
        }
        return logTag;
    }

    //时间戳转date字符串
    private static String timestampToDate(Long timestamp){
        if(timestamp.toString().length() <13){
            timestamp = Long.valueOf(timestamp.toString().substring(0,10)+"000");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = new Date(timestamp);
        String dateStr = sdf.format(date);
        return dateStr;
    }

    //去掉包名，只保留类名
    private static String classNameDeal(String allName){
        String[] className = allName.split("\\.");
        return className[className.length-1];
    }
}