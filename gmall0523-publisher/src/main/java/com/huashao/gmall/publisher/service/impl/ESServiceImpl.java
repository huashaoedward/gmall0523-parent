package com.huashao.gmall.publisher.service.impl;

import com.huashao.gmall.publisher.service.ESService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.TermsAggregation;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.support.ValueType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: huashao
 * Date: 2021/8/27
 * Desc:
 */

//将当前对象的创建交给Spring容器进行管理,表示这是service层
@Service
public class ESServiceImpl implements ESService {

    //将ES的客户端操作对象注入到Service中。
    // 虽然ES的客户端的工具类是在不同的module下，但SpringBoot已帮集成好了，可直接注入
    @Autowired
    JestClient jestClient;

    /*
    GET /gmall0523_dau_info_2020-10-24-query/_search
    {
      "query": {
        "match_all": {}
      }
    }
     */
    @Override
    public Long getDauTotal(String date) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(new MatchAllQueryBuilder());
        String query = sourceBuilder.toString();
        String indexName = "gmall0523_dau_info_"+date+"-query";
        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .build();
        Long total = 0L;
        try {
            SearchResult result = jestClient.execute(search);
            total = result.getTotal();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("查询ES失败");
        }

        return total;
    }

    /*
    ！！！aggs这个关键词是分组统计，terms相当于groupby，field是分组字段，size是显示多少条
    GET /gmall0523_dau_info_2020-10-24-query/_search
    {
      "aggs": {
        "groupBy_hr": {
          "terms": {
            "field": "hr",
            "size": 24
          }
        }
      }
    }

     */
    @Override
    //查询某天某时段的日活数
    //把以上的es查询语句转成代码
    /*
    * 代码逻辑：jestClient.execute(search)，要search,要querry，要sourceBuilder，要termsAggregationBuilder。。。*/
    public Map<String, Long> getDauHour(String date) {
        Map<String, Long> hourMap = new HashMap<>();

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        /*
        "groupBy_hr"是聚合函数的名字，ValueType是聚合统计结果的类型，是Long
        field是分组字段，size是显示多少条
         */
        TermsAggregationBuilder termsAggregationBuilder =
                new TermsAggregationBuilder("groupBy_hr", ValueType.LONG).field("hr").size(24);

        //第二种生成方式
//        TermsAggregationBuilder aggBuilder =
//                AggregationBuilders.terms("groupby_hr").field("hr").size(24);

        sourceBuilder.aggregation(termsAggregationBuilder);

        String indexName = "gmall0523_dau_info_"+ date +"-query";
        //转成字符串
        String query = sourceBuilder.toString();
        Search search = new Search.Builder(query)
                .addIndex(indexName)
                .build();
        try {
            SearchResult result = jestClient.execute(search);
            TermsAggregation termsAgg = result.getAggregations().getTermsAggregation("groupBy_hr");
            //必须判空
            if(termsAgg!=null){
                List<TermsAggregation.Entry> buckets = termsAgg.getBuckets();
                for (TermsAggregation.Entry bucket : buckets) {
                    //k是小时，v是统计数
                    hourMap.put(bucket.getKey(),bucket.getCount());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("ES查询异常");
        }
        return hourMap;
    }
}
