package com.wulang.es.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wulang.es.utils.EsClient;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * @author wulang
 * @create 2020/9/19/13:06
 */
public class IdGetSearch {

    ObjectMapper mapper = new ObjectMapper();
    RestHighLevelClient client = EsClient.getClient();
    String index = "sms-logs-index";
    String type = "sms-logs-type";

    @Test
    public void findById() throws IOException {
        // 创建GetRequest对象
        GetRequest request = new GetRequest(index, "1");

        //  执行查询
        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        // 输出结果
        System.out.println(response.getSourceAsMap());
    }

    /**
     * 根据多个id 查询,类似 mysql 中的 where in (id1,id2...)
     */
    @Test
    public void findByIds() throws IOException {
        //  创建request对象
        SearchRequest request = new SearchRequest(index);

        //  指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------
        builder.query(QueryBuilders.idsQuery().addIds("1", "2", "3"));
        //------------------------------------------------------
        request.source(builder);

        // 执行
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    /**
     * 前缀查询，可以通过一个关键字去指定一个field 的前缀，从而查询到指定文档
     */
    @Test
    public void findByPrefix() throws IOException {
        //  创建request对象
        SearchRequest request = new SearchRequest(index);

        //  指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------
        builder.query(QueryBuilders.prefixQuery("corpName", "阿"));
        //------------------------------------------------------
        request.source(builder);

        // 执行
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    /**
     * 模糊查询，我们可以输入一个字符的大概，ES 可以根据输入的大概去匹配内容。查询结果不稳定
     */
    @Test
    public void findByFuzzy() throws IOException {
        //  创建request对象
        SearchRequest request = new SearchRequest(index);

        //  指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------
        builder.query(QueryBuilders.fuzzyQuery("corpName", "腾讯客堂").prefixLength(2));//指定前两个字符不能出错
        //------------------------------------------------------
        request.source(builder);

        // 执行
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    /**
     * 通配查询，同mysql中的like 是一样的，可以在查询时，在字符串中指定通配符*和占位符？
     */
    @Test
    public void findByWildCard() throws IOException {
        //  创建request对象
        SearchRequest request = new SearchRequest(index);

        //  指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------
        builder.query(QueryBuilders.wildcardQuery("corpName", "海尔*"));
        //------------------------------------------------------
        request.source(builder);

        // 执行
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    /**
     * 范围查询，只针对数值类型，对一个field 进行大于或者小于的范围指定
     */
    @Test
    public void findByRang() throws IOException {
        //  创建request对象
        SearchRequest request = new SearchRequest(index);

        //  指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------
        builder.query(QueryBuilders.rangeQuery("fee").gt(10).lte(30));
        //------------------------------------------------------
        request.source(builder);

        // 执行
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    /**
     * 正则查询，通过你编写的正则表达式去匹配内容
     * Ps:prefix wildcard  fuzzy 和regexp 查询效率比较低 ,在要求效率比较高时，避免使用
     */
    @Test
    public void findByRegexp() throws IOException {
        //  创建request对象
        SearchRequest request = new SearchRequest(index);
        request.types(type);

        //  指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------
        builder.query(QueryBuilders.regexpQuery("mobile", "138[0-9]{8}"));
        //------------------------------------------------------
        request.source(builder);

        // 执行
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        // 输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }
}
