package com.huashao.gmall.publisher.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Author: huashao
 * Date: 2021/9/7
 * Desc:
 */
public interface TrademarkStatMapper {

    //要注意的是注解@Param
    List<Map> selectTradeSum(@Param("start_time")String startTime,
                             @Param("end_time") String endTime,
                             @Param("topN") int topN);
}
