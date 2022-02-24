package com.pumpink.demo.mapper;

import com.pumpink.demo.bean.pojo.CheckResponseResult;
import com.pumpink.demo.bean.pojo.InfCallSequence;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface ChcekResultMapper {

    //根据时间戳查询不分组
    CheckResponseResult searchResultNotGroup(String millis);

     //总结果插入
     @Transactional
    public int insertResult(CheckResponseResult responseResult);
     //各个结果插入
     @Transactional
     int insertInfList(List<InfCallSequence> infCallSequence);
     //根据条件查出来数据
     @Transactional
     CheckResponseResult searchResult(String millis);
//     //查询表中是否存在这个时间戳
     String getTableMiller(String millis);

     int insertResultFu(InfCallSequence infCallSequence);
//     //查询附表的时间戳是否存在
     //String getTableLstMiller(String millis);

     //根据时间查出结果报告返回的数据是集合
     List<CheckResponseResult> searchReportByTime(String time);
}
