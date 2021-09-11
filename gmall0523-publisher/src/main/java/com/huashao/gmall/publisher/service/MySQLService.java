package com.huashao.gmall.publisher.service;

import java.util.List;
import java.util.Map;

/**
 * Author: huashao
 * Date: 2021/9/6
 * Desc: 根据起始时间和前几，从mysql中获取交易额
 */
public interface MySQLService {

    List<Map> getTradeAmount(String startTime,String endTime,int topN);

}
