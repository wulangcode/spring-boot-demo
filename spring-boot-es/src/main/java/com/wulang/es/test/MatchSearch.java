package com.wulang.es.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wulang.es.utils.EsClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * match 查询属于高级查询，会根据你查询字段的类型不一样，采用不同的查询方式
 * 查询的是日期或者数值，他会将你基于字符串的查询内容转换为日期或数值对待
 * 如果查询的内容是一个不能被分词的内容（keyword）,match 不会将你指定的关键字进行分词
 * 如果查询的内容是一个可以被分词的内容（text）,match 查询会将你指定的内容根据一定的方式进行分词，去分词库中匹配指定的内容
 * <br>
 * match 查询，实际底层就是多个term 查询，将多个term查询的结果给你封装到一起
 * <br/>
 *
 * @author wulang
 * @create 2020/9/19/12:55
 */
public class MatchSearch {
    ObjectMapper mapper = new ObjectMapper();
    RestHighLevelClient client = EsClient.getClient();
    String index = "sms-logs-index";
    String type = "sms-logs-type";

    @Test
    public void matchAllSearch() throws IOException {
        // 1.创建request对象
        SearchRequest request = new SearchRequest(index);

        //  2.创建查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        //  ES 默认只查询10条数据
        builder.size(20);
        request.source(builder);

        //  3.执行查询
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4.输出查询结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
        System.out.println(response.getHits().getHits().length);
    }

    @Test
    public void matchSearch() throws IOException {
        // 1.创建request对象
        SearchRequest request = new SearchRequest(index);

        //  2.创建查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------------------
        builder.query(QueryBuilders.matchQuery("smsContent", "伟大战士"));
        //--------------------------------------------------------------
        builder.size(20);
        request.source(builder);

        //  3.执行查询
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4.输出查询结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
        System.out.println(response.getHits().getHits().length);
    }

    /**
     * 基于一个field 匹配的内容，按照 and 或者or的方式连接
     */
    @Test
    public void booleanMatchSearch() throws IOException {
        // 1.创建request对象
        SearchRequest request = new SearchRequest(index);

        //  2.创建查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------------------
//        builder.query(QueryBuilders.matchQuery("smsContent", "战士 团队").operator(Operator.AND));
        builder.query(QueryBuilders.matchQuery("smsContent", "战士 团队").operator(Operator.OR));
        //--------------------------------------------------------------
        builder.size(20);
        request.source(builder);

        //  3.执行查询
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4.输出查询结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
        System.out.println(response.getHits().getHits().length);
    }

    /**
     * match 针对一个field 做检索，multi_math 针对多个field 进行检索，多个field对应一个文本。
     */
    @Test
    public void multiMatchSearch() throws IOException {
        // 1.创建request对象
        SearchRequest request = new SearchRequest(index);

        //  2.创建查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------------------
        builder.query(QueryBuilders.multiMatchQuery("北京", "province", "smsContent"));
        //--------------------------------------------------------------
        builder.size(20);
        request.source(builder);

        //  3.执行查询
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4.输出查询结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
        System.out.println(response.getHits().getHits().length);
    }

}
