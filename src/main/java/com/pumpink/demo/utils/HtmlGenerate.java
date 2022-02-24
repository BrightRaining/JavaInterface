package com.pumpink.demo.utils;

import com.pumpink.demo.bean.pojo.CheckResponseResult;

import java.io.*;
import java.util.Date;

public class HtmlGenerate {


    private static String path = "/testPumkin/index.html";
    private static String context = "<tr>\t\t\t\t<td>1</td>\t\t\t\t<td>modelName</td>\t\t\t\t<td>结果</td>\t\t\t\t<td>\t\t\t\t\t<button id=\"btn\" type=\"button\" onclick=\"javascript:window.location.href='https://www.baidu.com/';\" class=\"btn btn-success dropdown-toggle \">\t\t\t\t\t  <span  aria-hidden=\"true\"  ></span> 查看\t\t\t\t\t</button>\t\t\t\t</td>\t\t\t</tr>";
    private static String test = "index.html";

    /**
     * 根据本地模板生成静态页面  -这个html是主界面的内容
     *
     * @param
     * @return
     */
    public static String htmlFileGener(CheckResponseResult checkResponseResult) {
        String str = "";
        String path = null;
        long beginDate = (new Date()).getTime();
        //在用jar包的形式部署在服务器上时，会无法读取模板位置，所以直接通过代码生成文件比较方便
        try {
            String s = replaceContent(checkResponseResult);
            path = "index_" + System.currentTimeMillis() + ".html";
            File f = new File(path);

            BufferedWriter o = new BufferedWriter(new FileWriter(f));
            o.write(s);
            o.close();
            LoggerUtil.info("共用时：" + ((new Date()).getTime() - beginDate) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }


    private static String replaceContent(CheckResponseResult checkResponseResults) {

        StringBuffer sb = new StringBuffer();
        sb.append("<!doctype html>\n" +
                "<html lang=\"zh-CN\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->\n" +
                "    <title>自动化接口测试报告</title>\n" +
                "\n" +
                "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\" integrity=\"sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu\" crossorigin=\"anonymous\">\n" +
                "      <script src=\"https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js\"></script>\n" +
                "      <script src=\"https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js\"></script>\n" +
                "\n" +
                "\n" +
                "  </head>\n");

        sb.append("  <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->\n" +
                "    <script src=\"https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js\" integrity=\"sha384-nvAa0+6Qg9clwYCGGPpDQLVpLNn0fRaROjHqs13t4Ggj3Ez50XnGQqc/r8MhnRDZ\" crossorigin=\"anonymous\"></script>\n" +
                "    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->\n" +
                "    <script src=\"https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js\" integrity=\"sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd\" crossorigin=\"anonymous\"></script>\n");

        sb.append("<script>\n" +
                "\n" +
                "// 执行加载方法之后,设置.样式.\n" +
                "var up = function updates(){\n" +
                "  //使用id选择器;例如:tab对象->tr->td对象.\n" +
                "  $(\".centerContentTd\").each(function(i){\n" +
                "  //获取td当前对象的文本,如果长度大于25;\n" +
                "  if($(this).text().length>100){\n" +
                "  //给td设置title属性,并且设置td的完整值.给title属性.\n" +
                "  $(this).attr(\"title\",$(this).text());\n" +
                "  //获取td的值,进行截取。赋值给text变量保存.\n" +
                "  var text=$(this).text().substring(0,60)+\"...\";\n" +
                "  //重新为td赋值;\n" +
                "  $(this).text(text);\n" +
                "\n" +
                "      }\n" +
                "    });\n" +
                "}\n" +
                "\n" +
                "var dataHandel = function getDataResult(millers){\n" +
                "  $.getJSON(\"http:121.199.33.137:8089/searchReport?millis=\"+millers,function(data){\n" +
                "    var result = '';\n" +
                "    result+= '<table  class=\"table table-striped table-bordered table-hover table-condensed\">'\n" +
                "    result+= '<thead>'\n" +
                "    result+= '<tr>'\n" +
                "    result+= '<th width=5px>#</th>'\n" +
                "    result+= '<th width=80px>名称</th>'\n" +
                "    result+= '<th width=80px>执行结果</th>'\n" +
                //"    // result+= '<th>请求头</th>'\n" +
               // "    result+= '<th width=100px>请求链接及其请求参数</th>'\n" +
//                "    result+= '<th>返回信息</th>'\n" +
                "    result+= '<th width=80px>错误信息</th>'\n" +
                "    result+= '</tr>'\n" +
                "    result+= '</thead>'\n");
        //这里需要替换
        sb.append("$.each(data.resultInfList,function(idx,item){\n" +
//                "        if(idx==0){\n" +
//                "          return true;\n" +
//                "        }\n" +
                "        result+= '<tbody>'\n" +
                "        result+= '<tr>'\n" +
                "        result+= '<td>'+(idx+1)+'</td>'\n" +
               // "        result+= '<td>'+item.interfaceName+'</td>'\n" +
                "        result+= '<td>'+item.testCaseName+'</td>'\n" +
                "        if(item.infEnd==true){\n" +
                "          result+= '<td>Pass</td>'\n" +
                "        }else if(item.infEnd==false){\n" +
                "          result+= '<td>Fail</td>'\n" +
                "        }\n" +
               // "        // result+= '<td>'+item.infRequestHeader+'</td>'\n" +
                //"        result+= '<td class=\"centerContentTd\">'+item.infRequestParam+'</td>'\n" +
//                "        result+= '<td class=\"centerContentTd\">'+item.infReturnMsg+'</td>'\n" +
                "        if(item.errorMsg !== undefined){\n" +
                "          result+= '<td class=\"centerContentTd\">'+item.errorMsg+'</td>'\n" +
                "        }\n" +
                "        result+= '</tr>'\n" +
                "        result+= '</tbody>'\n" +
                "    });");
        //改变id
        sb.append("result+= '</table>'\n" +
                "\n" +
                 "  $(\"#tt\").after(result);"+
                "\n" +
                "\n" +
                "    setTimeout(function()\n" +
                "      {\n" +
                "          if($('.centerContentTd').is(':visible'))\n" +
                "            up();\n" +
//                "          else\n" +
//                "            alert('no');\n" +
                "      }, 3);\n" +
                "  });\n" +
                "}");

        sb.append("</script>\n");

        sb.append("  <body>\n" +
                "\t\t<div id='contents' class=\"container\">\n" +
                "\t<h2>表格</h2>\n" +
                "\t<p>总执行结果:</p>\n" +
                "\t<table id='moban' class=\"table table-striped table-bordered table-hover table-condensed\">\n" +
                "\t\t<thead>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<th>#</th>\n" +
                "\t\t\t\t<th>模块名称</th>\n" +
                "\t\t\t\t<th>结果</th>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</thead>\n" +
                "\t\t<tbody>");


        if (checkResponseResults != null ) {
                sb.append("<tr>");

                sb.append("<td>");
                sb.append(1);
                sb.append("</td>");

                sb.append("<td>");
                sb.append(checkResponseResults.getMsg());
                sb.append("</td>");

                sb.append("<td>");
                sb.append(checkResponseResults.isResult());
                sb.append("</td>");

                sb.append("<td width='100px'>");
                sb.append("<button id=\"" + checkResponseResults.getMillis() + "\" type=\"button\" onclick=\"dataHandel('" + checkResponseResults.getMillis() + "')\" class=\"btn btn-success dropdown-toggle \" ><span  aria-hidden=\"true\"  ></span> 查看</button>\n");
                sb.append("</td>");

                sb.append("<tr/>");
            }

            sb.append("\t\t</tbody>\n" +
                    "\t</table>\n" +
                    "</div>\n");

            sb.append("<div id=\"tt\" class=\"container\"></div>\n");
            sb.append("\t</body>\n" +
                    "</html>");

        String s = sb.toString();
        return s;
    }
}

