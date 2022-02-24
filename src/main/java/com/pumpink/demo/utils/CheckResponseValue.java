package com.pumpink.demo.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import groovy.util.logging.Slf4j;
import io.restassured.response.Response;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CheckResponseValue {

    static Map<String,String> map = new HashMap<>();
    //若配置文件中的正则表达式没有填写则默认判断非空
    final static String notNullValue = "/^\\s*$/";

    //校验规则
    static {
        try {
            URL url = CheckResponseValue.class.getClassLoader().getResource("regularMent.properties");
            InputStreamReader inputStreamReader = null;
            Properties properties = new Properties();

            InputStream inputStream = url.openStream();
            inputStreamReader = new InputStreamReader(inputStream);
            //加载配置文件;
            properties.load(inputStreamReader);
            //根据key获取value;
            Set<Object> objects = properties.keySet();
            for (Object object : objects) {
                String value = properties.getProperty(object
                        .toString()).toString();
                map.put(object.toString(),value);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




    public static Map<String,String> readFileProperties(String fileName){

        Map<String,String> map =new HashMap<>();

        try {
            URL url = CheckResponseValue.class.getClassLoader().getResource(fileName);
            InputStreamReader inputStreamReader = null;
            Properties properties = new Properties();
            InputStream inputStream = url.openStream();
            inputStreamReader = new InputStreamReader(inputStream);
            //加载配置文件;
            properties.load(inputStreamReader);
            //根据key获取value;
            Set<Object> objects = properties.keySet();
            for (Object object : objects) {
                String value = properties.getProperty(object
                        .toString()).toString();
                map.put(object.toString(),value);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }



    /**
     * 检验返回的字段是否缺失
     * @param json
     */
    public static void jsonResponseRule(String json,Object ob){

        if (ob != null ) {
            //循环遍历对象的属性值进行判断必要值是否存在
            //通过getDeclaredFields()方法获取对象类中的所有属性（含私有）
            Field[] fields = ob.getClass().getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                StringBuffer sb = new StringBuffer(json);
                System.out.println("开始检验设定字段名称："+name);
                if(sb.indexOf(name) < 0){
                    throw new RuntimeException("该字段在返回数据中未找到！"+name);
                }else if(sb.indexOf(name)>0){
                    //如果找到了，将位置再向后推一位看是否有字段名字多位数
                    int i = sb.indexOf(name);
                    String substring = sb.substring(i+name.length(), name.length()+i+1);
                    String reg = "[a-z0-9A-Z]+$";
                    boolean matches = substring.matches(reg);
                    if(matches){
                        throw new RuntimeException("该字段与设定字段名称不一致,请检查!");
                    }
                }
            }
        }

    }






    /**
     * 返回字段中字段值校验
     * @param catg_item_list
     * @param ob 接口返回的数字
     */
    public static void regularResponseKeyWord(List catg_item_list,Object ob){

        if (ob != null ) {
            //循环遍历对象的属性值进行判断必要值是否存在
            //通过getDeclaredFields()方法获取对象类中的所有属性（含私有）
            Field[] fields = ob.getClass().getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                //设置允许通过反射访问私有变量
                field.setAccessible(true);
                //获取字段的值
                String value = null;
                try {
                    Object o = field.get(ob);
                    if(o != null) {
                        value = field.get(ob).toString();
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


                //进行属性值字段校验
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> entry = it.next();
                    String key = entry.getKey();
                    String entryValue = entry.getValue();
                    if(name.equals(key)){
                        LoggerUtil.info("进行字段校验 属性名："+name+" 属性值："+value);
                        //若配置文件中未设定具体规则则默认判空
                        if("".equals(entryValue)){
                            boolean matches = value.matches(notNullValue);
                            if(matches){
                                throw new RuntimeException("正则非空字段校验失败！报错字段名称："+name);
                            }
                        }else {
//                            Pattern p=Pattern.compile(entryValue);
//                            Matcher m=p.matcher(value);
//                            System.out.println(m.matches());
                            boolean matches = value.matches(entryValue);
                            if(!matches){
                                throw new RuntimeException("正则校验失败不符合预期内容！报错字段："+name+" 请对照配置文件检查错误,正则表达式："+entryValue);
                            }
                        }

                    }
                }
            }
        }


        if (catg_item_list != null) {
            for (Object item_list : catg_item_list) {
                //循环遍历对象的属性值进行判断必要值是否存在
                //通过getDeclaredFields()方法获取对象类中的所有属性（含私有）
                Field[] fields = item_list.getClass().getDeclaredFields();
                for (Field field : fields) {
                    String name = field.getName();
                    //设置允许通过反射访问私有变量
                    field.setAccessible(true);
                    //获取字段的值
                    String value = null;
                    try {
                        Object o = field.get(item_list);
                        if(o != null) {
                            value = field.get(item_list).toString();
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                    //进行属性值字段校验
                    Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, String> entry = it.next();
                        String key = entry.getKey();
                        String entryValue = entry.getValue();
                        if(name.equals(key)){
                            LoggerUtil.info("进行字段校验 属性名："+name+" 属性值："+value);
                            //若配置文件中未设定具体规则则默认判空
                            if("".equals(entryValue)){
                                boolean matches = value.matches(notNullValue);
                                if(matches){
                                    throw new RuntimeException("正则非空字段校验失败！报错字段名称："+name);
                                }
                            }else {
                                boolean matches = value.matches(entryValue);
                                if(!matches){
                                    throw new RuntimeException("正则校验失败不符合预期内容！报错字段："+name+" 请对照配置文件检查错误,正则表达式为："+entryValue);
                                }
                            }


                        }
                    }

                }
            }
        }

    }




    /**
     * 将返回的字段全部解析出来
     * @param objJson
     * @return
     */
    static String  analysisJson(Object objJson) {

        StringBuffer sb = new StringBuffer();

        //如果objJson为json数组
        if(objJson instanceof JSONArray) {
            JSONArray objArray = (JSONArray)objJson;
            for (int i = 0; i < objArray.size(); i++) {
                analysisJson(objArray.get(i));
                sb.append(objArray.get(i));
            }
        } else if(objJson instanceof JSONObject) { //如果objJson为json对象
            JSONObject jsonObject = (JSONObject)objJson;
            Iterator it = jsonObject.keySet().iterator();
            while(it.hasNext()) {
                String key = it.next().toString();
                sb.append(key);
                Object value = jsonObject.get(key); //value
                if(value instanceof JSONArray) { //如果value是数组
                    JSONArray objArray = (JSONArray)value;
                    analysisJson(objArray);
                } else if(value instanceof JSONObject){ //如果value是json对象
                    analysisJson((JSONObject)value);
                } else { //如果value是基本类型
                    System.out.println("["+key+"]:"+value.toString()+" ");
//                    sb.append(key);
                }
            }
        } else { //objJson为基本类型
            LoggerUtil.info(objJson.toString() + " ");
//            sb.append(objJson.toString());
        }

        return sb.toString();
    }


    public static void checkResponseNotIsSpecialChar(String result,String str){
        String regEx = "[_`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】。、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        boolean b = m.find();
        if(b){
            throw new RuntimeException("未通过正则校验请检查！字段名称："+str);
        }

    }




    public static void checkMessage(String json){

        if(!"".equals(json)){
            String message = JSON.parseObject(json).getString("message");
            //LoggerUtil.info("返回Message: "+json,CheckResponseValue.class);
            if(message != null){
                if(message.equals("系统异常, 请联系南瓜电影客服.") || "".equals(message)){
                    throw new RuntimeException("接口访问异常！"+json);
                }
            }
        }

    }



    /**
     *
     * @param result 接口返回字符串
     * @param expectedResults 预期值
     * @param actualValue 实际需要值
     */
    public static void checkValue(String result, String expectedMessage,String actualMessage, String expectedResults, String actualValue){
        checkMessage(result);
      //  LoggerUtil.info("返回值:"+result,CheckResponseValue.class);

        if(!expectedMessage.equals(actualMessage)){
            throw new RuntimeException("返回信息:message有误请检查！预期值："+expectedMessage+"实际值:"+actualMessage+" 接口返回数据:"+result);
        }
        if(expectedResults != null && !expectedResults.equals(actualValue)){
            throw new RuntimeException("返回信息有误请检查！预期值:"+expectedResults+" 实际值:"+actualValue+" 接口返回数据:"+result);
        }
        if(expectedResults == null && actualValue != null){
            if(!actualValue.matches("[0-9]{1,}")){
                throw new RuntimeException("验证码有误请检查！接口返回数据:"+result);
            }
        }
    }

    public static void checkValueNotEmpty(String s,String type,String value){
        checkMessage(s);
        LoggerUtil.info(s+" 返回值 "+type+":"+value);
        if(value == null || "".equals(value)){
            throw new RuntimeException("返回值不能为空");
        }
    }

    public static void checkValue(String result,String expectedResults, String actualValue){
        checkMessage(result);
        if(expectedResults != null && !expectedResults.equals(actualValue)){
            throw new RuntimeException("判断失败！预期值:"+expectedResults+" 实际值:"+actualValue+" 接口返回数据:"+result);
        }
    }

    public static void checkUserZhu(String result,String expectedResults, String actualValue){
        checkMessage(result);
        if(expectedResults != null && expectedResults.equals(actualValue)){
            throw new RuntimeException("该账户已被注销/列入黑名单！预期值:"+expectedResults+" 实际值:"+actualValue+" 接口返回数据:"+result);
        }else {
            LoggerUtil.info("该账户未被列入黑名单/被注销 预期值:"+expectedResults+" 实际值:"+actualValue+" 接口返回数据:");
        }

    }


    public static void checkCode(Response result){
        int statusCode = result.getStatusCode();
        if(statusCode != 200){
            throw new RuntimeException("接口访问异常！请检查！"+result.asString());
        }
    }


}
