package com.wulang.es.test;

import com.wulang.es.utils.EsClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * query 查询：根据你的查询条件，去计算文档的匹配度得到一个分数，并根据分数排序，不会做缓存的。
 * <p>
 * filter 查询：根据查询条件去查询文档，不去计算分数，而且filter会对经常被过滤的数据进行缓存。
 *
 * @author wulang
 * @create 2020/9/19/13:57
 */
public class FilterSearch {
    RestHighLevelClient client = EsClient.getClient();
    String index = "sms-logs-index";
    String type = "sms-logs-type";

    @Test
    public void filter() throws IOException {

        // 1.searchRequest
        SearchRequest searchRequest = new SearchRequest(index);

        // 2.指定查询条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.filter(QueryBuilders.termQuery("corpName", "海尔智家公司"));
        boolBuilder.filter(QueryBuilders.rangeQuery("fee").gt(20));
        sourceBuilder.query(boolBuilder);
        searchRequest.source(sourceBuilder);

        //  3.执行
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        //  4. 输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
            System.out.println(hit.getId() + "的分数是：" + hit.getScore());
        }
    }
}
