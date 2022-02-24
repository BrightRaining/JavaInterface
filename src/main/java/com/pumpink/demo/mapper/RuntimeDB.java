package com.pumpink.demo.mapper;

import com.alibaba.fastjson.JSON;
import com.pumpink.demo.bean.pojo.CheckResponseResult;
import com.pumpink.demo.bean.pojo.InfCallSequence;
import com.pumpink.demo.utils.BeanTools;
import com.pumpink.demo.utils.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RuntimeDB {

    @Autowired
    ChcekResultMapper mapper;



    /**
     * 将执行结果录入数据库
     * @param result
     * @return
     */
    public CheckResponseResult insertResult(CheckResponseResult result){

        CheckResponseResult responseResult = BeanTools.getBean(CheckResponseResult.class);
        List<InfCallSequence> resultInfList = responseResult.getResultInfList();

        for (InfCallSequence infCallSequence : resultInfList) {
            infCallSequence.setMillis(responseResult.getMillis());
        }

        for (int i = 0; i < resultInfList.size(); i++) {
            LoggerUtil.info("开始添加数据"+resultInfList.get(i).toString());
            mapper.insertResultFu(resultInfList.get(i));
        }
        mapper.insertResult(responseResult);


        //分组后的数据
        CheckResponseResult rs = searchResponseResult(responseResult.getMillis());
        List<InfCallSequence> resultGroup = rs.getResultInfList();

        String millis = rs.getMillis();
        //不分组的查询数据 -如果执行过程中发生错误则要将错误信息放入分组后的报告中
        CheckResponseResult checkResponseResult = searchResponseNotGroup(millis);
        List<InfCallSequence> rts = checkResponseResult.getResultInfList();
        for (InfCallSequence rt : rts) {
            if(rt.getErrorMsg() != null){
                String interfaceName = rt.getInterfaceName();
                for (InfCallSequence infCallSequence : resultGroup) {
                    if(interfaceName.equals(infCallSequence.getInterfaceName())){
                        infCallSequence.setErrorMsg(rt.getErrorMsg());
                    }
                }
            }
        }

        LoggerUtil.info(JSON.toJSONString(rs));
        return rs;
    }

    /**
     * 根据时间戳查询 -不分组
     * @param millis
     * @return
     */
    public CheckResponseResult searchResponseNotGroup(String millis){
        CheckResponseResult checkResponseResult = mapper.searchResultNotGroup(millis);
        return checkResponseResult;
    }



    /**
     * 根据时间错查询报告 -有分组
     * @param millis
     * @return
     */
    public CheckResponseResult searchResponseResult(String millis){
        CheckResponseResult rs = mapper.searchResult(millis);

        List<InfCallSequence> resultInfList = rs.getResultInfList();

        int pass = 0;
        int failed = 0;
        for (InfCallSequence infCallSequence : resultInfList) {
            boolean infEnd = infCallSequence.getInfEnd();
            if(infEnd){
                pass ++;
            }else {
                failed++;
            }
        }

        //不分组的查询数据 -如果执行过程中发生错误则要将错误信息放入分组后的报告中
        CheckResponseResult checkResponseResult = searchResponseNotGroup(millis);
        List<InfCallSequence> rts = checkResponseResult.getResultInfList();
        for (InfCallSequence rt : rts) {
            if(rt.getErrorMsg() != null){
                String interfaceName = rt.getInterfaceName();
                for (InfCallSequence infCallSequence : resultInfList) {
                    if(interfaceName.equals(infCallSequence.getInterfaceName())){
                        infCallSequence.setErrorMsg(rt.getErrorMsg());
                        infCallSequence.setInfEnd(false);
                    }
                }
            }
        }

        return rs;
    }


    /**
     * 根据一段时间查询报告
     * @param time
     */
    public  List<CheckResponseResult> searchReportByTime(String time){
        LoggerUtil.info("开始查询集合报告："+time);
        List<CheckResponseResult> checkResponseResults = mapper.searchReportByTime(time);

        for (CheckResponseResult checkResponseResult : checkResponseResults) {
            int pass = 0;
            int failed = 0;
            List<InfCallSequence> resultInfList = checkResponseResult.getResultInfList();
            for (InfCallSequence infCallSequence : resultInfList) {
                boolean infEnd = infCallSequence.getInfEnd();
                if(infEnd){
                    pass ++;
                }else {
                    failed++;
                }
            }
        checkResponseResult.setTotalCase("总计执行用例："+resultInfList.size()+"条  Pass: "+pass +"条  Failed: "+failed+" 条");
        }
        return checkResponseResults;
    }



}
