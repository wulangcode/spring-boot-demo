package com.wulang.es.test;

import com.wulang.es.utils.EsClient;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.BoostingQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * 复合过滤器，将你的多个查询条件 以一定的逻辑组合在一起，
 * <p>
 * must:所有条件组合在一起，表示 and 的意思
 * must_not: 将must_not中的条件，全部都不匹配，表示not的意思
 * should:所有条件用should 组合在一起，表示or 的意思
 *
 * @author wulang
 * @create 2020/9/19/13:45
 */
public class BoolSearch {
    RestHighLevelClient client = EsClient.getClient();
    String index = "sms-logs-index";
    String type = "sms-logs-type";

    /**
     * minimum_should_match：
     * 参数指定should返回的文档必须匹配的子句的数量或百分比。如果bool查询包含至少一个should子句，而没有must或 filter子句，则默认值为1。
     * 否则，默认值为0
     *
     * matchPhrasePrefixQuery：短语前缀搜索
     * matchPhraseQuery：短语搜索 或指定分词器analyzer standrd
     * @throws IOException
     */
    @Test
    public void boolSearch() throws IOException {

        //  1.创建 searchRequest
        SearchRequest request = new SearchRequest(index);
        request.types(type);
        // 2.指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // #省是 晋城 或者北京
        boolQueryBuilder.should(QueryBuilders.termQuery("province", "北京"));
        boolQueryBuilder.should(QueryBuilders.termQuery("province", "晋城"));

        //# 运营商不能是联通
        boolQueryBuilder.mustNot(QueryBuilders.termQuery("operatorId", 2));

        //#smsContent 包含 战士 和的
        boolQueryBuilder.must(QueryBuilders.matchQuery("smsContent", "战士"));
        boolQueryBuilder.must(QueryBuilders.matchQuery("smsContent", "的"));

        builder.query(boolQueryBuilder);
        request.source(builder);
        //  3.执行查询
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4.输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }

    /**
     * boosting 查询可以帮助我们去影响查询后的score
     * positive:只有匹配上positive 查询的内容，才会被放到返回的结果集中
     * negative: 如果匹配上了positive 也匹配上了negative, 就可以 降低这样的文档score.
     * negative_boost:指定系数,必须小于1   0.5
     * <p>
     * 关于查询时，分数时如何计算的：
     * 搜索的关键字再文档中出现的频次越高，分数越高
     * 指定的文档内容越短，分数越高。
     * 我们再搜索时，指定的关键字也会被分词，这个被分词的内容，被分词库匹配的个数越多，分数就越高。
     */
    @Test
    public void boostSearch() throws IOException {

        //  1.创建 searchRequest
        SearchRequest request = new SearchRequest(index);
        // 2.指定查询条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoostingQueryBuilder boost = QueryBuilders.boostingQuery(
            QueryBuilders.matchQuery("smsContent", "战士"),
            QueryBuilders.matchQuery("smsContent", "团队")
        ).negativeBoost(0.2f);
        builder.query(boost);
        request.source(builder);
        //  3.执行查询
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        // 4.输出结果
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }
}
