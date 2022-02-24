package com.pumpink.runThreadPool.pool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoolThread{

    /**
     * https://www.cnblogs.com/mei0619/p/7096235.html  反射机制-通过反射动态的调用方法
     * 制造线程数还与电脑配置有关系目前线程数在50误差在1s随着线程数的增多误差也会增多 -100以后就需要放在服务器上
     */
    static int num = 0;    //访问失败次数
    static int ThreadNum = 50;   //定义线程数

    static CountDownLatch countDownLatch = new CountDownLatch(ThreadNum);

    public void runThread(int threadNum){
        ThreadNum = threadNum;
        ExecutorService executorService = Executors.newFixedThreadPool(ThreadNum);
        for (int i = 0; i < ThreadNum; i++) {
            executorService.submit(buildThread());
            executorService.execute(buildThread());
        }
    }

    public Thread buildThread(){
        //创建线程
        Thread thread = new Thread(new Runnable() {
            public void run() {
                synchronized (countDownLatch) {  //这一步不知道有没有必要，但是我还是加了
                    //发令枪减1
                    countDownLatch.countDown();
                }

                try {
                    System.out.println("线程：" + Thread.currentThread().getName() + "准备");
                    //线程阻塞
                    countDownLatch.await();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        return thread;
    }

    public void reflactMethod(String methodName){
        try {
            Class<?> aClass = Class.forName("");
            //获取类中的方法
            Method method = aClass.getMethod(methodName);
            //获取该方法中的参数类型
            Class<?>[] parameterTypes = method.getParameterTypes();
            if(parameterTypes.length > 0 && parameterTypes != null){
                
            }else {
                method.invoke(aClass.newInstance());
            }
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
