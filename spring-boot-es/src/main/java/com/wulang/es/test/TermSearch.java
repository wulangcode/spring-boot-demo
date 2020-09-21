package com.wulang.es.test;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wulang.es.pojo.SmsLogs;
import com.wulang.es.utils.EsClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * <br>
 * term 查询是代表完全匹配，搜索之前不会对你搜索的关键字进行分词，直接拿 关键字 去文档分词库中匹配内容
 * </br>
 * terms 和 term 查询的机制一样，搜索之前不会对你搜索的关键字进行分词，直接拿 关键字 去文档分词库中匹配内容
 * terms:是针对一个字段包含多个值
 * term : where province =北京
 * terms: where province = 北京  or  province =?  (类似于mysql 中的 in)
 * 也可针对 text,  只是在分词库中查询的时候不会进行分词
 *
 * @author wulang
 * @create 2020/9/19/12:38
 */
public class TermSearch {
    ObjectMapper mapper = new ObjectMapper();
    RestHighLevelClient client = EsClient.getClient();
    String index = "sms-logs-index";
    String type = "sms-logs-type";

    @Test
    public void termSearchTest() throws IOException {
        // 1.创建request对象
        SearchRequest request = new SearchRequest(index);
        request.types(type);

        //  2.创建查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.from(0);
        builder.size(5);
        builder.query(QueryBuilders.termQuery("province", "北京"));

        request.source(builder);

        //  3.执行查询
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4.输出查询结果
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            SmsLogs smsLogs = JSON.parseObject(JSON.toJSONString(sourceAsMap), SmsLogs.class);
            System.out.println(smsLogs);
//            System.out.println(sourceAsMap);

        }
    }

    @Test
    public void termsSearchTest() throws IOException {
        // 1.创建request对象
        SearchRequest request = new SearchRequest(index);

        // 2.创建查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.termsQuery("province", "北京", "晋城"));
        request.source(builder);

        // 3.执行查询
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 输出查询结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }
}
