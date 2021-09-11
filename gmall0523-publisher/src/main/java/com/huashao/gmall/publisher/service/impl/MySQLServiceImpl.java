package com.huashao.gmall.publisher.service.impl;

import com.huashao.gmall.publisher.mapper.TrademarkStatMapper;
import com.huashao.gmall.publisher.service.MySQLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Author: huashao
 * Date: 2021/9/6
 * Desc:
 */

//！！！表示这是service层的
@Service
public class MySQLServiceImpl implements MySQLService {

    @Autowired
    TrademarkStatMapper trademarkStatMapper;

    @Override
    public List<Map> getTradeAmount(String startTime, String endTime, int topN) {

        //底层是调用的mapper的方法
        return trademarkStatMapper.selectTradeSum(startTime,endTime,topN);
    }
}
