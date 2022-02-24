package com.pumpink.demo.utils.file;

import java.io.*;
import java.util.*;

public class ReadFile {



    //private static String notNullfilePath = "CheckParams.txt";
    private static String regulaParams = "";

    static Map<String,String> map = new HashMap<>();








    /**
     * 校验非空字段集
     * @return
     */
    public static List<String> doinput() {

        List<String> list = null;

        try {

            File file  = new File(ReadFile.class.getClassLoader().getResource(regulaParams).getPath());
            //System.out.println(file.getCanonicalFile());
            FileReader fr = new FileReader(file.getCanonicalFile());//文件必须事先存在
            //读取数据
            //int read() 读取单个字符，返回的是字符编码

            int num;
            list = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            while((num = fr.read())!=-1){
                if(num != 44){
                    sb.append((char)num);
                }else {
                    list.add(sb.toString());
//                    System.out.println("单循环打印："+sb.toString());
                    sb = new StringBuilder();
                }
            }
            fr.close();
//        System.out.println("list集合展示数据"+list.toString());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return list;
    }

 }
