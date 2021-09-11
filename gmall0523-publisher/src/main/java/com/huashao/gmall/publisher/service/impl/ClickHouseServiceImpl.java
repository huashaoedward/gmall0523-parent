package com.huashao.gmall.publisher.service.impl;

import com.huashao.gmall.publisher.mapper.OrderWideMapper;
import com.huashao.gmall.publisher.service.ClickHouseService;
import com.huashao.gmall.publisher.mapper.OrderWideMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: huashao
 * Date: 2021/9/6
 * Desc:
 */
//备注这是service层
@Service
public class ClickHouseServiceImpl implements ClickHouseService {

    @Autowired
    OrderWideMapper orderWideMapper;

    @Override
    public BigDecimal getOrderAmountTocal(String date) {

        //底层是调用mapper的方法
        return orderWideMapper.selectOrderAmountTotal(date);
    }

    @Override  //List<Map{hr->11,am->10000}>  ==> Map<String, BigDecimal>
    public Map<String, BigDecimal> getOrderAmountHour(String date) {
        Map<String, BigDecimal> rsMap = new HashMap<String, BigDecimal>();
        List<Map> mapList = orderWideMapper.selectOrderAmountHour(date);
        for (Map map : mapList) {
            //注意：key的名称不能随便写，和mapper映射文件中，查询语句的别名一致
            //小时数是2位，如果不足两位前面补0，是String类型；
            rsMap.put(String.format("%02d",map.get("hr")),(BigDecimal) map.get("am"));
        }
        return rsMap;
    }
}
