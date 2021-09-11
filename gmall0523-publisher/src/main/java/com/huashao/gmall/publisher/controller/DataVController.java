package com.huashao.gmall.publisher.controller;

import com.huashao.gmall.publisher.service.MySQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Author: huashao
 * Date: 2021/9/6
 * Desc:
 */
@RestController
public class DataVController {

    //自动注入，声明的是MySQLService，实际创建对象是他的实现类MySQLServiceImpl
    @Autowired
    MySQLService mySQLService;

    @RequestMapping("/trademark-sum")
    public Object trademarkSum(String startTime,String endTime,int topN){

        //实际调用的是MySQLServiceImpl的方法
        List<Map> rsMap = mySQLService.getTradeAmount(startTime, endTime, topN);
        return rsMap;
    }
}
