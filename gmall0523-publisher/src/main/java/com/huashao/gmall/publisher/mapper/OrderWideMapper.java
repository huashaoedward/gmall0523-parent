package com.huashao.gmall.publisher.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Author: huashao
 * Date: 2021/9/5
 * Desc: 对订单宽表进行操作的接口
 */
public interface OrderWideMapper {
    //获取指定日期的交易额
    BigDecimal selectOrderAmountTotal(String date);

    //获取指定日期的分时交易额，返回值是 map<小时，交易额>，并存入list
    List<Map> selectOrderAmountHour(String date);
}
