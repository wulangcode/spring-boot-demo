package com.wulang.es.test;

import com.wulang.es.utils.EsClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
import org.elasticsearch.search.aggregations.metrics.ExtendedStats;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * @author wulang
 * @create 2020/9/19/14:10
 */
public class AggSearch {

    RestHighLevelClient client = EsClient.getClient();
    String index = "sms-logs-index";
    String type = "sms-logs-type";

    /**
     * 去重计数，cardinality 先将返回的文档中的一个指定的field进行去重，统计一共有多少条
     */
    @Test
    public void aggCardinality() throws IOException {

        // 1.创建request
        SearchRequest request = new SearchRequest(index);

        // 2. 指定使用聚合查询方式
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.aggregation(AggregationBuilders.cardinality("provinceAgg").field("province"));
        request.source(builder);

        // 3.执行查询
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 4.输出返回结果
        Cardinality agg = response.getAggregations().get("provinceAgg");
        System.out.println(agg.getValue());
    }

    /**
     * 统计一定范围内出现的文档个数，比如，针对某一个field 的值再0~100,100~200,200~300 之间文档出现的个数分别是多少
     * 范围统计 可以针对 普通的数值，针对时间类型，针对ip类型都可以响应。
     * <p>
     * 数值 rang
     * 时间  date_rang
     * ip   ip_rang
     */
    @Test
    public void aggRang() throws IOException {
        // 1.创建request
        SearchRequest request = new SearchRequest(index);

        // 2. 指定使用聚合查询方式
        SearchSourceBuilder builder = new SearchSourceBuilder();
        /*AggregationBuilders.ipRange()
        AggregationBuilders.dateRange()*/
        builder.aggregation(AggregationBuilders.range("agg").field("fee")
            .addUnboundedTo(30)
            .addRange(30, 60)
            .addUnboundedFrom(60));
        request.source(builder);

        // 3.执行查询
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 4.输出返回结果
        Range agg = response.getAggregations().get("agg");
        for (Range.Bucket bucket : agg.getBuckets()) {
            String key = bucket.getKeyAsString();
            Object from = bucket.getFrom();
            Object to = bucket.getTo();
            long docCount = bucket.getDocCount();
            System.out.println(String.format("key: %s ,from: %s ,to: %s ,docCount: %s", key, from, to, docCount));
        }
    }

    /**
     * 他可以帮你查询指定field 的最大值，最小值，平均值，平方和...
     * 使用 extended_stats
     */
    @Test
    public void aggExtendedStats() throws IOException {
        // 1.创建request
        SearchRequest request = new SearchRequest(index);

        // 2. 指定使用聚合查询方式
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.aggregation(AggregationBuilders.extendedStats("agg").field("fee"));
        request.source(builder);

        // 3.执行查询
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 4.输出返回结果
        ExtendedStats extendedStats = response.getAggregations().get("agg");
        System.out.println("最大值：" + extendedStats.getMaxAsString() + ",最小值：" + extendedStats.getMinAsString());
    }
}
