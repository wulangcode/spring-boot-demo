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
 * 1）match query：用于执行全文查询的标准查询，包括模糊匹配和短语或接近查询。重要参数：控制Token之间的布尔关系：operator：or/and
 * 2）match_phrase query：与match查询类似，但用于匹配确切的短语或单词接近匹配。重要参数：Token之间的位置距离：slop 参数
 * 3）match_phrase_prefix query：与match_phrase查询类似，但是会对最后一个Token在倒排序索引列表中进行通配符搜索。重要参数：模糊匹配数控制：max_expansions 默认值50，
 * 最小值为1
 * <p>
 * common terms query 目的：在保证检索性能的前提下，提高搜索结果的准确度。（能检索到the a 等高频率的停顿词）
 * <p/>
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

    /**
     * 允许我们在单个查询字符串中指定AND | OR | NOT条件，同时也和 multi_match query 一样，支持多字段搜索。
     */
    @Test
    public void matchStringSearch() throws IOException {
        // 1.创建request对象
        SearchRequest request = new SearchRequest(index);

        //  2.创建查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------------------
        builder.query(QueryBuilders.queryStringQuery("province:'" + "北京" + "' AND appName:'" + "北京" + "'"));
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
     * simple_query_string支持以下特殊字符：
     *
     * + 表示与运算，相当于query_string 的 AND
     * | 表示或运算，相当于query_string  的 OR
     * - 取反单个令牌,相当于query_string 的 NOT
     * "" 表示对检索词进行 match_phrase query
     * * 字词末尾表示前缀查询
     */
    @Test
    public void simpleQueryStringSearch() throws IOException {
        // 1.创建request对象
        SearchRequest request = new SearchRequest(index);

        //  2.创建查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //--------------------------------------------------------------
        builder.query(QueryBuilders.simpleQueryStringQuery("北京").field("province").defaultOperator(Operator.AND));
        /**
         *"query" : "\"北京\""，会对检索词执行 match_phrase query ！
         */
        //builder.query(QueryBuilders.simpleQueryStringQuery("\""+"北京"+"\"").field("province").defaultOperator(Operator.AND));
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
